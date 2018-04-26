package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import audio.audioPlayer;
import helper.TextureLoader;

public class Ability {
	private int type, id, damage; 
	public int anicounter = 0;
	private float x,y ,ox , oy, cost,range;
	private Rectangle bounds,realbounds;
	public BufferedImage[] animate;
	private audioPlayer ap;
	public static Ability[] allkind = new Ability[100];
	public static Ability wind3 
		= new Ability(0,0,2,2f,50,TextureLoader.animationWind,new Rectangle(76,76,40,40),new audioPlayer("/audio/se/Wind5.wav"));
	public static Ability claw
		= new Ability(0,1,1,2f,0,TextureLoader.animationClaw,new Rectangle(60,56,60,80),new audioPlayer("/audio/se/Slash1.wav"));
	public static Ability hit
		=new Ability(0,2,1,0.5f,0,TextureLoader.animationHit,new Rectangle(76,76,40,40),new audioPlayer("/audio/se/Blow1.wav"));
	
	
	public Ability(int type,int id,int damage,float range,float cost,BufferedImage[] animate,Rectangle bounds,audioPlayer a){
		this.type = type;
		this.id = id;
		this.damage = damage;
		this.animate = animate;
		this.bounds = bounds;
		this.realbounds = new Rectangle();
		this.ap = a;
		this.cost = cost;
		this.range = range;
		allkind[id] = this;
	}
	public Ability(){

	}
	
	public Ability copy(Ability a){
		this.type = a.type;
		this.damage = a.damage;
		this.animate = a.animate.clone();
		this.bounds = a.bounds.getBounds();
		this.realbounds = a.realbounds.getBounds();
		this.ap = a.ap;
		this.cost = a.cost;
		this.range = a.range;
		return this;
	}
	public void tick(){
		realbounds.setBounds((int)(x+bounds.x),
				(int)(y+bounds.y), bounds.width,
				bounds.height);
	}
	
	public float getRange(){
		return range;
	}
	
	public void render(Graphics g ,Creature e){
		anicounter ++;
		if(anicounter < 2 ){
			switch(e.face){
			case 0:
				ox = (int) (e.x - e.camera.getCamera().getXoffset() - animate[0].getWidth()/2 + e.getWidth()/2);
				oy = (int) (e.y - e.camera.getCamera().getYoffset() - range*48 - animate[0].getHeight()/2 + e.getHeight()/2);//- animate[0].getHeight() + e.getHeight());
				break;
			case 1:
				ox = (int) (e.x - e.camera.getCamera().getXoffset() - animate[0].getWidth()/2+ e.getWidth()/2);
				oy = (int) (e.y - e.camera.getCamera().getYoffset() + range*48 - animate[0].getHeight()/2 + e.getHeight()/2);
				break;
			case 2:
				ox = (int) (e.x - e.camera.getCamera().getXoffset() - range*48 - animate[0].getWidth()/2 + e.getWidth()/2);
				oy = (int) (e.y - e.camera.getCamera().getYoffset() - animate[0].getHeight()/2 + e.getHeight()/2);
				break;
			case 3:
				ox = (int) (e.x - e.camera.getCamera().getXoffset() + range*48 - animate[0].getWidth()/2 + e.getWidth()/2 );
				oy = (int) (e.y - e.camera.getCamera().getYoffset() - animate[0].getHeight()/2+ e.getHeight()/2);
				break;
				
			case 4:
				ox = (int) (e.x - e.camera.getCamera().getXoffset() - range*48 - animate[0].getWidth()/2 + e.getWidth()/2+this.bounds.width/2.5);
				oy = (int) (e.y - e.camera.getCamera().getYoffset() - range*48 - animate[0].getHeight()/2 + e.getHeight()/2+this.bounds.height/2.5);
				break;
			case 7:
				ox = (int) (e.x - e.camera.getCamera().getXoffset() - range*48 - animate[0].getWidth()/2 + e.getWidth()/2+this.bounds.width/2.5);
				oy = (int) (e.y - e.camera.getCamera().getYoffset() + range*48 - animate[0].getHeight()/2 + e.getHeight()/2-this.bounds.height/2.5);
				break;
			case 5:
				ox = (int) (e.x - e.camera.getCamera().getXoffset() + range*48 - animate[0].getWidth()/2 + e.getWidth()/2 -this.bounds.width/2.5);
				oy = (int) (e.y - e.camera.getCamera().getYoffset() - range*48 - animate[0].getHeight()/2 + e.getHeight()/2+this.bounds.height/2.5);
				break;
			case 6:
				ox = (int) (e.x - e.camera.getCamera().getXoffset() + range*48 - animate[0].getWidth()/2 + e.getWidth()/2 -this.bounds.width/2.5);
				oy = (int) (e.y - e.camera.getCamera().getYoffset() + range*48 - animate[0].getHeight()/2 + e.getHeight()/2-this.bounds.height/2.5);
				break;
			
			}
			if(anicounter == 1){
				if(e.getEnergy() < cost){
					e.attack = false;
					anicounter = 0;
					return;	
				}
				e.subEnergy(cost);
				ap.start(1);
			}
			ox += e.camera.getCamera().getXoffset() ;
			oy += e.camera.getCamera().getYoffset();
		}
		
		if(anicounter / 5 > e.using.animate.length + 1 ){	
			anicounter = 0;
			return;
		}
		if(anicounter / 5 > e.using.animate.length - 1){
			if(e.attack)
				e.attack = false;
			return;
		}
		x = ox - e.camera.getCamera().getXoffset();
		y = oy - e.camera.getCamera().getYoffset();
		if(e.attack)
			g.drawImage(animate[anicounter / 5],(int)x,(int)y,null);
		g.setColor(Color.RED);
		//g.fillRect((int)(x+bounds.x),(int)(y+bounds.y), bounds.width,
		// bounds.height);
		//g.fillRect((int)realbounds.x,(int)realbounds.y,realbounds.width,realbounds.height);
		if(anicounter < 2 ) return;
		for(int i = 0 ; i < e.getTarget().length ; i++){
			if(e.getTarget()[i]!=null && e.getTarget()[i].realbounds.intersects(realbounds)){
				e.getTarget()[i].damage(damage);
			}
		}
	}
}
