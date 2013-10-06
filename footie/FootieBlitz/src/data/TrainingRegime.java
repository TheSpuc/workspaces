package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import model.Hjaelper;

public class TrainingRegime {

	//Values for which areas to train
	public int _acceleration;
	public int _topspeed;
	public int _dribbling;
	public int _marking;
	public int _strength;
	public int _tackling;
	public int _agility;
	public int _reaction;
	public int _shooting;
	public int _shotpower;
	public int _vision;
	public int _passing;
	public int _technique;
	public int _jumping;
	public int _stamina;
	public int _heading;
	public int _handling;
	public int _commandofarea;
	public int _shotstopping;
	public int _rushingout;
	public int person_id;
	
	public String generateSQLFromTraining(double pp, ResultSet res){

			Random r = new Random();
			double xp;
			double acceleration = 0, topSpeed = 0, agility = 0, strength = 0, jumping = 0, reaction = 0, stamina = 0, dribbling = 0, shooting = 0,
			shotPower = 0, passing = 0, technique = 0, vision = 0, marking = 0, tackling = 0, heading = 0, commandOfArea = 0, handling = 0,
			rushingOut = 0, shotStopping = 0;

			pp -= 16;
			xp = 16;
			
			while(xp > 1){
				try{

					//Randomly pick which area the xp goes to. 
					double d = r.nextInt(100);
					
					if(d < _acceleration) { acceleration += 0.1; xp -= CostToIncreaseAbility(res.getDouble("acceleration") + acceleration); }
					else if(d < _topspeed) { topSpeed += 0.1; xp -= CostToIncreaseAbility(res.getDouble("topspeed") + topSpeed); }
					else if(d < _dribbling) { dribbling += 0.1; xp -= CostToIncreaseAbility(res.getDouble("dribbling") + dribbling); }
					else if(d < _marking) { marking += 0.1; xp -= CostToIncreaseAbility(res.getDouble("marking") + marking); }
					else if(d < _strength) { strength += 0.1; xp -= CostToIncreaseAbility(res.getDouble("strength") + strength); }
					else if(d < _tackling) { tackling += 0.1; xp -= CostToIncreaseAbility(res.getDouble("tackling") + tackling); }
					else if(d < _agility) { agility += 0.1; xp -= CostToIncreaseAbility(res.getDouble("agility") + agility); }
					else if(d < _reaction) { reaction += 0.1; xp -= CostToIncreaseAbility(res.getDouble("reaction") + reaction); }
					else if(d < _shooting) { shooting += 0.1; xp -= CostToIncreaseAbility(res.getDouble("shooting") + shooting); }
					else if(d < _shotpower) { shotPower += 0.1; xp -= CostToIncreaseAbility(res.getDouble("shotpower") + shotPower); }
					else if(d < _vision) { vision += 0.1; xp -= CostToIncreaseAbility(res.getDouble("vision") + vision); }
					else if(d < _passing) { passing += 0.1; xp -= CostToIncreaseAbility(res.getDouble("passing") + passing); }
					else if(d < _technique) { technique += 0.1; xp -= CostToIncreaseAbility(res.getDouble("technique") + technique); }
					else if(d < _jumping) { jumping += 0.1; xp -= CostToIncreaseAbility(res.getDouble("jumping") + jumping); }
					else if(d < _stamina) { stamina += 0.1; xp -= CostToIncreaseAbility(res.getDouble("stamina") + stamina); }
					else if(d < _heading) { heading += 0.1; xp -= CostToIncreaseAbility(res.getDouble("heading") + heading); }
					else if(d < _handling) { handling += 0.1; xp -= CostToIncreaseAbility(res.getDouble("handling") + handling); }
					else if(d < _commandofarea) { commandOfArea += 0.1; xp -= CostToIncreaseAbility(res.getDouble("commandofarea") + commandOfArea); }
					else if(d < _shotstopping) { shotStopping += 0.1; xp -= CostToIncreaseAbility(res.getDouble("shotstopping") + shotStopping); }
					else if(d < _rushingout) { rushingOut += 0.1; xp -= CostToIncreaseAbility(res.getDouble("rushingout") + rushingOut); }
						
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			//Rest gemmes med spilleres pp
			pp += xp;
			pp = Hjaelper.round(pp, 2);
			
			String result = "UPDATE persons SET acceleration = acceleration +" + Hjaelper.round(acceleration, 2) + 
					", topspeed = topspeed + "+ Hjaelper.round(topSpeed, 2) +
					", agility = agility + "+ Hjaelper.round(agility, 2) +
					", strength = strength + "+ Hjaelper.round(strength, 2) +
					", jumping = jumping + "+ Hjaelper.round(jumping, 2) +
					", dribbling = dribbling + "+ Hjaelper.round(dribbling, 2) +
					", tackling = tackling + " + Hjaelper.round(tackling, 2) +
					", technique = technique + "+ Hjaelper.round(technique, 2) +
					", heading = heading + "+ Hjaelper.round(heading, 2) +
					", commandofarea = commandofarea + "+ Hjaelper.round(commandOfArea, 2) +
					", rushingout = rushingout + "+ Hjaelper.round(rushingOut, 2) +
					", shotstopping = shotstopping + "+ Hjaelper.round(shotStopping, 2) +
					", marking = marking + " + Hjaelper.round(marking, 2) + 
					", handling = handling +" + Hjaelper.round(handling, 2) + 
					", reaction = reaction + " + Hjaelper.round(reaction, 2) + 
					", shotpower = shotpower + " + Hjaelper.round(shotPower, 2) + 
					", shooting = shooting + " + Hjaelper.round(shooting, 2) + 
					", passing = passing + " + Hjaelper.round(passing, 2) + 
					", vision = vision + " + Hjaelper.round(vision, 2) + 
					", stamina = stamina + " + Hjaelper.round(stamina, 2) + 
					", playerpoints = " + pp +
					" WHERE id=" + person_id + ";";

			return result;
	}
	

	public static double CostToIncreaseAbility(double score){
		double cost = 0;
		if(score < 50) cost = 0.1;
		else if(score < 60) cost = 0.2;
		else if(score < 70) cost = 0.4;
		else if(score >= 70) cost = 0.6;
		return cost;	
	}
	
}
