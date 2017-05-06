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
    <title>修改人员</title>
</head>
<body style="align:center;text-align:center;">
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
    <div class="navbar-header">
        <a class="navbar-brand" href="/job/list">作业发布系统</a>
    </div>
    <div>
        <ul class="nav navbar-nav">
            <li><a href="${pageContext.request.contextPath}/job/list?userId=${user.id}">作业列表</a></li>
            <li><a href="${pageContext.request.contextPath}/job/addOrEdit?userId=${user.id}">添加/修改作业实例</a></li>
            <li class="active"><a href="#">修改人员信息</a></li>
            <li><a href="${pageContext.request.contextPath}/welcome/logout">注销</a></li>
        </ul>
    </div>
    </div>
</nav>
	<form action="${pageContext.request.contextPath}/user/save" method="post" class="form-signin">
		<input type="hidden" name="userId" value="${user.id}"/>
        <table align="center" cellpadding="0" cellspacing="0" style="border:0;">
            <tr>
                <td>
                    username:
                </td>
                <td>
                    <input class="form-control" <c:if test="${not empty user }"> readonly="true" </c:if> type="text" name="username" value="${user.username}" />
                </td>
            </tr>
            <tr>
                <td>
                    password:
                </td>
                <td>
                    <input class="form-control" type="password" name="password" value="${user.password}" />
                </td>
            </tr>
           <tr>
                <td>
                    mail_address:
                </td>
                <td>
                     <input class="form-control" type="text" name="mailAddress" value="${user.mailAddress}"/>
                </td>
            </tr>
        </table>
		<button class="btn btn-lg btn-primary btn-block" type="submit">提交</button>
	</form>
    
</body>
</html>
