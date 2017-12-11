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
			    /*  {field:'chanceSource',title:'数据机会来源名',width:80,align:'center'} ,*/    
			     {field:'customerName',title:'客户名称',width:100,align:'center'},    
			     /* {field:'successRate',title:'成功几率',width:80,align:'center'},  */   
			     {field:'overview',title:'概要',width:100,align:'center'},    
			     {field:'linkMan',title:'联系人',width:100,align:'center'},    
			     {field:'linkPhone',title:'联系电话',width:100,align:'center'},    
			     /* {field:'description',title:'机会描述',width:100,align:'center'}, */    
			     {field:'createMan',title:'创建人',width:100,align:'center'},    
			     {field:'createTime',title:'创建时间',width:100,align:'center'},    
			    /*  {field:'assignMan',title:'指派人',width:70,align:'center'},    
			     {field:'assignTime',title:'指派时间',width:100,align:'center'}, */   
			     {field:'status',title:'分配状态',width:70,align:'center',
			    	formatter:function(value,row,index){
			    		if (value==1) {
							return "已分配";
						} else {
							return "未分配";
						}
			    	}
			     },    
			    /*  {field:'devResult',title:'开发中 ',width:100,align:'center'},  */   
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
			'customerName':$("#s_customerNameId").val(),
			'linkMan':$("#s_linkManId").val(),
			'createMan':$("#s_createManId").val(),
			'status':$("#s_statusId").val(),
			'beginTime':$("#s_beginTimeId").val(),
			'endTime':$("#s_endTimeId").val()
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
		var values = '${user.name}';
		$("#createManId").﻿﻿textbox('setValue',values);
		$("#createTimeId").﻿﻿textbox('setValue',getCurrentDateTime());
		
	}
	/* 打开修改dialog */
	function openUpdateDialog() {
		var selections = $("#datagrid").datagrid("getSelections");
		if(selections.length == 0) {
			$.messager.alert("系统提示", "请选择要修改的数据");
			return;
		}
		var row = selections[0];
		$("#dialog").dialog("open").dialog("setTitle","修改信息");
		url = "${ctx}/saleChance/update.action";
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
	/* 导出表格 */
	function createExcel() {
		window.location.href="${ctx}/saleChance/createExcel.action";
	}
	
	/* 打开导入表格的dialog */
	function openReadExcel() {
		$("#dialogExcel").dialog("open")
	}
	
	/* 上传Excel */
	function uploadExcel() {
		/*  var formData = new FormData($( "#uploadForm" )[0]);  
	     $.ajax({  
	          url: '${ctx }/saleChance/uploadExcel.action',  
	          type: 'POST',
	          datatype:"json",
	          data: formData,  
	          async: false,  
	          cache: false,  
	          contentType: false,  
	          processData: false,  
	          success: function (data) {
	              alert(data.msg);  
	          },  
	          error: function (returndata) {  
	              alert(data.msg);  
	          }  
	     });  */
		$("#uploadForm").submit();
	}
	
	/* 打印 */
	function  printTable(){
        $("#aa").jqprint({
        	debug: true, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
            importCSS: true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
            printContainer: true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
            operaSupport: true//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
        });
    }
</script>
</head>
<body id="aa">
	<table id="datagrid"></table>
	
	<!-- toolbar 开始 -->
	<div id="toolbar">
		<div> 
			<a class="easyui-linkbutton" href="javascript:openAddDialog()" iconCls="icon-add">添加</a>
			<a class="easyui-linkbutton" href="javascript:openUpdateDialog()" iconCls="icon-edit">修改</a>
			<a class="easyui-linkbutton" href="javascript:doDelete()" iconCls="icon-remove">删除</a>
			<a class="easyui-linkbutton" href="javascript:createExcel()" iconCls="icon-add">导出表格</a>
			<a class="easyui-linkbutton" href="javascript:openReadExcel()" iconCls="icon-add">导入表格</a>
			<a class="easyui-linkbutton" href="javascript:printTable()" iconCls="icon-add">打印表格</a>
		</div>
		<div>
			<%-- 数据字典名：<input type="text" id="saleChanceName" class="easyui-combobox"
					 data-options="
					 	url:'${ctx}/saleChance/findSaleChanceName.action',
					 	valueField: 'saleChanceName',
					 	textField: 'saleChanceName',
					 	panelHeight:'auto',
					 	editable:false,
					 	blankText:'qingxuanze' "/> --%>
		       	客户名称：<input type="text" id="s_customerNameId" name="customerName" style="width: 100px"/>&nbsp;&nbsp;&nbsp;
		       	联系人：<input type="text" id="s_linkManId" name="linkMan" style="width: 100px" />&nbsp;&nbsp;&nbsp;
		       	创建人：<input type="text" id="s_createManId" name="createMan" style="width: 100px" />&nbsp;&nbsp;&nbsp;
		       	分配状态：
		       	<select id="s_statusId" name="status" class="easyui-combobox" style="width:90px;">   
				    <option value="">--请选择--</option>
				    <option value="1">已分配</option>   
				    <option value="0" >未分配</option>   
				</select><br>
				开始时间：<input id="s_beginTimeId" class="easyui-datebox" name="beginTime" data-options="sharedCalendar:'#cc'">&nbsp;&nbsp;&nbsp;
				结束时间：<input id="s_endTimeId"  class="easyui-datebox" name="endTime" data-options="sharedCalendar:'#cc'">&nbsp;&nbsp;&nbsp;
				<div id="cc" class="easyui-calendar"></div>
		  <a href="javascript:doSearch();" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
		</div>
	</div>
	<!-- toolbar 结束 -->
	
	<!-- 导入Excel表格dialog -->
	<div id="dialogExcel" class="easyui-dialog" title="导入Excel" style="width:400px;height:200px;padding:10px"
			data-options="
				closed:true,
				iconCls: 'icon-save',
			">
		<form name="Form2" id="uploadForm" action="${ctx }/saleChance/uploadExcel.action" method="post"  enctype="multipart/form-data">
			<div style="margin-bottom:20px">
				<input class="easyui-filebox" name="file" data-options="prompt:'选择Excel'" style="width:80%">
			</div>
		</form>
		<div>
			<a href="Javascript:uploadExcel()" class="easyui-linkbutton" style="width:100%">导入</a>
		</div>
	</div>
	<!-- 导入Excel表格dialog -->
	
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
						<!-- <textarea rows="3" cols="50" id="descriptionId" name="description"></textarea> -->
						<input class="easyui-textbox" id="descriptionId" name="description" data-options="multiline:true" style="width:250px;height:60px">
					</td>
				</tr>
				<tr>
					<td>创建人：</td>
					<td><input type="text" id="createManId" name="createMan"  class="easyui-textbox" required="true"/><font color="red">*</font></td>
					<td>创建时间：</td>
					<td>
					<input  id="createTimeId" name="createTime"  type= "text" class= "easyui-datebox" required ="required"/>
					<font color="red">*</font></td>
				</tr>
				<tr>
					<td>指派给：</td>
					<td><input  id="assignManId" name="assignMan" required="true" missingMessage="不能为空" class="easyui-combobox"
					 data-options="
					 	url:'${ctx}/saleChance/findAssignMan.action',
					 	valueField: 'trueName',
					 	textField: 'trueName',
					 	panelHeight:'auto',
					 	editable:false,
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


