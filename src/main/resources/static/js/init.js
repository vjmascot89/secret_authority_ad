(function($){
  $(function(){

    $('.sidenav').sidenav();

  }); // end of document ready
})(jQuery); // end of jQuery name space

// var elem = document.querySelector('.modal');
//  var instance = M.Modal.init(elem, options);
//
 // Or with jQuery

 $(document).ready(function(){
   $('.modal').modal();
   $('.datepicker').datepicker();
   $('select').formSelect();
 });
