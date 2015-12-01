/**
 * Created by jasperchiu on 10/3/15.
 */
angular.module('app.module2', [
    'ui.router'
])
    .config(
    ['$stateProvider', '$urlRouterProvider','$locationProvider',
        function ($stateProvider, $urlRouterProvider,$locationProvider) {
          $locationProvider.html5Mode(true);
            $stateProvider
                //////////////
                // module2 //
                //////////////
                .state('portal.module2', {
                    url: '^/module2',
                    templateUrl: 'SVGSiteMockup/site/module-two/module2.html',
                    //templateUrl: 'editor/svg-editor.html',
                    controller: ['$scope', '$state',
                        function ($scope, $state) {

                            //Content in controller

                        }]
                })
        }]
);
