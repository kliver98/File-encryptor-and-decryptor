package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelOptions extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton btnCipher;
	private JButton btnDecipher;

	public PanelOptions() {
		init();
	}
	
	public void init() {
		setLayout(new BorderLayout());
		loadPanel();
	}
	
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

	@Override
	public void actionPerformed(ActionEvent e) {
		String c = e.getActionCommand();
		if (c.equals(Constants.CIPHER)) {
			System.out.println(Constants.CIPHER);
		} else if(c.equals(Constants.DECIPHER)) {
			System.out.println(Constants.DECIPHER);
		}
	}
	
}
