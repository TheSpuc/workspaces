<?php 

echo '<table>';

if ($type == 1){
	echo $html->tableHeaders(array('First name', 'Last name', 'Age', 'Energy'));

    foreach ($squaddata as $player){
		echo $html->tableCells(array(h($player['Person']['firstname']), h($player['Person']['lastname']),$player['Person']['age'], $player['Person']['energy']));
 	}
 	
 	echo 'asd';
}

if ($type == 2){
	echo $html->tableHeaders(array('First name', 'Last name', 'Age', 'Wage', 'Contract ends'));

    foreach ($squaddata as $player){
		echo $html->tableCells(array(h($player['Person']['firstname']), h($player['Person']['lastname']),$player['Person']['age'], $player['Contract']['weeklywage'], $player['Contract']['enddate']));
 	}
}

if ($type == 3){
	echo $html->tableHeaders(array('First name', 'Last name', 'ha', 'ss', 'ro', 'ca', 're'));

    foreach ($squaddata as $player){
		echo $html->tableCells(array(h($player['Person']['firstname']), 
									h($player['Person']['lastname']),
									$player['Person']['handling'], 
									$player['Person']['shotstopping'], 
									$player['Person']['rushingout'],
									$player['Person']['commandofarea'],
									$player['Person']['reaction']));
 	}
}


echo '</table>';
?>