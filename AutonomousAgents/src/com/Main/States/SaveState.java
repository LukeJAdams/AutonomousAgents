package com.Main.States;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
import javafx.stage.FileChooser;

public class SaveState extends State {
	private int fileChooserCount=0; //Used to count the amount of times the fileChooser has been opened
	private FileChooser fileChooser = new FileChooser();
	public SaveState(ArrayList<Agent> agents,ArrayList<Predator> predators,ArrayList<Obstacle> obstacles,ArrayList<Vector> food) {
      super(agents,predators,obstacles,food);
      File Savedirectory = new File("Saves");
      if(!Savedirectory.exists())Savedirectory.mkdir(); //Will create Saves file if not existant.
  	fileChooser.setTitle("Please enter name to save");
  	 fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SAVE", "*.save")); //Will only show files with SAVE extension
     fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); //Sets initial directory to where the project/exe is stored.
	}
	@Override
	public void tick() {
		fileChooserCount++;
	
		if(fileChooserCount==1) {
			File file=fileChooser.showSaveDialog(Constants.stage); //Opens dialogue for saving.
		if (file != null) {
            try { //Trys to serialise classes
           FileOutputStream fileOutput=new FileOutputStream(file);
            	ObjectOutputStream out = new ObjectOutputStream(fileOutput);
            	out.writeObject(agents);
            	out.reset();
            	out.writeObject(predators);
            	out.reset();
            	out.writeObject(obstacles);
            	out.reset();
            	out.writeObject(food);
            	out.reset();
            	Constants.currentState=new PauseState(agents,predators,obstacles,food);
            	out.close();
            	fileOutput.close();
            } catch (IOException e) { 
                System.out.println(e.getMessage());
            }
        }else Constants.currentState=new PauseState(agents, predators, obstacles, food);
		
    }
	}
	@Override
	public void handleTimer(GraphicsContext gc) {
		tick();
		render(gc);
		 
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.fillRect(0,0,800,600);
		gc.strokeText("SAVING...",800/2, 600/2);
	}

	@Override
	public void MousePressed(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void KeyPressed(KeyEvent event) {
	  if(event.getCode()==KeyCode.ESCAPE) {
		  Constants.currentState=new PauseState(agents, predators, obstacles, food);
	  }
	}

}
