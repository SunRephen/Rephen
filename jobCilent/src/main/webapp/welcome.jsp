<%@ page isELIgnored="false"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<%@ page contentType="text/html; charset=UTF-8"%>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="登录">
    <title>登录</title>

    <!-- Bootstrap core CSS -->
	<!-- <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"> -->
	<link href="/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <style>
	body {
	  padding-top: 40px;
	  padding-bottom: 40px;
	  background-color: #eee;
	}
	
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

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="#{getContextPath}bootstrap/assets/js/html5shiv.js"></script>
      <script src="#{getContextPath}bootstrap/assets/js/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <div class="container">
	<c:if test="${not empty loginInfo}">
		<div style="width:50%;margin: 0 auto;" class="alert alert-info fade in">
	        <strong>${loginInfo}
	    </div>
	</c:if>
      <form method="post" action="/welcome/login" class="form-signin">
        <h2 class="form-signin-heading">请登录</h2>
        <input type="text" name="username" class="form-control" placeholder="用户名" autofocus>
        <input type="password" name="password" class="form-control" placeholder="密 &nbsp; &nbsp;码">

        <button class="btn btn-lg btn-primary btn-block" type="submit">登 &nbsp;&nbsp;&nbsp;录</button>
      </form>

    </div> <!-- /container -->
    
    <br/><br/><br/><br/><br/>
    <script type="text/javascript" src="/js/jquery-1.5.2.min.js"></script>
  </body>
</html>