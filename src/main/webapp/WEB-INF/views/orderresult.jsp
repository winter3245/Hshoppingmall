<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>주문결과</title>
<style>
/*
	td{
		padding:15px;
	}
*/
	th,td {
		padding : 30px
	}
</style>
</head>
<body>
	<jsp:include page="./common/common.jsp" flush = "true"/>
	
	<div class = "container">
		<div style = "margin : 30px;">
			<table style = "padding:100px;border-spacing:10px;text-align:center;vertical-align:middle;margin:10px;" border = "1" class = "table table-bordered">
			<!-- 
				<tr>
					<th>주문번호</th>
					<td><span style = "font-weight:bold;">${Order.merchant_id}</span></td>
				</tr>
				<tr>
					<th>주문가격</th>
					<td><span style = "font-weight:bold;">${Order.price}</span></td>
				</tr>
			 <c:if test = "${not empty Ordergoods}">
				<c:forEach items = "${Ordergoods}" var = "dto">
					<tr>
						<th>상품명</th>
						<td>${dto.name}</td>
					</tr>
					<tr>
						<th>상품수량</th>
						<td>${dto.qty}</td>
					</tr>
					<tr>
						<th>상품사진</th>
						<td><img src = "${dto.goodsprofile}" width = "200" height = "200"></td>
					</tr>
				</c:forEach>				
			</c:if>
				<c:if test = "${not empty vbank_num}">
				 <c:if test = "${not empty Order}">
					<tr>
						<th>계좌번호</th>
						<td><span style = "font-weight:bold;">${vbank_num}</span></td>
					</tr>
				 </c:if>
				<c:if test = "${empty Order}">
					<tr>
						<th>계좌번호</th>
						<td><span style = "font-weight:bold;">오류 발생</span></td>
					</tr>
				</c:if>
					<tr>
						<th>은행</th>
						<td><span style = "font-weight:bold;">${vbank_name}</span></td>
					</tr>
					<tr>
						<th>기한</th>
						<td><span style = "font-weight:bold;">${vbank_date}</span></td>
					</tr>
				</c:if>
			-->
				<!-- 
				<c:if test = "${empty vbank_num}">
					<th>오류 발생</th>
					<td>오류가 발생하였습니다.다시 결제하여 주십시오.</td>
				</c:if>
			    -->
			   <tr>
			   		<th>주문번호</th>
			   		<th colspan = "2">주문가격</th>
			   </tr>
			   <tr>
			   		<td class="align-middle">${Order.merchant_id}</td>
			   		<td class="align-middle" colspan = "2">${Order.price}</td>
			   </tr>
			   <tr>
			   		<th>상품명</th>
			   		<th>상품수량</th>
			   		<th>상품사진</th>
			   </tr>
			<c:if test = "${not empty Ordergoods}">
			  <c:forEach items = "${Ordergoods}" var = "dto">
			   <tr>
			   	 <td class="align-middle">${dto.name}</td>
			   	 <td class="align-middle">${dto.qty}</td>
			   	 <td class="align-middle"><img src = "${dto.goodsprofile}" width = "200" height = "200"></td>
			   </tr>
			  </c:forEach>
		    </c:if>
		    
		    <c:if test = "${not empty vbank_num}">
				 <c:if test = "${not empty Order}">
		    		<tr>
		    			<th>계좌번호</th>
		    			<th>은행</th>
		    			<th>기한</th>
		    		</tr>
		    		<tr>
		    			<td class="align-middle">${vbank_num}</td>
		    			<td class="align-middle">${vbank_name}</td>
		    			<td class="align-middle">${vbank_date}</td>
		    		</tr>
			     </c:if>
			</c:if>   
			
			<c:if test = "${empty vbank_num and empty Order}">
				<tr>
					<th></th>
				</tr>
				<tr>
					<td colspan="3" style = "font-weight:bold;">오류발생</td>
				</tr>		
			</c:if>
			</table>
		</div>
	</div>
	<script>
	 	if(localStorage.getItem("Userid") != null && "${method}" == "mobile") 
	 	{
	 		$.ajax({
	 			url : "/shoppingmall/mobilemaintainsession",
	 			data : {"Id" : localStorage.getItem("Userid")},
	 			method : "POST",
	 			success : function() {

	 			},
	 			error : function() {

	 			}
	 		});
	 	}
</script>
	<script>
	//alert("${Order}");
	//alert("${empty vbank_num}");
	 //alert("${vbank_holder}" + "," + "${vbank_code}" + "," + "${vbank_num}" + "," + "${Order.merchant_id}" + "," + "${Order.price}");
	 function cancelPay() {
	      $.ajax({
	        "url": "/shoppingmall/cancel",
	        "type": "POST",
	        "contentType": "application/json",
	        "data": JSON.stringify({
	          "merchant_uid": "${Order.merchant_id}", // 주문번호
	          "cancel_request_amount": "${Order.price}", // 환불금액
	          "reason": document.getElementById('reason').value, // 환불사유
	          "refund_holder": "${vbank_holder}", // [가상계좌 환불시 필수입력] 환불 가상계좌 예금주
	          "refund_bank": "${vbank_code}", // [가상계좌 환불시 필수입력] 환불 가상계좌 은행코드(ex. KG이니시스의 경우 신한은행은 88번)
	          "refund_account": "${vbank_num}" // [가상계좌 환불시 필수입력] 환불 가상계좌 번호
	        }),
	        "dataType": "json"
	      }).done(function(result){
	    	  console.log(result);
	    	  var cancelstatus = result.status;
	    	  console.log("cancelstatus : " + cancelstatus);
	    	  var canceldata = {"cancel" : cancelstatus, "merchant_id" : "${Order.merchant_id}"}
	    	  
	    	  $.ajax({
				"url" : "/shoppingmall/cancelstatus",
				"type" : "POST",
				headers: { "Content-Type": "application/json" },
				"data" : JSON.stringify(canceldata),
				success : function(){
					alert('status 변화 성공');
				},
				error : function(){
					alert('error');
				}
	    	  });
	    	  alert('환불 성공');
	      }).fail(function(error){
	    	  console.log(error);
	    	  alert('환불 실패'); 
	      });
	    }
	</script>
	 <script
    	src="https://code.jquery.com/jquery-3.3.1.min.js"
    	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
    	crossorigin="anonymous">
	 </script>
</body>
</html>