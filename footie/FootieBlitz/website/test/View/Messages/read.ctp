<style type="text/css">
	div#msgfrom{
		margin-left: 20px;
	}
	div#msgtext{
		margin-left: 40px;
		margin-bottom: 20px;
	}
</style>

<?php 
if(!empty($thread)){
	echo '<h1>' . h($subject) . '</h1>';
	}

if($FA){
		echo '<div id="FANoreply">You cannot contact the FA. The FA will contact you!</div>';
	}
	else{
		////Reply////
		//'url'=> $this->here sætter url til det som den er i forvejen så evt. parametre ikke går tabt
		echo '<div style="margin-left: 20px;">';
		echo $this->Form->create('Message', array('action' => 'read', 'url'=> $this->here));
		echo $this->Form->textarea('text', array('style' => array('width' => 'width: 70%;', 'height' => 'height: 150px;')));
		echo $this->Form->end('Reply');
		echo '</div>';
	}

if(!empty($thread)){
	foreach ($thread as $msg){
		echo '<div id="msgfrom"><h3>' . h($msg['From']['username']) . ' - ' . substr($msg['Message']['sent'], 0, 16) . '</h3></div>';
		echo '<div id="msgtext">' . strip_tags(nl2br($msg['Message']['text']), '<strong><em><br><a>') . '</div>';
	}


// debug($count);
// debug($saveThreads);

	
}
?>