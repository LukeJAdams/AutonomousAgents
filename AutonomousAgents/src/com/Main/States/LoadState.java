package com.Main.States;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
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
import javafx.stage.FileChooser;

public class LoadState extends State {

private int fileChooserCount=0;
private FileChooser fileChooser=new FileChooser();
	public LoadState() {
		super();
		 File Savedirectory = new File("Saves");
	      if(!Savedirectory.exists())Savedirectory.mkdir();
	  	fileChooser.setTitle("Please enter name to save");
	  	 fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SAVE", "*.save"));
	     fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
	}
	@SuppressWarnings("unchecked")
	@Override
	public void tick() {
		fileChooserCount++;
		// TODO Auto-generated method stub
		if(fileChooserCount==1) {
			File file=fileChooser.showOpenDialog(Constants.stage);
		if (file != null) {
            try {
           FileInputStream fileInput=new FileInputStream(file);
            	ObjectInputStream in = new ObjectInputStream(fileInput);
            	agents=(ArrayList<Agent>) in.readObject(); //Casts the stream into the agents object( it deserialises the stream)
            	predators=(ArrayList<Predator>)in.readObject();
            	obstacles=(ArrayList<Obstacle>) in.readObject();
            	food=(ArrayList<Vector>) in.readObject();
            
            	Constants.currentState=new MenuState(agents,predators,obstacles,food);
            	in.close();
            	fileInput.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }else Constants.currentState=new MenuState(agents, predators, obstacles, food);
		
	}
	}

	@Override
	public void handleTimer(GraphicsContext gc) {
		// TODO Auto-generated method stub
		tick();
		render(gc);
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, 800,600);
		gc.strokeText("LOADING...",800/2, 600/2);
	
		
	}

	@Override
	public void MousePressed(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void KeyPressed(KeyEvent event) {
		// TODO Auto-generated method stub
		if(event.getCode()==KeyCode.ESCAPE)Constants.currentState=new MenuState();
	}

}
