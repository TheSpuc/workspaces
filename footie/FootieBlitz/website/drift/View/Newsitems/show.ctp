<h1>Footie news</h1>

<?php 

echo '<div style="width: 500px;">';

 echo $this->Paginator->numbers();
 
 foreach ($data as $item)
 {
	echo '<h2>' . strip_tags(nl2br($item['NewsItem']['headline'])) . '</h2>';
	echo substr($item['NewsItem']['date'], 0, 10) . '<br />';
	echo $item['League']['leaguename'] . '<br /><br />';
	echo '<p>' . strip_tags(nl2br($item['NewsItem']['body']), '<strong><em><br><a>') . '</p><br /><br />';
 }
 echo '</div>';
 
 echo $this->Paginator->numbers();
 
 if (count($writerLeagues) > 0){
 	
 	?>
 	<br />
 	<br />	
 	<h3>Write new article</h3>
 	<form method="POST">
 	League <select name="slLeague">
 	<?php 
	 	foreach ($writerLeagues as $wl)
	 	{
	 		echo '<option value=' . $wl[0]['league_id'] . '>' . $wl[0]['leaguename'] . '</option>';
	 	}
 	?>
 	</select> 	
 	<br />
 	Headline <input type="text" id="txtHeadline" name="txtHeadline" />
 	<br />
 	Text 
 	<br />
 	<textarea cols=40 rows=10 id="txtBody" name="txtBody"></textarea>
 	<br />
 	<input type="submit" name="sbmNews" value="Submit" />
 	</form>
 	<?php 
 	
 }
?>

