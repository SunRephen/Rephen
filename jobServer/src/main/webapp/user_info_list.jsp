<%@ page isELIgnored="false"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<style>
table,th,td {
	margin: 0px;
	padding: 0px;
	line-height: 18px;
	color: #000;
	font-size: 12px;
}

.mytable th {
	background: #BCE774;
	text-align: center;
	font-weight: normal;
	width: 150px;
	padding: 6px;
}

.mytable td {
	background: #ECFBD4;
	padding: 3px;
}

.mytable th,.mytable td {
	border-top: 1px solid #e9e9e9;
	border-left: 1px solid #e9e9e9;
	text-align: left;
}

.mytable {
	border-bottom: 1px solid #e9e9e9;
	border-right: 1px solid #e9e9e9;
}

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
    <div>
        <ul class="nav navbar-nav">
            <li><a href="${pageContext.request.contextPath}/job/list">作业列表</a></li>
            <li class="active"><a href="#">人员列表</a></li>
            <li><a href="${pageContext.request.contextPath}/user/addOrEdit">添加/修改人员</a></li>
            <li><a href="${pageContext.request.contextPath}/welcome/logout">注销</a></li>
        </ul>
    </div>
    </div>
</nav>

	<c:choose><c:when test="${not empty message }">
		<table align="center" cellpadding="0" cellspacing="0" style="border:0;">
			<tr><td>${message }</td></tr>
		</table>
	</c:when><c:otherwise>
    <table width="50%" class="mytable" cellpadding="0" cellspacing="0">
        <thead>
            <tr>
                <th>id</th>
                <th>用户名</th>
                <th>邮箱地址</th>
                <th>动作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${list}" varStatus="status">
            <tr id="j_id_${item.id }">
                <td>${item.id}</td>
                <td>${item.username}</td>
                <td>${item.mailAddress}</td>
                <td>
                	<a href="/user/addOrEdit?userId=${item.id}">修改</a>
                	<a href="javascript:resetPassword(${item.id});">重置密码</a>
                    <a href="javascript:deleteUser(${item.id});">删除</a>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
    <div style="display:none" id="j_end">end</div>
    </c:otherwise></c:choose>

</body>
</html>
