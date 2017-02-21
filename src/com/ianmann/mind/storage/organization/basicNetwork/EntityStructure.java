package com.ianmann.mind.storage.organization.basicNetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.parser.ParseException;

import com.ianmann.database.orm.QuerySet;
import com.ianmann.database.orm.queries.scripts.WhereCondition;
import com.ianmann.database.utils.exceptions.ObjectNotFoundException;
import com.ianmann.mind.NeuralPathway;
import com.ianmann.mind.Neuron;
import com.ianmann.mind.NeuronType;
import com.ianmann.mind.core.Constants;
import com.ianmann.mind.emotions.EmotionUnit;

/**
 * We will use this class as a wrapper for a neurons entity structure definition. The neuron
 * will instantiate this every time that it needs to be evaluated as a neuron of this type.
 * @author kirkp1ia
 *
 */
public class EntityStructure {

	/**
	 * The iterator for the attribute that are associated with this entity structure.
	 * <br><br>
	 * Examples may be height, color, age, name, etc...
	 */
	public Iterator<AttributeField> iterateAttributes() {
		final ArrayList<NeuralPathway> list = this.getAttributes().asArrayList();
		Iterator<AttributeField> iter = new Iterator<AttributeField>() {

			int i = 0;
			
			@Override
			public boolean hasNext() {
				if (i < list.size()) {
					this.i ++;
					return true;
				} else {
					this.i = 0;
					return false;
				}
			}

			@Override
			public AttributeField next() {
				// TODO Auto-generated method stub
				try {
					return new AttributeField(list.get(i).fireSynapse());
				} catch (ObjectNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		};
		return iter;
	}
	
	/**
	 * Any attribute that are associated with this entity structure.
	 * <br><br>
	 * Examples may be height, color, age, name, etc...
	 */
	public QuerySet<NeuralPathway> getAttributes() {
		return this.neuron.synaptic_connections.filter(new ArrayList<WhereCondition>(){{
			add(new WhereCondition("type", WhereCondition.EQUALS, NeuronType.ATTRIBUTE));
		}});
	}
	
	/**
	 * The iterator for the breakdown definition of this entity structure.
	 * <br><br>
	 * Examples may be legs, eyes, face, etc...
	 */
	public Iterator<EntityStructure> iterateBreakdown() {
		final ArrayList<NeuralPathway> list = this.getBreakdown().asArrayList();
		Iterator<EntityStructure> iter = new Iterator<EntityStructure>() {

			int i = 0;
			
			@Override
			public boolean hasNext() {
				if (i < list.size()) {
					this.i ++;
					return true;
				} else {
					this.i = 0;
					return false;
				}
			}

			@Override
			public EntityStructure next() {
				// TODO Auto-generated method stub
				try {
					return new EntityStructure(list.get(i).fireSynapse());
				} catch (ObjectNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		};
		return iter;
	}
	
	/**
	 * The breakdown definition of this entity structure.
	 * <br><br>
	 * Examples may be legs, eyes, face, etc...
	 */
	public QuerySet<NeuralPathway> getBreakdown() {
		return this.neuron.synaptic_connections.filter(new ArrayList<WhereCondition>(){{
			add(new WhereCondition("receiver.type", WhereCondition.EQUALS, NeuronType.NOUN_DEFINITION));
		}});
	}
	
	/**
	 * Available actions that can be done by this entity.
	 * <br><br>
	 * Examples may be walk, eat, move, contain
	 */
	public ArrayList<ActionInstance> getActions;
	
	/**
	 * The iterator for the breakdown definition of this entity structure.
	 * <br><br>
	 * Examples may be legs, eyes, face, etc...
	 */
	public Iterator<EntityStructure> iterateParentDefinition() {
		final ArrayList<NeuralPathway> list = this.getBreakdown().asArrayList();
		Iterator<EntityStructure> iter = new Iterator<EntityStructure>() {

			int i = 0;
			
			@Override
			public boolean hasNext() {
				if (i < list.size()) {
					this.i ++;
					return true;
				} else {
					this.i = 0;
					return false;
				}
			}

			@Override
			public EntityStructure next() {
				// TODO Auto-generated method stub
				try {
					return new EntityStructure(list.get(i).fireSynapse());
				} catch (ObjectNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		};
		return iter;
	}
	
	/**
	 * The breakdown definition of this entity structure.
	 * <br><br>
	 * Examples may be legs, eyes, face, etc...
	 */
	public QuerySet<NeuralPathway> getParentDefinition() {
		return this.neuron.parent_neurons.all();
	}
	
	/**
	 * The neuron that is represented by this wrapper class. This is where the actual
	 * synaptic connections are stored.
	 */
	public Neuron neuron;
	
	public EntityStructure(Neuron _neuron) {
		this.neuron = _neuron;
	}
	
	public EntityStructure() {
		super();
	}
	
	public void addAttribute(Neuron _n) {
		this.attributes.add(_n.location);
	}
	
	public void addAttribute(File _n) {
		this.attributes.add(_n);
	}
	
	public ArrayList<ActionStructure> getActions() {
		ArrayList<ActionStructure> neurons = new ArrayList<ActionStructure>();
		
		for (File nFIle : this.actions) {
			try {
				neurons.add((ActionStructure) Neuron.parse(nFIle));
			} catch (FileNotFoundException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return neurons;
	}
	
	public void addAction(ActionStructure _n) {
		this.actions.add(_n.location);
	}
	
	public void addAction(File _n) {
		this.actions.add(_n);
	}
}
