<?PHP
App::uses('AppModel', 'Model');
/**
 * Message_thread Model
 *
 */
class Message_thread extends AppModel {

	var $actsAs = array('Containable');
	
	var $hasMany = array(
			'Message' => array(
				'className' => 'Message',
				'foreignKey' => 'thread_id'
			)
	);



// CREATE TABLE message_threads(
// id SERIAL PRIMARY KEY,
// subject varchar(120),
// updated timestamp
// );

}
?>