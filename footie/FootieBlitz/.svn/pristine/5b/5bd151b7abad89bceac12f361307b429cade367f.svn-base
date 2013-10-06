<?php

/**
 * Matchstats Controller
 *
 * @var Matchstats Controller
 */

class MatchstatsController extends AppController
{
	var $name = 'Matchstats';

	public $helpers = array('Js');


	function summary($id = null){
		$matchstats = $this->Matchstat->find(
			'all',
			array(
				'conditions' => array('Matchstat.match_id' => $id),
				'order' => 'Club.id DESC'
    		)
  		);
		//making the arrays
		$home = null;
		$away = null;
		$home['goals'] = $matchstats[0]['Match']['hometeamgoals'];
		$away['goals'] = $matchstats[0]['Match']['awayteamgoals'];
		
		$homeStatIndex = 0;
		$awayStatIndex = 1;
		if($matchstats[0]['Match']['hometeamid'] == $matchstats[1]['Club']['id']){
			$homeStatIndex = 1;
			$awayStatIndex = 0;
		}
		
		$home['id'] = $matchstats[$homeStatIndex]['Club']['id'];
		$away['id'] = $matchstats[$awayStatIndex]['Club']['id'];
		$home['clubname'] = $matchstats[$homeStatIndex]['Club']['clubname'];
		$away['clubname'] = $matchstats[$awayStatIndex]['Club']['clubname'];
		$home['possession'] = $matchstats[$homeStatIndex]['Matchstat']['possession'];
		$away['possession'] = $matchstats[$awayStatIndex]['Matchstat']['possession'];
		$home['shots'] = $matchstats[$homeStatIndex]['Matchstat']['shots'];
		$away['shots'] = $matchstats[$awayStatIndex]['Matchstat']['shots'];
		$home['corners'] = $matchstats[$homeStatIndex]['Matchstat']['corners'];
		$away['corners'] = $matchstats[$awayStatIndex]['Matchstat']['corners'];
		$home['throwins'] = $matchstats[$homeStatIndex]['Matchstat']['throwins'];
		$away['throwins'] = $matchstats[$awayStatIndex]['Matchstat']['throwins'];
		$home['fouls'] = $matchstats[$homeStatIndex]['Matchstat']['fouls'];
		$away['fouls'] = $matchstats[$awayStatIndex]['Matchstat']['fouls'];
		$home['offsides'] = $matchstats[$homeStatIndex]['Matchstat']['offsides'];
		$away['offsides'] = $matchstats[$awayStatIndex]['Matchstat']['offsides'];
		
		$this->set('hometeam', $home);
		$this->set('awayteam', $away);
		$this->set('matchstats', $matchstats);
		
		$this->loadModel('MatchLineup');
		$lineups = $this->MatchLineup->find('all',	array('conditions' => array('MatchLineup.match_id' => $id)));
		
		if ($lineups[0]['MatchLineup']['club_id'] == $matchstats[0]['Match']['hometeamid']){
			$this->set('homelineup', $lineups[0]);
			$this->set('awaylineup', $lineups[1]);
		}
		else{
			$this->set('homelineup', $lineups[1]);
			$this->set('awaylineup', $lineups[0]);
		}
		
		$this->loadModel('MatchEvent');
		$events = $this->MatchEvent->find('all',	array('conditions' => array('MatchEvent.match_id' => $id), 'order' => 'MatchEvent.matchminute ASC'));
		$this->set('events', $events);
		
		$this->loadModel('Stadium');
		$this->Stadium->recursive = -1;
		$this->set('stadium', $this->Stadium->find('first',	array('conditions' => array('Stadium.id' => $matchstats[0]['Match']['stadium_id']))));
  	}
  	
  	function stats($id = null) { 
  		$this->autoRender = false;
  		
		$this->set('matchstats', $this->Matchstat->find(
					'all',
					array(
						'conditions' => array('Matchstat.match_id' => $id)
		    		)
		  		));
		
// 		$this->viewPath = 'elements';
		$this->layout = 'ajax';
		$this->render('/Elements/matchstats');
	}

}
?>