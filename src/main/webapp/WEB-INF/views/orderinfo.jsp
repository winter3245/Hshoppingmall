<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="/shoppingmall/resources/js/jquery/jquery-2.2.4.min.js"></script>
<title>주문 내역</title>
<style>
	td{
		padding : 30px
	}
	
	td#end{
		padding-bottom:10px;
	}
	th { 
		padding : 30px
	}
</style>
</head>
<body>
  <jsp:include page = "./common/common.jsp" flush = "true"/>
   <c:if test = "${empty orders}">
   	 <div class = "container" style = "margin : 50px;text-align:center;">	
   		<span style = "font-weight:bold;">주문한 물품이 없습니다.</span>
     </div>
   </c:if>  
   
   <!-- 
   <c:if test = "${not empty orders}">
	<div class = "container" style = "margin : 30px;">	
		<table>
		  <c:forEach items = "${orders}" var = "dto" varStatus = "mystatus">
			<input type = "hidden" id = "${mystatus.index}seqdto" value = "${mystatus.index}">
			<tr>
				<th>주문번호</th>
			 <c:if test = "${dto.status eq 'paid'}">
				<td id = "${mystatus.index}merchantid">${dto.merchant_id}</td>
			 </c:if>
			 <c:if test = "${dto.status ne 'paid'}">
			 	<td>${dto.merchant_id}</td>
			 </c:if>
			</tr>
			
			<c:if test = "${empty dto.tekbenumber}">
			<tr>
				<th>운송장번호</th>
				<td>미발급</td>
			</tr>
			</c:if>
			<c:if test = "${not empty dto.tekbenumber}">
			<tr>
				<th>운송장번호</th>
				<td><input type = "text" id = "invoiceNumberText" value = "${dto.tekbenumber}"></td>
			</tr>
			<tr>
				<th>택배상태조회</th>
				<td><select id = "tekbeCompanyList"></select></td>
				<td><a href = "#" id = "tekbebutton" class = "btn amado-btn">택배 조회</a></td>
			</tr>
			<tr id = "invoicefind">
				
			</tr>
			<tr id = "trackingtekbe">
			
			</tr>
			<tr id = "#trackingtekbe2">
			
			</tr>
			</c:if>
			
			<tr>
				<th>주문상태</th>
			  <c:if test = "${dto.status eq 'paid'}">
				<td>거래완료</td>
			  </c:if>
			  <c:if test = "${dto.status eq 'ready'}">
				<td>미입금</td>
			  </c:if>
			   <c:if test = "${dto.status eq 'cancelled'}">
				<td>환불</td>
			  </c:if>
			</tr>
			
			<tr>
				<th>가격</th>
			  <c:if test = "${dto.status eq 'paid'}">
				<td id = "${mystatus.index}price">${dto.price}</td>
			  </c:if>
			  <c:if test = "${dto.status ne 'paid'}">
			  	<td>${dto.price}</td>
			  </c:if>		
			</tr>
			
			 <c:forEach items = "${ordergoods}" var = "list">
				<c:forEach items = "${list}" var ="dko">
				 <c:if test = "${dto.merchant_id eq dko.merchant_id}">
				<tr>
					<th>상품명</th>
					<td>${dko.name}</td>
				</tr>
				<tr>
					<th>상품사진</th>
					<td><img src="${dko.goodsprofile}" width = "300" height = "300"></td>
				</tr>
				<tr>
					<th>수량</th>
					<td>${dko.qty}</td>
				</tr>
				 </c:if>
				</c:forEach>
			</c:forEach>
			
		<c:if test = "${not empty vbanks}">
			<c:forEach items = "${vbanks}" var = "vbank" begin = "0" end = "${fn:length(vbanks)}" varStatus = "status">
				  <c:if test = "${vbank.merchant_id eq dto.merchant_id}">
					<tr>
						<th>은행</th>
						<td>${vbank.vbankname}</td>
					</tr>
					<tr>
						<th>계좌번호</th>
						<td>${vbank.vbanknum}</td>
					</tr>
					<tr>
						<th>기한</th>
						<td>${vbank.vbankdate}</td>
					</tr>
					<tr>
						<th>예금주</th>
						<td>${vbank.vbankholder}</td>
					</tr>
					<input type = "hidden" id ="${status.index}merchant_uid" value = "${vbank.merchant_id}">
					<input type = "hidden" id ="${status.index}price" value = "${dto.price}">
					<input type = "hidden" id ="${status.index}person" value = "${vbank.vbankperson}">
					<input type = "hidden" id ="${status.index}code" value = "${vbank.vbankcode}">
					<input type = "hidden" id ="${status.index}name" value = "${vbank.vbankname}">
					<input type = "hidden" id ="${status.index}num" value = "${vbank.vbanknum}">
					<input type = "hidden" id ="${status.index}date" value = "${vbank.vbankdate}">
					<input type = "hidden" id ="${status.index}holder" value = "${vbank.vbankholder}">
					<input type = "hidden" id ="${status.index}seq" value = "${status.index}">
					
			   <c:if test = "${dto.status ne 'ready' and dto.status ne 'cancelled' and dto.paymethod eq 'vbank'}">
				<tr>
					<th>환불요청</th>
						<td>
							<a href = "#" class = "btn amado-btn" onclick = "cancelPayVbank(document.getElementById('${status.index}seq').value)">환불</a>
						</td>
				</tr>
			 </c:if>
			  </c:if>
				</c:forEach>
		</c:if>
		
				<c:if test = "${dto.status ne 'ready' and dto.status ne 'cancelled' and dto.paymethod ne 'vbank'}">
				<tr>
					<th>환불요청</th>
						<td>
							<a href = "#" class = "btn amado-btn" onclick = "cancelPay(document.getElementById('${mystatus.index}seqdto').value)">환불</a>
						</td>
				</tr>
				</c:if>
			<tr>
				<th></th>
				<td style = "padding:50px;"></td>
			</tr>
		  </c:forEach>
		</table>
	</div>
