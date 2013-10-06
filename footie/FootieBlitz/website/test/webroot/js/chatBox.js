    //If user submits the form  
$(document).ready(function(){
    $("#submitmsg").click(function(){     
        var clientmsg = $("#usermsg").val();  
        $.post("live", {
			text: clientmsg,
			data: clientmsg
		});                
        $("#usermsg").attr("value", "");  
        return false;  
    }); 
 
});	

	// Load the file containing the chat log
	function loadLog(){
	var logPath = "/" + $("#logPath").val();
	//alert(logPath);
		$.ajax({
			url: logPath,
			cache: false,
			success: function(html){
				$("#chatBox").html(html); //Insert chat log into the .chatbox div
				
				var scrolling = $("#chatBox").prop("scrollHeight") - ($("#chatBox").scrollTop() + $("#chatBox").height());
				var scrollingTop = $("#chatBox").prop("scrollHeight") - 20;
				if(scrolling < 50){
					$("#chatBox").animate({ scrollTop: scrollingTop}, 'normal');					
				}				
			}
		});
		
	}
	setInterval (loadLog, 2500); //Reload file every 0.5 seconds

