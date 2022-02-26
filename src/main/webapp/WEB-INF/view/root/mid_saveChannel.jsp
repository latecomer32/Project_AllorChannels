<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>


.joinform{
margin-top:50px;
}


.form-floating>.form-control, .form-floating>.form-select {
	height: 80px;
	line-height: 1.25;
}

.form-control {
	border: 1px solid rgb(255, 242, 242);
	    border-radius: 0.35rem;
}

:root {
--bs-body-line-height: 0.5;
}
</style>

<div class="Display middeDisplay">
	<div class="container-sm">
		<form name="" class="">
			<div class="form-floating mb-3">
				<input type="text" class="form-control" id="channelName" placeholder="channelName" required autofocus> <label for="floatingInput">채널명</label>
			</div>
			<br />
		</form>
		채널은 계정당 1개만 생성할 수 있습니다.
		이후 채널명 변경은 불가능합니다.
		<br>
		<br>
	
		<button class="btn btn-primary" name="SignUp" onclick="btn_saveChannel()">채널 개설</button>
	</div>
</div>



