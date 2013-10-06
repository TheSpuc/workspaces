<?php
App::uses('AppController', 'Controller');
App::uses('CakeEmail', 'Network/Email');
App::uses('Sanitize', 'Utility');

/**
 * Users Controller
 *
 * @var User
 * @property User $User
 * @property 'Auth'Component $'Auth'
 */
class UsersController extends AppController {
	public $uses = array('User');
	
	//public $components = array('Session', 'Auth');
	public $components = array(
		'MathCaptcha' => array(
        	'timer' => 2
        ),
		'Auth' => array(
			'authenticate' => array(
				'Form' => array(
					'scope' => array('status' => 1)
				)
			)
		)
	);
	
	public $helpers = array('Session', 'Paginator', 'Html', 'Js', 'Form');
	var $paginate = array(
              'limit' => 4
              ); 
	public function beforeFilter() {
		parent::beforeFilter();

		$this->Auth->allow('add', 'logout', 'confirmuser', 'forgotpassword');// Letting users register themselves and logout
		$this->Auth->autoRedirect = false;
	}
	
	public function login() {
		if ($this->Auth->login()) {
			$this->User->Profile->login($this->Auth->user('id'));
			$this->Session->delete('Forum');
			$this->Session->delete('Clubs');
			//menuen
			$this->fillMenu();
			
			//set last login
			$this->User->save(array(
							'User' => array(
								'id' => $this->Auth->user('id'),
								'login' => time()
							)
						));
			
			$this->redirect($this->Auth->redirect());
		} else {
			if($this->request->is('post')){
				$user = $this->User->find('first', array(
					'conditions' => array(
						'User.username' => $this->request->data['User']['username'],
						'User.status' => 0
					)
				));
				if(!empty($user)){
					$this->Session->setFlash(__('User is not active yet. Follow the confirm link in your confirmation email'));
				}
				else{
					$this->Session->setFlash(__('Invalid username or password, try again'));
				}
			}
		}
	}
	
