package com.ianmann.mind.storage.organization.basicNetwork;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.core.navigation.Category;

public abstract class NeuralNetwork {
	
	/**
	 * The over-arching neuron in this network. This contains the
	 * type of network that this network represents.
	 */
	protected Neuron root;
	
	/**
	 * Instantiates the neural network with the root neuron _root.
	 * @param _root
	 */
	protected NeuralNetwork(Neuron _root) {
		this.root = _root;
	}
	
	/**
	 * Returns the root neuron in this neural network.
	 * @return
	 */
	public Neuron asNeuron() {
		return this.root;
	}
	
	/**
	 * Searches all of the synaptic endings that are connected to this neuron and puts them into
	 * their respecive lists according to their types. This is what parses the network from a flat
	 * list of neurons to a coherant network that which the neuron can know how exactly a given
	 * neuron is related to it.
	 */
	public abstract void sortNetwork();

}
