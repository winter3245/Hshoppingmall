<%@ page import = "java.util.*,spring.myapp.shoppingmall.dto.goods" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!-- Title  -->
    <title>홈페이지</title>
	
    <!-- Favicon  -->
    <link rel="icon" href="img/core-img/favicon.ico">

    <!-- Core Style CSS -->
    <link rel="stylesheet" href="/shoppingmall/resources/css/core-style.css">
    <link rel="stylesheet" href="/shoppingmall/resources/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
	<script>
		document.cookie = 'same-site-cookie=foo; SameSite=Lax';
		document.cookie = 'cross-site-cookie=bar; SameSite=None; Secure';
	</script>
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
              	<br><br>
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
                    <li class="active"><a href="/shoppingmall/home">홈페이지</a></li>
                    <li><a href="/shoppingmall/shop">상품</a></li>
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
					
					<li><a href="/shoppingmall/board/list">문의 게시판</a></li>
					<li><a href="/shoppingmall/resources/jsp/introduce.jsp">회사 소개</a></li>
					<li><a href="/shoppingmall/resources/jsp/question.jsp">자주 묻는 질문</a></li>
                    <c:if test = "${not empty sessionScope.Userid }">
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

        <!-- Product Catagories Area Start -->
        <div class="products-catagories-area clearfix">
            <div class="amado-pro-catagory clearfix">

                <!-- Single Catagory -->
                <div class="single-products-catagory clearfix">
                    <a href="/shoppingmall/shop?kind=chair">
                        <img src="/shoppingmall/resources/img/bg-img/1.jpg" alt="">
                        <!-- Hover Content -->
                        <div class="hover-content">
                            <div class="line"></div>
                            <p>최저가 180원</p>
                            <h4>의자</h4>
                        </div>
                    </a>
                </div>

				  <!-- Single Catagory -->
                <div class="single-products-catagory clearfix">
                    <a href="/shoppingmall/shop?kind=shoes">
                        <img src="/shoppingmall/resources/img/bg-img/shoes.jfif" alt="">
                        <!-- Hover Content -->
                        <div class="hover-content">
                            <div class="line"></div>
                            <p>최저가 180원</p>
                            <h4>신발</h4>
                        </div>
                    </a>
                </div>

                <!-- Single Catagory -->
                <div class="single-products-catagory clearfix">
                    <a href="/shoppingmall/shop?kind=game">
                        <img src="/shoppingmall/resources/img/bg-img/Game.jpg" alt="">
                        <!-- Hover Content -->
                        <div class="hover-content">
                            <div class="line"></div>
                            <p>최저가 180원</p>
                            <h4>게임</h4>
                        </div>
                    </a>
                </div>

                <!-- Single Catagory -->
                <div class="single-products-catagory clearfix">
                    <a href="/shoppingmall/shop?kind=nightstand">
                        <img src="/shoppingmall/resources/img/bg-img/4.jpg" alt="">
                        <!-- Hover Content -->
                        <div class="hover-content">
                            <div class="line"></div>
                            <p>최저가 180원</p>
                            <h4>스탠드</h4>
                        </div>
                    </a>
                </div>

                <!-- Single Catagory -->
                <div class="single-products-catagory clearfix">
                    <a href="/shoppingmall/shop?kind=plantpot">
                        <img src="/shoppingmall/resources/img/bg-img/pot.jfif" alt="">
                        <!-- Hover Content -->
                        <div class="hover-content">
                            <div class="line"></div>
                            <p>최저가 180원</p>
                            <h4>화분</h4>
                        </div>
                    </a>
                </div>

                <!-- Single Catagory -->
                <div class="single-products-catagory clearfix">
                    <a href="/shoppingmall/shop?kind=smalltable">
                        <img src="/shoppingmall/resources/img/bg-img/6.jpg" alt="">
                        <!-- Hover Content -->
                        <div class="hover-content">
                            <div class="line"></div>
                            <p>최저가 320원</p>
                            <h4>책상</h4>
                        </div>
                    </a>
                </div>

                <!-- Single Catagory -->
                <div class="single-products-catagory clearfix">
                    <a href="/shoppingmall/shop?kind=book">
                        <img src="/shoppingmall/resources/img/bg-img/book.jpg" alt="">
                        <!-- Hover Content -->
                        <div class="hover-content">
                            <div class="line"></div>
                            <p>최저가 320원</p>
                            <h4>책</h4>
                        </div>
                    </a>
                </div>
                <!-- Single Catagory -->
                <div class="single-products-catagory clearfix">
                    <a href="/shoppingmall/shop?kind=computer">
                        <img src="/shoppingmall/resources/img/bg-img/computer.jpg" alt="">
                        <!-- Hover Content -->
                        <div class="hover-content">
                            <div class="line"></div>
                            <p>최저가 320원</p>
                            <h4>컴퓨터</h4>
                        </div>
                    </a>
                </div>

                <!-- Single Catagory -->
                <div class="single-products-catagory clearfix">
                    <a href="/shoppingmall/shop?kind=homedeco">
                        <img src="/shoppingmall/resources/img/bg-img/9.jpg" alt="">
                        <!-- Hover Content -->
                        <div class="hover-content">
                            <div class="line"></div>
                            <p>최저가 320원</p>
                            <h4>장식품</h4>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <!-- Product Catagories Area End -->
    </div>
    <!-- ##### Main Content Wrapper End ##### -->

    <!-- ##### Newsletter Area Start ##### -->
    <section class="newsletter-area section-padding-100-0">
        <div class="container">
            <div class="row align-items-center">
                <!-- Newsletter Text -->
                <div class="col-12 col-lg-6 col-xl-7">
                    <div class="newsletter-text mb-100">
                        <h2>Subscribe for a <span>25% Discount</span></h2>
                        <p>www.forallshoppingmall.com</p>
                    </div>
                </div>
                <!-- Newsletter Form -->
                <div class="col-12 col-lg-6 col-xl-5">
                    <div class="newsletter-form mb-100">
                        <form action="#" method="post">
                            <input type="email" name="email" class="nl-email" placeholder="Your E-mail">
                            <input type="submit" value="Subscribe">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- ##### Newsletter Area End ##### -->

    <!-- ##### Footer Area Start ##### -->
    <footer class="footer_area clearfix">
        <div class="container">
            <div class="row align-items-center">
                <!-- Single Widget Area -->
                <div class="col-12 col-lg-4">
                    <div class="single_widget_area">
                        <!-- Logo -->
                        <div class="footer-logo mr-50">
                            <a href="/shoppingmall/home"><img src="/shoppingmall/resources/img/core-img/logo2.png" alt=""></a>
                        </div>
                        <!-- Copywrite Text -->
                        <p class="copywrite"><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart-o" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a> & Re-distributed by <a href="https://themewagon.com/" target="_blank">Themewagon</a>
