<?php 


//debug($homeatt);


if ($screen == 1){
	//screen 1 = attendance	
	echo '<h3>Attendance</h3>';
	
	echo '<table id="tblStats" class="tablesorter">';
	echo '<thead>'; 
	echo '<tr>';
	echo '<th style="cursor:pointer; width:40%">Club</td>';
	echo '<th style="cursor:pointer; width:15%">Home min.</td>';
	echo '<th style="cursor:pointer; width:15%">Home max.</td>';	
	echo '<th style="cursor:pointer; width:15%">Home avg.</td>';
	echo '<th style="cursor:pointer; width:15%">Home total</td>';
	echo '</tr>';
	echo '</thead>'; 
	echo '<tbody>';
	
	foreach ($homeatt as $info){
   		echo "<tr><td>" . $info['HomeTeam']['clubname'] . "</td><td>" . number_format($info[0]['min'], 0, ',', '.') . "</td><td>" . number_format($info[0]['max'], 0, ',', '.') . "</td><td>" . number_format($info[0]['avg'], 0, ',', '.') . "</td><td>" . number_format($info[0]['att'], 0, ',', '.') . "</td></tr>";
	}   
}
elseif ($screen == 2){
	//screen 2 = form	
	echo '<h3>Form - last five matches</h3>';

	echo '<table id="tblStats" class="tablesorter">';
	echo '<thead>'; 
	echo '<tr>';
	echo '<th style="cursor:pointer; width:40%">Club</td>';
	echo '<th style="cursor:pointer; width:20%">Goals conceded</td>';	
	echo '<th style="cursor:pointer; width:20%">Goals scored</td>';
	echo '<th style="cursor:pointer; width:20%">Results</td>';
	echo '<th style="cursor:pointer; width:20%">Points</td>';
	echo '</tr>';
	echo '</thead>'; 
	echo '<tbody>';
	
	foreach ($form as $info){
		$form = strrev($info[0]['form']);
		$form = str_replace('3', 'W', $form);
		$form = str_replace('1', 'D', $form);	
		$form = str_replace('0', 'L', $form);
   		echo "<tr><td>" . $info[0]['clubname'] . "</td><td>" . $info[0]['against'] . "</td><td>" . $info[0]['for'] . "</td><td>" . $form  . "</td><td>" . $info[0]['points'] . "</td></tr>";
	}  
}
     
  
     
echo '</tbody></table>';   	   	      	


?>