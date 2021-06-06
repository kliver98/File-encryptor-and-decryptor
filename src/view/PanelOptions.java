package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelOptions extends JPanel implements ActionListener {
	
	/**
	 * Relation to button for cipher
	 */
	private JButton btnCipher;
	/**
	 * Relation to button for decipher
	 */
	private JButton btnDecipher;
	/**
	 * Relation to main window application
	 */
	private Main main;

	/**
	 * Constructor that set relation with main window and initialize this panel
	 * @param Main main of application
	 */
	public PanelOptions(Main main) {
		this.main = main;
		init();
	}
	
	/**
	 * Initialize this panel and load panels inside
	 */
	public void init() {
		setLayout(new BorderLayout());
		loadPanel();
	}
	
	/**
	 * Load button and add listener and shows them
	 */
	private void loadPanel() {
		btnCipher = new JButton(Constants.CIPHER);
		btnCipher.setFont(new Font("Arial",Font.BOLD,14));
		btnCipher.addActionListener(this);
		btnDecipher = new JButton(Constants.DECIPHER);
		btnDecipher.setFont(new Font("Arial",Font.BOLD,14));
		btnDecipher.addActionListener(this);
		
	    JPanel aux = new JPanel(new GridLayout(1,2));
	    aux.add(btnCipher);
	    aux.add(btnDecipher);
	    add(aux);
	}

	/**
	 * Method that its called when any button its pressed and calls its respective methods
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String c = e.getActionCommand();
		if (c.equals(Constants.CIPHER)) {
			main.cipherFile();
		} else if(c.equals(Constants.DECIPHER)) {
			main.decipherFile();
		}
	}
	
}
