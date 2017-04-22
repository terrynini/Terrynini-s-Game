package entity;

import basic.Game;

public abstract class Creature extends Entity{
	protected float fullhealth;
	protected float health;
	protected float fullenergy;
	protected float energy;
	protected float speed;
	protected float state;
	protected boolean attack = false, dead = false;
	protected  Ability[] skill = new Ability[100];
	protected Ability using;
	protected int walkcounter;
	
	public Creature(Game game,float x, float y){
		super(game,x,y);
	}
	public float getFullhealth() {
		return fullhealth;
	}
	public float getHealth() {
		return health;
	}
	public float getFullenergy() {
		return fullenergy;
	}
	public float getEnergy() {
		return energy;
	}
	public void subEnergy(float sub){
		energy -= sub;
	}
	public boolean getAttack(){
		return attack;
	}
	public void setAttack(boolean a){
		attack = a;
	}
	
	public void move(int xoffset , int yoffset){
		int[] bx = {(int)(x+xoffset+bounds.x+bounds.width)/48,(int)(x+xoffset+bounds.x)/48,(int)(x+bounds.x)/48,(int)(x+bounds.x+bounds.width)/48 },
			  by = {(int)(y+yoffset+bounds.y+bounds.height)/48,(int)(y+yoffset+bounds.y)/48,(int)(y+bounds.y)/48,(int)(y+bounds.y+bounds.height)/48};
		if((x+xoffset+bounds.x) < 0)
			bx[1] = -1;
		if((y+yoffset+bounds.y)<0)
			by[1] = -1;
		if( xoffset > 0){
			if(camera.getGameScene().getMap().getground(bx[0], by[2]).isWalkable()&&
					camera.getGameScene().getMap().getground(bx[0], by[3]).isWalkable()){
				x += xoffset;
			}
		}else if(xoffset < 0){
			if(camera.getGameScene().getMap().getground(bx[1], by[2]).isWalkable()&&
					camera.getGameScene().getMap().getground(bx[1], by[3]).isWalkable()){
				
				x += xoffset;
			}
		}
		if( yoffset > 0){
			if(camera.getGameScene().getMap().getground(bx[2], by[0]).isWalkable()&&
					camera.getGameScene().getMap().getground(bx[3], by[0]).isWalkable()){
				y += yoffset;
			}
		}else if(yoffset < 0){
			if(camera.getGameScene().getMap().getground(bx[2], by[1]).isWalkable()&&
					camera.getGameScene().getMap().getground(bx[3], by[1]).isWalkable()){
				y += yoffset;
			}
		}
	}
}
