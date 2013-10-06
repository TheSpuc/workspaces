<?php

/**
 * Contracts Controller
 *
 * @var Contracts Controller
 */

class ContractsController extends AppController
{
	var $name = 'Contracts';

	public $helpers = array('Js', 'Html', 'Paginator');
	
	public $components = array('RequestHandler', 'Message');
	
	public $paginate = array(
	        'limit' => 25,
	        'order' => array(
	            'Contract.startdate' => 'desc'
			),
			'conditions' => array('Contract.acceptedbyplayer' => true, 'OR' => array('Contract.club_id <> Contract.from_club', 'Contract.from_club IS NULL'))
	);
	
	public function transfers() {
				
		//Paginate all transfers		
		$data = $this->paginate('Contract');
    	$this->set('data', $data);
    	
    	$this->set('toptransfers', 
    		$this->Contract->find(
    		'all', array(
	    		'conditions' => array('Contract.transferfee >' => 0, 'Contract.acceptedbyplayer' => true),
	    		'order' => 'Contract.transferfee DESC',
	    		'limit' => 10
    			)
    		)
    	);
    	
    	$this->set('topwages', $this->Contract->find(
    		'all', array( 
	    		'conditions' => array('Contract.weeklywage >' => 0, 'Contract.acceptedbyplayer' => true),
	    		'order' => 'Contract.weeklywage DESC',
	    		'limit' => 10
    			)
    		)
    	);
	}
	
	
	
	//Kun klubben der har sendt tilbuddet afsted kan trække det tilbage (og kun hvis kontrakten ikke er underskrevet)
	function withdrawoffer($id = null){
		
		//Hent kontrakten
		$this->Contract->read(null, $id);
		$contract = $this->Contract->data;
		
		if($this->Auth->user('id') == $contract['Club']['user_id'] && $contract['Contract']['acceptedbyplayer'] != 1){
			//Slet kontrakttilbuddet
			$this->Contract->delete($id);
			$this->Session->setFlash(__('Offer withdrawn.'));
		}
		else{
			$this->Session->setFlash(__('Cannot withdraw offer.'));
		}
		
		$this->redirect($this->referer());
	}
	
	//Kun personen der har modtaget tilbuddet kan sige nej (og kun hvis kontrakten ikke er underskrevet)
	function rejectcontract($id = null){
	
		//Hent kontrakten
		$this->Contract->read(null, $id);
		$contract = $this->Contract->data;
		
		if($this->Auth->user('id') == $contract['Person']['user_id'] && $contract['Contract']['acceptedbyplayer'] != 1){
			//Slet kontrakttilbuddet
			$this->Contract->delete($id);
			$this->Session->setFlash(__('Contract offer rejected.'));
			
			$this->Message->sendMessage('Contract offer rejected', 99, $contract['Club']['user_id'], $contract['Person']['firstname'] . ' ' . $contract['Person']['lastname'] . ' has rejected your contract offer.');
		}
		else{
			$this->Session->setFlash(__('Cannot reject offer.'));
		}
		
		$this->redirect($this->referer());
	}
	
	
	//Reject til klub
	function rejectoffer($id = null){

		//Hent kontrakten
		$this->Contract->read(null, $id);
		$contract = $this->Contract->data;

		//Hent spillerens nuværende kontrakt
		$activeContract = $this->Contract->find('first',
		array(
						'conditions' => array('Contract.person_id' => $contract['Person']['id'],
											'Contract.acceptedbyclub' => true,
											'Contract.acceptedbyplayer' => true,
											'Contract.offered' => true,
											'Contract.enddate >' => 'now()')
		));
		
		if($this->Auth->user('id') == $activeContract['Club']['user_id'] && $contract['Contract']['acceptedbyplayer'] != 1 && $contract['Contract']['acceptedbyclub'] != 1){
			//Slet kontrakttilbuddet
			$this->Contract->delete($id);
			$this->Session->setFlash(__('Transfer offer rejected.'));
		}
		else{
			$this->Session->setFlash(__('Cannot reject offer.'));
		}

		$this->redirect($this->referer());
	}
	
	//Accept til klub
	function acceptoffer($id = null){

		//Hent kontrakten
		$this->Contract->read(null, $id);
		$contract = $this->Contract->data;

		//Hent spillerens nuværende kontrakt
		$activeContract = $this->Contract->find('first',
		array(
						'conditions' => array('Contract.person_id' => $contract['Person']['id'],
											'Contract.acceptedbyclub' => true,
											'Contract.acceptedbyplayer' => true,
											'Contract.offered' => true,
											'Contract.enddate >' => 'now()')
		));
		
		if($this->Auth->user('id') == $activeContract['Club']['user_id'] && $contract['Contract']['acceptedbyplayer'] != 1 && $contract['Contract']['acceptedbyclub'] != 1){
			//Accepter tilbuddet
			$this->Contract->read(null, $contract['Contract']['id']);
			$this->Contract->set('acceptedbyclub', true);
			$this->Contract->save($this->Contract->data);
			
			$this->Session->setFlash(__('Transfer offer accepted.'));
		}
		else{
			$this->Session->setFlash(__('Cannot reject offer.'));
		}


		$this->redirect($this->referer());
	}
	
