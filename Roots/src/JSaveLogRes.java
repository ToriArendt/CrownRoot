import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class JSaveLogRes extends JFrame {
	private File rootFile;
	private ArrayList<Root> roots;
	private JTextField textField;
	private File paramFile;


	public JSaveLogRes(File rFile, ArrayList<Root> rSoFar) {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(23, 122, 313, 31);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.setBounds(334, 123, 89, 30);
		getContentPane().add(btnBrowse);
		
		  btnBrowse.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        JFileChooser fileChooser = new JFileChooser();

		        // For File
		        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		        fileChooser.setAcceptAllFileFilterUsed(false);

		        int rVal = fileChooser.showSaveDialog(null);
		        paramFile = fileChooser.getSelectedFile();
		        if (rVal == JFileChooser.APPROVE_OPTION) {
		          textField.setText(paramFile.toString());
		        }
		      }
		    });
		  

		
		JLabel lblNewLabel = new JLabel("Select name and location for new parameter file.");
		lblNewLabel.setBounds(71, 40, 313, 70);
		getContentPane().add(lblNewLabel);
		
		JButton btnNext = new JButton("Next >");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//TODO: Perform LogRes and output file here.
				JClassify classify = new JClassify(rootFile, paramFile);
				classify.setVisible(true);
				setVisible(false);
			}
		});
		btnNext.setBounds(359, 243, 85, 29);
		getContentPane().add(btnNext);
		
	}


}
