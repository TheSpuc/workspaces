package data.teamSetup;

import java.util.ArrayList;
import java.util.Random;

import model.Player;
import model.PlayerRole;
import model.Team;
import data.teamSetup.TeamTactic;

 public class CreateTeamSheet {
	/**
	 * Chooses players and subs and sets up players positions on a team given a tactic
	 * @param tactic the TeamTactic
	 * @param team the Team
	 */
	public static void creatTeamSheet(TeamTactic tactic, Team team, ArrayList<Player> players){
		if(tactic.equals(TeamTactic.T541)){
			System.out.println("Tactic T541 chosen");
			createTeamSheetFor541(team, players);			
		}
		else if (tactic.equals(TeamTactic.T532)){
			System.out.println("Tactic T532 chosen");
			createTeamSheetFor532(team, players);
		}
		else if (tactic.equals(TeamTactic.T451)){
			System.out.println("Tactic T451 chosen");
			createTeamSheetFor451(team, players);
		}
		else if (tactic.equals(TeamTactic.T442)){
			System.out.println("Tactic T442 chosen");
			createTeamSheetFor442(team, players);
		}
		else if (tactic.equals(TeamTactic.T433)){
			System.out.println("Tactic T433 chosen");
			createTeamSheetFor433(team, players);
		}
		else if (tactic.equals(TeamTactic.T352)){
			System.out.println("Tactic T352 chosen");
			createTeamSheetFor352(team, players);
		}
		else if (tactic.equals(TeamTactic.T343)){
			System.out.println("Tactic T343 chosen");
			createTeamSheetFor343(team, players);
		}
	}

	/**
	 * Chooses a random TeamTactic.
	 * returns TeamTactic 442 if something should go wrong
	 * @return a TeamTactic
	 */
	public static TeamTactic chooseRandomTeamTactic(){
		Random rand = new Random();
		int i = rand.nextInt(7);
		switch(i){
			case 0: return TeamTactic.T541;
			case 1: return TeamTactic.T532;
			case 2: return TeamTactic.T451;
			case 3: return TeamTactic.T442;
			case 4: return TeamTactic.T433;
			case 5: return TeamTactic.T352;
			case 6: return TeamTactic.T343;
		}
		return TeamTactic.T442;
	}

	/**
	 * Chooses players and subs for the 5-4-1 tactic and sets their positions
	 * @param teamT the Team
	 */
	public static void createTeamSheetFor541(Team teamT, ArrayList<Player> playerList){
		ArrayList<Player> players = new ArrayList<Player>(playerList);
		System.out.println("createTeamSheetFor541");
		System.out.println("Team Size: " + players.size());
		createPosition(PlayerRole.GK, 7, 250, teamT, players, PlayerRole.GK);
		createPosition(PlayerRole.CB, 120, 250, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.CB, 120, 180, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.CB, 120, 320, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.SB, 165, 100, teamT, players, PlayerRole.SB);
		createPosition(PlayerRole.SB, 165, 400, teamT, players, PlayerRole.SB);
		createPosition(PlayerRole.DM, 230, 250, teamT, players, PlayerRole.DM);
		createPosition(PlayerRole.CM, 320, 130, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.CM, 320, 370, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.AM, 360, 250, teamT, players, PlayerRole.AM);
		createPosition(PlayerRole.CF, 465, 250, teamT, players, PlayerRole.CF);
		//Substitutions
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.GK);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.SB);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CF);		
	}


	/**
	 * Chooses players and subs for the 5-3-2 tactic and sets their positions
	 * @param teamT the Team
	 */
	public static void createTeamSheetFor532(Team teamT, ArrayList<Player> playerList){
		ArrayList<Player> players = new ArrayList<Player>(playerList);
		System.out.println("createTeamSheetFor532");
		System.out.println("TeamASize: " + players.size());
		createPosition(PlayerRole.GK, 7, 250, teamT, players, PlayerRole.GK);
		createPosition(PlayerRole.CB, 120, 250, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.CB, 120, 180, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.CB, 120, 320, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.SB, 165, 100, teamT, players, PlayerRole.SB);
		createPosition(PlayerRole.SB, 165, 400, teamT, players, PlayerRole.SB);
		createPosition(PlayerRole.CM, 230, 250, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.CM, 305, 130, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.CM, 305, 370, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.CF, 465, 175, teamT, players, PlayerRole.CF);
		createPosition(PlayerRole.CF, 465, 325, teamT, players, PlayerRole.CF);
		//Substitutions
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.GK);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.SB);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CF);
	}

	/**
	 * Chooses players and subs for the 4-5-1 tactic and sets their positions
	 * @param teamT the Team
	 */
	public static void createTeamSheetFor451(Team teamT, ArrayList<Player> playerList){
		ArrayList<Player> players = new ArrayList<Player>(playerList);
		System.out.println("createTeamSheetFor451");
		System.out.println("TeamASize: " + players.size());
		createPosition(PlayerRole.GK, 7, 250, teamT, players, PlayerRole.GK);
		createPosition(PlayerRole.CB, 120, 200, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.CB, 120, 300, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.SB, 120, 115, teamT, players, PlayerRole.SB);
		createPosition(PlayerRole.SB, 120, 385, teamT, players, PlayerRole.SB);
		createPosition(PlayerRole.DM, 230, 250, teamT, players, PlayerRole.DM);
		createPosition(PlayerRole.CM, 305, 160, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.CM, 305, 340, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.WF, 370, 95, teamT, players, PlayerRole.WF);
		createPosition(PlayerRole.WF, 370, 405, teamT, players, PlayerRole.WF);
		createPosition(PlayerRole.CF, 470, 250, teamT, players, PlayerRole.CF);
		//Substitutions
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.GK);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.SB);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CF);
	}

	/**
	 * Chooses players and subs for the 4-4-2 tactic and sets their positions
	 * @param teamT the Team
	 */
	public static void createTeamSheetFor442(Team teamT, ArrayList<Player> playerList){
		ArrayList<Player> players = new ArrayList<Player>(playerList);
		System.out.println("createTeamSheetFor442");
		System.out.println("TeamASize: " + players.size());
		createPosition(PlayerRole.GK, 7, 250, teamT, players, PlayerRole.GK);
		createPosition(PlayerRole.CB, 120, 200, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.CB, 120, 300, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.SB, 125, 115, teamT, players, PlayerRole.SB);
		createPosition(PlayerRole.SB, 125, 385, teamT, players, PlayerRole.SB);
		createPosition(PlayerRole.CM, 305, 200, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.CM, 305, 300, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.WF, 320, 100, teamT, players, PlayerRole.WF);
		createPosition(PlayerRole.WF, 320, 400, teamT, players, PlayerRole.WF);
		createPosition(PlayerRole.CF, 465, 165, teamT, players, PlayerRole.CF);
		createPosition(PlayerRole.CF, 465, 335, teamT, players, PlayerRole.CF);
		//Substitutions
		createPosition(PlayerRole.SUB, 240, -20, teamT, players, PlayerRole.GK);
		createPosition(PlayerRole.SUB, 250, -20, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.SUB, 260, -20, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.SUB, 270, -20, teamT, players, PlayerRole.SB);
		createPosition(PlayerRole.SUB, 280, -20, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.SUB, 290, -20, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CF);
	}

	/**
	 * Chooses players and subs for the 4-3-3 tactic and sets their positions
	 * @param teamT the Team
	 */
	public static void createTeamSheetFor433(Team teamT, ArrayList<Player> playerList){
		ArrayList<Player> players = new ArrayList<Player>(playerList);
		System.out.println("createTeamSheetFor433");
		System.out.println("TeamASize: " + players.size());
		createPosition(PlayerRole.GK, 7, 250, teamT, players, PlayerRole.GK);
		createPosition(PlayerRole.CB, 120, 200, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.CB, 120, 300, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.SB, 125, 115, teamT, players, PlayerRole.SB);
		createPosition(PlayerRole.SB, 125, 385, teamT, players, PlayerRole.SB);
		createPosition(PlayerRole.DM, 300, 250, teamT, players, PlayerRole.DM);
		createPosition(PlayerRole.CM, 305, 130, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.CM, 305, 370, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.CF, 465, 200, teamT, players, PlayerRole.CF);
		createPosition(PlayerRole.CF, 465, 300, teamT, players, PlayerRole.CF);
		createPosition(PlayerRole.CF, 465, 250, teamT, players, PlayerRole.CF);
		//Substititions
		createPosition(PlayerRole.SUB, 240, -20, teamT, players, PlayerRole.GK);
		createPosition(PlayerRole.SUB, 250, -20, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.SUB, 260, -20, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.SUB, 270, -20, teamT, players, PlayerRole.SB);
		createPosition(PlayerRole.SUB, 280, -20, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.SUB, 290, -20, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CF);
	}

	/**
	 * Chooses players and subs for the 3-5-2 tactic and sets their positions
	 * @param teamT the Team
	 */
	public static void createTeamSheetFor352(Team teamT, ArrayList<Player> playerList){
		ArrayList<Player> players = new ArrayList<Player>(playerList);
		System.out.println("createTeamSheetFor352");
		System.out.println("TeamASize: " + players.size());		
		createPosition(PlayerRole.GK, 7, 250, teamT, players, PlayerRole.GK);
		createPosition(PlayerRole.CB, 120, 250, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.CB, 120, 180, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.CB, 120, 320, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.DM, 230, 250, teamT, players, PlayerRole.DM);
		createPosition(PlayerRole.AM, 360, 250, teamT, players, PlayerRole.AM);
		createPosition(PlayerRole.CM, 305, 250, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.WF, 320, 155, teamT, players, PlayerRole.WF);
		createPosition(PlayerRole.WF, 320, 345, teamT, players, PlayerRole.WF);
		createPosition(PlayerRole.CF, 465, 280, teamT, players, PlayerRole.CF);
		createPosition(PlayerRole.CF, 465, 220, teamT, players, PlayerRole.CF);
		//Substitutions
		createPosition(PlayerRole.SUB, 240, -20, teamT, players, PlayerRole.GK);
		createPosition(PlayerRole.SUB, 250, -20, teamT, players, PlayerRole.CB);
		createPosition(PlayerRole.SUB, 260, -20, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.SUB, 270, -20, teamT, players, PlayerRole.CM);
		createPosition(PlayerRole.SUB, 280, -20, teamT, players, PlayerRole.WF);
		createPosition(PlayerRole.SUB, 290, -20, teamT, players, PlayerRole.CF);
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CF);
	}

	/**
	 * Chooses players and subs for the 3-4-3 tactic and sets their positions
	 * @param teamT the Team
	 */
	public static void createTeamSheetFor343(Team teamT, ArrayList<Player> playerList){
		ArrayList<Player> players = new ArrayList<Player>(playerList);
		System.out.println("createTeamSheetFor343");
		System.out.println("TeamASize: " + players.size());
		createPosition(PlayerRole.GK, 7, 250, teamT, players, PlayerRole.GK);//Goalkeeper
		createPosition(PlayerRole.CB, 120, 250, teamT, players, PlayerRole.CB);//Defender1		
		createPosition(PlayerRole.CB, 120, 180, teamT, players, PlayerRole.CB);//Defender2		
		createPosition(PlayerRole.CB, 120, 320, teamT, players, PlayerRole.CB);//Defender3	
		createPosition(PlayerRole.CM, 230, 250, teamT, players, PlayerRole.CM);//Midfielder1		
		createPosition(PlayerRole.CM, 360, 250, teamT, players, PlayerRole.CM);//Midfielder2
		createPosition(PlayerRole.WF, 320, 100, teamT, players, PlayerRole.WF);//Winger1		
		createPosition(PlayerRole.WF, 320, 400, teamT, players, PlayerRole.WF);//Winger2
		createPosition(PlayerRole.CF, 445, 250, teamT, players, PlayerRole.CF);//Find stiker1		
		createPosition(PlayerRole.CF, 465, 200, teamT, players, PlayerRole.CF);//Find stiker2		
		createPosition(PlayerRole.CF, 465, 300, teamT, players, PlayerRole.CF);//Find stiker3
		//Substitutions
		createPosition(PlayerRole.SUB, 240, -20, teamT, players, PlayerRole.GK);//Goalkeeper		
		createPosition(PlayerRole.SUB, 250, -20, teamT, players, PlayerRole.CB);//Defender1		
		createPosition(PlayerRole.SUB, 260, -20, teamT, players, PlayerRole.CB);//Defender2
		createPosition(PlayerRole.SUB, 270, -20, teamT, players, PlayerRole.CM);//Midfielder1		
		createPosition(PlayerRole.SUB, 280, -20, teamT, players, PlayerRole.WF);//Winger1		
		createPosition(PlayerRole.SUB, 290, -20, teamT, players, PlayerRole.CF);//Find Striker1			
		createPosition(PlayerRole.SUB, 300, -20, teamT, players, PlayerRole.CF);//Find Striker2
	}

	private static void createPosition(PlayerRole role, int startPosX, int startPosY, Team team, ArrayList<Player> players, PlayerRole scoreRole){
		Player player = null;
		int removePlayer = 0;
		if(players.size() > 0){
			for(int i = 0; i< players.size(); i++){
				if(scoreRole.equals(PlayerRole.GK)){
					if(player == null || players.get(i).getGoalkeeperScore() >= player.getGoalkeeperScore()){
						player = players.get(i);
						removePlayer = i;
					}
				}
				else if(scoreRole.equals(PlayerRole.CB)){
					if(player == null || players.get(i).getDefenderScore() >= player.getDefenderScore()){
						player = players.get(i);
						removePlayer = i;
					}
				}
				else if(scoreRole.equals(PlayerRole.SB)){
					if(player == null || players.get(i).getFullbackScore() >= player.getFullbackScore()){
						player = players.get(i);
						removePlayer = i;
					}
				}
				else if(scoreRole.equals(PlayerRole.CM) || scoreRole.equals(PlayerRole.DM) || scoreRole.equals(PlayerRole.AM)){
					if(player == null || players.get(i).getMidfielderScore() >= player.getMidfielderScore()){
						player = players.get(i);
						removePlayer = i;
					}
				}
				else if(scoreRole.equals(PlayerRole.WF)){
					if(player == null || players.get(i).getWingerScore() >= player.getWingerScore()){
						player = players.get(i);
						removePlayer = i;
					}
				}
				else if(scoreRole.equals(PlayerRole.CF)){
					if(player == null || players.get(i).getStrikerScore() >= player.getStrikerScore()){
						player = players.get(i);
						removePlayer = i;
					}
				}
			}
			readyPlayer(player, startPosX, startPosY, team, role);
			players.remove(removePlayer);
		}
	}
	
	private static void readyPlayer(Player player, int startPosX, int startPosY, Team team, PlayerRole role){
		player.setRole(role);
		player.setStartPosX(startPosX);
		player.setStartPosY(startPosY);
		player.setPosX(player.getStartPosX());
		player.setPosY(player.getStartPosY());
//		System.out.println("readyPlayer: " + player.getFirstname() + " " + player.getLastname() + ", " + player.getId() + ", (" + player.getPosX() + "," + player.getPosY() + "), (" + player.getStartPosX() + "," + player.getStartPosY() + ")");
		if(role.equals(PlayerRole.GK)){
			player.setKeeper(true);
			Random r = new Random();
			player.setGuessPenalties(r.nextBoolean());
		}		
	}

}
