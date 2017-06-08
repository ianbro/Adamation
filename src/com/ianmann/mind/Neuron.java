package com.ianmann.mind;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.ianmann.mind.core.Constants;
import com.ianmann.mind.core.navigation.Category;
import com.ianmann.mind.emotions.EmotionUnit;
import com.ianmann.mind.storage.organization.NeuronType;
import com.ianmann.mind.storage.organization.basicNetwork.AttributeStructure;
import com.ianmann.mind.storage.organization.basicNetwork.Description;
import com.ianmann.mind.storage.organization.basicNetwork.EntityStructure;
import com.ianmann.mind.storage.organization.basicNetwork.NeuralNetwork;
import com.ianmann.mind.utils.Serializer;
import com.ianmann.utils.storage.StorageManageable;
import com.ianmann.utils.utilities.Files;
import com.ianmann.utils.utilities.GeneralUtils;
import com.ianmann.utils.utilities.JSONUtils;

/**
 * Root class for all thoughts. Every thought object
 * will inherit {@code Neuron}.
 * @author kirkp1ia
 *
 */
public class Neuron extends File {
	
	/**
	 * Contains CRUD operations for the Neuron class. This class
	 * implements the interface {@link StorageManageable}.
	 */
	public static NeuronManager storage = new NeuronManager();
	
	/**
	 * Denotes the structural layout of the network of neurons
	 * connected to this neuron. Examples of this may be noun
	 * structures or noun instances.
	 */
	protected int type;
	
	/**
	 * Groups of postsynaptic dendrites. These are the connections to another
	 * Neuron. They are grouped so that the networks can be parsed. For
	 * example, one list in this list may be a collection of attributes
	 * where as another list may be a collection of abilities.
	 */
	protected ArrayList<ArrayList<NeuralPathway>> axon;
	
	/**
	 * Used by developers or other users looking into the AI
	 * to get a sense of what this neuron actually stands for.
	 * <br><br>
	 * The file containing this neuron will be called this label if
	 * it is not null.
	 */
	protected String associatedMorpheme;
	
	/**
	 * Instantiates a Neuron with the path to a file that currently
	 * contains a Neuron's data.
	 * 
	 * It is assumed that this neuron already exists in memory. This
	 * constructor merely wraps it in a Neuron class for use in the
	 * program.
	 */
	protected Neuron(String _path) {
		super(_path);
	}
	
	/**
	 * Create Neuron with an existing neuron linked to it.
	 * This takes a string that can later be used by a developer
	 * to have a sense of what this neuron represents. This does not actually save
	 * the neuron to storage. You must seperately call save() on this neuron.
	 * @param _linkedThought
	 * @param _associated
	 */
	protected Neuron(String _path, int _type, String _label) {
		super(_path);
		this.associatedMorpheme = _label;
		this.initialize(_type, _label);
	}
	
	/**
	 * Constructors should call this method to do all the final attribute initialization.
	 * @param _linkedThought
	 * @param _associated
	 * @param _label
	 */
	private void initialize(int _type, String _label) {
		this.type = _type;
		
		this.axon = new ArrayList<ArrayList<NeuralPathway>>();
		
		this.setAssociatedMorpheme(_label);
	}
	
	/**
	 * Returns the integer denoting the type of neuron that this neuron is in relation
	 * to neural network structure.
	 * @return
	 */
	public int getType() {
		return this.type;
	}
	
	/**
	 * Returns all synaptic endings related to this neuron.
	 * Use fireSynapse() on the objects to get the actual
	 * Neuron object.
	 * @return
	 */
	public ArrayList<ArrayList<NeuralPathway>> getAxon() {
		return this.axon;
	}
	
	/**
	 * Set the morpheme that is associated with this neuron.
	 * @param _morpheme
	 */
	public void setAssociatedMorpheme(String _morpheme) {
		this.associatedMorpheme = _morpheme;
	}
	
	/**
	 * Return the morpheme associated with this neuron.
	 * @return
	 */
	public String getAssociatedMorpheme() {
		return this.associatedMorpheme;
	}
	
