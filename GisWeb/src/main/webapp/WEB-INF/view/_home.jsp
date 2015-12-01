<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- <!-- Left Category Tree & Layer Tree
<div id="gs-leftbar" class="col-sm-1" style="overflow:auto;">
	<div class="gs-leftNavTab">
		Nav tabs
		<ul class="nav nav-tabs">
			<li class="active"><a href="#category" data-toggle="tab">地图</a></li>
			 <li><a href="#layer" data-toggle="tab">图层</a></li>
		</ul>
		Tab panes
		<div class="tab-content">
			<div class="tab-pane fade in active" id="category"
				ng-controller="MapCategoryCtrl">

				<input type="text" placeholder="serach" style="width: 100%" ng-model="mapNameSearch" />
				 <div class="wmenu"> 
				<dl> 
				<dt class="user"><a href="#"><i class="fa fa-globe"></i>地图管理</a></dt> 
				<dd><a href="#">一般地图</a></dd> 
				<dd><a href="#">专用地图</a></dd> 
				</dl> 
			</div>
				 <ul id="accordion0" class="accordion0">
					<li class="Amap" ng-repeat='Amap in aMapList'>
						<div class="link0">
							<i class="fa fa-bars"></i>{{Amap.amapname}}
						</div>
						<ul id="accordion" class="accordion" style="display: none">
							<li ng-repeat='map in Amap.amapList  | filter:{name:mapNameSearch}'>
								<div class="link" ng-click="clickmap(map.id,map.tslist[0].version)">
									<i class="fa fa-map-marker"></i>{{map.name}}<i
										class="fa fa-chevron-down"></i>
								</div>
								<ul class="submenu">
									<li ng-repeat='time in map.tslist'><label
										class="label_time" ng-click="clickmap(time.mapId,time.version)">{{time.date}}</label></li>

								</ul>
							</li>
						</ul>
					</li>
				</ul>
				 
				 <ul id="accordion0" class="accordion0">
					<li class="Amap" ng-repeat='Amap in aMapList'>
						<div class="link0">
							<i class="glyphicon glyphicon-th-list"></i>{{Amap.amapname}}
						</div>
						<ul id="accordion" class="accordion" style="display: none">
							<li ng-repeat='map in Amap.amapList  | filter:{name:mapNameSearch}'>
								<div id="{{map.id}}" class="link " ng-click="clickmap(map.id,map.tslist[0].version)">
									<i class="glyphicon glyphicon-map-marker"></i>{{map.name}}<span class="glyphicon glyphicon-remove-circle" style="float:right;margin-right:10px;opacity:0.2;"  ng-click="deleteMap(map.id)"></span>
								</div>
								<ul id="accordion2" class="submenu" >
								<li ng-repeat='time in map.tslist'>
									<label class="label_time" ng-click="clickmap(time.mapId,time.version)">{{time.date}}<span class="glyphicon glyphicon-remove" style="float:right;margin-right:10px;opacity:0.4;" ng-click="deleteTimestamp(time.mapId,time.version)"></span></label></li>
								</ul>
							</li>
						</ul>
					</li>
				</ul> 
				
				<ul id="accordion0">
					<li ng-repeat='Amap in aMapList'>
						<div >
							{{Amap.amapname}}
						</div>
						<ul id="accordion" >
							<li ng-repeat='map in Amap.amapList | filter:{name:mapNameSearch}'>
								<div ng-click="clickmap(map.id,map.tslist[0].version)">
									{{map.name}}
								</div>
								<ul >
									<li ng-repeat='time in map.tslist'><label ng-click="clickmap(time.mapId,time.version)">{{time.date}}</label></li>

								</ul>
							</li>
						</ul>
					</li>
				</ul> 
			

				<p>{{category}}</p>
				<button type="button" id="button1" class="btn btn-success btn-sm"
					data-toggle="modal" data-target="#modalUpload"
					style="margin-buttom:2px; float:right ;">上传</button>
			</div>
			<div class="tab-pane fade" id="layer" ng-controller="MapLayerCtrl">{{layer}}</div>
		</div>
	</div>
