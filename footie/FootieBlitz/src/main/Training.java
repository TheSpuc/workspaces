package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.sql.SQLException;	
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

import trainingmodules.ControlBall;
import trainingmodules.Dribbling;
import trainingmodules.PassAndMeetBall;
import trainingmodules.RunWithBall;
import trainingmodules.RunningAndAgility;
import trainingmodules.TrainingModule;

import data.DAOCake;
import model.Bold;
import model.Match;
import model.Player;
import model.PlayerAction;
import model.Settings;
import model.Sysout;
import model.Team;
import model.Pitch;
import model.Match.MatchState;

public class Training{
	
	static Training training;
	
	public static void main(String[] args) {
		
		//Meget ligesom matchengine men med trainingmodules som beskriver hvem der er med og hvad de skal gøre
		
		training = new Training();
		
		TrainingFrame tf = training.new TrainingFrame();
		
	}
	

	public class TrainingFrame extends JFrame{

		String debug = "";
		
		Timer timer = new Timer();
		Timer loop = new Timer();

		int goalViewX, goalViewY, goalViewPos = 600;
		
		long outTime = 0;
		long lastUpdate;
		long frames = 0;
		long fps = 0;
		long runspeed = System.nanoTime();
		int goalX, goalY, goalSize;
		int goal2X, goal2Y, goal2Size;
		Timer matchTimer;

		int windowHeight = 750;
		int windowWidth = 1050;

		int pitchHeight = 513;
		int pitchWidth = 880;

		int pitchPosX = 90;
		int pitchPosY = 80;

		
		private boolean loadFinished = false;
		boolean pause = true;

		Thread mainloop = null;

		Graphics backbuffergc;
		Image backbuffer;

		Settings settings = Settings.getInstance();
		ArrayList<Bold> bolde = new ArrayList<Bold>();
		ArrayList<Player> players = new ArrayList<Player>();
		
		long lastCannon;
		int curBall;
		
		Random r = new Random();
		
		TrainingModule module;
		
		public TrainingFrame(){

			init();
		}

		public void update(){
			
			if (!pause){
				module.update();
				
				
			}
			paint(getGraphics()); 
		}
		
