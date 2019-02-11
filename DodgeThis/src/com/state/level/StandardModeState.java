package com.state.level;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import com.addition.KeyBoardManager;
import com.essential.Assets;
import com.essential.GameEngine;
import com.object.enemy.NormalBox;
import com.object.image.Background;
import com.object.player.Player;
import com.object.superClass.Enemy;
import com.state.GameState;
import com.state.StateManager;

public class StandardModeState extends GameState{
	
	//button
	private boolean aButton, dButton, leftButton, rightButton;
	private boolean escButton;
	
	
	
	////level specifics
	private double speedY;
	private Random random;
	//shaking
	private int shakingMethod;
	private final static int notShake = 1000, initialize_standardShake = 1001, initialize_customShake = 1002, standardShake = 1003;
	private double xShake, yShake;
	private int shakingIntensity;
	private int ss_TimeFrame, ss_DurationTimeFrame;
	//spawn
	private int spawnTimeFrame, durationSpawnTimeFrame;
	
	////objects
	//background
	private Background bg;
	//player
	private Player player;
	
	public StandardModeState(StateManager stateManager) {super(stateManager);}
	@Override public void init() {
		enemies = new ArrayList<Enemy>(60);
	}
	@Override public void update() {
		//controls
		buttonToUse();
		controls();
		
		stateShake();
		
		//its amazing there's no player update
		
		spawnEnemies();
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).update();
			
			if(player != null){
				if(player.intersects(enemies.get(i) ) ){
					System.out.println("Collide");
					if(!enemies.get(i).shouldRemove() ){enemies.get(i).setRemove(true);}
					if(shakingMethod != initialize_standardShake){shakingMethod = initialize_customShake; shakingIntensity = 8; ss_DurationTimeFrame = 20;}
				}
			}
			
			if(enemies.get(i).getY() - enemies.get(i).getImageHeight()/2 > GameEngine.gameFrame_height  ){
				if(!enemies.get(i).shouldRemove() ){enemies.get(i).setRemove(true);}
			}
			
			
			if(enemies.get(i).shouldRemove() ){enemies.remove(i);}
			if(i < 0){i = 0;}
		}

		
		
	}
	@Override public void render(Graphics g) {
		//background
		if(bg != null){bg.render(g);}
		if(player != null){player.render(g);}
		
		if(enemies != null){for(Enemy e: enemies){e.render(g);} }
	}
	
	@Override public void enter() {
		loadAssets(Assets.STANDARDLEVEL);
		
		random = new Random();
		
		bg = new Background(this);
		player = new Player(this);
		player.setPosition(GameEngine.gameFrame_width/2, GameEngine.gameFrame_height - 50);
		
		//game
		shakingMethod = notShake;
		speedY = 4d;
		durationSpawnTimeFrame = 30;
		// gap function(speedy - durationspawntimeframe) - cheight
		// gap = (2 * 30) - 30 = 30 limit possible 
		// speed = 12d limit possible  || 2 is standard low
		// durationSpawnTimeFrame = 15 limit possible || 60 is standard low
		//standard low?
	}
	@Override public void exit() {}
	@Override public String getName() {return "StandardMode_state";}
	
	//controls
	private void buttonToUse(){
		aButton = KeyBoardManager.keyInputMemory[KeyEvent.VK_A];
		dButton = KeyBoardManager.keyInputMemory[KeyEvent.VK_D];
		leftButton = KeyBoardManager.keyInputMemory[KeyEvent.VK_LEFT];
		rightButton = KeyBoardManager.keyInputMemory[KeyEvent.VK_RIGHT];
		
		escButton = KeyBoardManager.keyInputMemory[KeyEvent.VK_ESCAPE];
	}
	private void controls(){
		if(leftButton && rightButton){player.teleMid();}
		else if(leftButton && !rightButton){player.teleLeft();}
		else if(rightButton && !leftButton){player.teleRight();}
		else{
			if(aButton && dButton){player.teleMid();}
			else if(aButton && !dButton){player.teleLeft();}
			else if(dButton && !aButton){player.teleRight();}
			else{player.teleMid();}
		}
	}
	
	//state shake
	private void stateShake(){
		if(shakingMethod == initialize_standardShake){
			shakingIntensity = 3;
			
			ss_DurationTimeFrame = 30;
			ss_TimeFrame = 0;
			
			shakingMethod = standardShake;
		}
		else if(shakingMethod == initialize_customShake){
			shakingMethod = standardShake;
		}
		else if(shakingMethod == standardShake){
			xShake = 0; yShake = 0;
			ss_TimeFrame++;
			if(ss_TimeFrame > ss_DurationTimeFrame){
				xShake = 0; yShake = 0;
				ss_TimeFrame = 0;
				shakingMethod = notShake;
			}
			
			xShake = random.nextInt(shakingIntensity) + random.nextDouble();
			yShake = random.nextInt(shakingIntensity) + random.nextDouble();
		}
	}
	private void spawnEnemies(){
		spawnTimeFrame++;
		
		if(spawnTimeFrame > durationSpawnTimeFrame){
			spawnTimeFrame = 0;
			int number = random.nextInt(6);
			
			if(number == 0){spawn_singleBox("left");}
			else if(number == 1){spawn_singleBox("middle");}
			else if(number == 2){spawn_singleBox("right");}
			else if(number == 3){spawn_doubleBox("left mid");}
			else if(number == 4){spawn_doubleBox("mid right");}
			else if(number == 5){spawn_doubleBox("left right");}
			
		}
	}
	
	//enemy spawnage
	private void spawn_singleBox(String word){
		if(word.equals("left") ){
			NormalBox enemy = new NormalBox(this);
			enemy.setPosition(GameEngine.gameFrame_width/2 - 70, -100);
			enemy.setVector(0, speedY);
			enemies.add(enemy);
		}
		else if(word.equals("middle") ){
			NormalBox enemy = new NormalBox(this);
			enemy.setPosition(GameEngine.gameFrame_width/2, -100);
			enemy.setVector(0, speedY);
			enemies.add(enemy);
		}
		else if(word.equals("right") ){
			NormalBox enemy = new NormalBox(this);
			enemy.setPosition(GameEngine.gameFrame_width/2 + 70, -100);
			enemy.setVector(0, speedY);
			enemies.add(enemy);
		}
		else{return;}

	}
	private void spawn_doubleBox(String word){
		if(word.equals("left mid") ){
			NormalBox enemy = new NormalBox(this);
			enemy.initSet_boxCondition(GameEngine.gameFrame_width/2 - 35, -100, 0, speedY, 80, 30);
			enemies.add(enemy);
		}
		else if(word.equals("left right") ){
			NormalBox enemy = new NormalBox(this);
			enemy.setPosition(GameEngine.gameFrame_width/2 - 70, -100);
			enemy.setVector(0, speedY);
			enemies.add(enemy);
			
			enemy = new NormalBox(this);
			enemy.setPosition(GameEngine.gameFrame_width/2 + 70, -100);
			enemy.setVector(0, speedY);
			enemies.add(enemy);
		}
		else if(word.equals("mid right") ){
			NormalBox enemy = new NormalBox(this);
			enemy.initSet_boxCondition(GameEngine.gameFrame_width/2 + 35, -100, 0, speedY, 80, 30);
			enemies.add(enemy);
		}
		else{return;}
	}
	
	
	//4 gameLevelState
	////----events----////
	protected void eventTrigger() {}
	protected void eventManager() {}
	protected void eventActive() {}
	
	
	//getter and setter
	public double getxShake() {return xShake;}
	public double getyShake() {return yShake;}
	
}
































