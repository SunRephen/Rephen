<%@ page isELIgnored="false"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<style>
	.form-signin {
	  max-width: 330px;
	  padding: 15px;
	  margin: 0 auto;
	}
	.form-signin .form-signin-heading,
	.form-signin .checkbox {
	  margin-bottom: 10px;
	}
	.form-signin .checkbox {
	  font-weight: normal;
	}
	.form-signin .form-control {
	  position: relative;
	  font-size: 16px;
	  height: auto;
	  padding: 10px;
	  -webkit-box-sizing: border-box;
	     -moz-box-sizing: border-box;
	          box-sizing: border-box;
	}
	.form-signin .form-control:focus {
	  z-index: 2;
	}
	.form-signin input[type="text"] {
	  margin-bottom: -1px;
	  border-bottom-left-radius: 0;
	  border-bottom-right-radius: 0;
	}
	.form-signin input[type="password"] {
	  margin-bottom: 10px;
	  border-top-left-radius: 0;
	  border-top-right-radius: 0;
	}
	</style>
<style>
.leftTd {
	width: 200px;
	vertical-align: middle;
	text-align: right;
}
td {
	padding: 5px;
}
</style>
<script type="text/javascript" src="/js/jquery-1.5.2.min.js"></script>
<head>
    <title>添加/修改人员</title>
</head>
<body style="align:center;text-align:center;">
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
	    <a class="list-group-item" href="${pageContext.request.contextPath}/user/list">人员列表</a>
        <a class="list-group-item  active" href="#">添加/修改人员</a>
	</ul>
	</div>
</div>

<div class="col-md-2 sidebar-offcanvas"></div>

<div class="col-md-6 sidebar-offcanvas">
	<form class="form-horizontal" action="${pageContext.request.contextPath}/user/save" method="post" class="form-signin">
		<input type="hidden" name="id" value="${user.id}"/>
		
			<div class="form-group">
    			<label for="username" class="col-sm-2 control-label">username</label>
    			<div class="col-sm-7">
    				<input class="form-control" placeholder="用户名" <c:if test="${not empty user }"> readonly="true" </c:if> type="text" name="username" value="${user.username}" />
    			</div>
  			</div>
  			
  			<div class="form-group">
    			<label for="password" class="col-sm-2 control-label">password</label>
    			<div class="col-sm-7">
    				<input class="form-control" placeholder="密码" <c:if test="${not empty user }"> readonly="true" </c:if> type="password" name="password" value="${user.password}" />
    			</div>
  			</div>
  			
  			<div class="form-group">
    			<label for="mail_address" class="col-sm-2 control-label">mailAddress</label>
    			<div class="col-sm-7">
    				<input class="form-control" type="text" name="mailAddress" placeholder="邮箱" value="${user.mailAddress}"/>
    			</div>
  			</div>
  			
			<div class="form-group">
    			<div class="col-sm-offset-4 col-sm-4">
      				<button type="submit" class="btn btn-default">提交</button>
    			</div>
 			</div>
	</form>
</div>
	
	
<div class="col-md-2 sidebar-offcanvas"></div>    
</body>
</html>
