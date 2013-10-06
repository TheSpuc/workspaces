<?php

//if (isset($error)) {
//  echo('Invalid Login.');
//}
?>

<h1>Please log in.</h1>

<div id="divlogin" style="float:left; width: 20%;">
<?php
//Line below commented as old footie user now should be active on the site.
//<h3>If you are a user from the old Footie site then you will have to reset your password. <?php echo $this->Html->link('Please click here.', array('plugin' => null, 'controller' => 'users', 'action' => 'forgotpassword'));  </h3>


 echo $this->Form->create('User', array('action' => 'login')); 

// if ($session->check('Message.auth')) $session->flash('auth');
echo $this->Form->input('username');
echo $this->Form->input('password');
echo $this->Form->end('Login');

echo $this->Html->link('Forgot your password?', array('plugin' => null, 'controller' => 'users', 'action' => 'forgotpassword'));
?>
</div>
<div id="divabout" style="width: 70%; float:left;">
<style type="text/css">
.description{ padding-left: 40px; }
.title{ font-size: 16px;}
</style>
<div class="title"><b>Footie is a football RPG</b></div> 
<div class="description">that allows you to take on the role of a super agent, finding young talent and nurturing them to greatness.</div>
<br>
<div class="title"><b>Build your players from the ground up!</b></div>
<div class="description">
Your players start at the tender age of 14-18 years of age and are quick to learn. Make the right choices for them, and you will have a star player on your hands in a few years!</div><br>

<div class="title"><b>Find the right clubs and negotiate the right contracts for your players.</b></div>
<div class="description">
The better the club, the better the training and the faster your players develop.<br>
Money isn't just for fun, so negotiate the right contracts with the clubs.</div><br>

<div class="title"><b>A live match engine lets you follow your players as they battle for supremacy on the pitch.</b></div>
<div class="description">Watch as your player weaves his way past opposition defenders! Change playing style during the match to make the most of your opponents weaknesses.</div><br>

<div class="title"><b>Battle against other agents and their players in the online world of Footie!</b></div>
<div class="description">
Become owner of a team and balance the finances, stadium expansions and improvement on training facilities.<br>
Sign players from other agents and build a winning team.</div><br>

</div>
