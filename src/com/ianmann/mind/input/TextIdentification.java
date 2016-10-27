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
			Neuron n;
			try {
				n = Neuron.parse(neuronFile);
				return n;
			} catch (FileNotFoundException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new MorphemeNotFound(_morpheme);
			}
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
		return n.getAssociatedMorpheme();
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
}
