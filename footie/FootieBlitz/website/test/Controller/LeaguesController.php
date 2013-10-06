<?php

/**
 * Leagues Controller
 *
 * @var Leagues Controller
 */

App::uses('Sanitize', 'Utility');

class LeaguesController extends AppController {
	
	var $name = 'Leagues';

	public $helpers = array('Js', 'FootieText');
	public $components = array('RequestHandler', 'Session');
	
	
	function history($id = null){
		
		$id = Sanitize::escape($id);
		
		$league = $this->League->find(
			'all',
			array(
				'conditions' => array('League.id' => $id)
    		)
  		);
		$this->set('league', $league);
		
		$query = "Select s.number, attendance, matchdate, h.clubname as hometeam, a.clubname as awayteam from matches m inner join clubs a on a.id=m.awayteamid inner join seasons s on s.id=m.season_id inner join clubs h on h.id=m.hometeamid inner join leagues l on l.id=m.league_id where attendance > 0 and l.id = " . $id . " order by attendance, matchdate limit 1;";
		$this->set('minatt', $this->League->query($query));
		
		$query = "Select s.number, attendance, matchdate, h.clubname as hometeam, a.clubname as awayteam from matches m inner join clubs a on a.id=m.awayteamid inner join seasons s on s.id=m.season_id inner join clubs h on h.id=m.hometeamid inner join leagues l on l.id=m.league_id where attendance > 0 and l.id = " . $id . " order by attendance desc, matchdate limit 1;";
		$this->set('maxatt', $this->League->query($query));
		
	}
	
