<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="gs-editBody" ng-controller="MapEditCtrl" height="100%" width="100%">
<!-- 	<div id="gs-editContent">
		<div class="headerText">
			<i class="fa fa-paint-brush"></i>编辑文件
		<button type="submit" class="btn btn-sm btn-success "style="float:right;" ng-click=submit()>提交</button>
		
		</div>
		
		<textarea class="gs-editCode"  ng-model="text"></textarea>
	
	</div>
	<div id="gs-editShow">
		<div class="headerText">
		<i class="fa fa-hand-o-down"></i>显示地图
		</div>
		<div id = "SVGEditArea" class="gs-iframe">
		</div>
	 
	</div> -->

	<iframe src="resources/vgeEdit/svg-editor.html" height="100%" width="100%" async>
	</iframe> 
	<%-- <%@ include
					file="vgeEdit/svg-editor.html"%> --%>
</div>