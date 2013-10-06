<?PHP
App::uses('AppController', 'Controller');
App::uses('CakeEmail', 'Network/Email');
/**
 * Messages Controller
 *
 * @var Messages Controller
 * @property Messages $Messages
 * @property 'Auth'Component $'Auth'
 */
class MessagesController extends AppController {

	public $helpers = array('Js', 'Html', 'AutoComplete', 'JqueryEngine');

	/**
	*Write a new message and send it
	* @user_id 
	*/
	public function write($userId = null) {
		if($this->request->is('post')){
			if(!empty($this->request->data['User']['username'])){
				$user = ClassRegistry::init('User')->find('first', array(
					'conditions' => array(
						'User.username' => $this->request->data['User']['username'],
						'User.username <>' => 'FA'
					),
					'fields' => array('id', 'email'),
					'recursive' => -1
				));
				if(!empty($user)){
					$userId = $user['User']['id'];
					$this->sendMessage($userId, $this->request->data['Message']['subject']);
					//send email to recipient
					$message = "You've got a private message on Footie.<br/>" .
							   "<a href='http://footie-online.com'>Log in</a> to read it<br/><br/>";	
					$this->set('userdata', $user);
					$email = new CakeEmail();
					$email->from(array('info@footie-online.com' => 'Footie-online'));
					$email->to($user['User']['email']);
					$email->replyTo(array('info@footie-online.com' => 'Footie-online'));
					$email->subject('New Private Message (' . $this->request->data['Message']['subject'] . ')');
					$email->emailFormat('html');
					$email->send($message);		
				}
				else{
					$this->Session->setFlash(__('User not found.'));
				}
			}
			
			$this->redirect(array('action' => 'inbox'));
		}
	}
	
