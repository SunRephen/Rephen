<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="height: 100%">
<head>
<link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body style="height: 90%; margin: 0">
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
    <div class="navbar-header">
        <a class="navbar-brand" href="/job/list">作业调度监控系统</a>
    </div>
    <div>
        <ul class="nav navbar-nav">
            <li><a href="${pageContext.request.contextPath}/job/list">作业列表</a></li>
            <li><a href="${pageContext.request.contextPath}/user/list">人员列表</a></li>
            <li><a href="${pageContext.request.contextPath}/user/addOrEdit">添加/修改人员</a></li>
            <li  class="active"><a href="#">作业结果展示</a></li>
            <li><a href="${pageContext.request.contextPath}/welcome/logout">注销</a></li>
        </ul>
    </div>
    </div>
</nav>
	<input id="excuteCountResult" type="hidden" value="${excuteCountResult }"/>
	<input id="jobId" type="hidden" value="${jobId }"/>
	<div id="container" style="height: 90%"></div>
	<script type="text/javascript" src="/js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="/js/echarts.min.js"></script>

<script type="text/javascript">
var dom = document.getElementById("container");
var excuteCountResult = $("#excuteCountResult").val();
var myChart = echarts.init(dom);
option = null;
option = {
	    title : {
	        text: '作业历史执行情况',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	        data: ['成功次数','失败次数']
	    },
	    series : [
	        {
	            name: '访问来源',
	            type: 'pie',
	            radius : '50%',
	            center: ['50%', '60%'],
	            data:eval('(' + excuteCountResult + ')'),
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
myChart.on('click', function (params) {
	if(params.name == '成功次数'){
	    window.open('http://localhost/job/showRecentDetail?id='+document.getElementById("jobId").value);
	}
});
       </script>
</body>
</html>