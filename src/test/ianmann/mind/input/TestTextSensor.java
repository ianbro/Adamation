package test.ianmann.mind.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.core.Constants;
import com.ianmann.mind.input.TextIdentification;
import com.ianmann.mind.input.text.TextSensor;
import com.ianmann.mind.storage.ShortTermMemory;

public class TestTextSensor {

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		// TODO Auto-generated method stub
		Constants.readStorageVariables();
		ShortTermMemory.initialize();
		TextIdentification.initialize();

		System.out.println("Loading sensor...");
		
		TextSensor reader = new TextSensor(System.in, 1);
		Thread t = new Thread(reader);
		t.start();
	}

}
