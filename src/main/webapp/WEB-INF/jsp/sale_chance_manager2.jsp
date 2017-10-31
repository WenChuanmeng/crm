<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="../common/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
	$(function(){
		/*展示数据的datagrid表格*/
		$("#datagrid").datagrid({
			url:'${ctx}/saleChance/findAll.action',
			method:'get',
			fit:true,
			singleSelect:false,
			toolbar:'#toolbar',
			rownumbers:true,
			fitColumns:true,
			pagination:true,
			columns:[[    
			     {field:'cb',checkbox:true,align:'center'},    
			     {field:'id',title:'编号',width:80,align:'center'},    
			     {field:'chanceSource',title:'数据机会来源名',width:80,align:'center'},    
			     {field:'customerName',title:'客户名称',width:100,align:'center'},    
			     {field:'successRate',title:'成功几率',width:80,align:'center'},    
			     {field:'overview',title:'概要',width:100,align:'center'},    
			     {field:'linkMan',title:'联系人',width:100,align:'center'},    
			     {field:'linkPhone',title:'联系电话',width:100,align:'center'},    
			     {field:'description',title:'机会描述',width:100,align:'center'},    
			     {field:'createMan',title:'创建人',width:100,align:'center'},    
			     {field:'createTime',title:'创建时间',width:100,align:'center'},    
			     {field:'assignMan',title:'指派人',width:70,align:'center'},    
			     {field:'assignTime',title:'指派时间',width:100,align:'center'},    
			     {field:'status',title:'分配状态',width:70,align:'center'},    
			     {field:'devResult',title:'开发中 ',width:100,align:'center'},    
			]]  
		});
		
		/*添加和修改弹出的dialog */
		$("#dialog").dialog({
			closed:'true',
			buttons:[
				{
					text:'保存',
					iconCls:'icon-ok',
					handler:function(){
						doSave();
					}
				},
				{
					text:'关闭',
					iconCls:'icon-cancel',
					handler:function(){
						$("#dialog").dialog("close");
					}
				}
				
			]
			
		});
	});
	
	/*添加或修改的dialog */
	function doSave() {
		$('#form').form('submit', {    
		    url:url,    
		    onSubmit: function(){    
		        // do some check    
		       /*  if($("#saleChanceName").combobox("getValue") == "") {
		        	$.messager.alert("系统提示", "请选择用户角色");
		        	return false;
		        } */
		        //validate none 做表单字段验证，当所有字段都有效的时候返回true。该方法使用validatebox(验证框)插件。 
		        // return false to prevent submit;  
		        return $(this).form("validate");
		    },    
		    success:function(data){//正常返回ServerResponse
		    	//alert(data);
		    	var data = eval('(' + data + ')');
		    	if(data.status == Util.SUCCESS) {
		    		$.messager.alert("系统提示", data.msg);
		    		$("#dialog").dialog("close");
		    		$("#datagrid").datagrid("reload");
		    	}
		    }    
		});  
	}

	/* 查找 */
	function doSearch(){
		$("#datagrid").datagrid("load",{
			'saleChanceName':$("#saleChanceName").val(),
			'saleChanceValue':$("#saleChanceValue").val()
		})
	}
	
	/* 删除 */
	function doDelete(){
		var ids = Util.getSelectionsIds("#datagrid");
		if (ids.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的数据");
			return;
		}
		$.messager.confirm("系统提示", "您确认要删除么", function(r){
			if (r){
				$.post(
					"${ctx}/saleChance/delete.action",
					{ids:ids}, 
					function(result) {
						$.messager.alert("系统提示", result.msg);
						if(result.status == Util.SUCCESS) {
							$("#datagrid").datagrid("reload");
						}
					},
					"json"
				);
			}
		})
	}
	
	var url;
	/* 打开添加dialog */
	function openAddDialog() {
		$("#dialog").dialog("open").dialog("setTitle","添加信息");
		url = "${ctx}/saleChance/add.action";
		$('#form').form("clear");
		
	}
	/* 打开修改dialog */
	function openUpdateDialog() {
		var selections = $("#datagrid").datagrid("getSelections");
		if(selections.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的数据");
			return;
		}
		var row = selections[0];
		$("#dialog").dialog("open").dialog("setTitle","修改信息");
		url = "${ctx}/saleChance/update.action";
		$('#form').form("load", row);
	}
</script>
</head>
<body>
	<table id="datagrid"></table>
	
	<!-- toolbar 开始 -->
	<div id="toolbar">
		<div>
			<a class="easyui-linkbutton" href="javascript:openAddDialog()" iconCls="icon-add">添加</a>
			<a class="easyui-linkbutton" href="javascript:openUpdateDialog()" iconCls="icon-edit">修改</a>
			<a class="easyui-linkbutton" href="javascript:doDelete()" iconCls="icon-remove">删除</a>
		</div>
		<div>
			数据字典名：<input type="text" id="saleChanceName" class="easyui-combobox"
					 data-options="
					 	url:'${ctx}/saleChance/findSaleChanceName.action',
					 	valueField: 'saleChanceName',
					 	textField: 'saleChanceName',
					 	panelHeight:'auto',
					 	editable:false,
					 	blankText:'qingxuanze' "/>
		       数据字典值：<input type="text" id="saleChanceValue"></input>
		  <a href="javascript:doSearch();" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
		</div>
	</div>
	<!-- toolbar 结束 -->
	
	<!-- 添加和修改的dialog 开始 -->
	<div id="dialog" style="width:700;height:280,padding: 10px 20px">
		<form action="" id="form" method="post">
			<input type="hidden" id="id" name="id"/>
			<table cellspacing="8px">
				<tr>
					<td>客户名称	：</td>
					<td><input type="text" id="customerNameId" name="customerName" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
					<td>数据机会来源名：</td>
					<td><input type="text" id="chanceSourceId" name="chanceSource" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>联系人：</td>
					<td><input type="text" id="linkManId" name="linkMan" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
					<td>联系电话	：</td>
					<td><input type="text" id="linkPhoneId" name="linkPhone" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
				</tr>
				<tr>
					<td>成功几率	（%）：</td>
					<td><input type="text" id="successRateId" name="successRate" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
				</tr>
				<tr>
					<td>概要：</td>
					<td colspan="3"><input type="text" id="overviewId" name="overview" style="width: 300px" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>机会描述：</td>
					<td colspan="3"><!-- <input type="textarea" id="saleChanceValue" name="saleChanceValue" style="width: 300px;height: 60px" class="" required="true"/><font color="red">*</font> -->
						<textarea rows="3" cols="50" id="descriptionId" name="description"></textarea>
					</td>
				</tr>
				<tr>
					<td>创建人：</td>
					<td><input type="text" id="createManId" name="createMan" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
					<td>创建时间：</td>
					<td>
					<input  id="createTimeId" name="createTime"  type= "text" class= "easyui-datebox" required ="required"/>
					<font color="red">*</font></td>
				</tr>
				<tr>
					<td>指派给：</td>
					<td><input type="text" id="assignManId" name="assignMan" class="easyui-combobox"
					 data-options="
					 	url:'${ctx}/saleChance/findSaleChanceName.action',
					 	valueField: 'assignMan',
					 	textField: 'assignMan',
					 	panelHeight:'auto',
					 	
					 	"/></td>
					<td>指派时间：</td>
					<td>
						<input  id="assignTimeId" name="assignTime"  type= "text" class= "easyui-datebox" />  
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 添加和修改的dialog 结束 -->


</body>
</html>