	public function logout() {
		//Forum
		$this->Session->delete('Forum');
		
		$this->redirect($this->Auth->logout());
	}
/**
 * index method
 *
 * @return void
 */
	public function index() {
		$this->User->recursive = 0;
		$this->set('users', $this->paginate());
	}

/**
 * view method
 *
 * @param string $id
 * @return void
 */
	public function view($id = null) {
		$this->User->id = $id;
		if (!$this->User->exists()) {
			throw new NotFoundException(__('Invalid user'));
		}
		$this->set('user', $this->User->read(null, $id));
	}

/**
 * add method
 *
 * @return void
 */
	public function add() {
		
		$userCount = $this->User->find('count', array('conditions' => array('User.status >' => 0, 'User.status <' => 3)));
		$ticketCount = $this->User->User_ticket->find('count');

		$this->set('countTotal', $userCount + $ticketCount);

		if ($userCount + $ticketCount > 50){
			$this->Session->setFlash(__('The beta is currently full. Please check back shortly.'));
		}
		else{
			
			if ($this->request->is('post')) {

				if($this->MathCaptcha->validate($this->request->data['User']['captcha'])){
					$this->User->create();
					
					//Set important fields to make sure user hasn't posted them
					$this->request->data['User']['access'] = 2;
					$this->request->data['User']['login'] = 0;
					$this->request->data['User']['status'] = 0;
					$this->request->data['User']['signature'] = '';
					$this->request->data['User']['totalPosts'] = 0;
					$this->request->data['User']['totalTopics'] = 0;
					$this->request->data['User']['admin_level'] = 0;				
					
					if ($this->User->save($this->request->data)) {
						//Create forum profile - commented 1/6 2012 so users who haven't activated their account yet and logged in are not displayed
						//$this->User->Profile->getUserProfile($this->User->id);
						
						
						////Create and send email/////
						//make a hash that is saved in the database and sent in an email. This is in link to confirm Footie account
						$userHash = md5($this->request->data['User']['email'] . $this->request->data['User']['username'].$this->request->data['User']['password'] . rand());
						//$link = "<a href='" . Router::url("", true) . "confirmuser/" . $userHash . "'>Go Footie Crazy!</a>";
						$footieLink = "<a href='http://www.footie-online.com/users/confirmuser/" . $userHash . "'>Go Footie Crazy!</a>";
						$wikiLink = "<a href='http://www.wiki.footie-online.com/wiki/footie/index.php/Category:Home'>wiki</a>";
						$wikiCreatePlayerLink = "<a href='http://www.wiki.footie-online.com/wiki/footie/index.php/Creating_a_new_player'>Creating a new player.</a>";
						$message = 	"Welcome to Footie " . $this->request->data['User']['username'] . "<br/>" .
													"To activate your Footie account follow this link: " . $footieLink . " (the link is active for 7 days).<br/>" .
													$wikiLink .	"If you want to know more about Footie, take a look at our . A good place to start is " . $wikiCreatePlayerLink;
							
						//Create the user ticket, for confirmuser.
						$this->User->User_ticket->save(array(
											'User_ticket' => array(
												'user_id' => $this->User->id,
												'expires' => date("Y-m-d", 8 * 86400 + time()),
												'hash' => $userHash
						//,
						//'timestamp' =>
						)
						));
					
						//$this->Html->link('Go Footie Crazy!', array('plugin' => null,'controller' => 'user', 'action' =>'confirmuser', $userHash));
						$email = new CakeEmail();
						$email->from(array('info@footie-online.com' => 'Footie-online'));
						$email->to($this->request->data['User']['email']);
						$email->replyTo(array('info@footie-online.com' => 'Footie-online'));
						$email->subject('Footie registration');
						$email->emailFormat('html');
						$email->send($message);
						$this->set('userset', $this->request->data);
							
						$this->Session->setFlash(__('The user has been saved. Please check your mail for your activation link.'));
						$this->redirect(array('action' => 'login'));
					} else {
						$this->Session->setFlash(__('The user could not be saved. Please, try again.'));
					}
				}
				else{
					$this->Session->setFlash(__('Validation question not answered correctly.'));
				}
			}	
			
			$this->set('captcha', $this->MathCaptcha->getCaptcha());
		}	
	}
/**
 * confirmuser method
 *
 * @param string $userHash
 * @return void
 */
	public function confirmUser($userHash = null){
		$ticket = $this->User->User_ticket->find('all', array(
			'conditions' => array(
					'hash' => $userHash
			)
		));
		if(!empty($ticket)){
		$ticket = $ticket[0];
		$saveUser = array(
			'User' => array(
				'id' => $ticket['User_ticket']['user_id'],
				'status' => 1
			)
		);
		$this->User->id = $ticket['User_ticket']['user_id'];
		$this->User->save($saveUser);
		$this->User->User_ticket->id = $ticket['User_ticket']['id'];
		$this->User->User_ticket->delete();		
		$this->Session->setFlash(__('You have now been confirmed on Footie. <a href="http://footie-online.com/users/login">Log in to start your career</a>'));
		}
		else {
		$this->Session->setFlash(__('This link is not active. If you have any questions, please contact info@footie-online.com.'));
		}
	}	
	
/**
 * forgotpassword method
 *
 * @return void
 */
	public function forgotpassword(){
		if(!empty($this->request->data['ForgotPassword']['email'])){
			
			$findUser = $this->User->find('all', array(
				'conditions' => array(
					'User.email' => $this->request->data['ForgotPassword']['email']
				),
				'recursive' => -1
			));
			//Hvis der findes en User med den email skal der laves et nyt password og sendes til den User, sammen med username
			if(!empty($findUser)){
				$findUser = $findUser[0];
				$newPass = $this->getRandomString();
				//save new password
				$saveUser = array(
					'User' => array(
						'id' => $findUser['User']['id'],
						'password' => $newPass,
						'username' => $findUser['User']['username']
					)
				);	
				if($this->User->save($saveUser)){				
					$message = "Your password has been reset <br/>
								Username: " . $findUser['User']['username'] ." <br/>
								Password: " . $newPass . "<br/><br/>
								Remember to change your password when you log in.";
					$email = new CakeEmail();
					$email->from(array('info@footie-online.com' => 'Footie-online'));
					$email->to($findUser['User']['email']);				
					$email->replyTo(array('info@footie-online.com' => 'Footie-online'));
					$email->subject('Forgotten password');
					$email->emailFormat('html');
					$email->send($message);
					$this->set('userset', $this->request->data);
				
					$this->Session->setFlash(__('An email with a new password has been sent to ' .$findUser['User']['email']));
				}else{
					$this->Session->setFlash(__('The email could not be send. Try again and if that does not work, contact Ulrik or Rune.'));
				}
			}else{
				$this->Session->setFlash(__('A user with that email could not be found.'));
			}
		}
	
	}
	
	
/**
 * edit method
 *
 * @param string $id
 * @return void
 */
 /* For now we use the forum/forumusers/edit from the forum plugin. No security check done
	public function edit() {
		$this->User->id = $this->Auth->user('id');
		if (!$this->User->exists()) {
			throw new NotFoundException(__('Invalid user'));
		}
		if ($this->request->is('post') || $this->request->is('put')) {
			
			$this->User->set('email', $this->request->data['email']);
					
			if ($this->User->save($this->User->data)) {
				$this->Session->setFlash(__('The user has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The user could not be saved. Please, try again.'));
			}
		} else {
			$this->request->data = $this->User->read(null, $id);
		}
	}
	*/

/**
 * delete method
 *
 * @param string $id
 * @return void
 */
 /* Removed 16/6 2012 - we do not delete users. Close account method needed instead. No security check done
	public function delete($id = null) {
		if (!$this->request->is('post')) {
			throw new MethodNotAllowedException();
		}
		$this->User->id = $id;
		if (!$this->User->exists()) {
			throw new NotFoundException(__('Invalid user'));
		}
		if ($this->User->delete()) {
			$this->Session->setFlash(__('User deleted'));
			$this->redirect(array('action' => 'index'));
		}
		$this->Session->setFlash(__('User was not deleted'));
		$this->redirect(array('action' => 'index'));
	}
*/
	
/**
 * admin_index method
 *
 * @return void
 */
 /* Not currently used. No security check done
	public function admin_index() {
		$this->User->recursive = 0;
		$this->set('users', $this->paginate());
	}
*/
/**
 * admin_view method
 *
 * @param string $id
 * @return void
 */
  /* Not currently used. No security check done
	public function admin_view($id = null) {
		$this->User->id = $id;
		if (!$this->User->exists()) {
			throw new NotFoundException(__('Invalid user'));
		}
		$this->set('user', $this->User->read(null, $id));
	}
*/
/**
 * admin_add method
 *
 * @return void
 */
   /* Not currently used. No security check done
	public function admin_add() {
		if ($this->request->is('post')) {
			$this->User->create();
			if ($this->User->save($this->request->data)) {
				$this->Session->setFlash(__('The user has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The user could not be saved. Please, try again.'));
			}
		}
	}
*/
/**
 * admin_edit method
 *
 * @param string $id
 * @return void
 */
   /* Not currently used. No security check done
	public function admin_edit($id = null) {
		$this->User->id = $id;
		if (!$this->User->exists()) {
			throw new NotFoundException(__('Invalid user'));
		}
		if ($this->request->is('post') || $this->request->is('put')) {
			if ($this->User->save($this->request->data)) {
				$this->Session->setFlash(__('The user has been saved'));
				$this->redirect(array('action' => 'index'));
			} else {
				$this->Session->setFlash(__('The user could not be saved. Please, try again.'));
			}
		} else {
			$this->request->data = $this->User->read(null, $id);
		}
	}
	*/

/**
 * admin_delete method
 *
 * @param string $id
 * @return void
 */
   /* Not currently used. No security check done
	public function admin_delete($id = null) {
		if (!$this->request->is('post')) {
			throw new MethodNotAllowedException();
		}
		$this->User->id = $id;
		if (!$this->User->exists()) {
			throw new NotFoundException(__('Invalid user'));
		}
		if ($this->User->delete()) {
			$this->Session->setFlash(__('User deleted'));
			$this->redirect(array('action' => 'index'));
		}
		$this->Session->setFlash(__('User was not deleted'));
		$this->redirect(array('action' => 'index'));
	}
	*/
	
