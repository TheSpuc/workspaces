     	var playerpoints = "#playerpoints";
	    var attpoints = 0.0;


     $(document).ready(function(){
     	
     	$('#txtfname').keyup(function() {
  			if ($(this).val().length < 21 && $(this).val().length > 1){
  				$('#warnfname').hide();
  			} 
  			else{
  				$('#warnfname').show();  				
  			}
		});
     	$('#txtlname').keyup(function() {
  			if ($(this).val().length < 21 && $(this).val().length > 1){
  				$('#warnlname').hide();
  			} 
  			else{
  				$('#warnlname').show();  				
  			}
		});
		
		var ygunupimage = "url(http://dl.dropbox.com/u/2596837/Footie/YoungGun.png)";
		var ygundownimage = "url(http://dl.dropbox.com/u/2596837/Footie/YoungGunPushed.png)";
		var lbloomupimage = "url(http://dl.dropbox.com/u/2596837/Footie/LateBloomer.png)";
		var lbloomdownimage = "url(http://dl.dropbox.com/u/2596837/Footie/LateBloomerPushed.png)";     
	 	var ygun = "up";
		var lbloom = "up";
		if(ygun == "up"){
			$('#youngGun').css("background-image", ygunupimage); 
		}
		else if (ygun == "down"){
			$('#youngGun').css("background-image", ygundownimage); 
		}
		if(lbloom == "up"){
			$('#lateBloomer').css("background-image", lbloomupimage); 
		}
		else if(lbloom == "down"){
			$('#lateBloomer').css("background-image", lbloomdownimage); 
		}
	 	
	    attpoints = parseFloat($(playerpoints).val());

		progressbar("#acc");
	    progressbar("#tps");
	    progressbar("#agi");
	    progressbar("#str");
	    progressbar("#jum");
	    progressbar("#rea");
	    progressbar("#sta");
	    progressbar("#drb");
	    progressbar("#sho");
	    progressbar("#spo");
	    progressbar("#pas");
	    progressbar("#tec");
	    progressbar("#vis");
	    progressbar("#mar");
	    progressbar("#tck");
	    progressbar("#hea");
	    progressbar("#coa");
	    progressbar("#han");
	    progressbar("#rou");
	    progressbar("#sts");
	    progressbar("#nrg");
	    progressbar("#mor");
     
         getpp();
         $( "#sldrib" ).slider({
            value: $("#hddrib").val(),
            change: function(event, ui) {
                  $("#hddrib").val(ui.value); 
           }});

         $( "#slthro" ).slider({
            value: $("#hdthro").val(),
            change: function(event, ui) {
                  $("#hdthro").val(ui.value); 
           }});

         $( "#slruns" ).slider({
            value: $("#hdruns").val(),
            change: function(event, ui) {
                  $("#hdruns").val(ui.value); 
           }});

         $( "#sllong" ).slider({
            value: $("#hdlong").val(),
            change: function(event, ui) {
                  $("#hdlong").val(ui.value); 
           }});

         $( "#slaggr" ).slider({
            value: $("#hdaggr").val(),
            change: function(event, ui) {
                  $("#hdaggr").val(ui.value); 
           }});

         $( "#slment" ).slider({
            value: $("#hdment").val(),
            change: function(event, ui) {
                  $("#hdment").val(ui.value); 
           }});

         $( "#slclos" ).slider({
            value: $("#hdclos").val(),
            change: function(event, ui) {
                  $("#hdclos").val(ui.value); 
           }});

         $( "#slcros" ).slider({
            value: $("#hdcros").val(),
            change: function(event, ui) {
                  $("#hdcros").val(ui.value); 
           }});

         $( "#slmark" ).slider({
            value: $("#hdmark").val(),
            change: function(event, ui) {
                  $("#hdmark").val(ui.value); 
           }});

         $( "#slpass" ).slider({
            value: $("#hdpass").val(),
            change: function(event, ui) {
                  $("#hdpass").val(ui.value); 
           }});


         function rnd(num, dec) {
	   var result = Math.round(parseNum(num)*100) / 100;
   	   return result;
         }

        var timeout;
           $('.up').click(function() {
           var id = $(this).attr('id');
           var add = 0.1;
           if (id[id.length-1] == "t"){
                   id = "#" + id.replace("upt", "");
                   var hid = id.replace("#", "#h");
                   var i = 0;
                   while (i < 10){
                           addpp(id, add, hid);
                           i++;
                   }
           }
           else {
                   id = "#" + id.replace("up", "");
                   var hid = id.replace("#", "#h");
                   addpp(id, add, hid);
           }        
            });

        $('.up').mousedown(function() {
           var id = $(this).attr('id');
           var add = 0.1;
           if (id[id.length-1] == "t"){
                     id = "#" + id.replace("upt", "");
                     var hid = id.replace("#", "#h");
                     timeout = setInterval(function(){
                           var i = 0;
                           while (i < 10){
                                addpp(id, add, hid);
                                i++;
                           }
                     }, 200);
           }
           else{
                     id = "#" + id.replace("up", "");
                     var hid = id.replace("#", "#h");
                     timeout = setInterval(function(){
                     addpp(id, add, hid);
                     }, 200);
}
            });

        $('.down').click(function() {
           var id = $(this).attr('id');
           var sub= 0.1;
           if (id[id.length-1] == "t"){
                     id = "#" + id.replace("downt", "");
                     var hid = id.replace("#", "#h");
                     var i = 0;
                     while (i < 10){
                         subpp(id, sub, hid);
                         i++;
                     }   
           }
           else {
                     id = "#" + id.replace("down", "");
                     var hid = id.replace("#", "#h");
                     subpp(id, sub, hid);    
           }
       
         });

        $('.down').mousedown(function() {
           var id = $(this).attr('id');
           var sub = 0.1;
           if (id[id.length-1] == "t"){
                     id = "#" + id.replace("downt", "");
                     var hid = id.replace("#", "#h");
                     timeout = setInterval(function(){
                           var i = 0;
                           while (i < 10){
                                subpp(id, sub, hid);
                                i++;
                           }
                     }, 200);
           }
           else {
           id = "#" + id.replace("down", "");
           var hid = id.replace("#", "#h");
           timeout = setInterval(function(){
           subpp(id, sub, hid);
                }, 200);     
			}      
		});
		 
		$('.ageup').click(function() {
			var age = parseInt($('#age').html());
			if (age < 18){
				age++;
				if(age <= 17){
					attpoints += 75;
					if(ygun == "down") { attpoints += 36}
					else if(lbloom == "down") { attpoints -= 36}
				}
				else{
					attpoints += 60;
				}
				$('#age').html(age); 
				$("input[id='age']").val(age);
				$('#divattp').html(attpoints.toFixed(2));                 
			}
		});
		$('.agedown').click(function() {
			var age = parseInt($('#age').html());
			if (age > 14){
				if (age == 15){ 
					if(attpoints < 75){
						$("#agemsg").html("You have too few attribute points to reduce age. You need at least 75 attribute points to reduce age by 1 year").show().fadeOut(20000);                      
					}
					else if (eachatt(75)) {
						age--;
						attpoints -= 75;
						if(ygun == "down") { attpoints -= 36}
						else if(lbloom == "down") { attpoints += 36}
					}
					else $("#agemsg").html("You have attribute scores higher than your age allows. Lower all attributes to 75 or less").show().fadeOut(20000);
				}
				if (age == 16){ 
					if(attpoints < 75){
						$("#agemsg").html("You have too few attribute points to reduce age. You need at least 75 attribute points to reduce age by 1 year").show().fadeOut(20000);                      
					}
					else if (eachatt(80)) {
						age--;
						attpoints -= 75;
						if(ygun == "down") { attpoints -= 36}
						else if(lbloom == "down") { attpoints += 36}
					}
					else $("#agemsg").html("You have attribute scores higher than your age allows. Lower all attributes to 80 or less").show().fadeOut(20000);
				}
				if (age == 17){ 
					if(attpoints < 75){
						$("#agemsg").html("You have too few attribute points to reduce age. You need at least 75 attribute points to reduce age by 1 year").show().fadeOut(20000);                      
					}
					else if (eachatt(84)) {
						age--;
						attpoints -= 75;
						if(ygun == "down") { attpoints -= 36}
						else if(lbloom == "down") { attpoints += 36}
					}
					else $("#agemsg").html("You have attribute scores higher than your age allows. Lower all attributes to 84 or less").show().fadeOut(20000);
				} 
				if (age == 18){ 
					if(attpoints < 60){
						$("#agemsg").html("You have too few attribute points to reduce age. You need at least 60 attribute points to reduce age by 1 year").show().fadeOut(20000);
					}	
					else if (eachatt(87)) {
						age--;
						attpoints -= 60;
					}                        
					else $("#agemsg").html("You have attribute scores higher than your age allows. Lower all attributes to 87 or less").show().fadeOut(20000);
				}       
				if (age == 19){
					if(attpoints < 60){
						$("#agemsg").html("You have too few attribute points to reduce age. You need at least 60 attribute points to reduce age by 1 year").show().fadeOut(20000);	
					} 
					else if (eachatt(89)) {
						age--;
						attpoints -= 60;
					}
					else $("#agemsg").html("You have attribute scores higher than your age allows. Lower all attributes to 89 or less").show().fadeOut(20000);
				} 
				$("input[id='age']").val(age);
				$('#age').html(age);
				$('#divattp').html(attpoints.toFixed(2));           
			}
		});

		$(document).mouseup(function(){
			ival = 0;
			clearInterval(timeout);
			return false; 
		});
		
		//Height change
		$("input[name='option_layout']").change(
			function(){
				if ($("input[name='option_layout']:checked").val()=="1"){
					var randomNum = Math.ceil(Math.random()*17) + 160;
				}
				else if($("input[name='option_layout']:checked").val()=="2"){
					var randomNum = Math.ceil(Math.random()*15) + 173;
				}
				else if($("input[name='option_layout']:checked").val()=="3"){
					var randomNum;
					var hugeheight = Math.ceil(Math.random()*22);
					if(hugeheight > 20){
						randomNum = Math.ceil(Math.random()*22) + 180;   
					}
					else {randomNum = Math.ceil(Math.random()*16) + 177;}
				}                  
				$("input[id='height']").val(randomNum);
			}
		);

		$("div.fg-button").hover(
			function(){
				$(this).addClass("ui-state-hover"); 
			},
			function(){ 
				$(this).removeClass("ui-state-hover"); 
			}
		);
	});

	

