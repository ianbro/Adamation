package com.ianmann.mind.storage.organization.basicNetwork.entity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;

import com.ianmann.mind.NeuralPathway;
import com.ianmann.mind.Neuron;
import com.ianmann.mind.storage.organization.NeuronType;
import com.ianmann.mind.storage.organization.basicNetwork.ExtendableNeuralNetwork;
import com.ianmann.utils.utilities.GeneralUtils;

public class EntityStructure extends ExtendableNeuralNetwork {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * Array of ints that point to, in the axon, where the
	 * links to all {@link AttributeStructure}s that belong
	 * to this {@link EntityInstance} are expected to be found.
	 * </p>
	 * <p>
	 * Attributes:
	 * <br>
	 * Attributes in this dendrite group are any attribute that
	 * can describe this {@link EntityStructure}.
	 * </p>
	 * <p>
	 * Examples may be color, age, name, etc...
	 * </p>
	 */
	private static final int PATH_TO_LIST_ATTRIBUTES = 1;
	
	/**
	 * <p>
	 * Array of ints that point to, in the axon, where the
	 * links to all {@link EntityInstance}s that belong
	 * to this {@link EntityInstance} are expected to be found.
	 * </p>
	 * <p>
	 * Breakdown:<br>
	 * Entity Instances in this dendrite group are any object that
	 * combine to define this {@link EntityStructure}.
	 * </p>
	 * <p>
	 * Examples may be leg, eyes, head, soul, etc...
	 * </p>
	 */
	private static final int PATH_TO_LIST_BREAKDOWN = 2;
	
	/**
	 * <p>
	 * Wraps a neuron file in a {@link EntityStructure} object.
	 * </p>
	 * <p>
	 * If _doLoadAttributes is true, then the actual data in the file will
	 * be read into memory. Otherwise, in order to save memory, This instance
	 * will simply have the path to the file loaded and no other data.
	 * </p>
	 * @param _path
	 * @param _doLoadAttributes
	 * @throws ParseException 
	 * @throws FileNotFoundException 
	 */
	public EntityStructure(String _path, boolean _doLoadAttributes) throws FileNotFoundException, ParseException {
		super(_path, _doLoadAttributes);
	}
	
	/**
	 * Instantiate an Entity Structure network. This contains multiple attributes and acts
	 * as a definition for a noun. Examples of this may be the structure definition for
	 * person, table, car, etc...
	 * 
	 * This neuron will be used to create noun instances based on the structure of this
	 * definition.
	 * @param _neuron
	 */
	public EntityStructure(String _path, String _label, EntityStructure _parent) {
		super(_path, NeuronType.NOUN_DEFINITION, _label, _parent);
	}
	
	/**
	 * Creates and saves an Entity Structure network that is under the category _category and
	 * has the parent _parent. This network has no label to it.
	 * @param _parent
	 * @param _category
	 * @return
	 */
	public static EntityStructure create(EntityStructure _parent, String _label) {
		String filePath = Neuron.getNewFileLocation(_label);
		EntityStructure structure = new EntityStructure(filePath, _label, _parent);
		
		structure.save();
		return structure;
	}
	
	/**
	 * Creates and saves an Entity Structure network that is under the category _category,
	 * has the parent _parent and is associated with the morpheme _label.
	 * @param _parent
	 * @param _category
	 * @return
	 */
	public static EntityStructure create(EntityStructure _parent) {
		String filePath = Neuron.getNewFileLocation(null);
		EntityStructure structure = new EntityStructure(filePath, null, _parent);
		
		structure.save();
		return structure;
	}
	
	public EntityInstance instantiateStructure() {
		return EntityInstance.create(this);
	}
	
	public EntityInstance instantiateStructure(String _label) {
		return EntityInstance.create(this, _label);
	}
	
	/**
	 * Determines if this network is a sub-structure of the structure _parent.
	 * A network is considered a substructure if all of the attributes in
	 * _parent's network are present in this neurons network.
	 * @param _parent
	 * @return
	 */
	public boolean isSubStructure(EntityStructure _parent) {
		return this.getAttributes().containsAll(_parent.getAttributes());
	}
	
