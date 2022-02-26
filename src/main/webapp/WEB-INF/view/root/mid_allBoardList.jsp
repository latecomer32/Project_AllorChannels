<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
table.tmp_table tr {
	height: 53px;
	text-align: center;
	vertical-align: middle; /* tr 안에서 세로 정렬 방법 top, middle, bottom */
	border-bottom: 1px dotted rgb(218, 218, 218);
}

table.tmp_table th {
	
}

.grayFont_th {
	color: rgb(150, 150, 150);
	font-size: 0.8rem;
}

.grayFont_td {
	color: rgb(150, 150, 150);
	font-size: 0.9rem;
}

.titleFont_td {
	color: rgb(70, 70, 70) !important;
	font-size: 1rem;
}

.channelName {
	max-width: 120px;
	main-width: 50px;
	max-height: 37px;
	margin: 2px 3px;
}
</style>

<c:choose>
	<c:when test="${fn:contains(Uri, '/category')}">
		<h1>MyBlog</h1>
		<hr>
	</c:when>
	<c:when test="${fn:contains(Uri, '/channels')}">
		<h1>${channelName}</h1>
		<hr>
	</c:when>
	<c:otherwise>
		<h1>All</h1>
		<hr>
	</c:otherwise>

</c:choose>

<!----------------------- 글 상세 보기 시작 ----------------------->
<!-- "/index/board/detail/{no}" no값이 null이 아닐때-->
<!-- !empty board.title를 조건으로 한건 
	  MySQL board 테이블에서 no컬럼은 자동증가로 값이 조건하므로 empty가 아니다.
      하지만 board.title은 값이 없으면 empty이므로 
      board.title를 특정해서 empty이면 출력하지 않도록 설정 -->
<c:if test="${!empty board.title}">
	<table class="table">
		<tbody>
			<tr>
				<td class="table-active">글 번호</td>
				<td>${board.no}</td>
				<td class="table-active">글 제목</td>
				<td>${board.title}</td>
			</tr>
			<tr>
			<tr>
				<td class="table-active">작 성 자</td>
				<td>${board.nickName}</td>
				<td class="table-active">작 성 일</td>
				<td><fmt:formatDate pattern="yy/MM/dd" value="${board.date}" var="date_year" /> <fmt:formatDate pattern="hh:mm:ss" value="${board.date}" var="date_hour" /> <c:choose>
						<c:when test="${date_year == today_str_year}">
							<c:out value="${date_hour}" />
						</c:when>
						<c:otherwise>
							<c:out value="${date_year}" />
						</c:otherwise>
					</c:choose></td>
			</tr>
			<tr>
				<td class="table-active NoUnderline">본문</td>
				<td class="NoUnderline"></td>
				<td class="table-active">조 회 수</td>
				<td>${board.viewCount}</td>
			</tr>

			<tr>
				<td class="NoUnderline" colspan="4"></td>
			</tr>

			<tr>
				<td colspan="4"><div class="min_content_height">${board.content}</div></td>
			</tr>
		</tbody>
	</table>
	<c:if test="${nickName==board.nickName}">
		<c:choose>
			<c:when test="${fn:contains(Uri, '/channels')}">
				<c:set var="updateUri" value="/index/channels/detail/${channelName}/${board.no}/update"></c:set>
			</c:when>
			<c:when test="${fn:contains(Uri, '/category')}">
				<c:set var="updateUri" value="/index/category/detail/${board.no}/update"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="updateUri" value="/index/board/detail/${board.no}/update"></c:set>
			</c:otherwise>
		</c:choose>
				<a href="${updateUri}" class="btn btn-primary col-2 d-flex justify-content-start"">글 수정</a>
	</c:if>
	<hr>
</c:if>
<!-------------------------- 글 상세 보기 끝 --------------------------->


