<?php

/**
 * Training regime Controller
 *
 * @var Training regime Controller
 */

class TrainingRegimeController extends AppController
{
	var $name = 'TrainingRegimes';

	public $components = array('RequestHandler');
	
	public function create() {
				
		if ($this->RequestHandler->isPost()){
			
			//Check that all values add up to 100
			$total = $this->sumPostValues();
			
			if ($total != 100){
				$this->Session->setFlash(__('Validation error. Training regime not saved.'));
			}
			else{
				//Create new training regime
				$this->TrainingRegime->create();
				$this->TrainingRegime->set('user_id', $this->Auth->user('id'));
				$this->TrainingRegime->set('name', $_POST['name']);
				$this->TrainingRegime->set('description', $_POST['description']);
				
				$this->TrainingRegime->set('acceleration', $_POST['acc']);
				$this->TrainingRegime->set('topspeed', $_POST['tsp']);
				$this->TrainingRegime->set('dribbling', $_POST['drb']);
				$this->TrainingRegime->set('marking', $_POST['mar']);
				$this->TrainingRegime->set('strength', $_POST['str']);
				$this->TrainingRegime->set('tackling', $_POST['tck']);
				$this->TrainingRegime->set('agility', $_POST['agi']);
				$this->TrainingRegime->set('reaction', $_POST['rea']);
				$this->TrainingRegime->set('shooting', $_POST['sho']);
				$this->TrainingRegime->set('shotpower', $_POST['shp']);
				$this->TrainingRegime->set('vision', $_POST['vis']);
				$this->TrainingRegime->set('passing', $_POST['pas']);
				$this->TrainingRegime->set('technique', $_POST['tec']);
				$this->TrainingRegime->set('jumping', $_POST['jum']);
				$this->TrainingRegime->set('stamina', $_POST['sta']);
				$this->TrainingRegime->set('heading', $_POST['hea']);
				$this->TrainingRegime->set('handling', $_POST['han']);
				$this->TrainingRegime->set('commandofarea', $_POST['coa']);
				$this->TrainingRegime->set('shotstopping', $_POST['sst']);
				$this->TrainingRegime->set('rushingout', $_POST['rus']);

				$this->TrainingRegime->save($this->TrainingRegime->data);	

				$this->Session->setFlash(__('Training regime created.'));
				$this->redirect($this->referer());
			}

		}
	}
	
	
	//Edit training regime. Only the user who created a regime can edit it
	function edit($id = null){
		
		//Hent kontrakten
		$this->TrainingRegime->read(null, $id);
		$regime = $this->TrainingRegime->data;
		$this->set('regime', $regime);
		
		if($this->Auth->user('id') == $regime['TrainingRegime']['user_id']){
			
			if ($this->RequestHandler->isPost()){
				
				//Check that all values add up to 100
				$total = $this->sumPostValues();
				
				if ($total != 100){
					$this->Session->setFlash(__('Validation error. Training regime not saved.'));
				}
				else{
					//Edit training regime
					$this->TrainingRegime->set('name', $_POST['name']);
					$this->TrainingRegime->set('description', $_POST['description']);
				
					$this->TrainingRegime->set('acceleration', $_POST['acc']);
					$this->TrainingRegime->set('topspeed', $_POST['tsp']);
					$this->TrainingRegime->set('dribbling', $_POST['drb']);
					$this->TrainingRegime->set('marking', $_POST['mar']);
					$this->TrainingRegime->set('strength', $_POST['str']);
					$this->TrainingRegime->set('tackling', $_POST['tck']);
					$this->TrainingRegime->set('agility', $_POST['agi']);
					$this->TrainingRegime->set('reaction', $_POST['rea']);
					$this->TrainingRegime->set('shooting', $_POST['sho']);
					$this->TrainingRegime->set('shotpower', $_POST['shp']);
					$this->TrainingRegime->set('vision', $_POST['vis']);
					$this->TrainingRegime->set('passing', $_POST['pas']);
					$this->TrainingRegime->set('technique', $_POST['tec']);
					$this->TrainingRegime->set('jumping', $_POST['jum']);
					$this->TrainingRegime->set('stamina', $_POST['sta']);
					$this->TrainingRegime->set('heading', $_POST['hea']);
					$this->TrainingRegime->set('handling', $_POST['han']);
					$this->TrainingRegime->set('commandofarea', $_POST['coa']);
					$this->TrainingRegime->set('shotstopping', $_POST['sst']);
					$this->TrainingRegime->set('rushingout', $_POST['rus']);
				
					$this->TrainingRegime->save($this->TrainingRegime->data);
				
					$this->Session->setFlash(__('Training regime saved.'));
					$this->redirect($this->referer());
				}

			}
			
		}
		else{
			$this->Session->setFlash(__('This training regime was not created by you and you cannot edit it.'));
		}
	}
	
	public function sumPostValues() {
		$total = $_POST['acc'];
		$total += $_POST['tsp'];
		$total += $_POST['drb'];
		$total += $_POST['mar'];
		$total += $_POST['str'];
		$total += $_POST['tck'];
		$total += $_POST['agi'];
		$total += $_POST['rea'];
		$total += $_POST['sho'];
		$total += $_POST['shp'];
		$total += $_POST['vis'];
		$total += $_POST['pas'];
		$total += $_POST['tec'];
		$total += $_POST['jum'];
		$total += $_POST['sta'];
		$total += $_POST['hea'];
		$total += $_POST['han'];
		$total += $_POST['coa'];
		$total += $_POST['sst'];
		$total += $_POST['rus'];
		
		return $total;
	}
	
	
}
?>