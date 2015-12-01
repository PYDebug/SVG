/**
 * Created by jasperchiu on 10/3/15.
 */
angular.module('app.module3', [
    'ui.router'
])
    .config(
    ['$stateProvider', '$urlRouterProvider',
        function ($stateProvider, $urlRouterProvider) {
            $stateProvider
                //////////////
                // module2 //
                //////////////
                .state('module3', {
                    url: '/module3',
                    templateUrl: './SVGSiteMockup/site/module-three/module3.html',
                    //templateUrl: 'editor/svg-editor.html',
                    controller: ['$scope', '$state',
                        function ($scope, $state) {

                            //Content in controller

                        }]
                })
        }]
);
