<?php

class Club extends AppModel
{
 var $name = 'Club';
 	
 	public $actsAs = array('Containable');
 
    var $hasMany = array(
        'Contract' => array(
            'className'  => 'Contract',
            'foreignKey' => 'club_id',
            'order'      => 'Contract.id DESC'
        ),
	    'Construction' => array(
                'className'  => 'Construction',
                'foreignKey' => 'club_id',
                'conditions' => array('Construction.status' => '0')
	    )
    );
	 var $belongsTo = array(
		'League' 	=> array(
			'className' => 'League'
			),
		'User'		=> array(
			 'className' => 'User'//Her kunne vi måske bruge owner som className
			),
		'Stadium' => array(
			 'className' => 'Stadium',
			)
	 );
	 var $hasOne = array(
	        'Lineup' => array(
	           //  'className'  => 'Contract',
	           //  'foreignKey' => 'club_id',
	 		),
	 		'Teamtactics' => array(
			 //  'className'  => 'Contract',
			 //  'foreignKey' => 'club_id',
	 		)
	 );
	// public function getMatches(){
	 // return $this->Match->find('all', array(
		// 'conditions' => array(
			// 'OR' => array('Match.hometeamid' => clubid), array('Match.awayteamid' => clubid)
		// )
	// ));	 
	// }
}


// CREATE TABLE clubs (
// id serial primary key, 
// clubname varchar(60) NOT NULL, 
// shortname varchar(6) NOT NULL, 
// league_id INTEGER REFERENCES leagues(id), 
// firstcolor INTEGER NOT NULL, 
// secondcolor INTEGER NOT NULL, 
// stadium_id INTEGER REFERENCES stadiums(id), 
// money INTEGER NOT NULL, 
// owner_id INTEGER REFERENCES users(id), 
// trainingfacc INTEGER DEFAULT 1, 
// seatprice INTEGER DEFAULT 5, 
// standprice INTEGER default 5, 
// fame INTEGER DEFAULT 2000, 
// created timestamp NOT NULL
// );
?>

