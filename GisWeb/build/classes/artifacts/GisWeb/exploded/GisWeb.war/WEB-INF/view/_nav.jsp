<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="container">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle collapsed"
			data-toggle="collapse" data-target="#navbar" aria-expanded="false"
			aria-controls="navbar">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="#">基于SVG的时空GIS系统</a>
	</div>
	<div id="gs-navbar" class="navbar-collapse collapse">
		<ul class="nav navbar-nav">
			<li class="active"><a href="#home">首页</a></li>
			<li><a href="#mapCompare">地图比对</a></li>
			<li><a href="#mapEdit">地图编辑</a></li>
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown">更多<span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">扩展功能1</a></li>
					<li><a href="#">扩展功能2</a></li>
					<li><a href="#">扩展功能3</a></li>
					<li class="divider"></li>
					<li class="dropdown-header">帮助信息</li>
					<li><a href="#">关于我们</a></li>
					<li><a href="#">联系我们</a></li>
				</ul></li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li><a href="#" data-toggle="modal" data-target="#modalLogin">登录</a></li>
			<li><a href="#" data-toggle="modal" data-target="#modalRegister">注册</a></li>
		</ul>
	</div>
</div>

<!-- Modal Parts -->
<!-- 登录模态框 -->
<div class="modal fade" id="modalLogin" tabindex="-1" role="dialog"
	aria-labelledby="modalLoginLabl" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="modalLoginLabl"
					style="text-align: center">登录</h4>
			</div>
			<div class="modal-body">
				<!-- 主题部分-->
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label for="inputLoginUsername" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="inputLoginUsername"
								placeholder="用户名 / 邮箱名">
						</div>
					</div>
					<div class="form-group">
						<label for="inputLoginPassword" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-10">
							<input type="password" class="form-control"
								id="inputLoginPassword" placeholder="密码">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<div class="checkbox">
								<label> <input type="checkbox">记住我
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="submit" class="btn btn-success"
								data-dismiss="modal" onclick="alert('提交登录表单');">登录</button>
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
<!-- 登录模态框 -->

<!-- 注册模态框 -->
<div class="modal fade" id="modalRegister" tabindex="-1" role="dialog"
	aria-labelledby="modalRegisterLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="modalRegisterLabel"
					style="text-align: center">注册</h4>
			</div>
			<div class="modal-body">
				<!-- 主题部分-->
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label for="inputRegisterUsername" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-10">
							<input type="text" class="form-control"
								id="inputRegisterUsername" placeholder="用户名">
						</div>
					</div>
					<div class="form-group">
						<label for="inputRegisterEmail" class="col-sm-2 control-label">邮箱</label>
						<div class="col-sm-10">
							<input type="email" class="form-control" id="inputRegisterEmail"
								placeholder="邮箱">
						</div>
					</div>
					<div class="form-group">
						<label for="inputRegisterPassword" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-10">
							<input type="password" class="form-control"
								id="inputRegisterPassword" placeholder="密码">
						</div>
					</div>
					<div class="form-group">
						<label for="inputRegisterConfirmPassword"
							class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-10">
							<input type="password" class="form-control"
								id="inputRegisterConfirmPassword" placeholder="确认密码">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="submit" class="btn btn-success"
								data-dismiss="modal" onclick="alert('提交注册表单');">注册</button>
							<button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
</div>
<!-- 注册模态框 -->