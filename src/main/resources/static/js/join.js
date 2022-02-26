function joinCheck() {
  /* */

  if (document.join.joinUserId.value == "") {
    alert("아이디 입력하세요");
    document.join.joinUserId.focus();
    return false;
  }

  exp3 = /^[a-z0-9_]+$/;
  if (!exp3.test(document.join.joinUserId.value)) {
    alert("아이디를 영문과 숫자로만 입력하세요");
    document.join.joinUserId.focus();
    return false;
  }

  if (document.join.joinPassword.value == "") {
    alert("비밀번호를 입력하세요");
    document.join.joinPassword.focus();
    document.join.joinPassword.value = "";
    return false;
  }

  if (document.join.joinPassword.value.length < 4) {
    alert("비밀번호를 4자리 이상 입력하세요");
    document.join.joinPassword.focus();
    document.join.joinPassword.value = "";
    return false;
  }

  exp2 = /^[a-z0-9_]+$/;
  if (!exp2.test(document.join.joinPassword.value)) {
    alert("비밀번호를 영문과 숫자로만 입력하세요");
    document.join.joinPassword.focus();
    document.join.joinPassword.value = "";
    return false;
  }

  if (document.join.PasswordCheck.value == "") {
    alert("비밀번호를 다시 입력하세요");
    document.join.PasswordCheck.focus();
    document.join.PasswordCheck.value = "";
    return false;
  }

  if (document.join.joinPassword.value != document.join.PasswordCheck.value) {
    alert("비밀번호가 일치하지 않습니다.");
    document.join.joinPassword.focus();
    document.join.PasswordCheck.value = "";
    document.join.joinPassword.value = "";
    return false;
  }

  if (document.join.joinNickName.value == "") {
    alert("닉네임을 입력해주세요");
    document.join.joinNickName.focus();
    return false;
  }

  exp4 = /^[가-힣a-zA-Z0-9_]+$/;
  if (!exp4.test(document.join.joinNickName.value)) {
    alert("닉네임을 한글,영어,숫자로 입력해주세요");
    document.join.joinNickName.focus();
    return false;
  }

  let data = {
    //id값으로 찾아서 값 가져옴
    userId: $("#joinUserId").val(),
    nickName: $("#joinNickName").val(),
    password: $("#joinPassword").val(),
    email: $("#email").val(),
  };

  //console.log(data);

  // ajax호출시 default가 비동기 호출
  // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청!
  // ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해주네요.
  $.ajax({
    //회원가입 수행 요청
    type: "POST",
    url: "/auth/joinForm",
    data: JSON.stringify(data), //http body 데이터, JavaScript 값이나 객체를 JSON 문자열로 변환해준다
    contentType: "application/json; charset=utf-8", //body데이터가 어떤 타입인지(MIME)
    dataType: "json", // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면)=> javascript 오브젝트로 변경
  })
    .done(function (resp) {
      alert("회원가입이 완료되었습니다.");

      location.href = "/index";
    })
    .fail(function (error) {
      console.log("실패 data:", data); //console.log("실패 data:"+ data); 라고 쓰면 원하는대로 출력되지 않았었음.
      alert(JSON.stringify(error.responseText));
    }); //ajax 통신을 이용해서 3개의 데이터를 json으로 변경히여 insert 요청!!
}
