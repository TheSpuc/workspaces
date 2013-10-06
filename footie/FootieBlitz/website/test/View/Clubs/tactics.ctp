<?PHP
//debug($lineup);

echo $this->Html->css('clubtactics');

echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
echo $this->Html->css('http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/themes/smoothness/jquery-ui.css');
echo $this->Html->script('https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js');
echo $this->Html->script('tablesorter');

echo $this->Html->script('clubtactics');


if ($userismanager == 1 || $uid == 1){
    
   	//Hent alle navne og id på spillere for klubben
   	$allNames = array();
   	$allIds = array();
    foreach ($squad as $player)
	{
		array_push($allIds, $player['Person']['id']);
		array_push($allNames, (h($player['Person']['firstname'] . " " . $player['Person']['lastname'])));
	}

?>

<form id='form1' method="POST">
<table><tr><td width=1%>

<div id="formationdiv" style="position:relative;">

<img border="1" src="/img/pitch.png" class="pitch" id="pitch" width="594 px" height="372" />

<?php
   echo "<input type='hidden' value='" . $this->data['Club']['id'] . "' name='clId' />";

   $i = 0;
   $dx = 30;
   $fx = 1;
   $dy = 0;
   $fy = 0.7;
   
   
   	while ($i < 11){
	      $i = $i + 1;
	      $pos = explode(",", $lineup['Lineup']["pos" . $i]);
	      $x = $fx * $pos[0] + $dx;
	      $y = $fy * $pos[1] + $dy;
	
		$info2 = $lineup['pl' . $i];
	      if(isset($info2['id'])){
	         if ($i > 1){
	             echo "<div style='left:" . $x . "px; top:"  . $y . "px; ' class='draggable' id='pos" . $i . "' name='pos" . $i ."'>" . $info2["shirtnumber"] . ". " . h($info2["firstname"] . " " . $info2["lastname"]) . "</div>";
	         }else{
	            echo "<div style='left:" . $x . "px; top:"  . $y . "px; ' class='nondraggable' id='pos" . $i . "' name='pos" . $i ."'>" . $info2["shirtnumber"] . ". " . h($info2["firstname"] . " " . $info2["lastname"]) . "</div>";
	         }
	         echo "<input type='hidden' value='" . $pos[0] . "," . $pos[1] . "' name='p" . $i . "' id='p" . $i . "' />";
	         echo "<input type='hidden' value='" . $lineup['Lineup']["pl" . $i . "id"] . "' name='pid" . $i . "' id='pid" . $i . "' />";	
	      }else{
	         if ($i > 1){
	             echo "<div style='left:" . $x . "px; top:"  . $y . "px; ' class='draggable' id='pos" . $i . "' name='pos" . $i ."'>Not Selected</div>";
	         }else{
	            echo "<div style='left:" . $x . "px; top:"  . $y . "px; ' class='nondraggable' id='pos" . $i . "' name='pos" . $i ."'>Not Selected</div>";
	         }
	         echo "<input type='hidden' value='" . $pos[0] . "," . $pos[1] . "' name='p" . $i . "' id='p" . $i . "' />";
	         echo "<input type='hidden' value='-1' name='pid" . $i . "' id='pid" . $i . "' />";
	      }
	}

?>
</div></td><td>
 

<div id="lineupdiv">

<?php

	$lineupIDs = array();
	$i = 1;
	$color = "green";
        echo '<span id="lineupnames" class="lineupnames" style="position:relative;left:10px;top:10px;">';

	while ($i < 19){
	
		if ($i > 11){
          	$color = "orange";
      	}

     	$info=$lineup['pl' . $i];
      	if(isset($info['id'])){ 
      		array_push($lineupIDs, $info['id']);
			
			echo "<input type='hidden' name='hllpl" . $i . "' id='hllpl" . $i . "' value='" . $info['id'] . "' />";	
			echo '<span class="lname" style="color:' . $color . ';" id="lpl' . $i . '" >' . $info['shirtnumber'] . '. ' . h($info["firstname"] . ' ' . $info["lastname"]) . '</span>';
      	
      	}else{
                   //Fjern spiller fra opstillingen fordi han ikke er på kontrakt i klubben
                  ///////////mangler i denne fil

	        array_push($lineupIDs, -1);
				
			echo "<input type='hidden' name='hllpl" . $i . "' id='hllpl" . $i . "' value='-1' />";	
			echo '<span style="color:' . $color . ';" id="lpl' . $i . '" >Not Selected</span>';              
      	}
        if ($i == 1){
         	echo '(GK)';
        }

        echo '<br />';
		$i = $i+1;
	}

          
    foreach ($squad as $player)
	{
		if (!in_array($player['Person']['id'], $lineupIDs)){
			echo "<input type='hidden' name='hllpl" . $i . "' id='hllpl" . $i . "' value='" . $player['Person']['id'] . "' />";	
			echo "<span style='color:red;' id='lpl" . $i . "'>" . $player['Person']['shirtnumber'] . ". " . h($player['Person']['firstname'] . " " . $player['Person']['lastname']) . "</span><br />" ;
		}
		$i = $i+1;
    }
                 
          
  	echo '<div><br /> </div></span></div></td></tr></table>';

//Set default roles
  $fktaker = -1;
  $captain = -1;
  $throwleft = -1;
  $throwright = -1;
  $penalty= -1;
  $fklong= -1;
  $cornerright= -1;
  $cornerleft= -1;
  $targetman= -1;

//Hvis de findes i db så overskriv dem
if (isset($teamtactics['Teamtactics']['freekickshort'])){
   $fktaker = $teamtactics['Teamtactics']['freekickshort'];
   $captain= $teamtactics['Teamtactics']['captain'];
   $throwleft= $teamtactics['Teamtactics']['throwinleft'];
   $throwright= $teamtactics['Teamtactics']['throwinright'];
   $penalty= $teamtactics['Teamtactics']['penaltytaker'];
   $fklong= $teamtactics['Teamtactics']['freekicklong'];
   $cornerright= $teamtactics['Teamtactics']['cornerright'];
   $cornerleft= $teamtactics['Teamtactics']['cornerleft'];
   $targetman= $teamtactics['Teamtactics']['targetman'];
}

?>

<table>
<tr><td>
Captain: </td><td><select id="captain" name="captain">
<?php
$i = 0;
if(in_array($captain,$allIds) == false) {
  echo "<option value='-1' selected>Not selected</option>";
}
foreach ($allNames as $value) {

  if ($allIds[$i] == $captain){
  echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
  }
  else{
  echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
  }
  $i = $i + 1;
  }
?>
</select></td></tr>
<tr><td>
Free kicks (short): </td><td><select id="freekicktaker" name="freekicktaker">
<?php
$i = 0;
if(in_array($fktaker,$allIds) == false) {
  echo "<option value='-1' selected>Not selected</option>";
}
foreach ($allNames as $value) {
  if ($allIds[$i] == $fktaker){
  echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
  }
  else{
  echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
  }
  $i = $i + 1;
  }
?>
</select></td></tr>
<tr><td>
Free kicks (long): </td><td><select id="freekicktakerlong" name="freekicktakerlong">
<?php
$i = 0;
if(in_array($fklong,$allIds) == false) {
  echo "<option value='-1' selected>Not selected</option>";
}
foreach ($allNames as $value) {
  if ($allIds[$i] == $fklong){
  echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
  }
  else{
  echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
  }
  $i = $i + 1;
  }
?>
</select></td></tr>
<tr><td>
Penalty: </td><td><select id="penalty" name="penalty">
<?php
$i = 0;
if(in_array($penalty,$allIds) == false) {
  echo "<option value='-1' selected>Not selected</option>";
}
foreach ($allNames as $value) {
  if ($allIds[$i] == $penalty){
  echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
  }
  else{
  echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
  }
  $i = $i + 1;
  }
?>
</select></td></tr>
<tr><td>
Corner right: </td><td><select id="cornerright" name="cornerright">
<?php
$i = 0;
if(in_array($cornerright,$allIds) == false) {
  echo "<option value='-1' selected>Not selected</option>";
}
foreach ($allNames as $value) {
  if ($allIds[$i] == $cornerright){
  echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
  }
  else{
  echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
  }
  $i = $i + 1;
  }
?>
</select></td></tr>
<tr><td>
Corner left: </td><td><select id="cornerleft" name="cornerleft">
<?php
$i = 0;
if(in_array($cornerleft,$allIds) == false) {
  echo "<option value='-1' selected>Not selected</option>";
}
foreach ($allNames as $value) {
  if ($allIds[$i] == $cornerleft){
  echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
  }
  else{
  echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
  }
  $i = $i + 1;
  }
?>
</select></td></tr>
<tr><td>
Throw in right: </td><td><select id="throwright" name="throwright">
<?php
$i = 0;
if(in_array($throwright,$allIds) == false) {
  echo "<option value='-1' selected>Not selected</option>";
}
foreach ($allNames as $value) {
  if ($allIds[$i] == $throwright){
  echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
  }
  else{
  echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
  }
  $i = $i + 1;
  }
?>
</select></td></tr>
<tr><td>
Throw in left: </td><td><select id="throwleft" name="throwleft">
<?php
$i = 0;
if(in_array($throwleft,$allIds) == false) {
  echo "<option value='-1' selected>Not selected</option>";
}
foreach ($allNames as $value) {
  if ($allIds[$i] == $throwleft){
  echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
  }
  else{
  echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
  }
  $i = $i + 1;
  }
?>
</select></td></tr>
<tr><td>
Target man: </td><td><select id="targetman" name="targetman">
<?php
$i = 0;

if ($targetman == -1){
     echo "<option value=-1 selected>No target man</option>";
 }
 else{
      echo "<option value=-1>No target man</option>";
 }

foreach ($allNames as $value) {
  if ($allIds[$i] == $targetman){
      echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
  }
  else{
      echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
  }
  $i = $i + 1;
  }
?>
</select></td></tr>

</table>
<input value="Save" type="submit" name="submit"/>
<?PHP


	echo '<table class="tablesorter" id="tblplayers"><thead><tr>';
    echo '<th style="cursor:pointer; width: 25%;">Player</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Acceleration">Ac</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Top speed">TS</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Agility">Ag</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Strength">St</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Jumping">Jm</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Reaction">Re</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Stamina">St</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Dribbling">Dr</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Shooting">Sh</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Shot power">SP</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Passing">Ps</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Technique">Tc</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Vision">Vs</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Marking">Ma</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Tackling">Tk</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Heading">He</th>';  
    echo '<th style="cursor:pointer; width: 3%;" title="Command of Area">CA</th>';  
    echo '<th style="cursor:pointer; width: 3%;" title="Handling">Ha</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Rushing Out">RU</th>';
    echo '<th style="cursor:pointer; width: 3%;" title="Shot Stopping">SS</th>';
    echo '<th style="cursor:pointer; width: 15%;" title="Energy">En</th>';        
 	echo '</tr></thead><tbody>'; 

   	foreach ($squad as $player)
	{
?>
<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
<?PHP
      	        echo "<td>" . $player['Person']['shirtnumber'] . "." . $this->Html->link(h($player['Person']['firstname'] . " " . $player['Person']['lastname']), '/person/persondetails/' . $player['Person']['id']);
                if ($player['Person']['npc'] == 1){
                     echo "*";
                }
                echo "</a></td>";   		
		echo '<td>' . round($player['Person']['acceleration']) . '</td>';
		echo '<td>' . round($player['Person']['topspeed']) . '</td>';
		echo '<td>' . round($player['Person']['agility']) . '</td>';
		echo '<td>' . round($player['Person']['strength']) . '</td>';
		echo '<td>' . round($player['Person']['jumping']) . '</td>';
		echo '<td>' . round($player['Person']['reaction']) . '</td>';
		echo '<td>' . round($player['Person']['stamina']) . '</td>';
		echo '<td>' . round($player['Person']['dribbling']) . '</td>';
		echo '<td>' . round($player['Person']['shooting']) . '</td>';
		echo '<td>' . round($player['Person']['shotpower']) . '</td>';
		echo '<td>' . round($player['Person']['passing']) . '</td>';
		echo '<td>' . round($player['Person']['technique']) . '</td>';
		echo '<td>' . round($player['Person']['vision']) . '</td>';
		echo '<td>' . round($player['Person']['marking']) . '</td>';
		echo '<td>' . round($player['Person']['tackling']) . '</td>';
		echo '<td>' . round($player['Person']['heading']) . '</td>';
		echo '<td>' . round($player['Person']['commandofarea']) . '</td>';
      	echo '<td>' . round($player['Person']['handling']) . '</td>';
		echo '<td>' . round($player['Person']['rushingout']) . '</td>';
		echo '<td>' . round($player['Person']['shotstopping']) . '</td>';
		echo '<td width="70px" style="font-size:63%; cursor:pointer;" title="' . $player['Person']['energy'] . '"><div class="pro">' . $player['Person']['energy'] . '</div></td>';
      	echo '</tr>';   
   	}
	echo '</tbody></table></form>';
}
else{
	echo 'You\'re not the registered owner of this club and cannot view or set tactics.';
}
?>