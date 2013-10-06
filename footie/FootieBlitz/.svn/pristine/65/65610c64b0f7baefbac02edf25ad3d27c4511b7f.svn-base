<?php
	echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
	echo $this->Html->css('http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/themes/smoothness/jquery-ui.css');
	echo $this->Html->script('https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js');
	echo $this->Html->script('contractnegotiate');
?>
<h1>Negotiate contract</h1>

<?php
   
//debug($contract);
//debug($showform);
//debug($activeContract);



if (!isset($contract['Contract']['id'])){
	echo 'Contract doesn\'t exist!';
}
elseif ($contract['Contract']['acceptedbyplayer'] == 1){
	echo 'This contract has already been signed by all parties and cannot be negotiated.';
}
else{

	 if ($showform == 1){
	
	      echo '<form method="post" id="fcon" name="f' . mktime() . '"><table cellpadding="0" cellspacing="0">';
	      echo '<tr><td>Club </td><td>';
	      echo $contract['Club']['clubname'];
	      echo '</td></tr>';
	      echo "<tr><td>Employee</td><td>" . h($contract['Person']['firstname'] . ' ' . $contract['Person']['lastname']) . "</td>";
	      echo '<tr><td>Role</td><td id="roletd" name="roletd">Player</td></tr>';
	      echo '<tr><td>Sign on fee</td><td><input type="text" id="signonfee" name="signonfee" value="' . $contract['Contract']['signonfee'] . '"/><span id="signonfeeerror"></span></td></tr>';
	      echo '<tr><td>Wage (suggested for this player: ' . $suggwage . ') </td><td><input type="text" id="wage" name="wage" value="' . $contract['Contract']['weeklywage'] . '"/><span id="wageerror"></span></td></tr>';
	      echo '<tr><td>Goal bonus</td><td><input type="text" name="goalbonus" id="goalbonus" value="' . $contract['Contract']['goalbonus'] . '"/><span id="goalerror"></span></td></tr>';
	      echo '<tr><td>Assist bonus</td><td><input type="text" name="assistbonus" id="assistbonus" value="' . $contract['Contract']['assistbonus'] . '"/><span id="assisterror"></span></td></tr>';
	      $chk = '';
	      if ($contract['Contract']['minimumreleaseclause'] > -1){
	         $chk = ' checked';
	      }
	      echo '<tr><td>Release clause <input type=checkbox name="withclause" id="withclause"' . $chk . '></td>';
	      echo '<td><input type="text" name="releaseclause" id="releaseclause" value="' . $contract['Contract']['minimumreleaseclause'] . '"/><span id="releaseerror"></span></td></tr>';
	      echo '<tr><td>Expiration date</td><td><div type="text" id="expdate" name="expdate"></div></td></tr>';
	      echo '<input type="hidden" name="postdate" id="postdate" value="' . $contract['Contract']['enddate'] . '" />';
	      echo '<input type="hidden" name="signed" id="signed" value="0" />';
	      echo '<tr><td>';
	      if ($contract['Contract']['offered'] == 1 && $contract['Contract']['acceptedbyclub'] == 1){ 
	            echo '<input type="button" id="btnsign" value="Sign contract"><input type="button" id="btnrej" value="Reject contract">';
	      }
	      echo '</td><td><input type="submit" value="Send negotiated offer" name="subm"/>';
	      echo '<INPUT TYPE="button" value="Cancel" onClick="parent.location=\'?q=node/4&id=' . $contract['Person']['id'] . '\'"></td></tr>';
	      echo '</table></form>';
	
	 }
	 elseif ($showform == 2){
	     $curclause = '';
	     if(isset($activeContract['Contract']) && array_key_exists('minimumreleaseclause', $activeContract['Contract']) == true && $activeContract['Contract']['minimumreleaseclause'] > -1){
	         $curclause = ' (current release clause: ' . $activeContract['Contract']['minimumreleaseclause'] . ')';
	     }
	     echo '<form method="post" name="f' . mktime() . '"><table cellpadding="0" cellspacing="0">';
	     echo '<tr><td>Tranfer fee' . $curclause . '</td><td><input type="text" id="transferfee" name="transferfee" value="' . $contract['Contract']['transferfee'] . '"/><span id="transferfeeerror"></span></td></tr>';
	     echo '<tr><td></td><td><input type="submit" value="Send negotiated offer" name="subm"/>';
		 echo $this->Html->link('Cancel', '/users/office', array('class' => 'button', 'target' => '_blank'));
	     echo '</table></form>';
 	}	
}          
              

?>
