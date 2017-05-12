<%@ page isELIgnored="false"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="/js/jquery-1.5.2.min.js"></script>
<script type="text/javascript">
	var path = "${pageContext.request.contextPath}";
	$(document).ready(function() {
		$("#j_job_submit").click(function() {
			$.ajax({
				url : path+"/job/save",
				type : "POST",
				async:false,
				data : $('#j_job_form').serialize(),
				error : function() {
				},
				
				success : function(resp) {
					resp = $.parseJSON(resp);
					alert(resp.message);
					if (resp.success) {
						var url = path+"/job/list?userId="+$("#userId").val();
						window.location.href = url;
					}
				}
			});
		});
	});
</script>
<head>
    <title>添加/修改任务</title>
</head>
<body style="align:center;text-align:center;">
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
	    <div class="navbar-header">
	        <a class="navbar-brand" href="/job/list?userId=${userId}">作业发布系统</a>
	    </div>
	    <ul class="nav navbar-nav navbar-right">
	    	<li><a href="/welcome/logout">注销</a></li>
	    </ul>
    </div>
</nav>

<div class="col-md-2 sidebar-offcanvas">
<div class="list-group">
	<ul class="nav nav-pills nav-stacked">
	    <a href="/job/list?userId=${userId}" class="list-group-item">作业列表</a>
	    <a class="list-group-item active" href="#">添加/修改作业</a>
	    <a class="list-group-item" href="${pageContext.request.contextPath}/user/edit?userId=${userId}">修改人员信息</a>
	</ul>
	</div>
</div>

<div class="col-md-6 sidebar-offcanvas">
	<form class="form-horizontal" id="j_job_form">
		<input type="hidden" id="userId" name="userId" value="${userId }"/>
        <input type="hidden" name="jobId" value="${job.id }" />
        
        <div class="form-group">
    		<label for="job_class" class="col-sm-2 control-label">job_class</label>
    		<div class="col-sm-7">
      			<input type="text" class="form-control" id="jobClass" name="jobClass" value="${job.jobClass}" placeholder="作业类名" />
    		</div>
  		</div>
  		
  		<div class="form-group">
    		<label for="job_method" class="col-sm-2 control-label">job_method</label>
    		<div class="col-sm-7">
      			<input type="text" class="form-control" id="jobMethod" name="jobMethod" value="${job.jobMethod}" placeholder="作业方法的名称">
    		</div>
  		</div>
  		
  		<div class="form-group">
    		<label for="job_arguments" class="col-sm-2 control-label">job_arguments</label>
    		<div class="col-sm-7">
      			<textarea rows="10" class="form-control" id="jobArguments" name="jobArguments" placeholder="作业的参数，如果有多个参数请用#&分开"><c:if test="${not empty job.jobArguments }">${job.jobArguments}</c:if></textarea>
    		</div>
  		</div>
  		
  		<div class="form-group">
    		<label for="job_name" class="col-sm-2 control-label">job_name</label>
    		<div class="col-sm-7">
      			<input type="text" class="form-control" id="jobName" name="jobName" value="${job.jobName}" placeholder="作业的名称">
    		</div>
  		</div>
  		
  		<div class="form-group">
    		<label for="job_class" class="col-sm-2 control-label">cronExpression</label>
    		<div class="col-sm-7">
      			<input type="text" class="form-control" id="cronExpression" name="cronExpression" value="${job.cronExpression}" placeholder="作业cron表达式">
    		</div>
  		</div>
  		
  		<div class="form-group">
    		<label for="job_class" class="col-sm-2 control-label">description</label>
    		<div class="col-sm-7">
      			<input type="text" class="form-control" id="description" name="description" value="${job.description}" placeholder="作业描述">
    		</div>
  		</div>
  		
	</form>
	<button id="j_job_submit" class="btn btn-default">提交</button> 
	</div>
    <div class="col-md-4" style="text-align:left">
        <p>
            需要修改的一般仅限于<strong>job_arguments</strong>和<strong>cron_expression</strong>
        </p>
		<p>
			<strong>cron_expression</strong>是用来指定何时执行任务。 <br>
			一个cron表达式有至少6个（也可能是7个）由空格分隔的时间元素。从左至右，这些元素的定义如下： <br> 
			1．秒（0–59） <br> 
			2．分钟（0–59） <br> 
			3．小时（0–23） <br> 
			4．月份中的日期（1–31） <br>
			5．月份（1–12或JAN–DEC） <br> 
			6．星期中的日期（1–7或SUN–SAT） <br>
			7．年份（1970–2099）
		</p>
		<table width="100%" cellspacing="1" cellpadding="1" border="1">
			<tbody>
				<tr>
					<td>表 达 式</td>
					<td>意 义</td>
				</tr>
				<tr>
					<td>0 0/10 * * * ?</td>
					<td>每隔10分钟一次</td>
				</tr>
				<tr>
					<td>0 0 10,14,16 * * ?</td>
					<td>每天上午10点，下午2点和下午4点</td>
				</tr>
				<tr>
					<td>0 0,15,30,45 * 1-10 * ?</td>
					<td>每月前10天每隔15分钟</td>
				</tr>
				<tr>
					<td>30 0 0 1 1 ? 2012</td>
					<td>在2012年1月1日午夜过30秒时</td>
				</tr>
				<tr>
					<td>0 0 8-5 ? * MON-FRI</td>
					<td>每个工作日的工作时间</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>
