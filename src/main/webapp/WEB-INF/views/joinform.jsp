<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<style>
	.error {
		color:red
	}
</style>
</head>
<body>
	<jsp:include page="./common/common.jsp" flush="true"/>
 <div class = "container jumbotron" align="center" style = "width : 60%;">
 	<h1 style = "text-align:center;">회원가입</h1>
 	<form:form  action = "/shoppingmall/join" method = "post" onsubmit="return chk();" class="form-horizontal" modelAttribute = "user">
 		<div class="form-group">
			<label for="inputPassword3" class="col-sm-2 control-label">아이디</label>
    <div class="col-sm-10">
      <form:input type="text" class="form-control" id = "Id" path = "Id" placeholder="Id"/>
      <form:errors class="error" path="Id"/>
      <br>
      <input class = "btn amado-btn" type = "button" value ="중복확인" onclick = "check()">
    </div>
  </div>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">비밀번호</label>
    <div class="col-sm-10">
      <form:input type="password" class="form-control" id = "pw" path = "Password" placeholder="Password"/>
      <form:errors class="error" path="Password"/>
    </div>
  </div>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">주소</label>
    <div class="col-sm-10">
      <form:input type="text" class="form-control" id = "saddr" path = "Address" placeholder="Address" readonly="true" />
      <form:errors class="error" path="Address"/>
      <br>
      <a href = "#" class = "btn amado-btn" onclick = "goPopup()">도로명 주소</a><br>
    </div>
  </div>
   <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">성별</label>
    <div class="col-sm-10">
      <!--<form:input type="text" class="form-control" path = "Sex" placeholder="Sex"/>-->
      <form:select path = "Sex" style = "padding:2px;">
      	 <form:option value="남자">남자</form:option>
      	 <form:option value="여자">여자</form:option>
      </form:select>
      <form:errors class="error" path="Sex"/>
    </div>
  </div>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">이름</label>
    <div class="col-sm-10">
      <form:input type="text" class="form-control" path = "Name" placeholder="Name"/>
       <form:errors class="error" path="Name"/>
    </div>
  </div>
   <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">나이</label>
    <div class="col-sm-10">
      <!--<form:input type="text" class="form-control" path = "Age" placeholder="Age"/>-->
      <form:select path = "Age" style = "padding:2px;">
      	<c:forEach begin = "1" end = "200" var = "x">
      	 	<form:option value="${x}">${x}</form:option>
      	</c:forEach>
      </form:select>
       <form:errors class="error" path="Age"/>
    </div>
  </div>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">전화번호</label>
    <div class="col-sm-10">
      <form:input type="text" class="form-control" path = "PhoneNumber" placeholder="Phone"/>
       <form:errors class="error" path="PhoneNumber"/>
    </div>
  </div>
   <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">이메일</label>
    <div class="col-sm-10">
      <form:input type="email" class="form-control" path = "email" placeholder="Email"/>
      <form:errors class="error" path="email"/>
    </div>
  </div>
   <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <button type="submit" class="btn amado-btn">회원가입</button>
    </div>
  </div>
   	</form:form> 		
 </div>
 <script>
function goPopup(){
		// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
	    var pop = window.open("/shoppingmall/jusoPopup","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
	    
		// 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
	    //var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
	}
	/** API 서비스 제공항목 확대 (2017.02) **/
	function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn
							, detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
	/*	document.form.roadAddrPart1.value = roadAddrPart1;
		document.form.roadAddrPart2.value = roadAddrPart2;
		document.form.addrDetail.value = addrDetail;
		document.form.zipNo.value = zipNo;*/
		document.getElementById("saddr").value = roadFullAddr;
	}
	
	var idchk = 0;
	function check() 
	{
		if (document.getElementById("Id").value.length == 0) 
		{
			alert("아이디를 입력하세요");
			return;
		}
		else if(document.getElementById("Id").value.length > 0 && document.getElementById("Id").value.length < 5)
		{
			alert("최소한 아이디 길이는 5자리 이어야 합니다.");
			return;
		}
		
		$.ajax({
			type : "get",
			async : true,
			url : "/shoppingmall/check",
			data : {
				id : $("#Id").val()
			},
			dataType : "json",
			contentType : "application/json; charset=UTF-8",
			success : function(data, textStatus) {
				if (data > 0) {
					alert("이미 존재하는 회원입니다.");
				}else if (data == 0) {
					alert("등록 가능한 회원입니다.");
					idchk = 1;
				}
			},
			error : function(data, textStatus) {
				alert("에러가 발생했습니다.");
			},
			complete : function(data, textStatus) {
				
			}
		});
	}
	
	function chk() {
		if (idchk == 0) {
			alert("중복확인을 해주십시오.");
			return false;
		} else
			return true;
	}
</script>
 <!--
	<form action = "/shoppingmall/join" method = "post">
		<table>
			<tr>
				<th>아이디</th>
				<td><input type = "text" name = "Id"></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type = "password" name = "Password"></td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type = "text" name = "Name"></td>
			</tr>
			<tr>
				<th>나이</th>
				<td><input type = "text" name = "Age"></td>
			</tr>
			<tr>
				<th>주소</th>
				<td><input type = "text" name = "Address"></td>
			</tr>
			<tr>
				<th>성별</th>
				<td><input type = "text" name = "Sex"></td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td><input type = "text" name = "PhoneNumber"></td>
			</tr>
		</table>
			<input type = "submit" value = "회원가입">
	</form>
   -->
</body>
</html>