var exec = require('cordova/exec');
var shortcut = {};
shortcut.install = install;

module.exports = shortcut;

function install(data, successFn, failureFn) {
    data = data || {};

    exec(successFn, failureFn, 'Shortcut', 'install', [
        data.title,
        data.iconUrl,
        data.shortcutData
    ]);
}
