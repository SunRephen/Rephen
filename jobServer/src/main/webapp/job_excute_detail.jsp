<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	<input id="value" type="hidden" value="${value }"/>
	<input id="xAxis" type="hidden" value="${xAxis }"/>
	<input id="jobId" type="hidden" value="${jobId }"/>
	<input id="showAll" type="hidden" value="${showAll }"/>
	<div id="container" style="height: 90%"></div>
	<c:if test="${showAll == '0'}">
		<a href='http://localhost/job/showHistoryDetail?id=${jobId }'>查看该作业全部历史记录</a>
	</c:if>
	<script type="text/javascript" src="/js/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="/js/echarts.min.js"></script>

<script type="text/javascript">
var dom = document.getElementById("container");
var xAxis = $("#xAxis").val();
var value = $("#value").val();
var showAll = $("#showAll").val();
var title;
if(showAll == '1'){
	title = "任务历史运行耗时";
}else{
	title = "最近一天任务运行耗时";
}
var myChart = echarts.init(dom);
var app = {};
option = null;
option = {
    title: {
        text: title
    },
    tooltip: {
        trigger: 'axis',
        position: function (pt) {
            return [pt[0], '10%'];
        }
    },
    legend: {
        data:['耗时']
    },
    grid: {
        left: '5%',
        right: '5%',
        bottom: '10%',
        containLabel: true
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: eval('(' + xAxis + ')')
    },
    yAxis: {
        type: 'value'
    },
    dataZoom: [{
        type: 'inside',
        start: 0,
        end: 10
    }, {
        start: 0,
        end: 10,
        handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
        handleSize: '80%',
        handleStyle: {
            color: '#fff',
            shadowBlur: 3,
            shadowColor: 'rgba(0, 0, 0, 0.6)',
            shadowOffsetX: 2,
            shadowOffsetY: 2
        }
    }],
    series: [
             {
                 name:'耗时(ms)',
                 type:'line',
                 smooth:false,
                 symbol: 'none',
                 sampling: 'average',
                 itemStyle: {
                     normal: {
                         color: 'rgb(255, 70, 131)'
                     }
                 },
                 
                 data:eval('(' + value + ')')
             }
         ]
           
};
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
       </script>
</body>
</html>