	/**
	*Read messages in a thread and reply
	* @thread_id the id of the thread currently being read
	*
	*/
	public function read($threadId = null) {		
		//Hent Message_thread med alle messages som er fra eller til brugeren
		$thread = $this->Message->find('all', array(
			'conditions' => array(
		        	'Message.thread_id' => $threadId
				),
				'order' => 'Message.sent DESC'
			)
		);		
		//Hvis der ikke er nogen messages er tråden ikke til brugeren
		if (empty($thread)){
			$this->Session->setFlash(__('No messages to display.'));
		}
		else{
			$this->set('subject', $thread[0]['Message_thread']['subject']);
			//Har User lov at laese traaden? Er han på from eller to på nogen af messages?
			$allowed = false;
			$i = 0;
			$FA = false; //Er det FA der har skrevet? Så må man ikke svare.
			while($i < count($thread) && $allowed == false){
				if($thread[$i]['Message']['from_user_id'] == $this->Auth->user('id')){ $allowed = true; }
				else if($thread[$i]['Message']['to_user_id'] == $this->Auth->user('id')){ $allowed = true; }
				
				if($thread[$i]['Message']['from_user_id'] == 0 || $thread[$i]['Message']['from_user_id'] == 99){ $FA = true; }
				
				$i = $i + 1;
			}
			if($allowed == true){
				$this->set('thread', $thread);
				$this->set('FA', $FA);
				//Find seneste afsender i tråden som ikke er brugeren selv
				$replyTo = $this->Auth->user('id');
				$i = 0;
				while ($i < count($thread) && $replyTo == $this->Auth->user('id')) {
					$replyTo = $thread[$i]['Message']['from_user_id'];
					$i = $i + 1;
				}
				//Set all messages in thread to read = true
				foreach($thread as $thrd){
					if($thrd['Message']['read'] != true && $thrd['Message']['from_user_id'] != $this->Auth->user('id')){
						$this->Message->id = $thrd['Message']['id'];
						$saveThread['Message']['read'] = true;
						$this->Message->save($saveThread);
					}
				}
				//Send reply
				if($this->request->is('post')){
					if (!empty($this->request->data['Message']['text'])){
						$this->sendMessage($replyTo, $thread[0]['Message_thread']['subject'], $threadId);
						
						//send email to recipient
						$user = ClassRegistry::init('User')->find('first', array(
							'conditions' => array(
								'User.id' => $replyTo,
								'User.username <>' => 'FA'
							),
							'fields' => array('id', 'email'),
							'recursive' => -1
						));
						$message = "You've got a private message on Footie.<br/>" .
									"<a href='http://footie-online.com'>Log in</a> to read it<br/><br/>";	
						$this->set('userdata', $user);
						$email = new CakeEmail();
						$email->from(array('info@footie-online.com' => 'Footie-online'));
						$email->to($user['User']['email']);
						$email->replyTo(array('info@footie-online.com' => 'Footie-online'));
						$email->subject('New Private Message (' . $thread[0]['Message_thread']['subject'] . ')');
						$email->emailFormat('html');
						$email->send($message);	
						
						//deleted should be set to false for all messages with thread id = threadId, or else thread cannot be seen by reciever
						$threadms = $this->Message->find('all', array(
							'conditions' => array(
								'Message.thread_id' => $threadId,
								'OR' => array(
									'Message.from_user_id' => $this->Auth->user('id'),
									'Message.to_user_id' => $this->Auth->user('id')
								),
								'AND' => array(
									array(
										'OR' => array(
											'Message.deleted_to' => true,
											'Message.deleted_from' => true
										)
									)
								)
							),
						'order' => 'Message.sent'
						));
						if(!empty($threadms)){
							$this->set('deleted', $threadms);
							//sæt deleted til false. Da den nye besked skal kunne læses, skal alle beskeder være ikke-deleted, ellers er tråden gemt.
							$this->Message->save(array(
								'Message' => array(
									'id' => $threadms[0]['Message']['id'],
									'deleted_from' => false,
									'deleted_to' => false,
								)
							));
						}
						$this->redirect(array('action' => 'read', $threadId));
					}
					else{
						$this->Session->setFlash(__('You cannot send an empty message.'));
					}
				}
			}
			else{	
				$this->redirect(array('action' => 'inbox'));
				$this->Session->setFlash(__('You are not allowed to read this message'));
			}
		}
	}
	
	private function sendMessage($userId, $subject, $threadId = null){
		if ($threadId == null){
			$this->Message->Message_thread->save(array(
						'Message_thread' => array(
							'subject' => $subject,
							'updated' => 'now()'
				)
			));
			
			$threadId = $this->Message->Message_thread->id;
		}else{
			//Set updated on Message_thread to time of new message (now)
			$this->Message->Message_thread->id = $threadId;
			$this->Message->Message_thread->save(array(
				'Message_thread' => array(
					'updated' => 'now()'
				)
			));
		}
		$this->Message->save(array(
			'Message' => array(
				'thread_id' => $threadId,
				'from_user_id' => $this->Auth->user('id'),
				'to_user_id' => $userId,
				'text' => $this->request->data['Message']['text'],
				'read' => false
			)
		));
	}
	
