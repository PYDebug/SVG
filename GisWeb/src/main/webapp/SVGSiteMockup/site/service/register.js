'use strict';

/**
 * @ngdoc function
 * @name morningStudioApp.controller:RegisterCtrl
 * @description
 * # RegisterCtrl
 * Controller of the morningStudioApp
 */
angular.module('app')
  .controller('RegisterController', function($scope,$state,$localStorage,$http,
    sessionService, tokenFactory, qService) {

      $scope.name = "";

      $scope.password = "";

      $scope.role = "NORMAL_USER";

      $scope.register = function(){
        tokenFactory.register().post({
          "username":$scope.name,
          "password":$scope.password,
          "role":$scope.role
        },function success(data){
          if (data.errorCode=='NO_ERROR') {
            $state.go('login');
          }else if (data.errorCode=='DUPLICATION') {
            alert(data.data);
          }
        },function error(data){

        });
      }
  });
