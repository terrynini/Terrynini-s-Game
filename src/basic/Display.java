package basic;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Scene.ViewCard;

public class Display implements KeyListener{
	private JFrame frame;
	private Canvas canvas;
	private Game game;
	
	private String title;
	private int width, height,backcounter;
	
	private boolean[] keyboard = new boolean[256];
	private int lastkey = KeyEvent.VK_DOWN;
	public int getLastkey() {
		return lastkey;
	}

	public void setLastkey(int lastkey) {
		this.lastkey = lastkey;
	}

	public boolean up, down, right, left;
	
	public Display(String t, int w , int h,Game g){
		title = t;
		width = w;
		height = h;
		game = g;
		createDisplay();
	}
	
	private void createDisplay(){
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(new ImageIcon(Display.class.getResource("/img/icon.png")).getImage());
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setFocusable(false);
		//canvas.setBackground(Color.BLACK);
		
		frame.add(canvas);
		frame.pack();
		frame.addKeyListener(this);
	}
	
	public Canvas getcanvas(){
		return canvas;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		int key = arg0.getKeyCode();
		if(key  > 255)
			return;
		keyboard[key] = true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		keyboard[arg0.getKeyCode()] = false;
		lastkey =  arg0.getKeyCode();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void tick(){
		up = keyboard[KeyEvent.VK_UP];
		down = keyboard[KeyEvent.VK_DOWN];
		left = keyboard[KeyEvent.VK_LEFT];
		right = keyboard[KeyEvent.VK_RIGHT];
		if(up && down)
			up = down = false;
		if(left && right)
			left = right = false;
		if(keyboard[KeyEvent.VK_B]){
			if( backcounter < 20);
			else{
				if(ViewCard.getScene().equals(game.getBackPackMainCard())){
					ViewCard.setScene(game.getGameScene());
				}else{
					game.getBackPackMainCard().resetOptions();
					ViewCard.setScene(game.getBackPackMainCard());
				}
				game.setDelta(0);
				backcounter = 0;
			}
		}
		backcounter++;
		if(game.getGameScene() != null &&game.getGameScene().getPlayer()!=null &&!game.getGameScene().getPlayer().getAttack()){
			game.getGameScene().getPlayer().setAttack(keyboard[KeyEvent.VK_SPACE]);
		}
	}
	
	public boolean getKeyboard(int i){
		return keyboard[i];
	}
	
	public JFrame getFrame(){
		return frame;
	}
}
