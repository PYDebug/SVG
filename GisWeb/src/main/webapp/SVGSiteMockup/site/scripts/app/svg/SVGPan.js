/**
 *  SVGPan library 1.2.1
 * ======================
 *
 * Given an unique existing element with id "viewport" (or when missing, the first g
 * element), including the the library into any SVG adds the following capabilities:
 *
 *  - Mouse panning
 *  - Mouse zooming (using the wheel)
 *  - Object dragging
 *
 * You can configure the behaviour of the pan/zoom/drag with the variables
 * listed in the CONFIGURATION section of this file.
 *
 * Known issues:
 *
 *  - Zooming (while panning) on Safari has still some issues
 *
 * Releases:
 *
 * 1.2.1, Mon Jul  4 00:33:18 CEST 2011, Andrea Leofreddi
 *	- Fixed a regression with mouse wheel (now working on Firefox 5)
 *	- Working with viewBox attribute (#4)
 *	- Added "use strict;" and fixed resulting warnings (#5)
 *	- Added configuration variables, dragging is disabled by default (#3)
 *
 * 1.2, Sat Mar 20 08:42:50 GMT 2010, Zeng Xiaohui
 *	Fixed a bug with browser mouse handler interaction
 *
 * 1.1, Wed Feb  3 17:39:33 GMT 2010, Zeng Xiaohui
 *	Updated the zoom code to support the mouse wheel on Safari/Chrome
 *
 * 1.0, Andrea Leofreddi
 *	First release
 *
 * This code is licensed under the following BSD license:
 *
 * Copyright 2009-2010 Andrea Leofreddi <a.leofreddi@itcharm.com>. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY Andrea Leofreddi ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Andrea Leofreddi OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of Andrea Leofreddi.
 */

"use strict";

/// CONFIGURATION
/// ====>

var enablePan = 1; // 1 or 0: enable or disable panning (default enabled)
var enableZoom = 1; // 1 or 0: enable or disable zooming (default enabled)
var enableDrag = 0; // 1 or 0: enable or disable dragging (default disabled)
var _cur_z = 0;
var _level = 1;
var _pre_level = 1;

var x_base_zero = 27430;
var y_base_zero = 5340;

function transLonLatToCoordinate(lon,lat) {
    var topTileFromX = -180;
    var topTileFromY = 90;

    var tdtScale = {
        18: 5.36441802978515E-06,
        17: 1.07288360595703E-05,
        16: 2.1457672119140625E-05,
        15: 4.29153442382814E-05,
        14: 8.58306884765629E-05,
        13: 0.000171661376953125,
        12: 0.00034332275390625,
        11: 0.0006866455078125,
        10: 0.001373291015625,
        9: 0.00274658203125,
        8: 0.0054931640625,
        7: 0.010986328125,
        6: 0.02197265625,
        5: 0.0439453125,
        4: 0.087890625,
        3: 0.17578125,
        2: 0.3515625,
        1: 0.703125
    };
    var coef = tdtScale[_cur_z+15] * 256;

    var x_num = (lon - topTileFromX) / coef;
    var y_num = (topTileFromY - lat) / coef;

    x_num = (x_num-x_base_zero)*256;
    y_num = (y_num-y_base_zero)*256;
    return {x:x_num,y:y_num};
}

/**根据行列号返回经纬度坐标
 * Created by SHIKUN on 2015/10/11.
 */
function transCoordinateToLonLat(x_num,y_num) {
    var topTileFromX = -180;
    var topTileFromY = 90;
    var tdtScale = {
        18: 5.36441802978515E-06,
        17: 1.07288360595703E-05,
        16: 2.1457672119140625E-05,
        15: 4.29153442382814E-05,
        14: 8.58306884765629E-05,
        13: 0.000171661376953125,
        12: 0.00034332275390625,
        11: 0.0006866455078125,
        10: 0.001373291015625,
        9: 0.00274658203125,
        8: 0.0054931640625,
        7: 0.010986328125,
        6: 0.02197265625,
        5: 0.0439453125,
        4: 0.087890625,
        3: 0.17578125,
        2: 0.3515625,
        1: 0.703125
    };

    x_num = x_num/256 + x_base_zero;
    y_num = y_num/256 + y_base_zero;

    var coef = tdtScale[_cur_z+15] * 256;
    var lon=x_num*coef+topTileFromX;
    var lat=topTileFromY-y_num*coef;

    return{lon:lon,lat:lat};
}

/// <====
/// END OF CONFIGURATION

var root = document.documentElementz;
var state = 'none', svgRoot, stateTarget, stateOrigin, stateTf;
function initSVG(){
	root = document.documentElement;
	setupHandlers(root);
}

function initHTML(){
	root = document.documentElement;
	root = root.getElementsByTagName('svg')[0];
	setupHandlers(root);
}

function initWithRoot(root){
	setupHandlers(root);
	_cur_z = 0;
}

/**
 * Register handlers
 */
