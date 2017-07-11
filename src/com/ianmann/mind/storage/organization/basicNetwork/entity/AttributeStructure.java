package com.ianmann.mind.storage.organization.basicNetwork.entity;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.NeuralPathway;
import com.ianmann.mind.Neuron;
import com.ianmann.mind.storage.organization.NeuronType;
import com.ianmann.mind.storage.organization.basicNetwork.ExtendableNeuralNetwork;
import com.ianmann.mind.storage.organization.basicNetwork.NeuralNetwork;

public class AttributeStructure extends ExtendableNeuralNetwork {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * <p>
	 * Provides the index in this {@link Neuron}'s axon at which the dendrite
	 * group containing this {@link AttributeStructure}'s possibilities is
	 * located.
	 * </p>
	 * <p>
	 * The value of this is 1.
	 * </p>
	 */
	private final static int PATH_TO_LIST_POSSIBILITIES_IN_ROOT = 1;
	
	/**
	 * <p>
	 * Loads an {@link AttributeStructure} into memory from a given file at
	 * _path.
	 * </p>
	 * <p>
	 * If _doLoadAttributes is true, then the actual data in the file will
	 * be read into memory. Otherwise, in order to save memory, This instance
	 * will simply have the path to the file loaded and no other data.
	 * </p>
	 * @param _path
	 * @param _doLoadAttribute
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public AttributeStructure(String _path, boolean _doLoadAttributes) throws FileNotFoundException, ParseException {
		super(_path, _doLoadAttributes);
	}

	/**
	 * <p>
	 * Wraps an already created {@link Neuron} in a parsed {@link AttributeStructure}
	 * {@link NeuralNetwork}. This will provide getters that retreive expected
	 * relationships from this {@link NeuralNetwork} and other {@link Neuron}s that
	 * for this {@link AttributeStructure}.
	 * </p>
	 * <p>
	 * This assumes that those relationships
	 * are in the expected places on the root {@link Neuron}'s axon.
	 * </p>
	 * @param _root
	 */
	public AttributeStructure(String _path, String _label, AttributeStructure _parent) {
		super(_path, NeuronType.ATTRIBUTE, _label, _parent);
	}
	
	/**
	 * <p>
	 * Returns the list of {@link NeuralPathway}s that link to the {@link Neuron}s
	 * that represent the possible {@link Description}s that can be used to fill this
	 * {@link AttributeStructure}.
	 * </p>
	 * @return
	 */
	public ArrayList<NeuralPathway> getPossibilities() {
		return this.getDendriteGroup(AttributeStructure.PATH_TO_LIST_POSSIBILITIES_IN_ROOT);
	}
	
	/**
	 * Creates and saves a neuron for an attribute structure neural network and return that neuron
	 * as a parsed AttributeStructure.
	 * @param _parent
	 * @param _category
	 * @return
	 */
	public static AttributeStructure create(AttributeStructure _parent) {
		String filePath = Neuron.getNewFileLocation(null);
		AttributeStructure structure = new AttributeStructure(filePath, null, _parent);
		structure.save();
		return structure;
	}
	
	/**
	 * Creates and saves a neuron for an attribute structure neural network and return that neuron
	 * as a parsed AttributeStructure. This Structure will have the label of _label.
	 * @param _parent
	 * @param _category
	 * @return
	 */
	public static AttributeStructure create(AttributeStructure _parent, String _label) {
		String filePath = Neuron.getNewFileLocation(_label);
		AttributeStructure structure = new AttributeStructure(filePath, _label, _parent);
		structure.save();
		return structure;
	}
	
	public Description createDescription(String _label) {
		Description newDescription = Description.create(this, _label);
		return newDescription;
	}
	
	public Description createDescription() {
		Description newDescription = Description.create(this);
		return newDescription;
	}
	
	/**
	 * Returns whether or not the Description network with root neuron of _descriptionNeuron
	 * can be a value for this attribute.
	 * @param _descriptionNeuron
	 * @return
	 */
	public boolean hasPossibility(Description _descriptionNeuron) {
		if (_descriptionNeuron.getType() == NeuronType.DESCRIPTION) {
			for (NeuralPathway possibility : this.getPossibilities()) {
				if (possibility.fireSynapse().equals(_descriptionNeuron)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * <p>
	 * Returns the index in this {@link AttributeStructure}s list of possibilities
	 * at which the {@link Desctription} d is located.
	 * </p>
	 * <p>
	 * If d is null or if d is not a possibility of this {@link AttributeStructure},
	 * -1 is returned.
	 * </p>
	 * @param d
	 * @return
	 */
	public int getPossibilityIndex(Description d) {
		if (d == null) { return -1; }
		for (int i = 0; i < this.getPossibilities().size(); i++) {
			if (this.getPossibilities().get(i).fireSynapse().equals(d)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Add _n as a Description neuron object to the possibilities for this
	 * Attribute Structure.
	 * @param _n
	 */
	public void addPossibility(Description _n) {
		if (!this.hasPossibility(_n)) {
			this.addNeuralPathway(AttributeStructure.PATH_TO_LIST_POSSIBILITIES_IN_ROOT, _n);
		}
	}
	
	/**
	 * Removes _n as a possibility of this {@link AttributeStructure}. If _n is not
	 * a possibility for this, then nothing is done.
	 * @param _n
	 */
	public void removePossibility(Description _n) {
		int indexOfPossibility = this.getPossibilityIndex(_n);
		if (indexOfPossibility >= 0) {
			this.removeNeuralPathway(AttributeStructure.PATH_TO_LIST_POSSIBILITIES_IN_ROOT, indexOfPossibility);
		}
	}

}
