package com.ianmann.mind.test;

import java.io.File;
import java.io.FileNotFoundException;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.Stimulant;
import com.ianmann.mind.core.Constants;
import com.ianmann.mind.emotions.EmotionUnit;

public abstract class TestThoughtRelationships {

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		// TODO Auto-generated method stub
		Constants.readStorageVariables();

		Neuron n1 = new Neuron(null, EmotionUnit.CONTENT);
		Neuron n2 = new Neuron(n1, EmotionUnit.CONTENT);
		
//		Stimulant comStim = new Stimulant("Communication", n2);
		Stimulant comStim = Stimulant.deserialize(new File(Constants.STIMULANT_ROOT + "Communication.stim"));
		
		System.out.println(comStim.getReaction().fireSynapse());
	}

}
