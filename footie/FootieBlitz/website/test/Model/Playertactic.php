<?php

class Playertactic extends AppModel
{
 var $name = 'playertactics';
    
	var $belongsTo = array(
		'Person' => array(
			'className' => 'Person',
			'foreignKey' => 'person_id'
		)
    );
	
}


?>