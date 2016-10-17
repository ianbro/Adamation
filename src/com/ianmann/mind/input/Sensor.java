package com.ianmann.mind.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public abstract class Sensor implements Runnable{

	private volatile boolean running = true;
	
	private InputStream inputStream;
	//Need field that tells this sensor where to put input
	
	protected Sensor(InputStream _inputStream, int _memoryLocation) {
		this.inputStream = _inputStream;
	}
	
	public void run() {
		while(this.running) {
			this.getInput();
		}
	}
	
	public void terminate() {
		this.running = false;
		try {
			this.inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieve input from input stream and put it
	 * into the short term memory.
	 */
	protected abstract void getInput();
	
	/**
	 * This is the body of the sensor. Most of the logic will
	 * go in here. Each iteration of the sensors run loop will
	 * call this method.
	 */
	protected abstract void evaluate();
}
