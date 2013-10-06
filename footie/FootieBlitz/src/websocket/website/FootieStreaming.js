var currFr = 0;
var secs = 0;
var	mins = 0;
var	goalsA = 0;
var	goalsB = 0;
var	commentary = new Array();
var	shootoutShotsA = new Array();
var	shootoutShotsB = new Array();
var	shootout = false;
var	boldX = 0;
var	boldY = 0;
var	boldZ = 0;
var prevBoldZ = 0;
var	teamA = new Object();
var	teamB = new Object();
var	initDone = false;
var pitchWidth = 900;
var pitchHeight = 500;
var dataBuffer = new Array();
var svgxmlns = "http://www.w3.org/2000/svg";
var drawCanvas = false;
var drawSVG = true;
var shirtNumberBaseY = 4;
var pitchWidth = 513;
var pitchLength = 880;
var shiftX = 20;
var shiftY = 15;
var viewState = 0;
var avgBufferSize = 15;
var pingSent = 0;

var websocketID = "footie",
websocketClient = null,
wsUsername = null,
wsPassword = null;

var selectedPlayerID = "";
var pitchOffset;
var grassOffset;
var mouseX = 0;
var mouseY = 0;

var action = 0;

$(document).ready(function() {

	document.onselectstart = function(){ return false; }
	initPitch();
	initPage();
	
	pitchOffset = $("#pitchLines").offset();
	grassOffset = $("#grass").offset(); 
	
	$("#grass").mousemove(function(e) {
		if (selectedPlayerID.length > 0){
			mouseX = e.pageX - grassOffset.left;
			mouseY = e.pageY - grassOffset.top;
		}
//		e.preventDefault();
	});
	
	$("#grass").mouseleave(function(e) {
//		selectedPlayerID = "";
//		$("#commandLine").attr("x1", 0);
//		$("#commandLine").attr("y1", 0);
//		$("#commandLine").attr("x2", 0);
//		$("#commandLine").attr("y2", 0);
//		e.preventDefault();
	});
	
	$(document).mouseup(function(e) {
		if (selectedPlayerID.length > 0){
			var relX = e.pageX - pitchOffset.left;
			var relY = e.pageY - pitchOffset.top;
			
			console.log("up selectedPlayerID: " + selectedPlayerID);
			
			$("#commandLine").attr("x1", 0);
			$("#commandLine").attr("y1", 0);
			$("#commandLine").attr("x2", 0);
			$("#commandLine").attr("y2", 0);
			console.log("up selectedPlayerID: " + selectedPlayerID);
			
			try{
				var id = parseInt(selectedPlayerID.replace("teamAplayer", ""));
				console.log("id: " + id + " relX: " + relX + "relY: " + relY);
				websocketClient.startRun(id, relX, relY);
			}
			catch(e){
				console.log(e.toString());
			}
			selectedPlayerID = "";
		}
	});
	
//	$("#commandLine").mouseup(function(e) {
////		   var relX = e.pageX - pitchOffset.left;
////		   var relY = e.pageY - pitchOffset.top;
//		   console.log("up selectedPlayerID: " + selectedPlayerID);
//		selectedPlayerID = "";
//		$("#commandLine").attr("x1", 0);
//		$("#commandLine").attr("y1", 0);
//		$("#commandLine").attr("x2", 0);
//		$("#commandLine").attr("y2", 0);
////		e.preventDefault();
//		console.log("up selectedPlayerID: " + selectedPlayerID);
//	});
	
	
	$("#lnkStartStop").click(function(e) {
		
		if ($("#lnkStartStop").html() == "start"){
			logon();
			$("#lnkStartStop").html("stop");
		}
		else{
			if( websocketClient ) {
				websocketClient.close();
			}
			$("#lnkStartStop").html("start");
		}
		e.preventDefault();
		
	});
	
	$("#lnkPing").click(function() {
		
		pingSent = +new Date();
		websocketClient.pingServer();
	});

	$("#lnkTransform").click(function() {
		
		if (viewState == 0){
			viewState = 1;
			$("#transformG").attr("transform", "rotate(90 460 271) translate(200, 200) scale(0.5)");
		}
		else{
			viewState = 0;
			$("#transformG").attr("transform", "");
		}
		
	});
	
	$("#slSize").change(function() {
		console.log("Setting view scale to " + $("#slSize").val());
		setViewSize($("#slSize").val());
	});
});//document.ready end

