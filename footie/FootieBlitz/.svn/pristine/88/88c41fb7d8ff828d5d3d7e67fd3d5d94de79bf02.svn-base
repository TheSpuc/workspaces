<?php 

//debug($matchstats);

if ($matchstats[0]['Club']['id'] == $matchstats[0]['Match']['hometeamid']){
	$home = $matchstats[0];
	$away = $matchstats[1];
}
else{
	$home = $matchstats[1];
	$away = $matchstats[0];
}

echo '<table><thead><tr>'; 
echo '<th>Stat</th>';
echo '<th>' . $home['Club']['clubname'] . '</th>';
echo '<th>' . $away['Club']['clubname'] . '</th>';
echo '</tr></thead><tbody>'; 

echo '<tr><td>Possession</td><td>' . $home['Matchstat']['possession'] . '</td><td>' . $away['Matchstat']['possession'] . '</td></tr>';
echo '<tr><td>Shots</td><td>' . $home['Matchstat']['shots'] . '</td><td>' . $away['Matchstat']['shots'] . '</td></tr>';
echo '<tr><td>On target</td><td>' . $home['Matchstat']['ontarget'] . '</td><td>' . $away['Matchstat']['ontarget'] . '</td></tr>';
echo '<tr><td>Corners</td><td>' . $home['Matchstat']['corners'] . '</td><td>' . $away['Matchstat']['corners'] . '</td></tr>';      	
echo '<tr><td>Free kicks</td><td>' . $home['Matchstat']['freekicks'] . '</td><td>' . $away['Matchstat']['freekicks'] . '</td></tr>';      	
echo '<tr><td>Fouls comitted</td><td>' . $home['Matchstat']['fouls'] . '</td><td>' . $away['Matchstat']['fouls'] . '</td></tr>';
echo '<tr><td>Throw ins</td><td>' . $home['Matchstat']['throwins'] . '</td><td>' . $away['Matchstat']['throwins'] . '</td></tr>';
echo '<tr><td>Offsides</td><td>' . $home['Matchstat']['offsides'] . '</td><td>' . $away['Matchstat']['offsides'] . '</td></tr>';   
echo '<tr><td>Yellow cards</td><td>' . $home['Matchstat']['yellowcards'] . '</td><td>' . $away['Matchstat']['yellowcards'] . '</td></tr>';      	   	
echo '<tr><td>Red cards</td><td>' . $home['Matchstat']['redcards'] . '</td><td>' . $away['Matchstat']['redcards'] . '</td></tr>';  
echo '</table>';    	   	      	


?>