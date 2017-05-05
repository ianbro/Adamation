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

public class EntityInstance extends NeuralNetwork {
	
	/**
	 * Entity Structure that defines how the class is organized.
	 * The file contains an EntityStructure object.
	 */
	private File structure;

	/**
	 * Any attribute that this entity possesses.
	 * <br><br>
	 * Examples may be eyes, color, age, name, etc...
	 * <br><br>
	 * Each file contains a thought link to a neuron
	 */
	private ArrayList<File> attributes;
	
	public EntityInstance(Neuron _root) {
		super(_root);
		this.sortNetwork();
	}
	
	public static EntityInstance create(EntityStructure _structure, Category _category) {
		Neuron toCreate = new Neuron(NeuronType.NOUN_INSTANCE, EmotionUnit.NEUTRAL, _category);
		toCreate.save();
		toCreate.addNeuralPathway(_structure.root);
		return new EntityInstance(toCreate);
	}
	
	public static EntityInstance create(EntityStructure _structure, Category _category, String _label) {
		Neuron toCreate = new Neuron(NeuronType.NOUN_INSTANCE, EmotionUnit.NEUTRAL, _label, _category);
		toCreate.save();
		toCreate.addNeuralPathway(_structure.root);
		return new EntityInstance(toCreate);
	}
	
	/**
	 * Return the parent structure of which this entity instance is an instance of.
	 * @return
	 */
	public EntityStructure getStructure() {
		return ((EntityStructure) NeuralPathway.deserialize(this.structure).fireSynapse().parsed());
	}

	/**
	 * @Override
	 * Sets the structure of which this entity instance is an instance of and sets the attributes that this
	 * entity instance is related to.
	 */
	protected void sortNetwork() {
		this.attributes = new ArrayList<File>();
		
		for (int i = 0; i < this.root.getSynapticEndings().size(); i ++) {
			NeuralPathway currentPathway = NeuralPathway.deserialize(this.root.getSynapticEndings().get(i));
			Neuron current = currentPathway.fireSynapse();
			if (current.getType() == NeuronType.NOUN_DEFINITION) {
				this.structure = currentPathway.location;
				break;
			}
		}
		
		for (int i = 0; i < this.root.getSynapticEndings().size(); i ++) {
			NeuralPathway currentPathway = NeuralPathway.deserialize(this.root.getSynapticEndings().get(i));
			Neuron current = currentPathway.fireSynapse();
			if (current.getType() == NeuronType.DESCRIPTION) {
				AttributeStructure currentAttribute = ((Description) current.parsed()).getStructure();
				if (this.getStructure().hasAttribute(currentAttribute)) {
					this.attributes.add(currentPathway.location);
				}
			}
		}
	}
	
	/**
	 * Sets _attribute to relate to this EntityInstances root Neuron. If _attribute's structure is not found an
	 * attribute of this instances structure (contained in the structure's network), then an {@link AttributeNotFoundException}
	 * is thrown.
	 * @param _attribute
	 * @throws AttributeNotFoundException
	 */
	public void addAttribute(EntityInstance _attribute) throws AttributeNotFoundException {
		if (!this.getStructure().hasAttribute(_attribute.getStructure())) {
			throw new AttributeNotFoundException(_attribute.getStructure().asNeuron());
		} else {
			this.asNeuron().addNeuralPathway(_attribute.asNeuron());
			this.sortNetwork();
		}
	}
	
	/**
	 * Sets _attribute to relate to this EntityInstances root Neuron. If _attribute's structure is not found an
	 * attribute of this instances structure (contained in the structure's network), then an {@link AttributeNotFoundException}
	 * is thrown.
	 * @param _attribute
	 * @throws AttributeNotFoundException
	 */
	public void addAttribute(Description _attribute) throws AttributeNotFoundException {
		if (!this.getStructure().hasAttribute(_attribute.getStructure())) {
			throw new AttributeNotFoundException(_attribute.getStructure().asNeuron());
		} else {
			this.asNeuron().addNeuralPathway(_attribute.asNeuron());
			this.sortNetwork();
		}
	}
}
