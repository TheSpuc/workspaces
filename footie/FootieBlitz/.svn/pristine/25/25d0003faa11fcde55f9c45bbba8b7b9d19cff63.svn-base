package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import data.PositionScoreCalculator;
import data.PositionScoreCalculator.Position;

import states.StateBeforeMatch;
import states.StateClearing;
import states.StateCorner;
import states.StateCrossing;
import states.StateDribbling;
import states.StateFreeKick;
import states.StateGoal;
import states.StateGoalKick;
import states.StateHTFT;
import states.StateHoldUpBall;
import states.StateKickOff;
import states.StateNothing;
import states.StatePassing;
import states.StatePenalty;
import states.StatePenaltyShootout;
import states.StatePressuring;
import states.StateRunOffBall;
import states.StateRunWithBall;
import states.StateShooting;
import states.StateThrowIn;
import trainingmodules.Instruction;

import model.ComplexAction.Action;
import model.Match.MatchState;

public class Player {
 
	public static int maxCareerPPFromFacc = 750;
	public static int maxCareerPPFromXP = 750;
	public static int maxCareerPPFromFaccAndXP = 1200;
	
	ArrayList<Integer>line = new ArrayList<Integer>();
	public ArrayList<ComplexAction> complexActions = new ArrayList<ComplexAction>();
	public double x, y;
	public Point markErr = new Point();
	int prevX, prevY;
	public int age, finalHeight;
	public double totalPPFromXP;
	public double totalPPFromFacc;
	public int countryId, userId, agentLoyalty, wage, expectedWage, agentId, leagueId, ratingsSum, ratingCount, value;
	public double agentOpinion;
	int offsideX, offsideY;
	double start_acceleration, start_topSpeed, start_dribbling, start_strength, start_tackling, start_marking, start_agility, start_Energy,
	start_reaction, start_handling, start_shooting, start_shotpower, start_passing, start_technique, start_vision, start_jumping, start_heading, 
	start_shotstopping, start_commandofarea, start_rushingout, start_morale;
	public double targetSpeed, speed, acceleration, topSpeed, dribbling, strength, tackling, marking, energy,
	agility, reaction, handling, shooting, shotpower, passing, technique, vision, height, jumping, heading, 
	stamina, shotstopping, commandOfArea, rushingout, morale;

	double goalkeeperScore, defenderScore, fullbackScore, midfielderScore, wingerScore, strikerScore;
	int bestPositionScore;
	PositionScoreCalculator.Position bestPosition = Position.GK;

	public int runTargetX, runTargetY;
	boolean guessPenalties = false;
	
	public Instruction instructionHasBall = null;
	public Instruction instructionNoBall = null;
	
	states.PlayerState statePressuring;
	states.PlayerState stateNothing;
	states.PlayerState stateKickOff;
	states.PlayerState stateBeforeMatch;
	states.PlayerState stateThrowIn;
	states.PlayerState stateHTFT;
	states.PlayerState stateFreeKick;
	states.PlayerState statePenalty;
	states.PlayerState stateGoalKick;
	states.PlayerState stateCorner;
	states.PlayerState stateGoal;
	states.PlayerState stateShooting;
	states.PlayerState statePassing;
	states.PlayerState stateDribbling;
	states.PlayerState stateRunWithBall;
	states.PlayerState stateCrossing;
	states.PlayerState stateClearing;
	states.PlayerState statePenaltyShootout;
	states.PlayerState stateRunOffBall;
	states.PlayerState stateHoldUpBall;
	
	states.PlayerState state;
	StateHasBall stateHasBall;
	StateOppHasBall stateOppHasBall;
	StateTeamHasBall stateTeamHasBall;
	StateKeeperHasBall stateKeeperHasBall;
	StateKeeper stateKeeper;
	
	//Er spilleren indenfor keeperens command of area rækkevidde?
	boolean isInKeeperArea = false;
	
	Random r = new Random();
	double a = 0; //retning
	public double igennem;
	public double targetA = 1; //den retning han gerne vi vende mod
	public double targetX = 0; 
	public double targetY = 0;
	public long ready = 0, lastUpdate = 0, lastPathCheck = 0, saveTime = 0, bounceTime = 0, lastdribble = 0, ReactionTime = 0, ballTimer = 0, wait = 0, readyfri = 0;
	int shirtNumber, id;
	int dtx = 0, dty = 0;
	public boolean isKeeper, hasBall, urgent;
	public boolean kasterSig = false;
	public boolean bestemplads = false;// sættes til true hvis målmanden har bolden og har fået en position at gå hen på - viser om spillere har fået en plads ved døbolde
	public double pladsX, pladsY; //Placering af spillere ved døbolde
	double freeFactor; //Hvor fri er spilleren. hvor langt er der til nærmeste modspiller
	String matchMessage = "";
	String matchMessage2 = "";

	Bold bold;
	Pitch pitch;
	Settings settings;
	PlayerRole role;
	public double posX, posY, startPosX, startPosY;
	PlayerAction playerAction = PlayerAction.nothing;
	Team myTeam, oppTeam;
	Match match;
	String firstname = "Per", lastname = "Pallesen";
	ArrayList<Player> currentlyMarking = new ArrayList<Player>();

	int countToBall = 999999;

	byte dribble, throughballs, runs, longshots, aggression, mentality, closingdown, crossball, 
	tightmarking, defensiveLine, pressing, shortLongPassing;

	public boolean forwardOnSetpieces;

	public PlayerMatchStats playerMatchStats = new PlayerMatchStats(this);

	double offsideBy = 0;

	public Player(){
		
	}

	public Player cloneForMove(){
		Player result = new Player();
		
		result.agility = agility;
		result.topSpeed = topSpeed;
		result.acceleration = acceleration;
		result.height = height;
		
		result.speed = speed;
		result.a = a;
		result.targetA = targetA;
		result.x = x;
		result.y = y;
		result.targetX = targetX;
		result.targetY = targetY;
		result.wait = wait;
		
		result.bold = bold;
		result.match = match;
		result.myTeam = myTeam;
		result.oppTeam = oppTeam;
		result.settings = settings;

		return result;
	}
	
	public void setStates(){
		statePressuring = (states.PlayerState)(new StatePressuring(this));
		stateNothing = (states.PlayerState)(new StateNothing(this));
		stateKickOff = (states.PlayerState)(new StateKickOff(this));
		stateBeforeMatch = (states.PlayerState)(new StateBeforeMatch(this));
		stateThrowIn = (states.PlayerState)(new StateThrowIn(this));
		stateHTFT = (states.PlayerState)(new StateHTFT(this));
		stateFreeKick = (states.PlayerState)(new StateFreeKick(this));
		statePenalty = (states.PlayerState)(new StatePenalty(this));
		stateGoalKick = (states.PlayerState)(new StateGoalKick(this));
		stateCorner = (states.PlayerState)(new StateCorner(this));
		stateGoal = (states.PlayerState)(new StateGoal(this));
		stateShooting = (states.PlayerState)(new StateShooting(this));
		statePassing = (states.PlayerState)(new StatePassing(this));
		stateDribbling = (states.PlayerState)(new StateDribbling(this));
		stateRunWithBall = (states.PlayerState)(new StateRunWithBall(this));
		stateCrossing = (states.PlayerState)(new StateCrossing(this));
		stateClearing = (states.PlayerState)(new StateClearing(this));
		statePenaltyShootout = (states.PlayerState)(new StatePenaltyShootout(this));
		stateRunOffBall = (states.PlayerState)(new StateRunOffBall(this));
		stateHoldUpBall = (states.PlayerState)(new StateHoldUpBall(this));
		stateHasBall = new StateHasBall(this);
		stateKeeperHasBall = new StateKeeperHasBall(this, bold);
		stateKeeper = new StateKeeper(this, match, bold, pitch);
		
		state = stateNothing;
	}
	
	public Player(Settings settings, int topSpeed, Bold bold, Pitch pitch, PlayerRole role, Match match){

		this.match = match;
		this.role = role;
		this.pitch = pitch;
		this.bold = bold;
		this.topSpeed = topSpeed;
		this.settings = settings;
		shirtNumber = 0;
		speed = 0;
		acceleration = 50;
		shooting = 50;
		shotpower = 50;
		x = 50;
		y = 50;
		agility = 40;
		dribbling = 50;
		strength = 50;
		tackling = 50;
		reaction = 50;
		handling = 50;
		passing = 50;
		technique = 50;
		height = 180;
		jumping = 50;
		stamina = 50;
		shotstopping = 50;
		rushingout = 50;
		morale = 50;
		energy = 100;
		vision = 50;
		isKeeper = false;
		urgent = false;
	
	}

	public void setAllStatsTo50(){
		start_acceleration = 50;
		start_topSpeed = 100;
		start_dribbling = 50;
		start_strength = 50;
		start_tackling = 50;
		start_marking = 50;
		start_agility = 50;
		start_Energy = 50;
		start_reaction = 50;
		start_handling = 50;
		start_shooting = 50;
		start_shotpower = 50;
		start_passing = 50;
		start_technique = 50;
		start_vision = 50;
		start_jumping = 50;
		start_heading = 50;
		start_shotstopping = 50;
		start_commandofarea = 50;
		start_rushingout = 50;
		start_morale = 50;
		speed = 50;
		acceleration = 50;
		topSpeed = 100;
		dribbling = 50;
		strength = 50;
		tackling = 50;
		marking = 50;
		energy = 50;
		agility = 50;
		reaction = 50;
		handling = 50;
		shooting = 50;
		shotpower = 50;
		passing = 50;
		technique = 50;
		vision = 50;
		height = 50;
		jumping = 50;
		heading = 50;
		stamina = 50;
		shotstopping = 50;
		commandOfArea = 50;
		rushingout = 50;
		morale = 50;
	}
	
