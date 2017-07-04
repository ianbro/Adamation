package com.ianmann.mind.storage.assimilation;

import com.ianmann.mind.Neuron;

public class MorphemeNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MorphemeNotFound(String _morpheme) {
		super("Adamation has not yet associated a meaning for '" + _morpheme + "'");
	}
	
	public MorphemeNotFound(Neuron _neuron) {
		super("Adamation has not yet associated a morpheme for the idea stored at '" + _neuron.getAbsolutePath() + "'");
	}
}
