package basic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import helper.TextureLoader;

public class Ground{

	public static Ground[] grounds = new Ground[100];
	public static Ground rockroad = new Ground(TextureLoader.rockRoad[4],0,true,true);
	public static Ground rockroadl = new Ground(TextureLoader.rockRoad[3],1,true,true);
	public static Ground rockroadld = new Ground(TextureLoader.rockRoad[6],2,true,true);
	public static Ground rockroadlu = new Ground(TextureLoader.rockRoad[0],3,true,true);
	public static Ground rockroadu = new Ground(TextureLoader.rockRoad[1],4,true,true);
	public static Ground rockroadr = new Ground(TextureLoader.rockRoad[5],5,true,true);
	public static Ground rockroadrd = new Ground(TextureLoader.rockRoad[8],6,true,true);
	public static Ground rockroadru = new Ground(TextureLoader.rockRoad[2],7,true,true);
	public static Ground rockroadd = new Ground(TextureLoader.rockRoad[9],8,true,true);
	public static Ground rockroadt = new Ground(TextureLoader.rockRoad[10],9,true,true);
	public static Ground rockwall = new Ground(TextureLoader.rockWall[4],10,false,true);
	public static Ground rockwalll = new Ground(TextureLoader.rockWall[3],11,false,true);
	public static Ground rockwallld = new Ground(TextureLoader.rockWall[6],12,false,true);
	public static Ground rockwalllu = new Ground(TextureLoader.rockWall[0],13,false,true);
	public static Ground rockwallu = new Ground(TextureLoader.rockWall[1],14,false,true);
	public static Ground rockwallr = new Ground(TextureLoader.rockWall[5],15,false,true);
	public static Ground rockwallrd = new Ground(TextureLoader.rockWall[8],16,false,true);
	public static Ground rockwallru = new Ground(TextureLoader.rockWall[2],17,false,true);
	public static Ground rockwalld = new Ground(TextureLoader.rockWall[9],18,false,true);
	public static Ground rockwallt = new Ground(TextureLoader.rockWall[10],19,false,true);
	public static Ground rockwallb = new Ground(TextureLoader.rockWall[7],20,false,true);
	public static Ground rockwallp = new Ground(TextureLoader.rockWallpaper[4],21,false,true);
	public static Ground rockwallpl = new Ground(TextureLoader.rockWallpaper[3],22,false,true);
	public static Ground rockwallpd = new Ground(TextureLoader.rockWallpaper[10],23,false,true);
	public static Ground rockwallpr = new Ground(TextureLoader.rockWallpaper[5],24,false,true);
	public static Ground grass = new Ground(TextureLoader.grass,98,true,true);
	public static Ground dirt = new Ground(TextureLoader.dirt,99,true,true);
	public static Ground snow = new Ground(TextureLoader.snow,97,true,true);
	//public static Ground lake = new Ground(TextureLoader.);
	
	private BufferedImage texture;
	private BufferedImage[][] animation;
	private boolean Walkable;
	private boolean HardToWalk;
	private int type;
	
	public Ground(BufferedImage t, int ty, boolean wb, boolean HTW){
		texture = t;
		type = ty;
		Walkable = wb;
		HardToWalk = HTW;
		grounds[ty] = this;
	}
	
	public Ground(BufferedImage[][] t, int ty, boolean wb, boolean HTW){
		animation = t;
		type = ty;
		Walkable = wb;
		HardToWalk = HTW;
		grounds[ty] = this;
	}
	
	public boolean isWalkable() {
		return Walkable;
	}
	public void setWalkable(boolean walkable) {
		Walkable = walkable;
	}
	public boolean isHardToWalk() {
		return HardToWalk;
	}
	public void setHardToWalk(boolean hardToWalk) {
		HardToWalk = hardToWalk;
	}
	public void tick(){
		
	}
	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x, y, null);
	}
	public void render(Graphics g, int x, int y,int width, int height){
		g.drawImage(texture, x, y, width, height, null);
	}
	public int getType(){
		return type;
	}
}
