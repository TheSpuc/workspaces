<?PHP

echo $this->Html->css('clubtactics');
echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
echo $this->Html->css('http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/themes/smoothness/jquery-ui.css');
echo $this->Html->script('https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js');
echo $this->Html->script('live');
echo $this->Html->script('chatBox');

//debug($match);
//debug($players);
//debug($initstr);
//debug($usermanagedclub);

//echo '<script type="text/javascript" src="misc/raphael-min.js"></script>';

if (isset($match['Match']['id'])){
//chatBox//
$writeText = stripslashes(htmlspecialchars($text));
if(isset($text) && $writeText != ""){ 
	$fp = fopen($logPath, 'a');  
	fwrite($fp, "<div class='msgln'>(".date("g:i A").") <b>".$users_username."</b>: ".$writeText."<br></div>");  
	fclose($fp); 
	if($writeText == "**clean**"){
		$fp = fopen($logPath, 'w');
		fwrite($fp, "<div class='msgln'>Welcome to the Match Chat<br></div>");
		fclose($fp);
   }
}

//Match//

echo '<input type="hidden" id="matchID" value="' . $match['Match']['id'] . '">';
echo "<input type='hidden' id='mid' name='mid' value='" . $match['Match']['id']  . "'>";
echo '<input type="hidden" name="logPath" id="logPath" value="' . $logPath . '" />';
		
echo "<input type='hidden' id='initstr' name='initstr' value='" . $initstr[0][0]['frame'] . "'>";

?>

<div id="divwatchmatch">
     <div id="livediv" class="livediv">	
            
		<div id="pitchdiv" WIDTH="1102px" HEIGHT="557px">
             <APPLET CODEBASE='/applets/live/' CODE='ClientApplet.class' WIDTH=1100 HEIGHT=555>
             <?PHP echo "<PARAM NAME=matchid VALUE='" . $match['Match']['id'] . "'>"; ?>
             </APPLET>
        </div>
        <div id="stream-wrapper"></div>

        </div>
 </div>

<div id="tabs" class="halfwidth">
	<ul>
		<li><a href="#tablineups">Lineups</a></li>
		<li><a href="#tabmatchstats">Match stats</a></li>
		<li><a href="#tabplayerstats">Player stats</a></li>
		<li><a href="#tabteamtactics">Team tactics</a></li>
		<li><a href="#tabplayertactics">Playertactics</a></li>
	</ul>
	
	<div id="tablineups" style="width: 500px;">
		<div id="divlineups">
		
		</div>
	</div>
	<div id="tabmatchstats">
		<div id="divmstats"></div>
	</div>
	<div id="tabplayerstats">
		<div id="divpstats" style=' font-size:0.8em; '></div>
	</div>
	<div id="tabteamtactics">
<?PHP
	if ($usermanagedclub > -1 || 1==1){
		echo 'Team tactics';
	   	echo '<table><tr><td width=1%>';
	
	   echo '<div id="formationdiv" style="position:relative;">';
	   echo '<img border="1" src="/img/pitch.png" class="pitch" id="pitch" width="594 px" height="372" />';
	
	   $i = 0;
	   $dx = 30;
	   $fx = 1;
	   $dy = 0;
	   $fy = 0.7;

		$arr = explode(':', $initstr[0][0]['frame']);
		
	    $i = 0;
	    $j = 0;
	 	while ($i < count($arr))
		{
			$p = explode('.', $arr[$i]);
			
			if ($p[0] == "t")
			{
	           if ($p[1] == $usermanagedclub){
	              $j = 1;
	           }
	           else{
	              $j = 0;
	           }
			}
			else if ($p[0] == "s")
			{}
			else if ($p[0] == "sc")
			{}
			else if ($p[0] == "off")
			{}
			else
			{
	            if($j > 0 && $j < 12){
		              $x = $p[4];
		              $y = $p[5];
		              if ($x > 440) {
		                    $x = 880 - $x;
		                    $y = 513 - $y;
		              }
		
		              echo "<input type='hidden' value='" . $x . "," . $y . "' name='p" . $j . "' id='p" . $j . "' />";
		              echo "<input type='hidden' value='" . $p[0] . "' name='pid" . $j . "' id='pid" . $j . "' />";
		
		              $x = $fx * $x + $dx;
		              $y = $fy * $y + $dy;
		
		              if ($i > 1){
		                     echo "<div style='left:" . $x . "px; top:"  . $y . "px; ' class='draggable' id='pos" . $j . "' name='pos" . $j ."'>" . $p[1] .". " . h($p[2] . " " . $p[3]) . "</div>";
		               }else{
		                   echo "<div style='left:" . $x . "px; top:"  . $y . "px; ' class='nondraggable' id='pos" . $j . "' name='pos" . $j ."'>" . $p[1] .". " . h($p[2] . " " . $p[3]) . "</div>";
		               }
		
		               $j++;
	            }
			}
			$i = $i + 1;
		}


?>
<input type="button" id="btnUpdTTactics" value="Update formation" />
</div>         
</td><td> 
<div style="background:#FFFFFF;" id="lineupdiv">

<?php

	$lineup = "";
	$i = 1;
	$color = "green";
    echo '<span id="lineupnames" class="lineupnames" style="position:relative;left:10px;top:10px;">';

    $go = 0;
    $i = 0;
    $j = 1;
 	while ($i < count($arr))
	{
		$p = explode('.', $arr[$i]);
		
		if ($p[0] == "t")
		{
		   if ($usermanagedclub == $p[1]) {
             	$go = 1;
           }
           else {
               $go = 0;
           }
		}
		else if ($p[0] == "s")
    	{}
        else if ($p[0] == "s")
        {}
		else if ($p[0] == "off")
		{}
		else
		{
            if($go == 1){
                if($j < 12){
   					echo "<span id='lpl" . $j . "' class='lineupname' style='color: #088A08;'> " . $p[1] .". " . h($p[2] . " " . $p[3]) . "</span>";
                }
                else{
                   echo "<span id='lpl" . $j . "' class='lineupname' style='color: #FF8000;'> " . $p[1] .". " . h($p[2] . " " . $p[3]) . "</span>";
                }
                echo "<input type='hidden' name='hllpl" . $j . "' id='hllpl" . $j . "' value='" . $p[0] . "' />";	
              
                if ($j == 1)
                     echo '(GK)';

                 echo '<br />';
                 $j++;
             }
		}
    	$i = $i + 1;
	}
				         
    echo '<div><br /> </div></span></div></td><td><div id="divroles"></div>';
    echo '<input type="button" id="btnUpdRoles" value="Update roles" />';
    //<input type="button" id="btnUpdRoles" value="Save roles" />
    echo '</td></tr></table>';
    }
?>
    <span id="tacticsinfo"></span>

	</div>
	<div id="tabplayertactics">
	<div id="hdtacs"></div>
<?php

echo '<select id="plsel" name="plsel">';

 foreach ($players as $info)
 {
 	if ($i == 0){
          echo "<option value=" . $info[0]['id'] . " selected>" . h($info[0]['firstname'] . " " . $info[0]['lastname']) . "</option>";
         $i = 1;
     }
     else{
           echo "<option value=" . $info[0]['id'] . ">" . h($info[0]['firstname'] . " " . $info[0]['lastname']) . "</option>";
     }
 }

?>
</select>
<table>
<tr><td>Dribbling</td><td><div id="sldrib" style='width:200px;'></div></td></tr>
<tr><td>Through balls</td><td><div id="slthro" style='width:200px;'></div></td></tr>
<tr><td>Forward runs</td><td><div id="slruns" style='width:200px;'></div></td></tr>
<tr><td>Long shots</td><td><div id="sllong" style='width:200px;'></div></td></tr>
<tr><td>Aggression</td><td><div id="slaggr" style='width:200px;'></div></td></tr>
<tr><td>Mentality</td><td><div id="slment" style='width:200px;'></div></td></tr>
<tr><td>Closing down</td><td><div id="slclos" style='width:200px;'></div></td></tr>
<tr><td>Crossing</td><td><div id="slcros" style='width:200px;'></div></td></tr>
<tr><td>Passing</td><td><div id="slpass" style='width:200px;'></div></td></tr>
<tr><td>Marking</td><td><div id="slmark" style='width:200px;'></div></td></tr>
<?PHP
echo '<tr><td>Forward on set pieces</td><td><input type="checkbox" id="chbxsetp" name="chbxsetp"></td></tr>';
?>
<tr><td></td><td> <input type="button" value="Save playing style" name="svstyle" id="svplst" /> <span id="styleinfo"></span></td></tr>
</table>
	</div>
</div>

<div class="chatWrapper">  
    <div class="chatMenu">  
        <p class="welcome">Welcome, <?PHP echo " " . $users_username; ?><b></b></p>  

    </div>  
      
    <div class="chatBox" id="chatBox"><?php  
	if(file_exists($logPath) && filesize($logPath) > 0){  
		$handle = fopen($logPath, "r");  
		$contents = fread($handle, filesize($logPath));  
		fclose($handle);  
		  
		echo $contents;  
	}  
?></div>  
      
    <form name="message" action="">  
        <input name="usermsg" type="text" id="usermsg" class="usermsg" size="63" />  
        <input name="submitmsg" type="submit"  id="submitmsg" value="Send" />  
    </form>  
</div>  

<!--<div id="commentarydiv" style="color:#190707;"></div> -->


<?PHP

}
else{
   echo "Currently no matches.";

}
   
   
   
if ($dispform == 1){
	echo '<form method="post">';
    echo '<input type="submit" name="submit" value="Create match now">';
    echo '</form>';
}



echo $this->Js->writeBuffer(); // Write cached scripts
?>