	public void checkTeammatesOffside(){

		resetOffside();

		//find linien ved bagerste forsvarer
		double offX = 0;

		//Hent modstanderne
		List<Player> sortedOpps = new ArrayList<Player>();
		for (Player p : oppTeam.getPlayers()){
			sortedOpps.add(p);
			//Forsvareres markeringer kan gå op til 30 pixels galt. 
			p.markErr.x = (r.nextInt(60) - 30) * (100 - (int)p.marking) / 100;
			p.markErr.y = (r.nextInt(60) - 30) * (100 - (int)p.marking) / 100;
		}

		//sorter
		Collections.sort(sortedOpps, new Comparator<Player>(){
			public int compare(Player p1, Player p2) {
				return (int)(Hjaelper.Distance(p2.getX(), p2.getY(), p2.getMyTeam().getGoalX(), p2.getMyTeam().getGoalY()) -
						Hjaelper.Distance(p1.getX(), p1.getY(), p1.getMyTeam().getGoalX(), p1.getMyTeam().getGoalY()));
			}});

		if (sortedOpps.size() > 2)
			offX = Math.abs(oppTeam.getGoalX() - sortedOpps.get(sortedOpps.size() - 2).getX());

		//de faar 8 pixels at loebe paa fordi prikkerne har en stoerrelse ogsaa
		for (Player p : myTeam.getPlayers()){
			if (Math.abs(oppTeam.getGoalX() - p.getX()) + 8 < offX){


				//man er ikke offside naar man er bag bolden
				if (Math.abs(oppTeam.getGoalX() - p.getX()) < Math.abs(oppTeam.getGoalX() - bold.getX()))
					p.setOffsideBy(Math.abs(offX - Math.abs(oppTeam.getGoalX() - p.getX())));

				//man er ikke offside paa egen halvdel
				if (myTeam.getGoalX() < oppTeam.getGoalX()){
					if (p.getX() < pitch.getPitchMidX())
						p.setOffsideBy(0);
				}
				else
					if (p.getX() > pitch.getPitchMidX())
						p.setOffsideBy(0);
			}

			//de 8 pixels de har faaet skal traekkes fra saa det bliver doemt det rigtige sted
			if (myTeam.getGoalX() < oppTeam.getGoalX())
				p.setOffsideX((int)p.getX() + 8);
			else
				p.setOffsideX((int)p.getX() - 8);

			p.setOffsideY((int)p.getY());
		}

		//jeg er ikke selv offside paa min egen aflevering
		offsideBy = 0;

		match.setOffsideX((int)offX);

	}

	public void resetOffside(){

		for (Player p : myTeam.getPlayers()){
			p.setOffsideBy(0);
			if (p != this)
				if (p.getPlayerAction() == PlayerAction.chasingBall || 
						p.getPlayerAction() == PlayerAction.receiving_pass || 
						p.getPlayerAction() == PlayerAction.goForBall ||
						p.getPlayerAction() == PlayerAction.dribble) 
					p.setPlayerAction(PlayerAction.nothing);
		}
		for (Player p : oppTeam.getPlayers()){
			p.setOffsideBy(0);
			if (p.getPlayerAction() == PlayerAction.chasingBall || 
					p.getPlayerAction() == PlayerAction.receiving_pass || 
					p.getPlayerAction() == PlayerAction.goForBall ||
					p.getPlayerAction() == PlayerAction.dribble) 
				p.setPlayerAction(PlayerAction.nothing);
		}
	}

	public double convertDistToPassPow(double dist, boolean high){
		double result = 25 + dist * 0.5 + Math.sqrt(dist);
		if (high) result *= 0.75;
		return result;
	}

	public double getMaxPassPow(){
		return 100 + (shotpower + passing) * 0.25;
	}

	private boolean klar(double vinkelPaaBold){

		boolean result = false;

		//hvis vinklen paa bolden er for stor er vi ikke klar
		if (match.getState() == MatchState.ON && vinkelPaaBold > Math.PI / 3){
			for (Player p : oppTeam.getPlayers())
				if (Hjaelper.Distance(x, y, p.getX(), p.getY()) < 20)
					result = true; // med mindre vi er presset - saa skal vi afsted med bolden
		}
		else // hvis vinklen paa bolden er lille er vi klar
			result = true;

		return result;
	}

	private void turnWithBall(Double tA){

		lastdribble = System.currentTimeMillis();

		double off = r.nextDouble() - r.nextDouble();

		a = tA;

		off *= 10 / technique;
		a += off;

		bold.setZForce(0);
		bold.setA(a);
		bold.setLastTouch(this);
		bold.setShot(false);
		bold.setSpeed(15);

	}

	private void updateTasks(){
		//Opdater command of area
		if (!isKeeper){
			if (!isInKeeperArea && Hjaelper.Distance(myTeam.getKeeper().x, myTeam.getKeeper().y, x, y) < 130){
				isInKeeperArea = true;
			}
			else if (isInKeeperArea && Hjaelper.Distance(myTeam.getKeeper().x, myTeam.getKeeper().y, x, y) >= 130){
				isInKeeperArea = false;

				marking = start_marking * ((100d + energy + 50d + morale) / 300d);
				vision = start_vision * ((100d + energy + 50d + morale) / 300d);
			}

			if (isInKeeperArea){
				vision = start_vision * ((100d + energy + 50d + morale) / 200d);
				vision = vision + ((commandOfArea - 9d)/10d) + ((100d - vision) / 12d);

//				Sysout.print(shirtNumber + " er indenfor rækkevidde. Start vision: " + start_vision + " - vision: " + vision);

				marking = start_marking * ((100d + energy + 50d + morale) / 200d);
				marking = marking + ((commandOfArea - 9d)/10d) + ((100d - marking) / 12d);
			}
		}
		
		targetSpeed = topSpeed;

		//Fjern complex actions hvis bolden er ude
		if (!match.state.equals(MatchState.ON) && complexActions.size() > 0)
			complexActions.clear();
		
		
		//opdater hvor fri han er for ham selv og alle andre spillere. Skal altid goeres
		freeFactor = 9999;
		for (Player p : oppTeam.getPlayers()){
			double d = Hjaelper.Distance(x, y, p.getX(), p.getY());
			if (d < freeFactor)
				freeFactor = d;
			if (freeFactor > 150)
				freeFactor = 150;
		}
	}
	
	public void update(){
		//Sysout.print("Update: " + shirtNumber + ") " + firstname + " " + lastname, "verbose");
		
		setPosition(myTeam, oppTeam);
		updateTasks();
		setPieceStates();
		
		if (wait > 0){
			wait -= 20;
			
			if (wait < 0)
				wait = 0;

			if (complexActions.size() > 0) complexActions.clear();
		}
		else{ //else if (System.currentTimeMillis() - lastUpdate > (800 - reaction * 4)){
			
			if (role.equals(PlayerRole.GK) && match.getState().equals(MatchState.ON))
				stateKeeper.update();
			else if (complexActions.size() > 0)
				complexActions.get(0).run();
			else{
				state.update();
			}
			
			lastUpdate = System.currentTimeMillis();
		}
		
//		if (Hjaelper.Distance(targetX, targetY, x, y) <= 15)
//			targetA = Math.atan2((bold.getX() - x), (bold.getY() - y));
//		else
//			targetA = Math.atan2((targetX - x), (targetY - y));
	}
	
	//Sætter states ved dødbolde og andre situationer hvor matchstate!=ON og sætter tilbage til nothing når matchstate=ON igen
	public void setPieceStates(){
		//Spillernes opforsel ved dodbolde og andet
		if (match.getState() == MatchState.HALF_TIME || match.getState() == MatchState.FINISHED){
			if (!state.equals(stateHTFT)) state = stateHTFT;
		}
		else if (match.getState() == MatchState.BEFORE_MATCH){
			if (!state.equals(stateBeforeMatch)) setState(stateBeforeMatch);
		}
		else if (match.getState() == MatchState.KICK_OFF){
			if (!state.equals(stateKickOff)) setState(stateKickOff);
		}
		else if (match.getState() == MatchState.THROW_IN){
			if (!state.equals(stateThrowIn)) setState(stateThrowIn);
		}
		else if (match.getState() == MatchState.FREE_KICK){
			if (!state.equals(stateFreeKick)) setState(stateFreeKick);		
		}
		else if (match.getState() == MatchState.PENALTY){
			if (!state.equals(statePenalty)) setState(statePenalty);
		}
		else if (match.getState() == MatchState.GOAL_KICK){
			if (!state.equals(stateGoalKick)) setState(stateGoalKick);
		}
		else if (match.getState() == MatchState.CORNER){
			if (!state.equals(stateCorner)) setState(stateCorner);			
		}
		else if (match.getState() == MatchState.GOAL){
			if (!state.equals(stateGoal)) setState(stateGoal);
		}
		
		if ((state.equals(stateBeforeMatch) || state.equals(stateKickOff) || state.equals(stateThrowIn) || state.equals(stateFreeKick) ||
				 state.equals(statePenalty) || state.equals(stateGoalKick) || state.equals(stateCorner) || state.equals(stateGoal)) && 
				 match.getState().equals(MatchState.ON))
			setState(stateNothing);
	}

	public void NoReact(){
		if (match.getState().equals(MatchState.ON)){
			bold.passTo = null;
			bold.cross = false;
			bold.shot = false;
			bold.setLastTouch(this);
			bold.setA(bold.getA() + 1 + ((r.nextDouble() * Math.PI * 2) - 2));
			bold.setSpeed(bold.getSpeed() * 0.7 *(r.nextDouble()));//så bolden mister fart
			if (bold.getSpeed() < 60) bold.setSpeed(60);//Så bolden ikke går i stå
			wait = 600 - (long)reaction * 3;
		}
	}
	
