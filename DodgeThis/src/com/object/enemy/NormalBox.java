package com.object.enemy;

import java.awt.Color;
import java.awt.Graphics;

import com.object.superClass.Enemy;
import com.state.State;
import com.state.level.StandardModeState;

public class NormalBox extends Enemy{

	

	public NormalBox(State state) {
		super(state);
		imageWidth = 30; imageHeight = 30;
		cwidth = 30; cheight = 30;
	}
	@Override public void update() {
		
		
		x+=dx; y+=dy;
	}
	@Override public void render(Graphics g) {
		int xTotal = (int) (x - imageWidth/2 + ((StandardModeState)state).getxShake() );
		int yTotal = (int) (y - imageHeight/2 + ((StandardModeState)state).getyShake() );
		
		
		g.setColor(Color.GREEN); g.fillRect(xTotal, yTotal, imageWidth, imageHeight);
		g.setColor(Color.WHITE); g.drawRect(xTotal, yTotal, imageWidth, imageHeight);
	}
	
	public void initSet_boxCondition(double x, double y, double speedX, double speedY, int width, int height){
		this.x = x; this.y = y;
		setVector(speedX, speedY);
		imageWidth = width; imageHeight = height;
		cwidth = width; cheight = height;
	}
	

}
