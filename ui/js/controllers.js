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
