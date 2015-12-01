/**
 * MapLoaderDouble.js
 * Load Double SVG Area
 * Refer to MapLoader.js
 * just for baseLayer of the comparison window
 */

//var svgNS = "http://www.w3.org/2000/svg";
//var xlinkNS = "http://www.w3.org/1999/xlink";
//var widthSize = 50;
//var heightSize = 50;
var svgCanvasRootL;
var svgCanvasRootR;
var baseLayerRootL;
var baseLayerRootR;

//mode-->0:use own maps 1:use Baidu maps
//basetype-->0:svg map 1:tiandi png 2:baidu png
//var mode = 0;
//var basetype = 1;

//window attribute
var window_widthD = 600;
var window_heightD = 600;
var scaleD;
var offset_xD;
var offset_yD;

//load attribute
var x_leftD;
var x_rightD;
var y_upD;
var y_downD;

/*var x_min = 27390;
var x_max = 27484;
var y_min = 5324;
var y_max = 5370;
var x_base = 27430;
var y_base = 5340;*/
function loadDoubleMapHTML(){
	var gL = document.getElementById("windowL");
	var gR = document.getElementById("windowR");
	
	loadMapByRootL(gL);
	loadMapByRootR(gR);
}

function loadMapByRootL(root){
	var g = root.getElementById("viewportL");
	svgCanvasRootL = g;	
	var base = g.getElementsByClassName("baseLayer")[0];
	baseLayerRootL = base;
	var parent = root.parentNode;
	var style;
	if (window.getComputedStyle) {
	    style = window.getComputedStyle(root, null);
	}else{
		style = root.currentStyle;
	}
    console.log("width=" + style.width + "\nheight=" + style.height);
    
    window_widthD = parseInt(style.width)
	window_heightD = parseInt(style.height)
	
	scaleD = 1;
	offset_xD = 0;
	offset_yD = 0;
	
	x_leftD = parseInt(offset_xD/widthSize)+x_base-1;
	x_rightD = parseInt((offset_xD+window_widthD)/widthSize+1)+x_base+1;
	y_upD = parseInt(offset_yD/heightSize)+y_base-1;
	y_downD = parseInt((offset_yD+window_heightD)/heightSize+1)+y_base+1;
	
	console.log("width=" + window_widthD + " height=" + window_heightD);
	console.log(x_leftD+","+x_rightD+","+y_upD+","+y_downD);
	if(mode == 0){
		loadArea(x_leftD,x_rightD,y_upD,y_downD,base);
	}
	else if(mode == 1){
		
	}
	
	initL(svgCanvasRootL);
}

function loadMapL(evt){
	var root = evt.target;
	var g = root.getElementById("viewportL");
	svgCanvasRootL = g;	
	var base = g.getElementsByClassName("baseLayer")[0];
	baseLayerRootL = base;
	var parent = root.parentNode;
	var style;
	if (window.getComputedStyle) {
	    style = window.getComputedStyle(root, null);
	}else{
		style = root.currentStyle;
	}
    console.log("width=" + style.width + "\nheight=" + style.height);
    
    window_widthD = parseInt(style.width)
	window_heightD = parseInt(style.height)
	
	scaleD = 1;
	offset_xD = 0;
	offset_yD = 0;
	
	x_leftD = parseInt(offset_xD/widthSize)+x_base-1;
	x_rightD = parseInt((offset_xD+window_widthD)/widthSize+1)+x_base+1;
	y_upD = parseInt(offset_yD/heightSize)+y_base-1;
	y_downD = parseInt((offset_yD+window_heightD)/heightSize+1)+y_base+1;
	
	console.log("width=" + window_widthD + " height=" + window_heightD);
	console.log(x_leftD+","+x_rightD+","+y_upD+","+y_downD);
	if(mode == 0){
		loadArea(x_leftD,x_rightD,y_upD,y_downD,base);
	}
	else if(mode == 1){
		
	}
	
	initL(svgCanvasRootL);
}

function loadMapByRootR(root){
	var g = root.getElementById("viewportR");
	svgCanvasRootR = g;	
	var base = g.getElementsByClassName("baseLayer")[0];
	baseLayerRootR = base;
	var parent = root.parentNode;
	var style;
	if (window.getComputedStyle) {
	    style = window.getComputedStyle(root, null);
	}else{
		style = root.currentStyle;
	}
    console.log("width=" + style.width + "\nheight=" + style.height);
    
    window_widthD = parseInt(style.width)
	window_heightD = parseInt(style.height)
	
	scaleD = 1;
	offset_xD = 0;
	offset_yD = 0;
	
	x_leftD = parseInt(offset_xD/widthSize)+x_base-1;
	x_rightD = parseInt((offset_xD+window_widthD)/widthSize+1)+x_base;
	y_upD = parseInt(offset_yD/heightSize)+y_base-1;
	y_downD = parseInt((offset_yD+window_heightD)/heightSize+1)+y_base;
	
	console.log("width=" + window_widthD + " height=" + window_heightD);
	console.log(x_leftD+","+x_rightD+","+y_upD+","+y_downD);
	if(mode == 0){
		loadArea(x_leftD,x_rightD,y_upD,y_downD,base);
	}
	else if(mode == 1){
		
	}
	
	initR(svgCanvasRootR);
}

function loadMapR(evt){
	var root = evt.target;
	var g = root.getElementById("viewportR");
	svgCanvasRootR = g;	
	var base = g.getElementsByClassName("baseLayer")[0];
	baseLayerRootR = base;
	var parent = root.parentNode;
	var style;
	if (window.getComputedStyle) {
	    style = window.getComputedStyle(root, null);
	}else{
		style = root.currentStyle;
	}
    console.log("width=" + style.width + "\nheight=" + style.height);
    
    window_widthD = parseInt(style.width)
	window_heightD = parseInt(style.height)
	
	scaleD = 1;
	offset_xD = 0;
	offset_yD = 0;
	
	x_leftD = parseInt(offset_xD/widthSize)+x_base-1;
	x_rightD = parseInt((offset_xD+window_widthD)/widthSize+1)+x_base;
	y_upD = parseInt(offset_yD/heightSize)+y_base-1;
	y_downD = parseInt((offset_yD+window_heightD)/heightSize+1)+y_base;
	
	console.log("width=" + window_widthD + " height=" + window_heightD);
	console.log(x_leftD+","+x_rightD+","+y_upD+","+y_downD);
	if(mode == 0){
		loadArea(x_leftD,x_rightD,y_upD,y_downD,base);
	}
	else if(mode == 1){
		
	}
	
	initR(svgCanvasRootR);
}





function reloadWindowD(){
	console.log("scaleD:"+scaleD+" offset_xD:"+offset_xD+" offset_yD:"+offset_yD);
	
	x_left_newD = parseInt(offset_xD/widthSize)+x_base-1;
	x_right_newD = parseInt((offset_xD+window_widthD/scaleD)/widthSize+1)+x_base;
	y_up_newD = parseInt(offset_yD/heightSize)+y_base-1;
	y_down_newD = parseInt((offset_yD+window_heightD/scaleD)/heightSize+1)+y_base;

	
	baseLayerRootL.innerHTML="";
	baseLayerRootR.innerHTML="";
	
	if(mode == 0){
		loadArea(x_left_newD,x_right_newD,y_up_newD,y_down_newD,baseLayerRootL);
		loadArea(x_left_newD,x_right_newD,y_up_newD,y_down_newD,baseLayerRootR);
	}

	console.log(x_left_newD+","+x_right_newD+","+y_up_newD+","+y_down_newD);
}