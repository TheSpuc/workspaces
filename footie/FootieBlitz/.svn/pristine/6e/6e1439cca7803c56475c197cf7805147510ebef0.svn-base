<?php

class Stadium extends AppModel
{
 var $name = 'Stadium';
  var $useTable = 'stadiums';
	 
	 var $hasMany = array(
		 'Match' => array(
			'className' => 'Match',
			'foreignKey' => 'stadium_id'
		 )
	 );

}

// CREATE TABLE stadiums(
// id SERIAL PRIMARY KEY,
// stadiumname varchar(50),
// seats integer,
// terraces integer
// );

?>

