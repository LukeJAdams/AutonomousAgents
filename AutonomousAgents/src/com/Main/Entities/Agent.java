package com.Main.Entities;//defines it is in the entities package

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import com.Main.Utils.Constants;
import com.Main.Utils.Vector;
//all imports
public class Agent implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	private int previousState,currentState=0;
//Defines a class called Agent
protected Vector position,velocity,acceleration;
//Defines protected variables,in preparation for the inheritance.

public Vector getPosition() {
	return position;
}
protected double AgentSize,maxForce,maxSpeed,neighbourDistance;
public Agent(double x,double y) { //Defines the constructor which takes in 2 doubles
	Random rand=new Random();//Declares and initialises a new instance of the Random class
	position=new Vector(x,y); //Initiliases the position vector with the parameters.
	double angle=rand.nextFloat()*(2*Math.PI); //Initialises a new local variable
	//which uses the random object to find a float between 0 and 1, and mutliplies it by 2pi, to get a
	//random angle from 0 to 2pi(a circle)
	velocity=new Vector(Math.cos(angle),Math.sin(angle));
	//initialises the velocity vector with x and y properties using the sine and cosine function,to 
	//get normalized speeds between -1 and 1.
	acceleration=new Vector();//Initiliases acceleration as an empty vector.
	AgentSize=2.0f; //Defines the size of the agent.
	maxSpeed=2.0f; //Defines the max speed of the agent.
	maxForce=0.03f; //Defines the maxforce of the agent(as in how quickly a change can occur).
	neighbourDistance=50; //Specifies the distance of the neighbourhood.
}


private Vector Seperation(ArrayList<Agent> agents) {//Defines the seperation routine
	double desired=25;
	Vector steer=new Vector();
	int count=0;
	for(Agent A:agents) {
		double d=Vector.distanceBetween(position, A.position);
		if((d>0)&& (d<desired)) {
			Vector difference=Vector.Sub(position, A.position);
			difference.Normalize();
			difference.Div(d);
			steer.Add(difference);
			count++;
		}
	}
	if(count>0) {
		steer.Div(count);
	}
	if(steer.Mag()>0) {
		steer.Normalize();
		steer.Multi(maxSpeed);
		steer.Sub(velocity);
		steer.limit(maxForce);
	}
	return steer;
}
protected Vector avoidObstacles(ArrayList<Obstacle> obstacles) {
	if(obstacles.isEmpty()) {
		return new Vector();
	}
	double d=0;
	int count=0;
	Vector steer=new Vector();
	for(Obstacle o:obstacles) {
		d=Vector.distanceBetween(position, o.GetPosition());
		if(d<40) {
			steer.Add(o.GetPosition());
			count++;
		}
	}
	if(count==0)return new Vector();
	steer.Div(count);
	steer=Seek(steer);
	steer.Multi(-2);
	return steer;
}
protected Vector Alignment(ArrayList<Agent> agents) {//Declares the Alignment routine
	
	Vector sum=new Vector();
	int count=0;
	for(Agent A:agents) {
		double d=Vector.distanceBetween(position, A.position);
		if((d>0)&&(d<neighbourDistance)) {
			sum.Add(A.velocity);
			count++;
		}
	}
	if(count>0) {
		sum.Div(count);
		sum.Normalize();
		sum.Multi(maxSpeed);
		Vector steer=Vector.Sub(sum, velocity);
		steer.limit(maxForce);
		return steer;
	}else {
		return new Vector();
	}
}