	function negotiate($id = null){
		//Hent kontrakten
		$this->Contract->read(null, $id);
		$contract = $this->Contract->data;
		$this->set('contract', $this->Contract->data);
		
		$this->set('showform', 0); //showform = hvilken form skal vises paa siden
		
		if (!isset($contract['Contract']['id'])){
			echo 'Contract doesn\'t exist!.';
		}
		elseif ($contract['Contract']['acceptedbyplayer'] == 1){
			echo 'This contract has already been signed by all parties and cannot be negotiated.';
		}
		else{
			
			//Hent spillerens nuværende kontrakt
			$activeContract = $this->Contract->find('first',
			array(
				'conditions' => array('Contract.person_id' => $contract['Person']['id'],
									'Contract.acceptedbyclub' => true,
									'Contract.acceptedbyplayer' => true,
									'Contract.offered' => true,
									'Contract.enddate >' => 'now()')
			));
			$this->set('activeContract', $activeContract);
			
			if ($contract['Person']['npc'] == 1 && 1==2){
				$suggwage = ceil($contract['Person']['age'] / 20);
			}
			else{
				$suggwage = $this->getSuggestedWage($contract['Person']['id'], $contract['Club']['league_id']);
			}
			$this->set('suggwage', $suggwage);
			
			$upd = 0; //upd = hvad skal opdateres
			
			//Hvis kontrakten lige nu venter på klubben der vil have spilleren
			if ($contract['Contract']['offered'] == 0 && $contract['Contract']['acceptedbyclub'] == 1){
			
			    //Kun klubbens ejer kan forhandle
				if ($this->Auth->user('id') == $contract['Club']['user_id']){
				 
					//Hvis der sendes et ændret tilbud tilbage til spilleren
					if(isset($_POST['subm'])){
						$offerClub = "<a href='/clubs/clubdetails/". $contract['Club']['id'] . "'>". $contract['Club']['clubname'] . "</a>.";
						//$html->link($contract['Club']['clubname'],array('plugin' => null, 'controller' => 'clubs', 'action' => 'clubdetails', $contract['Club']['id']));
						$offerContract = "<a href='/contracts/negotiate/". $contract['Contract']['id'] . "'>here</a>";
						//$html->link('here', array('plugin' => null, 'controller' => 'contracts', 'action' => 'contractdetails', $contract['Contract']['id']));
						$offered = 'true';
						$msgrecp = $contract['Person']['user_id'];
						$subject = 'Contract negotiation';
						$msgbody = $contract['Person']['firstname'] . ' ' . $contract['Person']['lastname'] . ' has received a new contract offer from ' . $offerClub . 
						' Please find the negotiated offer ' . $offerContract;
						
						$info = 'The new offer has been sent to ' . $contract['Person']['firstname'] . ' ' . $contract['Person']['lastname'] . '.';
		              
		                $upd = 1;
					}
				
					//Vis kontrakten
				    $this->set('showform', 1);
			     }
			}
		
            //Hvis kontrakten lige nu venter på klubben der vil have spilleren og spillerens nuværende klub har forhandlet
            elseif ($contract['Contract']['offered'] == 0 && $contract['Contract']['acceptedbyclub'] == 0){
		
				//Kun klubbens ejer kan forhandle
				if ($this->Auth->user('id') == $contract['Club']['user_id']){
				 
					//Hvis der sendes et ændret tilbud tilbage til den nuværende klub
					if(isset($_POST['subm'])){
			
						if(!isset($_POST['transferfee'])){
							$_POST['transferfee']=0;
						}
						$offerClub = "<a href='/clubs/clubdetails/". $contract['Club']['id'] . "'>". $contract['Club']['clubname'] . "</a>";
						$offerContract = "<a href='/contracts/negotiate/". $contract['Contract']['id'] . "'>here</a>";
 						$msgrecp = $activeContract['Club']['user_id'];
 						$subject = 'Transfer negotiation';
 						$msgbody = $offerClub . ' have made a new offer for ' . $contract['Person']['firstname'] . ' ' . $contract['Person']['lastname'] . '. Please find the negotiated offer ' . $offerContract;
						$info = 'The new offer has been sent to ' . $activeContract['Club']['clubname'] . '.';
				
						if(isset($activeContract['Contract']['minimumreleaseclause']) && $_POST['transferfee'] > $activeContract['Contract']['minimumreleaseclause'] && $activeContract['Contract']['minimumreleaseclause'] > -1){
							$offerClub = "<a href='/clubs/clubdetails/". $contract['Club']['id'] . "'>". $contract['Club']['clubname'] . "</a>";
 							$subject = 'Release clause met';
 	                        $msgbody = $offerClub . ' have made a new offer for ' . $contract['Person']['firstname'] . ' ' . $contract['Person']['lastname'] . ' that meets the players current minimum release clause. ' . $offerClub . ' have therefore been allowed to discuss personal terms with ' . $contract['Person']['firstname'] . ' ' . $contract['Person']['lastname'] . '.';
	                        $info = 'Your offer has met the players minimum release clause and contract offer has been sent to ' . $contract['Person']['firstname'] . ' ' . $contract['Person']['lastname'] . '.';
						}
				
				        $upd = 2;
					}
					
				    //Vis kontrakten
				    $this->set('showform', 2);
				}
			}
			
            //Hvis kontrakten lige nu venter på klubben der har spilleren
            elseif ($contract['Contract']['offered'] == 1 && $contract['Contract']['acceptedbyclub'] == 0){
			
                 //Kun klubbens ejer kan forhandle
                 if ($this->Auth->user('id') == $activeContract['Club']['user_id']){
                                    
					//Hvis der sendes et ændret tilbud tilbage til den nuværende klub
					if(isset($_POST['subm'])){
					
						if(!isset($_POST['transferfee'])){
							$_POST['transferfee']=0;
						}
						$offerClub = "<a href='/clubs/clubdetails/". $activeContract['Club']['id'] . "'>". $activeContract['Club']['clubname'] . "</a>";
						$offerContract = "<a href='/contracts/negotiate/". $contract['Contract']['id'] . "'>here</a>";
						$accept = "<a href='/contracts/acceptoffer/". $activeContract['Contract']['id'] . "'>accept</a>";
						$reject = "<a href='/contracts/rejectoffer/". $activeContract['Contract']['id'] . "'>reject</a>";
 						$msgrecp = $contract['Club']['user_id'];
 						$subject = 'Transfer negotiation';
 						$msgbody = $offerClub . ' have made a counter offer for ' . $contract['Person']['firstname'] . ' ' . $contract['Person']['lastname'] . '. Please find the negotiated offer ' . $offerContract;
//						. '<br/>' . $accept . ' ' . $reject;
						$info = 'The negotiated offer has been sent to ' . $contract['Club']['clubname'] . '.';
				
						$upd = 3;
					}
			
					//Vis kontrakten
					$this->set('showform', 2);
				}
			}
			
			//Hvis kontrakten lige nu venter på spilleren
			elseif ($contract['Contract']['offered'] == 1 && $contract['Contract']['acceptedbyclub'] == 1){
				//Kun spillerens ejer kan forhandle
				if ($this->Auth->user('id') == $contract['Person']['user_id']){
	
				if(isset($_POST['signed'])){
	
					//Hvis der sendes et ændret tilbud tilbage til klubben
					if ($_POST['signed'] == "0"){
						$offerPerson = "<a href='/person/persondetails/". $contract['Person']['id'] . "'>". $contract['Person']['firstname'] . " " . $contract['Person']['lastname'] . "</a>";
						$offerContract = "<a href='/contracts/negotiate/". $contract['Contract']['id'] . "'>here</a>";
						$offered = 'false';
 						$msgrecp = $contract['Club']['user_id'];
 			            $subject = 'Contract negotiation';
			            $msgbody = $offerPerson . ' has decided to negotiate your contract offer. Please find the negotiated offer ' . $offerContract;
						$info = 'Your counter offer has been sent to ' . $contract['Club']['clubname'] . '.';
						
						$upd = 1;
					}
					elseif ($_POST['signed'] == "-1"){
			 
						// Slet kontrakttilbuddet
						$this->Contract->delete($id);
						$offerPerson = "<a href='/person/persondetails/". $contract['Person']['id'] . "'>". $contract['Person']['firstname'] . " " . $contract['Person']['lastname'] . "</a>";
						$this->Message->sendMessage('Contract offer declined', 99, $contract['Club']['user_id'], $offerPerson . ' has rejected your contract offer.');
			
						$this->Session->setFlash(__('Contract offer declined.'));
					    $this->redirect(array('controller' => 'person', 'action' => 'persondetails/' . $contract['Person']['id']));
					}
					elseif ($_POST['signed'] == "1"){
						//Hvis kontrakten skrives under
						
						//Start transaction
			  			$dataSource = ConnectionManager::getDataSource('default');
			  			$dataSource->begin();
			
			  			try{
			  				
							//sæt nuværende kontrakter til at stoppe nu
			  				$this->Contract->query('UPDATE contracts SET enddate = now() WHERE enddate > now() AND acceptedbyplayer=\'t\' AND person_id = ' . $contract['Person']['id']);
			                
			                //Hvilken sæson er vi i gang med?
			  				$this->loadModel('Season');
			  				$season = $this->Season->find('first', array('conditions' => array('Season.firstday <' => 'now()', 'Season.lastday >=' => date('Y-m-d'))));
			  				
			  				
			  				
							//Start kontrakt nu
			  				$this->Contract->read(null, $id);
			  				$this->Contract->set('startdate', date('Y-m-d'));
			  				$this->Contract->set('acceptedbyplayer', true);
			  				if (isset($activeContract['Club']['id'])){
			  					$this->Contract->set('from_club', $activeContract['Club']['id']);
			  					$this->Contract->set('from_league', $activeContract['Club']['league_id']);
			  				}
			  				$this->Contract->set('to_league', $contract['Club']['league_id']);
			  				$this->Contract->set('season_id', $season['Season']['id']);
			  				$this->Contract->save($this->Contract->data);

			  				$this->loadModel('ClubExpense');
			  				
			  				//Betal transfer fee og sign on fee

			  				//Hvis der skal betales transfer fee
							if ($contract['Contract']['transferfee'] > 0){
	
																
								//Registrer i regnskabet
								$this->ClubExpense->create();
								$this->ClubExpense->set('amount', $contract['Contract']['transferfee']);
								$this->ClubExpense->set('type', 4);
								$this->ClubExpense->set('date', date('Y-m-d'));
								$this->ClubExpense->set('description', 'Transfer fee');
								$this->ClubExpense->set('club_id', $contract['Club']['id']);
								$this->ClubExpense->save($this->ClubExpense->data);

								if (isset($activeContract['Club']['id'])){
									//Registrer i regnskabet (tidligere klub)
									$this->loadModel('ClubIncome');
									$this->ClubIncome->create();
									$this->ClubIncome->set('amount', $contract['Contract']['transferfee']);
									$this->ClubIncome->set('type', 4);
									$this->ClubIncome->set('date', date('Y-m-d'));
									$this->ClubIncome->set('description', 'Transfer fee');
									$this->ClubIncome->set('club_id', $activeContract['Club']['id']);
									$this->ClubIncome->save($this->ClubIncome->data);
									
								}
							}

							//Og giv til spilleren hvis der er sign on fee
							if ($contract['Contract']['signonfee'] > 0){
								
								//Giv til spilleren
								$this->Contract->Person->read(null, $contract['Person']['id']);
								$this->Contract->Person->set('money', $contract['Person']['money'] + $contract['Contract']['signonfee']);
								$this->Contract->Person->save($this->Contract->Person->data);
								
								//Registrer udgiften
								$this->ClubExpense->create();
								$this->ClubExpense->set('amount', $contract['Contract']['signonfee']);
								$this->ClubExpense->set('type', 5);
								$this->ClubExpense->set('date', date('Y-m-d'));
								$this->ClubExpense->set('description', 'Sign on fee');
								$this->ClubExpense->set('club_id', $contract['Club']['id']);
								$this->ClubExpense->save($this->ClubExpense->data);
							}
	
							//Slet andre tilbud der ikke er accepteret
							$this->Contract->query('DELETE FROM contracts WHERE acceptedbyplayer=\'f\' AND person_id = ' . $contract['Person']['id']);
	
							//hent første ledige trøjenummer
							$shirtNo = $this->Contract->query("select COALESCE(min(p.shirtnumber+1), 1) as number from persons p inner join contracts c on c.person_id=p.id where enddate>now() and club_id=" . $contract['Club']['id'] . " and (select count(*) from persons q inner join contracts c on c.person_id=q.id where enddate>now() and club_id=" . $contract['Club']['id'] . " and shirtnumber=p.shirtnumber+1)=0 and p.shirtnumber+1<100");
	
							//Tildel første ledige trøjenummer
							$this->Contract->Person->read(null, $contract['Person']['id']);
							$this->Contract->Person->set('shirtnumber', $shirtNo[0][0]['number']);
							$this->Contract->Person->save($this->Contract->Person->data);
							$offerPerson = "<a href='/person/persondetails/". $contract['Person']['id'] . "'>". $contract['Person']['firstname'] . " " . $contract['Person']['lastname'] . "</a>";
							$offerClub = "<a href='/clubs/clubdetails/". $contract['Club']['id'] . "'>". $contract['Club']['clubname'] . "</a>";
							//privatemsg_new_thread(array(user_load($contract['Club']['user_id'])), $lname . ' accepts contract', $fname . ' ' . $lname . ' has accepted your contract offer. He has joined your squad and is available for immediate selection.', array('author' => user_load(99)));
							$this->Message->sendMessage($contract['Person']['lastname'] . ' accepts contract', 99, $contract['Club']['user_id'], $offerPerson . ' has accepted your contract offer. He has joined your squad and is available for immediate selection.');
 							
 							//Hvis der findes en tidligere klub og det ikke er den samme som den "nye" klub sendes der en besked til ejer af tidligere klub
 							if (isset($activeContract['Club']['user_id']) && $activeContract['Club']['user_id'] != $contract['Club']['user_id']){
// 								privatemsg_new_thread(array(user_load($prevClubOwner)), $lname . ' leaves club', $fname . ' ' . $lname . ' has accepted a contract offer from ' . $clubname . '. The agreed transfer fee of ' .  $transferfee . ' has been paid.', array('author' => user_load(99)));
 								$this->Message->sendMessage($contract['Person']['lastname'] . ' leaves club', 99, $activeContract['Club']['user_id'], $offerPerson . ' has accepted a contract offer from ' . $offerClub . '. The agreed transfer fee of ' .  $contract['Contract']['transferfee'] . ' has been paid.');
 							}
					
							$this->Session->setFlash(__('Contract offer accepted.'));
							
						} catch (Exception $e) {
							$dataSource->rollback();
							debug($e);
						}
							
						$dataSource->commit();
						
						$this->Session->delete('Clubs');
						$this->redirect(array('controller' => 'person', 'action' => 'persondetails/' . $contract['Person']['id']));
					}
				}

				//Vis kontrakten
				$this->set('showform', 1);
			}
		}
			
		if ($upd == 1){
           	if(!isset($_POST['releaseclause'])){
           		$_POST['releaseclause']=-1;
           	}
            if(!isset($_POST['assistbonus'])){
				$_POST['assistbonus']=0;
			}
			if(!isset($_POST['goalbonus'])){
				$_POST['goalbonus']=0;
			}
			if(!isset($_POST['transferfee'])){
				$_POST['transferfee']=0;
			}
			if(!isset($_POST['signonfee'])){
				$_POST['signonfee']=0;
			}
			if(!isset($_POST['wage'])){
				 $_POST['wage']=0;
           	}

			$minimumreleaseclause=-1;
			$assistbonus=-1;
			$goalbonus=-1;

           	if (isset($_POST['withclause'])){
               $minimumreleaseclause=$_POST['releaseclause'];
          	}

			$assistbonus=$_POST['assistbonus'];
          	$goalbonus=$_POST['goalbonus'];

          	//Opdater kontrakttilbuddet
			$this->Contract->read(null, $id);
          	$this->Contract->set('enddate', $_POST['postdate']);
          	$this->Contract->set('weeklywage', $_POST['wage']);
          	$this->Contract->set('goalbonus', $_POST['goalbonus']);
          	$this->Contract->set('assistbonus', $_POST['assistbonus']);
          	$this->Contract->set('minimumreleaseclause', $minimumreleaseclause);
          	$this->Contract->set('offered', $offered);
          	$this->Contract->set('signonfee', $_POST['signonfee']);
          	$this->Contract->set('dateoffered', date("Y-m-d"));
          	$this->Contract->save($this->Contract->data);
          	
//         	privatemsg_new_thread(array(user_load($msgrecp)), $subject, $msgbody, array('author' => user_load(99)));
          	$this->Message->sendMessage($subject, 99, $msgrecp, $msgbody);
          	
          	$this->Session->setFlash(__($info));
          	$this->redirect(array('controller' => 'person', 'action' => 'persondetails/' . $contract['Person']['id']));
          	
       	}
   		elseif ($upd == 2){

         	if($_POST['transferfee'] > $activeContract['Contract']['minimumreleaseclause'] && $activeContract['Contract']['minimumreleaseclause'] > -1){
              	//Opdater kontrakttilbuddet
              	$this->Contract->set('transferfee', $_POST['transferfee']);
              	$this->Contract->set('offered', true);
              	$this->Contract->set('acceptedbyclub', true);
              	$this->Contract->set('dateoffered', date("Y-m-d"));
              	$this->Contract->save($this->Contract->data);
			}
			else{
				//Opdater kontrakttilbuddet
				$this->Contract->set('transferfee', $_POST['transferfee']);
				$this->Contract->set('offered', true);
				$this->Contract->set('acceptedbyclub', false);
				$this->Contract->set('dateoffered', date("Y-m-d"));
				$this->Contract->save($this->Contract->data);
			}

			// 			privatemsg_new_thread(array(user_load($msgrecp)), $subject, $msgbody, array('author' => user_load(99)));
			$this->Message->sendMessage($subject, 99, $msgrecp, $msgbody);
			
			$this->Session->setFlash(__($info));
			$this->redirect(array('controller' => 'person', 'action' => 'persondetails/' . $contract['Person']['id']));
		}
		elseif ($upd == 3){
		
			//Opdater kontrakttilbuddet
			$this->Contract->set('transferfee', $_POST['transferfee']);
			$this->Contract->set('offered', false);
			$this->Contract->set('acceptedbyclub', false);
			$this->Contract->set('dateoffered', date("Y-m-d"));
			$this->Contract->save($this->Contract->data);
			
// 			privatemsg_new_thread(array(user_load($msgrecp)), $subject, $msgbody, array('author' => user_load(99)));
			$this->Message->sendMessage($subject, 99, $msgrecp, $msgbody);
			
			$this->Session->setFlash(__($info));
			$this->redirect(array('controller' => 'person', 'action' => 'persondetails/' . $contract['Person']['id']));
		}
	}
}
	
