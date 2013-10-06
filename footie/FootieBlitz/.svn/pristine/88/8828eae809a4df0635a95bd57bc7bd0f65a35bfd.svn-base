package data;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class TransferList {
//	CREATE TABLE transferList (
//			id SERIAL NOT NULL,
//			person_id INTEGER REFERENCES persons(id) NOT NULL,
//			contract_id INTEGER REFERENCES contracts(id) NOT NULL,
//			playerPrice INTEGER DEFAULT NULL,
//			message varchar(255) DEFAULT NULL,
//			dateListed timestamp DEFAULT NULL,
//			dateUpdated timestamp DEFAULT NULL
//			);
	// INSERT INTO transferlist 
	//(person_id, contract_id, playerPrice, message, dateListed, dateUpdated)
	// VALUES (1005, 3247, 80000, 'Go', now(), now()) 
	// VALUES (1040, 3243, 80500, 'Go nu', now(), now()) 
	// VALUES (944, 1082, 5500, 'Ikke så god', now() - interval '200 days', now()) 
	
	public static List<LinkedHashMap<String,Object>> getTransferList(){
		List<LinkedHashMap<String,Object>> result;
		result = DAO.selectFromDBWithQuery("SELECT * FROM transferlist;");
		return result;		
	}
	
	public static void listPlayer(int playerId, int contractId, int playerPrice, String message, Date updated){
		List<LinkedHashMap<String,Object>> listedPlayer;
		listedPlayer = DAO.selectFromDBWithQuery("SELECT * FROM transferlist WHERE person_id = " + playerId + ";");
		String logMessage = "TransferList.listPlayer started: ";
		
		if(listedPlayer.isEmpty()){ //The player is not listed. This is a new listing			
			createNewListing(playerId, contractId, playerPrice, message);
			logMessage += "Player with id " + playerId + " is transferlisted (new listing). ";
		}
		else {			
						
			updateListing(playerId, contractId, playerPrice, message);			
		}
	}
	
	public static void createNewListing(int playerId, int contractId, int playerPrice, String message){
		DAO.updateDBWithQuery("INSERT INTO transferlist " +
				"(person_id, contract_id, playerPrice, message) " +
				"VALUES (" + playerId + ", " + contractId + ", " + playerPrice + ", '" + message + "');");		
	}
	
	public static void updateListing(int playerId, int contractId, int playerPrice, String message){
		DAO.updateDBWithQuery("UPDATE transferlist set " +
				"contract_id = " + contractId + "," +
				"playerprice = " + playerPrice + ", " +
				"message = '" + message + "'," +
				"dateupdated = now() WHERE person_id = " + playerId + ";");
	}
	
	public static void deletePlayerListings(int playerId){
		DAO.updateDBWithQuery("DELETE FROM transferlist WHERE person_id = " + playerId + ";");
	}
	
	/**
	 * Deletes all entries of players on expired contracts
	 * Makes sure that players can't be listed on the transferlist on an expired contract
	 */
	public static void deleteListingsWithExpiredContracts(){
		List<LinkedHashMap<String,Object>> listedPlayer;
		listedPlayer = DAO.selectFromDBWithQuery("SELECT * FROM transferlist t LEFT JOIN contracts c ON t.contract_id = c.id WHERE c.enddate < now()");
		String logMessage = "TransferList.deletePlayersOnExpiredContracts started: ";
		
		int playerId, contractId;
		for(int i = 0; i < listedPlayer.size(); i++){
			playerId = 0;
			contractId = 0;
			playerId = (Integer)listedPlayer.get(i).get("person_id");
			contractId = (Integer)listedPlayer.get(i).get("contract_id");
			if(playerId != 0 && contractId != 0){
				DAO.updateDBWithQuery("DELETE FROM transferlist WHERE person_id = " + playerId + " AND contract_id = " + contractId + ";");
			}
		}
	}
	//SELECT * FROM transferlist t LEFT JOIN contracts c ON t.contract_id = c.id WHERE c.enddate < now()
	
}
