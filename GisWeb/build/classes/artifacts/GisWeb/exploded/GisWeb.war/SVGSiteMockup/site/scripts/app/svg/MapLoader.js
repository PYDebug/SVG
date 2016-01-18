var svgNS = "http://www.w3.org/2000/svg";
var xlinkNS = "http://www.w3.org/1999/xlink";
var widthSize = 50;
var heightSize = 50;
var svgCanvasRoot;
var baseLayerRoot;

//mode-->0:use own maps 1:use Baidu maps
//basetype-->0:svg map 1:tiandi png 2:baidu png
var mode = 1;
var basetype = 1;

//window attribute
var window_width;
var window_height;
var scale;
var offset_x;
var offset_y;

//load attribute
var x_left;
var x_right;
var y_up;
var y_down;

var x_min = 27390;
var x_max = 27484;
var y_min = 5324;
var y_max = 5370;
var x_base = 27430;
var y_base = 5340;


function loadBlock(root, x, y, toX, toY) {
	var img = document.createElementNS(svgNS, "image");
	img.setAttributeNS(null, "x", "" + toX);
	img.setAttributeNS(null, "y", "" + toY);
	img.setAttributeNS(null, "width", widthSize + "");
	img.setAttributeNS(null, "height", heightSize + "");

	switch(basetype){
	case 0:
		img.setAttributeNS(xlinkNS, "xlink:href", "resource/svg/block/base/"+x + "/" + y);
		break;
	case 1:
		img.setAttributeNS(xlinkNS, "xlink:href", "resource/png/block/tiandi/"+x + "/" + y);
		break;
	case 2:
		img.setAttributeNS(xlinkNS, "xlink:href", "resource/png/block/baidu/"+x + "/" + y);
		break;
	}
	img.async = true;
	root.appendChild(img);
}

function loadArea(fromX, toX, fromY, toY, root) {
	for (var i = fromX; i <= toX; i++)
		for (var j = fromY; j <= toY; j++) {
			if(i>=x_min && i<=x_max)
				if(j>=y_min && j<=y_max)
					loadBlock(root, i, j, (i - x_base) * (widthSize - 0), (j - y_base)
					* (heightSize - 0));
		}

}

function loadMap(evt) {
	root = evt.target;
	g = root.getElementById("viewport");
	svgCanvasRoot = g;
	var base = g.getElementsByClassName("baseLayer")[0];
	baseLayerRoot = base;
	var parent = root.parentNode;
	//console.log(parent.id);





	if (window.getComputedStyle) {
	    style = window.getComputedStyle(root, null);
	}else{
		style = root.currentStyle;
	}
    console.log("width=" + style.width + "\nheight=" + style.height);

    window_width = parseInt(style.width)
	window_height = parseInt(style.height)
	//alert(g.clientWidth);
	scale = 1;
	offset_x = 0;
	offset_y = 0;

	x_left = parseInt(offset_x/widthSize)+x_base-1;
	x_right = parseInt((offset_x+window_width)/widthSize+1)+x_base;
	y_up = parseInt(offset_y/heightSize)+y_base-1;
	y_down = parseInt((offset_y+window_height)/heightSize+1)+y_base;

	console.log("width=" + window_width + " height=" + window_height);
	console.log(x_left+","+x_right+","+y_up+","+y_down);



	if(mode == 0){
		/*var map = new BMap.Map("gs-mapContent");          // 创建地图实例
	    var point = new BMap.Point(116.404, 39.915);  // 创建点坐标
	    map.centerAndZoom(point, 15);                 // 初始化地图，设置中心点坐标和地图级别
	    map.enableScrollWheelZoom(true);


		var svg = document.createElementNS(svgNS, "svg");
		svg.setAttributeNS(null, "width", "100%");
		svg.setAttributeNS(null, "height", "100%");
		//svg.setAttributeNS(svgNS,"xmlns",svgNS);

		svg.id = "viewport";
		parent.appendChild(svg);

		base = svg;
		root = svg;
		*/
		loadArea(x_left,x_right,y_up,y_down,base);
	}
	else if(mode == 1){
		//var map = new BMap.Map("allmap");  // 创建Map实例
		var map = new BMap.Map("gs-mapContent");  // 创建Map实例
		map.centerAndZoom("上海",15);
		map.enableScrollWheelZoom(true);

	}

	//loadLayer(g,"sch");
	// loadArea(27390,27400,5324,5334,g);

	// loadBlock(root,27392,5367,100,100);
	// alert(root);
	initWithRoot(root);
}