	public function inbox() {
	//Set messages to be deleted or unread
		if($this->request->is('post')){
			if (!empty($this->request->data['deleted']) || !empty($this->request->data['unread'])){ //for at sikre at else bliver sat det rigtige sted
			if (!empty($this->request->data['deleted'])){
				//the deleted id's we get are actually thread id's. This means we have to find a message with that thread_id and the users id, and make sure that the user is the right to or from compared to the message
				$mesg = null;
				foreach($this->request->data['deleted'] as $mess){
					$deleted = $this->Message->find('all', array(
					'conditions' => array(
							'thread_id' => $mess,
							'OR' => array(
								'Message.from_user_id' => $this->Auth->user('id'),
								'Message.to_user_id' => $this->Auth->user('id')
							)
						),
						'order' => 'Message.sent DESC'
					));
				
					//check if the from_id is the same as user id
					if($deleted[0]['Message']['from_user_id'] == $this->Auth->user('id')){
						$this->Message->save(array(
							'Message' => array(
								'id' => $deleted[0]['Message']['id'],
								'deleted_from' => true								
							)
						));
						$this->Session->setFlash(__('Message(s) deleted'));
					}
					elseif($deleted[0]['Message']['to_user_id'] == $this->Auth->user('id')){
						$this->Message->save(array(
							'Message' => array(
								'id' => $deleted[0]['Message']['id'],
								'deleted_to' => true,
								'read' => true
							)
						));
						$this->Session->setFlash(__('Message(s) deleted'));
					}
				}
			}
			if (!empty($this->request->data['unread'])){
				//the unread id's we get are actually thread id's. This means we have to find a message with that thread_id and the users id, and make sure that the user is the right to or from compared to the message
			}
			}
			else{
				$this->Session->setFlash(__('There is nothing to delete'));
			}
			
		}	
	
		//Hent Message_thread med alle messages som er fra eller til brugeren
		$messages = $this->Message->find('all', array(
			'conditions' => array(
					'OR' => array(
						'Message.from_user_id' => $this->Auth->user('id'),
						'Message.to_user_id' => $this->Auth->user('id')
					)
				),
				'order' => 'Message.sent DESC'
			)
		);
		$thread = array();
		$deleted = array();
		//er beskeden slettet af brugeren?
		foreach($messages as $msg){
			if($msg['Message']['from_user_id'] == $this->Auth->user('id')){
					if($msg['Message']['deleted_from'] == true){ $deleted[$msg['Message']['thread_id']] = $msg['Message']['thread_id']; }
			}
			else if($msg['Message']['to_user_id'] == $this->Auth->user('id')){
					if($msg['Message']['deleted_to'] == true){ $deleted[$msg['Message']['thread_id']] = $msg['Message']['thread_id']; }
			}
		}
		//alle beskeder der ikke er slettet af brugeren, arrangeres efter threads
		foreach($messages as $msg){
			if(!array_key_exists($msg['Message']['thread_id'], $deleted)){
				if(array_key_exists($msg['Message_thread']['id'], $thread)){
					$thread[$msg['Message_thread']['id']]['Messages'][$msg['Message']['id']] = $msg['Message'];
					if($msg['Message']['read'] == false && $msg['Message']['from_user_id'] != $this->Auth->user('id')){
						$thread[$msg['Message_thread']['id']]['read'] = false;
					}
				}else{
					$thread[$msg['Message_thread']['id']] = $msg['Message_thread'];
					$thread[$msg['Message_thread']['id']]['read'] = true;
					if($msg['Message']['read'] == false && $msg['Message']['from_user_id'] != $this->Auth->user('id')){
						$thread[$msg['Message_thread']['id']]['read'] = false;
					}
					if($msg['Message']['from_user_id'] ==$this->Auth->user('id')){
						$thread[$msg['Message_thread']['id']]['participant'] = $msg['To']['username'];
					}
					else{
						$thread[$msg['Message_thread']['id']]['participant'] = $msg['From']['username'];
					}
					$thread[$msg['Message_thread']['id']]['Messages'][$msg['Message']['id']] = $msg['Message'];
				}
			}
		}		
		$this->set('th', $thread);
	}
	
    function auto_complete() {
		$autoText = strtoupper($this->params['url']['autoCompleteText']);
		
        $terms = $this->Message->From->find('all', array(
            'conditions' => array(
                'UPPER(From.username) LIKE' => '%' . $autoText .'%',
                'From.username <>' => 'FA'
            ),
            'fields' => array('From.username'),
            'limit' => 5,
            'recursive'=>-1,
        ));
        $terms = Set::Extract($terms,'{n}.From.username');
        $this->set('terms', $terms);
        $this->layout = 'ajax';    
    } 

}