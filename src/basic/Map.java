package basic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import entity.Enemy;
import helper.Dungeongenerator;
import helper.TextureLoader;

public class Map {
	private Game camera;
	private Dungeongenerator dg;
	private int width, height,miniwidth = 2;
	private int[][][] draw ;
	
	public Map(Game game){
		width = 51;
		height =  51;
		camera = game;
		draw = new int[6][height][width];
		dg = new Dungeongenerator(camera);
	}
	public Stack<Point> bfs(Enemy e){
		Stack<Point> path = new Stack<Point>();
		int[][] temp = dg.getUsed().clone();
		int[] dx = { 0, 0, 1, -1 }, dy = { 1, -1, 0, 0 };
		Point[][] parent = new Point[100][100];
		for(int i = 0 ; i < 100; i ++)
			for(int j = 0 ; j  < 100 ; j ++)
				parent[i][j] = new Point();
		Queue<Point> q = new LinkedList<Point>(); 
		q.offer(new Point((int)((e.getY()+e.getBounds().y+e.getBounds().height/2)/48) , (int)((e.getX()+e.getBounds().x+e.getBounds().width/2)/48)));
		temp[(int)((e.target[0].getY()+e.target[0].getBounds().y+e.target[0].getBounds().height/2)/48)][(int)((e.target[0].getX()+e.target[0].getBounds().x+e.target[0].getBounds().width/2)/48)] = 4;
		draw[0][(int)((e.getY()+e.getBounds().y+e.getBounds().height/2)/48)][(int)((e.getX()+e.getBounds().x+e.getBounds().width/2)/48)] = 99;
		while(!q.isEmpty()){
			if(temp[q.peek().x][q.peek().y] == 4){
				temp[q.peek().x][q.peek().y] = 3;
				int y = q.peek().y, x = q.peek().x;
				while(!parent[x][y].equals(new Point((int)((e.getY()+e.getBounds().y+e.getBounds().height/2)/48) , (int)((e.getX()+e.getBounds().x+e.getBounds().width/2)/48)))){
					path.push(parent[x][y]);
					x = parent[x][y].x;
					y = parent[x][y].y;
					if(x == y && y == 0)
						break;
				}
			}
			temp[q.peek().x][q.peek().y] = 3;
			for(int d = 0  ; d < 4 ; d ++){
				if(temp[q.peek().x+dx[d]][q.peek().y+dy[d]] != 3 && temp[q.peek().x+dx[d]][q.peek().y+dy[d]] != 0){
					if(q.size() > 600)
						break;
					q.add(new Point(q.peek().x+dx[d],q.peek().y+dy[d]));
					parent[q.peek().x+dx[d]][q.peek().y+dy[d]].setLocation(q.peek().x,q.peek().y);
				}
			}
			q.poll();
		}
		Stack<Point> pathxx = (Stack<Point>) path.clone();
		while(!pathxx.isEmpty()){
			draw[0][pathxx.peek().x][pathxx.peek().y] = 97;
			pathxx.pop();
		}
		System.out.println("xxxxxxx"+path.size());
		return path;
	}
	public void Generate(int hard){
		dg.set(50, 50,draw);
		dg.start();
	}
	public void tick(){
	}
	public BufferedImage minirender(){
		BufferedImage temp = new BufferedImage(width*miniwidth,height*miniwidth,BufferedImage.TYPE_INT_ARGB);
		Graphics2D gg = temp.createGraphics();
		int px ,py;
		for(int x = 0; x< width; x++ )
			for(int y = 0 ; y < height ;y++){
				px = x*miniwidth;
				py = y*miniwidth;
				getground(x,y).render(gg, px, py , miniwidth, miniwidth);
			}
		return temp;
	}
	public void render(Graphics g){
		int px ,py;
		for(int x = 0; x< width; x++ )
			for(int y = 0 ; y < height ;y++){
				px = (int)(x*48-camera.getCamera().getXoffset());
				py = (int)(y*48-camera.getCamera().getYoffset());
				if(px < -48 || px > camera.getWidth() || py < -48 || py > camera.getHeight())
					continue;
				getground(x,y).render(g, px, py);
				
			}
		g.drawImage(TextureLoader.startpoint[2], (int)(dg.startpoint[1]*48 - camera.getCamera().getXoffset()),(int)(dg.startpoint[0]*48  - camera.getCamera().getYoffset()), null);
	}
	public Dungeongenerator getdg(){
		return dg;
	}
	public Ground getground(int x , int y){
			if(y < 0 || x < 0 || y >= height || x >= width)
				return Ground.grounds[1];
			return Ground.grounds[draw[0][y][x]];  
	}
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
