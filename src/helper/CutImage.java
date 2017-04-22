package helper;

import java.awt.image.BufferedImage;

public class CutImage {
	private BufferedImage image;
	
	public CutImage(BufferedImage whole){
		image = whole;
	}
	
	public BufferedImage cut(float row, float col, int width, int height){
		return image.getSubimage((int)(col*width), (int)(row*height), width, height);
	}
}
