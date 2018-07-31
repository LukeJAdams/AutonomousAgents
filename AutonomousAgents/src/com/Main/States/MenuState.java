package com.Main.States;
import java.util.ArrayList;
import com.Main.Entities.Agent;
import com.Main.Entities.Obstacle;
import com.Main.Entities.Predator;
import com.Main.Utils.Constants;
import com.Main.Utils.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MenuState extends State {
	
	private String[] options; //Declares a string array of menu choices.
	private boolean displayMenu=false;
	private String helpText="Welcome to the Autonomous Agents simulation."+System.lineSeparator()+"To go back to the main menu, press Esc"+System.lineSeparator()+"Click Play to start simulation"+System.lineSeparator()+"To spawn an agent in, click with your mouse"+
			System.lineSeparator()+"To spawn a predator in, hold down Shift and click"+System.lineSeparator()+"To spawn the Agents food in, hold down Ctrl and click"+System.lineSeparator()+
			"To spawn an obstacle in, hold down Alt and click"+System.lineSeparator()+"To see analysis of the current simulation, press Q"+System.lineSeparator()+"To reset, press R";
   
	public MenuState() {
		options=new String[] {"PLAY","LOAD","EXIT","HELP"}; //Initialises a string array of menu choices
		agents=new ArrayList<>();
		predators=new ArrayList<>();
		obstacles=new ArrayList<>();
		 food=new ArrayList<>();
	}
	public MenuState(ArrayList<Agent> agents,ArrayList<Predator> predators,ArrayList<Obstacle> obstacles,ArrayList<Vector> food) {
		super(agents,predators,obstacles,food); //Calls base class with parameters.
		options=new String[] {"PLAY","LOAD","EXIT","HELP"};
	}
	
	@Override
	public void tick() {
	
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, 800,600);
		gc.setFill(Color.BLACK);
		if(displayMenu) {
			gc.strokeText(helpText, 200, 200);
		}else {
		gc.setFont(Font.font(60));
		gc.fillText("F L O C K", 300, 100);
		gc.setFont(Font.font(20));
		
		for(int i=0;i<options.length;i++) { //draws all options on the menu
			gc.strokeRect(325,150+(i*80),200,50); //Draws rectangle and text inside of it
			gc.strokeText(options[i], 400, 180+(i*80));
		}
		}
	}
     private void changeState(int newState) {
    	 switch(newState) { //depending on newState, the current state will be changed accordingly
    	 case 0:
    	 Constants.currentState=new PlayState(agents,predators,obstacles,food);
    	 break;
    	 case 1:
    		 Constants.currentState=new LoadState();
    		 break;
    	 case 2:
    		 System.exit(0);
    		 break;
    	 case 3:
    		displayMenu=true; 
    		 break;
    	 }
     }
	@Override
	public void MousePressed(MouseEvent event) {
		for(int i=0;i<options.length;i++) { //Checks, for each rectangle, whether the mouse click was inside of the box.
			if((event.getX()>=325 & event.getX()<=525)&(event.getY()>=150+(i*80)&event.getY()<=200+(i*80)))changeState(i);
		}
	}

	@Override
	public void KeyPressed(KeyEvent event) {
		// TODO Auto-generated method stub
		if(displayMenu && event.getCode()==KeyCode.ESCAPE)Constants.currentState=new MenuState(agents,predators,obstacles,food);
		else return;
		
	}
	@Override
	public void handleTimer(GraphicsContext gc) {
		// TODO Auto-generated method stub
		tick();
		render(gc);
	}

}
