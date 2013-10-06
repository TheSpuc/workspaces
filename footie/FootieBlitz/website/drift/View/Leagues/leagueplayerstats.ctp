<div>
<div style="width:100%; float: left; ">
<?PHP
echo '<h1>' . $this->Html->link($league['League']['leaguename'], array('plugin' => null,'controller' => 'leagues', 'action' =>'leaguedetails', $league['League']['id'])) . '</h1>';
echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
echo $this->Html->script('leagueplayerstats');
echo $this->Html->script('tablesorter');

//debug($league);
//debug($maxseason);

echo '<input type="hidden" id="leagueid" value=' . $league['League']['id'] . '>';
?>

Table:
<select name="slTable" id="slTable">
<option value=1 selected="true">Top scorer</option>
<option value=2>Most assists</option>
<option value=3>Highest avg. rating</option>
<option value=4>Most effective shooters</option>
<option value=5>Most accurate passers</option>
<option value=6>Top defenders</option>
<option value=7>Top keepers</option>
</select> 
<br />

<div id="divSeson">
Season: 
<select name="slSeason" id="slSeason">

<?PHP 

for ($counter = 1; $counter <= $maxseason; $counter += 1) {
  if($maxseason == $counter){ 
    echo '<option value=' . $counter . ' selected="true">' . $counter . '</option>'; 
  }else{
    echo '<option value=' . $counter . '>' . $counter . '</option>'; 
  }
}
?>
</select> 
</div>
<br />


</div>
<div id="divtable">

</div>

</div>