function setViewSize(scale){
	$("#transformG").attr("transform", "scale(" + scale + ")");
}

//Method is called when the page is loaded
function initPage() {
	// check if WebSockets are supported by the browser
	if( jws.browserSupportsWebSockets() ) {
		console.log("Websockets are supported");
		// instaniate new TokenClient, either JSON, CSV or XML
		websocketClient = new jws.jWebSocketJSONClient();
	} else {
		console.log("Websockets are NOT supported");
	}
}

function logon() {
	var wsURL = jws.getDefaultServerURL();// get the default server url
	//wsURL = "ws://80.198.124.75:8787/jWebSocket/jWebSocket";
	console.log("Connecting to " + websocketID + " at " + wsURL + "..." );

	//get the guest username and password
	wsUsername=jws.GUEST_USER_LOGINNAME;
	wsPassword=jws.GUEST_USER_PASSWORD;

	// try to establish the connection to jWebSocket server
	websocketClient.logon( wsURL, wsUsername, wsPassword, {
		OnOpen: function( aEvent ) {
			console.log("Connection to " + websocketID + " established." );
			console.log("calling getinit");
			if (!initDone) {
				websocketClient.getInit();
			}
		},

		// OnMessage callback
		OnMessage: function( aEvent, aToken ) {
//			console.log(( aToken ? aToken.type : "-" ) + ": " +	aEvent.data);

			if( websocketClient.isLoggedIn() ) {
//				console.log("User is authenticated");
			} else {
//				console.log("User is connected");
			}
//			console.log(websocketClient.getId() + "&nbsp;"	+ ( jws.browserSupportsNativeWebSockets ? "(native)" : "(flashbridge)" ));
			if( aToken ) {
				// is it a response from a previous request of this client?
				if( aToken.type == "response" ) {
					if( aToken.reqType == "login" ) {// figure out of which request
						if( aToken.code == 0 ) {
							console.log("Welcome '" + aToken.username + "'" );
						}else {
							console.log("Error logging in '" + eUsername.value + "': " + aToken.msg );
						}
					}
				// is it an event w/o a previous request ?
				}else if( aToken.type == "goodBye" ) {
					console.log("good bye (reason: " + aToken.reason + ")!" );
				}
			}
		},
		// OnClose callback
		OnClose: function( aEvent ) {
			console.log("Disconnected from " + websocketID + ".");
		}

	});
}



//Position pitch lines
function initPitch(){
	$("#pitchLines").attr("x", shiftX);
	$("#pitchLines").attr("y", shiftY);
	$("#pitchLines").attr("width", pitchLength);
	$("#pitchLines").attr("height", pitchWidth);
	$("#midLine").attr("x1", pitchLength / 2 + shiftX);
	$("#midLine").attr("x2", pitchLength / 2 + shiftX);
	$("#midLine").attr("y1", shiftY);
	$("#midLine").attr("y2", pitchWidth + shiftY);
	$("#area1").attr("x", shiftX);
	$("#area1").attr("y", shiftY + pitchWidth / 2 - 322 / 2);
	$("#area2").attr("x", shiftX + pitchLength - 132);
	$("#area2").attr("y", shiftY + pitchWidth / 2 - 322 / 2);
	$("#goalArea1").attr("x", shiftX);
	$("#goalArea1").attr("y", shiftY + pitchWidth / 2 - 146 / 2);
	$("#goalArea2").attr("x", shiftX + pitchLength - 44);
	$("#goalArea2").attr("y", shiftY + pitchWidth / 2 - 146 / 2);
	$("#centerCircle").attr("cx", shiftX + pitchLength / 2);
	$("#centerCircle").attr("cy", shiftY + pitchWidth / 2);
	$("#centerDot").attr("cx", shiftX + pitchLength / 2);
	$("#centerDot").attr("cy", shiftY + pitchWidth / 2);
	var areaDot1X = shiftX + 88 - 1;
	var areaDotY = shiftY + pitchWidth / 2 - 1;
	var areaDot2X = shiftX + pitchLength - 88 + 1;
	$("#areaDot1").attr("cx", areaDot1X);
	$("#areaDot1").attr("cy", areaDotY);
	$("#areaDot2").attr("cx", areaDot2X);
	$("#areaDot2").attr("cy", areaDotY);
	$("#areaCircle1").attr("cx", areaDot1X);
	$("#areaCircle1").attr("cy", areaDotY);
	$("#areaCircle2").attr("cx", areaDot2X);
	$("#areaCircle2").attr("cy", areaDotY);
	
	$("#goal1").attr("x", shiftX - 10);
	$("#goal1").attr("y", shiftY + pitchWidth / 2 - 59 / 2);
	$("#goal2").attr("x", shiftX + pitchLength);
	$("#goal2").attr("y", shiftY + pitchWidth / 2 - 59 / 2);
}

