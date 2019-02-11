package com.state;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

public class StateManager {
	private Map<String, State> map;
	private State currentState;
	
	public StateManager() {map = new HashMap<String, State>();}
	
	//the often used method and maybe only 1 times only
	/** public void addState(State state)
	 * addState is a method that is preparing the game before you going to use it
	 * 
	 * @param state if there is <b>no state</b> in the first hand the currentState will be installed
	 * */ public void addState(State state){
		map.put(state.getName().toUpperCase(), state);
		state.init();
		if(currentState == null){state.enter(); currentState = state;}
	}
	public void setStateAndRemovePrevious(String nextStateName){
		State nextState = map.get(nextStateName.toUpperCase() );
		State prevState = map.get(currentState.getName().toUpperCase() );

		if(nextState == null){System.err.println("State <"+ nextStateName +"> does not exists"); return;}//return statement the method doesn't work and not crashing the games
		currentState.exit();
		nextState.enter();
		currentState = nextState;
		
		map.remove(prevState.getName().toUpperCase() );
	}
	public void goNewState(State nextNewState){
		map.put(nextNewState.getName().toUpperCase(), nextNewState);
		nextNewState.init();
		
		if(currentState == null){nextNewState.enter(); currentState = nextNewState;}
		else if(currentState != null){
			currentState.exit();
			nextNewState.enter();
			currentState = nextNewState;
		}
	}
	public void goNewStateAndRemovePrevious(State nextNewState){
		if(currentState == null){return;}
		State prevState = currentState;
		map.put(nextNewState.getName().toUpperCase(), nextNewState);
		nextNewState.init();
		
		if(currentState != null){
			currentState.exit();
			nextNewState.enter();
			currentState = nextNewState;
		}
		
		map.remove(prevState.getName().toUpperCase() );
		
	}
	
	//still need a test about this thing
	public void removeState(String name){
		State calledState = map.get(name.toUpperCase() );
		if(calledState == null){System.err.println("State <"+ name +"> does not exists"); return;}
		else if(calledState == currentState){System.err.println("you can't remove a state that still active"); return;}
		
		map.remove(calledState);
	}
	public void removeStateClass(State pickState){
		if(pickState == currentState){System.err.println("you can't remove a state that still active"); return;}
		map.remove(pickState);
	}
	
	public void listTheState(){
		for(int i = 0; i < map.size(); i++){
			System.out.println("StateManager.listTheState() codeName-list " + (i+1) + " = " + this.map.keySet().toArray()[i] );
		}
	}
	
	
	//the normals
	public void update(){currentState.update();}
	public void render(Graphics g){currentState.render(g);}
	
	//getter and setter
	public Map<String, State> getMap() {return map;}
	public State getCurrentState() {return currentState;}
	
}











