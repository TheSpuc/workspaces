<?php



class ScoutReportController extends AppController
{
	var $name = 'ScoutReports';

	//public $components = array('RequestHandler');
	
	
	public function view($id = null) {
		
		//if ($this->RequestHandler->isPost() && isset($_POST['countries'])) {
		//}	

		$report = $this->ScoutReport->find('first', array(
			'conditions' => array('ScoutReport.id' => $id)
		));
		$this->set('report', $report);

	}
		
	public function assignment($id = null) {
	
		$assignment = $this->ScoutReport->find('all', array(
					'conditions' => array('ScoutReport.scout_assignment_id' => $id),
					'order' => 'ScoutReport.potential DESC'
					
	
		));
		$this->set('assignment', $assignment);
	
	}
}
?>