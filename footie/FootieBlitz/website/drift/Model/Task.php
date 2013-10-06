<?php

class Task extends AppModel {

	var $belongsTo = array(
			'TaskType' => array(
				'className' => 'TaskType',
				'foreignKey' => 'task_types_id'
			),
			'TaskStatus' => array(
				'className' => 'TaskStatus',
				'foreignKey' => 'status'
			),
			'User' => array(
				'className' => 'User',
				'foreignKey' => 'user_id'
			)
	);
	var $hasMany = array(
			'TaskFeedback' => array(
				'className' => 'TaskFeedback',
				'foreignKey' => 'task_id'
			)
	);

	public $validate = array(
        'title' => array(
            'rule' => 'notEmpty'
        ),
        'description' => array(
            'rule' => 'notEmpty'
        )
    );
}