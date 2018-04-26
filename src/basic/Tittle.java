package basic;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Tittle {
	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.setTitle("Terrynini's Game");
		menu.setSize(822,653);
		menu.setLocationRelativeTo(null);
		menu.setVisible(true);
		menu.setIconImage(new ImageIcon(Menu.class.getResource("/img/icon.png")).getImage());
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setResizable(false);
		/*ChatClient CC = new ChatClient();
		CC.setSize(300,400);
		CC.setLocationRelativeTo(null);
		CC.setVisible(true);
		CC.setIconImage(new ImageIcon("res/img/icon.png").getImage());
		CC.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CC.setResizable(false);
		*/
	}
}
