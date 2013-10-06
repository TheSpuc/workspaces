package websocket;

import org.jwebsocket.api.WebSocketConnector;

public class WebsocketClientThread implements Runnable {

	private volatile boolean run = true;
	private WebSocketConnector connector;
	WebsocketServer server;
	 
	public WebsocketClientThread(WebSocketConnector connector, WebsocketServer server){
		this.connector = connector;
		this.server = server;
	}
	
//	@Override
	public void run() {
		System.out.println("***********Client '" + connector.getId() + "' connected.*********");
		 while (run) {
			 
		 }
	}

	public WebSocketConnector getConnector() {
		return connector;
	}

	public void setConnector(WebSocketConnector connector) {
		this.connector = connector;
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}
	
	
}
