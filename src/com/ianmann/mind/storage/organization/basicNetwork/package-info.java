/**
 *
 */
/**
 * @author kirkp1ia
 * This package contains layouts for different neuron structures.
 * These are almost like classes in programming.
 * <br><br>
 * Adamation will organize thoughts into objects, actions, states and such.
 *<br><br>
 *	Each thought will be almost like a class in programming. It will have attributes if it's a
 *	noun. If it's an action, it will have links to example memories. state neurons will include
 *	a group of nouns, actions, who did the actions, and a focus in that state/scenery such as 
 *	a specific noun contained in the scenareo. These state neurons will be the basis of
 *	forming sentences.
 *	<br><br>
 *	ex. The neuron layout for person would look something like the following:
 *	<br><br>
 *	neuron - person
 *		attribute neurons - face, hand, leg, nose, eyes, name, tendencies, age
 *		ability neurons - run, walk, move, eat, speak, listen, think
 *		<br><br>
 *	ex. The neuron layout for an action like run will look something like the following:
 *		neurons that describe it (neurons for movement, in_a_direction, fast)
 *		neurons that represent scenareos where the focus subject of the scene ran.
 *		<br><br>
 *	ex. scenareo/state neuron for a student eating an apple will look like the following:
 *		neurons for nouns in the scene (apple, student, students_desk, <other background stuff like back wall of the room.>)
 *		neuron for actions such as eating or sitting still in the chair.
 *		neuron for the focus noun and action like student eating
 */
package com.ianmann.mind.storage.organization.basicNetwork;