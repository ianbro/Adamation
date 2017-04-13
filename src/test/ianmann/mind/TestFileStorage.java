package test.ianmann.mind;

import java.io.File;
import java.io.FileNotFoundException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.core.Constants;
import com.ianmann.mind.emotions.EmotionUnit;
import com.ianmann.mind.input.TextIdentification;
import com.ianmann.mind.storage.ShortTermMemory;
import com.ianmann.utils.utilities.JSONUtils;

public class TestFileStorage {

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		// TODO Auto-generated method stub
		Constants.readStorageVariables();
		ShortTermMemory.initialize();
		TextIdentification.initialize();

		JSONObject json;
		Neuron n = Neuron.fromJSON(new File("mind/storage/neurons/vehicle/car/taurus.nrn"));
		System.out.println(JSONUtils.formatJSONObject(n.jsonify(), 1));
	}

}
