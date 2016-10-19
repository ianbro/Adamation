package com.ianmann.mind;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.ianmann.mind.core.Constants;
import com.ianmann.mind.emotions.EmotionUnit;
import com.ianmann.mind.utils.Serializer;
import com.ianmann.utils.utilities.Files;

/**
 * Root class for all thoughts. Every thought object
 * will inherit {@code Neuron}.
 * @author kirkp1ia
 *
 */
public class Neuron implements Serializable {

	/**
	 * References to any neuron that is linked to this neuron.
	 * The AI will use this list to link through the thoughts.
	 * The file it points to contains one {@code NeuralPathway} object
	 * and can be thought of as a synaptic connection.
	 */
	private ArrayList<File> SynapticEndings;
	
	/**
	 * File in which this object is stored.
	 */
	public File location;
	
	/**
	 * EmotionUnit that is associated with this thought
	 */
	private EmotionUnit associatedEmotion;
	
	/**
	 * Used by developers or other users looking into the AI
	 * to get a sense of what this neuron actually stands for.
	 */
	private String label;
	
	private String[] associatedMorphemes;
	
	/**
	 * Create Neuron with an existing neuron linked to it.
	 * @param _linkedThought
	 * @param _associated
	 */
	public Neuron(Neuron _linkedThought, EmotionUnit _associated) {
		this.initialize(_linkedThought, _associated, null);
		this.associatedMorphemes = new String[0];
		this.save();
	}
	
	/**
	 * Create Neuron with an existing neuron linked to it.
	 * This takes a string that can later be used by a developer
	 * to have a sense of what this neuron represents.
	 * @param _linkedThought
	 * @param _associated
	 */
	public Neuron(Neuron _linkedThought, EmotionUnit _associated, String _label) {
		this.initialize(_linkedThought, _associated, _label);
		this.associatedMorphemes = new String[]{_label};
		this.save();
	}
	
	/**
	 * Constructors should call this method to do all the final attribute initialization.
	 * @param _linkedThought
	 * @param _associated
	 * @param _label
	 */
	private void initialize(Neuron _linkedThought, EmotionUnit _associated, String _label) {
		this.SynapticEndings = new ArrayList<File>();
		this.addNeuralPathway(_linkedThought);
		
		this.associatedEmotion = _associated;
		this.label = _label;
		this.location = new File(this.getFileLocation());

		try {
			java.nio.file.Files.createFile(this.location.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a morpheme to the list of morphemes associated with this neuron.
	 * @param _morpheme
	 */
	public void addAssociatedMorpheme(String _morpheme) {
		/*
		 * We have to add a spot to the array of morphemes.
		 * So we copy the array to a temporary array, then
		 * re-define the array to one with one more element
		 * in it than previously.
		 */
		String[] ms = new String[this.associatedMorphemes.length];
		
		for (int i = 0; i < this.associatedMorphemes.length; i++) {
			ms[i] = this.associatedMorphemes[i];
		}
		
		this.associatedMorphemes = new String[ms.length + 1];
		
		for (int i = 0; i < ms.length; i++) {
			this.associatedMorphemes[i] = ms[i];
		}
		
		/*
		 * Now add _morpheme to the array.
		 */
		this.associatedMorphemes[this.associatedMorphemes.length-1] = _morpheme;
	}
	
	/**
	 * Return the array of morphemes associated with this neuron.
	 * @return
	 */
	public String[] getAssociatedMorphemes() {
		return this.associatedMorphemes;
	}
	
	/**
	 * Retrieve the location to the file containing this Neuron
	 */
	private String getFileLocation() {
		if (this.location == null) {
			Scanner s;
			try {
				s = new Scanner(new File(Constants.NEURON_ROOT + "ids"));
				int next = s.nextInt();
				s.close();
				PrintWriter p = new PrintWriter(new File(Constants.NEURON_ROOT + "ids"));
				p.print(next+1);
				p.close();
				return Constants.NEURON_ROOT + String.valueOf(next) + ".nrn";
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			return this.location.getPath();
		}
	}
	
	/**
	 * Make new pathway to a thought.
	 * @param _thought
	 */
	public void addNeuralPathway(Neuron _thought) {
		if (_thought != null) {
			NeuralPathway t = new NeuralPathway(_thought.location);
			this.SynapticEndings.add(t.location);
		}
	}
	
	/**
	 * Print this object to the file at {@link Neuron.location}
	 */
	private void save() {
		FileOutputStream fos = null;
		try {
			byte[] serializedThought = Serializer.serialize(this);
			fos = new FileOutputStream(this.location);
			fos.write(serializedThought);
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads a file with a serialized {@link Neuron} object in it
	 * and parses it into an instance of {@link Neuron}
	 * @param _inputFile
	 * @return
	 */
	public static Neuron deserialize(File _inputFile) {
		try {
			byte[] fileBytes = Files.readFile(_inputFile);
			Neuron n = Serializer.deserialize(Neuron.class, fileBytes);
			return n;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
