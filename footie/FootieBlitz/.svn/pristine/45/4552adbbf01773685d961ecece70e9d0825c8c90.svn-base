<?PHP

/**
 * Newsitem Controller
 *
 * @var Newsitem Controller
 */

App::uses('AppController', 'Controller');

class NewsitemsController extends AppController {


	public $helpers = array('Js', 'Html', 'Paginator');

	public $paginate = array(
        'limit' => 5,
        'order' => array(
            'NewsItem.date' => 'desc'
        )
    );
    
	public function show() {
		
		$this->loadModel('NewsItem');
		
		$arrmenudata = $this->Session->read('Clubs');
		$leagueIDs = array();
		
		foreach($arrmenudata['mallleagues'] as $league)
		{
			array_push($leagueIDs, $league[0]['league_id']);
		}
		
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
		
		$writerLeagues = $this->NewsItem->query('SELECT league_id, leaguename FROM news_writers w INNER JOIN leagues l ON l.id=w.league_id WHERE user_id=' . $this->Auth->user('id'));
		$this->set('writerLeagues', $writerLeagues);
		
		$data = $this->paginate('NewsItem');
    	$this->set('data', $data);
	}
	
	

}