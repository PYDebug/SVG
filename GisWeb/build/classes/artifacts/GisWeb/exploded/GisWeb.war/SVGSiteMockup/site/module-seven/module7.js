/**
 * Created by jasperchiu on 10/3/15.
 */
angular.module('app.module7', [
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
                .state('portal.module7', {
                    url: '^/module7',
                    templateUrl: 'SVGSiteMockup/site/module-seven/module7.html',
                    //templateUrl: 'editor/svg-editor.html',
                    controller: ['$scope', '$state',
                        function ($scope, $state) {

                            //Content in controller
                            _pre_level = 1;
                            _level = 1;
                        }]
                })
        }]
);
