
$(document).ready(function(){
	
	$("#release").click(function(e){
		e.preventDefault();
	    centerPopup("backgroundPopup", "popupContract");  
	    loadPopup("backgroundPopup", "popupContract");  
    });

    $("#backgroundPopup").click(function(){  
    	disablePopup("backgroundPopup", "popupContract");  
    });  
 
    $(document).keypress(function(e){  
	    if(e.keyCode==27 && popupStatus==1){  
	    	disablePopup("backgroundPopup", "popupContract");  
	    }  
    });
    
});
