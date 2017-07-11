/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Jul 4, 2017
 */
package com.ianmann.mind.storage.organization.basicNetwork.state;

/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Jul 4, 2017
 *
 */
public class StateSubjectAlreadySetError extends Error {

	public StateSubjectAlreadySetError(State _state) {
		super("The State neuron: " + _state.toString() + " already has a subject. A states subject cannot be changed.");
	}
}
