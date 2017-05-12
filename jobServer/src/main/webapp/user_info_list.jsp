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
	function deleteUser(id){
		$.get(path + "/user/delete?id=" + id, function(resp){
			resp=$.parseJSON(resp);
			if(resp.message) {
				alert(resp.message);
			}
			location.reload();
		});
	};
	function resetPassword(id){
		$.get(path + "/user/resetPassword?id=" + id, function(resp){
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
        人员管理
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
	    <a href="${pageContext.request.contextPath}/job/list" class="list-group-item">作业列表</a>
	    <a class="list-group-item active" href="#">人员列表</a>
        <a class="list-group-item" href="${pageContext.request.contextPath}/user/addOrEdit">添加/修改人员</a>
	</ul>
	</div>
</div>


<div class="col-md-10 sidebar-offcanvas">
    <table width="100%" class="table-striped" cellpadding="0" cellspacing="0">
        <thead>
            <tr>
                <th style="text-align:center;">id</th>
                <th style="text-align:center;">用户名</th>
                <th style="text-align:center;">邮箱地址</th>
                <th style="text-align:center;">动作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${list}" varStatus="status">
            <tr id="j_id_${item.id }">
                <td>${item.id}</td>
                <td>${item.username}</td>
                <td>${item.mailAddress}</td>
                <td>
                	<a href="/user/addOrEdit?userId=${item.id}"><button class="btn btn-info">修改</button></a>
                	<a href="javascript:resetPassword(${item.id});"><button class="btn btn-warning">重置密码</button></a>
                    <a href="javascript:deleteUser(${item.id});"><button class="btn btn-danger">删除</button></a>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
    <div style="display:none" id="j_end">end</div>
</div>

</body>
</html>
