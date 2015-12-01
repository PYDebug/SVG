'use strict';

angular.module('app').service('sessionService',
	function categoryService ($localStorage, $location,
		$rootScope, tokenFactory, qService, $http) {


		function checkLocalToken(){
			//这边比较token用$localstorage,因为$rootScope一刷新页面就清空了
			if (!$localStorage.token  || !$localStorage.currentUser) {

				$location.path('/login');
				return false;
			}else{
				$rootScope.currentUser = $localStorage.currentUser;
				$rootScope.token = $localStorage.token;
			}
		};

		this.checkToken = function() {
			return checkLocalToken();
		};

		this.storageChecking = function(){
			 checkLocalToken();
			 checkLocalToken();
		};


		this.saveToken = function(user, token) {
			var user = wrapperUser(user);
			$localStorage.currentUser = user;
			$localStorage.token = token;
			$rootScope.currentUser = user;
			$rootScope.token = token;
			$http.defaults.headers.common['x-auth-token'] = token;
			// if(user.show_role == 'ADMIN'){
			// 	$location.path('/usermanage');
			// }else if(user.show_role == 'NORMAL_USER'){
			// 	$location.path('/teacher/reservation');
			// }else{
			// 	$location.path('/student/reservation');
			// }
			$location.path('/portal');
		};


		function wrapperUser(user){
			// if(user.role == 'STUDENT' || user.role =='ADMINISTRATOR'){
			// 	user.show_role = user.role;
			// }else{
			// 	user.show_role = 'TEACHER';
			// }
			return user;
		};

		this.delToken = function() {
			delete $localStorage.currentUser;
			delete $localStorage.token;
			delete $rootScope.currentUser;
			delete $rootScope.token;
			delete $http.defaults.headers.common['x-auth-token'];
			$location.path('/login');
		};
		this.headers = function(){
			return {
      	'x-auth-token': $localStorage.token
    	};
		};
	}
);
