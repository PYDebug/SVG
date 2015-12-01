/**
 * Created by jasperchiu on 10/3/15.
 */
angular.module('app.module1', [
    'ui.router'
])
    .config(
    ['$stateProvider', '$urlRouterProvider','$locationProvider',
        function ($stateProvider, $urlRouterProvider,$locationProvider) {
          $locationProvider.html5Mode(true);
            $stateProvider
                //////////////
                // module1 //
                //////////////
                .state('portal.module1', {
                    url: '^/module1',
                    templateUrl: 'SVGSiteMockup/site/module-one/module1.html',
                    //templateUrl: 'editor/svg-editor.html',
                    controller: ['$scope', '$state',
                        function ($scope, $state) {

                            //Content in controller

                        }]
                })
        }]
);
