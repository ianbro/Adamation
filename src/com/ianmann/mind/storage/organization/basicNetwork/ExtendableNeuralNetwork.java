/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Jun 11, 2017
 */
package com.ianmann.mind.storage.organization.basicNetwork;

import java.io.FileNotFoundException;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.NeuralPathway;
import com.ianmann.mind.Neuron;

/**
 * <p>
 * Provides a parent Neuron that the NeuralNetwork can inherit from.
 * </p>
 * <p>
 * An example of when this might come in handy is if the NeuralNetwork
 * in question is an {@link EntityStructure} for person. You may want
 * this {@link NeuralNetwork} to inherit from an {@link EntityStructure}
 * for living beings.
 * </p>
 *
 * @author Ian
 * Created: Jun 11, 2017
 *
 */
public abstract class ExtendableNeuralNetwork extends NeuralNetwork {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * <p>
	 * Provides the two ints in order that point to where the parent
	 * {@link Neuron} is expected to be located in the axon of this
	 * {@link NeuralNetwork}'s root {@link Neuron}.
	 * </p>
	 * <p>
	 * The value for this is {0,0}.
	 * </p>
	 */
	private final static int[] PATH_TO_PARENT_IN_AXON = {0,0};
	
	/**
	 * <p>
	 * Loads a {@link Neuron} into memory from an already existing file.
	 * If _doLoadAttributes is set, then the data from the file will
	 * actually be read into this objects storage. Otherwise,
	 * {@link Neuron#loadAttributes()} will have to be called in order
	 * to access any information about the {@link Neuron}.
	 * </p>
	 * @param _path
	 * @param _doLoadAttributes
	 * @param _parent
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	protected ExtendableNeuralNetwork(String _path, boolean _doLoadAttributes) throws FileNotFoundException, ParseException {
		super(_path, _doLoadAttributes);
	}

	/**
	 * Calls the super constructor, then sets the parent {@link Neuron}
	 * of this {@link NeuralNetwork} to the {@link Neuron} provided in _parent.
	 * @param _root
	 */
	protected ExtendableNeuralNetwork(String _path, int _type, String _label, ExtendableNeuralNetwork _parent) {
		super(_path, _type, _label);
		this.setParentNeuron(_parent);
	}
	
	/**
	 * Returns the parent {@link Neuron} for this {@link NeuralNetwork}'s
	 * root {@link Neuron}.
	 * </p>
	 * <p>
	 * This method expects the {@link NeuralPathway} to the parent to be
	 * in the axon at the path [0][0] ({@code this.root.getAxon().get(0).get(0)}).
	 * See {@link ExtendableNeuralNetwork#PATH_TO_PARENT_IN_AXON}.
	 * </p>
	 * @return
	 */
	public ExtendableNeuralNetwork getParentNeuron() {
		try {
			return (ExtendableNeuralNetwork) this.getNeuralPathway(
					ExtendableNeuralNetwork.PATH_TO_PARENT_IN_AXON[0],
					ExtendableNeuralNetwork.PATH_TO_PARENT_IN_AXON[1]
			).fireSynapse();
		} catch (IndexOutOfBoundsException e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * <p>
	 * Adds a {@link NeuralPathway} to the root {@link Neuron}'s axon.
	 * </p>
	 * <p>In the event that the
	 * root {@link Neuron} does not have a pathway in the location expected for the parent
	 * {@link NeuralPathway} to be, then it will simply be set to a connection to _parent.
	 * </p>
	 * <p>
	 * If a pathway in the above mentioned location does exist, this method will remove that
	 * link and then set the new link.
	 * </p>
	 * @param _parent
	 */
	public void setParentNeuron(ExtendableNeuralNetwork _parent) {
		ExtendableNeuralNetwork currentParent = this.getParentNeuron();
		if (_parent == null) {
			return;
		} else if (currentParent == null) {
			this.addNeuralPathway(
					ExtendableNeuralNetwork.PATH_TO_PARENT_IN_AXON[0],
					ExtendableNeuralNetwork.PATH_TO_PARENT_IN_AXON[1],
					_parent);
		} else if (!_parent.equals(this.getParentNeuron())) {
			this.removeNeuralPathway(
					ExtendableNeuralNetwork.PATH_TO_PARENT_IN_AXON[0],
					ExtendableNeuralNetwork.PATH_TO_PARENT_IN_AXON[1]);
			this.addNeuralPathway(
					ExtendableNeuralNetwork.PATH_TO_PARENT_IN_AXON[0],
					ExtendableNeuralNetwork.PATH_TO_PARENT_IN_AXON[1],
					_parent);
		}
	}

}
