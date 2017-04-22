package basic;

import entity.Entity;

public class Camera {
	private Game game;
	private float xoffset, yoffset;
	public Camera(Game g ,float xoff, float yoff){
		game = g;
		xoffset = xoff;
		yoffset = yoff;
	}
	
	public void follow(Entity e){
		xoffset = e.getX() - game.getWidth() / 2 + e.getWidth() / 2;
		yoffset = e.getY() - game.getHeight() /2 + e.getHeight() / 2;

		blankErase();
	}

	//public void move(float x , float y){
		//xoffset += x;
		//yoffset += y;
	//}
	public void blankErase(){
		if(xoffset < 0)
			xoffset = 0;
		else if(xoffset > game.getGameScene().getMap().getWidth()*48 - game.getWidth()){
			xoffset = game.getGameScene().getMap().getWidth()*48 - game.getWidth();
		}
		if(yoffset < 0)
			yoffset = 0;
		else if(yoffset > game.getGameScene().getMap().getHeight()*48 - game.getHeight()){
			yoffset = game.getGameScene().getMap().getHeight()*48 - game.getHeight();
		}
	}
	
	public float getXoffset() {
		return xoffset;
	}

	public void setXoffset(float xoffset) {
		this.xoffset = xoffset;
	}

	public float getYoffset() {
		return yoffset;
	}

	public void setYoffset(float yoffset) {
		this.yoffset = yoffset;
	}
}
