package com.ianmann.mind.storage.organization.basicNetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.core.Constants;
import com.ianmann.mind.core.navigation.Category;
import com.ianmann.mind.emotions.EmotionUnit;

public class MemoryStructure {

	/**
	 * TODO:
	 * Need to work on getting this to include inheritence for entities.
	 * To do this, we will loop through the neuron _extends's attributes and
	 * add each attribute to this neurons attributes. We will finish the loop
	 * on _extends when we reach a neuron that is not an attribute anymore.
	 * This means that we need a method to see if a neuron is an attribute.
	 * 
	 * Assemble the structure of an entity definition.
	 * @param root - Neuron that represents the basis and container for this definition
	 * @param attributes - list of attributes such as name, age and so on. These are
	 * not the specific attribute such as "Ian" or 20, but the neuron that represents
	 * the idea of name and the idea of age as attributes in general. Think of these as
	 * a list of fields types.
	 * @param exampleMemories - Neurons that represent any known encounter with
	 * this entity. These will be used for things like envisioning the noun.
	 * @param abilities - Neurons that represent actions that can be done by this
	 * entity such as walk, eat, or talk. Inanimate objects won't have many if any
	 * neurons in this list.
	 * @return
	 */
	public Neuron assembleAsEntityStructure(String _label, Category _category,
										EmotionUnit _associated,
										Neuron _linkedThought,
										Neuron _extends,
										ArrayList<File> attributes,
										ArrayList<File> exampleMemories,
										ArrayList<File> abilities) {
		
		Neuron root = new Neuron(null, _associated, _label, _category);
		
		for (File file : attributes) {
			try {
				root.addNeuralPathway(Neuron.parse(file));
			} catch (FileNotFoundException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (File file : exampleMemories) {
			try {
				root.addNeuralPathway(Neuron.parse(file));
			} catch (FileNotFoundException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (File file : abilities) {
			try {
				root.addNeuralPathway(Neuron.parse(file));
			} catch (FileNotFoundException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		root.addNeuralPathway(_linkedThought);
		
		return root;
	}
}
