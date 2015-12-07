app.controller("MapCategoryCtrl",function ($scope,$http) {
    'use strict';

   /* $scope.mapList=[{mapname:'中国地图',mapid:"1",times:[{timestamp:'2015-1-1',id:'01'}]}
                    ,{mapname:'北京地图',mapid:"2",times:[{timestamp:'2014-12-26',id:'01'}]}
    				,{mapname:'上海地图',mapid:"3",times:[{timestamp:'2014-1-1',id:'01'},{timestamp:'2013-1-1',id:'02'}]}
    				,{mapname:'上海地铁路线',mapid:"4",times:[{timestamp:'2020-1-1',id:'01'},{timestamp:'2012-1-1',id:'02'}]}
    				,{mapname:'地震影响区域',mapid:"5",times:[{timestamp:'2008-12-26',id:'01'},{timestamp:'2008-12-25',id:'02'}]}];*/

/*    $http.get("./getmaps")
    .success(function(response) {

    	 //$scope.aMapList[0].amapList = response;

    	 //for(var i=0;i<response.length;i++){
    	//	 $scope.mapList[i].tslist = response[i].tslist;
    		 //alert(response[i].tslist[0].date);
    	 //}
    	 //$scope.aMapList = [{amapname:'通用地图',amapList:$scope.mapList}
			//,{amapname:'专用地图'}];
    	//var item = {"id":1,"name":"map1","typeId":0,tslist:[]};
    	//item.tslist.push ([{"date":"2015-04-05","version":1,"mapId":1}]);
    	//$scope.mapList.push(item);
    	 //$scope.aMapList[0].amapList.push( {"id":1,"name":"map1","typeId":0});
    	 //$scope.aMapList[0].amapList[0].tslist = [];
    	//$scope.aMapList[0].amapList[0].tslist.push ({"date":"2015-04-05","version":1,"mapId":1});
    });
    //$scope.mapList = [{"id":1,"name":"map1","typeId":0,"tslist":[{"date":"2015-04-05","version":1,"mapId":1},{"date":"2015-05-13","version":2,"mapId":1}]},{"id":2,"name":"map2","typeId":0,"tslist":[{"date":"2010-05-01","version":1,"mapId":2},{"date":"2011-05-01","version":2,"mapId":2}]},{"id":3,"name":"map3","typeId":0,"tslist":[{"date":"2011-01-12","version":1,"mapId":3}]},{"id":4,"name":"map4","typeId":0,"tslist":[{"date":"2011-01-01","version":1,"mapId":4}]}];
   //$scope.mapList = [{"id":1,"name":"map1","typeId":0,"tslist":[{"date":"2015-04-05","version":1,"mapId":1},{"date":"2015-05-13","version":2,"mapId":1}]}];
    $scope.mapList = [{"id":1,"name":"中国地图","typeId":0,"tslist":[{"date":"2015-04-05","version":1,"mapId":1},{"date":"2015-05-13","version":2,"mapId":1}]},
                      {"id":2,"name":"北京地图","typeId":0,"tslist":[{"date":"2015-04-05","version":1,"mapId":1}]},
                      {"id":4,"name":"上海地铁路线","typeId":0,"tslist":[{"date":"2020-1-1","version":1,"mapId":1},{"date":"2012-1-1","version":1,"mapId":1}]}
    ];
    $scope.aMapList = [{amapname:'通用地图',amapList:$scope.mapList}
    				,{amapname:'专用地图'}];*/


    $scope.clickmap = function(mapid,time){
    	//$scope.$emit('change-map',"./resource/svg/"+mapid+"/"+time);
    	var xmlHttp = new XMLHttpRequest();
		//console.log("form outer "+outer);
		xmlHttp.onreadystatechange = function() {

			if (xmlHttp.readyState==4 && xmlHttp.status==200)
		    {
				//inner++;
				//console.log("form inner "+inner);
			$scope.$parent.mapContent=xmlHttp.responseText;
			$scope.svg2 = xmlHttp.responseText;
			//alert($scope.svg2);
			document.getElementById("SVGMapContent").getElementsByTagName("g")[0].innerHTML=xmlHttp.responseText;
			initHTML();
		    }
		}

		xmlHttp.open("GET", "resource/svg/"+mapid+"/"+time, true);
		xmlHttp.send();
    };

    $scope.deleteMap = function(mapid){
    	$http.get("./api/delete/"+mapid)
        .success(function(response) {
        	 //$scope.aMapList[0].amapList = response;

        	alert(response);
        	refresh();
        });
    };

    $scope.deleteTimestamp = function(mapid, version){
    	$http.get("./api/delete/"+mapid+"/"+version)
        .success(function(response) {
        	 //$scope.aMapList[0].amapList = response;

        	alert(response);
        	refresh();
        });
    };
});
