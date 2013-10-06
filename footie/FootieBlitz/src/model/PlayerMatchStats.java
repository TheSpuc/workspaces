package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import data.DAOCake;

public class PlayerMatchStats {

	Player player;
	public boolean sharpTurnOn = false;
	public boolean saveOn = false;
	public int sharpTurns;
	public int dribbles;
	public int dribblesLost;
	public int updatesMarking;
	public int updatesPushing;
	public int interceptions;
	public int tackles;
	public int successfulTackles;
	public int freekicksCommitted;
	public int closeSaves;
	public int saveAttempts;
	public int saves;
	public int ballDirChanges;
	public double turnTotal;
	public double cmsRun;
	public double cmsRunAtSpeed;
	public int clearances;
	public int shots;
	public int shotsOnTarget;
	public int goals;
	public int assists;
	public int crosses;
	public int ballControlAttempts;
	public int longPasses;
	public int throughPasses;
	public int headers;
	public int passes;
	public int passesOnTarget;
	public int matchRating;
	public int minutesPlayed;
	
	public PlayerMatchStats(Player p){
		player = p;
	}
	
	public void increaseMinutesPlayed(){
		minutesPlayed++;
		if (minutesPlayed > 90) minutesPlayed=90;
	}
	
	public void writeAllToDB(int matchId, int personId, boolean setMinutesPlayed){
		try {
			String sql = "UPDATE match_playerstats SET passAttempts=" + passes + ", passesSucceeded=" + passesOnTarget + ", interceptions=" + interceptions + ", tackles=" + tackles + ", successfultackles=" + successfulTackles + ", ";
			sql += "dribbles=" + dribbles + ", dribblesLost=" + dribblesLost + ", shots=" + shots + ", shotsontarget=" + shotsOnTarget + ", goals=" + goals + ", ";
			sql += "assists=" + assists + ", crosses=" + crosses + ", headers=" + headers + ", clearances=" + clearances + ", metersrun=" + getMetersRun() + ", ";
			sql += "freekickscom=" + freekicksCommitted + ", saveattempts=" + saveAttempts+ ", saves=" + saves + ", rating=" + getMatchRatingNew(false, 0);
			if (setMinutesPlayed) sql += ", minutes_played = " + minutesPlayed;
			sql += " WHERE match_id=" + matchId + " AND person_id=" + personId + ";";
			DAOCake.executeSimpleStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void print(){
		
		if (Settings.SO){
			String print = player.lastname + "\t" + sharpTurns + "\t" + dribbles + "\t" + dribblesLost + "\t" + updatesMarking + "\t" + updatesPushing + "\t";
			print += tackles + "\t" + successfulTackles + "\t" + freekicksCommitted + "\t" + closeSaves + "\t" + saveAttempts + "\t" + saves + "\t" + ballDirChanges + "\t";
			print += (int)turnTotal + "\t" + clearances + "\t" + shots + "\t" + shotsOnTarget + "\t" + goals + "\t" + crosses + "\t";
			print += ballControlAttempts + "\t" + longPasses + "\t" + throughPasses + "\t" + headers + "\t" + passes + "\t" + passesOnTarget + "\t" + assists + "\t";
			print += getMetersRun() + "\t" + getMetersRunAtSpeed();
			Sysout.print(print, "PlayerMatchStats");
		}
		
	}
	
	public double getMatchRatingNew(boolean saveXP, int matchrep){
		double matchRating = 0;
		
//		SHOOTING START
//		MIN/MAX -2/6
		double shootingScore = 0;
		if (shots >0){
			shootingScore = ((double)shotsOnTarget/(double)shots)+(Math.sqrt(shots)*0.19)-((shots-shotsOnTarget)*0.18);
			if(shootingScore < -2) shootingScore = -2;
			else if(shootingScore > 2) shootingScore = 2;
		}
		if(goals >0){
			double goalScore = goals + 1; 
			if(goalScore > 5) goalScore = 5;
			shootingScore += goalScore;
		}
		if(shootingScore < -2) shootingScore = -2;
		else if(shootingScore > 6) shootingScore = 6;
//		SHOOTING END
		
//		DEFENCE START
//		MIN/MAX -2/4
		double defenceScore = 0;
		if(tackles > 0){	
			defenceScore = ((double)successfulTackles / (double)tackles)+(Math.sqrt(successfulTackles) * 0.35)-((successfulTackles - tackles)*0.25);
		}
		if(freekicksCommitted > 0)defenceScore -= freekicksCommitted * 0.4;
		if(clearances > 0) defenceScore += clearances * 0.4 + 0.4;
		if(interceptions > 0) defenceScore += interceptions * 0.08;		
		if (defenceScore < -2) defenceScore = -2;
		else if(defenceScore > 4) defenceScore = 4;
//		DEFENCE END
		
//		DRIBBLING START
//		MIN/MAX -2/3
		double dribbleScore = 0;
		if(dribbles > 0){
			dribbleScore = (dribbles *0.08+0.2)+(dribbles*0.028)-(dribblesLost * 0.23);
			if(dribbleScore < -2) dribbleScore = -2;
			else if(dribbleScore > 3) dribbleScore = 3;
		}
//		DRIBBLING END
		
//		PASSING START
//		MIN/MAX  -2/4
		double passScore = 0;
		if(passes > 0){
			passScore = ((double)passesOnTarget / (double)passes) + (Math.sqrt(passes)*0.19)-((passes - passesOnTarget)*0.18);
			if(assists > 0)	passScore += assists*0.6 + 0.5;
			if(passScore < -2) passScore = -2;
			else if(passScore > 4) passScore = 4;
		}	
//		PASSING END
		
//		SAVES START
//		MIN/MAX -2/5
		double saveScore = 0;
		if(saveAttempts > 0){
			saveScore = ((double)saves/(double)saveAttempts) + (Math.sqrt(saveAttempts)*0.6) - ((saveAttempts - saves)*0.8);
			if(saveScore < -2) saveScore = -2;
			else if(saveScore > 5) saveScore = 5;
		}
//		SAVES END
		
//		METERS RUN START
//		MIN/MAX 0/5
		double metersScore = 0;
		if(getMetersRun() > 0) metersScore = Math.sqrt(getMetersRun())*0.02 + 2.6;
		if(metersScore < 0) metersScore = 0;
		else if (metersScore > 5) metersScore = 5;
//		METERS RUN END
		
//	MATCH RATING
		matchRating = shootingScore + defenceScore + dribbleScore + passScore + saveScore + metersScore;
		
		Sysout.print(player.lastname, "PlayerMatchStats");
		Sysout.print("shootingScore: " + shootingScore, "PlayerMatchStats");
		Sysout.print("defenceScore: " + defenceScore, "PlayerMatchStats");
		Sysout.print("dribbleScore: " + dribbleScore, "PlayerMatchStats");
		Sysout.print("passScore: " + passScore + " ***PassesAttempted: " + passes + ", PassesOnTarget: " + passesOnTarget + ", Assists: " + assists, "PlayerMatchStats");
		Sysout.print("saveScore: " + saveScore, "PlayerMatchStats");
		Sysout.print("metersScore:" + metersScore, "PlayerMatchStats");
		
		if(matchRating <= 0) matchRating = 0;
		else if(matchRating > 0 && matchRating <= 0.5) matchRating = 0.5;
		else if(matchRating > 0.5 && matchRating <= 1) matchRating = 1;
		else if(matchRating > 1 && matchRating <= 1.5) matchRating = 1.5;
		else if(matchRating > 1 && matchRating <= 2) matchRating = 2;
		else if(matchRating > 2 && matchRating <= 2.5) matchRating = 2.5;
		else if(matchRating > 2.5 && matchRating <= 3) matchRating = 3;
		else if(matchRating > 3 && matchRating <= 3.5) matchRating = 3.5;
		else if(matchRating > 3.5 && matchRating <= 4) matchRating = 4;
		else if(matchRating > 4 && matchRating <= 4.5) matchRating = 4.5;
		else if(matchRating > 4.5 && matchRating <= 5) matchRating = 5;
		else if(matchRating > 5 && matchRating <= 5.5) matchRating = 5.5;
		else if(matchRating > 5.5 && matchRating <= 6) matchRating = 6;
		else if(matchRating > 6 && matchRating <= 6.5) matchRating = 6.5;
		else if(matchRating > 6.5 && matchRating <= 7.5) matchRating = 7;
		else if(matchRating > 7.5 && matchRating <= 8.5) matchRating = 7.5;
		else if(matchRating > 8.5 && matchRating <= 9.5) matchRating = 8;
		else if(matchRating > 9.5 && matchRating <= 10.5) matchRating = 8.5;
		else if(matchRating > 10.5 && matchRating <= 11.5) matchRating = 9;
		else if(matchRating > 11.5 && matchRating <= 13) matchRating = 9.5;
		else if(matchRating > 13) matchRating = 10;
		
		Sysout.print("matchRating:" + matchRating, "PlayerMatchStats");
		
		if (matchRating > 0 && saveXP && matchrep > 0){
			
			if (player.totalPPFromXP < Player.maxCareerPPFromXP 
					&& player.totalPPFromXP + player.totalPPFromFacc < Player.maxCareerPPFromFaccAndXP){

				MatchXP(XPToGive(matchRating), shootingScore, defenceScore, dribbleScore, passScore, saveScore, metersScore);
			}
		}
		
		return matchRating;
	}
	
	/**
	 * Adds match xp to player
	 * @param matchRating
	 * @param shootingScore
	 * @param defenceScore
	 * @param dribbleScore
	 * @param passScore
	 * @param saveScore
	 * @param metersScore
	 */
	public void MatchXP(double xp, double shootingScore, double defenceScore, double dribbleScore, double passScore, double saveScore, double metersScore){
		Random r = new Random();
		double totalXP = xp;
		int runs;
		double ab, cost;
		double acceleration = 0, topSpeed = 0, agility = 0, strength = 0, jumping = 0, reaction = 0, stamina = 0, dribbling = 0, shooting = 0,
		shotPower = 0, passing = 0, technique = 0, vision = 0, marking = 0, tackling = 0, heading = 0, commandOfArea = 0, handling = 0,
		rushingOut = 0, shotStopping = 0;

		//Randomly pick which area the xp goes to. 
		double d = r.nextInt((int)shootingScore + (int)defenceScore + (int)dribbleScore + (int)passScore + (int)saveScore + (int)metersScore + 5);
		Sysout.print("Random d = " + d, "PlayerMatchStats");
		if(d < shootingScore){
			//Shooting xp (shooting, shotPower)
			runs = 30;
			while(xp > 0 && runs > 0){
				ab = (double)r.nextInt(2);
				if(ab == 0){
					cost = CanAddXPToAbility(player.start_shooting);
					if(cost < xp){shooting += 0.1; xp -= cost;}
				}
				else if(ab == 1){
					cost = CanAddXPToAbility(player.start_shotpower);
					if(cost < xp){shotPower += 0.1;	xp -= cost;}
				}
			runs--;	
			}			
		}
		else if (d < shootingScore + defenceScore){
			//Defence xp
			runs = 30;
			while(xp > 0 && runs > 0){
				ab = (double)r.nextInt(2);
				if(ab == 0){
					cost = CanAddXPToAbility(player.start_marking);
					if(cost < xp){marking += 0.1; xp -= cost;}
				}
				else if(ab == 1){
					cost = CanAddXPToAbility(player.start_tackling);
					if(cost < xp){tackling += 0.1;xp -= cost;}
				}
			runs--;
			}
		}
		else if(d < shootingScore + defenceScore + dribbleScore){
			//Dribble xp
			runs = 30;
			while(xp > 0 && runs > 0){
				ab = (double)r.nextInt(3);
				if(ab == 0){
					cost = CanAddXPToAbility(player.start_dribbling);
					if(cost < xp){dribbling += 0.1; xp -= cost;}
				}
				else if(ab == 1){
					cost = CanAddXPToAbility(player.start_technique);
					if(cost < xp){technique += 0.1; xp -= cost;}
				}
				else if(ab == 2){
					cost = CanAddXPToAbility(player.start_agility);
					if(cost < xp){agility += 0.1; xp -= cost;}
				}
			runs--;
			}
		}
		else if(d < shootingScore + defenceScore + dribbleScore + passScore){
			//Pass xp
			runs = 30;
			while(xp > 0 && runs > 0){
				ab = (double)r.nextInt(2);
				if(ab == 0){
					cost = CanAddXPToAbility(player.start_passing);
					if(cost < xp){passing += 0.1; xp -= cost;}
				}
				else if(ab == 1){
					cost = CanAddXPToAbility(player.start_vision);
					if(cost < xp){vision += 0.1;xp -= cost;}
				}
			runs--;
			}
		}
		else if(d < shootingScore + defenceScore + dribbleScore + passScore + saveScore){
			//Save xp
			runs = 30;
			while(xp > 0 && runs > 0){
				ab = (double)r.nextInt(4);
				if(ab == 0){
					cost = CanAddXPToAbility(player.start_commandofarea);
					if(cost < xp){commandOfArea += 0.1; xp -= cost;}
				}
				else if(ab == 1){
					cost = CanAddXPToAbility(player.start_handling);
					if(cost < xp){handling += 0.1; xp -= cost;}
				}
				else if(ab == 2){
					cost = CanAddXPToAbility(player.start_rushingout);
					if(cost < xp){rushingOut += 0.1; xp -= cost;}
				}
				else if(ab == 3){
					cost = CanAddXPToAbility(player.start_shotstopping);
					if(cost < xp){shotStopping += 0.1; xp -= cost;}
				}
			runs--;
			}
		}
		else if(d < shootingScore + defenceScore + dribbleScore + passScore + saveScore + (metersScore / 1.5)){
			//Stamina xp
			runs = 10;
			while(xp > 0 && runs > 0){
				cost = CanAddXPToAbility(player.stamina);
				if(cost < xp){stamina += 0.1; xp -= cost;}
			
			runs--;	
			}
		}
		else {}
		if(xp > 0){//her deles "rest"-xp ud
			//Generel xp 
			runs = 50;
			while(xp > 0 && runs > 0){
				ab = (double)r.nextInt(6);
				if(ab == 0){
					cost = CanAddXPToAbility(player.start_acceleration);
					if(cost < xp){acceleration += 0.1; xp -= cost;}
				}
				else if(ab == 1){
					cost = CanAddXPToAbility(player.start_topSpeed);
					if(cost < xp){topSpeed += 0.1;	xp -= cost;}
				}
				else if(ab == 2){
					cost = CanAddXPToAbility(player.start_strength);
					if(cost < xp){strength += 0.1;	xp -= cost;}
				}
				else if(ab == 3){
					cost = CanAddXPToAbility(player.start_reaction);
					if(cost < xp){reaction += 0.1;	xp -= cost;}
				}
				else if(ab == 4){
					cost = CanAddXPToAbility(player.start_jumping);
					if(cost < xp){jumping += 0.1;	xp -= cost;}
				}
				else if(ab == 5){
					cost = CanAddXPToAbility(player.start_heading);
					if(cost < xp){heading += 0.1;	xp -= cost;}
				}
			runs--;	
			}
		}
		//If any xp is left, add them to pp
		double pp = 0.0;
		if(xp > 0.005) pp = xp;
		
		pp = Math.round(pp * 100) / 100.0;
		
		//Write it to the db
		try {
			String sql = "UPDATE persons SET acceleration = acceleration +" +acceleration + 
			", topspeed = topspeed + "+ topSpeed +
			", agility = agility + "+ agility +
			", strength = strength + "+ strength +
			", jumping = jumping + "+ jumping +
			", dribbling = dribbling + "+ dribbling +
			", tackling = tackling + " + tackling +
			", technique = technique + "+ technique +
			", heading = heading + "+ heading +
			", commandofarea = commandofarea + "+ commandOfArea +
			", rushingout = rushingout + "+ rushingOut +
			", shotstopping = shotstopping + "+ shotStopping +
			", marking = marking + " + marking + 
			", handling = handling +" + handling + 
			", reaction = reaction + " + reaction + 
			", shotpower = shotpower + " + shotPower + 
			", shooting = shooting + " + shooting + 
			", passing = passing + " + passing + 
			", vision = vision + " + vision + 
			", stamina = stamina + " + stamina + 
			", playerpoints = playerpoints + " + pp +
			", totalPPFromXP = totalPPFromXP + " + totalXP +
			" WHERE id=" + player.id + ";";
			DAOCake.executeSimpleStatement(sql);
			
			sql = "INSERT INTO xp " +
					"(match_id, person_id, matchrating, acceleration, topspeed, agility, strength, jumping, dribbling " +
					", tackling, technique, heading, commandofarea, rushingout, shotstopping, marking, handling, reaction " +  
					", shotpower, shooting, passing, vision, stamina, playerpoints) VALUES " +
					"(" + player.getMatch().getMatchId() + ", " + player.getId() + ", " + matchRating +
					"," + acceleration + "," + topSpeed + "," + agility + "," + strength + "," + jumping + "," + dribbling +
					"," + tackling + "," + technique + "," + heading + "," + commandOfArea + "," + rushingOut +
					"," + shotStopping + "," + marking + "," + handling + "," + reaction +  
					"," + shotPower + "," + shooting + "," + passing + "," + vision + "," + stamina + "," + pp + ");";
			
			DAOCake.executeSimpleStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks to see how many xp it takes to add 0.1 to an attribute 
	 * @param (double)cost - xp cost to add 0.1 to an attribute
	 * returns cost
	 */
	public double CanAddXPToAbility(double score){
		double cost = 0;
		if(score <= 50) cost = 0.1;
		else if(score <= 60) cost = 0.2;
		else if(score <=70) cost = 0.4;
		else if(score >70) cost = 0.6;
		return cost;	
	}
	
	public double XPToGive(double rating){
		double result = 0;
		
		if (rating < 0.5){
			result = 0.1;
		}else if (rating < 1){
			result = 0.2;
		}else if (rating < 2){
			result = 0.3;
		}else if (rating < 4){
			result = 0.4;
		}else if (rating < 5){
			result = 0.6;
		}else if (rating < 6){
			result = 0.8;
		}else if (rating < 7){
			result = 1;
		}else if (rating < 8){
			result = 1.2;
		}else if (rating < 9.5){
			result = 1.5;
		}else{
			result = 2.0;
		}
		
		int playerScore = player.getBestPositionScore();
		if (playerScore > 0) {
			System.out.println("result: " + result + ", oppavg: " + player.oppTeam.avgPlayerRating + ", playerScore: " + playerScore);
			result = result * (double)player.oppTeam.avgPlayerRating / (double)playerScore; 
			System.out.println("result after abilities: " + result);
			result = result * (double)minutesPlayed / 90.0;
			result = Hjaelper.round(result, 1);
			System.out.println("result after minutes: " + result);
		}
		
		return result;
	}
	
	
	public void addSharpTurn(){
		sharpTurnOn = true;
		sharpTurns++;
	}
	
	public int getMetersRun(){
		return (int)cmsRun / 8;
	}
	
	public int getMetersRunAtSpeed(){
		return (int)cmsRunAtSpeed / 8;
	}
	
	public double getNewStamina(double old){
		
		double percent = getMetersRun() / 11000;
		if (percent > 1) percent = 1;
		return old + ((99 - old) / 25) * percent ;
	}
}
