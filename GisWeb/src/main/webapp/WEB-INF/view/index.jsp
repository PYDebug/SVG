<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<!-- <meta http-equiv="pargma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
	<meta http-equiv="expires" content="Sunday 26 October 2008 01:00 GMT"> -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
		<base href="/">
    <title>时态GIS系统</title>
    <meta name="description" content="overview &amp; stats"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="./SVGSiteMockup/site/lib/ng-dialog/css/ngDialog.css"/>
    <link rel="stylesheet" href="./SVGSiteMockup/site/lib/ng-dialog/css/ngDialog.min.css"/>
    <link rel="stylesheet" href="./SVGSiteMockup/site/lib/ng-dialog/css/ngDialog-theme-default.min.css"/>
    <link rel="stylesheet" href="./SVGSiteMockup/site/lib/ng-dialog/css/ngDialog-theme-plain.min.css"/>
    <link rel="stylesheet" href="./SVGSiteMockup/assets/css/bootstrap.css"/>
    <link rel="stylesheet" href="./SVGSiteMockup/assets/css/font-awesome.css"/>
    <!-- page specific plugin styles -->
    <!-- text fonts -->
    <link rel="stylesheet" href="./SVGSiteMockup/assets/css/ace-fonts.css"/>
		<link rel="stylesheet" href="./SVGSiteMockup/assets/css/main.css"/>
    <!-- ace styles -->
    <link rel="stylesheet" href="./SVGSiteMockup/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style"/>

    <link rel="stylesheet" href="./SVGSiteMockup/site/scripts/angular-datepicker/dist/index.min.css"/>
    <link rel="stylesheet" href="./SVGSiteMockup/site/scripts/bootstrap-datepicker/css/datepicker.css"/>
    <link rel="stylesheet" href="./SVGSiteMockup/site/scripts/bootstrap-datepicker/css/datepicker3.css"/>
    <link rel="stylesheet" href="./SVGSiteMockup/site/scripts/select2/select2.css"/>

		<link href="./SVGSiteMockup/assets/css/style.css" rel="stylesheet" type="text/css" media="all"/>
		<%-- <link href='http://fonts.googleapis.com/css?family=Armata' rel='stylesheet' type='text/css'> --%>

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="../assets/css/ace-part2.css" class="ace-main-stylesheet"/>
    <![endif]-->
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="../assets/css/ace-ie.css"/>
    <![endif]-->
    <!-- inline styles related to this page -->
    <!-- ace settings handler -->
    <script src="./SVGSiteMockup/assets/js/ace-extra.js"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=9fmSpnUVpgjdeBMrDfMPP1lV"></script>
		<script src=" http://api.tianditu.com/js/maps.js" type="text/javascript"></script>
    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
    <!--[if lte IE 8]>
    <script src="../assets/js/html5shiv.js"></script>
    <script src="../assets/js/respond.js"></script>
    <![endif]-->
</head>

<body class="no-skin" ng-app="app">
	<%-- <pre>
	      <!-- Here's some values to keep an eye on in the sample in order to understand $state and $stateParams -->
	      $state = {{$state.current.name}}
	      $stateParams = {{$stateParams}}
	      $state full url = {{ $state.$current.url.source }}
	      <!-- $state.$current is not a public api, we are using it to
	           display the full url for learning purposes-->
	    </pre> --%>
	<div style="min-height: 800px;">
		<div ui-view></div>
	</div>
<!-- /.main-container -->
<!-- basic scripts -->
<!--[if !IE]> -->
<script type="text/javascript">
    window.jQuery || document.write("<script src='./SVGSiteMockup/assets/js/jquery.js'>" + "<" + "/script>");
</script>
<!-- <![endif]-->
<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='../assets/js/jquery1x.js'>" + "<" + "/script>");
</script>
<![endif]-->

<script src="./SVGSiteMockup/assets/js/bootstrap.js"></script>
<!-- page specific plugin scripts -->
<!--[if lte IE 8]>
<script src="../assets/js/excanvas.js"></script>
<![endif]-->
<script src="./SVGSiteMockup/assets/js/jquery-ui.custom.js"></script>
<script src="./SVGSiteMockup/assets/js/jquery.ui.touch-punch.js"></script>

<!-- ace scripts -->
<script src="./SVGSiteMockup/assets/js/ace/elements.scroller.js"></script>

<script src="./SVGSiteMockup/assets/js/ace/ace.js"></script>
<script src="./SVGSiteMockup/assets/js/ace/ace.ajax-content.js"></script>
<script src="./SVGSiteMockup/assets/js/ace/ace.touch-drag.js"></script>
<script src="./SVGSiteMockup/assets/js/ace/ace.sidebar.js"></script>
<script src="./SVGSiteMockup/assets/js/ace/ace.sidebar-scroll-1.js"></script>
<script src="./SVGSiteMockup/assets/js/ace/ace.submenu-hover.js"></script>


