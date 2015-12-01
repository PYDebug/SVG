app.controller("MapEditCtrl", function ($scope) {
    'use strict';
    $scope.text = 
"<svg width='100%' height='100%' version='1.1' xmlns='http://www.w3.org/2000/svg'>\n"+
"<polygon points = '250 300,220 350, 280 350' stroke = 'blue' fill = 'none'/>\n"+
"<polygon points = '200 200,300 200, 300 400, 200 400' stroke = 'blue' fill = 'none'/>\n"+
"<polygon points = '200 100,300 100, 400 300, 300 500, 200 500, 100 300' stroke = 'blue' fill = 'none' />\n"+
"</svg>";
    
    //init view
    //document.getElementById("SVGEditArea").innerHTML = $scope.text;
    
    $scope.currentlayer = "test";
    $scope.layerList = [{layername:"河流",layerid:"riverOpt",ischecked:"true"},
                        {layername:"建筑",layerid:"buildingOpt",ischecked:"true"},
                        {layername:"教学楼",layerid:"tBuildingOpt",ischecked:"true"},
                        {layername:"学生公寓",layerid:"sBuildingOpt",ischecked:"true"},
                        {layername:"体育馆",layerid:"gBuildingOpt",ischecked:"true"},
                        {layername:"超市",layerid:"superMarketOpt",ischecked:"true"},
                        {layername:"绿化带",layerid:"plantingOpt",ischecked:"true"},
                        {layername:"停车场",layerid:"parkOpt",ischecked:"true"}];
/*    $scope.clicklayer = function(name){
    	$scope.currentlayer = name;
    };*/
    $scope.changelayer = function(flag,id){
    	flag = !flag;
    	//$scope.layer.ischecked=true;
    	$scope.currentlayer = flag+" "+id;
    	if(!flag){
    		switch(id){
    		case "riverOpt": $("#water").find("*").css("visibility","hidden");break;
    		case "buildingOpt":$("#building").find("*").css("visibility","hidden");
    			$("#tBuildingOpt").hide();
    			$("#sBuildingOpt").hide();
    			$("#gBuildingOpt").hide();
    			break;
    		case "tBuildingOpt" : $("#teaching-building").find("*").css("visibility","hidden"); break;
    		case "sBuildingOpt" : $("#apartment").find("*").css("visibility","hidden"); break;
    		case "gBuildingOpt" : $("#gym").find("*").css("visibility","hidden"); break;
    		case "superMarketOpt" : $("#supermarket").find("*").css("visibility","hidden"); break;
    		case "parkOpt" : $("#park").find("*").css("visibility","hidden"); break;
    		case "plantingOpt":$("#green-belt").find("*").css("visibility","hidden"); break;
    		}
    	}else{
    		switch(id){
    		case "riverOpt": $("#water").find("*").css("visibility","visible");break;
    		case "buildingOpt":$("#building").find("*").css("visibility","visible");
    			$("#tBuildingOpt").show();
    			$("#sBuildingOpt").show();
    			$("#gBuildingOpt").show();
    		 	break;
    		case "tBuildingOpt" : $("#teaching-building").find("*").css("visibility","visible"); break;
    		case "sBuildingOpt" : $("#apartment").find("*").css("visibility","visible"); break;
    		case "gBuildingOpt" : $("#gym").find("*").css("visibility","visible"); break;
    		case "superMarketOpt" : $("#supermarket").find("*").css("visibility","visible"); break;
    		case "parkOpt" : $("#park").find("*").css("visibility","visible"); break;
    		case "plantingOpt":$("#green-belt").find("*").css("visibility","visible"); break;
    		}
    		
    	}
    }
    
    $scope.submit = function(){
    	document.getElementById("SVGEditArea").innerHTML = $scope.text;
    	//$scope.text = "hello";
    }
});