package Scene;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import basic.Game;
import basic.Menu;
import helper.TextureLoader;

public class GameOverCard  extends ViewCard{

	public Menu menu;
	public float alpha = 0.0f;
	private BufferedImage show = new BufferedImage(816,624, BufferedImage.TYPE_INT_RGB);
	private Graphics2D gg  = show.createGraphics();
	public GameOverCard(Game g) {
		super(g);
	}

	@Override
	public void tick() {
		if(alpha < 0.7f)
			alpha += 0.001f;
		if(alpha < 0.1f)
			return;
		for(int i = 0 ; i < 256; i++){
			if(game.getDisplay().getKeyboard(i)){
				game.getDisplay().getFrame().dispose();
				menu = new Menu();
				menu.setSize(822,653);
				menu.setLocationRelativeTo(null);
				menu.setVisible(true);
				menu.setIconImage(new ImageIcon("res/img/icon.png").getImage());
				menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				menu.setResizable(false);
				game.stop();
				break;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,     
               alpha));
		gg.drawImage(TextureLoader.gameOver,0,0,game.width,game.height, null);
		g.drawImage(show,0,0,game.width,game.height, null);
		//
		BufferedImage aa = TextureLoader.gameOver;

	}

}
