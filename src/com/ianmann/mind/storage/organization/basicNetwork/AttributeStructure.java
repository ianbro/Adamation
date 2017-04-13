package com.ianmann.mind.storage.organization.basicNetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.NeuralPathway;
import com.ianmann.mind.Neuron;
import com.ianmann.mind.core.navigation.Category;
import com.ianmann.mind.emotions.EmotionUnit;
import com.ianmann.mind.storage.organization.NeuronType;

public class AttributeStructure extends NeuralNetwork {
	
	private ArrayList<File> possibilities = new ArrayList<File>();

	public AttributeStructure(Neuron _root) {
		super(_root);
		this.sortNetwork();
	}
	
	/**
	 * Creates and saves a neuron for an attribute structure neural network and return that neuron
	 * as a parsed AttributeStructure.
	 * @param _parent
	 * @param _category
	 * @return
	 */
	public static AttributeStructure create(AttributeStructure _parent, Category _category) {
		Neuron neuron = new Neuron(NeuronType.ATTRIBUTE, EmotionUnit.NEUTRAL, _category);
		neuron.save();
		if (_parent != null) {
			neuron.setParentNeuron(_parent.root);
		}
		return (AttributeStructure) neuron.parsed();
	}
	
	/**
	 * Creates and saves a neuron for an attribute structure neural network and return that neuron
	 * as a parsed AttributeStructure. This Structure will have the label of _label.
	 * @param _parent
	 * @param _category
	 * @return
	 */
	public static AttributeStructure create(AttributeStructure _parent, Category _category, String _label) {
		Neuron neuron = new Neuron(NeuronType.ATTRIBUTE, EmotionUnit.NEUTRAL, _label, _category);
		neuron.save();
		if (_parent != null) {
			neuron.setParentNeuron(_parent.root);
		}
		return (AttributeStructure) neuron.parsed();
	}

	/**
	 * @Override
	 * All neurons that are network roots of Description are added to this Attribute
	 * Structures list of possibilities.
	 */
	public void sortNetwork() {
		// TODO Auto-generated method stub
		for (File synapseFile : this.root.getSynapticEndings()) {
			Neuron current = NeuralPathway.deserialize(synapseFile).fireSynapse();
			if (current.getType() == NeuronType.DESCRIPTION) {
				this.possibilities.add(synapseFile);
			}
		}
	}
	
	/**
	 * Returns whether or not the Description network with root neuron of _descriptionNeuron
	 * can be a value for this attribute.
	 * @param _descriptionNeuron
	 * @return
	 */
	public boolean hasPossibility(Neuron _descriptionNeuron) {
		if (_descriptionNeuron.getType() == NeuronType.DESCRIPTION) {
			for (int i = 0; i < this.possibilities.size(); i++) {
				if (this.getPossibility(i).fireSynapse().location.equals(_descriptionNeuron.location)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Return the NeuralPathway that contains the possibility at index in this
	 * AttributeStructure objects list of possibilities.
	 * @param index
	 * @return
	 */
	public NeuralPathway getPossibility(int index) {
		return NeuralPathway.deserialize(this.possibilities.get(index));
	}
	
	/**
	 * Add _n as a Description neuron object to the possibilities for this
	 * Attribute Structure.
	 * @param _n
	 */
	public void addPossibility(Neuron _n) {
		if (_n.getType() == NeuronType.DESCRIPTION) {
			NeuralPathway synapse = this.root.addNeuralPathway(_n);
			this.possibilities.add(synapse.location);
		}
	}
	
	/**
	 * Add _n as a file containing a Description neuron object to the
	 * possibilities for this Attribute Structure.
	 * @param _n
	 */
	public void addPossibility(File _neuronFile) {
		try {
			Neuron neuron = Neuron.fromJSON(_neuronFile);
			this.addPossibility(neuron);
		} catch (FileNotFoundException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
