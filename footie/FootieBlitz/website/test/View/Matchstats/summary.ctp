<?php

echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');


echo '</br>';
//debug($stadium);
//debug($_SESSION);

?>



<div id="divmatch"  style="margin: 0px auto 0px auto; width:600px;"> 

<div id="divHead" style="text-align: center">
<h1>Match summary</h1>

<table>
<?PHP
echo '<tr><td width=49%><h2 style="float: right;">' . $hometeam['clubname'] . ' ' . $hometeam['goals'] . '</h2></td><td><h2> - </h2></td><td width=49%><h2 style="float: left;">' . $awayteam['goals'] . ' ' . $awayteam['clubname'];
if ($matchstats[0]['Match']['et'] == 1){
	echo ' (extra time)';
}
echo '</h2></td></tr>';

if ($matchstats[0]['Match']['shootoutgoalshome'] > 0 || $matchstats[0]['Match']['shootoutgoalsaway'] > 0){
	echo '</table><table>';
	echo '<tr><td width=49%><h2 style="float: right;">Penalties: ' . $matchstats[0]['Match']['shootoutgoalshome'] . '</h2></td><td><h2> - </h2></td><td width=49%><h2 style="float: left;">' . $matchstats[0]['Match']['shootoutgoalsaway'] . ' </h2></td></tr>';
}

?>
</table>

</div>

<div id="divInfo" style="float: right; padding: 20px; text-align: right;">
<?PHP
echo 'Date: ' . $matchstats[0]['Match']['matchdate'] . '<br />';
echo 'Venue: ' . $stadium['Stadium']['stadiumname'] . '<br />';
echo 'Attendance: ' . $matchstats[0]['Match']['attendance'] . ' (seats: ' . $matchstats[0]['Match']['seats'] . ')';
?>
</div>

<?php

foreach ($events as $e)
{
	echo '<table><tr><td width=50%>';
	if ($e['MatchEvent']['club_id'] == $hometeam['id']){
		echo '\'' . $e['MatchEvent']['matchminute'] . ' - ' . h($e['Person']['lastname']);
		
		if ($e['MatchEvent']['type'] == 0){
			echo ' (goal)';
		}
		
		echo '</td><td>';
	}
	else{
		echo '</td><td width=50% style="text-align: right">';
		
		echo '\'' . $e['MatchEvent']['matchminute'] . ' - ' . h($e['Person']['lastname']);
		
		if ($e['MatchEvent']['type'] == 0){
			echo ' (goal)';
		}
	}
	echo '</td></tr></table>';
}

?>

<table>
<?php 

	echo '<tr><td width=20%>Possesion</td><td><div style="float: right;">' . $hometeam['possession'] . '%</div></td><td width=50%>' . $awayteam['possession'] . '%</td></tr>';
	echo '<tr><td>Shots</td><td><div style="float: right;">' . $hometeam['shots'] . '</div></td><td>' . $awayteam['shots'] . '</td></tr>';
	echo '<tr><td>Corners</td><td><div style="float: right;">' . $hometeam['corners'] . '</div></td><td>' . $awayteam['corners'] . '</td></tr>';
	echo '<tr><td>Throw ins</td><td><div style="float: right;">' . $hometeam['throwins'] . '</div></td><td>' . $awayteam['throwins'] . '</td></tr>';
	echo '<tr><td>Fouls</td><td><div style="float: right;">' . $hometeam['fouls'] . '</div></td><td>' . $awayteam['fouls'] . '</td></tr>';
	echo '<tr><td>Offsides</td><td><div style="float: right;">' . $hometeam['offsides'] . '</div></td><td>' . $awayteam['offsides'] . '</td></tr>';
	
?>
</table>

<h3 style="text-align: center">Lineups</h3>
<?PHP echo $this->element('lineups'); ?>

</div>




<?php
//Nedenstående skal med allersidst når der er script på siden. Se http://book.cakephp.org/view/1594/Using-a-specific-Javascript-engine
echo $this->Js->writeBuffer(); 
?>