	private void checkPathToTarget(double x, double y, Team myTeam, Team oppTeam){

		if (System.currentTimeMillis() - lastPathCheck > 250){
			lastPathCheck = System.currentTimeMillis();
			
			double q;
			line.clear();
			double tempX = this.x, tempY = this.y;
			targetX = x;
			targetY = y;

			if (shirtNumber == 9){
				//			if (instructionNoBall != null) System.out.println(instructionNoBall.getAction());
				//			System.out.println("x: " + x);
				//			System.out.println("y: " + y);
				//			System.out.println("vision: " + vision);
			}

			q = Math.atan2((x - this.x), (y - this.y));
			tempX += 5 * Math.sin(q);
			tempY += 5 * Math.cos(q);
			double dist = Hjaelper.Distance(tempX, tempY, targetX, targetY);
			int limitWhile = 1000;
			while (Hjaelper.Distance(tempX, tempY, targetX, targetY) > 5 && Hjaelper.Distance(tempX, tempY, targetX, targetY) <= dist && limitWhile > 0){

				limitWhile--;

				q = Math.atan2((targetX - tempX), (targetY - tempY));
				tempX += 6 * Math.sin(q);
				tempY += 6 * Math.cos(q);
				dist = Hjaelper.Distance(tempX, tempY, targetX, targetY);
				line.add((int)tempX);
				line.add((int)tempY);

				for (Player p : myTeam.getPlayers()){
					if (p != this){
						if ((Hjaelper.Distance(tempX, tempY, p.getX(), p.getY()) < 10)){
							targetX = p.getX();
							targetY = p.getY();
							q = Math.atan2((targetX - this.x), (targetY - this.y));

							q -= Math.PI / 2;
							if (q > Math.PI * 2) q -= Math.PI * 2;
							else if(q < 0) q += Math.PI * 2;

							double t1x = targetX - 12 * Math.sin(q);
							double t1y = targetY - 12 * Math.cos(q);
							double t2x = targetX + 12 * Math.sin(q);
							double t2y = targetY + 12 * Math.cos(q);
							double d1 = Math.sqrt(Math.abs(t1x - x) * Math.abs(t1x - x) + Math.abs(t1y - y) * Math.abs(t1y - y));
							double d2 = Math.sqrt(Math.abs(t2x - x) * Math.abs(t2x - x) + Math.abs(t2y - y) * Math.abs(t2y - y));

							if (d1 < d2){
								targetX = t1x;
								targetY = t1y;
							}
							else{
								targetX = t2x;
								targetY = t2y;
							}

						}
					}
				}
				for (Player p : oppTeam.getPlayers()){
					if (p != this){
						if ((Hjaelper.Distance(tempX, tempY, p.getX(), p.getY()) < 10)){
							targetX = p.getX();
							targetY = p.getY();
							q = Math.atan2((targetX - this.x), (targetY - this.y));

							q -= Math.PI / 2;
							if (q > Math.PI * 2) q -= Math.PI * 2;
							else if(q < 0) q += Math.PI * 2;

							double t1x = targetX - 12 * Math.sin(q);
							double t1y = targetY - 12 * Math.cos(q);
							double t2x = targetX + 12 * Math.sin(q);
							double t2y = targetY + 12 * Math.cos(q);
							double d1 = Math.sqrt(Math.abs(t1x - x) * Math.abs(t1x - x) + Math.abs(t1y - y) * Math.abs(t1y - y));
							double d2 = Math.sqrt(Math.abs(t2x - x) * Math.abs(t2x - x) + Math.abs(t2y - y) * Math.abs(t2y - y));

							if (d1 < d2){
								targetX = t1x;
								targetY = t1y;
							}
							else{
								targetX = t2x;
								targetY = t2y;
							}
						}
					}
				}

				for (Player p : oppTeam.getPlayers()){
					if (Hjaelper.Distance(this.x, this.y, p.getX(), p.getY()) < 16 && Hjaelper.Distance(x, y, p.getX(), p.getY()) < Hjaelper.Distance(x, y, this.x, this.y)){

						if (Hjaelper.Distance(x, y, this.x, this.y) - Hjaelper.Distance(x, y, p.getX(), p.getY()) < 3 && 
								Hjaelper.Distance(this.x, this.y, p.getX(), p.getY()) > 12){

							targetX = x;
							targetX = y;
						}
						else{
							q = Math.atan2((p.getX() - this.x), (p.getY() - this.y));

							q -= Math.PI / 2;
							if (q > Math.PI * 2) q -= Math.PI * 2;
							else if(q < 0) q += Math.PI * 2;

							double t1x = p.getX() - 13 * Math.sin(q);
							double t1y = p.getY() - 13 * Math.cos(q);
							double t2x = p.getX() + 13 * Math.sin(q);
							double t2y = p.getY() + 13 * Math.cos(q);
							double d1 = Math.sqrt(Math.abs(t1x - x) * Math.abs(t1x - x) + Math.abs(t1y - y) * Math.abs(t1y - y));
							double d2 = Math.sqrt(Math.abs(t2x - x) * Math.abs(t2x - x) + Math.abs(t2y - y) * Math.abs(t2y - y));

							if (d1 < d2){
								targetX = t1x;
								targetY = t1y;
							}
							else{
								targetX = t2x;
								targetY = t2y;
							}
						}
					}
				}
			}
		}
	}

	//Push opposition players
	private void push(){
		//Skub
		for (Player p : oppTeam.players)
			if (Hjaelper.Distance(x, y, p.x, p.y) < 12){

				//Direction towards the opponent
				double dirToOpp = Math.atan2(p.x - x, p.y - y);
				
				//We only push if the opponent is in our way
				if (Math.abs(Hjaelper.angleDifference(dirToOpp, a)) < Math.PI / 2){
					playerMatchStats.updatesPushing++;
					
					double dir;
					double q;
					
					dir = ((p.strength+100)/2 + r.nextInt(15)) - ((strength+100)/2 + r.nextInt(15));
					if(p.getPlayerAction().equals(PlayerAction.dribble))//hvis modstanderen dribbler, har han lidt sværere ved at bruge al sin styrke/balance på at skubbe
						dir -= 5;
					else if(playerAction.equals(PlayerAction.dribble))
						dir += 5;
					
//					dir*= 0.5;
					
					if (dir > 0){
						q = Math.atan2(x-p.x, y-p.y);
						while(Hjaelper.Distance(x, y, p.x, p.y) < 11.5){
							x += Math.sin(q);
							y += Math.cos(q);
						}
					}
					else{
						q = Math.atan2(p.x-x, p.y-y);
						while(Hjaelper.Distance(x, y, p.x, p.y) < 11.5){
							p.x += Math.sin(q);
							p.y += Math.cos(q);
						}
					}
				}
			}
	}
	
