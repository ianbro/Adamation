/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Jul 3, 2017
 */
package com.ianmann.mind.exceptions;

/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Jul 3, 2017
 *
 */
public class NeuralNetworkTypeNotFoundError extends Error {

	public NeuralNetworkTypeNotFoundError(int _type, String _neuronFilePath) {
		super("The Neuron at the location: \"" + _neuronFilePath + "\" has an invalid NeuralNetwork type: " + _type);
	}
	
	public NeuralNetworkTypeNotFoundError(String _neuronFilePath, Throwable e) {
		super("The Neuron at the location: \"" + _neuronFilePath + "\" could not be loaded due to a bad file format.");
	}
}
