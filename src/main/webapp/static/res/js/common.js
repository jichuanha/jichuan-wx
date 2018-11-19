function getJsonP(_url, _data, _success, _error) {
    var json = null;
    if (_data == undefined) _data = {};
    _data.jsonpCallback = "handler" + Math.round(Math.random() * 1000);
    $.ajax({
        url: _url,
        cache: false,
        type: "POST",
        data: _data,
        dataType: 'jsonp',
        jsonpCallback: _data.jsonpCallback,
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        success: _success,
        error: _error
    });
    return json;
}

function jsonP(_url, _data) {
    if (_data == undefined) _data = {};
    _data.jsonpCallback = "handler"
    $.ajax({
        url: _url,
        cache: false,
        type: "POST",
        data: _data,
        dataType: 'jsonp',
        jsonpCallback: "handler",
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true
    });
}
