package websocket;

import java.util.Collection;
import javolution.util.FastList;
import org.apache.log4j.Logger;
import org.jwebsocket.api.PluginConfiguration;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.kit.CloseReason;
import org.jwebsocket.kit.PlugInResponse;
import org.jwebsocket.logging.Logging;
import org.jwebsocket.plugins.TokenPlugIn;
import org.jwebsocket.token.Token;

public class LiveStreaming extends TokenPlugIn {

	//change the Apache logger to your Classname
	private static Logger mLog = Logging.getLogger(LiveStreaming.class);
	// if you change the namespace, don't forget to change the ns_sample!
	private final static String NS_SAMPLE = "com.footie.websocket.live.LiveStreaming";

	private Collection<WebSocketConnector> mClients;
	
	//Constructor
	public LiveStreaming(PluginConfiguration aConfiguration) {
		super(aConfiguration);
		if (mLog.isDebugEnabled()) {
			mLog.debug("Instantiating footie PlugIn ...");
		}
		// specify default name space for sample plugin
		this.setNamespace(NS_SAMPLE);
		
		mClients = new FastList<WebSocketConnector>().shared();
	}

	//Method is called when a token has to be progresed
	@Override
	public void processToken(PlugInResponse aResponse, WebSocketConnector aConnector, Token aToken) {
		// get the type of the token
		// the type can be associated with a "command"
		String lType = aToken.getType();

		// get the namespace of the token
		// each plug-in should have its own unique namespace
		String lNS = aToken.getNS();

		// check if token has a type and a matching namespace
		if (lType != null && lNS != null && lNS.equals(getNamespace())) {
			if (lType.equals("getAuthorName")) {//if the request is "getAuthorName"
				mLog.debug("Authorname was requested");
				Token lResponse = createResponse(aToken);//create the response
				//add the variable "name" with the value "Laurid Meyer" to the response
				lResponse.setString("name", "Laurid Meyer");
				sendToken(aConnector, aConnector, lResponse);//send the response
			}else if (lType.equals("calculate")) {//if the request is "calculate"
				int square= aToken.getInteger("myNumber");//get the Value of the variable "myNumber"
				mLog.debug("trying to calculate:"+square);
				square*=square;// square the input
				Token lResponse = createResponse(aToken);//create the response
				//add the variable "calNumber" with the value -square- to the response
				lResponse.setInteger("calNumber",square);
				sendToken(aConnector, aConnector, lResponse);//send the response
			}
		}
	}
	
	public void broadcast(Token aToken, WebSocketConnector except) {
		for (WebSocketConnector lConnector : mClients) {
			if(lConnector!=except){
				getServer().sendToken(lConnector, aToken);
			}
		}
	}
	
	public void broadcastToAll(Token aToken) {
		for (WebSocketConnector lConnector : mClients) {
			getServer().sendToken(lConnector, aToken);
		}
	}
	
	@Override
	public void connectorStarted(WebSocketConnector aConnector) {
	    // this method is called every time when a client
	    // connected to the server
		mClients.add(aConnector);
		if (mLog.isDebugEnabled()) {
			mLog.debug("new Client has registered: " + aConnector.getId());
		}
	}
	
	@Override
	public void connectorStopped(WebSocketConnector aConnector, CloseReason aCloseReason) {
		// ensure that we do not keep any dead connectors in the list
		mClients.remove(aConnector);
		if (mLog.isDebugEnabled()) {
			mLog.debug("client " + aConnector.getId() + " is gone");
		}
	}
}