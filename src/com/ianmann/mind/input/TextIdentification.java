package com.ianmann.mind.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.core.Constants;
import com.ianmann.mind.storage.assimilation.MorphemeNotFound;
import com.ianmann.utils.utilities.Files;

public abstract class TextIdentification {

	private TextIdentification() { /* Don't instantiate this class */ }
	
	private static JSONObject data = new JSONObject();
	
	/**
	 * Initialize variables in this class
	 */
	public static void initialize() {
		load();
	}
	
	/**
	 * Load stored morphemes into data.
	 */
	private static void load() {
		String pathToMorphemesFile = Constants.NEURON_ROOT + "morphemes.json";
		
		try {
			data = (JSONObject) Files.json(new File(pathToMorphemesFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Return the neuron representation of a morpheme.
	 * @param _morpheme
	 * @return
	 * @throws MorphemeNotFound
	 */
	public static Neuron getNeuronForMorpheme(String _morpheme) throws MorphemeNotFound {
		JSONObject currentKey = data;
		for (String character : _morpheme.split("")) {
			currentKey = (JSONObject) currentKey.get(character.toUpperCase());
			if (currentKey == null) {
				throw new MorphemeNotFound(_morpheme);
			}
		}
		
		if (currentKey.containsKey("stored")) {
			String neuronPath = Constants.NEURON_ROOT + (String) currentKey.get("stored");
			File neuronFile = new File(neuronPath);
			Neuron n = Neuron.deserialize(neuronFile);
			return n;
		} else {
			throw new MorphemeNotFound(_morpheme);
		}
	}
	
	/**
	 * Return the string representation of a morpheme.
	 * @param n
	 * @return
	 * @throws MorphemeNotFound
	 */
	public static String getMorphemeForNeuron(Neuron n) throws MorphemeNotFound {
		if (n.getAssociatedMorphemes().length > 0) {
			/*
			 * TODO:
			 * Eventually, I want to have something that decides which morpheme out of the list
			 * to choose based on context.
			 */
			return n.getAssociatedMorphemes()[0];
		} else {
			return null;
		}
	}
	
	/**
	 * returns whether or not a morpheme has been encountered.
	 * @param _morpheme
	 * @return
	 */
	public static boolean morphemeStored(String _morpheme) {
		JSONObject currentKey = data;
		for (String character : _morpheme.split("")) {
			currentKey = (JSONObject) currentKey.get(character.toUpperCase());
			if (currentKey == null) {
				return false;
			}
		}
		
		if (currentKey.containsKey("stored")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Split a given word up into it's morphemes according to the
	 * data in morphemes.json. This doesn't find the meanings of those
	 * morphemes, just the morphemes themselves.
	 * @param _word
	 * @return
	 */
	public static String[] splitMorphemes(String _word) {
		// Use morphemes.json to split _word into morphemes
		ArrayList<String> morphemes = new ArrayList<String>();
		String modifiedWord = _word;	// Want to keep the original just in case. This is a temporary.
		String nonMorpheme = "";	// Stored morpheme that is not stored yet in memory
		
		while (modifiedWord.length() > 0) {
			/*
			 * Begin next morpheme
			 */
			
			int indexInWord = 0;	// Used to mark the end of a morpheme. modifiedWord will have up to this index truncated.
			String currentMorpheme = "";	// Morpheme to add to the list of morphemes when done finding it.
			JSONObject currentKey = data;	// json object of the search to see if the morpheme exists in memory
			
			for (String character : modifiedWord.split("")) {
				/*
				 * Loop through the current version of modified word and find the end of the next morpheme.
				 * Assume that the beginning of the morpheme is the beginning of modifiedWord. When we find
				 * the end, break out of the loop.
				 */
				if (currentKey != null) {
					currentKey = (JSONObject) currentKey.get(character.toUpperCase());
				}
				
				if (currentKey == null) {
					break;
				} else {
					indexInWord ++;
					currentMorpheme = currentMorpheme + character;
				}
			}
			
			/*
			 * We found the end of the currentMorpheme. If the morpheme is not stored
			 * in memory, assume it's not a morpheme so we add it to nonMorpheme. if
			 * the morpheme is in memory, dump nonMorpheme into the list of morphemes
			 * first as a nonsense word and then add current morpheme to the list.
			 */
			if (currentMorpheme.equals("")) {
				// morpheme not found
			} else {
				if (morphemeStored(currentMorpheme)) {
					if (!nonMorpheme.equals("")) {
						morphemes.add(nonMorpheme);
						nonMorpheme = "";
					}
					morphemes.add(currentMorpheme);
				} else {
					nonMorpheme = nonMorpheme + currentMorpheme;
				}
				modifiedWord = modifiedWord.substring(indexInWord);
			}
		}
		
		/*
		 * Return the list of morphemes as an array. The array represents
		 * an entire word with one or more morphemes.
		 * 
		 * Make sure that any nonMorpheme value that is left over
		 * is dumped into the array.
		 */
		if (!nonMorpheme.isEmpty()) {
			morphemes.add(nonMorpheme);
		}
		String[] morphemeArray = new String[morphemes.size()];
		morphemes.toArray(morphemeArray);
		return morphemeArray;
	}
}
