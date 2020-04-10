<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<style>
	span {
		color:red
	}
</style>
<title>로그인</title>
</head>
<body>
   <jsp:include page = "./common/common.jsp" flush = "true"/>
   <div class="container" align="center">
    <div>
      <form class="form-signin jumbotron" method="post" action="/shoppingmall/login" style ="text-align:center;width:600px;">
        <h2 style = "margin-bottom:10px;font-weight:bold;">로그인</h2>
        <p>
          <label for="username" class="sr-only">아이디</label>
        <c:if test = "${empty User.id}">
          <input style = "font-size:100%;" type="text" id="username" name="id" class="form-control" placeholder="아이디를 입력해주세요" required autofocus>
        </c:if>
        <c:if test = "${not empty User.id}">
          <input style = "font-size:100%;" type="text" id="username" name="id" class="form-control" placeholder="아이디를 입력해주세요" value = "${User.id}" required autofocus>
        </c:if>
        </p>
        <p>
          <label for="password" class="sr-only">비밀번호</label>
          <input style = "font-size:100%;" type="password" id="password" name="password" class="form-control" placeholder="비밀번호를 입력해주세요" required>
            <div style = "text-align:left;font-size:18px;">
				<input type = "checkbox" name = "isRemember" id = "remember" onclick = "func()"/>&nbsp; 아이디 기억하기
			</div>
           <c:if test = "${not empty error}">
          	 <span>${error}</span>
           </c:if>
           <c:if test = "${not empty logout}">
          	 <span>${logout}</span>
           </c:if>
        </p>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn amado-btn" type="submit">로그인</button>
      </form>
    </div>
</div>
<script>
	if("${sessionScope.Userid}" == "" && localStorage.getItem("Userid") != null)
	{
		localStorage.removeItem("Userid");
	}
</script>
<script>
	if("${duplsession}" != "") 
	{
		alert("${duplsession}");
	}
</script>
  </body>
</html>