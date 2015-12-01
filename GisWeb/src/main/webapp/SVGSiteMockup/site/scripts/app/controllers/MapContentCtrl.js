app.controller("MapContentCtrl", function($scope) {
	'use strict';

	/*$scope.svg = "./resource/svg/1/1";

	var xmlHttp1 = new XMLHttpRequest();
	//console.log("outer");
	xmlHttp1.onreadystatechange = function() {
		if (xmlHttp1.readyState==4 && xmlHttp1.status==200)
	    {
			//console.log("inner");
		$scope.svg2 = xmlHttp1.responseText;
		$scope.svg2 = xmlHttp1.responseText;
		$scope.$parent.mapContent=xmlHttp1.responseText;
		//alert($scope.svg2);
		initHTML();
		document.getElementById("SVGMapContent").getElementsByTagName("g")[0].innerHTML=xmlHttp1.responseText;
		}
	};
	
	//$scope.svg

	xmlHttp1.open("GET", "./resource/svg/1/1", true);
	xmlHttp1.send();*/

	var outer=0;
	var inner=0;
	$scope.$on('change-map', function(event, data) {
		outer++;
		var xmlHttp = new XMLHttpRequest();
		//console.log("form outer "+outer);
		xmlHttp.onreadystatechange = function() {

			if (xmlHttp.readyState==4 && xmlHttp.status==200)
		    {
				inner++;
				//console.log("form inner "+inner);
				
			$scope.svg2 = xmlHttp.responseText;
			//$scope.$parent.mapContent=xmlHttp.responseText;
			//alert($scope.svg2);
			document.getElementById("SVGMapContent").innerHTML=xmlHttp.responseText;
		    }
		}

		xmlHttp.open("GET", data, true);
		xmlHttp.send();

	});
});

function MapSearch()
{
	var mapOpCtrl = new mapOperationCtrl();
	mapOpCtrl.setDiffColor($("#MapEleSearch").val());
	
	}

