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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.ianmann.mind.core.Constants;
import com.ianmann.mind.emotions.EmotionUnit;
import com.ianmann.utils.utilities.Files;

/**
 * Root class for all thoughts. Every thought object
 * will inherit {@code Neuron}.
 * @author kirkp1ia
 *
 */
public class Neuron {

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
	 * Create Neuron with an existing neuron linked to it.
	 * @param _linkedThought
	 * @param _associated
	 */
	public Neuron(Neuron _linkedThought, EmotionUnit _associated) {
		this.SynapticEndings = new ArrayList<File>();
		this.addNeuralPathway(_linkedThought);
		
		this.associatedEmotion = _associated;
		
		this.location = new File(this.getFileLocation());

		try {
			java.nio.file.Files.createFile(this.location.toPath());
			this.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		byte[] serializedThought = this.serialized();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(this.location);
			fos.write(serializedThought);
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] serialized() {
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			oos.flush();
		} catch (IOException e) {}
		return baos.toByteArray();
	}
	
	/**
	 * Serialize this object as a java object
	 * @param _serializedObject
	 * @return
	 */
	public static Neuron deserialize(byte[] _serializedObject) {
		Neuron n = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(_serializedObject);
			ObjectInputStream ois = new ObjectInputStream(bais);
			n = (Neuron) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return n;
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
			Neuron n = deserialize(fileBytes);
			System.out.println(n);
			return n;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
