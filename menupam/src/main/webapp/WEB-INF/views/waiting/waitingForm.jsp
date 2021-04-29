<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>메뉴팜</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='../../resources/css/reset.css'>
    <link rel='stylesheet' type='text/css' media='screen' href='../../resources/css/main.css'>
    <link rel='stylesheet' type='text/css' media='screen' href='../../resources/css/waitForm.css'>
    <script src="https://kit.fontawesome.com/e5012d0871.js" crossorigin="anonymous"></script>
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
            <!--여기서 부터 코드 작성-->
            <div class="body">
			<div class="content_wrapper">
			
            <form:form modelAttribute="waiting"
            	action="${context}/waiting/registerWait" method="POST">
               
                <div class="textBox">
						<p class="fontMedium" id="title">대기</p>
					</div>
					<br>
					<div class="line"></div>
					<div class="content">
						<div class="wrap_shop">
							<i class="fas fa-store store"></i>
							<div class="shop">
								<span id="shop_info">${shop.shopName}</span>
								<!-- 매장 이름 -->
							</div>
						</div>
						<hr color="#F2BB13">
					</div>
                <div class="imgLarge">
                    <img src="../../resources/images/sample.PNG" alt=""> <!-- 사진주소를어떻게.. -->
                </div>
                <div class="text_box">
                	<p class="fontSmall">현재 대기인원 수 : ${waitCount}명</p>
                	<br>
					<p class="fontSmall">예상 대기시간 : (00시간) 00분</p>
				</div>
				<br><br>
				<div class="wrap_field">
				<div class="field">
	            <span class="fas fa-ghost"> 인원수 </span>
	            <input type="number" class="input party" id="name" name="waitParty" required="required">
	          </div>
	          <br>
	          <div class="field space">
	            <span class="fas fa-mobile-alt"> 연락처  </span>
	            
	            <input type="tel" class="input" id="phone" name="waitPhone" value="${sessionScope.userInfo.memberPhone}" required placeholder="-없이 입력" required="required"
	            pattern="[0-9]{11}">
	          </div>
	          <input name=shopIdx value="${shop.shopIdx}" style="display: none">
            	<button class="btnMedium">대기 등록하기</button>
            </div>
            	</form:form>
            	
            	</div>
            	</div>
        </div>
        <div class="footer">
            <div><i class="fas fa-search"></i></div>
            <div><i class="fas fa-qrcode"></i></div>
            <div><i class="fas fa-home"></i></div>
            <div><i class="far fa-clipboard"></i></div>
            <div><i class="far fa-user"></i></div>
        </div> 
    </div> 

</body>
</html>