function addpp(id, add, hid){
           if (attpoints >= add){
               var old = parseFloat($(id).html());
               var age = parseFloat($('#age').val());
               if (old < 100){
                  if (old >49.9 && old < 60 && attpoints >= (add * 2)){
                    old += add;
                    attpoints -= add * 2;
                  }
                  else if (old > 59.9 && old < 70 && attpoints >= (add * 4)){
                    old += add;
                    attpoints -= add * 4;
                  }
                  else if (old > 69.9 && attpoints >= (add*6)){
                     if(old < 75){
                        old += add;
                        attpoints -= (add * 6);
                      }
                      else if (age == 15 && old < 80){
                          old += add;
                        attpoints -= (add * 6);
                      }
                      else if (age == 16 && old < 84){
                          old += add;
                        attpoints -= (add * 6);
                      }
                      else if (age == 17 && old < 87){
                          old += add;
                        attpoints -= (add * 6);
                      }
                      else if (age == 18 && old < 89){
                          old += add;
                        attpoints -= (add * 6);
                      }
                      else if (age >= 19 && old < 90){
                          old += add;
                        attpoints -= (add * 6);
                      }
                     
                 }
                  else if(old < 50  && attpoints >= add){ 
                    old+=add;
                    attpoints-= add;
                  }
                  $(id).html(old.toFixed(2));                  
                  $(hid).val(old);
                  $('#divattp').html(attpoints.toFixed(2));
                  $('#playerpoints').val(attpoints.toFixed(3));          
                  progressbar(id);
                  getpp();
                }
              }
}

