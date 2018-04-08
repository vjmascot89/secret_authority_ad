$( document ).ready(function() {
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
  // $.ajax({
  //   url: 'match_name.html',
  //   success: function(data) {
  //       html_match_name = data;
  //       register_template("match_name", html_match_name);
  //   }
  // });
  //
  // $.ajax({
  //   url: 'match_content.html',
  //   success: function(data) {
  //       html_match_content = data;
  //       register_template("match_content", html_match_content);
  //   }
  // });
  //
  // $.ajax({
  //   url: 'players.html',
  //   success: function(data) {
  //       html_players = data;
  //       register_template("players", html_players);
  //   }
  // });

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
            "firstTeam" : 
              {
                "teamName" : formObj.team1,
                "ratio" : formObj.ratio1,
              },
              "secondTeam":{
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

  function player_add(e){
      // debugger;
      var matchId = e.target.value;
      if (is_player_valid()) {
          var formObj = getFormObj("player-add-"+matchId);
          $.post(
            "localhost:8080/sattalagao/"+matchId,
            formObj,
            function(data, status){
              console.log(status);
              render_player(data);
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
  function is_player_valid(){
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
      match["team1"] = data.currentMatch.firstTeam.teamName;
      match["team2"] = data.currentMatch.secondTeam.teamName;
      match["ratio1"] = data.currentMatch.firstTeam.ratio;
      match["ratio2"] = data.currentMatch.secondTeam.ratio;
      matches.push(match);
    }

    $.tmpl( "match_name", matches ).appendTo( "#match_name" );
    $.tmpl( "match_content", matches ).appendTo( "#match_content" );
  }

  function render_player(data){
    var entries = [];
    for (player in data.sattaPlayer){
      var entry = {};
      entry["sattaPlayerName"] = player.sattaPlayerName;
      entry["currentPotTeamOne"] = player.currentPotTeamOne;
      entry["currentPotRatioOnTeamOne"] = player.currentPotRatioOnTeamOne;
      entry["teamOneWinAmount"] = player.teamOneWinAmount;
      entry["currentPotTeamTwo"] = player.currentPotTeamTwo;
      entry["currentPotRatioOnTeamOne"] = player.currentPotRatioOnTeamOne;
      entry["teamTwoWinAmount"] = player.teamTwoWinAmount;
      entry["player_id"] = player.id;
      entries.push(match);
    }

    $.tmpl( "players", entries ).appendTo( "#players_for_match"+data.id );
  }
});
