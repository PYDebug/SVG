/**
 * Created by jasperchiu on 10/3/15.
 */
//angular.module('app', [
//    'app.module1',
//    'app.module2',
//    'app.module4',
//    'app.module3',
//    'app.module5',
//    'app.module7',
//    'ui.router',
//    'ngAnimate'
//])
//    .run(
//    ['$rootScope', '$state', '$stateParams',
//        function ($rootScope, $state, $stateParams) {
//            $rootScope.$state = $state;
//            $rootScope.$stateParams = $stateParams;
//        }
//    ])
//
//    .config(
//    ['$stateProvider', '$urlRouterProvider',
//        function ($stateProvider,   $urlRouterProvider) {
//
//
//            // Use $urlRouterProvider to configure any redirects (when) and invalid urls (otherwise).
//            //$urlRouterProvider
//            //    .otherwise('/');
//
//            //////////////////////////
//            // State Configurations //
//            //////////////////////////
//
//            // Use $stateProvider to configure your states.
//            $stateProvider
//
//                //////////
//                // Home //
//                //////////
//
//                .state("home", {
//
//                    // Use a url of "/" to set a state as the "index".
//                    url: "/",
//
//                    // Example of an inline template string. By default, templates
//                    // will populate the ui-view within the parent state's template.
//                    // For top level states, like this one, the parent template is
//                    // the index.html file. So this template will be inserted into the
//                    // ui-view within index.html.
//                    templateUrl: 'module-index/module-index.html'
//
//                })
//
//
//        }
//    ]
//);
//

/**
 * Created by jasperchiu on 10/3/15.
 */
angular.module('app', [
    'app.module1',
    'app.module2',
    'app.module4',
    'app.module3',
    'app.module5',
    'app.module7',
    'app.module8',
    'ui.router',
    'ngAnimate',
    'ngDialog',
    'ngStorage',
    'ngResource',
    'datePicker',
    'ui.select2',
    'ui.date',
    'ui.bootstrap',
    'angularMoment'
])
    // .run(
    // ['$rootScope', '$state', '$stateParams','$http',
    //     function ($rootScope, $state, $stateParams,$http, sessionService) {
    //         $rootScope.$state = $state;
    //         $rootScope.$stateParams = $stateParams;
    //         $http.get("./api/getlayers", {headers:sessionService.headers()})
    //             .success(function(response) {
    //                 $rootScope.mapList = response;
    //             });
    //         // downList()
    //     }
    //
    // ])

    .config(
    ['$stateProvider', '$urlRouterProvider','$locationProvider',
        function ($stateProvider,   $urlRouterProvider, $locationProvider) {


            // $locationProvider.html5Mode({
            //     enabled: false,
            //     requireBase: false
            // });
            $locationProvider.html5Mode(false);

            // Use $urlRouterProvider to configure any redirects (when) and invalid urls (otherwise).
            $urlRouterProvider
                .otherwise('/login');

            //////////////////////////
            // State Configurations //
            //////////////////////////

            // Use $stateProvider to configure your states.
            $stateProvider

                //////////
                // Home //
                //////////

                .state("portal", {

                    // Use a url of "/" to set a state as the "index".
                    //abstract: "true",
                    url: "^/portal",
                    templateUrl: 'SVGSiteMockup/site/portal/portal.html',
                    controller: 'PortalController'
                })

                .state("login", {

                    // Use a url of "/" to set a state as the "index".
                    url: "^/login",
                    templateUrl: 'SVGSiteMockup/site/portal/login.html',
                    controller: 'LoginController'

                })

                .state("register", {

                    // Use a url of "/" to set a state as the "index".
                    url: "^/register",
                    templateUrl: 'SVGSiteMockup/site/portal/register.html',
                    controller: 'RegisterController'

                })

                // .state("home", {
                //
                //     // Use a url of "/" to set a state as the "index".
                //     url: "/",
                //
                //     // Example of an inline template string. By default, templates
                //     // will populate the ui-view within the parent state's template.
                //     // For top level states, like this one, the parent template is
                //     // the index.html file. So this template will be inserted into the
                //     // ui-view within index.html.
                //     templateUrl: 'module-index/module-index.html'
                //
                // })


        }
    ]
);
