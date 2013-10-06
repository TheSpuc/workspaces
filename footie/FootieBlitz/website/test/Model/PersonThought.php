<?php
class PersonThought extends AppModel {

		var $name = 'PersonThought';
		var $useTable = 'person_thoughts';

		var $belongsTo = array(
		        'Person' => array(
					'className' => 'Person',
					'foreignKey' => 'person_id'
			)
		);
}
?>