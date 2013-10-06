<?PHP

if(!isset($ownedClub['Club'])){
	echo 'Only club owners can send contract offers';
}
else{
	
	echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
	echo $this->Html->script('contractoffer');
	echo $this->Html->css('http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/themes/smoothness/jquery-ui.css');
	echo $this->Html->script('https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js');
	
// 	debug($ownedClub);
// 	debug($person);
// 	debug($currentClub);
	
	?>
	
	<h1>Contract offer</h1>
	
	<form method="post"">
	
	<?php echo '<input type="hidden" id="pid" name="pid" value=' . $person['Person']['id'] . ' />'; ?>
	
	<table>
	<tr><td>Club </td><td>
	<?php 
	
	echo $ownedClub['Club']['clubname'];
	echo '</td><td style="width: 20%;"></td></tr>';
	echo "<tr><td>Employee</td><td>" . h($person['Person']['firstname'] . ' ' . $person['Person']['lastname']) . "</td><td></td>";
	echo "</td>";
	echo '<tr><td>Role</td><td id="roletd" name="roletd">';
	echo 'Player</td><td></td></tr>';
	echo '<input type="hidden" id="role" name="role" value="1" />';
	
	if(!isset($currentClub['Club'])){
	  	echo '<tr><td>Tranfer fee</td><td>0 (free agent)<input type="hidden" id="transferfee" name="transferfee" value="0"/></td><td></td>';
	}
	elseif($currentClub['Club']['id'] == $ownedClub['Club']['id']){
	 	echo '<tr><td>Tranfer fee</td><td>0 (contract extension)<input type="hidden" id="transferfee" name="transferfee" value="0"/></td><td></td>';
	}
	else{
	  $curclause = '';
	  if (isset($person['ActiveContract']['minimumreleaseclause'])){
		  if($person['ActiveContract']['minimumreleaseclause'] > -1){
		       $curclause = ' (current release clause: ' .  $person['ActiveContract']['minimumreleaseclause'] . ')';
		  }
	  }
	  echo '<tr><td>Tranfer fee' . $curclause . '</td><td><input type="text" id="transferfee" name="transferfee" value="0"/></td><td><span id="transferfeeerror"></span></td></tr>';
	}
	echo '<tr><td>Sign on fee</td><td><input type="text" id="signonfee" name="signonfee" value="0"/></td><td><span id="signonfeeerror"></span></td></tr>';
	echo '<tr><td>Wage (suggested for this player: ' . $suggwage . ') </td><td><input type="text" id="wage" name="wage" value="0"/></td><td><span id="wageerror"></span></td></tr>';
	
	echo '<tbody class="bonus">';
	echo '<tr><td>Goal bonus</td><td><input type="text" name="goalbonus" id="goalbonus" value="0"/></td><td><span id="goalerror"></span></td></tr>';
	echo '<tr><td>Assist bonus</td><td><input type="text" name="assistbonus" id="assistbonus" value="0"/></td><td><span id="assisterror"></span></td></tr>';
	echo '<tr><td>Release clause <input type=checkbox name="withclause" id="withclause"></td><td><input type="text" name="releaseclause" id="releaseclause" value="0"/></td><td><span id="releaseerror"></span></td></tr>';
	echo '</tbody>';
	
	?>
	<tr><td>Expiration date</td><td><div type="text" id="expdate" name="expdate"></div></td></tr>
	<input type="hidden" name="postdate" id="postdate" />
	<tr><td></td><td><input type="submit" value="Offer contract" name="submit"/>
	<?PHP
	echo $this->Html->link('Cancel',  '/clubs/clubdetails/' . $ownedClub['Club']['id']);
	?>
	</table>
	</form>
	
	<?PHP

}