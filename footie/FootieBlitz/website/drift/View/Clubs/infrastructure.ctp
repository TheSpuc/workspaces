<?php

echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
echo $this->Html->script('infrastructure');

?>

<h1>Infrastructure</h1>
<?PHP 
echo'<h2>' . $this->data['Club']['clubname'] . '</h2>';

?>

<div id="divStadium" style="float: left; width: 45%;">
<h3>Stadium</h3>
<form id="frmTickets" method="post">
<?PHP
echo $this->data['Stadium']['stadiumname'];
echo '<table><thead><tr><th>Type</th><th>Capacity</th><th>Ticket price</th></tr></thead>';
echo '<tr><td>Seats</td><td>' . $this->data['Stadium']['seats'] . '</td><td>';
if ($userownsclub == 1){
	echo '<select name="slSeats" id="slSeats">';
	
	for ($counter = 1; $counter <= 200; $counter += 1) {
	  if($this->data['Club']['seatprice'] == $counter){ 
	    echo '<option value=' . $counter . ' selected="true">' . $counter . '</option>'; 
	  }else{
	    echo '<option value=' . $counter . '>' . $counter . '</option>'; 
	  }
	}
	
	echo '</select></td></tr>'; 
}
else{
	echo $this->data['Club']['seatprice'] . '</td></tr>';
}
echo '<tr><td>Terraces</td><td>' . $this->data['Stadium']['terraces'] . '</td><td>';
if ($userownsclub == 1){
	echo '<select name="slStands" id="slStands">';
	
	for ($counter = 1; $counter <= 200; $counter += 1) {
	  if($this->data['Club']['standprice'] == $counter){ 
	    echo '<option value=' . $counter . ' selected="true">' . $counter . '</option>'; 
	  }else{
	    echo '<option value=' . $counter . '>' . $counter . '</option>'; 
	  }
	}
	
	echo '</select></td></tr>'; 
}
else{
	echo $this->data['Club']['standprice'] . '</td></tr>';
}
echo '</table>';

if ($userownsclub == 1){ echo $this->Form->submit('Save ticket prices'); } 
echo '</form>';

if ($userownsclub == 1){ 

	echo '<form id="frmStadium" method="post">';
	if (isset($this->data['Construction'])){
	
		echo '<div id="currCons" style="padding: 10px; font-weight:bold;">';
		foreach ($this->data['Construction'] as $cons)
		{
			if ($cons['type'] == 2){
				$currentSeatCons = 0;
				$currentStandCons = 0;
				
				if (isset($cons['terraces'])){
					$currentStandCons = $cons['terraces'];
				}
				if (isset($cons['terraces'])){
					$currentSeatCons = $cons['seats'];
				}
				
				echo 'A stadium expansion of ' . $currentStandCons . ' terraces and ' . $currentSeatCons . ' seats is in progress. ';
				echo 'Estimated completion date: ' . substr($cons['finished'], 0, 10)  . '<br />';
			}
		}
		echo '</div>';
		
	} 
	
	?>
	<h3>Stadium expansion</h3>
	<select name="slExp" id="slExp">
	
		 <option value="t500">500 terraces (£200.000 / 15 days)</option>
		 <option value="t1000">1000 terraces (£350.000 / 25 days)</option> 
		 <option value="t2000">2000 terraces (£600.000 / 45 days)</option> 
		 <option value="s500">500 seats (£350.000 / 25 days)</option>
		 <option value="s1000">1000 seats (£600.000 / 40 days)</option> 
		 <option value="s2000">2000 seats (£1.000.000 / 55 days)</option> 

	</select>
	<?PHP
	echo $this->Form->submit('Start stadium expansion'); 
	echo '</form>';
}
?>

</div>


<div id="divStats" style="float: right; width: 45%;">
<h3>Match attendance statistics</h3>
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
</form>
<table>
<?PHP
 //echo $this->Html->tableHeaders(array('Competition', 'Lowest', 'Highest', 'Average'));
 echo '<thead><tr><th></th><th colspan=3>Terraces</th><th colspan=3>Seats</th></tr>';
 echo '<tr><th>Competition</th><th>Lowest</th><th>Highest</th><th>Average</th><th>Lowest</th><th>Highest</th><th>Average</th></tr></thead>';
 
 foreach ($avgatt as $info)
 {
	echo $this->Html->tableCells(array($info['League']['leaguename'], $info[0]['min'], $info[0]['max'], $info[0]['average'], $info[0]['minseats'], $info[0]['maxseats'], $info[0]['avgseats']));
 }
?>
</table>
</div>

<div id="divTrainingfacc"  style="clear: both;">
<h3>Training facilities</h3>
<div id="currCons" style="padding: 10px; font-weight:bold;">Level: 
<?PHP 
echo $this->data['Club']['trainingfacc'] . '<br /><br />'; 


if ($currentlyUpgrading == 1){
	
	foreach ($this->data['Construction'] as $cons)
	{
		if ($cons['type'] == 1){
			echo 'Training facilities are currently being upgraded with an estimated completion date of: ' . substr($cons['finished'], 0, 10)  . '<br />';
		}
	}
	
} 

if ($currentlyUpgrading == 0 && $userownsclub == 1){

	echo 'Training facilities upgrade will cost ' . number_format($faccCost, 0, "", ".") . ' and take approximately ' . $faccTime . ' days.';
	echo '<form method="POST"><input type="submit" name="sbmUpgrade" value="Start upgrade"></form>';   	
}

?>
</div>
</div>


<?PHP

echo $this->Js->writeBuffer();

?>