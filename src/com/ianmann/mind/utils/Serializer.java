package com.ianmann.mind.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Serializer {

	public static byte[] serialize(Object _instance) throws IOException {
		if (!(_instance instanceof Serializable)) {
			throw new NotSerializableException("Instance of type: " + _instance.getClass().getName() + " must implement " + Serializable.class.getName());
		} else {
			ByteArrayOutputStream baos = null;
			
			baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(_instance);
			oos.flush();
			return baos.toByteArray();
		}
	}
	
	/**
	 * Serialize this object as a java object
	 * @param _serializedObject
	 * @return
	 */
	public static <T extends Serializable> T deserialize(Class<T> _class, byte[] _serializedObject) {
		T retInst = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(_serializedObject);
			ObjectInputStream ois = new ObjectInputStream(bais);
			retInst = (T) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return retInst;
	}
	
}
