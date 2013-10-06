<?php
echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
echo $this->Html->script('/js/leagues.js');
echo $this->Html->script('tablesorter');
?>

<div style="width: 100%">
<div style="float: left;">
<?php
//debug($news);
foreach ($offers as $offer)
	 {
		echo $offer['Person']['lastname'] . ' has a contract offer from ' . $this->Html->link($offer['Club']['clubname'],  '/clubs/clubdetails/' . $offer['Club']['id']) . '. ';
		echo $this->Html->link('[View offer]',  '/contracts/negotiate/' . $offer['ActiveContract']['id']) . '<br />';
	 }
?>
</div>

<div style="clear:both;"></div>

<div id="divnews" style="clear: right; float:right; width:40%; "> 
<h3>Footie news</h3>

<?php 
echo '<table>';
 foreach ($news as $item)
 {
	echo '<tr><td><strong><a href="/newsitems/show">' . strip_tags(nl2br($item['NewsItem']['headline'])) . '</a></strong></td>';
	echo '<td>' . substr($item['NewsItem']['date'], 0, 10) . '</td></tr>';
 }
 echo '</table>';
 
 ?>
</div>

<div id="divrumours" style="clear:right; float:right; width:40%;  margin-top: 15px;"> 
<h3>Transfer rumours</h3>

<?php 

 echo '<table>';
 foreach ($rumours as $item)
 {
 	echo '<tr><td><strong><a href="/forum/topics/view/' . $item['Topic']['slug'] . '">' . strip_tags(nl2br($item['Topic']['title'])) . '</a></strong></td>';
 	echo '<td>' . substr($item['Topic']['modified'], 0, 10) . '</td></tr>';
 }
 echo '</table>';
?>
</div>

<div id="divtransfers" style="clear:right; float:right; width:40%;  margin-top: 15px;"> 
<h3>Latest transfers</h3>
<?php 

 echo '<table>';
 echo '<table><thead>';
 echo $this->Html->tableHeaders(array('Player', 'From', 'To', 'Transfer fee'));
 echo '</thead>';
 
 foreach ($transfers as $item)
 {
 	echo '<td>' . $this->Html->link(h($item['Person']['lastname']),  '/person/persondetails/' . $item['Person']['id']) . '</td>';
	echo '<td>' . $this->Html->link($item['PrevClub']['clubname'],  '/clubs/clubdetails/' . $item['PrevClub']['id']) . '</td>';
	echo '<td>' . $this->Html->link($item['Club']['clubname'],  '/clubs/clubdetails/' . $item['Club']['id']) . '</td>';
	echo '<td>£ ' . number_format($item['Contract']['transferfee'], 0, ',', '.') . '</td></tr>'; 
 }
 echo '</table>';
?>
<a href="/contracts/transfers/">[All transfers]</a>
</div>

<div id="divplayers" style="width:55%; "> 
<h1>Players</h1>
<table style="width:90%; clear:none;">
<?php 
 echo $this->Html->tableHeaders(array('Name', 'Club', 'PP'));

 foreach ($players as $player)
 {
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
</div>

<div id="divclubs" style="width:55%; margin-top: 15px;"> 
<h1>Clubs</h1>
<table style="width:90%; clear:none;">
<?php 

 echo $this->Html->tableHeaders(array('Club', 'League'));

 foreach ($clubs as $club)
 {
	echo $this->Html->tableCells(array( 
		$this->Html->link($club['Club']['clubname'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $club['Club']['id'])),$this->Html->link($club['League']['leaguename'], array('plugin' => null,'controller' => 'leagues', 'action' =>'leaguedetails', $club['Club']['league_id']))
	));

 }
?>
</table>
</div>

<div id="divmatches" style="float:left; width:55%; margin-top: 15px;"> 
<h1>Recent Matches</h1>
<table id="results" class="tablesorter" style="width: 100%;"> 

<thead> 
<tr> 
    <th style="cursor:pointer; width: 32%; text-align:center;">Date and Venue</th>
    <th style="cursor:pointer; width: 20%; text-align:center;">Home</th> 
    <th style="cursor:pointer; width: 20%; text-align:center;">Away</th>
    <th style="cursor:pointer; width: 20%; text-align:center;">Competition</th>  
	<th style="cursor:pointer; width: 14%; text-align:center;">Summary</th> 
    <th style="cursor:pointer; width: 14%; text-align:center;">Replay</th> 
</tr> 
</thead> 
<tbody> 
<?PHP

 
$index = 0;
               foreach($recentMatches as $match){
               $index++;
if ($index>6){ ?> <div><tr class="extra" style="display:none"><?PHP
}
else {?> <tr><?PHP
}
                    echo '<td style="text-align:center;">' . substr($match[0]['matchdate'], 0, 16) . '<br>' . $match[0]['stadiumname'] . '</td>';
                    echo '<td style="text-align:center;">' . $this->Html->link($match[0]['hometeamname'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $match[0]['hometeamid'])) . '</td>';
                    echo '<td style="text-align:center;">' . $this->Html->link($match[0]['awayteamname'], array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $match[0]['awayteamid'])) . '</td>';
                    echo '<td style="text-align:center;">' . $this->Html->link($match[0]['leaguename'], array('plugin' => null,'controller' => 'leagues', 'action' =>'leaguedetails', $match[0]['league_id'])) . '<br />' . $match[0]['stagename'] . '</td>';
					echo '<td style="text-align:center;"><a href="/matchstats/summary/' . $match[0]['id'] . '">Summary</a></td>';
                    echo '<td style="text-align:center;"><a href="/matches/replay/' . $match[0]['id'] . '">Replay</a></td></tr>';
                    if($index==6){
                          ?><tr class="more one"><td></td><td colspan="2"  style="text-align:center;">More Matches</td><td></td><td></td></tr>
<?PHP
                    }
               }
               if($index > 6){ echo '</div>';}
               echo '</tbody> </table></div>';
?>

 </div>
  <?php
 echo $this->Js->writeBuffer();
 ?>