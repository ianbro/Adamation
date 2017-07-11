package com.ianmann.mind.storage.organization.basicNetwork.entity;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.storage.organization.NeuronType;

public class AttributeMismatchException extends Exception {

	public AttributeMismatchException() {
		super("Attributes may not be set for an Attribute Structure besides its own structure.");
	}
}
