<?php 
//debug($match);
//debug($stats);
//debug($homelineup);

  	$statarr = array();
  			
	foreach ($stats as $info){
  	
  		$statarr[$info[0]['person_id']] = $info[0];
  				
	} 

    //debug($statarr);
    
	echo '<table><thead><tr><td>' . $match['HomeTeam']['clubname'] . '</td></tr>'; 
    echo '<tr><th>Player</th>';
    echo '<th title="Save attempts">SVA</th>';
    echo '<th title="Successful saves">SV</th>';
    echo '<th title="Clearances">CL</th>';
    echo '<th title="Tackle attempts">TKA</th>';
    echo '<th title="Successful tackles">TK</th>';
    echo '<th title="Interceptions">INT</th>';
    echo '<th title="Free kicks committed">FKC</th>';
    echo '<th title="Pass attempts">PA</th>';
    echo '<th title="Successful passes">P</th>';
    echo '<th title="Assists">ASS</th>';
    echo '<th title="Dribbles">DR</th>';
    echo '<th title="Failed dribbles">FDR</th>';
    echo '<th title="Shot attempts">SH</th>';
    echo '<th title="Shots on target">SHT</th>';
    echo '<th title="Goals">G</th>';        
    echo '<th title="Distance run (meters)">DR</th>';
    echo '<th title="Match rating">MR</th>';
  	echo '</tr></thead><tbody>';
  	
  	
  	for ( $counter = 1; $counter <= 18; $counter += 1) {
		
		echo '<tr>';

		if (isset($homelineup['pl' . $counter]['id'])){

		   	echo '<td>' . h($homelineup['pl' . $counter]['lastname']) . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['saveattempts'] . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['saves'] . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['clearances'] . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['tackles'] . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['successfultackles'] . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['interceptions'] . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['freekickscom'] . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['passattempts'] . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['passessucceeded'] . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['assists'] . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['dribbles'] . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['dribbleslost'] . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['shots'] . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['shotsontarget'] . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['goals'] . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['metersrun'] . '</td>';
			echo '<td>' . $statarr[$homelineup['pl' . $counter]['id']]['rating'] . '</td>';

		}
		else{
			//echo '<td>-</td>';
		}
		
		echo '</tr>';
	}	
  	
    echo '</table>'; 
    
    

	echo '<table><thead><tr><td>' . $match['AwayTeam']['clubname'] . '</td></tr>'; 
    echo '<tr><th>Player</th>';
    echo '<th title="Save attempts">SVA</th>';
    echo '<th title="Successful saves">SV</th>';
    echo '<th title="Clearances">CL</th>';
    echo '<th title="Tackle attempts">TKA</th>';
    echo '<th title="Successful tackles">TK</th>';
    echo '<th title="Interceptions">INT</th>';
    echo '<th title="Free kicks committed">FKC</th>';
    echo '<th title="Pass attempts">PA</th>';
    echo '<th title="Successful passes">P</th>';
    echo '<th title="Assists">ASS</th>';
    echo '<th title="Dribbles">DR</th>';
    echo '<th title="Failed dribbles">FDR</th>';
    echo '<th title="Shot attempts">SH</th>';
    echo '<th title="Shots on target">SHT</th>';
    echo '<th title="Goals">G</th>';        
    echo '<th title="Distance run (meters)">DR</th>';
    echo '<th title="Match rating">MR</th>';
  	echo '</tr></thead><tbody>';
  	
  	
  	for ( $counter = 1; $counter <= 18; $counter += 1) {
		
		echo '<tr>';

		if (isset($awaylineup['pl' . $counter]['id'])){

		   	echo '<td>' . h($awaylineup['pl' . $counter]['lastname']) . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['saveattempts'] . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['saves'] . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['clearances'] . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['tackles'] . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['successfultackles'] . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['interceptions'] . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['freekickscom'] . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['passattempts'] . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['passessucceeded'] . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['assists'] . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['dribbles'] . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['dribbleslost'] . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['shots'] . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['shotsontarget'] . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['goals'] . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['metersrun'] . '</td>';
			echo '<td>' . $statarr[$awaylineup['pl' . $counter]['id']]['rating'] . '</td>';

		}
		else{
			//echo '<td>-</td>';
		}
		
		echo '</tr>';
	}	
  	
    echo '</table>'; 
      	
