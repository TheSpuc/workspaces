var playedvisible = 0;
$(document).ready(function() 
    { 
	
    
    
	$("#slSeason").change(function() { $('#frmSeason').submit(); });
	$("#slStage").change(function() { $('#frmSeason').submit(); });
	
	$("#table").tablesorter( {sortList: [[9,1], [8,1], [6,1], [5,1], [1,0]]} ); 
	
         //$("#results").tablesorter(); 
        $('.more.one').click(function() {
         if (playedvisible == 0){
window.setTimeout(function(){
//             $(".extra").slideDown("linear");
             $(".extra").show();
             playedvisible = 1;
return false;
},1);
         }
         else{
window.setTimeout(function(){
//             $(".extra").slideUp("linear");
             $(".extra").fadeOut(600);
             playedvisible = 0;
return false;
},1);
         }
         });

        $('.more.two').click(function() {
         if (playedvisible == 0){
//             $(".extra2").css("visibility","visible");
             $(".extra2").show();
             playedvisible = 1;
         }
         else{
//             $(".extra2").css("visibility","hidden");
             $(".extra2").fadeOut(600);
             playedvisible = 0;
         }
         });
    });


function offHover(box) {
//box.style.background='#EFEFEF';
box.style.fontWeight='normal';
var elements = $(box).children();
for(var i = 0; i<elements.length; i++){
var e = elements[i];
//$(e).css("background","#EFEFEF");
//$(e).css("background","#00B6FF");  
$(e).css("background","#848484"); 
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
//$(e).css("background","#00A0E0");
$(e).css("background","#474747");
$(e).css("fontWeight","bold");
}
}
function tdOnHover(box){
var e = "." + $(box).attr("class");
//$(e).css("background","#C7C7C7");
//$(e).css("background","#00A0E0");
$(e).css("background","#474747");
$(e).css("fontWeight","bold");
}
function tdOffHover(box){
var e = "." + $(box).attr("class");
//$(e).css("background","#EFEFEF");
//$(e).css("background","#00B6FF");  
$(e).css("background","#848484"); 
$(e).css("fontWeight","normal");
}