	/**
	 * Returns all attributes in this neurons network as Neuron objects. Only use this if
	 * you will need to access the data in the neurons as this function reads each neuron
	 * from its file.
	 * @return
	 */
	public ArrayList<NeuralPathway> getAttributes() {
		ArrayList<NeuralPathway> allAttributes = this.getDendriteGroup(EntityStructure.PATH_TO_LIST_ATTRIBUTES);
		
		EntityStructure parent = (EntityStructure) this.getParentNeuron();
		if (parent != null) {
			allAttributes.addAll(parent.getAttributes());
		}
		return allAttributes;
	}
	
	/**
	 * Return the related neuron as a parsed neuron object at index in this networks
	 * list of attributes.
	 * @param index
	 * @return
	 */
	public Neuron getAttribute(int index) {
		return this.getAttributes().get(index).fireSynapse();
	}
	
	/**
	 * Adds the neuron _n to the network of attributes that this network is consisted of.
	 * @param _n
	 */
	public void addAttribute(Neuron _n) {
		if (_n.getType() == NeuronType.NOUN_DEFINITION || _n.getType() == NeuronType.ATTRIBUTE) {
			this.addNeuralPathway(EntityStructure.PATH_TO_LIST_ATTRIBUTES, _n);
		}
	}
	
	/**
	 * Determines whether or not this entity instance can be assigned an instance of _attribute.
	 * It is assumed to be true if _attribute is contained in this EntityStructures list of attributes.
	 * @param _attribute
	 * @return
	 */
	public boolean hasAttribute(AttributeStructure _attribute) {
		for (NeuralPathway synapse : this.getAttributes()) {
			if (synapse.fireSynapse().equals(_attribute)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Determines whether or not this entity instance can be assigned an instance of _attribute.
	 * It is assumed to be true if _attribute can be assigned to any {@link AttributeStructure}
	 * that is contained in this EntityStructures list of Breakdowns.
	 * @param _attribute
	 * @return
	 */
	public boolean hasBreakdown(EntityStructure _attribute) {
		for (NeuralPathway synapse : this.getBreakdowns()) {
			if (synapse.fireSynapse().equals(_attribute)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * <p>
	 * Return all Entity Structures that are contained in the dendrite group containing
	 * the breakdown of this Structure.
	 * </p>
	 * <p>
	 * Entity Instances in this dendrite group are any object that
	 * combine to define this {@link EntityStructure}.
	 * </p>
	 * @return
	 */
	public ArrayList<NeuralPathway> getBreakdowns() {
		ArrayList<NeuralPathway> allBreakdowns = this.getDendriteGroup(EntityStructure.PATH_TO_LIST_BREAKDOWN);
		
		EntityStructure parent = (EntityStructure) this.getParentNeuron();
		if (parent != null) {
			allBreakdowns.addAll(parent.getBreakdowns());
		}
		return allBreakdowns;
	}
	
	/**
	 * <p>
	 * Adds an {@link EntityStructure} that will help combine with the others in the
	 * dendrite group referenced by {@link EntityStructure#PATH_TO_LIST_BREAKDOWN} to
	 * form the physical definition of this {@link EntityStructure}.
	 * </p>
	 * <p>
	 * For example, if this {@link EntityStructure} represents Person, then one of
	 * these breakdown {@link EntityStructure}s may be a definition for Leg or Arm.
	 * </p>
	 * @param _breakdown
	 */
	public void addBreakdown(EntityStructure _breakdown) {
		this.addNeuralPathway(EntityStructure.PATH_TO_LIST_BREAKDOWN, _breakdown);
	}
	
	public String toString() {
		String str = "<EntityStructure: type(" + NeuronType.mapType(this.getType()) + ")";
		if (!GeneralUtils.isNumeric(this.getAssociatedMorpheme())) {
			str = str + ";label(" + this.getAssociatedMorpheme() + ")";
		}
		str = str + ">";
		return str;
	}
}
