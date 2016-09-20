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
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

import com.ianmann.mind.core.Constants;
import com.ianmann.mind.emotions.EmotionUnit;

/**
 * Root class for all thoughts. Every thought object
 * will inherit {@code AbstractThought}.
 * @author kirkp1ia
 *
 */
public abstract class AbstractThought {

	/**
	 * References to any thought that is linked to this thought.
	 * The AI will use this list to link through the thoughts.
	 * The file it points to contains one {@code ThoughtLink} object.
	 */
	private ArrayList<File> associatedThoughts;
	
	/**
	 * File in which this object is stored.
	 */
	public File location;
	
	/**
	 * EmotionUnit that is associated with this thought
	 */
	private EmotionUnit associatedEmotion;
	
	/**
	 * Create Abstract thought with a thought linked to it.
	 * @param _linkedThought
	 * @param _associated
	 */
	public AbstractThought(AbstractThought _linkedThought, EmotionUnit _associated) {
		this.associatedThoughts = new ArrayList<File>();
		this.addThoughtPathway(_linkedThought);
		
		this.associatedEmotion = _associated;
		
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
	 * Retrieve the location to the file containing this Abstract Thought
	 */
	public String getFileLocation() {
		if (this.location == null) {
			Scanner s;
			try {
				s = new Scanner(new File(Constants.THOUGHT_ROOT + "ids"));
				int next = s.nextInt();
				s.close();
				PrintWriter p = new PrintWriter(new File(Constants.THOUGHT_ROOT + "ids"));
				p.print(next+1);
				p.close();
				return Constants.THOUGHT_ROOT + String.valueOf(next) + ".tlink";
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
	public void addThoughtPathway(AbstractThought _thought) {
		ThoughtLink t = new ThoughtLink(_thought.location);
		this.associatedThoughts.add(t.location);
	}
	
	/**
	 * Print this object to the file at {@link AbstractThought.location}
	 */
	private void save() {
		String serializedThought = this.serialized();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(this.location);
			fos.write(serializedThought.getBytes());
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
	
	public static AbstractThought deserialize(String _serializedObject) {
		AbstractThought t = null;
		try {
			byte[] b = _serializedObject.getBytes();
			ByteArrayInputStream bais = new ByteArrayInputStream(b);
			ObjectInputStream ois = new ObjectInputStream(bais);
			t = (AbstractThought) ois.readObject();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		
		return t;
	}
	
	/**
	 * Reads a file with a serialized {@link AbstractThought} object in it
	 * and parses it into an instance of {@link AbstractThought}
	 * @param _inputFile
	 * @return
	 */
	public static AbstractThought deserialize(File _inputFile) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(_inputFile));
			AbstractThought t = (AbstractThought) ois.readObject();
			return t;
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
