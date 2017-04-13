package com.ianmann.mind.storage.organization.basicNetwork;

import java.io.File;
import java.io.FileNotFoundException;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.NeuralPathway;
import com.ianmann.mind.Neuron;
import com.ianmann.mind.core.navigation.Category;
import com.ianmann.mind.emotions.EmotionUnit;
import com.ianmann.mind.storage.organization.NeuronType;

public class Description extends NeuralNetwork {
	
	private File attributeStructureNeuron;

	public Description(Neuron _root) {
		super(_root);
		this.sortNetwork();
	}
	
	public static Description create(AttributeStructure _definition, Category _category) {
		Neuron neuron = new Neuron(NeuronType.DESCRIPTION, EmotionUnit.NEUTRAL, _category);
		neuron.save();
		Description desc = (Description) neuron.parsed();
		desc.setStructure(_definition);
		return desc;
	}
	
	public static Description create(AttributeStructure _definition, Category _category, String _label) {
		Neuron neuron = new Neuron(NeuronType.DESCRIPTION, EmotionUnit.NEUTRAL, _label, _category);
		neuron.save();
		Description desc = (Description) neuron.parsed();
		desc.setStructure(_definition);
		return desc;
	}

	/**
	 * @Override
	 * Sets the attribute structure that this description fulfills.
	 */
	public void sortNetwork() {
		// TODO Auto-generated method stub
		for (int i = 0; i < this.root.getSynapticEndings().size(); i ++) {
			Neuron current = NeuralPathway.deserialize(this.root.getSynapticEndings().get(i)).fireSynapse();
			if (current.getType() == NeuronType.ATTRIBUTE) {
				if (((AttributeStructure) current.parsed()).hasPossibility(this.root)) {
					this.attributeStructureNeuron = current.location;
				}
			}
		}
	}
	
	/**
	 * Sets the attribute Structure that this description fulfills. If this description already has a structure,
	 * it is cleared and removed from this neuron's relationships.
	 * @param _structure
	 */
	public void setStructure(AttributeStructure _structure) {
		if (this.attributeStructureNeuron != null) {
			this.root.removeNeuralPathway(NeuralPathway.deserialize(this.attributeStructureNeuron).fireSynapse());
		}
		NeuralPathway newPathWay = this.root.addNeuralPathway(_structure.root);
		this.attributeStructureNeuron = newPathWay.location;
		_structure.addPossibility(this.root);
	}

}
