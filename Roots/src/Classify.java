import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import weka.classifiers.functions.SimpleLogistic;
import weka.core.Instance;
/** 
 * Takes root file, parameter file, and save location
 *  and outputs the name_crown.txt and name_other.txt files.
 *  
 * @author Victoria Arendt
 *
 */

public class Classify {
	private ArrayList<Root> roots;
	private LogisticRegression l;
	private File saveDir;
	private String saveName;
	public ArrayList<Root> crownRoots;
	public ArrayList<Root> otherRoots;
	private SimpleLogistic cla;

	/**
	 * Constructor Method from param file
	 * 
	 * @param rootFile - 3D reconstruction output from root system to be classified
	 * @param paramFile - Weka output file corresponding to selected parameters
	 * @param directory - Directory for saving output files
	 * @param name - prefix of output file names (will be name_crown and name_other)
	 * @throws Exception - exception thrown if file not found
	 */
	public Classify(File rootFile, File paramFile, File directory, String name) throws Exception {
		// Separate roots from rootFile
		Separation s = new Separation();
		roots = s.separateRoots(rootFile.toString());

		// Create Logistic Regession from paramFile
		l = new LogisticRegression(paramFile);
		cla = l.getClassifier();

		//Store information about where to save files
		saveDir = directory;
		saveName = name;

		// Split crown vs. other
		splitRootTypes();
	}

	/**
	 * 
	 * @param rootFile - 3D reconstruction output from root system to be classified
	 * @param l - logistic regression object classifier
	 * @param directory - Directory for saving output files
	 * @param name - prefix of output file names (will be name_crown and name_other)
	 * @throws Exception - exception thrown if file not found
	 */
	public Classify(File rootFile, LogisticRegression l, File directory, String name) throws Exception {
		// Separate roots from rootFile
		Separation s = new Separation();
		roots = s.separateRoots(rootFile.toString());

		//Store information about where to save files
		saveDir = directory;
		saveName = name;
		
		// Save classifier
		cla = l.getClassifier();
		
		// Split crown vs. other
		splitRootTypes();

	}

	/**
	 * This method determines if each root from the rootFile is a crown or other root and 
	 * appends it to the correct file.
	 * 
	 * @throws Exception - if file not found
	 */
	public void outputRoots() throws Exception {
		// Create file and their corresponding writers
		File crownFile = new File(saveDir,saveName+"_crown.txt");
		BufferedWriter outC = new BufferedWriter(new FileWriter(crownFile, true));
		File otherFile = new File(saveDir,saveName+"_other.txt");
		BufferedWriter outO = new BufferedWriter(new FileWriter(otherFile, true));

		// write crown roots
		for (Root r: crownRoots) {
			r.printTo(outC);
		}

		// write other roots
		for (Root r: otherRoots) {
			r.printTo(outO);
		}
	}

	/**
	 * Splits roots into crown or other based on classifier
	 * 
	 * @throws Exception if error with classifier
	 */
	public void splitRootTypes() throws Exception {
		ArrayList<Root> crown = new ArrayList<Root>();
		ArrayList<Root> other = new ArrayList<Root>();
		for (Root r: roots) {
			if (isCrown(r)) {
				crown.add(r);
			}
			else{
				other.add(r);
			}
		}
		crownRoots = crown;
		otherRoots = other;
	}

	/**
	 * Determines if a root is a crown root
	 * 
	 * @param r - root
	 * @return true if crown
	 * @throws Exception if error in classifier
	 */
	public boolean isCrown(Root r) throws Exception {
		// get probabilities for root instance
		double[] vals = cla.distributionForInstance(new Instance(1,r.getValues()));
		
		// vals[1] is probability crown root
		if (vals[1]>vals[0]) {
			return true;
		}
		return false;
	}

}
