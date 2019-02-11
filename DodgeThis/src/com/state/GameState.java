package com.state;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.object.superClass.Enemy;

public class GameState extends State{
	
	////----events----////
	////core parameter
	protected int fieldState;
	protected boolean blockKeyInput, blockCoreInput;
	protected int eventOccur;
	protected boolean eventCountBoolean; protected int eventCount;
	
	////objects
	protected ArrayList<Enemy> enemies;
	
	public GameState(StateManager stateManager) {this.stateManager = stateManager;}
	@Override public void init() {}
	@Override public void update() {}
	@Override public void render(Graphics g) {}
	
	@Override public void enter() {}
	@Override public void exit() {}
	@Override public String getName() {return "gameState_superclass";}
	
	
	//render
	protected void fadeImage(Graphics g, float alpha){((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha) );}
	
	//getter and setter
	public ArrayList<Enemy> getEnemies() {return enemies;}
	
}
