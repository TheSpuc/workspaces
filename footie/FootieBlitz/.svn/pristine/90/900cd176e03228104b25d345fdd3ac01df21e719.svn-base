<?php

class AgentContract extends AppModel
{
 var $name = 'AgentContract';
 
    var $belongsTo = array(
        'User' => array(
			'className' => 'User'
		),
		'Person' => array(
			'className' => 'Person'
		)
    );
}


// CREATE TABLE agent_contracts (
//  id serial primary key,
//  user_id int references users(id),
//  person_id int references persons(id),
//  offered timestamp,
//  accepted timestamp,
//  cancelled timestamp,
//  percent_wage int default 0,
//  percent_fee int default 0
// );
?>