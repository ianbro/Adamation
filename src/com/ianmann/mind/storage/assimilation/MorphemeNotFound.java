package com.ianmann.mind.storage.assimilation;

import com.ianmann.mind.Neuron;

public class MorphemeNotFound extends Exception {

	public MorphemeNotFound(String _morpheme) {
		super("Adamation has not yet associated a meaning for '" + _morpheme + "'");
	}
	
	public MorphemeNotFound(Neuron _neuron) {
		super("Adamation has not yet associated a morpheme for the idea stored at '" + _neuron.location.getAbsolutePath() + "'");
	}
}
