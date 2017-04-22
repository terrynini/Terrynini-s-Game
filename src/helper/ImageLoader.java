package helper;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//because I have added my res to build, I can load Image by relative path
public class ImageLoader {
	private BufferedImage image;
	public static BufferedImage load(String path){
		try{
			return ImageIO.read(ImageLoader.class.getResource(path));
		}catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
}
