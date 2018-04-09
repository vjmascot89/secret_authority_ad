(function($){
  $(function(){

    $('.sidenav').sidenav();
    $('.modal').modal();
    $('.datepicker').datepicker({
      autoClose : true,
      format : "yyyy-mm-dd"
    });
    $('select').formSelect();
    // $('.tabs').tabs();
    debugger;
    var html_match_name = "";
    var html_match_content = "";
    var html_players = "";

    $.when(
      $.get('match_name.html'),
      $.get('match_content.html'),
      $.get('players.html'),
    ).done(function(match_name, match_content, players){
        html_match_name = match_name[0];
        html_match_content = match_content[0];
        html_players = players[0];
        register_template("match_name", html_match_name);
        register_template("match_content", html_match_content);
        register_template("players", html_players);
        $.ajax({
          url: '/satteri',
          success: function(data) {
              render_match(data);
          }
        });
      });
  }); // end of document ready
})(jQuery); // end of jQuery name space

// var elem = document.querySelector('.modal');
//  var instance = M.Modal.init(elem, options);
//
 // Or with jQuery
