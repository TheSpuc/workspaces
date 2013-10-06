<h1>Scout report</h1>

<div style="width: 500px;">
<?php 
//debug($assignment);

echo '<h3>Assignments reports</h3>';
echo '<table>';

foreach ($assignment as $item)
{
	$rating = $item['ScoutReport']['potential'];
	if ($rating > 90)
		$rating = 5;
	else if($rating > 80)
		$rating = 4;
	else if($rating > 70)
		$rating = 3;
	else if($rating > 60)
		$rating = 2;
	else if ($rating > 50)
		$rating = 1;
	else
		$rating = 0;
	
	echo '<tr><td>' . substr($item['ScoutReport']['created'], 0, 10) . '</td>';
	echo '<td>' . $item['Person']['firstname'] . ' ' . $item['Person']['lastname'] . '</td>';
	echo '<td>Rating: ' . $rating . '</td>';
	echo '<td>' . $this->Html->link('[report]', array('controller' => 'ScoutReport', 'action' => 'view', $item['ScoutReport']['id'])) . '</td>';
	echo '</tr>';
}

echo '</table>';
?>
</div>