function subpp(id, sub, hid){
            var old = parseFloat($(id).html());
            var start = id.replace("#", "#start");
            var startint = parseFloat($(start).val());
 if (old > startint){
                  if (old >50 && old < 60.1){
                    
                    old -= sub;
                    attpoints += sub*2;
                  }
                  else if (old > 60 && old < 70.1){
                    old -= sub;
                    attpoints += sub*4;
                  }
                  else if (old > 70){
                    old -= sub;
                    attpoints += sub*6;
                  }
                  else{ 
                    old -= sub;
                    attpoints+= sub;
                  }
               $(id).html(old.toFixed(2));
               $(hid).val(old);
               $('#divattp').html(attpoints.toFixed(2));
               $('#playerpoints').val(attpoints);
               progressbar(id);
               getpp();
           }
}


function getpp(){
var res = 0.00;
var acc = parseFloat($('#acc').html()); res += udregnpp(acc);
var tps = parseFloat($('#tps').html()); res += udregnpp(tps);
var agi = parseFloat($('#agi').html()); res += udregnpp(agi);
var str= parseFloat($('#str').html()); res += udregnpp(str);
var jum = parseFloat($('#jum').html()); res += udregnpp(jum);
var rea= parseFloat($('#rea').html()); res += udregnpp(rea);
var sta= parseFloat($('#sta').html()); res += udregnpp(sta);
var drb= parseFloat($('#drb').html()); res += udregnpp(drb);
var sho= parseFloat($('#sho').html()); res += udregnpp(sho);
var spo= parseFloat($('#spo').html()); res += udregnpp(spo);
var pas= parseFloat($('#pas').html()); res += udregnpp(pas);
var tec= parseFloat($('#tec').html()); res += udregnpp(tec);
var vis= parseFloat($('#vis').html()); res += udregnpp(vis);
var mar= parseFloat($('#mar').html()); res += udregnpp(mar);
var tck= parseFloat($('#tck').html()); res += udregnpp(tck);
var hea= parseFloat($('#hea').html()); res += udregnpp(hea);
var coa= parseFloat($('#coa').html()); res += udregnpp(coa);
var han= parseFloat($('#han').html()); res += udregnpp(han);
var rou= parseFloat($('#rou').html()); res += udregnpp(rou);
var sts= parseFloat($('#sts').html()); res += udregnpp(sts);

$('#ppspend').html(res.toFixed(2));

}
function udregnpp(att){
var pp = 0;
if(att < 50) {pp = att}
else if (att < 60) {pp = 50; att =  att - 50; att = att * 2; pp = pp+att;}
else if (att < 70) {pp = 70; att =  att - 60; att = att * 4; pp = pp+att;}
else {pp = 110; att =  att - 70; att = att * 6; pp = pp+att;}
return pp;
}

