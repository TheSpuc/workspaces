<?PHP
echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
echo $this->Html->script('personstats');

//debug($career);
//debug($careerdetails);
?>


<div id="divcareer" style="width:47%; float: left; margin-right: 20px;">
<h2>Career overview</h2>
<table id="tblcareer" style="width:100%;"> 
<thead>
<tr> 
    <th style="width: 40%;">Club</th> 
    <th style="width: 20%;">Fee</th> 
    <th style="width: 20%;">Date</th>
    <th style="width: 20%;">Season</th> 
</tr> 
</thead> 
<tbody>

<?PHP
foreach($career as $info){
			   
echo '<tr>';
echo '<td>' . $this->Html->link($info['Club']['clubname'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $info['Club']['id'])) . '</td>';
echo '<td>' . number_format($info[0]['fee'], 0, ',', '.') . '</td>';
echo '<td>' . $info[0]['date'] . '</td>';
echo '<td>' . $info[0]['season'] . '</td>';
echo '</tr>';
}

?>
</tbody>
</table>
</div>

<div id="divcareerdetails" style="width:47%; float: left; margin-right: 20px;">
<h2>Career details</h2>
<table id="tblcareerdetails" style="width:100%;"> 
<thead>
<tr> 
    <th style="width: 10%;">Season</th>
    <th style="width: 25%;">Club</th> 
    <th style="width: 25%;">League</th> 
    <th style="width: 10%;">Matches</th> 
    <th style="width: 10%;">Assists</th> 
    <th style="width: 10%;">Goals</th>         
    <th style="width: 10%;">Rating</th>     
</tr> 
</thead> 
<tbody>

<?PHP
$season = 0;
foreach($careerdetails as $info){
		
echo '<tr>';	   
if ($season != $info[0]['season']){
	$season = $info[0]['season'];
	echo '<td>' . $info[0]['season'] . '</td>';
}
else{
	echo '</tr><tr><td></td>';
}
echo '<td>' . $this->Html->link($info[0]['club'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $info[0]['club_id'])) . '</td>';
echo '<td>' . $this->Html->link($info[0]['leaguename'], array('plugin' => null,'controller' => 'leagues', 'action' =>'leaguedetails', $info[0]['league_id'])) . '</td>';
echo '<td>' . $info[0]['matches'] . '</td>';
echo '<td>' . $info[0]['assists'] . '</td>';
echo '<td>' . $info[0]['goals'] . '</td>';
echo '<td>' . $info[0]['rating'] . '</td>';
echo '</tr>';
}


?>
</tbody>
</table>
</div>