</c:if>
	-->
	
	<c:if test = "${not empty orders}">
	<div class = "container" style = "margin : 30px;">	
		<table style = "text-align:center;margin:10px;border-bottom : 3px solid black" border = "1" class = "table table-bordered">  <!--  padding:100px;border-spacing:10px;border = "1" -->
		  <c:forEach items = "${orders}" var = "dto" varStatus = "mystatus">
			<input type = "hidden" id = "${mystatus.index}seqdto" value = "${mystatus.index}">
			<tr style = "border-top : 3px solid black">
				<th class="align-middle">주문번호</th>
				<th class="align-middle">주문상태</th>
				<th class="align-middle">가격</th>
				<th class="align-middle">주문날짜</th>
				<th class="align-middle">배송지</th>
			</tr>
			<tr>
				 <c:if test = "${dto.status eq 'paid'}">
					<td class="align-middle" id = "${mystatus.index}merchantid">${dto.merchant_id}</td>
			 	 </c:if>
			 	<c:if test = "${dto.status ne 'paid'}">
			 		<td class="align-middle">${dto.merchant_id}</td>
			 	</c:if>
			 	 <c:if test = "${dto.status eq 'paid'}">
					<td class="align-middle">구매완료</td>
			  	</c:if>
			  	<c:if test = "${dto.status eq 'ready'}">
					<td class="align-middle">미입금</td>
			  	</c:if>
			   	<c:if test = "${dto.status eq 'cancelled'}">
					<td class="align-middle">환불</td>
			  	</c:if>
			  	  <c:if test = "${dto.status eq 'paid'}">
					<td class="align-middle" id = "${mystatus.index}price">${dto.price}</td>
			  </c:if>
			  <c:if test = "${dto.status ne 'paid'}">
			  	    <td class="align-middle">${dto.price}</td>
			  </c:if>
			  <td class="align-middle">${dto.time}</td>
			  <td class="align-middle">${dto.address}</td>
			</tr>
			
			<tr>
				<th class="align-middle">상품명</th>
				<th class="align-middle">수량</th>
				<th class="align-middle" colspan = "3">상품사진</th>
			</tr>
			 <c:forEach items = "${ordergoods}" var = "list">
				<c:forEach items = "${list}" var ="dko">
				 <c:if test = "${dto.merchant_id eq dko.merchant_id}">
				 <tr>
					<td class="align-middle">${dko.name}</td>
					<td class="align-middle">${dko.qty}</td>
					<td class="align-middle" colspan = "3"><img src="${dko.goodsprofile}" width = "200" height = "100"></td>
				</tr>
				 </c:if>
				</c:forEach>
			</c:forEach>
			
			<c:forEach items = "${vbanks}" var = "vbank" begin = "0" end = "${fn:length(vbanks)}" varStatus = "status">
				  <c:if test = "${vbank.merchant_id eq dto.merchant_id}">
					<tr>
						<th class="align-middle">은행</th>
						<th class="align-middle">계좌번호</th>
						<th class="align-middle">입금기한</th>
						<th class="align-middle" colspan = "2">예금주</th>
					</tr>
					<tr>	
						<td class="align-middle">${vbank.vbankname}</td>
						<td class="align-middle">${vbank.vbanknum}</td>
						<td class="align-middle">${vbank.vbankdate}</td>
						<td class="align-middle" colspan = "2">${vbank.vbankholder}</td>
					</tr>
					<input type = "hidden" id ="${status.index}merchant_uid" value = "${vbank.merchant_id}">
					<input type = "hidden" id ="${status.index}vbankprice" value = "${dto.price}">
					<input type = "hidden" id ="${status.index}person" value = "${vbank.vbankperson}">
					<input type = "hidden" id ="${status.index}code" value = "${vbank.vbankcode}">
					<input type = "hidden" id ="${status.index}name" value = "${vbank.vbankname}">
					<input type = "hidden" id ="${status.index}num" value = "${vbank.vbanknum}">
					<input type = "hidden" id ="${status.index}date" value = "${vbank.vbankdate}">
					<input type = "hidden" id ="${status.index}holder" value = "${vbank.vbankholder}">
					<input type = "hidden" id ="${status.index}seq" value = "${status.index}">
					<tr>
						<td class="align-middle">환불요청</td>
							<td class="align-middle" colspan = "4">
							<c:if test = "${dto.status eq 'paid' and not empty dto.tekbenumber}">
								<button class = "btn amado-btn" onclick = "cancelPayVbank(document.getElementById('${status.index}seq').value)">무통장 입금 환불</button>
							</c:if>
							</td>
					</tr>
			  </c:if>
			</c:forEach>
			<c:if test = "${dto.status ne 'ready' and dto.status ne 'cancelled' and dto.paymethod ne 'vbank' and not empty dto.tekbenumber}">
				<tr>
					<th class="align-middle">환불요청</th>
						<td class="align-middle" colspan = "4">
							<button class = "btn amado-btn" onclick = "cancelPay(document.getElementById('${mystatus.index}seqdto').value)">환불</button>
						</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
	<br>
	<br>

	<table style = "text-align:center;margin:10px;" border = "1" class = "table table-bordered"> 
			<tr>
				<th>운송장번호</th>
				<td><input type = "text" id = "invoiceNumberText"></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<th>택배상태조회</th>
				<td><select id = "tekbeCompanyList"></select></td>
				<td><button class = "btn amado-btn" id = "tekbebutton">택배 조회</button></td>
				<td></td>
			</tr>
			
			<tr id = "invoicefind">
					
			</tr>
			<tr id = "trackingtekbe">
			
			</tr>
			<!-- 
			<tr id = "trackingtekbe2">
			
			</tr>
			-->
	</table>
                    <div class="col-12">
                        <nav aria-label="navigation">
                            <ul class="pagination justify-content-end mt-50">
    							<c:if test="${ curPageNum > 5 }">
            						<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/showorder?page=1">◀◀</a></li>
        						</c:if>
        
        						<c:if test="${ curPageNum > 5 }">
            						<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/showorder?page=${ blockStartNum - 1 }">◀</a></li>
        						</c:if>
        
        						<c:forEach var="i" begin="${ blockStartNum }" end="${ blockLastNum }">
            						<c:choose>
            							<c:when test="${ i > lastPageNum }">
            								
            							</c:when>
                						<c:when test="${ i == curPageNum }">
                    						<li class="page-item active" style = "color:black;">${ i }</li>
                						</c:when>
                						<c:otherwise>
                    						<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/showorder?page=${ i }">${ i }</a></li>
                						</c:otherwise>
            						</c:choose>
            						&nbsp;&nbsp;
        						</c:forEach>
 
        						<c:if test="${ lastPageNum > blockLastNum }">
            						<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/showorder?page=${ blockLastNum + 1 }">▶</a></li>
        						</c:if>
        						<c:if test = "${lastPageNum > 5}">
        							<li class="page-item"><a class="page-link" style = "color:black;" href="/shoppingmall/showorder?page=${lastPageNum}">▶▶</a></li>
        						</c:if>
    						</ul>
                        </nav>
                   </div>
               </div>	
            </c:if>
          
   <script> 
   //alert(document.getElementById('merchantid').innerHTML);
   $(document).ready(function(){
    var myKey = "3e55oKGbQ4in2yahYWBuFA"; // sweet tracker에서 발급받은 자신의 키 넣는다.
    
        // 택배사 목록 조회 company-api
        $.ajax({
            type:"GET",
            dataType : "json",
            url:"https://info.sweettracker.co.kr/api/v1/companylist?t_key="+myKey,
            success:function(data){
                    // 방법 2. Json으로 가져온 데이터에 Array로 바로 접근하기
                    var CompanyArray = data.Company; // Json Array에 접근하기 위해 Array명 Company 입력
                    console.log(CompanyArray); 
                    
                    var myData="";
                    $.each(CompanyArray,function(key,value) {
                            myData += ('<option value='+value.Code+'>' +'key:'+key+', Code:'+value.Code+',Name:'+value.Name + '</option>');                        
                    });
                    $("#tekbeCompanyList").html(myData);
            }
        });
   
        // 배송정보와 배송추적 tracking-api
        $("#tekbebutton").click(function() {
            var t_code = $('#tekbeCompanyList option:selected').attr('value');
            var t_invoice = $('#invoiceNumberText').val();
            $.ajax({
                type:"GET",
                dataType : "json",
                url:"https://info.sweettracker.co.kr/api/v1/trackingInfo?t_key="+myKey+"&t_code="+t_code+"&t_invoice="+t_invoice,
                success:function(data){
                    console.log(data);
                    var myInvoiceData = "";
                    if(data.status == false){
                        myInvoiceData += ('<td>'+"오류"+'</td>');
                        myInvoiceData += ('<td>'+data.msg+'</td>');
                        myInvoiceData += ('<td>'+'</td>');
                        myInvoiceData += ('<td>'+'</td>');
                    }else{
                        myInvoiceData += ('<th>'+"보내는사람"+'</td>');                     
                        myInvoiceData += ('<th>'+data.senderName+'</td>');                     
                        myInvoiceData += ('</tr>');     
                        myInvoiceData += ('<tr>');                
                        myInvoiceData += ('<th>'+"제품정보"+'</td>');                     
                        myInvoiceData += ('<th>'+data.itemName+'</td>');                     
                        myInvoiceData += ('</tr>');     
                        myInvoiceData += ('<tr>');                
                        myInvoiceData += ('<th>'+"송장번호"+'</td>');                     
                        myInvoiceData += ('<th>'+data.invoiceNo+'</td>');                     
                        myInvoiceData += ('</tr>');     
                        myInvoiceData += ('<tr>');                
                        myInvoiceData += ('<th>'+"송장번호"+'</td>');                     
                        myInvoiceData += ('<th>'+data.receiverAddr+'</td>');                     
                    }
                    
                    $("#invoicefind").html(myInvoiceData)
                    var trackingDetails = data.trackingDetails;
                    
                    var myTracking="";
                    var header ="";
                    header += ('<th>'+"시간"+'</th>');
                    header += ('<th>'+"장소"+'</th>');
                    header += ('<th>'+"유형"+'</th>');
                    header += ('<th>'+"전화번호"+'</th>');                     
                    $("#trackingtekbe").html(header);
                    
                    $.each(trackingDetails,function(key,value) {
                    	var td1 = document.createElement("td"); 
                        td1.innerHTML = value.timeString; 
                        var td2 = document.createElement("td"); 
                        td2.innerHTML = value.where; 
                        var td3 = document.createElement("td"); 
                        td3.innerHTML = value.kind; 
                        var td4 = document.createElement("td"); 
                        td4.innerHTML = value.telno; 
                        
                   		var tr = document.createElement("tr"); 
                        tr.appendChild(td1); 
                        tr.appendChild(td2); 
                        tr.appendChild(td3); 
                        tr.appendChild(td4); 
                        
                        document.getElementById("tekbetable").appendChild(tr);
                    	/*
						myTracking += ('<tr>');
						myTracking += ('<td>' + value.timeString + '</td>');
						myTracking += ('<td>' + value.where + '</td>');
					    myTracking += ('<td>' + value.kind + '</td>');
						myTracking += ('<td>' + value.telno + '</td>');
					    myTracking += ('</tr>');
					    */
				    });
				//$("#trackingtekbe2").html(myTracking);
				}
			});
		});
	});
				function cancelPay(i) {
					$.ajax({
								//"url": "https://localhost:8443/shoppingmall/cancel",
								"url" : "/shoppingmall/refundadmin",
								"type" : "POST",
								"contentType" : "application/json",
								"data" : JSON.stringify({
											"merchant_uid" : document.getElementById(i + 'merchantid').innerHTML, // 주문번호
											"cancel_request_amount" : document.getElementById(i + 'price').innerHTML // 환불금액
										}),
								success : function() {
									alert('요청 성공');
								},
								error : function() {
									alert('이미 환불 요청을 하셨습니다.');
								}
							//"dataType": "json"
							});
					/*
					.done(function(result){
					  console.log(result);
					  var cancelstatus = result.status;
					  console.log("cancelstatus : " + cancelstatus);
					  var canceldata = {"cancel" : cancelstatus, "merchant_id" : document.getElementById(i + 'merchantid').innerHTML}
					  
					  $.ajax({
						"url" : "https://localhost:8443/shoppingmall/cancelstatus",
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
					 */
				}

				function cancelPayVbank(i) {
					//alert(document.getElementById(i + 'price').value)
					$.ajax({
								//"url": "https://localhost:8443/shoppingmall/cancel",
								"url" : "/shoppingmall/refundadmin",
								"type" : "POST",
								"contentType" : "application/json",
								"data" : JSON.stringify({
									"merchant_uid" : document.getElementById(i + 'merchant_uid').value, // 주문번호
									"cancel_request_amount" : document.getElementById(i + 'vbankprice').value, // 환불금액
									"refund_holder" : document.getElementById(i + 'holder').value, // [가상계좌 환불시 필수입력] 환불 가상계좌 예금주
									"refund_bank" : document.getElementById(i + 'code').value, // [가상계좌 환불시 필수입력] 환불 가상계좌 은행코드(ex. KG이니시스의 경우 신한은행은 88번)
									"refund_account" : document.getElementById(i + 'num').value
								// [가상계좌 환불시 필수입력] 환불 가상계좌 번호
								}),
								success : function() {
									alert('환불 요청 성공');
								},
								error : function() {
									alert('이미 환불 요청을 하셨습니다.');
								}
							//"dataType": "json"
							});
					/*
					  .done(function(result){
						  console.log(result);
						  var cancelstatus = result.status;
						  console.log("cancelstatus : " + cancelstatus);
						  var canceldata = {"cancel" : cancelstatus, "merchant_id" : document.getElementById(i + 'merchant_uid').value}
						  
						  $.ajax({
							"url" : "https://localhost:8443/shoppingmall/cancelstatus",
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
					 */
				}
			</script>
			<script>
			function hashHandler() {
				  alert("change");
				}

				window.addEventListener('hashchange', hashHandler, false);
			</script>
</body>
</html>