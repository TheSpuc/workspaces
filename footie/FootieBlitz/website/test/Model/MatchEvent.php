<?php

class MatchEvent extends AppModel
{
     
	var $belongsTo = array(
        'Person' => array(
			'className' => 'Person',
			'foreignKey' => 'person_id'
		)

    );
	
}



?>