var refresh = function(){};
var refreshCateGory = function(){
	downList();
};
angular.module('app').controller("HomeCtrl",function ($scope,$http, sessionService, $rootScope) {
    'use strict';
    /*$scope.$on('change-map',function(event,data){
    	$scope.$broadcast('change-map',data);
    });*/

    var mapContent;//to save the map content

    $scope.aMapList = [{amapname:'通用地图',amapList:[]}
	,{amapname:'专用地图',amapList:[]}];
    $scope.mapList = [];
    $http.get("api/getlayers",{headers:sessionService.headers()})
    .success(function(response) {
			response = response.data;
    	$scope.aMapList[0].amapList = [];
    	$scope.aMapList[1].amapList = [];
			$rootScope.mapList = response;
    	 //$scope.aMapList[0].amapList = response;
    	 $scope.mapList = response;
    	 //alert(response.length);
    	 for(var map in response){
    		 //alert(response[map].name);
    		 $scope.aMapList[response[map].typeid].amapList.push(response[map]);
    	 }
    	// downList();


    });
    /*$scope.mapList = [{"id":1,"name":"中国地图","typeId":0,"tslist":[{"date":"2015-04-05","version":1,"mapId":1},{"date":"2015-05-13","version":2,"mapId":1}]},
                      {"id":2,"name":"北京地图","typeId":0,"tslist":[{"date":"2015-04-05","version":1,"mapId":1}]},
                      {"id":4,"name":"上海地铁路线","typeId":0,"tslist":[{"date":"2020-1-1","version":1,"mapId":1},{"date":"2012-1-1","version":1,"mapId":1}]}
    ];*/
    $scope.refreshList =  function(){
    	console.log('get layers');
    	$http.get("api/getlayers?r="+Math.random(), {headers:sessionService.headers()})
        .success(function(response) {
					response = response.data;
        	 $scope.aMapList[0].amapList = [];
        	 $scope.aMapList[1].amapList = [];
        	 //$scope.aMapList[0].amapList = response;
        	 $scope.mapList = response;
        	 for(var map in response){
        		 $scope.aMapList[response[map].typeid].amapList.push(response[map]);
        	 }
        	 //downList();
        });
    };

    refresh = $scope.refreshList;

    $scope.uploadmap = function(){
    	var uploadType=0;

    	var mapType = $("#mapTypeSel").val();


    	//upload map type ,defalut value is 0;1 for new type map;2 for old type map
    	if(document.getElementById("inlineRadio1").checked)
    		{
    			uploadType=1;
    		}
    	else if(document.getElementById("inlineRadio2").checked)
    		{
    			uploadType=2;
    		}
    	if(uploadType==1)
    		{
    			var filename=document.getElementById("inp").value;
    			//var mapType=0;

    			var date=$("#dp1").val().split("/");
    			var newDate=date[2]+"-"+date[0]+"-"+date[1];

    			$.ajaxFileUpload({
    		        url:'./upload/'+filename+'/'+mapType+"/"+newDate,
    		        fileElementId:'inputUploadFile',
    		        success: function (data, status){
    		          alert("上传成功");
    		          refresh();
    		        },
    		        error: function (data, status)
    		        {
    		           alert("fail");
    		         }
    		             }
    			);
    		}
    	else if(uploadType==2)
    		{

    		var mapId=document.getElementById("sel").value;
    		//var mapType=0;

    		var date=$("#dp1").val().split("/");
    		var newDate=date[2]+"-"+date[0]+"-"+date[1];
    		$.ajaxFileUpload({
    	        url:'./uploadTS/'+mapId+"/"+newDate,
    	        fileElementId:'inputUploadFile',
    	        success: function (data, status){
    	          alert("上传成功");
    	          refresh();
    	        },
    	        error: function (data, status)
    	        {
    	           alert("fail");
    	         }
    	             }
    		);
    		}

    	//reload map category
    	refresh();
    }

	$scope.getmapyao=function (map,layer,timestamp)
	{
		//localStorage.setItem("map" , 0);
		//localStorage.setItem("layer",layer);
		//localStorage.setItem("timestamp",timestamp);

		if(timestamp=="") {
			clearLayerContent();
			loadLayer(layer);
		}
		else{
			clearLayerContent();
			loadLayerAndTime(layer,timestamp)
		}
		//console.log(localStorage.layer);
		//console.log(localStorage.timestamp);

	}

	$scope.setLc=function (layer,timestamp)
	{
		localStorage.setItem("layer",layer);
		localStorage.setItem("timestamp",timestamp);
	}

});


function uploadDisplay()
{
	}


	/*
	$.ajaxFileUpload({
        url:'http://localhost:8080/GisWeb_GradleEclipse/upload',
        fileElementId:'inputUploadFile',
        success: function (data, status){
          alert(123);
        },
        error: function (data, status)
        {
           alert(456);
         }
             }
);
*/