<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></p>
                    </div>
                </div>
                <!-- Single Widget Area -->
                <div class="col-12 col-lg-8">
                    <div class="single_widget_area">
                        <!-- Footer Menu -->
                        <div class="footer_menu">
                            <nav class="navbar navbar-expand-lg justify-content-end">
                                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#footerNavContent" aria-controls="footerNavContent" aria-expanded="false" aria-label="Toggle navigation"><i class="fa fa-bars"></i></button>
                                <div class="collapse navbar-collapse" id="footerNavContent">
                                    <ul class="navbar-nav ml-auto">
                                        <li class="nav-item active">
                                            <a class="nav-link" href="/shoppingmall/home">홈페이지</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="/shoppingmall/shop">상품</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="/shoppingmall/product">상품 상세보기</a>
                                        </li>
                                        <c:if test = "${not empty sessionScope.Userid }">
                                        	<li class="nav-item">
                                            	<a class="nav-link" href="/shoppingmall/showbasket">장바구니</a>
                                        	</li>
                                        </c:if>
                                           <c:if test = "${not empty sessionScope.Userid }">
											<li class = "nav-item"><a class="nav-link" href="/shoppingmall/showorder">주문 정보</a></li>
										</c:if>
                                        <li class="nav-item">
                                             <c:if test ="${not empty sessionScope.Userid}">
                    							<a class="nav-link" href="/shoppingmall/logout">로그아웃</a>
                							</c:if>
                                        </li>
                                    </ul>
                                </div>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </footer>
    <!-- ##### Footer Area End ##### -->

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
    <script type = "text/javascript">
  		if("${sessionScope.Userid}" != "" && localStorage.getItem("Userid") == null)	
  		{
  			localStorage.setItem("Userid","${sessionScope.Userid}");
  		}
  		else if("${sessionScope.Userid}" == "" && localStorage.getItem("Userid") != null)
  		{
  			localStorage.removeItem("Userid");
  		}
  	</script>
</body>
</html>