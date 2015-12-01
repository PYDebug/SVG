/**
 * Created by jasperchiu on 10/3/15.
 */


angular.module('app.module8', [
    'ui.router'])
    .config(
    ['$stateProvider', '$urlRouterProvider','$locationProvider',
        function ($stateProvider, $urlRouterProvider, $locationProvider) {
          $locationProvider.html5Mode(true);
            $stateProvider
                //////////////
                // module2 //
                //////////////
                .state('portal.module8', {
                    url: '^/module8/:mapid/:timestamp/;flag',
                    templateUrl: 'SVGSiteMockup/site/module-eight/module8.html',
                    //controller: 'HomeCtrl',
                    //templateUrl: 'editor/svg-editor.html',
                    controller: ['$scope', '$state','$stateParams','ngDialog',
                        function ($scope, $state,$stateParams,ngDialog) {
                            //$scope.maplist = $scope.$root.mapList;

                            $scope.selectedMap=null;

                            $scope.selectMap = function(map){
                                if($scope.selectedMap != map){
                                    $scope.selectedMap = map;
                                    //$scope.maplist2 = $scope.maplist1.slice(0);
                                    //for(var i = 0 ; i < $scope.maplist2.length ; i++){
                                    //    if($scope.maplist2[i].date==timestamp.date){
                                    //        $scope.maplist2.splice(i,1);
                                    //    }
                                    //}
                                    //$scope.selectedStatus = "mapSelected"
                                    //$scope.selectedTimestamp2 = {};
                                }
                                else{
                                    $scope.selectedMap=null;
                                }
                            }

                            $scope.newMap = function(select_map){
                                ngDialog.open({
                                    template: './SVGSiteMockup/site/module-eight/popupTmpl.html',
                                    className: 'ngdialog-theme-plain',
                                    controller: ['$scope', function($scope) {
                                        // controller logic
                                        $scope.mapname="";
                                        $scope.mapid= "";
                                        $scope.timestamp="";
                                        $scope.date = new Date();
                                        $scope.version=0;
                                        $scope.flag = 0;
                                        if(select_map!=null){
                                            $scope.mapname= select_map.name;
                                            $scope.mapid=select_map.id;
                                            $scope.flag = 1;
                                        }
                                        $scope.addLayer = function(){
                                            //alert($scope.timestamp);
                                            //{"id":1,"name":"中国地图","typeId":0,"tslist":[{"date":"2015-04-05","version":1,"mapId":1}
                                            var newmap = {
                                                "id": $scope.$root.mapList.length+1,
                                                "name": $scope.mapname,
                                                "typeid":1,
                                                "tslist":[{"date":$scope.timestamp,"version":1,"mapId":$scope.$root.mapList.length+1}]
                                            }
                                            //$scope.$root.mapList.push(newmap);
                                            $scope.closeThisDialog("");
                                        }
                                    }

                                    ]
                                });
                            }
                            //$scope.setLc=function (layer,timestamp)
                            //{
                            //document.getElementsByTagName('iframe')[0].contentWindow.localStorage.setItem("layer",$stateParams.mapid);
                            //document.getElementsByTagName('iframe')[0].contentWindow.localStorage.setItem("timestamp",$stateParams.timestamp);
                            //document.getElementsByTagName('iframe')[0].contentWindow.localStorage.setItem("flag",$stateParams.flag);
                            //}



                        }]
                })
        }]
);