	//If shadow == false: Turns accelerates and moves the player
	//returns array containing new speed, a (direction), x and y.
	//Shadow == true can be used for checking where the player would be if he moved now
	//faceBall = true means the player will keep facing the ball and move sidewards or backwards at limited speed if he has to
	public double[] move(boolean shadow, double speed, double a, double x, double y){

		double[] result = new double[4];

		result[0]= speed;
		result[1]= a;
		result[2]= x;
		result[3]= y;
		
		if (kasterSig){

			this.speed -= (200.0 - this.speed) / 35.0;
			//			Sysout.print(speed);
			this.x += ((settings.getSpeedmod()) * this.speed) * Math.sin(this.a) / 40;
			this.y += ((settings.getSpeedmod()) * this.speed) * Math.cos(this.a) / 40;

			if (this.speed < 5){
				kasterSig = false;
				wait = 250;
			}
		}
		else{
			if (!shadow){
				//Set target direction according to the target or the ball
				if (Hjaelper.Distance(targetX, targetY, x, y) > 15 && 
						!playerAction.equals(PlayerAction.dribble) &&
						!playerAction.equals(PlayerAction.chasingBall) &&
						!playerAction.equals(PlayerAction.goForBall) &&
						!playerAction.equals(PlayerAction.holdUpBall)){
					
					checkPathToTarget(targetX, targetY, myTeam, oppTeam);
					targetA = Math.atan2(targetX - x, targetY - y);
				}
				else if (!shadow && match.getState().equals(MatchState.ON)){
					checkPathToTarget(targetX, targetY, myTeam, oppTeam);
					targetA = Math.atan2(bold.getX() - x, bold.getY() - y);
				}
				else{
					targetA = Math.atan2(targetX - x, targetY - y);
				}
			}
		}
		
		if (!kasterSig || shadow){
			
			double dirToTarget = Math.atan2(targetX - x, targetY - y);
//			targetA = Math.atan2(targetX - x, targetY - y);

			if (shadow || complexActions.size() == 0 || 
					(complexActions.size() > 0 && 
							(complexActions.get(0).action.equals(Action.RUNTO) || complexActions.get(0).action.equals(Action.RUNAROUNDLEFT) || 
									complexActions.get(0).action.equals(Action.RUNAROUNDRIGHT)))){

				//Push opposition players
				if (!shadow) push();

				if (!shadow && (isKeeper || role == PlayerRole.GK)){
					//keeper har bolden
					if (bold.getHasBall() == this && match.getState().equals(MatchState.ON)){

						//find forsiden af keeperen
						double tempX = x + 5 * Math.sin(a);
						double tempY = y + 8 * Math.cos(a);

						//flyt bolden derhen hvis det ikke er inden i maalet
						if (!pitch.isOut((int)tempX, (int)tempY)){
							bold.setX((int)tempX);
							bold.setY((int)tempY);
						}
					}
				}

				if (!shadow && Hjaelper.Distance(x, y, bold.getX(), bold.getY()) <= 8 &&
						bold.getZ() < height){//t¾t nok pŒ bolden	

					if (bold.getHasBall() == null){

						if (offsideBy > 0){
							match.setSetPieceTeam(oppTeam);
							match.setSetPieceX(offsideX);
							match.setSetPieceY(offsideY);
							match.setState(MatchState.FREE_KICK);
						}

						//						if (!playerAction.equals(PlayerAction.dribble) || (System.currentTimeMillis() - bold.ballTimer > 650 - dribbling && System.currentTimeMillis() - lastUpdate > 650 - reaction))

						//Hvis vi er tæt nok på bolden og kan nå at reagere
						if (Hjaelper.Distance(x, y, bold.getX(), bold.getY()) < 8 && 
								(bold.getZ() < height || 
										(role.equals(PlayerRole.GK) && bold.getZ() < height + jumping + (height / 4)))){

							if  (System.currentTimeMillis() - bold.getBallTimer() > 500 - reaction * 2){
								if (role.equals(PlayerRole.GK))
									stateKeeper.update();
								else
									state.update();	
								lastUpdate = System.currentTimeMillis();
							}
							//hvis bolden rammer spilleren uden han kan nå at reagere
							else if (Hjaelper.Distance(x, y, bold.getX(), bold.getY()) < 6 && 
									bold.getZ() < height && bold.getLastTouch() != this){//hvis bolden er tæt på ham og bolden har ændret fart eller retning

								//The ball doesn't always hit the player
								if (r.nextDouble() < 0.12){
									resetOffside();
									NoReact();
								}
							}
						}
					}
				}

				//Hvor meget drejer jeg
				double dA = Math.abs(Hjaelper.angleDifference(a, dirToTarget));
				int topSpeedSidewards = (int)(topSpeed / 5);
				
				if (wait > 0){
					if (!shadow) speed -= ((acceleration / 8) + speed)/15;
				}
				else{
					double gammelA = a;

					if (targetA > Math.PI * 2) targetA -= Math.PI * 2;
					else if (targetA < 0) targetA += Math.PI * 2;

					double agilitymod = settings.getAgilitymod();
					double agilitymod2 = (agility+40) * 0.5;
					double maxTurn = agilitymod * (agilitymod2) * ((topSpeed - speed) / topSpeed);

					Sysout.print("shirtnumber: " + shirtNumber, "move");
					Sysout.print("maxTurn: " + maxTurn, "move");
					Sysout.print("topSpeed: " + topSpeed, "move");
					Sysout.print("speed: " + speed, "move");

					Sysout.print("dA: " + dA, "move");
					Sysout.print("acc: " + getCurrentAcceleration(speed), "move");

					if (dA > 0.05 && (dA < settings.getSharpTurnLimit() || speed < topSpeed / 7)){

						if (targetA < Math.PI){

							if (a > targetA){

								if (a - targetA < Math.PI){
									if (dA < maxTurn){
										a -= dA;
										if (!shadow) playerMatchStats.turnTotal += Math.abs(dA);
									}
									else{
										a -= maxTurn;
										if (!shadow) playerMatchStats.turnTotal += maxTurn;
									}
								}

								else{
									if (dA < maxTurn){
										a += dA;
										if (!shadow) playerMatchStats.turnTotal += Math.abs(dA);
									}
									else{
										a += maxTurn;
										if (!shadow) playerMatchStats.turnTotal += maxTurn;
									}
								}
							}
							else{
								if (dA < maxTurn){
									a += dA;
									if (!shadow) playerMatchStats.turnTotal += Math.abs(dA);
								}
								else{
									a += maxTurn;
									if (!shadow) playerMatchStats.turnTotal += maxTurn;
								}
							}
						}
						else{
							if (a < targetA){
								if (targetA - a < Math.PI){
									if (dA < maxTurn){
										a += dA;
										if (!shadow) playerMatchStats.turnTotal += Math.abs(dA);
									}
									else{
										a += maxTurn;
										if (!shadow) playerMatchStats.turnTotal += maxTurn;
									}
								}
								else{
									if (dA < maxTurn){
										a -= dA;
										if (!shadow) playerMatchStats.turnTotal += Math.abs(dA);
									}
									else{
										a -= maxTurn;
										if (!shadow) playerMatchStats.turnTotal += maxTurn;
									}
								}
							}
							else{
								if (dA < maxTurn){
									a -= dA;
									if (!shadow) playerMatchStats.turnTotal += Math.abs(dA);
								}
								else{
									a -= maxTurn;
									if (!shadow) playerMatchStats.turnTotal += maxTurn;
								}
							}
						}

					}
					else if(dA <= 0.05){
						a = dirToTarget;
					}

					if (a > Math.PI * 2) a -= Math.PI * 2;
					else if (a < 0) a += Math.PI * 2;

					//If it's a shadow move set a in the result and return the players real a to what it was before
					result[1]= a;
					if (shadow){
						a = gammelA;
					}
					else
						this.a = a;

					//////////////////////////Speed////////////////////////
					double oldSpeed = speed;

					if (Hjaelper.Distance(x, y, targetX, targetY) < ((settings.getSpeedmod()) * (speed / 5) + 4) / 38 && !playerAction.equals(PlayerAction.dribble)){
						targetSpeed = 0;
					}
					else if (targetSpeed > topSpeed){
						targetSpeed = topSpeed;
					}
					
					//Hvis jeg drejer mere end sharp turn limit og jeg enten skal løbe langt eller er i fart den forkerte vej saa stop og vend om
					if (dA > settings.getSharpTurnLimit() && (Hjaelper.Distance(x, y, targetX, targetY) >= 15 || speed > topSpeedSidewards)){
						targetSpeed = 0;
						if (!playerMatchStats.sharpTurnOn && !shadow) playerMatchStats.addSharpTurn();
					}
					else
						if (playerMatchStats.sharpTurnOn && !shadow) playerMatchStats.sharpTurnOn = false;

					//The player accelerates if he wants to run faster and he's not turning more than 60 degrees
					if (speed < targetSpeed && dA < Math.PI / 3){
						
						speed += getCurrentAcceleration(speed);
						//Sysout.print("2- " + lastname + " speed: " + speed + " acceleration: " + acceleration + " targetSpeed: " + targetSpeed);
					}
					else{
						//If the player isn't far from target and he's running slower than topSpeedSidewards and he isn't turning too much he can still accelerate
						if (Hjaelper.Distance(x, y, targetX, targetY) < 15 && speed < topSpeedSidewards && dA < settings.getSharpTurnLimit()){
							speed += getCurrentAcceleration(speed);
						}
					}

					if (speed > targetSpeed)
						speed -= (acceleration)/5 + 0.0015;

					if (speed < 0) speed = 0;

					double tempTopSpeed = topSpeed;

					if (dA > 0.2 && dA < settings.getSharpTurnLimit()){
						tempTopSpeed /= (dA * 0.6) + 1;
					}

					if (dA > 0.2 && Hjaelper.Distance(x, y, targetX, targetY) < 15 && dA < settings.getSharpTurnLimit()){
						tempTopSpeed = topSpeedSidewards;
						Sysout.print("sidestepping", "move");
					}
					
					Sysout.print("tempTopSpeed: " + tempTopSpeed, "move");

					if (speed < 0) speed = 0;
					if (speed > tempTopSpeed) speed = tempTopSpeed;

					//If it's a shadow move return the players real speed to what it was before
					result[0]= speed;
					if (shadow){
						speed = oldSpeed;
					}
					else
						this.speed = speed;
				}

				//				if (Hjaelper.Distance(targetX, targetY, x, y) > 5 || wait > 0){
				if (speed > 0.1){
					double gX = x;
					double gY = y;

					//If we're almost at our target don't run past it, just stand on it
					if (Hjaelper.Distance(x, y, targetX, targetY) < ((settings.getSpeedmod()) * (speed / 4) + 4) / 38 && !playerAction.equals(PlayerAction.dribble)){
						x = targetX;
						y = targetY;
					}
					else{
						//If the player isn't almost at his target but he's sidestepping - sidestep towards target.
						if (Hjaelper.Distance(x, y, targetX, targetY) < 15 && Hjaelper.Distance(x, y, targetX, targetY) > 2 &&
								(speed < topSpeedSidewards || Hjaelper.angleDifference(dirToTarget, a) < settings.getSharpTurnLimit())){
							
							x += ((settings.getSpeedmod()) * (speed + 7)) * Math.sin(dirToTarget) / 53;
							y += ((settings.getSpeedmod()) * (speed + 7)) * Math.cos(dirToTarget) / 53;
						}
						else{//If the player isn't sidestepping he runs in the direction he's facing 
							x += ((settings.getSpeedmod()) * (speed + 7)) * Math.sin(a) / 53;
							y += ((settings.getSpeedmod()) * (speed + 7)) * Math.cos(a) / 53;
						}
					}

					if (!shadow) playerMatchStats.cmsRun += Hjaelper.Distance(x, y, gX, gY) * 4;
					if ((double)speed / (double)topSpeed > 0.9 && !shadow) playerMatchStats.cmsRunAtSpeed += Hjaelper.Distance(x, y, gX, gY) * 4;

					result[2] = x;
					result[3] = y;
					//If it's a shadow move set x and y to old values
					if (shadow){
						x = gX;
						y = gY;
					}
					else{
						this.x = x;
						this.y = y;
					}
				}
				//				}

			}
		}
		return result;
	}
	
