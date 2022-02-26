function btn_deleteCategory() {
  var deleteCategoryNoArray = [];
  $("input[name=deleteCategory]:checked").each(function () {
    deleteCategoryNoArray.push($(this).attr("value"));
  });
  console.log(deleteCategoryNoArray);
  uri = $("#deleteCategoryURI").val();

  console.log("deleteCategoryURI:", uri);
  $.ajax({
    type: "POST",
    url: uri,
    data: JSON.stringify(deleteCategoryNoArray),
    contentType: "application/json; charset=utf-8",
    dataType: "json",
  })
    .done(function (resp) {
      alert("삭제가 완료되었습니다.");
      location.href = "/index";
    })
    .fail(function (error) {
      alert("삭제할 항목을 먼저 체크하세요.");
    });
}

function submitItem() {
  if (!validateItem()) {
    return;
  }

  let data = {
    title: $("#title").val(),
    categoryName: $("#categoryName").val(),
  };
  uri = $("#saveCategoryURI").val();
  console.log("data:", data);
  console.log("saveCategoryURI:", uri);
  $.ajax({
    type: "POST",
    url: uri,
    data: JSON.stringify(data),
    contentType: "application/json; charset=utf-8",
    dataType: "json",
  })
    .done(function (resp) {
      alert("등록이 완료되었습니다.");
      location.href = "/index";
    })
    .fail(function (error) {
      alert(JSON.stringify(error));
    });
}

/** 아이템 체크 */
function validateItem() {
  var items = $("input[type='text'][name='item']");
  if (items.length == 0) {
    alert("작성된 아이템이 없습니다.");
    return false;
  }

  var flag = true;
  for (var i = 0; i < items.length; i++) {
    if ($(items.get(i)).val().trim() == "") {
      flag = false;
      alert("내용을 입력하지 않은 항목이 있습니다.");
      break;
    }
  }

  return flag;
}

/** UI 설정 */
$(function () {
  $("#itemBoxWrap").sortable({
    placeholder: "itemBoxHighlight",
    start: function (event, ui) {
      ui.item.data("start_pos", ui.item.index());
    },
    stop: function (event, ui) {
      var spos = ui.item.data("start_pos");
      var epos = ui.item.index();
      reorder();
    },
  });
  //$("#itemBoxWrap").disableSelection();

  $("#sortable").sortable();
  $("#sortable").disableSelection();
});

/** 아이템 순서 조정 */
function reorder() {
  $(".itemBox").each(function (i, box) {
    $(box)
      .find(".itemNum")
      .html(i + 1);
  });
}

/** 아이템 추가 */
function createItem() {
  $(createBox())
    .appendTo("#itemBoxWrap")
    .hover(
      function () {
        $(this).css("backgroundColor", "#f9f9f5");
        $(this).find(".deleteBox").show();
      },
      function () {
        $(this).css("background", "none");
        $(this).find(".deleteBox").hide();
      }
    )
    .append("<div class='deleteBox'>[삭제]</div>")
    .find(".deleteBox")
    .click(function () {
      var valueCheck = false;
      $(this)
        .parent()
        .find("input")
        .each(function () {
          if ($(this).attr("name") != "type" && $(this).val() != "") {
            valueCheck = true;
          }
        });

      if (valueCheck) {
        var delCheck = confirm("입력하신 내용이 있습니다.\n삭제하시겠습니까?");
      }
      if (!valueCheck || delCheck == true) {
        $(this).parent().remove();
        reorder();
      }
    });
  // 숫자를 다시 붙인다.
  reorder();
}

/** 아이템 박스 작성 */
function createBox() {
  var contents =
    "<div class='itemBox'>" +
    "<div style='float:left;'>" +
    "<span class='itemNum'></span> " +
    "<input type='text' id='categoryName' name='item' style='width:300px;'/>" +
    "</div>" +
    "</div>";
  return contents;
}
//]]>
