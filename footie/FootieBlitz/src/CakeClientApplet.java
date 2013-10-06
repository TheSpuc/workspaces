import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import model.Player;
import model.Team;
import model.Match.MatchState;

public class CakeClientApplet extends java.applet.Applet implements Runnable{

	private static final long serialVersionUID = 1L;
	Timer loop = new Timer();
	Timer updateTimer = new Timer();
	int[] x = new int[22];
	int[] y = new int[22];
	int boldX = 0, boldY = 0, boldZ = 0;
	int antalA = 0;
	int antalB = 0;
	
	Team teamA, teamB;
	int goalsA, goalsB;
	
	FontMetrics fontMetrics;
	
    DatagramSocket socket;
    InetAddress address;

    DatagramPacket packet;
    
	int goalX, goalY, goalSize;
	int goal2X, goal2Y, goal2Size;

	final double SIZE_FACTOR = 1.2;

	int windowHeight = (int)(600 / SIZE_FACTOR);
	int windowWidth = (int)(1200 / SIZE_FACTOR);

	int pitchHeight = (int)(513 / SIZE_FACTOR);
	int pitchWidth = (int)(880 / SIZE_FACTOR);

	int pitchPosX = (int)(40 / SIZE_FACTOR);
	int pitchPosY = (int)(40 / SIZE_FACTOR);

	boolean connection;
	String str = "";

	Thread mainloop = null;
	int fr = 0;
	int currFr = 0;
	String matchID = "";

	Graphics backbuffergc;
	Image backbuffer;

	String ip = "";
	boolean httpPolling = true;
	boolean stopped = false;
	
	long lastPacketReceived = 0;
	int mins, secs;
	
	ArrayList<String> commentary = new ArrayList<String>();
	ArrayList<String> data = new ArrayList<String>();
	
	boolean matchLoaded = false;
	int screenState = 0;
	
	boolean shootout = false;
	ArrayList<Integer> shootoutShotsA = new ArrayList<Integer>();
	ArrayList<Integer> shootoutShotsB = new ArrayList<Integer>();
	
	
	public void start() {
//		mainloop = new Thread(this);
//		if (mainloop != null) {
//			mainloop.start();
//		}
	}

	public void stop ()  {
		stopped = true;
		
//		if (mainloop != null)  {
//			if (socket != null && !socket.isClosed()) 
//				socket.close();
//			
//			mainloop.stop();
//			mainloop=null;
//		}

	}

	public void destroy()  {
		updateTimer.cancel();
		updateTimer.purge();
		loop.cancel();
		loop.purge();
	}
	