	public double getCurrentAcceleration(double currentSpeed){
		double speedLeft = topSpeed - currentSpeed;
		double result = ((speedLeft * 3) * (acceleration / 150)) / 70;

		return result;
	}
	
	//Metode til at få spillere til at løbe hurtigt derhen hvor de skal uden at tænke videre over det - f.eks. ud af banen ved udskiftning
	public void runBlind(){
		if (speed < topSpeed) 
			speed += ((acceleration/3))/100+0.0015;
		
		a = targetA;
		
		if (Hjaelper.Distance(targetX, targetY, x, y) > 8){
			if (speed > 0.1){
				double gX = x;
				double gY = y;

				x += ((settings.getSpeedmod()) * (100 + speed)) * Math.sin(a) / 53;
				y += ((settings.getSpeedmod()) * (100 + speed)) * Math.cos(a) / 53;

				playerMatchStats.cmsRun += Hjaelper.Distance(x, y, gX, gY) * 4;
				if ((double)speed / (double)topSpeed > 0.9) playerMatchStats.cmsRunAtSpeed += Hjaelper.Distance(x, y, gX, gY) * 4;
			}
		}
	}
	
	public int getXIn(int frames){
		return	(int)(x + (((settings.getSpeedmod()) * (speed + 7)) * Math.sin(a) / 53) * frames);
	}

	public int getYIn(int frames){
		return	(int)(y + (((settings.getSpeedmod()) * (speed + 7)) * Math.cos(a) / 53) * frames);
	}

//	public void controlledMove(){
//
//		if (Hjaelper.Distance(x, y, bold.getX(), bold.getY()) < 8 &&
//				bold.getZ() < height){//t¾t nok pŒ bolden	
//
//			if (match.getState() == MatchState.ON){
//				double temX, temY;
//
//				temX = x + (Math.sin(a) * 0.01);
//				temY = y + (Math.cos(a) * 0.01);
//			}
//
//			if (bold.getHasBall() == null){
//
//				if (offsideBy > 0){
//					match.setSetPieceTeam(oppTeam);
//					match.setSetPieceX(offsideX);
//					match.setSetPieceY(offsideY);
//					match.setState(MatchState.FREE_KICK);
//				}
//
//			}
//		}
//
//		if (targetSpeed > topSpeed) targetSpeed = topSpeed;
//
//		if (speed < targetSpeed) 
//			speed += ((topSpeed - speed) + (acceleration/6))/65+0.001;//skal gøre at man accelererer langsommere jo højere fart man er i
//		else if (speed > targetSpeed)
//			speed -= ((topSpeed - speed) + acceleration)/20+0.001;
//		if (speed < 0) speed = 0;
//
//		if (speed > 0.1){
//			x += ((settings.getSpeedmod())) + speed * Math.sin(a) / 40;
//			y += ((settings.getSpeedmod())) + speed * Math.cos(a) / 40;
//		}
//	}


	public void moveGKOld(Team myTeam, Team oppTeam, int goalX, int goalY){

		if (wait < 1) {
			
			
			if (!kasterSig){
				if (speed < 0) speed = 0;
				if (speed > topSpeed) speed = topSpeed;

				//Hvis vi ikke er ret taet paa target saa flyt derhen imod
				if (Hjaelper.Distance(targetX, targetY, x, y)>2){

					if (wait > 0){
						speed -= ((topSpeed - speed) + acceleration)/50;
					}
					else{
						double gammelA = a;

						if (targetA > Math.PI * 2) targetA -= Math.PI * 2;
						else if (targetA < 0) targetA += Math.PI * 2;

						double agilitymod = settings.getAgilitymod();
						double agilitymod2 = agility+40;

						if (Math.abs(targetA - a) > 0.2){

							if (targetA < Math.PI){

								if (a > targetA){

									if (a - targetA < Math.PI){
										a -= agilitymod * (agilitymod2);
									}

									else{
										a += agilitymod * agilitymod2;
									}
								}
								else{
									a += agilitymod * agilitymod2;
								}
							}
							else{
								if (a < targetA){
									if (targetA - a < Math.PI){
										a += agilitymod * agilitymod2;
									}
									else{
										a -= agilitymod * agilitymod2;
									}
								}
								else{
									a -= agilitymod * agilitymod2;
								}
							}

						}


						if (a > Math.PI * 2) a -= Math.PI * 2;
						else if (a < 0) a += Math.PI * 2;

						if (targetSpeed > topSpeed) targetSpeed = topSpeed;

						//speed += acceleration/20+0.001;
						if (speed < targetSpeed) 
							speed += getCurrentAcceleration(speed);//skal gøre at man accelererer langsommere jo højere fart man er i
						else if (speed > targetSpeed)
							speed -= ((topSpeed - speed) + acceleration)/70+0.001;
						if (speed < 0) speed = 0;

						double tempX, tempY;

						tempX = x + (Math.sin(a) * 0.01);
						tempY = y + (Math.cos(a) * 0.01);

						double tempTopSpeed = topSpeed;

						//Hvor meget drejer jeg
						double dA = Hjaelper.angleDifference(a, targetA);
						//Hvis jeg drejer mere end sharp turn limit saa stop og vend om
						if (dA > settings.getSharpTurnLimit()){
							speed = 0;
						}//ellers gaa ned i fart afhaengig af hvor meget jeg skal dreje
						else if (Hjaelper.angleDifference(a, targetA) > 0.5){
							tempTopSpeed /= Hjaelper.angleDifference(a, targetA) * 2;
						}

						if (speed < 0) speed = 0;
						if (speed > tempTopSpeed) speed = tempTopSpeed;

					}

					if (Hjaelper.Distance(targetX, targetY, x, y) > 3 || wait > 0){
						if (speed > 0.1){
							double gX = x;
							double gY = y;

							if (Hjaelper.Distance(x, y, targetX, targetY) > 10){
								x += ((settings.getSpeedmod()) * (50 + speed)) * Math.sin(targetA) / 40;
								y += ((settings.getSpeedmod()) * (50 + speed)) * Math.cos(targetA) / 40;
							}
							else{
								double ma = Math.atan2((targetX - x), (targetY - y));
								x += ((settings.getSpeedmod()) * (50 + speed)) * Math.sin(ma) / 100;
								y += ((settings.getSpeedmod()) * (50 + speed)) * Math.cos(ma) / 100;
								//								Sysout.print(Math.abs(((settings.getSpeedmod())) + speed * Math.cos(ma) / 100) + " - " + Math.abs(((settings.getSpeedmod())) + speed * Math.cos(ma) / 40));
							}

							playerMatchStats.cmsRun += Hjaelper.Distance(x, y, gX, gY) * 4;
						}
					}



					////Acceleration////
					//			//if (speed < topSpeed) speed += ((topSpeed - speed) + (acceleration/6))/40+0.001;

					//			if (speed > 0.1){
					//				x += ((settings.getSpeedmod())) + speed * Math.sin(targetA) / 40;
					//				y += ((settings.getSpeedmod())) + speed * Math.cos(targetA) / 40;
					//			}
				}
				else{//hvis vi er tæt på target
					targetSpeed = 0;
				}

			}

			//		//double tempTopSpeed = topSpeed;

			//		
			//		//Hvor meget drejer jeg
			//		double dA = Hjaelper.angleDifference(a, targetA);
			//		//Hvis jeg drejer mere end sharp turn limit saa stop og vend om
			//		if (dA > settings.getSharpTurnLimit()){
			//			speed = 0;
			//		}//ellers gaa ned i fart afhaengig af hvor meget jeg skal dreje
			//		else if (Hjaelper.angleDifference(a, targetA) > 0.5){
			//			tempTopSpeed /= Hjaelper.angleDifference(a, targetA) * 2;
			//		}

			//		if (speed < 0) speed = 0;
			//		if (speed > tempTopSpeed) speed = tempTopSpeed;
			//
			//		if (wait > 0){
			//			speed -= ((topSpeed - speed) + acceleration)/20+0.001;
			//		}
			//
			//		if (Hjaelper.Distance(targetX, targetY, x, y) > 4 || wait > 0){
			//			if (speed > 0.1){
			//				x += ((settings.getSpeedmod())) + speed * Math.sin(targetA) / 40;
			//				y += ((settings.getSpeedmod())) + speed * Math.cos(targetA) / 40;
			//			}
			//		}
		}

		if (kasterSig){

			speed -= (200 - speed) / 35;
			//			Sysout.print(speed);
			x += ((settings.getSpeedmod()) * speed) * Math.sin(a) / 40;
			y += ((settings.getSpeedmod()) * speed) * Math.cos(a) / 40;

			if (speed < 5){
				kasterSig = false;
				wait = 750;
			}
		}

	}	

