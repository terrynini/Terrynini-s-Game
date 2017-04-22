package Scene;

import java.awt.Graphics;

import basic.Game;

public abstract class ViewCard {
	
	private static ViewCard currentCard = null;
	protected Game game;
	
	public static void setScene(ViewCard card){
		currentCard = card;
	}
	
	public static ViewCard getScene(){
		return currentCard;
	}
	
	public ViewCard(Game g){
		game = g;
	}
	public abstract void tick();
	public abstract void render(Graphics g);
}
