<?php

echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
echo $this->Html->script('tablesorter');
echo $this->Html->script('clubdetails');

//debug($this->data);
//debug($_SESSION);
//debug($lineup);
//debug($showplayers);
?>

<div id="divcontent" style="margin: 0px auto 0px auto;">
<div style="width:100%; float: left;">
<div id="divclubinfo" style="float:left; width:48%; ">
<h1>Club info</h1>
<table>

	<tr>
		<td>Name</td><td><?php 
		echo $club['Club']['clubname'] /* . ' (' . $club['Club']['shortname'] . ')' */; 
		echo ' ' . $this->Html->link('[history]',  '/clubs/history/' . $club['Club']['id']);
		?></td>
	</tr>
	<tr>
		<td>League</td><td><?php echo $this->Html->link($club['League']['leaguename'], array('controller' => 'leagues', 'action' => 'leaguedetails', $club['League']['id']));; ?></td>
	</tr>
	<tr>
		<td>Owner</td><td><?php echo $this->Html->link(h($club['User']['username']), array('plugin' => 'forum', 'controller' => 'forumusers', 'action' => 'profile', $club['User']['id'])); ?>
		<? if ($userownsclub == 1 || $uid == 1){
			echo ' ' . $this->Html->link('[tactics]',  '/clubs/tactics/' . $club['Club']['id']);
		 } ?>
		</td>
	</tr>
	
	<? if ($userownsclub == 1){ ?>
	<tr>
		<td>Balance</td><td><?php 
		echo "£ " . number_format($club['Club']['money'], 0, ',', '.'); 
		echo ' ' . $this->Html->link('[finances]',  '/clubs/finances/' . $club['Club']['id']);
		?></td>
	</tr>
	<? } ?>	
	<tr>
		<td>Stadium</td>
		<td><?php echo $club['Stadium']['stadiumname'] . '<br/>' ?>
			Seats: <?php echo $club['Stadium']['seats'] . '<br/>'; ?>
			Terraces: <?php echo $club['Stadium']['terraces']; ?>
		</td>
	</tr>
	<tr>
		<td>Training Facilities</td>
		<td>Level: <?php echo $club['Club']['trainingfacc']; ?></td>
	</tr>
	<? if ($userownsclub == 1){ ?>
	<tr>
		<td>Infrastructure</td>
		<td>
		<?php echo ' ' . $this->Html->link('[infrastructure]',  '/clubs/infrastructure/' . $club['Club']['id']);?>
		</td>
	</tr>
	<?php } ?>
</table>
</div>

<?php 

//////SQUAD/////////
echo '<div id="divsquad" style="float:right; width:48%; ">'; 
echo '<h1>Squad</h1>';
echo $this->Form->create(false, array('type' => 'post'));//, 'action' => 'clubdetails/' . $this->data['Club']['id']
echo '<table class="tablesorter" id="tblSquad">'; 
	echo '<thead>';
	if ($userownsclub == 1){
		echo $this->Html->tableHeaders(array('Shirt no', 'Name', 'Contract ends', 'Wage concerns'), array(), array('style' => 'cursor:pointer;'));
	}
	else{
		echo $this->Html->tableHeaders(array('Shirt no', 'Name', 'Contract ends'), array(), array('style' => 'cursor:pointer;'));
	}
		
	echo '</thead>';
 
	echo '<tbody>';
		foreach ($showplayers as $player){
			if ($userownsclub == 1){
				$personID = 'select' . $player['Person']['id'];
                echo '<input type="hidden" id="' . $personID . '" name="players[' . $player['Person']['id'] . ']" value="' . $player['Person']['shirtnumber'] . '">';
				echo '<tr><td id="' . $player['Person']['id'] . '"><select id="' . $personID . '" class="selectbokse">';
					for ($i=1; $i<=100; $i++){
						echo '<option value=' . $i . ' '; 
							if($i == $player['Person']['shirtnumber']){
								echo ' selected="selected"';				
							}
						echo '>' . $i . '</option>';
					}
				echo '</select>';
		
			}else{
				echo '<tr><td>' . $player['Person']['shirtnumber'] . '</td>'; 
			}
			echo '<td>' . $this->Html->link(h($player['Person']['firstname'] . ' ' . $player['Person']['lastname']),  '/person/persondetails/' . $player['Person']['id']) . '</td>';
			echo '<td>' . $this->Html->link($player['ActiveContract']['enddate'],  '/contracts/contractdetails/' . $player['ActiveContract']['id']) . '</td>';
			if ($userownsclub == 1){
				if (isset($player['PersonThought'][0])){
					echo '<td style="color: red;">Yes</td></tr>';
				}
				else{
					echo '<td>No</td></tr>';
				}
			} else{
				echo '</tr>';
			}
		}
	echo '</tbody>';