	public function loginas($id = null) {

		$this->User->id = $id;
		if (!$this->User->exists()) {
			throw new NotFoundException(__('Invalid user'));
		}
		if($this->Auth->user('admin_level') == 99){
			$data = $this->User->read(null, $id);
			$_SESSION['Auth']['User']['id'] = $data['User']['id'];
			$_SESSION['Auth']['User']['username'] = $data['User']['username'];
			$_SESSION['Auth']['User']['admin_level'] = $data['User']['admin_level'];
				
		}
		
		$this->redirect(array('action' => 'office'));
	}
	
	
	
	function adminpanel(){ //No security check done
		if($this->Auth->user('admin_level') == 99 || $this->Auth->user('id') == 1){
		$this->set('authuser', $this->User->find('all',
		array(
				'conditions' => array(
					'User.id' => $this->Auth->user('id')
				)
		)
				));
		}
		else{
			$this->redirect(array('action' => 'office'));	
		}	
		
		$this->loadModel('Match');
		$this->set('users', $this->User->find('all', array('recursive' => -1)));
		
		//Find all matches currently running
		$this->set('matches', $this->Match->find('all', array(
			'recursive' => 0,
			'conditions' => array(
				'status' => 1, 
				'matchdate <= now()'
			)
		)));
		
		//POST//
		if (!empty($this->request->data)) {
         
			$this->set('sendt', $this->request->data);
		 
		 	//Reset match//
		 	if(isset($_POST['sbmResetMatch'])){
		 		$id = Sanitize::paranoid($_POST['txtMatchId']);
		 		$days = Sanitize::paranoid($_POST['txtAddDays']);
		 		
		 		$match = $this->Match->find('first', array('conditions' => array('Match.id' => $id)));
		 		$replaypath = "/var/www/footiecake_2.0/app/webroot/matchengine/" . $match['Match']['league_id'] . "/" . $id . ".txt";
		 		
				$this->User->query('delete from xp where match_id=' . $id);
				$this->User->query('delete from club_incomes where ext_id=' . $id);
				$this->User->query('delete from club_expenses where ext_id=' . $id);
				$this->User->query('delete from match_lineups where match_id=' . $id);
				$this->User->query('delete from match_events where match_id=' . $id);
				$this->User->query('delete from matchstats where match_id=' . $id);
				$this->User->query('delete from match_playerstats where match_id=' . $id);
				$this->User->query("update matches set status=0, matchdate=matchdate + interval '" . $days . " days' where id=" . $id);
				
		 		
		 		if (file_exists($replaypath)) {
		 			unlink($replaypath);
		 		}
		 		
				$this->Session->setFlash(__('Match reset. Replay deleted: ' . $replaypath . ' - check server for crashed match engines!'));
			}

			
		
			//New news writer//
			if(isset($_POST['sbmNewWriter'])){
				$this->User->query('INSERT INTO news_writers (user_id, league_id) VALUES (' . Sanitize::paranoid($_POST['txtUser']) . ', ' . Sanitize::paranoid($_POST['txtLeague']) . ')');
				$this->Session->setFlash(__('New writer created'));
			}
			
			//Copy production site to test site//
			if(isset($_POST['sbmProdToTest'])){
				$output = shell_exec( " cp -r -a /var/www/footiecake_2.0/app/Controller/* /var/www/test_footiecake/app/Controller/ 2>&1 " );
				$output = $output . shell_exec( " cp -r -a /var/www/footiecake_2.0/app/Controller/Component/* /var/www/test_footiecake/app/Controller/Component/ 2>&1 " );
				$output = $output . shell_exec( " cp -r -a /var/www/footiecake_2.0/app/View/* /var/www/test_footiecake/app/View/ 2>&1 " );
				$output = $output . shell_exec( " cp -r -a /var/www/footiecake_2.0/app/Model/* /var/www/test_footiecake/app/Model/ 2>&1 " );
				$output = $output . shell_exec( " cp -r -a /var/www/footiecake_2.0/app/Plugin/* /var/www/test_footiecake/app/Plugin/ 2>&1 " );
				$output = $output . shell_exec( " cp -r -a /var/www/footiecake_2.0/app/Vendor/* /var/www/test_footiecake/app/Vendor/ 2>&1 " );
				$output = $output . shell_exec( " cp -a /var/www/footiecake_2.0/app/webroot/* /var/www/test_footiecake/app/webroot/ 2>&1 " );
				$output = $output . shell_exec( " cp -r -a /var/www/footiecake_2.0/app/webroot/img/* /var/www/test_footiecake/app/webroot/img/ 2>&1 " );
				$output = $output . shell_exec( " cp -r -a /var/www/footiecake_2.0/app/webroot/js/* /var/www/test_footiecake/app/webroot/js/ 2>&1 " );
				$output = $output . shell_exec( " cp -r -a /var/www/footiecake_2.0/app/webroot/css/* /var/www/test_footiecake/app/webroot/css/ 2>&1 " );
				$output = $output . shell_exec( " cp -r -a /var/www/footiecake_2.0/app/webroot/applets/* /var/www/test_footiecake/app/webroot/applets/ 2>&1 " );
				$output = $output . shell_exec( " cp -r -a /var/www/footiecake_2.0/app/webroot/files/* /var/www/test_footiecake/app/webroot/files/ 2>&1 " );

				
				$this->Session->setFlash(__('Copying production site to test site<br />' . $output));
			}

			//Clear cache//
			/*
			Cache::clear();
	   		 $cachePaths = array('js', 'css', 'menus', 'views', 'persistent', 
				'models'); 
	    		foreach($cachePaths as $config) { 
	       		 clearCache(null, $config); 
	    		}*/
			
		//RENAME USER//
			//1. Er der postet de rigtige data
			if(!empty($this->request->data['RenameUser']['username']) && !empty($this->request->data['RenameUser']['user_id'])){
				$oldData = $this->User->findById($this->request->data['RenameUser']['user_id']);
				//2. Findes der et match i databasen
				if(!empty($oldData)){
					$this->request->data['User']['username'] = $this->request->data['RenameUser']['username'];
					$this->request->data['User']['id'] = $this->request->data['RenameUser']['user_id'];
					$this->set('savedata', $this->request->data);
					if ($this->User->save($this->request->data)) {
						$this->Session->setFlash(__('Username saved - (' . $oldData['User']['username']  . ' is now ' . $this->request->data['RenameUser']['username'] . ')'));
					}	
				}
			}	
		//UP FOR USER//
			//1. Er der postet de rigtige data
			if(!empty($this->request->data['UPForUser']['userpoints']) && !empty($this->request->data['UPForUser']['user_id'])){
				$oldData = $this->User->findById($this->request->data['UPForUser']['user_id']);
				//2. Findes der et match i databasen
				if(!empty($oldData)){
					$this->request->data['User']['userpoints'] = $this->request->data['UPForUser']['userpoints'];
					$this->request->data['User']['id'] = $this->request->data['UPForUser']['user_id'];
					if ($this->User->save($this->request->data)) {
						$this->Session->setFlash(__('Userpoints saved - (' . $oldData['User']['username']  . ' has ' . $this->request->data['UPForUser']['userpoints'] . ' userpoints)'));
					}	
				}
			}		
		//CLUB TO USER//
			//1. Er der postet de rigtige data
			if(!empty($this->request->data['ClubToUser']['club_id']) && !empty($this->request->data['ClubToUser']['user_id'])){
				$clubToUserclub = $this->User->Club->findById($this->request->data['ClubToUser']['club_id']);
				$clubToUserowner = $this->User->findById($this->request->data['ClubToUser']['user_id']);
				//2. Findes der et match i databasen
				if(!empty($clubToUserclub) && !empty($clubToUserowner)){
					$this->request->data['Club']['id'] = $this->request->data['ClubToUser']['club_id'];
					$this->request->data['Club']['user_id'] = $this->request->data['ClubToUser']['user_id'];
					if ($this->User->Club->save($this->request->data)) {
						$this->Session->setFlash(__('Club joined to owner - (' . $clubToUserclub['Club']['clubname'] . ' to '. $clubToUserowner['User']['username'] . ')'));
					}	
				}
			}
		//USER FOR USER//
			//1. Er der postet de rigtige data
			if(!empty($this->request->data['UserPassword']['user_id'])){
				$oldData = $this->User->findById($this->request->data['UserPassword']['user_id']);
				//2. Findes der et match i databasen
				if(!empty($oldData)){
					$this->request->data['User']['password'] = $this->request->data['UserPassword']['password'];
					$this->request->data['User']['id'] = $this->request->data['UserPassword']['user_id'];
					$this->set('savedata', $this->request->data);
					if ($this->User->save($this->request->data)) {
						$this->Session->setFlash(__('Password saved - (' . $oldData['User']['username']  . ' has had his password changed)'));
					}	
				}
			}
		//MONEY FOR CLUB//
			//1. Er der postet de rigtige data
			if(!empty($this->request->data['MoneyForClub']['money']) && !empty($this->request->data['MoneyForClub']['club_id'])){
				$oldData = $this->User->Club->findById($this->request->data['MoneyForClub']['club_id']);
				//2. Findes der et match i databasen
				if(!empty($oldData)){
					$this->request->data['Club']['money'] = $this->request->data['MoneyForClub']['money'];
					$this->request->data['Club']['id'] = $this->request->data['MoneyForClub']['club_id'];
					if ($this->User->Club->save($this->request->data)) {
						$this->Session->setFlash(__('Money saved - (' . $oldData['Club']['clubname'] . ' has ' . $this->request->data['MoneyForClub']['money'] . '£)'));
					}	
				}
			}
		//RENAME CLUB//
			//1. Er der postet de rigtige data
			if(!empty($this->request->data['RenameClub']['clubname']) && !empty($this->request->data['RenameClub']['club_id'])){
				$oldData = $this->User->Club->findById($this->request->data['RenameClub']['club_id']);
				//2. Findes der et match i databasen
				if(!empty($oldData)){
					$this->request->data['Club']['clubname'] = $this->request->data['RenameClub']['clubname'];
					$this->request->data['Club']['id'] = $this->request->data['RenameClub']['club_id'];
					if ($this->User->Club->save($this->request->data)) {
						$this->Session->setFlash(__('Club renamed - ('. $oldData['Club']['clubname'] . ' has now been named ' . $this->request->data['RenameClub']['clubname'] . ')'));
					}	
				}
			}

		//RENAME CLUB SHORT//
			//1. Er der postet de rigtige data
			if(!empty($this->request->data['RenameClubShort']['shortname']) && !empty($this->request->data['RenameClubShort']['club_id'])){
				$oldData = $this->User->Club->findById($this->request->data['RenameClubShort']['club_id']);
				//2. Findes der et match i databasen
				if(!empty($oldData)){
					$this->request->data['Club']['shortname'] = $this->request->data['RenameClubShort']['shortname'];
					$this->request->data['Club']['id'] = $this->request->data['RenameClubShort']['club_id'];
					if ($this->User->Club->save($this->request->data)) {
						$this->Session->setFlash(__('Club renamed - ('. $oldData['Club']['clubname'] . ' (' . $oldData['Club']['shortname'] . ') has now been named ' . $oldData['Club']['clubname'] . ' (' . $this->request->data['RenameClubShort']['shortname'] . '))'));
					}	
				}
			}			
		//SET CLUB FAME//
			//1. Er der postet de rigtige data
			if(!empty($this->request->data['ClubFame']['fame']) && !empty($this->request->data['ClubFame']['club_id'])){
				$oldData = $this->User->Club->findById($this->request->data['ClubFame']['club_id']);
				//2. Findes der et match i databasen
				if(!empty($oldData)){
					$this->request->data['Club']['fame'] = $this->request->data['ClubFame']['fame'];
					$this->request->data['Club']['id'] = $this->request->data['ClubFame']['club_id'];
					if ($this->User->Club->save($this->request->data)) {
						$this->Session->setFlash(__('Club saved - (' . $oldData['Club']['clubname'] . ' has ' . $this->request->data['ClubFame']['fame'] . ' fame)'));
					}	
				}
			}
		//RENAME STADIUM//
			//1. Er der postet de rigtige data
			if(!empty($this->request->data['RenameStadium']['stadiumname']) && !empty($this->request->data['RenameStadium']['club_id'])){
				$oldData = $this->User->Club->findById($this->request->data['RenameStadium']['club_id']);
				//2. Findes der et match i databasen
				if(!empty($oldData)){
					$this->request->data['Stadium']['stadiumname'] = $this->request->data['RenameStadium']['stadiumname'];
					$this->request->data['Stadium']['id'] = $oldData['Stadium']['id'];
					if ($this->User->Club->Stadium->save($this->request->data)) {
						$this->Session->setFlash(__('Stadium renamed - (' . $oldData['Stadium']['stadiumname'] . ' has now been named ' . $this->request->data['Stadium']['stadiumname'] . ')'));
					}	
				}
			}
    	
		
		//RENAME PERSON FIRSTNAME//
			//1. Er der postet de rigtige data
			if(!empty($this->request->data['RenamePersonFirst']['firstname']) && !empty($this->request->data['RenamePersonFirst']['person_id'])){
				$oldData = $this->User->Person->findById($this->request->data['RenamePersonFirst']['person_id']);
				//2. Findes der et match i databasen
				if(!empty($upForUseruser)){
					$this->request->data['Person']['firstname'] = $this->request->data['RenamePersonFirst']['firstname'];
					$this->request->data['Person']['id'] = $this->request->data['RenamePersonFirst']['person_id'];
					if ($this->User->Person->save($this->request->data)) {
						$this->Session->setFlash(__('Person renamed - (' . $upForUseruser['Person']['firstname'] . ' ' . $upForUseruser['Person']['lastname'] . ' has now been named ' . $this->request->data['RenamePersonFirst']['firstname'] . ' ' . $upForUseruser['Person']['lastname'] . ')'));
					}	
				}
			}
		
		//RENAME PERSON LASTNAME//
			//1. Er der postet de rigtige data
			if(!empty($this->request->data['RenamePersonLast']['lastname']) && !empty($this->request->data['RenamePersonLast']['person_id'])){
				$upForUseruser = $this->User->Person->findById($this->request->data['RenamePersonLast']['person_id']);
				//2. Findes der et match i databasen
				if(!empty($upForUseruser)){
					$this->request->data['Person']['lastname'] = $this->request->data['RenamePersonLast']['lastname'];
					$this->request->data['Person']['id'] = $this->request->data['RenamePersonLast']['person_id'];
					if ($this->User->Person->save($this->request->data)) {
						$this->Session->setFlash(__('Person renamed - (' . $upForUseruser['Person']['firstname'] . ' ' . $upForUseruser['Person']['lastname'] . ' has now been named ' . $upForUseruser['Person']['firstname'] . ' ' . $this->request->data['RenamePersonLast']['lastname'] . ')'));
					}	
				}
			}
		//PP FOR PERSON//
			//1. Er der postet de rigtige data
			if(!empty($this->request->data['PPForPerson']['playerpoints']) && !empty($this->request->data['PPForPerson']['person_id'])){
				$upForUseruser = $this->User->Person->findById($this->request->data['PPForPerson']['person_id']);
				//2. Findes der et match i databasen
				if(!empty($upForUseruser)){
					$this->request->data['Person']['playerpoints'] = $this->request->data['PPForPerson']['playerpoints'];
					$this->request->data['Person']['id'] = $this->request->data['PPForPerson']['person_id'];
					if ($this->User->Person->save($this->request->data)) {
						$this->Session->setFlash(__('Playerpoints saved - (' . $upForUseruser['Person']['firstname'] . ' ' . $upForUseruser['Person']['lastname'] . ' has ' . $this->request->data['PPForPerson']['playerpoints'] . ' playerpoints)'));
					}	
				}
			}
		}
	}
	
