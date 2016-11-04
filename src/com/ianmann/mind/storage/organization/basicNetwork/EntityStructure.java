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
	
	public ArrayList<Neuron> getAttributes() {
		ArrayList<Neuron> neurons = new ArrayList<Neuron>();
		
		for (File nFile : this.attributes) {
			try {
				neurons.add(Neuron.parse(nFile));
			} catch (FileNotFoundException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return neurons;
	}
	
	public void addAttribute(Neuron _n) {
		this.attributes.add(_n.location);
	}
	
	public void addAttribute(File _n) {
		this.attributes.add(_n);
	}
	
	public ArrayList<ActionStructure> getActions() {
		ArrayList<ActionStructure> neurons = new ArrayList<ActionStructure>();
		
		for (File nFIle : this.actions) {
			try {
				neurons.add((ActionStructure) Neuron.parse(nFIle));
			} catch (FileNotFoundException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return neurons;
	}
	
	public void addAction(ActionStructure _n) {
		this.actions.add(_n.location);
	}
	
	public void addAction(File _n) {
		this.actions.add(_n);
	}
}
