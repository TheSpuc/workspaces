package states;

import model.Hjaelper;
import model.Player;
import model.PlayerAction;
import model.PlayerRole;

//Bruges mellem andre states og som første state.
public class StateNothing implements PlayerState	{

	Player player;

	public StateNothing(Player player) {
		this.player = player;
	}

	public void update(){

		if (player.getRole().equals(PlayerRole.GK)){
			player.getStateKeeper().update();
		}else{
			//Har jeg bolden?
			if (Hjaelper.Distance(player.x, player.y, player.getBold().getX(), player.getBold().getY()) < 8 &&
					player.getBold().getZ() < player.height){//t¾t nok pŒ bolden	

				if (player.getBold() != null){
					player.getStateHasBall().update();
				}
			}
			// Har mit hold bolden
			else if (player.getBold().getLastTouch() != null && player.getBold().getLastTouch().getMyTeam().equals(player.getMyTeam())){

				//skal jeg markere alligevel?
				if (player.getCurrentlyMarking().size() > 0 && player.getBold().getPassTo() != player && !player.getPlayerAction().equals(PlayerAction.dribble)){
					player.getStateOppHasBall().update();
				}
				else{
					player.getStateTeamHasBall().update();
				}
			}
			//har modstanderne bolden
			else {
				player.getStateOppHasBall().update();
			}

			player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));
		}
	}

	public String toString(){
		return "Nothing";
	}
}
