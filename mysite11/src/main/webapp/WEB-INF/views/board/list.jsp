 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

 

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<title>Mysite</title>

<script>

$(function(){
 
	
	$("#kwd")

	$('input[type="date"]').hide();

	$("select[name=keyField]").change(function(){
		if(this.value == 'regDate'){
			$('input[type="date"]').show();
			$('#kwd').hide();
		}else{
			$('input[type="date"]').hide();
			$('#kwd').show();
		}
			
	});
	
 
});
 

  
</script>


<script type="text/javascript">

function check(){
	if(document.dateSearch.kwd.value=="" && document.dateSearch.startDate.value==""){
		alert("검색어를 입력하세요")
	}else{
		document.dateSearch.submit();
	}
}

</script>

</head>

 


<body>



	<div id="container">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="content">
			<div id="board">
				<form id="search_form" action="/mysite/board?keyword=${keyword}&startDate=${startDate}&endDate=${endDate}&keyField=${keyField}" method="get" name="dateSearch">
					<input type="hidden" name="a" value="list">
					
					<div>
					
	        <select name="keyField" id="keyField" size="1" >
    				<option value="name" id="name">작성자</option>		
    				<option value="title" id="title">제목</option>
    				<option value="content" id="content">내용</option>
    				<option value="regDate" id="regDate" >작성일시</option>	 
   	     	</select>
    				    <input type="date" id="startDate" name="startDate" value="${startDate }" /> 
    				    <input type="date" id="endDate" name="endDate" value="${endDate }"/>
    				     
   				 
					<input type="text" id="kwd" name="kwd" value="${keyword }">
					<input type="button"   value="찾기" onclick="javascript:check()">
					</div>
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
					
					
					<c:forEach items="${list }" var="vo">
					<tr>
							 <td>${vo.no }</td>
							<td><a href="/mysite/board?a=read&no=${vo.no}">${vo.title }</a></td>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							
							<td>
						
								<c:if test="${authUser.no == vo.userNo }">
									<a href="/mysite/board?a=delete&no=${vo.no }" class="del">삭제</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
					
				
				</table>
				
			 
				  <div class="pager">
					<div class="pager">
						<ul>
							 
							 <li><a href="">◀</a></li>
							<c:forEach var="i" begin="1" end="10">
							 <li class="selected"><a href="/mysite/board?a=list&pageNum=${i}" > ${i}</a></li>			         	 
							</c:forEach>
							<li><a href="">▶</a></li>

							 
						</ul>
 	 	 
				</div>

					
				<c:if test="${authUser != null }">
					<div class="bottom">
						<a href="/mysite/board?a=writeform" id="new-book">글쓰기</a>
					</div>
				</c:if>				
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	 
	</div><!-- /content -->
	</div><!-- /container -->
</body>

 
</html>		
	