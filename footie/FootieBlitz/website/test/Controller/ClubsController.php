<?php

/**
 * Clubs Controller
 *
 * @var Clubs Controller
 */

App::uses('Sanitize', 'Utility');

class ClubsController extends AppController
{
	var $name = 'Clubs';

	public $helpers = array('Js', 'Html');
	public $components = array('RequestHandler', 'Session');


	function history($id = null){
		$id = Sanitize::escape($id);
		
		$this->set('history', $this->Club->find('all', array(
					'fields' => array(
						'clubname',
						'max(Contract.transferfee) as feepaid',
						'max(Contract.weeklywage) as wage',
						'(SELECT budget || \' (season \' || season || \')\' FROM (SELECT sum(transferfee) as budget, (SELECT number FROM seasons WHERE firstday <= c.startdate AND lastday >= c.startdate) as season FROM contracts c WHERE acceptedbyplayer=true AND club_id=' . $id . ' GROUP BY season) tmp ORDER BY budget DESC LIMIT 1) as maxbudget',
						'max(Income.amount) as feereceived'
						),
					'joins' => array(
						array('table' => 'contracts', 'alias' => 'Contract', 'type' => 'INNER', 'conditions' => array('Contract.club_id = Club.id')),
						array('table' => 'club_incomes', 'alias' => 'Income', 'type' => 'INNER', 'conditions' => array('Income.club_id = Club.id', 'Income.type = 4')),
						),
					'conditions' => array(
						'Club.id' => $id,
						'Contract.acceptedbyplayer' => true
						),
					'group' => array('clubname', 'Club.id'),
					'recursive' => -1
					)
				)
			);
			
			$query = "Select attendance, matchdate, clubname from matches m inner join clubs a on a.id=m.awayteamid inner join leagues l on l.id=m.league_id where l.leaguereputation > 0 and attendance > 0 and hometeamid = " . $id . " order by attendance, matchdate limit 1;";
			$this->set('minatt', $this->Club->query($query));
		
			$query = "Select attendance, matchdate, clubname from matches m inner join clubs a on a.id=m.awayteamid inner join leagues l on l.id=m.league_id where l.leaguereputation > 0 and attendance > 0 and hometeamid = " . $id . " order by attendance desc, matchdate limit 1;";
			$this->set('maxatt', $this->Club->query($query));
			
			$query = "select * from (select firstname, lastname, person_id, sum(goals) as goals, number as season from match_playerstats s inner join matches m on s.match_id=m.id inner join seasons ss on m.season_id=ss.id inner join persons p on p.id=s.person_id where club_id=" . $id . " group by person_id, number, firstname, lastname) tmp where goals > 0 order by goals desc, season;";
			$this->set('maxgoals', $this->Club->query($query));
			
			$query = "select * from (select firstname, lastname, person_id, sum(assists) as assists, number as season from match_playerstats s inner join matches m on s.match_id=m.id inner join seasons ss on m.season_id=ss.id inner join persons p on p.id=s.person_id where club_id=" . $id . " group by person_id, number, firstname, lastname) tmp where assists > 0 order by assists desc, season;";
			$this->set('maxassists', $this->Club->query($query));
			
 			$query = "select * from (select firstname, lastname, person_id, round(avg(rating), 2) as rating, number as season from match_playerstats s inner join matches m on s.match_id=m.id inner join seasons ss on m.season_id=ss.id inner join persons p on p.id=s.person_id where club_id=" . $id . " group by person_id, number, firstname, lastname) tmp where rating > 0 order by rating desc, season;";
			$this->set('maxrating', $this->Club->query($query));
			
			$this->loadModel('Match');
			$matches = $this->Match->find('all', array(
					'fields' => array(
						'Home.id',
						'Home.clubname',
						'Away.id',
						'Away.clubname',						
						'hometeamgoals',
						'awayteamgoals',
						'matchdate'
						),
					'joins' => array(
						array('table' => 'clubs', 'alias' => 'Home', 'type' => 'INNER', 'conditions' => array('Match.hometeamid = Home.id')),
						array('table' => 'clubs', 'alias' => 'Away', 'type' => 'INNER', 'conditions' => array('Match.awayteamid = Away.id')),					
						array('table' => 'leagues', 'alias' => 'League', 'type' => 'INNER', 'conditions' => array('Match.league_id = League.id'))
						),										
					'conditions' => array(
						'League.leaguereputation > ' => 0,
						'Match.status' => 2,
						"OR" => array(
							'Match.hometeamid' => $id,
							'Match.awayteamid' => $id,
							)
						),
					'order' => 'matchdate',
					'recursive' => -1
					)
				);
			
			$currwon = 0;
			$won = 0;
			$currlost = 0;
			$lost = 0;
			$currunbeaten = 0;
			$unbeaten = 0;
				
			foreach($matches as $match){
				if ($match['Home']['id'] == $id){
					if ($match['Match']['hometeamgoals'] > $match['Match']['awayteamgoals']){
						$currwon = $currwon + 1;
						$currunbeaten = $currunbeaten + 1;
						if ($currlost > $lost){
							$lost = $currlost;
						}
						$currlost = 0;
					}
					elseif ($match['Match']['hometeamgoals'] < $match['Match']['awayteamgoals']){
						$currlost = $currlost + 1;
						if ($currwon > $won){
							$won = $currwon;
						}
						if ($currunbeaten > $unbeaten){
							$unbeaten = $currunbeaten;
						}
						$currwon = 0;
						$currunbeaten = 0;
					}
					else{
						$currunbeaten = $currunbeaten + 1;
						if ($currlost > $lost){
							$lost = $currlost;
						}
						if ($currwon > $won){
							$won = $currwon;
						}
						$currwon = 0;
						$currlost = 0;
					}
				}
				elseif($match['Away']['id'] == $id){
					if ($match['Match']['hometeamgoals'] < $match['Match']['awayteamgoals']){
						$currwon = $currwon + 1;
						$currunbeaten = $currunbeaten + 1;
						if ($currlost > $lost){
							$lost = $currlost;
						}
						$currlost = 0;
					}
					elseif ($match['Match']['hometeamgoals'] > $match['Match']['awayteamgoals']){
						$currlost = $currlost + 1;
						if ($currwon > $won){
							$won = $currwon;
						}
						if ($currunbeaten > $unbeaten){
							$unbeaten = $currunbeaten;
						}
						$currwon = 0;
						$currunbeaten = 0;
					}
					else{
						$currunbeaten = $currunbeaten + 1;
						if ($currlost > $lost){
							$lost = $currlost;
						}
						if ($currwon > $won){
							$won = $currwon;
						}
						$currwon = 0;
						$currlost = 0;
					}
				}
			}	
			if ($currwon > $won){
				$won = $currwon;
			}
			if ($currlost > $lost){
				$lost = $currlost;
			}	
			if ($currunbeaten > $unbeaten){
				$unbeaten = $currunbeaten;
			}
					
			$this->set('won', $won);
			$this->set('lost', $lost);
			$this->set('unbeaten', $unbeaten);
			$this->set('currwon', $currwon);
			$this->set('currlost', $currlost);
			$this->set('currunbeaten', $currunbeaten);
			
	}
	
