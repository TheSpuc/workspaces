<?php

App::uses('Sanitize', 'Utility');

class StadiumsController extends AppController
{
	var $name = 'Stadiums';

	public $helpers = array('Js');
	public $components = array('RequestHandler');
	
	
	function allstadiums(){
		
		
		if(isset($_POST['savesubmit'])){
			
			$this->Stadium->create();
			$this->Stadium->set('stadiumname', $_POST['sname']);
			$this->Stadium->save($this->Stadium->data);
			
		}
		
		if(isset($_POST['querysubmit'])){
			
		

			$this->Stadium->query("INSERT INTO stadiums (stadiumname) VALUES ('" . Sanitize::escape($_POST['sname']) . "')");
			
		}
		
		
		$this->Stadium->recursive=-1;
		
		$stadiums = $this->Stadium->find(
					'all'
			);
			
		$this->set('stadiums', $stadiums);


	}


	function updplayertactics($personid, $frame){ 
	
		$msg = "";
		$frame = Sanitize::clean($frame);
		$personid = Sanitize::clean($personid);
		
		$this->loadModel('Person');
		$person = $this->Person->find('first', array('conditions' => array('Person.id' => $personid)));

		if ($person['Person']['user_id'] == $this->Auth->user('id')){		
			
			$this->Match->query("INSERT INTO logs (dt, msg) VALUES (now(), 'updplayertactics: " . $personid . " - frame: " . $frame . "')");	
		
			$arr = explode("_", $frame);
			
			if (count($arr) == 11 && $personid > 0){
				
				$query = "UPDATE match_playertactics SET ";
				$query = $query . "dribble=$arr[0], ";
				$query = $query . "throughballs=$arr[1], ";
				$query = $query . "runs=$arr[2], ";
				$query = $query . "longshots=$arr[3], ";
				$query = $query . "aggression=$arr[4], ";
				$query = $query . "mentality=$arr[5], ";
				$query = $query . "closingdown=$arr[6], ";
				$query = $query . "crossball=$arr[7], ";
				$query = $query . "tightmarking=$arr[8], ";
				$query = $query . "passing=$arr[9], ";
				$query = $query . "forwardonsetpieces='$arr[10]' ";
				
				$query = $query . "WHERE person_id=" . $person['Person']['id'];
				
				$this->Person->query($query);
		
				/*
				$num = db_update('me_livematch')
				->fields(array(
				'frame' => 'updplayertac:' . $personid,
				))
				->condition('id', (-4000 - $personid))
				->execute();
				
				if ($num == 0){
				db_insert('me_livematch')->fields(array(
					'id' => (-4000 - $personid),
					'frame' => 'updplayertac:' . $personid,
					'matchid' => 0,
					))->execute();
				}
				*/
				
				$msg = 'Playing style updated';
				$this->Match->query("INSERT INTO logs (dt, msg) VALUES (now(), 'updplayertactics: OK fra service')");	
		
			}
			else{
				$msg = 'Error sending data to server';
			}
		}
		else{
			$msg = 'You are not the agent of this player';
		}
		$this->set('msg', $msg);
		
		$this->layout = 'ajax';
	   	$this->render('/Elements/liveupdplayertactics');
	}

	
	function getplayertactics($matchid){
	
	
	   $person = $this->Match->query("SELECT p.id as personid, firstname, lastname, * FROM persons p INNER JOIN match_playertactics t ON t.person_id=p.id WHERE p.user_id = " . $this->Auth->user('id'));
  		$this->set('person', $person);

	
		
		$this->layout = 'ajax';
	   	$this->render('/Elements/livegetplayertactics');
		   	
	}

		
	function updroles($matchId, $values){
	
		$values = Sanitize::clean($values);
		$arr = explode("_", $values);
		$msg = "";
		
		if (count($arr) == 9){
		
			$this->loadModel('Club');
			$club = $this->Club->find('first', array('conditions' => array('Club.user_id' => $this->Auth->user('id'))));
	
	
		   	if (isset($club['Club']['id'])){
				
				$this->Club->query("UPDATE match_teamtactics SET captain=$arr[0], throwinright=$arr[1], throwinleft=$arr[2], penaltytaker=$arr[3], freekickshort=$arr[4], freekicklong=$arr[5], cornerright=$arr[6], cornerleft=$arr[7], targetman=$arr[8] WHERE club_id=" . $club['Club']['id']);
				$this->Club->query("INSERT INTO manager_commands (match_id, created, command, status, statusmsg, matchtimeused, user_id) VALUES ($matchId, now(), 'updroles:" . $club['Club']['id'] . "', 0, 'Inserted by service', '', " . $this->Auth->user('id') . ")");
			}
	
			$msg = 'Roles updated';
		}
		else{
			$msg = 'Error sending data to server';
		}
		
		$this->set('msg', $msg);
		
		$this->layout = 'ajax';
	   	$this->render('/Elements/liveupdroles');
	}

