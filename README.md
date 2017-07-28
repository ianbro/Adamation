# Adamation
AI designed for conversation with emotional reactions

	The goal of this project is to create an artificial intelligence that mimics the human mind as much as possible. For instance, the human mind has the ability to associate thoughts with one another in the form of neurons and the synaptic links between these neurons. In the same way, This program has thought objects that link to each-other via links which represent synaptic links. When the mind starts a thought process, the synaptic links fire and the mind travels through the link of thoughts to reach a conclusion or response.
	
	The Adamation will have a folder containing it's long term memory. It will also have a smaller place for short term memory. This is essentially the minds RAM. A processor works between these two to make sense of the short term memory and place it into long term memory.
	
	Adamation design umls can be found in the design folder of this project. Style preferences are found in design/written_thoughts/style_preferences.txt
	
## Running Adamation
### Development
1. Install eclipse, java and the java sdk
	a. https://www.eclipse.org/downloads/
	b. https://java.com/en/
	c. http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html (requires Java SE 1.7 or later. You can probably get away with an earlier version but I am developing on 1.7)
	
2. Install the maven plugin in the Eclipse Market Place. https://stackoverflow.com/questions/8620127/maven-in-eclipse-step-by-step-installation

3. Refresh maven to download and install needed software. https://stackoverflow.com/questions/2555845/how-to-update-maven-repository-in-eclipse

4. Ensure that your project folder "Adamation" contains a folder called "mind" with the following structure:
	- mind
		- storage
			- neurons
				- ids
					- This file (ids) must only have a single number in it. Start with 1
			- pathways
				- ids
					- This file (ids) must only have a single number in it. Start with 1
					
This folder (mind) contains the neurons, neural pathways and other information that Adamation creates and saves.

5. There is no actual main yet but I have a couple that test certain aspects of the code. One is "https://github.com/ianbro/Adamation/blob/master/src/test/ianmann/mind/TestNounsAndAttributes.java". This will test relationships between neurons to create noun definitions.

Commenting out lines 22 - 28 and uncommenting line 21 will create a definition of a Person (Make sure it's not already created in mind folder. Then, with that created, uncomment line 22 and re comment line 21. Run this and it will instantiate a person and save them as a neural network. Then running line 23 will instantiate a second person. Lines 25 - 28 will alter one of those people.

### Production
Coming soon! (more like a long time from now, let's be honest.)
	
## Resources
	https://www.youtube.com/watch?v=nalDtxyAuU8
		- Focuses on non-memory neurons (body control)
		- Crash course on structure of a neuron, synaptic connections and nerves.
	https://www.youtube.com/watch?v=VitFvNvRIIY
		- Talks about neurotransmitters
