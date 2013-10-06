<?php

class Contract extends AppModel
{
 var $name = 'Contract';
 
    var $belongsTo = array(
        'Club' => array(
			'className' => 'Club'
		),
		'PrevClub' => array(
			'className' => 'Club',
			'foreignKey' => 'from_club'
		),
		'Person' => array(
			'className' => 'Person'
		)
    );
}


// CREATE TABLE contracts (
// id serial primary key, 
// club_id INTEGER REFERENCES clubs(id), 
// person_id INTEGER REFERENCES persons(id), 
// startdate date, 
// enddate date, 
// weeklywage integer, 
// goalbonus integer, 
// assistbonus integer,  
// minimumreleaseclause integer default (-1), 
// role integer REFERENCES contract_roles(ID), 
// transferfee integer default 0, 
// signonfee integer default 0, 
// acceptedbyclub boolean default false, 
// acceptedbyplayer boolean default false, 
// dateoffered timestamp, 
// offered boolean
// );
?>