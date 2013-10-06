package model;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;	
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import websocket.WebsocketServerThread;

import data.ClubsDAO;
import data.DAOCake;
import data.DAOCake.MatchEventType;
import data.teamSetup.CreateTeamSheet;
import data.teamSetup.SetupPositions;
import data.teamSetup.TeamTactic;
import model.Match.MatchState;

public class MatchEngine{

	String debug = "";
	
	WebsocketServerThread wsServer;
	
	Timer timer = new Timer();
	Timer loop = new Timer();

	String settingWriteFramesToDB = "writeFramesToDB";
	String settingWriteFramesToWS = "writeFramesToWS";
	
	double possessionA;
	double totalPossession;
	long outTime = 0;
	long lastUpdate;
	long frames = 0;
	long fps = 0;
	long runspeed = System.nanoTime();
	int goalX, goalY, goalSize;
	int goal2X, goal2Y, goal2Size;
	Team teamA;
	Team teamB;
	double matchSpeed = 7, originalMatchSpeed = 7; //7
	Timer matchTimer;
	public static boolean pause = false;
	int prevmins, prevsecs, mins = 0, secs = 0, ticks = 0, addedMins = 2;
	Random r = new Random();
	public static boolean waitForSubs = false;
	public String streamInject = "";
	
	//replay
	ArrayList<String> dataBufferStr = new ArrayList<String>();
	boolean saveReplays = false;
	int liveMatchFrame = 0;
	ArrayList<String> liveFrames = new ArrayList<String>(); 
	
	Player blaa, roed, blaa2, roed2, roed3, roed4, roed5, roed6, roed7, roed8, 
	roed9, roed10, blaa3, blaa4, blaa5, blaa6, blaa7, blaa8, blaa9, blaa10;
	Player kBlaa, kRoed;

	int windowHeight = 700;
	int windowWidth = 1050;

	int pitchHeight = 513;
	int pitchWidth = 880;

	int pitchPosX = 90;
	int pitchPosY = 80;

	Pitch pitch = new Pitch(pitchHeight, pitchPosX, pitchPosY, pitchWidth);
	Match match = null;

	float x = 100, y = 150;
	float x2 = 100, y2 = 50;
	Bold bold = new Bold(pitchHeight,pitchPosX, pitchPosY, pitchWidth, pitch, match);
	long time = 0;
	boolean timeRunning = false;
	float a = 0.01f, a2 = 0.01f;

	private boolean loadFinished = false;

	Thread mainloop = null;

	Settings settings;

	int matchId, leagueId;

	Player controlledPlayer;
	
	public MatchEngine(int matchId, int leagueId){

		this.leagueId = leagueId;
		this.matchId = matchId;

		init(matchId);
	}

	public void update(){
		
		handleMatchStates();
		
		handleSubs();
		
		updateTeamsBallPlayers();

		handleLiveStream();

		handleReplay();

		handleTimeAndStats();
	
		
				
		debug = match.getState().toString();
		debug = "Team A: " + teamA.getTeamState();

		frames++;
	}

	private void updateTeamsBallPlayers(){
		if (bold.getLastLastTouch() != null){
			teamA.update(bold);
			teamB.update(bold);
		}
		
		bold.setSettings(settings);
		bold.update();

		for (Player p : teamA.getPlayers()){
			if (Hjaelper.Distance(p.x, p.y, bold.getX(), bold.getY()) <= 8 &&
					bold.getZ() < p.height){	

				if (bold.passTo != null && bold.passFrom != null && bold.passFrom.myTeam == p.myTeam && !bold.passFrom.equals(p)){
					bold.passFrom.playerMatchStats.passesOnTarget++;
					bold.passTo = null;
					bold.passFrom = null;
				}

			}
			
			p.update();
			p.move(false, p.getSpeed(), p.getA(), p.getX(), p.getY());
			p.setSettings(settings);
		}

		for (Player p : teamB.getPlayers()){
			if (Hjaelper.Distance(p.x, p.y, bold.getX(), bold.getY()) <= 8 &&
					bold.getZ() < p.height){	

				if (bold.passTo != null && bold.passFrom != null && bold.passFrom.myTeam == p.myTeam && !bold.passFrom.equals(p)){
					bold.passFrom.playerMatchStats.passesOnTarget++;
					bold.passTo = null;
					bold.passFrom = null;
				}
			}
			
			p.update();
			p.move(false, p.getSpeed(), p.getA(), p.getX(), p.getY());
			p.setSettings(settings);
		}
	}
	
	public String getStream(){
		String stream = "";
		
		if (streamInject.length() > 0){
			stream = streamInject;
			streamInject = "";
		}
		
		if (mins != prevmins){
			stream += "m:" + mins + ":";
			prevmins = mins;
		}
		
		if (secs != prevsecs){
			stream += "s:" + secs + ":";
			prevsecs = secs;
		}

		if (bold.getPrevX() != (int)bold.getX() || bold.getPrevY() != (int)bold.getY()){
			stream += "b:" + (int)(bold.getX() - pitchPosX) + ":" + (int)(bold.getY() - pitchPosY) + ":" + (int)(bold.getZ()) + ":";//bold
			bold.setPrevX((int)bold.getX());
			bold.setPrevY((int)bold.getY());
		}
		
		//Commentary
		synchronized (match.getCommentary()) {
			if (match.getCommentary().size() > 0){
				for (String s : match.getCommentary())
					stream += "c:" + s + ":";
				
				match.getCommentary().clear();
			}
		}
		
		
		for (Player p : teamA.getPlayers()){
			if (p.getPrevX() != (int)p.getX() || p.getPrevY() != (int)p.getY()){
				stream += p.getId() + ":" + (int)(p.getX() - pitchPosX) + ":" + (int)(p.getY() - pitchPosY) + ":";//x og y for hver spiller
				p.setPrevX((int)p.getX());
				p.setPrevY((int)p.getY());
			}
		}
		for (Player p : teamA.getUsedSubs()){
			if (p.getPrevX() != (int)p.getX() || p.getPrevY() != (int)p.getY()){
				stream += p.getId() + ":" + (int)(p.getX() - pitchPosX) + ":" + (int)(p.getY() - pitchPosY) + ":";//x og y for hver spiller
				p.setPrevX((int)p.getX());
				p.setPrevY((int)p.getY());
			}
		}
		for (Player p : teamB.getPlayers()){
			if (p.getPrevX() != (int)p.getX() || p.getPrevY() != (int)p.getY()){
				stream += p.getId() + ":" + (int)(p.getX() - pitchPosX) + ":" + (int)(p.getY() - pitchPosY) + ":";//x og y for hver spiller
				p.setPrevX((int)p.getX());
				p.setPrevY((int)p.getY());
			}	
		}
		for (Player p : teamB.getUsedSubs()){
			if (p.getPrevX() != (int)p.getX() || p.getPrevY() != (int)p.getY()){
				stream += p.getId() + ":" + (int)(p.getX() - pitchPosX) + ":" + (int)(p.getY() - pitchPosY) + ":";//x og y for hver spiller
				p.setPrevX((int)p.getX());
				p.setPrevY((int)p.getY());
			}
		}
		
		if (stream.length() > 0)
			stream = stream.substring(0, stream.length() - 1);
		return stream; 
	}
	
	public void writeReplay(String fileName, ArrayList<Integer> contents) throws FileNotFoundException{

		PrintWriter pw = new PrintWriter(System.getProperty("user.dir") + "\\bin\\" + fileName);

		for (int i = 0; i < contents.size(); i++)
			pw.println(contents.get(i));

		pw.flush();
		pw.close();

	}

