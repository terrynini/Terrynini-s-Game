package backpack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import helper.TextureLoader;

public class Item {
	private static Font 
		font = new Font(Font.MONOSPACED,Font.PLAIN,18)
		,font2 = new Font(Font.MONOSPACED,Font.PLAIN,14);
	public static Item[] kind = new Item[100];
	public static Item greatBall = new Item(TextureLoader.pokemonball[1],"great balls","陳廷宇的great balls,不知道為什麼會在這裡。",0)
						,healpotion = new Item(TextureLoader.potion,"heal potion","能夠治療傷口的神奇藥水",1)
						,coffee = new Item(TextureLoader.coffee,"coffee","無糖的那種",2);
					
	private BufferedImage img ;
	private String name,discribe;
	private int id;
	public Item(BufferedImage i,String name,String discribe,int id){
		img = i;
		this.name = name;
		this.id = id;
		Item.kind[id]=this;
		this.discribe = discribe;
	}
	
	public int getid(){
		return this.id;
	}
	public void render(Graphics g,int x , int y,int num){
		int xx = x, yy = y;
		if(this.img.getHeight() < 71)
			yy = y + (71 - this.img.getHeight())/2;
		if(this.img.getWidth() < 71)
			xx = x + (71 - this.img.getWidth())/2;
		g.drawImage(img, xx, yy, null);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setFont(font);
		g2d.drawString(name,x + 71 + 30 , y + 71/4);
		g2d.drawString(Integer.toString(num), x , y +20);
		g2d.setFont(font2);
		g2d.setColor(Color.GRAY);
		g2d.drawString(discribe,(int)( x + 71 + 30), y + 71/2);
		g2d.setColor(Color.WHITE);
	}
}
