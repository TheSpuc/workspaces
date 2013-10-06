<?php

/**
 * Person Controller
 *
 * @var Person Controller
 */

App::uses('Sanitize', 'Utility');

class PersonController extends AppController {

	var $name = 'Persons';
	public $components = array('RequestHandler', 'Message');

	public $helpers = array('Js', 'Form');

	function career($id = null){
		$id = Sanitize::escape($id);
		$this->loadModel('Contract');
				
		$this->set("career", $this->Contract->find('all',
		array(
			'fields' => array(
				'Club.id',
				'Club.clubname',
				'max(transferfee) as fee',
				'min(startdate) as date',
				'COALESCE((SELECT number FROM seasons WHERE firstday < min(Contract.startdate) AND lastday > min(Contract.startdate)), 1) as season',
			),
			'conditions' => array(
				'Contract.person_id' => $id,
				'Contract.acceptedbyplayer' => true,
			),
			'group' => 'Club.clubname, Club.id',
			'order' => 'date',
			'recursive' => 0
			)
		));
		
		$query = "SELECT leaguename, l.id as league_id, count(*) as matches, sum(goals) goals, sum(assists) assists, round(avg(rating), 2) as rating, ";
		$query = $query . "(SELECT number FROM seasons WHERE firstday<=matchdate::date AND lastday>=matchdate::date) AS season, ";
		$query = $query . "(SELECT clubname FROM clubs c INNER JOIN contracts con ON c.id=con.club_id WHERE startdate<matchdate ";
		$query = $query . "AND enddate >= matchdate AND person_id=s.person_id) AS club, club_id, ";
		$query = $query . "MIN(matchdate) as firstmatch ";
		$query = $query . "FROM match_playerstats s INNER JOIN matches m ON m.id=s.match_id INNER JOIN leagues l ON l.id=m.league_id WHERE ";
		$query = $query . "person_id=$id AND rating > 0 AND leaguereputation > 0 AND matchdate>(SELECT min(firstday) FROM seasons) GROUP BY season, leaguename, l.id, club, club_id ";
		$query = $query  . "ORDER BY season, firstmatch;";
		
		$this->set("careerdetails", $this->Contract->query($query));
		
	}
		
