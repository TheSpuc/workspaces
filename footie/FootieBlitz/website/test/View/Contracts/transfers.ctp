

<div style="width: 950px; margin: 0 auto 0 auto;">
<h1>Transfer history</h1>
<?php 

echo '<div style="width: 450px; float:left; ">';

echo '<h2>All transfers</h2>';

 echo $this->Paginator->numbers();
 
 //print_r($data);
 echo '<table><thead>';
 echo $this->Html->tableHeaders(array('Date', 'Player', 'From', 'To', 'Transfer fee'));
 echo '</thead>';
 
 foreach ($data as $item)
 {

 	echo '<tr><td>' . $item['Contract']['startdate'] . '</td>';
 	
 	echo '<td>' . $this->Html->link(h($item['Person']['firstname'][0] . ' ' . $item['Person']['lastname']),  '/person/persondetails/' . $item['Person']['id']) . '</td>';
	echo '<td>' . $this->Html->link($item['PrevClub']['clubname'],  '/clubs/clubdetails/' . $item['PrevClub']['id']) . '</td>';
	echo '<td>' . $this->Html->link($item['Club']['clubname'],  '/clubs/clubdetails/' . $item['Club']['id']) . '</td>';
	echo '<td>£ ' . number_format($item['Contract']['transferfee'], 0, ',', '.') . '</td></tr>'; 
 }

 echo '</table>';
 echo $this->Paginator->numbers();
 echo '</div>';
 
 echo '<div style="width: 450px; float:right; ">';
 echo '<h2>Top transfer fees</h2>';
 
 echo '<table><thead>';
 echo $this->Html->tableHeaders(array('Date', 'Player', 'From', 'To', 'Transfer fee'));
 echo '</thead>';
 foreach ($toptransfers as $item)
 {
 	echo '<tr><td>' . $item['Contract']['startdate'] . '</td>';
 	echo '<td>' . h($item['Person']['firstname'][0] . '. ' . $item['Person']['lastname']) . '</td>';
 	echo '<td>' . $item['PrevClub']['clubname'] . '</td>';
 	echo '<td>' . $item['Club']['clubname'] . '</td>';
 	echo '<td>£ ' . number_format($item['Contract']['transferfee'], 0, ',', '.') . '</td></tr>';
 }
 echo '</table>';
 
 echo '</div>';
 
 echo '<div style="width: 450px; clear: right; float:right; ">';
 echo '<h2>Top wages</h2>';
 
 echo '<table><thead>';
 echo $this->Html->tableHeaders(array('Date', 'Player', 'Club', 'Wage'));
 echo '</thead>';
 foreach ($topwages as $item)
 {
 	echo '<tr><td>' . $item['Contract']['startdate'] . '</td>';
 	echo '<td>' . h($item['Person']['firstname'][0] . '. ' . $item['Person']['lastname']) . '</td>';
 	echo '<td>' . $item['Club']['clubname'] . '</td>';
 	echo '<td>£ ' . number_format($item['Contract']['weeklywage'], 0, ',', '.') . '</td></tr>';
 }
 echo '</table>';
 
 echo '</div>';
?>

</div>