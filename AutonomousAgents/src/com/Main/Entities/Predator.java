package com.Main.Entities;
import java.util.ArrayList;
import com.Main.Utils.Vector;
public class Predator extends Agent {
	private static final long serialVersionUID = 1L;
	public Predator(double x, double y) {
		super(x, y);
		neighbourDistance=100;
		maxSpeed=2.5;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void tick(ArrayList<Agent> agents,ArrayList<Vector> food,ArrayList<Obstacle> obstacles,ArrayList<Predator> predators) {
		//System.out.println("Here");
		checkCollisions(agents);
		Vector aliVec=Alignment(agents);
		Vector cohVec=Cohesion(agents);
		Vector obsVec=avoidObstacles(obstacles);
		Vector preVec=avoidPredators(predators);
		Vector fooVec=seekFood(food);
		fooVec.Multi(-1);
		cohVec.Multi(3);
		aliVec.Multi(3);
		obsVec.Multi(3);
		preVec.Multi(3);
		acceleration.Add(aliVec);
		acceleration.Add(cohVec);
		acceleration.Add(obsVec);
		acceleration.Add(preVec);
		//acceleration.Add(fooVec);
		velocity.Add(acceleration);
		velocity.limit(maxSpeed);
		position.Add(velocity);
		acceleration.Multi(0);
		 if (position.GetX() < -AgentSize) position.SetX(800+AgentSize);
		    if (position.GetY() < -AgentSize) position.SetY(600+AgentSize);
		    if (position.GetX()> 800+AgentSize) position.SetX(-AgentSize);
		    if (position.GetY() > 600+AgentSize) position.SetY(-AgentSize);
	}
	private void checkCollisions(ArrayList<Agent> agents) {
		double d=0;
	if(agents.isEmpty())return;
	for(int i=0;i<agents.size();i++) {
		d=Vector.distanceBetween(position, agents.get(i).position);
		if(d<5)agents.remove(i);
	}
	}

}
