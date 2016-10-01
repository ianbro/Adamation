package com.ianmann.utils.utilities;

import java.lang.reflect.Constructor;

public interface JSONMappable {
	
	public Constructor getJsonConstructor();
	
	public String[] getConstructorFieldOrder();

}