	function getroles(){
	
		$this->loadModel('Club');
		$club = $this->Club->find('first', array('conditions' => array('Club.user_id' => $this->Auth->user('id'))));


	   if (isset($club['Club']['id'])){
	   	
		   $allNames = array();
		   $allIds = array();
		   
		   	$this->loadModel('MatchLineup');
			$lineup = $this->MatchLineup->find('first', array('conditions' => array('MatchLineup.club_id' => $club['Club']['id'])));

//		   debug($club);
		   
		   
			foreach ($lineup as $rec) {
				if (isset($rec['firstname'])){
			      	array_push($allIds, $rec['id']);
					array_push($allNames, $rec['lastname']);
				}
		    }
			
			$teamtactics = $this->MatchLineup->query("SELECT * FROM match_teamtactics WHERE club_id=" . $club['Club']['id']);
			$this->set('teamtactics', $teamtactics);
			$this->set('allNames', $allNames);
			$this->set('allIds', $allIds);
			
	   }
	   else{
	   		echo 'You do not own a club involved in this match';
	   }
	   
	   $this->layout = 'ajax';
	   $this->render('/Elements/livegetroles');
	}




	function updteamtactics($frame, $matchId){ 

		$frame = Sanitize::clean($frame);
		$frame = str_replace("_",":",$frame);
		
		$this->Match->query("INSERT INTO logs (dt, msg) VALUES (now(), 'updteamtactics: " . Sanitize::clean($matchId) . " - frame: " . $frame . "')");
	 			
		$this->loadModel('Match');
		$match = $this->Match->find('first', array('conditions' => array('Match.id' => $matchId)));
		
		$usermanagedclub = -1;
		if ($this->Auth->user('id') == $match['HomeTeam']['user_id']){
			$usermanagedclub = $match['HomeTeam']['id'];
		}
		elseif ($this->Auth->user('id') == $match['AwayTeam']['user_id']){
			$usermanagedclub = $match['AwayTeam']['id'];
		}


	   if ($usermanagedclub > -1){
	   
			$numSubs = 11;
			
	   		$dataSource = ConnectionManager::getDataSource('default');
			$dataSource->begin();
	   		
	   		try{
	   			
  				$this->loadModel('MatchLineup');
	   			
	   			$lineup = $this->MatchLineup->find('first',
					array(
						'conditions' => array(
							'MatchLineup.club_id' => $usermanagedclub,
							'MatchLineup.match_id' => $matchId
						)
					));
		   		
		   		
		   		if (isset($lineup['MatchLineup']['id'])){
					
					$arr = explode(":", $frame);
		
					$onField = array();
					$i = 1;
					
					while ($i < count($arr)){
						$pl = explode(",", $arr[$i]);
						$onField[$i] = $pl[0];
		            		        $i = $i +1;
					}
					
					$i = 1;
					while ($i < 12){
						if (in_array($lineup['MatchLineup']['pl' . $i . 'id'], $onField)){
							$numSubs = $numSubs -1;
							$onField = $this->remove_item_by_value($onField, $lineup['MatchLineup']['pl' . $i . 'id']);
						}	
		            	$i = $i +1;
					}			
				}
		
		   		if ($numSubs > 3){
		   			echo 'Maximum of three substitutions allowed in this match. ' . $numSubs . ' substitutions made. (' . $matchId . ', ' . $usermanagedclub . ')';
		   		}
		   		else{
		   			$this->Match->query("INSERT INTO logs (dt, msg) VALUES (now(), 'updteamtactics: ok fra service')");
				
					$this->Match->query("INSERT INTO manager_commands (match_id, created, command, status, statusmsg, matchtimeused, user_id) VALUES ($matchId, now(), '" . $usermanagedclub . Sanitize::clean($frame) . "', 0, 'Inserted by service', '', " . $this->Auth->user('id') . ")");


		
		   		/*
					db_insert('me_livematch')->fields(array(
					'id' => -1000,
					'frame' => $record['clubid'] . $frame,
					'matchid' => $matchId,
					))->execute();
				*/
					
					echo 'Tactics updated.';
				}
			} catch (Exception $e) {
				$dataSource->rollback();
				debug($e);
			}
				
			$dataSource->commit();
	   }
	   else{
	   		echo 'You are not the owner of a club in this match.';
	   }	
	
	   $this->layout = 'ajax';
	   $this->render('/Elements/liveupdteamtactics');
	}

	
	
