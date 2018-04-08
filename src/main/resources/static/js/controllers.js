
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
        };
        debugger;
        $.ajax({
          url : "/startmatch",
          type : "POST",
          data : JSON.stringify(data),
          contentType:"application/json; charset=utf-8",
          dataType:"json",
          contentType:"application/json; charset=utf-8",
          success : function(match, status){
            console.log(status);
            render_match(match);
          },
        });
    }
}

function player_add(e){
    // debugger;
    var matchId = e.target.value;
    if (is_player_valid()) {
        var formObj = getFormObj("player-add-"+matchId);
        $.ajax({
          url : "/sattalagao/"+matchId,
          type : "POST",
          data : JSON.stringify(formObj),
          dataType:"json",
          contentType:"application/json; charset=utf-8",
          success : function(match, status){
            console.log(status);
            render_player(match);
          },
        });
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
  debugger;
  var render_matches = [];
  for (match_index in matches){
    var data = matches[match_index];
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
    render_matches.push(match);
  }

  $.tmpl( "match_name", render_matches ).appendTo( "#match_name" );
  $.tmpl( "match_content", render_matches ).appendTo( "#match_content" );
}

function render_player(data){
  var entries = [];
  for (player_index in data.sattaPlayer){
    var player = data.sattaPlayer[player_index];
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