	function newoffer($id = null){
		
		//Hent brugerens klub
		$this->Contract->Club->recursive=-1;
		$ownedClub = $this->Contract->Club->find('first',
		array(
				'conditions' => array('Club.user_id' => $this->Auth->user('id'))
		));
		$this->set('ownedClub', $ownedClub);
		
		//Hent person
		$person = $this->Contract->Person->find('first',
		array(
				'conditions' => array('Person.id' => $id)
		));
		$this->set('person', $person);
		
		if ($person['Person']['npc'] == 1 && 1==2){
			$suggwage = ceil($person['Person']['age'] / 20);
		}
		else{
			$suggwage = $this->getSuggestedWage($id, $ownedClub['Club']['league_id']);
		}
		$this->set('suggwage', $suggwage);
		
		//Hent personens nuværende klub
		//$this->Person->ActiveContract->Club->recursive = -1;
		$currentClub = $this->Contract->Person->ActiveContract->Club->find('first', array('conditions' => array(
					'Club.id' => $person['ActiveContract']['club_id']
		)));
		$this->set('currentClub', $currentClub);
		
		
		if ($this->RequestHandler->isPost() && isset($_POST['submit']) && isset($ownedClub['Club'])){
		
			if(!isset($_POST['releaseclause'])){
				$_POST['releaseclause']=-1;
			}
			if(!isset($_POST['assistbonus'])){
				$_POST['assistbonus']=0;
			}
			if(!isset($_POST['goalbonus'])){
				$_POST['goalbonus']=0;
			}
			if(!isset($_POST['transferfee'])){
				$_POST['transferfee']=0;
			}
			if(!isset($_POST['signonfee'])){
				$_POST['signonfee']=0;
			}
			if(!isset($_POST['wage'])){
				$_POST['wage']=0;
			}
		
			$minimumreleaseclause=-1;
			$assistbonus=-1;
			$goalbonus=-1;
			if($_POST['role'] == 1){
				if (isset($_POST['withclause'])){
					$minimumreleaseclause=$_POST['releaseclause'];
				}
				$assistbonus=$_POST['assistbonus'];
				$goalbonus=$_POST['goalbonus'];
			}
		
			$transferfee=$_POST['transferfee'];
			$acceptedbyclub='false';
			if(!isset($currentClub['Club']) || $currentClub['Club']['id'] == $ownedClub['Club']['id']){
				//Personer uden kontrakt skal ikke have tilbud accepteret af klub
				$acceptedbyclub = true;
				$transferfee=0;
			}
			else{
				//Hvis personen er tilknyttet en klub men release clause er opfyldt er tilbuddet automatisk accepteret
				if($_POST['transferfee'] >= $person['ActiveContract']['minimumreleaseclause'] && $person['ActiveContract']['minimumreleaseclause'] > -1){
					$acceptedbyclub = true;
				}
			}
		
			
			//Opret kontrakt(-tilbud)
			$this->Contract->create();
			$this->Contract->set('club_id', $ownedClub['Club']['id']);
			$this->Contract->set('person_id', $id);
			$this->Contract->set('enddate', $_POST['postdate']);
			$this->Contract->set('weeklywage', $_POST['wage']);
			$this->Contract->set('goalbonus', $_POST['goalbonus']);
			$this->Contract->set('assistbonus', $_POST['assistbonus']);
			$this->Contract->set('minimumreleaseclause', $minimumreleaseclause);
			$this->Contract->set('offered', true);
			$this->Contract->set('acceptedbyplayer', false);
			$this->Contract->set('acceptedbyclub', $acceptedbyclub);
			$this->Contract->set('role', $_POST['role']);
			$this->Contract->set('signonfee', $_POST['signonfee']);
			$this->Contract->set('transferfee', $_POST['transferfee']);
			$this->Contract->set('dateoffered', date("Y-m-d"));
			$this->Contract->save($this->Contract->data);
			
			$offerClub = "<a href='/clubs/clubdetails/" . $ownedClub['Club']['id'] . "'>" . $ownedClub['Club']['clubname'] . "</a>";
			$offerPerson = "<a href='/person/persondetails/" . $person['Person']['id'] . "'>" . $person['Person']['firstname'] . " " . $person['Person']['lastname'] . "</a>";
			$offerContract = "<a href='/contracts/negotiate/" . $this->Contract->id . "'>here</a>";
			//Send beskeder til folk
			if ($acceptedbyclub == 'true'){				
				if($_POST['transferfee'] >= $person['ActiveContract']['minimumreleaseclause'] && $person['ActiveContract']['minimumreleaseclause'] > -1){
					//privatemsg_new_thread(array(user_load($currentclubowner)), 'New transfer offer', '<a href="?q=node/7&id=' . $ownedclubid . '">' . $ownedclubname . '</a> have made an offer of ' . $_POST['transferfee'] . ' for <a href="?q=node/4&id=' . $personid . '">' . $personname  . '</a>. The offer has been accepted since it meets the players minimum fee release clause of ' . $currentclubreleaseclause . '.', array('author' => user_load(99)));
					$this->Message->sendMessage('New transfer offer accepted', 99, $currentClub['Club']['user_id'], $offerClub . ' have made an offer of ' . $_POST['transferfee'] . ' for ' . $offerPerson  . '. The offer has been accepted since it meets the players minimum fee release clause of ' . $person['ActiveContract']['minimumreleaseclause'] . '.');
				}
		
				if($person['Person']['user_id'] != 99){
					//privatemsg_new_thread(array(user_load($personuid)), 'New contract offer', '<a href="?q=node/7&id=' . $ownedclubid . '">' . $ownedclubname . '</a> have offered a contract to <a href="?q=node/4&id=' . $personid . '">' . $personname  . '</a>.', array('author' => user_load(99)));
					$this->Message->sendMessage('New contract offer', 99, $person['Person']['user_id'], $offerClub . ' have offered a contract to ' . $offerPerson  . '. View the offer ' . $offerContract);
				}
			}
			else{
				//privatemsg_new_thread(array(user_load($currentclubowner)), 'New transfer offer', '<a href="?q=node/7&id=' . $ownedclubid . '">' . $ownedclubname . '</a> have made an offer of ' . $_POST['transferfee'] . ' for <a href="?q=node/4&id=' . $personid . '">' . $personname  . '</a>.', array('author' => user_load(99)));
				$this->Message->sendMessage('New transfer offer', 99, $currentClub['Club']['user_id'], $offerClub . ' have made an offer of ' . $_POST['transferfee'] . ' for ' . $offerPerson . '. You can accept the offer from your office or view the offer ' . $offerContract);
			}
		
			$this->Session->setFlash(__('Offer has been sent.'));
		    $this->redirect(array('controller' => 'clubs', 'action' => 'clubdetails/' . $ownedClub['Club']['id']));		
		}		
	}
	
	
	function contractdetails($id = null){
		$result = $this->Contract->find(
			'first',
			array(
				'conditions' => array('Contract.id' => $id)
    		));
  				
  		$this->set('contract', $result);		
		
  		$this->set('userOwnsClub', $this->Auth->user('id') == $result['Club']['user_id']);
  		$this->set('userOwnsPerson', $this->Auth->user('id') == $result['Person']['user_id']);
  		
  		//Find resterende løn
  		$date_parts1=explode("-", date("Y-m-d"));
  		$date_parts2=explode("-", $result['Contract']['enddate']);
  		
  		//gregoriantojd() Converts a Gregorian date to Julian Day Count
  		$start_date=gregoriantojd($date_parts1[1], $date_parts1[2], $date_parts1[0]);
  		$end_date=gregoriantojd($date_parts2[1], $date_parts2[2], $date_parts2[0]);
  		$remainingDays = $end_date - $start_date;
  		
  		$remainingWages = $result['Contract']['weeklywage'] * $remainingDays;
  		$this->set('remainingWages', $remainingWages);
  		
  		//Hvis kontrakten skal stoppes
  		if ($this->RequestHandler->isPost() && $this->Auth->user('id') == $result['Club']['user_id'] && isset($_POST['rlaccept']) && strtotime($result['Contract']['enddate']) > strtotime(date('Y-m-d'))){
  			
  			//Start transaction
  			$dataSource = ConnectionManager::getDataSource('default');
  			$dataSource->begin();
  			
  			$npc = '.';
  			try {

  				
		    if ($result['Person']['npc'] == 1){
		    
		    }
		    else{
		    
	            if ($remainingWages > 0){
	            	
		            //tage penge fra klubben
	  				
					$this->loadModel('ClubExpense');
					$this->ClubExpense->create();
					$this->ClubExpense->set('amount', $remainingWages);
					$this->ClubExpense->set('type', 3);
					$this->ClubExpense->set('date', date('Y-m-d'));
					$this->ClubExpense->set('description', 'Contract termination');
					$this->ClubExpense->set('club_id', $result['Club']['id']);
					$this->ClubExpense->save($this->ClubExpense->data);
	
		            //giv penge til personen
		            $this->Contract->Person->read(null, $result['Person']['id']);
		            $this->Contract->Person->set('money', $result['Person']['money'] + $remainingWages);
					$this->Contract->Person->save($this->Contract->Person->data);
				}
			
				if ($result['Person']['user_id'] > 0){
					$offerClub = "<a href='/clubs/clubdetails/" . $result['Club']['id'] . "'>" . $result['Club']['clubname'] . "</a>";
					$offerPerson = "<a href='/person/persondetails/" . $result['Person']['id'] . "'>" . $result['Person']['firstname'] . " " . $result['Person']['lastname'] . "</a>";
					$npc = '. Since ' . $offerPerson . ' is an npc, no further wages will be paid.';
					if($result['Person']['npc'] == false){
						$npc = ' and remaining wages of ' . $remainingWages . ' have been paid.';
					}
					$this->Message->sendMessage($this->Contract->Person->data['lastname'] . ' released from club', 99, $result['Person']['user_id'], $offerPerson . ' has been released from his contract to ' . $offerClub . $npc);
				}
		    }

			//Afslut kontrakten
			$this->Contract->read(null, $result['Contract']['id']);
		    $this->Contract->set('enddate', date('Y-m-d'));
			$this->Contract->save($this->Contract->data);
			
			$offerClub = "<a href='/clubs/clubdetails/" . $result['Club']['id'] . "'>" . $result['Club']['clubname'] . "</a>";
			$offerPerson = "<a href='/person/persondetails/" . $result['Person']['id'] . "'>" . $result['Person']['firstname'] . " " . $result['Person']['lastname'] . "</a>";
			if($result['Person']['npc'] == false){
				$npc = ' and remaining wages of ' . $remainingWages . ' have been paid.';
			}
			$this->Message->sendMessage($result['Person']['firstname'] . " " . $result['Person']['lastname'] . ' released from club', 99, $result['Club']['user_id'], $offerPerson . ' has been released from his contract to ' . $offerClub . $npc);
			
			$this->Session->setFlash(__('Player has been released.'));
			
		  	} catch (Exception $e) {
  				$dataSource->rollback();
//   				debug($e);
  			}
  			
  			$dataSource->commit();
  				
  			$this->Session->delete('Clubs');
		    $this->redirect(array('controller' => 'clubs', 'action' => 'clubdetails/' . $result['Club']['id']));

  			
  		}
  	}

