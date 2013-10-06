<p>Clubs</p>

<?php
    //echo $this->data['Club']['clubname'];


?>
<?php //var_dump($showclubs) 
	echo $users_username;
	
	
	echo $uid;
	?>
<table>
<?php 

echo $html->tableHeaders(array_keys($showclubs[0]['Club']));

foreach ($showclubs as $club)
{
//echo $html->tableCells($club['Club']);?>
<tr>

	<td><?php	echo $this->Html->link(__($club['Club']['id'], true), array('action' => 'clubdetails', $club['Club']['id'])); ?></td>
	<td><?php	echo $this->Html->link(__($club['Club']['clubname'], true), array('action' => 'clubdetails', $club['Club']['id'])); ?></td>
	<td><?php	echo $club['Club']['stadium_id']; ?></td>
	<td><?php	echo $club['Club']['money']; ?></td>
	<td><?php	echo $club['Club']['user_id']; ?></td>
</tr>
<?php }
 debug($showclubs);
?>
</table>

<table>
<?php 

echo $html->tableHeaders(array_keys($showcontracts[0]['Contract']));

foreach ($showcontracts as $thiscontract)
{
	echo $html->tableCells($thiscontract['Contract']);
}
?>
</table>
<table>
<?php 

echo $html->tableHeaders(array_keys($showplayers[0]['Person']));

foreach ($showplayers as $thisplayer)
{
	echo $html->tableCells($thisplayer['Person']);
}
 debug($showplayers);
?>
</table>