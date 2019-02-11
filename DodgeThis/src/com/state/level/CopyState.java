package com.state.level;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.state.GameState;
import com.state.StateManager;


public class CopyState extends GameState{//this state is just for only quick copy not meant to be used

	public CopyState(StateManager stateManager) {
		super(stateManager);
	}
	@Override public void init() {}
	@Override public void update() {}
	@Override public void render(Graphics g) {}
	
	@Override public void enter() {}
	@Override public void exit() {}
	@Override public String getName() {return "test";}
	
	
	//4 gameLevelState
	////----events----////
	protected void eventTrigger() {}
	protected void eventManager() {}
	protected void eventActive() {}
	
	
	//key input
	@Override public void keyPressed(KeyEvent e) {}
	@Override public void keyReleased(KeyEvent e) {}
	
	
	
}