	function infrastructure($id = null){
		if (isset($id)){
				
			//$this->Club->recursive = -1;
			$this->Club->contain('Stadium', 'Construction');
			$this->data = $this->Club->read(null, $id);				
				
			$this->loadModel('Season');
			$maxseason = $this->Season->find('first', array('fields' => array('MAX(Season.number) as maxseason')));
			$this->set('maxseason', $maxseason[0]['maxseason']);
			$selectedSeason = $maxseason[0]['maxseason'];
			
			$faccCost = pow(2, ($this->data['Club']['trainingfacc'] - 1)) * 300000;
			$this->set('faccCost', $faccCost);
			$faccTime = round(($this->data['Club']['trainingfacc'] + 1) * (($this->data['Club']['trainingfacc'] + 1) / 3)) * 6;
			$this->set('faccTime', $faccTime);
			
			$currentlyUpgrading = false;
			if (isset($this->data['Construction'])){
				foreach ($this->data['Construction'] as $cons)
				{
					if ($cons['type'] == 1){
						$currentlyUpgrading = true;
					}
				}
			}
			$this->set('currentlyUpgrading', $currentlyUpgrading);
			
			if($this->data['Club']['user_id'] == $this->Auth->user('id')){
				$this->set('userownsclub', 1);
				
				if ($this->RequestHandler->isPost()){
					//Change stats season
					if (isset($_POST['slSeason'])){
						$selectedSeason = $_POST['slSeason'];
					}else{
						$selectedSeason = $maxseason[0]['maxseason'];
					}
				
					//Change ticket prices
					if (isset($_POST['slSeats']) && isset($_POST['slStands'])){
						if ($_POST['slSeats'] > -1 && $_POST['slSeats'] < 201 && $_POST['slStands'] > -1 && $_POST['slStands'] < 201){
							$this->Club->read(null, $this->data['Club']['id']);
							$this->Club->set('standprice', $_POST['slStands']);
							$this->Club->set('seatprice', $_POST['slSeats']);
							$this->Club->save($this->Club->data);
							$this->Session->setFlash(__('Ticket prices saved.'));
							$this->data = $this->Club->read(null, $id);
						}
						else{
							$this->Session->setFlash(__('Invalid ticket prices.'));	
						}
					}
				
					//Start training facc upgrade or stadium expansion
					if ((!$currentlyUpgrading && isset($_POST['sbmUpgrade'])) || isset($_POST['slExp'])){
						 //Start transaction
			  			$dataSource = ConnectionManager::getDataSource('default');
			  			$dataSource->begin();
			
						$this->loadModel('ClubExpense');
						$this->loadModel('Construction');
						
						$seats = 0;
						$terraces = 0;
						$cost = $faccCost;
						$days = $faccTime;
						$conType = 1;
						$desc = 'Training facilities upgrade';
						if (isset($_POST['slExp'])){
							$conType = 2;
							$cost = 0;
							$desc = 'Stadium expansion';
							
							/*
							<option value="t500">500 terraces (£200.000 / 15 days)</option>
							<option value="t1000">1000 terraces (£350.000 / 25 days)</option>
							<option value="t2000">2000 terraces (£600.000 / 45 days)</option>
							<option value="s500">500 seats (£350.000 / 25 days)</option>
							<option value="s1000">1000 seats (£600.000 / 40 days)</option>
							<option value="s2000">2000 seats (£1.000.000 / 55 days)</option>
							*/
							
							if ($_POST['slExp'] == 't500'){
								$cost = 200000;
								$days = 15;
								$terraces = 500;
							}
							if ($_POST['slExp'] == 't1000'){
								$cost = 350000;
								$days = 25;
								$terraces = 1000;
							}
							if ($_POST['slExp'] == 't2000'){
								$cost = 600000;
								$days = 45;
								$terraces = 2000;
							}
							if ($_POST['slExp'] == 's500'){
								$cost = 350000;
								$days = 25;
								$seats = 500;
							}
							if ($_POST['slExp'] == 's1000'){
								$cost = 600000;
								$days = 40;
								$seats = 1000;
							}
							if ($_POST['slExp'] == 's2000'){
								$cost = 1000000;
								$days = 55;
								$seats = 2000;
							}
						}
						else{
							$currentlyUpgrading = true;
						}
						
			  			try{
			  				
			  				$this->ClubExpense->create();
							$this->ClubExpense->set('amount', $cost);
							$this->ClubExpense->set('type', 8);
							$this->ClubExpense->set('date', date('Y-m-d'));
							$this->ClubExpense->set('description', $desc);
							$this->ClubExpense->set('club_id', $this->data['Club']['id']);
							$this->ClubExpense->save($this->ClubExpense->data);
							
							
							$finished = date("Y-m-d", $days*86400 + time());
						
							$this->Construction->create();
							$this->Construction->set('club_id', $this->data['Club']['id']);
							$this->Construction->set('type', $conType);
							$this->Construction->set('start', date('Y-m-d'));
							$this->Construction->set('price', $cost);
							$this->Construction->set('finished', $finished);
							$this->Construction->set('seats', $seats);
							$this->Construction->set('terraces', $terraces);
							$this->Construction->save($this->Construction->data);
							
							$this->Session->setFlash($desc . ' started.');
							
						} catch (Exception $e) {
							$dataSource->rollback();
							debug($e);
						}
							
						$dataSource->commit(); 
						
					 	$this->data = $this->Club->read(null, $id);
						  
					}
				}
			}
			else{
				$this->set('userownsclub', 0);
			}

			
			$selSeasonDates = $this->Season->find('first', array(
					'conditions' => array('Season.number' => $selectedSeason), 
					'fields' => array('firstday', 'lastday')  
					)
			);

			$this->set('selectedSeason', $selectedSeason);

			$this->loadModel('Match');
			$this->Match->recursive=-1;
			$this->set('avgatt', $this->Match->find('all', array(
					'conditions' => array('Match.hometeamid' => $id, 
											//'Match.league_id' => $this->data['Club']['league_id'],
											'Match.matchdate >' => $selSeasonDates['Season']['firstday'],
											'Match.matchdate <' => $selSeasonDates['Season']['lastday']
										),
					'joins' => array(array('table' => 'leagues', 'alias' => 'League', 'type' => 'INNER', 'conditions' => array('Match.league_id = League.id'))),
					'fields' => array(
						'League.leaguename', 
						'AVG(Match.attendance)::int AS average',
						'AVG(Match.seats)::int AS avgseats',
						'MIN(Match.attendance) as min',
						'MIN(Match.seats) as minseats',
						'MAX(Match.attendance) as max',
						'MAX(Match.seats) as maxseats'
					),
					'group' => 'League.leaguename' 
					)
				)
			);

		}
	}
	
