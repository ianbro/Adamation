package test.ianmann.mind;

import java.io.FileNotFoundException;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.core.Config;
import com.ianmann.mind.storage.ShortTermMemory;

public class TestFileStorage {

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		// TODO Auto-generated method stub
		Config.readStorageVariables();
		ShortTermMemory.initialize();
	}

}
