package com.ianmann.mind.core;

import java.io.File;
import java.io.FileNotFoundException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.ianmann.utils.utilities.Files;

public abstract class Constants {

	public static String STORAGE_ROOT;
	public static String STIMULANT_ROOT;
	public static String PATHWAY_ROOT;
	public static String NEURON_ROOT;
	
	/**
	 * Maximum number of memory elements that should be in
	 * memory at a time.
	 */
	public static int SHORT_TERM_CAPACITY;
	
	private static File constantsFile = new File("constants.json");
	
	public static void readStorageVariables() throws FileNotFoundException, ParseException {
		JSONObject jsonConstants = (JSONObject) Files.json(Constants.constantsFile);
		Constants.STORAGE_ROOT = (String) ((JSONObject) jsonConstants.get("STORAGE")).get("ROOT");
		Constants.STIMULANT_ROOT = Constants.STORAGE_ROOT + (String) ((JSONObject) jsonConstants.get("STORAGE")).get("STIMULANTS");
		Constants.PATHWAY_ROOT = Constants.STORAGE_ROOT + (String) ((JSONObject) jsonConstants.get("STORAGE")).get("NEURAL_PATHWAYS");
		Constants.NEURON_ROOT = Constants.STORAGE_ROOT + (String) ((JSONObject) jsonConstants.get("STORAGE")).get("NEURONS");
		Constants.SHORT_TERM_CAPACITY = (int) jsonConstants.get("SHORT_TERM_CAPACITY");
		
	}
	
}
