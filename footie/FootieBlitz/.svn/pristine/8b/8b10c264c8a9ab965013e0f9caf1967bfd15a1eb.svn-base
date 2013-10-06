package websocket;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.MatchEngine;
import model.PlayerAction;

import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.api.WebSocketConnectorStatus;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.config.JWebSocketConfig;
import org.jwebsocket.factory.JWebSocketFactory;
import org.jwebsocket.kit.RawPacket;
import org.jwebsocket.kit.WebSocketServerEvent;
import org.jwebsocket.listener.WebSocketServerTokenEvent;
import org.jwebsocket.listener.WebSocketServerTokenListener;
import org.jwebsocket.server.TokenServer;
import org.jwebsocket.token.Token;

public class WebsocketServer implements WebSocketServerTokenListener {

    private TokenServer tokenServer;
    public volatile boolean run = true;
    ArrayList<WebsocketClientThread> clients = new ArrayList<WebsocketClientThread>();
    String namespace = "com.footie.websocket.live.WebsocketServer";
    boolean serverReady = false;
    MatchEngine matchEngine;
    
    public WebsocketServer(MatchEngine matchEngine){
    	this.matchEngine = matchEngine;
    }
    
    public TokenServer getTokenServer() {
        return tokenServer;
    }

    public void init() {
        try {
        	JWebSocketFactory.printCopyrightToConsole();
        		
        	JWebSocketConfig.initForConsoleApp(new String[0]);
            JWebSocketFactory.start();
            tokenServer = (TokenServer) JWebSocketFactory.getServer("ts0");
            if (tokenServer != null) {
                System.out.println("server was found");
                tokenServer.addListener(this);
                serverReady = true;
            } else {
                System.out.println("server was NOT found");

            }
        } catch (Exception lEx) {
            lEx.printStackTrace();
        }
    }

    public void processToken(WebSocketServerTokenEvent serverTokenEvent, Token token) {
    	System.out.println("Client '" + serverTokenEvent.getSessionId() + "' sent Token: '" + token.toString() + "'.");
        // here you can interpret the token type sent from the client according to your needs.
        String lNS = token.getNS();
        String lType = token.getType();
        
     // check if token has a type and a matching namespace
        if (lType != null && namespace.equals(lNS)) {
          // create a response token
          Token lResponse = serverTokenEvent.createResponse(token);
          if ("getInfo".equals(lType)) {
            // if type is "getInfo" return some server information
          }
          else if ("ping".equals(lType)) {
        	  sendPacket("-", "fbp");
        	  matchEngine.streamInject += "ping:";
          } 
          else if ("run".equals(lType)) {
        	  try{
	        	  int id = token.getInteger("id");
	        	  int x = token.getInteger("x");
	        	  int y = token.getInteger("y");
	        	  System.out.println("Run received: " + id + ", " + x + ", " + y);
//	        	  matchEngine.startRun(id, x, y);
        	  }
        	  catch (Exception e) {
        		  System.out.println("************" + e.toString());
        	  }

          }
          else if("init".equals(lType)){
        	  String initData = matchEngine.getInitStream();
        	  initData = initData.replace("\"", "\\\\\'");

        	  String json = "{\"ns\":\"fbinit\",\"id\":123,\"data\":\"" + initData + "\"}";
        	  System.out.println(json);
//        	  WebSocketPacket wsPacket;
        	  lResponse.setNS("fbinit");
        	  lResponse.setString("data", initData);
//        		  wsPacket = new RawPacket(json, "UTF-8");
//        		  getTokenServer().sendPacket(event.getConnector(), wsPacket);
          }
          else {
            // if unknown type in this namespace, return corresponding error message
            lResponse.setString("msg", "Token type '" + lType + "' not supported in namespace '" + lNS + "'.");
          }
          serverTokenEvent.sendToken(lResponse);
        }
    }

    public void processClosed(WebSocketServerEvent arg0) {
    }

    public void processOpened(WebSocketServerEvent event) {
    	System.out.println("client connected... " + event.getSessionId());
    	
    }

    public void sendPacket(String data, String namespace) {
    	if (serverReady){
    		
    		Map lConnectorMap = getTokenServer().getAllConnectors();

    		Collection<WebSocketConnector> lConnectors = lConnectorMap.values();
    		String json = "{\"ns\":\"" + namespace + "\",\"id\":123,\"data\":\"" + data + "\"}";
    		WebSocketPacket wsPacket;
			try {
				wsPacket = new RawPacket(json, "UTF-8");
			
	    		for (WebSocketConnector wsc : lConnectors) {
	    			
//	    			System.out.println(wsc.getId() + " -> " + json);
	    			getTokenServer().sendPacket(wsc, wsPacket);        
	    		}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    	}
    }

    public void processPacket(WebSocketServerEvent arg0, WebSocketPacket arg1) {
        System.out.println("packet received " + arg1.getString());      
    }

    public void start() {

        init();
        sendPacket("server ready", "fbi");
        
        while (run) {
        	try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            try {
                Object c = getTokenServer().getAllConnectors();

                Map lConnectorMap = getTokenServer().getAllConnectors();
                List<Map> lResultList = new ArrayList<Map>();
                Collection<WebSocketConnector> lConnectors = lConnectorMap.values();
                for (WebSocketConnector lConnector : lConnectors) {
                    Map lResultItem = new HashMap<String, Object>();
                    lResultItem.put("port", lConnector.getRemotePort());
                    lResultItem.put("unid", lConnector.getNodeId());
                    lResultItem.put("username", lConnector.getUsername());
                    lResultItem.put("isToken", lConnector.getBoolean(TokenServer.VAR_IS_TOKENSERVER));
                    lResultList.add(lResultItem);
                }
                for (Map m : lResultList) {
//                    System.out.println(m);
                }

//                sendPacket("server pong", "fbi");
            } catch (Exception ex) {
                Logger.getLogger(WebsocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
