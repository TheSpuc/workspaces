<?PHP

//debug($matches);
	echo "<table><tr><td style='width: 16.6%;'>";
	
	echo "Rename user";
    echo $this->Form->create('RenameUser');
    echo $this->Form->input('username', array('type' => 'text', 'label' => 'Username'));
    echo $this->Form->input('user_id', array('type' => 'text', 'label' => 'User id'));
	echo $this->Form->end('Rename');
	
	echo "</td><td style='width: 16.6%;'>";
	
	echo "Set UP for user";
    echo $this->Form->create('UPForUser');
    echo $this->Form->input('userpoints', array('type' => 'text', 'label' => 'Userpoints'));
    echo $this->Form->input('user_id', array('type' => 'text', 'label' => 'User id'));
	echo $this->Form->end('Set');
	
	echo "</td><td style='width: 16.6%;'>";
	
	echo "Join club to user";
    echo $this->Form->create('ClubToUser');
    echo $this->Form->input('club_id', array('type' => 'text', 'label' => 'Club id'));
    echo $this->Form->input('user_id', array('type' => 'text', 'label' => 'User id'));
	echo $this->Form->end('Join');
	
	echo "</td><td style='width: 16.6%;'>";
	?>
	Reset match
	<form method="POST">
		Match id<br />
		<input type="text" name="txtMatchId" /><br />
        Days to add to matchdate<br />
		<input type="text" name="txtAddDays" /><br />
		<input type="submit" name="sbmResetMatch">
	</form>
	<?PHP
	
	echo "</td><td style='width: 16.6%;'>";
	echo 'Matches running';
		foreach ($matches as $match){
			echo '<div>' . $match['HomeTeam']['clubname'] . ' - ' . $match['AwayTeam']['clubname'] . '</div>';
			echo '<div>' . $match['Match']['id'] . ' (' .  substr($match['Match']['matchdate'], 0, 16) . ')</div>';
			echo '-------------';
		}
	echo "</td><td>";

	echo "Set user password";
    echo $this->Form->create('UserPassword');
    echo $this->Form->input('password', array('type' => 'text', 'label' => 'Password'));
    echo $this->Form->input('user_id', array('type' => 'text', 'label' => 'User id'));
	echo $this->Form->end('Set Password');
	
	echo "</td></tr><tr><td>";
	
	echo "Rename club";
    echo $this->Form->create('RenameClub');
    echo $this->Form->input('clubname', array('type' => 'text', 'label' => 'Clubname'));
    echo $this->Form->input('club_id', array('type' => 'text', 'label' => 'Club id'));
	echo $this->Form->end('Rename');

	echo "</td><td>";
	
	echo "Rename club shortname";
    echo $this->Form->create('RenameClubShort');
    echo $this->Form->input('shortname', array('type' => 'text', 'label' => 'Short name'));
    echo $this->Form->input('club_id', array('type' => 'text', 'label' => 'Club id'));
	echo $this->Form->end('Rename');
	
	echo "</td><td>";
	
	echo "Set money for club";
    echo $this->Form->create('MoneyForClub');
    echo $this->Form->input('money', array('type' => 'text', 'label' => 'Money'));
    echo $this->Form->input('club_id', array('type' => 'text', 'label' => 'Club id'));
	echo $this->Form->end('Set');
	
	echo "</td><td>";
		
	echo "Set club fame";
    echo $this->Form->create('ClubFame');
    echo $this->Form->input('fame', array('type' => 'text', 'label' => 'Fame'));
    echo $this->Form->input('club_id', array('type' => 'text', 'label' => 'Club id'));
	echo $this->Form->end('Set');
	
	echo "</td><td>";

	
	echo "</td></tr><tr><td>";
	
	echo "Rename stadium";
    echo $this->Form->create('RenameStadium');
    echo $this->Form->input('stadiumname', array('type' => 'text', 'label' => 'Stadium name'));
    echo $this->Form->input('club_id', array('type' => 'text', 'label' => 'Club id'));
	echo $this->Form->end('Rename');	
	
	echo "</td><td>";
	
	
	echo "</td><td>";
	
	
	echo "</td><td>";
	
	
	echo "</td></tr><tr><td>";
	
	echo "Rename person firstname";
    echo $this->Form->create('RenamePersonFirst');
    echo $this->Form->input('firstname', array('type' => 'text', 'label' => 'Firstname'));
    echo $this->Form->input('person_id', array('type' => 'text', 'label' => 'Person id'));
	echo $this->Form->end('Set');
	
	echo "</td><td>";
	
	echo "Rename person lastname";
    echo $this->Form->create('RenamePersonLast');
    echo $this->Form->input('lastname', array('type' => 'text', 'label' => 'Lastname'));
    echo $this->Form->input('person_id', array('type' => 'text', 'label' => 'Person id'));
	echo $this->Form->end('Set');
	
	echo "</td><td>";
	
	echo "Set PP for person";
    echo $this->Form->create('PPForPerson');
    echo $this->Form->input('playerpoints', array('type' => 'text', 'label' => 'Playerpoints'));
    echo $this->Form->input('person_id', array('type' => 'text', 'label' => 'Person id'));
	echo $this->Form->end('Set');
	
	echo "</td><td>";
	
	?>
	Create new news writer
	<form method="POST">
		League id<br />
		<input type="text" name="txtLeague" /><br />
		User id<br />
		<input type="text" name="txtUser" />
		<input type="submit" name="sbmNewWriter">
	</form>
	</td><td>
	
	Reset test site
	<form method="POST">
	<input type="submit" name="sbmProdToTest">
	</form>
	</td>
	
	<?php 
		
	echo "</tr></table>";
echo 'Sendt: ';
debug($sendt);
echo 'Savedata: ';
debug($savedata);

	debug($users);
// echo 'Club Data: ';
// debug($clubdata);
//echo 'Klub: ';
//debug($klub);
?>