	function financedetails($id = null, $type = 'season', $number = null) {
		$this->autoRender = false;
	
		$id = Sanitize::escape($id);
		$type = Sanitize::escape($type);
		$number = Sanitize::escape($number);
		
		$this->set('type', $type);
		
		$this->Club->recursive = 0;
		$club = $this->Club->read(null, $id);
		$this->set('club', $club);
				
		$this->loadModel('Season');
		
		if ($type == 'season'){
			if (!isset($number)){
				$season = $this->Season->find('first', array('order'=>array("Season.number" => "desc")));
			}
			else{
				$season = $this->Season->find('first', array('conditions' => array('Season.number' => $number)));	
			}
			$where = " club_id=" . $id . " AND date >= '" . $season['Season']['firstday'] . "' AND date::date <= '" . $season['Season']['lastday'] . "'";
			$this->set('season', $season);
			
			$this->set('maxseason', $this->Season->find('first', array('fields' => array('MAX(Season.number) as maxseason'))));
		}
		else{
			if (!isset($number)){
				$number = date("W");
			}
			$where = " club_id=" . $id . " AND EXTRACT(YEAR FROM CURRENT_DATE)=EXTRACT(YEAR FROM date) AND EXTRACT(WEEK FROM date) = " . $number;
			$this->set('week', $number);
			$this->set('maxweek', date("W"));
		}
		
		
		if($club['Club']['user_id'] == $this->Auth->user('id')){
			$this->set('userownsclub', 1);

			$id = $club['Club']['id'];
			
			$query = "SELECT description, (SELECT COALESCE(sum(amount), 0) FROM club_expenses WHERE type=t.id AND " . $where . ") AS expenses, ";
			$query = $query . "(SELECT COALESCE(sum(amount), 0) FROM club_incomes WHERE type=t.id AND " . $where . ") AS income ";
			$query = $query . "FROM finance_types t UNION ALL ";
			$query = $query . "SELECT 'Sub total' as description, ";
			$query = $query . "(SELECT COALESCE(sum(amount), 0) AS expenses FROM club_expenses WHERE " . $where . ") AS expenses, ";
			$query = $query . "(SELECT COALESCE(sum(amount), 0) AS expenses FROM club_incomes WHERE " . $where . ") AS income;";
			$this->set('overview', $this->Club->query($query));
		}
		else{
			$this->set('userownsclub', 0);
		}
				
		$this->layout = 'ajax';
		$this->render('/Elements/financedetails');
	}
	
