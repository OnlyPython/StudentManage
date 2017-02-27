 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<%@ page import="entity.Student" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Student List </title>
<style type="text/css">
	.odd{background-color: AliceBlue;}
	td,th {
    border: 1px solid green;
	}
	table {
	    border-collapse: collapse;
	}
</style>
<script type="text/javascript">
	function gotoPage(i){
		var fms = document.forms[0];
		fms.currentPage.value = i;
		fms.submit();
	}
	function changeNumPerPage(i){
		var fms = document.forms[0];
		fms.numPerPage.value = i;
		fms.submit();
	}
	window.onload=function(){
		document.getElementById('currentPage').value = 1;
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/common/header.jsp" %>
	<div>
		<form action="${ctx }/student/student-list" method="get">
			<input type="hidden" id = "currentPage" name="currentPage" value="${page.currentPage }"/> 
			<input type="hidden" id = "numPerPage" name="numPerPage" value="${page.numPerPage }"/> 
			学生姓名：<input type ="text" name="studentName" value="${param.studentName}"/>
			<input type="submit" value="检索" id="sbm"/>
		</form>
	</div>
	<hr/>
	<table>
	<tr>
		<th>No.</th>
		<th>Name</th>
		<th>Age</th>		
		<th>Email</th>
		<th>Update</th>
		<th>Delete</th>
	</tr>
	<%-- <%
	List<Student> studentList = (List<Student>)request.getAttribute("students");
	for(Student stu :studentList) {
	%> 
	<tr>
		<td><%=stu.getId() %></td>
		<td><%=stu.getName() %></td>
		<td><%=stu.getAge() %></td>
		<td><%=stu.getEmail() %></td>
	</tr>
	<%
	}
	%> --%>
	<c:forEach items="${requestScope.page.list }" var = "stu" varStatus="status">
		<tr class="${status.index%2==0 ? 'odd' : '' }">
			<td>${status.count + page.offset }</td>
			<td>${stu.name }</td>
			<td>${stu.age }</td>
			<td>${stu.email }</td>
			<td>
				<form action="${ctx }/student/student-add" method="get" >
					<input type="hidden" name="id" value="${stu.id }">
					<input type="submit" value="修改">
				</form>
			</td>
			<td>
				<form action="${ctx }/student/student-delete" method="post" onsubmit="return confirm('确定删除吗？')">
					<input type="hidden" name="id" value="${stu.id }">
					<input type="submit" value="删除">
				</form>
			</td>
		</tr>
	</c:forEach>
	</table>
<hr/>
	<select onchange="changeNumPerPage(this.value)">
	<option value="5" ${5==page.numPerPage ? 'selected="selected"':'' }>5</option>
	<option value="10" ${10==page.numPerPage ? 'selected="selected"':'' }>10</option>
	<option value="15" ${15==page.numPerPage ? 'selected="selected"':'' }>15</option>
	<option value="20" ${20==page.numPerPage ? 'selected="selected"':'' }>20</option>
	<option value="25" ${25==page.numPerPage ? 'selected="selected"':'' }>25</option>
	</select>
	<c:forEach begin="1" end="${page.totalPageNum }" step ="1" var ="v" varStatus="status">
		<c:choose>
			<c:when test="${v==page.currentPage }">
				${v }
			</c:when>
			<c:otherwise>
				<a href="#" onclick="gotoPage(${v})">${v }</a>
			</c:otherwise>
		</c:choose>
		<c:if test="${!status.last }">|</c:if>
	</c:forEach>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="${ctx }/student/student-add">添加学生</a>
</body>
</html>