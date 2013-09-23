package nameServer;

import java.util.HashMap;
import java.util.Map;

public class Dao {
	
	private Map<String, NameClient> map;
	private static Dao dao;
	
	private Dao(){
		map = new HashMap<>();
	}
	
	public synchronized static Dao getInstance(){
		if(dao == null){
			dao = new Dao();
		}
		return dao;
	}
	
	public void addNameClient(String key, NameClient value){
		map.put(key, value);
	}
	
	public NameClient getNameClient(String key){
		return map.get(key);
	}
	
	public Map<String, NameClient> getClients(){
		synchronized (dao) {
			return new HashMap<>(map);
		}
	}
	
}
