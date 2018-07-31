package com.Main.Main; //Specifies that it is inside of the Main package
import com.Main.States.MenuState;
import com.Main.Utils.Constants;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
//All imports used inside of the Main class
public class Main extends Application { //Defines the Main class, which inherits the Application
	//class, which is within the javafx library.
	
public static void main(String[] args) {//Declares the main sub-routine that the program will go to first.
	launch(args);//Corresponds to the sub-routine of JavaFx that will initialise and start all of the
	//important parts of the application.
}
@Override //Sub-routine below overrides a sub-routine 'start' in the JavaFx library

public void start(Stage primaryStage) throws Exception {
	//Defines a public procedure that takes in a stage parameter.
	
	Constants.currentState=new MenuState();
	primaryStage.setTitle("Testing"); //Sets the title of the application to "Testing"
	 Constants.root=new Group(); //Defines a new group called root.
	 Constants.stage=primaryStage;
	Canvas canvas=new Canvas(800,600); //Declares and initialises a new graphics canvas
	//of size 800 by 600
	canvas.setFocusTraversable(true); //This will make sure mouse events can be recorded without
	//the user needing to request focus to the form, i.e. sometimes you have to click a screen to get
	//it to respond
	Constants.root.getChildren().add(canvas); //Will add the Canvas to the group.

	primaryStage.setScene(new Scene(Constants.root)); //Sets the scene of the Stage.
	//using the group to define the scene
	Constants.root.setOnMouseClicked(new EventHandler<MouseEvent>(){
		//Defines the routine that will handle mouse events.
		@Override
		public void handle(MouseEvent event) {//Handles all mouse events
			Constants.currentState.MousePressed(event);	
		}
	});
	Constants.root.setOnMouseDragged(new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			Constants.currentState.MousePressed(event);
		}
	});
	Constants.root.setOnKeyPressed(new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {
			//System.out.println("Key pressed");
			Constants.currentState.KeyPressed(event);
		}
	});
	GraphicsContext gc=canvas.getGraphicsContext2D();//Declares and initialises
	//a graphicsContext object is the main way to draw graphics to the screen. It is assigned by
	//getting the graphics off the canvas object.
	primaryStage.sizeToScene(); //This will size the Application screen to the canvas size(800,600)
	primaryStage.setResizable(false); //This will prevent the stage from being resized.
	new AnimationTimer() {
		//This is the core loop, which will update at 60 ticks per second, a usual time for
		//any application
		
		@Override
		public void handle(long now) {
			//Calls the handle timer method in the currentState
			Constants.currentState.handleTimer(gc);
			
		}
	}.start(); //This will start the animation timer, it is needed otherwise nothing will update.
	
	primaryStage.show(); //This will show the application	
}
}
