package websocket;

import model.MatchEngine;

public class WebsocketServerThread implements Runnable{

	WebsocketServer wsServer;
	MatchEngine matchEngine;
	
	public WebsocketServerThread(MatchEngine matchEngine){
		this.matchEngine = matchEngine;
	}
	
//	@Override
	public void run() {
		wsServer = new WebsocketServer(matchEngine);
		wsServer.start();
	}

	public void sendPacket(String data, String namespace){
		wsServer.sendPacket(data, namespace);
	}
}
