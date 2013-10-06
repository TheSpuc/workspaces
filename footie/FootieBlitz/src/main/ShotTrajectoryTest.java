package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import model.Bold;
import model.Hjaelper;
import model.Match;
import model.Pitch;
import model.Player;
import model.PlayerAction;
import model.Match.MatchState;
import ai.AIDribble;

public class ShotTrajectoryTest {

	public static void main(String[] args) {
		Frame frame = new Frame();
	}
	
	private static class Frame extends JFrame {

		Graphics backbuffergc;
		Image backbuffer;
		
		int windowWidth = 400;
		int windowHeight = 400;
		
		int goalX = 350;
		int goalY = 175;
		int goalSize = 59;
		
		Point player = new Point(30, 30);
		Point target = new Point(goalX, goalY + 5);
		
		Match match = new Match(null);
		Pitch pitch = new Pitch(400, 0, 0, 350);
		Bold bold = new Bold(400, 0, 0, 350, pitch, match);
		
		int speed = 150;
		double curl = 0.01;
		double op = 20;
		double param = 223;
		
		public Frame(){
			setSize(windowWidth, windowHeight);
			setVisible(true);
			setTitle("Shot trajectory test");
			addMouseListener(new l());
			addKeyListener(new Keys());
		}
		
		private double adjustAForCurl(){
			
			double result = 0;
			
			double distance = Hjaelper.Distance(bold.getX(), bold.getY(), target.x, target.y);
			double boldX = bold.getX();
			double boldY = bold.getY();
			double boldA = bold.getA();
			double boldSpeed = bold.getSpeed();
			double boldZ = bold.getZ();
			double boldZForce = bold.getZForce();
			double boldCurl = bold.getCurl();
			
			//Check the trajectory of the ball until it's further away than the target
			while (Hjaelper.Distance(bold.getX(), bold.getY(), boldX, boldY) < distance){
				bold.update();
			}
				
			double angleDifference = Hjaelper.angleDifference(Math.atan2(target.y - boldY, target.x - boldX), 
					Math.atan2(bold.getY() - boldY, bold.getX() - boldX));
			
			System.out.println("angleDifference: " + angleDifference);
			if (angleDifference > 0){
				
			}
			else{
				
			}
			result = angleDifference;
			
			bold.setX(boldX);
			bold.setY(boldY);
			bold.setA(boldA - angleDifference);
			bold.setSpeed(boldSpeed);
			bold.setZ(boldZ);
			bold.setZForce(boldZForce);
			bold.setCurl(boldCurl);
			
			//Calculate how much curl we get based on zForce, speed and distance to target
			/*
			double totalCurl = curl * 2 * bold.getZForce();
			
			double percentDistance = distance / 250.0;
			if (percentDistance > 1) percentDistance = 1.0;
			totalCurl *= percentDistance;

			double percentSpeed = bold.getSpeed() / 200.0;
			if (percentSpeed > 1) percentSpeed = 1.0;
			totalCurl *= percentSpeed;
			
			System.out.println("percentDistance: " + percentDistance);
			System.out.println("percentSpeed: " + percentSpeed);	
			System.out.println("totalCurl: " + totalCurl);			
			
			*/
			return result;
		}
		
		public void paint(Graphics g) {

			if (backbuffergc == null) {
				backbuffer = createImage(windowWidth, windowHeight);
				backbuffergc = backbuffer.getGraphics();
			}

			backbuffergc.setColor(Color.GREEN.darker());
			backbuffergc.fillRect(0, 0, windowWidth, windowHeight);

			//maal
			backbuffergc.setColor(Color.WHITE);
			backbuffergc.drawRect(goalX, goalY, 10, goalSize);
			
			//target
			backbuffergc.setColor(Color.RED);
			backbuffergc.drawRect(target.x - 2, target.y - 2, 4, 4);

			backbuffergc.setColor(Color.GRAY);
			backbuffergc.drawLine(player.x, player.y, target.x, target.y);
			
			double distance = Hjaelper.Distance(player.x, player.y, target.x, target.y);
			int whileLimit = 500;

			backbuffergc.setColor(Color.WHITE);
			backbuffergc.drawString("Speed: " + speed, 10, 10);
			backbuffergc.drawString("Curl: " + curl, 10, 20);
			backbuffergc.drawString("Distance: " + distance, 10, 30);
			backbuffergc.drawString("Param: " + param, 10, 40);
			backbuffergc.drawString("Op: " + op, 10, 50);
			
			
			bold.setSpeed(speed);
			bold.setCurl(curl);
			bold.setX(player.x);
			bold.setY(player.y);
			bold.setZForce(op);
			bold.setZ(0);
			bold.setA(Math.atan2(target.x - player.x, target.y - player.y));
			
			adjustAForCurl();
			
			backbuffergc.setColor(Color.YELLOW);
			while (bold.getX() < goalX && whileLimit > 0){
				
				backbuffergc.drawRect((int)bold.getX(), (int)bold.getY(), 1, 1);
				bold.update();
				
				whileLimit--;
			}
			
			try{
				g.drawImage(backbuffer, 0, 32, windowWidth, windowHeight, this);
			}
			catch (NullPointerException e){

			}

		}
		
		class l implements java.awt.event.MouseListener{

			public void mouseClicked(MouseEvent arg0) {
				if (arg0.isShiftDown()){
					target.x = arg0.getX();
					target.y = arg0.getY();
				}
				else{
					player.x = arg0.getX();
					player.y = arg0.getY();
				}
				paint(getGraphics());
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		}
		
		class Keys implements java.awt.event.KeyListener{

			public void keyPressed(KeyEvent e) {

				if (e.isShiftDown()){
					if (e.getKeyChar() == '+'){
						speed++;
					}
					else if(e.getKeyChar() == '-'){
						speed--;
					}
				}
				else if (e.isAltDown()){
					if (e.getKeyChar() == '+'){
						op++;
					}
					else if(e.getKeyChar() == '-'){
						op--;
					}
				}
				else if (e.isControlDown()){
					if (e.getKeyChar() == '+'){
						param += 1;
					}
					else if(e.getKeyChar() == '-'){
						param -= 1;
					}
				}
				else{
					if (e.getKeyChar() == '+'){
						curl += 0.001;
					}
					else if(e.getKeyChar() == '-'){
						curl -= 0.001;
					}
				}
				paint(getGraphics());
			}

			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		}
	}

}
