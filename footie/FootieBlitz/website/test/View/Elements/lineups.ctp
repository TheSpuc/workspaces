<table>
<?php 

	for ( $counter = 1; $counter <= 18; $counter += 1) {
		
		if ($counter == 12){
			echo '</table><h4 style="text-align: center">Substitutes</h4><table>';
		}
		
		echo '<tr>';
		echo '<td width=50%>';
		if (isset($homelineup['pl' . $counter]['id'])){
			echo $homelineup['pl' . $counter]['shirtnumber'] . '. ' . h($homelineup['pl' . $counter]['lastname']);
		}
		else{
			echo '-';
		}
		
		for ( $i = 1; $i <= 3; $i += 1) {
			if (isset($homelineup['MatchLineup']['subon' . $i])){
				if ($homelineup['MatchLineup']['subon' . $i] == $homelineup['pl' . $counter]['id']){
					echo ' (on: \'' . $homelineup['MatchLineup']['subtime' . $i] . ')';
				}
				if ($homelineup['MatchLineup']['suboff' . $i] == $homelineup['pl' . $counter]['id']){
					echo ' (off: \'' . $homelineup['MatchLineup']['subtime' . $i] . ')';
				}
			}
		}
		
		echo '</td>';
		echo '<td width=50% style="text-align: right">';
		
		for ( $i = 1; $i <= 3; $i += 1) {
			if (isset($awaylineup['MatchLineup']['subon' . $i])){
				if ($awaylineup['MatchLineup']['subon' . $i] == $awaylineup['pl' . $counter]['id']){
					echo '(on: \'' . $awaylineup['MatchLineup']['subtime' . $i] . ') ';
				}
				if ($awaylineup['MatchLineup']['suboff' . $i] == $awaylineup['pl' . $counter]['id']){
					echo '(off: \'' . $awaylineup['MatchLineup']['subtime' . $i] . ') ';
				}
			}
		}
		if (isset($awaylineup['pl' . $counter]['id'])){
			echo $awaylineup['pl' . $counter]['shirtnumber'] . '. ' . h($awaylineup['pl' . $counter]['lastname']);
		}
		else{
			echo '-';
		}
		
		echo '</td>';
		echo '</tr>';
	}	

	
?>
</table>