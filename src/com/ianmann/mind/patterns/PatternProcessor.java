package com.ianmann.mind.patterns;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.core.navigation.Category;
import com.ianmann.mind.emotions.EmotionUnit;

/**
 * Adamation needs to be able to distinguish patterns. This may include
 * patterns such as an equation, a series of images, a sentence or anything
 * else on which rules can be applied.
 * @author kirkp1ia
 *
 */
public class PatternProcessor extends Neuron {
	
	/**
	 * Location in memory that this processor will grab input from.
	 */
	protected int memoryLocation;

	/**
	 * Create a pattern processor and link it to data in short term memory
	 * @param _toOutput - Process or neuron that the output of this process
	 * will fire upon completion of this process
	 * @param _associated
	 * @param _label
	 */
	public PatternProcessor(Neuron _toOutput, EmotionUnit _associated, String _label) {
		super(_toOutput, _associated, _label, Category.PATTERN);
		// TODO Auto-generated constructor stub
	}

	/**
	 * This represents the condition that a pattern needs
	 * to be met to be split into seperate terms.
	 */
	
}
