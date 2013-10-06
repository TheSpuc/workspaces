<?
App::uses('CakeEmail', 'Network/Email');
/**
* Message Component for footie-online.com.
*/
class MessageComponent extends Component {

	public function sendMessage($subject, $fromID, $toID, $text){
		$this->Message = ClassRegistry::init('Message');
			
		$this->Message->Message_thread->save(array(
 								'Message_thread' => array(
 									'subject' => $subject,
 									'updated' => 'now()'
		)
		));
			
		$threadId = $this->Message->Message_thread->id;
		
		$this->Message->save(array(
 								'Message' => array(
 									'thread_id' => $threadId,
 									'from_user_id' => $fromID,
 									'to_user_id' => $toID,
 									'text' => $text)
		));
		
		//send email to recipient
		$user = ClassRegistry::init('User')->find('first', array(
							'conditions' => array(
								'User.id' => $toID,
								'User.username <>' => 'FA'
							),
							'fields' => array('id', 'email'),
							'recursive' => -1
						));
		$message = "You've got a private message on Footie.<br/>" .
				   "<a href='http://footie-online.com'>Log in</a> to read it<br/><br/>";
		$email = new CakeEmail();
		$email->from(array('info@footie-online.com' => 'Footie-online'));
		$email->to($user['User']['email']);
		$email->replyTo(array('info@footie-online.com' => 'Footie-online'));
		$email->subject($subject);
		$email->emailFormat('html');
		$email->send($message);	
	}
}
?>