echo '</table>';
echo $this->Form->end('Save Shirt Numbers');
echo '</div>'; 

 ////////////OFFERS RECIEVED///////////////
echo '<div id="divoffers" style="float:left; ">'; 
if ($userownsclub == 1){
	 foreach ($offersReceived as $offer)
	 {
	 	if($offer[0]['acceptedbyclub'] == 1){
	 		if ($offer[0]['club_id'] == $clubid){
            	echo 'You have offered a contract extension to ' . h($offer[0]['firstname'] . ' ' . $offer[0]['lastname']) . ' ';
            	echo $this->Html->link('[withdraw offer]', array('controller' => 'contracts', 'action' => 'withdrawoffer', $offer[0]['contract_id']));
            }
            else{
            	echo $offer[0]['clubname'] . ' have made an offer of ' . $offer[0]['transferfee'] . ' for ' . h($offer[0]['firstname'] . ' ' . $offer[0]['lastname']);
            	echo ' which has been accepted.';
            }
	 	} 
	 	else{
	 		if($offer[0]['offered'] == 1){      
                echo $offer[0]['clubname'] . ' have made an offer of ' . $offer[0]['transferfee'] . ' for ' . h($offer[0]['firstname'] . ' ' . $offer[0]['lastname']) . '.';
                echo $this->Html->link('[accept]', array('controller' => 'contracts', 'action' => 'acceptoffer', $offer[0]['contract_id']));
                echo $this->Html->link('[negotiate]', array('controller' => 'contracts', 'action' => 'negotiate', $offer[0]['contract_id']));
                echo $this->Html->link('[reject]', array('controller' => 'contracts', 'action' => 'rejectoffer', $offer[0]['contract_id']));
            }            
            else{
            	echo $offer[0]['clubname'] . ' have made an offer for ' . h($offer[0]['firstname'] . ' ' . $offer[0]['lastname']) . '. They are currently considering your counter offer.';
          	}
	 	}
	 	echo '<br />';
	 }
	 
	 foreach ($offersSent as $offer)
	 {
	 	if($offer[0]['acceptedbyclub'] == 1){
	 	
	 		if($offer[0]['offered'] == 1){
	 			echo 'You have offered a contract to ' . $this->Html->link(h($offer[0]['firstname'] . ' ' . $offer[0]['lastname']), array('controller' => 'person', 'action' => 'persondetails', $offer[0]['person_id'])) . '. '; 
	 			echo $this->Html->link('[withdraw offer]', array('controller' => 'contracts', 'action' => 'withdrawoffer', $offer[0]['contract_id']));
	 		}
	 		else{
	 			echo $this->Html->link(h($offer[0]['firstname'] . ' ' . $offer[0]['lastname']), array('controller' => 'person', 'action' => 'persondetails', $offer[0]['person_id']));
	 			echo ' wants to negotiate your contract offer. ';
	 			echo $this->Html->link('[withdraw offer]', array('controller' => 'contracts', 'action' => 'withdrawoffer', $offer[0]['contract_id']));
	 			echo $this->Html->link('[negotiate]', array('controller' => 'contracts', 'action' => 'negotiate', $offer[0]['contract_id']));
	 		}
	 	}
	 	else{
	 		if($offer[0]['offered'] == 1){
	 			echo 'You have an offer of ' . $offer[0]['transferfee'] . ' pending for ';
	 			echo $this->Html->link(h($offer[0]['firstname'] . ' ' . $offer[0]['lastname']), array('controller' => 'person', 'action' => 'persondetails', $offer[0]['person_id'])) . '. ';
	 			echo $this->Html->link('[withdraw offer]', array('controller' => 'contracts', 'action' => 'withdrawoffer', $offer[0]['contract_id']));
	 		}
	 		else{
	 			echo 'A counter offer of ' . $offer[0]['transferfee'] . ' for ';
	 			echo $this->Html->link(h($offer[0]['firstname'] . ' ' . $offer[0]['lastname']), array('controller' => 'person', 'action' => 'persondetails', $offer[0]['person_id']));
	 			echo ' has been received. ';
	 			echo $this->Html->link('[withdraw offer]', array('controller' => 'contracts', 'action' => 'withdrawoffer', $offer[0]['contract_id']));
	 			echo $this->Html->link('[negotiate]', array('controller' => 'contracts', 'action' => 'negotiate', $offer[0]['contract_id']));
	 		}
	 	}	
	 	echo '<br />';
	 }
}
?>
<div style="clear:both; "></div>
</div>
</div>
<div style="width:100%;">
<div id="divmatch" style="float:left; width:48%; "> 
<h1>Fixtures played</h1>
<table>
<?php 
 echo $this->Html->tableHeaders(array('Date & Venue', 'Home', 'Away', 'Competition', 'Summary', 'Replay'));

 foreach ($playedfixtures as $match)
 {
	echo '<tr><td style="text-align:center;">' . substr($match['Match']['matchdate'], 0, 16) . '<br>' . $match['Stadium']['stadiumname']. '</td>';
	echo '<td style="text-align:right;">' . $this->Html->link($match['HomeTeam']['clubname'], '/clubs/clubdetails/' . $match['HomeTeam']['id']) . ' &#160;&#160;&#160;' . $match['Match']['hometeamgoals'];
	if ($match['Match']['shootoutgoalshome'] > 0 || $match['Match']['shootoutgoalsaway'] > 0){
		echo '<br />Penalties: ' . $match['Match']['shootoutgoalshome'];
	}
	echo '</td><td style="text-align:left;">' . $match['Match']['awayteamgoals']. ' &#160;&#160;&#160;' . $this->Html->link($match['AwayTeam']['clubname'], '/clubs/clubdetails/' . $match['AwayTeam']['id']);
	if ($match['Match']['et'] == 1){
		echo ' (extra time)';
	}
	if ($match['Match']['shootoutgoalshome'] > 0 || $match['Match']['shootoutgoalsaway'] > 0){
		echo '<br />' . $match['Match']['shootoutgoalsaway'];
	}
	echo '</td><td>' . $this->Html->link($match['League']['leaguename'], '/leagues/leaguedetails/' . $match['League']['id']) . '</td>';
	echo '<td><a href="/matchstats/summary/' . $match['Match']['id'] . '">Summary</a></td>';
    echo '<td><a href="/matches/replay/' . $match['Match']['id'] . '">Replay</a></td></tr>';		
 }
