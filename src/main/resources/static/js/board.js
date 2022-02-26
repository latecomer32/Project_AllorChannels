let index = {
  init: function () {
    $("#btn-saveTheWriting").on("click", () => {
      this.saveTheWriting();
    });
    $("#btn-delete").on("click", () => {
      this.deleteById();
    });
    $("#btn-updateTheWriting").on("click", () => {
      this.updateTheWriting();
    });
    $("#btn-reply-save").on("click", () => {
      this.replySave();
    });
    $("#btn-writing-filter").on("click", () => {
      this.writingFilter();
    });
  },

  saveTheWriting: function () {
    let data = {
      title: $(".title").val(),
      content: $("#summernote").val(),
      categoryName: $("#categoryName").val(),
      channelName: $("#channelName").val(),
    };
    console.log("saveTheWriting", data);
    $.ajax({
      type: "POST",
      url: "/board/saveTheWritingForm",
      data: JSON.stringify(data),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
    })
      .done(function (resp) {
        history.back();
      })
      .fail(function (error) {
        alert("글의 용량이 너무 큽니다.");
      });
  },

  deleteById: function () {
    //let id = $("#id").val(); 아래 data부분 없애고 이렇게만 넣으면 - (1/3)

    $("input[name=no]:checked").each(function () {
      var no = $(this).attr("value");
      console.log(no);

      $.ajax({
        type: "DELETE",
        url: "/index/board/detail/" + no,
        data: JSON.stringify(no),
      })
        .done(function (resp) {
          location.reload();
        })
        .fail(function (error) {
          alert(JSON.stringify(error));
        });
    });
  },

  updateTheWriting: function () {
    let data = {
      no: $("#boardNo").val(),
      title: $(".title").val(),
      content: $("#summernote").val(),
      categoryName: $("#categoryName").val(),
      channelName: $("#channelName").val(),
    };
    $.ajax({
      type: "PUT",
      url: "/index/board/detail/update",
      data: JSON.stringify(data),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
    })
      .done(function (resp) {
        alert("글수정이 완료되었습니다.");
        history.back();
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },
};

index.init();

//테이블 헤드 체크박스 누르면 화면 내 체크박스들 전부 check On/ Off 기능.
function btn_allDelete(btn_allDelete) {
  const checkboxes = document.getElementsByName("no");

  checkboxes.forEach((checkbox) => {
    checkbox.checked = btn_allDelete.checked;
  });
}
