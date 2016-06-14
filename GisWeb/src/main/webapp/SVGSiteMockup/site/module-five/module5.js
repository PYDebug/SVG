/**
 * Created by carlos on 10/7/15.
 */
angular.module('app.module5', [
    'ui.router'
])
    .config(
    ['$stateProvider', '$urlRouterProvider','$locationProvider',
        function ($stateProvider, $urlRouterProvider, $locationProvider) {
          // $locationProvider.html5Mode({
          //     enabled: false,
          //     requireBase: false
          // });
          $locationProvider.html5Mode(true);
            $stateProvider
                //////////////
                // module5 //
                //////////////
                .state('portal.module5', {
                    url: '^/module5/:mapid/:newversion/:oldversion',
                    templateUrl: 'SVGSiteMockup/site/module-five/module5.html',
                    //templateUrl: 'editor/svg-editor.html',
                    controller: ['$scope', '$state','$sce','$stateParams',
                        function ($scope, $state,$sce,$stateParams) {

                            //Content in controller
                            $scope.addnode = [];
                            $scope.deletenode = [];
                            $scope.sideClass = "";
                            $scope.showSidebar = function(){

                                if($scope.sideClass=="")
                                    $scope.sideClass = "open";
                                else
                                    $scope.sideClass = "";


                            };

                            $scope.showSelection = true;


                            var mapid = $stateParams.mapid;
                            var newversion = $stateParams.newversion;
                            var oldversion = $stateParams.oldversion;

                            //$scope.startCompare = function(mapid,newversion,oldversion){

                            //};



                            $scope.backToCompareSelection = function(){
                                $scope.showSelection = true;
                            };

                            //var xhttp;

                            function loadXMLDoc(dname)
                            {
                            // if (window.XMLHttpRequest)
                            //   {
                            //   var xhttp=new XMLHttpRequest();
                            //   }
                            // else
                            //   {
                            //   xhttp=new ActiveXObject("Microsoft.XMLHTTP");
                            //   }
                            var xhttp = new XMLHttpRequest();
                          	//console.log("form outer "+outer);

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
                                if(parseInt(newversion)>parseInt(oldversion))
                                {
                                    var firstparam = newversion;
                                    var secondparam = oldversion;
                                }
                                else
                                {
                                    var firstparam = oldversion;
                                    var secondparam = newversion
                                }
                                var diffxml=loadXMLDoc("resource/svg/block/matching/"+mapid+"/"+firstparam+"/"+secondparam);
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
                                if(parseInt(newversion)>parseInt(oldversion))
                                {
                                    var firstparam = newversion;
                                    var secondparam = oldversion;
                                }
                                else
                                {
                                    var firstparam = oldversion;
                                    var secondparam = newversion
                                }

                                var diffxml=loadXMLDoc("resource/svg/block/matching/"+mapid+"/"+firstparam+"/"+secondparam);
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
                                if(parseInt(newversion) > parseInt(oldversion))
                                    var version = oldversion;
                                else
                                    var version = newversion;
                                var url = "resource/svg/block/"+mapid+"/1/1/"+version;
                                var xml=loadXMLDoc(url);
                                //alert(xml);
                                //var path="/svg:svg/svg:*[2]/svg:*[31]";
                                getDeleteXML(mapid,newversion,oldversion);
                                var k=1;
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
                            //          alert(nodes[0].childNodes[0].nodeValue);
                                    }
                                    // code for Mozilla, Firefox, Opera, etc.
                                    else if (document.implementation && document.implementation.createDocument)
                                    {
                                        //var nsResolver = document.createNSResolver( xml.ownerDocument == null ? xml.documentElement : xml.ownerDocument.documentElement );
                                        var nodes=xml.evaluate(path, xml, NSResolver, XPathResult.ANY_TYPE, null);
                                        var result=nodes.iterateNext();

                            //          alert(nodes);
                                        //while (result)
                                        //{
                                        if(result!= null)
                                        {
                                        	//if(result.nodeName=="g"||result.nodeName=="text")
                                        	if(result.nodeName=="g")
                                        	{
                                                if (result.getElementsByTagName("text")[0] == "undefined" || result.getElementsByTagName("text")[0] == null)
                                                    $scope.deletenode.push(k+result.innerHTML);
                                                else
                                                    $scope.deletenode.push(k+result.getElementsByTagName("text")[0].innerHTML);

                                                var x_coor = -10;
                                                var y_coor = -20;
                                                if(result.nodeName=="g"&&!result.hasAttribute("transform")){
                                                	x_coor = result.getElementsByTagName("text")[0].getAttribute("x")-10;
                                                	y_coor = result.getElementsByTagName("text")[0].getAttribute("y")-20;
                                                }

                                                if(result.nodeName=="g") {
                                                    var p = document.createElement("text")
                                                    p.innerText = k;
                                                    p.setAttribute("font-size","20pt");
                                                    p.setAttribute("fill","red");
                                                    p.setAttribute("x",x_coor);
                                                    p.setAttribute("y",y_coor);
                                                    p.setAttribute("text-anchor","end");
                                                    result.appendChild(p);
                                                    k++;
                                                }
                                                else{
                                                    var p = document.createElement("text")
                                                    p.innerText = k;
                                                    p.setAttribute("font-size","20pt");
                                                    p.setAttribute("fill","red");
                                                    p.setAttribute("x",x_coor);
                                                    p.setAttribute("y",y_coor);
                                                    p.setAttribute("text-anchor","end");
                                                    result.parentNode.appendChild(p);
                                                    k++;
                                                }
                                            }
                                        	var parent = result.parentNode;
                                        	if(parent.tagName != "g" || parent.getAttribute("class")!="entity"){
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
                                        }

                                            //result=nodes.iterateNext();
                                        //}
                                    }
                                }
                                //alert(xml.toString());
                                $scope.svg1 = $sce.trustAsHtml(xml.documentElement.innerHTML);

                            };

                            var loadSVG2 = function(mapid,newversion,oldversion){
                                if(parseInt(newversion) > parseInt(oldversion))
                                    var version = newversion;
                                else
                                    var version = oldversion;
                                var url = "resource/svg/block/"+mapid+"/1/1/"+version;
                                var xml=loadXMLDoc(url);
                                //alert(xml);
                                //var path="/svg:svg/svg:*[2]/svg:*[31]";
                                getInsertXML(mapid,newversion,oldversion);
                                var k=1;
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
                            //          alert(nodes[0].childNodes[0].nodeValue);
                                    }
                                    // code for Mozilla, Firefox, Opera, etc.
                                    else if (document.implementation && document.implementation.createDocument)
                                    {
                                        //var nsResolver = document.createNSResolver( xml.ownerDocument == null ? xml.documentElement : xml.ownerDocument.documentElement );
                                        var nodes=xml.evaluate(path, xml, NSResolver, XPathResult.ANY_TYPE, null);
                                        var result=nodes.iterateNext();

                            //          alert(nodes);
                                        //while (result)
                                        //{
                                        if(result!= null)
                                        {

                                            //if(result.nodeName=="g"||result.nodeName=="text")
                                        	if(result.nodeName=="g")
                                        	{

                                                if (result.getElementsByTagName("text")[0] == "undefined" || result.getElementsByTagName("text")[0] == null)
                                                    $scope.addnode.push(k+result.innerHTML);
                                                else
                                                    $scope.addnode.push(k+result.getElementsByTagName("text")[0].innerHTML);


                                                var x_coor = -10;
                                                var y_coor = -20;
                                                if(result.nodeName=="g"&&!result.hasAttribute("transform")){
                                                  if(x_coor = result.getElementsByTagName("text").length != 0){
                                                	   x_coor = result.getElementsByTagName("text")[0].getAttribute("x")-10;
                                                	   y_coor = result.getElementsByTagName("text")[0].getAttribute("y")-20;
                                                  }
                                                }

                                                if(result.nodeName=="g") {
                                                    var p = document.createElement("text")
                                                    p.innerText = k;
                                                    p.setAttribute("font-size","20pt");
                                                    p.setAttribute("fill","blue");
                                                    p.setAttribute("x", x_coor);
                                                    p.setAttribute("y", y_coor);
                                                    p.setAttribute("text-anchor","end");
                                                    result.appendChild(p);
                                                    k++;
                                                }
                                                else{
                                                    var p = document.createElement("text")
                                                    p.innerText = k;
                                                    p.setAttribute("font-size","20pt");
                                                    p.setAttribute("fill","blue");
                                                    p.setAttribute("x", x_coor);
                                                    p.setAttribute("y", y_coor);
                                                    p.setAttribute("text-anchor","end");
                                                    result.parentNode.appendChild(p);
                                                    k++;
                                                }
                                            }
                                        	var parent = result.parentNode;
                                        	if(parent.tagName != "g" || parent.getAttribute("class")!="entity"){
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

                                        }

                                            //result=nodes.iterateNext();
                                        //}
                                    }
                                }
                                //alert(xml.toString());
                                $scope.svg2 = $sce.trustAsHtml(xml.documentElement.innerHTML);
                            };

                            $scope.showSelection = false;
                            loadSVG1(mapid,newversion,oldversion);
                            loadSVG2(mapid,newversion,oldversion);

                            var getInsert = function(){

                            };




                        }]
                })
        }]
);
