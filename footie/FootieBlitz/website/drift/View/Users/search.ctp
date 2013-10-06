<?php 
echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
echo $this->Html->css('http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/themes/smoothness/jquery-ui.css');
echo $this->Html->script('https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js');
echo $this->Html->script('tablesorter'); 

global $user;

$firstSelect = "";
$secondSelect = "";
$sel1 = "";
$sel2 = "";
$select1 = "";
$select2 = "";
$fname = "";
$lname = "";
$club = "";
$agent= "";
$contract = "";
$cont = "";


?>
<form method="post">
<table>
<tr>
<td>Firstname</td>
<td><input type="text" name="fname" value="<?PHP  if(isset($_POST['fname'])) echo $_POST['fname']; else echo '';?>"></td>
<td>Agent</td>
<td><input type="text" name="agent" value="<?PHP  if(isset($_POST['agent'])) echo $_POST['agent']; else echo '';?>"></td>
<td>Contract</td>
<td><select name="contract">
            <option value="none" <?PHP if($contract == "none") echo 'selected="selected"'; ?>></option>
            <option value="unemployed" <?PHP if($cont == "unemployed") echo 'selected="selected"'; ?>>Unemployed</option>
            <option value="expiring1" <?PHP if($cont == "expiring1") echo 'selected="selected"'; ?>>Expiring within 1 week</option>
</select></td>
</tr>
<tr>
<td>Lastname</td>
<td><input type="text" name="lname" value="<?PHP  if(isset($_POST['lname'])) echo $_POST['lname']; else echo '';?>"></td>
<td><select name="select1">
            <option value="acceleration" <?PHP if($select1 == "acceleration") echo 'selected="selected"'; ?>>Acceleration</option>
            <option value="topspeed" <?PHP if($select1 == "topspeed") echo 'selected="selected"'; ?>>Top Speed</option>
            <option value="agility" <?PHP if($select1 == "agility") echo 'selected="selected"'; ?>>Agility</option>
            <option value="strength" <?PHP if($select1 == "strength") echo 'selected="selected"'; ?>>Strength</option>
            <option value="jumping" <?PHP if($select1 == "jumping") echo 'selected="selected"'; ?>>Jumping</option>
            <option value="reaction" <?PHP if($select1 == "reaction") echo 'selected="selected"'; ?>>Reaction</option>
            <option value="stamina" <?PHP if($select1 == "stamina") echo 'selected="selected"'; ?>>Stamina</option>
            <option value="dribbling" <?PHP if($select1 == "dribbling") echo 'selected="selected"'; ?>>Dribbling</option>
            <option value="shooting" <?PHP if($select1 == "shoooting") echo 'selected="selected"'; ?>>Shoot</option>
            <option value="shotpower"> <?PHP if($select1 == "shotpower") echo 'selected="selected"'; ?>Shot Power</option>
            <option value="passing" <?PHP if($select1 == "passing") echo 'selected="selected"'; ?>>Passing</option>
            <option value="technique" <?PHP if($select1 == "technique") echo 'selected="selected"'; ?>>Technique</option>
            <option value="vision" <?PHP if($select1 == "vision") echo 'selected="selected"'; ?>>Vision</option>
            <option value="marking" <?PHP if($select1 == "marking") echo 'selected="selected"'; ?>>Marking</option>
            <option value="tackling" <?PHP if($select1 == "tackling") echo 'selected="selected"'; ?>>Tackling</option>
            <option value="heading" <?PHP if($select1 == "heading") echo 'selected="selected"'; ?>>Heading</option>
            <option value="commandofarea" <?PHP if($select1 == "commandofarea") echo 'selected="selected"'; ?>>Command of Area</option>
            <option value="handling" <?PHP if($select1 == "handling") echo 'selected="selected"'; ?>>Handling</option>
            <option value="rushingout" <?PHP if($select1 == "rushingout") echo 'selected="selected"'; ?>>Rushing Out</option>
            <option value="shotstopping" <?PHP if($select1 == "shotstopping") echo 'selected="selected"'; ?>>Shot Stopping</option>
</select></td>
<td>
<?PHP
echo $this->Form->input('inSel', array(
						'label' => false,
						'name' => 'sel1',
						'value' => $sel1,
						'validate' => 'numeric',
						'after' => ' or higher',
						'div' => false
	));
	
