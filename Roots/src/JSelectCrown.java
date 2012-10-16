
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.selectable.SelectableScatter;


public class JSelectCrown extends JFrame {

	private File rFile;
	private File gFile;
	private DefaultListModel list;
	private ArrayList<Root> rSoFar;
	private ArrayList<Root> fileRoots;
	
	/**
	 * Create the application.
	 * @param listModel 
	 * @param f 
	 * @param f2 
	 * @throws FileNotFoundException 
	 */
	public JSelectCrown(File rootFile, File fileToGraph, DefaultListModel listModel, ArrayList<Root> roots) throws FileNotFoundException {
		rFile = rootFile;
		gFile = fileToGraph;
		list = listModel;
		rSoFar = roots;
		Separation s = new Separation();
		fileRoots = s.separateRoots(gFile.toString());
		
		setBounds(100, 100, 1175, 563);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		
		
		//TODO: Plot gFile and make selectable.
		
		JButton btnNext = new JButton("Next >");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.getSize() == 0) {
					JSaveLogRes saveLogRes = new JSaveLogRes(rFile, rSoFar);
					saveLogRes.setVisible(true);
					setVisible(false);
				}
				else {
					//TODO: Add roots from this file to ArrayList with characteristics
					File nextFile = new File( (String) list.remove(0));
					JSelectCrown s;
					try {
						s = new JSelectCrown(rFile, nextFile, list, rSoFar);
						s.setVisible(true);
						setVisible(false);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		btnNext.setBounds(1084, 506, 85, 29);
		getContentPane().add(btnNext);
		
		JPanel panel = new JPanel();
		
		
		
		Chart c = new Chart();
		List<AbstractDrawable> draws = new ArrayList<AbstractDrawable>();
		for (Root r: fileRoots) {
			org.jzy3d.colors.Color[] colors = new org.jzy3d.colors.Color[r.voxels.size()];
			Coord3d[] coords = new Coord3d[r.voxels.size()];
			for(int i = 0; i<coords.length; i++) {
				Coord vox =  r.voxels.get(i);
				coords[i] = new Coord3d(vox.x, vox.y, vox.z);
				colors[i] = new org.jzy3d.colors.Color(255,255,255);
			}
			
			SelectableScatter scat = new SelectableScatter(coords, colors);
			draws.add(scat);
			c.getAxeLayout().setMainColor(org.jzy3d.colors.Color.WHITE);
			c.getView().setBackgroundColor(org.jzy3d.colors.Color.BLACK);
		}
		c.getScene().add(draws);
		
		ChartLauncher.openChart(c);
//		panel.setLayout(new java.awt.BorderLayout());
//		panel.add((JComponent)c.getCanvas());
//		
//		panel.setBounds(6, 6, 1163, 496);
		getContentPane().add(panel);
		
		
		
	}
}
