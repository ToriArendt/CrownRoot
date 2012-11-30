import java.io.File;
import java.util.ArrayList;
import weka.classifiers.functions.SimpleLogistic;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;


/**
 * Creates parameters and classifier for identifying crown roots
 * 
 * @author Victoria Arendt
 *
 */
public class LogisticRegression {
	int NUMBER_COEFF = 6;
	SimpleLogistic log = new SimpleLogistic();
	String[] atts = {"area", "volume", "curvature", "angle", "attachedToSeed", "isCrown"};
	private File pFile;
	
	/**
	 * Create classifier from a training set
	 * 
	 * @param roots - training set of roots
	 * @param saveName - where to save param file
	 * @throws Exception 
	 */
	public LogisticRegression(ArrayList<Root> roots, String saveName) throws Exception{
		// Create list of attributes
		FastVector attributes  = new FastVector(NUMBER_COEFF);
		for (int i = 0; i<NUMBER_COEFF-1; i++){
			attributes.addElement(new Attribute(atts[i]));
		}
		// Create class types (crown and other) and add to attributes
		FastVector rootType = new FastVector(2);
		rootType.addElement("other");
		rootType.addElement("crown");
		attributes.addElement(new Attribute(atts[NUMBER_COEFF-1],rootType));
		
		//formalize dataset (Instances)
		Instances data = new Instances("root",attributes,roots.size());
		data.setClassIndex(data.numAttributes()-1); // class = crown, other
		
		pFile = new File(saveName);
		
		// Create an instance for each root
		for (Root r: roots) {
			double [] vals = new double[data.numAttributes()];
			for (int j = 0; j < NUMBER_COEFF; j++) {
					vals[j] = r.getValues()[j];
			}
			data.add(new Instance(1.0,vals));
		}
		
		//Build Classifier
		log.buildClassifier(data);
		
		// Save file
		weka.core.SerializationHelper.write(saveName, log);
		
		
	}
	
	/**
	 * Constructor from file
	 *  
	 * @param paramFile - file name
	 * @throws Exception
	 */
	public LogisticRegression(File paramFile) throws Exception  {
		pFile = paramFile;
		log = (SimpleLogistic) weka.core.SerializationHelper.read(paramFile.toString());
	}
	
	/**
	 * Get file name
	 * @return parameter file name
	 */
	public File getFile() {
		return pFile;
	}

	
	/**
	 * Get Classifier
	 * @return classifier (SimpleLogistic)
	 */
	public SimpleLogistic getClassifier() {
		return log;
	}




}
