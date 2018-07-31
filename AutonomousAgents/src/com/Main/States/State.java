package com.Main.States;

import java.util.ArrayList;
import com.Main.Entities.Agent;
import com.Main.Entities.Obstacle;
import com.Main.Entities.Predator;
import com.Main.Utils.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class State { //Defines an abstract class

	protected ArrayList<Agent> agents; //Declares a protected array list of Agent
	protected ArrayList<Obstacle> obstacles;
	protected ArrayList<Predator> predators;
	protected ArrayList<Vector> food;
	public State() {
		agents=new ArrayList<>();
		obstacles=new ArrayList<>();
		predators=new ArrayList<>();
		food=new ArrayList<>();
	}
	public State(ArrayList<Agent> agents,ArrayList<Predator> predators,ArrayList<Obstacle> obstacles,ArrayList<Vector> food) {
		this.agents=agents;
		this.predators=predators;
		this.obstacles=obstacles;
		this.food=food;
	}
	//Declares abstract methods
public abstract void tick();
public abstract void handleTimer(GraphicsContext gc);
public abstract void render(GraphicsContext gc);
public abstract void MousePressed(MouseEvent event);
public abstract void KeyPressed(KeyEvent event);
}

