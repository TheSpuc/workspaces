package model;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import data.teamSetup.TeamTactic;
import model.Match.MatchState;


public class Team implements Cloneable{

	ClubRecords records;
	String name;
	public int leagueReputation, leagueId, fame;
	int id, userId;


	ArrayList<Player> players = new ArrayList<Player>();
	ArrayList<Player> subs = new ArrayList<Player>();
	ArrayList<Player> usedSubs = new ArrayList<Player>();
	int shootoutPenalty = 1;
	Color color;
	Color color2;
	int goalX;
	int goalY;
	TeamState teamState;
	int backLine;
	public int buildUpPatience = 30000; //How long the team will pass the ball around before starting to prefer passes going forward
	Team oppTeam;
	ArrayList<Player> oppsMarked = new ArrayList<Player>();
	ArrayList<Player> playersByHeight = new ArrayList<Player>();
	ArrayList<Player> playersFrontToBack = new ArrayList<Player>();
	ArrayList<Player> penaltyTakers = new ArrayList<Player>();
	double offsideDistToGoal;
	MatchEngine matchEngine;
	Player frontPlayer, backPlayer;
	Player pThrowinRight, pThrowinLeft, pPenalty, pFreekickShort, pFreekickLong, pCornerRight, pCornerLeft, pCaptain, pTargetMan;
	long teamStateTimer = 0;
	public int subNo = 0;
	
	TeamTactic teamTactic;

	ArrayList<Point> throughLine = new ArrayList<Point>();
	
	int avgPlayerRating = 50;
	
	int statShots;
	Bold bold;
	
	public int ownerId;
	
	public Team(){
		
	}
	
	/*
	 * Sets and returns the average of all player ratings in the starting lineup
	 * @Return The average of all player ratings
	 */
	public int setAvgPlayerRating(){
		if (players.size() > 0){
			int sum = 0;
			for (Player p : players){
				sum += p.getBestPositionScore();
			}
			avgPlayerRating = sum / players.size();
		}
		return avgPlayerRating;
	}
	
	public int getAvgPlayerRating(){
		return avgPlayerRating;
	}
	