	public void setPosition(Team myTeam, Team oppTeam){
		posX = startPosX;
		posY = startPosY;

		Sysout.print("Setpos shirtnumber: " + shirtNumber, "setposition");
		Sysout.print("startPosX: " + startPosX, "setposition");
		
		if (!this.isKeeper){
			//plus er hvor meget ekstra der skal traekkes frem eller tilbage. Afhaengig af hvor bolden er i forhold til eget mål
			double plus = (Math.abs(bold.getX() - myTeam.goalX));
			Sysout.print("plus1: " + plus, "setposition");
			
			if (plus > 1) plus = Math.log(plus) * 10;
			Sysout.print("plus2: " + plus, "setposition");
			
			if ((bold.getHasBall() != null && bold.getHasBall().isKeeper) || match.getState().equals(MatchState.GOAL_KICK)){
				//If the keeper has the ball we can push up a little
				plus += 50;
			}
			Sysout.print("plus3: " + plus, "setposition");
			plus *= ((double)mentality / 70.0);
			Sysout.print("plus4: " + plus, "setposition");
			plus *= 1.16;
			if (myTeam.getGoalX() < oppTeam.getGoalX()){

				if (myTeam.getTeamState() == TeamState.defend){
//					posX /= 1.4;
					posX += plus;  
					posY = startPosY;
				}
				else if (myTeam.getTeamState() == TeamState.mid){
//					posX += 10;
					posX += plus * 1.8;
					posY = startPosY;
				}
				else if (myTeam.getTeamState() == TeamState.attack){
					posX += 100;
					posX += plus * 4;  
					posY = startPosY;
				}
			}
			else {

				if (myTeam.getTeamState() == TeamState.defend){
//					posX *= 1.4;
					posX -= plus; 
					posY = startPosY;
				}
				else if (myTeam.getTeamState() == TeamState.mid){
//					posX -= 10;
					posX -= plus * 1.8;
					posY = startPosY;
				}
				else if (myTeam.getTeamState() == TeamState.attack){
					posX -= 100;
					posX -= plus * 4;  
					posY = startPosY;
				}
			}

			Point p = pitch.stayOnPitch((int)posX, (int)posY);
			posX = p.x;
			posY = p.y;


			//faa folk med frem hvis vi har bolden og den er fremme på banen
			if (match.getState() == MatchState.ON && bold.getLastTouch() != null && 
					bold.getLastTouch() != this && bold.getLastTouch().getMyTeam() == myTeam){

				if (Math.abs(oppTeam.getGoalX() - bold.getX()) < 320 && 
						!bold.udenfor &&
						Math.abs(pitch.pitchHeight / 2 + pitch.pitchPosY - bold.y) > 100){

					//The defenders don't rush forward here (they can always chose to make a forward run if the want to)
					if(Math.abs(myTeam.getBackLine() - posX) > 100){
						//løb med frem
						double distToGoal;
						if (myTeam.getGoalX() < oppTeam.getGoalX()){
							distToGoal = Hjaelper.Distance(posX, 0, oppTeam.goalX, 0) - 64;
							if(distToGoal < 1) distToGoal = 1;
//							System.out.println("HEEEEEEEEEEEEUNIWUKNEWOUNHEWUOH: " + distToGoal);
//							System.out.println(this.firstname + " " + this.lastname);
							posX = oppTeam.getGoalX() - 50 - r.nextInt(20);
						}
						else{
							distToGoal = Hjaelper.Distance(posX, 0, oppTeam.goalX, 0) - 64;
							if(distToGoal < 1) distToGoal = 1;
//							System.out.println("HEEEEEgegfewgwgwgwgeefEWOUNHEWUOH: " + distToGoal);
//							System.out.println(this.firstname + " " + this.lastname);
							posX = oppTeam.getGoalX() + 50 + r.nextInt(20);
						}
					}
				}
			}
			
			//Don't move too far away from the ball, unless the keeper has it, then it is ok to move up the pitch
			if(Hjaelper.Distance(posX, 0, bold.x, 0) > 300 && match.getState() == MatchState.ON && (bold.getHasBall() != null && !bold.getHasBall().isKeeper)){
				if(posX < bold.x) posX = bold.x - 300;
				else if(posX > bold.x) posX = bold.x + 300;
			}
			if(bold.getTeamHasBall() != myTeam && bold.getTeamHasBall() != null && Hjaelper.Distance(posY, 0, bold.y, 0) > 285 && match.getState() == MatchState.ON){
				if(posY < bold.y) posY = bold.y - 285;
				else if(posY > bold.y) posY = bold.y + 285;
			}
			
			/*			
			//hold en hvis defensiv linie i forsvaret
			if (role.equals(PlayerRole.CB) || role.equals(PlayerRole.SB) || role.equals(PlayerRole.SW) ||
					role.equals(PlayerRole.WB)){
//				if (myTeam.teamState != null && myTeam.teamState.equals(TeamState.attack))
//					if (myTeam.goalX < oppTeam.goalX) posX = startPosX + 200 + defensiveLine * 80;
//					else posX = startPosX - 200 - defensiveLine * 80;
//				else
//					if (myTeam.goalX < oppTeam.goalX) posX = startPosX + defensiveLine * 80;
//					else posX = startPosX - defensiveLine * 80;
//			
			}
			//angrebet laegger sig saa langt fremme forsvaret tillader
			else{
				if (myTeam.teamState != null && myTeam.teamState.equals(TeamState.attack) && myTeam.frontPlayer != null)

					//forreste spiller helt oppe paa offsidelinien
					if (myTeam.frontPlayer.equals(this)){
						if (myTeam.getGoalX() < oppTeam.getGoalX())
							posX = oppTeam.getGoalX() - oppTeam.getOffsideDistToGoal();
						else
							posX = oppTeam.getGoalX() + oppTeam.getOffsideDistToGoal();
					}
					//resten efter opstilling bagved
					else{
						if (myTeam.getGoalX() < oppTeam.getGoalX()){
							double f = (Math.abs(startPosX - myTeam.frontPlayer.startPosX)) * Math.abs(myTeam.backPlayer.posX - myTeam.frontPlayer.posX) / Math.abs(myTeam.backPlayer.startPosX - myTeam.frontPlayer.startPosX);
							posX = oppTeam.getGoalX() - oppTeam.getOffsideDistToGoal() - f;
						}
						else{
							double f = (Math.abs(startPosX - myTeam.frontPlayer.startPosX)) * Math.abs(myTeam.backPlayer.posX - myTeam.frontPlayer.posX) / Math.abs(myTeam.backPlayer.startPosX - myTeam.frontPlayer.startPosX);
							posX = oppTeam.getGoalX() + oppTeam.getOffsideDistToGoal() + f;
						}
					}

			}
		}
			 */
			
			//Ikke noget med at klistre sig til baglinien - eller løber ud af banen
			if (Hjaelper.Distance(posX, 0, oppTeam.goalX, 0) < 50)
				if (myTeam.getGoalX() < oppTeam.getGoalX())
					posX = oppTeam.goalX - 50;
				else
					posX = oppTeam.goalX + 50;
			
			//ikke offside
			if (match.state != MatchState.THROW_IN){
				if (Hjaelper.Distance(posX, 0, oppTeam.getGoalX(), 0) < oppTeam.getOffsideDistToGoal() &&
						Math.abs(oppTeam.goalX - bold.x) > Math.abs(oppTeam.goalX - posX) &&
						playerAction != PlayerAction.receiving_pass && playerAction != PlayerAction.chasingBall
						&& playerAction != PlayerAction.dribble && bold.getLastTouch() != this){
					
					if (myTeam.getGoalX() < oppTeam.getGoalX()){
						if (oppTeam.getOffsideDistToGoal() > Math.abs(oppTeam.goalX - bold.x)){
							posX = oppTeam.getGoalX() - Math.abs(oppTeam.goalX - bold.x);
						}
						else
							posX = oppTeam.getGoalX() - oppTeam.getOffsideDistToGoal();
					}
					else{
						if (oppTeam.getOffsideDistToGoal() > Math.abs(oppTeam.goalX - bold.x)){
							posX = oppTeam.getGoalX() + Math.abs(oppTeam.goalX - bold.x);
						}
						else
							posX = oppTeam.getGoalX() + oppTeam.getOffsideDistToGoal();
					}
						
				}
			}
			
			//og ikke længere tilbage end offsidelinien

			
			//medmindre en modspiller er igennem
			if (Math.abs(posX - myTeam.goalX) < myTeam.getBackLine()){
//				if(myTeam.backPlayer == this){
//					myTeam.calculateBackline();
//				}
				
				//Hvis ham der kan komme først til bolden er modstander og han møder bolden bag offsidelinien er vi nødt til at bakke ned bag linien
				if (bold.firstToBall.myTeam.equals(oppTeam) && Math.abs(bold.firstToBallPoint.x - myTeam.getGoalX()) < myTeam.getBackLine()){
					//ingenting fordi vi placerer os som vi alligevel ville 
					
				}
				else{ //ingen modstander kan komme ind bag offsidelinien så vi holder den

					posX = myTeam.goalX + myTeam.getBackLine();
					if (myTeam.goalX > pitch.getPitchMidX())
						posX = myTeam.goalX - myTeam.getBackLine();

					posX += markErr.x;
				}
			}
		}
	}
	
	public void updateEnergyAndMorale(){
		//opdater energiniveau efter hvor langt spilleren har løbet 
		energy = start_Energy - (playerMatchStats.getMetersRun() * 0.01d) * ((100d - stamina) / 100d);
		if (energy < 0) energy = 0;

		//opdater evner efter energiniveau
		
		//How much does morale affect stats?
		double moraleFactor = (100d + morale) / 150d;
		if (moraleFactor > 1.1) moraleFactor = 1.1;
		
		//The energyFactor is used to drop stats directly based on energy
		double energyFactor = (80d + energy) / 180d;
		
		//The actual boost (or the opposite) to stats from morale
		double moraleBoost = 40d * moraleFactor - 40d;
		
		shooting = start_shooting * energyFactor + moraleBoost;
		passing = start_passing * energyFactor + moraleBoost;
		tackling = start_tackling * energyFactor + moraleBoost;
		vision = start_vision * energyFactor + moraleBoost;
		topSpeed = start_topSpeed * energyFactor + moraleBoost;
		agility = start_agility * energyFactor + moraleBoost;
		acceleration = start_acceleration * energyFactor + moraleBoost;
		dribbling = start_dribbling * energyFactor + moraleBoost;
		handling = start_handling * energyFactor + moraleBoost;
		heading = start_heading * energyFactor + moraleBoost;
		jumping = start_jumping * energyFactor + moraleBoost;
		marking = start_marking * energyFactor + moraleBoost;
		reaction = start_reaction * energyFactor + moraleBoost;
		shotpower = start_shotpower * energyFactor + moraleBoost;
		strength = start_strength * energyFactor + moraleBoost;
		technique = start_technique * energyFactor + moraleBoost;
		commandOfArea = start_commandofarea * energyFactor + moraleBoost;

//		if (Settings.SO){
//			Sysout.print(lastname + " har løbet " + playerMatchStats.getMetersRun() + " meter og har " + energy + " energi efter at have startet med " + start_Energy + ". (stamina: " + stamina + ")");
//			Sysout.print(lastname + " har " + topSpeed + " i speed efter at have startet med " + start_topSpeed);
//		}
	}

