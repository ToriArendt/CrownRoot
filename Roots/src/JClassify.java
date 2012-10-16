import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;


public class JClassify extends JFrame{

	private File paramFile;
	private File rootFile;
	private File saveLocation;
	private JTextField textField;
	private JTextField textField_1;
	
	/**
	 * Create the application.
	 * @param paramFile 
	 * @param rootFile 
	 */
	public JClassify(File rFile, File pFile) {
		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		rootFile = rFile;
		paramFile = pFile;
		
		textField = new JTextField();
		textField.setBounds(23, 84, 313, 31);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.setBounds(335, 85, 89, 30);
		getContentPane().add(btnBrowse);
		
		  btnBrowse.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        JFileChooser fileChooser = new JFileChooser();

		        // For File
		        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		        fileChooser.setAcceptAllFileFilterUsed(false);

		        int rVal = fileChooser.showSaveDialog(null);
		        saveLocation = fileChooser.getSelectedFile();
		        if (rVal == JFileChooser.APPROVE_OPTION) {
		          textField.setText(saveLocation.toString());
		        }
		      }
		    });
		
		  JLabel lblNewLabel = new JLabel("Save classification files in...");
			lblNewLabel.setBounds(135, 49, 182, 23);
			getContentPane().add(lblNewLabel);
			
			JLabel label = new JLabel("Save files with prefix...");
			label.setBounds(147, 127, 150, 23);
			getContentPane().add(label);
			
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(96, 162, 247, 31);
			getContentPane().add(textField_1);
			
			JButton btnPerformClassification = new JButton("Next >");
			btnPerformClassification.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Classify c = new Classify(rootFile, paramFile, saveLocation, textField_1.getText());
						
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnPerformClassification.setBounds(359, 243, 85, 29);
			getContentPane().add(btnPerformClassification);
			
			JButton button = new JButton("< Prev");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JChooseParam paramPage=new JChooseParam(rootFile, paramFile);
					paramPage.setVisible(true);
					setVisible(false);
				}
			});
			button.setBounds(6, 243, 85, 29);
			getContentPane().add(button);
			
			
	}
}
