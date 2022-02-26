<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<style>
.joinform {
	margin-top: 50px;
}

.form-floating>.form-control, .form-floating>.form-select {
	height: 80px;
	line-height: 1.25;
}

.form-control {
	border: 1px solid rgb(255, 242, 242);
	border-radius: 0.35rem;
}

:root { -
	-bs-body-line-height: 0.5;
}
</style>



<div class="Display middeDisplay">

	<div class="container">
		<form method="post">
			<c:choose>
				<c:when test="${empty channelName}">
					<input type="hidden" id="channelName" value=""/>
					채널명 : <input id="channelName" value="없음" disabled/>
				</c:when>
				<c:otherwise>
					<input type="hidden" id="channelName" value="${channelName}"/>
					채널명 : <input id="channelName" value="${channelName}" disabled/>

				</c:otherwise>
			</c:choose>


			<div class="form-group">
				제목 : <input type="text" placeholder="Enter title" class="title" id="title" value="${board.title}">
			</div>


			<c:choose>
				<c:when test="${fn:contains(Uri, '/channels')}">
					<c:set var="List" value="${getChannelCategoryList}"></c:set>
				</c:when>
				<c:otherwise>
					<c:set var="List" value="${getCategoryList}"></c:set>
				</c:otherwise>
			</c:choose>
			카테고리 : <select id="categoryName" name="categoryName">
				<option value="${board.categoryName}" selected>${board.categoryName}</option>
				<option value="없음">카테고리 없음</option>
				<c:forEach var="List" items="${List}">
					<option value="${List.categoryName}">${List.categoryName}</option>
				</c:forEach>

			</select>

			<div class="form-group">
				내용
				<textarea id="summernote" name="editordata" >${board.content}</textarea>
			</div>

		</form>
		
		<input type="hidden" id="boardNo" value="${board.no}"/>
		
		<button id="btn-updateTheWriting" class="btn btn-primary">글 저장</button>
	</div>
</div>

<!-- summer note -->
<script>
	$(document).ready(function() {
		//여기 아래 부분
		$('#summernote').summernote({
			height : 300, // 에디터 높이
			minHeight : null, // 최소 높이
			maxHeight : null, // 최대 높이
			focus : true, // 에디터 로딩후 포커스를 맞출지 여부
			lang : "ko-KR", // 한글 설정
			placeholder : '최대 2048자까지 쓸 수 있습니다' //placeholder 설정

		});
	});
</script>




