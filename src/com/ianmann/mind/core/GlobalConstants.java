/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Aug 2, 2017
 */
package com.ianmann.mind.core;

import com.ianmann.mind.NeuralPathway;

/**
 * @TODO: TODO
 * 
 * <p>
 * Contains constant variables used throughout the AI. These
 * variables are not configurable and are final.
 * </p>
 *
 * @author Ian
 * Created: Aug 2, 2017
 *
 */
public final class GlobalConstants {

	/**
	 * <p>
	 * Size that the {@link NeuralPathway} needs to be in order for it
	 * successfully be fired. There will also be a random number generated
	 * to add more of a dynamic affect to this.
	 * </p>
	 * <p>
	 * This number will be compared against the {@link NeuralPathway}s
	 * connection resistance factor. If this is larger than that, then
	 * the connection will fire.
	 * </p>
	 * <p>
	 * In order to acheive this, the connection will have multiple steps.
	 * Each step will increase the resistance by a fixed amount (TODO: this
	 * amount should be in this class of constants as well.) and if the
	 * resistance gets to be greater than this number, then we know that
	 * the resistance is higher than this number.
	 * </p>
	 */
	public final static double NEURON_FIRING_FACTOR = 0.000003;
}
