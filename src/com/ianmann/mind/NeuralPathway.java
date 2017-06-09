package com.ianmann.mind;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.ianmann.mind.core.Constants;
import com.ianmann.utils.storage.StorageManageable;
import com.ianmann.utils.utilities.Files;
import com.ianmann.utils.utilities.JSONUtils;

public class NeuralPathway extends File {
	
	/**
	 * Contains CRUD operations for the NeuralPathway class. This class
	 * implements the interface {@link StorageManageable}.
	 */
	public static NeuralPathwayManager storage = new NeuralPathwayManager();
	
	/**
	 * double used to represent size of pathway. If the value of this
	 * is large, then the AI will follow this pathway over another
	 * smaller connection.
	 */
	private double connectionSize;
	/**
	 * The amount of size that {@code NeuralPathway.connectionSize}
	 * goes up or down by.
	 */
	private static final double INCREMENTATION_STEP = 0.00001;

	/**
	 * This will be returned when the AI processor accesses this
	 * thought link and calls {@link this.FireSynapse()}.
	 */
	private Neuron recieverNeuron;
	
	/**
	 * Comparator object for comparing two NeuralPathway objects. This allows arrays of NeuralPathway
	 * objects to be sorted.
	 */
	public static Comparator<NeuralPathway> neuralPathwayComparator = new Comparator<NeuralPathway>() {

		@Override
		public int compare(NeuralPathway o1, NeuralPathway o2) {
			if (o1.connectionSize > o2.connectionSize) {
				return 1;
			} else if (o1.connectionSize < o2.connectionSize) {
				return -1;
			} else {
				return 0;
			}
		}
	};
	
	/**
	 * Instantiates a NeuralPathway with the path to a file that currently
	 * contains a NeuralPathway instances data.
	 * 
	 * It is assumed that this pathway already exists in memory. This
	 * constructor merely wraps it in a NeuralPathway class for use in the
	 * program.
	 * 
	 * If _doReadFile is true, this constructor will read the data in
	 * the file at _path into the attributes for this NeuralPathway.
	 * @throws ParseException 
	 * @throws FileNotFoundException 
	 */
	protected NeuralPathway(String _path, boolean _doLoadAttributes) throws FileNotFoundException, ParseException {
		super(_path);
		if (_doLoadAttributes) {
			this.loadAttributes();
		}
	}
	
	/**
	 * <p>
	 * Creates a NeuralPathway instance that connects to the Neuron in _resultThoughtFile.
	 * The NeuralPathway will be stored at _path.
	 * </p>
	 * <p>
	 * The connection size is set to the default: 0.00001.
	 * </p>
	 * <p>
	 * This constructor calls the save method for this NeuralPathway.
	 * </p>
	 * @param _path
	 * @param _resultThoughtFile
	 */
	protected NeuralPathway(String _path, Neuron _resultThoughtFile) {
		super(_path);
		this.recieverNeuron = _resultThoughtFile;
		this.connectionSize = 0.00001;
		this.save();
	}
	
