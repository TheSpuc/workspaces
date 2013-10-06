import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

import model.Bold;
import model.Hjaelper;
import model.Pitch;
import model.Player;
import model.PlayerAction;
import model.ReplayEvent;
import model.Team;

public class Replay extends JApplet implements Runnable {

	final double SIZE_FACTOR = 1.2;
	int matchId = 1500, leagueId = 3;
	boolean local = false;
	String path = "";
	
	Font fontShirtNumbers = new Font("Helvetica", Font.PLAIN,  9);
	Font fontCommentary = new Font("Helvetica", Font.PLAIN,  11);
	FontMetrics fontMetrics = getFontMetrics(fontShirtNumbers);
	
	Timer timer = new Timer();
	String tA = "", tB = "";
	String[] playersA = new String[11];
	String[] playersB = new String[11];
	int speed = 20;
	long frames = 0;
	long fps = 0;
	int goalX, goalY, goalSize;
	int goal2X, goal2Y, goal2Size;
	Team teamA;
	Team teamB;
	ArrayList<String> commentary = new ArrayList<String>();
	
	//replay
	int matchSpeed = 1;	
	ArrayList<String> data = new ArrayList<String>(); 
	long lastReplayFrame = System.currentTimeMillis();
	int currentFrame = 0;
	int secs = 0, mins = 0, ticks = 0;

	Player p1, p2, p3;

	Pitch pitch;

	int windowHeight = (int)(625 / SIZE_FACTOR);
	int windowWidth = (int)(1000 / SIZE_FACTOR);

	int pitchHeight = (int)(513 / SIZE_FACTOR);
	int pitchWidth = (int)(880 / SIZE_FACTOR);

	int pitchPosX = (int)(40 / SIZE_FACTOR);
	int pitchPosY = (int)(40 / SIZE_FACTOR);

	int goalsA, goalsB;
	ArrayList<Player> players = new ArrayList<Player>();
	Bold b = new Bold();
	long time = 0;
	boolean timeRunning = false;
	float a = 0.01f, a2 = 0.01f;

	Thread mainloop = null;

	Graphics backbuffergc;
	Image backbuffer;

	boolean pause = true;
	
	ArrayList<ReplayEvent> events = new ArrayList<ReplayEvent>();
	ArrayList<String> times = new ArrayList<String>();
	int totalFrames;
	
	boolean loadDone = false;
	boolean showTimeLinetime = false;
	String timelineString = "";
	int mouseX, mouseY;
	ReplayEvent markedEvent = null;
	
	boolean shootout = false;
	ArrayList<Integer> shootoutShotsA = new ArrayList<Integer>();
	ArrayList<Integer> shootoutShotsB = new ArrayList<Integer>();
	int penaltyShootoutStartsFrame = -1;
	
	SortedMap<Integer, ArrayList<Integer>> shootoutShotsByFrameA = new TreeMap<Integer, ArrayList<Integer>>();
	SortedMap<Integer, ArrayList<Integer>> shootoutShotsByFrameB = new TreeMap<Integer, ArrayList<Integer>>();
	SortedMap<Integer, SortedMap<Integer, Point>> playerPositionsByFrame = new TreeMap<Integer, SortedMap<Integer, Point>>();
	SortedMap<Integer, Integer[]> goalsByFrame = new TreeMap<Integer, Integer[]>();
	
	int buttonW = 40, buttonH = 20;
	Color buttonBGColor = Color.darkGray, buttonTextColor = Color.WHITE;
	int pMidX = pitchPosX + pitchWidth / 2; 
	int btnNextHighlight = pMidX + 20, btnPrevHighlight = pMidX - 60, btnPlay = pMidX - 20;
	int buttonY = pitchHeight + pitchPosY + 20;
	
	public void start() {
		
		System.out.println("mem: " + Runtime.getRuntime().maxMemory());
		mainloop = new Thread(this);
		if (mainloop != null) {
			mainloop.start();
		}
		
		if (!local){
			try{
				leagueId = Integer.parseInt(getParameter("league"));
				matchId = Integer.parseInt(getParameter("match"));
			}
			catch (Exception ne){
				ne.printStackTrace();
				JOptionPane.showMessageDialog(null, "Forkert liga eller kamp-id: league " + leagueId + ", match " + matchId);
			}
			path = "http://" + getCodeBase().getHost() + "/matchengine/" + leagueId + "/" + matchId + ".txt";
			System.out.println(path);
		}
		else
			path = "http://www.footie-online.com/matchengine/3/1530.txt";
		
		backbuffer = createImage(windowWidth, windowHeight);
		backbuffergc = backbuffer.getGraphics();
		
		System.out.println(1);
		
		if (data.size() == 0){
			try {
				Loader l = new Loader(path);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, path + " - " + e.getMessage() + " league " + leagueId + ", match " + matchId);
			}
		}
		System.out.println(2);
		matchSpeed=30;
		
	
		addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
				
