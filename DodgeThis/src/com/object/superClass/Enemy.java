package com.object.superClass;

import java.awt.Graphics;

import com.object.ObjectEntity;
import com.state.State;

public class Enemy extends ObjectEntity{
	
	
	private boolean remove;
	
	public Enemy(State state) {
		super(state);
	}
	@Override public void update() {}
	@Override public void render(Graphics g) {}
	
	
	//getter and setter
	public boolean shouldRemove(){return remove;}
	public void setRemove(boolean remove) {this.remove = remove;}

}
