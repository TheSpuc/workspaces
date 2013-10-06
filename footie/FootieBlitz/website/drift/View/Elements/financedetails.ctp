<?php 

if($userownsclub > 0){
	
//debug($club);
//debug($maxseason);
//debug($expenses);
//debug($income);
//debug($overview);
}
else{
	echo 'Only club owners can view finances.';	
}
 

if ($type == 'season'){
	if($season['Season']['number'] < $maxseason[0]['maxseason']){
		$prevSeason = $season['Season']['number'] + 1;
		echo $this->Html->link('season ' . $prevSeason, '/clubs/financedetails/' . $club['Club']['id'] . '/season/' . $prevSeason, array('class' => 'ajax-link', 'onclick'=>'return false;'));
		echo ' ';
	}
	
	echo 'Season ' . $season['Season']['number'] . ' ';
	
	if($season['Season']['number'] > 1){
		$nextSeason = $season['Season']['number'] - 1;
		echo $this->Html->link('season ' . $nextSeason, '/clubs/financedetails/' . $club['Club']['id'] . '/season/' . $nextSeason, array('class' => 'ajax-link', 'onclick'=>'return false;'));
	}
}
else{
	if($week < $maxweek){
		$prevWeek = $week + 1;
		echo $this->Html->link('week ' . $prevWeek, '/clubs/financedetails/' . $club['Club']['id'] . '/week/' . $prevWeek . '', array('class' => 'ajax-link2', 'onclick'=>'return false;'));
		echo ' ';
	}
	
	echo 'Week ' . $week;
	
	if($week > 1){
		echo ' ';
		$nextWeek = $week - 1;
		echo $this->Html->link('week ' . $nextWeek, '/clubs/financedetails/' . $club['Club']['id'] . '/week/' . $nextWeek . '', array('class' => 'ajax-link2', 'onclick'=>'return false;'));
	}
}
echo '<table id="finoverview" class="tablesorter">';
echo '<thead>'; 
echo '<tr>';
echo '<th style="cursor:pointer; width:35%">Category</td>';
echo '<th style="cursor:pointer; width:17%">Expenses</td>';
echo '<th style="cursor:pointer; width:24%">Income</td>';
echo '</tr>';
echo '</thead>'; 
echo '<tbody>';
   
$result = 0;   
foreach ($overview as $info){
   	echo "<tr><td>" . $info[0]['description'] . "</td><td>" . number_format($info[0]['expenses'], 0, ',', '.') . "</td><td>" . number_format($info[0]['income'], 0, ',', '.') . "</td></tr>";
   	if($info[0]['description'] == "Sub total"){
   		$result = number_format($info[0]['income'] - $info[0]['expenses'], 0, ',', '.');
   	}
}
   
echo '<tr><td>Result</td><td></td><td>' . $result . '</td></tr>';     
echo '</tbody></table>';   	   	      	


?>