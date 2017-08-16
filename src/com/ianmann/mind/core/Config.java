package com.ianmann.mind.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.ianmann.mind.Neuron;
import com.ianmann.utils.utilities.Files;

public abstract class Config {

	public static String STORAGE_ROOT;
	public static String STIMULANT_ROOT;
	public static String PATHWAY_ROOT;
	public static String NEURON_ROOT;
	public static String CORE_ROOT;
	public static String PATH_TO_LANGUAGE_FOLDER;
	
	/**
	 * Maximum number of memory elements that should be in
	 * memory at a time.
	 */
	public static int SHORT_TERM_CAPACITY;
	
	/**
	 * Map of locations in short term memory and what references them
	 */
	public static HashMap<String, Integer> SHORT_TERM_MEM_LOCATIONS;
	
	private static File constantsFile = new File("constants.json");
	
	/**
	 * Load the global variables for this project
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public static void readStorageVariables() throws FileNotFoundException, ParseException {
		JSONObject jsonConstants = (JSONObject) Files.json(Config.constantsFile);
		Config.STORAGE_ROOT = (String) ((JSONObject) jsonConstants.get("STORAGE")).get("ROOT");
		Config.CORE_ROOT = Config.STORAGE_ROOT + "core/";
		Config.STIMULANT_ROOT = Config.STORAGE_ROOT + (String) ((JSONObject) jsonConstants.get("STORAGE")).get("STIMULANTS");
		Config.PATHWAY_ROOT = Config.STORAGE_ROOT + (String) ((JSONObject) jsonConstants.get("STORAGE")).get("NEURAL_PATHWAYS");
		Config.NEURON_ROOT = Config.STORAGE_ROOT + (String) ((JSONObject) jsonConstants.get("STORAGE")).get("NEURONS");
		Config.SHORT_TERM_CAPACITY = (Long.valueOf((long) jsonConstants.get("SHORT_TERM_CAPACITY"))).intValue();
		Config.SHORT_TERM_MEM_LOCATIONS = (HashMap<String, Integer>) ((JSONObject) jsonConstants.get("MEMORY_LOCATIONS")).get("INPUT_ADDRESSES");
		Config.PATH_TO_LANGUAGE_FOLDER = Config.CORE_ROOT + "language/";
	}
	
	public static String getLanguageFolderSpecific(Neuron _languageNeuron) {
		return PATH_TO_LANGUAGE_FOLDER + _languageNeuron.getAssociatedMorpheme() + "/";
	}
	
}
