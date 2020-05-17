var eventBus = function(_eventBusURL){ 
    return new EventBus(_eventBusURL);
}

var eventBusOpen = function(_eventBusURL, _address){
    var eventBus = new EventBus(_eventBusURL+ "/registry");
    eventBus.onopen = function () {
        eventBus.registerHandler(_address, function (error, message) {
            printMessage(JSON.parse(JSON.stringify(message.body)));
        });
    }
    eventBusClose(eventBus);
}

var eventBusSend = function(_eventBusURL, _address, $message){
    var eventBus = new EventBus(_eventBusURL);
    eventBus.onopen = function () {        
        eventBus.send(_address, $message);
    }
    eventBusClose(eventBus);
}

var eventBusClose = function($eventBus){    
    $eventBus.onclose = function(err) {
        alert('Error while trying to connect to the server: ' + err.reason + " with state: " + eventBus.state); // this does happen 
    }
}

let getInstances = function(_eventBusURL){
    $.ajax({
        method: "GET",
        url: _eventBusURL + "/register"
    }).done(function( message ) {
        if(message){
            printMessage(JSON.parse(JSON.stringify(message)));
        }
    });
}

let getConfigurationServer = function(_eventBusURL){
    $.ajax({
        method: "GET",
        url: _eventBusURL + "/configuration"
    }).done(function( data ) {
        return data;
    });
}
function printMessage(jsonMessage){
    $('#incomming').append("<div class='speech-bubble'><p class='speech-bubble-text'>" + jsonMessage).append('</p></div>');
    $(".services-registry tbody tr").remove();
    for( var key in jsonMessage){
        var rootNode = jsonMessage[key];
        var trHTML = '';
        var serviceNode = '';
        var counter = 0;
        $.each(rootNode, function (i, item) {
            counter++;
            if(i == 0){
                trHTML += '<tr>'
                    + '<th class="text-center"><h6>' + key + '</h6></th>'
                    /*+ '<th class="text-center"><h6><a href="#">' + (item.schema + '://' + item.ip + ':' + item.port + '/' + item.service) + '</a></h6></th>'*/
                    + '<th class="text-center"><h6>' + item.schema  + '</h6></th>';
            }
            serviceNode += '<div style="padding-bottom: 5px;"><a href="' + item.url + '" target="_blank">' + (item.service + ':' + item.port) + '</a></div>';
        });
        trHTML += '<th class="text-center"><h6>' + "UP (" + counter + ")"  + '</h6></th>'
            + '<th class="text-center"><h6 class="service-name">' +  serviceNode + '</h6></th>'
            +'</tr>';
        $('.services-registry').append(trHTML);
    }
}
