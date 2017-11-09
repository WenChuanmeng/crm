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
		
		 // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        /* / 异步加载数据
        $.post('${ctx}/customer/customerConstitute.action',
        	function(result){
        	if(result.status == 0) {
        	var xAxisData=new Array();
	    	var seriesData=new Array();
	    	var data = result.data;
	    	for(var i=0;i<data.length;i++){
	    	xAxisData.push(data[i].levelName);
	    	seriesData.push(data[i].levelNum);
	    	}
	    	console.log(xAxisData);
	    	        	// 填入数据
	    	            myChart.setOption({
	    	                title: {
	    	                    text: 'ECharts 入门示例'
	    	                },
	    	                tooltip: {},
	    	                legend: {
	    	                    data:['销量']
	    	                },
	    	                xAxis: {
	    	                    data: xAxisData
	    	                },
	    	                yAxis: {},
	    	                series: [{
	    	                    // 根据名字对应到相应的系列
	    	                    name: '销量',
	    	                    type: 'bar',
	    	                    data: seriesData
	    	                }]
	    	            });
	        	} else {
	        	alert("查询失败");
	        	}
	        	
	        	},
	        	'json'
	        	); */
		
		var myChart = echarts.init(document.getElementById('main'));
		var options = {
			url : '${ctx}/customerServiceShow/customerServiceAnalysis.action',
			type : 'post',
			datatype : 'json',
			success : function(result) {
				if (result.status == 0) {
					
					var names = new Array();
					var values = new Array();
					var brower = new Array();
					var data = result.data;
					for (var int = 0; int < data.length; int++) {
						names.push(data[int].serviceTypeName);
						values.push(data[int].serviceTypeTimes);
					}
					 $.each(result.data, function (index, item) {
			                names.push(item.serviceTypeName);    //挨个取出类别并填入类别数组 
			                values.push(item.serviceTypeTimes);
			                brower.push({
			                    name: item.serviceTypeName,
			                    value: item.serviceTypeTimes
			                });
			            });
					myChart.setOption({
						    title : {
						        text: '客户服务类型分析',
						        x:'center'
						    },
						    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						    },
						    legend: {
						        orient: 'vertical',
						        left: 'left',
						        data:names
						    },
						    series : [
						        {
						            name: '访问来源',
						            type: 'pie',
						            radius : '55%',
						            center: ['50%', '60%'],
						            data:brower,
						            itemStyle: {
						                emphasis: {
						                    shadowBlur: 10,
						                    shadowOffsetX: 0,
						                    shadowColor: 'rgba(0, 0, 0, 0.5)'
						                }
						            }
						        }
						    ]
						});
				} else {
					$.message.alert(result.msg);
				}
			}
		};
		$.ajax(options);
	});
</script>
</head>
<body>
	 <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="main" style="width: 100%;height:100%;"></div>

</body>
</html>