function loadMapByRoot(root) {
	g = root.getElementById("viewport");
	svgRoot = g;
	svgCanvasRoot = g.getElementsByClassName("content")[0];;
	var base = g.getElementsByClassName("baseLayer")[0];
	baseLayerRoot = base;
	var parent = root.parentNode;
	//console.log(parent.id);





	if (window.getComputedStyle) {
		style = window.getComputedStyle(root, null);
	}else{
		style = root.currentStyle;
	}
	console.log("width=" + style.width + "\nheight=" + style.height);

	window_width = parseInt(style.width)
	window_height = parseInt(style.height)
	//alert(g.clientWidth);
	scale = 1;
	offset_x = 0;
	offset_y = 0;

	x_left = parseInt(offset_x/widthSize)+x_base-1;
	x_right = parseInt((offset_x+window_width)/widthSize+1)+x_base;
	y_up = parseInt(offset_y/heightSize)+y_base-1;
	y_down = parseInt((offset_y+window_height)/heightSize+1)+y_base;

	console.log("width=" + window_width + " height=" + window_height);
	console.log(x_left+","+x_right+","+y_up+","+y_down);



	if(mode == 0){
		/*var map = new BMap.Map("gs-mapContent");          // 创建地图实例
		 var point = new BMap.Point(116.404, 39.915);  // 创建点坐标
		 map.centerAndZoom(point, 15);                 // 初始化地图，设置中心点坐标和地图级别
		 map.enableScrollWheelZoom(true);


		 var svg = document.createElementNS(svgNS, "svg");
		 svg.setAttributeNS(null, "width", "100%");
		 svg.setAttributeNS(null, "height", "100%");
		 //svg.setAttributeNS(svgNS,"xmlns",svgNS);

		 svg.id = "viewport";
		 parent.appendChild(svg);

		 base = svg;
		 root = svg;
		 */
		loadArea(x_left,x_right,y_up,y_down,base);
	}
	else if(mode == 1){
		//var map = new BMap.Map("allmap");  // 创建Map实例
		// var map = new BMap.Map("gs-mapContent");  // 创建Map实例
		// map.centerAndZoom("上海",15);
		// map.enableScrollWheelZoom(true);
		 var cur_z = 0;
	}

	//loadLayer(g,"sch");
	// loadArea(27390,27400,5324,5334,g);

	// loadBlock(root,27392,5367,100,100);
	// alert(root);
	initWithRoot(root);
}

function loadLayer(root, layer){
	var xmlHttp = new XMLHttpRequest();
	//console.log("form outer "+outer);
	xmlHttp.onreadystatechange = function() {

		if (xmlHttp.readyState==4 && xmlHttp.status==200)
	    {

			var g = document.createElementNS(svgNS, "g");
			g.setAttributeNS(null, "class", "layer");
			g.setAttributeNS(null, "layerid", layer);
			g.innerHTML=xmlHttp.responseText;
			root.appendChild(g);

			hideInfo(scale);
	    }
	}

	xmlHttp.open("GET", "resource/svg/block/"+layer+"/1/1"+"?r="+Math.random(), true);
	xmlHttp.send();
}

var transform = "translate("+(x_min-x_base)*widthSize+", "+(y_min-y_base)*heightSize+")"

function loadLayerBlock(root, layer, x, y){
	var xmlHttp = new XMLHttpRequest();
	//console.log("form outer "+outer);
	xmlHttp.onreadystatechange = function() {

		if (xmlHttp.readyState==4 && xmlHttp.status==200)
	    {

			var g = document.createElementNS(svgNS, "g");
			g.setAttributeNS(null, "class", "layer");
			g.setAttributeNS(null, "layerid", layer);
			g.setAttributeNS(null,"transform",transform);
			g.innerHTML=xmlHttp.responseText;
			root.appendChild(g);

			hideInfo(scale);
	    }
	}

	xmlHttp.open("GET", "resource/svg/block/"+layer+"/"+x+"/"+y+"?r="+Math.random(), true);
	xmlHttp.send();
}

function loadLayer(layer){
	var xmlHttp = new XMLHttpRequest();
	//console.log("form outer "+outer);
	xmlHttp.onreadystatechange = function() {

		if (xmlHttp.readyState==4 && xmlHttp.status==200)
	    {

			var g = document.createElementNS(svgNS, "g");
			g.setAttributeNS(null, "class", "layer");
			g.setAttributeNS(null, "layerid", layer);
			if(xmlHttp.responseXML.rootElement.tagName == "svg")
				g.innerHTML = xmlHttp.responseXML.rootElement.innerHTML;
			else
				g.innerHTML=xmlHttp.responseText;
			g.async = true;
			svgCanvasRoot.appendChild(g);
			/*g.ready(function(e) {
		        $(".entity").mouseover(function(e) {
		            showWin(e);

		        });
		        $(".entity").mouseout(function(e) {
		            hideWin(e);

		        });

		    });*/
			var entities = g.getElementsByClassName('entity');
			console.log(entities.length);
			for(entity in entities){
				var temp = entities[entity];
				if(temp.nodeType == 1){
					console.log("add attributes");
					temp.setAttribute("onmouseover","showWin(evt)");
					temp.setAttribute("onmouseout","hideWin(evt)");
					/*temp.mouseover(function(e) {
/*			            showWin(e);

			        });*/
			        /*temp.mouseout(function(e) {
			            hideWin(e);

			        });*/
				}
			}
			console.log(entities.length);
			hideInfo(scale);
	    }
	}

	xmlHttp.open("GET", "resource/svg/block/"+layer+"/1/1"+"?r="+Math.random(), true);
	xmlHttp.send();
}