//Graphics for the progressbar
function progressbar(id){
//find out if the element exists
if($(id).length) {

var progress = parseFloat($(id).html());
var proid = id.replace("#", "");

document.getElementById(proid).style.width= '' + progress + '%';
document.getElementById(proid).style.borderWidth= 'thin';
 if(progress >= 95){
     document.getElementById(proid).style.backgroundColor = '#08FF00';
     document.getElementById(proid).style.borderColor= '#08FF48';
}
else if(progress < 95 && progress >= 90){
     document.getElementById(proid).style.backgroundColor = '#22FF00';
     document.getElementById(proid).style.borderColor= '#22FF48';
}
else if(progress < 90 && progress >= 85){
     document.getElementById(proid).style.backgroundColor = '#3DFF00';
     document.getElementById(proid).style.borderColor= '#3DFF48';
}
else if(progress < 85 && progress >= 80){
     document.getElementById(proid).style.backgroundColor = '#55FF00';
     document.getElementById(proid).style.borderColor= '#55FF48';
}
else if(progress < 80 && progress >= 75){
     document.getElementById(proid).style.backgroundColor = '#70FF00';
     document.getElementById(proid).style.borderColor= '#70FF48';
}
else if(progress < 75 && progress >= 70){
     document.getElementById(proid).style.backgroundColor = '#88FF00';
     document.getElementById(proid).style.borderColor= '#88FF48';
}
 else if(progress < 70 && progress >= 65){
     document.getElementById(proid).style.backgroundColor = '#A3FF00';
     document.getElementById(proid).style.borderColor= '#A3FF48';
}
 else if(progress < 65 && progress >= 60){
     document.getElementById(proid).style.backgroundColor = '#BBFF00';
     document.getElementById(proid).style.borderColor= '#BBFF48';
}
 else if(progress < 60 && progress >= 55){
     document.getElementById(proid).style.backgroundColor = '#E8FF00';
     document.getElementById(proid).style.borderColor= '#E8FF48';
}
 else if(progress < 55 && progress >= 50){
     document.getElementById(proid).style.backgroundColor = '#FFFF00';
     document.getElementById(proid).style.borderColor= '#FFFF48';
}
 else if(progress < 50 && progress >= 45){
     document.getElementById(proid).style.backgroundColor = '#FFE500';
     document.getElementById(proid).style.borderColor= '#FFE548';
}
 else if(progress < 45 && progress >= 40){
     document.getElementById(proid).style.backgroundColor = '#FFCC00';
     document.getElementById(proid).style.borderColor= '#FFCC48';
}
 else if(progress < 40 && progress >= 35){
     document.getElementById(proid).style.backgroundColor = '#FFB200';
     document.getElementById(proid).style.borderColor= '#FFB248';
}
 else if(progress < 35 && progress >= 30){
     document.getElementById(proid).style.backgroundColor = '#FF9900';
     document.getElementById(proid).style.borderColor= '#FF9948';
}
 else if(progress < 30 && progress >= 25){
     document.getElementById(proid).style.backgroundColor = '#FF6600';
     document.getElementById(proid).style.borderColor= '#FF6648';
}
 else if(progress < 25 && progress >= 20){
     document.getElementById(proid).style.backgroundColor = '#FF4B00';
     document.getElementById(proid).style.borderColor= '#FF4B48';
}
 else if(progress < 20 && progress >= 15){
     document.getElementById(proid).style.backgroundColor = '#FF3300';
     document.getElementById(proid).style.borderColor= '#FF3348';
}
 else if(progress < 15 && progress > 10){
     document.getElementById(proid).style.backgroundColor = '#FF1800';
     document.getElementById(proid).style.borderColor= '#FF1848';
}
 else if(progress <= 10){
     document.getElementById(proid).style.backgroundColor = '#FF0000';
     document.getElementById(proid).style.borderColor= '#FF0048';
}
}
}