?>	
</td>
<td></td>
<td></td>
</tr><tr>
<td>Contracted to</td>
<td><input type="text" name="club" value="<?PHP  if(isset($_POST['club'])) echo $_POST['club']; else echo '';?>"></td>
<td><select name="select2">
            <option value="acceleration" <?PHP if($select2 == "acceleration") echo 'selected="selected"'; ?>>Acceleration</option>
            <option value="topspeed"  <?PHP if($select2 == "topspeed") echo 'selected="selected"'; else if($select2 == "") echo 'selected="selected"'; ?>>Top Speed</option>
            <option value="agility" <?PHP if($select2 == "agility") echo 'selected="selected"'; ?>>Agility</option>
            <option value="strength" <?PHP if($select2 == "strength") echo 'selected="selected"'; ?>>Strength</option>
            <option value="jumping" <?PHP if($select2 == "jumping") echo 'selected="selected"'; ?>>Jumping</option>
            <option value="reaction" <?PHP if($select2 == "reaction") echo 'selected="selected"'; ?>>Reaction</option>
            <option value="stamina" <?PHP if($select2 == "stamina") echo 'selected="selected"'; ?>>Stamina</option>
            <option value="dribbling" <?PHP if($select2 == "dribbling") echo 'selected="selected"'; ?>>Dribbling</option>
            <option value="shooting" <?PHP if($select2 == "shoooting") echo 'selected="selected"'; ?>>Shoot</option>
            <option value="shotpower"> <?PHP if($select2 == "shotpower") echo 'selected="selected"'; ?>Shot Power</option>
            <option value="passing" <?PHP if($select2 == "passing") echo 'selected="selected"'; ?>>Passing</option>
            <option value="technique" <?PHP if($select2 == "technique") echo 'selected="selected"'; ?>>Technique</option>
            <option value="vision" <?PHP if($select2 == "vision") echo 'selected="selected"'; ?>>Vision</option>
            <option value="marking" <?PHP if($select2 == "marking") echo 'selected="selected"'; ?>>Marking</option>
            <option value="tackling" <?PHP if($select2 == "tackling") echo 'selected="selected"'; ?>>Tackling</option>
            <option value="heading" <?PHP if($select2 == "heading") echo 'selected="selected"'; ?>>Heading</option>
            <option value="commandofarea" <?PHP if($select2 == "commandofarea") echo 'selected="selected"'; ?>>Command of Area</option>
            <option value="handling" <?PHP if($select2 == "handling") echo 'selected="selected"'; ?>>Handling</option>
            <option value="rushingout" <?PHP if($select2 == "rushingout") echo 'selected="selected"'; ?>>Rushing Out</option>
            <option value="shotstopping" <?PHP if($select2 == "shotstopping") echo 'selected="selected"'; ?>>Shot Stopping</option>
</select></td><td>
<?PHP
//echo $html->input('User/sel2', array('size' => '60'));  
// display an error message if data doesn't validate  
//echo $html->tagErrorMsg('User/sel2', 'Please enter the Title.'); 

echo $this->Form->input('inSel', array(
						'label' => false,
						'name' => 'sel2',
						'value' => $sel2,
						'validate' => 'numeric',
						'after' => ' or higher',
						'div' => false
	));

 
?>
</td>
<td></td>
<td></td>
<!--<td><input type="text" name="sel2" style="width:30px;" value="<?PHP  //if($sel2 != "") echo $sel2; ?>"> or higher</td>-->
</tr>
</table>
<input type="submit" value="Search" name="submit"/>
</form>
<?PHP


?>
<table id="myTable" class="tablesorter" cellpadding="0" cellspacing="0" style="width:100%;">
<thead>
<tr>
<th style="cursor:pointer; width:16%">Name</td>
<th style="cursor:pointer; width:13%">Club</td>
<th style="cursor:pointer; width:13%">Agent</td>
<th style="cursor:pointer; width:2.9%" title="Acceleration">Ac</td>
<th style="cursor:pointer; width:2.9%" title="Top speed">TS</td>
<th style="cursor:pointer; width:2.9%" title="Agility">Ag</td>
<th style="cursor:pointer; width:2.9%" title="Strength">St</td>
<th style="cursor:pointer; width:2.9%" title="Jumping">Ju</td>
<th style="cursor:pointer; width:2.9%" title="Reaction">Re</td>
<th style="cursor:pointer; width:2.9%" title="Stamina">St</td>
<th style="cursor:pointer; width:2.9%" title="Dribbling">Dr</td>
<th style="cursor:pointer; width:2.9%" title="Shooting">Sh</td>
<th style="cursor:pointer; width:2.9%" title="Shot power">SP</td>
<th style="cursor:pointer; width:2.9%" title="Passing">Pa</td>
<th style="cursor:pointer; width:2.9%" title="Technique">Te</td>
<th style="cursor:pointer; width:2.9%" title="Vision">Vi</td>
<th style="cursor:pointer; width:2.9%" title="Marking">Ma</td>
<th style="cursor:pointer; width:2.9%" title="Tackling">Ta</td>
<th style="cursor:pointer; width:2.9%" title="Heading">He</td>
<th style="cursor:pointer; width:2.9%" title="Command of Area">CA</td>
<th style="cursor:pointer; width:2.9%" title="Handling">Ha</td>
<th style="cursor:pointer; width:2.9%" title="Rushing Out">RU</td>
<th style="cursor:pointer; width:2.9%" title="Shot Stopping">SS</td>
</tr>
</thead>
<tbody>
<?PHP

