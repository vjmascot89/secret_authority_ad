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
    var matchId = e.target.id;
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
    match["class"] = "";
    render_matches.push(match);
  }
  // render_matches[render_matches.length - 1]["class"] = "active";

  $.tmpl( "match_name", render_matches ).appendTo( "#match_name" );
  $.tmpl( "match_content", render_matches ).appendTo( "#match_content" );

  $('.tabs').tabs();
  for (match_index in render_matches) {
    $('#player-add-form-match-'+render_matches[match_index].match_id).modal();
    $('#stop-match-form-'+render_matches[match_index].match_id).modal();
  }

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
    entries.push(entry);
  }
  debugger;
  $.tmpl( "players", entries ).appendTo( "#players_for_match"+data.id );

}

function stop_match(e) {
  var matchId = e.target.id;
  var win_name = "win-match-" + matchId;
  var formObj = getFormObj("stop-match-"+matchId);
  $.ajax({
    url : "/stopmatch/" + formObj[win_name] + "/winner/" + matchId,
    type : "GET",
    success : function(match, status){
      console.log(status);
      location.reload(true);
    },
  });
}

function delete_player(e) {
  var playerId = e.target.id;
  $.ajax({
    url : "/sattalagao/" + playerId,
    type : "DELETE",
    success : function(match, status){
      console.log(status);
      location.reload(true);
    },
  });
}

function render_previous_matches_dropdown(data){
  var matches = [];
  for (match_index in data){
    var match = data[match_index];
    var entry = {};
    entry["team1"] = match.firstTeam.teamName;
    entry["team2"] = match.secondTeam.teamName;
    entry["date"] = match.date;
    entry["match_id"] = match.id;
    matches.push(entry);
  }
  $.tmpl( "previous_matches_dropdown", matches ).appendTo( "#previous-matches-dropdown" );
}
