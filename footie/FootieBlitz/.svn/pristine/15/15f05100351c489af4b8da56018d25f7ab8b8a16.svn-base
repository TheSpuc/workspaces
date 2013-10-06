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
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;	
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

import ai.AIDribble;

import states.StateHoldUpBall;

import data.DAOCake;
import model.Bold;
import model.Hjaelper;
import model.Match;
import model.MatchEngine;
import model.Player;
import model.PlayerAction;
import model.PlayerMatchStats;
import model.PlayerRole;
import model.Team;
import model.Pitch;
import model.Match.MatchState;

public class MatchFrame extends JFrame{

	String debug = "";
	
	Timer timer = new Timer();
	Timer loop = new Timer();

	int goalViewX, goalViewY, goalViewPos = 850;
	
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

	Thread mainloop = null;

	Graphics backbuffergc;
	Image backbuffer;

	SettingsFrame settingsFrame;

	MatchEngine matchEngine;
	Player dragPlayer;
	
	public MatchFrame(int matchId, int leagueId){

		matchEngine = new MatchEngine(matchId, leagueId);
		
		init();
	}

	public void update(){
		
		matchEngine.setSettings(settingsFrame.getSettings());

		paint(getGraphics()); 
	}
	
	public void init() {

		
		
		settingsFrame = new SettingsFrame(matchEngine.getTeamA(), matchEngine.getTeamB());
		
		addMouseMotionListener(new MouseMoveListener());
		addMouseListener(new MouseListener());
		addKeyListener(new Keys());
		addWindowStateListener(new WindowStateListener() {
			
			public void windowStateChanged(WindowEvent arg0) {
				if (arg0.getNewState() == WindowEvent.WINDOW_CLOSING)
					try {
						DAOCake.closeConnection();
//						System.out.println("Viduet lukket - db forbindelse lukket");
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
		setTitle("MainFrame");
		
		backbuffer = createImage(windowWidth, windowHeight);
		backbuffergc = backbuffer.getGraphics();

		debug += "Settings done";
		
		goalSize = 59;
		goalX = pitchPosX + pitchWidth;
		goalY = (pitchHeight - goalSize) / 2 + pitchPosY;
		goal2Size = 59;
		goal2X = pitchPosX;
		goal2Y = (pitchHeight - goalSize) / 2 + pitchPosY;
		
		settingsFrame.visSpillere();

		matchTimer = new Timer();
		matchTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				setTitle("F - " + matchEngine.getMins());
				
			}
		}, 5000, 5000);
		
		loop.schedule(new realTime(), 0, 30);
		
		loadFinished = true;
		debug += " - Init done";
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
			/////////
//			backbuffergc.setColor(Color.WHITE);
//			matchEngine.getTeamA().setColor(Color.white);
//			matchEngine.getTeamA().setColor2(Color.black);
//			matchEngine.getTeamB().setColor(Color.gray.brighter());
//			matchEngine.getTeamB().setColor2(Color.black);
			
			////////////
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
//			backbuffergc.setColor(Color.RED);
//			backbuffergc.drawString(Long.toString(fps), 10, 20);

			//boldens skygge

			int ballShadowSize = (int)(8 - matchEngine.getBold().getZ() / 350);

			backbuffergc.setColor(Color.DARK_GRAY);
			backbuffergc.fillOval((int)(matchEngine.getBold().getX() + matchEngine.getBold().getZ() / 100 - ballShadowSize / 2), 
					(int)(matchEngine.getBold().getY() + matchEngine.getBold().getZ() / 100 - ballShadowSize / 2), ballShadowSize, ballShadowSize);
			
			
			int ballSize = (int)(8 + matchEngine.getBold().getZ() / 70);
			
			//bolden under spillerne hvis den er lav
			if (matchEngine.getBold().getZ() < 170){
				//bold
				backbuffergc.setColor(Color.WHITE);
				backbuffergc.fillOval((int)Math.round(matchEngine.getBold().getX()) - ballSize / 2, (int)Math.round(matchEngine.getBold().getY()) - ballSize / 2, ballSize, ballSize);
				backbuffergc.setColor(Color.BLACK);
				backbuffergc.drawOval((int)Math.round(matchEngine.getBold().getX()) - ballSize / 2, (int)Math.round(matchEngine.getBold().getY()) - ballSize / 2, ballSize, ballSize);
			}

			//bold hoejdebar
			backbuffergc.setColor(Color.cyan);
			backbuffergc.fillRect(pitchPosX - 30, pitchPosY + pitchHeight - (int)(matchEngine.getBold().getZ() * 0.1), 
					5, (int)(matchEngine.getBold().getZ() * 0.1));
			
			
			//Subs
			for (Player p : matchEngine.getTeamA().getUsedSubs()){
				//spiller - cirkel med kant 
				backbuffergc.setColor(matchEngine.getTeamA().getColor());
				backbuffergc.fillOval((int)p.getX() - 7, (int)p.getY() - 7, 14, 14);
				backbuffergc.setColor(matchEngine.getTeamA().getColor2());
				backbuffergc.drawOval((int)p.getX() - 7, (int)p.getY() - 7, 14, 14);

				//tr¿jenummer
				int nrx = 6;
				if (p.getShirtNumber() < 10)
					nrx = 2;

				backbuffergc.setColor(matchEngine.getTeamA().getColor2());
				backbuffergc.drawString(Integer.toString(p.getShirtNumber()),(int)p.getX() - nrx, (int)p.getY() + 5);
			}
			for (Player p : matchEngine.getTeamB().getUsedSubs()){
				//spiller - cirkel med kant 
				backbuffergc.setColor(matchEngine.getTeamB().getColor());
				backbuffergc.fillOval((int)p.getX() - 7, (int)p.getY() - 7, 14, 14);
				backbuffergc.setColor(matchEngine.getTeamB().getColor2());
				backbuffergc.drawOval((int)p.getX() - 7, (int)p.getY() - 7, 14, 14);

				//tr¿jenummer
				int nrx = 6;
				if (p.getShirtNumber() < 10)
					nrx = 2;

				backbuffergc.setColor(matchEngine.getTeamB().getColor2());
				backbuffergc.drawString(Integer.toString(p.getShirtNumber()),(int)p.getX() - nrx, (int)p.getY() + 5);
			}
			for (Player p : matchEngine.getTeamA().getSubs()){
				//spiller - cirkel med kant 
				backbuffergc.setColor(matchEngine.getTeamA().getColor());
				backbuffergc.fillOval((int)p.getX() - 7, (int)p.getY() - 7, 14, 14);
				backbuffergc.setColor(matchEngine.getTeamA().getColor2());
				backbuffergc.drawOval((int)p.getX() - 7, (int)p.getY() - 7, 14, 14);

				//tr¿jenummer
				int nrx = 6;
				if (p.getShirtNumber() < 10)
					nrx = 2;

				backbuffergc.setColor(matchEngine.getTeamA().getColor2());
				backbuffergc.drawString(Integer.toString(p.getShirtNumber()),(int)p.getX() - nrx, (int)p.getY() + 5);
			}
			for (Player p : matchEngine.getTeamB().getSubs()){
				//spiller - cirkel med kant 
				backbuffergc.setColor(matchEngine.getTeamB().getColor());
				backbuffergc.fillOval((int)p.getX() - 7, (int)p.getY() - 7, 14, 14);
				backbuffergc.setColor(matchEngine.getTeamB().getColor2());
				backbuffergc.drawOval((int)p.getX() - 7, (int)p.getY() - 7, 14, 14);

				//tr¿jenummer
				int nrx = 6;
				if (p.getShirtNumber() < 10)
					nrx = 2;

				backbuffergc.setColor(matchEngine.getTeamB().getColor2());
				backbuffergc.drawString(Integer.toString(p.getShirtNumber()),(int)p.getX() - nrx, (int)p.getY() + 5);
			}
			
			
			for (Player p : matchEngine.getTeamA().getPlayers()){
				//spiller - cirkel med kant 
				backbuffergc.setColor(matchEngine.getTeamA().getColor());
				backbuffergc.fillOval((int)p.getX() - 7, (int)p.getY() - 7, 14, 14);
				backbuffergc.setColor(matchEngine.getTeamA().getColor2());
				backbuffergc.drawOval((int)p.getX() - 7, (int)p.getY() - 7, 14, 14);
//				target
//				backbuffergc.drawLine((int)p.getTargetX(), (int)p.getTargetY(), (int)p.getTargetX(), (int)p.getTargetY());

				//retning
				double a = p.getA();
				double tempX = p.getX() + 8 * (Math.sin(a));
				double tempY = p.getY() + 8 * (Math.cos(a));
				backbuffergc.drawLine((int)p.getX(), (int)p.getY(), (int)tempX, (int)tempY);

				//tr¿jenummer
				int nrx = 6;
				if (p.getShirtNumber() < 10)
					nrx = 2;

				backbuffergc.setColor(matchEngine.getTeamA().getColor2());
				backbuffergc.drawString(Integer.toString(p.getShirtNumber()),(int)p.getX() - nrx, (int)p.getY() + 5);

				if (p.getPlayerAction() == PlayerAction.dribble || p.getPlayerAction() == PlayerAction.receiving_pass){
					int wx = fontMetrics.stringWidth(p.getLastname()) / 2;

					backbuffergc.drawString(p.getLastname(), (int)p.getX() - wx, (int)p.getY() + 18);
				}
				
				
				backbuffergc.setColor(matchEngine.getTeamA().getColor2());
				backbuffergc.drawLine((int)p.getX(), (int)p.getY(), (int)p.getXIn(15), (int)p.getYIn(15));
				
				if (p.getState().equals(p.getStateDribbling())){
					
					AIDribble ai = p.getStateHasBall().getAiDribble();
					backbuffergc.setColor(Color.MAGENTA);
					
					backbuffergc.drawLine(ai.getXLeftBehindOpp(), ai.getYLeftBehindOpp(), ai.getXLeftBehindOpp(), ai.getYLeftBehindOpp());
					backbuffergc.drawLine(ai.getXRightBehindOpp(), ai.getYRightBehindOpp(), ai.getXRightBehindOpp(), ai.getYRightBehindOpp());
					backbuffergc.drawLine(ai.getXRightOfOpp(), ai.getYRightOfOpp(), ai.getXRightOfOpp(), ai.getYRightOfOpp());
					backbuffergc.drawLine(ai.getXLeftOfOpp(), ai.getYLeftOfOpp(), ai.getXLeftOfOpp(), ai.getYLeftOfOpp());
				}
//				backbuffergc.setColor(Color.gray.lightGray);
//				backbuffergc.drawLine((int)p.getX(), (int)p.getY(), (int)p.getTargetX(), (int)p.getTargetY());
//				linie til dem der markeres
				try{
					if (p.getCurrentlyMarking().size() > 0)
						synchronized (p.getCurrentlyMarking()) {
							for (Player mark : p.getCurrentlyMarking())
								backbuffergc.drawLine((int)p.getX(), (int)p.getY(), (int)mark.getX(), (int)mark.getY());
						}
				}
				catch (Exception e) {
					// TODO: handle exception
				}
				
				//linie til holdupballtarget
				try{
					if (p.getPlayerAction().equals(PlayerAction.holdUpBall)){

						backbuffergc.setColor(Color.YELLOW);
						for (Point point : p.getStateHasBall().getAiHoldUpBall().getPointsAwayFromOpp())
							backbuffergc.drawLine((int)p.getX(), (int)p.getY(), point.x, point.y);

						backbuffergc.drawLine((int)p.getX(), (int)p.getY(), (int)p.getStateHasBall().getAiHoldUpBall().getTarget().x, (int)p.getStateHasBall().getAiHoldUpBall().getTarget().y);
					}
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			}


			for (Player p : matchEngine.getTeamB().getPlayers()){
				//spiller - cirkel med kant 
				backbuffergc.setColor(matchEngine.getTeamB().getColor());
				backbuffergc.fillOval((int)p.getX()- 7, (int)p.getY() - 7, 14, 14);
				backbuffergc.setColor(matchEngine.getTeamB().getColor2());
				backbuffergc.drawOval((int)p.getX() - 7, (int)p.getY() - 7, 14, 14);
				
				//retning
				double a = p.getA();
				double tempX = p.getX() + 1000 * (Math.sin(a) * 0.007);
				double tempY = p.getY() + 1000 * (Math.cos(a) * 0.007);
				backbuffergc.drawLine((int)p.getX(), (int)p.getY(), (int)tempX, (int)tempY);

				//tr¿jenummer		
				int nrx = 6;
				if (p.getShirtNumber() < 10)
					nrx = 2;

				backbuffergc.setColor(matchEngine.getTeamB().getColor2());
				backbuffergc.drawString(Integer.toString(p.getShirtNumber()),(int)p.getX() - nrx, (int)p.getY() + 5);

				if (matchEngine.getBold().getLastTouch() != null && matchEngine.getBold().getLastTouch().equals(p) || p.getPlayerAction() == PlayerAction.dribble || 
						p.getPlayerAction() == PlayerAction.receiving_pass){
					int wx = fontMetrics.stringWidth(p.getLastname()) / 2;

					backbuffergc.drawString(p.getLastname(), (int)p.getX() - wx, (int)p.getY() + 18);
				}
				
				
				if (p.getState().equals(p.getStateDribbling())){
					
					AIDribble ai = p.getStateHasBall().getAiDribble();
					backbuffergc.setColor(Color.MAGENTA);
					
					backbuffergc.drawLine(ai.getXLeftBehindOpp(), ai.getYLeftBehindOpp(), ai.getXLeftBehindOpp(), ai.getYLeftBehindOpp());
					backbuffergc.drawLine(ai.getXRightBehindOpp(), ai.getYRightBehindOpp(), ai.getXRightBehindOpp(), ai.getYRightBehindOpp());
					backbuffergc.drawLine(ai.getXRightOfOpp(), ai.getYRightOfOpp(), ai.getXRightOfOpp(), ai.getYRightOfOpp());
					backbuffergc.drawLine(ai.getXLeftOfOpp(), ai.getYLeftOfOpp(), ai.getXLeftOfOpp(), ai.getYLeftOfOpp());
				}
	
				//target
//				backbuffergc.setColor(Color.gray.lightGray);
//				backbuffergc.drawLine((int)p.getX(), (int)p.getY(), (int)p.getTargetX(), (int)p.getTargetY());
				
//				linie til dem der markeres
				try{
					if (p.getCurrentlyMarking().size() > 0)
						synchronized (p.getCurrentlyMarking()) {
							for (Player mark : p.getCurrentlyMarking())
								backbuffergc.drawLine((int)p.getX(), (int)p.getY(), (int)mark.getX(), (int)mark.getY());
						}
				}
				catch (Exception e) {
					// TODO: handle exception
				}
				
			}

//			backbuffergc.setColor(Color.ORANGE);
//			ArrayList<Point> defLine = getThroughLine(teamB);
//			for (int i = 0; i < defLine.size() - 1; i++)
//				backbuffergc.drawLine(defLine.get(i).x, defLine.get(i).y, defLine.get(i+1).x, defLine.get(i+1).y);
//			
//			for (Player p : teamA.getPlayers())
//				for (int i = 0; i < defLine.size() -1; i++){
//
//					//Vi tjekker kun på det liniestykke der er ud for angriberen
//					if (defLine.get(i).y > p.getY() && defLine.get(i+1).y < p.getY()){
//						double lineDir = Math.PI * 2 + Math.atan2(defLine.get(i+1).x - defLine.get(i).getX(), defLine.get(i+1).y - defLine.get(i).y);
//						double lineX = defLine.get(i).x + (Math.sin(lineDir) * (Math.abs(p.getY() - defLine.get(i).y) / Math.abs(Math.cos(lineDir))));
//
//						//hvis han er længere fremme end forsvarslinien
//						if (Math.abs(lineX - p.getOppTeam().getGoalX()) > Math.abs(p.getX() - p.getOppTeam().getGoalX())){
//
//							Point distPoint = Hjaelper.intersection(defLine.get(i).x, defLine.get(i).y, defLine.get(i+1).x, defLine.get(i+1).y, (int)p.getX(), (int)p.getY(), (int)(p.getX() + Math.sin(lineDir - Math.PI / 2) * 500), (int)(p.getY() + Math.cos(lineDir - Math.PI / 2) * 500));
//							double throughDist = Hjaelper.Distance(distPoint.x, distPoint.y, p.getX(), p.getY());
////							System.out.println("Through dist: " + throughDist);
//
//							int throughX = (int)p.getX() + 50;
//							if (p.getMyTeam().getGoalX() > p.getOppTeam().getGoalX())
//								throughX = (int)p.getX() - 50;
//							int throughY = (int)p.getY();
//
//							backbuffergc.fillOval(throughX, throughY, 5, 5);
//						}
//					}
//				}
//			
//			defLine = getThroughLine(teamA);
//			for (int i = 0; i < defLine.size() - 1; i++)
//				backbuffergc.drawLine(defLine.get(i).x, defLine.get(i).y, defLine.get(i+1).x, defLine.get(i+1).y);
//
//			for (Player p : teammatchEngine.getBold().getPlayers())
//				for (int i = 0; i < defLine.size() -1; i++){
//
//					//Vi tjekker kun på det liniestykke der er ud for angriberen
//					if (defLine.get(i).y > p.getY() && defLine.get(i+1).y < p.getY()){
//						double lineDir = Math.PI * 2 + Math.atan2(defLine.get(i+1).x - defLine.get(i).getX(), defLine.get(i+1).y - defLine.get(i).y);
//						double lineX = defLine.get(i).x + (Math.sin(lineDir) * (Math.abs(p.getY() - defLine.get(i).y) / Math.abs(Math.cos(lineDir))));
//
//						//hvis han er længere fremme end forsvarslinien
//						if (Math.abs(lineX - p.getOppTeam().getGoalX()) > Math.abs(p.getX() - p.getOppTeam().getGoalX())){
//
//							Point distPoint = Hjaelper.intersection(defLine.get(i).x, defLine.get(i).y, defLine.get(i+1).x, defLine.get(i+1).y, (int)p.getX(), (int)p.getY(), (int)(p.getX() + Math.sin(lineDir - Math.PI / 2) * 500), (int)(p.getY() + Math.cos(lineDir - Math.PI / 2) * 500));
//							double throughDist = Hjaelper.Distance(distPoint.x, distPoint.y, p.getX(), p.getY());
////							System.out.println("Through dist: " + throughDist);
//
//							int throughX = (int)p.getX() + 50;
//							if (p.getMyTeam().getGoalX() > p.getOppTeam().getGoalX())
//								throughX = (int)p.getX() - 50;
//							int throughY = (int)p.getY();
//
//							backbuffergc.fillOval(throughX, throughY, 5, 5);
//						}
//					}
//				}

			//boldholderens overblik mht. through balls
//			if (matchEngine.getBold().getLastTouch() != null){
//				int overblik = (int)matchEngine.getBold().getLastTouch().getVision() * 4;
//				overblik *= 2;
//				backbuffergc.drawOval((int)matchEngine.getBold().getLastTouch().getX() - overblik / 2, (int)matchEngine.getBold().getLastTouch().getY() - overblik / 2, overblik, overblik);
//			}
			
			//ofside linie og prikker
//			backbuffergc.drawLine(matchEngine.getMatch().getOffsideX() + goal2X, pitchPosY, matchEngine.getMatch().getOffsideX() + goal2X, pitchHeight + pitchPosY);
//			backbuffergc.drawLine(goalX - matchEngine.getMatch().getOffsideX(), pitchPosY, goalX - matchEngine.getMatch().getOffsideX(), pitchHeight + pitchPosY);
//			
//			for (Player p : teamA.getPlayers())
//				backbuffergc.drawOval(p.getOffsideX() - 1, p.getOffsideY() - 1, 2, 2);
//			
//			for (Player p : teammatchEngine.getBold().getPlayers())
//				backbuffergc.drawOval(p.getOffsideX() - 1, p.getOffsideY() - 1, 2, 2);
			
			//posX og posY
//			backbuffergc.setColor(teamA.getColor());
//			for (Player p : teamA.getPlayers())
//				backbuffergc.drawOval((int)p.getPosX() - 1, (int)p.getPosY() - 1, 2, 2);
//			
//			backbuffergc.setColor(teammatchEngine.getBold().getColor());
//			for (Player p : teammatchEngine.getBold().getPlayers())
//				backbuffergc.drawOval((int)p.getPosX() - 1, (int)p.getPosY() - 1, 2, 2);
			
			//Tegn TeamA's targets
			backbuffergc.setColor(matchEngine.getTeamA().getColor());
			for (Player p : matchEngine.getTeamA().getPlayers()){
				backbuffergc.drawOval((int)p.getTargetX() -1, (int)p.getTargetY() -1, 2, 2);
			}
			
			//Tegn TeamB's pos
			backbuffergc.setColor(matchEngine.getTeamB().getColor());
			for (Player p : matchEngine.getTeamB().getPlayers()){
				backbuffergc.drawOval((int)p.getPosX() -1, (int)p.getPosY() -1, 2, 2);
			}
			
			//maalene
			backbuffergc.setColor(Color.WHITE);
			backbuffergc.drawRect(goalX, goalY, 10, goalSize);
			backbuffergc.drawRect(goal2X-10, goal2Y, 10, goal2Size);

			//bolden over spillerne og maalene hvis den er hoej
			if (matchEngine.getBold().getZ() >= 170){
				//bold
				backbuffergc.setColor(Color.WHITE);
				backbuffergc.fillOval((int)Math.round(matchEngine.getBold().getX()) - ballSize / 2, (int)Math.round(matchEngine.getBold().getY()) - ballSize / 2, ballSize, ballSize);
				backbuffergc.setColor(Color.BLACK);
				backbuffergc.drawOval((int)Math.round(matchEngine.getBold().getX()) - ballSize / 2, (int)Math.round(matchEngine.getBold().getY()) - ballSize / 2, ballSize, ballSize);
			}
			
			
			//DEBUG: ALTID SE BOLDEN ØVERST
			backbuffergc.setColor(Color.WHITE);
			backbuffergc.fillOval((int)Math.round(matchEngine.getBold().getX()) - ballSize / 2, (int)Math.round(matchEngine.getBold().getY()) - ballSize / 2, ballSize, ballSize);
			backbuffergc.setColor(Color.BLACK);
			backbuffergc.drawOval((int)Math.round(matchEngine.getBold().getX()) - ballSize / 2, (int)Math.round(matchEngine.getBold().getY()) - ballSize / 2, ballSize, ballSize);
			
			/////////
			
			if (matchEngine.getMatch().getState().equals(MatchState.PENALTYSHOOTOUT)){
				//Penalty scoreboard
				int scx = 200, scw = 250, sch = 60, scy = pitchHeight + pitchPosY - 15;
				backbuffergc.setColor(Color.DARK_GRAY);
				backbuffergc.fillRoundRect(scx, scy, scw, sch, 10, 10);
				
				backbuffergc.setColor(Color.GRAY);
				backbuffergc.drawRoundRect(scx+1, scy+1, scw-3, sch-3, 10, 10);

				backbuffergc.setColor(Color.WHITE);
				backbuffergc.drawString("Penalties", scx + 5, scy + 15);
				backbuffergc.drawString(matchEngine.getTeamA().getName(), scx + 5, scy + 35);
				backbuffergc.drawString(matchEngine.getTeamB().getName(), scx + 5, scy + 50);
				
				int maxWidth = fontMetrics.stringWidth(matchEngine.getTeamA().getName());
				if (fontMetrics.stringWidth(matchEngine.getTeamB().getName()) > maxWidth)
					maxWidth = fontMetrics.stringWidth(matchEngine.getTeamB().getName());
				
				int dotSize = 10;
				for (int i = 0; i < matchEngine.getMatch().getShootoutShotsA().size(); i++){
					if (matchEngine.getMatch().getShootoutShotsA().get(i) == 2)
						backbuffergc.setColor(Color.GREEN);
					else if (matchEngine.getMatch().getShootoutShotsA().get(i) == 1)
						backbuffergc.setColor(Color.RED);
					else if (matchEngine.getMatch().getShootoutShotsA().get(i) == 0)
						backbuffergc.setColor(Color.BLACK);
					
					backbuffergc.fillOval(scx + maxWidth + 20 + i * (dotSize+2), scy + 35 - 9, dotSize, dotSize);
					
					backbuffergc.setColor(Color.GRAY);
					backbuffergc.drawOval(scx + maxWidth + 20 + i * (dotSize+2), scy + 35 - 9, dotSize, dotSize);
				}
				for (int i = 0; i < matchEngine.getMatch().getShootoutShotsB().size(); i++){
					if (matchEngine.getMatch().getShootoutShotsB().get(i) == 2)
						backbuffergc.setColor(Color.GREEN);
					else if (matchEngine.getMatch().getShootoutShotsB().get(i) == 1)
						backbuffergc.setColor(Color.RED);
					else if (matchEngine.getMatch().getShootoutShotsB().get(i) == 0)
						backbuffergc.setColor(Color.BLACK);

					backbuffergc.fillOval(scx + maxWidth + 20 + i * (dotSize+2), scy + 50 - 9, dotSize, dotSize);
					
					backbuffergc.setColor(Color.GRAY);
					backbuffergc.drawOval(scx + maxWidth + 20 + i * (dotSize+2), scy + 50 - 9, dotSize, dotSize);
				}	
				
				backbuffergc.setColor(Color.WHITE);
				backbuffergc.drawString(matchEngine.getMatch().getShootoutGoalsA()+"", scx + scw - 15, scy + 35);
				backbuffergc.drawString(matchEngine.getMatch().getShootoutGoalsB()+"", scx + scw - 15, scy + 50);
			}
			
			if (matchEngine.getMatch().getState() != null)
				backbuffergc.drawString(matchEngine.getMatch().getState().toString() + " - " + matchEngine.getBold().isUdenfor(), 100, 50);

			//debug
			//backbuffergc.drawString(debug + " - bold Z: " + Math.round(matchEngine.getBold().getZ()), 100, pitchPosY + pitchHeight);
//			backbuffergc.drawString(matchEngine.getMatch().getPenaltyTaken() + " - " + (int)((System.currentTimeMillis() - matchEngine.getMatch().getPenaltyTime()) / 1000), 100, pitchPosY + pitchHeight);
//			backbuffergc.drawString(matchEngine.getMatch().getState().toString() + " - " + matchEngine.getBold().isShot(), 100, pitchPosY + pitchHeight + 15);
//			backbuffergc.drawString(matchEngine.getTeamA().getShootoutPenalty() + " - " + matchEngine.getTeamB().getShootoutPenalty(), 100, pitchPosY + pitchHeight + 30);
			backbuffergc.setColor(Color.WHITE);
			
			if (matchEngine.getTeamA().getTeamState() != null && matchEngine.getTeamB().getTeamState() != null){
			backbuffergc.drawString(matchEngine.getTeamA().getName() + " state: " + matchEngine.getTeamA().getTeamState().toString(), 100, pitchPosY + pitchHeight + 60);
			backbuffergc.drawString(matchEngine.getTeamB().getName() + " state: " + matchEngine.getTeamB().getTeamState().toString(), 100, pitchPosY + pitchHeight + 90);
			}
			//textbox
			Player p = matchEngine.getBold().getLastTouch();
			if (p != null && p.getMatchMessage() != null)
				backbuffergc.drawString("Match Message: " + p.getMatchMessage(), pitchPosX + (pitchWidth / 2), pitchPosY + pitchHeight + 20);
			if (p != null && p.getMatchMessage2() != null)
				backbuffergc.drawString("Match Message: " + p.getMatchMessage2(), pitchPosX + (pitchWidth / 2), pitchPosY + pitchHeight + 30);
			
			ArrayList<String> tempCommentary = new ArrayList<String>();
			synchronized (matchEngine.getMatch().getCommentary()) {
				for (String s : matchEngine.getMatch().getCommentary())
					tempCommentary.add(s);
				
				matchEngine.getMatch().clearCommentary();
			}
			
			for (String str : tempCommentary)
				settingsFrame.addCommentary(matchEngine.getMins() + ":" + matchEngine.getSecs() + " - " + str);
			
			//Scoreboard
			int scx = 300, scw = 150, sch = 65, scy = 10;
			backbuffergc.setColor(Color.DARK_GRAY);
			backbuffergc.fillRoundRect(scx, scy, scw, sch, 10, 10);
			
			backbuffergc.setColor(Color.GRAY);
			backbuffergc.drawRoundRect(scx+1, scy+1, scw-3, sch-3, 10, 10);

			backbuffergc.setColor(Color.WHITE);

			String time = "";
			if (matchEngine.getMins() < 10)
				time += "0" + matchEngine.getMins();
			else
				time += matchEngine.getMins();

			if (matchEngine.getSecs() < 10)
				time += ":0" + matchEngine.getSecs();
			else
				time += ":" + matchEngine.getSecs();

			if (matchEngine.getMins() > 43 && matchEngine.getMatch().getHalf() == 1)
				time += " +" + matchEngine.getAddedMins();
			else if (matchEngine.getMins() > 88)
				time += " +" + matchEngine.getAddedMins();
			
			backbuffergc.drawString(time, scx + scw / 2 - fontMetrics.stringWidth(time) / 2, scy + 15);

			backbuffergc.setColor(Color.WHITE);
			String ta = matchEngine.getTeamA().getName(), tb = matchEngine.getTeamB().getName();
			while (fontMetrics.stringWidth(ta) > scw - 30)
				ta = ta.substring(0, ta.length() - 2);
			while (fontMetrics.stringWidth(tb) > scw - 30)
				tb = tb.substring(0, tb.length() - 2);
			
			backbuffergc.drawString(ta, scx + 5, 50);
			backbuffergc.drawString(tb, scx + 5, 65);
			
			String sa = "", sb = "";
			if (matchEngine.getMatch().getFirstMatchGoalsA() > -1 && matchEngine.getMatch().getFirstMatchGoalsB() > -1){
				sa = "(" + matchEngine.getMatch().getFirstMatchGoalsA() + ") ";
				sb = "(" + matchEngine.getMatch().getFirstMatchGoalsB() + ") ";
			}
			sa += Integer.toString(matchEngine.getBold().getTeamAMaal());
			sb += Integer.toString(matchEngine.getBold().getTeamBMaal());
			
			backbuffergc.drawString(sa, scx + scw - 5 - fontMetrics.stringWidth(sa), 50);
			backbuffergc.drawString(sb, scx + scw - 5 - fontMetrics.stringWidth(sb), 65);			
			
			
			if (matchEngine.getBold().isShot() && Math.abs(matchEngine.getBold().getY() - (matchEngine.getTeamA().getGoalY() + 30)) < 45){
				goalViewX = goalViewPos + 184 / 2 + (int)((matchEngine.getBold().getY() - (matchEngine.getTeamA().getGoalY() + 30)) * 184 / 59d);
				goalViewY = matchEngine.getPitch().getPitchPosY() + matchEngine.getPitch().getPitchHeight() + 25 + (int)(61 - (matchEngine.getBold().getZ() / 244d * 61));
//				System.out.println("GOalview: " + goalViewX + ", " + goalViewY);
			}
			
			backbuffergc.setColor(Color.WHITE);
			backbuffergc.fillOval(goalViewX - 2, goalViewY - 2, 4, 4);
			
			backbuffergc.drawLine(goalViewPos, matchEngine.getPitch().getPitchPosY() + matchEngine.getPitch().getPitchHeight() + 25 + 61, goalViewPos, matchEngine.getPitch().getPitchPosY() + matchEngine.getPitch().getPitchHeight() + 25);
			backbuffergc.drawLine(goalViewPos, matchEngine.getPitch().getPitchPosY() + matchEngine.getPitch().getPitchHeight() + 25, goalViewPos + 184, matchEngine.getPitch().getPitchPosY() + matchEngine.getPitch().getPitchHeight() + 25);
			backbuffergc.drawLine(goalViewPos + 184, matchEngine.getPitch().getPitchPosY() + matchEngine.getPitch().getPitchHeight() + 25 + 61, goalViewPos + 184, matchEngine.getPitch().getPitchPosY() + matchEngine.getPitch().getPitchHeight() + 25);
			
//			backbuffergc.drawLine(pitch.getPitchPosX(), pitch.getPitchPosY() + pitch.getPitchHeight() + 5, pitch.getPitchPosX() + 100, pitch.getPitchPosY() + pitch.getPitchHeight() + 5);
//			backbuffergc.drawString("100 pixel", pitch.getPitchPosX(), pitch.getPitchPosY() + pitch.getPitchHeight() + 20);

			
			// 	linier til target		
//					int e=0;
//					if (blaa.getLine() != null){
//						while(e<blaa.getLine().size()){
//							int a=blaa.getLine().get(e);
//							e++;
//							int b=blaa.getLine().get(e);
//							e++;
//							backbuffergc.drawLine(a, b, a, b);
//			
//						}
//					}
//					if (roed.getLine() != null){
//						while(e<roed.getLine().size()){
//							int a=roed.getLine().get(e);
//							e++;
//							int b=roed.getLine().get(e);
//							e++;
//							backbuffergc.drawLine(a, b, a, b);
//			
//						}
//					}

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

			if (e.getKeyChar() == 'a'){
				matchEngine.getBold().setA(Math.PI * 1.5);
				matchEngine.getBold().setSpeed(200);
				matchEngine.getBold().setShot(true);
			}
			else if (e.getKeyChar() == 's'){
				matchEngine.getBold().setA(0);
				matchEngine.getBold().setSpeed(200);
				matchEngine.getBold().setShot(true);
			}
			else if (e.getKeyChar() == 'w'){
				matchEngine.getBold().setA(Math.PI);
				matchEngine.getBold().setSpeed(200);
				matchEngine.getBold().setShot(true);
			}
			else if (e.getKeyChar() == 'd'){
				matchEngine.getBold().setA(Math.PI * .5);
				matchEngine.getBold().setSpeed(200);
				matchEngine.getBold().setShot(true);
			}
			else if (e.getKeyChar() == 'q'){
				matchEngine.getBold().setA(Math.PI * 1.25);
				matchEngine.getBold().setSpeed(200);
				matchEngine.getBold().setShot(true);
			}
			else if (e.getKeyChar() == 'e'){
				matchEngine.getBold().setA(Math.PI * .75);
				matchEngine.getBold().setSpeed(200);
				matchEngine.getBold().setShot(true);
			}
			else if (e.getKeyChar() == 'u'){
				matchEngine.getBold().setZForce(100);
			}
			else if (e.getKeyChar() == 'l'){
				matchEngine.setPause(!matchEngine.isPause());
			}
			else if (e.getKeyChar() == '9'){
				matchEngine.setMins(90);
			}
			else if (e.getKeyChar() == '+'){
				matchEngine.setMins(matchEngine.getMins() + 15);
			}
			else if (e.getKeyChar() == 'p'){
				matchEngine.getMatch().setState(MatchState.PENALTY);
				matchEngine.getMatch().setSetPieceX(matchEngine.getPitch().getPitchPosX() + 88);
				matchEngine.getMatch().setSetPieceY(matchEngine.getPitch().getPitchHeight() / 2 + matchEngine.getPitch().getPitchPosY());
				matchEngine.getBold().setX(matchEngine.getPitch().getPitchPosX() + 88);
				matchEngine.getBold().setY(matchEngine.getPitch().getPitchHeight() / 2 + matchEngine.getPitch().getPitchPosY());
				matchEngine.getBold().setSpeed(0);
				matchEngine.getMatch().setSetPieceTeam(matchEngine.getTeamB());
			}
			else if (e.getKeyChar() == 'f'){//frispark
				matchEngine.getMatch().setSetPieceX((int)matchEngine.getBold().getX());
				matchEngine.getMatch().setSetPieceY((int)matchEngine.getBold().getY());
				matchEngine.getBold().setSetPieceTime(System.currentTimeMillis());
				matchEngine.getMatch().setState(MatchState.FREE_KICK);
				matchEngine.getMatch().setSetPieceTeam(matchEngine.getBold().getLastTouch().getOppTeam());
			}
			else if (e.getKeyChar() == 'z'){
				matchEngine.getBold().setZ(244);
				matchEngine.getBold().setZForce(0);
			}
			else if (e.getKeyChar() == 'c'){
				for (Player p : matchEngine.getTeamA().getPlayers()){
					if (Hjaelper.Distance(p.getX(), p.getY(), matchEngine.getBold().getX(), matchEngine.getBold().getY()) < 9)
						p.getStateHasBall().update();
				}
				for (Player p : matchEngine.getTeamB().getPlayers()){
					if (Hjaelper.Distance(p.getX(), p.getY(), matchEngine.getBold().getX(), matchEngine.getBold().getY()) < 9)
						p.getStateHasBall().update();
				}	
					
			}
			else if (e.getKeyChar() == 'm'){
				try {
					DAOCake.executeSimpleStatement("INSERT INTO livematches VALUES (-1000, '" + matchEngine.getTeamA().getId() + ":15,7,250:14,180,468:3,170,58:4,134,176:5,132,342:6,235,256:7,283,416:8,311,266:9,374,182:10,372,318:11,285,88', " + matchEngine.getMatch().getMatchId() + ");");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class MouseMoveListener implements java.awt.event.MouseMotionListener{

		public void mouseDragged(MouseEvent e) {
			if (dragPlayer != null){
				dragPlayer.setX(e.getX());
				dragPlayer.setY(e.getY() - 20);
			}
			
		}

		public void mouseMoved(MouseEvent e) {

			
		}
		
	}
	
	class MouseListener implements java.awt.event.MouseListener{

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mousePressed(MouseEvent e) {

			if (e.isAltDown()){
				System.out.println(e.getX());
				System.out.println(e.getY());
			}
			else if (e.isShiftDown()){
				System.out.println("Bold speed: " + matchEngine.getBold().getSpeed());
				List<Player> t = new ArrayList<Player>();
				
				t.addAll(matchEngine.getTeamA().getPlayers());
				t.addAll(matchEngine.getTeamB().getPlayers());
				
				Player result = t.get(0);
				
				for (Player p : t){
					if (Hjaelper.Distance(e.getX(), e.getY(), p.getX(), p.getY()) < 
							Hjaelper.Distance(e.getX(), e.getY(), result.getX(), result.getY()))
						result = p;
				}
				
				System.out.println(result.getShirtNumber() + "(" + result.getLastname() + "): " + result.getPlayerAction() + " - har løbet: " + result.getPlayerMatchStats().getMetersRun());
				PlayerMatchStats s = result.getPlayerMatchStats();
				System.out.println("Passes: " + s.passes + " completed: " + s.passesOnTarget + " shots: " + s.shots + " on target: " + s.shotsOnTarget);
				System.out.println("Stamina: " + result.getStamina() + ". Energy: " + result.getEnergy());
				System.out.println("State: " + result.getPlayerAction().toString());
				System.out.println("State2: " + result.getState().toString());
				System.out.println("wait: " + result.getWait());
				System.out.println("Markerror: " + result.markErr.x + ", " + result.markErr.y);
				System.out.println("Target: " + result.targetX + ", " + result.targetY);
				System.out.println("TargetSpeed: " + result.targetSpeed + ", Speed: " + result.speed);
				System.out.println("myTeam.goal: " + result.getMyTeam().getGoalX() + ", " + result.getMyTeam().getGoalY());
			}
			else if (e.isControlDown()){
				for (Player p : matchEngine.getTeamA().getPlayers()){
					if (Hjaelper.Distance(e.getX(), e.getY() - 20, p.getX(), p.getY()) < 8){
						dragPlayer = p;
						System.out.println(p.getShirtNumber());
					}
				}
				for (Player p : matchEngine.getTeamB().getPlayers()){
					if (Hjaelper.Distance(e.getX(), e.getY() - 20, p.getX(), p.getY()) < 8){
						dragPlayer = p;
						System.out.println(p.getShirtNumber());
					}
				}
			}
			else{
				matchEngine.getBold().setX(e.getX());
				matchEngine.getBold().setY(e.getY());

				double vinkelretPaaMaal =  Math.atan2(goalX - goal2X, 0);
				double retningPaaMaal =  Math.atan2(goalX - matchEngine.getBold().getX(), goalY - matchEngine.getBold().getY());
				System.out.println(Hjaelper.angleDifference(vinkelretPaaMaal, retningPaaMaal));

			}
		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			dragPlayer = null;

		}


	}
}