<!-- Include angular.js, angular-animate.js and angular-ui-router.js-->
<script src="./SVGSiteMockup/site/lib/angular-1.2.14/angular.js"></script>
<script src="./SVGSiteMockup/site/lib/angular-1.2.14/angular-animate.js"></script>
<script src="./SVGSiteMockup/site/lib/angular-ui-router.js"></script>

<script src="./SVGSiteMockup/site/lib/ng-dialog/js/ngDialog.js"></script>
<script src="./SVGSiteMockup/site/lib/ngstorage/ngStorage.js"></script>
<script src="./SVGSiteMockup/site/lib/angular-resource/angular-resource.js"></script>


<script src="./SVGSiteMockup/site/js/app.js"></script>
<%-- <script src="./SVGSiteMockup/site/js/router.js"></script> --%>

<script src="./SVGSiteMockup/site/module-one/module1.js"></script>
<script src="./SVGSiteMockup/site/module-two/module2.js"></script>
<script src="./SVGSiteMockup/site/module-four/module4.js"></script>
<script src="./SVGSiteMockup/site/module-three/module3.js"></script>
<script src="./SVGSiteMockup/site/module-five/module5.js"></script>
<script src="./SVGSiteMockup/site/module-seven/module7.js"></script>
<script src="./SVGSiteMockup/site/module-eight/module8.js"></script>
<script src="./SVGSiteMockup/site/service/login.js"></script>
<script src="./SVGSiteMockup/site/service/register.js"></script>
<script src="./SVGSiteMockup/site/service/portal.js"></script>
<script src="./SVGSiteMockup/site/service/q-service.js"></script>
<script src="./SVGSiteMockup/site/service/session-service.js"></script>
<script src="./SVGSiteMockup/site/factory/token-factory.js"></script>



<script src="./SVGSiteMockup/site/scripts/app/main.js"></script>
<script src="./SVGSiteMockup/site/scripts/app/controllers/HomeCtrl.js"></script>
<script src="./SVGSiteMockup/site/scripts/app/controllers/MapCategoryCtrl.js"></script>
<script src="./SVGSiteMockup/site/scripts/app/controllers/MapLayerCtrl.js"></script>
<script src="./SVGSiteMockup/site/scripts/app/controllers/MapContentCtrl.js"></script>
<script src="./SVGSiteMockup/site/scripts/app/controllers/MapCompareCtrl.js"></script>
<script src="./SVGSiteMockup/site/scripts/app/controllers/MapOperationCtrl.js"></script>
<script src="./SVGSiteMockup/site/scripts/app/controllers/MapEditCtrl.js"></script>
<script src="./SVGSiteMockup/site/scripts/index.js"></script>
<script src="./SVGSiteMockup/site/scripts/infoWin.js"></script>

<script src="./SVGSiteMockup/site/scripts/angular-bootstrap/ui-bootstrap-tpls.js"></script>
<script src="./SVGSiteMockup/site/scripts/select2/select2.js"></script>
<script src="./SVGSiteMockup/site/scripts/angular-ui-select2/src/select2.js"></script>
<script src="./SVGSiteMockup/site/scripts/angular-ui-calendar/src/calendar.js"></script>
<script src="./SVGSiteMockup/site/scripts/angular-datepicker/dist/index.min.js"></script>
<script src="./SVGSiteMockup/site/scripts/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script src="./SVGSiteMockup/site/scripts/angular-aria/angular-aria.js"></script>
<script src="./SVGSiteMockup/site/scripts/angular-ui-date/src/date.js"></script>
<script src="./SVGSiteMockup/site/scripts/portal-datepicker.js"></script>
<script src="./SVGSiteMockup/site/scripts/moment/moment.js"></script>
<script src="./SVGSiteMockup/site/scripts/angular-moment/angular-moment.js"></script>


<!-- 	<script src="resources/scripts/icheck.js"></script> -->
<script src="resources/scripts/ajaxfileupload.js"></script>
<!-- SVG Libraries -->
<script src="./SVGSiteMockup/site/scripts/app/svg/SVGPan.js"></script>
<script src="./SVGSiteMockup/site/scripts/app/svg/MapLoader.js"></script>
<script src="./SVGSiteMockup/site/scripts/app/svg/InfoWindow.js"></script>
<script src="./SVGSiteMockup/site/scripts/app/svg/SVGPanDouble.js"></script>
<script src="./SVGSiteMockup/site/scripts/app/svg/MapLoaderDouble.js"></script>


</body>

</html>