	public void getData(){

		if (!httpPolling){
			byte[] buf = new byte[256];
			packet = new DatagramPacket(buf, buf.length);

			try {
				socket.receive(packet);
				lastPacketReceived = System.currentTimeMillis();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String str = new String(packet.getData(), 0, packet.getLength());

			String[] s = str.split(":");


			antalA = Integer.parseInt(s[0]);
			antalB = Integer.parseInt(s[1]);

			int i = 0;

			//roed
			while (i < antalA){
//				x[i] = Integer.parseInt(s[i + 2 + i]) / SIZE_FACTOR;
//				y[i] = Integer.parseInt(s[i + 2 + i + 1]) / SIZE_FACTOR;
				i++;
			}

			//blaa
			while (i < antalA + antalB){
//				x[i] = Integer.parseInt(s[i + i + 2]) / SIZE_FACTOR;
//				y[i] = Integer.parseInt(s[i + 2 + i + 1]) / SIZE_FACTOR;
				i++;
			}

			//bold
//			i = antalA * 2+ antalB * 2 + 2;
//			boldX = Integer.parseInt(s[i]) / SIZE_FACTOR;
//			i++;
//			boldY = Integer.parseInt(s[i]) / SIZE_FACTOR;
		}
		else{
			URL url;
			URLConnection urlConn;
			DataOutputStream printout;
			DataInputStream input;
			try {
				System.out.println("Host: " + getCodeBase().getHost());
				if (getCodeBase().getHost().length() == 0){
				
				}else{
					url = new URL("http://www." + getCodeBase().getHost() + "/matches/livedata/" + matchID + "/" + fr);
					urlConn = url.openConnection();
					urlConn.setDoInput (true);
					urlConn.setDoOutput (true);
					urlConn.setUseCaches (false);
					printout = new DataOutputStream (urlConn.getOutputStream ());
					printout.flush ();
					printout.close ();
					input = new DataInputStream (urlConn.getInputStream ());
					String str;
					int frameID = 0;
					while (null != ((str = input.readLine()))){
						String arr[] = str.split("_");

						for (int i=0; i < arr.length; i++){
							synchronized (data) {
								data.add(arr[i].toString());

								try{
									frameID = Integer.parseInt(arr[i].split(":")[0]);
								}catch(Exception e){
									e.printStackTrace();
								}
							}
						}
						if (frameID > fr)
							fr = frameID;

						System.out.println("fr: " + fr);
					}
					lastPacketReceived = System.currentTimeMillis();
					input.close ();
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void runFrame(){
		synchronized (data) {
			if (data.size() > 0)
				if (stopped)
					data.remove(0);
				else
					parseInput(data.remove(0));
		}
		if (!stopped)
			paint(getGraphics());
	}
	
	private void parseInput(String str){
		String ln[] = str.split(":");
		
		int frameID = 0;
		try{
			frameID = Integer.parseInt(ln[0]);
		}catch(NumberFormatException e){
			e.printStackTrace();
		}

		if (ln[0].length() > 0 && frameID > currFr){
			currFr = frameID;

			int j = 1;
			while (j < ln.length){
				try{
//					System.out.println("ln[j]: " + ln[j]);
					if (ln[j].equals("s")){
						j++;
						secs = Integer.parseInt(ln[j]);
					}
					else if(ln[j].equals("m")){
						j++;
						mins = Integer.parseInt(ln[j]);
					}
					else if(ln[j].equals("sc")){
						j++;
						goalsA = Integer.parseInt(ln[j]);
						j++;
						goalsB = Integer.parseInt(ln[j]);
					}
					else if(ln[j].equals("c")){
						j++;
						commentary.add(ln[j]);
					}
					else if(ln[j].equals("lu")){
						//lineup 
					}
					else if(ln[j].equals("Shout")){
						shootout = true;
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
					}
					else if (ln[j].equals("b")){
						j++;
						boldX = (Integer.parseInt(ln[j]));
						j++;
						boldY = (Integer.parseInt(ln[j]));
						j++;
						boldZ = Integer.parseInt(ln[j]);
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

						j++;
						player.setX(Integer.parseInt(ln[j]));
						j++;
						player.setY(Integer.parseInt(ln[j]));
					}
				}
				catch (NumberFormatException e){
					e.printStackTrace();
					System.out.println(ln[0]);
				}
				j++;
			}
		}
		else
			System.out.println(str);
		
	}

	public void initTeams(String str){
		String ss[] = str.split(":");
		try{
			int k = 1;
			while (k < ss.length){
				String p[] = ss[k].split("\\.");
				
				if (p[0].equals("s")){

				}
				else if (p[0].equals("t")){
					if (teamA == null)
						teamA = new Team(p[2], new Color(Integer.parseInt(p[3])), new Color(Integer.parseInt(p[4])), Integer.parseInt(p[1]), null, 0, 0);
					else
						teamB = new Team(p[2], new Color(Integer.parseInt(p[3])), new Color(Integer.parseInt(p[4])), Integer.parseInt(p[1]), null, 0, 0);
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
					}
					else{
						player.setX(-200);
						player.setY(-200);
					}
						
					if (teamB == null)
						teamA.getPlayers().add(player);
					else
						teamB.getPlayers().add(player);
				}

				k++;
			}
			matchLoaded = true;
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}
	
	public void run() {

	}

	public void init() {

		matchID = getParameter("matchid");
		if (matchID == null)
			matchID = "153";
		System.out.println("matchID: " + matchID);
		
		setSize(windowWidth, windowHeight);
		windowWidth = getSize().width;
		windowHeight = getSize().height;
		
		backbuffer = createImage(windowWidth, windowHeight);
		backbuffergc = backbuffer.getGraphics();
		
		goalSize = (int)(59 / SIZE_FACTOR);
		goalX = pitchPosX + pitchWidth;
		goalY = (pitchHeight - goalSize) / 2 + pitchPosY;
		goal2Size = goalSize;
		goal2X = pitchPosX;
		goal2Y = (pitchHeight - goalSize) / 2 + pitchPosY;
		
//	    ip = getCodeBase().getHost();
//	
//	    if (ip == null || ip.length() < 2)
//	    	ip = "86.52.12.153";
//	    
//	    //Til test hvor det koerer lokalt
//	    ip = "localhost";
//	    	
//	    
//		try {
//			socket = new DatagramSocket();
//			
//			address = InetAddress.getByName(ip);
//			
//			byte[] request = new byte[256];
//			DatagramPacket requestPacket = new DatagramPacket(request, request.length, address, 4445);
//			
//			System.out.println("Sending request");
//			
//			socket.send(requestPacket);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		Font font = new Font("Helvetica", Font.PLAIN,  10);
		backbuffergc.setFont(font);

		fontMetrics = getFontMetrics(font);
		
		repaint();		
		
		getData();
		if (data.size() > 0){
			initTeams(data.remove(0));
			
		}
		else{
			System.out.println("No match data found.");
//			System.exit(0);
		}
	
		updateTimer.schedule(new updateTimerTask(), 0, 30);
		loop.schedule(new realTime(), 0, 1000);
		
	}

	private void getScreenTopMenu(){
		Font font = new Font("Helvetica", Font.PLAIN,  10);
		backbuffergc.setFont(font);
		
		backbuffergc.setColor(Color.BLACK);
		backbuffergc.drawRoundRect(11, 11, 75, 20, 5, 5);
		backbuffergc.setColor(Color.GREEN);
		backbuffergc.fillRoundRect(10, 10, 75, 20, 5, 5);
		backbuffergc.setColor(Color.WHITE);
		backbuffergc.drawString("Match", 10 + 75/2 - fontMetrics.stringWidth("Match") / 2 , 15);
		
		backbuffergc.setColor(Color.BLACK);
		backbuffergc.drawRoundRect(101, 11, 75, 20, 5, 5);
		backbuffergc.setColor(Color.GREEN.darker().darker());
		backbuffergc.fillRoundRect(100, 10, 75, 20, 5, 5);
		backbuffergc.setColor(Color.WHITE);
		backbuffergc.drawString("Match stats", 100 + 75/2 - fontMetrics.stringWidth("Match stats") / 2 , 10 + 15);
		
	}
	
	private void getScreenMatchStats(){
		backbuffergc.setColor(Color.GREEN.darker());
		backbuffergc.fillRect(0, 0, windowWidth, windowHeight);

		//Football pitch
		backbuffergc.setColor(Color.WHITE);
		backbuffergc.drawRect(pitchPosX, pitchPosY, pitchWidth, pitchHeight);//Banen
		backbuffergc.drawArc((int)(pitchPosX - 8 / SIZE_FACTOR), (int)(pitchPosY - 8 / SIZE_FACTOR), (int)(16 / SIZE_FACTOR), (int)(16 / SIZE_FACTOR), 0, -90);//Hjørneflag
		backbuffergc.drawArc((int)(pitchPosX + pitchWidth - 8 / SIZE_FACTOR), (int)(pitchPosY - 8 / SIZE_FACTOR), (int)(16 / SIZE_FACTOR), (int)(16 / SIZE_FACTOR), -90, -90);//Hjørneflag
		backbuffergc.drawArc((int)(pitchPosX - 8 / SIZE_FACTOR), (int)(pitchPosY + pitchHeight - 8 / SIZE_FACTOR), (int)(16 / SIZE_FACTOR), (int)(16 / SIZE_FACTOR), 0, 90);//Hjørneflag
		backbuffergc.drawArc((int)(pitchPosX + pitchWidth - 8 / SIZE_FACTOR), (int)(pitchPosY + pitchHeight - 8 / SIZE_FACTOR), (int)(16 / SIZE_FACTOR), (int)(16 / SIZE_FACTOR), -180, -90);//Hjørneflag		
	}
	
	
	private void getScreenMatch(){
		Font font = new Font("Helvetica", Font.PLAIN, 9);
		backbuffergc.setFont(font);
		
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
		
		if (matchLoaded){

			int playerSize = (int)(14 / SIZE_FACTOR);
			int ballSize = (int)(8 / SIZE_FACTOR + boldZ / (80 * SIZE_FACTOR));
			int ballShadowSize = (int)(8 - boldZ / (350 * SIZE_FACTOR));

			backbuffergc.setColor(Color.DARK_GRAY);
			backbuffergc.fillOval((int)(boldX / SIZE_FACTOR + pitchPosX - ballShadowSize / 2 + boldZ / (100 * SIZE_FACTOR)), (int)(boldY / SIZE_FACTOR + pitchPosY - ballShadowSize / 2 + boldZ / (100 * SIZE_FACTOR)), ballShadowSize, ballShadowSize);

			if (boldZ <= 175){
				backbuffergc.setColor(Color.WHITE);
				backbuffergc.fillOval((int)(boldX / SIZE_FACTOR) + pitchPosX - ballSize / 2, (int)(boldY / SIZE_FACTOR) + pitchPosY - ballSize / 2, ballSize, ballSize);
				backbuffergc.setColor(Color.BLACK);
				backbuffergc.drawOval((int)(boldX / SIZE_FACTOR) + pitchPosX - ballSize / 2, (int)(boldY / SIZE_FACTOR) + pitchPosY - ballSize / 2, ballSize, ballSize);
			}

			if (teamA != null)
				for (Player p : teamA.getPlayers()){
					int nrx = (int)(5 / SIZE_FACTOR);
					if (p.getShirtNumber() < 10)
						nrx = (int)(2 / SIZE_FACTOR);

					backbuffergc.setColor(teamA.getColor());
					backbuffergc.fillOval((int)(p.getX() / SIZE_FACTOR) + pitchPosX - playerSize / 2, (int)(p.getY() / SIZE_FACTOR) + pitchPosY - playerSize / 2, playerSize, playerSize);
					backbuffergc.setColor(teamA.getColor2());
					backbuffergc.drawOval((int)(p.getX() / SIZE_FACTOR) + pitchPosX - playerSize / 2, (int)(p.getY() / SIZE_FACTOR) + pitchPosY - playerSize / 2, playerSize, playerSize);
					backbuffergc.drawString(Integer.toString(p.getShirtNumber()),(int)(p.getX() / SIZE_FACTOR) + pitchPosX - nrx, (int)(p.getY() / SIZE_FACTOR) + pitchPosY + (int)(5 / SIZE_FACTOR));
				}


			if (teamB != null)
				for (Player p : teamB.getPlayers()){
					int nrx = (int)(5 / SIZE_FACTOR);
					if (p.getShirtNumber() < 10)
						nrx = (int)(2 / SIZE_FACTOR);

					backbuffergc.setColor(teamB.getColor());
					backbuffergc.fillOval((int)(p.getX() / SIZE_FACTOR) + pitchPosX - playerSize / 2, (int)(p.getY() / SIZE_FACTOR) + pitchPosY - playerSize / 2, playerSize, playerSize);
					backbuffergc.setColor(teamB.getColor2());
					backbuffergc.drawOval((int)(p.getX() / SIZE_FACTOR) + pitchPosX - playerSize / 2, (int)(p.getY() / SIZE_FACTOR) + pitchPosY - playerSize / 2, playerSize, playerSize);
					backbuffergc.drawString(Integer.toString(p.getShirtNumber()),(int)(p.getX() / SIZE_FACTOR) + pitchPosX - nrx, (int)(p.getY() / SIZE_FACTOR) + pitchPosY + (int)(5 / SIZE_FACTOR));
				}

			backbuffergc.setColor(Color.WHITE);
			backbuffergc.drawRect(goalX, goalY, (int)(10 / SIZE_FACTOR), goalSize);
			backbuffergc.drawRect(goal2X - (int)(10 / SIZE_FACTOR), goal2Y, (int)(10 / SIZE_FACTOR), goal2Size);

			if (boldZ > 175){
				backbuffergc.setColor(Color.WHITE);
				backbuffergc.fillOval((int)(boldX / SIZE_FACTOR) + pitchPosX - ballSize / 2, (int)(boldY / SIZE_FACTOR) + pitchPosY - ballSize / 2, ballSize, ballSize);
				backbuffergc.setColor(Color.BLACK);
				backbuffergc.drawOval((int)(boldX / SIZE_FACTOR) + pitchPosX - ballSize / 2, (int)(boldY / SIZE_FACTOR) + pitchPosY - ballSize / 2, ballSize, ballSize);
			}

			//Scoreboard
			int scx = pitchPosX + pitchWidth + 50, scw = 150, sch = 65, scy = 10;
			backbuffergc.setColor(Color.DARK_GRAY);
			backbuffergc.fillRoundRect(scx, scy, scw, sch, 10, 10);

			backbuffergc.setColor(Color.GRAY);
			backbuffergc.drawRoundRect(scx+1, scy+1, scw-3, sch-3, 10, 10);

			backbuffergc.setColor(Color.WHITE);

			String time = "";
			if (mins < 10)
				time += "0" + mins;
			else
				time += mins;

			if (secs < 10)
				time += ":0" + secs;
			else
				time += ":" + secs;

			//		if (matchEngine.getMins() > 43 && matchEngine.getMatch().isFirstHalf())
			//			time += " +" + matchEngine.getAddedMins();
			//		else if (matchEngine.getMins() > 88)
			//			time += " +" + matchEngine.getAddedMins();

			backbuffergc.drawString(time, scx + scw / 2 - fontMetrics.stringWidth(time) / 2, scy + 15);

			backbuffergc.setColor(Color.WHITE);
			String ta = teamA.getName(), tb = teamB.getName();
			while (fontMetrics.stringWidth(ta) > scw - 30)
				ta = ta.substring(0, ta.length() - 2);
			while (fontMetrics.stringWidth(tb) > scw - 30)
				tb = tb.substring(0, tb.length() - 2);

			backbuffergc.drawString(ta, scx + 5, 50);
			backbuffergc.drawString(tb, scx + 5, 65);

			String sa = Integer.toString(goalsA), sb = Integer.toString(goalsB);

			backbuffergc.drawString(sa, scx + scw - 5 - fontMetrics.stringWidth(sa), 50);
			backbuffergc.drawString(sb, scx + scw - 5 - fontMetrics.stringWidth(sb), 65);	
			
			if (shootout){
				//Penalty scoreboard
				scx = 200;
				scw = 250;
				sch = 60;
				scy = pitchHeight + pitchPosY - 15;
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
				
				int dotSize = 10;
				for (int i = 0; i < shootoutShotsA.size(); i++){
					if (shootoutShotsA.get(i) == 2)
						backbuffergc.setColor(Color.GREEN);
					else if (shootoutShotsA.get(i) == 1)
						backbuffergc.setColor(Color.RED);
					else if (shootoutShotsA.get(i) == 0)
						backbuffergc.setColor(Color.BLACK);
					
					backbuffergc.fillOval(scx + maxWidth + 20 + i * (dotSize+2), scy + 35 - 9, dotSize, dotSize);
					
					backbuffergc.setColor(Color.GRAY);
					backbuffergc.drawOval(scx + maxWidth + 20 + i * (dotSize+2), scy + 35 - 9, dotSize, dotSize);
				}
				for (int i = 0; i < shootoutShotsB.size(); i++){
					if (shootoutShotsB.get(i) == 2)
						backbuffergc.setColor(Color.GREEN);
					else if (shootoutShotsB.get(i) == 1)
						backbuffergc.setColor(Color.RED);
					else if (shootoutShotsB.get(i) == 0)
						backbuffergc.setColor(Color.BLACK);

					backbuffergc.fillOval(scx + maxWidth + 20 + i * (dotSize+2), scy + 50 - 9, dotSize, dotSize);
					
					backbuffergc.setColor(Color.GRAY);
					backbuffergc.drawOval(scx + maxWidth + 20 + i * (dotSize+2), scy + 50 - 9, dotSize, dotSize);
				}	
				
				int shootoutGoalsA = 0, shootoutGoalsB = 0;
				for (Integer i : shootoutShotsA)
					shootoutGoalsA++;
				for (Integer i : shootoutShotsB)
					shootoutGoalsB++;
				
				backbuffergc.setColor(Color.WHITE);
				backbuffergc.drawString(shootoutGoalsA+"", scx + scw - 15, scy + 35);
				backbuffergc.drawString(shootoutGoalsB+"", scx + scw - 15, scy + 50);
			}
		}
	}
	
	public void paint(Graphics g) {

		super.paint(g);

		switch(screenState){

		case 0: //Match
			getScreenMatch();
			break;


		case 1: //Match stats

			break;
		}
		
		getScreenTopMenu();
		
		g.drawImage(backbuffer, 0, 0, windowWidth, windowHeight, this);
	}

	 class realTime extends TimerTask{
		public void run(){
			if (matchLoaded)
				getData();
		}
	}
	class updateTimerTask extends TimerTask{
		public void run(){
			runFrame();
		}
	}
}
