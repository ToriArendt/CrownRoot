import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Classify {
	private ArrayList<Root> roots;
	private LogisticRegression l;
	private File saveDir;
	private String saveName;
	
	public Classify(File rootFile, File paramFile, File directory, String name) throws FileNotFoundException {
		Separation s = new Separation();
		roots = s.separateRoots(rootFile.toString());
		l = new LogisticRegression(paramFile);
		saveDir = directory;
		saveName = name;
	}
	
	public void outputRoots () {
		File crownFile = new File(saveDir,saveName+"_crown");
		File otherFile = new File(saveDir,saveName+"_other");
		double[] coefficients = l.getCoefficients();
		for (Root r: roots) {
			double[] parameterVals = r.getValues();
			double logres = 0;
			for (int i = 0; i<coefficients.length; i++) {
				logres += coefficients[i]*parameterVals[i];
			}
			if (logres > 0.5) {
				r.printTo(crownFile);
			}
			else { 
				r.printTo(otherFile); // TODO: WRITE PRINTO METHOD
			}
			
		}
	}
	
}
