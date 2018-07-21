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
		map.paintComponent(g);
	}

	public void actionPerformed(ActionEvent e) {
		userOnLog2L(map.logLeft);
		userOnLog3L(map.logRight);
		logBoundaryRightBound(map.logRight);
		vehicleBoundaryRightBound(map.vehicleRight64);
		vehicleCollisionRightBound(map.vehicleRight64);
		vehicleBoundaryLeftBound(map.vehicleLeft);
		vehicleCollisionLeftBound(map.vehicleLeft);
		logBoundaryLeftBound(map.logLeft);
		
		repaint();
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
	
	public void vehicleBoundaryRightBound(Vehicle[] vehicleArray) {
		for (int i = 0; i < vehicleArray.length; i++) {
			if (vehicleArray[i].getVehiclePosX() >= 640.0) {
			vehicleArray[i].setVehiclePosX(-30.0);
			}
		}
	}
	
	public void vehicleBoundaryLeftBound(Vehicle[] vehicleArray) {
		for (int i = 0; i < vehicleArray.length; i++) {
			if (vehicleArray[i].getVehiclePosX() <= 0.0) {
				vehicleArray[i].setVehiclePosX(670.0);
			}
		}
	}

	public void vehicleCollisionRightBound(Vehicle[] vehicleArray) {
		for (int i = 0; i < vehicleArray.length; i++) {
			if (map.frog.getPlayerPosX() - vehicleArray[i].getVehiclePosX() < 32 && map.frog.getPlayerPosX() - vehicleArray[i].getVehiclePosX() > -32 &&
					vehicleArray[i].getVehiclePosY() == map.frog.getPlayerPosY()) {
				System.exit(0); //Will close program on collision.
			}
		}
	}
	
	public void vehicleCollisionLeftBound (Vehicle[] vehicleArray) {
		for (int i = 0; i < vehicleArray.length; i++) {
			if (map.frog.getPlayerPosX() - vehicleArray[i].getVehiclePosX() > -32 && map.frog.getPlayerPosX() - vehicleArray[i].getVehiclePosX() < 32 &&
					vehicleArray[i].getVehiclePosY() == map.frog.getPlayerPosY()){
				System.exit(0); //Will close program on collision.
			}
		}
	}
	
	public void logBoundaryRightBound (Log[] logArray) {
		for (int i = 0; i < logArray.length; i++) {
			if(logArray[i].getLogPosX() >= 640.0) {
				logArray[i].setLogPosX(-30.0);
			}
		}
	}
	
	public void logBoundaryLeftBound (Log[] logArray) {
		for (int i = 0; i < logArray.length; i++) {
			if (logArray[i].getLogPosX() <= -30) {
				logArray[i].setLogPosX(640);
			}
		}
	}
	
	public void userOnLog2L (Log[] logArray) { //For Left-Bound Logs
		for (int i = 0; i < logArray.length; i++) {
			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < -16 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -48 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX() + 32);
			}
			
			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < 16 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -16 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX());
			}
		}
	}
	
	public void userOnLog3L (Log[] logArray) { //For Left-Bound Logs
		for (int i = 0; i < logArray.length; i++) {
			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < -48 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -80 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX() + 64);
			}
			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < -16 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -48 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX() + 32);
			}
			
			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < 16 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -16 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX());
			}
		}
	}
	
	public void userOnLog5L (Log[] logArray) {
		for (int i = 0; i < logArray.length; i++) {
			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < -112 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -144 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX() + 128);
			}
			
			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < -80 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -112 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX() + 96);
			}
			
			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < -48 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -80 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX() + 64);
			}
			
			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < -16 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -48 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX() + 32);
			}
	
			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < 16 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -16 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX());
			}
		}
	}
	
	public void userOnLog7L (Log[] logArray) {
		for (int i = 0; i < logArray.length; i++) {
			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < -176 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -208 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX() + 192);
			}
		
			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < -144 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -176 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX() + 160);
			}
			
			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < -112 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -144 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX() + 128);
			}
		
			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < -80 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -112 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX() + 96);
			}
		
			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < -48 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -80 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX() + 64);
			}
		
			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < -16 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -48 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX() + 32);
			}

			if (logArray[i].getLogPosX() - map.frog.getPlayerPosX() < 16 && logArray[i].getLogPosX() - map.frog.getPlayerPosX() > -16 && logArray[i].getLogPosY() == map.frog.getPlayerPosY()) {
				map.frog.setPosX(logArray[i].getLogPosX());
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
	