if(isset($_POST['submit'])){
//debug($selectquery);	
//debug($result10);
foreach ($result10 as $person){
?>
 <tr onMouseOver="onHover(this)" onMouseOut="offHover(this)">
<?PHP 
		echo '<td>' .$this->Html->link(h($person[0]['firstname'] . ' ' . $person[0]['lastname']), '/person/persondetails/' . $person[0]['pid']) . '</td>';
		if($person[0]['clubname'] != "")echo '<td>' . $this->Html->link($person[0]['clubname'], '/clubs/clubdetails/' .$person[0]['club_id']) . '</td>';
		else echo '<td>Unemployed</td>';
		if(isset($person[0]['id']) && $person[0]['id'] > 0) echo '<td>' . $this->Html->link(h($person[0]['username']), 'users/userdetails/' . $person[0]['user_id']) . '</td>';
		else echo '<td>NPC</td>';
		echo '<td class="acc" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['acceleration'], 0) . '</td>
       <td class="tps" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['topspeed'], 0) . '</td>
       <td class="agi" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['agility'], 0) . '</td>
       <td class="str" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['strength'], 0) . '</td>
       <td class="jum" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['jumping'], 0) . '</td>
       <td class="rea" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['reaction']) . '</td>
       <td class="sta" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['stamina']) . '</td>
       <td class="drb" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['dribbling']) . '</td>
       <td class="sho" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['shooting']) . '</td>
       <td class="spo" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['shotpower']) . '</td>
       <td class="pas" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['passing']) . '</td>
       <td class="tec" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['technique']) . '</td>
       <td class="vis" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['vision']) . '</td>
       <td class="mar" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['marking']) . '</td>
       <td class="tac" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['tackling']) . '</td>
       <td class="hea" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['heading']) . '</td>
       <td class="coa" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['commandofarea']) . '</td>
       <td class="han" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['handling']) . '</td>
       <td class="rou" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['rushingout']) . '</td>
       <td class="sts" onMouseOver="tdOnHover(this)" onMouseOut="tdOffHover(this)">' . round($person[0]['shotstopping']) . '</td></tr>';
}
}
echo '</tbody>';                    
echo '</table>';  

//Paginator page-backward/forward
// echo '<div>';
// echo $this->Paginator->prev(
    // '<< Previous',
    // array(
      // 'class' => 'PrevPg'
    // ),
    // null,
    // array(
      // 'class' => 'PrevPg DisabledPgLk'
    // )
  // ).
  // $this->Paginator->numbers().
  // $this->Paginator->next(
    // 'Next >>',
    // array(
      // 'class' => 'NextPg'
    // ),
    // null,
    // array(
      // 'class' => 'NextPg DisabledPgLk'
    // )
  // ),
  // array(
    // 'style' => 'width: 100%;'
  // );  
// echo '</div>';
?>

<SCRIPT>
$(document).ready(function(){
	$("#myTable").tablesorter();
});

function offHover(box) {
box.style.background='#EFEFEF';
box.style.fontWeight='normal';
var elements = $(box).children();
for(var i = 0; i<elements.length; i++){
var e = elements[i];
//$(e).css("background","#EFEFEF");
$(e).css("fontWeight","normal");
}
}
function onHover(box) {
box.style.background='#C7C7C7';
box.style.fontWeight='bold';
var elements = $(box).children();
for(var i = 0; i<elements.length; i++){
var e = elements[i];
//alert($(e).attr("class"));
//$(e).css("background","#C7C7C7");
$(e).css("fontWeight","bold");
}
}
function tdOnHover(box){
var e = "." + $(box).attr("class");
//$(e).css("background","#C7C7C7");
$(e).css("fontWeight","bold");
}
function tdOffHover(box){
var e = "." + $(box).attr("class");
//$(e).css("background","#EFEFEF");
$(e).css("fontWeight","normal");
}
</SCRIPT>