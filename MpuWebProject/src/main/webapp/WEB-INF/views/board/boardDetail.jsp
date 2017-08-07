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
	<link	href="<%=application.getContextPath()%>/resources/bootstrap-3.3.7/css/bootstrap.min.css"
			rel="stylesheet" type="text/css" />
	<script	src="<%=application.getContextPath()%>/resources/jquery/jquery-3.2.1.min.js"
				type="text/javascript"></script>
	<script	src="<%=application.getContextPath()%>/resources/bootstrap-3.3.7/js/bootstrap.min.js"
				type="text/javascript"></script>			
	<script>
		function handleBtnUpdate() {
			var bpassword = $("#bpassword").val();
			if( bpassword =="") {
				$("#bpassword").attr("placeholder", "비밀번호를 입력하셔야 합니다.");
				$("#bpassword").focus();
				return ;
			}
			$.ajax({
				url: "boardCheckBpassword",
				method: "post",
				// data: "bno=${board.bno}&bpassword="+bpassword,
				data: {"bno":"${board.bno}", "bpassword":bpassword},
				success: function(data) {
					if(data.result =="success") {
						// console.log("success");
						location.href="boardUpdate?bno=${board.bno}&pageNo=${pageNo}";
					} else {
						$("#bpassword").val("");
						$("#bpassword").attr("placeholder", "비밀번호를 입력하셔야 합니다.");
						$("#bpassword").focus();
					}
				}
			});
		}
		function handleBtnDelete() {
			var bpassword = $("#bpassword").val();
			if( bpassword =="") {
				$("#bpassword").attr("placeholder", "비밀번호를 입력하셔야 합니다.");
				$("#bpassword").focus();
				return ;
			}
			$.ajax({	
				url: "boardCheckBpassword",
				method: "post",
				data: {"bno":"${board.bno}", "bpassword":bpassword},
				success: function(data) {
					if(data.result =="success") {
						console.log("success");
						location.href="boardDelete?bno=${board.bno}";
					} else {
						$("#bpassword").val("");
						$("#bpassword").attr("placeholder", "비밀번호를 입력하셔야 합니다.");
						$("#bpassword").focus();
					}
				}
			});
		}
		function handleBtnLike() {
			location.href="boardLike?bno=${board.bno}&pageNo=${pageNo}";		
		}
		
		function handleBtnComment(){
			/* 로그인 관련 */
			/* if( $("#btitle").val() == "") {
				$("#btitle").attr("placeholder", "제목을 입력하세요!");
				$("#btitle").focus();
				return ;
			} else */ 

			if( $("#bcpassword").val() == "") {
				$("#bcpassword").attr("placeholder", "비밀번호");
				$("#bcpassword").focus();
				return ;
			} else if( $("#bccomment").val() == "") {
				$("#bccomment").val("");
				$("#bccomment").attr("placeholder", "내용을 입력하세요!");
				$("#bccomment").focus();
				return ;
			} 
			return $("#form1").submit();
		}
	</script>
</head>
<body>
	<div style="max-width: 1000px; margin: auto;">
	<h4>${board.btitle}</h4>
	<hr />
	<form method="post"  id="form1" action="boardCommentWrite" style="padding: 0px 20px"
		enctype="multipart/form-data">
		<div class="form-group">
			<div class="input-group">
				<input type="text" class="form-control" placeholder="번호"	name="bno"  value="${board.bno}" style="background-color: white; width:50px;]" readonly/>
				<span class="input-group-addon"> <span class="glyphicon glyphicon-user"></span></span>
				<input type="text" class="form-control" placeholder="작성자"	name="bwriter"  value="${board.bwriter}" style="background-color: white;" readonly/>
				<span class="input-group-addon"> <span class="glyphicon glyphicon-heart"></span></span>
				<input type="text" class="form-control" placeholder="추천수"	name="blikecount"  value="${board.blikecount}"  style="background-color: white;" readonly/>
				<span class="input-group-addon"> <span class="glyphicon glyphicon-eye-open"></span></span>
				<input type="text" class="form-control" placeholder="조회수"	name="bhitcount"  value="${board.bhitcount}"  style="background-color: white;" readonly/>
				<span class="input-group-addon"> <span class="glyphicon glyphicon-time"></span></span>
				<fmt:formatDate var="bdate" value="${board.bdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				<input type="text" class="form-control" placeholder="날짜"	 name="bdate"  value="${bdate}" style="background-color: white;" readonly/>
			</div>
		</div>

		<div class="form-group" style="text-align: center;">
			<c:if test='${board.boriginalfilename != "" && board.boriginalfilename != null}'>
				<img src="boardImage?bno=${board.bno }" style="max-width: 980px;"/>
			</c:if>
		</div>
		<div class="form-group" style="text-align: center;">			
			<p class="text-justify" >${board.bcontent}</p>
		</div>
		<div class="form-group" style="text-align: center;">
			<div class="btn btn-danger" style="border-radius: 50px; width: 100px; height: 100px; line-height: 30px;" onclick="handleBtnLike()">
				${board.blikecount}<br/>
				<img src="../resources/image/thumbsup.png" style="width: 30px; height: 30px;"/>
			</div>			
		</div>
		<hr/>
		<div class="form-group" align="right">
			<input type="password" id="bpassword" placeholder="비밀번호"	name="bpassword"  style="width: 150px; height: 33px;"  maxlength="10"/>
			<a href="boardList?pageNo=${pageNo}" class="btn btn-primary" >목록</a>
			<input type="button" class="btn btn-warning" value="수정"  onclick="handleBtnUpdate()" />
			<input type="button" class="btn btn-danger" value="삭제"    onclick="handleBtnDelete()"/>			
		</div>		
		<hr/>
		<!-- 댓글 리스트 -->
		<table class="table table-bordered table-striped table-hover ">
			<c:forEach var="comment" items="${list}" varStatus="status">
				<fmt:formatDate var="bcdateDay" value="${comment.bcdate}" pattern="yyyy-MM-dd"/>
				<fmt:formatDate var="bcdateTime" value="${comment.bcdate}" pattern="HH:mm:ss"/>
					<tr >				
						<td style="width: 15%; ">${comment.bcwriter}</td>
						<td style="width: 70%"><p>${comment.bccomment}</p></td>
						<td style="width: 15%; text-align: right">${bcdateDay}<br/>${bcdateTime}</td>
					</tr>			
			</c:forEach>
		</table>
		<!-- 댓글 -->
		<div class="form-group">
			<div class="input-group">
				<span class="input-group-addon"> <span
					class="glyphicon glyphicon-pencil"></span>
				</span>
				<textArea rows="5" cols="30" class="form-control" placeholder="내용" 
					name="bccomment" id="bccomment"></textArea>
				<!-- 세션 아이디 -->
				<input type="hidden" class="form-control"  name="bcwriter"  id="bcwriter" value="admin"/>				
				<input type="hidden" class="form-control"  name="pageNo"  id="pageNo" value="${pageNo}"/>			
			</div>
		</div>
		<div class="form-group" align="right">
			<input type="password" id="bcpassword" placeholder="비밀번호"	name="bcpassword"  style="width: 150px; height: 33px;"  maxlength="10"/>
			<input type="button" class="btn btn-success" value="등록"  onclick="handleBtnComment()" />		
		</div>
	</form>
	</div>
</body>
</html>