<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="../common/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">

/* 打开新的tab 订单详情页 */
function findOrderItem(id) {
	window.parent.openTab('订单详情','${ctx }/orderItem/index.action?orderId='+id+'&customerId=${param.customerId}','icon-lsdd');
}

/* 显示内容 */
$(function() {
	
	$.post("${ctx}/customerOrder/findCustomerById.action",
		{"customerId":'${param.customerId}'},
		function (data) {
			if (data.status==0) {
				$("#numId").﻿﻿textbox('setValue',data.data.num);
				$("#nameId").﻿﻿textbox('setValue',data.data.name);
			}
		},"json"		
	);
	/*展示数据的datagrid表格*/
	$("#datagrid").datagrid({
		url:'${ctx}/customerOrder/findAll.action?customerId=${param.customerId}',//只查询已分配的咨询师
		saveUrl:'${ctx}/customerOrder/add.action?customerId=${param.customerId}',//添加
		updateUrl:'${ctx}/customerOrder/update.action?customerId=${param.customerId}',//更新
		destroyUrl:'${ctx}/customerOrder/deleteById.action',//删除
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
		     {field:'orderNo',title:'订单号',width:100,align:'center',editor:{type:'validatebox',options:{required:true}}},    
		     {field:'orderDate',title:'订购日期',width:100,align:'center' ,editor:{type:'datebox',options:{required:true}}},    
		     {field:'address',title:'送货地址',width:100,align:'center',editor:{type:'validatebox',options:{required:true}}},    
		     {field:'status',title:'状态',width:100,align:'center',editor:{type:'validatebox',options:{required:true}},
		    	 formatter:function(value,row,index){
			    		if (value==0) {
							return "未付款";
						} else {
							return "已付款";
						}
			    	}
		     },
		     {field:'',title:'操作',width:100,align:'center',editor:{type:'validatebox',options:{required:true}},
		    	 formatter:function(value,row,index){
			    		if (true) {
							return "<a href='javascript:findOrderItem("+row.id+")' >查看订单明细</a>";
						}
			    	}
		     }
		]]  
	});
})
/* 打开新的标签页 */
function openTab(id) {
	 window.parent.openTab('客户开发计划项','${ctx }/customerPlan/index.action');
}
	$(function(){
		
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
			'devResult':$("#s_devResultId").val(),
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
					"${ctx}/customerPlan/delete.action",
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
		url = "${ctx}/customerPlan/add.action";
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
		url = "${ctx}/customerPlan/update.action";
		$('#form').form("load", row);
	}
	
	 /* 开发成功或失败 */
	 function updateSaleChance(devResult) {
			$.post("${ctx}/cusDevPlan/updateSaleChanceDevResult.action",
				{"id":'${param.saleChanceId}',"devResult":devResult},
				function(data) {
					 if(data.status == 0){//0代表成功
						 $.messager.alert("系统提示",data.msg);
					 	 window.location.reload();
					 }else{
						 $.messager.alert("系统提示",data.msg);
					 }
				},"json"
			);
	}
	
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
<!-- panl -->
	<div id="p" class="easyui-panel" title="My Panel" >
		<!-- 添加和修改的dialog 开始 -->
		<form action="" id="form" method="post">
			<input type="hidden" id="id" name="id"/>
			<table cellspacing="8px">
				<tr>
					<td>编号	：</td>
					<td><input type="text" id="numId" name="num" class="easyui-textbox" readonly="readonly"/></td>
					<td>客户名称：</td>
					<td><input type="text" id="nameId" name="name" class="easyui-textbox" readonly="readonly" /></td>
				</tr>
			</table>
		</form>
		
	<!-- 添加和修改的dialog 结束 -->    
	</div>  
<!-- panl -->

	<table id="datagrid"></table>
	<!-- toolbar 开始 -->
		<div id="toolbar">
			<%-- <c:if test="${param.show != 'true' }">
					<div> 
						<a class="easyui-linkbutton" href="javascript:$('#datagrid').edatagrid('addRow')" iconCls="icon-add">添加</a>&nbsp;&nbsp;
						<a class="easyui-linkbutton" href="javascript:$('#datagrid').edatagrid('destroyRow')" iconCls="icon-remove">删除</a>&nbsp;&nbsp;
						<a class="easyui-linkbutton" href="javascript:$('#datagrid').edatagrid('saveRow');$('#datagrid').edatagrid('reload')" iconCls="icon-save">保存</a>&nbsp;&nbsp;
						<a class="easyui-linkbutton" href="javascript:$('#datagrid').edatagrid('cancelRow')" iconCls="icon-undo">撤销行</a>&nbsp;&nbsp;
					</div>
			</c:if> --%>
		</div>
	<!-- toolbar 结束 -->
	
	


</body>
</html>


