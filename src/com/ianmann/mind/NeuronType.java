package com.ianmann.mind;

import java.security.InvalidParameterException;

import com.ianmann.database.orm.Model;

public final class NeuronType {

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
	 */
	public static final int NOUN_INSTANCE = 2;
	
	/**
	 * Designates that this neuron represents a category which groups other neurons together.
	 */
	public static final int CATEGORY = 3;
	
	/**
	 * <p>
	 * Represents the actual value of an attribute. This may include neurons representing
	 * red, tall, heavy, etc...
	 * </p>
	 */
	public static final int DESCRIPTION = 4;
	
	/**
	 * <p>
	 * Designates that this neuron will contain the information representing a pattern
	 * and how the pattern will be evealuated.
	 * </p>
	 */
	public static final int PATTERN_PROCESSOR = 5;
	
	public static String mapType(int code) {
		switch(code) {
		case ATTRIBUTE:
			return "Attribute";
		case NOUN_DEFINITION:
			return "Noun Definition";
		case NOUN_INSTANCE:
			return "Noun Instance";
		case CATEGORY:
			return "Category";
		default:
			throw new InvalidParameterException(code + " is not a valid neruon type code.");
		}
	}
}
