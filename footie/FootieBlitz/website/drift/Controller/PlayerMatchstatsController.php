<?php

/**
 * PlayerMatchstats Controller
 *
 * @var PlayerMatchstats Controller
 */

class MatchplayerstatsController extends AppController
{
	var $name = 'MatchPlayerstats';

	public $helpers = array('Js');
  	
  	function stats($id = null) { 
  		$this->autoRender = false;
  		
  		$lineup = $this->Matchlineup->find('first', array('conditions' => array('Matchlineup.match_id' => $id)));
  		$this->set('lineup', 1);
		
		/*
		if (isset()){
			$this->set('matchplayerstats', $this->MatchPlayerstat->query(
				'SELECT m.*, p.firstname, p.lastname FROM lineups l INNER JOIN persons p ON l.person_id=p.id WHERE match_id=' . $match['Match']['id']
		  	));
		}	
		*/
		
		$this->layout = 'ajax';
		$this->render('/Elements/matchplayerstats');
	}

}
?>