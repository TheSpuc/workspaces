<?php
App::uses('AppModel', 'Model');
/**
 * User Model
 *
 */
class User_ticket extends AppModel {

	
 var $belongsTo = array(
		'User'		=> array(
			 'className' => 'User'//Her kunne vi m�ske bruge owner som className
		)	
	 );


// CREATE TABLE user_tickets(
// id SERIAL PRIMARY KEY,
// user_id integer,
// hash varchar(36),
// expires timestamp
// );
	 
}
	