package trainingmodules;

import java.util.ArrayList;

import model.Bold;
import model.Team;

public interface TrainingModule {

	public void update();
	public Team getTeamA();
	public Team getTeamB();
	public ArrayList<Bold> getBolde();
	public void clickReceived(int x, int y);
}
