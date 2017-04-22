package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import basic.Game;

public abstract class Entity {
	
	protected Game camera;
	protected float x, y;
	protected int width,height;
	protected Rectangle bounds;
	protected Rectangle realbounds;
	protected BufferedImage img;
	protected int face; //上下左右 0 1 2 3 左上開始順時針 4 5 6 7

	public Entity[] target = new Entity[100];
	
	public Entity(Game game,float x ,float y){
		this.x = x;
		this.y = y;
		camera = game;
		
		bounds = new Rectangle(0,0,width,height);
	}
	public Game getGame(){
		return camera;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	public Rectangle getBounds(){
		return bounds;
	}
	public Entity[] getTarget() {
		return target;
	}
	public void setTarget(Entity[] target) {
		this.target = target;
	}
	public abstract void damage(float damage);
	public abstract void tick();
	public abstract void render(Graphics g);
}
