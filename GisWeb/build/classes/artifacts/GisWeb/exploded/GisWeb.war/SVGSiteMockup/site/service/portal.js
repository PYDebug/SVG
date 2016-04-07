'use strict';

/**
 * @ngdoc function
 * @name morningStudioApp.controller:LoginCtrl
 * @description
 * # PortalCtrl
 * Controller of the morningStudioApp
 */
angular.module('app')
  .controller('PortalController', function($scope,
    sessionService, $localStorage) {
      $scope.currentUser = $localStorage.currentUser;
      $scope.logout = function() {
          sessionService.delToken();
        };

  });