	/**
	 * Retrieve the location to the file containing this Thought Link. The new
	 * location will always be named with the next highest id, using the file
	 * in the NeuralPathway storage root folder.
	 */
	protected static String getNewFileLocation() {
		Scanner s;
		try {
			s = new Scanner(new File(Constants.PATHWAY_ROOT + "ids"));
			int next = s.nextInt();
			s.close();
			PrintWriter p = new PrintWriter(new File(Constants.PATHWAY_ROOT + "ids"));
			p.print(next+1);
			p.close();
			return Constants.PATHWAY_ROOT + String.valueOf(next) + ".tlink";
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * <p>
	 * Retrieve Thought from file pointed to by this link.
	 * </p>
	 * <p>
	 * This method calls the loadAttributes method on the Neuron
	 * before returning it.
	 * </p>
	 * @see com.ianmann.mind.Neuron#loadAttributes()
	 * @return The neuron that this NeuralPathway links to.
	 */
	private Neuron getNeuronFromFile() {
		try {
			this.recieverNeuron.loadAttributes();
			return this.recieverNeuron;
		} catch (FileNotFoundException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Activate this link and retrieve the thought
	 * that is linked by this. This also increments
	 * the size of the synaptic path.
	 * @return
	 */
	public Neuron fireSynapse() {
		this.connectionSize += NeuralPathway.INCREMENTATION_STEP;
		this.save();
		return this.getNeuronFromFile();
	}
	
	/**
	 * <p>
	 * Print this object to the file at this objects file path.
	 * </p>
	 * <p>
	 * If the pathway file already exists, just rewrite the data
	 * in the file, overwriting the old data with the new data.
	 * </p>
	 */
	private void save() {
		NeuralPathway.storage.save(this);
	}
	
	/**
	 * Returns the path to the file containing this NeuralPathway objects
	 * data. The path will start after the folder that contains all of the
	 * NeuralPathway files.
	 * @return
	 */
	protected String getPathFromPathwayRoot() {
		return this.getAbsolutePath().split(Constants.PATHWAY_ROOT)[1];
	}
	
	/**
	 * <p>
	 * Parse json data in this NeuralPathways file into this objects attributes.
	 * </p>
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	protected void loadAttributes() throws FileNotFoundException, ParseException {
		JSONObject jsonNeuralPathway = (JSONObject) Files.json(this);
		
		this.connectionSize = (double) jsonNeuralPathway.get("connectionSize");
		
		this.recieverNeuron = new Neuron((String) jsonNeuralPathway.get("recieverNeuron"), false);
	}
	
	/**
	 * Returns a json object that contains the properties for this instance
	 * of NeuralPathway.
	 * @return
	 */
	protected JSONObject jsonify() {
		JSONObject jsonNeuralPathway = new JSONObject();
		
		jsonNeuralPathway.put("connectionSize", this.connectionSize);
		
		jsonNeuralPathway.put("recieverNeuron", this.recieverNeuron.getPathFromNeuronRoot());
		
		return jsonNeuralPathway;
	}
}

class NeuralPathwayManager implements StorageManageable<NeuralPathway> {

	/**
	 * <p>
	 * Creates a new NeuralPathway to the given Neuron.
	 * </p>
	 * <p>
	 * This method expects one parameter: the Neuron object to connect to.
	 * </p>
	 * @see com.ianmann.utils.storage.StorageManageable#create(java.lang.Object[])
	 */
	@Override
	public NeuralPathway create(Object... _params) {
		String location = NeuralPathway.getNewFileLocation();
		NeuralPathway dendrite = new NeuralPathway(location, (Neuron) _params[0]);
		return dendrite;
	}

	/**
	 * <p>
	 * Print this object to the file at this objects file path.
	 * </p>
	 * <p>
	 * If the pathway file already exists, just rewrite the data
	 * in the file, overwriting the old data with the new data.
	 * </p>
	 * @see com.ianmann.utils.storage.StorageManageable#save(java.lang.Object)
	 */
	@Override
	public void save(NeuralPathway _object) {
		try {
			try {
				if (!_object.exists()) {
					java.nio.file.Files.createFile(_object.toPath());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			PrintWriter objWriter = new PrintWriter(_object);
			objWriter.print(JSONUtils.formatJSON(_object.jsonify(), 0));
			objWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Removes the file that contains this NeuralPathway.
	 * 
	 * NOTE: This method calls the delete method on _object.
	 * @see com.ianmann.utils.storage.StorageManageable#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(NeuralPathway _object) {
		// TODO Auto-generated method stub
		return _object.delete();
	}

	/**
	 * DON'T USE THIS!
	 * @see com.ianmann.utils.storage.StorageManageable#get(java.util.HashMap)
	 */
	@Override
	public ArrayList<NeuralPathway> get(HashMap<String, Object> _params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * DON'T USE THIS!
	 * @see com.ianmann.utils.storage.StorageManageable#getAll()
	 */
	@Override
	public ArrayList<NeuralPathway> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
