<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 조회 결과</title>
<script src="/shoppingmall/resources/js/jquery/jquery-2.2.4.min.js"></script>
<script src="/shoppingmall/resources/js/popper.min.js"></script>
<script src="/shoppingmall/resources/js/bootstrap.min.js"></script>
<script src="/shoppingmall/resources/js/plugins.js"></script>
<script src="/shoppingmall/resources/js/active.js"></script>
<link rel="stylesheet" href="/shoppingmall/resources/css/core-style.css">
<link rel="stylesheet" href="/shoppingmall/resources/style.css">
</head>
<body>
 <ul>
 	<li>
 	<li>
 </ul>
 	<jsp:include page = "./common/common.jsp" flush = "true"/>
	<c:if test = "${not empty findUser}">
		<div class = "container"  align = "center">
		  <div class="jumbotron" style="padding-top: 20px;width:500px;">
			<h2>회원님의 아이디</h2> 
			<br>
			<span style = "color:black;font-weight:bold;font-size:30px;">${findUser.id}</span>
			<br>
		  	<a href = "/shoppingmall/home" class = "btn btn-primary">홈페이지 돌아가기</a>
		  </div>
		</div>
	</c:if>
	
	<c:if test = "${empty findUser}">
	  <div class = "container">
	    <div class="jumbotron" style="padding-top: 20px;width:700px;">
			<h1>이름 또는 핸드폰 번호를 다시 확인하여 주십시오.</h1>
			<a href = "/shoppingmall/home" class = "btn btn-primary">홈페이지 돌아가기</a>
	  	</div>
	  </div>
	</c:if>
</body>
</html>