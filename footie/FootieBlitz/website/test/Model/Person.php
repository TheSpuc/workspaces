<?php

class Person extends AppModel
{
	public $actsAs = array('Containable');
	
	var $name = 'Person';
	var $useTable = 'persons';
	
	var $hasOne = array(
		'ActiveContract' => array(
			'className' => 'Contract',
			'foreignKey' => 'person_id',
			'conditions' => array('ActiveContract.acceptedbyclub' => true,
								'ActiveContract.acceptedbyplayer' => true,
								'ActiveContract.offered' => true,
								'ActiveContract.enddate >' => 'now()')
			),
		'Playertactic' 	=> array(
			'className' => 'Playertactic',
			'foreignKey' => 'person_id')
			

	 );
 
	var $hasMany = array(
	   /* 'Contract' => array(
			'className' => 'Contract'
		),*/
		'MatchPlayerstat' => array(
			'className' => 'MatchPlayerstat',
			'conditions'    => array('MatchPlayerstat.rating >' => 0),
            'limit'        => '5'
         
		),
		'PersonTrait' 	=> array(
			'className' => 'PersonTrait',
			'foreignKey' => 'person_id'
		),
		'PersonThought' 	=> array(
					'className' => 'PersonThought',
					'foreignKey' => 'person_id',
					'conditions' => array('active' => true),
					'order' => 'date DESC'
		)
	
	);
	
	var $belongsTo = array(
		'User' => array(
			'className' => 'User'
		),
		'TrainingRegime' 	=> array(
				'className' => 'TrainingRegime',
				'foreignKey' => 'training_regime_id')
	);
	
	public $validate = array(
		'firstname' => array(
			'notempty' => array(
				'rule' => array('notempty'),
				'message' => 'Your custom message here',
				//'allowEmpty' => false,
				//'required' => false,
				//'last' => false, // Stop validation after this rule
				'on' => 'create', // Limit validation to 'create' or 'update' operations
			),
			'between' => array(
                'rule'    => array('between', 2, 20),
                'message' => 'Between 2 to 15 characters'
            )
		),
		'lastname' => array(
			'notempty' => array(
				'rule' => array('notempty'),
				'message' => 'Your custom message here',
				//'allowEmpty' => false,
				//'required' => false,
				//'last' => false, // Stop validation after this rule
				'on' => 'create', // Limit validation to 'create' or 'update' operations
			),
			'between' => array(
                'rule'    => array('between', 2, 20),
                'message' => 'Between 2 to 15 characters'
            )
		),
	);
}


// CREATE TABLE persons(
// id SERIAL PRIMARY KEY,
//user_id int references users(id),
// firstname varchar(20),
// lastname varchar(20),
// age int default 15,
// acceleration real default 15,
// topspeed real default 15,
// dribbling real default 15,
// marking real default 15,
// energy real default 100,
// strength real default 15,
// tackling real default 15,
// agility real default 15,
// reaction real default 15,
// handling real default 15,
// shooting real default 15,
// shotpower real default 15,
// passing real default 15,
// technique real default 15,
// height real default 180,
// jumping real default 15,
// stamina real default 15,
// shirtnumber real,
// heading real default 15,
// money bigint default 0,
// retired boolean default false,
// playerpoints real default 100,
// npc boolean default false,
// created timestamp default(now()),
// commandofarea real default 15,
// shotstopping real default 15,
// rushingout real default 15
// );
?>