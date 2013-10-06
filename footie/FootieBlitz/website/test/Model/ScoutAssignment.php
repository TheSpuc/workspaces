<?php

class ScoutAssignment extends AppModel
{
 var $name = 'ScoutAssignment';
 
    var $belongsTo = array(
        'User' => array(
			'className' => 'User'
		),
		'Person' => array(
			'className' => 'Person'
		),
		'Country' => array(
			'className' => 'Country'
		)
    );
    
    var $hasMany = array(
            'ScoutReport' => array(
    			'className' => 'ScoutReport',
    			'order' => 'potential DESC'
    		)
    );
}

//CREATE TABLE scout_assignments (
//id serial primary key, 
//club_id int references clubs(id), 
//user_id int references users(id), 
//country_id int references countries(id), 
//person_id int references persons(id), 
//start timestamp default now(), 
//finished timestamp);
?>