</div> -->

<!-- SVG Map Content -->
<div id="gs-center" class="col-sm-10" ng-controller="MapContentCtrl">
	<div id="gs-mapTitle">
	
		<label>Map Name</label>
		<!-- <div id="gs-mapModeSwitch" class="pull-right">
			<button class="btn btn-xs btn-primary">二维</button>
			<button class="btn btn-xs btn-default">三维</button>
		</div>
		 -->
		</div>
			
	<div id="gs-mapContent" style="border: 1px solid #ddd; height: 100%;">
		<!-- <object type='image/svg+xml' data="{{svg}}" width = '100%' height='100%' ></object> -->
		<!-- <iframe src="{{svg}}" width = '100%' height='100%' style="border:0px #000000 solid"></iframe> -->
		<!-- <script src="resources/scripts/app/svg/SVGPan.js" ></script> -->
		
		<svg id = "SVGMapContent" xmlns="http://www.w3.org/2000/svg"
			xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1"
			onload="loadMap(evt)" width = '100%' height='100%'
			>
  				<!-- <script xlink:href="/GisWeb_GradleEclipse/resources/scripts/app/svg/SVGPan.js" /> -->
  				<g id='viewport'>
  				<g class="baseLayer"></g></g>
   				<!-- <image xlink:href="{{svg}}" x="0" y="0" height="100%" width="100%" />
   				<g ng-include= "'./resource/svg/1/01'"></g> 		
   				{{svg}}
   				</g> -->
		</svg> 

	</div>
<!-- 	<div id = "allmap"></div> -->
</div> 

<!-- Right Menu 
<div id="gs-rightbar" class="col-sm-3">
	<div id="gs-panelAttribute" class="panel panel-default">
		<div class="panel-heading">地物属性</div>
		<div class="panel-body"></div>
	</div>
	<div id="gs-panelSearch" class="panel panel-default">
		<div class="panel-heading">地图搜素</div>
		<div class="panel-body"></div>
	</div>
	<div id="gs-panelNavigation" class="panel panel-default">
		<div class="panel-heading">地图导航</div>
		<div class="panel-body"></div>
	</div>
</div>
-->
<div ng-controller="MapOperationCtrl">
<div class="input-group" id="searchBox" >
			<input id="MapEleSearch" type="text" class="form-control" placeholder="Search for...">
			<span class="input-group-btn">
				<button class="btn btn-default" type="button"  ng-click="mapSearch()">
					<span class="glyphicon glyphicon-search"></span>
				</button>
			</span>
		</div>
		
<div id="fuzzyAlert" >
	<!-- <div>查找的是</div> -->
	<div ng-repeat='searchWord in searchList'>
		<div ng-click="mapSearchDirect(searchWord)">{{searchWord}}</div>
	</div>
	
</div>
</div>		
<div class="col-sm-2" id="gs-rightbar">
	<div class="col-sm-12">
		
		<!-- /input-group -->


		<div class="col-sm-12" id="layer" ng-controller="MapEditCtrl">
		<!-- <div>{{currentlayer}}</div> -->
		 	<div ng-repeat='layer in layerList' class="checkbox" >
				<label><input id="{{layer.layerid}}" name="box" type="checkbox" ng-model="layer.ischecked" ng-click="changelayer(layer.ischecked,layer.layerid)" ng-checked="{{layer.ischecked}}"><span>{{layer.layername}}</span>
			</label>
			</div>
			<!-- <input type="checkbox" checked="checked"><span>hh</span> -->
			<!-- <button class="btn btn-success" id="showLayer" ng-click="changelayer()">显示</button> -->
			<!-- <div id="riverOpt" class="checkbox">
				<label><input type="checkbox" value="" checked><span>河流</span>
			</label>
			</div>
			<div id="roadOpt" class="checkbox">
				<label> <input type="checkbox" value="" checked> <span>道路</span>
				</label>
			</div>
			<div  id="buildingOpt" class="checkbox">
				<label> <input type="checkbox" value="" checked> <span>建筑</span>
				</label>
			</div>
			<div  id="plantingOpt" class="checkbox">
				<label> <input type="checkbox" value="" checked> <span>绿化带</span>
				</label>
			</div> -->
		</div>
	</div>
	<!-- /.col-sm-3 -->