	function finances($id = null){
		if (isset($id)){
			
			$this->data = $this->Club->read(null, $id);
			
			$this->loadModel('Season');
			$this->loadModel('ClubGraph');
				
			$this->ClubGraph->recursive = -1;
			$balancegraph = $this->ClubGraph->find('all', array('limit' => 73, 'order' => 'ClubGraph.created DESC', 'conditions' => array('ClubGraph.club_id' => $id)));
			$this->set('balancegraph', array_reverse($balancegraph));
			
			$seasons = $this->Season->find('all');
			$this->set('seasons', $seasons);
			
			if($this->data['Club']['user_id'] == $this->Auth->user('id')){
				$this->set('userownsclub', 1);
			}
			else{
				$this->set('userownsclub', 0);
			}
			
			$wages = $this->Club->query("select COALESCE((SELECT sum(weeklywage) FROM contracts WHERE enddate>now() AND acceptedbyplayer='t' AND club_id = " . $this->data['Club']['id'] . "), 0) as dailywages");
			$this->set('wages', $wages);
			
			$expenses = $this->Club->query("SELECT EXTRACT(WEEK FROM date) AS week, EXTRACT(YEAR FROM date) AS year, sum(amount) FROM club_expenses WHERE club_id = " .$this->data['Club']['id'] . " AND date > now() - interval '15 weeks'  GROUP BY year, week ORDER BY year, week;");
			$this->set('expenses', $expenses);
			
			$income = $this->Club->query("SELECT EXTRACT(WEEK FROM date) AS week, EXTRACT(YEAR FROM date) AS year, sum(amount) FROM club_incomes WHERE club_id = " .$this->data['Club']['id'] . " AND date > now() - interval '15 weeks'  GROUP BY year, week ORDER BY year, week;");
			$this->set('income', $income);
			
			$balance = $this->Club->query("SELECT EXTRACT(WEEK FROM created) date, money amount FROM (SELECT * FROM club_graphs WHERE club_id = " .$this->data['Club']['id'] . " AND EXTRACT(DOW FROM created) = (SELECT EXTRACT(DOW FROM now())) AND created > now() - interval '15 weeks') AS sundays;");
			$this->set('balance', $balance);
		}
	}
	
	
	function tactics($id = null){
		$this->data = $this->Club->read(null, $id);
		
		if($this->data['Club']['user_id'] == $this->Auth->user('id') || $uid == 1){
			//Brugeren ejer klubben
			$this->set('userismanager', 1);
			
			if ($this->RequestHandler->isPost()){
				
				//Sæt frisparkstager, anfører mm.
				$teamtacticsID = $this->Club->Teamtactics->field('id', array('Teamtactics.club_id' => $id));
				
				//Hvis der ikke findes en taktik så lav en ny
				if ($teamtacticsID == false){
					$this->Club->Teamtactics->create();
					$this->Club->Teamtactics->set('club_id', $id);
				}
				else{
					$this->Club->Teamtactics->read(null, $teamtacticsID);
				}					

				$this->Club->Teamtactics->set('freekickshort', $_POST["freekicktaker"]);
				$this->Club->Teamtactics->set('captain', $_POST["captain"]);
				$this->Club->Teamtactics->set('penaltytaker', $_POST["penalty"]);
				$this->Club->Teamtactics->set('throwinright', $_POST["throwright"]);
				$this->Club->Teamtactics->set('throwinleft', $_POST["throwleft"]);
				$this->Club->Teamtactics->set('cornerright', $_POST["cornerright"]);
				$this->Club->Teamtactics->set('cornerleft', $_POST["cornerleft"]);
				$this->Club->Teamtactics->set('freekicklong', $_POST["freekicktakerlong"]);
				$this->Club->Teamtactics->set('targetman', $_POST["targetman"]);
				$this->Club->Teamtactics->save($this->Club->Teamtactics->data);
				
				
				//Opdater formationen og lineup
				$lineupID = $this->Club->Lineup->field('id', array('Lineup.club_id' => $id));
				
				//Hvis der ikke findes en lineup så lav en ny
				if ($lineupID == false){
					$this->Club->Lineup->create();
					$this->Club->Lineup->set('club_id', $id);
				}
				else{
					$this->Club->Lineup->read(null, $lineupID);
				}
				
				$this->Club->Lineup->find('first',	array('conditions' => array('Lineup.club_id' => $id)));
				$this->Club->Lineup->set('pos1', $_POST["p1"]);
				$this->Club->Lineup->set('pos2', $_POST["p2"]);
				$this->Club->Lineup->set('pos3', $_POST["p3"]);
				$this->Club->Lineup->set('pos4', $_POST["p4"]);
				$this->Club->Lineup->set('pos5', $_POST["p5"]);
				$this->Club->Lineup->set('pos6', $_POST["p6"]);
				$this->Club->Lineup->set('pos7', $_POST["p7"]);
				$this->Club->Lineup->set('pos8', $_POST["p8"]);
				$this->Club->Lineup->set('pos9', $_POST["p9"]);
				$this->Club->Lineup->set('pos10', $_POST["p10"]);
				$this->Club->Lineup->set('pos11', $_POST["p11"]);
				$this->Club->Lineup->set('pl1id', $_POST["hllpl1"]);
				$this->Club->Lineup->set('pl2id', $_POST["hllpl2"]);
				$this->Club->Lineup->set('pl3id', $_POST["hllpl3"]);
				$this->Club->Lineup->set('pl4id', $_POST["hllpl4"]);
				$this->Club->Lineup->set('pl5id', $_POST["hllpl5"]);
				$this->Club->Lineup->set('pl6id', $_POST["hllpl6"]);
				$this->Club->Lineup->set('pl7id', $_POST["hllpl7"]);
				$this->Club->Lineup->set('pl8id', $_POST["hllpl8"]);
				$this->Club->Lineup->set('pl9id', $_POST["hllpl9"]);
				$this->Club->Lineup->set('pl10id', $_POST["hllpl10"]);
				$this->Club->Lineup->set('pl11id', $_POST["hllpl11"]);
				$this->Club->Lineup->set('pl12id', $_POST["hllpl12"]);
				$this->Club->Lineup->set('pl13id', $_POST["hllpl13"]);
				$this->Club->Lineup->set('pl14id', $_POST["hllpl14"]);
				$this->Club->Lineup->set('pl15id', $_POST["hllpl15"]);
				$this->Club->Lineup->set('pl16id', $_POST["hllpl16"]);
				$this->Club->Lineup->set('pl17id', $_POST["hllpl17"]);
				$this->Club->Lineup->set('pl18id', $_POST["hllpl18"]);
				$this->Club->Lineup->save($this->Club->Lineup->data);
				
				$this->Session->setFlash(__('Tactics saved.'));
			}
			
			$this->set('squad', $this->Club->Contract->find('all',
			array(
				'conditions' => array(
				'Contract.club_id' => $id,
				'Contract.acceptedbyclub' => 1,
				'Contract.acceptedbyplayer' => 1,
				'Contract.startdate <=' => date('Y-m-d'),
				'Contract.enddate >' => date('Y-m-d')
				),
				'order' => 'Person.shirtnumber'
			)
			));
			
			//Hent lineup
			$this->set('lineup', $this->Club->Lineup->find('first', array('conditions' => array('Lineup.club_id' => $id))));
			if (!isset($this->Club->Lineup->data)){
				
				//Hvis der ikke findes en lineup så indsæt standard lineup
				$this->Club->Lineup->create();
				$this->Club->Lineup->set('club_id', $id);
				$this->Club->Lineup->set('pos1', '7,250');
				$this->Club->Lineup->set('pos2', '180,468');
				$this->Club->Lineup->set('pos3', '170,58');
				$this->Club->Lineup->set('pos4', '134,176');
				$this->Club->Lineup->set('pos5', '132,342');
				$this->Club->Lineup->set('pos6', '235,256');
				$this->Club->Lineup->set('pos7', '283,416');
				$this->Club->Lineup->set('pos8', '311,266');
				$this->Club->Lineup->set('pos9', '374,182');
				$this->Club->Lineup->set('pos10', '372,318');
				$this->Club->Lineup->set('pos11', '285,88');
				$this->Club->Lineup->save($this->Club->Lineup->data);
				
				$this->Session->setFlash(__('No formation found. Standard formation created.'));
				
				$this->set('lineup', $this->Club->Lineup->data);
			}
			
			//Hent tactics
			$this->set('teamtactics', $this->Club->Teamtactics->find('first', array('conditions' => array('Teamtactics.club_id' => $id))));
			
		}
		else{
			//Brugeren ejer ikke klubben og skal ikke sætte taktik
			$this->set('userismanager', 0);
		}
	}
	
	
	
