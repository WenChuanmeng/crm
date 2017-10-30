<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="../common/head.jsp" %>
</head>
		<script type="text/javascript">
		/* 查找 */
		function doSearch(value){
			$("#datagrid").datagrid("load",{
				'userName':value
			})
		}
			function todelete() {
				var selectIds = $("#datagrid").datagrid("getSelections");
				
				var ids = [];
				for ( var id in selectIds) {
					ids.push(selectIds[id].id);
				}
				ids = ids.join(",");
				var options = {
					url : "${prc}/user/todelete.action",
					type : "post",
					datatype : "json",
					data : {"ids":ids},
					success : function (result) {
						$.messager.alert("系统提示", result.msg);
						if(result.status == 0) {
							$("#datagrid").datagrid("reload");
						}
					}
				};
				$.ajax(options);
			}
			
			/* 打开添加 */
	    	function toAdd() {
				alert()
	    		$("#id").val(null);
				$("#userName").val(null);
				$("#password").val(null);
				$("#trueName").val(null);
				$("#email").val(null);
				$("#phone").val(null);
				$("#roleName").val(null);
	    		$("#subID").val("add");
				$("#dlg").dialog("open");
				
			}
	    	
	    	/* 重置信息 */
	    	function clearForm() {
				$("#subForm").form("clear");
			}
	    	
	    	/* 添加 */
	    	function manage() {
	    		var urlSub = $("#subID").val();
				var options = {
					url : "${prc }/user/"+urlSub+".action",
					type : "post",
					datatype : "json",
					data : $("#subForm").serialize(),
					success : function (data) {
						if (data.status == 0) {
							alert(data.msg);
							$.messager.confirm(data.msg, function(r){
								if (r){
									
								}
							});
						} else {
							alert(data.msg)
						}
					}
				}
				$.ajax(options);
			}
	    	
	    	/* 修改信息  */
	    	function toUpdate() {
	    		//获得学生的学号
	    		
	    		var selectId = $("#datagrid").datagrid("getSelected");
	    		var id = selectId.id;
				var  options = {
					url : "${prc}/user/findById.action",
					type : "post",
					datatype : "json",
					data : {"id" : id},
					success : function (data) {
						if (data.status == 0) {
							$("#id").val(data.data.id);
							$("#userName").val(data.data.userName);
							$("#password").val(data.data.password);
							$("#trueName").val(data.data.trueName);
							$("#email").val(data.data.email);
							$("#phone").val(data.data.phone);
							$("#roleName").val(data.data.roleName);
							$("#subID").val("update");
							$("#dlg").dialog("open");
						} else {

						}
					}
				};
				$.ajax(options);
			}
	    	
	    	/* 日期格式 */
			$.extend($.fn.validatebox.defaults.rules, {
				md: {
					validator: function(value, param){
						var d1 = $.fn.datebox.defaults.parser(param[0]);
						var d2 = $.fn.datebox.defaults.parser(value);
						return d2<=d1;
					},
					message: 'The date must be less than or equals to {0}.'
				}
			});
		</script>
<body>
	<table id="datagrid" class="easyui-datagrid"  style="width:100%;height: 430px" fitColums="true" pagination="true" 
				rownumbers="true" fitColumns="true"
				data-options="singleSelect:false,collapsible:true,url:'${prc }/user/findAll.action',method:'get',toolbar:'#toolbar'">
			<thead>
				<tr>
					<th data-options="field:'cb',checkbox:true,align:'center'"></th>
					<th data-options="field:'id',width:80,align:'center'">编号</th>
					<th data-options="field:'userName',width:100,align:'center'">用户名</th>
					<th data-options="field:'password',width:120,align:'center'">密码</th>
					<th data-options="field:'trueName',width:80,align:'center'">真实姓名</th>
					<th data-options="field:'email',width:150,align:'center'">邮箱</th>
					<th data-options="field:'phone',width:100,align:'center'">电话</th>
					<th data-options="field:'roleName',width:100,align:'center'">角色</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar" style="padding:5px;height:auto">
			<div style="margin-bottom:5px">
				<a href="javascript:toAdd()" class="easyui-linkbutton" iconCls="icon-add" plain="true"></a>
				<a href="javascript:toUpdate()" class="easyui-linkbutton" iconCls="icon-edit" plain="true"></a>
				<a href="javascript:todelete()" class="easyui-linkbutton" iconCls="icon-remove" plain="true"></a>
				&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="easyui-searchbox" data-options="prompt:'用户名',searcher:doSearch" style="width:150px"></input>
			</div>
		</div>
		<!-- 添加学生 -->
			<div id="dlg" class="easyui-dialog" title="Basic Dialog" data-options="iconCls:'icon-save',closed:true" style="width:400px;height:200px;padding:10px">
				<!-- form -->
				<form id="subForm" method="post">
					<input type="hidden" id="id" name="id"  />
				    <div>
				        <label for="name">用户名:</label>   
				        <input id="userName" class="easyui-validatebox" type="text" name="userName" data-options="required:true" />   
				    </div>  
				    <div>
				        <label for="name">密码:</label>   
				        <input id="password" class="easyui-validatebox" type="text" name="password" data-options="required:true" />   
				    </div>  
				    <div>
				        <label for="name">真实姓名:</label>   
				        <input id="trueName" class="easyui-validatebox" type="text" name="trueName" data-options="required:true" />   
				    </div>  
				    <div>
				        <label for="name">邮箱:</label>   
				        <input id="email" class="easyui-validatebox" type="text" name="email" data-options="required:true" />   
				    </div>  
				    <div>
				        <label for="name">电话:</label>   
				        <input id="phone" class="easyui-validatebox" type="text" name="phone" data-options="required:true" />   
				    </div>  
				    <div>
				        <label for="name">角色:</label>   
				        <input id="roleName" class="easyui-validatebox" type="text" name="roleName" data-options="required:true" />   
				    </div>  
				</form>
				<a href="javascript:manage();" id="subID" class="easyui-linkbutton" data-options="iconCls:'icon-ok'"  >添加</a>
				<a href="javascript:clearForm();"  class="easyui-linkbutton" data-options="iconCls:'icon-ok'"  >重置</a>
				<!-- form -->
			</div>
		<!-- 添加学生 -->

</body>
</html>