package test.ianmann.mind.storage;

import java.io.FileNotFoundException;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.Neuron;
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
		
		Category noun = new Category(EmotionUnit.CONTENT, "entity");
		noun.save();
		Category definition = new Category(EmotionUnit.CONTENT, "definition", noun);
		definition.save();
	}
	
	public static void testExampleTruckDog() {
		Category dog = new Category(EmotionUnit.CONTENT, "dog");
		dog.save();

		Category vehicle = new Category(EmotionUnit.ECSTATIC, "vehicle");
		vehicle.save();
		Category car = new Category(EmotionUnit.ECSTATIC, "car", vehicle);
		car.save();
		Category truck = new Category(EmotionUnit.GLAD, "truck", vehicle);
		truck.save();
		
		Neuron ford = new Neuron(null, EmotionUnit.GLAD, "ford", vehicle);
		ford.save();
		Neuron taurus = new Neuron(ford, EmotionUnit.ECSTATIC, "taurus", car);
		taurus.save();
		ford.addNeuralPathway(taurus);
		taurus.addNeuralPathway(ford);
		
		Neuron f150 = new Neuron(ford, EmotionUnit.GLAD, "f150", truck);
		f150.save();
		
		Neuron poodle = new Neuron(null, EmotionUnit.CONTENT, "poodle", dog);
		poodle.save();
		
		Neuron unknownFord = new Neuron(ford, EmotionUnit.CONTENT, "unknownFord", car);
		unknownFord.save();
		
		unknownFord.assimilate(truck);
	}

}
