package Scene;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import audio.audioPlayer;
import basic.Game;
import helper.TextureLoader;

public class BackPackMainCard extends ViewCard {

	private float alpha = 1f;
	private boolean sub = true, wait = true;
	private Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 26);
	private RoundRectangle2D.Float rrr = new RoundRectangle2D.Float(24 * 8, 1, game.width - 24 * 8 - 2,
			game.height - 4 * 24 - 2, 21, 24),
			rrl = new RoundRectangle2D.Float(1, 1, 24 * 8 - 2, game.height - 4 * 24 - 2, 21, 21);
	private BufferedImage selectbox = new BufferedImage(16 * 9, 16 * 3, BufferedImage.TYPE_INT_ARGB);
	private int options = 0;
	private audioPlayer selecting = new audioPlayer("/audio/se/Decision2.wav");
	private audioPlayer decision = new audioPlayer("/audio/se/Cancel2.wav");

	public BackPackMainCard(Game g) {
		super(g);
	}


	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public void resetOptions(){
		options = 0;
		wait = true;
		alpha = 1f;
	}
	
	
	@Override
	public void tick() {
		if(!wait){
		if (game.getDisplay().up)
			if (options > 0) {
				selecting.start(1);
				options--;
				alpha = 1f;
			}
		if (game.getDisplay().down)
			if (options < 1) {
				selecting.start(1);
				options++;
				alpha = 1f;
			}
		if (game.getDisplay().getKeyboard(KeyEvent.VK_ENTER)) {
			// ��Q����
			decision.start(1);
			switch(options){
				case 0:
					game.getBackPackCard().setOptions(0);
					ViewCard.setScene(game.getBackPackCard());
					break;
			}
		}
		}
		if (sub) {
			if(alpha < 0.8)
				wait = false;
			if (alpha > 0.1)
				alpha -= 0.025f;
			else
				sub = false;
		} else {
			if (alpha < 0.9)
				alpha += 0.025f;
			else
				sub = true;
		}

		
	}

	@Override
	public void render(Graphics g) {
		// background
		game.getGameScene().render(g);
		// left column
		g.setClip(rrl);
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < game.height / 24 - 4; j++) {
				g.drawImage(TextureLoader.sysWindow[8], i * 24, j * 24, null);
				g.drawImage(TextureLoader.sysWindow[9], i * 24, j * 24, null);
			}
		g.drawImage(TextureLoader.sysWindow[0], 0, 0, null);
		for (int i = 1; i < 7; i++)
			g.drawImage(TextureLoader.sysWindow[1], i * 24, 0, null);
		g.drawImage(TextureLoader.sysWindow[2], 24 * 7, 0, null);
		for (int i = 1; i < game.height / 24 - 5; i++)
			g.drawImage(TextureLoader.sysWindow[3], 0, i * 24, null);
		for (int i = 1; i < game.height / 24 - 5; i++)
			g.drawImage(TextureLoader.sysWindow[4], 24 * 7, i * 24, null);
		g.drawImage(TextureLoader.sysWindow[5], 0, game.height - 5 * 24, null);
		for (int i = 1; i < 7; i++)
			g.drawImage(TextureLoader.sysWindow[6], 24 * i, game.height - 5 * 24, null);
		g.drawImage(TextureLoader.sysWindow[7], 24 * 7, game.height - 5 * 24, null);
		if (ViewCard.getScene().equals(this)) {
			// right column
			g.setClip(rrr);
			for (int i = 8; i < game.width / 24; i++)
				for (int j = 0; j < game.height / 24 - 4; j++) {
					g.drawImage(TextureLoader.sysWindow[8], i * 24 - 1, j * 24, null);
					g.drawImage(TextureLoader.sysWindow[9], i * 24, j * 24, null);
				}
			g.drawImage(TextureLoader.sysWindow[0], 24 * 8 - 1, 0, null);
			for (int i = 9; i < game.width / 24; i++)
				g.drawImage(TextureLoader.sysWindow[1], i * 24 - 1, 0, null);
			g.drawImage(TextureLoader.sysWindow[2], game.width - 24 - 1, 0, null);
			for (int i = 1; i < game.height / 24 - 5; i++)
				g.drawImage(TextureLoader.sysWindow[3], 24 * 8 - 1, i * 24, null);
			for (int i = 1; i < game.height / 24 - 5; i++)
				g.drawImage(TextureLoader.sysWindow[4], game.width - 24 - 1, i * 24, null);
			g.drawImage(TextureLoader.sysWindow[5], 24 * 8 - 1, game.height - 5 * 24, null);
			for (int i = 9; i < game.width / 24; i++)
				g.drawImage(TextureLoader.sysWindow[6], 24 * i - 1, game.height - 5 * 24, null);
			g.drawImage(TextureLoader.sysWindow[7], game.width - 24 - 1, game.height - 5 * 24, null);
			// items ֮����Q�ɽ�ɫ�б�
			//game.getGameScene().getPlayer().getBackpack().render(g,0);
		}
		g.setClip(null);
		selectbox = TextureLoader.selectrender(9, 3, alpha);
		g.drawImage(selectbox, 20, 40 + 16 * 3 * options, null);
		// left options
		Graphics2D g2d = (Graphics2D)g;
		g2d.setFont(font);
		g2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("����", 65, 72);
		g2d.drawString("�O��", 65, 120);

	}
}
