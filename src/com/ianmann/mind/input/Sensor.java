package com.ianmann.mind.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Base class that defines functionality for any sensor.
 * {@code com.ianmann.mind.input.Sensor} implements {@code java.lang.Runable}
 * so that it can process data separate from the main thread.
 * @author kirkp1ia
 *
 */
public abstract class Sensor implements Runnable{

	private volatile boolean running = true;
	
	/**
	 * Input will be read on this stream.
	 */
	protected InputStream inputStream;
	/**
	 * Index used to store data in short term
	 * memory.
	 */
	protected int memoryLocation;
	
	/**
	 * Base class that defines functionality for any sensor.
	 * {@code com.ianmann.mind.input.Sensor} implements {@code java.lang.Runable}
	 * so that it can process data separate from the main thread.
	 * <br><br>
	 * This constructor takes an input stream to read input from and
	 * an index which will be a reference to the location in short term
	 * memory where input will be stored.
	 * @param _inputStream
	 * @param _memoryLocation
	 */
	protected Sensor(InputStream _inputStream, int _memoryLocation) {
		this.inputStream = _inputStream;
		this.memoryLocation = _memoryLocation;
	}
	
	/**
	 * Initialize this sensor and start reading
	 * from {@code this.inputStream}.
	 */
	@Override
	public void run() {
		while(this.running) {
			byte[] input = this.getInput();
			this.evaluate(input);
		}
	}
	
	/**
	 * Halts the thread and closes {@code this.inputStream}.
	 */
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
	 * Retrieve input from {@code this.inputStream} and put it
	 * into the short term memory.
	 */
	protected abstract byte[] getInput();
	
	/**
	 * This is the body of the sensor. Most of the logic will
	 * go in here. Each iteration of the sensors run loop will
	 * call this method.
	 */
	protected abstract void evaluate(byte[] _input);
}