	function livedata($id = null, $frame = null) {
		$this->autoRender = false;
	
		$this->Match->cacheQueries = false;
		$cachePaths = array('js', 'css', 'menus', 'views', 'persistent',
		'models');
		foreach($cachePaths as $config) {
			clearCache(null, $config);
		}
		if($id != null){
		
			$str = "";
		    if ($frame == 0 || $frame == null){
		        $data = $this->Match->query("SELECT frame, id FROM livematches WHERE id = 0 OR (id > (SELECT max(id) - 200 FROM livematches WHERE match_id= $id) AND match_id= $id AND id > 0) ORDER BY id asc", $cachequeries = false);
		        //echo "SELECT frame, id FROM livematches WHERE id = 0 OR (id > (SELECT max(id) - 200 FROM livematches WHERE match_id= $id) AND match_id= $id AND id > 0) ORDER BY id asc";		    				
		    }
		    else{
		    	$data = $this->Match->query("SELECT frame, id FROM livematches WHERE match_id=" . $id . " AND id > " . $frame . " ORDER BY id asc", $cachequeries = false);		    				
		    }
		}

		$this->set('data', $data);
				
		$this->layout = 'ajax';
		$this->render('/Elements/livedata');
	}

	function live(){
	
		$this->loadModel('Match');
		
		if(isset($_POST['submit']) && $this->Auth->user('id') == 1){
			
			//Indsæt ny kamp
			$this->Match->create();
			$this->Match->set('status', 0);
			$this->Match->set('hometeamid', 20);
			$this->Match->set('awayteamid', 12);
			$this->Match->set('matchDate', 'now()');
			$this->Match->set('stadiumid', 7);
			$this->Match->set('hometeamgoals', 0);
			$this->Match->set('awayteamgoals', 0);
			$this->Match->set('leagueid', 4);
			$this->Match->set('findWinner', true);
			$this->Match->save($this->Match->data);
			
		}
		
		$livematchid = $this->Match->query("SELECT max(match_id) as id FROM livematches");
		
		if (isset($livematchid[0][0]['id'])){
			
			$match = $this->Match->find('first', array('conditions' => array('Match.id' => $livematchid[0][0]['id'])));
			$this->set('match', $match);
			
			$usermanagedclub = -1;
			if ($this->Auth->user('id') == $match['HomeTeam']['user_id']){
				$usermanagedclub = $match['HomeTeam']['id'];
			}
			elseif ($this->Auth->user('id') == $match['AwayTeam']['user_id']){
				$usermanagedclub = $match['AwayTeam']['id'];
			}
			$this->set('usermanagedclub', $usermanagedclub);
			
			
			
			
			//$this->set('usermanagedclub', 12);
			
			if (isset($match['Match']['id'])){
				
				//Hent kampinfo fra livematches
				$initstr = $this->Match->query("SELECT frame FROM livematches WHERE id=0 and match_id=" . $match['Match']['id']);
				$this->set('initstr', $initstr);
				
				//Hent brugerens spillere i kampen
				$players = $this->Match->query("SELECT p.id, firstname, lastname FROM persons p INNER JOIN match_playertactics t ON t.person_id=p.id WHERE user_id=" . $this->Auth->user('id'));
				$this->set('players', $players);
			
			}
		}
		
	}
	
		
	private function remove_item_by_value($array, $val = '', $preserve_keys = true) {
		if (empty($array) || !is_array($array)) return false;
		if (!in_array($val, $array)) return $array;
	
		foreach($array as $key => $value) {
			if ($value == $val) unset($array[$key]);
		}
	
		return ($preserve_keys === true) ? $array : array_values($array);
	}
}
?>