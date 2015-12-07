var x_base_min = 27390;
var	x_base_max = 27484;
var	y_base_min = 5324;
var	y_base_max = 5370;
var	x_base_zero = 27430;
var	y_base_zero = 5340;

var isBaseLoaded = false;
var scrollLeftBase = null;
var scrollTopBase = null;

function initBaseMap(){
	var zoom = 1;
	edge = 50*zoom;

	waWidth = $('#workarea').width();
	waHeight = $('#workarea').height();

	x_base_left = x_base_zero;
	y_base_top = y_base_zero;

	x_base_right = x_base_left + Math.ceil(waWidth/edge);
	if (x_base_right > x_base_max) {
		x_base_right = x_base_max;
	};
	y_base_bottom = y_base_top + Math.ceil(waHeight/edge);
	if (y_base_bottom > y_base_max) {
		y_base_bottom = y_base_max;
	};

	for (var i = x_base_left; i <= x_base_right; i++)
		for (var j = y_base_top; j <= y_base_bottom; j++) {
		var img = document.createElementNS("http://www.w3.org/2000/svg", "image");
		img.setAttributeNS(null, "x", "" + (i - 27430) * (50 - 0));
		img.setAttributeNS(null, "y", "" + (j - 5340) * (50 - 0));
		img.setAttributeNS(null, "width", "50");
		img.setAttributeNS(null, "height", "50");
		img.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", "/resource/svg/block/base/"+i + "/" + j)
		$('#canvasBackground').append(img);
		};

	hideSVGEntityText(zoom);
}

function initLocation(w_area){
	scrollLeftBase = w_area.scrollLeft;
	scrollTopBase = w_area.scrollTop;
	//alert(scrollLeftBase+" "+scrollTopBase)
	isBaseLoaded = true;
}

function resetWindowLocation(){
	offsetLeft = $('#workarea').width()/2;
	offsetTop = $('#workarea').height()/2;
	//console.log(offsetLeft+" "+waHeight);
	svgEditor.updateCanvas(false,{x:offsetLeft,y:offsetTop});
}

function reloadBaseMap(w_area){
	$('#canvasBackground').text('');
	var zoom = svgCanvas.getZoom();
	edge = 50*zoom;
	xLen = w_area.scrollLeft/zoom - scrollLeftBase;
	yLen = w_area.scrollTop/zoom - scrollTopBase;
	console.log(xLen+" "+yLen);
	if (xLen > 0) {
		moveX = Math.ceil(xLen/50);
	};
		moveX = Math.floor(xLen/50);
	if (yLen > 0) {
		moveY = Math.ceil(yLen/50);
	};
		moveY = Math.floor(yLen/50);
	x_base_left = x_base_zero + moveX;
	if (x_base_left < x_base_min) {
		x_base_left = x_base_min;
	};
	y_base_top = y_base_zero + moveY;
	if (y_base_top < y_base_min) {
		y_base_top = y_base_min;
	};
	if (x_base_left > x_base_max) {
		x_base_left = x_base_max;
	};
	if (y_base_top > y_base_max) {
		y_base_top = y_base_max;
	};
	waWidth = $('#workarea').width();
	waHeight = $('#workarea').height();
	//console.log(waWidth+" "+waHeight);
	x_base_right = x_base_left + Math.ceil(waWidth/edge);
	if (x_base_right > x_base_max) {
		x_base_right = x_base_max;
	};
	y_base_bottom = y_base_top + Math.ceil(waHeight/edge);
	if (y_base_bottom > y_base_max) {
		y_base_bottom = y_base_max;
	};
	console.log(x_base_left+","+x_base_right+","+y_base_top+","+y_base_bottom);
	for (var i = x_base_left; i <= x_base_right; i++)
	for (var j = y_base_top; j <= y_base_bottom; j++) {
		var img = document.createElementNS("http://www.w3.org/2000/svg", "image");
		img.setAttributeNS(null, "x", "" + (i - x_base_zero) * (50 - 0));
		img.setAttributeNS(null, "y", "" + (j - y_base_zero) * (50 - 0));
		img.setAttributeNS(null, "width", "50");
		img.setAttributeNS(null, "height", "50");
		img.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", "/resource/svg/block/base/"+i + "/" + j)
		$('#canvasBackground').append(img);
	};
	hideSVGEntityText(zoom);
}

function hideSVGEntityText(zoom){
	if(zoom < 3){

        $(".entity > text").css("fill-opacity", "0");
        console.log("hide");
    }

    else{

        $(".entity > text").css("fill-opacity", "1");
        console.log("show");
    }
}
