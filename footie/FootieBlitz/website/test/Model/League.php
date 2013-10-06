<?php

class League extends AppModel
{
 var $name = 'League';

 	public $actsAs = array('Containable');
 	
	var $hasMany = array(
		'Club' 	=> array(
			'className' => 'Club',
			'foreignKey' => 'league_id'
		),
		'Match' => array(
			'className' => 'Match',
			'foreignKey' => 'league_id'
		)
	);
}


// CREATE TABLE leagues (
// id serial primary key, 
// leaguename varchar(50) NOT NULL, 
// leaguereputation integer default 1
// );
?>