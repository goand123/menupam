<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>메뉴팜</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='../../../resources/css/reset.css'>
    <link rel='stylesheet' type='text/css' media='screen' href='../../../resources/css/main.css'>

   
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
         <a href="${context}/member/logout"><span>logout</span></a>
             	<a href="${context}/member/join"><span>join</span></a>       
                    
                   
	  
  	    <table style="text-align:center" border="1">
	       
	           <tr>
	              
	               <th><span>id</span></th>
	               <th ><span>이름</span></th>
	           </tr>
	       
	      
	       <c:forEach items="${memberAll}" var="member">
	           <tr>
	           		<td>${member.memberId}</td>
	               <td>${member.memberName}</td>
	               
	         
	           </tr>
	        </c:forEach>
	    
	    </table>
	   
	    	
	 
	 
	
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