package com.ianmann.mind.test;

import java.io.FileNotFoundException;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.Emotion;
import com.ianmann.mind.Stimulant;
import com.ianmann.mind.core.Constants;

public abstract class TestThoughtRelationships {

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		// TODO Auto-generated method stub
		Constants.readStorageVariables();

		System.out.println(Emotion.CONTENT.serialized().getBytes().length * 1000000);
		Stimulant s = new Stimulant("Communication", null);
	}

}
