<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="gs-compare" ng-controller="MapCompareCtrl">
	<div id="gs-compareSelection" ng-show="showSelection">
		<div id="gs-compareMapName" class="panel col-sm-3 comparePanel"
			ng-class="selectedStatus=='initial' ? 'panel-warning':'panel-success'">
			<div class="panel-heading">
				<h3 class="panel-title">选择地图</h3>
			</div>
			<div class="panel-body">
				<div class="compareSearch">
					<input type="text" class="form-control"
						placeholder="Search Map Name" ng-model="mapSearch" />
				</div>
				<div class="compareList">
					<div class="list-group">
						<a href="#" class="list-group-item"
							ng-repeat="map in mapList  | filter:{name:mapSearch}"
							ng-click="selectMap(map)"
							ng-class="selectedMap == map ? 'active':''"><i
							class="fa fa-map-marker"></i>{{map.name}}</a>
					</div>
				</div>
			</div>
		</div>


		<div id="gs-compareNextArrow" class="col-sm-1 compareArrow">
			<i class="fa fa-arrow-circle-right"></i>
		</div>


		<div id="gs-compareTimestamp1" class="panel col-sm-3 comparePanel"
			ng-class="selectedStatus=='initial' ? 'panel-default' : selectedStatus== 'mapSelected' ? 'panel-warning': 'panel-success'">
			<div class="panel-heading">
				<h3 class="panel-title">选择时间戳1</h3>
			</div>
			<div class="panel-body">
				<div class="compareSearch">
					<input type="text" class="form-control"
						placeholder="Search Timestamp 1" ng-model="timestamp1Search" />
				</div>
				<div class="compareList">
					<div class="list-group">
						<a href="#" class="list-group-item"
							ng-repeat="timestamp in maplist1  | filter:{date:timestamp1Search}"
							ng-click="selectTimestamp1(timestamp)"
							ng-class="selectedTimestamp1 == timestamp ? 'active':''"><i
							class="fa fa-clock-o"></i>{{timestamp.date}}</a>
					</div>
				</div>
			</div>
		</div>


		<div id="gs-compareCompareArrow" class="col-sm-1 compareArrow">
			<i class="fa fa-arrow-circle-right"></i>
		</div>


		<div id="gs-compareTimestamp2" class="panel col-sm-3 comparePanel"
			ng-class="selectedStatus=='initial'|| selectedStatus == 'mapSelected' ? 'panel-default' : selectedStatus=='timestamp1Selected' ? 'panel-warning':'panel-success'">
			<div class="panel-heading">
				<h3 class="panel-title">选择时间戳2</h3>
			</div>
			<div class="panel-body">
				<div class="compareSearch">
					<input type="text" class="form-control"
						placeholder="Search Timestamp 2" ng-model="timestamp2Search" />
				</div>
				<div class="compareList">
					<div class="list-group" ng-show="selectedStatus!='mapSelected'">
						<a href="#" class="list-group-item"
							ng-repeat="timestamp in maplist2 | filter: '!'+ selectedTimestamp1 | filter:{date:timestamp2Search}"
							ng-click="selectTimestamp2(timestamp)"
							ng-class="selectedTimestamp2 == timestamp ? 'active':''"><i
							class="fa fa-clock-o"></i>{{timestamp.date}}</a>
					</div>
				</div>
			</div>
		</div>


		<div id="gs-compareSubmit" class="col-sm-12">
			<button class="btn btn-success"
				ng-class="selectedStatus=='timestamp2Selected' ? '' : 'disabled'"
				ng-click="startCompare(selectedMap.id,selectedTimestamp2.version,selectedTimestamp1.version)">进行比较</button>
				
		</div>
	</div>

	<div id="gs-compareResult"  ng-show="!showSelection">
		<div id="gs-compareResultHeader" class="col-sm-12">
			<span class="pull-left">比对地图：{{selectedMap.name}}</span>
			<button class="btn btn-success pull-right" ng-click="backToCompareSelection()"><i class="fa fa-undo"></i> 返回</button>
			<!-- <button class="btn btn-primary pull-right"><i class="fa fa-arrow-down"></i> 下一处</button> -->
			<!-- <button class="btn btn-primary pull-right"><i class="fa fa-arrow-up"></i> 上一处</button> -->
		</div>
		<div id="gs-compareResultBody" class="col-sm-12">
			<div id="gs-compareResultBodyLeft" class="col-sm-6 " height = "100%">
				<div class="compareResultMapContent" >
				<svg id = "viewportL" xmlns="http://www.w3.org/2000/svg"
			xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1"
			 width = "100%"  height = "100%" onload="loadMapL(evt)">
			 <g id='viewportL'>
			 	<g class="baseLayer"></g>
				<g ng-bind-html="svg1"></g></g>
				</svg>
				</div>
				<div class="compareResultMapName">
				{{selectedTimestamp1.name}}
				</div>
				
			</div>
			<div id="gs-compareResultBodyRight" class="col-sm-6" height = "100%">
				<div class="compareResultMapContent" >
				<svg id = "viewportR" xmlns="http://www.w3.org/2000/svg"
			xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1"
			 width = "100%"  height = "100%" onload="loadMapR(evt)">
				 <g id='viewportR'>
				 <g class="baseLayer"></g>
				 <g ng-bind-html="svg2"></g></g>
				</svg></div>
				<div class="compareResultMapName">{{selectedTimestamp2.name}}</div>
				
			</div>
		</div>
		
	</div>

</div>


