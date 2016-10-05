package com.ianmann.mind.utils;

import java.io.Serializable;

public abstract class Serializer {

	public static <T extends Serializable> byte[] serialize(Class<T> _serializeClass, T _instance) {
		return null;
	}
	
}
