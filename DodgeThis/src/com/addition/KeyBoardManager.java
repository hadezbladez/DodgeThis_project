package com.addition;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardManager implements KeyListener{

	public static boolean[] keyInputMemory;
	
	public KeyBoardManager() {keyInputMemory = new boolean[256];}
	
	//main things
	@Override public void keyPressed(KeyEvent e) {keyInputMemory[e.getKeyCode()] = true;}
	@Override public void keyReleased(KeyEvent e) {keyInputMemory[e.getKeyCode()] = false;}
	@Override public void keyTyped(KeyEvent e) {}
	
	//something when wrongs
	public static void forceAllInput_false(){
		for(int i = 0; i < 256; i++){keyInputMemory[i] = false;}
	}
	
	
}