	function persondetails($id = null){   
		$this->set('userManagesPlayer', 0);
		$this->set('pid', $id);
		$this->data = $this->Person->read(null, $id);
		if($this->data['Person']['user_id'] == $this->Auth->user('id')){
			$this->set('userownsplayer', 1);
			$userownsplayer = 1;
		}
		else{
			$this->set('userownsplayer', 0);
			$userownsplayer = 0;
		}
		$this->set("personUser", $this->Person->User->find('first',
				array(
					'conditions' => array(
						'User.id' => $this->data['Person']['user_id']
					),
					'recursive' => 0
					)
				));
		
		//Tjek om der er tilbudt agent kontrakt
		$this->loadModel('AgentContract');
		$offer = $this->AgentContract->find('first',
		array(
							'conditions' => array(
								'AgentContract.user_id' => $this->Auth->user('id'),
								'AgentContract.person_id' => $id,
								'AgentContract.accepted' => NULL
		),
							'recursive' => -1
		)
		);
		if (isset($offer['AgentContract']['id'])){
			$this->set("agentContractOfferedID", $offer['AgentContract']['id']);
		}
		else{
			$this->set("agentContractOfferedID", 0);
		}
		
		
		
		//Tjek om der er en scout assignment i gang
		$this->loadModel('ScoutAssignment');
		$this->set("scoutAssignments", $this->ScoutAssignment->find('count',
				array(
					'conditions' => array(
						'ScoutAssignment.user_id' => $this->Auth->user('id'),
						'ScoutAssignment.person_id' => $id,
						'ScoutAssignment.finished' => NULL
					),
					'recursive' => -1
					)
				));
		
		//Hent ActiveContract-klub
		$this->Person->ActiveContract->Club->recursive = -1;
		$activeClub = $this->Person->ActiveContract->Club->find('first', array('conditions' => array(
			'Club.id' => $this->data['ActiveContract']['club_id']
		)));
		$this->set('activeClub', $activeClub);

		if($userownsplayer > 0 || ($this->data['Person']['user_id'] == 99 && $activeClub['Club']['user_id'] == $this->Auth->user('id'))){
			$this->set('userManagesPlayer', 1);
		}
		
		if ($this->RequestHandler->isPost() && isset($_POST['sbmScoutPlayer'])) {
			
			$this->ScoutAssignment->create();
			$this->ScoutAssignment->set('user_id', $this->Auth->user('id'));
			$this->ScoutAssignment->set('person_id', $id);			
			$this->ScoutAssignment->save($this->ScoutAssignment->data);
			
			$this->set("scoutAssignments", 1);
		}
		
		if ($this->RequestHandler->isPost() && ($userownsplayer == 1 || $userManagesPlayer = 1) && isset($_POST['hddrib'])) {
			$setp = false;
			if(isset($_POST['chbxsetp'])){
			    $setp = true;
			}
			
			$valid = true;
			
			//Check that tactics are between 0 and 100
			if ($_POST['hddrib'] < 0 || $_POST['hddrib'] > 100) { $valid = false; $err = 1; }
			if ($_POST['hdthro'] < 0 || $_POST['hdthro'] > 100) { $valid = false; $err = 2; }
			if ($_POST['hdruns'] < 0 || $_POST['hdruns'] > 100) { $valid = false; $err = 3; }
			if ($_POST['hdlong'] < 0 || $_POST['hdlong'] > 100) { $valid = false; $err = 4; }
			if ($_POST['hdaggr'] < 0 || $_POST['hdaggr'] > 100) { $valid = false; $err = 5; }
			if ($_POST['hdment'] < 0 || $_POST['hdment'] > 100) { $valid = false; $err = 6; }
			if ($_POST['hdclos'] < 0 || $_POST['hdclos'] > 100) { $valid = false; $err = 7; }
			if ($_POST['hdmark'] < 0 || $_POST['hdmark'] > 100) { $valid = false; $err = 8; }
			if ($_POST['hdpass'] < 0 || $_POST['hdpass'] > 100) { $valid = false; $err = 9; }
			
			if ($valid) {
				$this->request->data['Playertactic']["person_id"] = $id;
				$this->request->data['Playertactic']["dribble"] = $_POST['hddrib'];
				$this->request->data['Playertactic']["throughballs"] = $_POST['hdthro'];
				$this->request->data['Playertactic']["runs"] = $_POST['hdruns'];
				$this->request->data['Playertactic']["longshots"] = $_POST['hdlong'];
				$this->request->data['Playertactic']["aggression"] = $_POST['hdaggr'];
				$this->request->data['Playertactic']["mentality"] = $_POST['hdment'];
				$this->request->data['Playertactic']["closingdown"] = $_POST['hdclos'];
				$this->request->data['Playertactic']["crossball"] = $_POST['hdcros'];
				$this->request->data['Playertactic']["tightmarking"] = $_POST['hdmark'];			
				$this->request->data['Playertactic']["passing"] = $_POST['hdpass'];			
				$this->request->data['Playertactic']["forwardonsetpieces"] = $setp;			
				$this->Person->Playertactic->save($this->request->data);
				$this->Session->setFlash(__('Playing style updated.'));
			}
			else{
				$this->Session->setFlash(__('Error updating playing style. The error has been reported to site admin.'));
				$this->Message->sendMessage('Playing style validation error', 1, 2, 'Person id: ' . $this->data['Person']['id'] . '<br />Err: ' . $err . '<br />Post: ' . $this->implode_with_key($_POST));
			}
		}

		if ($this->RequestHandler->isPost() && $userownsplayer == 1 && isset($_POST['hacc'])) {

			$person = $this->Person->find('first', array('conditions' => array(
								'Person.id' => $id
			)));
			
			$accu = round($_POST['hacc'], 2);
			$tops = round($_POST['htps'], 2);
			$drbb = round($_POST['hdrb'], 2);
			$marki = round($_POST['hmar'], 2);
			$stre = round($_POST['hstr'], 2);
			$tack = round($_POST['htck'], 2);
			$agili = round($_POST['hagi'], 2);
			$reac = round($_POST['hrea'], 2);
			$handl = round($_POST['hhan'], 2);
			$shoot = round($_POST['hsho'], 2);
			$shootp = round($_POST['hspo'], 2);
			$pass = round($_POST['hpas'], 2);
			$tech = round($_POST['htec'], 2);
			$visi = round($_POST['hvis'], 2);
			$stam = round($_POST['hsta'], 2);
			$jump = round($_POST['hjum'], 2);
			$head = round($_POST['hhea'], 2);
			$comm = round($_POST['hcoa'], 2);
			$rush = round($_POST['hrou'], 2);
			$shstop = round($_POST['hsts'], 2);
			$playerp = round($_POST['playerpoints'], 2);

			$total = 0.0;
			$valid = true;
			$err = 0;
			$prepp = round($this->data['Person']['playerpoints'], 2);
			$prestats =  $this->implode_with_key($this->data['Person']);
			
			//Check that stats aren't less than before and between 0 and 100
			if ($accu < round($this->data['Person']['acceleration'], 2) || $accu < 10 || $accu > 100) { $valid = false; $err = 1; }
			if ($tops < round($this->data['Person']['topspeed'], 2) || $tops < 10 || $tops > 100) { $valid = false; $err = 2; }
			if ($drbb < round($this->data['Person']['dribbling'], 2) || $drbb < 10 || $drbb > 100) { $valid = false; $err = 3; }
			if ($marki < round($this->data['Person']['marking'], 2) || $marki < 10 || $marki > 100) { $valid = false; $err = 4; }
			if ($stre < round($this->data['Person']['strength'], 2) || $stre < 10 || $stre > 100) { $valid = false; $err = 5; }
			if ($tack < round($this->data['Person']['tackling'], 2) || $tack < 10 || $tack > 100) { $valid = false; $err = 6; }
			if ($agili < round($this->data['Person']['agility'], 2) || $agili < 10 || $agili > 100) { $valid = false; $err = 7; }
			if ($reac < round($this->data['Person']['reaction'], 2) || $reac < 10 || $reac > 100) { $valid = false; $err = 8; }
			if ($handl < round($this->data['Person']['handling'], 2) || $handl < 10 || $handl > 100) { $valid = false; $err = 9; }
			if ($shoot < round($this->data['Person']['shooting'], 2) || $shoot < 10 || $shoot > 100) { $valid = false; $err = 10; }
			if ($shootp < round($this->data['Person']['shotpower'], 2) || $shootp < 10 || $shootp > 100) { $valid = false; $err = 11; }
			if ($pass < round($this->data['Person']['passing'], 2) || $pass < 10 || $pass > 100) { $valid = false; $err = 12; }
			if ($tech < round($this->data['Person']['technique'], 2) || $tech < 10 || $tech > 100) { $valid = false; $err = 13; }
			if ($visi < round($this->data['Person']['vision'], 2) || $visi < 10 || $visi > 100) { $valid = false; $err = 14; }
			if ($stam < round($this->data['Person']['stamina'], 2) || $stam < 10 || $stam > 100) { $valid = false; $err = 15; }
			if ($jump < round($this->data['Person']['jumping'], 2) || $jump < 10 || $jump > 100) { $valid = false; $err = 16; }
			if ($head < round($this->data['Person']['heading'], 2) || $head < 10 || $head > 100) { $valid = false; $err = 17; }
			if ($comm < round($this->data['Person']['commandofarea'], 2) || $comm < 10 || $comm > 100) { $valid = false; $err = 18; }
			if ($rush < round($this->data['Person']['rushingout'], 2) || $rush < 10 || $rush > 100) { $valid = false; $err = 19; }
			if ($shstop < round($this->data['Person']['shotstopping'], 2) || $shstop < 10 || $shstop > 100) { $valid = false; $err = 20; }
			
			//Total pp spent has to be less than or equal to available pp.
			$total = $total + $this->ppCost($accu, $this->data['Person']['acceleration']);
			$total = $total + $this->ppCost($tops, $this->data['Person']['topspeed']);
			$total = $total + $this->ppCost($drbb, $this->data['Person']['dribbling']);
			$total = $total + $this->ppCost($marki, $this->data['Person']['marking']);
			$total = $total + $this->ppCost($stre, $this->data['Person']['strength']);
			$total = $total + $this->ppCost($tack, $this->data['Person']['tackling']);
			$total = $total + $this->ppCost($agili, $this->data['Person']['agility']);			
			$total = $total + $this->ppCost($reac, $this->data['Person']['reaction']);			
			$total = $total + $this->ppCost($handl, $this->data['Person']['handling']);			
			$total = $total + $this->ppCost($shoot, $this->data['Person']['shooting']);			
			$total = $total + $this->ppCost($shootp, $this->data['Person']['shotpower']);			
			$total = $total + $this->ppCost($pass, $this->data['Person']['passing']);			
			$total = $total + $this->ppCost($tech, $this->data['Person']['technique']);			
			$total = $total + $this->ppCost($visi, $this->data['Person']['vision']);			
			$total = $total + $this->ppCost($stam, $this->data['Person']['stamina']);			
			$total = $total + $this->ppCost($jump, $this->data['Person']['jumping']);			
			$total = $total + $this->ppCost($head, $this->data['Person']['heading']);			
			$total = $total + $this->ppCost($comm, $this->data['Person']['commandofarea']);			
			$total = $total + $this->ppCost($rush, $this->data['Person']['rushingout']);			
			$total = $total + $this->ppCost($shstop, $this->data['Person']['shotstopping']);				
			
			if ($total > $prepp) { $valid = false; $err = 21; }
			
			
			//debug($_POST);
			//debug($this->request->data['Person']);
			//debug($this->data['Person']);
			//debug($total);
			//debug($valid);
			//debug($err);
			
			
			$person['Person']['acceleration'] = $accu;
			$person['Person']['topspeed'] = $tops;
			$person['Person']['dribbling'] = $drbb;
			$person['Person']['marking'] = $marki;
			$person['Person']['strength'] = $stre;
			$person['Person']['tackling'] = $tack;
			$person['Person']['agility'] = $agili;
			$person['Person']['reaction'] = $reac;
			$person['Person']['handling'] = $handl;
			$person['Person']['shooting'] = $shoot;
			$person['Person']['shotpower'] = $shootp;
			$person['Person']['passing'] = $pass;
			$person['Person']['technique'] = $tech;
			$person['Person']['vision'] = $visi;
			$person['Person']['stamina'] = $stam;
			$person['Person']['jumping'] = $jump;
			$person['Person']['heading'] = $head;
			$person['Person']['commandofarea'] = $comm;
			$person['Person']['rushingout'] = $rush;
			$person['Person']['shotstopping'] = $shstop;
			$person['Person']['playerpoints'] = $prepp - $total;

		 	//Edit spilleren. 
			if (!empty($this->data)) {
				if ($valid) {
					if ($this->Person->save($person['Person'])) {
						$this->Session->setFlash(__('Training saved.'));
						//$this->Message->sendMessage('Training validation ok', 1, 2, 'Person id: ' . $this->data['Person']['id'] . '<br />prepp type: ' . gettype($prepp) . ' - total type: ' . gettype($total) . ' - pp type: ' . gettype($person['Person']['playerpoints']) . '<br />prePP: ' . $prepp . '<br />postPP: ' . $this->data['Person']['playerpoints'] . '<br />Err: ' . $err . '<br />Total: ' . $total . '<br />Post: ' . $this->implode_with_key($_POST) . '<br />PreStats: ' . $prestats);
					} else {
						debug($this->Person->validationErrors);
						$this->Session->setFlash(__('The player could not be edited. Please, try again.', true));
					}
				}
				else {
					$this->Session->setFlash(__('Error saving. Please load player details from the office page and try again. The error has been reported to site admin.'));
					$this->Message->sendMessage('Training validation error', 1, 2, 'Person id: ' . $this->data['Person']['id'] . '<br />prePP: ' . $prepp . '<br />postPP: ' . $this->data['Person']['playerpoints'] . '<br />Err: ' . $err . '<br />Total: ' . $total . '<br />Post: ' . $this->implode_with_key($_POST) . '<br />PreStats: ' . $prestats);
				}
			}
			else{
				$this->Session->setFlash(__('No data. Booooohooo.', true));
			}
			
			$this->data = $this->Person->read(null, $id);
		}


		if ($id == null){ 
			$this->Session->setFlash(__('No player found.', true));
		}
		else{
			//Find spilleren
			if (empty($this->data)) {
				$this->data = $this->Person->read(null, $id);
			}
			
			$this->set('form', $this->Person->MatchPlayerstat->find('all',
			  array(
				'conditions' => array(
					'MatchPlayerstat.person_id' => $id,
					'Match.status' => 2,
					'MatchPlayerstat.rating >' => 0),
				'order' => 'Match.matchdate DESC',
				'limit' => 5
			  )));
			
			$this->set('season', $this->Person->MatchPlayerstat->find('all', array(
				'fields' => array(
					'sum(interceptions+successfultackles) AS tackles', 
					'sum(saveattempts) AS saveattempts', 
					'sum(saves) AS saves', 
					'sum(goals) AS goals', 
					'sum(assists) AS assists', 
					'count(*) as matches', 
					'avg(rating) as rating'
				), 
				'conditions'=>array(
					'rating > '=> 0, 
					'(SELECT leaguereputation FROM leagues WHERE id = Match.league_id) > ' => 0, 
					'Match.status' => 2, 
					'Match.matchdate > (SELECT max(firstday) FROM seasons)',
					'MatchPlayerstat.person_id' => $id
					)
				)));
			
		  	$this->set('offers', $this->Person->ActiveContract->find('all',
		  		array(
				'conditions' => array(
					'ActiveContract.acceptedbyclub' => true,
					'ActiveContract.acceptedbyplayer' => false,
					'ActiveContract.offered' => true,
					'ActiveContract.person_id' => $id,
					'ActiveContract.enddate >' => 'now()')
				)));
			
			$this->set('playertactics', $this->Person->Playertactic->find('all',
		  		array(
				'conditions' => array('Playertactic.person_id' => $id)
				)));

			

			//If the user is the agent for the player or he owns the club where the players is currently playing he can set the training regime so find all available
			if($userownsplayer > 0 || ($this->data['Person']['user_id'] == 99 && $activeClub['Club']['user_id'] == $this->Auth->user('id'))){
				//If the user posted a change in selected training regime save it
				if ($this->RequestHandler->isPost() && isset($_POST['slTrainingRegime'])){
						$this->Person->id = $this->data['Person']['id'];
						$this->Person->saveField('training_regime_id', $_POST['slTrainingRegime']);
						
						$this->data = $this->Person->read(null, $id);
						$this->Session->setFlash(__('Training regime updated.', true));
				}
				
				$regimes = $this->Person->TrainingRegime->find('all', array('conditions' => array(
								'OR' => array(
									'TrainingRegime.user_id' => $this->Auth->user('id'),
									'TrainingRegime.public' => true
								)
				)));
				$this->set('regimes', $regimes);
			}	
				
				
			if($activeClub['Club']['user_id'] == $this->Auth->user('id')){
				$this->set('userownsactiveclub', 1);
			}
			else{
				$this->set('userownsactiveclub', 0);
			}
			
			$this->Person->ActiveContract->Club->recursive=-1;
			$ownedClub = $this->Person->ActiveContract->Club->find('first',
			array(
					'conditions' => array('Club.user_id' => $this->Auth->user('id'))
			));
			$this->set('ownedClub', $ownedClub);

		}
	}
	
	
	function implode_with_key($assoc, $inglue = '>', $outglue = ',') {
		$return = '';
	
		foreach ($assoc as $tk => $tv) {
			$return .= $outglue . $tk . $inglue . $tv;
		}
	
		return substr($return, strlen($outglue));
	}
	
	private function ppCost($newN, $oldN){
		$result = 0;
		
		$new = round($newN, 2);
		$old = round($oldN, 2);
		
		while ($old < $new){
			if ($old < 50){
				$result = $result + 0.1;	
			}
			elseif ($old < 60){
				$result = $result + 0.2;	
			}
			elseif ($old < 70){
				$result = $result + 0.4;	
			}
			else{
				$result = $result + 0.6;	
			}
			$old = round($old + 0.1, 2);
		}
		
		return $result;
	}
}


?>