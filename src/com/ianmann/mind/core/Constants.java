package com.ianmann.mind.core;

import java.io.File;
import java.io.FileNotFoundException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.ianmann.utils.utilities.Utilities;

public abstract class Constants {

	public static String STORAGE_ROOT;
	public static String STIMULANT_ROOT;
	public static String LINK_ROOT;
	public static String THOUGHT_ROOT;
	
	private static File constantsFile = new File("constants.json");
	
	public static void readStorageVariables() throws FileNotFoundException, ParseException {
		JSONObject jsonConstants = Utilities.json(Constants.constantsFile);
		Constants.STORAGE_ROOT = (String) ((JSONObject) jsonConstants.get("STORAGE")).get("ROOT");
		Constants.STIMULANT_ROOT = Constants.STORAGE_ROOT + (String) ((JSONObject) jsonConstants.get("STORAGE")).get("STIMULANTS");
		Constants.LINK_ROOT = Constants.STORAGE_ROOT + (String) ((JSONObject) jsonConstants.get("STORAGE")).get("THOUGHT_LINKS");
		Constants.THOUGHT_ROOT = Constants.STORAGE_ROOT + (String) ((JSONObject) jsonConstants.get("STORAGE")).get("THOUGHTS");
		
	}
	
}
