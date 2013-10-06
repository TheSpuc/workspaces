package model;

import java.util.ArrayList;
import data.DAOCake;

public class Match {

	MatchState state;
	MatchState nextState;
	public int attendance;
	Team setPieceTeam;
	int matchId;
	int leagueId = -1;
	int seasonId = -1;
	int setPieceX = 0, setPieceY = 0;
	long setPieceTime = 0;
	public Player setPieceTaker = null;
	int offsideX = 0;
	int offsideY = 0;
	boolean halfRunning = false;
	int half = 1;
	ArrayList<String> managerCommands = new ArrayList<String>();
	ArrayList<String> commentary = new ArrayList<String>();
	ArrayList<Integer> shootoutShotsA = new ArrayList<Integer>();
	ArrayList<Integer> shootoutShotsB = new ArrayList<Integer>();
	int shootoutGoalsA = 0;
	int shootoutGoalsB = 0;
	int matchrep = -1;
	MatchEngine engine;
	boolean mustFindWinner = true;
	boolean extraTime = true;
	int firstMatchGoalsA = -1;
	int firstMatchGoalsB = -1;
	boolean penaltyTaken = false;
	long penaltyTime = 0;
	boolean two_legs = false;
	boolean cup = false;
	
	public Match(MatchEngine engine){
		this.engine = engine;
		state = MatchState.KICK_OFF;
		matchId = 1;
		setPieceTeam = null;
	}
	
	public Team shootoutWinner(){
		Team result = null; 
		
		int shotsDoneA = 0;
		int shotsDoneB = 0;
		
		for (Integer i : shootoutShotsA)
			if (i > 0) shotsDoneA++;
		
		for (Integer i : shootoutShotsB)
			if (i > 0) shotsDoneB++;
		
		//Hvis de ikke begge har haft fem straffe endnu
		if (shotsDoneA < 5 || shotsDoneB < 5){
			
			//A har vundet hvis deres mål - B's mål er større end det antal straffe B har tilbage
			if (shootoutGoalsA - shootoutGoalsB > 5 - shotsDoneB)
				result = engine.getTeamA();
			
			//Samme for B
			if (shootoutGoalsB - shootoutGoalsA > 5 - shotsDoneA)
				result = engine.getTeamB();
		}
		else{
			//Hvis der er taget mere end fem straffe skal begge hold have taget lige mange straffe og et være foran for at have vundet
			if (shotsDoneA == shotsDoneB)
				if (shootoutGoalsB - shootoutGoalsA > 0)
					result = engine.getTeamB();
				else if (shootoutGoalsA - shootoutGoalsB > 0)
					result = engine.getTeamA();
		}
		
		return result;
	}
	
	public boolean playExtraTime(){
		boolean result = false;
		
		if (mustFindWinner){//Der skal findes en vinder af denne kamp
			if (firstMatchGoalsA > -1 && firstMatchGoalsB > -1){ //Der er spillet en kamp før som skal regnes sammen med denne
				if (firstMatchGoalsA + engine.getBold().getTeamAMaal() == firstMatchGoalsB + engine.getBold().getTeamBMaal()){
					//uafgjort - tjek udebanemål
					if (firstMatchGoalsA == engine.getBold().getTeamBMaal()) //lige mange udebanemål
						result = extraTime;
				}
			}
			else{ //der er ikke spillet nogen kamp før (men der skal findes en vinder alligevel
				if (engine.getBold().getTeamAMaal() == engine.getBold().getTeamBMaal()) result = extraTime;
			}
		}
		
		return result;
	}
	
	public void addCommentary(String str) {
		synchronized (commentary) {
			commentary.add(str);
		}
	}
	
	public void clearCommentary(){
		commentary.clear();
	}
	public ArrayList<String> getCommentary(){
		return commentary;
	}
	
	public Team getSetPieceTeam() {
		return setPieceTeam;
	}

	public void setSetPieceTeam(Team setPieceTeam) {
		this.setPieceTeam = setPieceTeam;
	}

	public boolean isHalfRunning() {
		return halfRunning;
	}

	public void setHalfRunning(boolean t) {
		halfRunning = t;;
	}

	public int getMatchId() {
		return matchId;
	}
	
	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	public MatchState getState() {
		return state;
	}