	function scouting(){
		$this->set('judgingPotential', $this->Auth->user('scouting'));
		
		$this->loadModel('UserCountryKnowledge');
		
		$knowledge = $this->UserCountryKnowledge->find('all', array(
							'conditions' => array('UserCountryKnowledge.user_id' => $this->Auth->user('id')),
							'order' => 'UserCountryKnowledge.knowledge DESC'
		));
		$this->set('knowledge', $knowledge);
		
	}
	
	function office(){
		
		//Footie news
		$this->loadModel('NewsItem');
		$this->loadModel('Match');
		$arrmenudata = $this->Session->read('Clubs');
		$leagueIDs = array();
		
		foreach($arrmenudata['mallleagues'] as $league)
		{
			array_push($leagueIDs, $league[0]['league_id']);
		}
		
		$writerLeagues = $this->NewsItem->query('SELECT league_id, leaguename FROM news_writers w INNER JOIN leagues l ON l.id=w.league_id WHERE user_id=' . $this->Auth->user('id'));
		$this->set('writerLeagues', $writerLeagues);
		
		$this->set('news', $this->NewsItem->find('all', array(
							'conditions' => array('NewsItem.league_id' => $leagueIDs),
							'order' => 'NewsItem.date DESC',
							'limit' => 5
		)));
		
		
		//Latest transfers
		$this->loadModel('Contract');
		$transfers = $this->Contract->find('all', array(
			'limit' => 5,
	        'order' => array(
	            'Contract.startdate' => 'desc'
				),
			'conditions' => array('Contract.acceptedbyplayer' => true, 'OR' => array('Contract.club_id <> Contract.from_club', 'Contract.from_club IS NULL'))
			)
		);
		$this->set('transfers', $transfers);
		
		//New news articles posted
		if($this->request->is('post') && isset($_POST['sbmNews'])){
				
			//If the user is a writer for the league
			if (in_array($_POST['slLeague'], $leagueIDs)) {
				$this->NewsItem->create();
				$this->NewsItem->set('league_id', $_POST['slLeague']);
				$this->NewsItem->set('author', $this->Auth->user('id'));
				$this->NewsItem->set('headline', $_POST['txtHeadline']);
				$this->NewsItem->set('body', $_POST['txtBody']);
				$this->NewsItem->save($this->NewsItem->data);
		
				$this->Session->setFlash(__('News item created.'));
			}
			else{
				$this->Session->setFlash(__('You are not a writer for this league. Please select a league from the list.'));
			}
		}
		///////////Footie news done////////////
		
		//Latest transfer rumours from forum
		$this->loadModel('Forum.Topic');
		$rumours = $this->Topic->getLatestByForum(10);
		$this->set('rumours', $rumours);
		//transfer rumours done
		
		$this->set('usern', $this->User->find('all', array(
			'conditions' => array(
				'User.id' => $this->Auth->user('id')),
			'fields' => array(
				'userpoints'),
			'recursive' => -1
		)));
		$players = $this->User->Person->find('all',
		array(
				'conditions' => array(
					'Person.user_id' => $this->Auth->user('id')
					// ,
					// 'Contract.acceptedbyclub' => true,
					// 'Contract.acceptedbyplayer' => true,
					// 'Contract.startdate <' => date('Y-m-d'),
					// 'Contract.enddate >' => date('Y-m-d')
				),
				'fields' => array('Person.id', 'Person.firstname', 'Person.lastname', 'Person.age', 'Person.playerpoints', 'Contract.club_id', 'Club.clubname'),
				'recursive' => 0,
				'joins' => array(
					array(
						'table' => 'contracts',
						'alias' => 'Contract',
						'type' => 'LEFT',
						'conditions' => array(
							'Contract.person_id = Person.id',
							'Contract.acceptedbyclub' => true,
							'Contract.acceptedbyplayer' => true,
							'Contract.startdate <=' => date('Y-m-d'),
							'Contract.enddate >' => date('Y-m-d')
						)
					),
					array(
						'table' => 'clubs',
						'alias' => 'Club',
						'type' => 'LEFT',
						'conditions' => array(
							'Club.id = Contract.club_id'
						)
					)
		)
		)
		);
		
		$this->set('players', $players);
		$plids = array();
		
		foreach ($players as $pl)
		{
			array_push($plids, $pl['Person']['id']);
		}
		
		$this->loadModel('Person');
		$this->set('offers', $this->Person->ActiveContract->find('all',
		array(
								'conditions' => array(
									'ActiveContract.acceptedbyclub' => true,
									'ActiveContract.acceptedbyplayer' => false,
									'ActiveContract.offered' => true,
									'ActiveContract.person_id' => $plids,
									'ActiveContract.enddate >' => 'now()')
		)));
		
		$this->set('clubs', $this->User->Club->find('all',
			array(
				'fields' => array('Club.id', 'Club.clubname', 'Club.shortname', 'Club.league_id', 'Club.firstcolor', 'Club.secondcolor', 'Club.stadium_id', 'Club.money', 'Club.user_id', 'Club.trainingfacc', 'Club.seatprice', 'Club.standprice', 'Club.fame', 'Club.created', 'League.leaguename'),
				'conditions' => array(
					'user_id' => $this->Auth->user('id')
				),
				'recursive' => -1,
				'joins' => array(
					array(
						'table' => 'leagues',
						'alias' => 'League',
						'type' => 'LEFT',
						'conditions' => array(
							'League.id = Club.league_id'
						)
					)
				)
			)
		));
		
	///RECENT MATCHES
		$this->set("recentMatches", $this->Match->query("SELECT DISTINCT ON(m.id, m.matchdate) m.id, (SELECT clubname FROM clubs c WHERE c.id=m.homeTeamID) as hometeamname, (SELECT id FROM clubs c WHERE c.id=m.homeTeamID) as hometeamid, (SELECT clubname FROM clubs c WHERE c.id=m.awayTeamID) as awayteamname, (SELECT id FROM clubs c WHERE c.id=m.awayTeamID) as awayteamid, m.matchdate, (SELECT stadiumname FROM stadiums s WHERE s.id=m.stadium_id) as stadiumname, (SELECT leaguename FROM leagues WHERE id=m.league_id) as leaguename, (SELECT name FROM competition_stages WHERE id=m.stage_id) as stagename, m.league_id FROM matches m INNER JOIN (SELECT DISTINCT ON(c.id, c.shortname, c.clubname) c.id, c.shortname, c.clubname, p.firstname, c.league_id, l.leaguename, u.id AS user_id FROM clubs c INNER JOIN contracts co ON co.club_id = c.id INNER JOIN persons p ON p.id = co.person_id INNER JOIN users u ON p.user_id = u.id INNER JOIN leagues l ON c.league_id = l.id WHERE u.id =" . $this->Auth->user('id') . " AND co.acceptedbyclub = true AND co.acceptedbyplayer = true AND enddate > now() OR c.user_id = " . $this->Auth->user('id') . ") userclubs ON (m.hometeamid = userclubs.id OR m.awayteamid = userclubs.id) WHERE (m.matchdate > (now()- INTERVAL '7 days') AND m.matchdate < now()) AND m.status = 2 ORDER BY m.matchdate DESC"));
		
	}
	
	
	function search(){  	  
  //$fname, $lname, $club, $agent, $firstSelect, $secondSelect, $contract
  if ($this->request->is('post')){
	  	$sel1 = Sanitize::escape($_POST['sel1']);
		$sel2 = Sanitize::escape($_POST['sel2']);
		$select1 = Sanitize::escape($_POST['select1']);
		$select2 = Sanitize::escape($_POST['select2']);
		$fname = Sanitize::escape($_POST['fname']);
		$lname = Sanitize::escape($_POST['lname']);
		$club = Sanitize::escape($_POST['club']);
		$agent= Sanitize::escape($_POST['agent']);
		$contract = Sanitize::escape($_POST['contract']);
		$cont = $contract;
		$firstSelect = "";
		$secondSelect = "";
		
	if (strlen(trim($sel1)) >= 1 && $this->isint($sel1)){
     $firstSelect = $this->setSelect("select1", "sel1");     
}
    if (strlen(trim($sel2)) >= 1 && $this->isint($sel2)){
     $secondSelect = $this->setSelect("select2", "sel2");     
}
	  
      $count = 0;
      $ordercount = 0;
      $orderselect = "";
	  //$this->set($deb, $this->data);
     $selectquery = "SELECT *, p.id as pid FROM persons p LEFT OUTER JOIN contracts con ON con.person_id=p.id AND con.role=1 AND con.acceptedbyplayer='true' AND con.enddate > now() LEFT OUTER JOIN clubs c ON c.id=con.club_id LEFT OUTER JOIN users u ON u.id = p.user_id ";

      if($fname != ""){ 
           $fname = "WHERE UPPER(firstname) LIKE UPPER('%" . $fname . "%')"; $count = 1;
           $selectquery = $selectquery . $fname;
      }
	  $this->set('deb', $selectquery);
      if($lname!= ""){ 
           if($count == 1) $lname = " AND lastname = '" . $lname . "'";
           else {$lname = "WHERE UPPER(lastname) LIKE UPPER('%" . $lname . "%')"; $count = 1;}
           $selectquery = $selectquery . $lname;
      }
      if($club != ""){ 
           if($count == 1) $club = " AND c.clubname= '" . $club . "'";
           else {$club = "WHERE UPPER(c.clubname) LIKE  UPPER('%" . $club . "%')"; $count = 1;}
           $selectquery = $selectquery . $club;
      }
      if($agent != ""){ 
           if($count == 1) $agent = " AND username = '" . $agent . "'";
           else {$agent = "WHERE UPPER(u.username) LIKE UPPER('%" . $agent . "%')";$count = 1;}
           $selectquery = $selectquery . $agent;
      }
      if($firstSelect!= ""){ 
           if($count == 1) $firstSelect= " AND " . $firstSelect;
           else {$firstSelect= "WHERE " . $firstSelect; $count = 1;}
           $orderselect = " ORDER BY " . $select1 . " DESC"; $ordercount = 1;
           $selectquery = $selectquery . $firstSelect;
      }
      if($secondSelect!= ""){ 
           if($count == 1) $secondSelect= " AND " . $secondSelect;
           else {$secondSelect= "WHERE " . $secondSelect; $count = 1;}
           if($ordercount == 1){ $orderselect = $orderselect . ", " . $select2 . " DESC";}
           else {$orderselect = " ORDER BY " . $select2 . " DESC";}
           $selectquery = $selectquery . $secondSelect;
      }
        if($contract != "" && $contract != "none"){
           if($contract == "unemployed"){ $contract = " (select count (*) from contracts where acceptedbyplayer='t' and enddate > now() and person_id=p.id) = 0 ";}
           if($contract == "expiring1"){ $contract = " con.enddate > now() AND con.enddate < now()::date+7 ";}
           if($count == 1) $contract= " AND " . $contract;
           else {$contract= "WHERE " . $contract; $count = 1;}
           $selectquery = $selectquery . $contract;
      }
	 // $this->User->Person->paginate('all',
		// array(
				// 'conditions' => array(
					// 'Person.id' => 'Contract.person_id'
				// ),
				// 'recursive' => 0,
				// 'joins' => array(
					// array(
						// 'table' => 'contracts',
						// 'alias' => 'Contract',
						// 'type' => 'LEFT',
						// 'conditions' => array(
							// 'Contract.club_id = Club.id',
							// 'Contract.acceptedbyclub' => true,
							// 'Contract.acceptedbyplayer' => true,
							// 'Contract.startdate <' => date('Y-m-d'),
							// 'Contract.enddate >' => date('Y-m-d')
						// )
					// ),
					// array(
						// 'table' => 'clubs',
						// 'alias' => 'Club',
						// 'type' => 'LEFT',
						// 'conditions' => array(
							// 'Club.id = Contract.club_id'
						// )
					// ),
					// array(
						// 'table' => 'users',
						// 'alias' => 'User',
						// 'type' => 'LEFT',
						// 'conditions' => array(
							// 'User.id = Person.user_id'
						// )
					// )
				// )
		// ));
$selectquery = $selectquery . $orderselect;
$selectquery = $selectquery . " LIMIT 35";
////echo $selectquery;
$this->set('selectquery', $selectquery);
$this->set('result10', $this->User->query($selectquery));
  }
  }
function isint( $mixed )
{
    return ( preg_match( '/^\d*$/'  , $mixed) == 1 );
}