	public double getX(){
		return x;
	}

	public void setX(double x){
		this.x = x;
	}
	public void setY(double y){
		this.y = y;
	}
	public double getSpeed(){
		return speed;
	}
	public void setSpeed(int s){
		this.speed = s;
	}
	public double getY(){
		return y;
	}
	/**
	 * @return a number from 0-6.2 or thereabouts
	 */
	public double getA(){
		return a;
	}
	public void setA(double a){
		this.a = a;
	}
	public double getTargetX(){
		return targetX;
	}
	public double getTargetY(){
		return targetY;
	}
	public void setTargetY(double targetY) {
		this.targetY = targetY;
	}
	public void setTargetX(double targetX) {
		this.targetX = targetX;
	}
	public ArrayList<Integer> getLine(){
		return line;
	}
	public void setSettings(Settings settings){
		this.settings = settings;
	}
	public int getShirtNumber() {
		return shirtNumber;
	}
	public void setShirtNumber(int shirtNumber) {
		this.shirtNumber = shirtNumber;
	}
	public boolean isKeeper() {
		if (getRole() == PlayerRole.GK)
			isKeeper = true;
		else isKeeper = false;
		return isKeeper;
	}
	public void setKeeper(boolean isKeeper) {
		this.isKeeper = isKeeper;
	}
	public double getStartPosX() {
		return startPosX;
	}
	public void setStartPosX(double startPosX) {
		this.startPosX = startPosX;
	}
	public double getStartPosY() {
		return startPosY;
	}
	public void setStartPosY(double startPosY) {
		this.startPosY = startPosY;
	}
	public int getDtx() {
		return dtx;
	}
	public int getDty() {
		return dty;
	}

	public PlayerAction getPlayerAction() {
		return playerAction;
	}
	public void setPlayerAction(PlayerAction playerAction) {
		this.playerAction = playerAction;
//		System.out.println(shirtNumber + ": " + playerAction);
	}
	public void setMyTeam(Team myTeam) {
		this.myTeam = myTeam;
	}
	public void setOppTeam(Team oppTeam) {
		this.oppTeam = oppTeam;
	}
	public String toString(){
		return shirtNumber + " - " + firstname + " " + lastname;
	}

	//Attributter

	public double getTopSpeed() {/////////////////TopSpeed
		return topSpeed;	}

	public byte getCrossball() {
		return crossball;
	}


	public void setCrossball(byte crossball) {
		this.crossball = crossball;
	}

	public void setTopSpeed(double topSpeed) {
		this.topSpeed = topSpeed;	}
	public void setTopSpeed(int s){
		this.topSpeed = s;
	}
	public double getDribbling() {/////////////////Dribbling
		return dribbling;	}
	public void setDribbling(double dribbling) {
		this.dribbling = dribbling;	}
	public void setDribbling(int d){
		this.dribbling = d;
	}
	public double getAgility() {/////////////////Agility
		return agility;	}
	public void setAgility(double agility) {
		this.agility = agility;
	}	
	public double getShooting() {/////////////////Shooting
		return shooting;	}
	public void setShooting(double shooting) {
		this.shooting = shooting;
	}
	public double getShotpower() {/////////////////Shotpower
		return shotpower;	}
	public void setShotpower(double shotpower) {
		this.shotpower = shotpower;
	}
	public double getAcceleration() {/////////////////Acceleration
		return acceleration;	}
	public void setAcceleration(double s){
		this.acceleration = s;
	}	
	public double getPassing() {/////////////////Passing
		return passing;	}
	public void setPassing(double passing) {
		this.passing = passing;
	}
	public double getTechnique() {/////////////////Technique
		return technique;	}
	public void setTechnique(double technique) {
		this.technique = technique;
	}
	public double getStrength(){/////////////////Strength
		return strength;	}
	public void setStrength(double strength) {
		this.strength = strength;	
	}
	public double getTackling(){/////////////////Tackling
		return tackling;	}
	public void setTackling(double tackling) {
		this.tackling = tackling;
	}
	public double getHandling() {/////////////////Handling
		return handling;	}
	public void setHandling(double handling) {
		this.handling = handling;
	}
	public double getReaction() {/////////////////Reaction
		return reaction;	}
	public void setReaction(double reaction) {
		this.reaction = reaction;
	}
	public double getVision() {/////////////////Vision
		return vision;	}
	public void setVision(double vision) {
		this.vision = vision;
	}
	public double getHeight(){/////////////////Height
		return height;	}
	public void setHeight(double height){
		this.height = height;
	}
	public double getJumping(){/////////////////Jumping
		return jumping;	}
	public void setJumping(double jumping){
		this.jumping = jumping;
	}
	public double getMarking(){/////////////////Marking
		return marking;	}
	public void setMarking(double marking){
		this.marking = marking;
	}
	
	public double getStart_rushingout() {
		return start_rushingout;
	}

	public void setStart_rushingout(double start_rushingout) {/////////////////Rushing Out
		this.start_rushingout = start_rushingout;
	}

	public double getRushingout() {
		return rushingout;
	}

	public void setRushingout(double rushingout) {
		this.rushingout = rushingout;
	}

	public boolean isHasBall() {
		return hasBall;
	}

	public void setHasBall(boolean hasBall) {
		this.hasBall = hasBall;
	}

	public Team getOppTeam() {
		return oppTeam;
	}

	public Team getMyTeam() {
		return myTeam;
	}

	public PlayerRole getRole(){
		return role;
	}

	public long getWait() {
		return wait;
	}

