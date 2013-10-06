var popupStatus = 0;  
	
	
function submitform(frmID)
{
	document.getElementById(frmID).submit();
}


function loadPopup(bgID, popupID){  

    if(popupStatus==0){  
	    $("#" + bgID).css({  
	    "opacity": "0.7"  
	    });  
	    $("#" + bgID).fadeIn("slow");  
	    $("#" + popupID).fadeIn("slow");  
	    popupStatus = 1;  
    }  
}  

function disablePopup(bgID, popupID){  

	if(popupStatus==1){  
		$("#" + bgID).fadeOut("slow");  
		$("#" + popupID).fadeOut("slow");  
		popupStatus = 0;  
	}  
}

//centering popup  
function centerPopup(bgID, popupID){  
    //request data for centering  
    var windowWidth = document.documentElement.clientWidth;  
    var windowHeight = document.documentElement.clientHeight;  
    var popupHeight = $("#" + popupID).height();  
    var popupWidth = $("#" + popupID).width();  
    
    //centering  
    $("#" + popupID).css({  
	    "position": "absolute",  
	    "top": windowHeight/2-popupHeight/2,  
	    "left": windowWidth/2-popupWidth/2  
    });  
    //only need force for IE6  
      
    $("#" + bgID).css({  
    	"height": windowHeight  
    });       
}  

