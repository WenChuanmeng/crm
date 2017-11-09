<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="../common/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>CRM</title>

</head>
<body class="easyui-layout">

	<div region="north" style="height: 78px; background-color: #fff">
	<table>
		<tr>
			<td width="85%"><div style="margin-left: 20px"><span style="font-size: 25;font-weight: 900" >Customer Relationship Management System</span></div></td>
			<td style=""><div style="margin-top: 40px"><span style="font-size: 16;font-weight: 600">欢迎：${user.name }，权限：${user.roleName }</span></div></td>
		</tr>
	</table>
	</div>
	<div region="center">
		<div class="easyui-tabs" fit="true" border="false" id="tabs">
			<div title="首页" data-options="iconCls:'icon-home'">
				<div  style="width:200px; height:26px; margin: 15% auto;">
					<span style="font-size: 30; font-weight: 700;">欢迎光临，${user.name }</span>
				</div>
			</div>
		</div>
	</div>
	<div region="west" style="width: 200px" title="导航菜单" split="true">
		<div class="easyui-accordion" data-options="fit:true,border:false">
			<div title="营销管理" data-options="selected:true,iconCls:'icon-yxgl'"
				style="padding: 10px">
				<a
					href="javascript:openTab('营销机会管理','${ctx }/saleChance/index.action','icon-yxjhgl')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-yxjhgl'"
					style="width: 150px">营销机会管理</a> <a
					href="javascript:openTab('客户开发计划','${ctx }/customerPlan/index.action','icon-khkfjh')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-khkfjh'"
					style="width: 150px">客户开发计划</a>
			</div>
			<div title="客户管理" data-options="iconCls:'icon-khgl'"
				style="padding: 10px;">
				<a
					href="javascript:openTab('客户信息管理','${ctx }/customer/index.action','icon-khxxgl')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-khxxgl'"
					style="width: 150px;">客户信息管理</a> <a
					href="javascript:openTab('客户流失管理','${ctx }/customerLoss/index.action','icon-khlsgl')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-khlsgl'"
					style="width: 150px;">客户流失管理</a>
			</div>
			<div title="服务管理" data-options="iconCls:'icon-fwgl'"
				style="padding: 10px">
				<a
					href="javascript:openTab('服务创建','${ctx }/customerServiceCreate/index.action','icon-fwcj')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-fwcj'" style="width: 150px;">服务创建</a>
				<a
					href="javascript:openTab('服务分配','${ctx }/customerServiceAssign/index.action','icon-fwfp')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-fwfp'" style="width: 150px;">服务分配</a>
				<a
					href="javascript:openTab('服务处理','${ctx }/customerServiceDeal/index.action','icon-fwcl')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-fwcl'" style="width: 150px;">服务处理</a>
				<a
					href="javascript:openTab('服务反馈','${ctx }/customerServiceDealResult/index.action','icon-fwfk')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-fwfk'" style="width: 150px;">服务反馈</a>
				<a
					href="javascript:openTab('服务归档','${ctx }/customerServiceShow/index.action','icon-fwgd')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-fwgd'" style="width: 150px;">服务归档</a>
			</div>
			<div title="统计报表" data-options="iconCls:'icon-tjbb'"
				style="padding: 10px">
				<a href="javascript:openTab('客户贡献分析','${ctx }/customer/customerContributeAnalysisPage.action','icon-khgxfx')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-khgxfx'"
					style="width: 150px;">客户贡献分析</a> <a
					href="javascript:openTab('客户构成分析','${ctx }/customer/customerConstitutePage.action','icon-khgcfx')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-khgcfx'"
					style="width: 150px;">客户构成分析</a> <a
					href="javascript:openTab('客户服务分析','${ctx }/customerServiceShow/customerServiceAnalysisPage.action','icon-khfwfx')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-khfwfx'"
					style="width: 150px;">客户服务分析</a>
			</div>
			<div title="基础数据管理" data-options="iconCls:'icon-jcsjgl'"
				style="padding: 10px">
				<a
					href="javascript:openTab('数据字典管理','${ctx}/dataDic/index.action','icon-sjzdgl')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-sjzdgl'"
					style="width: 150px;">数据字典管理</a> <a
					href="javascript:openTab('产品信息查询','${ctx}/product/index.action','icon-cpxxgl')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-cpxxgl'"
					style="width: 150px;">产品信息查询</a> 
					<a href="javascript:openTab('用户信息管理','${ctx}/user/index.action','icon-user')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-user'" style="width: 150px;">用户信息管理</a>
			</div>
			<div title="系统管理" data-options="iconCls:'icon-item'"
				style="padding: 10px">
				<a href="javascript:openUpdatePasswordDialog()"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-modifyPassword'"
					style="width: 150px;">修改密码</a> <a href="javascript:logout()"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-exit'" style="width: 150px;">安全退出</a>
			</div>
		</div>
	</div>
	<div region="south" style="height: 30px; padding: 5px" align="center">
		CRM管理系统
	</div>
	
	<!-- 修改密码 -->
		<div id="dialog" class="easyui-dialog" closed="true" data-options="buttons:'#dialog-button'" title="修改密码" style="width:400px;padding:30px 60px">
			<form id="passwordForm" action="${ctx }/users/updatePassword.action" method="post">
					<div style="margin-bottom:20px">
						<div>用户名:</div>
						<input id="userName" class="easyui-textbox" name="name" readonly="readonly"  style="width:100%;height:32px">
					</div>
					<div style="margin-bottom:20px">
						<div>前密码:</div>
						<input class="easyui-textbox" required="true" type="password" name="password" style="width:100%;height:32px">
					</div>
					<div style="margin-bottom:20px">
						<div>重置密码:</div>
						<input class="easyui-textbox" id="newpasswordId" required="true" type="password" name="newpassword" style="width:100%;height:32px">
					</div>
					<div style="margin-bottom:20px">
						<div>确认密码:</div>
						<input class="easyui-textbox" id="repasswordId" required="true" type="password" name="repassword" style="width:100%;height:32px">
					</div>
					<!-- dialog-button 开始-->
				<div id="dialog-button">
					<a href="javascript:doSave()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
					<a href="javascript:closeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
				</div>
				<!-- dialog-button 结束-->
			</form>
		</div>
		<!-- 修改密码 -->
					
	
