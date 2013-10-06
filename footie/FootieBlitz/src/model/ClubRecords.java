package model;

public class ClubRecords {

	public int youngestPlayer = -1;
	public int oldestPlayer = 0;
	public int goalsInAMatch = 0;
	public int assistsInAMatch = 0;
	public int goalsInASeason = 0;
	public int assistsInASeason = 0;
	public int highestAttendance = 0;
	public int lowestAttendance = -1;
	public int losingStreak = 0;
	public int winningStreak = 0;
	public int unbeatenRun = 0;
	public int currLosingStreak = 0;
	public int currWinningStreak = 0;
	public int currUnbeatenRun = 0;
	
	public static enum ClubRecordType{
		
		HighestFeePaid, // 0
		HighestFeeReceived,
		HighestWagePaid,
		BiggestBudget,
		WinningStreak,
		UnbeatenRun, // 5
		LosingStreak,
		GoalsInSeason, 
		AssistsInSeason,
		RatingInSeason,
		GoalsInMatch, // 10
		YoungestPlayer,
		OldestPlayer, 
		HighestAttendance,
		LowestAttendance,
		CurrWinningStreak, // 15
		CurrUnbeatenRun,
		CurrLosingStreak
	}
}