	//Find linien for hvornår en angriber er igennem dette holds forsvar
	public void calcThroughLine(Pitch pitch){

		ArrayList<Point> result = new ArrayList<Point>();
		ArrayList<Player> players = new ArrayList<Player>();

		Team defense = this;
		
		for (Player p : defense.getPlayers())
			if (!p.isKeeper())
				players.add(p);

		if (players.size() > 0){


			Collections.sort(players, new Comparator<Player>(){
				public int compare(Player p1, Player p2) {
					return (int)(Math.abs(p1.getX() - p1.getMyTeam().getGoalX())) - 
							(int)(Math.abs(p2.getX() - p2.getMyTeam().getGoalX()));
				}});

			//Find den bagerste forsvarer
			Player last = players.get(0);

			//Find hans skæringspunkter med sidelinierne i en vinkel
			Point p1 = Hjaelper.intersection((int)last.getX(), (int)last.getY(), defense.getOppTeam().getGoalX(), (int)last.getY() - (int)Math.abs(defense.getOppTeam().getGoalX() - last.getX()),
					0, pitch.getPitchPosY(), 1000, pitch.getPitchPosY());
			Point p2 = Hjaelper.intersection((int)last.getX(), (int)last.getY(), defense.getOppTeam().getGoalX(), (int)last.getY() + (int)Math.abs(defense.getOppTeam().getGoalX() - last.getX()),
					0, pitch.getPitchPosY() + pitch.getPitchHeight(), 1000, pitch.getPitchPosY() + pitch.getPitchHeight());

			//tilføj ham selv og skæringspunkterne til resultatet
			result.add(p2);
			result.add(new Point((int)last.getX(), (int)last.getY()));
			result.add(p1);

			//for hver forsvarer som ikke er keeper eller den bagerste
			for (Player p : players)

				if (!p.isKeeper() && p != last){
					//Tjek om forsvareren er længere fremme end linien eller længere tilbage
					for (int i = 0; i < result.size() -1; i++){

						//Vi tjekker kun på det liniestykke der er ud for forsvareren
						if (result.get(i).y > p.getY() && result.get(i+1).y < p.getY()){
							double lineDir = Math.PI * 2 + Math.atan2(result.get(i+1).x - result.get(i).getX(), result.get(i+1).y - result.get(i).y);
							double lineX = result.get(i).x + (Math.sin(lineDir) * (Math.abs(p.getY() - result.get(i).y) / Math.abs(Math.cos(lineDir))));

							//hvis han er længere tilbage && lidt fra linien
							if (Math.abs(lineX - defense.getGoalX()) > Math.abs(p.getX() - defense.getGoalX())){

								//indsæt den nye forsvarer og hans skæringspunkter til den eksisterende forsvarers linier
								double ned = Math.atan2(defense.getOppTeam().getGoalX() - p.getX(), p.getY() + Math.abs(defense.getOppTeam().getGoalX() - p.getX()) - p.getY());
								double op = Math.atan2(defense.getOppTeam().getGoalX() - p.getX(), p.getY() - Math.abs(defense.getOppTeam().getGoalX() - p.getX()) - p.getY());

								ned = Math.round(ned * 100d) / 100d;
								op = Math.round(op * 100d) / 100d;

								if (i == 0){
									//hvis det er første linie fjernes punktet hvor den skærer den nederste sidelinie før det nye indsættes
									Point po1 = Hjaelper.intersection((int)p.getX(), (int)p.getY(), (int)(p.getX() + Math.sin(op) * 1000), (int)(p.getY() + Math.cos(op) * 1000),
											result.get(i).x, result.get(i).y, result.get(i+1).x, result.get(i+1).y);
									Point po2 = Hjaelper.intersection((int)p.getX(), (int)p.getY(), (int)(p.getX() + Math.sin(ned) * 1000), (int)(p.getY() + Math.cos(ned) * 1000),
											0, pitch.getPitchPosY() + pitch.getPitchHeight(), 1000, pitch.getPitchPosY() + pitch.getPitchHeight());

									result.remove(0);
									result.add(0, po2);
									result.add(1, new Point((int)p.getX(), (int)p.getY()));
									result.add(2, po1);
								}else if(i == result.size() - 2){
									//Hvis det er sidste linie fjernes punktet hvor den skærer den øverste sidelinie før det nye indsættes

									Point po1 = Hjaelper.intersection((int)p.getX(), (int)p.getY(), (int)(p.getX() + Math.sin(op) * 1000), (int)(p.getY() + Math.cos(op) * 1000),
											0, pitch.getPitchPosY(), 1000, pitch.getPitchPosY());
									Point po2 = Hjaelper.intersection((int)p.getX(), (int)p.getY(), (int)(p.getX() + Math.sin(ned) * 1000), (int)(p.getY() + Math.cos(ned) * 1000),
											result.get(i).x, result.get(i).y, result.get(i+1).x, result.get(i+1).y);

									result.remove(result.size()-1);
									result.add(po2);
									result.add(new Point((int)p.getX(), (int)p.getY()));
									result.add(po1);
								}else{

									//hvis det er et sted midt på indsættes der hvor vi er
									Point po1 = null, po2 = null;

									//	            					Sysout.print(Math.abs(Math.atan2(result.get(i+1).x - result.get(i).x, result.get(i+1).y - result.get(i).y) - op));
									//	            					Sysout.print(Math.abs(Math.atan2(result.get(i+1).x - result.get(i).x, result.get(i+1).y - result.get(i).y) - ned));

									if (Math.abs(Math.atan2(result.get(i+1).x - result.get(i).x, result.get(i+1).y - result.get(i).y) - op) > 1)
										po1 = Hjaelper.intersection((int)p.getX(), (int)p.getY(), (int)(p.getX() + Math.sin(op) * 1000), (int)(p.getY() + Math.cos(op) * 1000),
												result.get(i).x, result.get(i).y, result.get(i+1).x, result.get(i+1).y);

									if (Math.abs(Math.atan2(result.get(i+1).x - result.get(i).x, result.get(i+1).y - result.get(i).y) - ned) > 1)
										po2 = Hjaelper.intersection((int)p.getX(), (int)p.getY(), (int)(p.getX() + Math.sin(ned) * 1000), (int)(p.getY() + Math.cos(ned) * 1000),
												result.get(i).x, result.get(i).y, result.get(i+1).x, result.get(i+1).y);

									int q = 1;
									int limitWhile = 1000;
									while ((po1 == null || pitch.isOut(po1.x, po1.y)) && limitWhile > 0){
										limitWhile--;
										if (i+q == result.size() - 1){
											po1 = Hjaelper.intersection((int)p.getX(), (int)p.getY(), (int)(p.getX() + Math.sin(op) * 1000), (int)(p.getY() + Math.cos(op) * 1000),
													0, pitch.getPitchPosY(), 1000, pitch.getPitchPosY());
										}
										else{
											if (Math.abs(Math.atan2(result.get(i+q+1).x - result.get(i+q).x, result.get(i+q+1).y - result.get(i+q).y) - op) > 1)
												po1 = Hjaelper.intersection((int)p.getX(), (int)p.getY(), (int)(p.getX() + Math.sin(op) * 1000), (int)(p.getY() + Math.cos(op) * 1000),
														result.get(i+q).x, result.get(i+q).y, result.get(i+q+1).x, result.get(i+q+1).y);
										}
										q++;
									}
									q--;
									//	            					Sysout.print(Math.abs(Math.atan2(result.get(i+q+1).x - result.get(i+q).x, result.get(i+q+1).y - result.get(i+q).y) - op));
									q = 0;
									limitWhile = 1000;
									while ((po2 == null || pitch.isOut(po2.x, po2.y)) && limitWhile > 0){
										limitWhile--;
										if (i-q == 0){
											po2 = Hjaelper.intersection((int)p.getX(), (int)p.getY(), (int)(p.getX() + Math.sin(ned) * 1000), (int)(p.getY() + Math.cos(ned) * 1000),
													0, pitch.getPitchPosY() + pitch.getPitchHeight(), 1000, pitch.getPitchPosY() + pitch.getPitchHeight());
										}
										else{
											if (Math.abs(Math.atan2(result.get(i-q-1).x - result.get(i-q).x, result.get(i-q-1).y - result.get(i-q).y) - ned) > 1)
												po2 = Hjaelper.intersection((int)p.getX(), (int)p.getY(), (int)(p.getX() + Math.sin(ned) * 1000), (int)(p.getY() + Math.cos(ned) * 1000),
														result.get(i-q).x, result.get(i-q).y, result.get(i-q-1).x, result.get(i-q-1).y);
										}
										q++;
									}
									q--;
									if (i-q-1 > 0)
										//	            						Sysout.print(Math.abs(Math.atan2(result.get(i-q-1).x - result.get(i-q).x, result.get(i-q-1).y - result.get(i-q).y) - ned));
										if (po1 != null && po2 != null){

											if (result.get(i).y > po1.y && result.get(i).y < po2.y){
												result.remove(i);
												result.add(i,po2);
												result.add(i+1,new Point((int)p.getX(), (int)p.getY()));
												result.add(i+2,po1);
											}
											else if (result.get(i+1).y > po1.y && result.get(i+1).y < po2.y){
												result.remove(i+1);
												result.add(i+1,po2);
												result.add(i+2,new Point((int)p.getX(), (int)p.getY()));
												result.add(i+3,po1);
											}
											else if (result.get(i-1).y > po1.y && result.get(i-1).y < po2.y){
												result.remove(i-1);
												result.add(i-1,po2);
												result.add(i,new Point((int)p.getX(), (int)p.getY()));
												result.add(i+1,po1);
											}
										}

								}
							}
							//hvis vi har tilføjet en forsvarer til linien kan vi hoppe ud og behøver ikke tjekke de andre
							i = result.size();
						}
					}
				}
		}
		throughLine = result;
	}

