<?php
App::uses('AppController', 'Controller');

/**
 * TaskFeedbacks Controller
 *
 * @var TaskFeedbacks Controller
 */
class TaskFeedbacksController extends AppController {
	
	public $components = array('Security');
	
	public $helpers = array('Session', 'Html', 'Js', 'Form');
	
	public function edit($id = null) {
		
	    $this->TaskFeedback->id = $id;
   		$data = $this->TaskFeedback->read();
   		
   		$this->set('task', $this->TaskFeedback->Task->find('first', array('conditions' => array('Task.id' => $data['TaskFeedback']['task_id']))));
   		
	    if ($this->request->is('get')) {
	        $this->request->data = $data;
	    } else {
	    	if ($data['TaskFeedback']['user_id'] == $this->Auth->user('id')){
		        if ($this->TaskFeedback->save($this->request->data)) {
		            $this->Session->setFlash('Feedback has been updated.');
		            $this->redirect(array('controller' => 'tasks', 'action' => 'overview'));
		        } else {
		            $this->Session->setFlash('Unable to update your feedback.');
		        }
	        }
	        else{
	        	$this->Session->setFlash('Only the user who created the feedback can edit it.');
	        }
	    }
	}
		
	public function add($id = null) {
		$fbid = $this->TaskFeedback->find('first', array('conditions' => array('TaskFeedback.user_id' => $this->Auth->user('id'), 'TaskFeedback.task_id' => $id)));
		
		if (!isset($fbid['TaskFeedback']['id'])){
			//If the user hasn't left feedback for this task yet let him do it
			if($this->request->is('post')){
			
				//Set important fields to make sure user hasn't posted them
				$this->request->data['TaskFeedback']['task_id'] = $id;				
				$this->request->data['TaskFeedback']['user_id'] = $this->Auth->user('id');					
				
				if ($this->TaskFeedback->save($this->request->data)) {
					$this->Session->setFlash(__('Feedback saved.'));
					$this->redirect(array('controller' => 'tasks', 'action' => 'overview'));
				}
				else{
					$this->Session->setFlash(__('Error saving feedback.'));	
				}
			}
			else{
				$this->set('task', $this->TaskFeedback->Task->find('first', array('conditions' => array('Task.id' => $id))));
			}
		}
		else{
			//The user has already submitted feedback for this task. Redirect to edit
			$this->redirect(array('action' => 'edit/' . $fbid['TaskFeedback']['id']));
		}

	}

}


