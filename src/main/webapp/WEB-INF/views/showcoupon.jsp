<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쿠폰 내역</title>
</head>
<body>
	<jsp:include page = "./common/common.jsp" flush = "true"/>
 <div class = "container">
	<table class = "table table-bordered" style = "text-align:center;">
		<tr>
			<th>쿠폰 아이디</th>
			<th>쿠폰 종류</th>
			<th>사용자 아이디</th>
			<th>사용 여부</th>
		</tr>
	 <c:if test = "${empty list}">
	 	<tr>
	 		<td colspan = "4">쿠폰이 없습니다.</td>
	 	</tr>
	 </c:if>
	 <c:if test = "${not empty list}">
		<c:forEach items = "${list}" var = "coupon">
			<tr>
				<td>${coupon.id}</td>
			  <c:if test = "${coupon.type eq 0}">
				<td>5% 할인 쿠폰</td>
			  </c:if>
			  <c:if test = "${coupon.type eq 1}">
				<td>10% 할인 쿠폰</td>
			  </c:if>
			  <c:if test = "${coupon.type eq 2}">
				<td>15% 할인 쿠폰</td>
			  </c:if>
			  <c:if test = "${coupon.type eq 3}">
				<td>20% 할인 쿠폰</td>
			  </c:if>
			  	<td>${sessionScope.Userid}</td>
			  <c:if test = "${coupon.usecheck eq 'no'}">
			  	<td>사용 가능</td>
			  </c:if>
			  <c:if test = "${coupon.usecheck eq 'yes'}">
			  	<td>사용함</td>
			  </c:if>
			</tr>
		</c:forEach>
	</c:if>
	</table>
 
  				<nav aria-label="navigation">
                            <ul class="pagination justify-content-end mt-50">
    							<c:if test="${ curPageNum > 5 }">
            						<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/showcoupon?page=1">◀◀</a></li>
        						</c:if>
        
        						<c:if test="${ curPageNum > 5 }">
            						<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/showcoupon?page=${ blockStartNum - 1 }">◀</a></li>
        						</c:if>
        
        						<c:forEach var="i" begin="${ blockStartNum }" end="${ blockLastNum }">
            						<c:choose>
            							<c:when test="${ i > lastPageNum }">
            								
            							</c:when>
                						<c:when test="${ i == curPageNum }">
                    						<li class="page-item active" style = "color:black;">${ i }</li>
                						</c:when>
                						<c:otherwise>
                    						<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/showcoupon?page=${ i }">${ i }</a></li>
                						</c:otherwise>
            						</c:choose>
            						&nbsp;&nbsp;
        						</c:forEach>
 
        						<c:if test="${ lastPageNum > blockLastNum }">
            						<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/showcoupon?page=${ blockLastNum + 1 }>">▶</a></li>
        						</c:if>
        						<c:if test = "${lastPageNum > 5}">
        							<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/showcoupon?page=${lastPageNum}">▶▶</a></li>
        						</c:if>
    						</ul>
                     </nav>
    </div>       
    <script>
	if("${sessionScope.Userid}" != "" && localStorage.getItem("Userid") == null)	//로그인 상태 + 스토리지에 아이디 없음
	{
		localStorage.setItem("Userid","${sessionScope.Userid}");
	}
	else if("${sessionScope.Userid}" == "" && localStorage.getItem("Userid") != null)  //로그인 상태 x + 스토리지에 아이디 남아 있음
	{
		localStorage.removeItem("Userid");
	}
    </script>
</body>
</html>