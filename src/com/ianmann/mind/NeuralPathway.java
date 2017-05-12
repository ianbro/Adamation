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
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.core.Constants;
import com.ianmann.mind.utils.Serializer;
import com.ianmann.utils.utilities.Files;

public class NeuralPathway extends File implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	 * thought link. The file that this points to contains an
	 * {@code Neuron} object.
	 */
	private File recieverNeuron;
	
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
	
	public NeuralPathway(String _path, Neuron _resultThoughtFile) {
		super(_path);
		this.recieverNeuron = _resultThoughtFile;
		this.connectionSize = 0.00001;
		
		try {
			java.nio.file.Files.createFile(this.toPath());
			this.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static NeuralPathway create(Neuron _resultThoughtFile) {
		String location = getNewFileLocation();
		NeuralPathway dendrite = new NeuralPathway(location, _resultThoughtFile);
		return dendrite;
	}
	
	/**
	 * Retrieve the location to the file containing this Thought Link
	 */
	private static String getNewFileLocation() {
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
	 * Retrieve Thought from file pointed to by this link.
	 * @return
	 */
	private Neuron getNeuronFromFile() {
		try {
			return Neuron.fromJSON(this.recieverNeuron);
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
	 * Print this object to the file at {@link NeuralPathway.location}
	 */
	private void save() {
		FileOutputStream fos = null;
		try {
			byte[] serializedLink = Serializer.serialize(this);
			fos = new FileOutputStream(this.location);
			fos.write(serializedLink);
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads a file with a serialized {@link NeuralPathway} object in it
	 * and parses it into an instance of {@link NeuralPathway}
	 * @param _inputFile
	 * @return
	 */
	public static NeuralPathway deserialize(File _inputFile) {
		try {
			byte[] fileBytes = Files.readFile(_inputFile);
			NeuralPathway l = Serializer.deserialize(NeuralPathway.class, fileBytes);
			return l;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int compareTo(NeuralPathway o) {
		if (this.connectionSize > o.connectionSize) {
			return 1;
		} else if (this.connectionSize < o.connectionSize) {
			return -1;
		} else {
			return 0;
		}
	}
}
