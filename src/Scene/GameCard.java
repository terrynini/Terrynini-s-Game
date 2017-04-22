package Scene;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import basic.Game;
import basic.Map;
import entity.Player;
import helper.AbilityController;
import helper.TextureLoader;
import helper.creatureController;

public class GameCard extends ViewCard{
	private Player player;
	private Map map;
	private creatureController mc;
	private playerCard pc;
	public GameCard(Game g){
		super(g);
		player = new Player(game,0,0);
		mc = new creatureController(game);
		mc.setTaget(player);
		mc.init();
		pc = new playerCard(game);
		map = new Map(game);
		map.Generate(0);
		//game.getCamera().move(0, 0);
	}
	public void tick(){
		if(!map.getdg().goodmap) return;
		map.tick();
		mc.tick();
		player.tick();
		pc.tick();
	}
	public void render(Graphics g){
		if(!map.getdg().goodmap){
			g.setColor(Color.black);
			g.fillRect(0, 0, game.width, game.height);
			g.drawImage(TextureLoader.loading, game.width-400, game.height-100, null);
		}
		else{
			map.render(g);
			mc.render(g);
			player.render(g);
			AbilityController.render(g);
			pc.render(g);
		}
	}
	public Map getMap(){
		return map;
	}
	public Player getPlayer(){
		return player;
	}
	public creatureController getCC(){
		return mc;
	}
}
