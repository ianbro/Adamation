package com.ianmann.utils.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class Files {

	public static Object json(File _jsonFile) throws FileNotFoundException, ParseException {
		Scanner s = new Scanner(_jsonFile).useDelimiter("\\Z");
		String rawJson = s.next();
		s.close();
		
		JSONParser jp = new JSONParser();
		Object json = jp.parse(rawJson);
		return json;
	}
}
