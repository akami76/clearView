<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Simple</title>
</head>
<style>
.table4_2 table {
	width:100%;
	margin:15px 0;
		border:0;
}
.table4_2 th {
	background-color:#ACF3FF;
	color:#000000
}
.table4_2,.table4_2 th,.table4_2 td {
	font-size:0.95em;
	text-align:center;
	padding:4px;
	border-collapse:collapse;
}
.table4_2 th,.table4_2 td {
	border: 1px solid #cff8fe;
	border-width:1px 0 1px 0
}
.table4_2 tr {
	border: 1px solid #cff8fe;
}
.table4_2 tr:nth-child(odd){
	background-color:#e3fbfe;
}
.table4_2 tr:nth-child(even){
	background-color:#fdfdfd;
}
</style>
<body>
	<table class="table4_2">
	
		<tr>
			<th>seq</th>
			<th>name</th>
			<th>regdate</th>
		</tr>
	<c:forEach items="${list}" var="simpleVO">

		<tr>
			<td>${simpleVO.seq}</td>
			<td>${simpleVO.name}</td>
			<td>${simpleVO.regdate}</td>
		</tr>
	</c:forEach>
	</table>
</body>
</html>

