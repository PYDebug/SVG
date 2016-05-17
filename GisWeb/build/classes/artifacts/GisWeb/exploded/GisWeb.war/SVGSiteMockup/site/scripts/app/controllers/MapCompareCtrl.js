/**
 *
 */
var refreshCompare = function(){};


angular.module('app').controller("MapCompareCtrl",function ($scope,$http,$sce,$stateParams, sessionService) {
    'use strict';
    $scope.oldlist = [
                          {name:"中国地图",id:"1","typeId":0,"tslist":[{"date":"2015-04-05","version":1,"mapId":1},{"date":"2015-05-13","version":2,"mapId":1}]},
                          {name:"北京地图",id:"2","typeId":0,timestamps:[{name:"2014-12-26",id:'01'}]},
                          {name:"上海地铁路线",id:"4","typeId":0,timestamps:[{name:"2014-1-1",id:'01'},{name:'2013-1-1',id:'02'}]},
                          ];
//    $scope.mapCategories = [{"id":1,"name":"中国地图","typeId":0,"tslist":[{"date":"2015-04-05","version":1,"mapId":1},{"date":"2015-05-13","version":2,"mapId":1}]},
//                      {"id":2,"name":"北京地图","typeId":0,"tslist":[{"date":"2015-04-05","version":1,"mapId":1}]},
//                      {"id":4,"name":"上海地铁路线","typeId":0,"tslist":[{"date":"2020-1-1","version":1,"mapId":1},{"date":"2012-1-1","version":1,"mapId":1}]}
//    ];
    $http.get("api/getlayers", {headers:sessionService.headers()})
    .success(function(response) {
      response = response.data
    	$scope.mapCategories = response;
    	 var mapid = $stateParams.mapId;
    	 var map;
    	 for (var i = 0; i < $scope.mapCategories.length; i++) {
    		 if($scope.mapCategories[i].id == mapid){
    	            map = $scope.mapCategories[i];
    	     }
    	 };

    	 $scope.selectedMap = map;
    	 $scope.maplist1 = map.tslist.slice(0);
    	 $scope.selectedStatus = "mapSelected"
    });
    //$scope.mapCategories = $scope.$root.mapList;



    $scope.selectedTimestamp1 = {};
    $scope.selectedTimestamp2 = {};

    // $scope.selectedMap = {};
    // $scope.maplist1 = [];
    $scope.maplist2 = [];
    // $scope.selectedTimestamp1 = {};
    // $scope.selectedTimestamp2 = {};
    $scope.mapSearch = "";
    $scope.timestamp1Search = "";
    $scope.timestamp2Search = "";
    // alert(map.name);


    $scope.insertNode = [];

    $scope.refreshCompareList =  function(){
    	console.log('get layers');
    	$http.get("api/getlayers?r="+Math.random())
        .success(function(response) {
          response = response.data
        	$scope.mapCategories = response;

            var mapid = $stateParams.mapId;

            for (var i = 0; i < $scope.mapCategories.length; i++) {
                if($scope.mapCategories[i].id == mapid){
                    map = $scope.mapCategories[i];
                }
            };

            $scope.selectedMap = map;
            $scope.maplist1 = map.tslist.slice(0);
            $scope.maplist2 = [];
        });

    };

    refreshCompare = $scope.refreshCompareList;

    $scope.selectedStatus = "initial"; // initial -> mapSelected -> timestamp1Selected -> timestamp2Selected

    $scope.selectMap = function (map){
    	if($scope.selectedMap != map){
    		$scope.selectedMap = map;
    		$scope.maplist1 = map.tslist.slice(0);
    		$scope.selectedStatus = "mapSelected"
    	    $scope.selectedTimestamp1 = {};
    	    $scope.selectedTimestamp2 = {};
    	}
    };


    $scope.selectTimestamp1 = function(timestamp){
    	if($scope.selectedTimestamp1 != timestamp){
    		$scope.selectedTimestamp1 = timestamp;
    		$scope.maplist2 = $scope.maplist1.slice(0);
    		for(var i = 0 ; i < $scope.maplist2.length ; i++){
    			if($scope.maplist2[i].date==timestamp.date){
    				$scope.maplist2.splice(i,1);
    			}
    		}
    		$scope.selectedStatus = "timestamp1Selected"
    	    $scope.selectedTimestamp2 = {};
    	}
    };

    $scope.selectTimestamp2 = function(timestamp){
    	if($scope.selectedTimestamp2 != timestamp){
    		$scope.selectedTimestamp2 = timestamp;
    		$scope.selectedStatus = "timestamp2Selected"
    	}
    };

    $scope.showSelection = true;


    $scope.startCompare = function(mapid,newversion,oldversion){
    	$scope.showSelection = false;
    	loadSVG1(mapid,newversion,oldversion);
    	loadSVG2(mapid,newversion,oldversion);
    };



    $scope.backToCompareSelection = function(){
    	$scope.showSelection = true;
    };

    //var xhttp;

    function loadXMLDoc(dname)
    {
    if (window.XMLHttpRequest)
      {
      var xhttp=new XMLHttpRequest();
      }
    else
      {
      xhttp=new ActiveXObject("Microsoft.XMLHTTP");
      }
    xhttp.open("GET",dname,false);
    xhttp.send("");
    return xhttp.responseXML;
    };

    function NSResolver(prefix) {
    	if (prefix == "svg")
    		return "http://www.w3.org/2000/svg";
    	return null;
    };

    var getInsertXML = function(mapid,newversion,oldversion){
    	$scope.insertNode = [];
    	if(newversion>oldversion)
    	{
    		var firstparam = newversion;
    		var secondparam = oldversion;
    	}
    	else
    	{
    		var firstparam = oldversion;
    		var secondparam = newversion
    	}
    	var diffxml=loadXMLDoc("resource/svg/block/matching/"+mapid+"/"+firstparam+"/"+secondparam+"?r="+Math.random());
    	//alert(diffxml);
    	//var path="/root/insert";
		if (window.ActiveXObject)
		{
			var nodes=diffxml.selectNodes("/root/insert");

    		for (var i=0;i<nodes.length;i++)
    		{
    			var splitpath = nodes[i].getAttribute("XPath").split('/');
    			var xpath = "";
    			for(var i =0 ; i<splitpath.length;i++)
    			{
    				if(splitpath[i]!="")
    					xpath = xpath + "/svg:"+splitpath[i];
    			}
    			$scope.insertNode.push(xpath);
    		}
		}
		// code for Mozilla, Firefox, Opera, etc.
		else if (document.implementation && document.implementation.createDocument)
		{
			//var nsResolver = document.createNSResolver( xml.ownerDocument == null ? xml.documentElement : xml.ownerDocument.documentElement );
			var nodes=diffxml.evaluate("/root/insert", diffxml, NSResolver, XPathResult.ANY_TYPE, null);
			var result=nodes.iterateNext();
    		while (result)
    		{
    			var splitpath = result.getAttribute("XPath").split('/');
    			var xpath = "";
    			for(var i =0 ; i<splitpath.length;i++)
    			{
    				if(splitpath[i]!="")
    					xpath = xpath + "/svg:"+splitpath[i];
    				//alert(xpath);
    			}
    			$scope.insertNode.push(xpath);
    			result=nodes.iterateNext();
    		}
		}
    };

    $scope.deleteNode = [];

    var getDeleteXML = function(mapid,newversion,oldversion){
    	$scope.deleteNode = [];
    	if(newversion>oldversion)
    	{
    		var firstparam = newversion;
    		var secondparam = oldversion;
    	}
    	else
    	{
    		var firstparam = oldversion;
    		var secondparam = newversion
    	}

    	var diffxml=loadXMLDoc("resource/svg/block/matching/"+mapid+"/"+firstparam+"/"+secondparam+"?r="+Math.random());
    	//alert(diffxml);
    	//var path="/root/insert";
		if (window.ActiveXObject)
		{
			var nodes=diffxml.selectNodes("/root/delete");

    		for (var i=0;i<nodes.length;i++)
    		{
    			var splitpath = nodes[i].getAttribute("XPath").split('/');
    			var xpath = "";
    			for(var i =0 ; i<splitpath.length;i++)
    			{
    				if(splitpath[i]!="")
    					xpath = xpath + "/svg:"+splitpath[i];
    			}
    			$scope.deleteNode.push(xpath);
    		}
		}
		// code for Mozilla, Firefox, Opera, etc.
		else if (document.implementation && document.implementation.createDocument)
		{
			//var nsResolver = document.createNSResolver( xml.ownerDocument == null ? xml.documentElement : xml.ownerDocument.documentElement );
			var nodes=diffxml.evaluate("/root/delete", diffxml, NSResolver, XPathResult.ANY_TYPE, null);
			var result=nodes.iterateNext();
    		while (result)
    		{
    			var splitpath = result.getAttribute("XPath").split('/');
    			var xpath = "";
    			for(var i =0 ; i<splitpath.length;i++)
    			{
    				if(splitpath[i]!="")
    					xpath = xpath + "/svg:"+splitpath[i];
    				//alert(xpath);
    			}
    			$scope.deleteNode.push(xpath);
    			result=nodes.iterateNext();
    		}
		}
    };


    $scope.svg1;
    $scope.svg2;

    var loadSVG1 = function(mapid,newversion,oldversion){
    	if(newversion > oldversion)
    		var version = oldversion;
    	else
    		var version = newversion;
    	var url = "resource/svg/block/"+mapid+"/1/1/"+version;
    	var xml=loadXMLDoc(url);
    	//alert(xml);
    	//var path="/svg:svg/svg:*[2]/svg:*[31]";
    	getDeleteXML(mapid,newversion,oldversion);
    	for(var i =0 ;i<$scope.deleteNode.length;i++)
    	{
    		var path = $scope.deleteNode[i];
			if (window.ActiveXObject)
			{
				var nodes=xml.selectNodes(path);

	    		for (i=0;i<nodes.length;i++)
	    		{
	    			nodes[i].setAttribute("fill","red");
	    		}
	//			alert(nodes[0].childNodes[0].nodeValue);
			}
			// code for Mozilla, Firefox, Opera, etc.
			else if (document.implementation && document.implementation.createDocument)
			{
				//var nsResolver = document.createNSResolver( xml.ownerDocument == null ? xml.documentElement : xml.ownerDocument.documentElement );
				var nodes=xml.evaluate(path, xml, NSResolver, XPathResult.ANY_TYPE, null);
				var result=nodes.iterateNext();
	//			alert(nodes);
	    		//while (result)
	    		//{
				if(result!= null)
				{
					var attr = result.getAttribute("style");
					if(attr!=null)
					{
						var attrs = attr.split(';');
						var new_attr = "";
						var isexist_attr = false;
						for(var j =0 ;j<attrs.length;j++)
						{
							if(attrs[j]!="")
							if(attrs[j].indexOf("fill:")==-1)
							{
								new_attr=new_attr+attrs[j]+";";
							}
							else
							{
								new_attr=new_attr+"fill:red"+";";
								isexist_attr=true;
							}

						}
						if(!isexist_attr)
							new_attr=new_attr+"fill:red"+";";
						result.setAttribute("style",new_attr);
					}
					else
					{
						result.setAttribute("style","fill:red");
					}
				}

	    			//result=nodes.iterateNext();
	    		//}
			}
    	}
    	//alert(xml.toString());
    	$scope.svg1 = $sce.trustAsHtml(xml.documentElement.outerHTML);
    };

    var loadSVG2 = function(mapid,newversion,oldversion){
    	if(newversion > oldversion)
    		var version = newversion;
    	else
    		var version = oldversion;
    	var url = "resource/svg/block/"+mapid+"/1/1/"+version;
    	var xml=loadXMLDoc(url);
    	//alert(xml);
    	//var path="/svg:svg/svg:*[2]/svg:*[31]";
    	getInsertXML(mapid,newversion,oldversion);
    	for(var i =0 ;i<$scope.insertNode.length;i++)
    	{
    		var path = $scope.insertNode[i];
			if (window.ActiveXObject)
			{
				var nodes=xml.selectNodes(path);

	    		for (i=0;i<nodes.length;i++)
	    		{
	    			nodes[i].setAttribute("fill","red");
	    		}
	//			alert(nodes[0].childNodes[0].nodeValue);
			}
			// code for Mozilla, Firefox, Opera, etc.
			else if (document.implementation && document.implementation.createDocument)
			{
				//var nsResolver = document.createNSResolver( xml.ownerDocument == null ? xml.documentElement : xml.ownerDocument.documentElement );
				var nodes=xml.evaluate(path, xml, NSResolver, XPathResult.ANY_TYPE, null);
				var result=nodes.iterateNext();
	//			alert(nodes);
	    		//while (result)
	    		//{
				if(result!= null)
				{
					var attr = result.getAttribute("style");
					if(attr!=null)
					{
						var attrs = attr.split(';');
						var new_attr = "";
						var isexist_attr = false;
						for(var j =0 ;j<attrs.length;j++)
						{
							if(attrs[j]!="")
							if(attrs[j].indexOf("fill:")==-1)
							{
								new_attr=new_attr+attrs[j]+";";
							}
							else
							{
								new_attr=new_attr+"fill:blue"+";";
								isexist_attr=true;
							}

						}
						if(!isexist_attr)
							new_attr=new_attr+"fill:blue"+";";
						result.setAttribute("style",new_attr);
					}
					else
					{
						result.setAttribute("style","fill:blue");
					}

				}

	    			//result=nodes.iterateNext();
	    		//}
			}
    	}
    	//alert(xml.toString());
    	$scope.svg2 = $sce.trustAsHtml(xml.documentElement.outerHTML);
    };



});
