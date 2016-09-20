package com.ianmann.mind;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * The basis for deciding what the AI will think and
 * what to put in the short term memory.
 * @author kirkp1ia
 *
 */
public class Emotion implements Comparable<Emotion>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Emotion LIVID = new Emotion("Livid", 1);
	public static Emotion MAD = new Emotion("Mad", 2);
	public static Emotion DEPRESSED = new Emotion("Depressed", 1);
	public static Emotion SAD = new Emotion("Sad", 2);
	public static Emotion DISAPOINTED = new Emotion("Disapointed", 3);
	public static Emotion NEUTRAL = new Emotion("Neutral", 4);
	public static Emotion HOPEFUL = new Emotion("Hopeful", 5);
	public static Emotion CONTENT = new Emotion("Content", 6);
	public static Emotion GLAD = new Emotion("Glad", 7);
	public static Emotion FUNNY = new Emotion("Funny", 7);
	public static Emotion ECSTATIC = new Emotion("Ecstatic", 8);

	/**
	 * Name given to emotion e.g.
	 * Sad, Happy, Funny
	 */
	private String name;
	
	/**
	 * Level of desire the AI has
	 * to experience this emotion.
	 */
	private int desire;
	
	private Emotion(String _name, int _desire) {
		this.name = _name;
		this.desire = _desire;
	}
	
	public int compareTo(Emotion o) {
		return this.desire - o.desire;
	}
	
	public String toString() {
		return this.name;
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
	
	public static Emotion deserialize(String _serializedObject) {
		Emotion em = null;
		try {
			byte[] b = _serializedObject.getBytes();
			ByteArrayInputStream bais = new ByteArrayInputStream(b);
			ObjectInputStream ois = new ObjectInputStream(bais);
			em = (Emotion) ois.readObject();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		
		return em;
	}

}
