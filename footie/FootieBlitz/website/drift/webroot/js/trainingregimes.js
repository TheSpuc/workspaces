

     $(document).ready(function(){
     	
    	$("#divTotal").html(sliderTotal() + "/100");
    	 
    	$(".slider").slider({
             value: 5,
             max: 20,
             slide: function(event, ui) {
            	 
            	 if (sliderTotal() - parseInt($("#" + $(this).attr("id") + "_hd").val()) + parseInt(ui.value) <= 100){
            		 $("#" + $(this).attr("id") + "_hd").val(ui.value);
            		 $("#inf" + $(this).attr("id")).html(ui.value); 
            	 }
            	 else{
            		 return false;
            	 }
            	 
            	 $("#divTotal").html(sliderTotal() + "/100");
            }});
    	 
     	
     });
     
     function sliderTotal()
     {
    	 var total=0;
    	 
    	 $('.sliders_hd').each(function(index) {
    		 total += parseInt($(this).val());
    	 });
    	 
    	 return total;
     }