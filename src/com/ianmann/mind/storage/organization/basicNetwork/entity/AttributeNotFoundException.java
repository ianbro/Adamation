package com.ianmann.mind.storage.organization.basicNetwork.entity;

import com.ianmann.mind.Neuron;

public class AttributeNotFoundException extends Exception {

	public AttributeNotFoundException(Neuron _neuron) {
		super("Attribute: " + _neuron.getPath() + " is not stored as an attribute for this entity structure.");
	}
}
