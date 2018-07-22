package frogger;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

	private Timer timer;
	private int delay = 10;
	
	private Map map = new Map();
	
	public Gameplay(int contentWidth, int contentHeight) {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		map.graphicsEngine.paintComponent(g);
	}

	public void actionPerformed(ActionEvent e) {
		checkLogs(map.logArray);
		repaint();
	}
	
	public void checkLogs(Log[][] logArray) {
		for (int i = 0; i < logArray.length; i++) {
			int countFalse = 0; //Keep track of how many logs the user is not stepping on.
		
			for (int j = 0; j < logArray[i].length; j++) {
				
				if (logArray[i][j].isLogRightBound() == true) {
					logBoundaryRightBound(logArray[i][j]);
					
					if (logArray[i][j].getLogLength() == 3) {
						if (userOnLog3R(logArray[i][j]) == false) {
							checkWater(0, 0, 128, 128, map.frog.getPlayerPosX(), map.frog.getPlayerPosY());
						}
					} 
					else if (logArray[i][j].getLogLength() == 5) {
						userOnLog5R(logArray[i][j]);
					} 
					else {
						userOnLog7R(logArray[i][j]);
					}
				}
				
				else if (logArray[i][j].isLogRightBound() == false) {
					logBoundaryLeftBound(logArray[i][j]);
					
					if (logArray[i][j].getLogLength() == 3) {
						if (userOnLog3L(logArray[i][j]) == false) {
							countFalse++;
						}
						if (countFalse == logArray[i].length) { //If the user is not stepping on all logs, check if user is on water
							//checkWater(0, 0, 640, 32, map.frog.getPlayerPosX(), map.frog.getPlayerPosY()); //Test water boundaries
						}
					} 
					else if (logArray[i][j].getLogLength() == 5) {
						userOnLog5L(logArray[i][j]);
					} 
					else {
						userOnLog7L(logArray[i][j]);
					}
				}
			}
		}
	}

	public void checkVehicles(Vehicle[][] vehicleArray) {
		for (int i = 0; i < vehicleArray.length; i++) {
			for (int j = 0; j < vehicleArray[i].length; j++) {
				
				if (vehicleArray[i][j].isVehicleRightBound() == true) {
					vehicleBoundaryRightBound(vehicleArray[i][j]);
					vehicleCollisionRightBound(vehicleArray[i][j]);
				}
				else if (vehicleArray[i][j].isVehicleRightBound() == false) {
					vehicleBoundaryLeftBound(vehicleArray[i][j]);
					vehicleCollisionLeftBound(vehicleArray[i][j]);
				}
			}
		}
	}
	
	public void checkTurtles(Turtle[][] turtleArray) {
		for (int i = 0; i < turtleArray.length; i++) {
			for (int j = 0; j < turtleArray[i].length; j++) {
				turtleBoundaryLeftBound(turtleArray[i][j]);
				
				if (turtleArray[i][j].getTurtleLength() == 2) {
					userOnTurtle2L(turtleArray[i][j]);
				}
				else if (turtleArray[i][j].getTurtleLength() == 3) {
					userOnTurtle3L(turtleArray[i][j]);
				}
			}
		}
	}
	
	public void checkWater(int xBoundary1, int yBoundary1, int xBoundary2, int yBoundary2, double userPosX, double userPosY) {
		if (userPosX >= xBoundary1 && userPosX <= xBoundary2 && userPosY >= yBoundary1 && userPosY <= yBoundary2) {
			System.exit(0);
		}
	}
	
	public void checkEndZone(int xBoundary1, int yBoundary1, int xBoundary2, int yBoundary2, double userPosX, double userPosY) {
		if (userPosX >= xBoundary1 && userPosX <= xBoundary2 && userPosY >= yBoundary1 && userPosY <= yBoundary2) {
			System.exit(0);
		}
	}
	

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			if (map.frog.getPlayerPosY() <= 0) {
				 map.frog.setPosY(0);
			}
			else {
				map.frog.moveUp();
			}
		}
			
		if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			if (map.frog.getPlayerPosY() >= 448) {
				map.frog.setPosY(448);
			}
			else {
				map.frog.moveDown();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			if (map.frog.getPlayerPosX() >= 608) {
				map.frog.setPosX(608);
			}
			else {
				map.frog.moveRight();
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			if (map.frog.getPlayerPosX() <= 0) {
				map.frog.setPosX(0);
			}
			else {
				map.frog.moveLeft();
			}
		}
	}
	
	public void vehicleBoundaryRightBound(Vehicle vehicle) {
		if (vehicle.getVehiclePosX() >= 640.0) {
			vehicle.setVehiclePosX(-30.0);
		}
	}
	
	public void vehicleBoundaryLeftBound(Vehicle vehicle) {
		if (vehicle.getVehiclePosX() <= 0.0) {
			vehicle.setVehiclePosX(670.0);
		}
	}

	public void vehicleCollisionRightBound(Vehicle vehicle) {
		if (map.frog.getPlayerPosX() - vehicle.getVehiclePosX() < 32 && map.frog.getPlayerPosX() - vehicle.getVehiclePosX() > -32 &&
			vehicle.getVehiclePosY() == map.frog.getPlayerPosY()) {
			//System.exit(0); //Will close program on collision.
			}
		}
	
	public void vehicleCollisionLeftBound (Vehicle vehicle) {
		if (map.frog.getPlayerPosX() - vehicle.getVehiclePosX() > -32 && map.frog.getPlayerPosX() - vehicle.getVehiclePosX() < 32 &&
			vehicle.getVehiclePosY() == map.frog.getPlayerPosY()){
			//System.exit(0); //Will close program on collision.
		}
	}
	
	public void logBoundaryRightBound (Log log) {
		if (log.getLogPosX() >= (log.getLogLength() * 32)) {
			log.setLogPosX(-30.0);
		}
	}
	
	public void logBoundaryLeftBound (Log log) {
		if (log.getLogPosX() <= -(log.getLogLength() * 32)) {
			log.setLogPosX(640);
		}
	}
	
	public void turtleBoundaryLeftBound (Turtle turtle) {
		if (turtle.getTurtlePosX() <= -(turtle.getTurtleLength() * 32)) {
			turtle.setTurtlePosX(640);
		}
	}
	
	public boolean userOnTurtle2L (Turtle turtle) { //For Left-Bound Turtles
		if (turtle.getTurtlePosX() - map.frog.getPlayerPosX() < -16 && turtle.getTurtlePosX() - map.frog.getPlayerPosX() > -48 && 
				turtle.getTurtlePosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(turtle.getTurtlePosX() + 32);
			return true;
		}
		else if (turtle.getTurtlePosX() - map.frog.getPlayerPosX() < 16 && turtle.getTurtlePosX() - map.frog.getPlayerPosX() > -16 && 
				turtle.getTurtlePosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(turtle.getTurtlePosX());
			return true;
		}
		return false;
	}
	
	public boolean userOnTurtle3L (Turtle turtle) { //for Left-Bound Turtles
		if (turtle.getTurtlePosX() - map.frog.getPlayerPosX() < -48 && turtle.getTurtlePosX() - map.frog.getPlayerPosX() > -80 && 
				turtle.getTurtlePosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(turtle.getTurtlePosX() + 64);
			return true;
		}
		else if (turtle.getTurtlePosX() - map.frog.getPlayerPosX() < -16 && turtle.getTurtlePosX() - map.frog.getPlayerPosX() > -48 && 
				turtle.getTurtlePosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(turtle.getTurtlePosX() + 32);
			return true;
		}
		else if (turtle.getTurtlePosX() - map.frog.getPlayerPosX() < 16 && turtle.getTurtlePosX() - map.frog.getPlayerPosX() > -16 && 
				turtle.getTurtlePosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(turtle.getTurtlePosX());
			return true;
		}
		return false;
	}
	
	public boolean userOnLog3R (Log log) {
		if (log.getLogPosX() - map.frog.getPlayerPosX() < -48 && log.getLogPosX() - map.frog.getPlayerPosX() > -96 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 64);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -16 && log.getLogPosX() - map.frog.getPlayerPosX() > -48 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 32);
			return true;
		}
		//8 is REQUIRED in order to allow the log to move to a maximum speed of 8 pixels/period.
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < 8 && log.getLogPosX() - map.frog.getPlayerPosX() > -16 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX());
			return true;
		}
		return false;
	}
		
	public boolean userOnLog5R (Log log) {
		if (log.getLogPosX() - map.frog.getPlayerPosX() < -112 && log.getLogPosX() - map.frog.getPlayerPosX() > -180 &&
					log.getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(log.getLogPosX() + 128);
				return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -80 && log.getLogPosX() - map.frog.getPlayerPosX() > -112 && 
					log.getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(log.getLogPosX() + 96);
				return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -48 && log.getLogPosX() - map.frog.getPlayerPosX() > -80 && 
					log.getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(log.getLogPosX() + 64);
				return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -16 && log.getLogPosX() - map.frog.getPlayerPosX() > -48 && 
					log.getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(log.getLogPosX() + 32);
				return true;
		}
			//8 is REQUIRED in order to allow the log to move to a maximum speed of 8 pixels/period.
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < 8 && log.getLogPosX() - map.frog.getPlayerPosX() > -16 && 
					log.getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(log.getLogPosX());
				return true;
		}
		return false;
	}
	
	public boolean userOnLog7R (Log log) {
		if (log.getLogPosX() - map.frog.getPlayerPosX() < -176 && log.getLogPosX() - map.frog.getPlayerPosX() > -240 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 192);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -144 && log.getLogPosX() - map.frog.getPlayerPosX() > -176 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 160);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -112 && log.getLogPosX() - map.frog.getPlayerPosX() > -144 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 128);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -80 && log.getLogPosX() - map.frog.getPlayerPosX() > -112 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 96);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -48 && log.getLogPosX() - map.frog.getPlayerPosX() > -80 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 64);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -16 && log.getLogPosX() - map.frog.getPlayerPosX() > -48 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 32);
			return true;
		}
		//8 is REQUIRED in order to allow the log to move to a maximum speed of 8 pixels/period.
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < 8 && log.getLogPosX() - map.frog.getPlayerPosX() > -16 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX());
			return true;
		}
		return false;
	}
	
	public boolean userOnLog3L (Log log) { //For Left-Bound Logs
		if (log.getLogPosX() - map.frog.getPlayerPosX() < -48 && log.getLogPosX() - map.frog.getPlayerPosX() > -80 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 64);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -16 && log.getLogPosX() - map.frog.getPlayerPosX() > -48 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 32);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < 16 && log.getLogPosX() - map.frog.getPlayerPosX() > -16 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX());
			return true;
		}
		return false;
	}
	
	public boolean userOnLog5L (Log log) {
		if (log.getLogPosX() - map.frog.getPlayerPosX() < -112 && log.getLogPosX() - map.frog.getPlayerPosX() > -144 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 128);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -80 && log.getLogPosX() - map.frog.getPlayerPosX() > -112 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 96);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -48 && log.getLogPosX() - map.frog.getPlayerPosX() > -80 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 64);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -16 && log.getLogPosX() - map.frog.getPlayerPosX() > -48 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 32);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < 16 && log.getLogPosX() - map.frog.getPlayerPosX() > -16 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX());
			return true;
		}
		return false;
	}
	
	public boolean userOnLog7L (Log log) {
		if (log.getLogPosX() - map.frog.getPlayerPosX() < -176 && log.getLogPosX() - map.frog.getPlayerPosX() > -208 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 192);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -144 && log.getLogPosX() - map.frog.getPlayerPosX() > -176 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 160);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -112 && log.getLogPosX() - map.frog.getPlayerPosX() > -144 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 128);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -80 && log.getLogPosX() - map.frog.getPlayerPosX() > -112 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 96);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -48 && log.getLogPosX() - map.frog.getPlayerPosX() > -80 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 64);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < -16 && log.getLogPosX() - map.frog.getPlayerPosX() > -48 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX() + 32);
			return true;
		}
		else if (log.getLogPosX() - map.frog.getPlayerPosX() < 16 && log.getLogPosX() - map.frog.getPlayerPosX() > -16 && 
				log.getLogPosY() == map.frog.getPlayerPosY()) {
			map.frog.setPosX(log.getLogPosX());
			return true;
		}
		return false;
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
	

