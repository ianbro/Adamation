package com.ianmann.utils.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class JSONUtils {

	public static String formatJSON(Object jsonTemp, int level) {
		String jsonString = "";
		
		if (jsonTemp instanceof JSONObject) {
			JSONObject json = (JSONObject) jsonTemp;
			jsonString = jsonString + formatJSONObject(json, level + 1);
		} else if (jsonTemp instanceof JSONArray) {
			JSONArray json = (JSONArray) jsonTemp;
			jsonString = jsonString + formatJSONArray(json, level + 1);
		} else {
			jsonString = jsonString + formatJSONOther(jsonTemp, level);
		}
		
		return jsonString;
	}
	
	public static String tabs(int level) {
		String tabs = "";
		
		for (int i = 0; i < level; i++) {
			tabs = tabs + "\t";
		}
		
		return tabs;
	}
	
	public static String formatJSONObject(JSONObject json, int level) {
		String jsonString = "";
		
		jsonString = jsonString + "{\r\n";
		
		int i = json.keySet().size();
		for (Object key : json.keySet()) {
			Object valueTemp = json.get(key);
			Object value = formatJSON(valueTemp, level);
			
			jsonString = jsonString + tabs(level) + "\"" + key + "\"" + ": " + value;
			if (i > 1) {
				jsonString = jsonString + ",";
			}
			i --;
			
			jsonString = jsonString + "\r\n";
		}
		
		jsonString = jsonString + tabs(level - 1) + "}";
		
		return jsonString;
	}
	
	public static String formatJSONArray(JSONArray json, int level) {
		String jsonString = "";
		
		jsonString = jsonString + "[\r\n";
		
		for (int i = 0; i < json.size(); i++) {
			Object value = formatJSON(json.get(i), level);
			
			jsonString = jsonString + tabs(level) + value;
			if (i < json.size() - 1) {
				jsonString = jsonString + ",";
			}
			
			jsonString = jsonString + "\r\n";
		}

		jsonString = jsonString + tabs(level - 1) + "]";
		
		return jsonString;
	}
	
	public static String formatJSONOther(Object json, int level) {
		String jsonString = "";
		
		try {
			if (json.getClass().equals(String.class)) {
				jsonString = "\"" + json + "\"";
			} else {
				jsonString = json.toString();
			}
		} catch (NullPointerException e) {
		}
		
		return jsonString;
	}
	
	public static JSONObject json(String jsonString) throws ParseException{
		JSONObject jsonObject = null;
		JSONParser parser = new JSONParser();
		jsonObject = ((JSONObject) parser.parse(jsonString));
		return jsonObject;
	}
	
	public static JSONObject json(File jsonFile) throws FileNotFoundException, ParseException{
		Scanner jsonReader = new Scanner(jsonFile).useDelimiter("\\A");
		return json(jsonReader.next());
	}

}
