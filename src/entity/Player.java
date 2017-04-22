package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import Scene.GameOverCard;
import Scene.ViewCard;
import backpack.BackPack;
import backpack.Item;
import basic.Game;
import helper.AbilityController;
import helper.TextureLoader;

public class Player extends Creature {

	private float exp;
	private int level,anicounter = 0,deadcounter;
	private boolean drawlvup = false;
	private final int EXP_BASE = 500000;//500000;
	private BackPack backpack;
	public Player(Game g, float x, float y) {
		super(g, x, y);
		img = TextureLoader.player[0][1];
		width = img.getWidth();
		height = img.getHeight();
		walkcounter = 0;
		speed = 3.4f;
		skill[0] = new Ability().copy(Ability.wind3);
		using = skill[0];
		face = 2;
		bounds.x = 6;
		bounds.y = 10;
		bounds.width = 36;
		bounds.height = 36;
		realbounds = new Rectangle();
		fullhealth = health = 500;
		fullenergy = energy = 200;
		exp = 0;
		level = 1;
		backpack = new BackPack(getGame());
	}
	
	public float getExp() {
		return exp;
	}

	public void gainExp(float exp) {
		this.exp += exp;
	}
	
	public int getLevel() {
		return level;
	}

	public BackPack getBackpack() {
		return backpack;
	}

	public void setBackpack(BackPack backpack) {
		this.backpack = backpack;
	}

	public void damage(float damage){
		if(!dead)
			health -= damage;
		if(health <= 0){
			dead = true;
		}
	}
	public void lvUp(){
		if(level == 99)
			return;
		level++;
		drawlvup = true;
	}
	public void gameover(){
		ViewCard.setScene(new GameOverCard(camera));
	}
	
	public float expCurve(int level){
		return (float)(EXP_BASE*Math.pow((level - 1), 2)/ Math.pow(98, 2));
	}
	public void deadrender(Graphics g){
		if(deadcounter/5 == TextureLoader.animationDead.length){
			gameover();
			return;
		}
		g.drawImage(TextureLoader.animationDead[deadcounter/5],
				(int) (x - camera.getCamera().getXoffset()-96/2+width/2), 
				(int) (y - camera.getCamera().getYoffset()-96/2),
				96,96,
				null);
		deadcounter++;
	}
	@Override
	public void tick() {
		if(dead)
			return;
		backpack.tick();
		if(camera.getDisplay().getKeyboard(KeyEvent.VK_Z)){
			if(backpack.use(Item.healpotion.getid()))
				this.health = this.fullhealth;
		}
		if(camera.getDisplay().getKeyboard((KeyEvent.VK_X))){
			if(backpack.use(Item.coffee.getid()))
				this.energy = this.fullenergy;
		}
		if (!(camera.getDisplay().up || camera.getDisplay().down || camera.getDisplay().left
				|| camera.getDisplay().right)) {
			switch (camera.getDisplay().getLastkey()) {
			case KeyEvent.VK_UP:
				img = TextureLoader.player[3][1];
				//face = 0;
				break;
			case KeyEvent.VK_DOWN:
				img = TextureLoader.player[0][1];
				//face = 1;
				break;
			case KeyEvent.VK_LEFT:
				img = TextureLoader.player[1][1];
				//face = 2;
				break;
			case KeyEvent.VK_RIGHT:
				img = TextureLoader.player[2][1];
				//face = 3;
				break;
				
			}		
		} else {
			if (camera.getDisplay().down) {
				if (walkcounter > 25)
					img = TextureLoader.player[0][0];
				else
					img = TextureLoader.player[0][2];
				walkcounter++;
				face = 1;
			}
			if (camera.getDisplay().up) {
				if (walkcounter > 25)
					img = TextureLoader.player[3][0];
				else
					img = TextureLoader.player[3][2];
				walkcounter++;
				face = 0;
			}
			if (camera.getDisplay().left) {
				if (walkcounter > 25)
					img = TextureLoader.player[1][0];
				else
					img = TextureLoader.player[1][2];
				walkcounter++;
				if(camera.getDisplay().up)
					face = 4;
				else if(camera.getDisplay().down)
					face = 7;
				else
					face = 2;
			}
			if (camera.getDisplay().right) {
				if (walkcounter > 25)
					img = TextureLoader.player[2][0];
				else
					img = TextureLoader.player[2][2];
				walkcounter++;
				if(camera.getDisplay().up)
					face = 5;
				else if(camera.getDisplay().down)
					face = 6;
				else
					face = 3;
			}
		}
		if (camera.getDisplay().up) {
			move(0, (int)(0-speed));
		}
		if (camera.getDisplay().left) {
			move((int)(0-speed), 0);
		}
		if (camera.getDisplay().down) {
			move(0, (int)speed);
		}
		if (camera.getDisplay().right) {
			move((int)speed, 0);
		}
		
		this.realbounds.setBounds((int)(x+bounds.x-camera.getCamera().getXoffset()),
				(int)(y+bounds.y-camera.getCamera().getYoffset()), bounds.width,
				bounds.height);
		if(energy + 0.5 > fullenergy)
			energy = fullenergy;
		else
			energy += 0.5 ;
		if(expCurve(level+1) <= exp){
			lvUp();
		}
		
		if(attack){
			skill[0].tick();
		}
		if(attack){
			AbilityController.put(skill[0],this);
		}
		if (walkcounter > 50)
			walkcounter = 0;

		target = camera.getGameScene().getCC().aliveEnemies;
		camera.getCamera().follow(this);
	}
	

	@Override
	public void render(Graphics g) {
		if(dead){
			deadrender(g);
			return;
		}
		
		g.drawImage(img, (int) (x - camera.getCamera().getXoffset()), (int) (y - camera.getCamera().getYoffset()),
				null);
		if(drawlvup){
			if(anicounter / 5 >TextureLoader.animationLvup.length -1){
				anicounter = 0;
				drawlvup = false;
			}else{
						g.drawImage(TextureLoader.animationLvup[anicounter/5],
								(int) (x - camera.getCamera().getXoffset()-TextureLoader.animationLvup[0].getWidth()/2 + width/2),
								(int) (y - camera.getCamera().getYoffset()-TextureLoader.animationLvup[0].getHeight()/2),
								null);
						anicounter++;
			}
		}
		g.setColor(Color.BLUE);
		g.fillRect((int) (x - camera.getCamera().getXoffset())-(int)(fullhealth /20) + 24 , 
				(int) (y - camera.getCamera().getYoffset())-3,(int)(health /10) , 3);
		
		// g.fillRect((int)(x+bounds.x-camera.getCamera().getXoffset()),
		//(int)(y+bounds.y-camera.getCamera().getYoffset()), bounds.width,
		// bounds.height);
		// g.drawImage(img, (int)(y-camera.getCamera().getYoffset()),
		// (int)(x-camera.getCamera().getXoffset()), null);
	}
}
