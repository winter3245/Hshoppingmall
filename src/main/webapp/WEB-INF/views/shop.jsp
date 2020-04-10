<%@ page import = "java.util.*,spring.myapp.shoppingmall.dto.goods" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	List<goods> list = (List<goods>)request.getAttribute("list");
	int CartSize = list.size();
	String kind = null;
	if(list.size() != 0)
		kind = list.get(0).getKind();
	else 
		kind = (String)request.getAttribute("kind");
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!-- Title  -->
    <title>상품</title>

    <!-- Favicon  -->
    <link rel="icon" href="/shoppingmall/resources/img/core-img/favicon.ico">

    <!-- Core Style CSS -->
    <link rel="stylesheet" href="/shoppingmall/resources/css/core-style.css">
    <link rel="stylesheet" href="/shoppingmall/resources/style.css">

</head>

<body>
    <!-- Search Wrapper Area Start -->
    <div class="search-wrapper section-padding-100">
        <div class="search-close">
            <i class="fa fa-close" aria-hidden="true"></i>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="search-content">
                       <form action="/shoppingmall/search" method="get">
                            <input type="search" name="search" id="search" placeholder="찾고자 하는 물품을 입력하세요." style = "font-weight:bold;font-size:100%;">
                            <button type="submit"><img src="/shoppingmall/resources/img/core-img/search.png" alt=""></button>
                      </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Search Wrapper Area End -->

    <!-- ##### Main Content Wrapper Start ##### -->
    <div class="main-content-wrapper d-flex clearfix">

        <!-- Mobile Nav (max width 767px)-->
        <div class="mobile-nav">
            <!-- Navbar Brand -->
            <div class="amado-navbar-brand">
                <a href="/shoppingmall/home"><img src="/shoppingmall/resources/img/core-img/logo.png" alt=""></a>
            </div>
            <!-- Navbar Toggler -->
            <div class="amado-navbar-toggler">
                <span></span><span></span><span></span>
            </div>
        </div>

        <!-- Header Area Start -->
        <header class="header-area clearfix">
            <!-- Close Icon -->
            <div class="nav-close">
                <i class="fa fa-close" aria-hidden="true"></i>
            </div>
            <!-- Logo -->
            <div class="logo">
                <a href="/shoppingmall/home"><img src="/shoppingmall/resources/img/core-img/logo.png" alt=""></a>
                <br>
                <br>
                <c:if test = "${empty sessionScope.Userid}">
              		<a href = "/shoppingmall/loginForm" class = "btn amado-btn">로그인</a><br><br>
		    	</c:if>
		    	<c:if test = "${empty sessionScope.Userid}">
              		<a href = "/shoppingmall/joinform" class = "btn amado-btn">회원가입</a><br><br>
		    	</c:if>
		    	<c:if test = "${empty sessionScope.Userid}">
		    		<a href = "/shoppingmall/findId" class = "btn amado-btn">아이디 찾기</a><br><br>
		    	</c:if> 
		    	<c:if test = "${empty sessionScope.Userid}">
		    		<a href = "/shoppingmall/findPassword" class = "btn amado-btn">비밀번호 찾기</a>
		    	</c:if>
		   <c:if test = "${not empty sessionScope.Userid}">
		    	<div class = "jumbotron" align = "center" style = "width : 160px;">
              	   <span>안녕하세요 <span style = "font-weight:bold;">${sessionScope.Userid}</span>님!</span><br>
              	   <span>오늘도 즐거운 쇼핑하세요!</span>
              	   <br>
			    </div>
				 <div>
					<a href = "/shoppingmall/updatePasswordForm" class = "btn amado-btn">비밀번호 변경</a>
			     </div>
			 	 <div>
			 	 	<br>
					<button onclick = "makecoupon('${sessionScope.Userid}')" class = "btn amado-btn">쿠폰 받기</button>
			     </div>
			    </c:if>
		    	
            </div>
            <!-- Amado Nav -->
            <nav class="amado-nav">
                <ul>
                    <li><a href="/shoppingmall/home">홈페이지</a></li>
                    <li class="active"><a href="/shoppingmall/shop">상품</a></li>
                    <li><a href="/shoppingmall/product">상품 상세보기</a></li>
                     <c:if test = "${not empty sessionScope.Userid }">
                            <li class="nav-item">
                                      <a class="nav-link" href="/shoppingmall/shoppingbasket">장바구니</a>
                            </li>
                    </c:if>
                    <c:if test = "${not empty sessionScope.Userid }">
						<li><a href="/shoppingmall/showorder">주문 정보</a></li>
					</c:if>
					 <c:if test = "${not empty sessionScope.Userid }">
                    	<li><a href="/shoppingmall/showcoupon">쿠폰 내역</a></li>
                	</c:if>
					<c:if test = "${sessionScope.Userid eq 'admin'}">
						<li><a href="/shoppingmall/admin/adminrefund">고객 환불</a>
					</c:if>
					<c:if test = "${sessionScope.Userid eq 'admin'}">
						<li><a href="/shoppingmall/admin/registerForm">상품 등록</a>
					</c:if>
                     <c:if test ="${not empty sessionScope.Userid}">
                    	<li><a href="/shoppingmall/logout">로그아웃</a></li>
                	</c:if>
                </ul>
            </nav>
            <!-- Button Group -->
            <!--  
            <div class="amado-btn-group mt-30 mb-100">
                <a href="#" class="btn amado-btn mb-15">%Discount%</a>
                <a href="#" class="btn amado-btn active">New this week</a>
            </div>
            -->
            <!-- Cart Menu -->
            <div class="cart-fav-search mb-100">
              <c:if test = "${not empty sessionScope.Userid }">
                <a href="/shoppingmall/showbasket" class="cart-nav"><img src="/shoppingmall/resources/img/core-img/cart.png" alt="">장바구니</a>
              </c:if>
                <a href="#" class="search-nav"><img src="/shoppingmall/resources/img/core-img/search.png" alt="">상품 검색</a>
            </div>
            <!-- Social Button -->
            <div class="social-info d-flex justify-content-between">
                <a href="#"><i class="fa fa-pinterest" aria-hidden="true"></i></a>
                <a href="#"><i class="fa fa-instagram" aria-hidden="true"></i></a>
                <a href="#"><i class="fa fa-facebook" aria-hidden="true"></i></a>
                <a href="#"><i class="fa fa-twitter" aria-hidden="true"></i></a>
            </div>
        </header>
        <!-- Header Area End -->

        <div class="shop_sidebar_area">

            <!-- ##### Single Widget ##### -->
            <div class="widget catagory mb-50">
                <!-- Widget Title -->
                <h6 class="widget-title mb-30">상품 종류</h6>

                <!--  Catagories  -->
                <div class="catagories-menu">
                    <ul>
					<% if(kind.equals("chair")) { %>                   
                        <li class ="active"><a href="/shoppingmall/shop">의자</a></li>
                    <% } 
					else 
					{ %>
                    	<li><a href = "/shoppingmall/shop">의자</a></li>
                     <% 
                    } 
                     %>
                     <% if(kind.equals("shoes")) {%>
                        <li class = "active"><a href="/shoppingmall/shop?kind=shoes">신발</a></li>
                    <% }
                    else
                    { %>
                        <li><a href = "/shoppingmall/shop?kind=shoes">신발</a></li>
                    <% 
                    } 
                    %>
                    <% if(kind.equals("game")) { %>                   
                        <li class ="active"><a href="/shoppingmall/shop?kind=game">게임</a></li>
                    <% } 
					else 
					{ %>
                    	<li><a href = "/shoppingmall/shop?kind=game">게임</a></li>
                     <% 
                    } 
                     %>
                      <% if(kind.equals("plantpot")) { %>                   
                        <li class ="active"><a href="/shoppingmall/shop?kind=plantpot">화분</a></li>
                    <% } 
					else 
					{ %>
                    	<li><a href = "/shoppingmall/shop?kind=plantpot">화분</a></li>
                     <% 
                    } 
                     %>
                      <% if(kind.equals("homedeco")) { %>                   
                        <li class ="active"><a href="/shoppingmall/shop?kind=homedeco">장식품</a></li>
                    <% } 
					else 
					{ %>
                    	<li><a href = "/shoppingmall/shop?kind=homedeco">장식품</a></li>
                     <% 
                    } 
                     %>
                      <% if(kind.equals("book")) { %>                   
                        <li class ="active"><a href="/shoppingmall/shop?kind=book">책</a></li>
                    <% } 
					else 
					{ %>
                    	<li><a href = "/shoppingmall/shop?kind=book">책</a></li>
                     <% 
                    } 
                     %>
                      <% if(kind.equals("smalltable")) { %>                   
                        <li class ="active"><a href="/shoppingmall/shop?kind=smalltable">책상</a></li>
                      <% } 
					else 
					{ %>
                    	<li><a href = "/shoppingmall/shop?kind=smalltable">책상</a></li>
                     <% 
                    } 
                     %>
                     
                      <% if(kind.equals("computer")) { %>                   
                        <li class ="active"><a href="/shoppingmall/shop?kind=computer">컴퓨터</a></li>
                      <% } 
					else 
					{ %>
                    	<li><a href = "/shoppingmall/shop?kind=computer">컴퓨터</a></li>
                     <% 
                    } 
                     %>
                    </ul>
                </div>
            </div>
        </div>

        <div class="amado_product_area section-padding-100">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">
                        <div class="product-topbar d-xl-flex align-items-end justify-content-between">
                            
                            <!-- 
                            <div class="total-products">
                                <p>Showing 1-8 0f 25</p>
                                <div class="view d-flex">
                                    <a href="#"><i class="fa fa-th-large" aria-hidden="true"></i></a>
                                    <a href="#"><i class="fa fa-bars" aria-hidden="true"></i></a>
                                </div>
                            </div>
                            <div class="product-sorting d-flex">
                                <div class="sort-by-date d-flex align-items-center mr-15">
                                    <p>Sort by</p>
                                    <form action="#" method="get">
                                        <select name="select" id="sortBydate">
                                            <option value="value">Date</option>
                                            <option value="value">Newest</option>
                                            <option value="value">Popular</option>
                                        </select>
                                    </form>
                                </div>
                                <div class="view-product d-flex align-items-center">
                                    <p>View</p>
                                    <form action="#" method="get">
                                        <select name="select" id="viewProduct">
                                            <option value="value">12</option>
                                            <option value="value">24</option>
                                            <option value="value">48</option>
                                            <option value="value">96</option>
                                        </select>
                                    </form>
                                </div>
                            </div>
                             -->
                        </div>
                    </div>
                </div>
					
					
             <div class="row">
             	<c:if test = "${empty list}">
             	 <div class = "container" style = "margin:60px; text-align:center;">
             		<span style = "font-weight:bold;">등록된 물품이 없습니다.</span>
             	</div>
             	</c:if>
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
                                    <p class="product-price"><span style = "color:black;">정가</span> ${dto.price}원</p>
                                    <a href="/shoppingmall/product?goods_id=${dto.id}">
                                        <h5>${dto.name}</h5>
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
                                    <div class="cart">
                                        <a href="/shoppingmall/shoppingbasket?goods_id=${dto.id}" data-toggle="tooltip" data-placement="left" title="Add to Cart"></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
			</c:forEach>
		</div>
	
			<div class="row">
                    <div class="col-12">
                        <nav aria-label="navigation">
                            <ul class="pagination justify-content-end mt-50">
    							<c:if test="${ curPageNum > 5 }"> <!-- 6 -->
            						<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/shop?page=1&kind=<%=kind%>">◀◀</a></li>
        						</c:if>
        
        						<c:if test="${ curPageNum > 5 }">
            						<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/shop?page=${ blockStartNum - 1 }&kind=<%=kind%>">◀</a></li>
        						</c:if>
        
        						<c:forEach var="i" begin="${ blockStartNum }" end="${ blockLastNum }">
            						<c:choose>
            							<c:when test="${ i > lastPageNum }">
            								
            							</c:when>
                						<c:when test="${ i == curPageNum }">
                    						<li class="page-item active" style = "color:black;">${ i }</li>
                						</c:when>
                						<c:otherwise>
                    						<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/shop?page=${ i }&kind=<%=kind%>">${ i }</a></li>
                						</c:otherwise>
            						</c:choose>
            						&nbsp;&nbsp;
        						</c:forEach>
 
        						<c:if test="${ lastPageNum > blockLastNum }">
            						<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/shop?page=${ blockLastNum + 1 }&kind=<%=kind%>">▶</a></li>
        						</c:if>
        						<c:if test = "${lastPageNum > 5}">
        							<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/shop?page=${lastPageNum}&kind=<%=kind%>">▶▶</a></li>
        						</c:if>
    						</ul>
                        </nav>
                   </div>
             </div>
        </div>
    </div>

    <!-- ##### jQuery (Necessary for All JavaScript Plugins) ##### -->
    <script src="/shoppingmall/resources/js/jquery/jquery-2.2.4.min.js"></script>
    <!-- Popper js -->
    <script src="/shoppingmall/resources/js/popper.min.js"></script>
    <!-- Bootstrap js -->
    <script src="/shoppingmall/resources/js/bootstrap.min.js"></script>
    <!-- Plugins js -->
    <script src="/shoppingmall/resources/js/plugins.js"></script>
    <!-- Active js -->
    <script src="/shoppingmall/resources/js/active.js"></script>
    <script type = "text/javascript">
    <script>
	if("${sessionScope.Userid}" != "" && localStorage.getItem("Userid") == null)	
	{
		localStorage.setItem("Userid","${sessionScope.Userid}");
	}
	else if("${sessionScope.Userid}" == "" && localStorage.getItem("Userid") != null)
	{
		localStorage.removeItem("Userid");
	}
</script>
<script>
function makecoupon(Id)
{
	$.ajax({
		url : "/shoppingmall/makecoupon",
		data : {"Id" : Id},
		method : "POST",
		success : function(data){
			if(data == 0)
			{
				alert('받을 수 있는 쿠폰이 없습니다.');
			}
			else if(data == 1)
			{
				alert('쿠폰을 모두 받으셨습니다.');
			}
		},
		error : function(err){
			alert("에러 발생");
		}
	});
}
</script>
   </body>
</html>