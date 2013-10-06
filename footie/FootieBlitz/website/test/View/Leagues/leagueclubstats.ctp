<div>
<div style="width:100%; float: left; ">
<?PHP
echo '<h1>' . $this->Html->link($league['League']['leaguename'], array('plugin' => null,'controller' => 'leagues', 'action' =>'leaguedetails', $league['League']['id'])) . '</h1>';
echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
echo $this->Html->script('leagueclubstats');
echo $this->Html->script('tablesorter');

//debug($league);
//debug($maxseason);

echo '<input type="hidden" id="leagueid" value=' . $league['League']['id'] . '>';
?>

Table:
<select name="slTable" id="slTable">
<option value=1 selected="true">Attendance</option>
<option value=2>Form</option>
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
