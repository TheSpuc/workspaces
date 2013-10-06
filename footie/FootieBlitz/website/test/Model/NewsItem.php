<?PHP

App::uses('AppModel', 'Model');
/**
 * NewsItem Model
 *
 */
class NewsItem extends AppModel {

	//var $actsAs = array('Containable');
	
	 var $belongsTo = array(
			'League'	=> array(
				 'className' => 'League',
				 'foreignKey' => 'league_id'
			)
		 );


}



?>