		public void init() {
			
			//module = new RunWithBall(settings);
			//module = new PassAndMeetBall(settings);
			module = new RunningAndAgility(settings);
			//module = new ControlBall(settings);
//			module = new Dribbling(Settings);
			
			bolde.addAll(module.getBolde());
			
			addMouseListener(new MouseListener());
			addKeyListener(new Keys());
			addWindowStateListener(new WindowStateListener() {
				
				public void windowStateChanged(WindowEvent arg0) {
					if (arg0.getNewState() == WindowEvent.WINDOW_CLOSING)
						try {
							DAOCake.closeConnection();
//							System.out.println("Vinduet lukket - db forbindelse lukket");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			});
			
			setSize(windowWidth, windowHeight);
			setVisible(true);
			setTitle("Training");
			
			backbuffer = createImage(windowWidth, windowHeight);
			backbuffergc = backbuffer.getGraphics();

			goalSize = 59;
			goalX = pitchPosX + pitchWidth;
			goalY = (pitchHeight - goalSize) / 2 + pitchPosY;
			goal2Size = 59;
			goal2X = pitchPosX;
			goal2Y = (pitchHeight - goalSize) / 2 + pitchPosY;
			
			matchTimer = new Timer();
			
			loop.schedule(new realTime(), 0, 30);
			
			loadFinished = true;
			debug += " - Init done";
			pause = false;
		}

		public void paint(Graphics g) {

			if (loadFinished){
				if (backbuffergc == null) {
					backbuffer = createImage(windowWidth, windowHeight);
					backbuffergc = backbuffer.getGraphics();
				}

				Font font = new Font("Helvetica", Font.PLAIN,  10);
				backbuffergc.setFont(font);

				Graphics2D g2d = (Graphics2D)g;
				FontMetrics fontMetrics = getFontMetrics(font);


				//gr¾s
				backbuffergc.setColor(Color.GREEN.darker());
				backbuffergc.fillRect(0, 0, windowWidth, windowHeight);

				//Football pitch
				backbuffergc.setColor(Color.WHITE);
				backbuffergc.drawRect(pitchPosX, pitchPosY, pitchWidth, pitchHeight);//Banen
				backbuffergc.drawLine(pitchWidth/2+pitchPosX, pitchPosY, pitchWidth / 2 + pitchPosX, pitchPosY + pitchHeight);//midterlinje
				backbuffergc.drawRect(pitchPosX, (pitchHeight - 322) / 2 + pitchPosY, 132, 322);//straffefelt 178
				backbuffergc.drawRect(pitchPosX + pitchWidth - 132, (pitchHeight - 322) / 2 + 80, 132, 322);//straffefelt
				backbuffergc.drawRect(pitchPosX, (pitchHeight - 146) / 2 + pitchPosY, 44, 146);//målfelt
				backbuffergc.drawRect(pitchPosX + pitchWidth - 44, (pitchHeight - 146) / 2 + 80, 44, 146);//målfelt
				backbuffergc.drawOval((pitchWidth - 146) / 2 + pitchPosX, (pitchHeight - 146) / 2 + pitchPosY, 146, 146);//midtercirkel
				backbuffergc.drawArc(pitchPosX + 88 - 73, pitchHeight / 2 + pitchPosY - 73, 146, 146, -52, 105);//straffehalv-cirkel
				backbuffergc.drawArc(pitchPosX + pitchWidth - 88 - 73, pitchHeight / 2 + pitchPosY - 73, 146, 146, 128, 105);//straffehalv-cirkel
				backbuffergc.drawArc(pitchPosX - 8, pitchPosY - 8, 16, 16, 0, -90);//Hjørneflag
				backbuffergc.drawArc(pitchPosX + pitchWidth - 8, pitchPosY - 8, 16, 16, -90, -90);//Hjørneflag
				backbuffergc.drawArc(pitchPosX - 8, pitchPosY + pitchHeight - 8, 16, 16, 0, 90);//Hjørneflag
				backbuffergc.drawArc(pitchPosX + pitchWidth - 8, pitchPosY + pitchHeight - 8, 16, 16, -180, -90);//Hjørneflag		
				backbuffergc.fillOval(pitchWidth / 2 + pitchPosX - 3, pitchHeight / 2 + pitchPosY - 3, 6, 6);//midterpletten
				backbuffergc.fillOval(pitchPosX + 88 - 1, pitchHeight / 2 + pitchPosY - 1, 3, 3);//straffepletten
				backbuffergc.fillOval(pitchPosX + pitchWidth - 88 - 1, pitchHeight / 2 + pitchPosY - 1, 3, 3);//straffepletten

				//frame sper second
				backbuffergc.setColor(Color.RED);
				backbuffergc.drawString(Long.toString(fps), 10, 20);

				for (Bold bold : bolde){
					//boldens skygge

					int ballShadowSize = (int)(8 - bold.getZ() / 350);

					backbuffergc.setColor(Color.DARK_GRAY);
					backbuffergc.fillOval((int)(Math.round(bold.getX()) + bold.getZ() / 100 - ballShadowSize / 2), 
							(int)(Math.round(bold.getY()) +bold.getZ() / 100 - ballShadowSize / 2), ballShadowSize, ballShadowSize);
					
					
					int ballSize = (int)(8 + bold.getZ() / 70);
					
					//bolden under spillerne hvis den er lav eller tegner vi den først efter spilleren
					if (bold.getZ() < 170){
						//bold
						backbuffergc.setColor(Color.WHITE);
						backbuffergc.fillOval((int)Math.round(bold.getX()) - ballSize / 2, (int)Math.round(bold.getY()) - ballSize / 2, ballSize, ballSize);
						backbuffergc.setColor(Color.BLACK);
						backbuffergc.drawOval((int)Math.round(bold.getX()) - ballSize / 2, (int)Math.round(bold.getY()) - ballSize / 2, ballSize, ballSize);
					}
				}
				

				
				for (Player p : module.getTeamA().getPlayers()){
					//spiller - cirkel med kant 
					backbuffergc.setColor(module.getTeamA().getColor());
					backbuffergc.fillOval((int)p.getX() - 7, (int)p.getY() - 7, 14, 14);
					backbuffergc.setColor(module.getTeamA().getColor2());
					backbuffergc.drawOval((int)p.getX() - 7, (int)p.getY() - 7, 14, 14);
//					target
					//			backbuffergc.drawLine((int)p.getTargetX(), (int)p.getTargetY(), (int)p.getTargetX(), (int)p.getTargetY());

					//retning
					double a = p.getA();
					double tempX = p.getX() + 8 * (Math.sin(a));
					double tempY = p.getY() + 8 * (Math.cos(a));
					
					backbuffergc.drawLine((int)p.getX(), (int)p.getY(), (int)tempX, (int)tempY);

					//tr¿jenummer
					int nrx = 6;
					if (p.getShirtNumber() < 10)
						nrx = 2;

					backbuffergc.setColor(module.getTeamA().getColor2());
					backbuffergc.drawString(Integer.toString(p.getShirtNumber()),(int)p.getX() - nrx, (int)p.getY() + 5);

					if (p.getPlayerAction() == PlayerAction.dribble || p.getPlayerAction() == PlayerAction.receiving_pass){
						int wx = fontMetrics.stringWidth(p.getLastname()) / 2;

						backbuffergc.drawString(p.getLastname(), (int)p.getX() - wx, (int)p.getY() + 18);
					}
					
//					linie til ham der markeres
//					if (p.getCurrentlyMarking() != null)
//						backbuffergc.drawLine((int)p.getX(), (int)p.getY(), (int)p.getCurrentlyMarking().getX(), (int)p.getCurrentlyMarking().getY());
				}


				for (Player p : module.getTeamB().getPlayers()){
					//spiller - cirkel med kant 
					backbuffergc.setColor(module.getTeamB().getColor());
					backbuffergc.fillOval((int)p.getX()- 7, (int)p.getY() - 7, 14, 14);
					backbuffergc.setColor(module.getTeamB().getColor2());
					backbuffergc.drawOval((int)p.getX() - 7, (int)p.getY() - 7, 14, 14);
					//target
					//			backbuffergc.drawLine((int)p.getTargetX(), (int)p.getTargetY(), (int)p.getTargetX(), (int)p.getTargetY());

					//retning
					double a = p.getA();
					double tempX = p.getX() + 1000 * (Math.sin(a) * 0.007);
					double tempY = p.getY() + 1000 * (Math.cos(a) * 0.007);
					backbuffergc.drawLine((int)p.getX(), (int)p.getY(), (int)tempX, (int)tempY);

					//tr¿jenummer		
					int nrx = 6;
					if (p.getShirtNumber() < 10)
						nrx = 2;

					backbuffergc.setColor(module.getTeamB().getColor2());
					backbuffergc.drawString(Integer.toString(p.getShirtNumber()),(int)p.getX() - nrx, (int)p.getY() + 5);

					
				}

				//maalene
				//Stolper tegnes nogle pixels længere ude for at gøre plads til urealistisk stor bold
				backbuffergc.setColor(Color.WHITE);
				backbuffergc.drawRect(goalX, goalY, 10, goalSize);
				backbuffergc.drawRect(goal2X-10, goal2Y, 10, goal2Size);

				
//				bolden over spillerne og maalene hvis den er hoej
				for (Bold bold : bolde){
					if (bold.getZ() >= 170){
						//bold
						int ballSize = (int)(8 + bold.getZ() / 100);
						backbuffergc.setColor(Color.WHITE);
						backbuffergc.fillOval((int)Math.round(bold.getX()) - ballSize / 2, (int)Math.round(bold.getY()) - ballSize / 2, ballSize, ballSize);
						backbuffergc.setColor(Color.BLACK);
						backbuffergc.drawOval((int)Math.round(bold.getX()) - ballSize / 2, (int)Math.round(bold.getY()) - ballSize / 2, ballSize, ballSize);
					}
					
					
					if (bold.isShot() && Math.abs(bold.getY() - (goalY + 30)) < 45){
						goalViewX = goalViewPos + 184 / 2 + (int)((bold.getY() - (goalY + 30)) * 184 / 59d);
						goalViewY = pitchPosY + pitchHeight + 25 + (int)(61 - (bold.getZ() / 244d * 61));
					}
				}

				

				
				backbuffergc.setColor(Color.WHITE);
				backbuffergc.fillOval(goalViewX - 2, goalViewY - 2, 4, 4);
				
//				backbuffergc.drawLine(goalViewPos, pitchPosY + pitchHeight + 25 + 61, goalViewPos, pitchPosY + pitchHeight + 25);
//				backbuffergc.drawLine(goalViewPos, pitchPosY + pitchHeight + 25, goalViewPos + 184, pitchPosY + pitchHeight + 25);
//				backbuffergc.drawLine(goalViewPos + 184, pitchPosY + pitchHeight + 25 + 61, goalViewPos + 184, pitchPosY + pitchHeight + 25);
				

				try{
					g.drawImage(backbuffer, 0, 22, windowWidth, windowHeight, this);
				}
				catch (NullPointerException e){
					
				}
				
			}
		}
		class realTime extends TimerTask{
			public void run(){
				update();
			}
		}

		class Keys implements java.awt.event.KeyListener{

			public void keyPressed(KeyEvent e) {

				if (e.getKeyChar() == 'l'){
					pause = !pause;
				}
				else if (e.getKeyChar() == 's'){
					module.getTeamA().getPlayers().get(0).topSpeed += 10;
					System.out.println("Topspeed: " + module.getTeamA().getPlayers().get(0).topSpeed);
				}
				else if (e.getKeyChar() == 'x'){
					module.getTeamA().getPlayers().get(0).topSpeed -= 10;
					System.out.println("Topspeed: " + module.getTeamA().getPlayers().get(0).topSpeed);
				}
				else if (e.getKeyChar() == 'a'){
					module.getTeamA().getPlayers().get(0).acceleration += 10;
					System.out.println("acceleration: " + module.getTeamA().getPlayers().get(0).acceleration);
				}
				else if (e.getKeyChar() == 'z'){
					module.getTeamA().getPlayers().get(0).acceleration -= 10;
					System.out.println("acceleration: " + module.getTeamA().getPlayers().get(0).acceleration);
				}
				else if (e.getKeyChar() == 'd'){
					module.getTeamA().getPlayers().get(0).dribbling += 10;
					System.out.println("dribbling: " + module.getTeamA().getPlayers().get(0).dribbling);
				}
				else if (e.getKeyChar() == 'c'){
					module.getTeamA().getPlayers().get(0).dribbling -= 10;
					System.out.println("dribbling: " + module.getTeamA().getPlayers().get(0).dribbling);
				}
				else if (e.getKeyChar() == 'f'){
					module.getTeamA().getPlayers().get(0).reaction += 10;
					System.out.println("reaction: " + module.getTeamA().getPlayers().get(0).reaction);
				}
				else if (e.getKeyChar() == 'v'){
					module.getTeamA().getPlayers().get(0).reaction -= 10;
					System.out.println("reaction: " + module.getTeamA().getPlayers().get(0).reaction);
				}
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}
		
		class MouseListener implements java.awt.event.MouseListener{

			public void mouseClicked(MouseEvent e) {
				module.clickReceived(e.getX(), e.getY());
			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}


		}
	}

}
