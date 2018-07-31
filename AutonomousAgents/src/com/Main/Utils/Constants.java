package com.Main.Utils;

import com.Main.States.State;
import javafx.scene.Group;
import javafx.stage.Stage;

public class Constants {
public static Group root;
public static State currentState;
public static double Alignment=1;
public static double Cohesion=1;
public static double Seperation=1.5;
public static Stage stage;
public static int Mode=0;
public static long lastTime=System.currentTimeMillis();
public static Matrix Q=new Matrix();
public static Matrix R=new Matrix(new int[] 
		{		50,40,20,50,
				-1,20,-1,-1,
				-1,-1,20,-1,
				50,40,60,50			}

		);
}