package com.ianmann.mind.patterns.language.text;

import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JOptionPane;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.core.navigation.Category;
import com.ianmann.mind.emotions.EmotionUnit;
import com.ianmann.mind.input.TextIdentification;
import com.ianmann.mind.storage.assimilation.MorphemeNotFound;

/**
 * Processes a specific language and determines the meaning of the
 * input
 * @author kirkp1ia
 *
 */
public class TextProcessor extends Neuron {

	/**
	 * Location in memory that this processor will insert input to.
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
	 * Thread used to return the neurons associated with the words and
	 * morphemes stored in short term memory.
	 */
	protected Thread processingThread;
	/**
	 * Runnable class which will become the target for this.processingThread
	 */
	protected Runnable processingRunnable;

	/**
	 * Create a pattern processor and link it to data in short term memory
	 * @param _toOutput - Process or neuron that the output of this process
	 * will fire upon completion of this process
	 * @param _associated
	 * @param _label
	 */
	public TextProcessor(Neuron _toOutput, EmotionUnit _associated, String _label, Neuron _delimiter, int _memoryLocation) {
		super(_toOutput, _associated, _label, Category.LANGUAGE);
		this.delimiter = _delimiter;
		this.memoryLocation = _memoryLocation;
	}
	
	/**
	 * Return the delimiter. This method also changes any special
	 * character delimiter to an escaped character.
	 * @return
	 */
	protected String getDelimeter() {
		String strdelimiter = this.delimiter.getAssociatedMorpheme();
		
		//Special characters to escape instead of literals
		switch(strdelimiter) {
		default:
			return strdelimiter;
		}
	}
	
	/**
	 * Return the input as a list of words seperated
	 * by the delimiter.
	 * @return
	 */
	protected String[] delimit(String _input) {
		return _input.split(this.getDelimeter());
	}
	
	/**
	 * Return the array of words. Every thing will be split into morphemes
	 * and words.
	 * @return
	 */
	protected String[] getSentenceDelimited(String _input) {
		String[] delimitedSentence = this.delimit(_input);
		
		return delimitedSentence;
	}
	
	/**
	 * Fire up the thread that will process the sentence and determine it's meaning.
	 */
	public void process(String _msg) {
		String[] messageDelimited = this.getSentenceDelimited(_msg);
		this.processingRunnable = new TextProcessorThread(messageDelimited);
		this.processingThread = new Thread(this.processingRunnable);
		this.processingThread.start();
	}
}

/**
 * Worker class that grabs the neurons for a sentence and determines
 * what it means. The process should then output it to another process
 * that will then evaluate the message and determine what to do with it.
 * @author kirkp1ia
 *
 */
class TextProcessorThread implements Runnable {
	
	/**
	 * Message that this text processor will try to interperate.
	 */
	private String[] message;
	
	public TextProcessorThread(String[] _msg) {
		this.message = _msg;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, Arrays.toString(this.message));
		
		
	}
	
	public Neuron getNeuron(String _morpheme) throws MorphemeNotFound {
		return TextIdentification.getNeuronForMorpheme(_morpheme);
	}
	
}

class StatementIterator implements Iterator<String> {
	
	private String[] data;
	private int currentIndex = 0;
	
	public StatementIterator(String[] _statement) {
		this.data = _statement;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		if (this.data.length > this.currentIndex) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String next() {
		// TODO Auto-generated method stub
		return this.data[this.currentIndex];
	}
	
}
