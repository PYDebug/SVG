'use strict';

angular.module('app').factory('tokenFactory', function($resource) {
	this.login = function(headers) {
		return $resource('/api/token', null, {
			'post': {
				method: 'POST',
				headers: headers
			}
		});
	};
	this.isLogin = function(headers) {
		return $resource('/api/token/isLogin', null, {
			'get': {
				method: 'GET',
				headers: headers
			}
		});
	};
  return this;
});
