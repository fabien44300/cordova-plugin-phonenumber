
//var exec = require('cordova/exec');

var PLUGIN_NAME = 'CordovaPhonenumber';

var CordovaPhonenumber = {

  getTelephonyService: function( success, error,  fonction) {
    cordova.exec(success, error, PLUGIN_NAME, 'getTelephonyService', [fonction]);
  }
};

module.exports = CordovaPhonenumber;
