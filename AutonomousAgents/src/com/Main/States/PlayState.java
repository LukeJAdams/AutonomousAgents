package com.Main.States;
import java.util.ArrayList;
import com.Main.Entities.Agent;
import com.Main.Entities.Obstacle;
import com.Main.Entities.Predator;
import com.Main.Utils.Constants;
import com.Main.Utils.Vector;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;



public class PlayState extends State{
	private GridPane AnalysisPane; //Declares a private GridPane
   private  TextField[] fields; //Declares an array of textfields
   private Label[] labels;
   private Button submit,NormalMode,LearnMode,TestMode;
   private boolean analysis,paused=false; //Boolean for checking if the play state is paused and if the analysis should be displayed
	public PlayState(ArrayList<Agent> agents,ArrayList<Predator> predators,ArrayList<Obstacle> obstacles,ArrayList<Vector> food) {
		super(agents,predators,obstacles,food);
		//Constructor that calls super class with argument
	  
	   submit=new Button("Update");
	   NormalMode=new Button("Normal mode");
	   LearnMode=new Button("Learn mode");
	   TestMode=new Button("Test Mode");
		labels=new Label[] {new Label("Alignment"),new Label("Cohesion"),new Label("Seperation"),new Label("AgentNo"),new Label("PredatorNo"),new Label("FoodNo"),new Label("ObstacleNo")};
		fields=new TextField[] {new TextField(Double.toString(Constants.Alignment)),new TextField(Double.toString(Constants.Cohesion)),new TextField(Double.toString(Constants.Seperation)),
				new TextField(Integer.toString(agents.size())),new TextField(Integer.toString(predators.size())),new TextField(Integer.toString(food.size())),new TextField(Integer.toString(obstacles.size()))};
		AnalysisPane=new GridPane();
		
		for(int i=0;i<labels.length;i++) {
		AnalysisPane.add(labels[i], 0,i); //Specifies the row and column.	
		} 
		
		for(int j=0;j<fields.length;j++) {
			if(j>2)fields[j].setDisable(true);
			AnalysisPane.add(fields[j], 1, j);
		}
		submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			boolean parsed[]=new boolean[3];
			@Override
			public void handle(MouseEvent event) { //try and catch to check if the user input is a double.
				for(int i=0;i<3;i++) {
				    try {
						Double.parseDouble(fields[i].getText());
						parsed[i]=true;
					} catch (Exception e) {
                        parsed[i]=false;
					}
				    
				}
				if(parsed[0]) {
					Constants.Alignment=Double.parseDouble(fields[0].getText()); //Takes the users input to store as a constant
					fields[0].setStyle("");
				}
				else {
				fields[0].setStyle("-fx-border-color: red ;  ");
				}
				if(parsed[1]) {
					Constants.Cohesion=Double.parseDouble(fields[1].getText());
					fields[1].setStyle("");
				}
				else {
					fields[1].setStyle("-fx-border-color: red ;  ");
				}
				if(parsed[2]) {
					Constants.Seperation=Double.parseDouble(fields[2].getText());
					fields[2].setStyle("");
				}
				else {
					fields[2].setStyle("-fx-border-color: red;");
				}
			}
			
		});
		AnalysisPane.add(submit, 2, 0);
		
		NormalMode.setOnMouseClicked(new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) { Constants.Mode=0;}});
		LearnMode.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {Constants.Mode=1;  }});
		TestMode.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {  Constants.Mode=2; }});
		AnalysisPane.add(NormalMode, 2,  2);
		AnalysisPane.add(LearnMode,  2,  3);
		AnalysisPane.add(TestMode,   2,  4);
	}
	
	@Override
	public void tick() {
		if(paused) {
			Constants.currentState=new PauseState(agents,predators,obstacles,food);//if paused change to pauseState
		}
		for(Agent a:agents) {
			a.tick(agents,food,obstacles,predators);
	}
		for(Obstacle o:obstacles) {
			o.checkCollisions(agents,predators);
		}
		for(Predator p:predators) {
			p.tick(agents,food,obstacles,predators);
		}
		//This will make sure the numbers are updated.
		fields[3].setText(Integer.toString(agents.size()));
		fields[4].setText(Integer.toString(predators.size()));
		fields[5].setText(Integer.toString(food.size()));
		fields[6].setText(Integer.toString(obstacles.size()));
	}
	@Override
	public void render(GraphicsContext gc) {
		
		gc.setFill(Color.WHITE); //sets the fill colour to white
		gc.fillRect(0, 0, 800, 600); //fills the screen fully.
		double theta=0; //Declares local variable theta.
		gc.setFill(Color.GREEN); //Changes colour to green
		for(Agent a:agents) { //For each Agent in agents list
		    theta= a.GetVelocity().Heading()+90; //This gets the heading + 90 to get the angle it is facing
			gc.save(); //This will save the current empty graphicscontext, with no specific translations
			//i.e. translation is set 0,0 by default
			gc.translate(a.GetPosition().GetX(), a.GetPosition().GetY()); //Translates the graphics
			//pointer the the agents position.
			gc.rotate(theta);//rotates the pointer by theta, to get it pointing in the correct direction.
			gc.fillPolygon(new double[] {-3,3,0},new double[] {6,6,-6}, 3);
			//Fills a triangle with the specific points with the fillPolygon routine
			gc.restore(); //Restores the object back the the initial save,i.e. it clears all pointer
			//translations
			}	
		gc.setFill(Color.BLUE);
		for(Vector v:food) {
			gc.fillOval(v.GetX(),v.GetY(),5,5);
		}
		gc.setFill(Color.DARKRED);
		for(Obstacle o:obstacles) {
			gc.fillOval(o.GetPosition().GetX(), o.GetPosition().GetY(), 5, 5);
		}
		gc.setFill(Color.RED);
		for(Predator p:predators) {
			theta=p.GetVelocity().Heading()+90;
			gc.save();
			gc.translate(p.GetPosition().GetX(), p.GetPosition().GetY());
			gc.rotate(theta);
			gc.fillPolygon(new double[] {-3,3,0},new double[] {6,6,-6}, 3);
			gc.restore();
		}
		
	}

	@Override
	public void MousePressed(MouseEvent event) {
		if(event.isAltDown())obstacles.add(new Obstacle(event.getX(),event.getY()));
		else if(event.isShiftDown())predators.add(new Predator(event.getX(),event.getY()));
	else if(event.isControlDown())food.add(new Vector(event.getX(),event.getY()));
		else 
			agents.add(new Agent(event.getX(),event.getY()));//This will add a new Agent,
		//passing the x and y coordinate of the Mouse event.
	
	}
	

	@Override
	public void KeyPressed(KeyEvent event) {
		if(event.getCode()==KeyCode.ESCAPE) {//Sets paused to the reverse i.e. if not paused, paused.
			paused=!paused;
		}
		else if(event.getCode()==KeyCode.R) { //Resets all important information
			agents.clear(); //Clears lists
			obstacles.clear();
			predators.clear();
			food.clear();
			Constants.Alignment=1; //Resets constants
			Constants.Cohesion=1;
			Constants.Seperation=1.5;
			fields[0].setText(Double.toString(Constants.Alignment));
			fields[1].setText(Double.toString(Constants.Cohesion));
			fields[2].setText(Double.toString(Constants.Seperation));
			
		}else if(event.getCode()==KeyCode.Q) {
			analysis=!analysis;
			if(analysis)Constants.root.getChildren().add(AnalysisPane);
			else Constants.root.getChildren().remove(AnalysisPane); //Deletes pane if it is not open
		}
		
		
	}
	@Override
	public void handleTimer(GraphicsContext gc) {
		// TODO Auto-generated method stub
			tick();
		render(gc);
	}

}
