package com.ianmann.mind.storage;

import java.util.ArrayList;

public abstract class ShortTermMemory {

	private ShortTermMemory(){/*Don't instantiate this class*/}
	
	private static volatile ArrayList<ArrayList<Byte>> memory = new ArrayList<ArrayList<Byte>>();
	
	public void addData(byte[] data) {
		/* Check to see that there's enough space in memory.
		 * If not, send some data to the processor that deals with
		 * 		converting short term memory to long term memory to
		 *		free up some space.
		 */
		memory.add(com.ianmann.utils.utilities.Arrays.wrapByteArray(data));
	}
	
}
