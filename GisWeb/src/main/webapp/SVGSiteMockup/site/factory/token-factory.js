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
	this.register = function(){
		return $resource('/api/register', null, {
			'post':{
				method: 'POST'
			}
		})
	}
  return this;
});
