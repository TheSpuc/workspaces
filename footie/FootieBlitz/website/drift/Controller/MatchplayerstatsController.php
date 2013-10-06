<?php

/**
 * Matchplayerstats Controller
 *
 * @var Matchplayerstats Controller
 */

App::uses('Sanitize', 'Utility');

class MatchplayerstatsController extends AppController
{
	var $name = 'MatchPlayerstats';
  	
  	function stats($id = null) { 
  		$this->autoRender = false;
  		
  		
  		$this->loadModel('Match');
  					  				
  		$match = $this->Match->find('first', array('conditions' => array('Match.id' => $id)));
  		$this->set('match', $match);
		
		if (isset($match['Match']['id'])){
			$this->set('stats', $this->MatchPlayerstat->query(
				"SELECT m.*, p.firstname, p.lastname FROM match_playerstats m INNER JOIN persons p ON m.person_id=p.id WHERE match_id=" . Sanitize::escape($match['Match']['id'])		  	
			));
		}	
		
		$this->loadModel('MatchLineup');
		$lineups = $this->MatchLineup->find('all',	array('conditions' => array('MatchLineup.match_id' => $id)));
		
		if ($lineups[0]['MatchLineup']['club_id'] == $match['Match']['hometeamid']){
			$this->set('homelineup', $lineups[0]);
			$this->set('awaylineup', $lineups[1]);
		}
		else{
			$this->set('homelineup', $lineups[1]);
			$this->set('awaylineup', $lineups[0]);
		}
		
		
		$this->layout = 'ajax';
		$this->render('/Elements/matchplayerstats');
	}

}
?>