	public static String[] validateLineup(String[] positions){
		final String[] formation442D = {"7,250", "180,468", "170,58", "134,176", "132,342", "235,256", "283,416", "311,266", "374,182", "372,318", "285,88"};
		
		positions[0] = "7,250";
		for (int i = 0; i < 11; i++){
			if (!positions[i].contains(",")){
				positions[i] = formation442D[i];
			}
			else{
				try{
					int x = Integer.parseInt(positions[i].split(",")[0]);
					int y = Integer.parseInt(positions[i].split(",")[1]);
					
					if (x < 5)
						x = 5;
					if (x > 875)
						x = 875;
					if (y < 5)
						y = 5;
					if (y > 508)
						y = 508;
						
					positions[i] = x + "," + y;
				}
				catch (Exception e){
					positions[i] = formation442D[i];
				}
				
			}
		}
			return positions;
	}
	
	public void setPenaltyTakers(){
		penaltyTakers.clear();
		//Er ham der skal tage det ifølge taktikken på banen?
		if (pPenalty != null && players.contains(pPenalty)){
			//Det er han. Han skal sparke først
			penaltyTakers.add(pPenalty);
		}
		
		//resten af spillerne på holdet tilføjes efter deres shooting 
		while (penaltyTakers.size() < players.size()){
			double shooting = 0;
			Player next = players.get(0);
			
			for (Player p : players){
				if (!penaltyTakers.contains(p)){
					if (p.getShooting() > shooting){
						next = p;
						shooting = p.getShooting();
					}
				}
			}
			if (!penaltyTakers.contains(next))
				penaltyTakers.add(next);
		}
		
	}
	
	public TeamState getTeamState() {
		return teamState;
	}
	
	public void sortByHeight(){
		//sorter medspillere (forsvarere) efter hvor høje de er - højest først
		playersByHeight.clear();
		playersByHeight.addAll(players);
		
		Collections.sort(playersByHeight, new Comparator<Player>(){
			public int compare(Player p1, Player p2) {
				return (int)p2.height - (int)p1.height;
			}});
	}

	public void sortFrontToBack(){
		//sorter medspillere (forsvarere) efter hvor høje de er - højest først
		playersFrontToBack.clear();
		playersFrontToBack.addAll(players);
		
		Collections.sort(playersFrontToBack, new Comparator<Player>(){
			public int compare(Player p1, Player p2) {
				return (int)Math.abs(p1.startPosX - p1.pitch.getPitchMidX()) - (int)Math.abs(p2.startPosX - p2.pitch.getPitchMidX());
			}});
	}
	
	public void update(Bold bold){

		this.bold = bold;
		int pitchMid = players.get(1).getPitch().getPitchMidX();
		//find linien for offside
		offsideDistToGoal = 9999;
		for (Player p : players)
			if (!p.isKeeper && Hjaelper.Distance(p.getX(), 0, goalX, 0) < offsideDistToGoal)
				offsideDistToGoal = Hjaelper.Distance(p.getX(), 0, goalX, 0);
		
		//Saet team state
		if ((System.currentTimeMillis() - teamStateTimer) > 1000){
		teamStateTimer = System.currentTimeMillis();
		//Her prøver jeg at sætte teamState efter hvem der har bolden og hvor på banen den befinder sig
		//Hvis målmanden har bolden skal begge hold gå på midten
		if(bold.hasKeeperGotBall()){
			teamState = TeamState.mid;
		}
		else{
		//Hold A
		if (getGoalX() < oppTeam.getGoalX()){
			//hvis vores hold har bolden
			if (bold.firstToBall != null && bold.firstToBall.getMyTeam().equals(this)){
				if (bold.getX() > pitchMid -120){
//					System.out.println("TEAM A - ATTACK: Vi når bolden først og bolden er over midten-50");
					teamState = TeamState.attack;
				}
				else if((bold.getLastTouch() != null && bold.getLastTouch().isKeeper) || bold.getX() < pitchMid - 320){
//					System.out.println("TEAM A - DEFEND: Vi når bolden først og bolden har rørt målmanden sidst eller er under midten-200");
					teamState = TeamState.defend;
				}
				else {
//					System.out.println("TEAM A - MID: Vi når bolden først");
					teamState = TeamState.mid;
				}
			}
			else{//hvis vores hold ikke når bolden først
				if (bold.getX() < pitchMid -50){
//					System.out.println("TEAM A - DEFEND: Vi når ikke bolden først og bolden er under midten");
					teamState = TeamState.defend;
				}
				else if((bold.getLastTouch() != null && bold.getLastTouch().isKeeper) || bold.getX() > (pitchMid + 200)){
//					System.out.println("TEAM A - ATTACK: Vi når ikke bolden først og bolden har rørt målmanden sidst eller bolden er over midten+200");
					teamState = TeamState.attack;
				}
				else {
//					System.out.println("TEAM A - MID: Vi når ikke bolden først og bolden er under midten");
					teamState = TeamState.mid;

				}
			}
		}
		//Hold B
		else{
			//hvis vores hold har bolden
			if (bold.firstToBall != null && bold.firstToBall.getMyTeam().equals(this)){
				if (bold.getX() < pitchMid+120){
//					System.out.println("TEAM B - ATTACK: Vi når bolden først og bolden er under midten+50");
					teamState = TeamState.attack;
				}
				else if((bold.getLastTouch() != null && bold.getLastTouch().isKeeper) || bold.getX() > pitchMid + 320){
//					System.out.println("TEAM B - DEFEND: Vi når bolden først og bolden har rørt målmanden sidst eller er over midten+200");
					teamState = TeamState.defend;
				}
				else{
//					System.out.println("TEAM B - MID: Vi når bolden først");
					teamState = TeamState.mid;
				
				}
			}
			else{//hvis vores hold ikke har bolden
				if (bold.getX() > pitchMid + 50){
//					System.out.println("TEAM B - DEFEND: Vi når ikke bolden først og bolden er over midten");
					teamState = TeamState.defend;
				}
				else if((bold.getLastTouch() != null && bold.getLastTouch().isKeeper) || bold.getX() < (pitchMid - 200)){
//					System.out.println("TEAM B - ATTACK: Vi når ikke bolden først og bolden har rørt målmanden sidst eller bolden er under midten+200");
					teamState = TeamState.attack;
				}
				else {
//					System.out.println("TEAM B - MID: Vi når ikke bolden først");
					teamState = TeamState.mid;
				}
			}
		}
		}
		}
		//midlertidig
//		if (bold.lastTouch.myTeam.equals(this) 
//				//|| (bold.firstToBall.myTeam.equals(this) && Hjaelper.Distance(bold.x, 0, goalX, 0) > players.get(0).pitch.pitchWidth * 0.5)
//				)
//			teamState = TeamState.attack;
//		else if (bold.lastTouch.oppTeam.equals(this) && Hjaelper.Distance(bold.x, 0, goalX, 0) < players.get(0).pitch.pitchWidth * 0.6)
//			teamState = TeamState.defend;
//		else if (teamState.equals(TeamState.attack))
//			teamState = TeamState.mid;
//		
//		
		if (players.get(0).getMatch().getState() == MatchState.FREE_KICK){
			if (players.get(0).getMatch().getSetPieceTeam() == this){
				if (Math.abs(goalX - players.get(0).getMatch().getSetPieceX()) > 400)
					teamState = TeamState.attack;
				else
					teamState = TeamState.mid;
			}
			else
				teamState = TeamState.defend;
		}
		else if (players.get(0).getMatch().getState() == MatchState.THROW_IN){
			if (players.get(0).getMatch().getSetPieceTeam() == this){
				if (Math.abs(goalX - players.get(0).getMatch().getSetPieceX()) > 400)
					teamState = TeamState.attack;
				else
					teamState = TeamState.mid;

			}
			else{
				if (Math.abs(players.get(0).getMatch().getSetPieceX() - goalX) < Math.abs(players.get(0).getPitch().getPitchMidX() - goalX))
					teamState = TeamState.defend;
				else
					teamState = TeamState.mid;	

			}
		}

		setMarkeringer(new ArrayList<Player>(), false);

	}

