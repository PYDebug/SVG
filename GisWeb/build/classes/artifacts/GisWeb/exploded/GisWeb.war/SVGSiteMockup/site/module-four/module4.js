/**
 * Created by jasperchiu on 10/3/15.
 */
angular.module('app.module4', [
    'ui.router'
])
    .config(
    ['$stateProvider', '$urlRouterProvider','$locationProvider',
        function ($stateProvider, $urlRouterProvider, $locationProvider) {
          $locationProvider.html5Mode(true);
            $stateProvider
                //////////////
                // module2 //
                //////////////
                .state('portal.module4', {
                    url: '^/module4/:mapid/:timestamp/:mapname/:flag/:version',
                    templateUrl: 'SVGSiteMockup/site/module-four/module4.html',
                    //templateUrl: 'editor/svg-editor.html',
                    controller: ['$scope', '$state','$stateParams',
                        function ($scope, $state,$stateParams) {

                            //$scope.setLc=function (layer,timestamp)
                            //{
                    	document.getElementsByTagName('iframe')[0].contentWindow.localStorage.setItem("layer",$stateParams.mapid);
                        document.getElementsByTagName('iframe')[0].contentWindow.localStorage.setItem("version",$stateParams.version);
                        document.getElementsByTagName('iframe')[0].contentWindow.localStorage.setItem("mapname",$stateParams.mapname);
                        document.getElementsByTagName('iframe')[0].contentWindow.localStorage.setItem("flag",$stateParams.flag);//0 新建图层 1 新建时间戳 2 打开地图
                        document.getElementsByTagName('iframe')[0].contentWindow.localStorage.setItem("timestamp",$stateParams.timestamp);
                            //}

                        }]
                })
        }]
);
