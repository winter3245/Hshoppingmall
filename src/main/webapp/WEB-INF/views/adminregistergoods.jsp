<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>물품 등록</title>
<script src="/shoppingmall/resources/js/jquery/jquery-2.2.4.min.js"></script>
<style>
	tr
	{
		height:60px;
	}
	th
	{
		width:100px;
	}
</style>
 	<meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!-- Title  -->

    <!-- Favicon  -->
    <link rel="icon" href="img/core-img/favicon.ico">

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
                <a href="/shoppingmall/home"><img src="/shoppingmall/resources/img/core-img/logo.png" alt=""></a><br>
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
                    <li><a href="/shoppingmall/shop">상품</a></li>
                    <li><a href="/shoppingmall/product">상품 상세보기</a></li>
                   <c:if test = "${not empty sessionScope.Userid }">
                   <li class="nav-item">
                       <a class="nav-link" href="/shoppingmall/showbasket">장바구니</a>
                   </li>
                   </c:if>
                    <c:if test = "${not empty sessionScope.Userid }">
								<li><a href="/shoppingmall/showorder">주문 정보</a></li>
					</c:if>
					<c:if test = "${sessionScope.Userid eq 'admin'}">
						<li><a href="/shoppingmall/admin/adminrefund">고객 환불</a>
					</c:if>
					<c:if test = "${sessionScope.Userid eq 'admin'}">
						<li class = "active"><a href="/shoppingmall/admin/registerForm">상품 등록</a>
					</c:if>
                    <c:if test = "${not empty sessionScope.Userid}">
                    	<li><a href="/shoppingmall/logout">로그아웃</a></li>
                    </c:if>
                </ul>
            </nav>
            <!-- Button Group -->
           <!-- 
            <div class="amado-btn-group mt-30 mb-100">
                <a href="#" class="btn amado-btn mb-15">%Discount%</a>
                <a href="#" class="btn amado-btn active">New this week</a>
            </div-->
            
            <!-- Cart Menu -->
            <div class="cart-fav-search mb-100">
             <c:if test = "${not empty sessionScope.Userid}">
                <a href="/shoppingmall/showbasket" class="cart-nav"><img src="/shoppingmall/resources/img/core-img/cart.png" alt="">장바구니 </a>
             </c:if>
                <a href="#" class="search-nav"><img src="/shoppingmall/resources/img/core-img/search.png" alt="">상품 찾기</a>
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
                     	<li><a href = "/shoppingmall/shop?kind=chair">의자</a></li>
                        <li><a href = "/shoppingmall/shop?kind=shoes">신발</a></li>
                    	<li><a href = "/shoppingmall/shop?kind=game">게임</a></li>
						<li><a href = "/shoppingmall/shop?kind=plantpot">화분</a></li>
                    	<li><a href = "/shoppingmall/shop?kind=homedeco">장식품</a></li>
                    	<li><a href = "/shoppingmall/shop?kind=book">책</a></li>
                    	<li><a href = "/shoppingmall/shop?kind=smalltable">책상</a></li>
                        <li><a href = "/shoppingmall/shop?kind=computer">컴퓨터</a></li>
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
        </div>

 <form action = "/shoppingmall/admin/registerGoods" method = "post">
 <h1 style = "text-align:center;">관리자 등록</h1>
	<div class = "container">
	  <div class = "jumbotron">
		<div id = "loadimg">
			
		</div>
		<br>
		<input type = "file" id = "imgfile"/>
		<br>
		<br>
		<div>
			<input type = "hidden" name = "thumbnail" id = "imgthumbnail"><br> 
		  <table class = "table table-responsive" id="registertable">
		    <tr>
		    	<th>물품명</th>
				<td><input type = "text" name = "name"></td>
			</tr>
			<tr>
				<th>가격</th>
				<td><input type = "text" name = "price"></td>
			</tr>
			<tr>
				<th>물품종류</th>
				<td>
					<select name="kind" style = "padding-bottom:3px;" id="productkind" onchange="changefunction()">
    					<option value="chair">의자</option>
    					<option value="shoes">신발</option>
    					<option value="game">게임</option>
   						<option value="plantpot">화분</option>
   						<option value="homedeco">장식품</option>
   						<option value="book">책</option>
   						<option value="smalltable">책상</option>
   						<option value="computer">컴퓨터</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>수량</th>
				<td><input type = "text" name = "remain"></td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea rows = "10" cols = "50" name = "content"></textarea>
				</td>
			</tr>
			<!-- <tr style = "text-align:right;" id = "submitnode">
				<th></th>
				<td><input type = "submit" value = "등록" style = "width:30px;" class = "btn amado-btn"></td>
			</tr>
			 -->
		  </table>
		  <input type = "submit" value = "등록" style = "width:30px;" class = "btn amado-btn">
		 </div>
	   </div>
	  </div>
 </form>
 </div>
 </div>
	<script>
		function registerfile()
		{
		   var formData = new FormData();
	       formData.append('file',$("#imgfile")[0].files[0]);
		   formData.append('goods_id',1);
		   $.ajax({
			data: formData,
		  	method: 'post',
		  	url: '/shoppingmall/upload',
		  	cache: false,
		  	contentType: false,
		  	processData: false,
		  	enctype: 'multipart/form-data',
		  	success: function(data) //url; 
		  	{
		  		alert(data.url);
		  		var img = document.createElement("img");
		      	img.src = data.url; //img.src = url;
		      	img.setAttribute("width","550px");
		  		img.setAttribute("height","550px");
		  		document.getElementById("loadimg").appendChild(img);
		  		$("#imgthumbnail").val(data.url);//$("#imgthumbnail").val(url);
		  		//document.getElementById("loadimg").setAttribute("style","padding:20px;width:100%;border:1px solid black;");
		  	},
		  	error:function(data)
		  	{
		  		console.log(data);
		  		alert('error');
		  	}
		  });
		}
		
		//
		function registerotherfile()
		{
		   var formData = new FormData();
	       formData.append('file',$("#otherfile")[0].files[0]);
		   $.ajax({
			data: formData,
		  	type: 'post',
		  	url: '/shoppingmall/upload',
		  	cache: false,
		  	contentType: false,
		  	processData: false,
		  	enctype: 'multipart/form-data',
		  	success: function(data) 
		  	{
		  		var atag = document.createElement("a");
		      	atag.setAttribute("href","/shoppingmall/downloadFile?storedfilename=" + data.orgname);
		      	atag.innerHTML = data.orgname;
		      	document.getElementById("filename").appendChild(atag);
		  	},
		  	error:function(url)
		  	{
		  		alert('error');
		  	}
		  });
		}	
		
		window.onload = function()
		{
			document.getElementById("imgfile").addEventListener("change",registerfile);
		}
		
	</script>
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
	
	function changefunction()
	{
		if(document.getElementById("productkind").value == "book"){
			var tr1 = document.createElement("tr");
			var th1 = document.createElement("th");
			var td1 = document.createElement("td");
			th1.innerHTML = "글쓴이";
			var writer = document.createElement("input");
			writer.setAttribute("type","text");
			writer.setAttribute("name","writer");
			td1.appendChild(writer);
			tr1.appendChild(th1);
			tr1.appendChild(td1);
			document.getElementById("registertable").appendChild(tr1);
			
			var tr2 = document.createElement("tr");
			var th2 = document.createElement("th");
			var td2 = document.createElement("td");
			th2.innerHTML = "출판사";
			var wcompany = document.createElement("input");
			wcompany.setAttribute("type","text");
			wcompany.setAttribute("name","wcompany");
			td2.appendChild(wcompany);
			tr2.appendChild(th2);
			tr2.appendChild(td2);
			document.getElementById("registertable").appendChild(tr2);
			
			var tr3 = document.createElement("tr");
			var th3 = document.createElement("th");
			var td3 = document.createElement("td");
			th3.innerHTML = "목차";
			var tcontent = document.createElement("textarea");
			tcontent.setAttribute("rows","10");
			tcontent.setAttribute("cols","50");
			tcontent.setAttribute("name","tcontent");
			td3.appendChild(tcontent);
			tr3.appendChild(th3);
			tr3.appendChild(td3);
			document.getElementById("registertable").appendChild(tr3);
			
			var tr4 = document.createElement("tr");
			var th4 = document.createElement("th");
			var td4 = document.createElement("td");
			th4.innerHTML = "출판일";
			var bookdate = document.createElement("input");
			bookdate.setAttribute("type","text");
			bookdate.setAttribute("name","bookdate");
			td4.appendChild(bookdate);
			tr4.appendChild(th4);
			tr4.appendChild(td4);
			document.getElementById("registertable").appendChild(tr4);
		}
	}
	</script>
</body>
</html>