package com.ianmann.mind.storage.organization.basicNetwork.entity;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.NeuralPathway;
import com.ianmann.mind.Neuron;
import com.ianmann.mind.storage.organization.NeuronType;
import com.ianmann.mind.storage.organization.basicNetwork.NeuralNetwork;
import com.ianmann.mind.storage.organization.basicNetwork.state.State;

/**
 * <p>
 * A implimentation of {@link NeuralNetwork} that represents a single entity. this
 * {@link NeuralNetwork} type is the core of the knowledge of a given object. Each
 * instance of this {@link Neuron} type is expected to be defined by an
 * {@link EntityStructure}.
 * </p>
 * <p>
 * An example of an entity that this {@link Neuron} type might represent is a single
 * person. Assume this person is Donald Trump. This will represent the root abstraction
 * of Donald Trump. The {@link EntityStructure} that defines Donald Trump will be a
 * {@link Neuron} that represents the abstraction of the definition for Person.
 * </p>
 * <p>
 * Therefor, Donald Trump can be defined by Person.
 * </p>
 * <p>
 * Any given {@link EntityInstance} can also be expected to contain a group of attributes
 * that represent the perceived descriptions of this entity (example: color of red). Also,
 * it contains {@link EntityInstance} {@link Neuron}s that represent the physical breakdown
 * of that entity. These descriptions and breakdowns are defined by this {@link EntityInstance}s
 * structure.
 * </p>
 *
 * @author Ian
 * Created: Jul 10, 2017
 *
 */
public class EntityInstance extends NeuralNetwork {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Array of ints that point, in the the axon, to the {@link NeuralPathway}
	 * that connects to the {@link EntityStructure} that defines this
	 * {@link EntityInstance}.
	 */
	private static final int[] PATH_TO_STRUCTURE = {1,0};
	
	/**
	 * <p>
	 * Integer that points to a list of dendrites that store the
	 * {@link Description} {@link Neuron}s that correspond to
	 * this {@link EntityInstance}s {@link AttributeStructure}
	 * {@link Neuron}s.
	 * </p>
	 * <p>
	 * An example of this may be red for color or 5'll" for height.
	 * </p>
	 */
	private static final int PATH_TO_LIST_DESCRIPTIONS = 2;
	
	/**
	 * <p>
	 * Integer that points to a list of dendrites that store the
	 * breakdown of this instance. These {@link Neuron}s are expected
	 * to be castable into {@link EntityInstance} objects.
	 * </p>
	 * <p>
	 * {@link EntityInstance}s in this dendrite group are used to
	 * create the abstraction of this {@link EntityInstance}.
	 * They correspond to the breakdown of this networks {@link EntityStructure}.
	 * </p>
	 */
	private static final int PATH_TO_LIST_BREAKDOWN_INSTANCES = 3;
	
	/**
	 * <p>
	 * Array of ints that point to, in the axon, where the
	 * link to the {@link EntityInstance} for which this {@link EntityInstance}
	 * is a breakdown {@link EntityInstance}.
	 * </p>
	 */
	private static final int[] PATH_TO_PARENT_ENTITY_INSTANCE = {4,0};
	
	/**
	 * <p>
	 * int that points to, in the axon, where the
	 * links to all {@link EntityInstance}s that belong
	 * to this {@link EntityInstance} are expected to be found.
	 * </p>
	 * <p>
	 * Possessions:
	 * <br>
	 * Entity Instances in this dendrite group are any object that
	 * can be possessed by this {@link EntityStructure} but do not
	 * make up the definition of this {@link EntityInstance}.
	 * </p>
	 * <p>
	 * Examples may be house, car, bed, etc...
	 * </p>
	 * <p>
	 * NOTE: These are not defined by this {@link EntityInstance}s structure.
	 * </p>
	 */
	private static final int PATH_TO_LIST_POSSESSIONS = 5;
	
