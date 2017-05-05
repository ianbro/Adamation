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
import com.ianmann.mind.storage.organization.basicNetwork.AttributeNotFoundException;
import com.ianmann.mind.storage.organization.basicNetwork.AttributeStructure;
import com.ianmann.mind.storage.organization.basicNetwork.Description;
import com.ianmann.mind.storage.organization.basicNetwork.EntityInstance;
import com.ianmann.mind.storage.organization.basicNetwork.EntityStructure;

public abstract class TestThoughtRelationships {

	public static void main(String[] args) throws FileNotFoundException, ParseException, AttributeNotFoundException {
		// TODO Auto-generated method stub
		Constants.readStorageVariables();
		ShortTermMemory.initialize();
		TextIdentification.initialize();

//		generatePersonNetwork();
		generatePerson("Ian");
		generatePerson("Wynton");
		
//		Neuron person = Neuron.fromJSON(new File("mind/storage/neurons/being/person.nrn"));
//		EntityStructure personStruct = new EntityStructure(person);
//		System.out.println(personStruct.getAttributes());
//		System.out.println(personStruct.getParentNetwork());
	}
	
	public static void generatePersonNetwork() {
		Category catBeing = new Category("being", null);
		Category catBodyPart = new Category("body part", null);
		Category identifier = new Category("identifier", null);
		Category size = new Category("size", null);
		
		EntityStructure bodyPart = EntityStructure.create(null, catBodyPart, "body bart");
		EntityStructure limb = EntityStructure.create(null, catBodyPart, "limb");
		
		EntityStructure leg = EntityStructure.create(null, catBodyPart, "leg");
		EntityStructure arm = EntityStructure.create(null, catBodyPart, "arm");
		
		AttributeStructure name = AttributeStructure.create(null, identifier, "name");
		AttributeStructure height = AttributeStructure.create(null, size, "height");
		
		EntityStructure being = EntityStructure.create(null, catBeing, "being");
		being.addAttribute(name.asNeuron());
		being.addAttribute(height.asNeuron());
		
		EntityStructure person = EntityStructure.create(being, catBeing, "person");
		person.addAttribute(leg.asNeuron());
		person.addAttribute(arm.asNeuron());
	}
	
	public static void generatePerson(String _name) throws FileNotFoundException, ParseException, AttributeNotFoundException {
		Neuron person = Neuron.fromJSON(new File("mind/storage/neurons/being/person.nrn"));
		EntityStructure personStruct = new EntityStructure(person);
		Neuron leg = Neuron.fromJSON(new File("mind/storage/neurons/body part/leg.nrn"));
		EntityStructure legStruct = new EntityStructure(leg);
		Neuron arm = Neuron.fromJSON(new File("mind/storage/neurons/body part/arm.nrn"));
		EntityStructure armStruct = new EntityStructure(arm);
		Neuron name = Neuron.fromJSON(new File("mind/storage/neurons/identifier/name.nrn"));
		AttributeStructure nameStruct = new AttributeStructure(name);
		
		EntityInstance personInstance = personStruct.instantiateStructure(person.getParentCategory(), _name);
		
		EntityInstance leftLeg = legStruct.instantiateStructure(leg.getParentCategory(), _name + "s_left_leg");
		EntityInstance rightLeg = legStruct.instantiateStructure(leg.getParentCategory(), _name + "s_right_leg");
		EntityInstance leftArm = armStruct.instantiateStructure(arm.getParentCategory(), _name + "s_left_arm");
		EntityInstance rightArm = armStruct.instantiateStructure(arm.getParentCategory(), _name + "s_right_arm");
		
		Description nameInstance = nameStruct.createDescription(_name);
		
		personInstance.addAttribute(rightArm);
		personInstance.addAttribute(leftArm);
		personInstance.addAttribute(rightLeg);
		personInstance.addAttribute(leftLeg);
		personInstance.addAttribute(nameInstance);
	}

}