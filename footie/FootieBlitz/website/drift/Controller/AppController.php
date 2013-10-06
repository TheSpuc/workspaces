<?php

/**
 * App Controller
 *
 * @var App Controller
 */

    class AppController extends Controller {
		// AppController's components are NOT merged with defaults,
		// so session component is lost if it's not included here!
		var $components = array('Session', 'Auth' => array(
            'loginRedirect' => array('controller' => 'users', 'action' => 'office'),
            'logoutRedirect' => array('controller' => 'users', 'action' => 'login')
        ));
		
		public $helpers = array('Form', 'Html', 'FootieText', 'Js', 'Session');
		
		function beforeRender(){
			//menuen
			//$this->set('menuclubs', $this->Session->read('Clubs'));
			
		}
		
		function beforeFilter() {
			
			$this->Auth->allow('login');
			$this->Auth->allow('livedata');
			 
			$this->set('logged_in', $this->_loggedIn());
			$this->set('admin', $this->_isAdmin());
			$this->set('users_username', $this->_usersUsername());
			$this->set('uid', $this->_uid());
			
			//menuen
			$this->fillMenu();			
		}
		
		function array_value_recursive($key, array $arr){
			$val = null;
			array_walk_recursive($arr, function($v, $k) use($key, &$val){
				$val = $k == $key ? $v : (!is_null($val) ? $val : false);
			});
			return $val;
		}
		
		function _isAdmin(){
			$admin = FALSE;
			if($this->Auth->user('access') == '0'){
				$admin = TRUE;
			}
			return $admin;
		}
		
		function _loggedIn(){
			$logged_in = FALSE;
			if($this->Auth->user()){
				$logged_in = TRUE;
			}
			return $logged_in;
		}
		
		function _usersUsername(){
			$users_username = NULL;
			if($this->Auth->user()){
				$users_username = $this->Auth->user('username');
			}
			return $users_username;
		}
		
		function _uid(){
			$uid = NULL;
			if($this->Auth->user()){
				$uid = $this->Auth->user('id');
			}
			return $uid;
		}

		
		public function fillMenu(){
			$this->loadModel('Season');
			$this->getNrNewMessages();			
			$menuclubs = $this->Session->read('Clubs');
			if($this->Auth->user() && !isset($menuclubs)){
				$menuclubs = array('OwnerClub' => array(), 'PlayerClubs' => array());
				$menudata = array();
				$menuclubs['OwnerClub'] = ClassRegistry::init('Club')->find('all',				
					array(
						'recursive' => 0,
						'fields' => array('Club.id', 'Club.clubname', 'Club.shortname', 'Club.user_id', 'Club.league_id', 'League.leaguename', 'League.id'),
						'conditions' => 'Club.user_id = ' . $this->Auth->user('id')
					)
				);
				//Hvis denne skal ÃƒÂ¦ndres til cakephp, skal der ogsÃƒÂ¥ ÃƒÂ¦ndres i elements/menu.ctp
				//hvor ens klubber vises.
				$menuclubs['PlayerClubs'] = ClassRegistry::init('User')->query('SELECT DISTINCT ON(c.id, c.shortname, c.clubname) c.id, c.shortname, c.clubname, p.firstname, c.league_id, l.leaguename, u.id AS user_id FROM clubs c INNER JOIN contracts co ON co.club_id = c.id INNER JOIN persons p ON p.id = co.person_id INNER JOIN users u ON p.user_id = u.id INNER JOIN leagues l ON c.league_id = l.id WHERE u.id =' . $this->Auth->user('id') . ' AND co.acceptedbyclub = true AND co.acceptedbyplayer = true AND enddate > now()');	
				
				$menuplayers['Players'] = ClassRegistry::init('Person')->find('all',
					array(
						'recursive' => -1,
						'fields' => array('Person.id', 'Person.firstname', 'Person.lastname', 'Person.playerpoints'),
						'conditions' => 'Person.user_id = ' . $this->Auth->user('id')
					)
				);

				//Test for et se om man kan lave et array med bedre styr på klub, spiller og liga data
				//kunne være fedt at få et menu-array i stil med:
				//array(clubs =>
					//array(array('id' => id, 'shortname' => shortname, osv. osv. owner => yes/no),
					
				$mclubs = array();
				$mleagues = array();
				$mplayers = array();
				$clubIDs = array();
				$mallleagues = array();
				
				$count0 = 0;
				foreach($menuclubs as $menclubs){
					foreach($menclubs as $meclubs){
						foreach($meclubs as $key => $clubs){
							//skal udskrive hvilken tilknytning man har til klubben. Owner eller Player
							$player = 0;
							if(array_key_exists('firstname', $clubs)){ $ownerplayer = ' (player)'; $player = 1;}
							else{ $ownerplayer = ' (owner)';}
							$clubnr = 'club ' . $count0;
							
							
							if($player == 1 && array_key_exists('clubname', $clubs) && $this->array_value_recursive('clubname', $mclubs) != $clubs['clubname']){}//hvis det er en spillerklub skal der checkes om klubben allerede er på listen
							else{
								//Her laves data
								if (array_key_exists('id', $clubs) && array_key_exists('clubname', $clubs)){ //der spørges om to ting, da der findes en id under leagues, som ikke skal tages med her.
									$mclubs[$clubnr]['id'] = $clubs['id'];
									$mclubs[$clubnr]['clubname'] = $clubs['clubname'] . $ownerplayer;
								}
								if (array_key_exists('shortname', $clubs)){$mclubs[$clubnr]['shortname'] = $clubs['shortname'] . $ownerplayer;}
								if (array_key_exists('leaguename', $clubs)){$mclubs[$clubnr]['leaguename'] = $clubs['leaguename'];}
								if (array_key_exists('league_id', $clubs)){$mclubs[$clubnr]['league_id'] = $clubs['league_id'];}
							}
						}
						$count0 += 1;				 
					}
				}
				//her findes og føjes ligaer til mleagues
				foreach($mclubs as $clubs){
					array_push($clubIDs, $clubs['id']);
					if($this->array_value_recursive('id', $mleagues) != $clubs['league_id']){
						$mleagues[$clubs['leaguename']] = array('id' =>$clubs['league_id'], 'leaguename' => $clubs['leaguename']);
					}
				}
				//her findes og føjes spillere til mplayers
				$count = 0;
				foreach($menuplayers as $menplayers){
					foreach($menplayers as $person){
					if (array_key_exists('id', $person['Person'])){$mplayers['Person ' . $count]['id'] = $person['Person']['id'];}
					if (array_key_exists('firstname', $person['Person'])){$mplayers['Person ' . $count]['firstname'] = $person['Person']['firstname'];}
					if (array_key_exists('lastname', $person['Person'])){$mplayers['Person ' . $count]['lastname'] = $person['Person']['lastname'];}
					if (array_key_exists('playerpoints', $person['Person'])){$mplayers['Person ' . $count]['playerpoints'] = $person['Person']['playerpoints'];}
					$count += 1;
					}
				}
				
				$csClubIds = '(' . implode(",", $clubIDs) . ')';
				if (count($clubIDs) > 0){
					$mallleagues = ClassRegistry::init('Club')->query('SELECT DISTINCT league_id, leaguename FROM matches m INNER JOIN leagues l ON m.league_id=l.id WHERE matchdate > (SELECT max(firstday) FROM seasons) AND matchdate < (SELECT max(lastday) FROM seasons) AND hometeamid IN ' . $csClubIds . ' OR awayteamid IN ' . $csClubIds);
				}
				
				$menudata['mclubs'] = $mclubs;
				$menudata['mleagues'] = $mleagues;
				$menudata['mplayers'] = $mplayers;
				$menudata['mallleagues'] = $mallleagues;
				$this->Session->write('Clubs', $menudata);
			}
			$this->set('menudata', $this->Session->read('Clubs'));
			
			//Find current season and fill in with days in between
			$season = $this->Season->find('all', array(
				'conditions' => array(
					'Season.firstday <= ' => date('Y-m-d'),
					'Season.lastday >= ' => date('Y-m-d')
				),
				'recursive' => -1
			));
			$day = strtotime($season['0']['Season']['firstday']);
			$season['0']['Season']['firstday'] = date("Y-m-d",$day);
			$lastday = strtotime($season['0']['Season']['lastday']);
			$season['0']['Season']['lastday'] = date("Y-m-d",$lastday);
			$cont = 1;
			while($day < $lastday){
			$season['0']['Season']['seasondays'][$cont] = date("Y-m-d",$day);			
			$day = strtotime('+1 day', $day);
			$cont += 1;
			$season['0']['Season']['seasondays'][$cont] = date("Y-m-d",$day);	
			}
			
			$this->set('season', $season);
		
			//Find current season and fill in with days in between
			$season = $this->Season->find('all', array(
				'conditions' => array(
					'Season.firstday <= ' => date('Y-m-d'),
					'Season.lastday >= ' => date('Y-m-d')
				),
				'recursive' => -1
			));
			$day = strtotime($season['0']['Season']['firstday']);
			$season['0']['Season']['firstday'] = date("Y-m-d",$day);
			$lastday = strtotime($season['0']['Season']['lastday']);
			$season['0']['Season']['lastday'] = date("Y-m-d",$lastday);
			$cont = 1;
			while($day < $lastday){
			$season['0']['Season']['seasondays'][$cont] = date("Y-m-d",$day);			
			$day = strtotime('+1 day', $day);
			$cont += 1;
			$season['0']['Season']['seasondays'][$cont] = date("Y-m-d",$day);	
			}
			
			$this->set('season', $season);
		}
		
		private function getNrNewMessages(){
			$newMessages = ClassRegistry::init('Message')->find('all', array(
				'conditions' => array(
					'read != true',
					'Message.to_user_id' => $this->Auth->user('id'),
					'Message.from_user_id <>' => $this->Auth->user('id')
				),
				'order' => 'Message.thread_id'
			));
			$thId = 0;
			$nrMsg = 0;
			foreach($newMessages as $new){
				if($new['Message']['thread_id'] != $thId){
					$nrMsg = $nrMsg + 1;
					$thId = $new['Message']['thread_id'];
				}
			}			
			$this->set('newMessages', $nrMsg);
			$newforummsgcount = 0;
			if ($this->Auth->user('id') > 0){
				$newforummsgcount = ClassRegistry::init('User')->query("select count(*) as count from forum_posts where user_id <> " . $this->Auth->user('id') . " AND created > (SELECT lastindexview from forum_profiles where user_id=" . $this->Auth->user('id') . ")");
			}
			$this->set('newforummsgcount', $newforummsgcount);
				
		}
		// function beforeRender(){
			// if(!array_key_exists('requested', $this -> params)){
				// $user = $this->session->read($this->Auth->sessionKey);
				// $this->set(compact('user'));
			// }
		// }
    }
    ?>

