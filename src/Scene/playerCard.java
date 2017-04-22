package Scene;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import backpack.Item;
import basic.Game;
import helper.TextureLoader;

public class playerCard extends ViewCard{

	private float alpha = 0.8f;
	private int[] potion = new int[2];
	private Font font = new Font(Font.MONOSPACED, Font.PLAIN, 20)
				,item = new Font(Font.MONOSPACED, Font.PLAIN, 12)
				,lv = new Font(Font.MONOSPACED,Font.PLAIN, 72);
	private BufferedImage show = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB), minimap;
	private boolean drawmini;
	private Graphics2D gg = show.createGraphics();
	public playerCard(Game g) {
		super(g);
	}
	
	public void setminimap(){
		minimap = game.getGameScene().getMap().minirender();
	}

	@Override
	public void tick() {
		potion[0] = game.getGameScene().getPlayer().getBackpack().getItemnum(Item.healpotion.getid());
		potion[1] = game.getGameScene().getPlayer().getBackpack().getItemnum(Item.coffee.getid());
		if(game.getDisplay().getKeyboard(KeyEvent.VK_M))
			drawmini = true;
		else
			drawmini = false;
		if(minimap == null)
			setminimap();
	}

	@Override
	public void render(Graphics g) {
		for(int i = 0 ; i < game.width/24 ; i++)
			for(int j = 0 ; j < 4 ; j++){
				g.drawImage(TextureLoader.sysWindow[8], i*24 , game.height - 24*(j + 1) , null);
				g.drawImage(TextureLoader.sysWindow[9], i*24 , game.height - 24*(j + 1) , null);
			}
		int level = game.getGameScene().getPlayer().getLevel();
		float th = game.getGameScene().getPlayer().getFullhealth()
				,nh = game.getGameScene().getPlayer().getHealth()
				,te = game.getGameScene().getPlayer().getFullenergy()
				,ne = game.getGameScene().getPlayer().getEnergy()
				,texp = game.getGameScene().getPlayer().expCurve(level+1)
				,lexp = game.getGameScene().getPlayer().expCurve(level)
				,nexp = game.getGameScene().getPlayer().getExp();
		
		g.setColor(Color.white);
		if((int)(192-(th-nh)/(th/192))> 0)
			g.drawImage(TextureLoader.hp.getSubimage(10, 0,(int)(192-(th-nh)/(th/192)), 4), 370, game.height - 24, null);
		
		if((int)(192-(te-ne)/(te/192))>0)
			g.drawImage(TextureLoader.mp.getSubimage(10, 0,(int)(192-(te-ne)/(te/192)), 4), 596, game.height - 24, null);
		
		if(nexp >= texp)
			game.getGameScene().getPlayer().lvUp();
		if((int)((nexp-lexp)*160/(texp-lexp)) > 0 && nexp < texp)
			g.drawImage(TextureLoader.exp.getSubimage(0, 0,(int)((nexp-lexp)*160/(texp-lexp)), 4),370, game.height - 60,
					(int)((nexp-lexp)*(596-370-192+192*2)/(texp-lexp)) , 4,null);
		
		gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT,alpha));	
		gg.drawImage(TextureLoader.potion, 0,0,32,32,null);
		g.drawImage(show,10,game.height - 40,32,32,null);
		
		gg.drawImage(TextureLoader.coffee, 0,0,32,32,null);
		g.drawImage(show,42,game.height - 40,32,32,null);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setFont(font);
		g2d.drawString(Integer.toString((int)(nh))+ "/" + Integer.toString((int)(th)), 370, game.height - 30);
		g2d.drawString(Integer.toString((int)(ne))+ "/" + Integer.toString((int)(te)), 596, game.height - 30);
		g2d.drawString(Integer.toString((int)(nexp-lexp))+"/"+Integer.toString((int)(texp-lexp)), 370, game.height - 66);
		g2d.setFont(item);
		g2d.drawString(Integer.toString(potion[0]),10,game.height-8);
		g2d.drawString(Integer.toString(potion[1]),42,game.height-8);
		g2d.setFont(lv);
		g2d.drawString("Lv" + Integer.toString(level), 210,  game.height - 25);
		
		if(drawmini){
			g2d.setFont(font);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f));
			g2d.drawImage(minimap,0,game.height- 24*4-minimap.getHeight(), null);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
			}
	}
}