  function setSelect($selbox, $selval){
	if ($_POST[$selbox] == "acceleration")  return "acceleration >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "topspeed")  return "topspeed >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "agility")  return "agility >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "strength")  return "strength >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "jumping")  return "jumping >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "reaction")  return "reaction >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "stamina")  return "stamina >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "dribbling")  return "dribbling >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "shooting")  return "shooting >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "shotpower")  return "shotpower >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "passing")  return "passing >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "technique")  return "technique >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "vision")  return "vision >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "marking")  return "marking >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "tackling")  return "tackling >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "heading")  return "heading >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "commandofarea")  return "commandofarea >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "handling")  return "handling >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "rushingout")  return "rushingout >= '" . Sanitize::escape($_POST[$selval]) . "'";
	else if ($_POST[$selbox] == "shotstopping")  return "shotstopping >= '" . Sanitize::escape($_POST[$selval]) . "'";
  }
  
	function getRandomString($length = 10) {
		$validCharacters = "abcdefghijklmnopqrstuxyvwzABCDEFGHIJKLMNOPQRSTUXYVWZ1234567890";
		$validCharNumber = strlen($validCharacters);
		$result = "";

		for ($i = 0; $i < $length; $i++) {
			$index = mt_rand(0, $validCharNumber - 1);
			$result .= $validCharacters[$index];
		}
		return $result;
	}

}


