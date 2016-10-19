package com.ianmann.mind.storage;

import java.util.ArrayList;

import com.ianmann.mind.core.Constants;

public abstract class ShortTermMemory {

	private ShortTermMemory(){/*Don't instantiate this class*/}
	
	private static volatile ArrayList<ArrayList<Byte>> memory = new ArrayList<ArrayList<Byte>>();
	
	/**
	 * initialize variables in this class.
	 */
	public static void initialize() {
		// Need 15 spaces in memory so just add 15 blank elements.
		for (int i = 0; i < Constants.SHORT_TERM_CAPACITY; i++) {
			memory.add(new ArrayList<Byte>());
		}
	}
	
	/**
	 * Wrapper method for {@code memory.add(int index, byte[] data}
	 * @param _data
	 * @param _location
	 */
	public static void addData(int _location, byte[] _data) {
		/* Check to see that there's enough space in memory.
		 * If not, send some data to the processor that deals with
		 * 		converting short term memory to long term memory to
		 *		free up some space.
		 */
		memory.add(_location, com.ianmann.utils.utilities.Arrays.wrapByteArray(_data));
	}
	
	/**
	 * Wrapper method for {@code memory.get(int index)}.
	 * @param i
	 * @return
	 */
	public static byte[] getData(int i) {
		return com.ianmann.utils.utilities.Arrays.unWrapByteArray(memory.get(i));
	}
	
}
