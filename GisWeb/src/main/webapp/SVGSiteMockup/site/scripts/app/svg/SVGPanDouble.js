
/**
 * SVGPanDouble.js
 * Enable synchronized operation between two svg image areas
 * take SVGPan.js as reference
 */

"use strict";

/// CONFIGURATION
/// ====>

var enablePanD = 1; // 1 or 0: enable or disable panning (default enabled)
var enableZoomD = 1; // 1 or 0: enable or disable zooming (default enabled)
var enableDragD = 0; // 1 or 0: enable or disable dragging (default disabled)

/// <====
/// END OF CONFIGURATION

var state = 'none', svgRoot, stateTarget, stateOrigin, stateTf;

var rootD = document.documentElement;
var svgRootL;
var svgRootR;

var rootDL;
var rootDR;

/**
 * setup handlers to two windows
 * @param root	:a html div element which contain two svg windows;
 */
function initWithRootD(root){
	//get rootL and rootR

	getRootD(root);
	setupHandlersD(svgRootL);
	setupHandlersD(svgRootR);
}

function initR(svgroot){

	svgRootR = svgroot;
	rootDR = svgroot.parentNode;
	setupHandlersD(rootDR);
}
function initL(svgroot){
	svgRootL = svgroot;
	rootDL = svgroot.parentNode;
	setupHandlersD(rootDL);
}

/**
 * Register handlers
 */
function setupHandlersD(root){
	setAttributesD(root, {
		"onmouseup" : "handleMouseUpD(evt)",
		"onmousedown" : "handleMouseDownD(evt)",
		"onmousemove" : "handleMouseMoveD(evt)"
		//"onmouseout" : "handleMouseUp(evt)", // Decomment this to stop the pan functionality when dragging out of the SVG element
	});
	if(navigator.userAgent.toLowerCase().indexOf('webkit') >= 0){
		root.addEventListener('mousewheel', handleMouseWheelD, false); // Chrome/Safari
	}
	else{
		root.addEventListener('mousewheel', handleMouseWheelD, false);
		root.addEventListener('DOMMouseScroll', handleMouseWheelD, false); // Others
	}
}

/**
 * Retrieves the root element for SVG manipulation. The element is then cached into the svgRoot global variable.
 */
function getRootD(root) {
	var svgRoot = [null,null];
	if(typeof(svgRootL) == "undefined") {
		var gL = null;
		gL = root.getElementById("viewportL");

		if(gL == null)
			alert('Unable to obtain SVG rootL element');

		setCTMD(gL, gL.getCTM());

		gL.removeAttribute("viewBox");

		svgRootL = gL;
	}

	if(typeof(svgRootR) == "undefined") {
		var gR = null;
		gR = root.getElementById("viewportR");

		if(gR == null)
			alert('Unable to obtain SVG rootL element');

		setCTMD(gR, gR.getCTM());

		gR.removeAttribute("viewBox");

		svgRootL = gR;
	}

	svgRoot[0] = svgRootL;
	svgRoot[1] = svgRootR;

	return svgRoot;
}

/**
 * Instance an SVGPoint object with given event coordinates.
 */
function getEventPointD(evt) {
	var p = rootDL.createSVGPoint();

	p.x = evt.clientX;
	p.y = evt.clientY;

	return p;
}

/**
 * Sets the current transform matrix of an element.
 */
function setCTMD(element, matrix) {
	var s = "matrix(" + matrix.a + "," + matrix.b + "," + matrix.c + "," + matrix.d + "," + matrix.e + "," + matrix.f + ")";

	element.setAttribute("transform", s);
}

/**
 * Dumps a matrix to a string (useful for debug).
 */
function dumpMatrixD(matrix) {
	var s = "[ " + matrix.a + ", " + matrix.c + ", " + matrix.e + "\n  " + matrix.b + ", " + matrix.d + ", " + matrix.f + "\n  0, 0, 1 ]";

	return s;
}

/**
 * Sets attributes of an element.
 */
function setAttributesD(element, attributes){
	for (var i in attributes)
		element.setAttributeNS(null, i, attributes[i]);
}

/**
 * Handle mouse wheel event.
 */
