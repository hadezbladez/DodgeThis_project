package com.object;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import com.state.State;




public abstract class ObjectEntity {
	
	protected State state;
	
	protected double x, y;
	protected double dx, dy;
	// collision
	protected int cwidth, cheight;
	
	//---- list of variables that will be inited on next subclass ----//
	
	// animation
	protected int imageWidth, imageHeight;
	
	//render
	protected AffineTransform oldAft;
	
	
	
	
	public ObjectEntity(State state) {this.state = state;}
	public abstract void update();
	public abstract void render(Graphics g);
	
	public void renderCollision(Graphics g){
		//draw collisions
		g.setColor(Color.BLUE);
		g.drawRect((int) (x - cwidth/2), (int) (y - cheight/2), cwidth, cheight);
	}
	
	
	protected Rectangle getRectangle() {return new Rectangle((int)x - cwidth / 2, (int)y - cheight / 2, cwidth, cheight);}
	public boolean intersects(ObjectEntity o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}
	public boolean contain(ObjectEntity o){
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.contains(r2);
	}
	
	public void setPosition(double x, double y){this.x = x; this.y = y;}
	public void setVector(double dx, double dy){this.dx = dx; this.dy = dy;}
	
	//render
	protected Graphics2D grapToGrap2D(Graphics g){return (Graphics2D)g;}
	protected void fadeImage(Graphics g, float alpha){
		if(alpha > 1f){alpha = 1f;}
		if(alpha < 0f){alpha = 0f;}
		grapToGrap2D(g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha) );
	}
	protected void saveAft(Graphics g){oldAft = ((Graphics2D)g).getTransform();}
	
	
	//getter and setter
	public double getX() {return x;} public double getY() {return y;}
	public int getImageWidth() {return imageWidth;} public int getImageHeight() {return imageHeight;}
	
	public void setX(double x) {this.x = x;} public void setY(double y) {this.y = y;}
	
}
