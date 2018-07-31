package com.Main.Utils;//Defines the package that the Vector class is apart of. This is inside the Util

import java.io.Serializable;

//package

public class Vector implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//Defines the public class Vector
private double x,y;//Declares 2 private double properties called x and y
public Vector(double x,double y) { //Declares a constructor which takes in 2 parameters, x and y.
	//Assigns the 2 parameters to the properties of the class, by using the this keyword
	this.x=x;
	this.y=y;
}
public void Set(Vector v) {//Declares a procedure that will assign the properties of the Vector parameter
	//to the properties of the current object.
	this.x=v.x;
	this.y=v.y;
}
public double Heading() {//Declares a function that returns a double. Heading will find the specific angle
	//that the agent is travelling in by converting the cartesian coordinates to polar coordinates.
	//The degree part is then taken as the angle at which the angle is at.
	return Math.toDegrees((Math.atan2(y, x)));
}
public static double distanceBetween(Vector v1,Vector v2) {//Declares a static function that will
	//find the distance between Vector v1 and v2, by using the distance formula in math.
	//((v2y-v1y)^2+(v2x-v1x)^2)^(1/2)
	double x=v2.x-v1.x;
	double y=v2.y-v1.y;
	return(Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2)));
}

public static Vector Sub(Vector v1,Vector v2) {//Declares a static routine which will return
	//a vector as a result of the subtraction of v1 and v2.
	return(new Vector(v1.x-v2.x,v1.y-v2.y));
}
public void limit(double max) { //limit will limit the properties of the vector by taking in a parameter.
	//If it is greater than the limit, it will be normalized and scaled to the max, to get properties that
	//are within the specified boundary.
	if(Math.pow(Mag(),2)>max*max) {
		Normalize();
		Multi(max);
	}
}
public void Div(double scalar) {//Declares a routine that will divide the properties by the scalar
	//parameter
	this.x/=scalar;
	this.y/=scalar;
}

public void Add(Vector v) { //Performs the Addition arithmetic operator by taking in a Vector object
	
	this.x+=v.x;
	this.y+=v.y;
}
public void Multi(double scalar) {//Multiplies the vector's properties by the scalar parameter
	this.x=x*scalar;
	this.y=y*scalar;
}
public void Normalize() {//Normalize will get the properties between 0 and 1, so they are now all
	//within a certain range.This is done by dividing both x and y by the magnitude.
	double mag=Mag();
	x=x/mag;
	y=y/mag;
}

public void Sub(Vector v) {//Performs the arithmetic subtraction operator.
	this.x-=v.x;
	this.y-=v.y;
}
public double Mag() {
	//returns the magnitude of the vector by using the equation
	// |v|=((vx^2)+(vy^2))^(1/2) where | | signifies magntiude
	return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
}
public Vector() {//Default constructor which assigns properties equal to 0. Useful for
	// when an empty vector is being returned.
	this.x=0;
	this.y=0;
}
public void SetX(double x) {//Setter for x
	this.x=x;
}
public void SetY(double y) { //Setter for y
	this.y=y;
}
public double GetX() { //Getter for x
	return x;
	
}
public double GetY() { //Getter for y
	return y;
}
}
