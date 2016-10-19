package com.ianmann.mind.input;

import java.io.File;
import java.io.FileNotFoundException;

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
}
