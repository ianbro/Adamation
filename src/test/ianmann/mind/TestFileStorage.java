package test.ianmann.mind;

import java.io.FileNotFoundException;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.core.Constants;
import com.ianmann.mind.storage.ShortTermMemory;

public class TestFileStorage {

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		// TODO Auto-generated method stub
		Constants.readStorageVariables();
		ShortTermMemory.initialize();
	}

}