	/**
	 * Retrieve the location to the file containing a new Neuron.
	 * <br><br>
	 * This method does the logic for deciding what to name the file.
	 * If no label is provided in the parameters, it will
	 * use an id from neuron ids file.
	 * <br><br>
	 * NOTE: _label is optional. If it is null, then the next
	 * id will be used instead of _label.
	 */
	protected static String getNewFileLocation(String _label) {
		String pathToNeurons = Constants.NEURON_ROOT;
		try {
			if (_label != null) {
				return pathToNeurons + _label + ".nrn";
			} else {
				Scanner s;
				s = new Scanner(new File(Constants.NEURON_ROOT + "ids"));
				int next = s.nextInt();
				s.close();
				PrintWriter p = new PrintWriter(new File(Constants.NEURON_ROOT + "ids"));
				p.print(next+1);
				p.close();
				return pathToNeurons + String.valueOf(next) + ".nrn";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Make new pathway to a thought. This automatically saves the changes to storage.
	 * @param _thought
	 */
	public NeuralPathway addNeuralPathway(Integer _dendriteGroup, Neuron _thought) {
		if (_thought != null) {
			NeuralPathway t = new NeuralPathway(_thought);
			this.axon.get(_dendriteGroup).add(t);
			this.save();
			return t;
		} else {
			return null;
		}
	}
	
	/**
	 * Remove the pathway to a thought. If no pathway is found at this location,
	 * nothing happens.
	 * @param _thought
	 */
	public void removeNeuralPathway(int _dendriteGroupIndex, int _indexInGroup) {
		NeuralPathway pathway = NeuralPathway.deserialize(this.axon.get(_dendriteGroupIndex).get(_indexInGroup));
		if (pathway != null) {
			if (pathway.delete()) {
				this.axon.get(_dendriteGroupIndex).remove(_indexInGroup);
				this.save();
			}
		} else {
			return;
		}
		
	}
	
	public NeuralNetwork parsed() {
		if (this.getType() == NeuronType.NOUN_DEFINITION) {
			return new EntityStructure(this);
		} else if (this.getType() == NeuronType.ATTRIBUTE) {
			return new AttributeStructure(this);
		} else if (this.getType() == NeuronType.DESCRIPTION) {
			return new Description(this);
		} else {
			return null;
		}
	}
	
	/**
	 * Print this object to the file at {@link Neuron.location}.
	 * <br><br>
	 * If the neuron file already exists, just rewrite the data
	 * in the file, overwriting the old data with the new data.
	 */
	public void save() {
		Neuron.storage.save(this);
	}
	
	/**
	 * Determines whether this neuron is the same as that in o. This is true if o's path is
	 * the same as this Neuron's path.
	 * @param o
	 * @return
	 */
	public boolean equals(Neuron o) {
		return this.getAbsolutePath().equals(o.getAbsolutePath());
	}
	
	/**
	 * Parse json data in a file into a Neuron object
	 * @param _neuronFile
	 * @return
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	private void readFile() throws FileNotFoundException, ParseException {
		JSONObject jsonNeuron = (JSONObject) Files.json(this);
		
		this.axon = new ArrayList<ArrayList<NeuralPathway>>();
		JSONArray axon = (JSONArray) jsonNeuron.get("axon");
		for (int i; i < axon.size(); i++) {
			JSONArray dendriteGroup = (JSONArray) axon.get(i);
			this.axon.add(new ArrayList<NeuralPathway>());
			for (Object path : dendriteGroup) {
				String filePath = Constants.STORAGE_ROOT + (String) path;
				this.axon.get(i).add(new NeuralPathway(filePath, false));
			}
		}
		
		this.type = (int) ((long) jsonNeuron.get("type"));
		
		if (!(jsonNeuron.get("associatedMorpheme") instanceof Long)) {
			this.associatedMorpheme = (String) jsonNeuron.get("associatedMorpheme");
		} else {
			this.associatedMorpheme = null;
		}
	}
	
	/**
	 * Return the neuron object as a json object.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject jsonify() {
		JSONObject neuronJson = new JSONObject();
		
		neuronJson.put("axon", new JSONArray());
		for (int i = 0; i < this.axon.size(); i++) {
			ArrayList<NeuralPathway> dendriteGroup = this.axon.get(i);
			((JSONArray) neuronJson.get("axon")).add(new JSONArray());
			for (NeuralPathway synapse : dendriteGroup) {
				(
					(JSONArray) ((JSONArray) neuronJson.get("axon"))
						.get(i)
				).add(synapse.getAbsolutePath().split(Constants.STORAGE_ROOT)[1]);
			}
		}
		
		neuronJson.put("type", this.type);
		
		if (this.associatedMorpheme != null) {
			neuronJson.put("associatedMorpheme", this.associatedMorpheme);
		} else {
			neuronJson.put("associatedMorpheme", 1);
		}
		
		return neuronJson;
	}
	
	public String toString() {
		String str = "<Neuron: type(" + NeuronType.mapType(this.type) + ")";
		if (!GeneralUtils.isNumeric(this.associatedMorpheme)) {
			str = str + ";label(" + this.associatedMorpheme + ")";
		}
		str = str + ">";
		return str;
	}
}

class NeuronManager implements StorageManageable<Neuron> {

	/**
	 * The parameters for this method should be treated like so:
	 * (Integer _type, String _associatedMorpheme)
	 * This is what the method expects in it's parameters.
	 * 
	 *  (non-Javadoc)
	 * @see com.ianmann.utils.storage.StorageManageable#create()
	 */
	@Override
	public Neuron create(Object... _params) {
		String location = Neuron.getNewFileLocation((String) _params[1]);
		Neuron neuron = new Neuron(
				location,
				(Integer) _params[0],
				(String) _params[1]
		);
		return neuron;
	}

	/**
	 * Print this object to the file at {@link Neuron.location}.
	 * <br><br>
	 * If the neuron file already exists, just rewrite the data
	 * in the file, overwriting the old data with the new data.
	 * 
	 * (non-Javadoc)
	 * @see com.ianmann.utils.storage.StorageManageable#save(java.lang.Object)
	 */
	@Override
	public void save(Neuron _object) {
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
	 * Removes this Neuron from memory. First, this will delete all of the
	 * NeuralPathway objects that are contained in this Neuron. Then the actual
	 * Neuron file will be deleted.
	 * 
	 * (non-Javadoc)
	 * @see com.ianmann.utils.storage.StorageManageable#delete(java.lang.Object)
	 */
	@Override
	public void delete(Neuron _object) {
		// TODO Auto-generated method stub
		for (int i = 0; i < _object.axon.size(); i++) {
			for (int j = 0; j < _object.axon.get(i).size(); j++) {
				_object.removeNeuralPathway(i, j);
			}
		}
		_object.delete();
	}

	/**
	 * DON'T USE THIS!
	 * (non-Javadoc)
	 * @see com.ianmann.utils.storage.StorageManageable#get(java.util.HashMap)
	 */
	@Override
	public ArrayList<Neuron> get(HashMap<String, Object> _params) {
		return null;
	}

	/** 
	 * DON'T USE THIS!
	 * (non-Javadoc)
	 * @see com.ianmann.utils.storage.StorageManageable#getAll()
	 */
	@Override
	public ArrayList<Neuron> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
