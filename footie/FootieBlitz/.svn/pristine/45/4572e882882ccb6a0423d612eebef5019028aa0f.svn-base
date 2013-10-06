<?php echo $this->Html->css('/css/menu.css'); ?>
<?php //echo $this->Html->link('/js/menu.js');?>

<ul id="nav">
	
	<?php if(isset($logged_in) && $logged_in): ?>
	
	<li class="top"><?php echo $this->Html->link($this->Html->tag('span', 'Office', array('class' => 'down')), array('plugin' => null,'controller' => 'users', 'action' =>'office'), array('class' => 'top_link', 'id' => 'office', 'escape' => false)); ?>
		<ul class="sub">
			<?php
			echo '<li>' . $this->Html->link('User profile', array('plugin' => 'forum', 'controller' => 'forumusers', 'action' =>'profile', $uid)) . '</li>';
			echo '<li>' . $this->Html->link('Bugs/new features', array('plugin' => null, 'controller' => 'tasks', 'action' =>'overview')) . '</li>';
						
			//echo '<li><a href="/forum/users/profile/' . $uid . '">User profile</a></li>';
			//echo '<li>' . $this->Html->link('User details', array('plugin' => null,'controller' => 'users', 'action' =>'userdetails', $uid)) . '</li>';
			?>
			
			<li class="mid"><a href="#nogo7" class="fly">Teams</a>
				<ul>
				<?php
					$mclubs = $menudata['mclubs'];
					foreach($mclubs as $key => $club){
						$clubname = $this->FootieText->shortenText($club['clubname'], 14);
						echo '<li>' . $this->Html->link($clubname, array('plugin' => null,'controller' => 'clubs', 'action' =>'clubdetails', $club['id'])) . '</li>';
					}
				?>
				</ul>
			</li>
			<li class="mid"><a href="#nogo7" class="fly">Leagues</a>
				<ul>
				<?php
					foreach($menudata['mallleagues'] as $league){
						$leaguename = $this->FootieText->shortenText($league[0]['leaguename'], 14);
						echo '<li>' . $this->Html->link($leaguename, array('plugin' => null,'controller' => 'leagues', 'action' =>'leaguedetails', $league[0]['league_id'])) . '</li>';
					}
				?>
				</ul>
			</li>
			<li class="mid"><a href="#nogo7" class="fly">Players</a>
				<ul>
				<?php
					$mplayers = $menudata['mplayers'];
					foreach($mplayers as $key => $player){
						$name = $this->FootieText->shortenText(h($player['firstname'] . ' ' . $player['lastname']), 14);
						echo '<li>' . $this->Html->link($name, array('plugin' => null,'controller' => 'person', 'action' =>'persondetails', $player['id'])) . '</li>';
					}
				?>
				</ul>
			</li>
		</ul>
	</li>
	<li class="top"><a href="#nogo2" id="leagues" class="top_link"><span class="down">Leagues</span></a>
		<ul class="sub">
			<li><a href="#nogo3" class="fly">World</a>
					<ul>
						<!--<li><a href="#nogo5" class="fly">Africa</a>
							<ul>
								<li><a href="#nogo12" class="fly">Cameroon</a>
									<ul>
										<li><a href="#nogo12">League 1</a></li>
										<li><a href="#nogo13">League 2</a></li>
									</ul>
								</li>
								<li><a href="#nogo12" class="fly">Egypt</a>
									<ul>
										<li><a href="#nogo12">League 1</a></li>
										<li><a href="#nogo13">League 2</a></li>
									</ul>
								</li>
								<li><a href="#nogo13" class="fly">Nigeria</a>
									<ul>
										<li><a href="#nogo12">League 1</a></li>
										<li><a href="#nogo13">League 2</a></li>
									</ul>
								</li>
								<li><a href="#nogo14" class="fly">South Africa</a>
									<<ul>
										<li><a href="#nogo12">League 1</a></li>
										<li><a href="#nogo13">League 2</a></li>
									</ul>
								</li>
							</ul>
						</li>
						<li><a href="#nogo6">America</a></li>
						<li><a href="#nogo6">Asia</a></li>-->
						<li><a href="#nogo4"  class="fly">Europe</a>
							<ul>
								<li><a href="#nogo12" class="fly">Denmark</a>
									<ul><?PHP
										echo '<li>' . $this->Html->link('Liga 1 ', array('plugin' => null,'controller' => 'leagues', 'action' =>'leaguedetails', 3)) . '</li>';
										//echo '<li>' . $this->Html->link('Liga 2 ', array('plugin' => null,'controller' => 'leagues', 'action' =>'leaguedetails', 1)) . '</li>';										
										?>
									</ul>
								</li>
							</ul>
						</li>						
					</ul>
			</li>
			<li class="mid"><a href="#nogo7" class="fly">Leagues</a>
					<ul>
						<?php
					$mleagues = $menudata['mleagues'];
					foreach($mleagues as $key => $league){
						$leaguename = $this->FootieText->shortenText($league['leaguename'], 14);
						echo '<li>' . $this->Html->link($leaguename, array('plugin' => null,'controller' => 'leagues', 'action' =>'leaguedetails', $league['id'])) . '</li>';
					}
				?>
					</ul>
			</li>
		</ul>
	</li>
	<li class="top">
	<?PHP echo $this->Html->link($this->Html->tag('span', 'Search'), array('plugin' => null,'controller' => 'users', 'action' =>'search'), array('class' => 'top_link', 'id' => 'search', 'escape' => false));
	?>
	</li>
	<?PHP
	$forumLinkText = "Forum";
	if ($newforummsgcount[0][0]['count'] > 0){
		$forumLinkText = $forumLinkText . " (" . $newforummsgcount[0][0]['count'] . ")";
	}
	?>
	<li class="top"><a href="/forum/" id="contacts" class="top_link"><span class="down"><?PHP echo $forumLinkText; ?> </span></a>
		<ul class="sub">			
			<li><?PHP echo $this->Html->link('Home', array('plugin' => 'forum','controller' => 'forum', 'action' =>'index'));?></li>
			<li><?PHP echo $this->Html->link('Dashboard', array('plugin' => 'forum','controller' => 'forumusers', 'action' =>'dashboard'));?></li>
			<li><?PHP echo $this->Html->link('New Posts', array('plugin' => 'forum','controller' => 'search', 'action' =>'index/new_posts'));?></li>
			<li><?PHP echo $this->Html->link('Search', array('plugin' => 'forum','controller' => 'search', 'action' =>'index'));?></li>
			<li><?PHP echo $this->Html->link('Rules', array('plugin' => 'forum','controller' => 'forum', 'action' =>'rules'));?></li>
			<li><?PHP echo $this->Html->link('Help', array('plugin' => 'forum','controller' => 'forum', 'action' =>'help'));?></li>
			<li><?PHP echo $this->Html->link('Users', array('plugin' => 'forum','controller' => 'forumusers', 'action' =>'index'));?></li>
			
			
		</ul>
	</li>

	<li class="top"><a href="/messages/inbox" id="contacts" class="top_link"><span class="down">Private Messages 
	<?php
	if($newMessages > 0){ echo ' (' . $newMessages . ')';}
	?>
	</span></a>
		<ul class="sub">
			<li><?PHP echo $this->Html->link('Inbox', array('plugin' => null,'controller' => 'messages', 'action' =>'inbox')); ?></li>
			<li><?PHP echo $this->Html->link('New Message', array('plugin' => null,'controller' => 'messages', 'action' =>'write')); ?></li>
		</ul>
	</li>
	<li class="top">
	
		<a href="/matches/live" id="contacts" class="top_link"><span>Live</span></a>
	</li>
	<li class="top">
		<a href="http://www.wiki.footie-online.com/wiki/footie/index.php/Category:Home" id="contacts" class="top_link"><span>Wiki</span></a>
	</li>
	
	<li class="top" style="float:right;"><span style="line-height: 33px;">
		Welcome, <?php echo h($users_username) . ' - '; ?> <a href="/users/logout">Logout</A>
	</span></li>
