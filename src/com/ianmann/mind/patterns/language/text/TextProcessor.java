package com.ianmann.mind.patterns.language.text;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.emotions.EmotionUnit;
import com.ianmann.mind.storage.ShortTermMemory;

/**
 * Processes a specific language and determines the meaning of the
 * input
 * @author kirkp1ia
 *
 */
public class TextProcessor extends Neuron {

	/**
	 * Location in memory that this processor will grab input from.
	 */
	protected int memoryLocation;
	
	/**
	 * Delimiter to separate words of the text.
	 * The delimiter should be stored in the array
	 * of associated morphemes.
	 * 
	 * For example, in written english, the delimiter
	 * would be a space character.
	 */
	protected Neuron delimiter;

	/**
	 * Create a pattern processor and link it to data in short term memory
	 * @param _toOutput - Process or neuron that the output of this process
	 * will fire upon completion of this process
	 * @param _associated
	 * @param _label
	 */
	public TextProcessor(Neuron _toOutput, EmotionUnit _associated, String _label, Neuron _delimiter, int _memoryLocation) {
		super(_toOutput, _associated, _label);
		this.delimiter = _delimiter;
		this.memoryLocation = _memoryLocation;
	}
	
	/**
	 * Return the delimiter. This method also changes any special
	 * character delimiter to an escaped character.
	 * @return
	 */
	protected String getDelimeter() {
		if (this.delimiter.getAssociatedMorphemes().length != 1) {
			return null;
		} else {
			String strdelimiter = this.delimiter.getAssociatedMorphemes()[0];
			
			//Special characters to escape instead of literals
			switch(strdelimiter) {
			default:
				return strdelimiter;
			}
		}
	}
	
	/**
	 * Grab the input from short term memory
	 * @return
	 */
	protected String getInput() {
		return new String(ShortTermMemory.getData(this.memoryLocation));
	}
	
	/**
	 * Return the input as a list of words seperated
	 * by the delimiter.
	 * @return
	 */
	public String[] delimit() {
		return this.getInput().split(this.getDelimeter());
	}
}
