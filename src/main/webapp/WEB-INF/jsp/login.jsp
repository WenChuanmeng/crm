<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="../common/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>CRM登录</title>

</head>
<body>
	<div style="margin: 100px auto;width: 400px" >
		<form id="subForm" action="${ctx }/login/loginIn.action" method="post">
			<div style="margin:20px 0;"></div>
			<div class="easyui-panel" title="Login to system" style="width:400px;padding:30px 70px 20px 70px" >
				<div style="margin-bottom:10px">
					<input class="easyui-textbox" name="name" style="width:100%;height:40px;padding:12px" data-options="prompt:'name',iconCls:'icon-man',iconWidth:38">
				</div>
				<div style="margin-bottom:20px">
					<input class="easyui-textbox" name="password" type="password" style="width:100%;height:40px;padding:12px" data-options="prompt:'password',iconCls:'icon-lock',iconWidth:38">
				</div>
				<!-- <div style="margin-bottom:20px">
					<input type="checkbox" checked="checked">
					<span>Remember me</span>
				</div> -->
				<div>
				
					<a href="javascript:login()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="padding:5px 0px;width:100%;">
						<span style="font-size:14px;">Login</span>
					</a>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		function login() {
			$("#subForm").submit();
		}
	</script>
</body>
</html>