	public void setMarkeringer(List<Player> kanIkkeDaekkeOp, boolean setpiece){
		
		//TODO: Forsvarere der ikke markerer skal dække af bag andre
		//TODO: lav separat setMarkeringerSetPiece()
	
		//Hent modstanderne som ikk er langt væk og giv dem score efter hvor farlige de er
		List<MarkingAttackerObject> sortedOpps = new ArrayList<MarkingAttackerObject>();

		//Hvor langt skal angriberne være fra vores offsidelinie før vi begynder at dække dem op?
		int maxMarkingDist;
		
		if (teamState.equals(TeamState.defend))
			maxMarkingDist = 350;
		else if (teamState.equals(TeamState.mid))
			//Teamstate mid should be when we don't have the ball but the teams are on the opposition half- i.e. just afte rlosing the ball in attack - 
			//so we want to mark opponents close to our goal than the ball and just behind the ball
			maxMarkingDist = 280;
		else
			maxMarkingDist = 100;
		
		for (Player p : oppTeam.getPlayers()){
			if (!p.isKeeper && (Math.abs(p.getX() - goalX) < offsideDistToGoal + maxMarkingDist) &&
					(matchEngine.getMatch().getState().equals(MatchState.ON) || !matchEngine.getPitch().isOut((int)p.getX(), (int)p.getY()))){
				
				//Marker kun dem der er foran bolden - 100
				if (Hjaelper.Distance(p.getX(), 0, goalX, 0) < Hjaelper.Distance(bold.getX(), 0, goalX, 0) + 100){
					
					//Giv score efter afstand til mål, afstand til bold og efter om spilleren har bolden
					double score = Hjaelper.Distance(p.getX(), p.getY(), goalX, goalY + 30);
					
					//Hvis spilleren har bolden er afstanden til mål vigtigere og afstand til bolden bliver lig 0
					if (bold.lastTouch != null && bold.lastTouch.equals(p) && bold.passTo == null && !bold.shot)
						score *= 0.7;
					
					//Hvis spilleren er ved at modtage en aflevering er afstanden til mål vigtigere og afstand til bolden bliver lig 0
					else if (bold.passTo != null && bold.passTo.equals(p))
						score *= 0.8;
					
					else
						score += Hjaelper.Distance(p.getX(), p.getY(), bold.x, bold.y);
				
					sortedOpps.add(new MarkingAttackerObject(p, score));
				}
			}
		}
		
		
		//sorter efter hvor farlige de er (score)
		Collections.sort(sortedOpps, new Comparator<MarkingAttackerObject>(){
			public int compare(MarkingAttackerObject p1, MarkingAttackerObject p2) {
				return (int)(p1.score - p2.score);
			}});
		
		for (MarkingAttackerObject opp : sortedOpps){
			Sysout.print("Attacker: " + opp.player.getShirtNumber(), "marking");
			
			//find punktet foran modstanderen
			double daekningspunktX = 0;
			double daekningspunktY = 0;
			double r;

			//Hvis spilleren har bolden skal vi dække i forhold til den
			if (bold.lastTouch != null && bold.getLastTouch().equals(opp) && !bold.shot && bold.passTo == null){
				r = Math.atan2(goalX - bold.getX(), goalY + 30 - bold.getY());
				daekningspunktX = bold.getX() + 15 * Math.sin(r);
				daekningspunktY = bold.getY() + 15 * Math.cos(r);
			}
			else{
				r = Math.atan2(goalX - opp.player.x, goalY + 30 - opp.player.y);
				daekningspunktX = opp.player.x + 15 * Math.sin(r);
				daekningspunktY = opp.player.y + 15 * Math.cos(r);
			}
			
			//Tjek alle forsvarerne som er i nærheden eller foran angriberen og giv dem en score for hvor tæt de er på modstanderen 
			//og en score for hvor godt de står mellem modstanderen og målet  
			for (Player p : players){
				if (!p.isKeeper && !kanIkkeDaekkeOp.contains(p)){
					
					if (opp.bestMarkers.size() == 0 || Hjaelper.Distance(p.getX(), p.getY(), opp.player.x, opp.player.y) < 150 || 
							Hjaelper.Distance(p.getXIn(15), p.getYIn(15), goalX, goalY + 29) < 
							Hjaelper.Distance(goalX, goalY + 29, opp.player.getXIn(15), opp.player.getYIn(15))){
						
						Sysout.print("Defender: " + p.getShirtNumber(), "marking");
						
						//Score til om forsvareren er mellem angriberen og målet
						double scorePos = Hjaelper.Distance(p.getXIn(15), p.getYIn(15), goalX, goalY + 30) - 
								Hjaelper.Distance(opp.player.getXIn(15), opp.player.getYIn(15), goalX, goalY + 30);
						Sysout.print("scorePos: " + scorePos, "marking");
						
						//Score til hvor langt væk forsvareren er fra punktet foran modstanderen
						double scoreDist = Hjaelper.Distance(p.getXIn(15), p.getYIn(15), daekningspunktX, daekningspunktY); 
						Sysout.print("scoreDist: " + scoreDist, "marking");
						
						//Score til hvor langt væk fra sin plads forsvareren skal løbe for at dække modstanderen op
						double formationScore = Hjaelper.Distance(p.getPosX(), p.getPosY(), daekningspunktX, daekningspunktY);
						Sysout.print("formationScore: " + formationScore, "marking");
						
						//Generel score til hvor godt forsvareren står til at dække ham op. Jo mindre score jo bedre

						//Hvor tæt han er på punktet foran angriberen / 2 + hvor langt ude til siden han er i forhold til angriberen / 2 + 
						//hvor meget han er på den forkerte side af angriberen + hvor langt angriberen er fra forsvarerens udgangsposition  * 2.5
						double scoreMark = 0.5 * scoreDist + Math.abs(p.y - opp.player.y) / 2 + scorePos * -1 + formationScore * 2.5;
						
						Sysout.print("scoreMark: " + scoreMark, "marking");
						
						opp.bestMarkers.add(new MarkingDefenderObject(p, scoreDist, scorePos, scoreMark, formationScore));

						if (scorePos > opp.safestDefDistDiff){
							opp.safestDefDistDiff = scorePos;
							opp.safestDef = p;
						}
						if (scoreDist < opp.closestDefDist) {
							opp.closestDefDist = scoreDist;
							opp.closestDef = p;
						}
						if (scoreMark < opp.bestMarkerScore){
							opp.bestMarkerScore = scoreMark;
							opp.bestMarker = p;
						}
						opp.numberOfMarkers++;
						
					}
				}
			}
		}
		
		//Ryd markeringer
		for (Player p : players)
			p.currentlyMarking.clear();
		
		//Løb de sorterede angribere igennem fra den farligste til den mindst farlige
		for (MarkingAttackerObject opp : sortedOpps){

			//safestDefDistDiff er positiv hvis en forsvarer er mellem angriberen og målet
			if (opp.safestDefDistDiff < 0){
				//Den nærmeste tager altid angriberen når han er igennem - evt. sammen med en anden angriber han markerer
				//Hvis angriberen er farlig kan den der først kan nå ind foran ham hjælpe til (ikke nødvendigvis den nærmeste)
				//Er angriberen f.eks. offside kan man lade ham stå men dette håndteres af den enkelte spiller
				
				if (opp == null)
					;
				if (opp.closestDef == null)
					;
				if (opp.closestDef.currentlyMarking == null)
					;
				if (opp.player == null)
					;
				
				//The closest marker tries to get back behind the opponent
				opp.closestDef.currentlyMarking.add(opp.player);
				
				//If it's a dangerous opponent the safest marker will help
				if (opp.score < 180 && !opp.safestDef.equals(opp.closestDef))
					opp.safestDef.currentlyMarking.add(opp.player);
			}
			else{
				opp.bestMarker.currentlyMarking.add(opp.player);
			}
		}
		
		//Løb igennem forsvarsspillerne som er sat til at markere. Hvis der er nogen der har to skal dem 
		//der ikke har nogen tage over (evt. skal hele holdet rykke over)
		ArrayList<Player> done = new ArrayList<Player>();
		
		for (Player p : players){
			if (p.currentlyMarking.size() > 1){
				for (Player oppPlayer : p.currentlyMarking){
					if (!done.contains(oppPlayer)){
						for (MarkingAttackerObject opp : sortedOpps){
							if (opp.player.equals(oppPlayer)){

								//sorter efter hvor godt de står til at markere
								Collections.sort(opp.bestMarkers, new Comparator<MarkingDefenderObject>(){
									public int compare(MarkingDefenderObject p1, MarkingDefenderObject p2) {
										return (int)(p1.markerScore - p2.markerScore);
									}});

								int i = 0;
								while (i < opp.bestMarkers.size())
									if (!opp.bestMarkers.get(i).equals(p) && opp.bestMarkers.get(i).player.currentlyMarking.size() == 0){
										opp.bestMarkers.get(i).player.currentlyMarking.add(opp.player);
										done.add(opp.player);
										i = opp.bestMarkers.size();
									}
									else
										i++;

							}
						}
					}
				}
			}
		}
			
		
		//Hvis en angriber er farlig kan en ledig forsvarsspiller også hjælpe den der dækker ham op
		
		//Løb igennem dem der ikke har nogen. Hvis de har udgangsposition tæt ved eller bag ved nogen modspillere bakker de op.
		
		
	}
	
//	public void setMarkeringer(List<Player> kanIkkedaekkeOp, boolean setpiece){
//		
//		for (Player p : kanIkkedaekkeOp){
//			p.setCurrentlyMarking(null);
//		}
//		
//		List<Player> doubleCheckMarkedOpps = new ArrayList<Player>();
//		for (Player p : players){
//			
//			if (setpiece) p.setCurrentlyMarking(null);
//			
//			//hvis vi er for langt fra hvor vi skal staa saa lad en anden daekke op
//			if (p.currentlyMarking != null &&
//				Hjaelper.Distance(p.posX, p.posY, p.currentlyMarking.x, p.currentlyMarking.y) > 250)
//				p.currentlyMarking = null;
//			
//			//hvis vi allerede har tilfoejet en modstander er det fordi en anden daekker ham op
//			if (doubleCheckMarkedOpps.contains(p.getCurrentlyMarking())){
//				p.setCurrentlyMarking(null);
//			}
//			else{
//				doubleCheckMarkedOpps.add(p.getCurrentlyMarking());
//			}
//		}
//
//		//Hent modstanderne
//		List<Player> sortedOpps = new ArrayList<Player>();
//
//		//Men ikke dem der er langt vaek
//		for (Player p : oppTeam.getPlayers()){
//			if ((!p.isKeeper) && (Hjaelper.Distance(p.getX(), 0, goalX, 0) < 400// || teamState.equals(TeamState.defend)
//					)){
//				sortedOpps.add(p);
//			}
//		}
//		
//		//sorter efter afstand til maal
//		Collections.sort(sortedOpps, new Comparator<Player>(){
//			public int compare(Player p1, Player p2) {
//				return (int)(Hjaelper.Distance(p1.getX(), p1.getY(), p1.getOppTeam().getGoalX(), p1.getOppTeam().getGoalY()) -
//				Hjaelper.Distance(p2.getX(), p2.getY(), p2.getOppTeam().getGoalX(), p2.getOppTeam().getGoalY()));
//			}});
//
//		
//		//hvor mange forsvarere skal vi bruge til at daekke modstanderne op? Lige s[ aa mange modstandere der er plus en.
//		int count = sortedOpps.size() + 1;
//		
//		//Hent dem der skal daekke op
//		List<Player> sortedMates = new ArrayList<Player>();
//
//		for (Player p : players)
//			if (p.getRole() != PlayerRole.GK && !kanIkkedaekkeOp.contains(p)){
//				sortedMates.add(p);
//			}
//		
//		double daekningspunktX = 0;
//		double daekningspunktY = 0;
//		
//		if (bold.lastTouch != null && !bold.shot && !setpiece){
//		
//			//find punktet foran boldholderen
//			double r;
//			
//			if (bold.passTo == null){
//				r = Math.atan2(bold.lastTouch.getOppTeam().getGoalX() - bold.getX(), bold.lastTouch.getOppTeam().getGoalY() + 30 - bold.getY());
//				daekningspunktX = bold.getX() + 15 * Math.sin(r);
//				daekningspunktY = bold.getY() + 15 * Math.cos(r);
//			}
//			else{
//				r = Math.atan2(bold.passTo.getOppTeam().getGoalX() - bold.passTo.getX(), bold.passTo.getOppTeam().getGoalY() + 30 - bold.passTo.getY());
//				daekningspunktX = bold.passTo.getX() + 15 * Math.sin(r);
//				daekningspunktY = bold.passTo.getY() + 15 * Math.cos(r);
//			}
//			
//		}
//		
//		final double daekX = daekningspunktX;
//		final double daekY = daekningspunktY;
//		
//		if (!setpiece){
//			//sorter medspillere (forsvarere) efter hvor langt fra punktet de er
//			Collections.sort(sortedMates, new Comparator<Player>(){
//				public int compare(Player p1, Player p2) {
//					return (int)(Hjaelper.Distance(daekX, daekY, p1.x, p1.y) -
//							Hjaelper.Distance(daekX, daekY, p2.x, p2.y));
//				}});
//
//
//			//den der er tættest på punktet foran bolden dækker boldholderen
//			if ((bold.getLastTouch() != null && bold.getLastTouch().myTeam.equals(oppTeam) 
//					&& bold.passTo == null) &&	Hjaelper.Distance(bold.getLastTouch().x, 
//							bold.getLastTouch().y, goalX, goalY) < 400){
//
//				sortedMates.get(0).currentlyMarking = bold.getLastTouch();
//				sortedMates.remove(0);
//			}
//
//			else if (bold.passTo != null && 
//					Hjaelper.Distance(bold.passTo.x, bold.passTo.y, goalX, goalY) < 400){
//
//				sortedMates.get(0).currentlyMarking = bold.passTo;
//				sortedMates.remove(0);
//			}
//		}
////		System.out.println("**********SortedMates***************");
////		for (Player q : sortedMates){
////			System.out.println(q.shirtNumber + " dækker op");
////		}
////		System.out.println("**********SortedOpps***************");
////		for (Player q : sortedOpps){
////			System.out.println(q.shirtNumber + " skal dækkes op");
////		}
//		
//		//Som udgangspunkt daekker man den op der er taettest paa
//		for (Player p : sortedMates){
//			
//			if (p.getCurrentlyMarking() == null){
//				Player mark = null;
//				double dist = 999999;
//				boolean farlig = false;
//				
//				//mark = den modstander der skal daekkes
//				for (Player q : sortedOpps){
//					
//					if (Hjaelper.Distance(p.getX(), p.getY(), q.getX(), q.getY()) < dist &&
//							!oppsMarked.contains(q) && !q.isKeeper && 
//							(Hjaelper.Distance(p.getPosX(), p.getPosY(), q.getX(), q.getY()) < 250 || teamState.equals(TeamState.defend))
//							){
//						
////						hvis modstanderen er tæt på mål og har bolden eller er ved at modtage den
////						skal der være to der dækker ham op
//						if ((bold.getLastTouch() == q || bold.passTo == q) && 
//								Hjaelper.Distance(q.x, q.y, goalX, goalY) < 300)
//							;
//
//						boolean myman = true;
//						
//						// hvis modstanderen er forbi os ser vi om der er en medspiller der kan tage over
//						if (Hjaelper.Distance(p.x, p.y, goalX, goalY+29) + 10 > Hjaelper.Distance(q.x, q.y, goalX, goalY+29)){
//							Player to = null;
//							for (Player m : sortedMates){
//								if (!m.isKeeper && m != p && m.getCurrentlyMarking() == null){
//									if (Hjaelper.Distance(m.x, m.y, goalX, goalY+29) < Hjaelper.Distance(p.x, p.y, goalX, goalY+29)){
//										if (Hjaelper.distToLine(m.x, m.y, q.x, q.y, goalX, goalY+29) < Hjaelper.Distance(m.x, m.y, q.x, q.y)){
//											if (to == null){
//												to = m;
//											}
//											else if (Hjaelper.distToLine(m.x, m.y, q.x, q.y, goalX, goalY+29) + Hjaelper.Distance(m.x, m.y, q.x, q.y) <
//													Hjaelper.distToLine(to.x, to.y, q.x, q.y, goalX, goalY+29) + Hjaelper.Distance(to.x, to.y, q.x, q.y)){
//												to = m;
//											}
//											myman = false;
//										}
//									}
//								}
//							}
//							if (to != null){
//								to.setCurrentlyMarking(q);
//								oppsMarked.add(q);
//							}
//						}
//						
//						//kun hvis der ikke er en ledig medspiller taettere paa 
//						
//						Player closer = null;
////						for (Player m : sortedMates){
////							if (!m.isKeeper && m != p && m.getCurrentlyMarking() == null){
////								if (Hjaelper.Distance(p.x, p.y, q.x, q.y) > Hjaelper.Distance(m.x, m.y, q.x, q.y)){
////
////									if (closer == null){
////										closer = m;
////										System.out.println(closer.shirtNumber + " stadig tættere på " + q.shirtNumber + " end " + p.shirtNumber);
////									}
////									else{
////										if (Hjaelper.Distance(p.x, p.y, goalX, goalY+29) > Hjaelper.Distance(q.x, q.y, goalX, goalY+29) && Hjaelper.Distance(m.x, m.y, goalX, goalY+29) < Hjaelper.Distance(closer.x, closer.y, goalX, goalY+29)){
////											closer = m;
////											System.out.println(closer.shirtNumber + " stadig stadig tættere på " + q.shirtNumber + " end " + p.shirtNumber);
////										}
////									}
////									naermest = false;
////								}
////							}
////						}
//						if(myman){
//							dist = Hjaelper.Distance(p.getX(), p.getY(), q.getX(), q.getY());
//							mark = q;
//							System.out.println(p.shirtNumber + " dækker " + q.shirtNumber);
//						}
//						else{
////							closer.setCurrentlyMarking(q);
////							oppsMarked.add(q);
////							System.out.println(closer.shirtNumber + " tættere på " + q.shirtNumber + " end " + p.shirtNumber);
//						}
//					}
//				}
//
//
//				if (mark != null){
//
//						p.setCurrentlyMarking(mark);
//						
////						System.out.println(p.lastname + " dækker " + mark.lastname);
//						//vi husker han er daekket
//						oppsMarked.add(mark);
//				}
//			}
//			
//		}
//		if (setpiece){
//			oppsMarked.clear();
//			ArrayList<Player> markers = new ArrayList<Player>();
//			for (Player p : getPlayersByHeight()){
//				p.currentlyMarking = null;
//				if (!kanIkkedaekkeOp.contains(p) && !p.isKeeper)
//					markers.add(p);
//			}
//			ArrayList<Player> markees = new ArrayList<Player>();
//			for (Player p : oppTeam.getPlayersByHeight())
//				if (Hjaelper.Distance(p.getX(), p.getY(), goalX, goalY) < 400)
//					markees.add(p);
//			
//			for (int i = 0; i < markees.size(); i++)
//				if (i < markers.size())
//					markers.get(i).currentlyMarking = markees.get(i);
//					
//		}
//	}
	
