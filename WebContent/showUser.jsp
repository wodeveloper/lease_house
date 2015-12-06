<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示用户</title>
</head>
<body> 
<%
	List userList=(List)request.getAttribute("list");
	for(int i=0;i<userList.size();i++){
		HashMap userMap=(HashMap)userList.get(i);
		%>
		id:<%=userMap.get("ID") %>
		username:<%=userMap.get("USER_NAME") %></br>
		create_date:<%=userMap.get("CREATE_DATE") %></br>
		<%
	}
%> 
    <a href="login.do?pageIndex=${pageIndex-1}">上一页</a><br>  
   <a href="login.do?pageIndex=${pageIndex+1}">下一页</a>
    <font color="red">欢迎登陆！！！</font><br>  
</body>  
</html>