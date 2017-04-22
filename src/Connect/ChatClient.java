package Connect;

import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class ChatClient extends JFrame{
	private JButton send = new JButton();
	private JTextField mymes = new JTextField();
	private JTextField allmes = new JTextField();
	private GridBagLayout layout = new GridBagLayout();
	public ChatClient(){
		setLayout(layout);
	}
}
