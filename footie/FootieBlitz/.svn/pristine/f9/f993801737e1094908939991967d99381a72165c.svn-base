<?php 
//debug($season);
//debug($this->data);
//debug($activeClub);

echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
echo $this->Html->css('http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/themes/smoothness/jquery-ui.css');
echo $this->Html->script('https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js');
echo $this->Html->css('/css/playerstats.css');
echo $this->Html->script('playerinfo'); 
		        	
if ($pid == null){

}
else{

?>
<div id="divcontent" style="margin: 0px auto 0px auto; width:90%;">
<div id="divclubinfo" style="float:left; width:35%; ">
<h1>Person info</h1>
<table>

	<tr>
		<td>Name</td><td><?php 
			echo h($this->data['Person']['firstname'] . ' ' . $this->data['Person']['lastname']); 
			echo ' ' . $this->Html->link('[career]',  '/person/career/' . $this->data['Person']['id']);
		?></td>
	</tr>
	
	<tr>
		<td>Age</td><td>
		<?php 
			$years = floor($this->data['Person']['age'] / 365);
			$days = $this->data['Person']['age'] - ($years * 365);
			echo $years . ' years and ' .  $days . ' days';  
			echo '<input type="hidden" id="age" value=' . $years . ' />';
		?>
		</td>
	</tr>
	<tr>
		<td>Height</td><td><?php echo $this->data['Person']['height']; ?></td>
	</tr>
	<? 	if($userownsplayer > 0){
          echo "<tr><td>Balance</td><td>" . htmlentities("� ") . number_format($this->data['Person']['money'], 0, ',', '.') . "</td></tr>";

          echo "<tr><td>Energy</td><td><div class='progress'><div id='nrg' class='pro'>" . $this->data['Person']['energy'] . "</div></div></td></tr>";
     	}
     	if (isset($activeClub['Club'])){
     		echo "<td>Club</td><td>" . $this->Html->link($activeClub['Club']['clubname'],  '/clubs/clubdetails/' . $activeClub['Club']['id']);
     		echo  ' ' . $this->Html->link('[View contract]',  '/contracts/contractdetails/' . $this->data['ActiveContract']['id']); 
     		if(isset($ownedClub['Club'])){ 
     			if(!isset($activeClub['Club'])){
     				echo  ' ' . $this->Html->link('[Make offer]',  '/contracts/newoffer/' . $this->data['Person']['id']); 
     			}
     			elseif($ownedClub['Club']['id'] != $activeClub['Club']['id']){
     				echo  ' ' . $this->Html->link('[Make offer]',  '/contracts/newoffer/' . $this->data['Person']['id']); 
     			}
     		}
     		echo "</td>";
     	}
     	else{
     		echo "<td>Club</td><td>Unemployed ";
     		if (isset($ownedClub)){ 
     			echo $this->Html->link('[Offer contract]',  '/contracts/newoffer/' . $this->data['Person']['id']); 
     		} 
     		echo "</td>";
     	}
		echo "</tr><tr><td>Agent</td><td>";
			// user.id 99 = NPC
     		if ($personUser['User']['id'] != 99){ 
     			echo $this->Html->link($personUser['User']['username'],  '/forum/forumusers/profile/' . $personUser['User']['id']); 
     		}
			else {echo 'This player is an NPC';}
     		echo "</td></tr>";

     	
     	//If the user is the agent for the player or he owns the club where the players is currently playing and the player doesn't have an agent he can set the training regime
     	if($userownsplayer > 0 || ($personUser['User']['id'] == 99 && $userownsactiveclub > 0)){
     		echo "</tr><tr><td>Training</td><td>";
     		echo '<form id="frmTrainingRegime" method="post"><select name="slTrainingRegime" id="slTrainingRegime" style="float:left;padding-right:5px;">';
     		if(!isset($this->data['Person']['training_regime_id'])){
     			echo '<option value=-1 selected="true">Not selected</option>';
     		}
     		foreach ($regimes as $result)
     		{
     			if($this->data['Person']['training_regime_id'] == $result['TrainingRegime']['id']){ 
				    echo '<option value=' . $result['TrainingRegime']['id'] . ' selected="true">' . $result['TrainingRegime']['name'] . '</option>'; 
				}else{
				    echo '<option value=' . $result['TrainingRegime']['id'] . '>' . $result['TrainingRegime']['name'] . '</option>'; 
				}
     		}
     		echo '</select><input type="submit" style="float:left;clear:none;padding-left:5px;" value="Save"></form></td></tr>';
     	}
	?>
</table>
</div>

<div id="divChart" style="float:right; width:35%;">

<?PHP
	$skspeed = ($this->data['Person']['topspeed'] + $this->data['Person']['acceleration']) / 2;
	$skstamina = $this->data['Person']['stamina'];
	$skstrength = $this->data['Person']['strength'];
	$skdefense = ($this->data['Person']['tackling'] + $this->data['Person']['marking']) / 2;
	$skpassing = ($this->data['Person']['passing'] + $this->data['Person']['vision']) / 2;
	$skdribbling = ($this->data['Person']['dribbling'] + $this->data['Person']['agility']) / 2;
	$skshooting = ($this->data['Person']['shooting'] + $this->data['Person']['shotpower']) / 2;
?>

<img src="http://chart.apis.google.com/chart?chxl=0:|Speed|Stamina|Strength|Defense|Passing|Dribbling|Shooting&chxr=0,26.667,100&chxt=x,y&chs=300x300&cht=rs&chco=FF0000&chd=t:<? echo $skspeed . ',' . $skstamina . ',' . $skstrength . ',' . $skdefense . ',' . $skpassing . ',' . $skdribbling . ',' . $skshooting . ',' . $skspeed; ?>&chls=2,4,0&chm=B,FF000080,0,0,0" width="300" height="300" alt="Skill chart" />
</div>

<div id="divplayerform" style="clear: both; float:left; width:40%; "> 
 <? if (count($form) > 0){ ?>
	<h1>Current form</h1>
	<table>
	
	<?PHP
		$saveattempts = 0;
		 foreach ($form as $result)
		 {
		 	$saveattempts = $saveattempts + $result['MatchPlayerstat']['saveattempts'];
		 }
		 
		 if ($saveattempts > 0){
		 	echo '<thead><tr><th>Date</th><th title="Tackles and interceptions">Saves</th><th>Attempts</th><th>Rating</th></tr></thead>';		 	
		 }
		 else{
		 	echo '<thead><tr><th>Date</th><th title="Tackles and interceptions">Tck/int</th><th>Goals</th><th>Assists</th><th>Rating</th></tr></thead>';
		 }

		 foreach ($form as $result)
		 {	
		 	if ($saveattempts > 0){
		 		echo $this->Html->tableCells(array(
					substr($result['Match']['matchdate'], 0, 10), 		
					$result['MatchPlayerstat']['saves'], 
					$result['MatchPlayerstat']['saveattempts'], 
					$result['MatchPlayerstat']['rating']
				));	
		 	}
		 	else{
			 	echo $this->Html->tableCells(array(
					substr($result['Match']['matchdate'], 0, 10), 
					$result['MatchPlayerstat']['successfultackles'] + $result['MatchPlayerstat']['interceptions'], 				
					$result['MatchPlayerstat']['goals'], 
					$result['MatchPlayerstat']['assists'], 
					$result['MatchPlayerstat']['rating']
				));
		 	}
		 }
		 if ($saveattempts > 0){
			 echo '<tr><td>Season (' . $season[0][0]['matches'] . ' matches)</td><td>' . $season[0][0]['saves'] . '</td><td>' . $season[0][0]['saveattempts'] . '</td><td>' . round($season[0][0]['rating'], 2) . '</td></tr>';
		 }
		 else{
		 	 echo '<tr><td>Season (' . $season[0][0]['matches'] . ' matches)</td><td>' . $season[0][0]['tackles'] . '</td><td>' . $season[0][0]['goals'] . '</td><td>' . $season[0][0]['assists'] . '</td><td>' . round($season[0][0]['rating'], 2) . '</td></tr>';
		 }
	?>
	</table>
	<? 
} 	
?>
</div>


<div id="divPlayerThoughts" style="float:right; width:40%; "> 

<h1>Player morale status</h1>
<table>
 <?PHP 
	echo "<tr><td>Morale </td><td><div class='progress'><div id='mor' class='pro'>" . $this->data['Person']['morale'] . "</div></div></td></tr>";

	echo $this->Html->tableHeaders(array('Date', 'Message'));

	foreach ($this->data['PersonThought'] as $result)
	{
		echo $this->Html->tableCells(array(substr($result['date'], 0, 10), $result['msg']));
	}

	?>
</table>
</div>

<div id="divoffers" style="width:100%; clear: both;"> 
<?php 
if($userownsplayer > 0){
	 foreach ($offers as $offer)
	 {
		echo 'You have a contract offer from ' . $this->Html->link($offer['Club']['clubname'],  '/clubs/clubdetails/' . $offer['Club']['id']) . '. ';
		echo $this->Html->link('[View offer]',  '/contracts/negotiate/' . $offer['ActiveContract']['id']) . '<br />';
	 }
}
?>
</div>

	


<div id="divplayerattr" style="width:100%; clear: both;"> 

<?php

if($userownsplayer > 0){ 

	echo $this->Form->create(false, array('type' => 'post', 'action' => 'persondetails/' . $pid));

	if($this->data['Person']['playerpoints'] < 0.1){ $roundatt = 0;}
	else {$roundatt = round($this->data['Person']['playerpoints'], 2);}
?>

<table>
	<tr>
		<td style="width:50%">Distribute attribute points (<span id="divattp"><?php echo $roundatt . ' ';?></span> remaining)</td>
		<td>PP used: (<span id="ppspend">0</span>)</td>
	</tr>
</table>
<?php } ?>
<div style="width:50%; float: left;">
<input type="hidden" id="playerpoints" name="playerpoints" value="<?php echo $this->data['Person']['playerpoints']; ?>"/>
<table>
	<tr title="Represents how fast your player accelerates" >
		<td style="cursor:help; width:30%;">Acceleration</td>
		<td style="width:40%;">
			<div class="progress">
				<div id="acc" name= "acc" class="pro"><?php echo ' ' . round($this->data['Person']['acceleration'], 2) . ' ';?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="accdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="accdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="accup" class="up" style="width:37%">
				<div class="fg-button ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="accupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="The top speed your player can reach">
		<td style="cursor:help;">Top Speed</td>
		<td>
			<div class="progress">
				<div id="tps" class="pro"><?php echo ' ' . round($this->data['Person']['topspeed'], 2) . ' '; ?></div>
			</div>
		</td>
<?php        if($userownsplayer > 0){ ?>
		<td>
			<div id="tpsdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="tpsdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="tpsup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="tpsupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="How tight your player turns. A higher stat means your player can turn tighter curves and loses less speed by turning">
		<td style="cursor:help;">Agility</td>
		<td>
			<div class="progress">
				<div id="agi"  class="pro"><?php echo ' ' . round($this->data['Person']['agility'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="agidownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="agidown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="agiup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="agiupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="Used for tackling and how difficult your player is to push off the ball">
		<td style="cursor:help;">Strength</td>
		<td>
			<div class="progress">
				<div id="str" class="pro"><?php echo ' ' . round($this->data['Person']['strength'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="strdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="strdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="strup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="strupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="How high your player can jump">
		<td style="cursor:help;">Jumping</td>
		<td>
			<div class="progress" >
				<div id="jum" class="pro"><?php echo ' ' . round($this->data['Person']['jumping'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?> 
		<td>
			<div id="jumdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="jumdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="jumup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="jumupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="How fast your player reacts. Used for all actions and decisions your player makes">
		<td style="cursor:help;">Reaction</td>
		<td>
			<div class="progress">
				<div id="rea" class="pro"><?php echo ' ' . round($this->data['Person']['reaction'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="readownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="readown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="reaup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="reaupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="A low score means your player gets tired earlier and decreases his stats for the rest of the game">
		<td style="cursor:help;">Stamina</td>
		<td>
			<div class="progress">
				<div id="sta" class="pro"><?php echo ' ' . round($this->data['Person']['stamina'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="stadownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="stadown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="staup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="staupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="A high score means your player loses less speed when dribbling, and better control of the ball">
		<td style="cursor:help;">Dribbling</td>
		<td>
			<div class="progress">
				<div id="drb" class="pro"><?php echo ' ' . round($this->data['Person']['dribbling'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="drbdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="drbdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="drbup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="drbupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="How well the player aims his shots. Also affects shot power minimally">
		<td style="cursor:help;">Shooting</td>
		<td>
			<div class="progress">
				<div id="sho"  class="pro"><?php echo ' ' . round($this->data['Person']['shooting'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="shodownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="shodown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="shoup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="shoupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="A high score means more power on your players shots">
		<td style="cursor:help;">Shot Power</td>
		<td>
			<div class="progress">
				<div id="spo" class="pro"><?php echo ' ' . round($this->data['Person']['shotpower'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="spodownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="spodown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="spoup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="spoupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>   
	</tr>
</table>
</div>
<div style="width:50%; float: right;">
<table>
	<tr title="How well your player aims his passes">
		<td style="cursor:help; width:30%;">Passing</td>
		<td style="width:40%;">
			<div class="progress">
				<div id="pas" class="pro"><?php echo ' ' . round($this->data['Person']['passing'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="pasdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="pasdown" class="down"  style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="pasup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="pasupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="A high score helps all skills that involve the ball">
		<td style="cursor:help;">Technique</td>
		<td>
			<div class="progress">
				<div id="tec" class="pro"><?php echo ' ' . round($this->data['Person']['technique'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="tecdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="tecdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="tecup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="tecupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="Decides how many, and which players your player can see on the pitch">
		<td style="cursor:help;">Vision</td>
		<td>
			<div class="progress">
				<div id="vis" class="pro"><?php echo ' ' . round($this->data['Person']['vision'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="visdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="visdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="visup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="visupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="A high score means you player is better at marking opponents">
		<td style="cursor:help;">Marking</td>
		<td>
			<div class="progress">
				<div id="mar" class="pro"><?php echo ' ' . round($this->data['Person']['marking'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="mardownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="mardown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="marup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="marupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="Affects the succes of tackles. A low score with a high aggression could result in many cautions">
		<td style="cursor:help;">Tackling</td>
		<td>
			<div class="progress">
				<div id="tck" class="pro"><?php echo ' ' . round($this->data['Person']['tackling'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="tckdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="tckdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="tckup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="tckupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="How good your player is at directing his headers, and how much power he can get on them">
		<td style="cursor:help;">Heading</td>
		<td>
			<div class="progress">
				<div id="hea" class="pro"><?php echo ' ' . round($this->data['Person']['heading'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="headownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="headown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="heaup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="heaupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="For goalkeepers. A high skill will give a boost to your defenders Marking and Vision attributes">
		<td style="cursor:help;">Command of Area (GK)</td>
		<td>
			<div class="progress">
				<div id="coa" class="pro"><?php echo ' ' . round($this->data['Person']['commandofarea'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="coadownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="coadown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="coaup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="coaupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="For goalkeepers. The skill of catching and holding the ball">
		<td style="cursor:help;">Handling (GK)</td>
		<td>
			<div class="progress">
				<div id="han" class="pro"><?php echo ' ' . round($this->data['Person']['handling'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="handownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="handown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="hanup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="hanupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="For goalkeepers. The skill of rushing out, pressuring attackers and shieldeing your goal">
		<td style="cursor:help;">Rushing Out (GK)</td>
		<td>
			<div class="progress">
				<div id="rou" class="pro"><?php echo ' ' . round($this->data['Person']['rushingout'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="roudownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="roudown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="rouup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="rouupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
	<tr title="For goalkeepers. The skill of blocking and guiding the ball to safety">
		<td style="cursor:help;">Shot Stopping (GK)</td>
		<td>
			<div class="progress">
				<div id="sts" class="pro"><?php echo ' ' . round($this->data['Person']['shotstopping'], 2) . ' '; ?></div>
			</div>
		</td>
<?php if($userownsplayer > 0){ ?>
		<td>
			<div id="stsdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="stsdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="stsup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="stsupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
<?php } ?>
	</tr>
</table>
</div>
<div style="clear:both;"><!-- --></div>


<input type="hidden" id="startacc" name="startacc" value= <?php echo $this->data['Person']['acceleration']; ?>/>
<input type="hidden" id="starttps" name="starttps" value= <?php echo $this->data['Person']['topspeed']; ?>/>
<input type="hidden" id="startagi" name="startagi" value= <?php echo $this->data['Person']['agility']; ?>/>
<input type="hidden" id="startstr" name="startstr" value= <?php echo $this->data['Person']['strength']; ?>/>
<input type="hidden" id="startjum" name="startjum" value= <?php echo $this->data['Person']['jumping'] ?>/>
<input type="hidden" id="startrea" name="startrea" value= <?php echo $this->data['Person']['reaction'] ?>/>
<input type="hidden" id="startsta" name="startsta" value= <?php echo $this->data['Person']['stamina'] ?>/>
<input type="hidden" id="startdrb" name="startdrb" value= <?php echo $this->data['Person']['dribbling'] ?>/>
<input type="hidden" id="startsho" name="startsho" value= <?php echo $this->data['Person']['shooting'] ?>/>
<input type="hidden" id="startspo" name="startspo" value= <?php echo $this->data['Person']['shotpower'] ?>/>
<input type="hidden" id="startpas" name="startpas" value= <?php echo $this->data['Person']['passing'] ?>/>
<input type="hidden" id="starttec" name="starttec" value= <?php echo $this->data['Person']['technique'] ?>/>
<input type="hidden" id="startvis" name="startvis" value= <?php echo $this->data['Person']['vision'] ?>/>
<input type="hidden" id="startmar" name="startmar" value= <?php echo $this->data['Person']['marking'] ?>/>
<input type="hidden" id="starttck" name="starttck" value= <?php echo $this->data['Person']['tackling'] ?>/>
<input type="hidden" id="starthea" name="starthea" value= <?php echo $this->data['Person']['heading'] ?>/>
<input type="hidden" id="startcoa" name="startcoa" value= <?php echo $this->data['Person']['commandofarea'] ?>/>
<input type="hidden" id="starthan" name="starthan" value= <?php echo $this->data['Person']['handling'] ?>/>
<input type="hidden" id="startrou" name="startrou" value= <?php echo $this->data['Person']['rushingout'] ?>/>
<input type="hidden" id="startsts" name="startsts" value= <?php echo  $this->data['Person']['shotstopping'] ?>/>

<input type="hidden" id="hacc" name="hacc" value="<?php echo $this->data['Person']['acceleration'] ?>"/>
<input type="hidden" id="htps" name="htps" value="<?php echo $this->data['Person']['topspeed'] ?>"/>
<input type="hidden" id="hagi" name="hagi" value="<?php echo $this->data['Person']['agility'] ?>"/>
<input type="hidden" id="hstr" name="hstr" value="<?php echo $this->data['Person']['strength'] ?>"/>
<input type="hidden" id="hjum" name="hjum" value="<?php echo $this->data['Person']['jumping'] ?>"/>
<input type="hidden" id="hrea" name="hrea" value="<?php echo $this->data['Person']['reaction'] ?>"/>
<input type="hidden" id="hsta" name="hsta" value="<?php echo $this->data['Person']['stamina'] ?>"/>
<input type="hidden" id="hdrb" name="hdrb" value="<?php echo $this->data['Person']['dribbling'] ?>"/>
<input type="hidden" id="hsho" name="hsho" value="<?php echo $this->data['Person']['shooting'] ?>"/>
<input type="hidden" id="hspo" name="hspo" value="<?php echo $this->data['Person']['shotpower'] ?>"/>
<input type="hidden" id="hpas" name="hpas" value="<?php echo $this->data['Person']['passing'] ?>"/>
<input type="hidden" id="htec" name="htec" value="<?php echo $this->data['Person']['technique'] ?>"/>
<input type="hidden" id="hvis" name="hvis" value="<?php echo $this->data['Person']['vision'] ?>"/>
<input type="hidden" id="hmar" name="hmar" value="<?php echo $this->data['Person']['marking'] ?>"/>
<input type="hidden" id="htck" name="htck" value="<?php echo $this->data['Person']['tackling'] ?>"/>
<input type="hidden" id="hhea" name="hhea" value="<?php echo $this->data['Person']['heading'] ?>"/>
<input type="hidden" id="hcoa" name="hcoa" value="<?php echo $this->data['Person']['commandofarea'] ?>"/>
<input type="hidden" id="hhan" name="hhan" value="<?php echo $this->data['Person']['handling'] ?>"/>
<input type="hidden" id="hrou" name="hrou" value="<?php echo $this->data['Person']['rushingout'] ?>"/>
<input type="hidden" id="hsts" name="hsts" value="<?php echo $this->data['Person']['shotstopping'] ?>"/>

<?php if($userownsplayer > 0){ 
	echo $this->Form->end('Save training'); 
?>

</div>

<?php
}

	if($userManagesPlayer > 0 || $userownsplayer > 0){ ?>
	
	 	<div id="divplayertactic" style="width:45%; float: left;"> 

		<?
		echo $this->Form->create(false, array('type' => 'post', 'action' => 'persondetails/' . $pid));
	
		echo '<input type="hidden" id="hddrib"  name="hddrib" value="' . $this->data['Playertactic']["dribble"] . '" />';
		echo '<input type="hidden" id="hdthro"  name="hdthro" value="' . $this->data['Playertactic']["throughballs"] . '" />';
		echo '<input type="hidden" id="hdruns"  name="hdruns" value="' . $this->data['Playertactic']["runs"] . '" />';
		echo '<input type="hidden" id="hdlong"  name="hdlong" value="' . $this->data['Playertactic']["longshots"] . '" />';
		echo '<input type="hidden" id="hdaggr"  name="hdaggr" value="' . $this->data['Playertactic']["aggression"] . '" />';
		echo '<input type="hidden" id="hdment"  name="hdment" value="' . $this->data['Playertactic']["mentality"] . '" />';
		echo '<input type="hidden" id="hdclos"  name="hdclos" value="' . $this->data['Playertactic']["closingdown"] . '" />';
		echo '<input type="hidden" id="hdcros"  name="hdcros" value="' . $this->data['Playertactic']["crossball"] . '" />';
		echo '<input type="hidden" id="hdmark"  name="hdmark" value="' . $this->data['Playertactic']["tightmarking"] . '" />';
		echo '<input type="hidden" id="hdpass"  name="hdpass" value="' . $this->data['Playertactic']["passing"] . '" />';
		
		$chb = '';
		if ($this->data['Playertactic']["forwardonsetpieces"] == 1){
		 $chb = 'checked="yes"';
		}
	
	
?>
		<h1>Playing style</h1>
		<table>
		<tr title="Chooses the dribble less frequently  <--> Chooses the dribble more frequently"><td style="width: 50%; cursor:help">Dribbling</td><td><div id="sldrib" style='width:200px;'></div></td></tr>
		<tr title="Hits through balls less frequently  <--> Hits through balls more frequently"><td>Through balls</td><td><div id="slthro" style='width:200px;'></div></td></tr>
		<tr title="Makes forward runs less frequently  <--> Makes forward runs more frequently"><td>Forward runs</td><td><div id="slruns" style='width:200px;'></div></td></tr>
		<tr title="Shoots at distance less frequently  <--> Shoots at distance more frequently"><td>Long shots</td><td><div id="sllong" style='width:200px;'></div></td></tr>
		<tr title="Tackles with less aggression (less chance of recieving cautions and winning tackles)  <--> Tackles with more aggression (larger chance of recieving cautions and winning tackles)"><td>Aggression</td><td><div id="slaggr" style='width:200px;'></div></td></tr>
		<tr title="Takes up positions further back on the field than the manager plans  <--> Takes up positions further up the field than the manager plans"><td>Mentality</td><td><div id="slment" style='width:200px;'></div></td></tr>
		<tr title="When marking, waits before closing down his opponent  <--> When marking, closes  down his opponent earlier"><td>Closing down</td><td><div id="slclos" style='width:200px;'></div></td></tr>
		<tr title="Chooses the cross less frequently  <--> Chooses the cross more frequently"><td>Crossing</td><td><div id="slcros" style='width:200px;'></div></td></tr>
		<tr title="Chooses the short pass more frequently  <--> Chooses the long pass more frequently"><td>Passing</td><td><div id="slpass" style='width:200px;'></div></td></tr>
		<tr title="Marks opponents less frequently  <--> Marks opponents more frequently"><td>Marking</td><td><div id="slmark" style='width:200px;'></div></td></tr>
		<?PHP
		echo '<tr title="Joins the attack on his teams corner kicks"><td>Forward on set pieces</td><td><input type="checkbox" id="chbxsetp" name="chbxsetp" ' . $chb . '></td></tr>';
		?>
		</table>
		<? echo $this->Form->end('Save playing style'); ?>
		</div>
<?
}

//NedenstÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¥ende skal med allersidst nÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¥r der er script pÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¥ siden. Se http://book.cakephp.org/view/1594/Using-a-specific-Javascript-engine



?>

</div>

<?PHP 
} 
echo $this->Js->writeBuffer(); 
?>