	/**
	 * <p>
	 * Path to the dendrite group of {@link EntityInstance}s to which this
	 * {@link EntityInstance} belongs. {@link EntityInstance}s in this list
	 * can be assumed to have this {@link EntityInstance} linked in their
	 * list of possessions.
	 * </p>
	 */
	private static final int PATH_TO_LIST_OWNERS = 6;
	
	/**
	 * <p>
	 * Points to the dendrite group of {@link Neuron}s that represent the different
	 * states of this root {@link Neuron}.
	 * </p>
	 * <p>
	 * This and {@link EntityInstance#PATH_TO_CONTAINER_STATE}
	 * are exclusively different. If this {@link EntityInstance} is a root subject,
	 * then it should use this dendrite group to point to all the {@link State}s that
	 * represent this entity.
	 * </p>
	 * <p>
	 * However, if this is a subject that only represents the entity at a given point
	 * in history (i.e. this is a copy of the root {@link EntityInstance} that is
	 * a {@link State}s subject, then this {@link EntityInstance} should use
	 * {@link EntityInstance#PATH_TO_CONTAINER_STATE} and point to the {@link State} for which
	 * this is the subject.
	 * </p>
	 */
	private static final int PATH_TO_TIMELINE = 7;
	
	
	
	
	/**
	 * <p>
	 * Loads an already existing {@link EntityInstance} into memory.
	 * </p>
	 * <p>
	 * If _doLoadAttributes is set to true, then the related {@link NeuralPathway}s
	 * will be loaded into memory. Otherwise, nothing will be loaded.
	 * </p>
	 * @param _path
	 * @param _doLoadAttributes
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public EntityInstance(String _path, boolean _doLoadAttributes) throws FileNotFoundException, ParseException {
		super(_path, _doLoadAttributes);
	}
	
	/**
	 * Creates a new {@link EntityInstance} that will be defined by the given
	 * _structure. It will also take on the given _type. This is used for
	 * neuron classes that extend {@link EntityInstance}.
	 * @param _path
	 * @param _label
	 * @param _structure
	 */
	public EntityInstance(String _path, int _type, String _label, EntityStructure _structure) {
		super(_path, _type, _label);
		this.setStructure(_structure);
	}
	
	/**
	 * Creates a new {@link EntityInstance} that will be defined by the given
	 * _structure.
	 * @param _path
	 * @param _label
	 * @param _structure
	 */
	public EntityInstance(String _path, String _label, EntityStructure _structure) {
		super(_path, NeuronType.NOUN_INSTANCE, _label);
		this.setStructure(_structure);
	}
	
	public static EntityInstance create(EntityStructure _structure) {
		String filePath = Neuron.getNewFileLocation(null);
		EntityInstance instance = new EntityInstance(filePath, null, _structure);
		instance.save();
		return instance;
	}
	
	public static EntityInstance create(EntityStructure _structure, String _label) {
		String filePath = Neuron.getNewFileLocation(_label);
		EntityInstance instance = new EntityInstance(filePath, _label, _structure);
		instance.save();
		return instance;
	}
	
	
	
	
	/**
	 * Return the parent structure of which this entity instance is an instance.
	 * @return
	 */
	public EntityStructure getStructure() {
		return (EntityStructure) this.getNeuralPathway(EntityInstance.PATH_TO_STRUCTURE[0], EntityInstance.PATH_TO_STRUCTURE[1]).fireSynapse();
	}
	
	/**
	 * <p>
	 * Sets the Structure of this {@link EntityInstance}. The structure is the definition
	 * of this network.
	 * </p>
	 * <p>
	 * For example, if this {@link EntityInstance} represents Joe Bag'O'Doughnuts, then
	 * _sructure will be a {@link EntityStructure} that represents Person.
	 * </p>
	 */
	public void setStructure(EntityStructure _structure) {
		this.addNeuralPathway(EntityInstance.PATH_TO_STRUCTURE[0], EntityInstance.PATH_TO_STRUCTURE[1], _structure);
	}
	
	
	
	
	/**
	 * Sets _attribute to relate to this EntityInstances root Neuron. If _attribute's structure is not found an
	 * attribute of this instances structure (contained in the structure's network), then an {@link AttributeNotFoundException}
	 * is thrown.
	 * @param _breakdownInstance
	 * @throws AttributeNotFoundException
	 */
	public void addBreakdownInstance(EntityInstance _breakdownInstance) throws AttributeNotFoundException {
		if (!this.getStructure().hasBreakdown(_breakdownInstance.getStructure())) {
			throw new AttributeNotFoundException(_breakdownInstance.getStructure());
		} else {
			this.addNeuralPathway(EntityInstance.PATH_TO_LIST_BREAKDOWN_INSTANCES, _breakdownInstance);
			_breakdownInstance.setParentEntityInstance(this);
		}
	}
	
