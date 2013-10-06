  $(document).ready(function(){

        $('#releaseclause').hide();

        $('#expdate').datepicker({ minDate: +37, defaultDate: +150, maxDate: +465, onSelect: function(dateText, inst) {  $('#postdate').val($.datepicker.formatDate('yy-mm-dd',$("#expdate").datepicker( 'getDate' ))); }  });

        $('#postdate').val( $.datepicker.formatDate('yy-mm-dd',$("#expdate").datepicker( 'getDate' )) );

        $("#signonfee").keypress(function (e)
        {
          //if the letter is not digit then display error and don't type anything
          if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))
          {
            //display error message
            $("#signonfeeerror").html("Digits Only").show().fadeOut(3000);
            return false;
          }
        });
        $("#wage").keypress(function (e)
        {
          //if the letter is not digit then display error and don't type anything
          if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))
          {
            //display error message
            $("#wageerror").html("Digits Only").show().fadeOut(3000);
            return false;
          }
        });
        $("#transferfee").keypress(function (e)
        {
          //if the letter is not digit then display error and don't type anything
          if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))
          {
            //display error message
            $("#transferfeeerror").html("Digits Only").show().fadeOut(3000);
            return false;
          }
        });
        $("#assistbonus").keypress(function (e)
        {
          //if the letter is not digit then display error and don't type anything
          if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))
          {
            //display error message
            $("#assisterror").html("Digits Only").show().fadeOut(3000);
            return false;
          }
        });
        $("#goalbonus").keypress(function (e)
        {
          //if the letter is not digit then display error and don't type anything
          if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))
          {
            //display error message
            $("#goalerror").html("Digits Only").show().fadeOut(3000);
            return false;
          }
        });
        $("#releaseclause").keypress(function (e)
        {
          //if the letter is not digit then display error and don't type anything
          if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))
          {
            //display error message
            $("#releaseerror").html("Digits Only").show().fadeOut(3000);
            return false;
          }
        });

       //Vis eller skjul release clause
       $("#withclause").change(function(event){
            if ($('#withclause').is(':checked')) {
                $('#releaseclause').show();
            }
            else{
                $('#releaseclause').hide();
            }
       });

       //Vis eller skjul bonus
        function update_bonusfields() {

           var sel = 0;
          $("#offerfrom option:selected").each(function () {
                sel = $(this).index();
              });

              if(sel == 0){
                 $('.bonus').show("slow");
                 $('#roletd').html("Player");
                 $('#role').val("1");
               }
               else{
                 $('.bonus').hide("slow");
                 $('#roletd').html("Manager");
                 $('#role').val("2");
                 $('#goalbonus').val("0");
                 $('#assistbonus').val("0");
             }
         };

         $("#offerfrom").change(function(event){
               update_bonusfields();
         });

        $("#offerfrom").keypress(function(event){
               update_bonusfields();
        });
     });