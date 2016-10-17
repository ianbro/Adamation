package com.ianmann.utils.utilities;

import java.util.ArrayList;

public abstract class Arrays {

	public static ArrayList<Byte> wrapByteArray(byte[] bs) {
		ArrayList<Byte> bytes = new ArrayList<Byte>();
		
		for (int i = 0; i < bs.length; i++) {
			bytes.add(bs[i]);
		}
		return bytes;
	}
}
