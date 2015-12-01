app.controller("MapOperationCtrl",['$scope','$http','$sce', function ($scope,$http,$sce)  {
    'use strict';
    /*$scope.$on('change-map',function(event,data){
    	$scope.$broadcast('change-map',data);
    });*/
   // $scope.searchList = ['同济大学','复旦大学'];
    $scope.searchList = [];
    
    $scope.mapSearchDirect = function(searchValue){
    	
    		$http.post('/fuzzyQuery',searchValue).
    			success(function(data,status,headers,config){
    				
    				removeLayer("result");
    				$scope.searchList=[];
    				//alert(data);
    				for(var result in data){
    					//alert(data[result].url);
    					if(data[result].type == 1)
    						loadLayerBlock("result",1,data[result].url);
    					else if(data[result].type == 0)
    						//alert(data[result].url);
    						$scope.searchList.push(data[result].url);
    				}
    			    console.log("fuzzy Search success ");
    			}).
    			error(function(data,status,headers,config){
    				console.log("fuzzy Search failed ");	
    			});
    		
    		
    		
 
    }
    
	$scope.mapSearch =function()
	{
/*		document.getElementById("SVGMapContent").innerHTML=$scope.$parent.mapContent;
		
		var mapOpCtrl = new mapOperationCtrl();
		
		mapOpCtrl.setDiffColor($("#MapEleSearch").val());*/
		var searchValue = $("#MapEleSearch").val();
		//alert(searchValue);
		if(!searchValue==""){
		$http.post('/fuzzyQuery',searchValue).
			success(function(data,status,headers,config){
				
				removeLayer("result");
				$scope.searchList=[];
				//alert(data);
				for(var result in data){
					//alert(data[result].url);
					if(data[result].type == 1)
						loadLayerBlock("result",1,data[result].url);
					else if(data[result].type == 0)
						//alert(data[result].url);
						$scope.searchList.push(data[result].url);
				}
			   	
				console.log("fuzzy Search success ");
			}).
			error(function(data,status,headers,config){
				console.log("fuzzy Search failed ");	
			});
		
		
		
		};
    
	}
    
    
    
}]);



function mapOperationCtrl() {
	var KeyToId = {
		"河流" : "water",
		"河" : "water",
		"水" : "water",
		"建筑" : "building",
		"楼" : "building",
		"公寓" : "apartment",
		"体育馆": "gym",
		"健身": "gym"	
	};
	var IdToColor={
			"water":"blue",
			"building":"gray",
			"apartment":"pink",
			"gym":"green"
	};
	this.getElement = function(keyword) {
		return $("g#" + KeyToId[keyword]);
	};

	this.setDiffColor = function(keyword) {
		var element = this.getElement(keyword);
		element.find("*")
				.each(
						function() {
							if ($(this).attr("style") != null 
									&& $(this).attr("style").toString().indexOf("fill:none")==-1) {
								//exist some path realted to water has style fill:none but do not exhibit
								$(this).attr("style",
												"stroke:none;fill-rule:nonzero;fill:"+
												IdToColor[KeyToId[keyword]]
												+";fill-opacity:1;");
							}
						});
	}
}