function youngGun(){
var age = parseInt($('#age').html());
if(ygun == "up" && lbloom == "up"){
$('#youngGun').css("background-image", ygundownimage); 
ygun = "down";
if(age == 15){ attpoints += 36 }
else if (age == 16) { attpoints += 72 }
else if (age >= 17) { attpoints += 108 }
$('#divattp').html(attpoints);  
 $('#traityl').val('1');
}
else if(ygun == "down"){
if(age == 14){
$('#youngGun').css("background-image", ygunupimage); 
ygun = "up";
 $('#traityl').val('0');
}
else if(age == 15 && attpoints >= 36){ 
attpoints -= 36 
$('#youngGun').css("background-image", ygunupimage); 
ygun = "up";
 $('#traityl').val('0');
}
else if (age == 16 && attpoints >= 72) { 
attpoints -= 72 
$('#youngGun').css("background-image", ygunupimage); 
ygun = "up";
 $('#traityl').val('0');
}
else if (age >= 17 && attpoints >= 108) { 
attpoints -= 108 
$('#youngGun').css("background-image", ygunupimage); 
ygun = "up";
 $('#traityl').val('0');
}
$('#divattp').html(attpoints); 
}
}
function lateBloom(){
var age = parseInt($('#age').html());
if(lbloom == "up" && ygun == "up"){
if(age == 14){
$('#lateBloomer').css("background-image", lbloomdownimage); 
lbloom = "down";
 $('#traityl').val('2');
}
else if(age == 15 && attpoints >= 36){ 
attpoints -= 36 
$('#lateBloomer').css("background-image", lbloomdownimage); 
lbloom = "down";
 $('#traityl').val('2');
}
else if (age == 16 && attpoints >= 72) { 
attpoints -= 72 
$('#lateBloomer').css("background-image", lbloomdownimage); 
lbloom = "down";
 $('#traityl').val('2');
}
else if (age >= 17 && attpoints >= 108) { 
attpoints -= 108 
$('#lateBloomer').css("background-image", lbloomdownimage); 
lbloom = "down";
 $('#traityl').val('2');
}
$('#divattp').html(attpoints); 
}
else if (lbloom == "down"){
$('#lateBloomer').css("background-image", lbloomupimage); 
lbloom = "up";
 $('#traityl').val('0');
if(age == 15){ attpoints += 36 }
else if (age == 16) { attpoints += 72 }
else if (age >= 17) { attpoints += 108 }
$('#divattp').html(attpoints); 
}
}

