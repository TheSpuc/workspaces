package states;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Bold;
import model.Hjaelper;
import model.Match;
import model.MatchEngine;
import model.Pitch;
import model.Player;
import model.PlayerRole;
import model.Sysout;
import model.Team;
import model.TeamState;
import model.Match.MatchState;

public class StateFreeKick implements PlayerState{

	private Player player;
	private Bold bold;
	private Team myTeam;
	private Team oppTeam;
	private Match match;
	private Pitch pitch;
	private Random r = new Random();
	
	public StateFreeKick(Player player){
		this.player = player;
		bold = player.getBold();
		myTeam = player.getMyTeam();
		oppTeam = player.getOppTeam();
		match = player.getMatch();
		pitch = player.getPitch();
	}
	
	public void update() {
		bold.setZForce(0);
		bold.setZ(0);
		player.setPosition(myTeam, oppTeam);
		if (player.wait <= 0 || System.currentTimeMillis() - match.getSetPieceTime() > 35000){
			if (!player.isKeeper){
				if (match.getSetPieceTeam() == myTeam){
					myTeam.setTeamState(TeamState.attack);

					//hvis det er langt væk så bare lad den nærmeste tage frisparket
					if (Hjaelper.Distance(match.getSetPieceX(), match.getSetPieceY(), oppTeam.getGoalX(), oppTeam.getGoalY()) > 400){

						
						if (match.setPieceTaker == null || match.setPieceTaker.getMyTeam().equals(oppTeam)){
							
							
							for (Player p : myTeam.getPlayers()){
								if (!p.isKeeper){
									if (match.setPieceTaker == null) match.setPieceTaker = p;
									else if (Hjaelper.Distance(match.setPieceTaker.posX, match.setPieceTaker.posY, match.getSetPieceX(), match.getSetPieceY()) > Hjaelper.Distance(p.posX, p.posY, match.getSetPieceX(), match.getSetPieceY()))
										match.setPieceTaker = p;
								}
							}
						}
						if (player == match.setPieceTaker){

//							System.out.println(player.getShirtNumber() + " vil tage frisparket. " + player.bestemplads);
							
							if (!player.bestemplads){
//								System.out.println(shirtNumber + " løber hen til bolden så han kan tage frispark.");
								//Stiller sig bag bolden
								double ta = Math.atan2(match.getSetPieceX() - oppTeam.getGoalX(), match.getSetPieceY() - oppTeam.getGoalY() + 30);								
								player.targetX = match.getSetPieceX() + (Math.sin(ta) * 30);
								player.targetY = match.getSetPieceY() + (Math.cos(ta) * 30);
							}

							if (Hjaelper.Distance(player.targetX, player.targetY, player.x, player.y) > 3){
								player.ready = System.currentTimeMillis();
							}
							if (Hjaelper.Distance(player.targetX, player.targetY, player.x, player.y) <= 5 && System.currentTimeMillis() - player.ready > 4000)
								player.bestemplads = true;
							
							if (player.bestemplads){
								player.targetX = bold.getX();
								player.targetY = bold.getY();
								//Løb hen til bolden til vi er tæt på
								if (Hjaelper.Distance(bold.getX(), bold.getY(), player.x, player.y) > 9 || MatchEngine.waitForSubs){
								}
								else{
								Player target = null;
								boolean allReady = true;

								for (Player p : myTeam.getPlayers()){
									if (p != player && !p.isKeeper){
										if(target == null){
											target = p;
										}
										else if (Hjaelper.Distance(p.getX(), p.getY(), bold.getX(), bold.getY()) < 
												Hjaelper.Distance(target.getX(), target.getY(), bold.getX(), bold.getY()) &&
												//bare for at sikre at der ikke altid blive spillet til den tættest på
												//skal laves bedre
												Hjaelper.Distance(target.getX(), target.getY(), bold.getX(), bold.getY())> 30){
											target = p;
										}
									}									
								}
								if(target == null){
									target = player.getMyTeam().getFrontPlayer();
								}

								if (allReady){
									player.getStateHasBall().getAiPass().pass(target, false, 1, new Point((int)target.getX(), (int)target.getY()), 1, 1);//boldDif skal indsættes istedet for 1
									match.setState(MatchState.ON);
									match.setPieceTaker = null;
									for (Player p : myTeam.getPlayers()){
										p.bestemplads = false;
									}
										
									for (Player p : oppTeam.getPlayers()){
										p.bestemplads = false;
									}
								}
							}
							}
						}
						else{//dem der ikke tager frisparket skal ikke stå i vejen
							//stå mindst 74 pixels fra bolden
							player.targetX = player.posX;
							player.targetY = player.posY;
							while (Hjaelper.Distance(match.getSetPieceX(), match.getSetPieceY(), player.targetX, player.targetY) < 74){
								double ta = Math.atan2(player.targetX - match.getSetPieceX(), player.targetY - match.getSetPieceY());
								player.targetX += 5 * Math.sin(ta);
								player.targetY += 5 * Math.cos(ta);
							}
						}
					}
					else{
						boolean shoot = true; //skyd direkte eller læg ind over?

						
						//Find vinklen på mål så vi kan se om vi skal skyde eller lægge ind over
						double vinkelretPaaMaal =  Math.atan2(oppTeam.getGoalX() - myTeam.getGoalX(), 0);
						double retningPaaMaal =  Math.atan2(oppTeam.getGoalX() - player.x, oppTeam.getGoalY() + 30 - player.y);
						if (Math.abs(Hjaelper.angleDifference(vinkelretPaaMaal, retningPaaMaal)) > 0.5)
							shoot = false;

						if (Hjaelper.Distance(match.getSetPieceX(), match.getSetPieceY(), oppTeam.getGoalX(), oppTeam.getGoalY()) > 250)
							shoot = false;

						Player taker = null;
						
						//Er ham der skal tage det ifølge taktikken på banen?
						if ((shoot && (myTeam.getpFreekickShort() == null || !myTeam.getPlayers().contains(myTeam.getpFreekickShort()))) 
								|| (!shoot && (myTeam.getpFreekickLong() == null || !myTeam.getPlayers().contains(myTeam.getpFreekickLong())))){
							
							
							//Niks, vi finder den med mest i shooting hvis der skal skydes
							if (shoot){
								for (Player p : myTeam.getPlayers()){
									if (!p.isKeeper){
										if (taker == null) taker = p;
										else if (taker.getShooting() < p.getShooting()) 
											taker = p;
									}
								}
							}
							else{//Niks, vi finder den med mest i passing hvis der skal lægges ind over
								for (Player p : myTeam.getPlayers()){
									if (!p.isKeeper){
										if (taker == null) taker = p;
										else if (taker.getPassing() < p.getPassing()) 
											taker = p;
									}
								}
							}
							
						}
						else{
							
							taker = myTeam.getpFreekickShort();
							if (!shoot) taker = myTeam.getpFreekickLong();
							
						}
						
						//ham der tager frisparket gaar til bolden
						if (player == taker){
							if (!player.bestemplads){
								//Stiller sig bag bolden
								double ta = Math.atan2(match.getSetPieceX() - oppTeam.getGoalX(), match.getSetPieceY() - oppTeam.getGoalY() + 30);
								player.pladsX = match.getSetPieceX() + (Math.sin(ta) * 20);
								player.pladsY = match.getSetPieceY() + (Math.cos(ta) * 20);
							}

							player.targetX = player.pladsX;
							player.targetY = player.pladsY;

							if (Hjaelper.Distance(player.pladsX, player.pladsY, player.x, player.y) < 4 && !player.bestemplads){
								
								boolean everyoneready = true;
								
								//Players get 10 seconds to get in position. We can't wait forever to take the freekick
								if (System.currentTimeMillis() - match.getSetPieceTime() < 10000){
									for (Player p : oppTeam.getPlayers())
										if (Hjaelper.Distance(p.x, p.y, p.targetX, p.targetY) > 50)
											everyoneready = false;
									for (Player p : myTeam.getPlayers())
										if (Hjaelper.Distance(p.x, p.y, p.targetX, p.targetY) > 50)
											everyoneready = false;
								}
								
								if (everyoneready || System.currentTimeMillis() - match.getSetPieceTime() < 25000){
									player.ready = System.currentTimeMillis();
									player.bestemplads = true;
								}
							}

							//Når vi har stået der lidt løber vi til bolden
							if (System.currentTimeMillis() - player.ready > 4000 && player.bestemplads == true){	
								player.pladsX = bold.getX();
								player.pladsY = bold.getY();
								player.targetX = bold.getX();
								player.targetY = bold.getY();
								//Løb hen til bolden til vi er tæt på
								if (Hjaelper.Distance(bold.getX(), bold.getY(), player.x, player.y) > 9){
								}
								else{
//									System.out.println("ELLERS SÅ SKYD DA");
									if (shoot){
										player.getStateHasBall().getAiShoot().getScore(0, 0, 0);
										player.getStateHasBall().getAiShoot().shoot(false, 0, 1, 1);
									}
									else{
										Player target = null;
										for (Player f : myTeam.getPlayersByHeight())
											if (!f.isKeeper && !f.equals(player))
												if (target == null && f.forwardOnSetpieces && 
														Hjaelper.Distance(f.x, f.y, f.getOppTeam().getGoalX(), f.getOppTeam().getGoalY() + 30) < 100) target = f;
									
										player.getStateHasBall().getAiCross().cross(target, 1);
									}
									match.setState(MatchState.ON);
									match.setPieceTaker = null;
									
									for (Player p : myTeam.getPlayers())
										p.bestemplads = false;
									for (Player p : oppTeam.getPlayers())
										p.bestemplads = false;
								}
							}


						}//de andre gaar i feltet eller placerer sig
						else{

							//dem der skal med frem
							if (player.forwardOnSetpieces){

								if (!player.bestemplads){
									if (myTeam.getGoalX() > oppTeam.getGoalX())
										player.targetX = oppTeam.getGoalX() + 80;
									else
										player.targetX = oppTeam.getGoalX() - 80;

									if (match.getSetPieceY() > pitch.getPitchPosY() + pitch.getPitchHeight() / 2)
										player.targetY = pitch.getPitchPosY() + pitch.getPitchHeight() / 2 - Math.abs(match.getSetPieceY() - pitch.getPitchPosY() + pitch.getPitchHeight() / 2) / 8;
									else
										player.targetY = pitch.getPitchPosY() + pitch.getPitchHeight() / 2 + Math.abs(match.getSetPieceY() - pitch.getPitchPosY() + pitch.getPitchHeight() / 2) / 8;

									player.pladsX = player.targetX;
									player.pladsY = player.targetY;
									player.bestemplads = true;
								}



								if (Hjaelper.Distance(player.x, player.y, player.targetX, player.targetY) < 9){
									int t = 10;
									if (r.nextDouble() < 0.5) t *= -1;
									player.targetX = player.pladsX + t + r.nextInt(25) - r.nextInt(25);
									player.targetY = player.pladsY + t + r.nextInt(50) - r.nextInt(50);
								}
								if (Hjaelper.Distance(player.x, player.y, player.pladsX, player.pladsY) < 30)
									player.targetSpeed = player.topSpeed / 3;
							}
							else{
								player.setPosition(myTeam, oppTeam);
								player.targetX = player.posX;
								player.targetY = player.posY;
							}

						}


					}

				}
				else{//hvis vi forsvarer
					myTeam.setTeamState(TeamState.defend);

					List<Player> mur = new ArrayList<Player>();
					if (Hjaelper.Distance(match.getSetPieceX(), match.getSetPieceY(), myTeam.getGoalX(), myTeam.getGoalY()) < 250){
						mur.add(myTeam.getPlayersFrontToBack().get(1));
						mur.add(myTeam.getPlayersFrontToBack().get(2));
						mur.add(myTeam.getPlayersFrontToBack().get(3));
					}

					//TODO: setMarkeringerSetPiece
//					if (Hjaelper.Distance(match.getSetPieceX(), match.getSetPieceY(), myTeam.getGoalX(), myTeam.getGoalY()) < 400)
//						myTeam.setMarkeringer(mur, true);

					player.targetSpeed = player.topSpeed;
					if (player.getCurrentlyMarking().size() == 1){
						//hvor taet paa skal vi markere
						double d = 0.3 * Hjaelper.Distance(player.getCurrentlyMarking().get(0).x, player.getCurrentlyMarking().get(0).y, myTeam.getGoalX(), myTeam.getGoalY() + 30);
						double c = (double)(100.0 - (double)player.getPressing()) / (double)100.0;
						d = d * c;
						if (d < 8) d = 8;

						//staa mellem manden og maalet - og lidt hen mod bolden
						double lidtModBolden = match.getSetPieceY() - pitch.getPitchMidY();
						lidtModBolden /= (0.05 * Math.abs(myTeam.getGoalX() - match.getSetPieceX()));
						double r = Math.atan2(myTeam.getGoalX() - bold.getX(), myTeam.getGoalY() + 30 - bold.getY());
						player.targetX = player.getCurrentlyMarking().get(0).x + d * Math.sin(r);
						player.targetY = player.getCurrentlyMarking().get(0).y + d * Math.cos(r);
						player.targetY += lidtModBolden;
					}
					else{
						player.targetX = player.startPosX;
						player.targetY = player.startPosY;
					}

					//stå mindst 74 pixels fra bolden
					while (Hjaelper.Distance(match.getSetPieceX(), match.getSetPieceY(), player.targetX, player.targetY) < 74){
						double ta = Math.atan2(player.targetX - match.getSetPieceX(), player.targetY - match.getSetPieceY());
						player.targetX += 1 * Math.sin(ta);
						player.targetY += 1 * Math.cos(ta);
					}

					//en spiller bliver fremme
					if (myTeam.getPlayersFrontToBack().get(0).equals(player) && player.getCurrentlyMarking().size() == 0){
						player.targetSpeed = player.topSpeed / 3;
						if (!player.bestemplads){
							player.bestemplads = true;
							player.targetX = pitch.getPitchMidX() + 50;
							player.targetY = pitch.getPitchMidY();
							if (myTeam.getGoalX() < oppTeam.getGoalX()) player.targetX -= 100;
							player.targetX += r.nextInt(50) - r.nextInt(50);
							player.targetY += r.nextInt(300) - r.nextInt(300);
						}
					}

					if (Hjaelper.Distance(match.getSetPieceX(), match.getSetPieceY(), myTeam.getGoalX(), myTeam.getGoalY()) < 250){
						//opstilling af mur
						double y = myTeam.getGoalY();
						if (Math.abs(myTeam.getGoalY() - match.getSetPieceY()) > Math.abs(myTeam.getGoalY() + 59 - match.getSetPieceY()))
							y = myTeam.getGoalY() + 59;
						double ta = Math.atan2(myTeam.getGoalX() - match.getSetPieceX(), y - match.getSetPieceY());

						if (myTeam.getPlayersFrontToBack().get(1).equals(player)){
							player.targetX = match.getSetPieceX() + Math.sin(ta) * 74;
							player.targetY = match.getSetPieceY() + Math.cos(ta) * 74;
						}
						else if(myTeam.getPlayersFrontToBack().get(2).equals(player)){
							player.targetX = myTeam.getPlayersFrontToBack().get(1).getTargetX() + Math.sin(ta + Math.PI / 2) * 14;
							player.targetY = myTeam.getPlayersFrontToBack().get(1).getTargetY() + Math.cos(ta + Math.PI / 2) * 14;
						}
						else if(myTeam.getPlayersFrontToBack().get(3).equals(player)){
							player.targetX = myTeam.getPlayersFrontToBack().get(1).getTargetX() + Math.sin(ta + Math.PI / 2) * 28;
							player.targetY = myTeam.getPlayersFrontToBack().get(1).getTargetY() + Math.cos(ta + Math.PI / 2) * 28;
						}
					}
				}
			}
			if (player.x == player.targetX && player.y == player.targetY) player.targetA = Math.atan2((bold.getX() - player.x), (bold.getY() - player.y));
			else player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));
		}
		if (player.getRole().equals(PlayerRole.GK)){
			//When the opponents have a freekick the keeper positions himself according to the ball
			player.targetX = player.getMyTeam().getGoalX(); 	
			player.targetY = player.getMyTeam().getGoalY() + 29;
			
			double q = Math.atan2((bold.getX() - player.getMyTeam().getGoalX()), (bold.getY() - player.getMyTeam().getGoalY())); //Finder vinklen mellem to punkter
			player.targetX += 25 * Math.sin(q); 	//placerer sig i forhold til bolden
			player.targetY += 25 * Math.cos(q);	//placerer sig i forhold til bolden
						
		}
	}

	public String toString(){
		return "Frispark";
	}
}
