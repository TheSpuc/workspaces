<h1>Edit Task</h1>
<?php
    echo $this->Form->create('Task', array('action' => 'edit'));
    echo $this->Form->input('task_types_id', array('label' => 'type', 'type'=>'select','options'=>$tasktypes));
    echo $this->Form->input('title');
    echo $this->Form->input('description', array('rows' => '3'));
    
    if ($admin_level == 99){
    	echo $this->Form->input('status', array('label' => 'status', 'type'=>'select','options'=>$taskstatus));
    	echo $this->Form->input('statusdescription', array('rows' => '3'));
    	echo $this->Form->input('public');
    }
    
    echo $this->Form->input('priority');	
    echo $this->Form->input('id', array('type' => 'hidden'));
    echo $this->Form->end('Save');
    
?>