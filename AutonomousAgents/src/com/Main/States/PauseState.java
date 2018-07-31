package com.Main.States;
import java.util.ArrayList;
import com.Main.Entities.Agent;
import com.Main.Entities.Obstacle;
import com.Main.Entities.Predator;
import com.Main.Utils.Constants;
import com.Main.Utils.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
public class PauseState extends State {
	private String[] options;
	private boolean displayHelp=false;
	private String helpText="Welcome to the Autonomous Agents simulation."+System.lineSeparator()+"To go back to the pause menu, press Esc"+System.lineSeparator()+"To spawn an agent in, click with your mouse"+
			System.lineSeparator()+"To spawn a predator in, hold down Shift and click"+System.lineSeparator()+"To spawn the Agents food in, hold down Ctrl and click"+System.lineSeparator()+
			"To spawn an obstacle in, hold down Alt and click"+System.lineSeparator()+"To see analysis of the current simulation, press Q"+System.lineSeparator()+"To reset, press R";
   
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	public PauseState(ArrayList<Agent> agents,ArrayList<Predator> predators,ArrayList<Obstacle> obstacles,ArrayList<Vector> food) {
		super(agents,predators,obstacles,food);
		
		options=new String[] {"S A V E","H E L P","E X I T","B A C K"};
	}

	@Override
	public void handleTimer(GraphicsContext gc) {
		// TODO Auto-generated method stub
		tick();
		render(gc);
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.fillRect(0,0,800,600);
		if(displayHelp) {
			gc.strokeText(helpText, 200, 200);
		}else {
		gc.save();
		gc.setFill(Color.BLACK);
		gc.setEffect(new Glow(20));
		gc.strokeRect(0, 0, 800, 600);
		gc.restore();
		gc.setFont(Font.font(20));
		for(int i=0;i<options.length;i++) {
			gc.strokeRect(325,100+(i*80),200,50);
			gc.strokeText(options[i], 400, 130+(i*80));
		}
		}
	}
	private void changeState(int newState) {
		switch(newState) {
		case 0:
			Constants.currentState=new SaveState(agents,predators,obstacles,food);
			break;
		case 1:
			displayHelp=true;
			break;
		case 2:
			Constants.currentState=new MenuState(agents,predators,obstacles,food);
			break;
		case 3:
			Constants.currentState=new PlayState(agents,predators,obstacles,food);
			break;
			
		}
	}

	@Override
	public void MousePressed(MouseEvent event) {
		// TODO Auto-generated method stub
		if(displayHelp)return;
		for(int j=0;j<options.length;j++) {
			if((event.getX()>=325 & event.getX()<=525)&(event.getY()>=100+(j*80)&event.getY()<=150+(j*80)))changeState(j);
		}
		
	}

	@Override
	public void KeyPressed(KeyEvent event) {
		if(displayHelp && event.getCode()==KeyCode.ESCAPE)displayHelp=false;
		else if(displayHelp)return;
		// TODO Auto-generated method stub
		if(event.getCode()==KeyCode.ESCAPE)Constants.currentState=new PlayState(agents,predators,obstacles,food);
	}

}