function setupHandlers(root){
	setAttributes(root, {
		"onmouseup" : "handleMouseUp(evt)",
		"onmousedown" : "handleMouseDown(evt)",
		"onmousemove" : "handleMouseMove(evt)"
		//"onmouseout" : "handleMouseUp(evt)", // Decomment this to stop the pan functionality when dragging out of the SVG element
	});
	if(navigator.userAgent.toLowerCase().indexOf('webkit') >= 0)
		root.addEventListener('mousewheel', handleMouseWheel, false); // Chrome/Safari
	else{
		root.addEventListener('mousewheel', handleMouseWheel, false);
		root.addEventListener('DOMMouseScroll', handleMouseWheel, false); // Others
	}
}

/**
 * Retrieves the root element for SVG manipulation. The element is then cached into the svgRoot global variable.
 */
function getRoot(root) {
	if(typeof(svgRoot) == "undefined") {
		var g = null;

		g = root.getElementById("viewport");

		if(g == null)
			g = root.getElementsByTagName('g')[0];

		if(g == null)
			alert('Unable to obtain SVG root element');

		setCTM(g, g.getCTM());

		g.removeAttribute("viewBox");

		svgRoot = g;
	}

	return svgRoot;
}

/**
 * Instance an SVGPoint object with given event coordinates.
 */
function getEventPoint(evt) {
	var p = root.createSVGPoint();

	p.x = evt.clientX;
	p.y = evt.clientY;

	return p;
}

/**
 * Sets the current transform matrix of an element.
 */
function setCTM(element, matrix) {
	var s = "matrix(" + matrix.a + "," + matrix.b + "," + matrix.c + "," + matrix.d + "," + matrix.e + "," + matrix.f + ")";

	element.setAttribute("transform", s);
}

function MySetCTM(element, x, y){
  var s = "translate(" + x + "," + y + ")";

  element.setAttribute("transform", s);
}

/**
 * Dumps a matrix to a string (useful for debug).
 */
function dumpMatrix(matrix) {
	var s = "[ " + matrix.a + ", " + matrix.c + ", " + matrix.e + "\n  " + matrix.b + ", " + matrix.d + ", " + matrix.f + "\n  0, 0, 1 ]";

	return s;
}

/**
 * Sets attributes of an element.
 */
function setAttributes(element, attributes){
	for (var i in attributes)
		element.setAttributeNS(null, i, attributes[i]);
}

/**
 * Handle mouse wheel event.
 */

function handleMouseWheel(evt) {
	if(!enableZoom)
		return;

	if(evt.preventDefault)
		evt.preventDefault();

	evt.returnValue = false;

	var svgDoc = evt.target.ownerDocument;

	var delta;

	if(evt.wheelDelta)
		delta = evt.wheelDelta / 3600; // Chrome/Safari
	else
		delta = evt.detail / -90; // Mozilla
	delta = delta*6;
	// alert(delta);
	var z;
	if(delta>0){
		// z = 1 + delta; // Zoom factor: 0.9/1.1
		if (_cur_z < 3) {
			z = 2;
			_cur_z = _cur_z+1;
      _pre_level= _level;
      _level = _level/2;
		}else {
			z = 1;
		}
		tmap.zoomIn();
	}else{
		// z = 1/(1-delta);
		if (_cur_z >-14) {
			z = 0.5;
			_cur_z = _cur_z-1;
      _pre_level= _level;
      _level = _level*2;
		}else {
			z = 1;
		}
		tmap.zoomOut();
	}
	var scaleBefore = scale;
	scale=scale*z;

	var p = getEventPoint(evt);
	offset_x = offset_x+p.x/scaleBefore-p.x/scale;
	offset_y = offset_y+p.y/scaleBefore-p.y/scale;


	reloadWindow();

	var g = getRoot(svgDoc);

	var p = getEventPoint(evt);
  p.x = 500;
  p.y = 275;
	p = p.matrixTransform(g.getCTM().inverse());

	// Compute new scale matrix in current mouse position
	//var k = root.createSVGMatrix().translate(p.x, p.y).scale(z).translate(-p.x, -p.y);
	// var k = root.createSVGMatrix().translate(500, 275).scale(z).translate(-500, -275);
  var k = root.createSVGMatrix().translate(p.x, p.y).scale(z).translate(-p.x, -p.y);
        setCTM(g, g.getCTM().multiply(k));

	if(typeof(stateTf) == "undefined")
		stateTf = g.getCTM().inverse();

	stateTf = stateTf.multiply(k.inverse());
}

/**
 * Handle mouse move event.
 */
 var _handler_x = 0;
 var _handler_y = 0;
 var _isMoved = false;
//

 var _pre__deltaX = 0;
 var _pre__deltaY = 0;

 var _deltaX = 0;
 var _deltaY = 0;

