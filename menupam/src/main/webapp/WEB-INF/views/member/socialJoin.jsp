<%@ include file="/WEB-INF/views/include/head.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8'>
<meta http-equiv='X-UA-Compatible' content='IE=edge'>
<title>메뉴팜</title>
<meta name='viewport' content='width=device-width, initial-scale=1'>
<link rel='stylesheet' type='text/css' media='screen'
	href='../../resources/css/reset.css'>
<link rel='stylesheet' type='text/css' media='screen'
	href='../../resources/css/main.css'>
<link rel='stylesheet' type='text/css' media='screen'
	href='../../resources/css/socialJoin.css'>
<script src="https://kit.fontawesome.com/e5012d0871.js"
	crossorigin="anonymous"></script>
</head>
<body>
	<div class="wrapper">
		<div class="header">
			<div class="search">
				<i class="fas fa-search"></i>
			</div>
			<div class="notice">
				<i class="far fa-clipboard"></i>
			</div>
		</div>
		<div class="main">
			<div class="body">
				<!--여기서 부터 코드 작성  버튼 : 아이디중복확인, 전화번호인증, 주소찾기-->
				<div class="content">
					<header>소셜 회원 가입</header>
					<div class="socialJoinline"></div>
					<form action="${context}/member/mailauth" method="post"
						id="form_join" class="member_info_form">
						<div class="field">
							<span class="fas fa-ghost"></span>
							<input type="text" class="name" id="name" name="memberName" value="${member.nickName}" readonly />
						</div>
						<div class="field space">
							<span class="fa fa-user"></span> 
							<input type="text" class="id" id="id" name="memberId" value="${member.id}" readonly />
							<span class="valid_info" id="id_check"></span>
						</div>
						<div class="field space">
							<span class="far fa-envelope"></span>
							<input type="email" class="email" id="email" name="memberEmail" value="${member.email}" readonly />
							<form:errors path="email" cssClass="valid_info" />
						</div>
						<div class="field space">
							<span class="fas fa-mobile-alt"></span>
							<input type="tel" class="name" id="Phone" name="memberPhone" required placeholder="전화번호" required="required">
							<form:errors path="tell" cssClass="valid_info" />
						</div>
						<br>
						<div class="field space" id="login_wrapper">
							<input type="submit" value="가입하기">
						</div>
				</div>
			</div>
		</div>
		<div class="footer">
			<div>
				<i class="fas fa-search"></i>
			</div>
			<div>
				<i class="fas fa-qrcode"></i>
			</div>
			<div>
				<i class="fas fa-home"></i>
			</div>
			<div>
				<i class="far fa-clipboard"></i></i>
			</div>
			<div>
				<i class="far fa-user"></i>
			</div>
		</div>
	</div>

	<script type="text/javascript">
   
let idCheckFlg = false;
   let idCheck = () => {
	   //사용자가 입력한 아이디
	   //요소의 아이디속성이 있을 경우 해당 엘리먼트를 가져다가 사용할 수 있다.
	   let memberId = id.value;
	   if(memberId){
		  
		   fetch("/member/idcheck?memberId=" + memberId,{
			   method:"get"
		   })
		   .then(response => response.text())
		   .then(text =>{
			   if(text == 'success'){
				   idCheckFlg = true;
				   id_check.innerHTML = '가능';
			   }else{
				   idCheckFlg = false;
				   id_check.innerHTML = '불가';
				   id.value="";
			   }
		   })
		   
	   }else{
		   alert("아이디를 입력하지 않으셨습니다.");
	   }
   }
   
   document.querySelector('#form_join').addEventListener('submit',(e)=>{
	   let memberPw = pw.value;
	   let regExp = /^(?!.*[ㄱ-힣])(?=.*\W)(?=.*\d)(?=.*[a-zA-Z])(?=.{8,})/;
	   
	   if(!idCheckFlg){
		   e.preventDefault();
		   alert("아이디 중복검사를 하지 않으셨습니다.");
		   id.focus()
	   }
	   
	   if(!(regExp.test(password))){
		   //form의 데이터 전송을 막음
		   e.preventDefault();
		   pw_confirm.innerHTML = '비밀번호는 숫자,영문자,특수문자 조합의 8글자 이상인 문자열입니다.';
		   pw.value='';
	   }
   });
   
   </script>







</body>
</html>