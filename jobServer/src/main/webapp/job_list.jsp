<%@ page isELIgnored="false"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<style>
button {
	margin: 10px;
}
</style>
<script type="text/javascript" src="/js/jquery-1.5.2.min.js"></script>
<script type="text/javascript">
	var path = "${pageContext.request.contextPath}";
	function add() {
		location = path + "/job/add";
	};
	function start(id){
		$.get(path + "/job/start?id=" + id, function(resp){
			resp=$.parseJSON(resp);
			if(resp.message) {
				alert(resp.message);
			}
			location.reload();
		});
	};
	function stop(id){
		$.get(path + "/job/stop?id=" + id, function(resp){
			resp=$.parseJSON(resp);
			if(resp.message) {
				alert(resp.message);
			}
			location.reload();
		});
	};
	function hide(id){
		$.get(path + "/job/hide?id=" + id, function(resp){
			resp=$.parseJSON(resp);
			if(resp.message) {
				alert(resp.message);
			}
			location.reload();
		});
	};
</script>
<head>
    <title>
        任务管理
    </title>
</head>
<body style="text-align:center;">

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
	    <div class="navbar-header">
	        <a class="navbar-brand" href="/job/list">作业调度监控系统</a>
	    </div>
	    <ul class="nav navbar-nav navbar-right">
	    	<li><a href="/welcome/logout">注销</a></li>
	    </ul>
    </div>
</nav>

<div class="col-md-2 sidebar-offcanvas">
<div class="list-group">
	<ul class="nav nav-pills nav-stacked">
	    <a href="#" class="list-group-item active">作业列表</a>
	    <a class="list-group-item" href="${pageContext.request.contextPath}/user/list">人员列表</a>
        <a class="list-group-item" href="${pageContext.request.contextPath}/user/addOrEdit">添加/修改人员</a>
	</ul>
	</div>
</div>

<div class="col-md-10">
	<c:choose><c:when test="${not empty message }">
		<table align="center" cellpadding="0" cellspacing="0" style="border:0;">
			<tr><td>${message }</td></tr>
		</table>
	</c:when><c:otherwise>
    <table width="99%" class="table-striped">
        <thead>
            <tr>
                <th style="text-align:center;">作业标识</th>
                <th style="text-align:center;">作业类</th>
                <th style="text-align:center;">作业方法</th>
                <th style="text-align:center;">作业参数</th>
                <th style="text-align:center;">作业组</th>
                <th style="text-align:center;">作业名称</th>
                <th style="text-align:center;">作业状态</th>
                <th style="text-align:center;">作业调度参数</th>
                <th style="text-align:center;">作业描述</th>
                <th style="text-align:center;">负责人</th>
                <th style="text-align:center;">动作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${list}" varStatus="status">
            <tr id="j_id_${item.id }">
                <td>${item.id}</td>
                <td>${item.jobClass}</td>
                <td>${item.jobMethod}</td>
                <td>${item.jobArguments}</td>
                <td>${item.jobGroup}</td>
                <td><a href="${pageContext.request.contextPath}/job/showDetail?id=${item.id}">${item.jobName}</a></td>
                <td>
                <c:choose>
                	<c:when test="${item.status == 1}">运行中</c:when>
                	<c:otherwise>停止中</c:otherwise>
                </c:choose>
                </td>
                <td>${item.cronExpression}</td>
                <td>${item.description}</td>
                <td>${item.username}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/job/update?id=${item.id}"><button class="btn btn-info">修改</button></a>
                    <c:if test="${item.status == '1'}">
                    <a href="javascript:stop(${item.id});"><button class="btn btn-warning">停止</button></a>
                    </c:if>
                    <c:if test="${item.status == '0'}">
                    <a href="javascript:start(${item.id});"><button class="btn btn-success">启动</button></a>
                    </c:if>
                    <c:if test="${item.isHide == '0'}">
                    <a href="javascript:hide(${item.id});"><button class="btn btn-danger">删除</button></a>
                    </c:if>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
    <div style="display:none" id="j_end">end</div>
    </c:otherwise></c:choose>
</div>
</body>
</html>
