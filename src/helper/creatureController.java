package helper;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import basic.Game;
import entity.Enemy;
import entity.Entity;

public class creatureController {
	public Enemy[] aliveEnemies = new Enemy[100];
	private int gcounter = 0 , seed;
	private Random interval = new Random();
	private Game game;
	private Entity target;
	private Rectangle bounds ;
	public creatureController(Game g){
		game = g;
	}
	
	public void init(){
		bounds = new Rectangle(8,20,32,36);
		Enemy.Orc = new Enemy(game,0,0,TextureLoader.Orc,bounds);
		for(int i = 0 ; i < 100 ; i++){
			aliveEnemies[i] = null;
		}
		seed = interval.nextInt(10) + 3;
		
	}
	public void generator(){
		
			for(int i = 0 ; i < aliveEnemies.length ; i++){
				if(aliveEnemies[i] == null){
					Enemy temp =  new Enemy(game,0,0,TextureLoader.Orc,bounds).copy(Enemy.Orc);
					boolean fine;
					while(true){
						temp.setX((int)(interval.nextInt(game.getGameScene().getMap().getWidth()*48 -144)+ 192));
						temp.setY((int)(interval.nextInt(game.getGameScene().getMap().getHeight()*48 -144)+192));
						fine = true;
						if(!game.getGameScene().getMap().getground((int)((temp.getX()+temp.getBounds().x)/48),(int)((temp.getY()+temp.getBounds().y)/48)).isWalkable())
							fine = false;
						else if(!game.getGameScene().getMap().getground((int)((temp.getX()+temp.getBounds().x+temp.getBounds().width)/48),(int)((temp.getY()+temp.getBounds().y)/48)).isWalkable())
							fine = false;
						else if(!game.getGameScene().getMap().getground((int)((temp.getX()+temp.getBounds().x+temp.getBounds().width)/48),(int)((temp.getY()+temp.getBounds().y+temp.getBounds().getHeight())/48)).isWalkable())
							fine = false;
						else if(!game.getGameScene().getMap().getground((int)((temp.getX()+temp.getBounds().x)/48),(int)((temp.getY()+temp.getBounds().y+temp.getBounds().getHeight())/48)).isWalkable())
							fine = false;
						else if(temp.getX()/48 >= 49 || temp.getY()/48 >= 49 )
							fine = false;
						if(fine)
							break;
					}
					aliveEnemies[i] = temp;
					aliveEnemies[i].cc = this;
					aliveEnemies[i].setTarget(target);
					System.out.println(aliveEnemies[i].getX() +" " + aliveEnemies[i].getY());
					seed = interval.nextInt(10);
					gcounter = -1 ;
					break;
				}	
			}
		
	}
	
	public void setTaget(Entity e){
		target = e;
	}
	
	public void tick(){
		if(gcounter == seed*100)//100
			generator();
		gcounter++;
		for(int i = 0 ; i < aliveEnemies.length; i++){
			if(aliveEnemies[i] == null)
				continue;
			aliveEnemies[i].tick();
		}
	}
	
	public void eliminate(Enemy e){
		for(int i = 0 ; i< aliveEnemies.length; i++){
			if( aliveEnemies[i] != null && aliveEnemies[i].equals(e)){
				aliveEnemies[i] = null;
				break;
			}
		}
	}
	
	public void render(Graphics g){
		for(int i = 0 ; i < aliveEnemies.length ; i++){
			if(aliveEnemies[i] == null)
				continue;
			aliveEnemies[i].render(g);
		}
	}
}