				if (arg0.getX() > btnNextHighlight && arg0.getX() < btnNextHighlight + buttonW && 
						arg0.getY() > buttonY && arg0.getY() < buttonY + buttonH){
					
					nextHighLight(1);
					findPositions();
				}
				if (arg0.getX() > btnPrevHighlight && arg0.getX() < btnPrevHighlight + buttonW && 
						arg0.getY() > buttonY && arg0.getY() < buttonY + buttonH){
					
					nextHighLight(-1);
					findPositions();
				}
				if (arg0.getX() > btnPlay && arg0.getX() < btnPlay + buttonW && 
						arg0.getY() > buttonY && arg0.getY() < buttonY + buttonH){
					
					pause = !pause;
				}
				
				if (showTimeLinetime){
					currentFrame = (int)(((double)(arg0.getX() - pitchPosX) / (double)pitchWidth) * (double)totalFrames);
				
					findPositions();
					
					if (currentFrame > penaltyShootoutStartsFrame){
						shootoutShotsA = new ArrayList<Integer>();
						shootoutShotsB = new ArrayList<Integer>();
						
						for (Integer i : shootoutShotsByFrameA.keySet())
							if (i < currentFrame)
								shootoutShotsA = shootoutShotsByFrameA.get(i);

						System.out.println("SA: " + shootoutShotsA);
						
						for (Integer i : shootoutShotsByFrameB.keySet()){
							System.out.println(i + " / " + currentFrame);
							System.out.println(i + " / " + shootoutShotsByFrameB.get(i));
							if (i < currentFrame){
								shootoutShotsB = shootoutShotsByFrameB.get(i);
								System.out.println(shootoutShotsByFrameB.get(i));
							}
						}
							
						
						System.out.println("SB: " + shootoutShotsB);

					}
				}
			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			
			public void mouseMoved(MouseEvent e) {
				if (e.getX() > pitchPosX - 5 && e.getX() < pitchPosX + pitchWidth + 5 &&
						e.getY() > pitchPosY + pitchHeight + 5 && e.getY() < pitchPosY + pitchHeight + 15){
					
					showTimeLinetime = true;
					mouseX = e.getX();
					mouseY = e.getY();
					int fx = (e.getX() - pitchPosX);
					if (fx < 0) fx = 0;
					if (fx > pitchPosX + pitchWidth) fx = pitchPosX + pitchWidth;
					
					timelineString = times.get((int)(((double)fx / (double)pitchWidth) * (double)totalFrames));
					for (ReplayEvent event : events){
						double percent = (double)event.getFrame() / (double)totalFrames;
						if (e.getX() > pitchPosX + (int)(pitchWidth * percent) - 1 && e.getX() < pitchPosX + (int)(pitchWidth * percent) + 1)
							markedEvent = event;
					}
				}
				else{
					showTimeLinetime = false;
					markedEvent = null;
				}
			}
			public void mouseDragged(MouseEvent e) {
			}
		});
		
		timer.schedule(new task(), 0, (long)(1000 / matchSpeed));
	}

	private void findPositions(){
		
		//find sidste koordinater for hver spiller
		
		int tempFrame = currentFrame;
		while (!playerPositionsByFrame.keySet().contains(tempFrame) && tempFrame > 0){
			tempFrame--;
		}
		
		SortedMap<Integer, Point> positions = playerPositionsByFrame.get(tempFrame);
		Integer[] g = goalsByFrame.get(tempFrame);
		
		goalsA = g[0];
		goalsB = g[1];
		
		for (Integer i : positions.keySet()){
			boolean found = false;
			int q = 0;
			
			while (q < teamA.getPlayers().size() && q < teamB.getPlayers().size() && !found){
				
				if (teamA.getPlayers().get(q).getId() == i){
					teamA.getPlayers().get(q).setX(positions.get(i).x);
					teamA.getPlayers().get(q).setY(positions.get(i).y);
					found = true;
				}
				else if (teamB.getPlayers().get(q).getId() == i){
					teamB.getPlayers().get(q).setX(positions.get(i).x);
					teamB.getPlayers().get(q).setY(positions.get(i).y);
					found = true;
				}
				
				q++;
			}
		}
		
		b.setX(positions.get(-1).x);
		b.setY(positions.get(-1).y);
		
		while (tempFrame < currentFrame){
			parseInput(data.get(tempFrame), false, tempFrame);
			tempFrame++;
		}
	}
	
	public void initTeams(String str){
		if (teamA != null) teamA.getPlayers().clear();
		if (teamB != null) teamB.getPlayers().clear();
		
		String ss[] = str.split(":");
		System.out.println(str);
		try{
			int k = 1;
			while (k < ss.length){
				String p[] = ss[k].split("\\.");
				
				if (p[0].equals("s")){

				}
				else if (p[0].equals("t")){
					if (teamA == null)
						teamA = new Team(p[2], Hjaelper.getNewColor(p[3]), Hjaelper.getNewColor(p[4]), Integer.parseInt(p[1]), null, 0, 0);
					else
						teamB = new Team(p[2], Hjaelper.getNewColor(p[3]), Hjaelper.getNewColor(p[4]), Integer.parseInt(p[1]), null, 0, 0);
				}
				else if (p[0].equals("off")){

				}
				else if (p[0].equals("sc")){
					goalsA = Integer.parseInt(p[1]);
					goalsB = Integer.parseInt(p[2]);
				}
				else{
					Player player = new Player();
					player.setId(Integer.parseInt(p[0]));
					player.setShirtNumber(Integer.parseInt(p[1]));
					player.setFirstname(p[2]);
					player.setLastname(p[3]);
					if (p.length > 5){
						player.setX(Integer.parseInt(p[4]));
						player.setY(Integer.parseInt(p[5]));
						player.setStartPosX(Integer.parseInt(p[4]));
						player.setStartPosY(Integer.parseInt(p[5]));
					}
					else{
						player.setX(-200);
						player.setY(-200);
					}
						
					if (teamB == null){
						System.out.println("Player added to teamA: " + player.getId());
						teamA.getPlayers().add(player);
					}
						
					else{
						System.out.println("Player added to teamB: " + player.getId());
						teamB.getPlayers().add(player);
					}
						
				}

				k++;
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void stop ()  {
		if (mainloop != null)  {
			mainloop.stop();
			mainloop=null;
		}
	}

	public void run() {

	}


	private void parseInput(String str, boolean initRun, int frame){
		String ln[] = str.split(":");
		
		if (ln[0].length() > 0){

			int j = 0;
			while (j < ln.length){
				try{
					if (ln[j].equals("s")){
						j++;
						secs = Integer.parseInt(ln[j]);
					}
					else if(ln[j].equals("m")){
						j++;
						mins = Integer.parseInt(ln[j]);
					}
					else if(ln[j].equals("ms")){
						j++;
						matchSpeed = Integer.parseInt(ln[j]);
					}
					else if(ln[j].equals("sc")){
						j++;
						goalsA = Integer.parseInt(ln[j]);
						j++;
						goalsB = Integer.parseInt(ln[j]);
					}
					else if(ln[j].equals("c")){
						j++;
						synchronized (commentary) {
							commentary.add(ln[j]);
						}
					}
					else if(str.startsWith("rc.")){
						if (initRun){
							String time = "";
							if (mins < 10) time += "0" + mins;
							else time += mins;
							if (secs < 10) time += ":0" + secs;
							else time += ":" + secs;

							String description = "";
							if(str.equals("rc.-14")) description = "Chance";
							else if(str.equals("rc.-13")) description = "Goal";

							events.add(new ReplayEvent(time, goalsA + " - " + goalsB, description, frame));
						}
					}
					else if(ln[j].equals("lu")){
						//lineup 
					}
					else if(ln[j].equals("Shout")){
						if (initRun){
							shootout = true;
							penaltyShootoutStartsFrame = frame;
							System.out.println("Shout starts: " + penaltyShootoutStartsFrame);
						}
						else{
							shootoutShotsA.clear();
							shootoutShotsB.clear();
						}
					}
					else if(ln[j].equals("shoutA")){
						shootoutShotsA.clear();
						j++;
						try{
							char[] ch = ln[j].toCharArray();
							for (Character c : ch)
								shootoutShotsA.add(Integer.parseInt(""+c));	
						}
						catch (Exception e){
							System.out.println("shoutA: " + ln[j]);
						}
						if (initRun){
							ArrayList<Integer> l = new ArrayList<Integer>();
							for (Integer i : shootoutShotsA)
								l.add(new Integer(i));
							
							shootoutShotsByFrameA.put(frame, l); 
//							System.out.println("ShoootA: " + shootoutShotsA);
						}
					}
					else if(ln[j].equals("shoutB")){
						shootoutShotsB.clear();
						j++;
						try{
							char[] ch = ln[j].toCharArray();
							for (Character c : ch)
								shootoutShotsB.add(Integer.parseInt(""+c));	
						}
						catch (Exception e){
							System.out.println("shoutB: " + ln[j]);
						}
						if (initRun){ 
							ArrayList<Integer> l = new ArrayList<Integer>();
							for (Integer i : shootoutShotsB)
								l.add(new Integer(i));
							
							shootoutShotsByFrameB.put(frame, l); 
//							System.out.println("ShoootB: " + shootoutShotsB);
						}
					}
					else if (ln[j].equals("b")){
						j++;
						b.setX(Integer.parseInt(ln[j]));
						j++;
						b.setY(Integer.parseInt(ln[j]));
						j++;
						b.setZ(Integer.parseInt(ln[j]));
					}
					else{
						int id = Integer.parseInt(ln[j]);
						
						Player player = null;
						for (Player pl : teamA.getPlayers())
							if (pl.getId() == id)
								player = pl;

						for (Player pl : teamB.getPlayers())
							if (pl.getId() == id)
								player = pl;

						if (player != null){
							j++;
							player.setX(Integer.parseInt(ln[j]));
							j++;
							player.setY(Integer.parseInt(ln[j]));
						}
						else{
							j += 2;
							if (!initRun) System.out.println("ID: " + id);	
						}
					}
				}
				catch (Exception e){
					e.printStackTrace();
					System.out.println(str);
				}
				j++;
			}
		}
		
	}
	
	public void init() {

		if (local) setSize(windowWidth, windowHeight);
		windowWidth = getSize().width;
		windowHeight = getSize().height;

		goalSize = (int)(59 / SIZE_FACTOR);
		goalX = pitchPosX + pitchWidth;
		goalY = (pitchHeight - goalSize) / 2 + pitchPosY;
		goal2Size = goalSize;
		goal2X = pitchPosX;
		goal2Y = (pitchHeight - goalSize) / 2 + pitchPosY;

		b.setX(0);
		b.setY(0);

		setLayout(null);

		setBackground(Color.GREEN.darker());
	}

	private void nextHighLight(int f){

		pause = true;
		currentFrame += 400 * f;
		if (currentFrame < 0) currentFrame = 0;
		if (currentFrame > data.size() - 1) currentFrame = data.size() - 1;
		
		while (currentFrame > 0 && currentFrame < data.size() - 1 && !data.get(currentFrame).equals("rc.-13")){
			currentFrame += f;
		}
		
		if (data.get(currentFrame).equals("rc.-13")){
			int q = 0;
			while (currentFrame > 0 && q < 400){
				currentFrame -= 1;
				parseInput(data.get(currentFrame), false, 0);
				q++;
			}
			mins -=1;
		}
		commentary.clear();
		pause = false;
	}

	public void paint(Graphics g) {

		//		super.paint(g);
		try{
			if (loadDone){

				if (backbuffergc == null){
					backbuffer = createImage(windowWidth, windowHeight);
					backbuffergc = backbuffer.getGraphics();
				}
				
				
				backbuffergc.setFont(fontShirtNumbers);

				backbuffergc.setColor(Color.GREEN.darker());
				backbuffergc.fillRect(0, 0, windowWidth, windowHeight);

				//Football pitch
				int pitchMidY = pitchPosY + pitchHeight / 2;
				int pitchMidX = pitchPosX + pitchWidth / 2;
				int pAreaY = pitchMidY - (int)(322 / SIZE_FACTOR) / 2;
				int mAreaY = pitchMidY - (int)(146 / SIZE_FACTOR) / 2;

				backbuffergc.setColor(Color.WHITE);
				backbuffergc.drawRect(pitchPosX, pitchPosY, pitchWidth, pitchHeight);//Banen
				backbuffergc.drawLine(pitchMidX, pitchPosY, pitchMidX, pitchPosY + pitchHeight);//midterlinje
				backbuffergc.drawRect(pitchPosX, pAreaY, (int)(132 / SIZE_FACTOR), (int)(322 / SIZE_FACTOR));//straffefelt 178
				backbuffergc.drawRect((int)(pitchPosX + pitchWidth - 132 / SIZE_FACTOR), pAreaY, (int)(132 / SIZE_FACTOR), (int)(322 / SIZE_FACTOR));//straffefelt
				backbuffergc.drawRect(pitchPosX, mAreaY, (int)(44 / SIZE_FACTOR), (int)(146 / SIZE_FACTOR));//målfelt
				backbuffergc.drawRect((int)(pitchPosX + pitchWidth - 43 / SIZE_FACTOR), mAreaY, (int)(44 / SIZE_FACTOR), (int)(146 / SIZE_FACTOR));//målfelt
				backbuffergc.drawOval(pitchMidX - (int)((146 / 2) / SIZE_FACTOR), pitchMidY - (int)((146 / 2) / SIZE_FACTOR), (int)(146 / SIZE_FACTOR), (int)(146 / SIZE_FACTOR));//midtercirkel
				backbuffergc.drawArc((int)(pitchPosX + 88 / SIZE_FACTOR - 73 / SIZE_FACTOR), (int)(pitchMidY - 73 / SIZE_FACTOR), (int)(146 / SIZE_FACTOR), (int)(146 / SIZE_FACTOR), -52, 105);//straffehalv-cirkel
				backbuffergc.drawArc((int)(pitchPosX + pitchWidth - 88 / SIZE_FACTOR - 73 / SIZE_FACTOR), (int)(pitchMidY - 73 / SIZE_FACTOR), (int)(146 / SIZE_FACTOR), (int)(146 / SIZE_FACTOR), 128, 105);//straffehalv-cirkel
				backbuffergc.drawArc((int)(pitchPosX - 8 / SIZE_FACTOR), (int)(pitchPosY - 8 / SIZE_FACTOR), (int)(16 / SIZE_FACTOR), (int)(16 / SIZE_FACTOR), 0, -90);//Hjørneflag
				backbuffergc.drawArc((int)(pitchPosX + pitchWidth - 8 / SIZE_FACTOR), (int)(pitchPosY - 8 / SIZE_FACTOR), (int)(16 / SIZE_FACTOR), (int)(16 / SIZE_FACTOR), -90, -90);//Hjørneflag
				backbuffergc.drawArc((int)(pitchPosX - 8 / SIZE_FACTOR), (int)(pitchPosY + pitchHeight - 8 / SIZE_FACTOR), (int)(16 / SIZE_FACTOR), (int)(16 / SIZE_FACTOR), 0, 90);//Hjørneflag
				backbuffergc.drawArc((int)(pitchPosX + pitchWidth - 8 / SIZE_FACTOR), (int)(pitchPosY + pitchHeight - 8 / SIZE_FACTOR), (int)(16 / SIZE_FACTOR), (int)(16 / SIZE_FACTOR), -180, -90);//Hjørneflag		
				backbuffergc.fillOval(pitchMidX - (int)((6 / 2) / SIZE_FACTOR), pitchMidY - (int)((6 / 2) / SIZE_FACTOR), 6, 6);//midterpletten
				backbuffergc.fillOval((int)(pitchPosX + 88 / SIZE_FACTOR - 1), pitchMidY - 1, 3, 3);//straffepletten
				backbuffergc.fillOval((int)(pitchPosX + pitchWidth - 88 / SIZE_FACTOR - 1), pitchMidY - 1, 3, 3);//straffepletten

				//		backbuffergc.drawString("Lag: " + (System.currentTimeMillis() - lastPacketReceived) + " - buffer: " + data.size(), 10, 10);

				int playerSize = (int)(14 / SIZE_FACTOR);
				int ballSize = (int)(8 / SIZE_FACTOR + b.getZ() / (80 * SIZE_FACTOR));
				int ballShadowSize = (int)(8 - b.getZ() / (350 * SIZE_FACTOR));

				backbuffergc.setColor(Color.DARK_GRAY);
				backbuffergc.fillOval((int)(b.getX() / SIZE_FACTOR + pitchPosX - ballShadowSize / 2 + b.getZ() / (100 * SIZE_FACTOR)), (int)(b.getY() / SIZE_FACTOR + pitchPosY - ballShadowSize / 2 + b.getZ() / (100 * SIZE_FACTOR)), ballShadowSize, ballShadowSize);

				if (b.getZ() <= 175){
					backbuffergc.setColor(Color.WHITE);
					backbuffergc.fillOval((int)(b.getX() / SIZE_FACTOR) + pitchPosX - ballSize / 2, (int)(b.getY() / SIZE_FACTOR) + pitchPosY - ballSize / 2, ballSize, ballSize);
					backbuffergc.setColor(Color.BLACK);
					backbuffergc.drawOval((int)(b.getX() / SIZE_FACTOR) + pitchPosX - ballSize / 2, (int)(b.getY() / SIZE_FACTOR) + pitchPosY - ballSize / 2, ballSize, ballSize);
				}


				//maal
				backbuffergc.setColor(Color.WHITE);
				backbuffergc.drawRect(goalX, goalY, (int)(10 / SIZE_FACTOR), goalSize);
				backbuffergc.drawRect(goal2X - (int)(10 / SIZE_FACTOR), goal2Y, (int)(10 / SIZE_FACTOR), goal2Size);


				backbuffergc.setColor(teamA.getColor());
				for (Player p : teamA.getPlayers()){
					//spiller - cirkel med kant 
					backbuffergc.setColor(teamA.getColor());
					backbuffergc.fillOval((int)(p.getX() / SIZE_FACTOR) + pitchPosX - playerSize / 2, (int)(p.getY() / SIZE_FACTOR) + pitchPosY - playerSize / 2, playerSize, playerSize);
					backbuffergc.setColor(teamA.getColor2());
					backbuffergc.drawOval((int)(p.getX() / SIZE_FACTOR) + pitchPosX - playerSize / 2, (int)(p.getY() / SIZE_FACTOR) + pitchPosY - playerSize / 2, playerSize, playerSize);

					//trøjenummer
					int nrx = (int)(5 / SIZE_FACTOR);
					if (p.getShirtNumber() < 10)
						nrx = (int)(2 / SIZE_FACTOR);

					backbuffergc.setColor(teamA.getColor2());
					backbuffergc.drawString(Integer.toString(p.getShirtNumber()),(int)(p.getX() / SIZE_FACTOR) + pitchPosX - nrx, (int)(p.getY() / SIZE_FACTOR) + pitchPosY + (int)(5 / SIZE_FACTOR));

					if (p.getPlayerAction() == PlayerAction.dribble || p.getPlayerAction() == PlayerAction.receiving_pass){
						int wx = fontMetrics.stringWidth(p.getLastname()) / 2;

						backbuffergc.drawString(p.getLastname(), (int)p.getX() - wx, (int)p.getY() + 18);
					}
				}

				backbuffergc.setColor(teamB.getColor());
				for (Player p : teamB.getPlayers()){
					//spiller - cirkel med kant 
					backbuffergc.setColor(teamB.getColor());
					backbuffergc.fillOval((int)(p.getX() / SIZE_FACTOR) + pitchPosX - playerSize / 2, (int)(p.getY() / SIZE_FACTOR) + pitchPosY - playerSize / 2, playerSize, playerSize);
					backbuffergc.setColor(teamB.getColor2());
					backbuffergc.drawOval((int)(p.getX() / SIZE_FACTOR) + pitchPosX - playerSize / 2, (int)(p.getY() / SIZE_FACTOR) + pitchPosY - playerSize / 2, playerSize, playerSize);

					//tr¿jenummer
					int nrx = (int)(5 / SIZE_FACTOR);
					if (p.getShirtNumber() < 10)
						nrx = (int)(2 / SIZE_FACTOR);

					backbuffergc.setColor(teamB.getColor2());
					backbuffergc.drawString(Integer.toString(p.getShirtNumber()),(int)(p.getX() / SIZE_FACTOR) + pitchPosX - nrx, (int)(p.getY() / SIZE_FACTOR) + pitchPosY + (int)(5 / SIZE_FACTOR));

					if (p.getPlayerAction() == PlayerAction.dribble || p.getPlayerAction() == PlayerAction.receiving_pass){
						int wx = fontMetrics.stringWidth(p.getLastname()) / 2;

						backbuffergc.drawString(p.getLastname(), (int)p.getX() - wx, (int)p.getY() + 18);
					}
				}

				//bolden over spillerne og maalene hvis den er hoej
				if (b.getZ() > 175){
					backbuffergc.setColor(Color.WHITE);
					backbuffergc.fillOval((int)(b.getX() / SIZE_FACTOR) + pitchPosX - ballSize / 2, (int)(b.getY() / SIZE_FACTOR) + pitchPosY - ballSize / 2, ballSize, ballSize);
					backbuffergc.setColor(Color.BLACK);
					backbuffergc.drawOval((int)(b.getX() / SIZE_FACTOR) + pitchPosX - ballSize / 2, (int)(b.getY() / SIZE_FACTOR) + pitchPosY - ballSize / 2, ballSize, ballSize);
				}

				String time = "";
				if (mins < 10)
					time += "0" + mins;
				else
					time += mins;

				if (secs < 10)
					time += ":0" + secs;
				else
					time += ":" + secs;

				//Scoreboard
				int scx = pitchWidth - 190, scw = 190, sch = 35, scy = pitchHeight + pitchPosY + 20;
				backbuffergc.setColor(Color.DARK_GRAY);
				backbuffergc.fillRoundRect(scx, scy, scw, sch, 10, 10);

				backbuffergc.setColor(Color.GRAY);
				backbuffergc.drawRoundRect(scx+1, scy+1, scw-3, sch-3, 10, 10);

				backbuffergc.setColor(Color.WHITE);

				backbuffergc.drawString(time, scx + scw / 2 - fontMetrics.stringWidth(time) / 2, scy + 15);

				backbuffergc.setColor(Color.WHITE);
				String ta = teamA.getName(), tb = teamB.getName();
				while (fontMetrics.stringWidth(ta) > scw / 2 - 10)
					ta = ta.substring(0, ta.length() - 2);
				while (fontMetrics.stringWidth(tb) > scw / 2 - 10)
					tb = tb.substring(0, tb.length() - 2);

				String teams = ta + " " + goalsA + " - " + goalsB + " " + tb;
				backbuffergc.drawString(teams, scx + scw / 2 - fontMetrics.stringWidth(teams) / 2, scy + 30);

				//kommentarer
				backbuffergc.setFont(fontCommentary);
				synchronized (commentary) {
					while (commentary.size() > 3)
						commentary.remove(0);

					for (int i = 0; i < commentary.size(); i++)
						backbuffergc.drawString(commentary.get(i), pitchPosX + 10, pitchPosY + pitchHeight + 17 + (commentary.size() - i) * 13);
				}

				if (shootout && currentFrame > penaltyShootoutStartsFrame){
					//Penalty scoreboard
					scx = 100;
					scw = 250;
					sch = 60;
					scy = pitchHeight + pitchPosY - 65;
					backbuffergc.setColor(Color.DARK_GRAY);
					backbuffergc.fillRoundRect(scx, scy, scw, sch, 10, 10);
					
					backbuffergc.setColor(Color.GRAY);
					backbuffergc.drawRoundRect(scx+1, scy+1, scw-3, sch-3, 10, 10);

					backbuffergc.setColor(Color.WHITE);
					backbuffergc.drawString("Penalties", scx + 5, scy + 15);
					backbuffergc.drawString(teamA.getName(), scx + 5, scy + 35);
					backbuffergc.drawString(teamB.getName(), scx + 5, scy + 50);
					
					int maxWidth = fontMetrics.stringWidth(teamA.getName());
					if (fontMetrics.stringWidth(teamB.getName()) > maxWidth)
						maxWidth = fontMetrics.stringWidth(teamB.getName());
					
					int shootoutGoalsA = 0, shootoutGoalsB = 0;
					int dotSize = 10;
					for (int i = 0; i < shootoutShotsA.size(); i++){
						if (shootoutShotsA.get(i) == 2){
							backbuffergc.setColor(Color.GREEN);
							shootoutGoalsA++;
						}
						else if (shootoutShotsA.get(i) == 1)
							backbuffergc.setColor(Color.RED);
						else if (shootoutShotsA.get(i) == 0)
							backbuffergc.setColor(Color.BLACK);
						
						backbuffergc.fillOval(scx + maxWidth + 20 + i * (dotSize+2), scy + 35 - 9, dotSize, dotSize);
						
						backbuffergc.setColor(Color.GRAY);
						backbuffergc.drawOval(scx + maxWidth + 20 + i * (dotSize+2), scy + 35 - 9, dotSize, dotSize);
					}
					for (int i = 0; i < shootoutShotsB.size(); i++){
						if (shootoutShotsB.get(i) == 2){
							backbuffergc.setColor(Color.GREEN);
							shootoutGoalsB++;	
						}
						else if (shootoutShotsB.get(i) == 1)
							backbuffergc.setColor(Color.RED);
						else if (shootoutShotsB.get(i) == 0)
							backbuffergc.setColor(Color.BLACK);

						backbuffergc.fillOval(scx + maxWidth + 20 + i * (dotSize+2), scy + 50 - 9, dotSize, dotSize);
						
						backbuffergc.setColor(Color.GRAY);
						backbuffergc.drawOval(scx + maxWidth + 20 + i * (dotSize+2), scy + 50 - 9, dotSize, dotSize);
					}	
					
					
					backbuffergc.setColor(Color.WHITE);
					backbuffergc.drawString(shootoutGoalsA+"", scx + scw - 15, scy + 35);
					backbuffergc.drawString(shootoutGoalsB+"", scx + scw - 15, scy + 50);
				}

				//		backbuffergc.drawString(ta, scx + 5, 50);
				//		backbuffergc.drawString(tb, scx + 5, 65);
				//
				//		String sa = Integer.toString(goalsA), sb = Integer.toString(goalsB);
				//
				//		backbuffergc.drawString(sa, scx + scw - 5 - fontMetrics.stringWidth(sa), 50);
				//		backbuffergc.drawString(sb, scx + scw - 5 - fontMetrics.stringWidth(sb), 65);

				//Eventlinie
				int lineY = pitchPosY + pitchHeight + 10;
				backbuffergc.setColor(Color.GRAY.darker());
				backbuffergc.fillRoundRect(pitchPosX, lineY - 7, pitchWidth, 14, 3, 3);
				backbuffergc.setColor(Color.BLACK);
				backbuffergc.drawRoundRect(pitchPosX, lineY - 7, pitchWidth, 14, 3, 3);
				backbuffergc.setColor(Color.WHITE);
				backbuffergc.fillRect(pitchPosX, lineY, pitchWidth, 2);
				backbuffergc.drawLine(pitchPosX, lineY-3, pitchPosX, lineY+3);
				backbuffergc.drawLine(pitchPosX + pitchWidth, lineY-3, pitchPosX + pitchWidth, lineY+3);
				
				int i = 0; //sættes til 0 eller 5 for hver gang for at sætte stregerne skiftevis under og over tidslinien
				for (ReplayEvent event : events){
					double percent = (double)event.getFrame() / (double)totalFrames;
					backbuffergc.drawLine(pitchPosX + (int)(pitchWidth * percent), lineY - 5 + i, pitchPosX + (int)(pitchWidth * percent), lineY + i);
					if (i == 0) i = 5;
					else i = 0;
				}
				
				if (showTimeLinetime){
					backbuffergc.setColor(Color.BLACK);
					backbuffergc.drawLine(mouseX, lineY-5, mouseX, lineY+5);
					
					backbuffergc.setColor(Color.WHITE);
					backbuffergc.drawString(timelineString, mouseX, lineY - 10);
					
					if (markedEvent != null){
						backbuffergc.drawString(markedEvent.getDescription(), mouseX, lineY + 1);
					}
				}
					
				//Markør på tidslinien
				double percent = (double)currentFrame / (double)totalFrames;
				int markerX = pitchPosX + (int)(pitchWidth * percent);
				
				backbuffergc.setColor(Color.RED);
				backbuffergc.drawLine(markerX, lineY-5, markerX, lineY+5);
				
				
				//Buttons
				
				backbuffergc.setColor(buttonBGColor);
				backbuffergc.fill3DRect(btnNextHighlight, buttonY, buttonW, buttonH, true);
				backbuffergc.setColor(buttonTextColor);
				backbuffergc.drawString(">>|", btnNextHighlight + 13, buttonY + 13);
				
				backbuffergc.setColor(buttonBGColor);
				backbuffergc.fill3DRect(btnPrevHighlight, buttonY, buttonW, buttonH, true);
				backbuffergc.setColor(buttonTextColor);
				backbuffergc.drawString("|<<", btnPrevHighlight + 13, buttonY + 13);
				
				backbuffergc.setColor(buttonBGColor);
				backbuffergc.fill3DRect(btnPlay, buttonY, buttonW, buttonH, true);
				backbuffergc.setColor(buttonTextColor);
				if (pause)
					backbuffergc.drawString(">", btnPlay + 13, buttonY + 13);
				else
					backbuffergc.drawString("||", btnPlay + 13, buttonY + 13);
				
				if (g != null && backbuffer != null){
					g.drawImage(backbuffer, 0, 0, windowWidth, windowHeight, this);
				}
			}
			else{
				if (g != null && backbuffer != null){
					backbuffergc.setFont(fontCommentary);
					backbuffergc.setColor(Color.GREEN.darker());
					backbuffergc.fillRect(0, 0, windowWidth, windowHeight);
					backbuffergc.setColor(Color.WHITE);
					backbuffergc.drawString("Loading replay...", 50, 50);
					g.drawImage(backbuffer, 0, 0, windowWidth, windowHeight, this);
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	class task extends TimerTask{

		public void run() {

			if (loadDone){
				if (currentFrame < data.size() && !pause){
					parseInput(data.get(currentFrame), false, 0);
					currentFrame++;
//					System.out.println(currentFrame);
				}
				paint(getGraphics());
			}
		}

	}
	
	class Loader extends Thread {
	    URL baseURL;
	    String fileName;

	    public Loader(String fileName) {

	    	System.out.println(fileName);
	    	this.fileName = fileName;
	        setPriority(MIN_PRIORITY);
	        start();
	    }

	    public void run() {
	    	long start = System.currentTimeMillis();
			
			BufferedReader bf = null;
			InputStream stream = null;

			if (local && 1==2)
				try {
					bf = new BufferedReader(new FileReader("../matches/" + leagueId + "/" + matchId + ".txt"));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			else{
				URL url;
				try {
					url = new URL(fileName);
					stream = url.openStream();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
					
				bf = new BufferedReader(new InputStreamReader(stream), 100 * 1024);
			}
			int empty = 0;
			String input = "";

			try {
				matchSpeed = Integer.parseInt(bf.readLine().split("\\.")[1]);
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			matchSpeed=30;
			
			System.out.println("a");
			
			String initStream = "";
			try {
				initStream = bf.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			initTeams(initStream);
			System.out.println("b");

			int bX = (int)b.getX();
			int bY = (int)b.getY();
			
			System.out.println((System.currentTimeMillis() - start) + " - start reading.");
			int frame = 0;
			try {
				while((input = bf.readLine()) != null){
					if (data.size() % 100 == 0)
						System.out.println(data.size());
					
					parseInput(input, true, frame);
					
					if (data.size() % 500 == 0){
						SortedMap<Integer, Point> allPlayers = new TreeMap<Integer, Point>();
						
						for (Player p : teamA.getPlayers())
							allPlayers.put(p.getId(), new Point((int)p.getX(), (int)p.getY()));
						for (Player p : teamB.getPlayers())
							allPlayers.put(p.getId(), new Point((int)p.getX(), (int)p.getY()));
						
						allPlayers.put(-1, new Point((int)b.getX(), (int)b.getY()));
						
						playerPositionsByFrame.put(data.size(), allPlayers);
						Integer[] g = {goalsA, goalsB};
						goalsByFrame.put(data.size(), g);
					}
					
					String time = "";
					if (mins < 10)
						time += "0" + mins;
					else
						time += mins;

					if (secs < 10)
						time += ":0" + secs;
					else
						time += ":" + secs;
					
					times.add(time);
					data.add(input);
					
					frame++;
				}
				
				
				mins = 0;
				secs = 0;
				goalsA = 0;
				goalsB = 0;
				shootoutShotsA.clear();
				shootoutShotsB.clear();
				
				totalFrames = frame;
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println((System.currentTimeMillis() - start) + " - done reading.");
			
			currentFrame = 1;
//			initTeams(initStream);
			for (Player p : teamA.getPlayers()){
				p.setX(p.getStartPosX());
				p.setY(p.getStartPosY());
			}
			for (Player p : teamB.getPlayers()){
				p.setX(p.getStartPosX());
				p.setY(p.getStartPosY());
			}
			b.setX(bX);
			b.setY(bY);
			
			try {
				bf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (stream != null)
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			System.out.println("File read - " + data.size() + " lines / " + empty + " empty lines");
			commentary.clear();
			loadDone = true;	
			
	    }
	}
}