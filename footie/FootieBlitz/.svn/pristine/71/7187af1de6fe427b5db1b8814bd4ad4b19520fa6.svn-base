<h1>Tasks</h1>

<?php 
	echo $this->Html->link('[add new task]', '/tasks/add');
	
	echo '<table>';
	echo '<thead>';
	echo $this->Html->tableHeaders(array('Priority', 'Type', 'Title', 'Description', 'Feedback', 'Status', 'Status description', 'Actions'));
	echo '</thead><tbody>';	
	foreach($tasks as $task){
		
		$actions = "";
		if (($task['Task']['user_id'] == $user_id && $task['Task']['status'] == 1) || $admin_level == 99){
			$actions = ' ' . $this->Html->link('[edit]', '/tasks/edit/' . $task['Task']['id']) . '<br />';
		}
		$actions = $actions . ' ' . $this->Html->link('[feedback]', '/TaskFeedbacks/add/' . $task['Task']['id']);

		echo $this->Html->tableCells(array(
			$task['Task']['priority'], 
			$task['TaskType']['title'],
			'<strong>' . h($task['Task']['title']) . '</strong>', 
			h($task['Task']['description']), 
			'<span style="color: green;">' . $task[0]['for'] . '</span>/<span style="color: red;">' . $task[0]['against'] . '</span>', 
			$task['TaskStatus']['title'], 
			$task['Task']['statusdescription'], 
			$actions
		));	
	}
	echo '</tbody>';
	echo '</table>';
	
	//debug($tasks);

?>

