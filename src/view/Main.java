package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.Controller;

@SuppressWarnings("serial")
public class Main extends JFrame {
	
	/**
	 * Relation with panel to choose a file to cipher or decipher
	 */
	private PanelFileSelector pFileSelector;
	/**
	 * Relation with panel to show cipher or decipher options
	 */
	private PanelOptions pOptions;
	/**
	 * Relation with panel to show information about current status of application and SHA1 hash loaded when decipher file
	 */
	private PanelInformation pInformation;
	/**
	 * Relation with controller class to handle connection between model and view
	 */
	private Controller controller;
	
	/**
	 * Constructor that initialize application and panels
	 */
	public Main() {
		
		controller = new Controller();
		
		this.setLayout(new BorderLayout());
		Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
		this.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
		this.setMinimumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
		this.setLocation((int)(dm.width/(3)),dm.height/5);
		this.setTitle(Constants.APP_TITLE);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		addPanels();
		this.setVisible(true);
	}
	
	/**
	 * Method that initialize panels and shows them
	 */
	public void addPanels() {
		
		pFileSelector = new PanelFileSelector();
		pOptions = new PanelOptions(this);
		pInformation = new PanelInformation();
		
		JPanel aux = new JPanel(new BorderLayout());
		int width = Constants.WIDTH;
		int height = Constants.HEIGHT;
		aux.setBorder(BorderFactory.createEmptyBorder(height/8,width/10,height/15,width/10));
		aux.add(pFileSelector,BorderLayout.NORTH);
		aux.add(pOptions,BorderLayout.CENTER);
		aux.add(pInformation,BorderLayout.SOUTH);
		
		add(aux,BorderLayout.CENTER);
		
		pack();
	}
	
	/**
	 * Method that call to controller for cipher file with path to file provided or null
	 * @return string with status returned by model
	 */
	public String cipherFile() {
		String path = pFileSelector.getPathToFileChoosed();
		if (path==null) {
			showMessage("Seleccione un archivo válido.", Status.ERROR, JOptionPane.ERROR_MESSAGE);
			return null;
		}
		String status = controller.cipherFile(path);
		return status;
	}
	
	/**
	 * Method that call to controller for decipher file with [path to file provided or null] and [password to decipher file]
	 * @return string with status returned by model
	 */
	public String decipherFile() {
		String path = pFileSelector.getPathToFileChoosed();
		if (path==null) {
			showMessage("Seleccione un archivo válido.", Status.ERROR, JOptionPane.ERROR_MESSAGE);
			return null;
		}
		String password = showInputDialog(Constants.PASSWORD_TO_DECIPHER);
		if (password==null) {
			showMessage("Digite contraseña.", Status.ERROR, JOptionPane.ERROR_MESSAGE);
			return null;
		}
		String status = controller.decipherFile(path, password);
		return status;
	}
	
	/**
	 * Method to show a input dialog and user type information
	 * @param String message to be shown
	 * @return String with what user types
	 */
	private String showInputDialog(String message) {
		Object response = JOptionPane.showInputDialog(null, message);
		return response!=null ? response.toString():null;
	}
	
	/**
	 * Method to show personalized message window
	 * @param string message to show
	 * @param Status title to put in head window
	 * @param int typeMessage to put image
	 */
	private void showMessage(String message, Status title, int typeMessage) {
		JOptionPane.showMessageDialog(this, message, title.toString(), typeMessage);
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Main main = new Main();
	}

}
