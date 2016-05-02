angular.module('app.module1').controller("MapOperationCtrl",['$scope','$http','$sce','sessionService', function ($scope,$http,$sce,sessionService)  {
    'use strict';
    /*$scope.$on('change-map',function(event,data){
    	$scope.$broadcast('change-map',data);
    });*/
   // $scope.searchList = ['同济大学','复旦大学'];
    $scope.searchList = [];

    $scope.isHide = true;

    $scope.mapSearchDirect = function(searchValue){
      $http({
              url: 'api/fuzzyQuery',
              method: 'POST',
              data: searchValue,
              headers: sessionService.headers()
          }).success(function(response){
            $("#MapEleSearch").val(searchValue);
            clearLayerContent();
            //removeLayer("result");
            $scope.searchList=[];
            //alert(data);
            for(var result in response.data){
              //alert(data[result].url);
              if(data[result].type == 1)
                loadLayerBlock("result",1,data[result].url);
              //else if(data[result].type == 0)
                //alert(data[result].url);
                //$scope.searchList.push(data[result].url);
            }
              console.log("fuzzy Search success ");
              if($scope.searchList.length<=0)
              $scope.isHide = true;
          }).error(function(error){
            console.log("fuzzy Search failed ");
          });
    		// $http.post('api/fuzzyQuery',searchValue).
    		// 	success(function(data,status,headers,config){
    		// 		$("#MapEleSearch").val(searchValue);
    		// 		clearLayerContent();
    		// 		//removeLayer("result");
    		// 		$scope.searchList=[];
    		// 		//alert(data);
    		// 		for(var result in data){
    		// 			//alert(data[result].url);
    		// 			if(data[result].type == 1)
    		// 				loadLayerBlock("result",1,data[result].url);
    		// 			//else if(data[result].type == 0)
    		// 				//alert(data[result].url);
    		// 				//$scope.searchList.push(data[result].url);
    		// 		}
    		// 	    console.log("fuzzy Search success ");
    		// 	    if($scope.searchList.length<=0)
    		// 			$scope.isHide = true;
    		// 	}).
    		// 	error(function(data,status,headers,config){
    		// 		console.log("fuzzy Search failed ");
    		// 	});




    }

	$scope.mapSearch =function()
	{
/*		document.getElementById("SVGMapContent").innerHTML=$scope.$parent.mapContent;

		var mapOpCtrl = new mapOperationCtrl();

		mapOpCtrl.setDiffColor($("#MapEleSearch").val());*/
		var searchValue = $("#MapEleSearch").val();
		//alert(searchValue);
		if(!searchValue==""){


      $http({
              url: 'api/fuzzyQuery',
              method: 'POST',
              data: searchValue,
              headers: sessionService.headers()
          }).success(function(response){
            clearLayerContent();
    				//removeLayer("result");
    				$scope.searchList=[];
    				//alert(data);
    				for(var result in response.data){
    					//alert(data[result].url);
    					if(data[result].type == 1)
    						loadLayerBlock("result",1,data[result].url);
    					else if(data[result].type == 0)
    						//alert(data[result].url);
    						$scope.searchList.push(data[result].url);
    				}

    				console.log("fuzzy Search success ");
    				if($scope.searchList.length>0)
    					$scope.isHide = false;

          }).error(function(error){
            console.log("fuzzy Search failed ");
          });


		// $http.post('api/fuzzyQuery',searchValue).
		// 	success(function(data,status,headers,config){
		// 		clearLayerContent();
		// 		//removeLayer("result");
		// 		$scope.searchList=[];
		// 		//alert(data);
		// 		for(var result in data){
		// 			//alert(data[result].url);
		// 			if(data[result].type == 1)
		// 				loadLayerBlock("result",1,data[result].url);
		// 			else if(data[result].type == 0)
		// 				//alert(data[result].url);
		// 				$scope.searchList.push(data[result].url);
		// 		}
    //
		// 		console.log("fuzzy Search success ");
		// 		if($scope.searchList.length>0)
		// 			$scope.isHide = false;
		// 	}).
		// 	error(function(data,status,headers,config){
		// 		console.log("fuzzy Search failed ");
		// 	});



		};

	}



}]);
