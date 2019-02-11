package com.object.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.essential.GameEngine;
import com.state.State;
import com.state.level.StandardModeState;

public class Background {
	
	private State state;
	
	//render
	private BufferedImage image;
	private int gapRange;
	
	public Background(State state) {
		this.state = state;
		image = GameEngine.assets.background_addition;
		gapRange = 60;
	}
	
	public void render(Graphics g){
		//back screens || does this thing destroy something?
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GameEngine.gameFrame_width, GameEngine.gameFrame_height);
		
		for(int i = 0; i < 4; i ++){
			g.drawImage(image, (int) (5 + (gapRange * i) + ((StandardModeState)state).getxShake() ), 0, 15, GameEngine.gameFrame_height, null);
		}
		
	}
	
	
	

	Graphics2D grapToGrap2D(Graphics g){return (Graphics2D)g;}
	void fadeImage(Graphics g, float alpha){
		if(alpha > 1f){alpha = 1f;}
		if(alpha < 0f){alpha = 0f;}
		grapToGrap2D(g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha) );
	}
	
	
	
	
	
	
	
	
}









