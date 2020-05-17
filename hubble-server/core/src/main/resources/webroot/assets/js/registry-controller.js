var _eventBusURL = '';
$(function () {
    getConfigurationServer(_eventBusURL);
    eventBusOpen(_eventBusURL, 'eb.service.discovery.connect');
    getInstances(_eventBusURL);
});

// Conocer la version del navegador
navigator.sayswho = (function () {
    var N = navigator.appName, ua = navigator.userAgent, tem;
    var M = ua.match(/(opera|chrome|safari|firefox|msie|trident)\/?\s*(\.?\d+(\.\d+)*)/i);
    if (M && (tem = ua.match(/version\/([\.\d]+)/i)) != null) {
        M[2] = tem[1];
    }
    M = M ? M[1] + ' Version: ' + M[2] : N + ', ' + navigator.appVersion + ', ' + '-?';
    return M;
})();
