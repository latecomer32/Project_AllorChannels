$("#btn_submit").on("click", function (e) {
  e.preventDefault();
  if (confirm("정말 회원탈퇴하시겠습니까?. 탈퇴시 게시글이 전부 삭제됩니다.")) {
    //확인 누르면 true, 취소 누르면 false

    $.ajax({
      type: "POST",
      url: "/header/cancel/membership",
    })
      .done(function (resp) {
        alert("탈퇴가 완료되었습니다.");
        location.href = "/logout";
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  } else {
    return false;
  }
});