function polarToCartesian(centerX, centerY, radius, angleInDegrees) {
	  var angleInRadians = angleInDegrees * Math.PI / 180.0;
	  var x = centerX + radius * Math.cos(angleInRadians);
	  var y = centerY + radius * Math.sin(angleInRadians);
	  return [x,y];
	}

//Method to parse a frame sent form the server and update graphics
	 function parseFrame(data){
		  
		 if (initDone){
		 var ln = data.split(":");
			
			var frameID = 0;
			try{
//				frameID = parseInt(ln[0]);
			}catch(e){
				console.log("frameId not recognized: " + e);
			}

			if (ln[0].length > 0){
				currFr = frameID;

				var j = 0;
				while (j < ln.length){
					try{
						if (ln[j] == "s"){
							j++;
							secs = parseInt(ln[j]);
							updateTimeText();
						}
						else if(ln[j] == "m"){
							j++;
							mins = parseInt(ln[j]);
							updateTimeText();
						}
						else if(ln[j] ==  "sc"){
							j++;
							goalsA = parseInt(ln[j]);
							j++;
							goalsB = parseInt(ln[j]);
							updateScoreText();
						}
						else if(ln[j] == "c"){
							j++;
							commentary.push(ln[j]);
						}
						else if(ln[j] == "lu"){
							//lineup 
						}
						else if(ln[j] == "Shout"){
							shootout = true;
						}
						else if(ln[j] == "shoutA"){
							shootoutShotsA = new Array();
							j++;
							try{
								for (var iq = 0, len = ln[j].length; iq < len; iq++) {
									shootoutShotsA.push(parseInt(ln[j][iq]));
								}
							}
							catch (e){
								console.log("shoutA error: " + ln[j]);
								console.log(e);
							}
						}
						else if(ln[j] == "shoutB"){
							shootoutShotsB = new Array();
							j++;
							try{
								for (var iq = 0, len = ln[j].length; iq < len; iq++) {
									shootoutShotsB.push(parseInt(ln[j][iq]));
								}
							}catch(e){
								console.log("shoutB: " + ln[j]);
								console.log(e);
							}
						}
						else if (ln[j] == "b"){
							j++;
							boldX = (parseInt(ln[j]));
							j++;
							boldY = (parseInt(ln[j]));
							j++;
							prevBoldZ = boldZ;
							boldZ = parseInt(ln[j]);
						}
						else if (ln[j] == "ping"){
							var frameResponseTime = +new Date() - pingSent;
							$("#spnPingResult").html($("#spnPingResult").html() + " - frame response time: " + frameResponseTime);
						}
						else{
							var id = ln[j];
							j++;
							
							if (teamA.players[id] !== undefined){
								teamA.players[id].x = parseInt(ln[j]);
								j++;
								teamA.players[id].y = parseInt(ln[j]);
							}
							
							if (teamB.players[id] !== undefined){
								teamB.players[id].x = parseInt(ln[j]);
								j++;
								teamB.players[id].y = parseInt(ln[j]);
							}
						}
					}
					catch (e){
						console.log(ln[j]);
						console.log(e);
					}
					j++;
				}
			}
			else
				console.log("error parsing: " + data);
			
			if (drawSVG){
				var SIZE_FACTOR = 1;
				var ballSize = Math.round(4 / SIZE_FACTOR + boldZ / (80 * SIZE_FACTOR));
				var ballShadowSize = Math.round(4 - boldZ / (350 * SIZE_FACTOR));
				
				$("#ball").attr("cx", boldX + shiftX);
				$("#ball").attr("cy", boldY + shiftY);
				$("#ball").attr("r", ballSize);
				$("#ballshadow").attr("cx", boldX + shiftX + boldZ / (100 * SIZE_FACTOR));
				$("#ballshadow").attr("cy", boldY + shiftY + boldZ / (100 * SIZE_FACTOR));
				$("#ballshadow").attr("r", ballShadowSize);
				
				if (prevBoldZ <= 180 && boldZ > 180){
					$("#transformG").append($("#ballshadow"));
					$("#transformG").append($("#ball"));
				}
				if (prevBoldZ > 180 && boldZ <= 180){
					$("#lowBallG").append($("#ballshadow"));
					$("#lowBallG").append($("#ball"));
				}
				
				for (var i = 0; i < teamA.playerList.length; i++){
					$("#teamAplayer" + teamA.playerList[i].id).attr("cx", teamA.playerList[i].x + shiftX);
					$("#teamAplayer" + teamA.playerList[i].id).attr("cy", teamA.playerList[i].y + shiftY);
					$("#teamAplayerNumber" + teamA.playerList[i].id).attr("x", teamA.playerList[i].x + shiftX);
					$("#teamAplayerNumber" + teamA.playerList[i].id).attr("y", teamA.playerList[i].y + shiftY + shirtNumberBaseY);
				}
				for (var i = 0; i < teamB.playerList.length; i++){
					$("#teamBplayer" + teamB.playerList[i].id).attr("cx", teamB.playerList[i].x + shiftX);
					$("#teamBplayer" + teamB.playerList[i].id).attr("cy", teamB.playerList[i].y + shiftY);
					$("#teamBplayerNumber" + teamB.playerList[i].id).attr("x", teamB.playerList[i].x + shiftX);
					$("#teamBplayerNumber" + teamB.playerList[i].id).attr("y", teamB.playerList[i].y + shiftY + shirtNumberBaseY);
				}
				
				if (selectedPlayerID.length > 0){
					$("#commandLine").attr("x1", $("#" + selectedPlayerID).attr("cx"));
					$("#commandLine").attr("y1", $("#" + selectedPlayerID).attr("cy"));
					
					$("#commandLine").attr("x2", mouseX);
					$("#commandLine").attr("y2", mouseY);
				}
				
				$("#rectBufferSize").attr("width", dataBuffer.length);
				$("#debug").html(dataBuffer.length);
			}
			
			if (drawCanvas){
				var c=document.getElementById("myCanvas");
				var ctx=c.getContext("2d");
				ctx.fillStyle="#FFFFFF";
				ctx.fillRect(0,0,pitchWidth,pitchHeight);
				ctx.fillStyle="#FF0000";
				ctx.beginPath();
				ctx.arc(boldX,boldY,5, 0, 2 * Math.PI, false);
				ctx.closePath();
				ctx.fill();
				ctx.fillStyle="#000000";
				ctx.lineWidth = 1;
				ctx.stroke();

				ctx.fillStyle="#00FF00";
				for (var i = 0; i < teamA.playerList.length; i++){
					ctx.fillRect(teamA.playerList[i].x,teamA.playerList[i].y,10,10);
				}
				ctx.fillStyle="#0000FF";
				for (var i = 0; i < teamB.playerList.length; i++){
					ctx.fillRect(teamB.playerList[i].x,teamB.playerList[i].y,10,10);
				}

				ctx.fillStyle = "blue";
				ctx.font = "bold 12px Arial";
				ctx.fillText(""+dataBuffer.length, 10, 10);
			}
	 	}
	 }
	 
	 function initMatch(data){
		 teamA = new Object();
		 teamB = new Object();
		 teamA.players = new Array();
		 teamB.players = new Array();
		 teamA.playerList = new Array();
		 teamB.playerList = new Array();

		 var ss = data.split(":");

		 try{
			 var k = 1;
			 while (k < ss.length){
				var p = ss[k].split(".");

				if (p[0] == "s"){

				}
				else if (p[0] == "t"){
					
					if (teamA.id == undefined){
						
						teamA.id = p[1];
						teamA.name = p[2];
						teamA.color1 = parseInt(p[3]);
						teamA.color2 = parseInt(p[4]);
					}
					else{
						teamB.id = p[1];
						teamB.name = p[2];
						teamB.color1 = parseInt(p[3]);
						teamB.color2 = parseInt(p[4]);
					}
				}
				else if (p[0] == "off"){

				}
				else if (p[0] == "sc"){
					goalsA = parseInt(p[1]);
					goalsB = parseInt(p[2]);
					updateScoreText();
				}
				else{
					var player = new Object();
					player.id = parseInt(p[0]);
					player.shirtNumber = parseInt(p[1]);
					player.firstname  = p[2];
					player.lastname = p[3];
					if (p.length > 5){
						player.x = parseInt(p[4]);
						player.y = parseInt(p[5]);
					}
					else{
						player.x = -200;
						player.y = -200;
					}
						
					if (teamB.id == undefined){
						teamA.players[player.id] = player;
						teamA.playerList.push(player);
						
						if (drawSVG){
							var C = document.createElementNS(svgxmlns, "circle");
							C.setAttributeNS(null, "cx", player.x + shiftX);
							C.setAttributeNS(null, "cy", player.y + shiftY);
							C.setAttributeNS(null, "r", 8); 
							C.setAttributeNS(null, "class", "teamAplayer"); 
							C.setAttributeNS(null, "id", "teamAplayer" + player.id); 
							C.setAttributeNS(null, "stroke", "black"); 
							C.setAttributeNS(null, "stroke-width", 1); 
							C.setAttributeNS(null, "fill", "red"); 
							C.setAttributeNS(null, "style", "cursor:pointer;"); 

							var T = document.createElementNS(svgxmlns, "text");
							T.setAttributeNS(null, "x", player.x + shiftX);
							T.setAttributeNS(null, "y", player.y  + shiftY + shirtNumberBaseY);
							T.setAttributeNS(null, "class", "teamAplayerNumber"); 
							T.setAttributeNS(null, "id", "teamAplayerNumber" + player.id); 
							T.setAttributeNS(null, "fill", "black");
							T.setAttributeNS(null, "text-anchor", "middle"); 
							T.setAttributeNS(null, "font-family", "sans-serif");
							T.setAttributeNS(null, "font-size", "10");
							T.setAttributeNS(null, "style", "cursor:pointer;");
						    var data = document.createTextNode(player.shirtNumber);
						    T.appendChild(data);
							
							$("#transformG").append(C);
							$("#transformG").append(T);
						}
					}
					else{
						teamB.players[player.id] = player;
						teamB.playerList.push(player);
						
						if (drawSVG){
							var C = document.createElementNS(svgxmlns, "circle");
							C.setAttributeNS(null, "cx", player.x + shiftX);
							C.setAttributeNS(null, "cy", player.y + shiftY);
							C.setAttributeNS(null, "r", 8); 
							C.setAttributeNS(null, "class", "teamBplayer"); 
							C.setAttributeNS(null, "id", "teamBplayer" + player.id); 
							C.setAttributeNS(null, "stroke", "black"); 
							C.setAttributeNS(null, "stroke-width", 1); 
							C.setAttributeNS(null, "fill", "blue"); 
							C.setAttributeNS(null, "style", "cursor:pointer;");
							
							var T = document.createElementNS(svgxmlns, "text");
							T.setAttributeNS(null, "x", player.x + shiftX);
							T.setAttributeNS(null, "y", player.y + shiftY + shirtNumberBaseY);
							T.setAttributeNS(null, "class", "teamBplayerNumber"); 
							T.setAttributeNS(null, "id", "teamBplayerNumber" + player.id); 
							T.setAttributeNS(null, "fill", "white");
							T.setAttributeNS(null, "text-anchor", "middle"); 
							T.setAttributeNS(null, "font-family", "sans-serif");
							T.setAttributeNS(null, "font-size", "10");
							T.setAttributeNS(null, "style", "cursor:pointer;");
						    var data = document.createTextNode(player.shirtNumber);
						    T.appendChild(data);
						    
							$("#transformG").append(C);
							$("#transformG").append(T);
						}
					}
				}

				k++;
			}
			 
			updateTimeText();
			updateScoreText(); 
			initDone = true;
			setTimeout(update, getTimeoutValue());
		}
		catch (e){
			console.log(e);
		}
	 }

	 function updateScoreText(){
		 document.getElementById("scoreText").textContent = teamA.name + " " + goalsA + " - " + goalsB + " " + teamB.name;
	 }
	 
	 function updateTimeText(){
		 var time = "" + mins + ":";
		 if (secs < 10){
			 time += "0";
		 }
		 time += secs;
		 
		 document.getElementById("timeText").textContent = time;
	 }
	 
	 function getTimeoutValue(){
		 var result = 1000 / 30 + (avgBufferSize - dataBuffer.length) * 5;
		 if (result < 15) result = 15;
		 else if (result > 60) result = 60;
		 return result;
	 }
	 
	 function update(){
		 
		 if (dataBuffer.length > 0){
			 parseFrame(dataBuffer.shift());
		 }
		 setTimeout(update, getTimeoutValue());
	 }
	 
