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
import java.nio.file.Files;
import java.util.Scanner;

import com.ianmann.mind.core.Constants;

public class ThoughtLink implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This will be returned when the AI processor accesses this
	 * thought link. The file that this points to contains an
	 * {@code AbstractThought} object.
	 */
	private File resultingThought;
	
	/**
	 * File in which this object is stored.
	 */
	public File location;
	
	public ThoughtLink(File _resultThoughtFile) {
		this.resultingThought = _resultThoughtFile;
		
		this.location = new File(this.getFileLocation());
		
		try {
			Files.createFile(this.location.toPath());
			this.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieve the location to the file containing this Thought Link
	 */
	public String getFileLocation() {
		if (this.location == null) {
			Scanner s;
			try {
				s = new Scanner(new File(Constants.LINK_ROOT + "ids"));
				int next = s.nextInt();
				s.close();
				PrintWriter p = new PrintWriter(new File(Constants.LINK_ROOT + "ids"));
				p.print(next+1);
				p.close();
				return Constants.LINK_ROOT + String.valueOf(next) + ".tlink";
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
	 * Retrieve Thought from file pointed to by this link.
	 * @return
	 */
	private AbstractThought getThoughtFromFile() {
		return AbstractThought.deserialize(this.resultingThought);
	}
	
	/**
	 * Activate this link and retrieve the thought
	 * that is linked by this.
	 * @return
	 */
	public AbstractThought activate() {
		return this.getThoughtFromFile();
	}
	
	/**
	 * Print this object to the file at {@link ThoughtLink.location}
	 */
	private void save() {
		String serializedLink = this.serialized();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(this.location);
			fos.write(serializedLink.getBytes());
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
	
	public static ThoughtLink deserialize(String _serializedObject) {
		ThoughtLink l = null;
		try {
			byte[] b = _serializedObject.getBytes();
			ByteArrayInputStream bais = new ByteArrayInputStream(b);
			ObjectInputStream ois = new ObjectInputStream(bais);
			l = (ThoughtLink) ois.readObject();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		
		return l;
	}
	
	/**
	 * Reads a file with a serialized {@link ThoughtLink} object in it
	 * and parses it into an instance of {@link ThoughtLink}
	 * @param _inputFile
	 * @return
	 */
	public static ThoughtLink deserialize(File _inputFile) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(_inputFile));
			ThoughtLink l = (ThoughtLink) ois.readObject();
			return l;
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
