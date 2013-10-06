<h1>Scouting assignments</h1>

<div style="width: 500px;">

<?php
if(count($countries) > 0){
	echo 'Scout country:';
	echo '<form method="post">';
	echo '<select name="countries">';

	 foreach ($countries as $item)
	 {
	 	echo '<option value=' . $item[0]['id'] . ' />' . $item[0]['name'] . '</option>';
	 }
	 echo '</select>'; 
	
	 echo $this->Form->submit('Add assignment');
	
	 echo '</form>';
}
?>

</div>

<?php 
//debug($countries);
//debug($assignments);
 
echo '<div style="width: 500px;">';
echo '<h3>Scouting assignments</h3>';
echo '<table>';

 foreach ($assignments as $item)
 {
 	$status = "pending";
 	if (isset($item['ScoutAssignment']['finished'])){
 		$status = "finished";
 	}
 	else if (isset($item['ScoutAssignment']['start'])){
 		$status = "ongoing";
 	}
 	
 	if (isset($item['Person']['id'])){
		echo '<tr><td>' . $item['Person']['firstname'] . ' ' . $item['Person']['lastname'] . '</td>';
 	}
 	elseif (isset($item['Country']['id'])){
		echo '<tr><td>' . $item['Country']['name'] . '</td>';
 	}
 	
	echo '<td>' . substr($item['ScoutAssignment']['assigned'], 0, 10) . '</td>';
	echo '<td>' . $status . '</td>';
	
	if (count($item['ScoutReport']) == 0){
		echo '<td></td>';
	}
	else if (count($item['ScoutReport']) > 1){
		echo '<td>' . $this->Html->link('[reports]', array('controller' => 'ScoutReport', 'action' => 'assignment', $item['ScoutAssignment']['id'])) . ' (' . count($item['ScoutReport']) . ')</td>';
	}
	else{
		echo '<td>' . $this->Html->link('[report]', array('controller' => 'ScoutReport', 'action' => 'view', $item['ScoutReport'][0]['id'])) . '</td>';
	}
	
	echo '<td>';
	
	if ($status == "ongoing"){
		echo $this->Html->link('[stop]', array('controller' => 'ScoutAssignment', 'action' => 'stopassignment', $item['ScoutAssignment']['id']));
	}
	else if ($status == "pending"){
		echo $this->Html->link('[cancel]', array('controller' => 'ScoutAssignment', 'action' => 'cancelassignment', $item['ScoutAssignment']['id']));
	}
	
	echo '</td>';
	//echo substr($item['NewsItem']['date'], 0, 10) . '<br />';
	//echo $item['League']['leaguename'] . '<br /><br />';
	echo '</tr>';
 }
 
 echo '</table>';
 echo '</div>';
 ?>

