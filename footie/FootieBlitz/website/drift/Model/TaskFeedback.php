<?php

class TaskFeedback extends AppModel {

	var $belongsTo = array(
			'Task' => array(
				'className' => 'Task',
				'foreignKey' => 'task_id'
			)
	);
}