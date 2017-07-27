/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Jul 4, 2017
 */
package com.ianmann.mind.storage.organization.basicNetwork.state;

import java.io.FileNotFoundException;

import org.json.simple.parser.ParseException;

import com.ianmann.mind.NeuralPathway;
import com.ianmann.mind.storage.organization.NeuronType;
import com.ianmann.mind.storage.organization.basicNetwork.NeuralNetwork;
import com.ianmann.mind.storage.organization.basicNetwork.entity.AttributeNotFoundException;
import com.ianmann.mind.storage.organization.basicNetwork.entity.AttributeStructure;
import com.ianmann.mind.storage.organization.basicNetwork.entity.Description;
import com.ianmann.mind.storage.organization.basicNetwork.entity.EntityInstance;

/**
 * @TODO: Implement Previous Neuron and document.
 * - Override methods in {@link EntityInstance} to factor in this being a state.
 * - Redo documentation. This no longer contains the subject... this IS the subject.
 * <p>
 * Represents the state of an entity. This contains the {@link EntityInstance} for
 * which this state is and the attributes and breakdowns that exist on the {@link State#subject}.
 * </p>
 * <p>
 * There may be many states for a single entity. This is key to the AI's perception of
 * time and actions. at one point in time, a given {@link EntityInstance} may have a
 * height {@link AttributeStructure} or 5'11" but later have a height of 6'1". Each
 * points in time can be represented by states.
 * </p>
 * <p>
 * Each {@link State#subject} should only contain attributes and breakdowns that
 * actually matter for that point in time.
 * </p>
 * <p>
 * The time between two states is deemed to be in the {@link State} of the one that is
 * first on the timeline. Therefore, a {@link State} {@link Neuron} can also represent a
 * change in state.
 * </p>
 * @author Ian
 * Created: Jul 4, 2017
 *
 */
public class State extends EntityInstance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * <p>
	 * The path to the root representation of the entity for which this {@link State}
	 * stores information.
	 * </p>
	 * <p>
	 * <strong>_subjectsRoot vs subject</strong>
	 * </p>
	 * <p>
	 * These two are not the same. _subjectsRoot holds all knowledge of the
	 * entity which this {@link State} is for. The subject is essentially a
	 * copy of _subjectRoot that only holds information for the entity that
	 * pertains to this {@link State}. The both represent the same entity,
	 * but they serve different purposes.
	 * </p>
	 */
	private static final int[] PATH_TO_SUBJECTS_ROOT = {8,1};
	
	/**
	 * Path that links this {@link State} to the {@link TimelineEvent} that that contains
	 * the rest of the history for this {@link State}s subject.
	 */
	private static final int[] PATH_TO_TIMELINE_EVENT = {9,0};

	/**
	 * <p>
	 * Loads a given {@link State} from a file at the given {@code _path} and wraps it
	 * in a {@link State} object.
	 * </p>
	 * @param _path - The path to the file at which the {@link State} is stored.
	 * @param _doLoadAttributes - Designates whether or not the attributes in this objects
	 * file should be loaded into this class.
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public State(String _path, boolean _doLoadAttributes) throws FileNotFoundException, ParseException {
		super(_path, _doLoadAttributes);
	}
	
	/**
	 * <p>
	 * Creates and saves a new state for the given {@code _subjectsRoot}.
	 * </p>
	 * @param _path
	 * @param _subjectsRoot - The root {@link EntityInstance} that this {@link State}
	 * will be for. See note above on {@link State#PATH_TO_SUBJECTS_ROOT} for more info.
	 * @param _label
	 */
	public State(String _path, EntityInstance _subjectsRoot) {
		super(
			_path, NeuronType.STATE,
			(_subjectsRoot.getAssociatedMorpheme() != null ? _subjectsRoot.getAssociatedMorpheme() + "_STATE" : null),
			(_subjectsRoot.getStructure() != null ? _subjectsRoot.getStructure() : null)
		);
		this.initializeSubject(_subjectsRoot);
	}
	
	private void initializeSubject(EntityInstance _subjectsRoot) {
		this.addNeuralPathway(State.PATH_TO_SUBJECTS_ROOT[0], State.PATH_TO_SUBJECTS_ROOT[1], _subjectsRoot);
		_subjectsRoot.addStateToTimeline(this);
	}
	
	public NeuralPathway getSubjectsRoot() {
		NeuralPathway pathway = this.getNeuralPathway(State.PATH_TO_SUBJECTS_ROOT[0], State.PATH_TO_SUBJECTS_ROOT[1]);
		return pathway;
	}
	
	@Override
	public void addDescription(Description _description) throws AttributeNotFoundException {
		super.addDescription(_description);
		EntityInstance subjectsRoot = (EntityInstance) this.getSubjectsRoot().fireSynapse();
		subjectsRoot.addDescription(_description);
	}
	
	@Override
	public void addBreakdownInstance(EntityInstance _breakdownInstance) throws AttributeNotFoundException  {
		super.addBreakdownInstance(_breakdownInstance);
		EntityInstance subjectsRoot = (EntityInstance) this.getSubjectsRoot().fireSynapse();
		subjectsRoot.addBreakdownInstance(_breakdownInstance);
	}
	
	@Override
	public void addPossession(EntityInstance _instance) {
		super.addPossession(_instance);
		EntityInstance subjectsRoot = (EntityInstance) this.getSubjectsRoot().fireSynapse();
		subjectsRoot.addPossession(_instance);
	}
}