	public void setState(MatchState state) {

		System.out.println(state.toString());
		if (state != MatchState.ON && state != MatchState.FINISHED){

			managerCommands = DAOCake.getManagerCommands(matchId, "");
			System.out.println(managerCommands.size() + " kommandoer klar.");
			if (managerCommands.size() > 0)
				engine.updateTactics();
		}

		setPieceTime = System.currentTimeMillis();
		this.state = state;
	}

	public int getSetPieceX() {
		return setPieceX;
	}

	public void setSetPieceX(int setPieceX) {
		this.setPieceX = setPieceX;
	}

	public int getSetPieceY() {
		return setPieceY;
	}

	public void setSetPieceY(int setPieceY) {
		this.setPieceY = setPieceY;
	}

	public int getHalf() {
		return half;
	}

	public void setHalf(int half) {
		this.half = half;
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

	
	
	public MatchState getNextState() {
		return nextState;
	}

	public void setNextState(MatchState nextState) {
		this.nextState = nextState;
	}

	public ArrayList<String> getManagerCommands() {
		return managerCommands;
	}

	public void setManagerCommands(ArrayList<String> managerCommands) {
		this.managerCommands = managerCommands;
	}

	public int getMatchrep() {
		if (matchrep == -1)
			matchrep = DAOCake.getMatchRep(matchId);
		return matchrep;
	}

	public void updMatchrep() {
		matchrep = DAOCake.getMatchRep(matchId);
	}
	
	public void setMatchrep(int matchrep) {
		this.matchrep = matchrep;
	}



	public boolean isMustFindWinner() {
		return mustFindWinner;
	}

	public void setMustFindWinner(boolean mustFindWinner) {
		this.mustFindWinner = mustFindWinner;
	}

	public int getFirstMatchGoalsA() {
		return firstMatchGoalsA;
	}

	public void setFirstMatchGoalsA(int firstMatchGoalsA) {
		this.firstMatchGoalsA = firstMatchGoalsA;
	}

	public int getFirstMatchGoalsB() {
		return firstMatchGoalsB;
	}

	public void setFirstMatchGoalsB(int firstMatchGoalsB) {
		this.firstMatchGoalsB = firstMatchGoalsB;
	}

	public int getShootoutGoalsA() {
		return shootoutGoalsA;
	}

	public void setShootoutGoalsA(int shootoutGoalsA) {
		this.shootoutGoalsA = shootoutGoalsA;
	}

	public int getShootoutGoalsB() {
		return shootoutGoalsB;
	}

	public void setShootoutGoalsB(int shootoutGoalsB) {
		this.shootoutGoalsB = shootoutGoalsB;
	}

	public boolean getPenaltyTaken() {
		return penaltyTaken;
	}

	public void setPenaltyTaken(boolean penaltyTaken) {
		this.penaltyTaken = penaltyTaken;
	}

	public long getPenaltyTime() {
		return penaltyTime;
	}

	public void setPenaltyTime(long penaltyTime) {
		this.penaltyTime = penaltyTime;
	}

	public ArrayList<Integer> getShootoutShotsA() {
		return shootoutShotsA;
	}

	public void setShootoutShotsA(ArrayList<Integer> shootoutShotsA) {
		this.shootoutShotsA = shootoutShotsA;
	}

	public ArrayList<Integer> getShootoutShotsB() {
		return shootoutShotsB;
	}

	public void setShootoutShotsB(ArrayList<Integer> shootoutShotsB) {
		this.shootoutShotsB = shootoutShotsB;
	}

	public int getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}

	public boolean isTwo_legs() {
		return two_legs;
	}

	public void setTwo_legs(boolean two_legs) {
		this.two_legs = two_legs;
	}

	public boolean isCup() {
		return cup;
	}

	public void setCup(boolean cup) {
		this.cup = cup;
	}

	public long getSetPieceTime() {
		return setPieceTime;
	}

	public void setSetPieceTime(long setPieceTime) {
		this.setPieceTime = setPieceTime;
	}



	public int getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(int seasonId) {
		this.seasonId = seasonId;
	}



	public enum MatchState{
		BEFORE_MATCH, KICK_OFF, FREE_KICK, PENALTY, THROW_IN, GOAL_KICK, CORNER, GOAL, ON, HALF_TIME, FINISHED, UPDATETACTICS, TIMEOUT, PENALTYSHOOTOUT
	}
}


