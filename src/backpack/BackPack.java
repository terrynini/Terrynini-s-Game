package backpack;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import basic.Game;

public class BackPack {
	private Game game;
	private int capacity = 100,itemcounter= 0;
	private Item[] items = new Item[capacity];
	private int[] itemnum = new int[capacity]
			,call = new int[capacity]; 
	public  BackPack(Game g){
		game = g;
		for(int i = 0 ; i < capacity ; i++)
			call[i] = 0;
		items[0] = Item.healpotion;
		itemnum[Item.healpotion.getid()] = 10;
		items[1] = Item.coffee;
		itemnum[Item.coffee.getid()] = 10;
		items[2] = Item.greatBall;
		itemnum[Item.greatBall.getid()] = 2;
		itemcounter = 3;
	}
	
	public void tick(){
		for(int i = 0 ; i < capacity ; i++){
			if(call[i] > 0)
				call[i]--;
		}
	}
	
	public int getItemcounter() {
		return itemcounter;
	}
	public int getItemnum(int i){
		return itemnum[i];
	}
	public boolean use(int i){
		if(call[i] != 0 || itemnum[i] == 0 )
			return false;
		else
			itemnum[i]--;
		call[i] = 60;
		return true;
	}
	public void render(Graphics g , int head){
		int counter = 0;
		for(int i = head ; i < capacity ; i++,counter++){
			if(40+counter*16*5>game.getHeight())
				break;
			if(items[i] == null)
				continue;
			items[i].render(g, 10 * 24, 40+counter*16*5,itemnum[items[i].getid()]);
			
		}
	}
}
