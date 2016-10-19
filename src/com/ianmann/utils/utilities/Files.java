package com.ianmann.utils.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
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
	
	public static byte[] readFile(File _inputFile) throws IOException {
		byte[] fileBytes = new byte[(int) _inputFile.length()];
		FileInputStream fis = new FileInputStream(_inputFile);
		fis.read(fileBytes);
		fis.close();
		
		for (int i = 0; i < fileBytes.length; i++) {
			fileBytes[i] = (byte) (fileBytes[i] & 0xff);
		}
		
		return fileBytes;
	}
}
