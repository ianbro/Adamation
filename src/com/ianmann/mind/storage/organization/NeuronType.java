package com.ianmann.mind.storage.organization;

import java.security.InvalidParameterException;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.storage.organization.basicNetwork.entity.AttributeStructure;
import com.ianmann.mind.storage.organization.basicNetwork.entity.Description;
import com.ianmann.mind.storage.organization.basicNetwork.entity.EntityInstance;
import com.ianmann.mind.storage.organization.basicNetwork.entity.EntityStructure;

public class NeuronType {

private NeuronType(){/* Please don't instantiate. */}
	
	/**
	 * </>
	 * Noun Definitions have different attributes. These serve the same
	 * purpose as attributes in classes. they are the field definition for
	 * a set of description values.
	 * </p>
	 * <p>
	 * This may include neurons such as height, weight, color. Description will
	 * represent the actual value of an attribute such as tall, heavy, red.
	 * </p>
	 * <p>
	 * This type will cause any Neuron of this type to be parsed as a
	 * {@link AttributeStructure} network when the {@link Neuron} is
	 * instantiated.
	 * </p>
	 */
	public static final int ATTRIBUTE = 0;
	
	/**
	 * <p>
	 * Designates that this neuron contains the definition for a noun type.
	 * Think of this as a java class.
	 * </p>
	 * <p>
	 * An example would be dog, cat, person, animal, being, etc...
	 * They are made up of different attributes and abilities.
	 * </p>
	 * <p>
	 * This type will cause any Neuron of this type to be parsed as a
	 * {@link EntityStructure} network when the {@link Neuron} is
	 * instantiated.
	 * </p>
	 */
	public static final int NOUN_DEFINITION = 1;
	
	/**
	 * <p>
	 * Designates that this neuron will contain the information representing
	 * a specific instance of a noun definition.
	 * </p>
	 * <p>
	 * Example:<br>
	 * * assume a noun definition exists that defines human.<br>
	 * Then a noun instance that corresponds to that could be a
	 * specific person.
	 * </p>
	 * <p>
	 * This type will cause any Neuron of this type to be parsed as a
	 * {@link EntityInstance} network when the {@link Neuron} is
	 * instantiated.
	 * </p>
	 */
	public static final int NOUN_INSTANCE = 2;
	
	/**
	 * <p>
	 * Represents the actual value of an attribute. This may include neurons representing
	 * red, tall, heavy, etc...
	 * </p>
	 * <p>
	 * This type will cause any Neuron of this type to be parsed as a
	 * {@link Description} network when the {@link Neuron} is
	 * instantiated.
	 * </p>
	 */
	public static final int DESCRIPTION = 4;
	
	/**
	 * <p>
	 * Designates that this {@link Neuron} is a container for a given state of an
	 * {@link EntityInstance}.
	 * </p>
	 * <p>
	 * This type will cause any Neuron of this type to be parsed as a
	 * {@link State} network when the {@link Neuron} is
	 * instantiated.
	 * </p>
	 */
	public static final int STATE = 5;
	
	/**
	 * <p>
	 * Designates that this neuron will contain the information representing a pattern
	 * and how the pattern will be evaluated.
	 * </p>
	 */
	public static final int PATTERN_PROCESSOR = 6;
	
	public static String mapType(int code) {
		switch(code) {
		case ATTRIBUTE:
			return "Attribute";
		case NOUN_DEFINITION:
			return "Noun Definition";
		case NOUN_INSTANCE:
			return "Noun Instance";
		case STATE:
			return "State";
		default:
			throw new InvalidParameterException(code + " is not a valid neruon type code.");
		}
	}
}
