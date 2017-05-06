<%@ page isELIgnored="false"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>错误</title>
</head>
<body>
<c:choose>
	<c:when test="${hasRecentDetail eq 'yes'}">
		该作业最近一天未执行或未成功执行过.<br>
		<a href='http://localhost/job/showHistoryDetail?id=${jobId }'>查看该作业全部历史记录</a>
	</c:when>
	<c:otherwise>
		访问出错，请查看日志
	</c:otherwise>
</c:choose>
</body>
</html>