  	private function getSuggestedWage($id, $league){
  	
  		$query = "SELECT ";
  		$query = $query . "(SELECT AVG(rating) FROM (SELECT rating FROM match_playerstats s INNER JOIN matches m ON s.match_id=m.id WHERE person_id=p.id AND rating > 0 ORDER BY matchdate DESC LIMIT 60) rat)::char(3) as avgrnd ";
  		$query = $query . "FROM persons p ";
  		$query = $query . "WHERE (SELECT count(*) FROM match_playerstats WHERE person_id=p.id AND rating > 0) > 59 ";
  		$query = $query . "AND id=$id ";
  		$query = $query . "UNION ";
  		$query = $query . "SELECT ";
  		$query = $query . "((COALESCE((SELECT SUM(rating) FROM match_playerstats WHERE person_id=p.id AND rating > 0), 0) + (SELECT 60 - COUNT(*) FROM match_playerstats WHERE person_id=p.id AND rating > 0)) / 60.0)::char(3) as avgrnd ";
  		$query = $query . "FROM persons p ";
  		$query = $query . "WHERE (SELECT count(*) FROM match_playerstats WHERE person_id=p.id AND rating > 0) < 60 ";
  		$query = $query . "AND id=$id";
  		$result = $this->Contract->query($query);
  	
  		$avg = $result[0][0]['avgrnd'];
  		$avgfloored = floor($avg);
  		$decimal = ($avg - $avgfloored) * 10;
  	
  		$season = $this->Contract->query('SELECT max(id) as id FROM seasons');
  	
  		$this->loadModel('WageStat');
  		$wagestats = $this->WageStat->find('all', array('conditions' => array(
  			'league_id' => $league,
  			'season_id' => $season[0][0]['id'],
  			'OR' => array(
  				array('rating' => $avgfloored), 
  				array('rating' => $avgfloored + 1)
  			)
  		)));
  	
  		$minwage = $wagestats[0]['WageStat']['wage'] + (($wagestats[1]['WageStat']['wage'] - $wagestats[0]['WageStat']['wage']) * $decimal / 10);
  		$minwage = floor($minwage);
  	
  		$maxwage = floor($minwage * 1.1);
  	
  		return $minwage . ' - ' . $maxwage;
  	}
}
?>