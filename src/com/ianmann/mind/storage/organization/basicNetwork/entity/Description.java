package com.ianmann.mind.storage.organization.basicNetwork.entity;

import java.io.FileNotFoundException;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.storage.organization.NeuronType;
import com.ianmann.mind.storage.organization.basicNetwork.NeuralNetwork;

public class Description extends NeuralNetwork {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * <p>
	 * The path in the axon of this {@link NeuralNetwork}s root {@link Neuron}
	 * pointing to where the root {@link Neuron} of the {@link AttributeStructure}
	 * is expected to be found.
	 * </p>
	 * <p>
	 * The value for this is {1,0}.
	 * </p>
	 */
	public final static int[] PATH_TO_STRUCTURE = {1,0};
	
	/**
	 * <p>
	 * Wraps a neuron file in a {@link Description} object.
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
	public Description(String _path, boolean _doLoadAttributes) throws FileNotFoundException, ParseException {
		super(_path, _doLoadAttributes);
	}

	/**
	 * Creates a {@link Description} object that is a possibility for the given
	 * {@link AttributeStructure} and stored in a new file at the given _path.
	 * @param _root
	 */
	public Description(String _path, AttributeStructure _attribute, String _label) {
		super(_path, NeuronType.DESCRIPTION, _label);
		this.setStructure(_attribute);
	}
	
	/**
	 * Returns the attribute structure for which this description is a possibility.
	 * @return
	 */
	public AttributeStructure getStructure() {
		return (AttributeStructure) this.getNeuralPathway(
				Description.PATH_TO_STRUCTURE[0],
				Description.PATH_TO_STRUCTURE[1]
		).fireSynapse();
	}
	
	public static Description create(AttributeStructure _definition) {
		String filePath = Neuron.getNewFileLocation(null);
		Description desc = new Description(filePath, _definition, null);
		desc.save();
		return desc;
	}
	
	public static Description create(AttributeStructure _definition, String _label) {
		String filePath = Neuron.getNewFileLocation(_label);
		Description desc = new Description(filePath, _definition, _label);
		desc.save();
		return desc;
	}
	
	/**
	 * Sets the attribute Structure that this description fulfills. If this description already has a structure,
	 * it is cleared and removed from this neuron's relationships, then replaced with _structure.
	 * @param _structure
	 */
	public void setStructure(AttributeStructure _structure) {
		AttributeStructure attribute = (AttributeStructure) this.removeNeuralPathway(Description.PATH_TO_STRUCTURE[0], Description.PATH_TO_STRUCTURE[1]);
		if (attribute != null) {
			attribute.removePossibility(this);
		}
		this.addNeuralPathway(Description.PATH_TO_STRUCTURE[0], Description.PATH_TO_STRUCTURE[1], _structure);
		_structure.addPossibility(this);
	}

}
