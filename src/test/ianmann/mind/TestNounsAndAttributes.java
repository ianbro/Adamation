package test.ianmann.mind;

import java.io.FileNotFoundException;
import org.json.simple.parser.ParseException;

import com.ianmann.mind.core.Constants;
import com.ianmann.mind.storage.ShortTermMemory;
import com.ianmann.mind.storage.organization.basicNetwork.entity.AttributeNotFoundException;
import com.ianmann.mind.storage.organization.basicNetwork.entity.AttributeStructure;
import com.ianmann.mind.storage.organization.basicNetwork.entity.Description;
import com.ianmann.mind.storage.organization.basicNetwork.entity.EntityInstance;
import com.ianmann.mind.storage.organization.basicNetwork.entity.EntityStructure;

public abstract class TestNounsAndAttributes {

	public static void main(String[] args) throws FileNotFoundException, ParseException, AttributeNotFoundException {
		// TODO Auto-generated method stub
		Constants.readStorageVariables();
		ShortTermMemory.initialize();

//		generatePersonNetwork();
//		generatePerson("Ian");
//		generatePerson("Wynton");
		
		EntityInstance ian = new EntityInstance("person_Ian.nrn", true);
		AttributeStructure height = new AttributeStructure("height.nrn", true);
		Description fe = height.createDescription("five_eleven");
		ian.addDescription(fe);
	}
	
	public static void generatePersonNetwork() {
		
		EntityStructure bodyPart = EntityStructure.create(null, "body_part");
		EntityStructure limb = EntityStructure.create(bodyPart, "limb");
		
		EntityStructure leg = EntityStructure.create(limb, "leg");
		EntityStructure arm = EntityStructure.create(limb, "arm");
		
		AttributeStructure name = AttributeStructure.create(null, "name");
		AttributeStructure height = AttributeStructure.create(null, "height");
		
		EntityStructure being = EntityStructure.create(null, "being");
		being.addAttribute(name);
		being.addAttribute(height);
		
		EntityStructure person = EntityStructure.create(being, "person");
		person.addBreakdown(leg);
		person.addBreakdown(arm);
	}
	
	public static void generatePerson(String _name) throws FileNotFoundException, ParseException, AttributeNotFoundException {
		EntityStructure personStruct = new EntityStructure("person.nrn", true);
		
		EntityStructure legStruct = new EntityStructure("leg.nrn", true);
		EntityStructure armStruct = new EntityStructure("arm.nrn", true);
		AttributeStructure nameStruct = new AttributeStructure("name.nrn", true);
		
		EntityInstance personInstance = personStruct.instantiateStructure("person_" + _name);
		
		EntityInstance leftLeg = legStruct.instantiateStructure(_name + "s_left_leg");
		EntityInstance rightLeg = legStruct.instantiateStructure(_name + "s_right_leg");
		EntityInstance leftArm = armStruct.instantiateStructure(_name + "s_left_arm");
		EntityInstance rightArm = armStruct.instantiateStructure(_name + "s_right_arm");
		
		Description nameInstance = nameStruct.createDescription(_name);
		
		/*
		 * The method for addBreakdownInstance is not saving the relationships.
		 */
		personInstance.addBreakdownInstance(rightArm);
		personInstance.addBreakdownInstance(leftArm);
		personInstance.addBreakdownInstance(rightLeg);
		personInstance.addBreakdownInstance(leftLeg);
		personInstance.addDescription(nameInstance);
	}

}