function loadLayerAndTime(layer,time){
	var xmlHttp = new XMLHttpRequest();
	//console.log("form outer "+outer);
	xmlHttp.onreadystatechange = function() {

		if (xmlHttp.readyState==4 && xmlHttp.status==200)
		{

			var g = document.createElementNS(svgNS, "g");
			g.setAttributeNS(null, "class", "layer");
			g.setAttributeNS(null, "layerid", layer);
			if(xmlHttp.responseXML.rootElement.tagName == "svg")
				g.innerHTML = xmlHttp.responseXML.rootElement.innerHTML;
			else
				g.innerHTML=xmlHttp.responseText;
			g.async = true;
			svgCanvasRoot.appendChild(g);
			/*g.ready(function(e) {
			 $(".entity").mouseover(function(e) {
			 showWin(e);

			 });
			 $(".entity").mouseout(function(e) {
			 hideWin(e);

			 });

			 });*/
			var entities = g.getElementsByClassName('entity');
			console.log(entities.length);
			for(entity in entities){
				var temp = entities[entity];
				if(temp.nodeType == 1){
					console.log("add attributes");
					temp.setAttribute("onmouseover","showWin(evt)");
					temp.setAttribute("onmouseout","hideWin(evt)");
					/*temp.mouseover(function(e) {
/*			            showWin(e);

			        });*/
			        /*temp.mouseout(function(e) {
			            hideWin(e);

			        });*/
				}
			}
			hideInfo(scale);
		}
	}

	xmlHttp.open("GET", "resource/svg/block/"+layer+"/1/1/"+time+"?r="+Math.random(), true);
	xmlHttp.send();
}

function loadLayerBlock(layer, x, y){
	var xmlHttp = new XMLHttpRequest();
	//console.log("form outer "+outer);
	xmlHttp.onreadystatechange = function() {

		if (xmlHttp.readyState==4 && xmlHttp.status==200)
	    {

			var g = document.createElementNS(svgNS, "g");
			g.setAttributeNS(null, "class", "layer");
			g.setAttributeNS(null, "layerid", layer);
			//g.setAttributeNS(null,"transform",transform);
			if(xmlHttp.responseXML){
				if(xmlHttp.responseXML.rootElement){
					if(xmlHttp.responseXML.rootElement.tagName == "svg")
						g.innerHTML = xmlHttp.responseXML.rootElement.innerHTML;
					else
						g.innerHTML=xmlHttp.responseText;
				}else
					g.innerHTML=xmlHttp.responseText;
			}else
				g.innerHTML=xmlHttp.responseText;
			g.async = true;
			svgCanvasRoot.appendChild(g);
			var entities = g.getElementsByClassName('entity');
			//console.log(entities.length);
			for(entity in entities){
				var temp = entities[entity];
				if(temp.nodeType == 1){
					console.log("add attributes");
					temp.setAttribute("onmouseover","showWin(evt)");
					temp.setAttribute("onmouseout","hideWin(evt)");
					/*temp.mouseover(function(e) {
/*			            showWin(e);

			        });*/
			        /*temp.mouseout(function(e) {
			            hideWin(e);

			        });*/
				}
			}
			hideInfo(scale);
	    }
	}

	xmlHttp.open("GET", "resource/svg/block/"+layer+"/"+x+"/"+y+"?r="+Math.random(), true);
	xmlHttp.send();
}

function removeLayer(layer){
	var layers = svgCanvasRoot.getElementsByClassName("layer");
	for(ly in layers){
		var temp = layers[ly];
		if(temp.nodeType == 1 && temp.getAttribute("layerid")=="result")
			svgCanvasRoot.removeChild(temp);
	}
}

function clearLayerContent(){
	svgCanvasRoot.innerHTML = "";
}

function reloadWindow(){
	console.log("scale:"+scale+" offset_x:"+offset_x+" offset_y:"+offset_y);

	x_left_new = parseInt(offset_x/widthSize)+x_base-1;
	x_right_new = parseInt((offset_x+window_width/scale)/widthSize+1)+x_base;
	y_up_new = parseInt(offset_y/heightSize)+y_base-1;
	y_down_new = parseInt((offset_y+window_height/scale)/heightSize+1)+y_base;


	baseLayerRoot.innerHTML="";

	hideInfo(scale);

	if(mode == 0)
		loadArea(x_left_new,x_right_new,y_up_new,y_down_new,baseLayerRoot);

	console.log(x_left_new+","+x_right_new+","+y_up_new+","+y_down_new);
}
