package basic;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import Scene.BackPackCard;
import Scene.BackPackMainCard;
import Scene.GameCard;
import Scene.ViewCard;
import helper.TextureLoader;


public class Game implements Runnable{
	
	private Display display;
	public int width, height;
	public String title;
	//for_thread
	private Thread thread;
	private boolean running = false;
	//for_render
	private BufferStrategy bs;
	private Graphics g;
	
	private Camera camera;
	private GameCard gameScene;
	private BackPackMainCard bpmc;
	private BackPackCard bpc;
	
	int fps = 60;
	double timePerTick = 1000000000/fps;
	double delta = 0;
	long now;
	long lastTime;
	
	public Game (String t ,int w, int h){
		width = w;
		height = h;	
		title = t;	
		camera = new Camera(this,0,0);
	}
	
	private void init(){
		display = new Display(title, width ,height,this);
		TextureLoader.init();
		gameScene = new GameCard(this);
		bpmc = new BackPackMainCard(this);
		bpc = new BackPackCard(this);
		ViewCard.setScene(gameScene);
	}
	
	private void timeTick(){
		display.tick();
		if(ViewCard.getScene() != null){
			ViewCard.getScene().tick();
		}
	}
	
	private void render(){
		bs = display.getcanvas().getBufferStrategy();
		if(bs == null){
			display.getcanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//clear_bs
		g.clearRect(0, 0, width, height);
		//draw_on_bs
		
		if(ViewCard.getScene() != null){
			ViewCard.getScene().render(g);
		}
		//show_bs
		bs.show();
		g.dispose();
	}
	public void run(){
		init();
		lastTime = System.nanoTime();
		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;
			if(delta >= 1){
				timeTick();
				render();
				delta--;
			}
		}
		stop();
	}
	public Display getDisplay(){
		return display;
	}
	public Camera getCamera(){
		return camera;
	}
	public GameCard getGameScene(){
		return gameScene;
	}
	public BackPackMainCard getBackPackMainCard(){
		return bpmc;
	}
	public BackPackCard getBackPackCard(){
		return bpc;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public void setDelta(int d){
		this.delta = d;
	}
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try{
			thread.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
