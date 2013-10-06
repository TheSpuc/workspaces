package trainingmodules;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import model.Bold;
import model.Hjaelper;
import model.Match;
import model.Pitch;
import model.Player;
import model.PlayerRole;
import model.Settings;
import model.StateHasBall;
import model.StateOppHasBall;
import model.StateTeamHasBall;
import model.Team;
import model.Match.MatchState;

public class Dribbling implements TrainingModule{

	Team teamA = new Team("Team A", Color.red, Color.white, 1, null, 10000, 10000);
	Team teamB = new Team("Team B", Color.white, Color.blue, 2, null, 10000, 10000);
	Settings settings;
	
	int pitchHeight = 513;
	int pitchWidth = 880;

	int pitchPosX = 90;
	int pitchPosY = 80;
	
	Pitch pitch;
	ArrayList<Bold> bolde = new ArrayList<Bold>();
	Match match;

	int step = 1;
	Point dribbleTarget = new Point(600, 300);
	
	public Dribbling(Settings settings){
		
		this.settings = settings;
		
		match = new Match(null);
		match.setState(MatchState.ON);
		pitch = new Pitch(pitchHeight, pitchPosX, pitchPosY, pitchWidth);

		teamA.setOppTeam(teamB);
		teamB.setOppTeam(teamA);
		
		loadBalls();
		loadPlayers();
		
		bolde.get(0).setLastTouch(teamA.getPlayers().get(0));
		
	}
	
	private void loadBalls(){
		Bold bold = new Bold(pitchHeight,pitchPosX,pitchPosY,pitchWidth,pitch, match);
		bold.setTeamA(teamA);
		bold.setTeamB(teamB);
		bold.setX(230);
		bold.setY(200);
		bold.setSettings(settings);
		
		bolde.add(bold);
	}
	
	private void loadPlayers(){
		Player p = new Player(settings, 100, bolde.get(0), pitch, PlayerRole.AM, match);
		teamA.addPlayer(p);
		p.setMyTeam(teamA);
		p.setOppTeam(teamB);
		
		p.setKeeper(false);
		p.setRole(PlayerRole.AM);
		p.setAllStatsTo50();
		p.setAcceleration(80);
		p.setTopSpeed(130);
		p.setAgility(80);
		p.setDribbling(80);
		
		p.setShirtNumber(9);
		p.setStartPosX(200);
		p.setStartPosY(200);
		p.setX(p.getStartPosX());
		p.setY(p.getStartPosY());
		p.setSettings(settings);
		p.setMatch(match);
		p.setBold(bolde.get(0));
		p.setPitch(pitch);
		
		p.setStateHasBall(new StateHasBall(p));
		p.setStateOppHasBall(new StateOppHasBall(p));
		p.setStateTeamHasBall(new StateTeamHasBall(p));
		
		p.setStates();
		
		
		Player p4 = new Player(settings, 100, bolde.get(0), pitch, PlayerRole.CM, match);
		teamB.addPlayer(p4);
		p4.setMyTeam(teamB);
		p4.setOppTeam(teamA);
		
		p4.setKeeper(false);
		p4.setRole(PlayerRole.CM);
		p.setAllStatsTo50();
		
		p4.setShirtNumber(6);
		p4.setStartPosX(400);
		p4.setStartPosY(200);
		p4.setX(p4.getStartPosX());
		p4.setY(p4.getStartPosY());
		p4.setSettings(settings);
		p4.setMatch(match);
		p4.setBold(bolde.get(0));
		p4.setPitch(pitch);
		
		p4.setStateHasBall(new StateHasBall(p4));
		p4.setStateOppHasBall(new StateOppHasBall(p4));
		p4.setStateTeamHasBall(new StateTeamHasBall(p4));
		
		p4.setStates();
		
		
		Player p2 = new Player(settings, 100, bolde.get(0), pitch, PlayerRole.GK, match);
		teamA.addPlayer(p2);
		p2.setMyTeam(teamA);
		p2.setOppTeam(teamB);
		
		p2.setKeeper(true);
		p2.setRole(PlayerRole.GK);
		
		p2.setShirtNumber(1);
		p2.setStartPosX(100);
		p2.setStartPosY(200);
		p2.setX(p2.getStartPosX());
		p2.setY(p2.getStartPosY());
		p2.setSettings(settings);
		p2.setMatch(match);
		p2.setBold(bolde.get(0));
		p2.setPitch(pitch);
		
		p2.setStateHasBall(new StateHasBall(p2));
		p2.setStateOppHasBall(new StateOppHasBall(p2));
		p2.setStateTeamHasBall(new StateTeamHasBall(p2));
		
		p2.setStates();
		
		Player p3 = new Player(settings, 100, bolde.get(0), pitch, PlayerRole.GK, match);
		teamB.addPlayer(p3);
		p3.setMyTeam(teamB);
		p3.setOppTeam(teamA);
		
		p3.setKeeper(true);
		p3.setRole(PlayerRole.GK);
		
		p3.setShirtNumber(1);
		p3.setStartPosX(800);
		p3.setStartPosY(200);
		p3.setX(p3.getStartPosX());
		p3.setY(p3.getStartPosY());
		p3.setSettings(settings);
		p3.setMatch(match);
		p3.setBold(bolde.get(0));
		p3.setPitch(pitch);
		p3.setOppTeam(teamB);
		
		p3.setStateHasBall(new StateHasBall(p3));
		p3.setStateOppHasBall(new StateOppHasBall(p3));
		p3.setStateTeamHasBall(new StateTeamHasBall(p3));
		
		p3.setStates();
	}
	
	public void update() {

		for (Bold bold : bolde)
			bold.update();
		
		Player opp = teamB.getPlayers().get(0);
		Player mainman = teamA.getPlayers().get(0);
		
		for (Player p : teamB.getPlayers())
			if (p.getShirtNumber() == 6){
				opp = p;
			
				p.instructionHasBall = new Instruction(new Point(), mainman, "highpass");
				
				p.update();
				p.move(false, p.getSpeed(), p.getA(), p.getX(), p.getY());	
			}
		
		for (Player p : teamA.getPlayers()){

			if (p.getShirtNumber() == 9){
				p.instructionHasBall = new Instruction(dribbleTarget, opp, "dribble");
				p.instructionNoBall = new Instruction(new Point(400, 200), null, "MeetBall");			
			}
			
			p.update();
			p.move(false, p.getSpeed(), p.getA(), p.getX(), p.getY());
		}
	}

	public Team getTeamA() {
		return teamA;
	}

	public Team getTeamB() {
		return teamB;
	}

	public ArrayList<Bold> getBolde() {
		return bolde;
	}

	public void clickReceived(int x, int y) {
		dribbleTarget.x = x;
		dribbleTarget.y = y;
	}
	
	
	
}
