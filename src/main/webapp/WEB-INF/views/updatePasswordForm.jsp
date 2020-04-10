<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
<script src="/shoppingmall/resources/js/jquery/jquery-2.2.4.min.js"></script>
<script src="/shoppingmall/resources/js/popper.min.js"></script>
<script src="/shoppingmall/resources/js/bootstrap.min.js"></script>
<script src="/shoppingmall/resources/js/plugins.js"></script>
<script src="/shoppingmall/resources/js/active.js"></script>
<link rel="stylesheet" href="/shoppingmall/resources/css/core-style.css">
<link rel="stylesheet" href="/shoppingmall/resources/style.css">
<style>
	#span
	{
		color:red;
	}
</style>
</head>
<body>
<br>
 <jsp:include page = "./common/common.jsp" flush = "true"/>
 <div class ="container jumbotron" align = "center" style = "width:40%;">
	<h2>변경하실 비밀번호를 입력해주세요.</h2>
  <form action = "/shoppingmall/updatePassword" method = "post" onsubmit = "return check()">
	현재 비밀번호: <input class="form-control" type = "password" name = "nowPassword"><br>
	<c:if test = "${not empty error}">
		<span id="span">${error}</span><br><br>
	</c:if>
	새 비밀번호: <input class="form-control" type = "password" name = "password" id = "pw"><br>
	새 비밀번호 확인: <input class="form-control" type = "password" name = "passwordcheck" id = "pwc"><br>
	<input type = "hidden" value = "${sessionScope.Userid}" name = "Userid">
	<div style = "text-align:center;">
	<input class = "btn amado-btn" type = "submit" value = "비밀번호 변경">
  	</div>
  </form>
 </div> 	
	<script>
	  function check()
	  {
		var pw = document.getElementById("pw").value;
		var pwc = document.getElementById("pwc").value;
		
		if(pw != pwc)
		{
			alert("비밀번호 확인이 틀렸습니다");
			return false;
		}
		else
			return true;
	  }
	</script>
</body>
</html>