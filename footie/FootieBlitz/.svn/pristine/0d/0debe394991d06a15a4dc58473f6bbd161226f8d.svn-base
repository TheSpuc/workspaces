<?php
//debug($history);
?>

<div id="divcontent" style="margin: 0px auto 0px auto;">
<h1><?php echo $history[0]['Club']['clubname']; ?></h1>
<table>
<tr><td>Highest transfer fee paid</td><td><?php echo "£ " . number_format($history[0][0]['feepaid'], 0, ',', '.'); ?></td></tr>
<tr><td>Highest transfer fee received</td><td><?php echo "£ " . number_format($history[0][0]['feereceived'], 0, ',', '.'); ?></td></tr>
<tr><td>Highest wage paid</td><td><?php echo "£ " . number_format($history[0][0]['wage'], 0, ',', '.'); ?></td></tr>
<tr><td>Biggest transfer budget</td><td><?php echo "£ " . $history[0][0]['maxbudget']; ?></td></tr>

<tr><td>Longest winning streak</td><td><?php echo $won . ' (current: ' . $currwon . ')'; ?></td></tr>
<tr><td>Longest unbeaten run</td><td><?php echo $unbeaten . ' (current: ' . $currunbeaten . ')'; ?></td></tr>
<tr><td>Longest losing streak</td><td><?php echo $lost . ' (current: ' . $currlost . ')'; ?></td></tr>

<tr><td>Most goals in a season</td><td><?php echo $maxgoals[0][0]['goals'] . " (" . $maxgoals[0][0]['firstname'] . " " . $maxgoals[0][0]['lastname'] . " - season " . $maxgoals[0][0]['season'] . ")"; ?></td></tr>
<tr><td>Most assists in a season</td><td><?php echo $maxassists[0][0]['assists'] . " (" . $maxassists[0][0]['firstname'] . " " . $maxassists[0][0]['lastname'] . " - season " . $maxassists[0][0]['season'] . ")"; ?></td></tr>
<tr><td>Highest avg. rating in a season</td><td><?php echo $maxrating[0][0]['rating'] . " (" . $maxrating[0][0]['firstname'] . " " . $maxrating[0][0]['lastname'] . " - season " . $maxrating[0][0]['season'] . ")"; ?></td></tr>

<!-- 
<tr><td>Most goals in one match</td><td></td></tr>
<tr><td>Youngest player</td><td></td></tr>
<tr><td>Oldest player</td><td></td></tr>
 -->
 
<tr><td>Highest attendance</td><td><?php echo $maxatt[0][0]['attendance'] . " (against " . $maxatt[0][0]['clubname'] . " on " . $maxatt[0][0]['matchdate'] . ")"; ?></td></tr>
<tr><td>Lowest attendance</td><td><?php echo $minatt[0][0]['attendance'] . " (against " . $minatt[0][0]['clubname'] . " on " . $minatt[0][0]['matchdate'] . ")"; ?></td></tr>

</table>
<?php
	 
?>
</div>
