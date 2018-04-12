function register_template(name, markup){
  $.template( name, markup );
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

function get_data_for_render_match(matches){
  var render_matches = [];
  for (match_index in matches){
    var data = matches[match_index];
    var match = {};
    match["match_id"] = data.id;
    match["balancePool"] = data.balancePool;
    match["name"] = data.name;
    fillRelevantValues(match,data,"totalBalanceOnTeamOneWin") ;
    fillRelevantValues(match,data,"totalBalanceOnTeamOneLoss") ;
    fillRelevantValues(match,data,"totalBalanceOnTeamTwoWin") ;
    fillRelevantValues(match,data,"totalBalanceOnTeamTwoLoss") ;
    match["date"] = data.currentMatch.date;
    match["matchStatus"] = data.currentMatch.matchStatus;
    match["team1"] = data.currentMatch.firstTeam.teamName;
    match["team2"] = data.currentMatch.secondTeam.teamName;
    match["ratio1"] = data.currentMatch.firstTeam.ratio;
    match["ratio2"] = data.currentMatch.secondTeam.ratio;
    match["team1Color"] = getTeamColor(data.currentMatch.firstTeam.teamName);
    match["team2Color"] = getTeamColor(data.currentMatch.secondTeam.teamName);
    fillFinalAmountOnTeams(match,data,"finalAmountOnTeamOneWin","totalBalanceOnTeamTwoWin","totalBalanceOnTeamOneLoss","balancePool") ;
    fillFinalAmountOnTeams(match,data,"finalAmountOnTeamTwoWin","totalBalanceOnTeamOneWin","totalBalanceOnTeamTwoLoss","balancePool") ;
    render_matches.push(match);
  }
  return render_matches;
}

function render_player(data){

  var entries = [];
  for (player_index in data.sattaPlayer){
    var player = data.sattaPlayer[player_index];
    var entry = {};
    entry["sattaPlayerName"] = player.sattaPlayerName;
    entry["currentPotRatioOnTeamOne"] = player.currentPotRatioOnTeamOne;
    fillRelevantValues(entry,player,"currentPotTeamOne") ;
    fillRelevantValues(entry,player,"teamOneWinAmount") ;
    fillRelevantValues(entry,player,"teamOneLossAmount") ;
    entry["currentPotRatioOnTeamTwo"] = player.currentPotRatioOnTeamTwo;
    fillRelevantValues(entry,player,"currentPotTeamTwo") ;
    fillRelevantValues(entry,player,"teamTwoWinAmount") ;
    fillRelevantValues(entry,player,"teamTwoLossAmount") ;
    fillRelevantValues(entry,player,"finalAmount") ;
    fillFinalAmountOnTeams(entry,player,"finalAmountOnTeamOneWin","teamOneWinAmount","teamTwoLossAmount") ;
    fillFinalAmountOnTeams(entry,player,"finalAmountOnTeamTwoWin","teamTwoWinAmount","teamOneLossAmount") ;
    entry["player_id"] = player.id;
    entry["matchStatus"] = data.currentMatch.matchStatus;
    entry["team1Color"] = getTeamColor(data.currentMatch.firstTeam.teamName);
    entry["team2Color"] = getTeamColor(data.currentMatch.secondTeam.teamName);
    entries.push(entry);
  }
  return entries;
}

function fillRelevantValues(matchList,data,attribute){
  if(data[attribute]!=0){
    matchList[attribute]=data[attribute];
  }else{
    matchList[attribute]="";
  }
}

function fillFinalAmountOnTeams(matchList,data,finalAmountOnWin,totalBalanceOnTeamOneWin,totalBalanceOnTeamOtherLoss,balancePool){
    matchList[finalAmountOnWin]=data[totalBalanceOnTeamOneWin]+data[totalBalanceOnTeamOtherLoss]+data[balancePool];

}
function fillFinalAmountOnTeams(matchList,data,finalAmountOnWin,totalBalanceOnTeamOneWin,totalBalanceOnTeamOtherLoss){
    matchList[finalAmountOnWin]=data[totalBalanceOnTeamOneWin]+data[totalBalanceOnTeamOtherLoss];

}

var getTeamColor = (function () {
    var color_dictionary = {
      "CSK" : "#0081E9",
      "DD" : "#C02826",
      "KXIP" : "#ED1D24",
      "KKR" : "#3A225D",
      "MI" : "#005FA2",
      "RCB" : "#C9920E",
      "RR" : "#254AA5",
      "SRH" : "#352722",
    };
    return function (teamName) {return color_dictionary[teamName];}
})();