function eachatt(maks){
accept = true;
if(parseInt($('#acc').html()) > maks) accept = false;
if(parseInt($('#tps').html()) > maks) accept = false;
if(parseInt($('#agi').html()) > maks) accept = false;
if(parseInt($('#str').html()) > maks) accept = false;
if(parseInt($('#jum').html()) > maks) accept = false;
if(parseInt($('#rea').html()) > maks) accept = false;
if(parseInt($('#sta').html()) > maks) accept = false;
if(parseInt($('#drb').html()) > maks) accept = false;
if(parseInt($('#sho').html()) > maks) accept = false;
if(parseInt($('#spo').html()) > maks) accept = false;
if(parseInt($('#pas').html()) > maks) accept = false;
if(parseInt($('#tec').html()) > maks) accept = false;
if(parseInt($('#vis').html()) > maks) accept = false;
if(parseInt($('#mar').html()) > maks) accept = false;
if(parseInt($('#tck').html()) > maks) accept = false;
if(parseInt($('#hea').html()) > maks) accept = false;
if(parseInt($('#coa').html()) > maks) accept = false;
if(parseInt($('#han').html()) > maks) accept = false;
if(parseInt($('#rou').html()) > maks) accept = false;
if(parseInt($('#sts').html()) > maks) accept = false;
return accept;
}
//var skspeed = (parseFloat($('#htps').val()) + parseFloat($('#hacc').val())) / 2;
//var skstamina = $('#hsta').val();
//var skstrength = $('#hstr').val();
//var skdefense =  (parseFloat($('#htck').val()) + parseFloat($('#hmar').val())) / 2;
//var skpassing =  (parseFloat($('#hpas').val()) + parseFloat($('#hvis').val())) / 2;
//var skdribbling =  (parseFloat($('#hdrb').val()) + parseFloat($('#hagi').val()) ) / 2;
//var skshooting =  (parseFloat($('#hsho').val()) + parseFloat($('#hspo').val()) ) / 2;

//$('#skillchart').gchart({ type: 'radar', width: 180, height: 150,  dataLabels: ['Speed', 'Stamina', 'Strength', 'Defense', 'Passing', 'Dribbling', 'Shooting'], series: [$.gchart.series([skspeed, skstamina, skstrength, skdefense, skpassing, skdribbling, skshooting, skspeed])]});
