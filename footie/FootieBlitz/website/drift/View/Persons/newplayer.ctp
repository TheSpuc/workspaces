<?php 
//debug($offers);
//debug($this->data);
//debug($activeClub);

echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
echo $this->Html->css('http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/themes/smoothness/jquery-ui.css');
echo $this->Html->script('https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js');
echo $this->Html->css('/css/playerstats.css');
echo $this->Html->script('playerinfo'); 
		        	
if (3 == null){

}
else{
//debug($request);
//debug($up);
?>
<div id="divcontent" style="margin: 0px auto 0px auto; width:90%;">
	<a href=''>How to create a player</a> 
	<form method="post">
		<table cellpadding="0" cellspacing="0">
			<tr title="Your player must have a firstname of 2 to 20 characters">
            	<td>First name </td>
                <td colspan="3">
                	<input type="text" name="fname" id="txtfname">
                </td>
                <td width=50%>
	                <div id="warnfname">Firstname should be 2 to 20 characters long</div>
                </td>
            </tr>
			<tr title="Your player must have a lastname of 2 to 20 characters">
            	<td>Last name </td>
                <td colspan="3">
                	<input type="text" name="lname" id="txtlname">
                </td>
                <td>
	                <div id="warnlname">Lastname should be 2 to 20 characters long</div>
                </td>
            </tr>
			<tr>
            	<td>Age</td>
                <td  id="age" name="age">14</td>
                <td  colspan="2">
					<input type="hidden" id="age" name="age" value="14"/>
      				<div id="agedown" class="agedown" style="float: left; width: 50%;">
                    	<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all">
                        	<span class="ui-icon ui-icon-circle-minus"></span>
                        </div>
                    </div>
      				<div id="ageup" class="ageup" style="float: left; width: 50%;">
                    	<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all">
                        	<span class="ui-icon ui-icon-circle-plus"></span>
                        </div>
                    </div>
				</td>
				<td>
				</td>
            </tr>
			<tr>
            	<td>Height</td>
				<input type="hidden" id="height" name="height" value="180"/>
				<td>Short: 
                	<input type="radio" name="option_layout" value="1"/>
                </td>
				<td>Medium: 
                	<input type="radio" name="option_layout" value="2"  checked="checked" />
                </td>
				<td>Tall: 
                	<input type="radio" name="option_layout" value="3" />
                </td>
       			<td>
				</td>
			</tr>
            </tr>
		</table>

<div id="divplayerattr" style="width:100%; clear: both;"> 
<table>
	<tr>
		<td style="width:50%">Distribute attribute points (<span id="divattp">100</span> remaining)<input type="hidden" id="playerpoints" name="playerpoints" value="100"/></td>
		<td>PP used: (<span id="ppspend">0</span>)</td>
	</tr>
</table>
<div style="width:50%; float: left;">
<table>
	<tr title="Represents how fast your player accelerates" >
		<td style="cursor:help; width:30%;">Acceleration</td>
		<td style="width:40%;">
			<div class="progress">
				<div id="acc" name= "acc" class="pro">20</div>
			</div>
		</td>
		<td>
			<div id="accdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="accdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="accup" class="up" style="width:37%">
				<div class="fg-button ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="accupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	</tr>
	<tr title="The top speed your player can reach">
		<td style="cursor:help;">Top Speed</td>
		<td>
			<div class="progress">
				<div id="tps" class="pro">20</div>
			</div>
		</td>
		<td>
			<div id="tpsdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="tpsdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="tpsup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="tpsupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="How tight your player turns. A higher stat means your player can turn tighter curves and loses less speed by turning">
		<td style="cursor:help;">Agility</td>
		<td>
			<div class="progress">
				<div id="agi"  class="pro">20</div>
			</div>
		</td>
		<td>
			<div id="agidownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="agidown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="agiup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="agiupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="Used for tackling and how difficult your player is to push off the ball">
		<td style="cursor:help;">Strength</td>
		<td>
			<div class="progress">
				<div id="str" class="pro">20</div>
			</div>
		</td>
		<td>
			<div id="strdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="strdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="strup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="strupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="How high your player can jump">
		<td style="cursor:help;">Jumping</td>
		<td>
			<div class="progress" >
				<div id="jum" class="pro">20</div>
			</div>
		</td>
		<td>
			<div id="jumdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="jumdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="jumup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="jumupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="How fast your player reacts. Used for all actions and decisions your player makes">
		<td style="cursor:help;">Reaction</td>
		<td>
			<div class="progress">
				<div id="rea" class="pro">20</div>
			</div>
		</td>
		<td>
			<div id="readownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="readown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="reaup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="reaupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="A low score means your player gets tired earlier and decreases his stats for the rest of the game">
		<td style="cursor:help;">Stamina</td>
		<td>
			<div class="progress">
				<div id="sta" class="pro">20</div>
			</div>
		</td>
		<td>
			<div id="stadownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="stadown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="staup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="staupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="A high score means your player loses less speed when dribbling, and better control of the ball">
		<td style="cursor:help;">Dribbling</td>
		<td>
			<div class="progress">
				<div id="drb" class="pro">20</div>
			</div>
		</td>
		<td>
			<div id="drbdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="drbdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="drbup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="drbupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="How well the player aims his shots. Also affects shot power minimally">
		<td style="cursor:help;">Shooting</td>
		<td>
			<div class="progress">
				<div id="sho"  class="pro">20</div>
			</div>
		</td>
		<td>
			<div id="shodownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="shodown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="shoup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="shoupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="A high score means more power on your players shots">
		<td style="cursor:help;">Shot Power</td>
		<td>
			<div class="progress">
				<div id="spo" class="pro">20</div>
			</div>
		</td>
		<td>
			<div id="spodownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="spodown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="spoup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="spoupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
</table>
</div>
<div style="width:50%; float: right;">
<table>
	<tr title="How well your player aims his passes">
		<td style="cursor:help; width:30%;">Passing</td>
		<td style="width:40%;">
			<div class="progress">
				<div id="pas" class="pro">20</div>
			</div>
		</td>
		<td>
			<div id="pasdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="pasdown" class="down"  style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="pasup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="pasupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="A high score helps all skills that involve the ball">
		<td style="cursor:help;">Technique</td>
		<td>
			<div class="progress">
				<div id="tec" class="pro">20</div>
			</div>
		</td>
		<td>
			<div id="tecdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="tecdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="tecup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="tecupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="Decides how many, and which players your player can see on the pitch">
		<td style="cursor:help;">Vision</td>
		<td>
			<div class="progress">
				<div id="vis" class="pro">20</div>
			</div>
		</td>
		<td>
			<div id="visdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="visdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="visup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="visupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="A high score means you player is better at marking opponents">
		<td style="cursor:help;">Marking</td>
		<td>
			<div class="progress">
				<div id="mar" class="pro">20</div>
			</div>
		</td>
		<td>
			<div id="mardownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="mardown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="marup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="marupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="Affects the succes of tackles. A low score with a high aggression could result in many cautions">
		<td style="cursor:help;">Tackling</td>
		<td>
			<div class="progress">
				<div id="tck" class="pro">20</div>
			</div>
		</td>
		<td>
			<div id="tckdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="tckdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="tckup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="tckupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="How good your player is at directing his headers, and how much power he can get on them">
		<td style="cursor:help;">Heading</td>
		<td>
			<div class="progress">
				<div id="hea" class="pro">20</div>
			</div>
		</td>
		<td>
			<div id="headownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="headown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="heaup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="heaupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="For goalkeepers. A high skill will give a boost to your defenders Marking and Vision attributes">
		<td style="cursor:help;">Command of Area (GK)</td>
		<td>
			<div class="progress">
				<div id="coa" class="pro">10</div>
			</div>
		</td>
		<td>
			<div id="coadownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="coadown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="coaup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="coaupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="For goalkeepers. The skill of catching and holding the ball">
		<td style="cursor:help;">Handling (GK)</td>
		<td>
			<div class="progress">
				<div id="han" class="pro">10</div>
			</div>
		</td>
		<td>
			<div id="handownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="handown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="hanup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="hanupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="For goalkeepers. The skill of rushing out, pressuring attackers and shieldeing your goal">
		<td style="cursor:help;">Rushing Out (GK)</td>
		<td>
			<div class="progress">
				<div id="rou" class="pro">10</div>
			</div>
		</td>
		<td>
			<div id="roudownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="roudown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="rouup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="rouupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
	<tr title="For goalkeepers. The skill of blocking and guiding the ball to safety">
		<td style="cursor:help;">Shot Stopping (GK)</td>
		<td>
			<div class="progress">
				<div id="sts" class="pro">10</div>
			</div>
		</td>
		<td>
			<div id="stsdownt" class="down" style="width:13%;">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-w"></span></div>
			</div>
			<div id="stsdown" class="down" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-minus"></span></div>
			</div>
			<div id="stsup" class="up" style="width:37%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-plus"></span></div>
			</div>
			<div id="stsupt" class="up" style="width:13%">
				<div class="fg-button fg-button-icon-left ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-triangle-e"></span></div>
			</div>
		</td>
	</tr>
</table>
</div>
<div style="clear:both;"><!-- --></div>


<input type="hidden" id="startacc" name="startacc" value="10"/>
<input type="hidden" id="starttps" name="starttps" value="10"/>
<input type="hidden" id="startagi" name="startagi" value="10"/>
<input type="hidden" id="startstr" name="startstr" value="10"/>
<input type="hidden" id="startjum" name="startjum" value="10"/>
<input type="hidden" id="startrea" name="startrea" value="10"/>
<input type="hidden" id="startsta" name="startsta" value="10"/>
<input type="hidden" id="startdrb" name="startdrb" value="10"/>
<input type="hidden" id="startsho" name="startsho" value="10"/>
<input type="hidden" id="startspo" name="startspo" value="10"/>
<input type="hidden" id="startpas" name="startpas" value="10"/>
<input type="hidden" id="starttec" name="starttec" value="10"/>
<input type="hidden" id="startvis" name="startvis" value="10"/>
<input type="hidden" id="startmar" name="startmar" value="10"/>
<input type="hidden" id="starttck" name="starttck" value="10"/>
<input type="hidden" id="starthea" name="starthea" value="10"/>
<input type="hidden" id="startcoa" name="startcoa" value="10"/>
<input type="hidden" id="starthan" name="starthan" value="10"/>
<input type="hidden" id="startrou" name="startrou" value="10"/>
<input type="hidden" id="startsts" name="startsts" value="10"/>

<input type="hidden" id="hacc" name="hacc" value=20 />
<input type="hidden" id="htps" name="htps" value=20 />
<input type="hidden" id="hagi" name="hagi" value=20 />
<input type="hidden" id="hstr" name="hstr" value=20 />
<input type="hidden" id="hjum" name="hjum" value=20 />
<input type="hidden" id="hrea" name="hrea" value=20 />
<input type="hidden" id="hsta" name="hsta" value=20 />
<input type="hidden" id="hdrb" name="hdrb" value=20 />
<input type="hidden" id="hsho" name="hsho" value=20 />
<input type="hidden" id="hspo" name="hspo" value=20 />
<input type="hidden" id="hpas" name="hpas" value=20 />
<input type="hidden" id="htec" name="htec" value=20 />
<input type="hidden" id="hvis" name="hvis" value=20 />
<input type="hidden" id="hmar" name="hmar" value=20 />
<input type="hidden" id="htck" name="htck" value=20 />
<input type="hidden" id="hhea" name="hhea" value=20 />
<input type="hidden" id="hcoa" name="hcoa" value=10 />
<input type="hidden" id="hhan" name="hhan" value=10 />
<input type="hidden" id="hrou" name="hrou" value=10 />
<input type="hidden" id="hsts" name="hsts" value=10 />

<?php if(1 > 0){ 
	echo $this->Form->end('Save training'); 
?>

</div>

<?php
}
	

//NedenstÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¥ende skal med allersidst nÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¥r der er script pÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¥ siden. Se http://book.cakephp.org/view/1594/Using-a-specific-Javascript-engine

?>

</div>

<?PHP 
} 
echo $this->Js->writeBuffer(); 
?>