private Vector Seek(Vector target) {//Declares the Seek routine
	Vector desired=Vector.Sub(target, position);
	desired.Normalize();
	desired.Multi(maxSpeed);
	Vector steer=Vector.Sub(desired, velocity);
	steer.limit(maxForce);
	return steer;
}
protected Vector seekFood(ArrayList<Vector> food) {
	if(food.isEmpty()) {
		
		return new Vector();
	
	}
	Vector steer=new Vector();
	int count=0;
	double d=0;
	for(int i=0;i<food.size();i++) {
		d=Vector.distanceBetween(position, food.get(i));
		if(d<5) {
			currentState=1;
			food.remove(food.get(i));
		}
		else if(d<40) {
		currentState=1;	
		steer.Add(food.get(i));
		count++;
}
		
}
	if(count==0) {
		currentState=0;
		return new Vector();
		
	}
	steer.Div(count);
	return Seek(steer);

}
protected Vector avoidPredators(ArrayList<Predator> predators) {
	if(predators.isEmpty()) {
		return new Vector();
	}
	double d=0;
	int count=0;
	Vector steer=new Vector();
	for(Predator p:predators) {
		d=Vector.distanceBetween(position, p.GetPosition());
		if(d<40&&d!=0) {
			currentState=2;
			steer.Add(p.GetPosition());
			count++;
		}
	}
	if(count==0) {
		currentState=0;
		return new Vector();
	}
	steer.Div(count);
	steer=Seek(steer);
	steer.Multi(-2);
	return steer;
}
private void ChangeState() {
	Random r=new Random();
	previousState=currentState;
	
	if(Constants.Mode==2) {
		currentState=Constants.Q.GetAction(currentState);
	}
	int choice=r.nextInt(4);
	while(Constants.R.Get(currentState,choice)==-1) {
		choice=r.nextInt(4);
	}
	currentState=choice;
	Constants.Q.Set(previousState, currentState, Constants.R.Get(previousState,choice)+(int)(0.6*Constants.R.GetMax(currentState)));
	
	
	System.out.println("Previous state:"+previousState+"Changed state:"+currentState);
	Constants.Q.Output();
	Constants.lastTime=System.currentTimeMillis();
	
}

public void tick(ArrayList<Agent> agents,ArrayList<Vector> food,ArrayList<Obstacle> obstacles,ArrayList<Predator> predators) {
	//food.remove(0);
	
	if( Constants.Mode!=0 &(System.currentTimeMillis()-Constants.lastTime>=5000 ||(currentState==2 & System.currentTimeMillis()-Constants.lastTime>=2000)))ChangeState();
	
	Vector sepVec=Seperation(agents);
	Vector aliVec=Alignment(agents);
	Vector cohVec=Cohesion(agents);
	sepVec.Multi(Constants.Seperation);                                        
	aliVec.Multi(Constants.Alignment);
	cohVec.Multi(Constants.Cohesion);

	acceleration.Add(sepVec);
	acceleration.Add(aliVec);
	acceleration.Add(cohVec);
	if(previousState!=1) {
		Vector fooVec=seekFood(food);
		fooVec.Multi(2);      
		acceleration.Add(fooVec);
		
	}

		Vector preVec=avoidPredators(predators);
		preVec.Multi(2);
		acceleration.Add(preVec);
	
		
	Vector obsVec=avoidObstacles(obstacles);                                    
		obsVec.Multi(2);
	acceleration.Add(obsVec);
if(Constants.Mode!=0&currentState==3) {acceleration.Add(new Vector(5,5));
}
	velocity.Add(acceleration);
	velocity.limit(maxSpeed);
	position.Add(velocity);
	
	acceleration.Multi(0);
	 if (position.GetX() < -AgentSize) position.SetX(800+AgentSize);
	    if (position.GetY() < -AgentSize) position.SetY(600+AgentSize);
	    if (position.GetX()> 800+AgentSize) position.SetX(-AgentSize);
	    if (position.GetY() > 600+AgentSize) position.SetY(-AgentSize);
}
public Vector GetPosition() {//Getter for position
	return position;
}
public Vector GetVelocity() {//Getter for velocity
	return velocity;
}
protected Vector Cohesion(ArrayList<Agent> agents) {//Declares Cohesion routine
	Vector sum=new Vector();
	int count=0;
	for(Agent A:agents) {
		double d=Vector.distanceBetween(position, A.position);
		if((d>0)&&(d<neighbourDistance)) {
			sum.Add(A.position);
			count++;
		}
	}
	if(count>0) {
		sum.Div(count);
		return Seek(sum);
	}else {
		return new Vector();
	}
}


}

  

