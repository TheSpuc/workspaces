<?php 


//debug($homeatt);


if ($screen == 1){
	//screen 1 = top scorer	

?>
	<div style="width:100%;">
	<h2>Top Scorers</h2>
	<table id="table" class="tablesorter" style="width:100%;"  border="0" cellspacing="-1" cellpadding="-1"> 
	<thead>
	<tr> 
    <th title="Name" style="width: 25%; text-align:center; cursor:pointer;">Name</th> 
    <th title="Team" style="width: 25%; text-align:center; cursor:pointer;">Team</th> 
    <th title="Goal Scored" style="width: 25%; text-align:center; cursor:pointer;">Matches</th>
    <th title="Goal Scored" style="width: 25%; text-align:center; cursor:pointer;">Goals</th> 
	</tr> 
	</thead> 
	<tbody>
	
	<?PHP
        foreach($topScorers as $scorer){
		    $name = h($scorer[0]['firstname'] . ' ' . $scorer[0]['lastname']);
			echo '<tr onMouseOver="onHover(this)" onMouseOut="offHover(this)">';
			echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $this->Html->link($this->FootieText->shortenText($name, 20), array('plugin' => null,'controller' => 'person', 'action' =>'persondetails', $scorer[0]['id'])) . '</a></td>';
			if (!isset($scorer[0]['club_id'])){
				echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">Unemployed</td>';		
			}
			else{
				echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $this->Html->link($scorer[0]['clubname'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $scorer[0]['club_id'])) . '</td>';		
			}
			echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $scorer[0]['matches'] . '</td>';
			echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $scorer[0]['goals'] . '</td>';			
		}

	?>
	</tbody>
	</table>
	</div> 

<?PHP
}
elseif ($screen == 2){
	//screen 2 = assists	

?>
<h2>Most Assists</h2>
<table id="table" class="tablesorter" style="width:100%;"  border="0" cellspacing="-1" cellpadding="-1"> 
<thead>
<tr> 
    <th title="Name" style="width: 25%; text-align:center; cursor:pointer;">Name</th> 
    <th title="Team" style="width: 25%; text-align:center; cursor:pointer;">Team</th> 
    <th title="Asssist Made" style="width: 25%; text-align:center; cursor:pointer;">Matches</th> 
    <th title="Asssist Made" style="width: 25%; text-align:center; cursor:pointer;">Assists</th> 
</tr> 
</thead> 
<tbody>

<?PHP

foreach($topAssists as $assist){
    $name = h($assist[0]['firstname'] . ' ' . $assist[0]['lastname']);
	echo '<tr onMouseOver="onHover(this)" onMouseOut="offHover(this)">';
	echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $this->Html->link($this->FootieText->shortenText($name, 20), array('plugin' => null,'controller' => 'person', 'action' =>'persondetails', $assist[0]['id'])) . '</a></td>';
	if (!isset($assist[0]['club_id'])){
		echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">Unemployed</td>';		
	}
	else{
		echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $this->Html->link($assist[0]['clubname'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $assist[0]['club_id'])) . '</td>';		
	}
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $assist[0]['matches'] . '</td>';
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $assist[0]['assists'] . '</td>';	
}
?>
</tbody>
</table>
</div>
<?PHP
}
elseif ($screen == 3){
	//screen 3 = avg. rating	

?>
<h2>Highest avg. rating</h2>
<table id="table" class="tablesorter" style="width:100%;"  border="0" cellspacing="-1" cellpadding="-1"> 
<thead>
<tr> 
    <th title="Name" style="width: 25%; text-align:center; cursor:pointer;">Name</th> 
    <th title="Team" style="width: 25%; text-align:center; cursor:pointer;">Team</th> 
    <th title="Rating" style="width: 25%; text-align:center; cursor:pointer;">Matches</th> 
    <th title="Rating" style="width: 25%; text-align:center; cursor:pointer;">Rating</th> 
</tr> 
</thead> 
<tbody>

<?PHP

foreach($topRating as $rating){
    $name = h($rating[0]['firstname'] . ' ' . $rating[0]['lastname']);
	echo '<tr onMouseOver="onHover(this)" onMouseOut="offHover(this)">';
	echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $this->Html->link($this->FootieText->shortenText($name, 20), array('plugin' => null,'controller' => 'person', 'action' =>'persondetails', $rating[0]['id'])) . '</a></td>';
	if (!isset($rating[0]['club_id'])){
		echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">Unemployed</td>';		
	}
	else{
		echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $this->Html->link($rating[0]['clubname'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $rating[0]['club_id'])) . '</td>';		
	}
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $rating[0]['matches'] . '</td>';
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $rating[0]['rating'] . '</td>';	
}
?>
</tbody>
</table>
</div>
<?PHP
}   
  
