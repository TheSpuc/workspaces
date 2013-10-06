<?PHP

App::uses('AppModel', 'Model');
/**
 * Message Model
 *
 */
class Message extends AppModel {

	var $actsAs = array('Containable');
	
	 var $belongsTo = array(
			'From'	=> array(
				 'className' => 'User',
				 'foreignKey' => 'from_user_id'
			),
			'To' => array(
				'className' => 'User',
				'foreignKey' => 'to_user_id'
			),
			'Message_thread' => array(
				'className' => 'Message_thread',
				'foreignKey' => 'thread_id'
			)
		 );


}



// CREATE TABLE messages(
// id SERIAL PRIMARY KEY,
// thread_id integer
// from_user_id integer,
// text varchar(8000),
// expires timestamp,
// to_user_id integer
// read boolean
// );


?>