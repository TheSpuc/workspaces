package model;

import java.awt.Point;
import java.util.Random;

import ai.AIGKSave;

import model.Match.MatchState;

public class StateKeeper implements PlayerState{

	private Player player;
	private Match match;
	private Bold bold;
	private Pitch pitch;
	Random r = new Random();
	private boolean rushingOut = false, rushingOutSet = false;
	AIGKSave aiGKSave;
	
	public StateKeeper(Player player, Match match, Bold bold, Pitch pitch) {
		super();
		this.player = player;
		this.match = match;
		this.bold = bold;
		this.pitch = pitch;
		
		aiGKSave = new AIGKSave(player, match, pitch, bold);
	}

	public void update() {
		
		int goalX = player.myTeam.goalX;
		int goalY = player.myTeam.goalY;

		if (match.getState().equals(MatchState.ON)){

			//If the keeper can reach the ball (standing)
			if (bold.getHasBall() != player && 
					Hjaelper.Distance(player.x, player.y, bold.getX(), bold.getY()) <= 8 &&
					bold.getZ() < player.height + (player.height / 4) + player.jumping && 
					System.currentTimeMillis() - player.saveTime > 350 &&
					(match.getState() == MatchState.ON || match.getState().equals(MatchState.PENALTYSHOOTOUT))){

				//If the ball is in the area and an opponent touched it last or it's not a pass or not a pass to the keeper
				//it's a save (even if it's just picking up the ball)
				
				//The ball has to be in the penalty area
				boolean isInField = bold.isBallInGoalField(player);
				
				//Last touch has to be opponent or me
				boolean lastT = bold.getLastTouch() == null || (bold.getLastTouch().equals(player) || bold.getLastTouch().getMyTeam().equals(player.getOppTeam()));
				
				//If it's a pass it can't be a pass to me - if passto is null it has to be a shot to be a save
				boolean pass = bold.isShot() || (bold.passTo != null && !bold.passTo.equals(player));
				
				if (isInField && 
						(lastT || pass)){
					aiGKSave.save();
				}
				else{
					//If it's not in the area or it's a pass from my team react to having the ball
					player.getStateKeeperHasBall().update();
				}
			}
			//If the keeper is making a flying save
			//height + (height / 4)armenes længde over hovedet + jumping = hvor hoejt maalmanden kan naa
			else if (player.kasterSig &&
					bold.getHasBall() != player && 
					Hjaelper.Distance(player.x, player.y, bold.getX(), bold.getY()) <= 11 &&
					bold.getZ() < player.height + (player.height / 4) + player.jumping && 
					System.currentTimeMillis() - player.saveTime > 350 &&
					(match.getState() == MatchState.ON || match.getState().equals(MatchState.PENALTYSHOOTOUT))){

				//If the ball is in the area it's a save (even if it's just picking up the ball)
				if (bold.isBallInGoalField(player)){
					aiGKSave.save();
				}
				else{
					//If it's not in the area we should not make a fingertip save
				}
			}

		}

		if (!player.kasterSig){
			if (match.state == MatchState.ON){

				if (bold.getHasBall() == player){//Hvis keeper har bolden

					bold.setSpeed(0);
					//bold.setZ(140);
					bold.setZForce(0);
					int rand = r.nextInt(500);
					//brug et sekund paa at komme op efter redningen
					if (System.currentTimeMillis() - player.saveTime > 900 + rand - player.reaction * 2){

						//loeb ud midt i feltet
						if (player.bestemplads == false){
							player.bestemplads = true;

							rand = r.nextInt(50)-25;//giv lidt random til hvor målmanden stiller sig ved udspark
							player.targetY = player.myTeam.getGoalY() + 25 + rand;
							rand = r.nextInt(70)-35;
							if (player.myTeam.getGoalX() > player.oppTeam.getGoalX()) player.targetX = player.myTeam.getGoalX() - 70 + rand;
							else player.targetX = player.myTeam.getGoalX() + 70 + rand;
						}


						if (System.currentTimeMillis() - player.saveTime > 6500){
							StateKeeperHasBall s = new StateKeeperHasBall(player, bold);
							s.update();
							player.bestemplads = false;
						}
					}

				}
				//If the ball is being passed to the player and he can get to it first, then meet it
				else if((bold.getPassTo() != null && bold.getPassTo().equals(player)) && 
						bold.firstToBall.equals(player)){
					
					Point pq = bold.meetBall(player, true);
					boolean meetBallOut = player.pitch.isOut(pq.x, pq.y);
					if (meetBallOut){
						pq = Hjaelper.intersection(player.myTeam.goalX, player.myTeam.goalY - 10, player.myTeam.goalX, player.myTeam.goalY + 59 + 10, (int)bold.x, (int)bold.y, pq.x, pq.y);
					}

					//							Sysout.print(shirtNumber + " tror han kan komme først til bolden");
					if (pq != null && !meetBallOut){
						player.targetX = pq.x;
						player.targetY = pq.y;
					}
				}
				else{//hvis han ikke har bolden placerer han sig i forhold til den
					player.bestemplads = false;
					goalX = player.myTeam.getGoalX();
					goalY = player.myTeam.getGoalY() + 30;

					double tempX = goalX, tempY = goalY; //midten af målet.
					double q;
					q = Math.atan2((bold.getX() - tempX), (bold.getY() - tempY)); //Finder vinklen mellem to punkter

					//hvis vi kan komme først til bolden tager vi den - hvis der ikke er dødbolde eller skud.
					if (bold.firstToBall.equals(player) && !bold.shot &&
							match.getState().equals(MatchState.ON) && (!rushingOutSet || rushingOut)){

						//The keeper can get to the ball first, but his rushing out ability determines whether he will come for the ball
						if (!rushingOutSet && 
								player.countToBall / bold.secondLowestCountToBall < (player.rushingout + r.nextInt(50) / 100.0)){
							rushingOut = true;

						}
						
						//DEBUG lige nu skal keeperen altid gå ud så vi er sikre på det virker. Slet nedenstående for at bruge rushingout
						rushingOut = true;
						
						rushingOutSet = true;
						
						if (rushingOut){
							Point pq = bold.meetBall(player, true);
							boolean meetBallOut = player.pitch.isOut(pq.x, pq.y);
							if (meetBallOut){
								pq = Hjaelper.intersection(player.myTeam.goalX, player.myTeam.goalY - 10, player.myTeam.goalX, player.myTeam.goalY + 59 + 10, (int)bold.x, (int)bold.y, pq.x, pq.y);
							}

							//							Sysout.print(shirtNumber + " tror han kan komme først til bolden");
							if (pq != null){
								player.targetX = pq.x;
								player.targetY = pq.y;
							}
							
						}
					}
					else{//hvis vi ikke kan komme først til bolden eller det er et skud

						rushingOut = false;
						rushingOutSet = false;
						
						if (Hjaelper.Distance(bold.x, bold.y, player.myTeam.goalX, player.myTeam.goalY + 30) >= 200 && 
								bold.udenfor == false && 
								bold.maal == false && !bold.shot){

							tempX = goalX;
							tempY = goalY; //midten af målet.
							tempX += 25 * Math.sin(q); 	//placerer sig i forhold til bolden
							tempY += 25 * Math.cos(q);	//placerer sig i forhold til bolden
						}
						else if(bold.lastTouch != null && bold.lastTouch.myTeam.equals(player.oppTeam) &&
								Hjaelper.Distance(bold.x, bold.y, player.myTeam.goalX, player.myTeam.goalY + 30) < 200 &&
								!bold.shot && !bold.cross && bold.getLastTouch().igennem > 0){
							
							tempX = goalX; 
							tempY = goalY; //midten af målet.
							tempX += 80 * Math.sin(q); //placerer sig i forhold til bolden
							tempY += 80 * Math.cos(q);	//placerer sig i forhold til bolden
						}
						else{
							tempX = goalX;
							tempY = goalY; //midten af målet.
							tempX += 18 * Math.sin(q); 	//placerer sig i forhold til bolden
							tempY += 18 * Math.cos(q);	//placerer sig i forhold til bolden
						}

						if (bold.isCross()){

							Point pq = bold.meetBall(player, true);

							if (player.pitch.isOut(pq.x, pq.y)){
								pq = Hjaelper.intersection(player.myTeam.goalX, player.myTeam.goalY - 10, player.myTeam.goalX, player.myTeam.goalY + 59 + 10, (int)bold.x, (int)bold.y, pq.x, pq.y);
							}

							//Disabled because the keeper is messing up on crosses. For now he always stays in the middle of the goal moving slightly towards the ball
							if (false && pq != null && Hjaelper.Distance(player.x, player.y, pq.x, pq.y) < 35){
								tempX = pq.x;
								tempY = pq.y;
							}
							else{
								q = Math.atan2(pq.x - goalX, pq.y - goalY);

								q = Math.atan2(bold.x - goalX, bold.y - goalY);
								tempX = goalX;
								tempY = goalY; //midten af målet.
								tempX += 20 * Math.sin(q); 	//placerer sig i forhold til bolden
								tempY += 20 * Math.cos(q);	//placerer sig i forhold til bolden
							}
						}


						if (bold.isShot() && bold.getLastShot() != null && bold.getLastShot().getMyTeam().equals(player.oppTeam)
								&& !player.kasterSig){

							if (bold.isPenalty()){
								if (player.isGuessPenalties()){
									
									System.out.println("Keeper gætter");
									
									player.setKasterSig(true);

									if (r.nextBoolean()) player.targetY = player.myTeam.getGoalY();
									else player.targetY = player.myTeam.getGoalY() + 59;
									
									player.setA(Math.atan2(player.targetX - player.getX(), player.targetY - player.getY()));
									player.setTargetA(player.getA());
									player.speed = (100 + player.shotstopping + player.agility) / 2;
								}
								bold.setPenalty(false);
							}
							else{
								//The keeper can't judge a shot and how to save it quicker than his reaction
								if (System.currentTimeMillis() - bold.getBallTimer() > 400 - 2 * player.reaction){

									//hvis skuddet går langt forbi gør vi ikke noget
									tempX = bold.x + Math.sin(bold.a) * 400;
									tempY = bold.y + Math.cos(bold.a) * 400;
									Point rammer = Hjaelper.intersection(player.myTeam.goalX, player.myTeam.goalY - 10, player.myTeam.goalX, player.myTeam.goalY + 59 + 10, 
											(int)bold.x, (int)bold.y, (int)tempX, (int)tempY);

									if (rammer != null && rammer.y < player.myTeam.goalY + 59 + 15 && rammer.y > player.myTeam.goalY - 15){ //den rammer tæt på mål

										if (!player.playerMatchStats.saveOn){
											player.playerMatchStats.saveOn = true;
											if (Hjaelper.Distance(player.x, player.y, bold.x, bold.y) < 132)
												player.playerMatchStats.closeSaves++;
										}

										//Som udgangspunkt løber vi hen og tager skuddet 
										Point p = bold.meetBall(player, true);
										tempX = p.x;
										tempY = p.y;

										//hvis vi først kan møde bolden på den anden side af baglinie og tæt på mål
										//så kaster vi os efter den mm. det er et lob - så er der ikke så meget at gøre (fordi vi først kan 
										//møde den på den anden side af baglinien
										if (Math.abs(tempX - player.pitch.getPitchMidX()) > Math.abs(player.myTeam.goalX - player.pitch.getPitchMidX())
												&& Hjaelper.Distance(player.getX(), player.getY(), bold.x, bold.y) < bold.speed * 0.6
												){
											player.kasterSig = true;

											//									p = bold.meetBall(player, false);
											//									tempX = p.x;
											//									tempY = p.y;

											p = Hjaelper.intersection((int)player.x, 0, (int)player.x, 500, (int)bold.x, (int)bold.y, (int)tempX, (int)tempY);
											if (p != null){
												tempX = p.x;
												tempY = p.y;
											}

											tempX -= 10 * Math.sin(bold.a);
											tempY -= 10 * Math.cos(bold.a);

											player.a = Math.atan2(tempX - player.x, tempY - player.y);
											player.targetA = player.a;
											player.speed = (100 + player.shotstopping + player.agility) / 2;
											//										Sysout.print("Kaster sig: " + speed);
										}
									}
									else{
										//									Sysout.print("Bolden går forbi");
										player.targetX = bold.x;
										player.targetY = bold.y;
									}
								}
							}
						}
						player.targetX = tempX;
						player.targetY = tempY;
					}
				}
				//keeper ser hele tiden hen mod bolden hvis han ikke selv har den

				//			a = Math.atan2(bold.getX() - x, bold.getY() - y);
				//			if (bold.getLastTouch() != player){
				//				a = Math.atan2(oppTeam.getGoalX() - x, oppTeam.getGoalY() + 25 - y);
				//			}

				if (!bold.isShot() && player.playerMatchStats.saveOn)
					player.playerMatchStats.saveOn = false;

				//når keeperen har bolden og er på plads vender han sig om mod modstanderens mål for at sparke ud
				if (Hjaelper.Distance(player.x, player.y, player.targetX, player.targetY) < 8 && 
						bold.getHasBall() == player && !player.kasterSig)
					player.targetA = Math.atan2(player.oppTeam.goalX - player.x, 0);

			}
			//er der frispark og skal jeg tage det?
			else if (match.state == MatchState.FREE_KICK && match.setPieceTeam.equals(player.myTeam)){

				Player taker = null;

				for (Player p : player.myTeam.players){
					if (taker == null) taker = p;
					else if (Hjaelper.Distance(taker.posX, taker.posY, match.setPieceX, match.setPieceY) > Hjaelper.Distance(p.posX, p.posY, match.setPieceX, match.setPieceY))
						taker = p;
				}

				if (player == taker){
					player.targetX = bold.getX();
					player.targetY = bold.getY();

					if (Hjaelper.Distance(bold.getX(), bold.getY(), player.x, player.y) < 9){
						if (System.currentTimeMillis() - player.ready > 4000){

							Player target =null;

							for (Player p : player.myTeam.getPlayers()){
								if (p != player && 
										Math.abs(player.x - player.getMyTeam().getGoalX()) < Math.abs(p.x - player.getMyTeam().getGoalX())){

									if (target == null) target = p;
									else if (Hjaelper.Distance(p.getX(), p.getY(), bold.getX(), bold.getY()) < 
											Hjaelper.Distance(target.getX(), target.getY(), bold.getX(), bold.getY())) 
										target = p;

								}
							}

							player.stateHasBall.getAiPass().pass(target, false, bold.getBoldDifPercent(), new Point((int)target.getX(), (int)target.getY()), 1, 1);
							match.setState(MatchState.ON);
							for (Player p : player.myTeam.getPlayers())
								p.bestemplads = false;
							for (Player p : player.oppTeam.getPlayers())
								p.bestemplads = false;
						}

					}
					else{
						player.ready = System.currentTimeMillis();
					}
				}
			}
			else{ //hvis matchstate ikke er ON og der ikke er frispark til os så stiller han sig midt i målet
				player.targetX = player.myTeam.goalX; 	
				player.targetY = player.myTeam.goalY + 29;
			}

			//keeper vender sig i retning af target
			if (!player.kasterSig) player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y)); //retning målmand bevæger sig i

			//If the keeper is close to his target (and doesn't need to move very far) he keeps his eyes on the ball and moves sidewards / backwards
			if (Hjaelper.Distance(player.x, player.y, player.targetX, player.targetY) < 15 && !player.kasterSig){
				player.targetA = Math.atan2((bold.x - player.x), (bold.y - player.y));
			}

			//			if (bold.udenfor == true || bold.maal == true){
			//				speed = 0;
			//			}
		}
	}

}
