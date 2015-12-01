<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="webgis">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WebGIS</title>
<!-- Bootstrap -->
<!-- <link href="resources/css/font-awesome.min.css" rel="stylesheet"> -->
<link href="resources/css/bootstrap.css" rel="stylesheet">
<link href="resources/css/_layout.css" rel="stylesheet">
<link href="resources/css/_home.css" rel="stylesheet">
<link href="resources/css/_mapCompare.css" rel="stylesheet">
<link href="resources/css/_mapEdit.css" rel="stylesheet">
<link href="resources/css/style.css" rel="stylesheet">
<!-- <link href="resources/css/red.css" rel="stylesheet"> -->

<link href="resources/css/prettify.css" rel="stylesheet">
<link href="resources/css/datepicker.css" rel="stylesheet">
	<style type="text/css">
	body, html,#allmap {z-index:0;top:-100%;width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微

软雅黑";}
	#gs-mapContent{z-index:0;width: 100%;height: 100%;overflow: hidden;margin:0;}
	</style>
<!-- 
<link
	href="http://aymkdn.github.io/Datepicker-for-Bootstrap/js/google-code-prettify/prettify.css"
	rel="stylesheet">
<link
	href="http://aymkdn.github.io/Datepicker-for-Bootstrap/css/bootstrap.css"
	rel="stylesheet">

<link
	href="http://github-proxy.kodono.info/?q=https://raw.github.com/Aymkdn/Datepicker-for-Bootstrap/master/datepicker.css"
	rel="stylesheet">

 -->

	<!-- Baidu Map Library -->
	<!-- <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=fPQTQyV2eDeHxF3hpAwlyCN4"></script> -->
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=3RuFV3DzwmKG0oYMUlBcspRy"></script>
</head>
<body>
	<div id="gs-wrapper">
		<!-- Top Navigation -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<%@ include file="_nav.jsp"%>
		</nav>

		<!-- Container Body -->
		<div id="gs-content" class="container tab-content"
			ng-controller="HomeCtrl">
			<div id="home" class="row tab-pane active"><%@ include
					file="_home.jsp"%></div>
			<div id="mapCompare" class="row tab-pane"><%@ include
					file="_mapCompare.jsp"%></div>
<%--  			<div id="mapEdit" class="row tab-pane"><%@ include
					file="_mapEdit.jsp"%></div>  --%>
		</div>
	</div>
	<!-- Container Body:gs-content -->

	<div id="info1">
		<p>低温与制冷工程研究所</p>
		<hr />
		<p>创建于1958年，是我国高等院校中最早创办的制冷及低温工程学科之一，1960年开始培养本科生，1978年开始培养研究生，1998年被国务院学位委员会批准为博士学位授权点及博士后流动站，2003年被评为浙江省重点学科，2007年被评为全国重点学科，是全国第三个被认定的重点学科。</p>
	</div>
	<div id="info2">
		<p>核工程实验室</p>
		<hr />
		<p>拥有甲级工程设计等一系列资质，主营业务包括工程设计、设备设计、工程咨询、工程总承包、工程项目管理、设备采购、施工管理、调试指导、技术开发和技术服务等。1998年10月，获得了质量管理体系认证证书。2003年3月，获得了GB/T19001-2000
			idt ISO9001-2000质量管理体系认证证书。2008年10月，获得HSE管理体系认证证书。</p>
	</div>
	<div id="info3">
		<p>停车场</p>
		<hr />
		<p>收费标准：￥15/小时</p>
	</div>

	<footer>
		<p>&copy; 2015 - 同济大学-软件学院</p>
	</footer>

	<!-- Put Scripts at the bottom of body -->
	<!-- JQuery -->
	<script src="resources/scripts/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script src="resources/scripts/bootstrap.min.js"></script>
	<!-- AngularJS -->
	<script src="resources/scripts/angular.min.js"></script>

	<script src="resources/scripts/prettify.js"></script>
	<script src="resources/scripts/saved_resource"></script>


	<!-- <script src="./Datepicker for Bootstrap_files/prettify.js"></script>
    <script src="./Datepicker for Bootstrap_files/jquery-1.7.1.min.js"></script>
    <script src="./Datepicker for Bootstrap_files/saved_resource"></script> -->





	<script src="resources/scripts/app/main.js"></script>
	<script src="resources/scripts/app/controllers/HomeCtrl.js"></script>
	<script src="resources/scripts/app/controllers/MapCategoryCtrl.js"></script>
	<script src="resources/scripts/app/controllers/MapLayerCtrl.js"></script>
	<script src="resources/scripts/app/controllers/MapContentCtrl.js"></script>
	<script src="resources/scripts/app/controllers/MapCompareCtrl.js"></script>
	<script src="resources/scripts/app/controllers/MapOperationCtrl.js"></script>
	<script src="resources/scripts/app/controllers/MapEditCtrl.js"></script>
	<script src="resources/scripts/index.js"></script>
	<script src="resources/scripts/infoWin.js"></script>
	<!-- 	<script src="resources/scripts/icheck.js"></script> -->
	<script src="resources/scripts/ajaxfileupload.js"></script>
	<!-- SVG Libraries -->
	<script src="resources/scripts/app/svg/SVGPan.js"></script>
	<script src="resources/scripts/app/svg/SVGPanDouble.js"></script>
	<script src="resources/scripts/app/svg/MapLoader.js"></script>
	<script src="resources/scripts/app/svg/MapLoaderDouble.js"></script>
	<script src="resources/scripts/app/svg/InfoWindow.js"></script>

	<script>
		$(function() {
			$('#gs-navbar a:first').tab('show');//初始化显示home tab 

			$('#gs-navbar a').click(function(e) {
				e.preventDefault();//阻止a链接的跳转行为 
				$(this).tab('show');//显示当前选中的链接及关联的content 
			});
		});
	</script>
	<!-- 	<script>	
		$(document).ready(function() {
			$('input').iCheck({
				checkboxClass : 'icheckbox_flat-red',
				radioClass : 'iradio_flat-red'
			});
		});
	</script> -->
	<!--  
	<script type="text/javascript"> 
	$('dd').hide();
	$('.wmenu dl dt').click(function(){ 
	$(this).toggleClass('icotop'); 
	$(this).siblings('dd').toggle(); 
}); 
</script> -->
</body>
</html>