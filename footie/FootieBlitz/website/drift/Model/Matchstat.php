<?php

class Matchstat extends AppModel
{
 var $name = 'matchstats';
    
	var $belongsTo = array(
        'Match' => array(
			'className' => 'Match',
			'foreignKey' => 'match_id'
		),
		'Club' => array(
			'className' => 'Club',
			'foreignKey' => 'club_id'
		)
    );
	
}


// CREATE TABLE matchstats(
// id SERIAL PRIMARY KEY,
// match_id int references matches(id),
// club_id int references clubs,
// shots int,
// ontarget int,
// possession int,
// corners int,
// freekicks int,
// throwins int,
// fouls int,
// offsides int,
// yellowcards int,
// redcards int
// );

?>