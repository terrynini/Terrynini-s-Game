package helper;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TextureLoader {
	
	private static CutImage wind3Animation = new CutImage(ImageLoader.load("/img/animations/Wind3.png"));
	private static CutImage clawAnimation = new CutImage(ImageLoader.load("/img/animations/Claw.png"));
	private static CutImage lvupAnimation = new CutImage(ImageLoader.load("/img/animations/Revival1.png"));
	private static CutImage deadAnimation = new CutImage(ImageLoader.load("/img/animations/StateDeath.png"));
	private static CutImage hitAnimation = new CutImage(ImageLoader.load("/img/animations/HitSpecial2.png"));
	
	private static CutImage notDoorSet = new CutImage(ImageLoader.load("/img/characters/!Door2.png"));
	private static CutImage iconImageSet = new CutImage(ImageLoader.load("/img/system/IconSet.png"));
	private static CutImage systemImageSet = new CutImage(ImageLoader.load("/img/system/Window.png"));
	private static CutImage gradientImageSet = new CutImage(ImageLoader.load("/img/system/gradients.png"));
	private static CutImage pokemonballImageSet = new CutImage(ImageLoader.load("/img/Poke_Balls.png"));
	private static CutImage selectSet = new CutImage(systemImageSet.cut(2, 2, 48, 48));
	
	private static CutImage[] dungeonImageSet = new CutImage[3];
	private static CutImage[] playerImageSet = new CutImage[3];
	private static CutImage[] monsterImageSet = new CutImage[3];
	private static CutImage[] groundImageSet = new CutImage[10];
	private static CutImage[] damageImageSet = new CutImage[3];
	
	public static BufferedImage grass, snow, dirt,gameOver, potion, coffee, hp, mp, exp, bar, loading;
	
	public static BufferedImage[] startpoint = new BufferedImage[5];
	public static BufferedImage[] rockRoad = new BufferedImage[18];
	public static BufferedImage[] rockWall = new BufferedImage[11];
	public static BufferedImage[] rockWallpaper = new BufferedImage[11];
	public static BufferedImage[] sysWindow = new BufferedImage[10];
	public static BufferedImage[] pokemonball = new BufferedImage[26];
	public static BufferedImage[] animationWind = new BufferedImage[15];
	public static BufferedImage[] animationClaw = new BufferedImage[6];
	public static BufferedImage[] animationLvup = new BufferedImage[25];
	public static BufferedImage[] animationDead = new BufferedImage[8];
	public static BufferedImage[] animationGover = new BufferedImage[6];
	public static BufferedImage[] animationHit = new BufferedImage[8];
	public static BufferedImage[] select = new BufferedImage[9];
	
	public static BufferedImage[][] Orc = new BufferedImage[4][3],player = new BufferedImage[4][3];
	
	public static void init(){
		dungeonImageSet[0] = new CutImage(ImageLoader.load("/img/tilesets/Dungeon_A4.png"));
		playerImageSet[0] = new CutImage(ImageLoader.load("/img/characters/Actor1.png"));
		playerImageSet[1] = new CutImage(ImageLoader.load("/img/characters/Actor2.png"));
		playerImageSet[2] = new CutImage(ImageLoader.load("/img/characters/Actor3.png"));
		monsterImageSet[0] = new CutImage(ImageLoader.load("/img/characters/Monster.png"));
		groundImageSet[0] = new CutImage(ImageLoader.load("/img/tilesets/Outside_A2.png"));
		groundImageSet[1] = new CutImage(ImageLoader.load("/img/tilesets/World_A1.png"));
		
		grass = groundImageSet[0].cut(0, 0, 48, 48);
		dirt = groundImageSet[0].cut(4, 0, 48, 48);
		snow = groundImageSet[0].cut(9, 0, 48, 48);
		potion = iconImageSet.cut(11,2,32,32);
		coffee = iconImageSet.cut(14, 2,32,32);
		hp = gradientImageSet.cut(64, 0, 256, 4);
		mp = gradientImageSet.cut(62, 0, 256, 4);
		exp = gradientImageSet.cut(21, 0, 256, 4);
		gameOver = ImageLoader.load("/img/system/GameOver.png");
		loading = ImageLoader.load("/img/system/Loading.png");
		bar = systemImageSet.cut(14,15,12,12);	
		
		startpoint[0] = startpoint[4] =  notDoorSet.cut(7, 9, 48, 48);
		startpoint[1] = startpoint[3] =  notDoorSet.cut(5, 9, 48, 48); 
		startpoint[2] =  notDoorSet.cut(4, 9, 48, 48);
				
		sysWindow[0] = systemImageSet.cut(0, 4, 24, 24);
		sysWindow[1] = systemImageSet.cut(0, 5, 24, 24);
		sysWindow[2] = systemImageSet.cut(0, 7, 24, 24);
		sysWindow[3] = systemImageSet.cut(1, 4, 24, 24);
		sysWindow[4] = systemImageSet.cut(1, 7, 24, 24);
		sysWindow[5] = systemImageSet.cut(3, 4, 24, 24);
		sysWindow[6] = systemImageSet.cut(3, 5, 24, 24);
		sysWindow[7] = systemImageSet.cut(3, 7, 24, 24);
		sysWindow[8] = systemImageSet.cut(0, 0, 24, 24);
		sysWindow[9] = systemImageSet.cut(5, 0, 24, 24);
		
		for(int i = 0 ; i < 3 ; i++)
			for(int j = 0 ; j < 3 ; j++)
				select[i*3+j] = selectSet.cut(i,j,16,16);
		for(int i = 0 ; i < 4 ; i++)
			animationGover[i] = deadAnimation.cut(1,1+i,192,192);
		for(int i = 0 ; i < 2 ; i++)
			animationGover[i+4] = deadAnimation.cut(2, i, 192, 192);
		
		
		for(int i = 0 ; i < 3 ; i++)
			animationDead[i] = deadAnimation.cut(2, 2+i, 192, 192);
		for(int i = 0 ; i < 5 ; i++)
			animationDead[i+3] = deadAnimation.cut(3,i,192,192);
		
		for(int i = 0 ; i < 4 ; i++)
			for(int j = 0 ; j < 3 ; j++)
				player[i][j] = playerImageSet[0].cut(i, j, 48, 48);
		
		for(int i = 0 ; i < 4 ; i++)
			for(int j = 0 ; j < 3 ; j++)
				Orc[i][j] = monsterImageSet[0].cut(i, j+6, 48, 48);
		
			for(int j = 0 ; j < 5 ; j++)
				animationHit[j] = hitAnimation.cut(0, j, 192, 192);
			for(int j = 0 ; j < 3 ; j++)
				animationHit[j+5] = hitAnimation.cut(1, j, 192, 192);

		for(int i = 0 ; i < 3 ; i++)
			for(int j = 0 ; j < 5 ; j++)
				animationWind[i*5+j] = wind3Animation.cut(i,j,192,192);
		
		for(int i = 0 ; i < 5 ; i++)
			for(int j = 0 ; j < 5 ; j++ )
				animationLvup[i*5+j] = lvupAnimation.cut(i, j, 192 ,192);
		
		for(int i = 0 ; i < 5 ; i++)
			for(int j = 0 ; j < 5 ; j++)
				pokemonball[i*5+j] = pokemonballImageSet.cut(i, j, 71, 71);
		
		for(int i = 0 ; i< 5 ; i++)
				animationClaw[i] = clawAnimation.cut(0,i,192,192);
		animationClaw[5] = clawAnimation.cut(1, 0,192,192);
		
		for(int j = 0 ; j < 6 ; j++){
			for(int i = 0 ; i < 3 ; i++){
				rockRoad[ j*3+i] = dungeonImageSet[0].cut(1+j*0.5f, 2+i*0.5f, 48, 48);
			}
		}
		for(int j = 0 ; j < 6 ; j++){
			for(int i = 0 ; i < 3 ; i++){
				if(j*3+i > 10) break;
				rockWall[ j*3+i] = dungeonImageSet[0].cut(11+j*0.5f, 2+i*0.5f, 48, 48);
			}
		}
		for(int j = 0 ; j < 6 ; j++){
			for(int i = 0 ; i < 3 ; i++){
				if(j*3+i > 8) break;
				rockWallpaper[ j*3+i] = dungeonImageSet[0].cut(13+j*0.5f, 2+i*0.5f, 48, 48);
			}
		}
		//rock road 
		Graphics2D gg;
		BufferedImage temp = new BufferedImage(48*2, 48*2,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockRoad[4], 0 , 0, null);
		gg.drawImage(rockRoad[4], 48 , 0, null);
		gg.drawImage(rockRoad[4], 0 , 48, null);
		gg.drawImage(rockRoad[4], 48 , 48, null);
		rockRoad[4] = temp.getSubimage(24, 24, 48, 48);
		
		temp = new BufferedImage(48, 48*2,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockRoad[3], 0, 0, null);
		gg.drawImage(rockRoad[3], 0, 48, null);
		rockRoad[3] = temp.getSubimage(0, 24, 48, 48);
		
		temp = new BufferedImage(48, 48*2,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockRoad[5], 0, 0, null);
		gg.drawImage(rockRoad[5], 0, 48, null);
		rockRoad[5] = temp.getSubimage(0, 24, 48, 48);
		
		temp = new BufferedImage(48 *2, 48,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockRoad[1], 0, 0, null);
		gg.drawImage(rockRoad[1], 48, 0, null);
		rockRoad[1] = temp.getSubimage(24, 0, 48, 48);
		
		temp = new BufferedImage(48 *2, 48,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockRoad[7], 0, 0, null);
		gg.drawImage(rockRoad[7], 48, 0, null);
		rockRoad[7] = temp.getSubimage( 24, 0, 48, 48);
		
		temp = new BufferedImage(48, 48,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockRoad[5].getSubimage(24, 0, 24, 48), 24, 0, null);
		gg.drawImage(rockRoad[3].getSubimage(0, 0, 24, 48), 0, 0, null);
		rockRoad[9] = temp.getSubimage(0, 0, 48, 48);
		
		temp = new BufferedImage(48, 48,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockRoad[0].getSubimage(0, 0, 24, 48), 0, 0, null);
		gg.drawImage(rockRoad[2].getSubimage(24, 0, 24, 48), 24, 0, null);
		rockRoad[10] = temp.getSubimage(0, 0, 48, 48);
		
		//rock wall
		temp = new BufferedImage(48*2, 48*2,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockWall[4], 0 , 0, null);
		gg.drawImage(rockWall[4], 48 , 0, null);
		gg.drawImage(rockWall[4], 0 , 48, null);
		gg.drawImage(rockWall[4], 48 , 48, null);
		rockWall[4] = temp.getSubimage(24, 24, 48, 48);
		
		temp = new BufferedImage(48, 48*2,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockWall[3], 0, 0, null);
		gg.drawImage(rockWall[3], 0, 48, null);
		rockWall[3] = temp.getSubimage(0, 24, 48, 48);
		
		temp = new BufferedImage(48, 48*2,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockWall[5], 0, 0, null);
		gg.drawImage(rockWall[5], 0, 48, null);
		rockWall[5] = temp.getSubimage(0, 24, 48, 48);
		
		temp = new BufferedImage(48 *2, 48,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockWall[1], 0, 0, null);
		gg.drawImage(rockWall[1], 48, 0, null);
		rockWall[1] = temp.getSubimage(24, 0, 48, 48);
		
		temp = new BufferedImage(48 *2, 48,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockWall[7], 0, 0, null);
		gg.drawImage(rockWall[7], 48, 0, null);
		rockWall[7] = temp.getSubimage( 24, 0, 48, 48);
		
		temp = new BufferedImage(48, 48,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockWall[5].getSubimage(24, 0, 24, 48), 24, 0, null);
		gg.drawImage(rockWall[3].getSubimage(0, 0, 24, 48), 0, 0, null);
		rockWall[9] = temp.getSubimage(0, 0, 48, 48);
		
		temp = new BufferedImage(48, 48,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockWall[0].getSubimage(0, 0, 24, 48), 0, 0, null);
		gg.drawImage(rockWall[2].getSubimage(24, 0, 24, 48), 24, 0, null);
		rockWall[10] = temp.getSubimage(0, 0, 48, 48);
		
		//wall paper
		temp = new BufferedImage(48*2, 48,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockWallpaper[1].getSubimage(0, 0, 48, 24), 0 , 0, null);
		gg.drawImage(rockWallpaper[1].getSubimage(0, 0, 48, 24), 48 , 0, null);
		gg.drawImage(rockWallpaper[7].getSubimage(0, 24, 48, 24), 0, 24, null);
		gg.drawImage(rockWallpaper[7].getSubimage(0, 24, 48, 24), 48, 24, null);
		rockWallpaper[4] = temp.getSubimage(24, 0, 48, 48);
		
		temp = new BufferedImage(48, 48,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockWallpaper[0].getSubimage(0, 0, 48, 24), 0, 0, null);
		gg.drawImage(rockWallpaper[6].getSubimage(0, 24, 48, 24), 0, 24, null);
		rockWallpaper[3] = temp.getSubimage(0, 0, 48, 48);
		
		temp = new BufferedImage(48, 48,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockWallpaper[2].getSubimage(0, 0, 48, 24), 0, 0, null);
		gg.drawImage(rockWallpaper[8].getSubimage(0, 24, 48, 24), 0, 24, null);
		rockWallpaper[5] = temp.getSubimage(0,0, 48, 48);
		
		temp = new BufferedImage(48 *2, 48,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockWallpaper[1], 0, 0, null);
		gg.drawImage(rockWallpaper[1], 48, 0, null);
		rockWallpaper[1] = temp.getSubimage(24, 0, 48, 48);
		
		temp = new BufferedImage(48 *2, 48,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockWallpaper[7], 0, 0, null);
		gg.drawImage(rockWallpaper[7], 48, 0, null);
		rockWallpaper[7] = temp.getSubimage( 24, 0, 48, 48);
		
		temp = new BufferedImage(48, 48,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockWallpaper[5].getSubimage(24, 0, 24, 48), 24, 0, null);
		gg.drawImage(rockWallpaper[3].getSubimage(0, 0, 24, 48), 0, 0, null);
		rockWallpaper[9] = temp.getSubimage(0, 0, 48, 48);
		
		temp = new BufferedImage(48, 48,BufferedImage.TYPE_INT_ARGB);
		gg = temp.createGraphics();
		gg.drawImage(rockWallpaper[0].getSubimage(0, 0, 24, 24), 0, 0, null);
		gg.drawImage(rockWallpaper[2].getSubimage(24, 0, 24, 24), 24, 0, null);
		gg.drawImage(rockWallpaper[6].getSubimage(0, 24, 24, 24), 0, 24, null);
		gg.drawImage(rockWallpaper[8].getSubimage(24, 24, 24, 24), 24, 24, null);
		rockWallpaper[10] = temp.getSubimage(0, 0, 48, 48);
	}
	
	public static BufferedImage selectrender(int width, int height, float alpha) {
		BufferedImage temp =  new BufferedImage(16 * width, 16 * height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = temp.createGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT, alpha));
		int h = TextureLoader.select[0].getHeight(), w = TextureLoader.select[0].getWidth();
		g.drawImage(TextureLoader.select[0], 0, 0, null);
		for (int i = 1; i < width-1; i++)
			g.drawImage(TextureLoader.select[1], w * i, 0, null);
		g.drawImage(TextureLoader.select[2], w * (width-1), 0, null);
		for(int i = 1 ; i< height-1 ; i ++)
			g.drawImage(TextureLoader.select[3], 0, h*i, null);
		for (int i = 1; i < (width-1); i++)
			for(int j = 1 ; j < height-1 ; j++)
				g.drawImage(TextureLoader.select[4], w*i, h*j, null);
		for(int i = 1 ; i< height-1 ; i ++)
			g.drawImage(TextureLoader.select[5], w * (width-1), h*i, null);
		g.drawImage(TextureLoader.select[6], 0, (height-1) * h, null);
		for (int i = 1; i < (width-1); i++)
			g.drawImage(TextureLoader.select[7], w * i, (height-1) * h, null);
		g.drawImage(TextureLoader.select[8], w * (width-1), (height-1) * h, null);
		
		return temp;
	}
	public static BufferedImage barrender(int height,float alpha){
		BufferedImage temp =  new BufferedImage(12, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = temp.createGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT, alpha));
		g.drawImage(TextureLoader.bar, 0, 0, 12, height, null);
		return temp;
	}
}
