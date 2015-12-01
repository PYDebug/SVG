/**
 * Created by jasperchiu on 10/3/15.
 */
angular.module('app.module3', [
    'ui.router'
])
    .config(
    ['$stateProvider', '$urlRouterProvider','$locationProvider',
        function ($stateProvider, $urlRouterProvider, $locationProvider) {
          // $locationProvider.html5Mode({
          //     enabled: false,
          //     requireBase: false
          // });
          $locationProvider.html5Mode(true);
            $stateProvider
                //////////////
                // module2 //
                //////////////
                .state('portal.module3', {
                    url: '^/module3/:mapId',
                    templateUrl: 'SVGSiteMockup/site/module-three/module3.html',
                    //templateUrl: 'editor/svg-editor.html',
                    controller: 'MapCompareCtrl',
                    resolve:{

                    }
                })
        }]
);