	function leaguedetails($id = null){
		
		$id = Sanitize::escape($id);
		
		$league = $this->League->find(
			'all',
			array(
				'conditions' => array('League.id' => $id)
    		)
  		);
		$this->set('league', $league);

		
		$this->loadModel('Season');
		$maxseason = $this->Season->find('first', array('fields' => array('MAX(Season.number) as maxseason')));
		$this->set('maxseason', $maxseason[0]['maxseason']);
		
		
		//If it's a cup we load the details of the current stage
		if ($league[0]['League']['cup'] == true){
			
			////////////////
			//TODO: create model for competition_stages
			////////////////
			
			$stage = $this->League->query("SELECT * FROM competition_stages WHERE league_id=" . $league[0]['League']['id'] . " AND number=" . $league[0]['League']['current_stage']);
			$this->set('stage', $stage);
			
			$allStages = $this->League->query("SELECT * FROM competition_stages WHERE league_id=" . $league[0]['League']['id'] . " Order by number");
			$this->set('allStages', $allStages);
			
		}
		
		if ($this->RequestHandler->isPost()){
			if (isset($_POST['slSeason'])){
				$selectedSeason = Sanitize::escape($_POST['slSeason']);
			}else{
				$selectedSeason = $maxseason[0]['maxseason'];
			}  
			if (isset($_POST['slStage'])){
				$newStageNumber = Sanitize::escape($_POST['slStage']);
				$selectedStage = $this->League->query("SELECT * FROM competition_stages WHERE league_id=" . $league[0]['League']['id'] . " AND number=" . $newStageNumber);
			}else{
				$selectedStage = $stage;
			}  
		}
		else{
			$selectedSeason = $maxseason[0]['maxseason'];
			$selectedStage = $stage;
		}
		$this->set('selectedStage', $selectedStage);
						
		//If it's a cup in a group stage we load the details of the groups
		if ($league[0]['League']['cup'] == true){
			
			if (isset($selectedStage[0][0]) && $selectedStage[0][0]['knockout'] != 1){
				
				$query = "SELECT c.id, c.clubname, group_name, " .
				"(SELECT count(*) FROM matches WHERE status=2 AND (hometeamid=c.id OR awayteamid=c.id) AND stage_id=" .$selectedStage[0][0]['id'] . " AND season_id=" . $selectedSeason . ") AS matches, " .
				"(SELECT count(*) FROM matches WHERE status=2 AND hometeamid=c.id AND hometeamgoals>awayteamgoals AND stage_id=" .$selectedStage[0][0]['id'] . " AND season_id=" . $selectedSeason . ") * 3 + " . 
				"(SELECT count(*) FROM matches WHERE status=2 AND awayteamid=c.id AND hometeamgoals<awayteamgoals AND stage_id=" .$selectedStage[0][0]['id'] . " AND season_id=" . $selectedSeason . ") * 3 + " .
				"(SELECT count(*) FROM matches WHERE status=2 AND (hometeamid=c.id OR awayteamid=c.id) AND hometeamgoals=awayteamgoals AND stage_id=" .$selectedStage[0][0]['id'] . " AND season_id=" . $selectedSeason . ") AS points, " .
				"(SELECT COALESCE(sum(hometeamgoals), 0) FROM matches WHERE status=2 AND hometeamid=c.id AND stage_id=" .$selectedStage[0][0]['id'] . " AND season_id=" . $selectedSeason . ") + " .
				"(SELECT COALESCE(sum(awayteamgoals), 0) FROM matches WHERE status=2 AND awayteamid=c.id AND stage_id=" .$selectedStage[0][0]['id'] . " AND season_id=" . $selectedSeason . ") AS goalsfor, " .
				"(SELECT COALESCE(sum(awayteamgoals), 0) FROM matches WHERE status=2 AND hometeamid=c.id AND stage_id=" .$selectedStage[0][0]['id'] . " AND season_id=" . $selectedSeason . ") + " .
				"(SELECT COALESCE(sum(hometeamgoals), 0) FROM matches WHERE status=2 AND awayteamid=c.id AND stage_id=" .$selectedStage[0][0]['id'] . " AND season_id=" . $selectedSeason . ") AS goalsagainst, " .
				"(SELECT COALESCE(sum(hometeamgoals), 0) FROM matches WHERE status=2 AND hometeamid=c.id AND stage_id=" .$selectedStage[0][0]['id'] . " AND season_id=" . $selectedSeason . ") + " .
				"(SELECT COALESCE(sum(awayteamgoals), 0) FROM matches WHERE status=2 AND awayteamid=c.id AND stage_id=" .$selectedStage[0][0]['id'] . " AND season_id=" . $selectedSeason . ") - " .
				"(SELECT COALESCE(sum(awayteamgoals), 0) FROM matches WHERE status=2 AND hometeamid=c.id AND stage_id=" .$selectedStage[0][0]['id'] . " AND season_id=" . $selectedSeason . ") - " .
				"(SELECT COALESCE(sum(hometeamgoals), 0) FROM matches WHERE status=2 AND awayteamid=c.id AND stage_id=" .$selectedStage[0][0]['id'] . " AND season_id=" . $selectedSeason . ") AS goaldiff " .
				"FROM clubs c " .
				"INNER JOIN stage_group_clubs sgc ON c.id=sgc.club_id " .
				"INNER JOIN competition_stage_groups sg ON sgc.competition_stage_group_id=sg.id " .
				"WHERE sg.stage_id=" .$selectedStage[0][0]['id'] . " AND sg.season_id=" . $selectedSeason . " " .
				"ORDER BY group_name, points desc, goaldiff desc, goalsfor desc"; 
				
				$groups = $this->League->query($query);
				$this->set('groups', $groups);
				
			}
			else{
				//If it's not a group stage we load the knockout tree
				$this->loadModel('Match');
				$cuptree = $this->Match->find('all', array(
									'conditions' => array('Match.league_id' => $league[0]['League']['id'], 'Match.cup_index >' => 0),
									'order' => array('Match.stage_id', 'Match.cup_index')
					)
				);
				$this->set('cuptree', $cuptree);
			}
		}
		
		
		$this->set('selectedSeason', $selectedSeason);
		$ss = $selectedSeason;
		$matchCond = " WHERE matchdate > (SELECT firstday FROM seasons WHERE number = $ss) AND matchdate < (SELECT lastday FROM seasons WHERE number = $ss) AND status = 2 ";
				
		$table = "SELECT *, (SELECT count(*) * 3 FROM matches $matchCond AND hometeamid=c.id AND hometeamgoals>awayteamgoals AND league_id = $id) ";
		$table = $table . " + (SELECT count(*) FROM matches $matchCond AND (hometeamid=c.id OR awayteamid=c.id) AND hometeamgoals=awayteamgoals AND league_id = $id)";
		$table = $table . " + (SELECT count(*) * 3 FROM matches $matchCond AND awayteamid=c.id AND hometeamgoals<awayteamgoals AND league_id = $id) as points, ";
		$table = $table . "(SELECT count(*) FROM matches $matchCond AND hometeamid=c.id AND hometeamgoals>awayteamgoals AND league_id = $id)";
		$table = $table . " + (SELECT count(*) FROM matches $matchCond AND awayteamid=c.id AND hometeamgoals<awayteamgoals AND league_id = $id) as wins, ";
		$table = $table . "(SELECT count(*) FROM matches $matchCond AND (hometeamid=c.id OR awayteamid=c.id) AND hometeamgoals=awayteamgoals AND league_id = $id) as draws, ";
		$table = $table . "(SELECT count(*) FROM matches $matchCond AND hometeamid=c.id AND hometeamgoals<awayteamgoals AND league_id = $id)";
		$table = $table . " + (SELECT count(*) FROM matches $matchCond AND awayteamid=c.id AND hometeamgoals>awayteamgoals AND league_id = $id) as losses, ";
		$table = $table . "(SELECT count(*) FROM matches $matchCond AND (hometeamid=c.id OR awayteamid=c.id) AND league_id = $id) as played, ";
		$table = $table . "(SELECT COALESCE(sum(hometeamgoals), 0) FROM matches $matchCond AND hometeamid=c.id AND league_id = $id)";
		$table = $table . " + (SELECT COALESCE(sum(awayteamgoals), 0) FROM matches $matchCond AND awayteamid=c.id AND league_id = $id) as gfor, ";
		$table = $table . "(SELECT COALESCE(sum(awayteamgoals), 0) FROM matches $matchCond AND hometeamid=c.id AND league_id = $id)";
		$table = $table . " + (SELECT COALESCE(sum(hometeamgoals), 0) FROM matches $matchCond AND awayteamid=c.id AND league_id = $id) as against, ";
		$table = $table . "(SELECT COALESCE(sum(hometeamgoals), 0) FROM matches $matchCond AND hometeamid=c.id AND league_id = $id)";
		$table = $table . " + (SELECT COALESCE(sum(awayteamgoals), 0) FROM matches $matchCond AND awayteamid=c.id AND league_id = $id)";
		$table = $table . " - (SELECT COALESCE(sum(awayteamgoals), 0) FROM matches $matchCond AND hometeamid=c.id AND league_id = $id)";
		$table = $table . " - (SELECT COALESCE(sum(hometeamgoals), 0) FROM matches $matchCond AND awayteamid=c.id AND league_id = $id) as difference ";
		$table = $table . "FROM clubs c WHERE league_id = $id ORDER BY points DESC, difference DESC, gfor DESC, played ASC, 5 ASC";
		 
		$this->set("leagueTable", $this->League->query($table)); 
		
		if ($league[0]['League']['cup'] == true && isset($selectedStage[0][0])){
			$this->set("matchesPlayed", $this->League->query("SELECT *, (SELECT clubname FROM clubs WHERE id=m.homeTeamID) as hometeamname, (SELECT id FROM clubs WHERE id=m.homeTeamID) as hometeamid, (SELECT clubname FROM clubs WHERE id=m.awayTeamID) as awayteamname, (SELECT id FROM clubs WHERE id=m.awayTeamID) as awayteamid, (SELECT stadiumname FROM stadiums WHERE id = m.stadium_id) as stadiumname FROM matches m WHERE season_id = (SElECT id FROM seasons WHERE number = $ss) AND stage_id=" . $selectedStage[0][0]['id'] . " AND status=2 AND league_id= $id ORDER BY matchdate DESC"));
		}
		else
		{
			$this->set("matchesPlayed", $this->League->query("SELECT *, (SELECT clubname FROM clubs WHERE id=m.homeTeamID) as hometeamname, (SELECT id FROM clubs WHERE id=m.homeTeamID) as hometeamid, (SELECT clubname FROM clubs WHERE id=m.awayTeamID) as awayteamname, (SELECT id FROM clubs WHERE id=m.awayTeamID) as awayteamid, (SELECT stadiumname FROM stadiums WHERE id = m.stadium_id) as stadiumname FROM matches m WHERE matchdate > (SELECT firstday FROM seasons WHERE number = $ss) AND matchdate < (SELECT lastday FROM seasons WHERE number = $ss) AND status=2 AND league_id= $id ORDER BY matchdate DESC"));
		}
		
		if ($league[0]['League']['cup'] == true && isset($selectedStage[0][0])){
			$this->set("matchesToPlay", $this->League->query("SELECT *, (SELECT clubname FROM clubs WHERE id=m.homeTeamID) as hometeamname, (SELECT id FROM clubs WHERE id=m.homeTeamID) as hometeamid, (SELECT clubname FROM clubs WHERE id=m.awayTeamID) as awayteamname, (SELECT id FROM clubs WHERE id=m.awayTeamID) as awayteamid, (SELECT stadiumname FROM stadiums WHERE id = m.stadium_id) as stadiumname FROM matches m WHERE season_id = (SElECT id FROM seasons WHERE number = $ss) AND stage_id=" . $selectedStage[0][0]['id'] . " AND status=0 AND league_id= $id ORDER BY matchdate ASC"));
		}
		else
		{
			$this->set("matchesToPlay", $this->League->query("SELECT *, (SELECT clubname FROM clubs WHERE id=m.homeTeamID) as hometeamname, (SELECT id FROM clubs WHERE id=m.homeTeamID) as hometeamid, (SELECT clubname FROM clubs WHERE id=m.awayTeamID) as awayteamname, (SELECT id FROM clubs WHERE id=m.awayTeamID) as awayteamid, (SELECT stadiumname FROM stadiums WHERE id = m.stadium_id) as stadiumname FROM matches m WHERE matchdate > (SELECT firstday FROM seasons WHERE number = $ss) AND matchdate < (SELECT lastday FROM seasons WHERE number = $ss) AND status=0 AND league_id= $id ORDER BY matchdate ASC"));
		}
		
		
		
  }
  
