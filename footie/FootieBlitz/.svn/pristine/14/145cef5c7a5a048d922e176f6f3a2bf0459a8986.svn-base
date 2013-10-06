
$(document).ready(function(){

 
getPlayerTactics();
getRoles();

$( "#tabs" ).tabs({  
    show: function(event, ui) { 
      var selected = $( "#tabs" ).tabs( "option", "selected" );

      if (selected == 0) getLineups(); 
      if (selected == 1) getMatchStats(); 
      if (selected == 2) getPlayerStats();  
      if (selected == 3) getRoles(); 
      if (selected == 4) getPlayerTactics();
   } 
});


 $( ".lineupnames" ).selectable({
click: function(event) {
  $(this).siblings().removeClass("ui-selected");
  $(this).addClass("ui-selected");
},
                       stop: function() {
                                $( ".ui-selected", this ).each(function() {
                                        var index = $( "#lineupnames span" ).index( this );

                                        if (selPlayer == -1){
                                               selPlayer = index;
                                        }
                                        else{
                                               var temp = $( "#lineupnames span" ).get(selPlayer).innerHTML;
                                               $( "#lineupnames span" ).get(selPlayer).innerHTML = $( "#lineupnames span" ).get(index).innerHTML;
                                               $( "#lineupnames span" ).get(index).innerHTML = temp;
                                               var num1 = parseInt($( "#lineupnames span" ).get(selPlayer).id.replace("lpl", ""));
                                               var num2 = parseInt($( "#lineupnames span" ).get(index).id.replace("lpl", ""));
                                               var id1 = "#"+$( "#lineupnames span" ).get(selPlayer).id.replace("lpl", "hllpl");
                                               var id2 = "#"+$( "#lineupnames span" ).get(index).id.replace("lpl", "hllpl");
//                                               if (num1 < 12 && num2 > 11) subs[$( id1 ).val()]=$( id2 ).val();
//                                               if (num2 < 12 && num1 > 11) subs[$( id2 ).val()]=$( id1 ).val();
                                               var tempid = $( id1 ).val();
                                               $( id1 ).val( $(id2).val() );
                                               $( id2 ).val( tempid );
                                               if (num1 < 12) $("#pos" + num1).html($( "#lineupnames span" ).get(selPlayer).innerHTML);
                                               if (num2 < 12) $("#pos" + num2).html(temp);
                                               selPlayer = -1;
                                               $(this).removeClass("ui-selected");
                                        }
				});

			}

         });

     var startX = 0;
     var startY = 0;
     var selPlayer = -1;

         $(".draggable").draggable({
       containment: "parent",
       start: function(event, ui) {

           var Startpos = $(this).position();
           startX = Startpos.left;
           startY = Startpos.top;
       },

       stop: function(event, ui) {

           var Stoppos = $(this).position();
           var dX = Stoppos.left - startX;
           var dY = Stoppos.top - startY; 
           var movedid = "";
           movedid = $(this).attr('id');
           movedid = movedid.replace("pos", "#p");
           var before = $(movedid).val();
           var bX = parseInt(before.split(",")[0]);
           var bY = parseInt(before.split(",")[1]);
           var newX = parseInt(bX + dX);
           var newY = parseInt(bY + dY / 0.7);
           $(movedid).val(""+newX+","+ newY);
          }
        });

var xmlhttp;


function getLineups(){

var html = $.ajax({

			  url: 'http://www.footie-online.com/matches/getlineups/' + $('#matchID').val() + '/',
			  dataType: 'text',
			  success: function(data) {
                            $('#divlineups').html(data);
			  },
			  error : function(jqXHR, textStatus, errorThrown){
			 	msg= "ERRORRRRR: " + errorThrown + " - " + textStatus + " / " + jqXHR;
                                $('#divlineups').html(msg);
                                
                               
		 	  }
			});
}

function getMatchStats(){
		
var html = $.ajax({

			  url: 'http://www.footie-online.com/matchstats/stats/' + $('#matchID').val() + '/',
			  dataType: 'text',
			  success: function(data) {
                            $('#divmstats').html(data);
			  },
			  error : function(jqXHR, textStatus, errorThrown){
			 	msg= "ERRORRRRR: " + errorThrown + " - " + textStatus;
                                $('#divmstats').html(msg);
		 	  }
			});
}

function getPlayerStats(){
var html = $.ajax({
			  url: 'http://www.footie-online.com/matchplayerstats/stats/' + $('#matchID').val() + '/',
			  dataType: 'text',
			  success: function(data) {
                            $('#divpstats').html(data);
			  },
			  error : function(jqXHR, textStatus, errorThrown){
			 	msg= "ERRORRRRR: " + errorThrown + " - " + textStatus;
                                $('#divmstats').html(msg);
		 	  }
			});
}

function getRoles(){
var html = $.ajax({
			  url: 'http://www.footie-online.com/matches/getroles/' + $('#matchID').val() + '/',
			  dataType: 'text',
			  success: function(data) {
                            $('#divroles').html(data);
			  },
			  error : function(jqXHR, textStatus, errorThrown){
			 	msg= "ERRORRRRR: " + errorThrown + " - " + textStatus;
                                $('#divmstats').html(msg);
		 	  }
			});
}

function getPlayerTactics(){

var html = $.ajax({
			  url: 'http://www.footie-online.com/matches/getplayertactics/' + $('#matchID').val() + '/',
			  dataType: 'text',
			  success: function(data) {
                             $('#hdtacs').html(data);
                             updateSliders();
			  },
			  error : function(jqXHR, textStatus, errorThrown){
			 	msg= "ERRORRRRR: " + errorThrown + " - " + textStatus;
                                $('#divmstats').html(msg);
		 	  }
			});
}

$("#svplst").click(function(){
   updPlayerTactics();
});


function updPlayerTactics(){

var pid = $("#plsel").val();

var rd = $('#' + pid + 'dr').val() + '_' + $('#' + pid + 'thro').val() + '_' + $('#' + pid + 'runs').val() + '_' + $('#' + pid + 'long').val() + '_' + $('#' + pid + 'aggr').val() + '_' + $('#' + pid + 'ment').val() + '_' + $('#' + pid + 'clos').val() + '_' + $('#' + pid + 'cros').val() + '_' + $('#' + pid + 'mark').val() + '_' + $('#' + pid + 'pass').val();

if ($('#chbxsetp').attr('checked') == 'checked'){
   rd = rd + '_t';
}
else{
   rd = rd + '_f';
}

var turl = 'http://www.footie-online.com/matches/updplayertactics/' + pid + '/' + rd;
//alert(turl);
var html = $.ajax({
			  url: turl,
			  dataType: 'text',
			  success: function(data) {
                              $("#styleinfo").html(data).show().fadeOut(3000);
			  },
			  error : function(jqXHR, textStatus, errorThrown){

			 	msg= "ERRORRRRR: " + errorThrown + " - " + textStatus + " -- " + jqXHR.responseText;
alert(msg);
		 	  }
			});
}

$("#plsel").change(function(){
	updateSliders();
});

function updateSliders(){
var pid = $( "#plsel" ).val();

$( "#sldrib" ).slider({ change: function(event, ui) {} });
$( "#slthro" ).slider({ change: function(event, ui) {} });
$( "#slruns" ).slider({ change: function(event, ui) {} });
$( "#sllong" ).slider({ change: function(event, ui) {} });
$( "#slaggr" ).slider({ change: function(event, ui) {} });
$( "#slment" ).slider({ change: function(event, ui) {} });
$( "#slclos" ).slider({ change: function(event, ui) {} });
$( "#slmark" ).slider({ change: function(event, ui) {} });
$( "#slpass" ).slider({ change: function(event, ui) {} });
$( "#slcros" ).slider({ change: function(event, ui) {} });

          $( "#sldrib" ).slider({
            value: $("#" + pid + "dr").val(),
            change: function(event, ui) {
                  $("#" + pid + "dr").val(ui.value); 
           }});

         $( "#slthro" ).slider({
            value: $("#" + pid + "thro").val(),
            change: function(event, ui) {
                  $("#" + pid + "thro").val(ui.value); 
           }});

         $( "#slruns" ).slider({
            value: $("#" + pid + "runs").val(),
            change: function(event, ui) {
                 $("#" + pid + "runs").val(ui.value); 
           }});

         $( "#sllong" ).slider({
            value: $("#" + pid + "long").val(),
            change: function(event, ui) {
                  $("#" + pid + "long").val(ui.value); 
           }});

         $( "#slaggr" ).slider({
            value: $("#" + pid + "aggr").val(),
            change: function(event, ui) {
                  $("#" + pid + "aggr").val(ui.value); 
           }});

         $( "#slment" ).slider({
            value: $("#" + pid + "ment").val(),
            change: function(event, ui) {
                   $("#" + pid + "ment").val(ui.value); 
           }});

         $( "#slclos" ).slider({
            value: $("#" + pid + "clos").val(),
            change: function(event, ui) {
                   $("#" + pid + "clos").val(ui.value); 
           }});

         $( "#slcros" ).slider({
            value: $("#" + pid + "cros").val(),
            change: function(event, ui) {
                   $("#" + pid + "cros").val(ui.value); 
           }});

         $( "#slmark" ).slider({
            value: $("#" + pid + "mark").val(),
            change: function(event, ui) {
                  $("#" + pid + "mark").val(ui.value); 
           }});

         $( "#slpass" ).slider({
            value: $("#" + pid + "pass").val(),
            change: function(event, ui) {
                  $("#" + pid + "pass").val(ui.value); 
           }});

           if ($("#" + pid + "setp").val() == 't'){
               $('#chbxsetp').attr('checked','checked');
           }
           else{
               $('#chbxsetp').removeAttr('checked');
           }
}




$("#btnUpdRoles").click(function(){

var rd = $('#captain').val() + '_' + $('#freekicktaker').val() + '_' + $('#freekicktakerlong').val() + '_' + $('#penalty').val() + '_' + $('#cornerright').val() + '_' + $('#cornerleft').val() + '_' + $('#throwright').val() + '_' + $('#throwleft').val() + '_' + $('#targetman').val();

var turl = 'http://www.footie-online.com/matches/updroles/' + $('#matchID').val() + '/' + rd;

var html = $.ajax({
			  url: turl,
			  dataType: 'text',
			  success: function(data) {
                              $("#tacticsinfo").html(data).show().fadeOut(3000);
			  },
			  error : function(jqXHR, textStatus, errorThrown){
			 	msg= "ERRORRRRR: " + errorThrown + " - " + textStatus + " -- " + jqXHR.responseText;
alert(msg);
		 	  }
			});

});


$("#btnUpdTTactics").click(function(){
var frame = "";

		var i = 1;
		
		for (i = 1; i <= 11; i++)
		{
			frame += "_" + $("#hllpl" + i).val();
			frame += "," + $("#p" + i).val();
		}
//alert(frame);
var html = $.ajax({
			  url: 'http://www.footie-online.com/matches/updteamtactics/' + frame + '/' + $('#matchID').val() + '/',
			  dataType: 'text',
			  success: function(data) {
                              $("#tacticsinfo").html(data).show().fadeOut(3000);
			  },
			  error : function(jqXHR, textStatus, errorThrown){
			 	msg= "ERRORRRRR: " + errorThrown + " - " + textStatus + " -- " + jqXHR.responseText;
//alert(msg);
		 	  }
			});

});



});