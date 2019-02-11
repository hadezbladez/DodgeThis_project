package com.object.player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.essential.GameEngine;
import com.object.ObjectEntity;
import com.state.State;
import com.state.level.StandardModeState;

public class Player extends ObjectEntity{
	/*
	we need assets but no animation
	
	*/
	
	private int positionMemory;
	private final static int positionMemory_left = 100, positionMemory_mid = 101, positionMemory_right = 102;
	
	
	
	//render
	private BufferedImage image;
	
	public Player(State state) {
		super(state);
		image = GameEngine.assets.player;
		
		imageWidth = 20; imageHeight = 20;
		cwidth = 20; cheight = 20;
		positionMemory = positionMemory_mid;
	}
	@Override public void update() {}
	@Override public void render(Graphics g) {
		int xTotal = (int) (x - imageWidth/2 + ((StandardModeState)state).getxShake() );
		int yTotal = (int) (y - imageHeight/2 + ((StandardModeState)state).getyShake() );
		
		g.drawImage(image, xTotal, yTotal, imageWidth, imageHeight, null);
	}
	
	
	
	public void teleLeft(){
		setPosition(GameEngine.gameFrame_width/2 - 70, GameEngine.gameFrame_height - 20);
		if(positionMemory != positionMemory_left){
			positionMemory = positionMemory_left;
		}
		
	}
	public void teleMid(){
		setPosition(GameEngine.gameFrame_width/2, GameEngine.gameFrame_height - 20);
		if(positionMemory != positionMemory_mid){
			positionMemory = positionMemory_mid;
		}
	}
	public void teleRight(){
		setPosition(GameEngine.gameFrame_width/2 + 70, GameEngine.gameFrame_height - 20);
		if(positionMemory != positionMemory_right){
			positionMemory = positionMemory_right;
		}
	}
	
	
	
}
