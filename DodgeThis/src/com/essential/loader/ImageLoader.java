package com.essential.loader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageLoader {//maximize the image loader skills
	private BufferedImage image, croppedImage;
	public ImageLoader(Class<?> classfile, String path) {loadImage(classfile, path);}
	public BufferedImage loadImage(Class<?> classfile, String path){
		URL url = classfile.getResource(path);
		image = null;
		
		try{image = ImageIO.read(url);}
		catch(IOException e){e.printStackTrace();}
		
		return image;
	}
	
	////static stuff
	public static BufferedImage loadImage_static(InputStream is){//we might need a change this
		BufferedImage img_ref = null;
		
		try {img_ref = ImageIO.read(is);}
		catch (UnsupportedEncodingException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		return img_ref;
	}
	public static BufferedImage loadCroppedImage_static(InputStream is, int xTile, int yTile, int width, int height){
		BufferedImage img_ref = null;
		
		try {img_ref = ImageIO.read(is);}
		catch (UnsupportedEncodingException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		return img_ref.getSubimage(xTile, yTile, width, height);
	}
	
	//getter and setter
	public BufferedImage Crop(int xTile, int yTile, int width, int height){return croppedImage.getSubimage(xTile, yTile, width, height);}
	public BufferedImage getSpritesheet() {return image;}
	
}