	/**
	 * <p>
	 * Returns all {@link NeuralPathway}s that are links to {@link EntityInstance}s that
	 * are defined by the {@link EntityStructure} _wantedStructure.
	 * </p>
	 * <p>
	 * For example, if this {@link EntityInstance} is Joe Bag'O'Doughnuts and _wantedStructure
	 * is Leg, then this will return the {@link EntityInstance}s for Joe's legs.
	 * </p>
	 * @TODO: This is not very fast. I would like a better way to filter out the EntityInsances
	 * that are not of type _wantedStructure instead of loading every single instance and getting
	 * its structure.
	 * @param _wantedStructure
	 * @return
	 * @throws AttributeNotFoundException 
	 */
	public ArrayList<NeuralPathway> getBreakdownInstances(EntityStructure _wantedStructure) throws AttributeNotFoundException {
		ArrayList<NeuralPathway> pathways = new ArrayList<NeuralPathway>();
		
		if (!this.getStructure().hasBreakdown(_wantedStructure)) {
			throw new AttributeNotFoundException(_wantedStructure);
		}
		
		for (NeuralPathway pathway : this.getDendriteGroup(EntityInstance.PATH_TO_LIST_BREAKDOWN_INSTANCES)) {
			EntityStructure neuronsStructure = ((EntityInstance) pathway.fireSynapse()).getStructure();
			if (neuronsStructure.equals(_wantedStructure)) {
				pathways.add(pathway);
			}
		}
		
		return pathways;
	}
	
	/**
	 * Sets the parent {@link EntityInstance} for which this {@link EntityInstance} is
	 * a breakdown of.
	 * @param _parentEntityInstance
	 * @return
	 */
	public NeuralPathway setParentEntityInstance(EntityInstance _parentEntityInstance) {
		NeuralPathway link = this.addNeuralPathway(
			EntityInstance.PATH_TO_PARENT_ENTITY_INSTANCE[0],
			EntityInstance.PATH_TO_PARENT_ENTITY_INSTANCE[1],
			_parentEntityInstance
		);
		return link;
	}
	
	/**
	 * Gets the parent {@link EntityInstance} for which this {@link EntityInstance} is
	 * a breakdown of.
	 * @return
	 */
	public NeuralPathway getParentEntityInstance() {
		NeuralPathway link = this.getNeuralPathway(
			EntityInstance.PATH_TO_PARENT_ENTITY_INSTANCE[0],
			EntityInstance.PATH_TO_PARENT_ENTITY_INSTANCE[1]
		);
		return link;
	}
	
	
	
	
	/**
	 * <p>
	 * TODO: Implement examples of {@link Description} and add this {@link EntityInstance} as an example of it.
	 * </p>
	 * <p>
	 * Sets _description to relate to this EntityInstances root Neuron. If _description's structure is not found an
	 * attribute of this instances structure (contained in the structure's network), then an {@link AttributeNotFoundException}
	 * is thrown.
	 * </p>
	 * @param _description
	 * @throws AttributeNotFoundException - if _description's structure is not found an
	 * attribute of this instances structure
	 */
	public void addDescription(Description _description) throws AttributeNotFoundException {
		if (!this.getStructure().hasAttribute(_description.getStructure())) {
			throw new AttributeNotFoundException(_description.getStructure());
		} else {
			this.addNeuralPathway(EntityInstance.PATH_TO_LIST_DESCRIPTIONS, _description);
		}
	}
	