function handleMouseWheelD(evt) {
	if(!enableZoomD)

		return;

	if(evt.preventDefault)
		evt.preventDefault();

	evt.returnValue = false;

	var svgDoc = evt.target.ownerDocument.parentNode;

	var delta;

	if(evt.wheelDelta)
		delta = evt.wheelDelta / 3600; // Chrome/Safari
	else
		delta = evt.detail / -90; // Mozilla
	delta = delta*6;
	//alert(delta);
	var z;
	if(delta>0){
		z = 1 + delta; // Zoom factor: 0.9/1.1
	}else{
		z = 1/(1-delta);
	}
	var scaleBefore = scaleD;
	scaleD=scaleD*z;

	var p = getEventPointD(evt);

	offset_xD = offset_xD+p.x/scaleBefore-p.x/scaleD;
	offset_yD = offset_yD+p.y/scaleBefore-p.y/scaleD;


	reloadWindowD();

	var g = getRootD(svgDoc);
	var gL = g[0];
	var gR = g[1];

	var p = getEventPointD(evt);

	p = p.matrixTransform(gL.getCTM().inverse());

	// Compute new scale matrix in current mouse position
	var k = rootDL.createSVGMatrix().translate(p.x, p.y).scale(z).translate(-p.x, -p.y);

    setCTMD(gL, gL.getCTM().multiply(k));
    setCTMD(gR, gR.getCTM().multiply(k));

	if(typeof(stateTf) == "undefined")
		stateTf = gL.getCTM().inverse();

	stateTf = stateTf.multiply(k.inverse());
}

/**
 * Handle mouse move event.
 */
 var handler_xD = 0;
 var handler_yD = 0;
 var isMovedD = false;
//

function handleMouseMoveD(evt) {
	if(evt.preventDefault)
		evt.preventDefault();

	evt.returnValue = false;

	var svgDoc = evt.target.ownerDocument.parentNode;

	var g = getRootD(svgDoc);
	var gL = g[0];
	var gR = g[1];


	if(state == 'pan' && enablePan) {
		// Pan mode
		var p = getEventPointD(evt).matrixTransform(stateTf);
		var deltaX = p.x - handler_xD;
		var deltaY = p.y - handler_yD;
		handler_xD = p.x;
		handler_yD = p.y;
		offset_xD = offset_xD - deltaX;
		offset_yD = offset_yD - deltaY;
		isMovedD = true;
		//console.log("deltaX:"+deltaX+" deltaY:"+deltaY);
		setCTMD(gL, stateTf.inverse().translate(p.x - stateOrigin.x, p.y - stateOrigin.y));
		setCTMD(gR, stateTf.inverse().translate(p.x - stateOrigin.x, p.y - stateOrigin.y));
	} else if(state == 'drag' && enableDrag) {
		// Drag mode
		var p = getEventPointD(evt).matrixTransform(g.getCTM().inverse());
		var deltaX = p.x - stateOrigin.x;
		var deltaY = p.y - stateOrigin.y;
		console.log("deltaX:"+deltaX+" deltaY:"+deltaY);
		setCTMD(stateTarget, root.createSVGMatrix().translate(p.x - stateOrigin.x, p.y - stateOrigin.y).multiply(g.getCTM().inverse()).multiply(stateTarget.getCTM()));

		stateOrigin = p;
	}
}

/**
 * Handle click event.
 */
function handleMouseDownD(evt) {
	if(evt.preventDefault)
		evt.preventDefault();

	evt.returnValue = false;

	var svgDoc = evt.target.ownerDocument;

	var g = getRootD(svgDoc);
	var gL = g[0];
	var gR = g[1];


	if(
		evt.target.tagName == "svg"
		|| !enableDragD // Pan anyway when drag is disabled and the user clicked on an element
	) {
		// Pan mode
		state = 'pan';

		stateTf = gL.getCTM().inverse();

		stateOrigin = getEventPointD(evt).matrixTransform(stateTf);
		//console.log("Point:"+getEventPoint(evt).x+" "+getEventPoint(evt).y);
		handler_xD = stateOrigin.x;
		handler_yD = stateOrigin.y;
	} else {
		// Drag mode
		state = 'drag';

		stateTarget = evt.target;

		stateTf = gL.getCTM().inverse();

		stateOrigin = getEventPointD(evt).matrixTransform(stateTf);
	}
}

/**
 * Handle mouse button release event.
 */
function handleMouseUpD(evt) {
	if(evt.preventDefault)
		evt.preventDefault();

	evt.returnValue = false;

	//var svgDoc = evt.target.ownerDocument;

	if(state == 'pan' || state == 'drag') {
		// Quit pan mode
		state = '';
	}
	handler_xD = 0;
	handler_yD = 0;
	if(isMovedD){
		reloadWindowD();
		isMovedD = false;
	}
}
