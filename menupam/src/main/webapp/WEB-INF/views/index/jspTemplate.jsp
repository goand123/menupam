<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>메뉴팜</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='resources/css/reset.css'>
    <link rel='stylesheet' type='text/css' media='screen' href='resources/css/main.css'>
    <script src='main.js'></script>
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
            <div class="btnBox">
                <input type="button" value="btnLarge" class="btnLarge">
                <br><br>
                <input type="button" value="btnMedium" class="btnMedium">
                <br><br>
                <input type="button" value="btnSmall" class="btnSmall">
            </div>
            <br>
            <div class="fontBox">
                <p class="fontLarge">fontLarge 폰트라지</p>
                <br><br>
                <p class="fontMedium">fontMedium 폰트미디엄</p>
                <br><br>
                <p class="fontSmall">fontSmall 폰트스몰</p>
                <br><br>
                <p class="fontXSmall">fontXSmall 폰트X스몰</p>
            </div>
            <br>
            <div class="imgBox">
                <div class="imgLarge">
                    <img src="resources/images/sample.PNG" alt="">
                </div>
                <br><br>
                <div class="imgMedium">
                    <img src="resources/images/sample.PNG" alt="">
                </div>
                <br><br>
                <div class="imgSmall">
                    <img src="resources/images/sample.PNG" alt="">
                </div>
            </div>
            
        </div>
        <div class="footer">
            <div><i class="fas fa-search"></i></div>
            <div><i class="fas fa-qrcode"></i></div>
            <div><i class="fas fa-home"></i></div>
            <div><i class="far fa-clipboard"></i></i></div>
            <div><i class="far fa-user"></i></div>
        </div> 
    </div> 

</body>
</html>