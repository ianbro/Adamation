package test.ianmann.mind;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.Stimulant;
import com.ianmann.mind.core.Constants;
import com.ianmann.mind.emotions.EmotionUnit;
import com.ianmann.mind.input.TextIdentification;
import com.ianmann.mind.storage.ShortTermMemory;
import com.ianmann.mind.storage.assimilation.Assimilation;
import com.ianmann.mind.storage.assimilation.MorphemeNotFound;

public abstract class TestThoughtRelationships {

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		// TODO Auto-generated method stub
		Constants.readStorageVariables();
		ShortTermMemory.initialize();
		TextIdentification.initialize();

//		Neuron n = new Neuron(null, EmotionUnit.CONTENT, "name");
//		Neuron ne = new Neuron(n, EmotionUnit.CONTENT, "Hello");
		
//		Stimulant comStim = new Stimulant("Communication", n2);
//		Stimulant comStim = Stimulant.deserialize(new File(Constants.STIMULANT_ROOT + "Communication.stim"));
//		
//		System.out.println(comStim);
		
		try {
			Neuron n1 = TextIdentification.getNeuronForMorpheme("Hello");
			
			System.out.println(Arrays.toString(n1.getAssociatedMorphemes()));
		} catch (MorphemeNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		try {
			Neuron n3 = TextIdentification.getNeuronForMorpheme("Ian");
			Neuron n2 = TextIdentification.getNeuronForMorpheme("name");
			System.out.println(Arrays.toString(n3.getAssociatedMorphemes()));
			System.out.println(Arrays.toString(n2.getAssociatedMorphemes()));
		} catch (MorphemeNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