	public void updateTactics(){
		
		System.out.println("Match engine updating tactics: " + mins + ":" + secs);
		for (String s : match.getManagerCommands()){
			
			String[] arr = s.split(":");
			
			if (arr[0].equals("updroles")){
				System.out.println("updroles");
				Team t = null;
				if (teamA.getId() == Integer.parseInt(arr[1]))
					t = teamA;
				else if (teamB.getId() == Integer.parseInt(arr[1]))
					t = teamB;
				
				if (t != null) DAOCake.updateTeamRoles(t);
			}
			else if (arr[0].equals("updplayertac")){
				System.out.println("updplayertac");
				int id = Integer.parseInt(arr[1]);
				Player player = null;

				for (Player p : teamA.getPlayers())
					if (p.id == id)
						player = p;
				
				for (Player p : teamB.getPlayers())
					if (p.id == id)
						player = p;
				
				for (Player p : teamA.getSubs())
					if (p.id == id)
						player = p;
				
				for (Player p : teamB.getSubs())
					if (p.id == id)
						player = p;
				
				if (player != null) DAOCake.updatePlayerTactics(player);
			}
			else{
				System.out.println("updteamtac");
				
				Team t = null;
				if (teamA.getId() == Integer.parseInt(arr[0]))
					t = teamA;
				else if (teamB.getId() == Integer.parseInt(arr[0]))
					t = teamB;

				if (t != null){
					ArrayList<Player> onField = new ArrayList<Player>();

					for (int i = 1; i < arr.length; i++){

						String[] pl = arr[i].split(",");

						for (Player p : t.getPlayers()){
							if (p.getId() == Integer.parseInt(pl[0])){

								onField.add(p);

								System.out.println(p.getLastname() + " er på banen og bliver.");

								if (p.getMyTeam().getGoalX() > pitch.getPitchMidX() && Integer.parseInt(pl[1]) < pitch.getPitchMidX()){ 
									p.setStartPosX(pitchWidth - Integer.parseInt(pl[1]) + pitchPosX);
									p.setStartPosY(pitchHeight - Integer.parseInt(pl[2]) + pitch.getPitchPosY());
								}
								else{							
									p.setStartPosX(Integer.parseInt(pl[1]) + pitchPosX);
									p.setStartPosY(pitch.getPitchPosY() + Integer.parseInt(pl[2]));
								}
								

							}
						}
						for (Player p : t.getSubs()){
							if (p.getId() == Integer.parseInt(pl[0])){

								t.getPlayers().add(p);
								onField.add(p);

								t.subNo++;
								DAOCake.subOn(matchId, mins, t.id, p.getId(), t.subNo);
								
								System.out.println(p.getLastname() + " bliver skiftet ind.");

								if (p.getMyTeam().getGoalX() > pitch.getPitchMidX() && Integer.parseInt(pl[1]) < pitch.getPitchMidX()){ 
									p.setStartPosX(pitchWidth - Integer.parseInt(pl[1]) + pitchPosX);
									p.setStartPosY(pitchHeight - Integer.parseInt(pl[2]) + pitch.getPitchPosY());
								}
								else{							
									p.setStartPosX(Integer.parseInt(pl[1]) + pitchPosX);
									p.setStartPosY(pitch.getPitchPosY() + Integer.parseInt(pl[2]));
								}

							}
						}

					}

					ArrayList<Player> getOut = new ArrayList<Player>();

					for (Player p : t.getPlayers())
						if (onField.contains(p)){
							t.getSubs().remove(p);
							t.getUsedSubs().remove(p);
						}
						else
							getOut.add(p);

					//					else if (t.getSubs().contains(p))
					//						t.getSubs().remove(p);

					for (Player p : getOut){

						System.out.println("Skiftet ud: " + p.getLastname());
						
						DAOCake.subOff(matchId, t.id, p.id, t.subNo);
						
						t.getPlayers().remove(p);
						t.getUsedSubs().add(p);

					}
					onField.get(0).isKeeper = true;
					onField.get(0).setRole(PlayerRole.GK);	
					
					for (Player p : t.getPlayers())
						System.out.println("ON: " + p.lastname);
					for (Player p : t.getUsedSubs())
						System.out.println("OFF: " + p.lastname);
					for (Player p : t.getSubs())
						System.out.println("SUB: " + p.lastname);

				}
			}
		}
		liveFrames.add("(" + liveMatchFrame + ", " + matchId + ", 'lu:" + getLineupNames() + "'), ");
		liveMatchFrame++;
		
		if (settings != null && settings.sysoutCategory(settingWriteFramesToDB)){
			try {
				DAOCake.executeSimpleStatement("DELETE FROM livematches WHERE id=0");
				String sql = "INSERT into livematches (id, match_id, frame) values (0, " + matchId + ", '" + getLineupNames() + "')";
				DAOCake.executeSimpleStatement(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		match.getManagerCommands().clear();
		
		//opdater sorterede lister af spillerne
		teamA.sortByHeight();
		teamB.sortByHeight();
		teamA.sortFrontToBack();
		teamB.sortFrontToBack();
		
		teamA.setFrontAndBackPlayers();
		teamB.setFrontAndBackPlayers();
		
		calculateBacklines();
	}
	
	public String getLineupNames(){
		String res = "";
		
		res += "sc." + bold.getTeamAMaal() + "." + bold.getTeamBMaal() + ":";
		
		res += "t." + teamA.getId() + "." + teamA.getName() + "." + teamA.getColor().getRGB() + "." + teamA.getColor2().getRGB() + ":";
		
		for (Player p : teamA.getPlayers())
			res += p.getId() + "." + p.getShirtNumber() + "." + p.getFirstname().replace(".", "") + "." + p.getLastname().replace(".", "") + "." + ((int)p.getStartPosX()-pitch.getPitchPosX()) + "." + ((int)p.getStartPosY()-pitch.getPitchPosY()) + ":";
		
		res += "s:";
		
		for (Player p : teamA.getSubs())
			res += p.getId() + "." + p.getShirtNumber() + "." + p.getFirstname().replace(".", "") + "." + p.getLastname().replace(".", "") + ":";
		
		res += "off:";
		
		for (Player p : teamA.getUsedSubs())
			res += p.getId() + "." + p.getShirtNumber() + "." + p.getFirstname().replace(".", "") + "." + p.getLastname().replace(".", "") + ":";
		
		res += "t." + teamB.getId() + "." + teamB.getName() + "." + teamB.getColor().getRGB() + "." + teamB.getColor2().getRGB() + ":";
		
		for (Player p : teamB.getPlayers())
			res += p.getId() + "." + p.getShirtNumber() + "." + p.getFirstname().replace(".", "") + "." + p.getLastname().replace(".", "") + "." + ((int)p.getStartPosX()-pitch.getPitchPosX()) + "." + ((int)p.getStartPosY()-pitch.getPitchPosY()) + ":";
		
		res += "s:";
		
		for (Player p : teamB.getSubs())
			res += p.getId() + "." + p.getShirtNumber() + "." + p.getFirstname().replace(".", "") + "." + p.getLastname().replace(".", "") + ":";
		
		res += "off:";
		
		for (Player p : teamA.getUsedSubs())
			res += p.getId() + "." + p.getShirtNumber() + "." + p.getFirstname().replace(".", "") + "." + p.getLastname().replace(".", "") + ":";
		
		res = res.substring(0, res.length() - 1);
		
		res = res.replace("'", "");
		return res;
	}
	
	public String getInitStream(){
		String res = "";
		
		res += "sc." + bold.getTeamAMaal() + "." + bold.getTeamBMaal() + ":";
		
		String color1 = "rgb(" + teamA.getColor().getRed() + "," + teamA.getColor().getGreen() + "," + teamA.getColor().getBlue() + ")";
		String color2 = "rgb(" + teamA.getColor2().getRed() + "," + teamA.getColor2().getGreen() + "," + teamA.getColor2().getBlue() + ")";
		res += "t." + teamA.getId() + "." + teamA.getName() + "." + color1 + "." + color2 + ":";
		
		for (Player p : teamA.getPlayers())
			res += p.getId() + "." + p.getShirtNumber() + "." + p.getFirstname().replace(".", "") + "." + p.getLastname().replace(".", "") + "." + ((int)p.getStartPosX()-pitch.getPitchPosX()) + "." + ((int)p.getStartPosY()-pitch.getPitchPosY()) + ":";
		
		res += "s:";
		
		for (Player p : teamA.getSubs())
			res += p.getId() + "." + p.getShirtNumber() + "." + p.getFirstname().replace(".", "") + "." + p.getLastname().replace(".", "") + ":";
		
		res += "off:";
		
		for (Player p : teamA.getUsedSubs())
			res += p.getId() + "." + p.getShirtNumber() + "." + p.getFirstname().replace(".", "") + "." + p.getLastname().replace(".", "") + ":";
		
		color1 = "rgb(" + teamB.getColor().getRed() + "," + teamB.getColor().getGreen() + "," + teamB.getColor().getBlue() + ")";
		color2 = "rgb(" + teamB.getColor2().getRed() + "," + teamB.getColor2().getGreen() + "," + teamB.getColor2().getBlue() + ")";
		res += "t." + teamB.getId() + "." + teamB.getName() + "." + color1 + "." + color2 + ":";
		
		for (Player p : teamB.getPlayers())
			res += p.getId() + "." + p.getShirtNumber() + "." + p.getFirstname().replace(".", "") + "." + p.getLastname().replace(".", "") + "." + ((int)p.getStartPosX()-pitch.getPitchPosX()) + "." + ((int)p.getStartPosY()-pitch.getPitchPosY()) + ":";
		
		res += "s:";
		
		for (Player p : teamB.getSubs())
			res += p.getId() + "." + p.getShirtNumber() + "." + p.getFirstname().replace(".", "") + "." + p.getLastname().replace(".", "") + ":";
		
		res += "off:";
		
		for (Player p : teamA.getUsedSubs())
			res += p.getId() + "." + p.getShirtNumber() + "." + p.getFirstname().replace(".", "") + "." + p.getLastname().replace(".", "") + ":";
		
		res = res.substring(0, res.length() - 1);
		
		res = res.replace("'", "");
		
		System.out.println(res);
		return res;
		
	}
	
	private void goToHalf(int half){
		match.setHalf(half);
		
		if (half == 3)
			DAOCake.setET(match.getMatchId());
		
		//spejl opstilling
		if (half == 2 || half == 4){
			for (Player p : teamA.getPlayers()){
				p.setStartPosX(teamB.getGoalX() - Math.abs(teamA.getGoalX() - p.getStartPosX()));
				if (p.getStartPosY() != -20) p.setStartPosY(pitch.getPitchMidY() + pitch.getPitchMidY() - p.getStartPosY());
			}
			for (Player p : teamB.getPlayers()){
				p.setStartPosX(teamA.getGoalX() + Math.abs(teamB.getGoalX() - p.getStartPosX()));
				if (p.getStartPosY() != -20) p.setStartPosY(pitch.getPitchMidY() + pitch.getPitchMidY() - p.getStartPosY());
			}
		}
		else{
			for (Player p : teamA.getPlayers()){
				p.setStartPosX(teamB.getGoalX() + Math.abs(teamA.getGoalX() - p.getStartPosX()));
				if (p.getStartPosY() != -20) p.setStartPosY(pitch.getPitchMidY() + pitch.getPitchMidY() - p.getStartPosY());
			}
			for (Player p : teamB.getPlayers()){
				p.setStartPosX(teamA.getGoalX() - Math.abs(teamB.getGoalX() - p.getStartPosX()));
				if (p.getStartPosY() != -20) p.setStartPosY(pitch.getPitchMidY() + pitch.getPitchMidY() - p.getStartPosY());
			}
		}

		if (half % 2 == 0){
			teamA.setGoalX(goalX);
			teamA.setGoalY(goalY);
			teamB.setGoalX(goal2X);
			teamB.setGoalY(goal2Y);
			match.setSetPieceTeam(teamB);
		}else{
			teamB.setGoalX(goalX);
			teamB.setGoalY(goalY);
			teamA.setGoalX(goal2X);
			teamA.setGoalY(goal2Y);
			match.setSetPieceTeam(teamA);
		}

		match.setState(MatchState.BEFORE_MATCH);

		switch (half){
			case 2:
				mins = 45;
				break;
			
			case 3:
				mins = 90;
				break;
				
			case 4:
				mins = 105;
				break;
		}
		
		secs = 0;
		bold.setX(pitchPosX + pitchWidth / 2);
		bold.setY(pitchPosY + pitchHeight / 2);
		bold.setZ(0);
		bold.setSpeed(0);
		bold.setZForce(0);
		matchSpeed = originalMatchSpeed;
		match.setHalfRunning(false);
	}
	
	private void startPenaltyShootout(){
		
		mins = 120;
		
		streamInject += "Shout:";
		
		match.setState(MatchState.PENALTYSHOOTOUT);
		if (r.nextBoolean()) match.setSetPieceTeam(teamA);
		else match.setSetPieceTeam(teamB);
		teamA.setPenaltyTakers();
		teamB.setPenaltyTakers();
		if (r.nextBoolean()) match.setSetPieceX(pitch.getPitchPosX() + 88);
		else match.setSetPieceX(pitch.getPitchPosX() + pitch.getPitchWidth() - 88);
		match.setSetPieceY(pitch.getPitchHeight() / 2 + pitch.getPitchPosY());
		
		for (Player p : teamA.getPlayers())
			p.setState(p.getStatePenaltyShootout());
		for (Player p : teamB.getPlayers())
			p.setState(p.getStatePenaltyShootout());
		
		bold.setShot(false);
	}
	
	private void handleMatchStates(){

		if(match.getState().equals(MatchState.PENALTYSHOOTOUT)){
			
			
			//Hvis der er skudt skal vi finde ud af om der er mål
			if (bold.isShot()){
				
				if (match.getPenaltyTaken()){
					//Hvis straffet er taget og bolden er ude er der ikke mål
					if (bold.isBallOut() && !bold.isMaal()){
						match.getSetPieceTeam().setShootoutPenalty(match.getSetPieceTeam().getShootoutPenalty() + 1);
						
						if (match.getSetPieceTeam().equals(teamA)){
							match.setSetPieceTeam(teamB);
							if (match.getShootoutShotsA().get(match.getShootoutShotsA().size() - 1) == 0){
								match.getShootoutShotsA().remove(match.getShootoutShotsA().size() - 1);
								match.getShootoutShotsA().add(1);
								
								streamInject += "shoutA:";
								for (Integer i : match.getShootoutShotsA())
									streamInject += i;
								streamInject +=":";
							}
						}
						else{
							match.setSetPieceTeam(teamA);
							if (match.getShootoutShotsB().get(match.getShootoutShotsB().size() - 1) == 0){
								match.getShootoutShotsB().remove(match.getShootoutShotsB().size() - 1);
								match.getShootoutShotsB().add(1);
								
								streamInject += "shoutB:";
								for (Integer i : match.getShootoutShotsB())
									streamInject += i;
								streamInject +=":";
							}
						}
						
						match.setPenaltyTaken(false);
						bold.setShot(false);
					}
					else if (bold.isMaal()){
						bold.setShot(false);
						match.setPenaltyTaken(false);
						
						if (match.getSetPieceTeam().equals(teamA)){
							match.setShootoutGoalsA(match.getShootoutGoalsA() + 1);
							match.setSetPieceTeam(teamB);
						}
						else{
							match.setShootoutGoalsB(match.getShootoutGoalsB() + 1);
							match.setSetPieceTeam(teamA);
						}
				
					}
				}
			}
			
			//Hvis der er taget straffe og der er gået nok tid og der ikke har været mål og der ikke er nogen der har vundet endnu så skift hold og tag et nyt
			if (match.getPenaltyTaken() && match.shootoutWinner() == null && System.currentTimeMillis() - match.getPenaltyTime() > 3000 &&
					(bold.getSpeed() < 1 || bold.isUdenfor())){
				
				match.getSetPieceTeam().setShootoutPenalty(match.getSetPieceTeam().getShootoutPenalty() + 1);
				
				if (match.getSetPieceTeam().equals(teamA)){
					match.setSetPieceTeam(teamB);
					if (match.getShootoutShotsA().get(match.getShootoutShotsA().size() - 1) == 0){
						match.getShootoutShotsA().remove(match.getShootoutShotsA().size() - 1);
						match.getShootoutShotsA().add(1);
						
						streamInject += "shoutA:";
						for (Integer i : match.getShootoutShotsA())
							streamInject += i;
						streamInject +=":";
					}
				}
				else{
					match.setSetPieceTeam(teamA);
					if (match.getShootoutShotsB().get(match.getShootoutShotsB().size() - 1) == 0){
						match.getShootoutShotsB().remove(match.getShootoutShotsB().size() - 1);
						match.getShootoutShotsB().add(1);
						
						streamInject += "shoutB:";
						for (Integer i : match.getShootoutShotsB())
							streamInject += i;
						streamInject +=":";
					}
				}
				
				match.setPenaltyTaken(false);
				bold.setShot(false);
			}
			
			//Hvis der ikke er taget straffe skal bolden flyttes hen mod / til straffepletten
			if (!match.getPenaltyTaken()){
				double ta = Math.atan2((match.getSetPieceX() - bold.getX()), (match.getSetPieceY() - bold.getY()));
				bold.setA(ta);
				bold.setSpeed(Hjaelper.Distance(bold.getX(), bold.getY(), match.getSetPieceX(), match.getSetPieceY()) * 1.4);
			}
			
			if (match.shootoutWinner() != null){
				match.setState(MatchState.FINISHED);
				closeMatch();
				DAOCake.updateShootout(match.getMatchId(), match.getShootoutGoalsA(), match.getShootoutGoalsB());
			}
				
		}
		
		if(match.getState().equals(MatchState.FINISHED)){

			boolean closeTransmission = true;
			for (Player p : teamA.getPlayers())
				if (p.getY() > pitch.getPitchPosY())
					closeTransmission = false;
			for (Player p : teamB.getPlayers())
				if (p.getY() > pitch.getPitchPosY())
					closeTransmission = false;

			if (closeTransmission){
				loop.cancel();
				
				if (settings != null && settings.sysoutCategory(settingWriteFramesToDB)){
					String sql = "DELETE FROM livematches;";

					try {
						DAOCake.executeSimpleStatement(sql);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.exit(0);
			}
		}

		//Stop kampe der ikke selv stopper
		if (match.getHalf() == 1 && mins > 50 && match.getState().equals(MatchState.HALF_TIME)){
			match.setState(MatchState.HALF_TIME);
			match.setHalf(2);
			outTime = System.currentTimeMillis();
			match.setHalfRunning(false);
		}
		else if ((match.getHalf() == 1 || match.getHalf() == 2) && mins > 95 && !match.getState().equals(MatchState.FINISHED)){
			if (match.playExtraTime())
				goToHalf(3);
			else
				closeMatch();
		}
		else if (match.getHalf() == 3 && mins > 110 && !match.getState().equals(MatchState.BEFORE_MATCH)){
			goToHalf(4);
		}
		else if (match.getHalf() == 4 && mins > 125 && 
				bold.getTeamAMaal() + match.firstMatchGoalsA == bold.getTeamBMaal() + match.firstMatchGoalsB && 
				!match.getState().equals(MatchState.PENALTYSHOOTOUT)){
			
			startPenaltyShootout();
		}
		else if (match.getHalf() == 4 && mins > 125 && 
				bold.getTeamAMaal() + match.firstMatchGoalsA != bold.getTeamBMaal() + match.firstMatchGoalsB && 
				!match.getState().equals(MatchState.PENALTYSHOOTOUT) && !match.getState().equals(MatchState.FINISHED)){
			
			closeMatch();
		}

		if (match.getState() == MatchState.ON){

			//i overtid gaar tiden langsommere
			if(match.getHalf() == 1 && mins >= 45)
				matchSpeed = 12;
			else if (mins >= 90)
				matchSpeed = 12;

			if (match.getHalf() == 1 && mins >= 45 + addedMins && secs > r.nextInt(59) && 
					Math.abs(bold.getX() - goal2X) > 250 && Math.abs(bold.getX() - goalX) > 250){

				match.setState(MatchState.HALF_TIME);
				outTime = System.currentTimeMillis();
				match.setHalfRunning(false);
			}
			else if (match.getHalf() == 2 && mins >= 90 + addedMins && secs > r.nextInt(59) && 
					Math.abs(bold.getX() - goal2X) > 250 && Math.abs(bold.getX() - goalX) > 250){

				if (match.playExtraTime()){
					goToHalf(3);
				}
				else
					closeMatch();
			}
			else if (match.getHalf() == 3 && mins >= 105 + addedMins && secs > r.nextInt(59) && 
					Math.abs(bold.getX() - goal2X) > 250 && Math.abs(bold.getX() - goalX) > 250){

				goToHalf(4);
			}
			else if (match.getHalf() == 4 && mins >= 120 + addedMins && secs > r.nextInt(59) && 
					bold.getTeamAMaal() + match.firstMatchGoalsA == bold.getTeamBMaal() + match.firstMatchGoalsB &&
					Math.abs(bold.getX() - goal2X) > 250 && Math.abs(bold.getX() - goalX) > 250){

				startPenaltyShootout();
			}
			else if (match.getHalf() == 4 && mins >= 120 + addedMins && secs > r.nextInt(59) && 
					bold.getTeamAMaal() + match.firstMatchGoalsA != bold.getTeamBMaal() + match.firstMatchGoalsB &&
					Math.abs(bold.getX() - goal2X) > 250 && Math.abs(bold.getX() - goalX) > 250){

				closeMatch();
			}

		}
		else if (match.getState() == MatchState.HALF_TIME && System.currentTimeMillis() - outTime > 16000){

			goToHalf(2);
		}

		if (match.getState() == MatchState.BEFORE_MATCH){

			boolean ready = true;

			for (Player p : teamA.getPlayers())
				if (Hjaelper.Distance(p.getX(), p.getY(), p.getStartPosX(), p.getStartPosY()) > 10)
					ready = false;

			for (Player p : teamB.getPlayers())
				if (Hjaelper.Distance(p.getX(), p.getY(), p.getStartPosX(), p.getStartPosY()) > 10)
					ready = false;

			if (ready)
				match.setState(MatchState.KICK_OFF);
		}


		if (match.getState() == MatchState.FREE_KICK){
			Player p1 = null; //Bliver fremme
			Player p2 = null; //Mur1
			Player p3 = null; //mur2
			Player p4 = null; //mur3

			//find spillere der bliver fremme eller er i muren
			for (Player p : match.getSetPieceTeam().getPlayers()){
				if (p1 == null) p1 = p;
				else if (p2 == null) p2 = p;
				else if (p3 == null) p3 = p;
				else if (p4 == null) p4 = p;
				else{
					if (Math.abs(p.getStartPosX() - pitch.getPitchMidX()) < Math.abs(p1.getStartPosX()  - pitch.getPitchMidX())) p1 = p;
					else if (Math.abs(p.getStartPosX() - pitch.getPitchMidX()) < Math.abs(p2.getStartPosX()  - pitch.getPitchMidX())) p2 = p;
					else if (Math.abs(p.getStartPosX() - pitch.getPitchMidX()) < Math.abs(p3.getStartPosX()  - pitch.getPitchMidX())) p3 = p;
					else if (Math.abs(p.getStartPosX() - pitch.getPitchMidX()) < Math.abs(p4.getStartPosX()  - pitch.getPitchMidX())) p4 = p;
				}
			}

			List<Player> mur = new ArrayList<Player>();
			mur.add(p2);
			mur.add(p3);
			mur.add(p4);

			//TODO: setMarkeringerSetPiece
			//			match.getSetPieceTeam().getOppTeam().setMarkeringer(mur, true);

		}
		
		
		if (match.getState() == MatchState.GOAL){
			
			if (System.currentTimeMillis() - outTime > 3000){
				
				if (Hjaelper.Distance(bold.getX(), bold.getY(), match.getSetPieceX(), match.getSetPieceY()) < 5) 
					match.setState(MatchState.KICK_OFF);
				
				double ta = Math.atan2((match.getSetPieceX() - bold.getX()), 
						(match.getSetPieceY() - bold.getY()));
				bold.setA(ta);
				bold.setSpeed(Hjaelper.Distance(bold.getX(), bold.getY(), match.getSetPieceX(), match.getSetPieceY()) * 1.4);
			}
		}
		else if (match.getState() == MatchState.KICK_OFF){
			match.setSetPieceX(pitch.getPitchMidX());
			match.setSetPieceY(pitch.getPitchMidY());
			double ta = Math.atan2((match.getSetPieceX() - bold.getX()), (match.getSetPieceY() - bold.getY()));
			bold.setA(ta);
			bold.setSpeed(Hjaelper.Distance(bold.getX(), bold.getY(), match.getSetPieceX(), match.getSetPieceY()) * 1.4);
		}
		else if (match.getState() == MatchState.THROW_IN){
			
			if (bold.getPassTo() == null && System.currentTimeMillis() - bold.getSetPieceTime() > 2500){
				
				double ta = Math.atan2((match.getSetPieceX() - bold.getX()), 
						(match.getSetPieceY() - bold.getY()));
				bold.setA(ta);
				bold.setSpeed(Hjaelper.Distance(bold.getX(), bold.getY(), match.getSetPieceX(), match.getSetPieceY()) * 1.4);
			}
		}
		else if (match.getState() == MatchState.PENALTY || match.getState() == MatchState.GOAL_KICK){
			
			if (bold.getPassTo() == null && System.currentTimeMillis() - bold.getSetPieceTime() > 2500){
				
				double ta = Math.atan2((match.getSetPieceX() - bold.getX()), 
						(match.getSetPieceY() - bold.getY()));
				bold.setA(ta);
				bold.setSpeed(Hjaelper.Distance(bold.getX(), bold.getY(), match.getSetPieceX(), match.getSetPieceY()) * 1.4);
			}
		}
		else if (match.getState() == MatchState.CORNER){
			
			if (System.currentTimeMillis() - bold.getSetPieceTime() > 2500){
				
				double x = match.getSetPieceX();
				if (x < pitch.getPitchMidX()) x += 9;
				else x -= 9;
				
				double y = match.getSetPieceY();
				if (y < pitch.getPitchMidY()) y += 7;
				else y -= 7;
				
				if (Hjaelper.Distance(bold.getX(), bold.getY(), x, y) > 5){

					double ta = Math.atan2((x - bold.getX()), (y - bold.getY()));
					bold.setA(ta);
					bold.setSpeed(Hjaelper.Distance(bold.getX(), bold.getY(), x, y) * 1.2);
				}
			}
		}
		if (bold.isMaal()){

			if (match.getState() == MatchState.ON){

				Team scoringTeam = teamA;
				
				if (Math.abs(bold.getX() - teamA.getGoalX()) < Math.abs(bold.getX() - teamB.getGoalX())){
					match.setSetPieceTeam(teamA);
					scoringTeam = teamB;
					if (bold.getLastTouch() != null && bold.getLastTouch() != teamA.getKeeper()) teamA.getKeeper().playerMatchStats.saveAttempts++;
				}
				else{
					match.setSetPieceTeam(teamB);
					if (bold.getLastTouch() != null && bold.getLastTouch() != teamB.getKeeper()) teamB.getKeeper().playerMatchStats.saveAttempts++;
				}
				addReplayCode(-13);
				
				match.setSetPieceX(pitchPosX + pitchWidth / 2);
				match.setSetPieceY(pitchPosY + pitchHeight / 2);

				match.setState(MatchState.GOAL);
				
				try {
					DAOCake.executeSimpleStatement("UPDATE matches set hometeamgoals=" + bold.getTeamAMaal() + ", awayteamgoals=" + bold.getTeamBMaal() + " WHERE id=" + match.getMatchId());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				//skal checke om der er scoret i modstanderens mål
//				if(bold.getLastShot().myTeam.getGoalX() == bold.getTeamA().getGoalX() && 
//						Hjaelper.Distance(bold.x, bold.y, bold.teamA.getGoalX(), bold.teamA.getGoalY()) > 
//						Hjaelper.Distance(bold.x, bold.y, bold.teamB.getGoalX(), bold.teamB.getGoalY()) ||
//						bold.getLastShot().myTeam.getGoalX() == bold.getTeamB().getGoalX() && 
//						Hjaelper.Distance(bold.x, bold.y, bold.teamB.getGoalX(), bold.teamB.getGoalY()) > 
//						Hjaelper.Distance(bold.x, bold.y, bold.teamA.getGoalX(), bold.teamA.getGoalY()));
				
				int scorer = -1;
				MatchEventType type = DAOCake.MatchEventType.GOAL;
				
				if (bold.getLastShot() != null){
					bold.getLastShot().getPlayerMatchStats().goals++;
					bold.getLastShot().playerMatchStats.shotsOnTarget++;
					
					scorer = bold.getLastShot().id;
					
					if (bold.getLastLastTouch() != null && bold.getLastShot().myTeam.equals(bold.getLastLastTouch().myTeam))
						bold.getLastLastTouch().playerMatchStats.assists++;
				}
				else{
					if (bold.getLastTouch() != null){
						if (!bold.getLastTouch().getMyTeam().equals(scoringTeam))
							type = MatchEventType.OWNGOAL;
						
						scorer = bold.getLastTouch().id;
					}
				}
				
				DAOCake.createMatchEvent(match.getMatchId(), scoringTeam.id, mins, scorer, type);
				
				streamInject += "sc:" + bold.getTeamAMaal() + ":" + bold.getTeamBMaal() + ":";
				
				if (settings != null && settings.sysoutCategory(settingWriteFramesToWS)){
					wsServer.sendPacket("sc:" + bold.getTeamAMaal() + ":" + bold.getTeamBMaal() + ":", "fbf");
				}
				
				if (settings != null && settings.sysoutCategory(settingWriteFramesToDB)){
					try {
						DAOCake.executeSimpleStatement("DELETE FROM livematches WHERE id=0");
						String sql = "INSERT into livematches (id, match_Id, frame) values (0, " + matchId + ", '" + getLineupNames() + "')";
						DAOCake.executeSimpleStatement(sql);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				match.addCommentary("GOOOAAAAALLLLLL!!!");
				outTime = System.currentTimeMillis();
				bold.lastShot = null;
			}
			
		}
		else if (bold.isUdenfor()){	

			bold.setShot(false);
			bold.setPassTo(null);
			if (match.getState() == MatchState.ON){

				for (Player p : teamA.getPlayers())
					p.setOffsideBy(0);
				
				for (Player p : teamB.getPlayers())
					p.setOffsideBy(0);
				
				bold.setSetPieceTime(System.currentTimeMillis());
				
				outTime = System.currentTimeMillis();
				match.setSetPieceX((int)bold.getX());
				match.setSetPieceY((int)bold.getY());

				//Hvem skal have bolden
				if (bold.getLastTouch() != null){
					match.setSetPieceTeam(bold.getLastTouch().getOppTeam());
				}

				//Er det indkast, hjorne eller maalspark
				if (match.getState() == MatchState.ON && 
					match.getSetPieceX() > pitchPosX && match.getSetPieceX() < pitchPosX + pitchWidth){
					match.setState(MatchState.THROW_IN);
					bold.setSetpieceTaken(false);
					bold.setPassTo(null);
					match.getSetPieceTeam().getOppTeam().getOppsMarked().clear();
					
					DAOCake.addToMatchStat(matchId, match.getSetPieceTeam().id, "throwins");
//					b.setSetPieceTime(System.currentTimeMillis());
					
					if (match.getSetPieceY() > pitchPosY + pitchHeight / 2)
						match.setSetPieceY(match.getSetPieceY() + 5);
					else
						match.setSetPieceY(match.getSetPieceY() - 5);
				}
				else{
					//teamA har maal til venstre
					if (teamA.getGoalX() < pitchPosX + 30){

						//bolden er ude bag teamAs baglinie
						if (match.getSetPieceX() < pitchPosX){

							//teamA har selv rort den sidst
							if (bold.getLastTouch().getMyTeam() == teamA){

								DAOCake.addToMatchStat(matchId, teamB.id, "corners");
								match.setState(MatchState.CORNER);
								bold.setHasBall(null);
								//hvilket hjorne?
								if (match.getSetPieceY() < pitchPosY + pitchHeight / 2){
									match.setSetPieceY(pitchPosY);
								}
								else{
									match.setSetPieceY(pitchPosY + pitchHeight);
								}
							}
							else{ //teamB har rort den sidst
								match.setState(MatchState.GOAL_KICK);

								if (match.getSetPieceY() < pitchPosY + (pitchHeight / 2)){
									match.setSetPieceY((pitchHeight - 146) / 2 + pitchPosY + 10);
								}
								else{
									match.setSetPieceY((pitchHeight - 146) / 2 + pitchPosY + 120);
								}
								match.setSetPieceX(pitchPosX + 44);

							}
						}
						else if (match.getSetPieceX() > pitchPosX + pitchWidth){ //ude bag teamBs baglinie

							//teamB har selv rort den sidst
							if (bold.getLastTouch().getMyTeam() == teamB){

								DAOCake.addToMatchStat(matchId, teamA.id, "corners");
								match.setState(MatchState.CORNER);
								bold.setHasBall(null);
								//hvilket hjorne?
								if (match.getSetPieceY() < pitchPosY + pitchHeight / 2){
									match.setSetPieceY(pitchPosY);
								}
								else{
									match.setSetPieceY(pitchPosY + pitchHeight);
								}
							}
							else{ //teamA har rort den sidst
								match.setState(MatchState.GOAL_KICK);

								if (match.getSetPieceY() < pitchPosY + (pitchHeight / 2)){
									match.setSetPieceY((pitchHeight - 146) / 2 + pitchPosY + 10);
								}
								else{
									match.setSetPieceY((pitchHeight - 146) / 2 + pitchPosY + 120);
								}
								match.setSetPieceX(pitchPosX + pitchWidth - 44);
							}
						}
					}

					//teamB har maal til venstre
					else{

						//bolden er ude bag teamBs baglinie
						if (match.getSetPieceX() < pitchPosX){

							//teamB har selv rort den sidst
							if (bold.getLastTouch().getMyTeam() == teamB){

								DAOCake.addToMatchStat(matchId, teamA.id, "corners");
								match.setState(MatchState.CORNER);
								bold.setHasBall(null);
								//hvilket hjorne?
								if (match.getSetPieceY() < pitchPosY + pitchHeight / 2){
									match.setSetPieceY(pitchPosY);
								}
								else{
									match.setSetPieceY(pitchPosY + pitchHeight);
								}
							}
							else{ //teamA har rort den sidst
								match.setState(MatchState.GOAL_KICK);

								if (match.getSetPieceY() < pitchPosY + pitchHeight / 2){
									match.setSetPieceY((pitchHeight - 146) / 2 + pitchPosY + 10);
								}
								else{
									match.setSetPieceY((pitchHeight - 146) / 2 + pitchPosY + 30);
								}
								match.setSetPieceX(pitchPosX + 44);

							}
						}
						else if (match.getSetPieceX() > pitchPosX + pitchWidth){ //ude bag teamAs baglinie

							//teamA har selv rort den sidst
							if (bold.getLastTouch().getMyTeam() == teamA){

								DAOCake.addToMatchStat(matchId, teamB.id, "corners");
								match.setState(MatchState.CORNER);
								bold.setHasBall(null);
								//hvilket hjorne?
								if (match.getSetPieceY() < pitchPosY + pitchHeight / 2){
									match.setSetPieceY(pitchPosY);
								}
								else{
									match.setSetPieceY(pitchPosY + pitchHeight);
								}
							}
							else{ //teamB har rort den sidst
								match.setState(MatchState.GOAL_KICK);

								if (match.getSetPieceY() < pitchPosY + (pitchHeight / 2)){
									match.setSetPieceY((pitchHeight - 146) / 2 + pitchPosY + 10);
								}
								else{
									match.setSetPieceY((pitchHeight - 146) / 2 + pitchPosY + 120);
								}
								match.setSetPieceX(pitchPosX + pitchWidth - 44);
							}
						}
					}
				}				
			}

		}
		else{ //bold er ikke ude

			if (match.getState() == MatchState.THROW_IN && bold.isSetpieceTaken())
				match.setState(MatchState.ON);
			if (match.getState() == MatchState.FREE_KICK){
				bold.setX(match.getSetPieceX());
				bold.setY(match.getSetPieceY());
			}
		}	
		
		
	}//HandleMatchstates() slut
	
	private void handleSubs(){
		//få udskiftede spillere ud af banen
		waitForSubs = false;
		for (Player p : teamA.getUsedSubs()){

			if (p.getY() > pitch.getPitchPosY()){
				p.setTargetA(Math.atan2((pitch.getPitchMidX() - p.getX()), (pitch.getPitchPosY() - p.getY())));
				p.targetX = pitch.getPitchMidX();
				p.targetY = pitch.getPitchPosY()-10;
				waitForSubs = true;
			}
			else{
				p.setTargetA(Math.atan2((pitch.getPitchMidX() - p.getX()), (-20 - p.getY())));
				p.targetX = pitch.getPitchMidX();
				p.targetY = -20;	
			}
			//Tving spilleren ud af banen uden at tænke på hvordan
			p.runBlind();
		}
		for (Player p : teamB.getUsedSubs()){

			if (p.getY() > pitch.getPitchPosY()){
				p.setTargetA(Math.atan2((pitch.getPitchMidX() - p.getX()), (pitch.getPitchPosY() - p.getY())));
				p.targetX = pitch.getPitchMidX();
				p.targetY = pitch.getPitchPosY()-10;	
				waitForSubs = true;
			}
			else{
				p.setTargetA(Math.atan2((pitch.getPitchMidX() - p.getX()), (-20 - p.getY())));
				p.targetX = pitch.getPitchMidX();
				p.targetY = -20;	
			}
			//Tving spilleren ud af banen uden at tænke på hvordan
			p.runBlind();
			
		}
	}
	
	private void handleLiveStream(){
		
		//Livematch
		String stream = getStream();
		
		if (stream.length() > 0 && settings != null && settings.sysoutCategory(settingWriteFramesToWS)){
			wsServer.sendPacket(stream, "fbf");
		}
		
		liveFrames.add("(" + liveMatchFrame + ", " + matchId + ", '" + stream + "'), ");
		
		//Move to handleReplay
		dataBufferStr.add(stream);
		
		liveMatchFrame++;
		
		if (liveFrames.size() > 30){
			if (settings != null && settings.sysoutCategory(settingWriteFramesToDB)){
				String sql = "INSERT into livematches (id, match_id, frame) values ";
				for (String s : liveFrames) {
					sql += s;
				}
				sql = sql.substring(0, sql.lastIndexOf(","));
				sql += "; ";
				try {
					DAOCake.executeSimpleStatement(sql);
					sql = "DELETE FROM livematches WHERE id > 0 AND id < " + (liveMatchFrame - 15000) + ";";
					DAOCake.executeSimpleStatement(sql);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			liveFrames.clear();
		}
		
	}
	
	private void handleReplay(){
		
		if (dataBufferStr.size() > 500){
			
			FileOutputStream fos;
			String path = settings.replayDir.replace("[currdir]", System.getProperty("user.dir")) + leagueId;
			try {
				File f = new File(path);
				if (!f.exists())
					f.mkdir();

				path += "/" + matchId + ".txt";
				
				fos = new FileOutputStream(path, true);
				PrintWriter pw = new PrintWriter(fos);
				
				for (int i = 0; i < dataBufferStr.size(); i++){
//					System.out.println(dataBuffer.get(i));
					pw.println(dataBufferStr.get(i));
				}
				pw.flush();
				pw.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			dataBufferStr.clear();
		}	
		
	}

	private void handleTimeAndStats(){
		
		if (match.isHalfRunning() && match.getState() != MatchState.BEFORE_MATCH && match.getState() != MatchState.KICK_OFF && match.getState() != MatchState.PENALTYSHOOTOUT){
			if (!timeRunning){
				timeRunning = true;
			}
			
			if (match.getState() == MatchState.ON){
				if (bold.getLastTouch().getMyTeam() == teamA)
					possessionA++;

				totalPossession++;
			}
			
			ticks++;
			if (ticks > 33 / matchSpeed){
				secs++;
				ticks = 0;
			}

			if (secs > 59){
				secs -= 60;
				mins++;
				
				DAOCake.updateMatchStat(match.getMatchId(), teamA.getId(), "possession", (int)((possessionA / totalPossession) * 100));
				DAOCake.updateMatchStat(match.getMatchId(), teamB.getId(), "possession", 100 - (int)((possessionA / totalPossession) * 100));
				
				for (Player p : teamA.getPlayers()){
					p.updateEnergyAndMorale();
					p.playerMatchStats.increaseMinutesPlayed();
					p.playerMatchStats.writeAllToDB(match.getMatchId(), p.id, true);
				}
				
				for (Player p : teamB.getPlayers()){
					p.updateEnergyAndMorale();
					p.playerMatchStats.increaseMinutesPlayed();
					p.playerMatchStats.writeAllToDB(match.getMatchId(), p.id, true);
				}
				
				DAOCake.writeEnergyToDB(teamA.getPlayers());
				DAOCake.writeEnergyToDB(teamB.getPlayers());
			}
		}
		else{
			if (timeRunning){
				timeRunning = false;
			}
		}
	}
	
	private void closeMatch(){
		match.setState(MatchState.FINISHED);
		match.setHalfRunning(false);
		
		DAOCake.closeMatch(match, bold.getTeamAMaal(), bold.getTeamBMaal(), teamA, teamB);
		for (Player p : teamA.getPlayers()){
			p.playerMatchStats.print();
			p.playerMatchStats.getMatchRatingNew(true, match.getMatchrep());
			if (match.getMatchrep() > 0 && (p.playerMatchStats.goals > 0 || p.playerMatchStats.assists > 0)){
				int[] bonus = DAOCake.getBonus(p.id);
				
				try {
					if (bonus[0] > 0 && p.playerMatchStats.goals > 0){//goalbonus
						DAOCake.executeSimpleStatement("INSERT INTO club_expenses (amount, type, date, description, club_id, ext_id) VALUES (" + bonus[0] + ", 7, now(), 'Goal bonus paid to " + p.lastname + "', " + teamA.id + ", " + matchId + ")");
						DAOCake.executeSimpleStatement("UPDATE persons set money=money + " + bonus[0] + " WHERE id=" + p.id);
					}
					if (bonus[1] > 0 && p.playerMatchStats.assists > 0){//assistbonus
						DAOCake.executeSimpleStatement("INSERT INTO club_expenses (amount, type, date, description, club_id, ext_id) VALUES (" + bonus[1] + ", 7, now(), 'Assist bonus paid to " + p.lastname + "', " + teamA.id + ", " + matchId + ")");
						DAOCake.executeSimpleStatement("UPDATE persons set money=money + " + bonus[1] + " WHERE id=" + p.id);	
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(match.getMatchrep() < 1){
				try{
					DAOCake.executeSimpleStatement("UPDATE persons SET energy = " + p.start_Energy + " WHERE id=" + p.getId() + ";");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		for (Player p : teamA.getUsedSubs()){
			p.playerMatchStats.getMatchRatingNew(true, match.getMatchrep());
			if(match.getMatchrep() < 1){
				try{
					DAOCake.executeSimpleStatement("UPDATE persons SET energy = " + p.start_Energy + " WHERE id=" + p.getId() + ";");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		for (Player p : teamB.getPlayers()){
			p.playerMatchStats.print();
			p.playerMatchStats.getMatchRatingNew(true, match.getMatchrep());
			if (match.getMatchrep() > 0 && (p.playerMatchStats.goals > 0 || p.playerMatchStats.assists > 0)){
				int[] bonus = DAOCake.getBonus(p.id);
				
				try {
					if (bonus[0] > 0 && p.playerMatchStats.goals > 0){//goalbonus
						DAOCake.executeSimpleStatement("INSERT INTO club_expenses (amount, type, date, description, club_id, ext_id) VALUES (" + bonus[0] + ", 7, now(), 'Goal bonus paid to " + p.lastname + "', " + teamB.id + ", " + matchId + ")");
						DAOCake.executeSimpleStatement("UPDATE persons set money=money + " + bonus[0] + " WHERE id=" + p.id);
					}
					if (bonus[1] > 0 && p.playerMatchStats.assists > 0){//assistbonus
						DAOCake.executeSimpleStatement("INSERT INTO club_expenses (amount, type, date, description, club_id, ext_id) VALUES (" + bonus[1] + ", 7, now(), 'Assist bonus paid to " + p.lastname + "', " + teamB.id + ", " + matchId + ")");
						DAOCake.executeSimpleStatement("UPDATE persons set money=money + " + bonus[1] + " WHERE id=" + p.id);	
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(match.getMatchrep() < 1){
				try{
					DAOCake.executeSimpleStatement("UPDATE persons SET energy = " + p.start_Energy + " WHERE id=" + p.getId() + ";");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		for (Player p : teamB.getUsedSubs()){
			p.playerMatchStats.getMatchRatingNew(true, match.getMatchrep());
			if(match.getMatchrep() < 1){
				try{
					DAOCake.executeSimpleStatement("UPDATE persons SET energy = " + p.start_Energy + " WHERE id=" + p.getId() + ";");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private int calcAttendance(Team homeT, Team awayT){
		int result = 0;
		
		if (match.matchrep > 0){

			//Get number of seats / stands and prices
			int info[] = DAOCake.getStadiumAndTicketInfo(homeT.getId());
			int seats = info[2];
			int terraces = info[3];
			int seatPrice = info[0];
			int standPrice = info[1];

			//Calculate average ticket price for the league
			int avgTerrPrice = 3 * homeT.getLeagueReputation();
			int avgSeatPrice = 5 * homeT.getLeagueReputation();
			
			//Calculate the percentage of fans who are willing to pay the set price
			int fansStandPerc = avgTerrPrice * 100 / standPrice;
			int fansSeatPerc = avgSeatPrice * 100 / seatPrice;
			
			fansStandPerc = (int)(fansStandPerc * 0.01 * fansStandPerc);
			fansSeatPerc = (int)(fansSeatPerc * 0.01 * fansSeatPerc);
			
			//////Away fans
			
			//Calculate max number of places for awayfans and max number of interested awayfans
			int maxAwayFans = (terraces + seats) / 5;
			
			///////////////////
			//We should add Math.pow(leagereputation, 2) * 400 to avgMatchFame
			//avgMatchFame is only used for the number of interested fans and it needs to be bigger just like the rep of the league should have
			//an influence on this.
			//////////////////
			int avgMatchFame = (awayT.getFame() + homeT.getFame()) / 2;
			int awayFans = avgMatchFame * 2 / 60; 
			
			//Calculate the amount of awayfans who want seats
			int seatedAwayFans = (int)(awayFans * 0.4);
			seatedAwayFans = seatedAwayFans * fansSeatPerc / 100;
			seatedAwayFans *= ((10.0 + r.nextFloat()) / 10.0);
			
			if (seatedAwayFans > seats)
				seatedAwayFans = seats;
			
			//Those who want to stand + half of those who couldn't get a seat will stand if there is enough tickets
			int standingAwayFans = (int)(awayFans * 0.6);
			if ((int)(awayFans * 0.4) - seatedAwayFans > 0) {
				standingAwayFans += ((int)(awayFans * 0.4) - seatedAwayFans) / 2;
			}
			
			//How many of those left to stand will pay for it?
			standingAwayFans = standingAwayFans * fansStandPerc / 100;
			
			if (standingAwayFans > terraces * 2)
				standingAwayFans = terraces * 2;
			
			if (seatedAwayFans + standingAwayFans > maxAwayFans)
				standingAwayFans = maxAwayFans - seatedAwayFans;
			
			standingAwayFans *= ((10.0 + r.nextFloat()) / 10.0);
			
			//If there isn't enough stands for the awayfans and there are seats left half of those who couldn't get seats will take stands
			int fansWithoutStands = 0;
			if (standingAwayFans > terraces) {
				fansWithoutStands = (standingAwayFans - terraces) / 2;
				standingAwayFans = terraces;
			}
			
			if (fansWithoutStands > 0 && seats - seatedAwayFans > 0){
				//The price of a seat might still scare some away
				fansWithoutStands = fansWithoutStands * fansSeatPerc / 100;
				fansWithoutStands *= ((10.0 + r.nextFloat()) / 10.0);
				
				if (fansWithoutStands > seats - seatedAwayFans)
					fansWithoutStands = seats - seatedAwayFans;
				
				seatedAwayFans = seatedAwayFans + fansWithoutStands;
			}
			
			seats = seats - seatedAwayFans;
			terraces = terraces - standingAwayFans;
			
			////////Home fans
	
			//Calculate max number of places for home fans and max number of interested home fans
			int maxHomeFans = seats + terraces;
			int homeFans = avgMatchFame / 4;
			
			//Calculate the amount of home fans who want seats
			int seatedHomeFans = (int)(homeFans * 0.4);
			seatedHomeFans = seatedHomeFans * fansSeatPerc / 100;
			seatedHomeFans *= ((10.0 + r.nextFloat()) / 10.0);
			
			if (seatedHomeFans > seats)
				seatedHomeFans = seats;
			
			//Those who want to sit + half of those who couldn't get a seat will stand if there is enough tickets
			int standingHomeFans = (int)(homeFans * 0.6);
			if ((int)(homeFans * 0.4) - seatedHomeFans > 0) {
				standingHomeFans += ((int)(homeFans * 0.4) - seatedHomeFans) / 2;
			}
			
			//How many of those left to stand will pay for it?
			standingHomeFans = standingHomeFans * fansStandPerc / 100;
			
			if (standingHomeFans > terraces * 2)
				standingHomeFans = terraces * 2;
			
			if (seatedHomeFans + standingHomeFans > maxHomeFans)
				standingHomeFans = maxHomeFans - seatedHomeFans;
			
			standingHomeFans *= ((10.0 + r.nextFloat()) / 10.0);
			
			//If there isn't enough stands for the home fans and there are seats left half of those who couldn't get seats will take stands
			fansWithoutStands = 0;
			if (standingHomeFans > terraces) {
				fansWithoutStands = (standingHomeFans - terraces) / 2;
				standingHomeFans = terraces;
			}
			
			if (fansWithoutStands > 0 && seats - seatedHomeFans > 0){
				//The price of a seat might still scare some away
				fansWithoutStands = fansWithoutStands * fansSeatPerc / 100;
				fansWithoutStands *= ((10.0 + r.nextFloat()) / 10.0);
				
				if (fansWithoutStands > seats - seatedHomeFans)
					fansWithoutStands = seats - seatedHomeFans;
				
				seatedHomeFans = seatedHomeFans + fansWithoutStands;
			}
			
			int usedSeats = seatedAwayFans + seatedHomeFans;
			int usedTerraces = standingAwayFans + standingHomeFans;
			
			result = usedSeats + usedTerraces;
			
			int income = (usedTerraces * standPrice) + (usedSeats * seatPrice) + (result * 3);
			try {
				//If it's a cup match in a one legged round split the income between the two clubs
				if (match.isCup() && !match.isTwo_legs()){
					income = income / 2;
					DAOCake.executeSimpleStatement("INSERT INTO club_incomes (club_id, amount, type, date, description, ext_id) VALUES (" + awayT.id + ", " + income + ", 1, now(), 'Match day income', " + matchId + ");");
				}
				DAOCake.executeSimpleStatement("INSERT INTO club_incomes (club_id, amount, type, date, description, ext_id) VALUES (" + homeT.id + ", " + income + ", 1, now(), 'Match day income', " + matchId + ");");
				//DAOCake.executeSimpleStatement("UPDATE clubs SET money = money + " + income + " where id = " + homeT.id + ";");
				DAOCake.executeSimpleStatement("UPDATE matches SET attendance = " + result + ", seats=" + usedSeats + " where id = " + matchId + ";");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		match.attendance = result;
		
		return result;
	}
	
	public void init(int matchId) {
		
		match = DAOCake.loadMatch(matchId, this);
		bold = new Bold(pitchHeight,pitchPosX, pitchPosY, pitchWidth, pitch, match);
		
//		dataBuffer.add((int)matchSpeed);
		System.out.println("Init match: " + match.getMatchId());
		DAOCake.setMatchStatus(match.getMatchId(), 1);
		try {
			DAOCake.findWinner(match);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			settings = Settings.getInstance();
			
			debug += "Settings done";
			
			if (settings != null && settings.sysoutCategory(settingWriteFramesToWS)){
				System.out.println("Starting websocket server thread...");
				wsServer = new WebsocketServerThread(this);
				new Thread(wsServer, "footieWSS").start();
				System.out.println("Websocket server thread started.");
			}
			
			DAOCake.openConnection(settings.sqlConn);
			DAOCake.executeSimpleStatement("DELETE FROM match_teamtactics;");
			DAOCake.executeSimpleStatement("DELETE FROM match_playertactics;");
			
			if (settings != null && settings.sysoutCategory(settingWriteFramesToDB)){
				DAOCake.executeSimpleStatement("DELETE FROM livematches;");
			}
			
			int teams[] = DAOCake.getTeams(matchId);
			teamA = ClubsDAO.loadTeam(teams[0], null, true, true);
			teamB = ClubsDAO.loadTeam(teams[1], null, true, true);
			teamA.matchEngine = this;
			teamB.matchEngine = this;
			
			DAOCake.createMatchStats(matchId, teams[0]);
			DAOCake.createMatchStats(matchId, teams[1]);
			
			teamA.setOppTeam(teamB);
			teamB.setOppTeam(teamA);
			
			debug += "Teams loaded";

			//Compare the colors of team kits and choose a good combination
			int kits = Hjaelper.findKits(teamA.getColor(), teamA.getColor2(), teamB.getColor(), teamB.getColor2());
			Hjaelper.switchKits(kits, teamA, teamB);
			
			//Setup tactics for each team
//			teamA.setTeamTactic(TeamTactic.T541); //Should get a team tactic set on the club, if the clubs hasn't got an owner
//			teamB.setTeamTactic(TeamTactic.T541); //Should get a team tactic set on the club, if the clubs hasn't got an owner
			SetupPositions setPos = new SetupPositions();
//			setPos.calculatePlayerPositionsScoresForTeam(teamA);
//			setPos.calculatePlayerPositionsScoresForTeam(teamB);
			
			//Prepare the players of each team
			System.out.println("Before Prep");
			setPos.preparePlayers(true, teamA, teamB, match, settings, bold, pitch, pitchPosX, pitchPosY, matchId, pitchWidth, pitchHeight);
			setPos.preparePlayers(false, teamB, teamA, match, settings, bold, pitch, pitchPosX, pitchPosY, matchId, pitchWidth, pitchHeight);

			teamA.setAvgPlayerRating();
			teamB.setAvgPlayerRating();
			
			//Initialise replay file
			dataBufferStr.add("ms." + (int)matchSpeed);			
			
			dataBufferStr.add(getLineupNames());
			
			String sql = "";
			
			//The init frame is still inserted to db as the live page still needs it for team tactics.
//			if (settings != null && settings.sysoutCategory(settingWriteFramesToDB)){
				DAOCake.executeSimpleStatement("DELETE FROM livematches");

				sql = "INSERT into livematches (id, match_id, frame) values (" + liveMatchFrame + ", " + matchId + ", '" + getLineupNames() + "')";
				System.out.println(sql);
				DAOCake.executeSimpleStatement(sql);
//			}
				
			System.out.println(teamA.getName() + " : " + teamA.getPlayers().size());
			System.out.println(teamB.getName() + " : " + teamB.getPlayers().size());
			System.out.println("CHECK PLAYERS");
			for(Player p : teamA.getPlayers()){
				System.out.println(p.getFirstname() + " " + p.getLastname() + ", " + p.getId() + "{" + p.getShirtNumber()+ "}, pos(" + p.getPosX() + "," + p.getPosY() + "), startPos(" + p.getStartPosX() + "," + p.getStartPosY() + ")");
			}
			for(Player p : teamA.getSubs()){
				System.out.println(p.getFirstname() + " " + p.getLastname() + ", " + p.getId() + "{" + p.getShirtNumber()+ "}, pos(" + p.getPosX() + "," + p.getPosY() + "), startPos(" + p.getStartPosX() + "," + p.getStartPosY() + ") SUBS");
			}
			System.out.println("---------------");
			for(Player p : teamB.getPlayers()){
				System.out.println(p.getFirstname() + " " + p.getLastname() + ", " + p.getId() + "{" + p.getShirtNumber()+ "}, pos(" + p.getPosX() + "," + p.getPosY() + "), startPos(" + p.getStartPosX() + "," + p.getStartPosY() + ")");
			}
			for(Player p : teamB.getSubs()){
				System.out.println(p.getFirstname() + " " + p.getLastname() + ", " + p.getId() + "{" + p.getShirtNumber()+ "}, pos(" + p.getPosX() + "," + p.getPosY() + "), startPos(" + p.getStartPosX() + "," + p.getStartPosY() + ") SUBS");
			}
			
			DAOCake.setLineup(teamA, matchId);
			DAOCake.setLineup(teamB, matchId);

			liveMatchFrame++;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//hvos man vil kontrollere en spiller
		//controlledPlayer = teamA.getPlayers().get(7);
		
		debug += "Players loaded";
		
		if (teamA.players.size() < 8 && teamB.players.size() < 8){
			System.out.println("Neither team has enough players. It's a 0-0 draw and no game is played.");
			match.setMatchrep(0);
			DAOCake.closeMatch(match, 0, 0, teamA, teamB);
			System.exit(0);
		}
		else if (teamA.players.size() < 8){
			System.out.println(teamA.name + " do not have enough players. They lose 3-0 and no game is played.");
			match.setMatchrep(0);
			DAOCake.closeMatch(match, 0, 3, teamA, teamB);
			System.exit(0);
		}
		else if (teamB.players.size() < 8){
			System.out.println(teamB.name + " do not have enough players. They lose 3-0 and no game is played.");
			match.setMatchrep(0);
			DAOCake.closeMatch(match, 3, 0, teamA, teamB);
			System.exit(0);
		}
		
		calcAttendance(teamA, teamB);
		
		bold.setTeamA(teamA);
		bold.setTeamB(teamB);
		
		//opdater sorterede lister af spillerne
		teamA.sortByHeight();
		teamB.sortByHeight();
		teamA.sortFrontToBack();
		teamB.sortFrontToBack();
				
		for (Player p : teamA.getPlayers())
			p.updateEnergyAndMorale();
		for (Player p : teamB.getPlayers())
			p.updateEnergyAndMorale();
		
		if (match.getState() == MatchState.BEFORE_MATCH){
			int tempY = -200;
			for (Player p : teamA.getPlayers()){
				p.setX(pitch.getPitchPosX() - 20 + pitch.getPitchWidth() / 2);
				p.setY(tempY);
				tempY += 20;
			}
			tempY = -200;
			for (Player p : teamB.getPlayers()){
				p.setX(pitch.getPitchPosX() + 20 + pitch.getPitchWidth() / 2);
				p.setY(tempY);
				tempY += 20;
			}
		}
		
		if (controlledPlayer != null){
			controlledPlayer.setX(controlledPlayer.getStartPosX());
			controlledPlayer.setY(controlledPlayer.getStartPosY());
			
		}
			
		bold.setX(pitchPosX + (pitchWidth / 2));
		bold.setY(pitchPosY + (pitchHeight / 2));

		goalSize = 59;
		goalX = pitchPosX + pitchWidth;
		goalY = (pitchHeight - goalSize) / 2 + pitchPosY;
		goal2Size = 59;
		goal2X = pitchPosX;
		goal2Y = (pitchHeight - goalSize) / 2 + pitchPosY;
		teamA.setGoalX(goal2X);
		teamA.setGoalY(goal2Y);
		teamB.setGoalX(goalX);
		teamB.setGoalY(goalY);

		teamA.setFrontAndBackPlayers();
		teamB.setFrontAndBackPlayers();

		calculateBacklines();
		
		//teamA har kick off
		match.setSetPieceTeam(teamA);

		loop.scheduleAtFixedRate(new realTime(), 0, 30);
		
		bold.setLastTouch(teamA.getPlayers().get(7));
		bold.setPassTo(teamA.getPlayers().get(8));
		loadFinished = true;
		debug += " - Init done";
	}

	/**
	 * Calculates and sets the backlines of each team
	 */
	public void calculateBacklines(){
		double teamABackline = (int)(1.2 * Math.abs(teamA.getGoalX() - teamA.backPlayer.getStartPosX()));
		teamA.setBackLine((int)teamABackline);		
		double teamBBackline = (1.2 * Math.abs(teamB.getGoalX() - teamB.backPlayer.getStartPosX()));
		teamB.setBackLine((int)teamBBackline);
		System.out.println(teamA.name + " defensive line: " + teamA.getBackLine());
		System.out.println(teamB.name + " defensive line: " + teamB.getBackLine());
	}
	
	public void addReplayCode(int code){
		dataBufferStr.add("rc." + code);
	}
	
	class realTime extends TimerTask{
		public void run(){
			
			try{
			if (!pause && loadFinished){

				update();
				
			}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public Team getTeamA() {
		return teamA;
	}

	public void setTeamA(Team teamA) {
		this.teamA = teamA;
	}

	public Team getTeamB() {
		return teamB;
	}

	public void setTeamB(Team teamB) {
		this.teamB = teamB;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public int getMins() {
		return mins;
	}

	public void setMins(int mins) {
		this.mins = mins;
	}

	public int getSecs() {
		return secs;
	}

	public void setSecs(int secs) {
		this.secs = secs;
	}

	public int getAddedMins() {
		return addedMins;
	}

	public void setAddedMins(int addedMins) {
		this.addedMins = addedMins;
	}

	public Pitch getPitch() {
		return pitch;
	}

	public void setPitch(Pitch pitch) {
		this.pitch = pitch;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
		bold.setSettings(settings);
		for (Player p : teamA.getPlayers())
			p.setSettings(settings);
		
		for (Player p : teamB.getPlayers())
			p.setSettings(settings);
	}

	public Bold getBold() {
		return bold;
	}

	public void setBold(Bold bold) {
		this.bold = bold;
	}
	
	
}