	/** Doesn't work properly
	 * Calculates and sets the backlines of each team
	 */
//	public void calculateBackline(){
//		double teamBackline = 0;
//		teamBackline = 1.2 * Math.abs(this.getGoalX() - this.backPlayer.getStartPosX());
//		System.out.println(this.name + " goalX: " + this.getGoalX() + ", " + this.backPlayer.getStartPosX()+ this.getGoalX() + ", teamBackline: " + teamBackline);
//		System.out.println(this.backPlayer.firstname + " " + this.backPlayer.lastname + " " +this.backPlayer.getStartPosX());
//		
//		if(this.getGoalX() < this.oppTeam.getGoalX()){
//			//Hvis vi er venstre hold og bolden er tættere på vores mål end backline, så sættes backline til bolden
//			if(bold != null && bold.x < teamBackline) teamBackline = (int)bold.x;
//		}
//		else {
//			teamBackline = this.getGoalX() - teamBackline;
//			//Hvis vi er højre hold og bolden er tættere på vores mål end backline, så sættes backline til bolden
//			if(bold != null && teamBackline < bold.x) teamBackline = (int)bold.x;
//		}
//		
//		
//		this.setBackLine((int)teamBackline);
//		System.out.println(this.name + " defensive line: " + this.getBackLine());
//	}
	
	
	public void setTeamState(TeamState teamState) {
		this.teamState = teamState;
	}

