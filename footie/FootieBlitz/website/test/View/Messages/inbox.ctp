<?php 

echo '<div id="divSubject"></div>';
//debug($th);
$int = 1;
echo $this->Form->create(null, array('type' => 'post', 'action' => 'inbox')); 
?>
<input type="submit" name="delete" value="Delete" />
<table>
<th>Nr.</th><th>Subject</th><th>Latest message</th><th>Messages</th><th>Participant</th><th>New Messages</th><th>Delete</th>
<?php
foreach ($th as $msg){
	echo '<tr>';
	echo '<td>' . $this->Html->link($int, array('plugin' => null,'controller' => 'messages', 'action' =>'read', $msg['id'])) . '</td>';	
	echo '<td>' . $this->Html->link($msg['subject'], array('plugin' => null,'controller' => 'messages', 'action' =>'read', $msg['id'])) . '</td>';
	echo '<td>' . $this->Html->link(substr($msg['updated'], 0, 16), array('plugin' => null,'controller' => 'messages', 'action' =>'read', $msg['id'])) . '</td>';
	//Hvor mange beskeder er der?
	echo '<td>' . count($msg['Messages']) . '</td>';
	echo '<td>' . h($msg['participant']) . '</td>';
	//Er der beskeder der ikke er laest?
	echo '<td>';
	if($msg['read'] == false){ echo 'New messages';}
	else{ echo 'No new messages';}
	echo '</td>';
	echo '<td>';
	echo '<input type="checkbox" name="deleted[]" value="' . $msg['id'] . '" value="Yes" />';
	echo '</td>';
	echo '</tr>';
	$int = $int + 1;
}
?>
</table>
<input type="submit" name="delete" value="Delete" />
</form>