	/**
	 * Returns all links to {@link Decription}s that can be used to describe the given _attribute. A {@link Description}
	 * is determined to be able to describe an {@link AttributeStructure} if that structure is the descriptions
	 * structure ({@link Description#getStructure()}).
	 * @param _attribute
	 * @return
	 * @throws AttributeNotFoundException 
	 */
	public ArrayList<NeuralPathway> getDescription(AttributeStructure _attribute) throws AttributeNotFoundException {
		ArrayList<NeuralPathway> descriptions = new ArrayList<NeuralPathway>();
		
		if (!this.getStructure().hasAttribute(_attribute)) {
			throw new AttributeNotFoundException(_attribute);
		}
		
		for (NeuralPathway pathway : this.getDendriteGroup(EntityInstance.PATH_TO_LIST_DESCRIPTIONS)) {
			if (pathway.fireSynapse().equals(_attribute)) {
				descriptions.add(pathway);
			}
		}
		
		return descriptions;
	}
	
	
	
	
	/**
	 * Returns all links to {@link EntityInstance}s that are possessed by this {@link EntityInstance} that are defined
	 * by _wantedType. An {@link EntityInstance} is said to be defined by a {@link EntityStructure} if that structure
	 * is the instances structure ({@link EntityInstance#getStructure()}).
	 * @param _wantedType
	 * @return
	 */
	public ArrayList<NeuralPathway> getPossessions(EntityStructure _wantedType) {
		ArrayList<NeuralPathway> possessions = new ArrayList<NeuralPathway>();
		
		for (NeuralPathway pathway : this.getDendriteGroup(EntityInstance.PATH_TO_LIST_POSSESSIONS)) {
			if (pathway.fireSynapse().equals(_wantedType)) {
				possessions.add(pathway);
			}
		}
		
		return possessions;
	}
	
	/**
	 * Adds a given possession to this {@link EntityInstance} effectively "giving" this instance possession of _instance.
	 * @param _instance
	 */
	public void addPossession(EntityInstance _instance) {
		this.addNeuralPathway(EntityInstance.PATH_TO_LIST_POSSESSIONS, _instance);
		_instance.addOwner(this);
	}
	
	/**
	 * <p>
	 * Adds the given _owner {@link EntityInstance} to this {@link EntityInstance}s
	 * list of owners. See {@link EntityInstance#PATH_TO_LIST_OWNERS} for information
	 * on owners.
	 * </p>
	 * @param _owner
	 * @return
	 */
	public NeuralPathway addOwner(EntityInstance _owner) {
		NeuralPathway link = this.addNeuralPathway(EntityInstance.PATH_TO_LIST_OWNERS, _owner);
		return link;
	}
	
	/**
	 * <p>
	 * Returns the list of {@link EntityInstance}s that own this {@link EntityInstance}.
	 * See {@link EntityInstance#PATH_TO_LIST_OWNERS} for information
	 * on owners.
	 * </p>
	 * @return
	 */
	public ArrayList<NeuralPathway> getOwners() {
		return this.getDendriteGroup(EntityInstance.PATH_TO_LIST_OWNERS);
	}
	
	
	
	
	/**
	 * Adds the given _state to this {@link EntityInstance}s timeline. This effectively adds the state as
	 * an point in history that Adamation can go back to and refer to.
	 * @param _state
	 */
	public void addStateToTimeline(State _state) {
		if (_state != null) {
			this.addNeuralPathway(EntityInstance.PATH_TO_TIMELINE, _state);
		}
	}
	
	/**
	 * <p>
	 * Returns all {@link NeuralPathwy}s that connect to this {@link EntityInstance}s timeline. This
	 * dendrite is always expected to be in order as far as point in history (first experience at index
	 * 0 and last experience at the highest index).
	 * </p>
	 * @return
	 */
	public ArrayList<NeuralPathway> getTimeline() {
		return this.getDendriteGroup(EntityInstance.PATH_TO_TIMELINE);
	}
}
