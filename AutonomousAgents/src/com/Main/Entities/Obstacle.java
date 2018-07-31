package com.Main.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import com.Main.Utils.Vector;
public class Obstacle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Vector position;

	public Obstacle(double x, double y) {
		position=new Vector(x,y);
	}
	public void checkCollisions(ArrayList<Agent> agents,ArrayList<Predator> predators) {
	double d=0;
	if(!agents.isEmpty()) {
	for(int i=0;i<agents.size();i++) {
		d=Vector.distanceBetween(position, agents.get(i).position);
		if(d<5)agents.remove(i);
	}
	}
	if(!predators.isEmpty()) {
		for(int i=0;i<predators.size();i++) {
			d=Vector.distanceBetween(position, predators.get(i).position);
			if(d<5)predators.remove(i);
		}
	}
	}
	
	public Vector GetPosition() {
		return position;
	}


}
