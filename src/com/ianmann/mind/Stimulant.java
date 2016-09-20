package com.ianmann.mind;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;

import com.ianmann.mind.core.Constants;

/**
 * Representation of an event that triggers a thought
 * to occur. This most likely will just be text input.
 * @author kirkp1ia
 *
 */
public class Stimulant {
	
	/**
	 * The thought that is activated by this
	 * stimulant. This file contains an instance
	 * of {@link ThoughtLink}.
	 */
	private File linkToThought;
	
	/**
	 * File in which this object is stored.
	 */
	public File location;
	
	public Stimulant(String identifyer, File _thoughtLink) {
		this.linkToThought = _thoughtLink;
		
		this.location = new File(Constants.STIMULANT_ROOT + identifyer + ".stim");
		
		try {
			Files.createFile(this.location.toPath());
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
	private ThoughtLink getThoughtFromFile() {
		return ThoughtLink.deserialize(this.linkToThought);
	}
	
	/**
	 * Print this object to the file at {@link Stimulant.location}
	 */
	private void save() {
		String serializedStimulant = this.serialized();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(this.location);
			fos.write(serializedStimulant.getBytes());
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String serialized() {
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			oos.flush();
		} catch (IOException e) {}
		return baos.toString();
	}
	
	public static Stimulant deserialize(String _serializedObject) {
		Stimulant s = null;
		try {
			byte[] b = _serializedObject.getBytes();
			ByteArrayInputStream bais = new ByteArrayInputStream(b);
			ObjectInputStream ois = new ObjectInputStream(bais);
			s = (Stimulant) ois.readObject();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		
		return s;
	}
	
	/**
	 * Reads a file with a serialized {@link Stimulant} object in it
	 * and parses it into an instance of {@link Stimulant}
	 * @param _inputFile
	 * @return
	 */
	public static Stimulant deserialize(File _inputFile) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(_inputFile));
			Stimulant s = (Stimulant) ois.readObject();
			return s;
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