function handleMouseMove(evt) {
	if(evt.preventDefault)
		evt.preventDefault();

	evt.returnValue = false;

	var svgDoc = evt.target.ownerDocument;

	var g = getRoot(svgDoc);

	if(state == 'pan' && enablePan) {
		// Pan mode
		var p = getEventPoint(evt).matrixTransform(stateTf);
		_deltaX = p.x - _handler_x;
		_deltaY = p.y - _handler_y;
		_handler_x = p.x;
		_handler_y = p.y;
		offset_x = offset_x - _deltaX;
		offset_y = offset_y - _deltaY;
		_isMoved = true;
		//console.log("_deltaX:"+_deltaX+" _deltaY:"+_deltaY);
		var result = transCoordinateToLonLat(x_base_zero - offset_x+500, y_base_zero + offset_y +275);
		// tmap.panBy(new TSize(-_deltaX, -_deltaY));
		//tmap.panTo(new TLngLat(result.lon, -result.lat));

		_pre__deltaX = p.x - stateOrigin.x;
		_pre__deltaY = p.y - stateOrigin.y;
		// setCTM(g, stateTf.inverse().translate(p.x - stateOrigin.x, p.y - stateOrigin.y));
		// setCTM(g, stateTf.translate(-_deltaX, -_deltaY));

		console.log("_deltaX:"+_deltaX+" _deltaY:"+_deltaY+"    "+"px:"+(p.x - stateOrigin.x)+" py:"+(p.y - stateOrigin.y));
	} else if(state == 'drag' && enableDrag) {
		// Drag mode
		var p = getEventPoint(evt).matrixTransform(g.getCTM().inverse());
		var _deltaX = p.x - stateOrigin.x;
		var _deltaY = p.y - stateOrigin.y;
		console.log("_deltaX:"+_deltaX+" _deltaY:"+_deltaY);
		//setCTM(stateTarget, root.createSVGMatrix().translate(p.x - stateOrigin.x, p.y - stateOrigin.y).multiply(g.getCTM().inverse()).multiply(stateTarget.getCTM()));

		stateOrigin = p;
	}

  // tmap.panBy(new TSize(0,-10));
  //
  // var k = root.createSVGMatrix().translate(0, 10);
  //
  // setCTM(g, g.getCTM().multiply(k));
  //tMapMove();
  //svgMove(g);
}

var done = false;

function tMapMove(x, y){
  tmap.panBy(new TSize(x,y));
  //done = true;
}

function svgMove(g, x, y){
  //if (done) {


  var k = root.createSVGMatrix().translate(-x, -y);

  setCTM(g, g.getCTM().multiply(k));
  stateTf = stateTf.multiply(k.inverse());
  // done = false;
  // }
}

/**
 * Handle click event.
 */

var _pre_dx = 0;
var _pre_dy = 0;
var _origin_dx = 0;
var _origin_dy = 0;

function handleMouseDown(evt) {

  // MySetCTM(g, 0, -10);

	if(evt.preventDefault)
		evt.preventDefault();

	evt.returnValue = false;

	var svgDoc = evt.target.ownerDocument;

  var p = getEventPoint(evt);

  _pre_dx = p.x;
  _pre_dy = p.y;

	var g = getRoot(svgDoc);

	if(
		evt.target.tagName == "svg"
		|| !enableDrag // Pan anyway when drag is disabled and the user clicked on an element
	) {
		// Pan mode
		state = 'pan';

		stateTf = g.getCTM().inverse();

		stateOrigin = getEventPoint(evt).matrixTransform(stateTf);
		//console.log("Point:"+getEventPoint(evt).x+" "+getEventPoint(evt).y);
		_handler_x = stateOrigin.x;
		_handler_y = stateOrigin.y;
	} else {
		// Drag mode
		state = 'drag';

		stateTarget = evt.target;

		stateTf = g.getCTM().inverse();

		stateOrigin = getEventPoint(evt).matrixTransform(stateTf);
	}
}

/**
 * Handle mouse button release event.
 */
function handleMouseUp(evt) {
	if(evt.preventDefault)
		evt.preventDefault();

	evt.returnValue = false;

	var svgDoc = evt.target.ownerDocument;
  var g = getRoot(svgDoc);

  var p = getEventPoint(evt);

  tMapMove(_pre_dx - p.x, _pre_dy - p.y);
  svgMove(g, (_pre_dx - p.x)*_level, (_pre_dy - p.y)*_level);

  _origin_dx = _origin_dx + (_pre_dx - p.x);
  _origin_dy = _origin_dy + (_pre_dy - p.y);

  // tMapMove(-10, 0);
  // svgMove(g, -10*_level, 0*_level);
  //
  // _origin_dx = _origin_dx - 10;
  // _origin_dy = _origin_dy + 0;

  console.log("dx:"+_origin_dx+"   ,dy:"+_origin_dy +"   ,_level:"+_level);

	if(state == 'pan' || state == 'drag') {
		// Quit pan mode
		state = '';
	}
	_handler_x = 0;
	_handler_y = 0;
	if(_isMoved){
		reloadWindow();
		_isMoved = false;
	}
	_pre__deltaX = 0;
	_pre__deltaY = 0;


}
