function btn_saveChannel() {
  (channelName = $("#channelName").val()), console.log(channelName);
  $.ajax({
    type: "POST",
    url: "/header/save/channel",
    data: channelName,
    contentType: "application/json; charset=utf-8",
    dataType: "text",
  })
    .done(function (resp) {
      alert("채널 개설 완료.");
      location.href = "/index";
    })
    .fail(function (error) {
      alert(JSON.stringify(error));
    });
}

$("#deleteChannel").on("click", function (e) {
  e.preventDefault();
  if (
    confirm("정말 채널을 삭제하시겠습니까? 채널관련 게시글이 모두 삭제됩니다.")
  ) {
    //확인 누르면 true, 취소 누르면 false
    (deleteChannelName = $("#deleteChannelName").val()),
      console.log(deleteChannelName);
    $.ajax({
      type: "POST",
      url: "/left/delete/channel",
      data: deleteChannelName,
      contentType: "application/json; charset=utf-8",
      dataType: "text",
    })
      .done(function (resp) {
        alert("채널 삭제 완료");
        location.href = "/index";
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  }
});
