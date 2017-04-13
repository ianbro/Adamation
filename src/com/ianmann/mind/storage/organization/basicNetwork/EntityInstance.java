package com.ianmann.mind.storage.organization.basicNetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.Neuron;

public class EntityInstance extends Neuron {
	
	/**
	 * Entity Structure that defines how the class is organized.
	 * The file contains an EntityStructure object.
	 */
	private File structure;

	/**
	 * Any attribute that this entity posesses.
	 * <br><br>
	 * Examples may be eyes, color, age, name, etc...
	 * <br><br>
	 * Each file contains a thought link to a neuron
	 */
	private ArrayList<File> attributes;
	
	public EntityInstance(EntityStructure _structure) {
		super();
//		this.structure = _structure.location;
	}
	
	public EntityStructure getStructure() {
//		try {
//			return (EntityStructure) ActionStructure.fromJSON(this.structure);
//		} catch (FileNotFoundException | ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
		return null;
	}
	
	public ArrayList<Neuron> getAttributes() {
		ArrayList<Neuron> neurons = new ArrayList<Neuron>();
		
		for (File nFile : this.attributes) {
			try {
				neurons.add(Neuron.fromJSON(nFile));
			} catch (FileNotFoundException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return neurons;
	}
	
	public void addAttribute(Neuron _attribute, Neuron _n) throws AttributeNotFoundException {
//		int indexOfAttribute = -1;
//		
//		EntityStructure structure = null;
//		try {
//			structure = (EntityStructure) EntityStructure.fromJSON(this.structure);
//		} catch (FileNotFoundException | ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		for (int i = 0; i < structure.getAttributes().size(); i ++) {
//			Neuron attribute = structure.getAttributes().get(i);
//			if (attribute.equals(_attribute)) {
//				indexOfAttribute = i;
//				break;
//			}
//		}
//		
//		if (indexOfAttribute == -1) {
//			throw new AttributeNotFoundException(_attribute);
//		} else {
//			this.attributes.add(indexOfAttribute, _n.location);
//		}
	}
	
	public void addAttribute(Neuron _attribute, File _n) throws FileNotFoundException, AttributeNotFoundException {
		try {
			Neuron toAdd = Neuron.fromJSON(_n);
			this.addAttribute(_attribute, toAdd);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
