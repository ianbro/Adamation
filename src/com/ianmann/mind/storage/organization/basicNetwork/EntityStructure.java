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
import com.ianmann.utils.utilities.GeneralUtils;

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
	
	public EntityInstance instantiateStructure(Category _category) {
		return EntityInstance.create(this, _category);
	}
	
	public EntityInstance instantiateStructure(Category _category, String _label) {
		return EntityInstance.create(this, _category, _label);
	}

	/**
	 * @Override
	 * Noun definition neurons and Attributes will be put into this networks list of
	 * attributes.
	 */
	protected void sortNetwork() {
		this.attributes = new ArrayList<File>();
		
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
	 * Returns an EntityStructure representing the parent network of this network. This uses
	 * this networks root Neuron and retrieves the network corresponding to that Neuron's
	 * parent Neuron.
	 * @return
	 */
	public EntityStructure getParentNetwork() {
		if (this.root.getParentNeuron() != null) {
			return new EntityStructure(this.root.getParentNeuron());
		} else {
			return null;
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
		
		EntityStructure parentNetwork = this.getParentNetwork();
		if (parentNetwork != null) {
			for (Neuron neuron : parentNetwork.getAttributes()) {
				neurons.add(neuron);
			}
		}
		
		for (File pathwayFile : this.attributes) {
			neurons.add(NeuralPathway.deserialize(pathwayFile).fireSynapse());
		}
		
		return neurons;
	}
	
	/**
	 * Return the related neuron as a parsed neuron object at index in this networks
	 * list of attributes.
	 * @param index
	 * @return
	 */
	public Neuron getAttribute(int index) {
		try {
			return Neuron.fromJSON(this.attributes.get(index));
		} catch (FileNotFoundException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Adds the neuron _n to the network of attributes that this network is consisted of.
	 * @param _n
	 */
	public void addAttribute(Neuron _n) {
		if (_n.getType() == NeuronType.NOUN_DEFINITION || _n.getType() == NeuronType.ATTRIBUTE) {
			NeuralPathway synapse = this.root.addNeuralPathway(_n);
			this.sortNetwork();
		}
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
	
	/**
	 * Determines whether or not this entity instance can be assigned an instance of _attribute.
	 * It is assumed to be true if _attribute is contained in this EntityStructures list of attributes.
	 * @param _attribute
	 * @return
	 */
	public boolean hasAttribute(EntityStructure _attribute) {
		for (Neuron neuron : this.getAttributes()) {
			if (neuron.location.equals(_attribute.root.location)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Determines whether or not this entity instance can be assigned an instance of _attribute.
	 * It is assumed to be true if _attribute is contained in this EntityStructures list of attributes.
	 * @param _attribute
	 * @return
	 */
	public boolean hasAttribute(AttributeStructure _attribute) {
		for (Neuron neuron : this.getAttributes()) {
			if (neuron.location.equals(_attribute.root.location)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Determines whether or not this entity instance can be assigned an instance of _attribute.
	 * It is assumed to be true if _attribute is contained in this EntityStructures list of attributes.
	 * @param _attribute
	 * @return
	 */
	public boolean hasAttribute(Neuron _attribute) {
		for (Neuron neuron : this.getAttributes()) {
			if (neuron.location.equals(_attribute.location)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Determines whether this neuron is the same as that in _entityStructure. This is true if
	 * _entityStructure.root.location is the same file as this.root.location.
	 * @param o
	 * @return
	 */
	public boolean equals(EntityStructure _entityStructure) {
		return this.asNeuron().equals(_entityStructure.asNeuron());
	}
	
	public String toString() {
		String str = "<EntityStructure: type(" + NeuronType.mapType(this.root.getType()) + ")";
		if (!GeneralUtils.isNumeric(this.root.getAssociatedMorpheme())) {
			str = str + ";label(" + this.root.getAssociatedMorpheme() + ")";
		}
		str = str + ">";
		return str;
	}
}
