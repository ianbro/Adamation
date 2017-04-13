package test.ianmann.mind;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.Stimulant;
import com.ianmann.mind.core.Constants;
import com.ianmann.mind.core.navigation.Category;
import com.ianmann.mind.emotions.EmotionUnit;
import com.ianmann.mind.input.TextIdentification;
import com.ianmann.mind.storage.ShortTermMemory;
import com.ianmann.mind.storage.assimilation.Assimilation;
import com.ianmann.mind.storage.assimilation.MorphemeNotFound;
import com.ianmann.mind.storage.organization.basicNetwork.AttributeStructure;
import com.ianmann.mind.storage.organization.basicNetwork.Description;
import com.ianmann.mind.storage.organization.basicNetwork.EntityStructure;

public abstract class TestThoughtRelationships {

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		// TODO Auto-generated method stub
		Constants.readStorageVariables();
		ShortTermMemory.initialize();
		TextIdentification.initialize();

		Category c = new Category("label", null);
		Category being = new Category("being", null);
		
		AttributeStructure name = AttributeStructure.create(null, c, "Name");
		EntityStructure person = EntityStructure.create(null, being, "Person");
		person.addAttribute(person.asNeuron());
		person.addAttribute(name.asNeuron());
		
		Description ian = name.createDescription("ian");
	}

}