elseif ($screen == 4){
	//screen 4 = top shooters	

?>
<h2>Most effective shooters</h2>
<table id="table" class="tablesorter" style="width:100%;"  border="0" cellspacing="-1" cellpadding="-1"> 
<thead>
<tr> 
    <th title="Name" style="width: 25%; text-align:center; cursor:pointer;">Name</th> 
    <th title="Team" style="width: 25%; text-align:center; cursor:pointer;">Team</th> 
    <th title="Shots" style="width: 12%; text-align:center; cursor:pointer;">Matches</th>
    <th title="Shots" style="width: 12%; text-align:center; cursor:pointer;">Shots</th>
    <th title="On target" style="width: 12%; text-align:center; cursor:pointer;">Goals</th> 
    <th title="Percent" style="width: 14%; text-align:center; cursor:pointer;">Percent</th>          
</tr> 
</thead> 
<tbody>

<?PHP

foreach($topShooter as $info){
    $name = h($info[0]['firstname'] . ' ' . $info[0]['lastname']);
	echo '<tr onMouseOver="onHover(this)" onMouseOut="offHover(this)">';
	echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $this->Html->link($this->FootieText->shortenText($name, 20), array('plugin' => null,'controller' => 'person', 'action' =>'persondetails', $info[0]['id'])) . '</a></td>';
	if (!isset($info[0]['club_id'])){
		echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">Unemployed</td>';		
	}
	else{
		echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $this->Html->link($info[0]['clubname'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $info[0]['club_id'])) . '</td>';		
	}
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $info[0]['matches'] . '</td>';
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $info[0]['shots'] . '</td>';
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $info[0]['goals'] . '</td>';
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $info[0]['percent'] . '</td>';
}
?>
</tbody>
</table>
</div>
<?PHP
}         	      	

