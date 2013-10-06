<?php 

			 $fktaker = -1;
			 $captain = -1;
			 $throwleft = -1;
			 $throwright = -1;
			 $penalty= -1;
			 $fklong= -1;
			 $cornerright= -1;
			 $cornerleft= -1;
			 $targetman= -1;
			 
   	     if(isset($teamtactics[0][0]['id'])){
			           $fktaker = $teamtactics[0][0]['freekickshort'];
			           $captain= $teamtactics[0][0]['captain'];
			           $throwleft= $teamtactics[0][0]['throwinleft'];
			           $throwright= $teamtactics[0][0]['throwinright'];
			           $penalty= $teamtactics[0][0]['penaltytaker'];
			           $fklong= $teamtactics[0][0]['freekicklong'];
			           $cornerright= $teamtactics[0][0]['cornerright'];
			           $cornerleft= $teamtactics[0][0]['cornerleft'];
			           $targetman= $teamtactics[0][0]['targetman'];
			    }
			
			?>
			
			<table>
			<tr><td>
			Captain: </td><td><select id="captain" name="captain">
			<?php
			$i = 0;
			if(in_array($captain,$allIds) == false) {
			  echo "<option value='-1' selected>Not selected</option>";
			}
			foreach ($allNames as $value) {
			
			  if ($allIds[$i] == $captain){
			  echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
			  }
			  else{
			  echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
			  }
			  $i = $i + 1;
			  }
			?>
			</select></td></tr>
			<tr><td>
			Free kicks (short): </td><td><select id="freekicktaker" name="freekicktaker">
			<?php
			$i = 0;
			if(in_array($fktaker,$allIds) == false) {
			  echo "<option value='-1' selected>Not selected</option>";
			}
			foreach ($allNames as $value) {
			  if ($allIds[$i] == $fktaker){
			  echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
			  }
			  else{
			  echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
			  }
			  $i = $i + 1;
			  }
			?>
			</select></td></tr>
			<tr><td>
			Free kicks (long): </td><td><select id="freekicktakerlong" name="freekicktakerlong">
			<?php
			$i = 0;
			if(in_array($fklong,$allIds) == false) {
			  echo "<option value='-1' selected>Not selected</option>";
			}
			foreach ($allNames as $value) {
			  if ($allIds[$i] == $fklong){
			  echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
			  }
			  else{
			  echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
			  }
			  $i = $i + 1;
			  }
			?>
			</select></td></tr>
			<tr><td>
			Penalty: </td><td><select id="penalty" name="penalty">
			<?php
			$i = 0;
			if(in_array($penalty,$allIds) == false) {
			  echo "<option value='-1' selected>Not selected</option>";
			}
			foreach ($allNames as $value) {
			  if ($allIds[$i] == $penalty){
			  echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
			  }
			  else{
			  echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
			  }
			  $i = $i + 1;
			  }
			?>
			</select></td></tr>
			<tr><td>
			Corner right: </td><td><select id="cornerright" name="cornerright">
			<?php
			$i = 0;
			if(in_array($cornerright,$allIds) == false) {
			  echo "<option value='-1' selected>Not selected</option>";
			}
			foreach ($allNames as $value) {
			  if ($allIds[$i] == $cornerright){
			  echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
			  }
			  else{
			  echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
			  }
			  $i = $i + 1;
			  }
			?>
			</select></td></tr>
			<tr><td>
			Corner left: </td><td><select id="cornerleft" name="cornerleft">
			<?php
			$i = 0;
			if(in_array($cornerleft,$allIds) == false) {
			  echo "<option value='-1' selected>Not selected</option>";
			}
			foreach ($allNames as $value) {
			  if ($allIds[$i] == $cornerleft){
			  echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
			  }
			  else{
			  echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
			  }
			  $i = $i + 1;
			  }
			?>
			</select></td></tr>
			<tr><td>
			Throw in right: </td><td><select id="throwright" name="throwright">
			<?php
			$i = 0;
			if(in_array($throwright,$allIds) == false) {
			  echo "<option value='-1' selected>Not selected</option>";
			}
			foreach ($allNames as $value) {
			  if ($allIds[$i] == $throwright){
			  echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
			  }
			  else{
			  echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
			  }
			  $i = $i + 1;
			  }
			?>
			</select></td></tr>
			<tr><td>
			Throw in left: </td><td><select id="throwleft" name="throwleft">
			<?php
			$i = 0;
			if(in_array($throwleft,$allIds) == false) {
			  echo "<option value='-1' selected>Not selected</option>";
			}
			foreach ($allNames as $value) {
			  if ($allIds[$i] == $throwleft){
			  echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
			  }
			  else{
			  echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
			  }
			  $i = $i + 1;
			  }
			?>
			</select></td></tr>
			<tr><td>
			Target man: </td><td><select id="targetman" name="targetman">
			<?php
			$i = 0;
			
			if ($targetman == -1){
			     echo "<option value=-1 selected>No target man</option>";
			 }
			 else{
			      echo "<option value=-1>No target man</option>";
			 }
			
			foreach ($allNames as $value) {
			  if ($allIds[$i] == $targetman){
			      echo "<option value=" . $allIds[$i] . " selected>" . $value . "</option>";
			  }
			  else{
			      echo "<option value=" . $allIds[$i] . ">" . $value . "</option>";
			  }
			  $i = $i + 1;
			  }
			?>
			</select></td></tr>
			
			</table>
			<?PHP
?>