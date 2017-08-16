package com.ianmann.mind;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.ianmann.mind.core.Config;
import com.ianmann.mind.exceptions.FileNotDeletedError;
import com.ianmann.mind.exceptions.NeuralNetworkTypeNotFoundError;
import com.ianmann.mind.storage.organization.NeuronType;
import com.ianmann.mind.storage.organization.basicNetwork.entity.AttributeStructure;
import com.ianmann.mind.storage.organization.basicNetwork.entity.Description;
import com.ianmann.mind.storage.organization.basicNetwork.entity.EntityStructure;
import com.ianmann.mind.storage.organization.basicNetwork.state.State;
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
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Signifies weather or not the attributes that are stored in file
	 * for this {@link Neuron} have been loaded into memory.
	 */
	private boolean loaded = false;

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
	private ArrayList<ArrayList<NeuralPathway>> axon;
	
	/**
	 * Used by developers or other users looking into the AI
	 * to get a sense of what this neuron actually stands for.
	 * <br><br>
	 * The file containing this neuron will be called this label if
	 * it is not null.
	 */
	private String associatedMorpheme;
	
	public static class NeuronManager implements StorageManageable<Neuron> {

		/**
		 * The parameters for this method should be treated like so:
		 * (Integer _type, String _associatedMorpheme)
		 * This is what the method expects in it's parameters.
		 * 
		 * @see com.ianmann.utils.storage.StorageManageable#create()
		 */
		public Neuron create(Object... _params) {
			String location = Neuron.getNewFileLocation((String) _params[1]);
			Neuron neuron = new Neuron(
					location,
					(Integer) _params[0],
					(String) _params[1]
			);
			this.save(neuron);
			return neuron;
		}

		/**
		 * Print this object to the file at this objects file path.
		 * <br><br>
		 * If the neuron file already exists, just rewrite the data
		 * in the file, overwriting the old data with the new data.
		 * 
		 * @see com.ianmann.utils.storage.StorageManageable#save(java.lang.Object)
		 */
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
		 * </p>
		 * <p>
		 * NOTE: This method calls the delete method on _object.
		 * </p>
		 * @see com.ianmann.utils.storage.StorageManageable#delete(java.lang.Object)
		 */
		public boolean delete(Neuron _object) {
			for (int i = 0; i < _object.axon.size(); i++) {
				for (int j = 0; j < _object.axon.get(i).size(); j++) {
					_object.removeNeuralPathway(i, j);
				}
			}
			return _object.delete();
		}

		/**
		 * DON'T USE THIS!
		 * @see com.ianmann.utils.storage.StorageManageable#get(java.util.HashMap)
		 */
		public ArrayList<Neuron> get(HashMap<String, Object> _params) {
			return null;
		}

		/** 
		 * DON'T USE THIS!
		 * @see com.ianmann.utils.storage.StorageManageable#getAll()
		 */
		public ArrayList<Neuron> getAll() {
			return null;
		}
		
	}
	
	/**
	 * Instantiates a Neuron with the path to a file that currently
	 * contains a Neuron's data.
	 * 
	 * It is assumed that this neuron already exists in memory. This
	 * constructor merely wraps it in a Neuron class for use in the
	 * program.
	 * 
	 * If _doReadFile is true, this constructor will read the data in
	 * the file at _path into the attributes for this Neuron.
	 * @throws ParseException 
	 * @throws FileNotFoundException 
	 */
	protected Neuron(String _pathFromNeuronRoot, boolean _doLoadAttributes) throws FileNotFoundException, ParseException {
		super(Config.NEURON_ROOT + _pathFromNeuronRoot);
		if (_doLoadAttributes) {
			this.loadAttributes();
		}
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

		this.save();
		this.loaded = true;
	}
	
	/**
	 * Returns the integer denoting the type of neuron that this neuron is in relation
	 * to neural network structure.
	 * @return
	 */
	public int getType() {
		try {
			this.loadAttributesIfNotYet();
		} catch (FileNotFoundException | ParseException e) {
			// Just means this hasn't been saved yet.
		}
		return this.type;
	}
	
	/**
	 * Set the morpheme that is associated with this neuron.
	 * @param _morpheme
	 */
	public void setAssociatedMorpheme(String _morpheme) {
		try {
			this.loadAttributesIfNotYet();
		} catch (FileNotFoundException | ParseException e) {
			// Just means it hasn't been saved to disk yet.
		}
		this.associatedMorpheme = _morpheme;
	}
	
	/**
	 * Return the morpheme associated with this neuron.
	 * @return
	 */
	public String getAssociatedMorpheme() {
		try {
			this.loadAttributesIfNotYet();
		} catch (FileNotFoundException | ParseException e) {
			// Just means it hasn't been saved to disk yet.
		}
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
		String pathToNeurons = Config.NEURON_ROOT;
		try {
			if (_label != null) {
				return pathToNeurons + _label + ".nrn";
			} else {
				Scanner s;
				s = new Scanner(new File(Config.NEURON_ROOT + "ids"));
				int next = s.nextInt();
				s.close();
				PrintWriter p = new PrintWriter(new File(Config.NEURON_ROOT + "ids"));
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
	 * @param _dendriteGroup - Group of dendrites in {@link this.axon} in which to add
	 * _thought. _thought will be added to an unknown index in this group of dendrites.
	 * @param _thought
	 */
	public NeuralPathway addNeuralPathway(Integer _dendriteGroup, Neuron _thought) {
		try {
			this.loadAttributesIfNotYet();
		} catch (FileNotFoundException | ParseException e1) {
			// Just means that it hasn't been saved yet.
		}
		if (_thought != null) {
			NeuralPathway t = NeuralPathway.storage.create(_thought);
			
			try {
			this.axon.get(_dendriteGroup).add(t);
			} catch (IndexOutOfBoundsException e) {
				
				/*
				 * Because we can't add an index that is floating out there,
				 * we need to initialize blank ArrayLists at each index
				 * before the one we're about to add.
				 * 
				 * What I mean by "floating out there" is this:
				 * If an array is {1, 2, 3}, java won't let us add a number to
				 * index 8 without adding something to indexes 3, 4, 5, 6, and 7
				 * first.
				 */
				for (int i = this.axon.size(); i <= _dendriteGroup; i++) {
					this.axon.add(i, new ArrayList<NeuralPathway>());
				}
				
				this.axon.get(_dendriteGroup).add(t);
			}
			
			this.save();
			return t;
		} else {
			return null;
		}
	}
	
	/**
	 * Make new pathway to a thought. This automatically saves the changes to storage.
	 * @param _dendriteGroup - Group of dendrites in {@link this.axon} in which to add
	 * _thought.
	 * @param _indexInGroup - The index in the group of dendrites (specified in _dendriteGroup)
	 * at which to add the link to _thought.
	 * @param _thought - The {@link Neuron} to add a {@link NeuralPathway} to link to.
	 */
	public NeuralPathway addNeuralPathway(Integer _dendriteGroup, Integer _indexInGroup, Neuron _thought) {
		try {
			this.loadAttributesIfNotYet();
		} catch (FileNotFoundException | ParseException e1) {
			// Just means that it hasn't been saved yet.
		}
		if (_thought != null) {
			NeuralPathway t = NeuralPathway.storage.create(_thought);
			
			try {
				this.axon.get(_dendriteGroup).add(_indexInGroup, t);
			} catch (IndexOutOfBoundsException e) {
				
				/*
				 * Because we can't add an index that is floating out there,
				 * we need to initialize blank ArrayLists at each index
				 * before the one we're about to add.
				 * 
				 * What I mean by "floating out there" is this:
				 * If an array is {1, 2, 3}, java won't let us add a number to
				 * index 8 without adding something to indexes 3, 4, 5, 6, and 7
				 * first.
				 */
				for (int i = this.axon.size(); i <= _dendriteGroup; i++) {
					this.axon.add(i, new ArrayList<NeuralPathway>());
				}
				
				this.axon.get(_dendriteGroup).add(_indexInGroup, t);
			}
			
			this.save();
			return t;
		} else {
			return null;
		}
	}
	
	/**
	 * Returns the {@link NeuralPathway} at the specified location in {@link this.axon}.
	 * @param _dendriteGroup - Group of dendrites in {@link this.axon} from which the
	 * {@link NeuralPathway} should be retrieved from.
	 * @param _indexInGroup - The index in the group of dendrites (specified in _dendriteGroup)
	 * at which the desired {@link NeuralPathway} is expected.
	 * @return The desired {@link NeuralPathway} specified by _dendriteGroup and
	 * _indexInGroup.
	 */
	public NeuralPathway getNeuralPathway(Integer _dendriteGroup, Integer _indexInGroup) {
		try {
			try {
				this.loadAttributesIfNotYet();
			} catch (FileNotFoundException | ParseException e1) {
				// Just means that it hasn't been saved yet.
			}
			return this.axon.get(_dendriteGroup).get(_indexInGroup);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/**
	 * Returns the list of dendrites at the given index in {@code this.axon}.
	 * @param _dendriteGroup
	 * @return The {@link ArrayList<NeuralPathway>} of dendrites or a blank
	 * {@link ArrayList<NeuralPathway>} if it doesn't exist.
	 */
	public ArrayList<NeuralPathway> getDendriteGroup(int _dendriteGroup) {
		try {
			try {
				this.loadAttributesIfNotYet();
			} catch (FileNotFoundException | ParseException e1) {
				// Just means that it hasn't been saved yet.
			}
			return this.axon.get(_dendriteGroup);
		} catch (IndexOutOfBoundsException e) {
			return new ArrayList<NeuralPathway>();
		}
	}
	
	/**
	 * Remove the pathway to a thought. If no pathway is found at this location,
	 * nothing happens. If there was a synaptic connection that was successfuly
	 * removed, that connections receiver {@link Neuron} is returned.
	 * @param _thought
	 */
	public Neuron removeNeuralPathway(int _dendriteGroupIndex, int _indexInGroup) {
		try {
			this.loadAttributesIfNotYet();
		} catch (FileNotFoundException | ParseException e1) {
			// Just means that it hasn't been saved yet.
		}
		NeuralPathway pathway = this.getNeuralPathway(_dendriteGroupIndex, _indexInGroup);
		if (pathway != null) {
			Neuron receiverNeuron = pathway.fireSynapse();
			if (NeuralPathway.storage.delete(pathway)) {
				this.axon.get(_dendriteGroupIndex).remove(_indexInGroup);
				this.save();
				return receiverNeuron;
			} else {
				throw new FileNotDeletedError(pathway.getAbsolutePath());
			}
		} else {
			return null;
		}
	}
	
	/**
	 * <p>
	 * Removes the {@link Neuron} at the given location and then replaces it with
	 * a {@link NeuralPathway} to the specified {@link Neuron}.
	 * </p>
	 * <p>
	 * If the given {@link Neuron} is already connected at the given location, then
	 * nothing will happen and the {@link NeuralPathway} that is currently at this
	 * location will be returned.
	 * </p>
	 * @param _dendriteGroupIndex
	 * @param _indexInGroup
	 * @param _neuron
	 * @return
	 */
	public NeuralPathway replaceNeuralPathway(int _dendriteGroupIndex, int _indexInGroup, Neuron _neuron) {
		try {
			this.loadAttributesIfNotYet();
		} catch (FileNotFoundException | ParseException e1) {
			// Just means that it hasn't been saved yet.
		}
		NeuralPathway pathway = this.getNeuralPathway(_dendriteGroupIndex, _indexInGroup);
		
		// If pathway is null, relatedNeuron should be too. Otherwise,
		// relatedNeuron should be pathway.fireSynapse()
		Neuron relatedNeuron = pathway != null ? pathway.fireSynapse() : null;
		
		// If relatedNeuron is not null from the resulting expression above,
		// then remove the link in order to replace it (unless the neuron
		// we're trying to replace is the same as the one it's already pointing
		// to).
		// 
		// If it is null, then we know it's not pointing to anything so we
		// aren't technically replacing anything. So don't remove anything.
		if (relatedNeuron != null && !relatedNeuron.equals(_neuron)) {
			this.removeNeuralPathway(_dendriteGroupIndex, _indexInGroup);
		}
		
		// If relatedNeuron is null or there's no difference between the neuron
		// we are adding and the one we're replacing.
		if (relatedNeuron == null || !_neuron.equals(relatedNeuron)) {
			return this.addNeuralPathway(_dendriteGroupIndex, _indexInGroup, _neuron);
		} else {
			return pathway;
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
	 * <p>
	 * Determines whether this neuron is the same as that in o. This is true if o's path is
	 * the same as this Neuron's path.
	 * </p>
	 * <p>
	 * If o is null, false is returned because "this" definately isn't null.
	 * </p>
	 * @param o
	 * @return
	 */
	public boolean equals(Neuron o) {
		return o == null ? null : this.getAbsolutePathForwardSlash().equals(o.getAbsolutePathForwardSlash());
	}
	
	/**
	 * <p>
	 * Parse json data in this Neurons file into this objects attributes.
	 * This will overwrite any changes that have been made to this object
	 * that have not been saved via {@link Neuron#save()}.
	 * </p>
	 * <p>
	 * When loading the axon, this method uses the NeuralPathway
	 * constructor that takes a path (String) and the boolean
	 * doLoadAttributes. This is to keep the program from loading
	 * every Neuron and NeuralPathway at once. The loading stops at this
	 * Neuron.
	 * </p>
	 * <p>
	 * NOTE:
	 * This should use the raw attributes rather than getters/setters and
	 * other accessers. The reason is because these accessers will usually
	 * call loadAttributes() if this.loaded is false. This would cause
	 * a max recursion error.
	 * </p>
	 * @param _neuronFile
	 * @return
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public void loadAttributes() throws FileNotFoundException, ParseException {
		JSONObject jsonNeuron = (JSONObject) Files.json(this);
		
		this.axon = new ArrayList<ArrayList<NeuralPathway>>();
		JSONArray axon = (JSONArray) jsonNeuron.get("axon");
		for (int i = 0; i < axon.size(); i++) {
			JSONArray dendriteGroup = (JSONArray) axon.get(i);
			this.axon.add(new ArrayList<NeuralPathway>());
			for (Object pathway : dendriteGroup) {
				String filePath = (String) pathway;
				this.axon.get(i).add(new NeuralPathway(filePath, false));
			}
		}
		
		this.type = (int) ((long) jsonNeuron.get("type"));
		
		if (!(jsonNeuron.get("associatedMorpheme") instanceof Long)) {
			this.associatedMorpheme = (String) jsonNeuron.get("associatedMorpheme");
		} else {
			this.associatedMorpheme = null;
		}
		
		this.loaded = true;
	}
	
	/**
	 * <p>
	 * Wraps a File that represents a {@link Neuron} in the correct {@link NeuralNetwork}
	 * type according to the "type" key in the Files JSON.
	 * </p>
	 * @param _path
	 * @param _doLoadAttributes
	 * @return
	 */
	public static Neuron networkFromNeuronFile(String _path, boolean _doLoadAttributes) {

		try {
			JSONObject jsonNeuron = (JSONObject) Files.json(new Neuron(_path, false));
		
			int type = (int) ((long) jsonNeuron.get("type"));
			
			switch (type) {
			case NeuronType.ATTRIBUTE:
					return new AttributeStructure(_path, _doLoadAttributes);
			case NeuronType.DESCRIPTION:
				return new Description(_path, _doLoadAttributes);
			case NeuronType.NOUN_DEFINITION:
				return new EntityStructure(_path, _doLoadAttributes);
			case NeuronType.NOUN_INSTANCE:
				return new EntityStructure(_path, _doLoadAttributes);
			case NeuronType.STATE:
				return new State(_path, _doLoadAttributes);
			default:
				throw new NeuralNetworkTypeNotFoundError(type, _path);
			}
		} catch (FileNotFoundException | ParseException e) {
			// TODO Auto-generated catch block
			throw new NeuralNetworkTypeNotFoundError(_path, e);
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
			ArrayList<NeuralPathway> dendriteGroup = this.getDendriteGroup(i);
			((JSONArray) neuronJson.get("axon")).add(new JSONArray());
			for (NeuralPathway synapse : dendriteGroup) {
				(
					(JSONArray) ((JSONArray) neuronJson.get("axon"))
						.get(i)
				).add(synapse.getAbsolutePathForwardSlash().split(Config.PATHWAY_ROOT)[1]);
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
	
	/**
	 * Ensures that this {@link Neuron} has been loaded into memory via
	 * {@link Neuron#loadAttributes()}. If it hasn't been, this will
	 * call that method.
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public void loadAttributesIfNotYet() throws FileNotFoundException, ParseException {
		if (!this.loaded) {
			this.loadAttributes();
		}
	}
	
	/**
	 * Returns the path starting from the path to the folder containing
	 * all Neuron files (not including that folder name).
	 * @return
	 */
	protected String getPathFromNeuronRoot() {
		String absolutePathForwardSlash = this.getAbsolutePathForwardSlash();
		String[] pathSplit = absolutePathForwardSlash.split(Config.NEURON_ROOT);
		return pathSplit[1];
	}
	
	/**
	 * Returns the absolute path to this {@link Neuron}s file but with forward slashes.
	 * @return
	 */
	public String getAbsolutePathForwardSlash() {
		return this.getAbsolutePath().replaceAll("\\\\", "/");
	}
	
	public String toString() {
		if (this.loaded) {
			String str = "<Neuron: location(" + this.getName().replace(".nrn", "") + ";type(" + NeuronType.mapType(this.type) + ")";
			if (!GeneralUtils.isNumeric(this.associatedMorpheme)) {
				str = str + ";label(" + this.associatedMorpheme + ")";
			}
			str = str + ">";
			return str;
		} else {
			return "<Neuron: location(" + this.getName().replace(".nrn", "") + ");[NOT LOADED]>";
		}
	}
}
