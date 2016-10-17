package com.ianmann.mind;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

import com.ianmann.mind.core.Constants;
import com.ianmann.mind.utils.Serializer;
import com.ianmann.utils.utilities.Files;

/**
 * Representation of an event that triggers a thought
 * to occur. This most likely will just be text input.
 * @author kirkp1ia
 *
 */
public class Stimulant implements Serializable {
	
	/**
	 * The thought that is activated by this
	 * stimulant. This file contains an instance
	 * of {@link NeuralPathway}.
	 */
	private File reaction;
	
	/**
	 * File in which this object is stored.
	 */
	public File location;
	
	public Stimulant(String _identifyer, Neuron _reactionNeuron) {
		this.reaction = new NeuralPathway(_reactionNeuron.location).location;
		
		this.location = new File(Constants.STIMULANT_ROOT + _identifyer + ".stim");
		
		try {
			java.nio.file.Files.createFile(this.location.toPath());
			this.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the link to the corresponding thought
	 * @return
	 */
	private NeuralPathway getNeuralPathwayFromFile() {
		return NeuralPathway.deserialize(this.reaction);
	}
	
	/**
	 * Returns the Neuron object which represents the reaction
	 * of the AI to this stimulant.
	 * @return
	 */
	public NeuralPathway getReaction() {
		return this.getNeuralPathwayFromFile();
	}
	
	/**
	 * Print this object to the file at {@link Stimulant.location}
	 */
	private void save() {
		FileOutputStream fos = null;
		try {
			byte[] serializedStimulant = Serializer.serialize(this);
			fos = new FileOutputStream(this.location);
			fos.write(serializedStimulant);
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads a file with a serialized {@link Stimulant} object in it
	 * and parses it into an instance of {@link Stimulant}
	 * @param _inputFile
	 * @return
	 */
	public static Stimulant deserialize(File _inputFile) {
		try {
			byte[] fileBytes = Files.readFile(_inputFile);
			Stimulant s = Serializer.deserialize(Stimulant.class, fileBytes);
			return s;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
