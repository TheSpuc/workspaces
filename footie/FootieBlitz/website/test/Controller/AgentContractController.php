<?php

/**
 * Agent Contract Controller
 *
 * @var Agent Contract Controller
 */

class AgentContractController extends AppController
{
	var $name = 'AgentContracts';

	public $components = array('RequestHandler');
	
	
	public function offer($id = null) {
				
		//Load person
		$this->loadModel('Person');
		$this->Person->recursive=-1;
		$person = $this->Person->find('first', array('conditions' => array('Person.id' => $id)));
		$this->set('person', $person);
		
		//Check that player exists
		if (!isset($person['Person'])){
			$this->Session->setFlash(__('Player not found.'));
			$this->redirect($this->referer());
		}
		
		//Check that player does not already have an agent
		elseif ($person['Person']['user_id'] != 99){
			$this->Session->setFlash(__('Player already has an agent.'));
			$this->redirect($this->referer());
		}
		
		//Check that user is agent and not club owner
		$this->loadModel('Club');
		$ownedClubs = $this->Club->find('count', array('conditions' => array('Club.user_id' => $this->Auth->user('id'))));

		if ($ownedClubs > 0){
			$this->Session->setFlash(__('Club owners cannot act as player agents.'));
			$this->redirect($this->referer());
		}
		
		//Check that the user hasn't already made an offer for this player (if he has redirect to change offer so he can change his current offer)
		$currentOffers = $this->AgentContract->find('first', array('conditions' => array('AgentContract.user_id' => $this->Auth->user('id'), 'AgentContract.person_id' => $id, 'AgentContract.accepted' => NULL)));
		
		if (isset($currentOffers['AgentContract']['id'])){
			$this->redirect('/AgentContract/changeoffer/' . $currentOffers['AgentContract']['id']);
		}
		
		if ($this->RequestHandler->isPost()){
			
			//Check that values are between 0 and 50
			if($this->data['AgentContract']['Wage'] > -1 && $this->data['AgentContract']['Wage'] < 51 && $this->data['AgentContract']['Sign on fee'] > -1 && $this->data['AgentContract']['Sign on fee'] < 51){
				//Create new contract
				$this->AgentContract->create();
				$this->AgentContract->set('user_id', $this->Auth->user('id'));
				$this->AgentContract->set('person_id', $id);
				$this->AgentContract->set('offered', date('Y-m-d'));
				$this->AgentContract->set('percent_wage', $this->data['AgentContract']['Wage']);
				$this->AgentContract->set('percent_fee', $this->data['AgentContract']['Sign on fee']);
				$this->AgentContract->save($this->AgentContract->data);
					
				$this->Session->setFlash(__('Offer sent.'));
				$this->redirect(array('controller' => 'users', 'action' => 'office'));
			}
			else{
				$this->Session->setFlash(__('Validation error. Please try again.'));
				$this->redirect($this->referer());
			}

		}
	}
	
	
	function changeoffer($id = null){
		
		//Load contract
		$this->AgentContract->read(null, $id);
		$contract = $this->AgentContract->data;
		$this->set('contract', $contract);
		
		//Load person
		$this->loadModel('Person');
		$this->Person->recursive=-1;
		$person = $this->Person->find('first', array('conditions' => array('Person.id' => $contract['AgentContract']['person_id'])));
		$this->set('person', $person);
		
		//Check that player exists
		if (!isset($person['Person'])){
			$this->Session->setFlash(__('Player not found.'));
			$this->redirect($this->referer());
		}
		
		//Check that player does not already have an agent
		elseif ($person['Person']['user_id'] != 99){
			$this->Session->setFlash(__('Player already has an agent.'));
			$this->redirect($this->referer());
		}
		
		//Only the user who offerede the contract can change it
		if($this->Auth->user('id') != $contract['AgentContract']['user_id']){
			$this->Session->setFlash(__('This contract offer was not created by you and you cannot change it.'));
			$this->redirect($this->referer());
		}
			
		if ($this->RequestHandler->isPost()){

			//Check that values are between 0 and 50
			if($this->data['AgentContract']['Wage'] > -1 && $this->data['AgentContract']['Wage'] < 51 && $this->data['AgentContract']['Sign on fee'] > -1 && $this->data['AgentContract']['Sign on fee'] < 51){
				
				//Change the contract
				$this->AgentContract->set('offered', date('Y-m-d'));
				$this->AgentContract->set('percent_wage', $this->data['AgentContract']['Wage']);
				$this->AgentContract->set('percent_fee', $this->data['AgentContract']['Sign on fee']);
				$this->AgentContract->save($this->AgentContract->data);
					
				$this->Session->setFlash(__('Offer changed.'));
				$this->redirect(array('controller' => 'users', 'action' => 'office'));
			}
			else{
				$this->Session->setFlash(__('Validation error. Please try again.'));
				$this->redirect($this->referer());
			}

		}
			
	
	}
		
	
}
?>