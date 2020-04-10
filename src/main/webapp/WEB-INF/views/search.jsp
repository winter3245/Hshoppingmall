<%@ page import = "java.util.*,spring.myapp.shoppingmall.dto.goods" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	List<goods> list = (List<goods>)request.getAttribute("list");
	
	String kind = null;
	if(list.size() != 0)
		kind = list.get(0).getKind();
	else 
		kind = (String)request.getAttribute("kind");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>검색 결과</title>
<meta charset="UTF-8">
    <meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!-- Title  -->
    <title>검색 결과</title>

    <!-- Favicon  -->
    <link rel="icon" href="/shoppingmall/resources/img/core-img/favicon.ico">

    <!-- Core Style CSS -->
    <link rel="stylesheet" href="/shoppingmall/resources/css/core-style.css">
    <link rel="stylesheet" href="/shoppingmall/resources/style.css">
</head>
<body>
		<jsp:include page = "./common/common.jsp" flush = "true"/>
	          <div class="row">
				<c:forEach items = "${list}" var = "dto">                    
                    <div class="col-12 col-sm-6 col-md-12 col-xl-6">
                        <div class="single-product-wrapper">
                            <!-- Product Image -->
                            <div class="product-img">
                                <img src="${dto.goodsprofile}" alt="" style = "height:600px;">
                                <!-- Hover Thumb -->
                            </div>

                            <!-- Product Description -->
                            <div class="product-description d-flex align-items-center justify-content-between">
                                <!-- Product Meta Data -->
                                <div class="product-meta-data">
                                    <div class="line"></div>
                                    <p class="product-price">${dto.price}</p>
                                    <a href="/shoppingmall/product?goods_id=${dto.id}">
                                        <h6>${dto.name}</h6>
                                    </a>
                                </div>
                                <!-- Ratings & Cart -->
                                <div class="ratings-cart text-right">
                                    <div class="ratings">
                                        <i class="fa fa-star" aria-hidden="true"></i>
                                        <i class="fa fa-star" aria-hidden="true"></i>
                                        <i class="fa fa-star" aria-hidden="true"></i>
                                        <i class="fa fa-star" aria-hidden="true"></i>
                                        <i class="fa fa-star" aria-hidden="true"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
			</c:forEach>
	    </div>
	    
				<div class = "row">
                    <div class="col-12">
                        <nav aria-label="navigation">
                            <ul class="pagination justify-content-end mt-50">
    							<c:if test="${ curPageNum > 5 }">
            						<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/search?page=1&kind=<%=kind%>&search='${search}'">◀◀</a></li>
        						</c:if>
        
        						<c:if test="${ curPageNum > 5 }">
            						<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/search?page=${ blockStartNum - 1 }&search='${search}'">◀</a></li>
        						</c:if>
        
        						<c:forEach var="i" begin="${ blockStartNum }" end="${ blockLastNum }">
            						<c:choose>
            							<c:when test="${ i > lastPageNum }">
            								
            							</c:when>
                						<c:when test="${ i == curPageNum }">
                    						<li class="page-item active" style = "color:black;">${ i }</li>
                						</c:when>
                						<c:otherwise>																								
                    						<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/search?page=${ i }&search='${search}'">${ i }</a></li>
                						</c:otherwise>
            						</c:choose>
            						&nbsp;&nbsp;
        						</c:forEach>
 
        						<c:if test="${ lastPageNum > blockLastNum }">
            						<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/search?page=${ blockLastNum + 1 }&search='${search}'">▶</a></li>
        						</c:if>
        						<c:if test = "${lastPageNum > 5}">
        							<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/search?page=${lastPageNum}&search='${search}'">▶▶</a></li>
        						</c:if>
    						</ul>
                        </nav>
                   </div>
              </div>
</body>
</html>