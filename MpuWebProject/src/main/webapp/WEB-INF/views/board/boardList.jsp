<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no">
<title>JSP Page</title>
<link
	href="<%=application.getContextPath()%>/resources/bootstrap-3.3.7/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />	
	<style>
	a {
		color: gray;
	}
	a:HOVER {
		color: red;
		text-decoration: none;
	}
	.glyphicon-th-list, .glyphicon-th-large {
	    font-size: 30px;
	}
	</style>
<script
	src="<%=application.getContextPath()%>/resources/jquery/jquery-3.2.1.min.js"
	type="text/javascript"></script>
<script
	src="<%=application.getContextPath()%>/resources/bootstrap-3.3.7/js/bootstrap.min.js"
	type="text/javascript"></script>
	<script>
	function handleBtnSearch() {
		location.href="boardSearch?btitle=${bsearch}";
	}
	</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/home.jsp" flush="false"></jsp:include>
	<div style="width: 1000px; margin: auto; text-align: center">
		<h4>게시물 목록</h4>
		<div style="width: 1000px; margin: auto; text-align: right">

		</div>
		<input type="hidden"  name="type" value="1"/>
		<table class="table table-bordered table-hover" 
			style="width: 1000px; text-align: center; border: 0px;">
			<tr class="info">
				<td style="width: 6%;  border-left: 0px; border-right: 0px">번호</td>
				<td style="width: 60%;  border-left: 0px; border-right: 0px">제목</td>
				<td style="width: 13%;  border-left: 0px; border-right: 0px">글쓴이</td>
				<td style="width: 9%;  border-left: 0px; border-right: 0px">날짜</td>
				<td style="width: 6%;  border-left: 0px; border-right: 0px">조회수</td>
				<td style="width: 6%;  border-left: 0px; border-right: 0px">추천수</td>
			</tr>
			<c:forEach var="b" items="${list}">
				<tr>
					<td style=" border-left: 0px; border-right: 0px">${b.bno}</td>
					<td style="text-align: left;  border-left: 0px; border-right: 0px">
						<a href="boardDetail?bno=${b.bno}&pageNo=${pageNo}" >
							${b.btitle}
							<c:if test="${b.count > 0}" >
							(${b.count})
							</c:if>
							<c:set var="now" value="<%= new java.util.Date() %>"/> 
							<fmt:formatDate var="today" value="${now}" pattern="yyyy-MM-dd"/>
							<fmt:formatDate var="bdate" value="${b.bdate}" pattern="yyyy-MM-dd"/>
							<c:if test="${bdate == today}"><img src="../resources/image/n.png" style="width: 15px"/></c:if>
							<c:if test="${b.blikecount >= 10}"><img src="../resources/image/hot.gif" style="width: 30px"/></c:if>
						</a>
					</td>					
					<td style="border-left: 0px; border-right: 0px">${b.bwriter}</td>
					<td style="border-left: 0px; border-right: 0px">${bdate}</td>
					<td style="border-left: 0px; border-right: 0px">${b.bhitcount}</td>
					<td style="border-left: 0px; border-right: 0px">${b.blikecount}</td>
				</tr>
			</c:forEach>
		</table>
<!-- 		<div  class="input-group"  style="margin-top: 20px; width: 1000px">
			<input type="text" name="bsearch" id="bsearch" placeholder="검색"	style="background-color: white; width:200px; height: 33px">			
			<a type="button"  class="btn btn-info"  href="boardSearch?bsearch=김">검색</a>
			<input type="button"  class="btn btn-info" value="검색 " href="boardList"  onclick="handleBtnSearch()" />
		</div> -->
		<div  class="input-group"  style="margin-top: 20px; width: 1000px">
			<a href="boardList?pageNo=1">[처음]</a>
			<c:if test="${groupNo>1}">
				<a href="boardList?pageNo=${startPageNo-1}">[이전]</a>
			</c:if>
			<c:forEach var="i" begin="${startPageNo}" end="${endPageNo}">			
				&nbsp;
				<a href="boardList?pageNo=${i}"
					<c:if test="${pageNo==i}">style="font-weight: bold; color: red"</c:if>>${i}</a>
				&nbsp;
			</c:forEach>

			<c:if test="${groupNo<totalGroupNo}">
				<a href="boardList?pageNo=${endPageNo+1}">[다음]</a>
			</c:if>
			<a href="boardList?pageNo=${totalPageNo}">[맨끝]</a>
		</div>
		<div style="margin-top: 10px; width: 700; text-align: right">
			<a href="boardWrite" class="btn btn-success">글쓰기</a>
		</div>

		<%-- <table class="table table-bordered table-hover" 
			style="width: 1000px; text-align: center; border: 0px;">
			<c:forEach var="b" items="${list}" begin="1" varStatus="status">
			<c:if test="${status.index%5 == 0}"><tr></c:if>
				
					<td style="line-height: 70px;  border-left: 0px; border-right: 0px">
					<div style="width: 200px;">
						<a href="boardDetail?bno=${b.bno}">
							<img src="boardImage?bno=${b.bno }" width="200px" height="200px"/>
							<span class="glyphicon glyphicon glyphicon-eye-open input-group"> ${b.btitle}</span>

							<c:set var="now" value="<%= new java.util.Date() %>"/> 
							<fmt:formatDate var="today" value="${now}" pattern="yyyy-MM-dd"/>
							<c:if test="${b.bdate == today}"><img src="../resources/image/new03.gif" style="width: 30px"/></c:if>
							<c:if test="${b.blikecount >= 10}"><img src="../resources/image/hot01.gif" style="width: 30px"/></c:if>
						</a>					
						<div class="glyphicon glyphicon glyphicon-eye-open input-group"> ${b.bhitcount}</div>
					</div>
					</td>
			<c:if test="${status.index%5 == 0}"></tr></c:if>
				
			</c:forEach> --%>

	</div>
</body>
</html>