	function showclubs(){
		$this->set('showclubs', $this->Club->find(
			'all',
			array(
				'fields' => array('Club.id', 'clubname','stadium_id', 'money', 'user_id'),
				'order' => 'Club.id DESC'  
    )
  ));
		$this->set('showcontracts', $this->Club->Contract->find(
			'all',
			array(
				'fields' => array('Contract.id', 'Contract.club_id', 'Contract.weeklywage', 'Person.firstname', 'Club.clubname', 'Contract.goalbonus'),
				'order' => 'Club.id DESC'  
    )
  ));
  
  		$this->set('showplayers', $this->Club->Contract->Person->find(
			'all',
			array(
				'fields' => array('Person.id', 'Person.firstname', 'Person.lastname', 'Person.age'),
				'order' => 'Person.id DESC'  
    )
  ));
  

		$this->set('uid', $this->Auth->user('id'));
	}

	function clubdetails($id = null){
		$this->loadModel('Person');

		
		if($this->request->is('post')){
			if (!empty($this->request->data)) {
				$this->set('numre', $this->request->data);
				$saved = false;
				$person = array();
				$savedp = array();
				$i = 0;
				
				//Search for duplicate shirt numbers
				$unique     = array_unique($this->request->data['players']); // preserves keys
				$diffkeys   = array_diff_key($this->request->data['players'], $unique);
				$duplicates = array_unique($diffkeys);
				if(!empty($duplicates)){
					$this->Session->setFlash(__('Shirt numbers could not be saved'));
				}
				//IF no duplicate shirt numbers were found
				else{
					foreach($this->request->data['players'] as $key => $player){
						$person['Person']['shirtnumber'] = $player;
						$person['Person']['id'] = $key;
						if(!empty($person['Person']['shirtnumber'] ) && !empty($person['Person']['id'])){
							if ($this->Person->save($person)){
								$saved = true;
								$savedp[''. $i . ''] = $person; 
								$i++;
							}
						}
					}
				}
				if($saved == true){					
					$this->Session->setFlash(__('Shirt numbers saved'));
				}
			}			
		}
		
	    $this->set('clubid', $id);
	    $club = $this->Club->read(null, $id);
		if (empty($club)) {
			$club = $this->Club->read(null, $id);
		}
		
		if($club['Club']['user_id'] == $this->Auth->user('id')){
			$this->set('userownsclub', 1);
		}
		else{
			$this->set('userownsclub', 0);
		}
		
		$this->set('lineup', $this->Club->Lineup->find('first', array( 'conditions' => array('Lineup.club_id' => $id))));		
		
		$this->set('offersReceived', $this->Club->Contract->query("SELECT *, c.id as contract_id FROM persons p INNER JOIN contracts c ON p.id=c.person_id INNER JOIN clubs cl ON cl.id=c.club_id WHERE acceptedbyplayer='false' AND enddate > now() AND p.id IN (SELECT per.id FROM persons per INNER JOIN contracts con ON per.id=con.person_id WHERE acceptedbyplayer='true' AND enddate > now() AND con.club_id=" . $club['Club']['id'] . ")"));
		
		$this->set('offersSent', $this->Club->Contract->query("SELECT *, c.id as contract_id FROM persons p INNER JOIN contracts c ON p.id=c.person_id WHERE acceptedbyplayer='false' AND enddate > now() AND c.club_id=" . $club['Club']['id'] . " AND p.id NOT IN (SELECT per.id FROM persons per INNER JOIN contracts con ON per.id=con.person_id WHERE acceptedbyplayer='true' AND enddate > now() AND con.club_id=" . $club['Club']['id'] . ")"));
		
	    $this->set('showplayers', $this->Club->Contract->find('all',
		array(
			'conditions' => array(
						'Contract.club_id' => $id,
						'Contract.acceptedbyclub' => 1,
						'Contract.acceptedbyplayer' => 1,
						'Contract.startdate <=' => date('Y-m-d'),
						'Contract.enddate >' => date('Y-m-d')
					),
				'order' => 'Person.shirtnumber'
	
			)
		));
		
	    $showplayers = $this->Person->find('all',
	    array(
	    			'contain' => array('ActiveContract', 'PersonThought' => array('conditions' => array('PersonThought.type' => 2, 'PersonThought.negative' => true, 'PersonThought.active' => true))),
	    			'conditions' => array(
	    						'ActiveContract.club_id' => $id						
	    ),
	    				'order' => 'Person.shirtnumber'
	    )
	     
	    );
	    $this->set('showplayers', $showplayers);
	    
		 $this->set('allfixtures', $this->Club->League->Match->find('all',
		array(
			'conditions' => array(
								'OR' => array(
								array('Match.hometeamid' => $id), 
								array('Match.awayteamid' => $id)
								),
								'status' => 0
			),
			'order' => 'Match.matchdate'
		)));
		
		$this->set('playedfixtures', $this->Club->League->Match->find('all',
		array(
		
			'conditions' => array(
								'OR' => array(
								array('Match.hometeamid' => $id), 
								array('Match.awayteamid' => $id)
								),
								'status' => 2,
								"Match.matchdate > (SELECT max(seasons.firstday) - interval '5 day' FROM seasons)"
			),
			'order' => array(
				'Match.matchdate' => "desc"
			)
		)));

		 $this->set('form', $this->Club->League->Match->find('all',
		array(
			'conditions' => array(
								'OR' => array(
								array('Match.hometeamid' => $id), 
								array('Match.awayteamid' => $id)
								),
								'status' => 2
			),
			'order' => 'Match.matchdate DESC',
			'limit' => 5
		)));

/*
		$this->set('friendlymatches', $this->Club->League->Match->find('all',
		array(
			'conditions' => array(
								'OR' => array(
								array('Match.hometeamid' => $id), 
								array('Match.awayteamid' => $id)
								),
								'status' => 0,
								'Match.league_id' => 4
			),
			'order' => 'Match.matchdate'
		)));
		
		$this->set('leaguematches', $this->Club->League->Match->find('all',
		array(
			'conditions' => array(
								'OR' => array(
								array('Match.hometeamid' => $id), 
								array('Match.awayteamid' => $id)
								),
								'status' => 0,
								'Match.league_id' => $this->data['League']['id']
			),
			'order' => 'Match.matchdate'
		)));
*/
		$this->set('club', $club);
	}
	
	function squad($clubid, $type) { 

		$this->set('type', $type);
		$this->set('squaddata', $this->Club->Contract->find('all',
			array(
				'conditions' => array(
					'Contract.club_id' => $clubid,
					'Contract.acceptedbyclub' => 1,
					'Contract.acceptedbyplayer' => 1,
					'Contract.startdate <=' => date('Y-m-d'),
					'Contract.enddate >=' => date('Y-m-d')
				)
			)
		)); 
		
		$this->render('/elements/squad');
	}


}
?>