<h1>Add Task</h1>
<div style="color: red; padding: 20px; font-size: 1.5em;"><strong>Please check if your bug report / feature request has already been submitted by another user before you create a new task!</div>
<br />
<p style="font-size: 1.2em;">After you've added a new task other users won't be able to see it until it has been reviewed and accepted by the footie team.</p></strong><br /><br />
<?php 

	echo $this->Form->create('Task');

//  echo $captcha;
   	echo $this->Form->input('captcha', array('label' => 'Calculate this: '.$captcha, 'div' => 'required'));
	echo $this->Form->input('task_types_id', array('label' => 'Type', 'type'=>'select','options'=>$tasktypes));
	echo $this->Form->input('title');
	echo $this->Form->input('description', array('type' => 'textarea'));
    echo $this->Form->input('priority', array('default'=> 0, 'label' => 'Priority (Use this to indicate whether the task is urgent or not - lower numbers have higher priority. 0 - 10 is good for new tasks, but you don\'t have to set a priority.)'));		
	echo $this->Form->end(__('Add'));


?>