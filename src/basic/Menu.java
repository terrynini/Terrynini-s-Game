package basic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import audio.audioPlayer;

public class Menu extends JFrame implements KeyListener{
	private JLabel background = new JLabel();
	private JLabel[] options = new JLabel[6];
	//private JPanel options = new JPanel();
	//private ImagePanel options = new ImagePanel();
	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints gbc = new GridBagConstraints();
	private int select = 3;
	
	private ImageIcon nopic = new ImageIcon("");
	private ImageIcon selectp = new ImageIcon(Menu.class.getResource("/img/titles1/select2.png"));
	private ImageIcon castle = new ImageIcon(Menu.class.getResource("/img/titles1/Castle_modify.png"));
	
	private audioPlayer ap = new audioPlayer("/audio/bgm/Theme1.wav");
	private audioPlayer selecting = new audioPlayer("/audio/se/Decision2.wav");
	private audioPlayer decision = new audioPlayer("/audio/se/Decision1.wav");

	public Menu(){
		ap.start(100);
		try{
			//Layout
			setLayout(layout);
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;
			gbc.fill = GridBagConstraints.BOTH;
			
			//draw
			//background
			background.setLayout(new GridBagLayout());
			background.setIcon(castle); 
			add(background,gbc);
		    
			//options
			String[] fill_in = {"    Start game", "    Continue game", "    Exit"};
			for(int i = 0 ; i < 6 ; i++)
				options[i] = new JLabel();
			for(int i = 0 ; i < 3 ; i++){
				options[i].setPreferredSize(new Dimension(80,70));
				options[i].setFont(new Font(Font.SANS_SERIF , 1, 26));
				options[i].setText(fill_in[i]);
				options[i+3].setPreferredSize(new Dimension(0,0));
				background.add(options[i],gbc);
				background.add(options[i+3],gbc);
				gbc.gridy++;
			}
			options[3].setIcon(selectp);
			//Listener
			addKeyListener(this);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		int key = arg0.getKeyCode();
		if(key == KeyEvent.VK_DOWN){
			selecting.start(1);
			if(select != 5){
				options[select++].setIcon(nopic);
				options[select].setIcon(selectp);
			}
		}else if(key == KeyEvent.VK_UP){
			selecting.start(1);
			if(select != 3){
				options[select--].setIcon(nopic);
				options[select].setIcon(selectp);
			}

		}else if(key == KeyEvent.VK_ENTER){
			decision.start(1);
			switch(select){
				case 3:
					//display game
					ap.stop();
					decision.stop();
					Game game = new Game("Terrynini's Game",816,624);
					game.start();
					this.dispose();
					break;
				case 4:
					break;
				case 5:
					ap.stop();
					decision.stop();
					this.dispose();
					break;
			}
		}
	}
	
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}	
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
