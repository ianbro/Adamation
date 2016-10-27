package test.ianmann.mind.storage;

import java.io.FileNotFoundException;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.core.Constants;
import com.ianmann.mind.core.navigation.Category;
import com.ianmann.mind.emotions.EmotionUnit;
import com.ianmann.mind.input.TextIdentification;
import com.ianmann.mind.storage.ShortTermMemory;

public class TestCategories {

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		// TODO Auto-generated method stub
		Constants.readStorageVariables();
		ShortTermMemory.initialize();
		TextIdentification.initialize();

		Category pattern = new Category(EmotionUnit.CONTENT, "pattern");
		Category noun = new Category(EmotionUnit.CONTENT, "language", pattern);
	}

}
