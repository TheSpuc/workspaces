package ai;

import java.util.Random;

import model.Player;
import model.Sysout;

public class AIHelper {
	
	/**
	 * returns the error in direction to apply to a pass/shot/header/dribble etc.
	 * @param ballDif How difficult the pass, shot, dribble etc. is because of ball movement (1-100) 
	 * @param pressure How difficult the pass, shot, dribble etc. is because of pressure from opponents (1-100)
	 * @param timeOnBall How difficult the pass, shot, dribble etc. is because of the amount of time the player has had on the ball (1-100)
	 * @param player The player who is passing, shooting etc.
	 * @param action The action the player is taking (pass, shot etc.)
	 **/
	public static double directionError(int ballDif, int pressure, Player player, PlayerAction action){
		double result = 0;
		double powerIntent = 50; //TODO powerIntent is how much power the player intends to put on the ball, compared to how much he can. (1-100).
		switch (action){
			case Shot: 
				powerIntent = 100;
				break;
		}
		//Set lasttouch on ball so we get the correct timeOnBall
		player.getBold().setLastTouch(player);
		int timeOnBall = player.getBold().getTimeOnBallDiff();
		
		Sysout.print("Player: technique(" + player.technique + "), shooting(" + player.shooting + ")", "directionError");
		Sysout.print("ballDif: " + ballDif, "directionError");
		Sysout.print("pressure: " + pressure, "directionError");
		Sysout.print("timeOnBall: " + timeOnBall, "directionError");
		Sysout.print("action: " + action, "directionError");
		
		Random r = new Random();

		//Even under perfect circumstances a player with poor skill will often make bigger errors than a player with good skill
		//Should be between 0.03 - 0.08
		double skillEffect = 0.03;
		
		//The player's technique relates to how difficult the ball is
		ballDif /= (player.technique / 50);
		
		switch (action){
			case Pass:
				//For a pass the player's vision relates to how much time on the ball he has
				timeOnBall /= (player.vision / 50.0);
				//Pressure relates to the passing skill
				pressure /= (player.passing / 50.0);
				
				skillEffect += (100 - player.passing) / (3000.0 - (powerIntent * 10));
				break;
			case ThroughPass:
				//For a pass the player's vision relates to how much time on the ball he has
				timeOnBall /= (player.vision / 50.0);
				//Pressure relates to the passing skill
				pressure /= (player.passing / 50.0);
				
				skillEffect += (100 - player.passing) / (3000.0 - (powerIntent * 10));
			break;
			case Shot:
				timeOnBall /= (player.shooting / 50.0);
				pressure /= (player.shooting / 50.0);
				skillEffect += (100 - player.shooting) / (3000.0 - (powerIntent * 10));
			break;
			case Dribble:
				timeOnBall /= (player.vision / 50.0);
				pressure /= (player.dribbling / 50.0);
				skillEffect += (100 - player.dribbling) / (3000.0 - (powerIntent * 10));
			break;
		}
		
		Sysout.print("ballDif: " + ballDif, "directionError");
		Sysout.print("pressure: " + pressure, "directionError");
		Sysout.print("timeOnBall: " + timeOnBall, "directionError");
		Sysout.print("skillEffect: " + skillEffect, "directionError");
		
		int totalDifficulty = ballDif + pressure + timeOnBall;
		//effect should go from 0-0.2
		double effect = (double)totalDifficulty / 1500.0;
		result = r.nextGaussian() * (skillEffect + effect);
		
		Sysout.print("totalDifficulty: " + totalDifficulty, "directionError");
		Sysout.print("effect: " + effect, "directionError");
		Sysout.print("result: " + result, "directionError");
		
		return result;
	}
	
	public enum PlayerAction{
		Pass,
		ThroughPass,
		Shot,
		Header,
		Dribble
	}
}
