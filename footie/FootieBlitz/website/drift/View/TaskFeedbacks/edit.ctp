<h1>Edit feedback</h1>
<?php

echo '<h3>' . $task['Task']['title'] . '</h3>';

echo '<table>';
echo '<tr><td>Priority</td><td>' . $task['Task']['priority'] . '</td></tr>';
echo '<tr><td>Type</td><td>' . $task['TaskType']['title'] . '</td></tr>';
echo '<tr><td>Description</td><td>' . $task['Task']['description'] . '</td></tr>';
echo '<tr><td>Status</td><td>' . $task['TaskStatus']['title'] . '</td></tr>';
echo '<tr><td>Status description</td><td>' . $task['Task']['statusdescription'] . '</td></tr>';
echo '<tr><td>Created by</td><td>' . $task['User']['username'] . '</td></tr>';
echo '</table>';

echo '<h3>Update feedback</h3>';
echo $this->Form->create('TaskFeedback', array('action' => 'edit'));
echo $this->Form->input('like', array('label' => 'I would prioritize this task', 'default'=> true));
echo $this->Form->input('comment', array('type' => 'textarea')); 
echo $this->Form->end('Save');
    
?>