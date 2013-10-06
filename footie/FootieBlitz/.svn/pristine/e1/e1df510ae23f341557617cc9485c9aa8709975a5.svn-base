<?PHP

echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
echo $this->Html->css('http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/themes/smoothness/jquery-ui.css');
echo $this->Html->script('https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js');
echo $this->Html->script('replay');


if (isset($match['Match']['id'])){

echo '<input type="hidden" id="matchID" value=' . $match['Match']['id'] . '>';
$league = 0;
$league = $match['Match']['league_id'];

?>
<div id="pitchdiv" WIDTH="992px" HEIGHT="662px">
     <APPLET CODEBASE='/applets/replay/' CODE='Replay.class' width='990px' height='660px'>
     <?PHP 
        echo "<PARAM NAME=league VALUE='" . $league  . "'>"; 
        echo "<PARAM NAME=match VALUE='" . $match['Match']['id'] . "'>"; 
      ?>
     </APPLET>
</div>
<div id="tabs">
	<ul>
		<li><a href="#tabmatchstats">Match stats</a></li>
		<li><a href="#tabplayerstats">Player stats</a></li>
	</ul>
	<div id="tabmatchstats">

		<div id="divmstats"></div>
	</div>
	<div id="tabplayerstats">

		<div id="divpstats" style=' font-size:0.8em; '></div>
	</div>
</div>

<?PHP

}
else{
 echo 'Error. No match found.';
}

echo $this->Js->writeBuffer(); // Write cached scripts
?>