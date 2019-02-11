package com.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.essential.Assets;
import com.essential.GameEngine;



public abstract class State {
	
	//class
	protected StateManager stateManager;
	
	public abstract void init();
	public abstract void update();
	public abstract void render(Graphics g);
	
	public abstract void enter();
	public abstract void exit();
	public abstract String getName();
	
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	
	
	//other things
	protected void loadAssets(int assetsNumber){
		if(GameEngine.assets == null){GameEngine.assets = new Assets(assetsNumber);}//new assets??
		else if(GameEngine.assets != null){
			System.out.println("State.loadAssets() something loaded = " + GameEngine.assets);
			GameEngine.assets = null; GameEngine.assets = new Assets(assetsNumber);
		}
	}
	protected void removeAssets(){
		if(GameEngine.assets != null){GameEngine.assets = null;}
		else if(GameEngine.assets == null){return;}
	}
	
	
	//rendering
	
	public StateManager getStateManager() {return stateManager;}
	public void destructObject(){if(stateManager != null){stateManager = null;} }
	
}
