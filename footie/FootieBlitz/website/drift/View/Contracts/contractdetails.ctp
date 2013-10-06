<?php

//echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');

//debug($contract);

echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
echo $this->Html->script('util');
echo $this->Html->script('contractdetails');

//if ($userOwnsClub || $userOwnsPerson){
	?>
	
	<div id="divcontractdetails" style="margin:0px auto; width:50%; "> 
	<h1>Contract details</h1>
	<table>
	<?php 
	
	  echo $this->Html->tableCells(array('Club', $contract['Club']['clubname']));
	  echo $this->Html->tableCells(array('Person', h($contract['Person']['firstname'] . ' ' . $contract['Person']['lastname'])));
	  echo $this->Html->tableCells(array('Role', str_replace($contract['Contract']['role'], '1', 'Player')));
	  echo $this->Html->tableCells(array('Start date', $contract['Contract']['startdate']));
	  echo $this->Html->tableCells(array('End date', $contract['Contract']['enddate']));    
	  echo $this->Html->tableCells(array('Wage', htmlentities("£ ") . number_format($contract['Contract']['weeklywage'], 0, ',', '.')));
	  echo $this->Html->tableCells(array('Goal bonus', htmlentities("£ ") . number_format($contract['Contract']['goalbonus'], 0, ',', '.')));    
	  echo $this->Html->tableCells(array('Assist bonus', htmlentities("£ ") . number_format($contract['Contract']['assistbonus'], 0, ',', '.')));      
	  echo $this->Html->tableCells(array('Release clause', htmlentities("£ ") . str_replace(number_format($contract['Contract']['minimumreleaseclause'], 0, ',', '.'), '-1', 'N/A')));       
	?>
	</table>

	<?PHP
		if ($userOwnsClub){
			?> <a href='#' id="release">Terminate contract</a> <?PHP
			echo ' ' . $this->Html->link('Offer new contract', '/contracts/newoffer/' . $contract['Person']['id']);
		}
	?>
	
	</div>
	
	<?php
	
		  $reminfo = ' Cost to release player: ';
		  if ($contract['Person']['npc'] == 1){
		       $reminfo = $reminfo . ' 0.';
		  }
		  else{
			  $reminfo = $reminfo . $remainingWages . '.';
		  }
		  ?>
		 <form method="POST" id="frmConfirm" name="frmConfirm">
		 <div id="popupContract" style="display:none; position:absolute; position:fixed; height:384px;  width:408px; z-index: 2; border:2px solid #cecece; background:#FFFFFF; padding:12px; ">  
		 <h1>Release player</h1>  
		 <p id="contactArea">  
		 You are about to release  <?php echo "" . h($contract['Person']['firstname'] . " " . $contract['Person']['lastname']) . "<br />"; ?>
		 <br/>
		 <?PHP 
		 	echo $reminfo; 
		 	
		 	if ($contract['Person']['npc'] != 1){
		 		?>
		 		<br/><br/>
		 		Message to player: <textarea name="message" style="width:100%; Height:50px" /></textarea>
			<?PHP 
			}
			?>
			
		 	<input type="submit" id="rlaccept" name="rlaccept" value="Accept" />
		 	<input type="button" id="rlcancel" name="rlcancel" value="Cancel" />
		
		 </p>  
		 </div>  
		 </form>
		 <div id="backgroundPopup" style="background:#000000; display:none; height:100%; width:100%; z-index:1; position:fixed; top:0; left:0;"></div>  

<?PHP
// }
// else{
// 	echo 'Only the owner of the club and the agent of the person who have signed this contract can view the details.';
// 	$this->Session->flash('Only the owner of the club and the agent of the person who have signed this contract can view the details.');
//}

//Nedenstående skal med allersidst når der er script på siden. Se http://book.cakephp.org/view/1594/Using-a-specific-Javascript-engine
echo $this->Js->writeBuffer(); 
?>