?>
</table>
</div>
<div id="divmatch" style="float:right; width:48%; "> 
<h1>Fixtures to come</h1>
<table>
<?php 

 echo $this->Html->tableHeaders(array('Date', 'Venue', 'Match', 'League'));

 foreach ($allfixtures as $match)
 {
	echo $this->Html->tableCells(array(substr($match['Match']['matchdate'], 0, 16), $match['Stadium']['stadiumname'],  
	 $this->Html->link($match['HomeTeam']['clubname'], '/clubs/clubdetails/' . $match['HomeTeam']['id'])
	  . ' - ' . 
	 $this->Html->link($match['AwayTeam']['clubname'], '/clubs/clubdetails/' . $match['AwayTeam']['id']), 
	 $this->Html->link($match['League']['leaguename'], '/leagues/leaguedetails/' . $match['League']['id'])
	 ));
 }
?>
</table>
</div>
</div>
</div>



<?php
print $this->Html->scriptBlock("

$(document).ready(function() {

	//$('#divsquad').load($('.ajax-link:first').attr('href'), onSuccess);
	$('.ajax-link:first').css('background-color', '#EEEEEE');
	
	function onClick(){

		$('.ajax-link').css('background-color', 'white');
		$(this).css('background-color', '#EEEEEE');

	    $('#divsquad').load($(this).attr('href'), onSuccess);
	} 

	function onSuccess(){
	   // alert('tjek');
	}

	$('.ajax-link').live('click', onClick);
		//Når selectbokse ændres
	$('.selectbokse').change(function(event){

		var txt2 = jQuery(this).find('option:selected').text(); 
		var id2 = $(this).attr('id'); 
		var tjek = 1; 
		var selBoxes = new Array();
		var dupArray = new Array();
		
		//selBoxes: Lav et array med de valgte select-options med id som key og shirtnumber som value
		$('.selectbokse').each(function(){
			idb = $(this).attr('id').split('select').pop();
			value = jQuery(this).find('option:selected').text();
			selBoxes[idb] = value;
			
		});
		
		//dupArray: Gennemgå selBoxes for duplicates på shirtnumber og læg alle duplikater i dupArray 
		for (outer in selBoxes) {   
			for (inner in selBoxes) {           
				if ((outer != inner) && (selBoxes[outer] == selBoxes[inner])){	
					dupArray[outer] = selBoxes[outer];
				}
			}
		}

		//Gennemgå selectboxe og sæt deres værdier til det der er selected
		$('.selectbokse').each(function(){
			changedId = id2.split('select').pop();
			currentId = $(this).attr('id').split('select').pop();
			document.getElementById(currentId).style.backgroundColor = '#F5F5F5';	
			$('#' + id2).val(txt2);
		});
		
		//Ændre farven på alle duplikater
		for (dup in dupArray){
			document.getElementById(dup).style.backgroundColor = '#FF4000';
		}

    });
});
	
",
array('inline' => true)
);


//NedenstÃ¥ende skal med allersidst nÃ¥r der er script pÃ¥ siden. Se http://book.cakephp.org/view/1594/Using-a-specific-Javascript-engine
echo $this->Js->writeBuffer(); 
?>
