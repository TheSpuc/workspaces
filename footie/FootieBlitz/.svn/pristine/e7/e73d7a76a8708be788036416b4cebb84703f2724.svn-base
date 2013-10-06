<?php
App::uses('AppController', 'Controller');
App::uses('Sanitize', 'Utility');

/**
 * Tasks Controller
 *
 * @var Task Controller
 */
class TasksController extends AppController {
	
	//public $components = array('Session', 'Auth');
	public $components = array(
		'MathCaptcha' => array(
        	'timer' => 2
        ),
        'Security'
	);
	
	public $helpers = array('Session', 'Html', 'Js', 'Form');
	
	public function overview() {
		$this->set('user_id', $this->Auth->user('id'));
		$this->set('admin_level', $this->Auth->user('admin_level'));
		
		//A user can only see tasks they registered or public and accepted/started tasks
		$cond = array(
					"OR" => array(
						array('Task.user_id' => $this->Auth->user('id')),
						array('AND' =>
							array(
								array('Task.status' => 2),
								array('Task.public' => true)
								)
							 ),
						array('AND' =>
							array(
								array('Task.status' => 3),
								array('Task.public' => true)
								)
							)
						)
					);
		
		//Admins see all tasks that aren't closed
		if ($this->Auth->user('admin_level') == 99){
			$cond = array('Task.status < ' => 4);
		}
		
		$this->set('tasks', $this->Task->find('all', array(
					'fields' => array('TaskType.title', 'TaskStatus.title', 'task_types_id', 'priority', 'title', 'description', 'public', 'status', 'statusdescription', 'user_id', '(SELECT count(*) FROM task_feedbacks WHERE task_id="Task"."id" AND "like"=false) as against', '(SELECT count(*) FROM task_feedbacks WHERE task_id="Task"."id" AND "like"=true) as for'), 
					'conditions' => $cond,
					'order' => array('Task.status', 'Task.priority'),
					'recursive' => 0
					)));
	}
	
	public function edit($id = null) {
		$this->set('admin_level', $this->Auth->user('admin_level'));
		$this->set('tasktypes', $this->Task->TaskType->find('list'));
		$this->set('taskstatus', $this->Task->TaskStatus->find('list'));
		
	    $this->Task->id = $id;
   		$data = $this->Task->read();
   		
	    if ($this->request->is('get')) {
	        $this->request->data = $data;
	    } else {
	    	if (($data['Task']['user_id'] == $this->Auth->user('id') && $data['Task']['status'] == 0) || $this->Auth->user('admin_level') == 99){
		        if ($this->Task->save($this->request->data)) {
		            $this->Session->setFlash('Task has been updated.');
		            $this->redirect(array('action' => 'overview'));
		        } else {
		            $this->Session->setFlash('Unable to update your task.');
		        }
	        }
	        else{
	        	$this->Session->setFlash('Only the user who created the task or users with admin privileges can edit tasks.');
	        }
	    }
	}
		
	public function add() {
		$this->set('tasktypes', $this->Task->TaskType->find('list'));
		
		
		if($this->request->is('post')){
			if($this->MathCaptcha->validate($this->request->data['Task']['captcha'])){
				
				//Set important fields to make sure user hasn't posted them
				$this->request->data['Task']['status'] = 1;
				$this->request->data['Task']['public'] = false;				
				$this->request->data['Task']['statusdescription'] = '';				
				$this->request->data['Task']['user_id'] = $this->Auth->user('id');					
				
				if ($this->Task->save($this->request->data)) {
					$this->Session->setFlash(__('Task saved.'));
					$this->redirect(array('action' => 'overview'));
				}
				else{
					$this->Session->setFlash(__('Error saving task.'));	
				}
				
			}
		}
		
		$this->set('captcha', $this->MathCaptcha->getCaptcha());
	}

}