	function leagueplayerstats($id = null){
		
		$id = Sanitize::escape($id);
		
		$this->set('league', $this->League->find(
			'first',
			array(
				'conditions' => array('League.id' => $id),
				'recursive' => -1
			)
		));
		
		$this->loadModel('Season');
		$maxseason = $this->Season->find('first', array('fields' => array('MAX(Season.number) as maxseason')));
		$this->set('maxseason', $maxseason[0]['maxseason']);
		
	}
	
	function loadleagueplayerstats($id = null, $screen = null, $ss = 1){
		$this->autoRender = false;	
			
		$id = Sanitize::escape($id);
		$screen = Sanitize::escape($screen);
		$ss = Sanitize::escape($ss);
			
		if ($screen == null || $screen == 1){					
		//TOP SCORERS//
		$this->set("topScorers", $this->League->query("SELECT p.id, firstname, lastname, clubname, cl.id as club_id, sum(goals) as goals, count(*) as matches from persons p inner join match_playerstats s on p.id=s.person_id inner join matches m on m.id=s.match_id left outer join contracts c on p.id=c.person_id and (enddate > now() OR enddate > (SELECT lastday FROM seasons WHERE number = $ss)) and startdate < (SELECT lastday FROM seasons WHERE number = $ss) and acceptedbyplayer='t' left outer join clubs cl on c.club_id=cl.id where matchdate > (SELECT firstday FROM seasons WHERE number = $ss) AND matchdate < (SELECT lastday FROM seasons WHERE number = $ss) and m.league_id=$id and rating > 0 group by firstname, lastname, clubname, p.id, cl.id order by sum(goals) desc, matches LIMIT 20;"));
		}
		elseif ($screen == 2){
		//TOP ASSISTS//
		$this->set("topAssists", $this->League->query("SELECT p.id, firstname, lastname, clubname, cl.id as club_id, sum(assists) as assists, count(*) as matches from persons p inner join match_playerstats s on p.id=s.person_id inner join matches m on m.id=s.match_id left outer join contracts c on p.id=c.person_id and (enddate > now() OR enddate > (SELECT lastday FROM seasons WHERE number = $ss)) and startdate < (SELECT lastday FROM seasons WHERE number = $ss) and acceptedbyplayer='t' left outer join clubs cl on c.club_id=cl.id where matchdate > (SELECT firstday FROM seasons WHERE number = $ss) AND matchdate < (SELECT lastday FROM seasons WHERE number = $ss) and m.league_id=$id and rating > 0 group by firstname, lastname, clubname, p.id, cl.id order by sum(assists) desc, matches LIMIT 20;"));
		}
		elseif ($screen == 3){
		//HIGHEST AVG. RATING//
		 $this->set("topRating", $this->League->query("SELECT p.id, firstname, lastname, clubname, cl.id as club_id, round(avg(rating)::numeric, 2) as rating, count(*) as matches from persons p inner join match_playerstats s on p.id=s.person_id inner join matches m on m.id=s.match_id left outer join contracts c on p.id=c.person_id and (enddate > now() OR enddate > (SELECT lastday FROM seasons WHERE number = $ss)) and startdate < (SELECT lastday FROM seasons WHERE number = $ss) and acceptedbyplayer='t' left outer join clubs cl on c.club_id=cl.id where matchdate > (SELECT firstday FROM seasons WHERE number = $ss) AND matchdate < (SELECT lastday FROM seasons WHERE number = $ss) and m.league_id=$id and rating > 0 group by firstname, lastname, clubname, p.id, cl.id order by avg(rating) desc LIMIT 20;"));
		}
		elseif ($screen == 4){
		//HIGHEST GOALS PER SHOTS//
		 $this->set("topShooter", $this->League->query("SELECT * FROM (SELECT p.id, firstname, lastname, clubname, cl.id as club_id, sum(shots) as shots, sum(goals) as goals, count(*) as matches, round(sum(goals)::numeric * 100.0 / sum(shots)::numeric, 2) as percent from persons p inner join match_playerstats s on p.id=s.person_id inner join matches m on m.id=s.match_id left outer join contracts c on p.id=c.person_id and (enddate > now() OR enddate > (SELECT lastday FROM seasons WHERE number = $ss)) and startdate < (SELECT lastday FROM seasons WHERE number = $ss) and acceptedbyplayer='t' left outer join clubs cl on c.club_id=cl.id where matchdate > (SELECT firstday FROM seasons WHERE number = $ss) AND matchdate < (SELECT lastday FROM seasons WHERE number = $ss) and m.league_id=$id and rating > 0 group by firstname, lastname, clubname, p.id, cl.id) tmp WHERE shots > 10 order by percent desc, shots desc LIMIT 20;"));
		}
		elseif ($screen == 5){
		//HIGHEST PASSES ON TARGET//
		 $this->set("topPasser", $this->League->query("SELECT * FROM (SELECT p.id, firstname, lastname, clubname, cl.id as club_id, sum(passattempts) as passes, sum(passessucceeded) as passesontarget, count(*) as matches, round(sum(passessucceeded)::numeric * 100.0 / sum(passattempts)::numeric, 2) as percent from persons p inner join match_playerstats s on p.id=s.person_id inner join matches m on m.id=s.match_id left outer join contracts c on p.id=c.person_id and (enddate > now() OR enddate > (SELECT lastday FROM seasons WHERE number = $ss)) and startdate < (SELECT lastday FROM seasons WHERE number = $ss) and acceptedbyplayer='t' left outer join clubs cl on c.club_id=cl.id where matchdate > (SELECT firstday FROM seasons WHERE number = $ss) AND matchdate < (SELECT lastday FROM seasons WHERE number = $ss) and m.league_id=$id and rating > 0 group by firstname, lastname, clubname, p.id, cl.id) tmp WHERE passes > 20 order by percent desc, passes desc LIMIT 20;"));
		}
		elseif ($screen == 6){
		//MOST TACKLES+INTERCEPTIONS PER GAME//
		 $this->set("topDefender", $this->League->query("SELECT * FROM (SELECT p.id, firstname, lastname, clubname, cl.id as club_id, sum(successfultackles+interceptions) as tackles, count(*) as matches, round(sum(successfultackles+interceptions)::numeric / count(*)::numeric, 2) as ratio from persons p inner join match_playerstats s on p.id=s.person_id inner join matches m on m.id=s.match_id left outer join contracts c on p.id=c.person_id and (enddate > now() OR enddate > (SELECT lastday FROM seasons WHERE number = $ss)) and startdate < (SELECT lastday FROM seasons WHERE number = $ss) and acceptedbyplayer='t' left outer join clubs cl on c.club_id=cl.id where matchdate > (SELECT firstday FROM seasons WHERE number = $ss) AND matchdate < (SELECT lastday FROM seasons WHERE number = $ss) and m.league_id=$id and rating > 0 group by firstname, lastname, clubname, p.id, cl.id) tmp WHERE matches > 10 order by ratio desc, matches desc LIMIT 20;"));
		}
		elseif ($screen == 7){
		//BEST KEEPERS//
		 $this->set("topKeeper", $this->League->query("SELECT * FROM (SELECT p.id, firstname, lastname, clubname, cl.id as club_id, sum(saves) as saves, sum(saveattempts) as saveattempts, count(*) as matches, round(sum(saves)::numeric * 100.0 / sum(saveattempts)::numeric, 2) as percent from persons p inner join match_playerstats s on p.id=s.person_id inner join matches m on m.id=s.match_id left outer join contracts c on p.id=c.person_id and (enddate > now() OR enddate > (SELECT lastday FROM seasons WHERE number = $ss)) and startdate < (SELECT lastday FROM seasons WHERE number = $ss) and acceptedbyplayer='t' left outer join clubs cl on c.club_id=cl.id where matchdate > (SELECT firstday FROM seasons WHERE number = $ss) AND matchdate < (SELECT lastday FROM seasons WHERE number = $ss) and m.league_id=$id and rating > 0 group by firstname, lastname, clubname, p.id, cl.id) tmp WHERE saveattempts > 10 order by percent desc, saves desc LIMIT 20;"));
		}		
		$this->set('screen', $screen); 
		
		$this->layout = 'ajax';
		$this->render('/Elements/leagueplayerstats');
	}
	