<?php else: ?>
	<li class="top"><span style="line-height: 33px;">
		<?php echo $this->Html->link('Register', array('controller' => 'users', 'action' => 'add')); ?> or <?php echo $this->Html->link('Login', array('controller' => 'users', 'action' => 'login')); ?>
		</span></li>
	<li class="top"><span style="line-height: 33px;">
		<a href="http://www.wiki.footie-online.com/wiki/footie/index.php/Category:Home" id="contacts" class="top_link"><span>Wiki</span></a>
	</span></li>
	<?php endif; ?>
</ul>
<!--MENU END -->
<!-- CALLENDER -->
<div style="width:100%; background-color:green;">
<table style="width:100%; height:100%; border-spacing: 1.12px">
<tr>
<?php
	$today = date('Y-m-d');
	$dayNr = 0;
	$left = 10;
	foreach($season['0']['Season']['seasondays'] as $days){
		
		if ($dayNr == 0){ 
			$title = "<br>Start of season";
		}
		else if($dayNr == 73){
			$title = "<br>End of Season";
		}		
		
		if($today == $days){
			echo "<td class='calendar' style='background-color: #559218;'>";			
		}
		else{
			echo "<td class='calendar'>";
		}
		echo "<div class='tooltip'><span style='left: " . $left . "px;'>" . $days . $title ."</span><div></td>";
		$title = "";
		$dayNr += 1;
		$left -= 2.9;
	}
	
?>
</tr>
</table>
</div>
<!-- CALENDAR END -->