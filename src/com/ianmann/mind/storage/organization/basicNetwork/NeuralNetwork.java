package com.ianmann.mind.storage.organization.basicNetwork;

import java.io.FileNotFoundException;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.Neuron;

public abstract class NeuralNetwork extends Neuron {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates the neural network with a path to the given
	 * file. This constructor assumes that the {@link Neuron}
	 * already exists in storage.
	 * @param _path - path to the file of the desired {@link Neuron}
	 * @param _doLoadAttribute - weather or not the {@link Neuron}'s
	 * data should be loaded from its file
	 * @throws ParseException 
	 * @throws FileNotFoundException 
	 */
	protected NeuralNetwork(String _path, boolean _doLoadAttribute) throws FileNotFoundException, ParseException {
		super(_path, _doLoadAttribute);
	}
	
	/**
	 * Calls the super constructor that creates the a new {@link Neuron} in
	 * storage.
	 * @param _path - The path to the file which will be created and in
	 * which the {@link Neuron} will be stored.
	 * @param _type - The type of {@link NeuralNetwork} that this {@link Neuron}
	 * is.
	 * @param _label - The String that will be set to this {@link Neuron}s
	 * associated morpheme.
	 */
	protected NeuralNetwork(String _path, int _type, String _label) {
		super(_path, _type, _label);
	}
}