	function loadleagueclubstats($id = null, $screen = null, $ss = 1){
		$this->autoRender = false;	
			
		$id = Sanitize::escape($id);
		$screen = Sanitize::escape($screen);
		$ss = Sanitize::escape($ss);
							
		$this->loadModel('Match');
		
		//If the user doesn't request a specific screen or screen is set to 1 we show attendance stats
		if ($screen == null || $screen == 1){
			$this->set('homeatt', $this->Match->find('all', array(
				'fields' => array('HomeTeam.id', 'HomeTeam.clubname', 'sum(attendance) as att', 'min(attendance) as min', 'max(attendance) as max', 'avg(attendance) as avg'),
				'conditions' => array(
					'Match.league_id' => $id, 
					'matchdate > (SELECT firstday FROM seasons WHERE number = ' . $ss . ')', 
					'matchdate < (SELECT lastday FROM seasons WHERE number = ' . $ss . ')'
					),
				'group' => array('HomeTeam.id', 'HomeTeam.clubname'),
				'order' => 'att desc'	
				)
			));
		}
		elseif ($screen == 2){
			
			$query = "select clubname, (select array_to_string(array_agg(\"case\"), '-') from ";
			$query = $query . "(select CASE WHEN (hometeamid=c.id and hometeamgoals > awayteamgoals) OR (awayteamid=c.id and awayteamgoals > hometeamgoals) ";
			$query = $query . "THEN '3' WHEN hometeamgoals=awayteamgoals THEN '1' ELSE '0' END from matches where (hometeamid=c.id or awayteamid=c.id) ";
			$query = $query . "and status = 2 and league_id = $id order by matchdate desc limit 5) tmp) as form, ";
			$query = $query . "(select sum(points) from ";
			$query = $query . "(select CASE WHEN (hometeamid=c.id and hometeamgoals > awayteamgoals) OR (awayteamid=c.id and awayteamgoals > hometeamgoals) ";
			$query = $query . "THEN 3 WHEN hometeamgoals=awayteamgoals THEN 1 ELSE 0 END as points from matches where (hometeamid=c.id or awayteamid=c.id) ";
			$query = $query . "and status = 2 and league_id = $id order by matchdate desc limit 5) tmp3) as points, ";
			$query = $query . "(select sum(goals) from ";
			$query = $query . "(select CASE WHEN hometeamid=c.id THEN hometeamgoals ";
			$query = $query . "WHEN awayteamid=c.id THEN awayteamgoals END as goals from matches where (hometeamid=c.id or awayteamid=c.id) ";
			$query = $query . "and status = 2 and league_id = $id order by matchdate desc limit 5) tmp2) as for, ";
			$query = $query . "(select sum(goals) from ";
			$query = $query . "(select CASE WHEN hometeamid=c.id THEN awayteamgoals ";
			$query = $query . "WHEN awayteamid=c.id THEN hometeamgoals END as goals from matches where (hometeamid=c.id or awayteamid=c.id) ";
			$query = $query . "and status = 2 and league_id = $id order by matchdate desc limit 5) tmp2) as against from clubs c order by points desc, form desc;";
			
			$this->set('form', $this->Match->query($query));
		}
		
		$this->set('screen', $screen); 
		
		$this->layout = 'ajax';
		$this->render('/Elements/leagueclubstats');
	}
	
	function leagueclubstats($id = null){
		
		$id = Sanitize::escape($id);
		
		$this->set('league', $this->League->find(
			'first',
			array(
				'conditions' => array('League.id' => $id),
				'recursive' => -1
			)
		));
		
		$this->loadModel('Season');

		$maxseason = $this->Season->find('first', array('fields' => array('MAX(Season.number) as maxseason')));
		$this->set('maxseason', $maxseason[0]['maxseason']);

	}
}


?>