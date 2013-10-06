<?php

class Match extends AppModel
{
 var $name = 'Match';

 	var $hasOne = array(
	 		'matchstats' => array(
		 		'className' => 'Matchstat',
		 		'foreignKey' => 'club_id'
	 		)
		 	
	 );
 	
	var $belongsTo = array(
        'HomeTeam' => array(
			'className' => 'Club',
			'foreignKey' => 'hometeamid'
		),
		'AwayTeam' => array(
			'className' => 'Club',
			'foreignKey' => 'awayteamid'
		),
		'League' => array(
			'className' => 'League',
			'foreignKey' => 'league_id'
		),
		'Stadium' => array(
			'className' => 'Stadium',
			'foreignKey' => 'stadium_id'
		)

    );
	
}

// CREATE TABLE matches(
// id SERIAL PRIMARY KEY,
// status int,
// hometeamid int references clubs(id),
// awayteamid int references clubs(id),
// matchdate timestamp,
// stadium_id int,
// hometeamgoals int,
// awayteamgoals int,
// league_id int,
// attendance int
// );

?>