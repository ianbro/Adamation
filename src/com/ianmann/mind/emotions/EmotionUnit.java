package com.ianmann.mind.emotions;

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
public class EmotionUnit implements Comparable<EmotionUnit>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static EmotionUnit LIVID = new EmotionUnit("Livid", 1);
	public static EmotionUnit MAD = new EmotionUnit("Mad", 2);
	public static EmotionUnit DEPRESSED = new EmotionUnit("Depressed", 1);
	public static EmotionUnit SAD = new EmotionUnit("Sad", 2);
	public static EmotionUnit DISAPOINTED = new EmotionUnit("Disapointed", 3);
	public static EmotionUnit NEUTRAL = new EmotionUnit("Neutral", 4);
	public static EmotionUnit HOPEFUL = new EmotionUnit("Hopeful", 5);
	public static EmotionUnit CONTENT = new EmotionUnit("Content", 6);
	public static EmotionUnit GLAD = new EmotionUnit("Glad", 7);
	public static EmotionUnit FUNNY = new EmotionUnit("Funny", 7);
	public static EmotionUnit ECSTATIC = new EmotionUnit("Ecstatic", 8);

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
	
	private EmotionUnit(String _name, int _desire) {
		this.name = _name;
		this.desire = _desire;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int compareTo(EmotionUnit o) {
		return this.desire - o.desire;
	}
	
	public String toString() {
		return this.name;
	}
	
	public static EmotionUnit getEmotion(String _emotion) {
		switch(_emotion) {
		case "Livid":
			return LIVID;
		case "Mad":
			return MAD;
		case "Depressed":
			return DEPRESSED;
		case "Sad":
			return SAD;
		case "Disapointed":
			return DISAPOINTED;
		case "Neutral":
			return NEUTRAL;
		case "Hopeful":
			return HOPEFUL;
		case "Content":
			return CONTENT;
		case "Glad":
			return GLAD;
		case "Funny":
			return FUNNY;
		case "Ecstatic":
			return ECSTATIC;
		default:
			return null;
		}
	}

}
