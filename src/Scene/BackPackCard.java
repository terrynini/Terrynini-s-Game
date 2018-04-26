package Scene;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import audio.audioPlayer;
import backpack.BackPack;
import basic.Camera;
import basic.Game;
import helper.TextureLoader;

public class BackPackCard extends ViewCard {
	private float alpha = 0.7f,  barmove, barlong;
	private boolean sub = true;
	private RoundRectangle2D.Float rrr = new RoundRectangle2D.Float(24 * 8, 1, game.width - 24 * 8 - 2,
			game.height - 4 * 24 - 2, 21, 21);
	private BufferedImage selectbox , bar, barall;
	private int options = 0,itemhead = 0,buttonwait = 0;
	private audioPlayer selecting = new audioPlayer("/audio/se/Decision2.wav");
	private audioPlayer decision = new audioPlayer("/audio/se/Cancel2.wav");
	private BackPack backpack;
	public BackPackCard(Game g) {
		super(g);
		backpack= game.getGameScene().getPlayer().getBackpack();
		barall = TextureLoader.barrender(475, 0.2f);
	}
	public void setOptions(int n){
		options = n;
	}
	@Override
	public void tick() {
		if (buttonwait == 5) {
			if (game.getDisplay().up)
				if (options > 0) {
					selecting.start(1);
					options--;
					alpha = 0.7f;
				}
			if (game.getDisplay().down)
				if (options < backpack.getItemcounter()-1) {
					selecting.start(1);
					options++;
					alpha = 0.7f;
				}
			buttonwait = 0 ;
		}
		if(options < itemhead)
			itemhead--;
		if(options > itemhead+5)
			itemhead++;
		if (sub) {
			if (alpha > 0.1)
				alpha -= 0.025f;
			else
				sub = false;
		} else {
			if (alpha < 0.7)
				alpha += 0.025f;
			else
				sub = true;
		}
		buttonwait++;
		selectbox = TextureLoader.selectrender(34, 5, alpha);
		if(backpack.getItemcounter() <= 6){
			barlong = 475;
			barmove = 0;
		}else{
			barlong = 475f*6f/backpack.getItemcounter();
			barmove = (475-barlong)/(backpack.getItemcounter()-1-5);
		}
		bar = TextureLoader.barrender((int)barlong, 0.4f);	
	}

	@Override
	public void render(Graphics g) {
		// background
		game.getBackPackMainCard().setAlpha(0.8f);
		game.getBackPackMainCard().render(g);
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
		// item part	
		g.drawImage(selectbox, 10 * 23, 40 + 16 * 5 * (options-itemhead), null);
		g.drawImage(bar, game.getWidth()-24, (int)(24+barmove*itemhead), null);
		g.drawImage(barall, game.getWidth()-24, 24, null);
		backpack.render(g, itemhead);
		g.setClip(null);
	}

}
