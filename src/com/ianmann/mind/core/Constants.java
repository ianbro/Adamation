package com.ianmann.mind.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.core.navigation.Category;
import com.ianmann.utils.utilities.Files;

public abstract class Constants {

	public static String STORAGE_ROOT;
	public static String STIMULANT_ROOT;
	public static String PATHWAY_ROOT;
	public static String NEURON_ROOT;
	public static String CORE_ROOT;
	public static String PATH_TO_CATEGORIES_FOLDER;
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
	public static void readStorageVariables() throws FileNotFoundException, ParseException {
		JSONObject jsonConstants = (JSONObject) Files.json(Constants.constantsFile);
		Constants.STORAGE_ROOT = (String) ((JSONObject) jsonConstants.get("STORAGE")).get("ROOT");
		Constants.CORE_ROOT = Constants.STORAGE_ROOT + "core/";
		Constants.STIMULANT_ROOT = Constants.STORAGE_ROOT + (String) ((JSONObject) jsonConstants.get("STORAGE")).get("STIMULANTS");
		Constants.PATHWAY_ROOT = Constants.STORAGE_ROOT + (String) ((JSONObject) jsonConstants.get("STORAGE")).get("NEURAL_PATHWAYS");
		Constants.NEURON_ROOT = Constants.STORAGE_ROOT + (String) ((JSONObject) jsonConstants.get("STORAGE")).get("NEURONS");
		Constants.SHORT_TERM_CAPACITY = (Long.valueOf((long) jsonConstants.get("SHORT_TERM_CAPACITY"))).intValue();
		Constants.SHORT_TERM_MEM_LOCATIONS = (HashMap<String, Integer>) ((JSONObject) jsonConstants.get("MEMORY_LOCATIONS")).get("INPUT_ADDRESSES");
		Constants.PATH_TO_CATEGORIES_FOLDER = Constants.CORE_ROOT + "categories/";
		Constants.PATH_TO_LANGUAGE_FOLDER = Constants.CORE_ROOT + "language/";
	}
	
	public static void setConstantCategories() throws FileNotFoundException, ParseException {
		Category.LANGUAGE = (Category) Category.parse(new File(Constants.PATH_TO_CATEGORIES_FOLDER + "language.ctgry"));
		Category.PATTERN = (Category) Category.parse(new File(Constants.PATH_TO_CATEGORIES_FOLDER + "pattern.ctgry"));
	}
	
	public static String getLanguageFolderSpecific(Neuron _languageNeuron) {
		return PATH_TO_LANGUAGE_FOLDER + _languageNeuron.getAssociatedMorpheme() + "/";
	}
	
}
