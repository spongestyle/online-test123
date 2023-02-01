<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>사원추가</h1>
	<div>${errorMsg}</div>
	<form method="post" action="${pageContext.request.contextPath}/employee/addEmp">
		<table>
			<tr>
				<th>empId</th>
				<td>
					<input type ="text" name="empId">
				</td>
				<th>empPw</th>
				<td>
					<input type ="text" name="empPw">
				</td>
				<th>empName</th>
				<td>
					<input type ="text" name="empName">
				</td>
			</tr>
		</table>
		<button type ="submit">사원추가</button>
	</form>
</body>
</html>