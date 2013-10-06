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

public class ControlBall implements TrainingModule{

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

	int step9 = 1;
	int step4 = 1;
	int step7 = 1;
	
	public ControlBall(Settings settings){
		
		this.settings = settings;
		
		match = new Match(null);
		match.setState(MatchState.ON);
		pitch = new Pitch(pitchHeight, pitchPosX, pitchPosY, pitchWidth);

		loadBalls();
		loadPlayers();
		
		bolde.get(0).setLastTouch(teamA.getPlayers().get(0));
		
	}
	
	private void loadBalls(){
		Bold bold = new Bold(pitchHeight,pitchPosX,pitchPosY,pitchWidth,pitch, match);
		bold.setTeamA(teamA);
		bold.setTeamB(teamA);
		bold.setX(200);
		bold.setY(440);
		bold.setSettings(settings);
		
		bolde.add(bold);
	}
	
	private void loadPlayers(){
		
		//p (trøjenummer 9) med lav technique høj passing
		Player p = new Player(settings, 100, bolde.get(0), pitch, PlayerRole.AM, match);
		teamA.addPlayer(p);
		p.setMyTeam(teamA);
		p.setOppTeam(teamB);
		
		p.setKeeper(false);
		p.setRole(PlayerRole.AM);
		
		p.setShirtNumber(9);
		p.setAllStatsTo50();
		p.setTechnique(40);
		p.setPassing(80);
		p.setTopSpeed(150);
		p.setStartPosX(200);
		p.setStartPosY(150);
		p.setX(p.getStartPosX());
		p.setY(p.getStartPosY());
		p.setSettings(settings);
		p.setMatch(match);
		p.setBold(bolde.get(0));
		p.setPitch(pitch);
		p.setOppTeam(teamB);
		
		p.setStateHasBall(new StateHasBall(p));
		p.setStateOppHasBall(new StateOppHasBall(p));
		p.setStateTeamHasBall(new StateTeamHasBall(p));
		
		p.setStates();
		

		//p4 (trøjenummer 4) med høj technique lav passing
		Player p4 = new Player(settings, 100, bolde.get(0), pitch, PlayerRole.AM, match);
		teamA.addPlayer(p4);
		p4.setMyTeam(teamA);
		p4.setOppTeam(teamB);
		
		p4.setKeeper(false);
		p4.setRole(PlayerRole.AM);
		
		p4.setShirtNumber(4);
		p4.setHeight(180);
		p4.setAllStatsTo50();
		p4.setTechnique(80);
		p4.setPassing(30);
		p4.setTopSpeed(150);
		p4.setStartPosX(500);
		p4.setStartPosY(450);
		p4.setX(p4.getStartPosX());
		p4.setY(p4.getStartPosY());
		p4.setSettings(settings);
		p4.setMatch(match);
		p4.setBold(bolde.get(0));
		p4.setPitch(pitch);
		p4.setOppTeam(teamB);
		
		p4.setStateHasBall(new StateHasBall(p4));
		p4.setStateOppHasBall(new StateOppHasBall(p4));
		p4.setStateTeamHasBall(new StateTeamHasBall(p4));
		
		p4.setStates();
		
		
		
		
		
		//p7 (trøjenummer 7) med høj passing til at lægge bolde ind til de andre
		Player p5 = new Player(settings, 100, bolde.get(0), pitch, PlayerRole.AM, match);
		teamA.addPlayer(p5);
		p5.setMyTeam(teamA);
		p5.setOppTeam(teamB);

		p5.setKeeper(false);
		p5.setRole(PlayerRole.AM);

		p5.setShirtNumber(7);
		p5.setHeight(180);
		p5.setAllStatsTo50();
		p5.setPassing(80);
		p5.setShotpower(80);
		p5.setTopSpeed(150);
		p5.setStartPosX(200);
		p5.setStartPosY(450);
		p5.setX(p5.getStartPosX());
		p5.setY(p5.getStartPosY());
		p5.setSettings(settings);
		p5.setMatch(match);
		p5.setBold(bolde.get(0));
		p5.setPitch(pitch);
		p5.setOppTeam(teamB);

		p5.setStateHasBall(new StateHasBall(p5));
		p5.setStateOppHasBall(new StateOppHasBall(p5));
		p5.setStateTeamHasBall(new StateTeamHasBall(p5));

		p5.setStates();

		
		///De to keepere som skal være der
		Player p2 = new Player(settings, 100, bolde.get(0), pitch, PlayerRole.GK, match);
		teamA.addPlayer(p2);
		p2.setMyTeam(teamA);
		p2.setOppTeam(teamB);
		
		p2.setKeeper(true);
		p2.setRole(PlayerRole.GK);
		
		p2.setShirtNumber(1);
		p2.setAllStatsTo50();
		p2.setStartPosX(100);
		p2.setStartPosY(200);
		p2.setX(p2.getStartPosX());
		p2.setY(p2.getStartPosY());
		p2.setSettings(settings);
		p2.setMatch(match);
		p2.setBold(bolde.get(0));
		p2.setPitch(pitch);
		p2.setOppTeam(teamB);
		
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
		p3.setAllStatsTo50();
		p3.setStartPosX(800);
		p3.setStartPosY(200);
		p3.setX(p3.getStartPosX());
		p3.setY(p3.getStartPosY());
		p3.setSettings(settings);
		p3.setMatch(match);
		p3.setBold(bolde.get(0));
		p3.setPitch(pitch);
		p3.setOppTeam(teamB);
		
		p3.setStateHasBall(new StateHasBall(p2));
		p3.setStateOppHasBall(new StateOppHasBall(p2));
		p3.setStateTeamHasBall(new StateTeamHasBall(p2));
		
		p3.setStates();
	}
	
