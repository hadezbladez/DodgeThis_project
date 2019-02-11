package com.addition;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.essential.GameEngine;

public class WindowsEventInformation extends WindowAdapter {
	
	private GameEngine GE;
	private boolean windowsClosed;
	private boolean focusedInFront;
	
	private boolean sysprinted_windowEvent_activateAndDeactivate;
	
	public WindowsEventInformation(GameEngine GE) {this.GE = GE;}
	
	@Override public void windowOpened(WindowEvent e) {
		super.windowOpened(e);
		if(!sysprinted_windowEvent_activateAndDeactivate){sysprinted_windowEvent_activateAndDeactivate = true;}
		System.out.println("WindowsEventInformation.windowOpened() = program is RUNNING and doing something");
	}
	@Override public void windowClosing(WindowEvent paramWindowEvent) {
		super.windowClosing(paramWindowEvent);
		windowsClosed = true;
		//GE.closingExit_using_X_only();
		GE.notRunning(GameEngine.EXITING);
	}
	
	@Override public void windowActivated(WindowEvent paramWindowEvent) {//this 2 things maybe will become useful
		super.windowActivated(paramWindowEvent);
		if(sysprinted_windowEvent_activateAndDeactivate){
			System.out.println("WindowsEventInformation.windowActivated() = Program is ACTIVE infront");
		}
		/*if(Engine.stateMusicLevel != null){
			if(!Engine.stateMusicLevel.playing() ){Engine.stateMusicLevel.resume();}
		}*/
		focusedInFront = true;
	}
	@Override public void windowDeactivated(WindowEvent paramWindowEvent) {
		super.windowDeactivated(paramWindowEvent);
		/*
		if(GE.getShutDown_sequence() == GameEngine.closingSequence || GE.getShutDown_sequence() == GameEngine.allClosed ){}
		else{
			if(sysprinted_windowEvent_activateAndDeactivate){System.out.println("WindowsEventInformation.windowDeactivated() = Program is NOT ACTIVE and not infront");}
		}*/
		
		focusedInFront = false;
	}
	@Override public void windowIconified(WindowEvent e) {
		super.windowIconified(e);
		System.out.println("WindowsEventInformation.windowIconified() = program is ICONIFIED");
	}
	@Override public void windowDeiconified(WindowEvent e) {
		super.windowDeiconified(e);
		System.out.println("WindowsEventInformation.windowDeiconified() = program is DEICONIFIED");
	}
	
	public void destructClass(){if(GE != null){GE = null;} }
	
	
	//getter and setter
	public boolean isWindowsClosed() {return windowsClosed;}
	public boolean isFocusedInFront() {return focusedInFront;}
	
}














