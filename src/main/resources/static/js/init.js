(function($){
  $(function(){

    $('.sidenav').sidenav();
    $('.modal').modal();
    $('.datepicker').datepicker({
      autoClose : true,
      format : "yyyy-mm-dd"
    });
    var today = new Date();
    var year = today.getFullYear();
    var month = today.getMonth();
    var day = today.getDate();
    if (month < 10) month = "0" + month;
    if (day < 10) day = "0" + day;

    $("#date").attr("value", year + "-" + month + "-" + day);
    $('select').formSelect();
    var html_match_name = "";
    var html_match_content = "";
    var html_players = "";
    var html_previous_matches_dropdown="";

    $.when(
      $.get('match_name.html'),
      $.get('match_content.html'),
      $.get('players.html'),
      $.get('previous_matches_dropdown.html'),
    ).done(function(match_name, match_content, players, previous_matches_dropdown){
        html_match_name = match_name[0];
        html_match_content = match_content[0];
        html_players = players[0];
        html_previous_matches_dropdown = previous_matches_dropdown[0];

        register_template("match_name", html_match_name);
        register_template("match_content", html_match_content);
        register_template("players", html_players);
        register_template("previous_matches_dropdown", html_previous_matches_dropdown);

        $.ajax({
          url: '/activematch',
          success: function(data) {
              render_match(data);
              $('.tabs').tabs();
              for(match_index in data){
                var match_data = data[match_index];
                add_player_to_list(match_data);
              }
          }
        });

        $.ajax({
          url: '/passivematch',
          success: function(data) {
              render_previous_matches_dropdown(data);
          }
        });

        $('.dropdown-trigger').dropdown();
      });
  }); // end of document ready
})(jQuery); // end of jQuery name space

// var elem = document.querySelector('.modal');
//  var instance = M.Modal.init(elem, options);
//
 // Or with jQuery
