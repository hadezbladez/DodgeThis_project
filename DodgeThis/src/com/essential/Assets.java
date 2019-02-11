package com.essential;

import java.awt.image.BufferedImage;

import com.essential.loader.ImageLoader;



public class Assets {
	
	////----graphics----////
	//////game states
	////splash screen
	public BufferedImage splashScreen_image;
	////in-game
	public BufferedImage player;
	public BufferedImage background_addition;
	
	
	public Assets(int load){init(load);}
	private void init(int load){
		if(load == SPLASHSCREEN_ASSETS){splashScreenAssets();}
		else if(load == STANDARDLEVEL){standardMode();}
	}
	
	
	//states
	public final static int SPLASHSCREEN_ASSETS = 100;
	private void splashScreenAssets(){
		splashScreen_image = ImageLoader.loadImage_static(getClass().getResourceAsStream("/image/ss/h1G.png") );
	}
	public final static int STANDARDLEVEL = 101;
	private void standardMode(){
		player = ImageLoader.loadImage_static(getClass().getResourceAsStream("/image/player/player.png") );
		background_addition = ImageLoader.loadImage_static(getClass().getResourceAsStream("/image/background/whiteBG.png") );
	}
	
	
	
}
