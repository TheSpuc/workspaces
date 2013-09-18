package nameServer;

import java.net.InetAddress;

public class NameClient {
	
	private String name;
	private InetAddress ip;
	
	public NameClient(String name, InetAddress ip){
		this.name = name;
		this.ip = ip;
	}
	
	public String getName(){
		return name;
	}
	
	public InetAddress getIp(){
		return ip;
	}
	
	@Override
	public String toString(){
		return name + ", " + ip;
	}
}