<script type="text/javascript">
	function openTab(text, url, iconCls) {
		if ($("#tabs").tabs("exists", text)) {
			$("#tabs").tabs("select", text);
		} else {
			var content = "<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='"
					+ url + "'></iframe>";
			$("#tabs").tabs("add", {
				title : text,
				iconCls : iconCls,
				closable : true,
				content : content
			});
		}
	}
	
	function openUpdatePasswordDialog() {
		$("#passwordForm").form("clear");
		$("#userName").textbox('setValue','${user.name }');
		$("#dialog").dialog("open")
	}
	
	function closeDialog(){
		 $("#dialog").dialog("close");
	}
	
	function doSave(){
		if ($("#repasswordId").val() != $("#newpasswordId").val() ) {
			$.messager.alert("系统提示", "两次密码不一致");
			return;
		}
		$('#passwordForm').form('submit', {    
		    url:"${ctx}/user/updatePassword.action",    
		    onSubmit: function(){    
		        return $(this).form("validate");
		    },    
		    success:function(data){//正常返回ServerResponse
		    	var data = eval('(' + data + ')');
		    	if(data.status == Util.SUCCESS) {
		    		$.messager.alert("系统提示", data.msg);
		    		$("#dialog").dialog("close");
		    	} else {
		    		$.messager.alert("系统提示", data.msg);
		    		
		    	}
		    }    
		});  
	}
	
	/* 退出 */
	function logout() {
		window.location.href="${ctx}/login/loginOut.action";		
	}
</script>
</body>
</html>