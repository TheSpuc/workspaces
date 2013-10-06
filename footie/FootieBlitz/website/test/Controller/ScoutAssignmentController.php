<?php



class ScoutAssignmentController extends AppController
{
	var $name = 'ScoutAssignments';

	public $components = array('RequestHandler');
	
	
	public function overview() {
		
		if ($this->RequestHandler->isPost() && isset($_POST['countries'])) {
			
			$this->ScoutAssignment->create();
			$this->ScoutAssignment->set('user_id', $this->Auth->user('id'));
			$this->ScoutAssignment->set('country_id', $_POST['countries']);			
			$this->ScoutAssignment->save($this->ScoutAssignment->data);
			
		}
			
		$countries = $this->ScoutAssignment->query('SELECT id, name FROM countries WHERE id NOT IN (SELECT COALESCE(country_id, -1) FROM scout_assignments WHERE finished IS NULL AND user_id=' . $this->Auth->user('id') . ') ORDER BY name');
		$this->set('countries', $countries);
			
		$assignments = $this->ScoutAssignment->find('all', array(
			'conditions' => array('ScoutAssignment.user_id' => $this->Auth->user('id')),
			'order' => 'ScoutAssignment.start'
			
		));
		$this->set('assignments', $assignments);
		
	}

	public function stopassignment($id = null){
		$this->ScoutAssignment->id = $id;
		$this->ScoutAssignment->saveField('finished', 'now()');
		
		$this->redirect(array('action' => 'overview'));
	}
	
	public function cancelassignment($id = null){
		$this->ScoutAssignment->delete($id);
	
		$this->redirect(array('action' => 'overview'));
	}
}
?>