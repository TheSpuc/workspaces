<?php


//debug($stadiums);

 foreach ($stadiums as $stadium)
 {
 	echo Sanitize::html($stadium['Stadium']['stadiumname']);
 	echo '<br />';
 }
?>

<form method="POST">
<input type="text" name="sname" />
<input type="submit" name="savesubmit" value="submit save" />
<input type="submit" name="querysubmit" value="submit query" />
</form>

