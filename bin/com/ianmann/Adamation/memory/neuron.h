/*
 * When set to a Neurons type, this designates that that Neuron represents
 * knowledge of a noun.
 */
#define NTYPE_NOUN 1

/*
 * When set to a Neurons type, this designates that that Neuron represents
 * knowledge of an attribute.
 */
#define NTYPE_ATTR 2

/*
 * Amount of size that a NeuralPathway's size increments each time it is fired.
 */
#define SYNPS_INC_STEP 0.00001;

/*
 * Struct representing the structure of a neural pathway.
 * =============================================================================
 * This represents the connection between two neurons. The connection can
 * go both ways. It contains the following members:
 *
 * - char *flocation:
 *      Path to the file containing this NeuralPathway. This path is
 *      This path is relative to the root folder of the minds storage.
 *      This file should be in json format.
 *
 * - char *host_location:
 *      Neuron instance representing the host of the
 *      connection. This host's synapticEndings list contains a reference
 *      to this NeuralPathway.
 *        NOTE: This is just the path to the file containing the Neuron. use
 *                 the function gethost(this) to access the actual Neuron.
 * 
 * - char *receiver_location:
 *      Neuron instance that will be activated (returned)
 *      in the event of this neural pathway being fired. In laymans terms,
 *      When a Neuron A fires off a connection to another Neuron B, B is the
 *      receiver Neuron and A is the host Neuron.
 *        NOTE: This is just the path to the file containing the Neuron. use
 *                 the function getreceiver(this) to access the actual Neuron.
 *
 * - double size:
 *      Represents the size of this NeuralPathway. Larger sizes mean
 *      that the pathway is easier for the mind to follow. This allows for
 *      habit formation and personality. It also will play into the ease of
 *      remembering the receiver Neuron's memory.
 *
 * - int emotion: Emotion to be fired off in the mind when this NeuralPathway
 *      is activated. The possible values for this are defined as macros in the
 *      header file for emotions.
 *      (src/com/ianmann/Adamation/emotions/emotions.h)
 * =============================================================================
 * TODO: Create Emotion struct. This should be in another file as it does
 *          not depend on Neuron or NeuralPathway.
 */
struct NeuralPathway;
typedef struct NeuralPathway NeuralPathway;


/*
 * Struct representing the structure of a neuron.
 * =============================================================================
 * This represents a single Neuron that contains a memory. It can be any type
 * of memory from knowledge of a noun to knowledge of explanation to knowledge
 * of an event.
 *
 * - char *flocation:
 *      Path to the file containing this Neuron. This path is
 *      This path is relative to the root folder of the minds storage.
 *      This file should be in json format.
 *
 * - char *synaptic_endings_location[]:
 *      Array of NeuralPathways that are hosted by this Neuron. The host on
 *      these NeuralPathways should be referencing this Neuron. Depending on
 *      the type of this Neuron, the synaptic_endings will be formatted in
 *      a certain way.
 *        NOTE: These are just the paths to the files containing the pathways.
 *                 use the function getsynends(this) to access the actual
 *                 pathways.
 *
 * - int type:
 *      Designates the type of Neuron that this is. It may be things such as
 *      noun, verb, event, etc... The possible values are preset as macros
 *      prepended with NTYPE_*.
 * =============================================================================
 * NOTE: This struct stores the raw data for Neurons. other C files provide
 * implementation for the complex Neuron structure based on this Neuron's type.
 */
struct Neuron;
typedef struct Neuron Neuron;

/* =============================================================================
 * ======================== Interface for NeuralPathway ========================
 * ===========================================================================*/

