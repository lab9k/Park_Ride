//AUTOCOMPLETION FOR ORIGIN AND DESTINATION
function AutocompleteDirectionsHandler(map) {
    this.map = map;
    this.originPlaceId = null;
    this.destinationId = null;

    var originInput = document.getElementById("start-input");
    var destinationInput = document.getElementById("end-input");
    var submitSearch = document.getElementById("form");

    this.directionsService = new google.maps.DirectionsService;
    //Uncommenting this line will display the detailed route to the destination
    //this.directionsDisplay.setPanel(document.getElementById("details-panel"));

    //Handles the autocompletion of an address
    var originAutocomplete = new google.maps.places.Autocomplete(
            originInput, {placeIdOnly: true});
    var destinationAutocomplete = new google.maps.places.Autocomplete(
            destinationInput, {placeIdOnly: true});

    this.setupPlaceChangedListener(originAutocomplete, 'ORIG');
    this.setupPlaceChangedListener(destinationAutocomplete, 'DEST');
    var me = this;
    submitSearch.addEventListener("submit", function (e) {
        me.validateInput(e);
    });
}

AutocompleteDirectionsHandler.prototype.validateInput = function(event) {
    var startInput = document.getElementById("start-input");
    var endInput = document.getElementById("end-input");
    var startID = document.getElementById("start-id");
    var endID = document.getElementById("end-id");
    var date = $("#date-input");
    var time = $("#hour-input");
    var day = new Date(date.val());
    var now = new Date();
    day.setHours(time.val().substring(0,2));
    day.setMinutes(time.val().substring(3,5));
    day.setSeconds(now.getSeconds());
    day.setMilliseconds(now.getMilliseconds());
    // add 15 minutes to entered date
    // if date is 15 minutes in past the request
    // still works
    day = new Date(day.getTime() + 5*60000);

    var formGroupStart = startInput.parentElement.parentElement;
    var formGroupEnd = endInput.parentElement.parentElement;
    var ret = false;

    if(startInput.value && startID.value){
        this.originPlaceId = startID.value;
    }
    if(endInput.value && endID.value){
        this.destinationPlaceId = endID.value;
    }

    if (!this.originPlaceId || !startID.value) {
        formGroupStart.className += " has-error";
        $("#loader-general").css("display", "none");
        event.preventDefault();
        ret = true;
    }
    if (!this.destinationPlaceId || !endID.value) {
        formGroupEnd.className += " has-error";
        $("#loader-general").css("display", "none");
        event.preventDefault();
        return;
    }

    if(day.getTime() < now.getTime()){
        if(day.getHours() < now.getHours() || (day.getHours() === now.getHours() && day.getMinutes() < now.getMinutes())){
            time.addClass("has-date-error");
        } else {
            date.addClass("has-date-error");
        }
        //document.getElementById('time-earlier').disabled = true;
        $("#loader-general").css("display", "none");
        event.preventDefault();
        return;
    }

    if (ret)
        return;
    formGroupStart.classList.remove("has-error");
    formGroupEnd.classList.remove("has-error");
    date.removeClass("has-date-error");
    time.removeClass("has-date-error");
    $("#loader-general").css("display", "block");
};

AutocompleteDirectionsHandler.prototype.setupPlaceChangedListener = function (autocomplete, mode) {
    var me = this;
    autocomplete.bindTo('bounds', this.map);
    autocomplete.addListener('place_changed', function () {
        var place = autocomplete.getPlace();

        var inputName = mode === 'ORIG' ? "start-input" : "end-input";
        var idInput = mode === 'ORIG' ? "start-id" : "end-id";
        var formGroup = document.getElementById(inputName).parentElement.parentElement;
        if (!place.place_id) {
            formGroup.className += " has-error";
            return;
        }
        document.getElementById(idInput).value = place.place_id;
        formGroup.classList.remove("has-error");

        if (mode === 'ORIG') {
            me.originPlaceId = place.place_id;
        } else {
            me.destinationPlaceId = place.place_id;
        }
    });
};

//AUTOCOMPLETION FOR DATE AND TIME
$(function () {
    if(typeof(window.location.search[0]) == 'undefined'){
       setDate();
    }
});

function setDate() {
    var date = new Date();
    var day = date.getDate();
    day = day < 10 ? "0" + day : day;
    var month = date.getMonth() + 1;
    month = month < 10 ? "0" + month : month;
    var hour = date.getHours();
    hour = hour < 10 ? "0" + hour : hour;
    var min = date.getMinutes();
    min = min < 10 ? "0" + min : min;
    $('#date-input').val(date.getFullYear() + "-" + month + "-" + day);
    $('#hour-input').val(hour + ":" + min);
}

//SWITCH
$("#arrow-switch").click(function() {
    var formGroupStart = $("#start-input").parent().parent();
    var formGroupEnd = $("#end-input").parent().parent();
    var startError = formGroupStart.hasClass("has-error");
    var endError = formGroupEnd.hasClass("has-error");
    if(startError && !endError) {
        formGroupStart.removeClass("has-error");
    } else if(!startError && endError) {
        formGroupStart.addClass("has-error");
    }
    if(endError && !startError) {
        formGroupEnd.removeClass("has-error");
    } else if(!endError && startError) {
        formGroupEnd.addClass("has-error");
    }

    var startInputText = $("#start-input").val();
    var endInputText = $("#end-input").val();
    $("#start-input").val(endInputText);
    $("#end-input").val(startInputText);
});

//CHANGE TIME BY 'minutesToChange' MINUTES
$("#time-earlier").click(function() {
    $("#loader-earlier").css("display", "block");
});

$("#time-later").click(function() {
    $("#loader-later").css("display", "block");
});

$(".result-button").click(function() {
    //Variable with the amoun of minutes you want to change
    var minutesToChange = 30;

    if( $(this).attr("id") === "time-earlier"){
        var minutesToChange = -minutesToChange;
    }

    var minToMili = minutesToChange * 60000;
    var input = $("#hour-input").val();
    var newDate = new Date( Date.parse($("#date-input").val() + " "  + input) + minToMili);

    var oldHour = input.substring(0,2);
    var oldMin = input.substring(3,5);
    var newHour = (newDate.getHours() >= 0 && newDate.getHours() <= 9) ? "0"+newDate.getHours() : newDate.getHours();
    var newMin = (newDate.getMinutes() >= 0 && newDate.getMinutes() <= 9) ? "0"+newDate.getMinutes() : newDate.getMinutes();

    //Change URL
    var newUrl = location.href.replace("hour-input="+oldHour+"%3A"+oldMin, "hour-input="+newHour+"%3A"+newMin);
    window.location.href = newUrl;
});

//MORE OPTIONS
$("#more-options-button").click(function() {
    if($("#more-options-button > span").hasClass("glyphicon-menu-right")) {
        $("#more-options-button > span").removeClass("glyphicon-menu-right");
        $("#more-options-button > span").addClass("glyphicon-menu-down");
    } else {
        $("#more-options-button > span").removeClass("glyphicon-menu-down");
        $("#more-options-button > span").addClass("glyphicon-menu-right");
    }
});