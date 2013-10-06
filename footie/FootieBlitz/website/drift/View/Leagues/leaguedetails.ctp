
<?php

echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
echo $this->Html->script('/js/leagues.js');
echo $this->Html->script('tablesorter');

echo '</br>';
//debug($maxseason);
//debug($maxseason);

?>

<div id="divcontent" style="margin: 0px auto 0px auto;">
<div style="width:70%;  float: left; margin-right: 3%;">
<h2><?PHP echo $league[0]['League']['leaguename']; ?></h2>

<form id="frmSeason" method="post">
Season: 
<select name="slSeason" id="slSeason">
<?PHP 

for ($counter = 1; $counter <= $maxseason; $counter += 1) {
  if($selectedSeason == $counter){ 
    echo '<option value=' . $counter . ' selected="true">' . $counter . '</option>'; 
  }else{
    echo '<option value=' . $counter . '>' . $counter . '</option>'; 
  }
}
?>
</select> 

<?

if ($league[0]['League']['cup'] == true){
	echo 'Stage: <select name="slStage" id="slStage">';
	
	foreach($allStages as $info){

	  if($info[0]['id'] == $selectedStage[0][0]['id']){ 
	    echo '<option value=' . $info[0]['number'] . ' selected="true">' . $info[0]['name'] . '</option>'; 
	  }else{
	    echo '<option value=' . $info[0]['number'] . '>' . $info[0]['name'] . '</option>'; 
	  } 
	}
	echo '</select>';
}

?>
</form>

<?php
if ($league[0]['League']['cup'] == false){
	//If it's a league we show league table and link to player / league stats
?>

<table id="table" class="tablesorter" style="width:100%;"  border="0" cellspacing="-1" cellpadding="-1"> 
<thead>
<tr> 
   <th title="Ranking" style="width: 5%; text-align:center; cursor:pointer;">Rnk</th> 
    <th title="Team" style="width: 31%; text-align:center; cursor:pointer;">Team</th> 
    <th title="Matches Played" style="width: 8%; text-align:center; cursor:pointer;">MP</th>
    <th title="Wins" style="width: 8%; text-align:center; cursor:pointer;">W</th> 
    <th title="Draws" style="width: 8%; text-align:center; cursor:pointer;">D</th> 
    <th title="Losses" style="width: 8%; text-align:center; cursor:pointer;">L</th> 
    <th title="Goals For" style="width: 8%; text-align:center; cursor:pointer;">GF</th> 
    <th title="Goals Against" style="width: 8%; text-align:center; cursor:pointer;">GA</th> 
    <th title="Goal Difference" style="width: 8%; text-align:center; cursor:pointer;">+/-</th> 
    <th title="Points" style="width: 8%; text-align:center; cursor:pointer;">Pts</th> 
</tr> 
</thead> 
<tbody> 
<?PHP
   
$i = 1;
               foreach($leagueTable as $info){
?>
                    <tr>
<?PHP
                    echo "<td>" . $i . "</td><td>". $this->Html->link($info[0]['clubname'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $info[0]['id'])) . "</td>";
					
					
                    echo '<td style="text-align:center;" class="played">' . $info[0]['played'] . '</td>';
                    echo '<td style="text-align:center;" class="wins">' . $info[0]['wins'] . '</td>';
                    echo '<td style="text-align:center;" class="draws">' . $info[0]['draws'] . '</td>';
                    echo '<td style="text-align:center;" class="losses">' . $info[0]['losses'] . '</td>';
                    echo '<td style="text-align:center;" class="for">' . $info[0]['gfor'] . '</td>';
                    echo '<td style="text-align:center;" class="against">' . $info[0]['against'] . '</td>';
                    echo '<td style="text-align:center;" class="difference">'  . $info[0]['difference'] . '</td>';
                    echo '<td style="text-align:center;" class="points">' . $info[0]['points'] . '</td></tr>';
$i++;

               }
?>
</tbody>
</table>
</div>
<div style="float: right;">
<?PHP
echo '<h3>' . $this->Html->link('Player Stats', array('plugin' => null, 'controller' => 'leagues', 'action' => 'leagueplayerstats', $league[0]['League']['id'])) . '</h3>';
echo '<h3>' . $this->Html->link('League Stats', array('plugin' => null, 'controller' => 'leagues', 'action' => 'leagueclubstats', $league[0]['League']['id'])) . '</h3>';

?>
</div>

<?php 
} //End if cup == false
else{
	
	if (isset($selectedStage[0][0])){
		
	
		//If it's a cup
		echo '<h2>' . $selectedStage[0][0]['name'] . '</h2>';
		
		if ($selectedStage[0][0]['knockout'] != 1){
			//If it's not knockout it's a group stage so show the group tables
	
			$g = '';
	
			foreach ($groups as $club){
	
				if ($club[0]['group_name'] != $g){
					$g = $club[0]['group_name'];
					echo '</tbody></table></div>';
					echo '<div class="divgrouptable" style="width: 40%; float: left; margin: 20px;"><h3>' . $g . '</h3><table>';
					echo '<thead><tr><th>Club</th><th>Matches</th><th>For</th><th>Against</th><th>Difference</th><th>Points</th></tr></thead>';
				}
				
				echo '<tr><td>' . $club[0]['clubname'] . '</td><td>' . $club[0]['matches'] . '</td><td>' . $club[0]['goalsfor'] . '</td><td>' . $club[0]['goalsagainst'] . '</td><td>' . $club[0]['goaldiff'] . '</td><td>' .$club[0]['points'] . '</td></tr>';  
				
			}
			echo '</tbody></table></div><div style="clear: both;"></div>';
		}
	}
	
}//End else (cup == true)

