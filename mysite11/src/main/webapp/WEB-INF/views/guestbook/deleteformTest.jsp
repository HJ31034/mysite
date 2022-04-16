<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="/mysite/assets/css/guestbook.css" rel="stylesheet" type="text/css">
	<title>Insert title here</title>
   
   <%
   String no = request.getParameter( "no" );
%>
</head>
<body>
   <div id="container">
   <div id="wrapper">
    
      <div id="content">
         <div id="guestbook" class="delete-form">
            <form method="post" action="/mysite/gb"  value="delete">
               
               <input type="hidden" name="a" value="delete"> 
               <input type='hidden' name="no" value="<%=no%>">
               
               <label>비밀번호</label>
               
               <input type="password" name="password">
               <input type="submit" value="확인">
            </form>
            <a href="/mysite/guestbook">방명록 리스트</a>
         </div>
      </div>
      </div>
		
		<div id="footer">
			<p>(c)opyright 2015,2016,2017</p>
		</div> <!-- /footer -->
		
	</div> <!-- /container -->

</body>
</html>
