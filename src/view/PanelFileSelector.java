package view;

import java.awt.BorderLayout;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class PanelFileSelector extends JPanel implements ActionListener {
	
	/**
	 * Relation to button for search file to cipher or decipher
	 */
	private JButton btnSearch;
	/**
	 * Relation to file chooser and get information about file choosed	
	 */
	private JFileChooser chooser;
	/**
	 * Relation to label to put route path for choosed file
	 */
	private JLabel lblRoute;
	
	/**
	 * Constructor that initialize panel
	 */
	public PanelFileSelector() {
		init();
	}
	
	/**
	 * Method that initialize panels and call to load sub panels
	 */
	public void init() {
		setLayout(new BorderLayout());
		int WIDTH = Constants.WIDTH;
		int HEIGHT = Constants.HEIGHT/3;
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		loadPanel();
	}
	
	/**
	 * Method that initialize and loads sub panels/components
	 */
	private void loadPanel() {
		btnSearch = new JButton(Constants.SEARCH);
		btnSearch.setFont(new Font("Arial",Font.BOLD,14));
		btnSearch.addActionListener(this);
		
		lblRoute = new JLabel(Constants.PATH_TO_FILE);
		lblRoute.setFont(new Font("Arial",Font.PLAIN,12));
		lblRoute.setHorizontalAlignment(SwingConstants.CENTER);
	    JPanel aux = new JPanel(new GridLayout(2,1));
	    aux.add(btnSearch);
	    aux.add(lblRoute);
	    add(aux);
	}
	
	/**
	 * Method that allows open window to choose file and return file choosed
	 * @return File file choosed or RuntimeException if no file were selected
	 */
	private File chooseFile() {
		chooser = new JFileChooser("./");
		chooser.setDialogTitle(Constants.SEARCH);
		int option = chooser.showOpenDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}

		throw new RuntimeException("No file selected");
	}
	
	/**
	 * Method that get a shortest route/path for a path given and maximum length
	 * @param String path to short
	 * @param int length to have maximum length
	 * @return String new path generated
	 */
	private String getShorterPath(String path, int length) {
		char chars[] = path.toCharArray();
		if (chars.length<length) return path;
		StringBuffer newString = new StringBuffer();
		for(int i=0; i<length/2; i++) {
			newString.append(chars[i]);
		}
		newString.append("...");
		String tmp = newString.toString();
		newString = new StringBuffer();
		for(int i=chars.length-1; i>chars.length-(length/2); i--) {
			newString.append(chars[i]);
		}
		return tmp+newString.reverse().toString();
	}
	
	/**
	 * Method to return path of file choosed
	 * @return String path of file choosed
	 */
	public String getPathToFileChoosed() {
		return chooser!=null ? chooser.getSelectedFile()!=null ? chooser.getSelectedFile().getAbsolutePath():null:null;
	}

	/**
	 * Method that its called when any button its pressed and calls its respective methods
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String c = e.getActionCommand();
		if (c.equals(Constants.SEARCH)) {
			File file = null;
			try {				
				file = chooseFile();
				String path = file.getAbsolutePath();
				if (path.toCharArray().length>Constants.MAX_PATH_LENGTH) path = getShorterPath(path, Constants.MAX_PATH_LENGTH);
				lblRoute.setText(Constants.PATH_TO_FILE+path);
			} catch(Exception err) {
				System.out.println(Constants.EXCEPTION+err.getMessage());
			}
		}
	}

}