	public void update() {

		for (Bold bold : bolde)
			bold.update();
		

		for (Player p : teamA.getPlayers()){
			
			//Spiller med nummer 7 afleverer på skift til de to medspillere
			if (p.getShirtNumber() == 7){

				if (step7 == 1){
					p.instructionHasBall = new Instruction(new Point(400, 200), teamA.getPlayers().get(0), "highPass");
					p.instructionNoBall = new Instruction(new Point(400, 200), null, "MeetBall");
					
					if (bolde.get(0).getPassTo() != null && bolde.get(0).getPassTo().equals(teamA.getPlayers().get(0)))
						step7++;
				}
				else if (step7 == 2){
					p.instructionNoBall = new Instruction(new Point((int)p.getStartPosX(), (int)p.getStartPosY()), null, "Position");
					
					if (bolde.get(0).getPassTo() != null && bolde.get(0).getPassTo().equals(p))
						step7++;
				}
				else if (step7 == 3){
					p.instructionHasBall = new Instruction(new Point(400, 200), null, "ControlBall");
					p.instructionNoBall = new Instruction(new Point(400, 200), null, "MeetBall");
					
					if (bolde.get(0).getPassTo() == null)
						step7++;
				}
				else if (step7 == 4){
					p.instructionHasBall = new Instruction(new Point(400, 200), teamA.getPlayers().get(1), "highPass");
					
					if (bolde.get(0).getPassTo() != null && bolde.get(0).getPassTo().equals(teamA.getPlayers().get(1)))
						step7++;
				}
				else if (step7 == 5){
					p.instructionNoBall = new Instruction(new Point((int)p.getStartPosX(), (int)p.getStartPosY()), null, "Position");
					
					if (bolde.get(0).getPassTo() != null && bolde.get(0).getPassTo().equals(p))
						step7++;
				}
				else if (step7 == 6){
					p.instructionHasBall = new Instruction(new Point(400, 200), null, "ControlBall");
					p.instructionNoBall = new Instruction(new Point(400, 200), null, "MeetBall");
					
					if (bolde.get(0).getPassTo() == null)
						step7 = 1;
				}
			}
			
			//Spiller nummer 4 og 9 kontrollerer boldene og afleverer tilbage til 7
			else if (p.getShirtNumber() == 4){

				if (step4 == 1){
					p.instructionNoBall = new Instruction(new Point((int)p.getStartPosX(), (int)p.getStartPosY()), null, "Position");
					
					if (bolde.get(0).getPassTo() != null && bolde.get(0).getPassTo().equals(p))
						step4++;
				}
				else if (step4 == 2){
					p.instructionHasBall = new Instruction(new Point(400, 200), null, "ControlBall");
					p.instructionNoBall = new Instruction(new Point(400, 200), null, "MeetBall");
					
					if (bolde.get(0).getPassTo() == null)
						step4++;
				}
				else if (step4 == 3){
					p.instructionHasBall = new Instruction(new Point(400, 200), teamA.getPlayers().get(2), "highPass");
					
					if (bolde.get(0).getPassTo() != null && bolde.get(0).getPassTo().equals(teamA.getPlayers().get(2)))
						step4 = 1;
				}
			}
			
			else if (p.getShirtNumber() == 9){

				if (step9 == 1){
					p.instructionNoBall = new Instruction(new Point((int)p.getStartPosX(), (int)p.getStartPosY()), null, "Position");
					
					if (bolde.get(0).getPassTo() != null && bolde.get(0).getPassTo().equals(p))
						step9++;
				}
				else if (step9 == 2){
					p.instructionHasBall = new Instruction(new Point(400, 200), null, "ControlBall");
					p.instructionNoBall = new Instruction(new Point(400, 200), null, "MeetBall");
					
					if (bolde.get(0).getPassTo() == null)
						step9++;
				}
				else if (step9 == 3){
					p.instructionHasBall = new Instruction(new Point(400, 200), teamA.getPlayers().get(2), "highPass");
					
					if (bolde.get(0).getPassTo() != null && bolde.get(0).getPassTo().equals(teamA.getPlayers().get(2)))
						step9 = 1;
				}
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
		// TODO Auto-generated method stub
		
	}
	
	
	
}
