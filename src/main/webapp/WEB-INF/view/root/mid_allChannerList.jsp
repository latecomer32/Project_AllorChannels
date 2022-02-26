<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<c:when test="${Uri == '/index/channels'}">
<h1>Channels</h1>
<hr>
</c:when>

</c:choose>


<div class="">

<!-- 글 상세보기 구현-->
<!-- !empty board.title를 조건으로 한건 board의 no컬럼은 자동증가로 값이 조건하므로 empty가 아니다.
      하지만 board.title은 값이 없으면 empty이므로 없을땐 출력하지 않도록 설정 -->
<c:if test="${!empty board.title}">
<div>
	<div scope="row">${board.no}</div>
	<div>${board.title}</div>
	<div>${board.nickName}</div>
	<div>
		<fmt:formatDate pattern="yy/MM/dd" value="${board.date}" var="date_year" />
		<fmt:formatDate pattern="hh:mm:ss" value="${board.date}" var="date_hour" />
		<c:choose>
			<c:when test="${date_year == today_str_year}">
				<c:out value="${date_hour}" />
			</c:when>
			<c:otherwise>
				<c:out value="${date_year}" />
			</c:otherwise>
		</c:choose>
	</div>
	<div>${board.viewCount}</div>
	<div>${board.content}</div>
</div>
</c:if>

<!-- 글 상세보기 구현 끝 -->

	
		<!-- 각 채널 4행(반응시-> 2행,1행 변경) 그리드 설정 -->
		<div class="band--">

			<!-- 채널 개수만큼 아래 내용 반복 -->
			<c:forEach var="getChannelList" items="${getChannelList}">

				<div class="card" style="width: 18rem;">
					<!-- 채널 제목 생성 -->
					<a href="/index/channels/${getChannelList.title}" class="btn btn-primary channelName">${getChannelList.title}</a>

					<!-- getChannelWritingList : 채널별(a) 5개(b) 글 전체 리스트(axb).
					     getChannelWritingList를 채널 개수만큼 반복 돌리며 채널명에 일치하는 글만 출력 -->
					<table class="tmp_table table table-hover table-borderless">
						<c:forEach var="getChannelWritingList" items="${getChannelWritingList}">
							<c:if test="${getChannelList.title==getChannelWritingList.channelName}">
								<tbody>
									<tr>
										<td><a class="titleFont_td NoUnderline" href="/index/channels/detail/${getChannelList.title}/${getChannelWritingList.no}">${getChannelWritingList.title}</a></td>
										<td><fmt:formatDate pattern="yy/MM/dd" value="${getChannelWritingList.date}" var="date_year" /> <fmt:formatDate pattern="hh:mm:ss" value="${getChannelWritingList.date}" var="date_hour" />
											<c:choose>
												<c:when test="${date_year == today_str_year}">
													<c:out value="${date_hour}" />
												</c:when>
												<c:otherwise>
													<c:out value="${date_year}" />
												</c:otherwise>
											</c:choose></td>
									</tr>
								</tbody>
							</c:if>
						</c:forEach>
					</table>
				</div>
			</c:forEach>
		</div>



		<!-- 채널 페이징 설정 -->
		<c:set var="channelrow" value="${(empty param.cr)?15:param.cr}" />
		<c:set var="channelPage" value="${(empty param.cp)?1:param.cp}" />
		<c:set var="channelstartNum" value="${channelPage-(channelPage-1)%5}" />
		<c:set var="channellastNum" value="${fn:substringBefore(Math.ceil(getChannelCount/channelrow),'.')}" />

		<!-- 채널 페이지 숫자 화면 중앙 배치 -->
		<div class="d-flex justify-content-center">
			<c:forEach var="i" begin="0" end="4">
				<c:if test="${(channelstartNum+i)<= channellastNum}">
					<a class="-text-" style="${(channelPage==(channelstartNum+i))?'color:orange;':''}" href="?cp=${channelstartNum+i}&cq=${param.cq}&cr=${param.cr}"> ${channelstartNum+i}</a>
				</c:if>
			</c:forEach>
		</div>

</div>
