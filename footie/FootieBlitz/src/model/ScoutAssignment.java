package model;

public class ScoutAssignment {

	public int id, userId, personId, countryId, hoursSinceStart, detailLevel;
	
	public ScoutAssignment(int id, int userId, int personId, int countryId, int hoursSinceStart, int detailLevel) {
		super();
		this.id = id;
		this.userId = userId;
		this.personId = personId;
		this.countryId = countryId;
		this.hoursSinceStart = hoursSinceStart;
		this.detailLevel = detailLevel;
	}
	
}
