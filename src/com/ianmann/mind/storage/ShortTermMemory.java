package com.ianmann.mind.storage;

import java.util.ArrayList;

import com.ianmann.mind.core.Constants;

public abstract class ShortTermMemory {

	private ShortTermMemory(){/*Don't instantiate this class*/}
	
	private static volatile ArrayList<ArrayList<Byte>> memory = new ArrayList<ArrayList<Byte>>();
	
	public static void initialize() {
		for (int i = 0; i < Constants.SHORT_TERM_CAPACITY; i++) {
			memory.add(new ArrayList<Byte>());
		}
	}
	
	public static void addData(byte[] _data, int _location) {
		/* Check to see that there's enough space in memory.
		 * If not, send some data to the processor that deals with
		 * 		converting short term memory to long term memory to
		 *		free up some space.
		 */
		memory.add(_location, com.ianmann.utils.utilities.Arrays.wrapByteArray(_data));
	}
	
	public static byte[] getData(int i) {
		return com.ianmann.utils.utilities.Arrays.unWrapByteArray(memory.get(i));
	}
	
}
