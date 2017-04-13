package com.ianmann.mind.storage.organization.basicNetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.NeuralPathway;
import com.ianmann.mind.Neuron;
import com.ianmann.mind.core.Constants;
import com.ianmann.mind.core.navigation.Category;
import com.ianmann.mind.emotions.EmotionUnit;
import com.ianmann.mind.storage.organization.NeuronType;

public class EntityStructure extends NeuralNetwork {

	/**
	 * Any attribute that this entity possesses.
	 * <br><br>
	 * Examples may be eyes, color, age, name, etc...
	 * <br><br>
	 * Each file contains a thought link to a neuron
	 */
	private ArrayList<File> attributes = new ArrayList<File>();
	
	/**
	 * Instantiate an Entity Structure network. This contains multiple attributes and acts
	 * as a definition for a noun. Examples of this may be the structure definition for
	 * person, table, car, etc...
	 * 
	 * This neuron will be used to create noun instances based on the structure of this
	 * definition.
	 * @param _neuron
	 */
	public EntityStructure(Neuron _neuron) {
		super(_neuron);
		this.sortNetwork();
	}
	
	/**
	 * Creates and saves an Entity Structure network that is under the category _category and
	 * has the parent _parent. This network has no label to it.
	 * @param _parent
	 * @param _category
	 * @return
	 */
	public static EntityStructure create(EntityStructure _parent, Category _category) {
		Neuron toCreate = new Neuron(NeuronType.NOUN_DEFINITION, EmotionUnit.NEUTRAL, _category);
		toCreate.save();
		if (_parent != null) {
			toCreate.setParentNeuron(_parent.root);
		}
		return (EntityStructure) toCreate.parsed();
	}
	
	/**
	 * Creates and saves an Entity Structure network that is under the category _category,
	 * has the parent _parent and is associated with the morpheme _label.
	 * @param _parent
	 * @param _category
	 * @return
	 */
	public static EntityStructure create(EntityStructure _parent, Category _category, String _label) {
		Neuron toCreate = new Neuron(NeuronType.NOUN_DEFINITION, EmotionUnit.NEUTRAL, _label, _category);
		toCreate.save();
		if (_parent != null) {
			toCreate.setParentNeuron(_parent.root);
		}
		return (EntityStructure) toCreate.parsed();
	}

	/**
	 * @Override
	 * Noun definition neurons and Attributes will be put into this networks list of
	 * attributes.
	 */
	public void sortNetwork() {
		for (File relatedNeuron : this.root.getSynapticEndings()) {
			NeuralPathway currentPathway = NeuralPathway.deserialize(relatedNeuron);
			Neuron current = currentPathway.fireSynapse();
			if (current.getType() == NeuronType.NOUN_DEFINITION) {
				this.attributes.add(relatedNeuron);
			} else if (current.getType() == NeuronType.ATTRIBUTE) {
				this.attributes.add(relatedNeuron);
			}
		}
	}
	
	/**
	 * Determines if this network is a sub-structure of the structure _parent.
	 * A network is considered a substructure if all of the attributes in
	 * _parent's network are present in this neurons network.
	 * @param _parent
	 * @return
	 */
	public boolean isSubStructure(EntityStructure _parent) {
		return this.attributes.containsAll(_parent.attributes);
	}
	
	/**
	 * Returns all attributes in this neurons network as Neuron objects. Only use this if
	 * you will need to access the data in the neurons as this function reads each neuron
	 * from its file.
	 * @return
	 */
	public ArrayList<Neuron> getAttributes() {
		ArrayList<Neuron> neurons = new ArrayList<Neuron>();
		
		for (File nFile : this.attributes) {
			try {
				neurons.add(Neuron.fromJSON(nFile));
			} catch (FileNotFoundException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return neurons;
	}
	
	/**
	 * Adds the neuron _n to the network of attributes that this network is consisted of.
	 * @param _n
	 */
	public void addAttribute(Neuron _n) {
		this.attributes.add(_n.location);
		this.root.addNeuralPathway(_n);
	}
	
	/**
	 * Adds the neuron _n to the network of attributes that this network is consisted of.
	 * This method takes a file containing a neuron instead of a neuron object.
	 * @param _n
	 */
	public void addAttribute(File _n) {
		try {
			Neuron neuron = Neuron.fromJSON(_n);
			this.addAttribute(neuron);
		} catch (FileNotFoundException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
