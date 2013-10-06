<?php
App::uses('AppModel', 'Model');
/**
 * User Model
 *
 */
class User extends AppModel {
/**
 * Display field
 *
 * @var string
 */
	public $displayField = 'username';
	
	var $hasMany = array(
			'Person' => array(
				'className' => 'Person',
				'foreignKey' => 'user_id'
			),
			'Forum.Access', 
			'Forum.Moderator'
	);
	var $hasOne = array(
			'Club' => array(
				'className' => 'Club',
				'foreignKey' => 'user_id'
			),
			'Forum.Profile',
			'User_ticket'
	);
/**
 * Validation rules
 *
 * @var array
 */
	public $validate = array(
		'username' => array(
			'notempty' => array(
				'rule' => array('notempty'),
				//'message' => 'Your custom message here',
				//'allowEmpty' => false,
				//'required' => false,
				//'last' => false, // Stop validation after this rule
				//'on' => 'create', // Limit validation to 'create' or 'update' operations
			),
			'between' => array(
                'rule'    => array('between', 2, 15),
                'message' => 'Between 2 to 15 characters'
            ),
		),
		'password' => array(
			'notempty' => array(
				'rule' => array('notempty'),
				//'message' => 'Your custom message here',
				//'allowEmpty' => false,
				//'required' => false,
				//'last' => false, // Stop validation after this rule
				//'on' => 'create', // Limit validation to 'create' or 'update' operations
			),
			'between' => array(
                'rule'    => array('between', 2, 15),
                'message' => 'Between 2 to 15 characters'
            ),
		),
		'email' => array(
			'email' => array(
				'rule' => array('email'),
				//'message' => 'Your custom message here',
				//'allowEmpty' => false,
				//'required' => false,
				//'last' => false, // Stop validation after this rule
				//'on' => 'create', // Limit validation to 'create' or 'update' operations
			)
		),
		'access' => array(
			'numeric' => array(
				'rule' => array('numeric'),
				//'message' => 'Your custom message here',
				//'allowEmpty' => false,
				//'required' => false,
				//'last' => false, // Stop validation after this rule
				//'on' => 'create', // Limit validation to 'create' or 'update' operations
			),
		),
		'isint' => array(
        	'rule'    => 'numeric',
        	'message' => 'Please insert a number.'
    ),

	);
	
	public function beforeSave() {
    if (isset($this->data[$this->alias]['password'])) {
        $this->data[$this->alias]['password'] = AuthComponent::password($this->data[$this->alias]['password']);
    }
    return true;
}
}
// CREATE TABLE users (
// id SERIAL PRIMARY KEY,
// username varchar(60) NOT NULL,
// password varchar(128) NOT NULL,
// email varchar(255) NOT NULL,
// created int NOT NULL,
// access int NOT NULL,
// login int NOT NULL,
// status int NOT NULL DEFAULT 0,
// signature VARCHAR(255) NOT NULL,
// locale VARCHAR(3) NOT NULL DEFAULT 'eng',
// timezone VARCHAR(4) NOT NULL DEFAULT -8,
// totalPosts INT NOT NULL,
// totalTopics INT NOT NULL,
// currentLogin timestamp DEFAULT NULL,
// lastLogin timestamp DEFAULT NULL,
// UNIQUE(username, email)
// );