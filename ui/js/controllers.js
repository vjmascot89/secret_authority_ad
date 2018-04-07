$( document ).ready(function() {
  var html_match_name = "";
  var html_match_content = "";
  var html_players = "";

  $.ajax({
    url: 'match_name.html',
    success: function(data) {
        html_match_name = data;
        register_template("match_name", html_match_name);
    }
  });

  $.ajax({
    url: 'match_content.html',
    success: function(data) {
        html_match_content = data;
        register_template("match_content", html_match_content);
    }
  });

  $.ajax({
    url: 'players.html',
    success: function(data) {
        html_players = data;
        register_template("players", html_players);
    }
  });

  function register_template(name, markup){
    $.template( name, markup );
  }

  function match_add(){
      debugger;
      if (is_match_valid()) {
          var formObj = getFormObj("start-match");
          var data = {};
          data["name"] = formObj.name;
          data["balancePool"] = formObj.balancePool;
          data["currentMatch"] = {
            "date" : formObj.date,
            "teams" : [
              {
                "teamName" : formObj.team1,
                "ratio" : formObj.ratio1,
              },
              {
                "teamName" : formObj.team2,
                "ratio" : formObj.ratio2,
              }
            ]
          };

          $.post(
            "localhost:8080/startmatch",
            data,
            function(data, status){
              console.log(status);
              //render_match(data);
            }
          );
      }
  }

  function getFormObj(formId) {
      var formObj = {};
      var inputs = $('#'+formId).serializeArray();
      $.each(inputs, function (i, input) {
          formObj[input.name] = input.value;
      });
      return formObj;
  }

  function is_match_valid(){
    return true;
  }

  function render_match(matches){
    var matches = [];
    for (data in matches){
      var match = {};
      match["match_id"] = data.id;
      match["balancePool"] = data.balancePool;
      match["name"] = data.name;
      match["totalBalanceOnTeamOneWin"] = data.totalBalanceOnTeamOneWin;
      match["totalBalanceOnTeamTwoWin"] = data.totalBalanceOnTeamTwoWin;
      match["date"] = data.currentMatch.date;
      match["matchStatus"] = data.currentMatch.matchStatus;
      match["team1"] = data.currentMatch.teams[0];
      match["team2"] = data.currentMatch.teams[1];
      match["ratio1"] = data.currentMatch.teams[0].ratio;
      match["ratio2"] = data.currentMatch.teams[1].ratio;
      matches.push(match);
    }

    $.tmpl( "match_name", matches ).appendTo( "#match_name" );
    $.tmpl( "match_content", matches ).appendTo( "#match_content" );
  }
});
