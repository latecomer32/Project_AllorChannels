<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- layout에 설정하면 안 먹힘 -->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>



<style>
.header {
	box-shadow: 0px 0px 4px 0.3px gray !important; //
	min-height: 5000px !important;
	margin-bottom: 20px !important;
}

ul li a {
	line-height: 450%;
	color: black !important;
	marging:0px;
	padding:0px
}

ul li {
	font-size:11pt;
	marging:0px;
	padding:0px
}

.textWhite {
	color: white !important;
}

.icon- {
	width: 150% !important;
	color: rgba(155, 155, 155, 0.3);
}

.inline-- {
	display: inline;
}
</style>







<!-- -------<header> ---------- -->
<div class=" header d-flex justify-content-start  ">
	<div class="leftDisplay"></div>
	<div class="d-flex justify-content-between middleDisplay">

		<!-- 상단 좌측 Home or Channels -->
		<div class="marginTopBottomAuto">
			<a class="marginTopBottomAuto NoUnderline inline--" href="/index"><h5 class="inline--">
					<b>All</b>
				</h5></a> or <a class="marginTopBottomAuto NoUnderline" href="/index/channels"><h5 class="inline--">
					<b>Channels</b>
				</h5></a>
		</div>

		<!-- 상단 중앙부터 우측 -->
		<ul class="nav">
			<form class="d-flex flex-row" action="/index">

				<!-- 검색군 종류(제목, 내용, 닉네임) select -->
				<li class="nav-item marginTopBottomAuto"><select type="text" name="f">
						<option ${(param.f=="title")?"selected":"" } value="title">제목</option>
						<option ${(param.f=="content")?"selected":"" } value="content">내용</option>
						<option ${(param.f=="nickName")?"selected":"" } value="nickName">닉네임</option>
				</select></li>

				<!-- 검색할 내용 입력 엔터시 submit -->
				<li class="nav-item marginTopBottomAuto"><input type="text" name="q" value="${param.q}" onKeypress="javascript:if(event.keyCode==13) {search_onclick_submit}" /></li>

				<!-- Search 아이콘 -->
				<!-- <li class="nav-item marginTopBottomAuto"><input type="image" src="/image/search.svg" onclick="search_onclick_submit" class="submit icon-" /></li> -->
			</form>

			<c:choose>
				<c:when test="${empty principal}">
					<!-- 로그인 전 헤더 메뉴(로그인, 회원가입) -->
					<li class="nav-item"><a class="nav-link" data-bs-toggle="modal" data-bs-target="#exampleModal" href="/index">로그인</a></li>
					<li class="nav-item"><a class="nav-link" href="/auth/joinForm">회원가입</a></li>
				</c:when>

				<c:otherwise>
					<!-- 로그인 후 헤더 메뉴(글쓰기, 회원가입, 채널 만들기(채널명), 닉네임, 로그아웃) -->
					<c:choose>
						<c:when test="${fn:contains(Uri, '/channels')}">
							<c:set var="writingUri" value="/channels/saveTheWritingForm/${channelName}"></c:set>
						</c:when>
						<c:otherwise>
							<c:set var="writingUri" value="/board/saveTheWritingForm/"></c:set>
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test="${Uri=='/index/channels' }">
						</c:when>
						<c:otherwise>
							<li class="nav-item"><a class="nav-link" href="${writingUri}">글쓰기</a></li>
							
						</c:otherwise>
					</c:choose>
					<li class="nav-item"><a class="nav-link" href="/header/member">회원정보</a></li>
					<c:choose>
						<c:when test="${Uri =='/header/member'}">
						</c:when>
						<c:when test="${empty getChannelName}">
							<li class="nav-item"><a class="nav-link" href="/header/channel">채널 만들기</a></li>
						</c:when>
						<c:otherwise>
							<li class="nav-item marginTopBottomAuto"><a class="btn btn-primary textWhite longTextCut_header" href="/index/channels/${getChannelName}"}>${getChannelName}</a></li>
						</c:otherwise>
					</c:choose>

					<li class="nav-item marginTopBottomAuto btn btn-success">${principal.nickName}</li>
					<li class="nav-item"><a class="nav-link flex-row-reverse" href="/logout">로그아웃</a></li>
					<input type="hidden" id="cancelMembership"  value="${principal.nickName}"/>
					<li class="nav-item marginTopBottomAuto" ><button id="btn_submit" class="btn btn-primary">탈퇴</button></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>

	<div class="rightDisplay"></div>
</div>

<!-- -------모달 창 ---------- -->

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">

			<form action="/auth/loginProc" method="POST">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">로그인</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="form-floating mb-3">
						<input type="text" class="form-control" id="loginUserId" placeholder="Id" name="loginUserId" required autofocus> <label for="floatingInput">ID</label>
					</div>
					<div class="form-floating mb-3">
						<input type="password" class="form-control" id="loginPpassword" placeholder="Password" name="loginPpassword" required> <label for="floatingPassword">Password</label>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>

					<input type="submit" class="btn btn-primary" name="login" value="로그인"> <a href="/auth/joinForm" class="btn btn-secondary">회원가입</a>
				</div>
			</form>

		</div>
	</div>
</div>