	public int getGoalX() {
		return goalX;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Player getKeeper(){
		Player result = null;

		for (Player p : players)
			if (p.isKeeper())
				result = p;

		return result;
	}

	public void setGoalX(int goalX) {
		this.goalX = goalX;
	}

	public int getGoalY() {
		return goalY;
	}

	public void setGoalY(int goalY) {
		this.goalY = goalY;
	}

	public Team(String name, Color color, Color color2, int id, MatchEngine mainFrame, int rep, int fame){
		leagueReputation = rep;
		this.fame = fame;
		this.id = id;
		this.name = name;
		this.color = color;
		this.color2 = color2;
		backLine = 200;
		this.matchEngine = mainFrame;
	}

	public void addReplayCode(int code){
		if (matchEngine != null) matchEngine.addReplayCode(code);
	}
	
	public int getBackLine() {
		return backLine;
	}

	public void setBackLine(int backLine) {
		this.backLine = backLine;
	}

	public ArrayList<Player> getPlayers(){
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;		
	}
	
	public Color getColor(){
		return color;
	}
	public Color getColor2(){
		return color2;
	}

	public Team getOppTeam() {
		return oppTeam;
	}

	public void setOppTeam(Team oppTeam) {
		this.oppTeam = oppTeam;
	}

	public String getName() {
		return name;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setColor2(Color color2) {
		this.color2 = color2;
	}

	public void addPlayer(Player p){
		if (!players.contains(p))
			players.add(p);
		p.setMyTeam(this);
	}
	
	public void addSub(Player p){
		if (!subs.contains(p))
			subs.add(p);
		p.setMyTeam(this);
	}

	public double getOffsideDistToGoal() {
		return offsideDistToGoal;
	}

	public void setFrontAndBackPlayers(){
		for (Player p : players){
			if (!p.isKeeper){
				if (frontPlayer == null)
					frontPlayer = p;
				else{
					if (goalX < oppTeam.goalX){
						if (p.startPosX > frontPlayer.startPosX)
							frontPlayer = p;
					}
					else{
						if (p.startPosX < frontPlayer.startPosX)
							frontPlayer = p;
					}
				}

				if (backPlayer == null)
					backPlayer = p;
				else{
					if (goalX < oppTeam.goalX){
						if (p.startPosX < backPlayer.startPosX)
							backPlayer = p;
					}
					else{
						if (p.startPosX > backPlayer.startPosX)
							backPlayer = p;
					}
				}
			}
		}
	}
	
	public Player getFrontPlayer(){
		return frontPlayer;
	}
	
	public void setOffsideDistToGoal(double offsideDistToGoal) {
		this.offsideDistToGoal = offsideDistToGoal;
	}

	public int getStatShots() {
		return statShots;
	}

	public void addStatShots() {
		this.statShots++;
	}

	public ArrayList<Player> getSubs() {
		return subs;
	}

	public void setSubs(ArrayList<Player> subs) {
		this.subs = subs;
	}

	public ArrayList<Player> getUsedSubs() {
		return usedSubs;
	}

	public void setUsedSubs(ArrayList<Player> usedSubs) {
		this.usedSubs = usedSubs;
	}

	public Player getpThrowinRight() {
		return pThrowinRight;
	}

	public void setpThrowinRight(Player pThrowinRight) {
		this.pThrowinRight = pThrowinRight;
	}

	public Player getpThrowinLeft() {
		return pThrowinLeft;
	}

	public void setpThrowinLeft(Player pThrowinLeft) {
		this.pThrowinLeft = pThrowinLeft;
	}

	public Player getpPenalty() {
		return pPenalty;
	}

	public void setpPenalty(Player pPenalty) {
		this.pPenalty = pPenalty;
	}

	public Player getpFreekickShort() {
		return pFreekickShort;
	}

	public void setpFreekickShort(Player pFreekickShort) {
		this.pFreekickShort = pFreekickShort;
	}

	public Player getpFreekickLong() {
		return pFreekickLong;
	}

	public void setpFreekickLong(Player pFreekickLong) {
		this.pFreekickLong = pFreekickLong;
	}

	public Player getpCornerRight() {
		return pCornerRight;
	}

	public void setpCornerRight(Player pCornerRight) {
		this.pCornerRight = pCornerRight;
	}

	public Player getpCornerLeft() {
		return pCornerLeft;
	}

	public Player getpTargetMan() {
		return pTargetMan;
	}

	public void setpTargetMan(Player pTargetMan) {
		this.pTargetMan = pTargetMan;
	}

	public void setpCornerLeft(Player pCornerLeft) {
		this.pCornerLeft = pCornerLeft;
	}

	public Player getpCaptain() {
		return pCaptain;
	}

	public void setpCaptain(Player pCaptain) {
		this.pCaptain = pCaptain;
	}

	public ArrayList<Player> getPlayersByHeight() {
		return playersByHeight;
	}

	public ArrayList<Player> getPlayersFrontToBack() {
		return playersFrontToBack;
	}

	public ArrayList<Player> getOppsMarked() {
		return oppsMarked;
	}

	public void setOppsMarked(ArrayList<Player> oppsMarked) {
		this.oppsMarked = oppsMarked;
	}

	public int getLeagueReputation() {
		return leagueReputation;
	}

	public void setLeagueReputation(int leagueReputation) {
		this.leagueReputation = leagueReputation;
	}

	public int getFame() {
		return fame;
	}

	public void setFame(int fame) {
		this.fame = fame;
	}
	
	public ArrayList<Player> getPenaltyTakers() {
		return penaltyTakers;
	}




	public ArrayList<Point> getThroughLine(Pitch pitch) {
		calcThroughLine(pitch);
		return throughLine;
	}

	public void setThroughLine(ArrayList<Point> throughLine) {
		this.throughLine = throughLine;
	}

	public int getShootoutPenalty() {
		return shootoutPenalty;
	}

	public void setShootoutPenalty(int shootoutPenalty) {
		this.shootoutPenalty = shootoutPenalty;
	}

	public TeamTactic getTeamTactic() {
		return teamTactic;
	}

	public void setTeamTactic(TeamTactic teamTactic) {
		this.teamTactic = teamTactic;
	}


	public ClubRecords getRecords() {
		return records;
	}

	public void setRecords(ClubRecords records) {
		this.records = records;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	private class MarkingAttackerObject{
		Player player;
		double score;
		ArrayList<MarkingDefenderObject> bestMarkers = new ArrayList<MarkingDefenderObject>();
		double safestDefDistDiff = -999999;
		double closestDefDist = 999999;
		double bestMarkerScore = 999999;
		Player closestDef;
		Player safestDef;
		Player bestMarker; 
		public double numberOfMarkers = 0;
		
		public MarkingAttackerObject(Player player, double score){
			this.player = player;
			this.score = score;
		}
	}
	
	private class MarkingDefenderObject{
		Player player;
		double scoreDist;
		double scorePos;
		double markerScore;
		double formationScore;
		
		public MarkingDefenderObject(Player player, double scoreDist, double scorePos, double markerScore, double formationScore){
			this.player = player;
			this.scoreDist = scoreDist;
			this.scorePos = scorePos;
			this.markerScore = markerScore;
			this.formationScore = formationScore;
		}
	}

}
