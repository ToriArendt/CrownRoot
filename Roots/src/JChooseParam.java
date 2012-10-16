import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class JChooseParam extends JFrame {
	private JTextField textField;
	private File paramFile;
	private File rootFile;



	/**
	 * Create the application.
	 * @param file 
	 */
	public JChooseParam(File file) {
		rootFile = file;
		paramFile = new File("");
		initialize();
	}

	

	public JChooseParam(File rootf, File paramf) {
		rootFile = rootf;
		paramFile = paramf;
		initialize();
	}
	
	public void initialize() {
		setTitle("Choose Logistic Regression Parameters");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JButton btnLoad = new JButton("Load Parameter File");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JClassify classifyPage=new JClassify(rootFile, paramFile);
				classifyPage.setVisible(true);
				setVisible(false);
			}
		});
		btnLoad.setBounds(145, 104, 171, 29);
		getContentPane().add(btnLoad);
		
		JButton btnNew = new JButton("Perform New Logistic Regression");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JChooseTrainSet trainSetPage=new JChooseTrainSet(rootFile);
				trainSetPage.setVisible(true);
				setVisible(false);
			}
		});
		btnNew.setBounds(96, 174, 260, 29);
		getContentPane().add(btnNew);
		
		textField = new JTextField();
		textField.setText(paramFile.toString());
		textField.setBounds(35, 64, 284, 29);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.setBounds(320, 65, 92, 29);
		getContentPane().add(btnBrowse);
		 btnBrowse.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        JFileChooser fileChooser = new JFileChooser();

		        // For File
		        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		        fileChooser.setAcceptAllFileFilterUsed(false);

		        int rVal = fileChooser.showOpenDialog(null);
		        paramFile = fileChooser.getSelectedFile();
		        if (rVal == JFileChooser.APPROVE_OPTION) {
		          textField.setText(paramFile.toString());
		        }
		      }
		    });
		
		JLabel lblor = new JLabel("-OR-");
		lblor.setBounds(214, 145, 34, 16);
		getContentPane().add(lblor);
	}
}
