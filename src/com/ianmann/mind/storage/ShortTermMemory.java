package com.ianmann.mind.storage;

import java.util.ArrayList;

import com.ianmann.mind.Neuron;

public abstract class ShortTermMemory {

	private ShortTermMemory(){/*Don't instantiate this class*/}
	
	private static volatile ArrayList<Neuron> memory = new ArrayList<Neuron>(15);
	
	/**
	 * initialize variables in this class.
	 */
	public static void initialize() {
		
	}
	
	/**
	 * Wrapper method for {@code memory.add(int index, byte[] data}
	 * @param _data
	 * @param _location
	 */
	public static void addData(int _location, Neuron _neuron) {
		/* Check to see that there's enough space in memory.
		 * If not, send some data to the processor that deals with
		 * 		converting short term memory to long term memory to
		 *		free up some space.
		 */
		memory.add(_location, _neuron);
	}
	
	/**
	 * Wrapper method for {@code memory.get(int index)}.
	 * @param i
	 * @return
	 */
	public static Neuron getData(int i) {
		return memory.get(i);
	}
	
}
