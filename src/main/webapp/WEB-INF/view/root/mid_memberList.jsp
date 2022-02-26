<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="middeDisplay">
	<style>

td {
	max-height: 30px !important;
}
</style>


	<div class="container-lg whiteBackground ">
		<div class="ListHeight">
			<form>
				<div class="container">
					<div class="row justify-content-start">

						<select class="col-2" name="order">
							<option ${(param.order=="no")?"selected":"" } value="no">번호</option>
							<option ${(param.order=="userId")?"selected":"" } value="title">아이디</option>
							<option ${(param.order=="nickName")?"selected":"" } value="nickName">닉네임</option>
							<option ${(param.order=="role")?"selected":"" } value="role">등급</option>
							<option ${(param.order=="oauth")?"selected":"" } value="oauth">소셜가입</option>
							<option ${(param.order=="date")?"selected":"" } value="date">가입일</option>

						</select> <select class="col-2" name="desc">
							<option ${(param.desc=="DESC")?"selected":"" } value="DESC">내림차순</option>
							<option ${(param.desc=="ASC")?"selected":"" } value="ASC">오름차순</option>
						</select>

						<div class="col-2">&nbsp;</div>

						<select class="col-1" name="r">
							<option ${(param.r=="15")?"selected":"" } value="15">15개</option>
							<option ${(param.r=="10")?"selected":"" } value="10">10개</option>
							<option ${(param.r=="20")?"selected":"" } value="20">20개</option>
							<option ${(param.r=="30")?"selected":"" } value="30">30개</option>
							<option ${(param.r=="50")?"selected":"" } value="50">50개</option>
							<option ${(param.r=="100")?"selected":"" } value="100">100개</option>
						</select> <select class="col-2" name="f">
							<option ${(param.f=="no")?"selected":"" } value="no">번호</option>
							<option ${(param.f=="userId")?"selected":"" } value="title">아이디</option>
							<option ${(param.f=="nickName")?"selected":"" } value="nickName">닉네임</option>
							<option ${(param.f=="role")?"selected":"" } value="role">등급</option>
							<option ${(param.f=="oauth")?"selected":"" } value="nickName">소셜가입</option>
							<option ${(param.f=="date")?"selected":"" } value="date">가입일</option>


						</select> <input class="col-2" type="text" name="q" value="${param.q}" />
						<button type="submit" class="btn btn-primary col-1">검색</button>
					</div>
				</div>
			</form>

			<form>
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<!-- <th scope="col">
								<div class="form-check">
									<input class="form-check-input" type="checkbox" value="all" id="flexCheckDefault" name="del_member">
								</div>
							</th> -->
							<th scope="col">번호</th>
							<th scope="col">닉네임</th>
							<th scope="col">등급</th>
							<th scope="col">가입일</th>

						</tr>
					</thead>

					<tbody>
						<c:forEach var="getMemberList" items="${getMemberList}">
							<tr>
								<%-- <td>
									<div class="form-check">
										<input class="form-check-input" type="checkbox" value="${getMemberList.no}" id="no" name="no">
									</div>
								</td> --%>
								<td scope="row">${getMemberList.no}</td>
								<td><a class="NoUnderline" href="/board/detail/${getMemberList.no}">${getMemberList.nickName}</a></td>
								<td>${getMemberList.role}</td>
								<td>${getMemberList.date}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>

		<div>
			
			<!-- <button id="btn-delete" class="btn btn-primary col-1">글 삭제</button> -->
			
			<c:set var="row" value="${(empty param.r)?15:param.r}" />
			<c:set var="page" value="${(empty param.p)?1:param.p}" />
			<c:set var="startNum" value="${page-(page-1)%5}" />
			<c:set var="lastNum" value="${fn:substringBefore(Math.ceil(getMemberCount/row),'.')}" />

			<div class="row justify-content-end">
				<div class="col-sm text-center"></div>
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

				<div class="col-sm text-right">
					현재 페이지 <span>${(empty param.p)?1:param.p}</span> / ${lastNum} pages
				</div>

			</div>
		</div>
	</div>
</div>
