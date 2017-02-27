<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Student-Add </title>
</head>
<body>
<%@include file="/WEB-INF/common/header.jsp" %>
<h2>添加学生</h2>
	<form action="${ctx }/student/student-update" method ="post">
		<input type="hidden" name="id" value="${student.id }"/>
		<label>学生姓名：<input type="text" name="name" id="name" value="${student.name }"/></label><br/>
		<label>学生年龄：<input type="text" name="age" id="age" value="${student.age }"/></label><br/>
		<label>学生Email：<input type="text" name="email" id="email" value="${student.email }"/></label><br/>
		<input type="submit" value="提交"/>
		<input type="reset" value="重置"/>
	</form>
	<br/>
	<a href="${ctx }/student/student-list">学生一览</a>
</body>
</html>