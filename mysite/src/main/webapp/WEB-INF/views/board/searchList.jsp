<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
<title>Mysite</title>
</head>
<body>
	<div id="container">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="content">
			<div id="board">
				<form id="search_form" action="/mysite/board?keyword=${keyword }" method="get">
					<input type="hidden" name="a" value="keywd">
					<input type="text" id="kwd" name="kwd" value="${keyword }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>			
					
				</table>
				
				
					<table class="tbl-ex">	
					<c:forEach items="${search }" var="vo">
						<tr>
							<td>${vo.no }</td>
							<td><a href="/mysite/board?a=read&no=${vo.no }">${vo.title }</a></td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td>${vo.userName }</td>
							
						</tr>
					</c:forEach>
				</table>
				
				
				<div class="pager">
					<ul>
						<li><a href="">◀</a></li>
						<li><a href="">1</a></li>
						<li><a href="">2</a></li>
						<li class="selected">3</li>
						<li><a href="">4</a></li>
						<li><a href="">5</a></li>
						<li><a href="">6</a></li>
						<li><a href="">7</a></li>
						<li><a href="">8</a></li>
						<li><a href="">9</a></li>
						<li><a href="">10</a></li>
						<li><a href="">▶</a></li>
					</ul>
				</div>			
				
				<%-- <div class="pager">
					<ul>
						<c:if test="${prevPage > 0 }">
							<li><a href="/mysite/board?a=list&p=${prevPage }&kwd=${keyword }">◀</a></li>
						</c:if>						
						<c:forEach 
							begin='${firstPage }' 
							end='${lastPage }' 
							step='1' 
							var='i'>
							<c:choose>
								<c:when test='${currentPage == i }'>
									<li class="selected">${i }</li>
								</c:when>
								<c:when test='${i > pageCount }'>
									<li>${i }</li>
								</c:when>
								<c:otherwise>
									<li><a href="/mysite/board?a=list&p=${i }&kwd=${keyword }">${i }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						
						<c:if test='${nextPage > 0 }'>
							<li><a href="/mysite/board?a=list&p=${nextPage }&kwd=${keyword }">▶</a></li>
						</c:if>
					</ul>
				</div>
				 --%>
					
				<c:if test="${authUser != null }">
					<div class="bottom">
						<a href="/mysite/board?a=writeform" id="new-book">글쓰기</a>
					</div>
				</c:if>				
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div><!-- /container -->
</body>
</html>		
		