elseif ($screen == 5){
	//screen 5 = top passers	

?>
<h2>Most accurate passers</h2>
<table id="table" class="tablesorter" style="width:100%;"  border="0" cellspacing="-1" cellpadding="-1"> 
<thead>
<tr> 
    <th title="Name" style="width: 25%; text-align:center; cursor:pointer;">Name</th> 
    <th title="Team" style="width: 25%; text-align:center; cursor:pointer;">Team</th> 
    <th title="Shots" style="width: 12%; text-align:center; cursor:pointer;">Matches</th>
    <th title="Shots" style="width: 12%; text-align:center; cursor:pointer;">Passes</th>
    <th title="On target" style="width: 12%; text-align:center; cursor:pointer;">On target</th> 
    <th title="Percent" style="width: 14%; text-align:center; cursor:pointer;">Percent</th>          
</tr> 
</thead> 
<tbody>

<?PHP

foreach($topPasser as $info){
    $name = h($info[0]['firstname'] . ' ' . $info[0]['lastname']);
	echo '<tr onMouseOver="onHover(this)" onMouseOut="offHover(this)">';
	echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $this->Html->link($this->FootieText->shortenText($name, 20), array('plugin' => null,'controller' => 'person', 'action' =>'persondetails', $info[0]['id'])) . '</a></td>';
	if (!isset($info[0]['club_id'])){
		echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">Unemployed</td>';		
	}
	else{
		echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $this->Html->link($info[0]['clubname'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $info[0]['club_id'])) . '</td>';		
	}
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $info[0]['matches'] . '</td>';
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $info[0]['passes'] . '</td>';
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $info[0]['passesontarget'] . '</td>';
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $info[0]['percent'] . '</td>';
}
?>
</tbody>
</table>
</div>
<?PHP
}    
elseif ($screen == 6){
	//screen 6 = top defenders	

?>
<h2>Top defenders</h2>
<table id="table" class="tablesorter" style="width:100%;"  border="0" cellspacing="-1" cellpadding="-1"> 
<thead>
<tr> 
    <th title="Name" style="width: 25%; text-align:center; cursor:pointer;">Name</th> 
    <th title="Team" style="width: 25%; text-align:center; cursor:pointer;">Team</th> 
    <th title="Shots" style="width: 25%; text-align:center; cursor:pointer;">Matches</th>
    <th title="On target" style="width: 25%; text-align:center; cursor:pointer;">Tackles/interceptions per match</th>       
</tr> 
</thead> 
<tbody>

<?PHP

foreach($topDefender as $info){
    $name = h($info[0]['firstname'] . ' ' . $info[0]['lastname']);
	echo '<tr onMouseOver="onHover(this)" onMouseOut="offHover(this)">';
	echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $this->Html->link($this->FootieText->shortenText($name, 20), array('plugin' => null,'controller' => 'person', 'action' =>'persondetails', $info[0]['id'])) . '</a></td>';
	if (!isset($info[0]['club_id'])){
		echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">Unemployed</td>';		
	}
	else{
		echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $this->Html->link($info[0]['clubname'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $info[0]['club_id'])) . '</td>';		
	}
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $info[0]['matches'] . '</td>';
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $info[0]['ratio'] . '</td>';
}
?>
</tbody>
</table>
</div>
<?PHP
} 
elseif ($screen == 7){
	//screen 7 = top keepers	

?>
<h2>Top keepers</h2>
<table id="table" class="tablesorter" style="width:100%;"  border="0" cellspacing="-1" cellpadding="-1"> 
<thead>
<tr> 
    <th title="Name" style="width: 25%; text-align:center; cursor:pointer;">Name</th> 
    <th title="Team" style="width: 25%; text-align:center; cursor:pointer;">Team</th> 
    <th title="Shots" style="width: 12%; text-align:center; cursor:pointer;">Matches</th>
    <th title="On target" style="width: 12%; text-align:center; cursor:pointer;">Attempted saves</th>       
    <th title="On target" style="width: 12%; text-align:center; cursor:pointer;">Saves</th>       
    <th title="On target" style="width: 14%; text-align:center; cursor:pointer;">Percent</th>               
</tr> 
</thead> 
<tbody>

<?PHP

foreach($topKeeper as $info){
    $name = h($info[0]['firstname'] . ' ' . $info[0]['lastname']);
	echo '<tr onMouseOver="onHover(this)" onMouseOut="offHover(this)">';
	echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $this->Html->link($this->FootieText->shortenText($name, 20), array('plugin' => null,'controller' => 'person', 'action' =>'persondetails', $info[0]['id'])) . '</a></td>';
	if (!isset($info[0]['club_id'])){
		echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">Unemployed</td>';		
	}
	else{
		echo '<td onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $this->Html->link($info[0]['clubname'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $info[0]['club_id'])) . '</td>';		
	}
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $info[0]['matches'] . '</td>';
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $info[0]['saveattempts'] . '</td>';
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $info[0]['saves'] . '</td>';
	echo '<td style="text-align:center;" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . $info[0]['percent'] . '</td>';		
}
?>
</tbody>
</table>
</div>
<?PHP
}        
?>