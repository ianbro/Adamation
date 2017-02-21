package com.ianmann.mind.storage.organization.basicNetwork;

import java.util.ArrayList;

import com.ianmann.database.orm.QuerySet;
import com.ianmann.mind.Neuron;
import com.ianmann.mind.storage.organization.basicNetwork.complex.EventSnapshot;

public class AttributeInstance {

	/**
	 * <p>
	 * Points to the neuron that represents the attribute field that this
	 * will fil.
	 * </p>
	 * <p>
	 * This can be things such as height, color, etc...
	 * This is not for things like tall, red. That's what this class {@code AttributeInstance}
	 * is for.
	 * </p>
	 */
	public AttributeField forField;
	
	/**
	 * <p>
	 * List of snapshots (images stored in the brain from a certain event) that represent
	 * memories and examples of this {@code AttributeInstance}.
	 * </p>
	 * <p>
	 * This may be things such as an image memory of a red t-shirt for red or a tall
	 * person for tall.
	 * </p>
	 */
	public ArrayList<EventSnapshot> examples;
}
