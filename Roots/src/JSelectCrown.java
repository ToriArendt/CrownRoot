
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.mouse.DualModeMouseSelector;
import org.jzy3d.maths.Coord3d;
/**
 * Select Crown roots from graph
 * 
 * @author Victoria Arendt
 *
 */
public class JSelectCrown extends JFrame {

	
	private static final long serialVersionUID = 3133732956785938939L;
	private File rFile;
	private File gFile;
	private DefaultListModel list;
	private ArrayList<Root> rSoFar;
	private ArrayList<Root> fileRoots;

	/**
	 * Create the application 
	 * 
	 * @param rootFile - file to classify
	 * @param fileToGraph - file graphed on this screen
	 * @param listModel - list of files to graph
	 * @param roots - roots classified
	 * @throws FileNotFoundException
	 */
	public JSelectCrown(File rootFile, File fileToGraph, DefaultListModel listModel, ArrayList<Root> roots) throws FileNotFoundException {
		rFile = rootFile;
		gFile = fileToGraph;
		list = listModel;
		rSoFar = roots;
		Separation s = new Separation();
		fileRoots = s.separateRoots(gFile.toString());

		setTitle("Select Crown Roots");
		setBounds(100, 100, 1175, 563);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);



		JPanel panel = new JPanel();

		// Next Button
		JButton btnNext = new JButton("Next >");

		// Create Chart for jzy3d
		Chart c = new Chart("swing");
		
		// Give roots their group numbers (1 root =  1 group number)
		ArrayList<Integer> rootnum = new ArrayList<Integer>();
		for (int ro = 0; ro<fileRoots.size(); ro++) {
			Root r = fileRoots.get(ro);
			for(int i = 0; i<r.voxels.size(); i++) {
				rootnum.add(ro);
			}
		}
		
		// Create coordinates and color arrays
		org.jzy3d.colors.Color[] colors = new org.jzy3d.colors.Color[rootnum.size()];
		Coord3d[] coords = new Coord3d[rootnum.size()];

		// Add all coordinates from all roots to on array.
		int next = 0;
		for (Root r: fileRoots) {
			for(int i = 0; i<r.voxels.size(); i++) {
				Coord vox =  r.voxels.get(i);
				coords[next+i] = new Coord3d(vox.x, vox.y, vox.z);
				colors[next+i] = new org.jzy3d.colors.Color(255,255,255);
			}
			next += r.voxels.size();
		}

		// Create scatter plot
		final MySelectableScatter scat = new MySelectableScatter(coords, colors, rootnum);
		scat.setHighlightColor(new org.jzy3d.colors.Color(255,0,0));
		DualModeMouseSelector selector = new DualModeMouseSelector(c,new MyScatterSelector(scat));
		c.getAxeLayout().setMainColor(org.jzy3d.colors.Color.WHITE);
		c.getView().setBackgroundColor(org.jzy3d.colors.Color.BLACK);
		c.getScene().add(scat);

		panel.setLayout(new java.awt.BorderLayout());
		panel.add((JComponent)c.getCanvas());
		panel.setBounds(6, 6, 1163, 496);
		getContentPane().add(panel);

		
		// Next button actions
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Integer> already = new ArrayList<Integer>(); 
				for (int i = 0; i<scat.getData().length; i++ ) {
					int num = scat.getGroup(i);
					if (!already.contains(num)) {
						Root r = fileRoots.get(num);
						if (scat.getHighlighted(i)) { // add highlighted to crown
							r.isCrown = true;
							rSoFar.add(r);
						}
						else {
							r.isCrown = false;
							rSoFar.add(r);
						}
						already.add(num);
					}
				}
				
				// if no more to graph, move to saving
				if (list.getSize() == 0) {
					JSaveLogRes saveLogRes = new JSaveLogRes(rFile, rSoFar);
					saveLogRes.setVisible(true);
					setVisible(false);
				}
				else { // graph next
					File nextFile = new File( (String) list.remove(0));
					JSelectCrown s;
					try {
						s = new JSelectCrown(rFile, nextFile, list, rSoFar);
						s.setVisible(true);
						setVisible(false);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}

				}

			}
		});
		btnNext.setBounds(1084, 506, 85, 29);
		getContentPane().add(btnNext);



	}
}
