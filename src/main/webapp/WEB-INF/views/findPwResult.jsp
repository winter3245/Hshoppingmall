<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 조회 결과</title>
<script src="/shoppingmall/resources/js/jquery/jquery-2.2.4.min.js"></script>
<script src="/shoppingmall/resources/js/popper.min.js"></script>
<script src="/shoppingmall/resources/js/bootstrap.min.js"></script>
<script src="/shoppingmall/resources/js/plugins.js"></script>
<script src="/shoppingmall/resources/js/active.js"></script>
<link rel="stylesheet" href="/shoppingmall/resources/css/core-style.css">
<link rel="stylesheet" href="/shoppingmall/resources/style.css">
</head>
<body>
	<jsp:include page = "./common/common.jsp" flush = "true"/>
  <c:if test = "${not empty findUser}">
	<div style = "margin-top:40px;">
	  <div class = "container" align = "center">
		 <div class="jumbotron" style="padding-top: 20px;">
		<span>비밀번호</span><input class = "form-control" type = "password" id = "pw"><br>
		<form action = "/shoppingmall/repassword" method = "post" onsubmit = "return auth();">
			<span>비밀번호 확인</span><input class = "form-control" type = "password" id = "repassword" name = "repw"><br>
			<input type = "hidden" value = "${findUser.id}" name = "userId">
			<input class = "btn btn-primary" type = "submit" value = "비밀번호 변경">
		</form>
		</div>
	  </div>
  	</div>
  </c:if>
  
	<script>
		function auth()
		{
			if($("#pw").val() == $("#repassword").val())
				return true;
			else{
				alert('비밀번호 확인을 다시 하십시오.');
				return false;
			}
		}
	</script>
</body>
</html>