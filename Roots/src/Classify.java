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
	
}
