package com.ianmann.mind.storage.organization.basicNetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.core.Constants;
import com.ianmann.mind.core.navigation.Category;
import com.ianmann.mind.emotions.EmotionUnit;

public class EntityStructure extends Neuron {

	/**
	 * Any attribute that this entity posesses.
	 * <br><br>
	 * Examples may be eyes, color, age, name, etc...
	 * <br><br>
	 * Each file contains a thought link to a neuron
	 */
	private ArrayList<File> attributes;
	
	/**
	 * Available actions that can be done by this entity.
	 * <br><br>
	 * Examples may be walk, eat, move, contain
	 * <br><br>
	 * Each file contains a thought link to an action neuron
	 */
	private ArrayList<File> actions;
	
	/**
	 * Think of this as a parent class. A structure may extend
	 * several structures and inherit attributes from those
	 * structures.
	 * <br><br>
	 * Each file contains an thought link to an entity structure
	 */
	private ArrayList<File> parentDefs;
	
	public EntityStructure() {
		super();
	}
}
