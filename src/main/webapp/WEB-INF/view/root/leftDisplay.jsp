<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>


<style>
/* 3.2. 리스트를 구성할 스타일 시트 */
.itemBox {
	border: solid 1px black;
	width: 400px;
	height: 50px;
	padding: 10px;
	margin-bottom: 10px;
}

.itemBoxHighlight {
	border: solid 1px black;
	width: 400px;
	height: 50px;
	padding: 10px;
	margin-bottom: 10px;
	background-color: yellow;
}

.deleteBox {
	float: right;
	display: none;
	cursor: pointer;
}

.beginEmpty {
	min-height: 50px;
}

.hrcolorwhite {
	color: white; /* IE */
	border-color: white; /* 사파리 */
	background-color: white; /* 크롬, 모질라 등, 기타 브라우저 */
}
</style>
<h1>&nbsp;</h1>
<hr class="hrcolorwhite">
<!-- leftDisplay view는 로그인 시 나오는 화면 좌측 카테고리 view -->

<!-- 카테고리가 필요없는 Uri=/index/channels 주소에서는 안나오도록 설정  -->
<c:if test="${Uri != '/index/channels'}">
	<c:choose>
		<c:when test="${empty principal}">
			<!-- 로그인 정보가 없다면 출력내용 없음 -->
		</c:when>
		<c:otherwise>
			<!-- 카테고리 전체 형상을 부트스트랩 card를 이용해서 생성 -->
			<div class="card" style="width: 18rem;">

				<!--------------------------- 카테고리 nav 메뉴(1. MyBlog, 2. 채널) 시작 -------------------------->
				<ul class="nav nav-tabs">
					<!--------------------------- 1. MyBlog : 채널 페이지가 아닌 곳에서는 MyBlog 메뉴가 항상 나온다. ---------------------->
					<li class="nav-item"><a class="longTextCut nav-link ${(fn:contains(Uri, '/channels'))?'':'active'}" aria-current="page" href="/index/category"><b>MyBlog</b></a></li>

					<!-- (B*)채널 카테고리 종류 foreach 출력 위함 -->
					<c:set var="List" value="${getCategoryList}"></c:set>

					<!-- (A*)아래 MyBlog 메뉴, getCategoryList 출력 -->
					<c:set var="saveCategoryURI" value='/category/saveCategoryName'></c:set>
					<c:set var="deleteCategoryURI" value='/category/deleteCategoryName'></c:set>

					<!--------------------------- 2. 채널(내 채널, 타인 채널) -------------------------->
					<c:choose>

						<c:when test="${fn:contains(Uri, '/channels')}">
							<!-- 내가 접속한 채널명(내 채널이던지 타인 채널이던지) 메뉴에 표기해주기 -->
							<li class="nav-item"><a class="longTextCut nav-link ${(fn:contains(Uri, '/channels'))?'active':''}" href="/index/channels/${channelName}"> ${channelName}</a></li>
							<input type="hidden" id="title" value="${channelName}" />

							<!-- (B*)채널 카테고리 종류 foreach 출력 위함 -->
							<c:set var="List" value="${getChannelCategoryList}"></c:set>

							<!-- (A*)아래 2줄은 자바스크립트에 $.ajax통신 URL경로 설정용 -->
							<c:set var="saveCategoryURI" value='/channel/saveChannelCategoryName'></c:set>
							<c:set var="deleteCategoryURI" value='/channel/deleteChannelCategoryName'></c:set>
						</c:when>
						<c:when test="${empty getChannelName}">
							<!-- 내 채널 미 개설시 공백 -->
						</c:when>
						<c:otherwise>
							<!-- 타 채널이 아닐 시 항상 내가 개설한 채널명 표기 -->
							<li class="nav-item"><a class="longTextCut nav-link " href="/index/channels/${getChannelName}"> ${getChannelName}</a></li>
						</c:otherwise>
					</c:choose>
				</ul>

				<!-- (A*)위에서 받아온 set값에 따라 Blog or channel용이냐에 따라 자바스크립트에 $.ajax통신 URL 경로 바뀜 -->
				<input type="hidden" id="deleteCategoryURI" value='${deleteCategoryURI}' /> <input type="hidden" id="saveCategoryURI" value='${saveCategoryURI}' />

				<!--------------------------- 카테고리 nav 메뉴(1. MyBlog, 2. 채널) 끝 -------------------------->

				<!----------------------------        카테고리 List 시작         -------------------------------->
				<div class="card-body">
					<ul class="list-group" id="sortable">

						<!-- DB에서 가져온 카테고리 정보 forEach문으로 나열 -->
						<c:choose>
							<c:when test="${fn:contains(Uri, '/channels')}">
								<c:set var="categoryLink" value="/index/channels/${channelName}"></c:set>
							</c:when>
							<c:otherwise>
								<c:set var="categoryLink" value="/index/category"></c:set>
							</c:otherwise>
						</c:choose>

						<!-- (B*)채널 카테고리 종류 foreach 출력 -->
						<c:forEach var="List" items="${List}">
							<a class="NoUnderline" href="${categoryLink}?c=${List.categoryName}">
								<li class="list-group-item ${(List.categoryName == categoryName)?'active':''}"><input type="checkbox" id="deleteCategory" name="deleteCategory" value="${List.no}" /> ${List.categoryName}
							</li>
							</a>
						</c:forEach>

						<!-- 카테고리 없음 출력용 -->
						<a class="NoUnderline" href="${categoryLink}?c=없음"><li class="list-group-item ${(categoryName == '없음')?'active':''}">카테고리 없음</li></a>
					</ul>
					<c:if test="${getChannelName==channelName or fn:contains(Uri, '/category')}">
						<!-- onclick -> category.js 자바 함수 실행 -->
						<input type="button" id="addItem" value="추가" onclick="createItem();" />
						<input type="button" id="btn_deleteCategory" name="btn_deleteCategory" value="삭제" onclick="btn_deleteCategory();" />
						<input type="button" id="submitItem" value="확인" onclick="submitItem();" />

						<c:if test="${!empty getChannelName and fn:contains(Uri, '/channels')}">
							<input type="button" id="deleteChannel" value="채널삭제" />
						</c:if>
						<!-- 채널 삭제용 -->
						<input type="hidden" id="deleteChannelName" value='${getChannelName}' />


						<!-- id="itemBoxWrap"는 카테고리 리스트 추가할 때 자바스크립트와 관련된 코드 -->
						<div id="itemBoxWrap"></div>
					</c:if>
				</div>
				<!----------------------------        카테고리 List 끝         -------------------------------->
			</div>
		</c:otherwise>
	</c:choose>
</c:if>

