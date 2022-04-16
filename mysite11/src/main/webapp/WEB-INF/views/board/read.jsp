<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
<script>



</script>


<title>Mysite</title>
</head>
<body>
	<div id="container">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="5">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${boardVo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>${boardVo.content }</td>
						<td> 
    					  
						  <td align="center" bgcolor="#DDDDDD">첨부파일</td>
    					 <td bgcolor="#FFFFE8">
    					 <a href="/mysite/board?a=filedownload&fileName=${boardVo.filename}" >${boardVo.filename}</a>
    					 
  						 &nbsp;&nbsp;<font color="blue">(${boardVo.filesize} KBytes)</font>  
						</td>
					</tr>
				</table>
				
				 
				
				<div class="bottom">
					<a href="/mysite/board">글목록</a>
					
					<c:if test="${authUser.no == boardVo.userNo }">
						<a href="/mysite/board?a=modifyform&no=${boardVo.no }">글수정</a>
					</c:if>
				</div>
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div><!-- /container -->
</body>
</html>		
		
