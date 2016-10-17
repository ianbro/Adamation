package com.ianmann.mind.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.ianmann.mind.storage.ShortTermMemory;

/**
 * Sensor used for text input. The format for text input should go
 * as follows:
 * <br><br>
 * {@code @HEADER_TEXT your message here}
 * @author kirkp1ia
 *
 */
public class TextSensor extends Sensor {
	
	/**
	 * If this is the header for the text, then
	 * it means that a person is trying to talk
	 * to it.
	 */
	private static int HEADER_TALK = 1;
	/**
	 * If this is the header for the text, then
	 * it means that a person is trying to talk
	 * to it and they are yelling.
	 */
	private static int HEADER_YELL = 2;
	
	/**
	 * File in which input will be retrieved
	 */
	private File inputFile;

	/**
	 * Sensor used for text input. Instead of reading raw bytes,
	 * this sensor will return everything as a String.
	 * @param _inputFile, _memoryLocation
	 * @param _memoryLocation
	 * @throws FileNotFoundException 
	 */
	public TextSensor(File _inputFile, int _memoryLocation) throws FileNotFoundException {
		super(new FileInputStream(_inputFile), _memoryLocation);
		this.inputFile = _inputFile;
	}

	/**
	 * Sensor used for text input. Instead of reading raw bytes,
	 * this sensor will return everything as a String.
	 * @param _inputFile, _memoryLocation
	 * @param _memoryLocation
	 * @throws FileNotFoundException 
	 */
	public TextSensor(InputStream _inputStream, int _memoryLocation) throws FileNotFoundException {
		super(_inputStream, _memoryLocation);
	}

	@Override
	protected byte[] getInput() {
		byte[] rawInput = new byte[501];
		
		try {
			this.inputStream.read(rawInput);
			
			if (rawInput[500] != 0x00) {
				rawInput[500] = 0x00;
				System.err.println("WARNING-TEXTSENSOR: Text input is too long for this sensor to handel.\n\tInput this as seperate messages.");
			}
			
			return rawInput;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void evaluate(byte[] _input) {
		// TODO Auto-generated method stub
		
		/*
		 * If nothing was in the file, then just ignore it.
		 */
		if (_input[0] == 0x00) {
			return;
		}
		
		try {
			int header = this.getHeader(new String(_input));
			String msg = this.getDataFromShortTermMemoryStripped(new String(_input));
			
			/*
			 * Strip off the extra null bytes from msg
			 */
			for (int i = 0; i < msg.length(); i++) {
				if (msg.charAt(i) == 0x00) {
					msg = msg.substring(0, i-1); //Minus 1 because input also includes the enter for submission
				}
			}
			
			ShortTermMemory.addData(msg.getBytes(), this.memoryLocation);
			
			if (header == HEADER_TALK) {
				this.onTalk(msg);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Return data but without the header.
	 * @return
	 */
	private String getDataFromShortTermMemoryStripped(String _rawIn) {
		if (_rawIn.charAt(0) != '@') {
			return _rawIn;
		}
		
		// Index of the space character after the header.
		int headerEnd = 0;
		
		for (int i = 0; i < _rawIn.length(); i++) {
			if (_rawIn.charAt(i) == ' ') {
				headerEnd = i;
				break;
			}
		}
		
		return _rawIn.substring(headerEnd+1);
	}
	
	/**
	 * Gets header from input as an integer.
	 * @param input
	 * @return
	 * @throws IOException
	 */
	private int getHeader(String _rawIn) throws IOException {
		if (_rawIn.charAt(0) != '@') {
			throw new IOException("Header not found. Please prepend header with '@' and append with a space character.");
		}
		
		// Index of the space character after the header.
		int headerEnd = 0;
		
		for (int i = 0; i < _rawIn.length(); i++) {
			if (_rawIn.charAt(i) == ' ') {
				headerEnd = i;
				break;
			}
		}
		
		String header = _rawIn.substring(1, headerEnd); //skip first character "@"
		
		switch(header) {
			case "TALK":
				return 1;
			default:
				throw new IOException("'" + header + "' is not a valid header.");
		}
	}
	
	/**
	 * Action to be called in the event of someone talking to Adamation.
	 * @param _input
	 */
	private void onTalk(String _input) {
		System.out.println(_input);
		System.out.println(com.ianmann.utils.utilities.Arrays.wrapByteArray(ShortTermMemory.getData(this.memoryLocation)));
	}

}
