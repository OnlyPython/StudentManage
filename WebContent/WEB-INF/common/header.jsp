<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div>
	欢迎，${sessionScope.user.realName } &nbsp;&nbsp;&nbsp;&nbsp;
	<a href = "${ctx }/user/logout">退出登录</a>
</div>
<br/>