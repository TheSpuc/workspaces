<?php

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<?php echo $this->Html->charset(); ?>
	<title>
		<?php __('CakePHP: the rapid development php framework:'); ?>
		<?php echo $title_for_layout; ?>
	</title>
	<?php
		echo $this->Html->meta('icon');

		echo $this->Html->css('cake.generic');

		//echo $scripts_for_layout;
	?>
<style>
#user.nav{
	width: 100%;
	text-align: right;
}
</style>


<!-- Google Analytics -->
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-33777245-1']);
  _gaq.push(['_setSiteSpeedSampleRate', 50]);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</head>
<body>
	<div id="container">
		<div id="header">
           	<div id="top">
            	<div id="football_logo"></div>
            	<div id="beta_logo"></div>            	
                <div id="top_text"></div>
			<?php 
			//echo $this->Html->link($this->Html->image('header.jpg', array('alt' => 'footie-online.com')), 'http://www.footie-online.com/', array('target' => '_self', 'escape' => false));

			//echo '<h1>Footie-online.com</h1>'; 
			?>
<div style="clear:both"></div>
            </div>
            <div id="menu_line">
            <ul id="menu_ul">            
                <li id="button_leagues" class="menuItem""></li>    
                 <li id="button_my_players" class="menuItem"></li>  
                	<li id="button_my_teams" class="menuItem"></li>
                      <li id="button_leagues" class="menuItem""></li>    
                 <li id="button_my_players" class="menuItem"></li>  
                	<li id="button_my_teams" class="menuItem"></li>           
            </ul>
        	</div>
            <div id="login_line"></div>
        <!-- MENU!! -->
        <?php echo $this->element('menu'); ?>   
        <!-- MENU END! -->     
		</div>        
		<div id="content">
			<div id='user.nav'>
				
			</div>
			<?php echo $this->Session->flash(); ?>

			<?php echo $content_for_layout; ?>

		</div>
		<div id="footer">
			<?php 
				echo $this->element('copyright');
				/* echo $this->Html->link(
					$this->Html->image('cake.power.gif', array('alt'=> __('CakePHP: the rapid development php framework', true), 'border' => '0')),
					'http://www.cakephp.org/',
					array('target' => '_blank', 'escape' => false)
				); */
				
			?>
		</div>
	</div>
	<?php 
		//echo $this->element('sql_dump'); 
	?>
</body>
</html>