?>

<div class="tbl"   style="width: 48%; font-size: 90%; margin-right:3%; margin-top:15px; float:left">
<h2>Matches Played</h2>
<table id="results" class="tablesorter" style="width: 100%;"> 

<thead> 
<tr> 
    <th style="cursor:pointer; width: 32%; text-align:center;">Date and Venue</th>
    <th style="cursor:pointer; width: 24%; text-align:center;">Home</th> 
    <th style="cursor:pointer; width: 24%; text-align:center;">Away</th> 
	<th style="cursor:pointer; width: 10%;">Summary</th> 
    <th style="cursor:pointer; width: 10%;">Replay</th> 
</tr> 
</thead> 
<tbody> 
<?PHP

 
$index = 0;
               foreach($matchesPlayed as $info){
               $index++;
if ($index>8){ ?> <div><tr class="extra" style="display:none"><?PHP
}
else {?> <tr><?PHP
}
                    echo '<td style="text-align:center;">' . substr($info[0]['matchdate'], 0, 16) . '<br>' . $info[0]['stadiumname'] . '</td>';
                    echo '<td style="text-align:right;">' . $this->Html->link($info[0]['hometeamname'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $info[0]['hometeamid'])) .' &#160;&#160;&#160;' . $info[0]['hometeamgoals'];
                    if ($info[0]['shootoutgoalshome'] > 0 || $info[0]['shootoutgoalsaway'] > 0){
                    	echo '<br />Penalties: ' . $info[0]['shootoutgoalshome'];	
                    }
                    echo '</td><td style="text-align:left;">' . $info[0]['awayteamgoals'] . ' &#160;&#160;&#160;' . $this->Html->link($info[0]['awayteamname'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $info[0]['awayteamid']));
                    if ($info[0]['et'] == 1){
                    	echo ' (extra time)';
                    }
                    if ($info[0]['shootoutgoalshome'] > 0 || $info[0]['shootoutgoalsaway'] > 0){
                    	echo '<br />' . $info[0]['shootoutgoalsaway'];
                    }
					echo '</td><td><a href="/matchstats/summary/' . $info[0]['id'] . '">Summary</a></td>';
                    echo '<td><a href="/matches/replay/' . $info[0]['id'] . '">Replay</a></td></tr>';
                    if($index==8){
                          ?><tr class="more one"><td colspan="5"  style="text-align:center;">All Matches</td></tr>
<?PHP
                    }
               }
               if($index > 8){ echo '</div>';}
               echo '</tbody> </table></div>';
?>
<div class="tbl" style="width: 48%; font-size: 90%; margin-top:15px; float: right;" >
<h2>Fixtures to come</h2>
<table id="fixtures" class="tablesorter"  style="width: 100%; "> 
<thead> 
<tr> 
    <th style="cursor:pointer; width: 40%; text-align:center;">Date and Venue</th>
    <th style="cursor:pointer; width: 30%; text-align:right;">Home</th>
    <th style="cursor:pointer; width: 30%; text-align:left;">Away</th> 
</tr> 
</thead> 
<tbody> 
<?PHP


$index = 0;
               foreach($matchesToPlay as $info){
               $index++;
if ($index>8){ ?> <tr class="extra2" style="display:none"><?PHP
}
else {?> <tr><?PHP
}
                    echo '<td style="text-align:center;">' . substr($info[0]['matchdate'], 0, 16) . '<br>' . $info[0]['stadiumname'] . '</td>';
                    echo '<td style="text-align:right;">' . $this->Html->link($info[0]['hometeamname'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $info[0]['hometeamid'])) . '</td>';
                    echo '<td style="text-align:left;">' . $this->Html->link($info[0]['awayteamname'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $info[0]['awayteamid'])) .'</td></tr>';
                    if($index==8){
                          ?><tr class="more two"><td colspan="4"  style="text-align:center;">All Fixtures</td></tr>
<?PHP
                    }
               }
               echo '</tbody> </table></div>';

?>

</div>
<?PHP

echo $this->Js->writeBuffer();

?>