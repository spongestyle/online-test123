<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<!-- empMenu -->
	<c:import url="/WEB-INF/view/employee/inc/empMenu.jsp"></c:import>
	
	<h1>Student List</h1>
	
	<a href="${pageContext.request.contextPath}/student/addStudent">학생등록</a>
	
	<table border="1">
		<tr>
			<th>studentId</th>
			<th>studentName</th>
			<th>삭제</th>
		</tr>
		
		<c:forEach var="s" items="${list}">
			<tr>
				<td>${s.studentId}</td>
				<td>${s.studentName}</td>
				<td><a href="${pageContext.request.contextPath}/student/removeStudent?studentNo=${s.studentNo}">삭제</a></td>
			</tr>
		</c:forEach>
	</table>
	
	<div>
		<a href="${pageContext.request.contextPath}/student/studentList?currentPage=${currentPage-1}">이전</a>
		<span>${currentPage}</span>
		<a href="${pageContext.request.contextPath}/student/studentList?currentPage=${currentPage+1}">다음</a>
	</div>
</body>
</html>