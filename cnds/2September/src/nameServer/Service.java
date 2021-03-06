package nameServer;

import java.net.InetAddress;
import java.util.Map;


public class Service {

	private static Service service;
	private Dao dao = Dao.getInstance();

	private Service(){
	}

	public synchronized static Service getInstance(){
		if(service == null){
			service = new Service();
		}
		return service;
	}

	/**
	 * Adding a new nameClient to the DNS server
	 * @param key
	 * @param sentence
	 * @param ip
	 * @param port
	 */
	public synchronized void addNameClient(String key, String sentence, InetAddress ip){
		dao.addNameClient(key, new NameClient(sentence, ip));
	}

	public NameClient getNameClient(String key){
		return dao.getNameClient(key);
	}

	/**
	 * Getting the specific ip address, for a saved nameClient
	 * @param key
	 * @return
	 */
	public InetAddress getIp(String key){
		InetAddress result = null;
		NameClient temp = dao.getNameClient(key);
		if(temp != null){
			result = temp.getIp();
		}
		return result;
	}

	/**
	 * For getting all registered clients on the service right now
	 * @return
	 */
	public String getClients(){
		String toSend = "";
		Map<String, NameClient> nameClients = dao.getClients();
		for(NameClient n : nameClients.values()){
			toSend += n + "; ";
		}
		return toSend;
	}
}
