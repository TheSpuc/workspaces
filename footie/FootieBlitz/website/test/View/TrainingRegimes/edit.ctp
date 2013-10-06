<?PHP

	echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
	echo $this->Html->script('trainingregimes');
	echo $this->Html->script('trainingregimesedit');
	echo $this->Html->css('http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/themes/smoothness/jquery-ui.css');
	echo $this->Html->script('https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js');

?>

<h1>Create new training regime</h1>

<form method="post"">

Training regime name: <br />
<input type="text" name="name" value=<?php echo '"' . $regime['TrainingRegime']['name'] . '"'; ?>> <br />
Training regime description: <br />
<textarea rows="4" cols="50" name="description"><?php echo $regime['TrainingRegime']['description']; ?></textarea><br />

<div id="divTotal"></div>
<table style="width: 50 %;">

<tr><td style="width: 100 px;">Acceleration <span id="infslacc"></span></td><td><div id="slacc" class="slider" style='width:200px;'></div><input type="hidden" id="slacc_hd" name="acc" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['acceleration']; ?> /></td></tr>
<tr><td style="width: 100 px;">Speed <span id="infsltsp"></span></td><td><div id="sltsp" class="slider" style='width:200px;'></div><input type="hidden" id="sltsp_hd" name="tsp" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['topspeed']; ?> /></td></tr>
<tr><td style="width: 100 px;">Dribbling <span id="infsldrb"></span></td><td><div id="sldrb" class="slider" style='width:200px;'></div><input type="hidden" id="sldrb_hd" name="drb" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['dribbling']; ?> /></td></tr>
<tr><td style="width: 100 px;">Marking <span id="infslmar"></span></td><td><div id="slmar" class="slider" style='width:200px;'></div><input type="hidden" id="slmar_hd" name="mar" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['marking']; ?> /></td></tr>
<tr><td style="width: 100 px;">Strength <span id="infslstr"></span></td><td><div id="slstr" class="slider" style='width:200px;'></div><input type="hidden" id="slstr_hd" name="str" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['strength']; ?> /></td></tr>
<tr><td style="width: 100 px;">Tackling <span id="infsltck"></span></td><td><div id="sltck" class="slider" style='width:200px;'></div><input type="hidden" id="sltck_hd" name="tck" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['tackling']; ?> /></td></tr>
<tr><td style="width: 100 px;">Agility <span id="infslagi"></span></td><td><div id="slagi" class="slider" style='width:200px;'></div><input type="hidden" id="slagi_hd" name="agi" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['agility']; ?> /></td></tr>
<tr><td style="width: 100 px;">Reaction <span id="infslrea"></span></td><td><div id="slrea" class="slider" style='width:200px;'></div><input type="hidden" id="slrea_hd" name="rea" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['reaction']; ?> /></td></tr>
<tr><td style="width: 100 px;">Shooting <span id="infslsho"></span></td><td><div id="slsho" class="slider" style='width:200px;'></div><input type="hidden" id="slsho_hd" name="sho" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['shooting']; ?> /></td></tr>
<tr><td style="width: 100 px;">Shot power <span id="infslshp"></span></td><td><div id="slshp" class="slider" style='width:200px;'></div><input type="hidden" id="slshp_hd" name="shp" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['shotpower']; ?> /></td></tr>
<tr><td style="width: 100 px;">Vision <span id="infslvis"></span></td><td><div id="slvis" class="slider" style='width:200px;'></div><input type="hidden" id="slvis_hd" name="vis" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['vision']; ?> /></td></tr>
<tr><td style="width: 100 px;">Passing <span id="infslpas"></span></td><td><div id="slpas" class="slider" style='width:200px;'></div><input type="hidden" id="slpas_hd" name="pas" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['passing']; ?> /></td></tr>
<tr><td style="width: 100 px;">Technique <span id="infsltec"></span></td><td><div id="sltec" class="slider" style='width:200px;'></div><input type="hidden" id="sltec_hd" name="tec" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['technique']; ?> /></td></tr>
<tr><td style="width: 100 px;">Jumping <span id="infsljum"></span></td><td><div id="sljum" class="slider" style='width:200px;'></div><input type="hidden" id="sljum_hd" name="jum" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['jumping']; ?> /></td></tr>
<tr><td style="width: 100 px;">Stamina <span id="infslsta"></span></td><td><div id="slsta" class="slider" style='width:200px;'></div><input type="hidden" id="slsta_hd" name="sta" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['stamina']; ?> /></td></tr>
<tr><td style="width: 100 px;">Heading <span id="infslhea"></span></td><td><div id="slhea" class="slider" style='width:200px;'></div><input type="hidden" id="slhea_hd" name="hea" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['heading']; ?> /></td></tr>
<tr><td style="width: 100 px;">Handling <span id="infslhan"></span></td><td><div id="slhan" class="slider" style='width:200px;'></div><input type="hidden" id="slhan_hd" name="han" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['handling']; ?> /></td></tr>
<tr><td style="width: 100 px;">Command of area <span id="infslcoa"></span></td><td><div id="slcoa" class="slider" style='width:200px;'></div><input type="hidden" id="slcoa_hd" name="coa" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['commandofarea']; ?> /></td></tr>
<tr><td style="width: 100 px;">Shot stopping <span id="infslsst"></span></td><td><div id="slsst" class="slider" style='width:200px;'></div><input type="hidden" id="slsst_hd" name="sst" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['shotstopping']; ?> /></td></tr>
<tr><td style="width: 100 px;">Rushing out <span id="infslrus"></span></td><td><div id="slrus" class="slider" style='width:200px;'></div><input type="hidden" id="slrus_hd" name="rus" class="sliders_hd" value=<?php echo $regime['TrainingRegime']['rushingout']; ?> /></td></tr>


<tr><td></td><td><input type="submit" value="Save" name="submit"/></td></tr>

</table>
</form>
