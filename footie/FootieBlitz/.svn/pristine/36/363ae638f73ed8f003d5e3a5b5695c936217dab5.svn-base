<table>
<?PHP
foreach($players as $player){

$club = "";
	if(empty($player['Club']['clubname'])){$club = "Unemployed";}
	else { $club = $this->Html->link($player['Club']['clubname'], array('plugin' => null, 'controller' => 'clubs', 'action' => 'clubdetails', $player['Contract']['club_id']));}
	
	if($player['Person']['playerpoints'] < 0.1){ $roundatt = 0;}
	else {$roundatt = round($player['Person']['playerpoints'], 2);}
	
	echo $this->Html->tableCells(array( 
	$this->Html->link(h($player['Person']['firstname'] . ' ' . $player['Person']['lastname']),array('plugin' => null, 'controller' => 'person', 'action' => 'persondetails', $player['Person']['id'])),
	$club,
	array($roundatt, array('class' => 'red'))
	));
}

?>
</table>