jws.Footie = {
	// if namespace is changed update server plug-in accordingly!
	NS: "com.footie.websocket.live.WebsocketServer",
	
	getInit: function(){
		if (this.isConnected()){
			console.log("getting init info");
			var token = {
				ns:	jws.Footie.NS,
				type: "init"
			};
			try{
				this.sendToken(token, null);
			}
			catch (e){
				console.log(e.toString());
			}
			console.log("init request sent");
		}
	},
	
	startRun: function(id, x, y){
		if (this.isConnected()){
			console.log("sending run");
			var token = {
				ns:	jws.Footie.NS,
				type: "run",
				id: id,
				x: x,
				y: y
			};
			try{
				this.sendToken(token, null);
			}
			catch (e){
				console.log(e.toString());
			}
			console.log("run sent");
		}
	},
	
	pingServer: function(options){
		if (this.isConnected()){
			var token = {
				ns:	jws.Footie.NS,
				type: "ping"
			};
			
			this.sendToken(token, options);
			console.log("ping sent");
		}
	},
	
	//Method is called when a token has to be processed
	processToken: function( aToken ) {
	    // check if namespace matches
	    if( aToken.ns == jws.Footie.NS ) {
	      // if it's an answer for the request "getAuthorName"
	      if( aToken.reqType == "getAuthorName" ) {
	    	  alert( "This Tutorial is done by: " + aToken.name );
	      }
	      // if it's an answer for the request "calculate"
	      else if( aToken.reqType == "calculate" ) {
	    	  alert( "calculated Number is: " + aToken.calNumber );
		  }
	      
	    }
	    else if (aToken.ns == "fbf"){
//	    	document.getElementById('debug').innerHTML=aToken.data;
	    	dataBuffer.push(aToken.data);
//	    	parseFrame(aToken.data)
	    }
	    else if (aToken.ns == "fbinit"){
	    	document.getElementById('info').innerHTML=aToken.data;
	    	initMatch(aToken.data);
	    	
	    	
	    	$('.teamAplayerNumber').mousedown( function(e) {
	    		selectedPlayerID = $(this).attr("id").replace("Number", "");
	    		console.log('selectedPlayer: ' + selectedPlayerID);
	    		mouseX = $("#" + selectedPlayerID).attr("cx");
	    		mouseY = $("#" + selectedPlayerID).attr("cy");
	    		console.log(e.shiftKey);
	    		console.log(e.which);
	    		//action: 1: run, 2: pass, 3: shoot
	    		if (e.which == 1){
	    			if (e.shiftKey){
	    				action = 3;
	    			}
	    			else{
	    				action = 1;
	    			}
	    		}
	    		else if(e.which == 3){
	    			action = 2;
	    		}
	    	});
	    	$('.teamAplayer').mousedown( function(e) {
	    		selectedPlayerID = $(this).attr("id");
	    		console.log('selectedPlayer: ' + selectedPlayerID);
	    		mouseX = $("#" + selectedPlayerID).attr("cx");
	    		mouseY = $("#" + selectedPlayerID).attr("cy");
	    		console.log(e.shiftKey);
	    		console.log(e.which);
	    		//action: 1: run, 2: pass, 3: shoot
	    		if (e.which == 1){
	    			if (e.shiftKey){
	    				action = 3;
	    			}
	    			else{
	    				action = 1;
	    			}
	    		}
	    		else if(e.which == 3){
	    			action = 2;
	    		}
	    	});
	    }
	    else if (aToken.ns == "fbi"){
	    	document.getElementById('info').innerHTML=aToken.data;
	    }
	    else if (aToken.ns == "fbp"){
	    	var roundTripTime = +new Date() - pingSent;
	    	$("#spnPingResult").html("Round trip time: " + roundTripTime);
	    }
	  },
	  
};
//add the client PlugIn
jws.oop.addPlugIn( jws.jWebSocketTokenClient, jws.Footie );