

     $(document).ready(function(){
     	
    	$('.sliders_hd').each(function(index) {
    		 $("#infsl" + $(this).attr("name")).html($(this).val()); 
    		 $("#sl" + $(this).attr("name")).slider('value', parseInt($(this).val()));

    	});
 
    
    	 
     	
     });
     
   