	public void setWait(long wait) {
		this.wait = wait;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getName(){
		return firstname + " " + lastname;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Bold getBold() {
		return bold;
	}

	public void setBold(Bold bold) {
		this.bold = bold;
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

	public void setRole(PlayerRole role) {
		this.role = role;
		if (role.equals(PlayerRole.GK))
			isKeeper = true;
		else 
			isKeeper = false;
	}

	public Pitch getPitch() {
		return pitch;
	}

	public void setPitch(Pitch pitch) {
		this.pitch = pitch;
	}

	public int getRuns() {
		return runs;
	}

	public void setRuns(byte runs) {
		this.runs = runs;
	}

	public int getPrevX() {
		return prevX;
	}

	public void setPrevX(int prevX) {
		this.prevX = prevX;
	}

	public int getPrevY() {
		return prevY;
	}

	public void setPrevY(int prevY) {
		this.prevY = prevY;
	}

	public ArrayList<Player> getCurrentlyMarking() {
		return currentlyMarking;
	}

	public byte getDribble() {
		return dribble;
	}

	public void setDribble(byte dribble) {
		this.dribble = dribble;
	}

	public byte getThroughballs() {
		return throughballs;
	}

	public void setThroughballs(byte throughballs) {
		this.throughballs = throughballs;
	}

	public byte getLongshots() {
		return longshots;
	}

	public void setLongshots(byte longshots) {
		this.longshots = longshots;
	}

	public byte getAggression() {
		return aggression;
	}

	public void setAggression(byte aggression) {
		this.aggression = aggression;
	}

	public byte getMentality() {
		return mentality;
	}

	public void setMentality(byte mentality) {
		this.mentality = mentality;
	}

	public byte getClosingdown() {
		return closingdown;
	}

	public void setClosingdown(byte closingdown) {
		this.closingdown = closingdown;
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public double getOffsideBy() {
		return offsideBy;
	}

	public void setOffsideBy(double offsideBy) {
		this.offsideBy = offsideBy;
	}

	public int getOffsideX() {
		return offsideX;
	}

	public void setOffsideX(int offsideX) {
		this.offsideX = offsideX;
	}

	public int getOffsideY() {
		return offsideY;
	}

	public void setOffsideY(int offsideY) {
		this.offsideY = offsideY;
	}

	public double getTargetSpeed() {
		return targetSpeed;
	}

	public void setTargetSpeed(double targetSpeed) {
		this.targetSpeed = targetSpeed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getHeading() {
		return heading;
	}

	public void setHeading(double heading) {
		this.heading = heading;
	}

	public double getStamina() {
		return stamina;
	}

	public void setStamina(double stamina) {
		this.stamina = stamina;
	}
	
	public double getShotstopping() {
		return shotstopping;
	}

	public void setShotstopping(double shotstopping) {
		this.shotstopping = shotstopping;
	}
	
	public byte getTightmarking() {
		return tightmarking;
	}

	public void setTightmarking(byte tightmarking) {
		this.tightmarking = tightmarking;
	}


	public byte getDefensiveLine() {
		return defensiveLine;
	}

	public void setDefensiveLine(byte defensiveLine) {
		this.defensiveLine = defensiveLine;
	}

	public byte getPressing() {
		return pressing;
	}

	public void setPressing(byte pressing) {
		this.pressing = pressing;
	}

	public double getFreeFactor(){
		return freeFactor;
	}

	public byte getShortLongPassing() {
		return shortLongPassing;
	}

	public void setShortLongPassing(byte shortLongPassing) {
		this.shortLongPassing = shortLongPassing;
	}

	public String getMatchMessage() {
		return matchMessage;
	}
	public String getMatchMessage2() {
		return matchMessage2;
	}

	public void setMatchMessage(String matchMessage) {
		if(this.matchMessage != ""){
			matchMessage2 = this.matchMessage;
		}
			this.matchMessage = lastname + " " + matchMessage;
			match.addCommentary(lastname + " " + matchMessage);
	}

	public double getTargetA() {
		return targetA;
	}

	public void setTargetA(double targetA) {
		this.targetA = targetA;
	}

	public boolean isForwardOnSetpieces() {
		return forwardOnSetpieces;
	}

	public void setForwardOnSetpieces(boolean forwardOnSetpieces) {
		this.forwardOnSetpieces = forwardOnSetpieces;
	}

	public PlayerMatchStats getPlayerMatchStats() {
		return playerMatchStats;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public double getStart_acceleration() {
		return start_acceleration;
	}

	public void setStart_acceleration(double startAcceleration) {
		start_acceleration = startAcceleration;
	}

	public double getStart_topSpeed() {
		return start_topSpeed;
	}

	public void setStart_topSpeed(double startTopSpeed) {
		start_topSpeed = startTopSpeed;
	}

	public double getStart_dribbling() {
		return start_dribbling;
	}

	public void setStart_dribbling(double startDribbling) {
		start_dribbling = startDribbling;
	}

	public double getStart_strength() {
		return start_strength;
	}

	public void setStart_strength(double startStrength) {
		start_strength = startStrength;
	}

	public double getStart_tackling() {
		return start_tackling;
	}

	public void setStart_tackling(double startTackling) {
		start_tackling = startTackling;
	}

	public double getStart_marking() {
		return start_marking;
	}

	public void setStart_marking(double startMarking) {
		start_marking = startMarking;
	}

	public double getStart_agility() {
		return start_agility;
	}

	public void setStart_agility(double startAgility) {
		start_agility = startAgility;
	}

	public double getStart_Energy() {
		return start_Energy;
	}

	public void setStart_Energy(double startEnergy) {
		start_Energy = startEnergy;
	}

	public double getStart_reaction() {
		return start_reaction;
	}

	public void setStart_reaction(double startReaction) {
		start_reaction = startReaction;
	}

	public double getStart_handling() {
		return start_handling;
	}

	public void setStart_handling(double startHandling) {
		start_handling = startHandling;
	}

	public double getStart_shooting() {
		return start_shooting;
	}

	public void setStart_shooting(double startShooting) {
		start_shooting = startShooting;
	}

	public double getStart_shotpower() {
		return start_shotpower;
	}

	public void setStart_shotpower(double startShotpower) {
		start_shotpower = startShotpower;
	}

	public double getStart_passing() {
		return start_passing;
	}

	public void setStart_passing(double startPassing) {
		start_passing = startPassing;
	}

	public double getStart_technique() {
		return start_technique;
	}

	public void setStart_technique(double startTechnique) {
		start_technique = startTechnique;
	}

	public double getStart_vision() {
		return start_vision;
	}

	public void setStart_vision(double startVision) {
		start_vision = startVision;
	}

	public double getStart_jumping() {
		return start_jumping;
	}

	public void setStart_jumping(double startJumping) {
		start_jumping = startJumping;
	}

	public double getStart_heading() {
		return start_heading;
	}

	public void setStart_heading(double startHeading) {
		start_heading = startHeading;
	}
	
	public double getStart_shotstopping() {
		return start_shotstopping;
	}

	
	
	public double getStart_commandofarea() {
		return start_commandofarea;
	}

	public void setStart_commandofarea(double startCommandofarea) {
		start_commandofarea = startCommandofarea;
	}

	public double getCommandOfArea() {
		return commandOfArea;
	}

	public void setCommandOfArea(double commandOfArea) {
		this.commandOfArea = commandOfArea;
	}

	public void setStart_shotstopping(double start_shotstopping) {
		this.start_shotstopping = start_shotstopping;
	}
	
	public int getCountToBall() {
		return countToBall;
	}

	public void setCountToBall(int countToBall) {
		this.countToBall = countToBall;
	}

	public long getLastdribble() {
		return lastdribble;
	}

	public void setLastdribble(long lastdribble) {
		this.lastdribble = lastdribble;
	}

	public StateKeeper getStateKeeper() {
		return (StateKeeper) stateKeeper;
	}

	public StateKeeperHasBall getStateKeeperHasBall() {
		return (StateKeeperHasBall) stateKeeperHasBall;
	}
	
	public StateHasBall getStateHasBall() {
		return (StateHasBall) stateHasBall;
	}

	public StateTeamHasBall getStateTeamHasBall() {
		return stateTeamHasBall;
	}

	public void setStateTeamHasBall(StateTeamHasBall stateTeamHasBall) {
		this.stateTeamHasBall = stateTeamHasBall;
	}

	public void setStateHasBall(StateHasBall stateHasBall) {
		this.stateHasBall = stateHasBall;
	}

	public StateOppHasBall getStateOppHasBall() {
		return stateOppHasBall;
	}

	public void setStateOppHasBall(StateOppHasBall stateOppHasBall) {
		this.stateOppHasBall = stateOppHasBall;
	}

	public states.PlayerState getState() {
		return state;
	}

	public void setState(states.PlayerState state) {
		this.state = state;
//		System.out.println(shirtNumber + ": " + state.toString());
	}

	public states.PlayerState getStateNothing() {
		return stateNothing;
	}

	public states.PlayerState getStateKickOff() {
		return stateKickOff;
	}

	public states.PlayerState getStateBeforeMatch() {
		return stateBeforeMatch;
	}

	public states.PlayerState getStateThrowIn() {
		return stateThrowIn;
	}

	public states.PlayerState getStateHTFT() {
		return stateHTFT;
	}

	public states.PlayerState getStateFreeKick() {
		return stateFreeKick;
	}

	public states.PlayerState getStatePenalty() {
		return statePenalty;
	}

	public states.PlayerState getStateGoalKick() {
		return stateGoalKick;
	}

	public states.PlayerState getStateCorner() {
		return stateCorner;
	}

	public states.PlayerState getStateGoal() {
		return stateGoal;
	}

	public states.PlayerState getStateShooting() {
		return stateShooting;
	}

	public states.PlayerState getStateDribbling() {
		return stateDribbling;
	}

	public states.PlayerState getStateRunWithBall() {
		return stateRunWithBall;
	}

	public states.PlayerState getStateCrossing() {
		return stateCrossing;
	}

	public states.PlayerState getStateClearing() {
		return stateClearing;
	}

	public states.PlayerState getStatePassing() {
		return statePassing;
	}

	public states.PlayerState getStatePressuring() {
		return statePressuring;
	}

	public void setStatePressuring(states.PlayerState statePressuring) {
		this.statePressuring = statePressuring;
	}

	public states.PlayerState getStatePenaltyShootout() {
		return statePenaltyShootout;
	}

	public boolean kasterSig() {
		return kasterSig;
	}

	public void setKasterSig(boolean kasterSig) {
		this.kasterSig = kasterSig;
	}

	public boolean isGuessPenalties() {
		return guessPenalties;
	}

	public void setGuessPenalties(boolean guessPenalties) {
		this.guessPenalties = guessPenalties;
	}

	public states.PlayerState getStateRunOffBall() {
		return stateRunOffBall;
	}

	public void setStateRunOffBall(states.PlayerState stateRunOffBall) {
		this.stateRunOffBall = stateRunOffBall;
	}

	public double getMorale() {
		return morale;
	}

	public void setMorale(double morale) {
		this.morale = morale;
	}

	public double getStart_morale() {
		return start_morale;
	}

	public void setStart_morale(double start_morale) {
		this.start_morale = start_morale;
	}

	public states.PlayerState getStateHoldUpBall() {
		return stateHoldUpBall;
	}

	public void setStateHoldUpBall(states.PlayerState stateHoldUpBall) {
		this.stateHoldUpBall = stateHoldUpBall;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getGoalkeeperScore() {
		return goalkeeperScore;
	}

	public void setGoalkeeperScore(double goalkeeperScore) {
		this.goalkeeperScore = goalkeeperScore;
	}

	public double getDefenderScore() {
		return defenderScore;
	}

	public void setDefenderScore(double defenderScore) {
		this.defenderScore = defenderScore;
	}

	public double getFullbackScore() {
		return fullbackScore;
	}

	public void setFullbackScore(double fullbackScore) {
		this.fullbackScore = fullbackScore;
	}

	public double getMidfielderScore() {
		return midfielderScore;
	}

	public void setMidfielderScore(double midfielderScore) {
		this.midfielderScore = midfielderScore;
	}

	public double getWingerScore() {
		return wingerScore;
	}

	public void setWingerScore(double wingerScore) {
		this.wingerScore = wingerScore;
	}

	public double getStrikerScore() {
		return strikerScore;
	}

	public void setStrikerScore(double strikerScore) {
		this.strikerScore = strikerScore;
	}
	
	public int getBestPositionScore() {
		return bestPositionScore;
	}

	public void setBestPositionScore(int bestPositionScore) {
		this.bestPositionScore = bestPositionScore;
	}

	public PositionScoreCalculator.Position getBestPosition() {
		return bestPosition;
	}

	public void setBestPosition(PositionScoreCalculator.Position bestPosition) {
		this.bestPosition = bestPosition;
	}



	public static enum Attributes{
		acceleration,
		topSpeed,
		agility,
		strength,
		jumping,
		reaction,
		stamina,
		dribbling,
		shooting,
		shotpower,
		passing,
		technique,
		vision,
		marking,
		tackling,
		heading,
		commandOfArea,
		handling,
		rushingout,
		shotstopping		
	}
}
