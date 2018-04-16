function match_add(){
    event.preventDefault();
    var form_modal = $(event.target).closest('.modal');
    M.Modal.getInstance(form_modal).close();
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
            $.merge(matches_list,match);
            render_match(match);
          },
          error : function ( jqXHR, textStatus,errorThrown) {
            var responseText = jQuery.parseJSON(jqXHR.responseText).message;
            console.log(responseText);
             M.toast({html: responseText});
          }
        });
    }
}

function player_add(e){
    event.preventDefault();
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
            replace_match_from_list(match);
            render_all_players(match);
          },
          error : function ( jqXHR, textStatus,errorThrown) {
            var responseText = jQuery.parseJSON(jqXHR.responseText).message;
            console.log(errorThrown);
             M.toast({html: errorThrown});
          }
        });
    }
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
    error : function ( jqXHR, textStatus,errorThrown) {
      var responseText = jQuery.parseJSON(jqXHR.responseText).message;
      console.log(errorThrown);
       M.toast({html: errorThrown});
    }
  });
}

function delete_player(e) {
  var playerId = e.target.id;
  $.ajax({
    url : "/sattalagao/" + playerId,
    type : "DELETE",
    success : function(match, status){
      console.log(status);
      replace_match_from_list(match);
      render_all_players(match);
    },
    error : function ( jqXHR, textStatus,errorThrown) {
      var responseText = jQuery.parseJSON(jqXHR.responseText).message;
      console.log(errorThrown);
       M.toast({html: errorThrown});
    }
  });
}

function render_match(matches){
  var render_matches = get_data_for_render_match(matches);
  $.tmpl( "match_name", render_matches ).appendTo( "#match_name" );
  $.tmpl( "match_content", render_matches ).appendTo( "#match_content" );
  for (match_index in render_matches) {
    // $('#tab-match-'+render_matches[match_index].match_id).tabs();
    $('#player-add-form-match-'+render_matches[match_index].match_id).modal();
    $('#stop-match-form-'+render_matches[match_index].match_id).modal();
  }
}

function update_match_details(data){
  $("#match-details-"+ data.id).empty();
  var render_matches = get_data_for_render_match([data]);
  $.tmpl( "match_details", render_matches[0] ).appendTo("#match-details-"+ data.id );
}

function add_player_to_list(data){
  $.tmpl( "players", render_player(data) ).appendTo( "#players_for_match"+data.id );
}

function render_all_players(data){
  var matchId = data.id;
  $( "#players_for_match" + matchId ).empty();
  update_match_details(data);
  if(player_view==="Consolidated"){
    render_consolidated_players();
  }
  else {
    var players = render_player(data);
    $.tmpl( "players", players ).appendTo( "#players_for_match"+matchId );
  }
  $("#player-add-"+matchId)[0].reset();
}

function delete_player_from_list(data){
  update_match_details(data);
  $( "#players_for_match" + data.id ).find(data.sattaPlayer[0].id).closest("tr").remove();
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

function get_passive_match(e){
  var matchId = e.target.id;
  $.ajax({
    url : "/passivematch/" + matchId,
    type : "GET",
    success : function(matches, status){
      $.merge(matches_list,matches);
      console.log(status);
      $( "#match_name" ).empty();
      $( "#match_content" ).empty();
      render_match(matches);
      for(match_index in matches){
        var match_data = matches[match_index];
        add_player_to_list(match_data);
      }
    },
    error : function ( jqXHR, textStatus,errorThrown) {
      var responseText = jQuery.parseJSON(jqXHR.responseText).message;
      console.log(errorThrown);
       M.toast({html: errorThrown});
    }
  });
}

function get_active_matches(){
  // $.ajax({
  //   url: '/activematch',
  //   success: function(data) {
  //     $( "#match_name" ).empty();
  //     $( "#match_content" ).empty();
  //     render_match(data);
  //     for(match_index in data){
  //       var match_data = data[match_index];
  //       render_player(match_data);
  //     }
  //   }
  // });
  location.reload(true);
}

function render_consolidated_players(){
  for (index in matches_list){
    var match = matches_list[index];
    var consolidated_players = get_consolidated_players(match);
    var matchId = match.id;
    $( "#players_for_match" + matchId ).empty();
    $.tmpl( "consolidated_players", consolidated_players ).appendTo( "#players_for_match"+matchId );
  }
}

function render_individual_players(){
  for (index in matches_list){
    var match = matches_list[index];
    var individual_players = render_player(match);
    var matchId = match.id;
    $( "#players_for_match" + matchId ).empty();
    $.tmpl( "players", individual_players ).appendTo( "#players_for_match"+matchId );
  }
}

function toggle_view(e){
  var element = e.target;
  var view = e.target.value;
  switch (view) {
    case "Individual":
      element.value = "Consolidated";
      render_consolidated_players();
      break;
    case "Consolidated":
      element.value = "Individual";
      render_individual_players();
      break;
    default:
  }
}

$("#view").change(function() {
  if($(this).is(":checked")) {
    player_view = "Consolidated";
    render_consolidated_players();
  }
  else {
    player_view = "Individual";
    render_individual_players();
  }
});