<!-- 게시판 페이징 변수 설정 -->
<c:set var="row" value="${(empty param.r)?15:param.r}" />
<c:set var="page" value="${(empty param.p)?1:param.p}" />
<c:set var="startNum" value="${page-(page-1)%5}" />
<c:set var="lastNum" value="${fn:substringBefore(Math.ceil(getWritingCount/row),'.')}" />
<!---------------------- 전체 게시글 시작 --------------------------------->
<table class="tmp_table table table-hover table-borderless">
	<thead>
		<tr>
			<c:if test="${!empty nickName}">
				<th scope="col">
					<div class="form-check">
						<input class="form-check-input" type="checkbox" value="btn_allDelete" id="flexCheckDefault" name="del_member" onclick="btn_allDelete(this);">
					</div>
				</th>
			</c:if>
			<th class="grayFont_th" scope="col">번호</th>
			<th class="grayFont_th" scope="col">제목</th>
			<th class="grayFont_th" scope="col">작성자</th>
			<th class="grayFont_th" scope="col">채널</th>
			<th class="grayFont_th" scope="col">카테고리</th>
			<th class="grayFont_th" scope="col">작성일</th>
			<th class="grayFont_th" scope="col">조회수</th>
		</tr>
	</thead>

	<tbody>
		<form>
			<c:forEach var="getWritingList" items="${getWritingList}">
				<tr>
					<c:if test="${!empty nickName}">
						<td>
							<div class="form-check">

								<input class="form-check-input" type="${(getWritingList.nickName==nickName)?'checkbox':'hidden'}" value="${getWritingList.no}" id="no" name="no"
									${(getWritingList.nickName==nickName)?'':'disabled'} />
							</div>
						</td>
					</c:if>

					<td class="grayFont_td" scope="row">${getWritingList.no}</td>

					<%-- 게시글 제목 링크 클릭시 이벤트 시작 --%>
					<c:choose>
						<%-- URI가 /index/channels/${channelName},/index/channels/${channelName}/${no} 2개 경우가 있다. --%>
						<c:when test="${fn:contains(Uri, '/channels')}">
							<c:set var="boardHref" value="/index/channels/detail/${channelName}/${getWritingList.no}?c=${categoryName}"></c:set>
						</c:when>
						<c:when test="${fn:contains(Uri, '/category')}">
							<c:set var="boardHref" value="/index/category/detail/${getWritingList.no}?c=${categoryName}"></c:set>
						</c:when>
						<c:otherwise>
							<c:set var="boardHref" value="/index/board/detail/${getWritingList.no}"></c:set>
						</c:otherwise>
					</c:choose>
					<td><a class="titleFont_td NoUnderline" href="${boardHref}"><div>${getWritingList.title}</div></a></td>
					<%-- 게시글 제목 링크 클릭시 이벤트 끝 --%>

					<td class="titleFont_td">${getWritingList.nickName}</td>

					<td class="titleFont_td"><a class="titleFont_td NoUnderline" href="/index/channels/${getWritingList.channelName}"><div>${getWritingList.channelName}</div></a></td>


					<!-- 7. 카테고리 구현은 주소매핑에 닉네임값이 필요해보임 그래서 아직 활성화하지 않음 -->
					<c:choose>
						<%-- URI가 /index/channels/${channelName},/index/channels/${channelName}/${no} 2개 경우가 있다. --%>
						<c:when test="${getWritingList.channelName !=''}">
							<c:set var="categoryHref" value="/index/channels/${getWritingList.channelName}/${getWritingList.no}?c=${getWritingList.categoryName}"></c:set>
						</c:when>
						<c:when test="${getWritingList.channelName ==''}">
							<c:set var="categoryHref" value="/index/category/${getWritingList.no}?c=${getWritingList.categoryName}"></c:set>
						</c:when>
						<c:otherwise>
							<c:set var="categoryHref" value="/index/board/detail/${getWritingList.no}"></c:set>
						</c:otherwise>
					</c:choose>
					<%-- <a class="titleFont_td NoUnderline" href="${categoryHref}"></a> --%>
					<td class="titleFont_td"><div>${getWritingList.categoryName}</div></td>





					<td class="grayFont_td"><fmt:formatDate pattern="yy/MM/dd" value="${getWritingList.date}" var="date_year" /> <fmt:formatDate pattern="hh:mm:ss" value="${getWritingList.date}"
							var="date_hour" /> <c:choose>
							<c:when test="${date_year == today_str_year}">
								<c:out value="${date_hour}" />
							</c:when>
							<c:otherwise>
								<c:out value="${date_year}" />
							</c:otherwise>
						</c:choose></td>
					<td class="grayFont_td">${getWritingList.viewCount}</td>
				</tr>
			</c:forEach>
		</form>
	</tbody>
</table>


<!-- 위의 form 정보를 id="btn-delete" 기반하여 자바스크립트 함수 실행 -->
<c:if test="${!empty nickName}">
	<button id="btn-delete" class="btn btn-primary col-2 d-flex justify-content-start" >글 삭제</button>
</c:if>

<!-- 현재 페이지 / 전체 페이지 -->
<span class="d-flex justify-content-end"> 현재 페이지 <span>${(empty param.p)?1:param.p}</span> / ${lastNum} pages
</span>

<div class="col-sm text-center">

	<c:if test="${startNum>1}">
		<a href="?p=${startNum-1}&f=${param.f}&q=${param.q}&r=${row}" class="btn">이전</a>
	</c:if>
	<c:if test="${startNum<=1}">
		<span class="btn" onclick="alert('이전 페이지가 없습니다.');">이전</span>
	</c:if>

	<c:forEach var="i" begin="0" end="4">
		<c:if test="${(startNum+i)<= lastNum}">
			<a class="-text-" style="${(page==(startNum+i))?'color:orange;':''}" href="?p=${startNum+i}&f=${param.f}&q=${param.q}&r=${row}&order=${param.order}&=desc=${param.desc}"> ${startNum+i}</a>
		</c:if>
	</c:forEach>

	<c:if test="${startNum+5<=lastNum}">
		<a href="?p=${startNum+5}&f=${param.f}&q=${param.q}&r=${row}" class="btn btn-next">다음</a>
	</c:if>
	<c:if test="${startNum+4>=lastNum }">
		<span class="btn" onclick="alert('다음 페이지가 없습니다.');">다음</span>
	</c:if>
</div>