</div>

<!-- Modal Parts -->
<!-- 上传模态框 -->

<div class="modal fade" id="modalUpload" tabindex="-1" role="dialog"
	aria-labelledby="modalUploadLabl" aria-hidden="true">
	<div class="modal-dialog" id="fordate">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="modalUploadLabl"
					style="text-align: center">上传地图</h4>
			</div>
			<div class="modal-body">
				<!-- 主题部分-->
				<form class="form-horizontal" role="form">

					<div class="form-group" style="text-align: center;">
						<label class="radio-inline"> <input type="radio"
							name="inlineRadioOptions" id="inlineRadio1" value="option1">新地图
						</label> <label class="radio-inline"> <input type="radio"
							name="inlineRadioOptions" id="inlineRadio2" value="option2"
							checked />旧地图
						</label>
					</div>
					<div class="form-group"
						style="margin-top: 15px; height: 20px; text-align: center; font-family: '宋体'" >
						<label>地图名称</label>
						 <select name="type"
							class="selectpicker show-tick" data-size="10" id="sel"
							style="margin-left: 5px; width: 160px; height: 25px;" >
							<option ng-repeat='map in mapList' value = {{map.id}}>{{map.name}}</option>
							<!-- <option>亳州市</option>
							<option>上海市</option>
							<option>北京市</option>
							<option>广州市</option>
							<option>香港</option> -->
						</select> <input type="text" name="mapname" id="inp"
							style="margin-left: 5px; width: 160px; height: 25px; display: none" />
					</div>
					<!--  
					<div class="form-group">
						<label for="inputUploadFile" class="col-sm-2 control-label">地图文件</label>
						<div class="col-sm-10">
							<input type="file" class="form-control" id="inputUploadFile" style="display:none"/>
							<button type="button" class="btn btn-success" style="margin-left:5px;width:160px;height:30px;" onclick="document.getElementById('inputUploadFile').click()">选择文件</button>
						</div>
					</div>
					-->
					<div class="form-group"
						style="margin-top: 25px; height: 20px; text-align: center; font-family: '宋体'; margin-bottom: 20px;">
						<label for="inputUploadFile" style="margin-right: 5px;">地图文件</label>
						<button type="button" class="btn btn-success"
							style="margin-left: 5px; width: 160px; height: 30px;"
							onclick="document.getElementById('inputUploadFile').click()">选择文件</button>
						<input type="file" class="form-control" id="inputUploadFile" name="inputUploadFile"
							style="display: none" />
					</div>
					
					<div class="form-group" style="margin-top: 15px; height: 20px; text-align: center; font-family: '宋体'">
            				<label>上传日期</label>        				
            				<input type="text" class="span2" value="" id="dp1" style="margin-left: 5px; width: 160px; height: 25px;">   				
          			</div>
					<div class="form-group" id="mpt"
						style="margin-top: 15px; height: 20px; text-align: center; font-family: '宋体';display: none">
						<label>地图类型</label>
						 <select name="type"  id="mapTypeSel"
							class="selectpicker show-tick" data-size="10" id="sel"
							style="margin-left: 5px; width: 160px; height: 25px;">
							<option value = 0>通用地图</option>
							<option value = 1>专用地图</option>
						</select> 
					</div>

					<div class="form-group">
						<div class="col-sm-offset-9 col-sm-10">
							<!-- <button type="submit" class="btn btn-success"
								data-dismiss="modal" onclick="SVG_upload();">上传</button> -->
							<button type="submit" class="btn btn-success"
								data-dismiss="modal" ng-click="uploadmap();">上传</button>
							<button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
						</div>
					</div>

					



				</form>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal dialog-->
</div>
<!-- 上传模态框 -->