/*
	$hometeamid = -1;
	$awayteamid = -1;
	$hometeamname = "";
	$awayteamname = "";
	
    $result = db_query("SELECT hometeamid, awayteamid, (SELECT clubname FROM me_club WHERE clubid=m.hometeamid) AS hometeamname, (SELECT clubname FROM me_club WHERE clubid=m.awayteamid) AS awayteamname FROM me_match m WHERE matchid = :matchid", array(':matchid' => $matchId,));
	if ($info= $result->fetchAssoc()){
		$hometeamid = $info['hometeamid'];
		$awayteamid = $info['awayteamid'];
		$hometeamname = $info['hometeamname'];
		$awayteamname = $info['awayteamname'];
	}

	echo '<h2>' . $hometeamname . '</h2>';
	echo '<table><thead><tr>'; 
    echo '<th>Player</th>';
    echo '<th title="Save attempts">SVA</th>';
    echo '<th title="Successful saves">SV</th>';
    echo '<th title="Clearances">CL</th>';
    echo '<th title="Tackle attempts">TKA</th>';
    echo '<th title="Successful tackles">TK</th>';
    echo '<th title="Interceptions">INT</th>';
    echo '<th title="Free kicks committed">FKC</th>';
    echo '<th title="Pass attempts">PA</th>';
    echo '<th title="Successful passes">P</th>';
    echo '<th title="Assists">ASS</th>';
    echo '<th title="Dribbles">DR</th>';
    echo '<th title="Failed dribbles">FDR</th>';
    echo '<th title="Shot attempts">SH</th>';
    echo '<th title="Shots on target">SHT</th>';
    echo '<th title="Goals">G</th>';        
    echo '<th title="Distance run (meters)">DR</th>';
    echo '<th title="Match rating">MR</th>';
  	echo '</tr></thead><tbody>'; 

	$result2 = db_query("SELECT * FROM me_match_lineup WHERE clubid= :clid and matchid = :mid", array( ':clid' =>$hometeamid , ':mid' =>$matchId,));
	if ($record2 = $result2->fetchAssoc()){
		
		$i = 1;
		while ($i < 19){
			$result = db_query("SELECT * FROM me_match_playerstats s INNER JOIN me_person p ON p.personid=s.personid WHERE matchid = :matchid AND s.personid = :pid", array(':matchid' => $matchId, ':pid' => $record2['pl' . $i . 'id'],));
			if ($info = $result->fetchAssoc()){
				      	echo '<tr>';
				      	echo '<td>' . $info ['lastname'] . '</td>';
						echo '<td>' . $info ['saveattempts'] . '</td>';
						echo '<td>' . $info ['saves'] . '</td>';
						echo '<td>' . $info ['clearances'] . '</td>';
						echo '<td>' . $info ['tackles'] . '</td>';
						echo '<td>' . $info ['successfultackles'] . '</td>';
						echo '<td>' . $info ['interceptions'] . '</td>';
						echo '<td>' . $info ['freekickscom'] . '</td>';
						echo '<td>' . $info ['passattempts'] . '</td>';
						echo '<td>' . $info ['passessucceeded'] . '</td>';
						echo '<td>' . $info ['assists'] . '</td>';
						echo '<td>' . $info ['dribbles'] . '</td>';
						echo '<td>' . $info ['dribbleslost'] . '</td>';
						echo '<td>' . $info ['shots'] . '</td>';
						echo '<td>' . $info ['shotsontarget'] . '</td>';
						echo '<td>' . $info ['goals'] . '</td>';
						echo '<td>' . $info ['metersrun'] . '</td>';
						echo '<td>' . $info ['rating'] . '</td>';
				      	echo '</tr>';    
			}
			$i = $i + 1;
		}
				
	}
    echo '</table>'; 
    
	echo '<h2>' . $awayteamname . '</h2>';
	echo '<table><thead><tr>'; 
    echo '<th>Player</th>';
    echo '<th title="Save attempts">SVA</th>';
    echo '<th title="Successful saves">SV</th>';
    echo '<th title="Clearances">CL</th>';
    echo '<th title="Tackle attempts">TKA</th>';
    echo '<th title="Successful tackles">TK</th>';
    echo '<th title="Interceptions">INT</th>';
    echo '<th title="Free kicks committed">FKC</th>';
    echo '<th title="Pass attempts">PA</th>';
    echo '<th title="Successful passes">P</th>';
    echo '<th title="Assists">ASS</th>';
    echo '<th title="Dribbles">DR</th>';
    echo '<th title="Failed dribbles">FDR</th>';
    echo '<th title="Shot attempts">SH</th>';
    echo '<th title="Shots on target">SHT</th>';
    echo '<th title="Goals">G</th>';        
    echo '<th title="Distance run (meters)">DR</th>';
    echo '<th title="Match rating">MR</th>';
  	echo '</tr></thead><tbody>'; 

	$result2 = db_query("SELECT * FROM me_match_lineup WHERE clubid= :clid and matchid = :mid", array( ':clid' =>$awayteamid , ':mid' =>$matchId,));
	if ($record2 = $result2->fetchAssoc()){
		
		$i = 1;
		while ($i < 19){
			$result = db_query("SELECT * FROM me_match_playerstats s INNER JOIN me_person p ON p.personid=s.personid WHERE matchid = :matchid AND s.personid = :pid", array(':matchid' => $matchId, ':pid' => $record2['pl' . $i . 'id'],));
			if ($info = $result->fetchAssoc()){
				      	echo '<tr>';
				      	echo '<td>' . $info ['lastname'] . '</td>';
						echo '<td>' . $info ['saveattempts'] . '</td>';
						echo '<td>' . $info ['saves'] . '</td>';
						echo '<td>' . $info ['clearances'] . '</td>';
						echo '<td>' . $info ['tackles'] . '</td>';
						echo '<td>' . $info ['successfultackles'] . '</td>';
						echo '<td>' . $info ['interceptions'] . '</td>';
						echo '<td>' . $info ['freekickscom'] . '</td>';
						echo '<td>' . $info ['passattempts'] . '</td>';
						echo '<td>' . $info ['passessucceeded'] . '</td>';
						echo '<td>' . $info ['assists'] . '</td>';
						echo '<td>' . $info ['dribbles'] . '</td>';
						echo '<td>' . $info ['dribbleslost'] . '</td>';
						echo '<td>' . $info ['shots'] . '</td>';
						echo '<td>' . $info ['shotsontarget'] . '</td>';
						echo '<td>' . $info ['goals'] . '</td>';
						echo '<td>' . $info ['metersrun'] . '</td>';
						echo '<td>' . $info ['rating'] . '</td>';
				      	echo '</tr>';    
			}
			$i = $i + 1;
		}
				
	}
	echo '</table>'; 
	
	*/
?>