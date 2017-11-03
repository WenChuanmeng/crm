<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="../common/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">

/* 打开新tab */
function openNewTab(url,icons) {
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length == 0) {
		$.messager.alert("系统提示", "请选择要修改的数据");
		return;
	}
	var row = selections[0];
	window.parent.openTab('客户开发计划项','${ctx }/'+url+'/index.action?customerId='+row.id,icons);
}
	$(function(){
		/*展示数据的datagrid表格*/
		$("#datagrid").datagrid({
			url:'${ctx}/customer/findAll.action',
			method:'get',
			fit:true,
			singleSelect:false,
			toolbar:'#toolbar',
			rownumbers:true,
			fitColumns:true,
			pagination:true,
			columns:[[    
			     {field:'cb',checkbox:true,align:'center'},    
			     {field:'id',title:'客户编号',width:80,align:'center'},    
			     {field:'num',title:'客户编号',width:80,align:'center'},    
			     {field:'name',title:'客户名称',width:100,align:'center'},    
			     {field:'managerName',title:'客户经理',width:100,align:'center'},    
			     {field:'level',title:'客户等级',width:100,align:'center'},    
			     {field:'phone',title:'联系电话',width:100,align:'center'},    
			     {field:'region',title:'地区',width:100,align:'center'},    
			     {field:'satisfy',title:'客户满意度',width:100,align:'center'},    
			     {field:'credit',title:'客户信用度',width:100,align:'center'},    
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
		       /*  if($("#customerName").combobox("getValue") == "") {
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
			'num':$("#s_numId").val(),
			'name':$("#s_nameId").val(),
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
					"${ctx}/customer/delete.action",
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
		url = "${ctx}/customer/add.action";
		$('#form').form("clear");
		var values = '${user.name}';
		$("#createManId").﻿﻿textbox('setValue',values);
		$("#createTimeId").﻿﻿textbox('setValue',getCurrentDateTime());
		
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
		url = "${ctx}/customer/update.action";
		$('#form').form("load", row);
	}
	
	//指派人生成时间
	$(function(){
	    $("#assignManId").combobox({
	        onSelect:function(record){//record就是User对象
	        	if(record.trueName=="--暂不指派--"){
	                $("#assignTimeId").﻿﻿textbox('setValue',null);
	                return;
	            }
	            if(record.trueName!=null){
	                $("#assignTimeId").﻿﻿textbox('setValue',getCurrentDateTime());
	            }else{
	                $("#assignTimeId").﻿﻿textbox('setValue',"");
	            }
	        }
	    });
	 });

	
	// 0-9 的日期和时间，在前面补0：9 -> 09
	 function formatZero(n){
	     if(n>=0&&n<=9){
	         return "0"+n;
	     }else{
	         return n;
	     }
	 }
	// 格式化时间
	function getCurrentDateTime () {
	    var date = new Date();//Mon Oct 30 2017 22:08:37 GMT+0800
	    var year=date.getFullYear();
	    var month=date.getMonth()+1;
	    var day=date.getDate();
	    var hours=date.getHours();
	    var minutes=date.getMinutes();
	    var seconds=date.getSeconds();
	    // 2017-01-01 02:23:06   yyyy-MM-dd hh:mm:ss
	    return year+"-"+this.formatZero(month)+"-"+this.formatZero(day)+" "+this.formatZero(hours)+":"+this.formatZero(minutes)+":"+this.formatZero(seconds);
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
			<a class="easyui-linkbutton" href="javascript:openNewTab('customerLinkMan','icon-lxr')" iconCls="icon-lxr">联系人管理</a>
			<a class="easyui-linkbutton" href="javascript:openNewTab('customerContact','icon-jwjl')" iconCls="icon-jwjl">交往记录管理</a>
			<a class="easyui-linkbutton" href="javascript:openNewTab('customerOrder','icon-jwjl')" iconCls="icon-jwjl">历史订单查看</a>
		</div>
		<div>
		       	编号：<input type="text" id="s_numId" name="num" style="width: 100px" />&nbsp;&nbsp;&nbsp;
		       	客户名称：<input type="text" id="s_nameId" name="name" style="width: 100px"/>&nbsp;&nbsp;&nbsp;
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
					<td><input type="text" id="nameId" name="name" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
					<td>客户地区：</td>
					<td>
						<select id="regionId" class="easyui-combobox" name="region" style="width:200px;">   
						    <option value="">--请选择--</option>   
						    <option value="华东">华东</option>   
						    <option value="华北">华北</option>   
						    <option value="华西">华西</option>   
						    <option value="华南" >华南</option>   
						    <option value="华中" >华中</option>   
						</select>
					</td>
				</tr>
				<tr>
					<td>客户名经理：</td>
					<td>
						<input  id="managerNameId" name="managerName" required="true" missingMessage="不能为空" class="easyui-combobox"
						 data-options="
						 	url:'${ctx}/customer/findAssignMan.action',
						 	valueField: 'trueName',
						 	textField: 'trueName',
						 	panelHeight:'auto',
						 	editable:false,
						 	"/><font color="red">*</font>
					 </td>
					<td>客户等级：</td>
					<td><input  id="levelId" name="level" required="true" missingMessage="不能为空" class="easyui-combobox"
					 data-options="
					 	url:'${ctx}/customer/findLevel.action',
					 	valueField: 'dataDicValue',
					 	textField: 'dataDicValue',
					 	panelHeight:'auto',
					 	editable:false,
					 	"/><font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>客户满意度：</td>
					<td>
						<select id="satisfyId" class="easyui-combobox" name="satisfy" style="width:200px;">   
						    <option value="">--请选择--</option>   
						    <option value="☆☆☆☆☆">☆☆☆☆☆</option>   
						    <option value="☆☆☆☆">☆☆☆☆</option>   
						    <option value="☆☆☆">☆☆☆</option>   
						    <option value="☆☆" >☆☆</option>   
						    <option value="☆" >☆</option>   
						</select>
					</td>
					<td>客户信用度：</td>
					<td>
						<select id="creditId" class="easyui-combobox" name="credit" style="width:200px;">   
						    <option value="">--请选择--</option>   
						    <option value="☆☆☆☆☆">☆☆☆☆☆</option>   
						    <option value="☆☆☆☆">☆☆☆☆</option>   
						    <option value="☆☆☆">☆☆☆</option>   
						    <option value="☆☆" >☆☆</option>   
						    <option value="☆" >☆</option>   
						</select>
					</td>
				</tr>
				<tr>
					<td>邮政编码：</td>
					<td><input type="text" id="postCodeId" name="postCode" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
					<td>联系电话	：</td>
					<td><input type="text" id="phoneId" name="phone" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
				</tr>
				<tr>
					<td>传真：</td>
					<td><input type="text" id="faxId" name="fax" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
					<td>网址	：</td>
					<td><input type="text" id="webSiteId" name="webSite" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
				</tr>
				<tr>
					<td>客户地址：</td>
					<td colspan="3"><input type="text" id="addressId" name=address style="width: 300px" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>营业执照注册号：</td>
					<td><input type="text" id="licenceNoId" name="licenceNo" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
					<td>法人	：</td>
					<td><input type="text" id="legalPersonId" name="legalPerson" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
				</tr>
				<tr>
					<td>注册资金（万元）：</td>
					<td><input type="text" id="bankrollId" name="bankroll" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
					<td>年营业额（万元）：</td>
					<td><input type="text" id="turnoverId" name="turnover" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
				</tr>
				<tr>
					<td>开户银行：</td>
					<td><input type="text" id="bankNameId" name="bankName" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
					<td>开户账号	：</td>
					<td><input type="text" id="bankAccountId" name="bankAccount" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
				</tr>
				<tr>
					<td>地税登记号：</td>
					<td><input type="text" id="localTaxNoId" name="localTaxNo" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
					<td>国税登记号	：</td>
					<td><input type="text" id="nationalTaxNoId" name="nationalTaxNo" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
				</tr>
			</table>
		</form>
		
	</div>
	<!-- 添加和修改的dialog 结束 -->


</body>
</html>


