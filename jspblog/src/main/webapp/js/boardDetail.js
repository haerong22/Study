function addReply(data) {
    let replyItem = `<li id="reply-${data.id}" class="media">
        <img src="/jspblog/images/profile.jpg" class="img-circle"
        style="width: 50px; height: 50px;" alt="profile-image"/>
        <div class="media-body">
        <strong class="text-primary">${data.userId}</strong>
        <p>${data.content}</p></div>
        <div class="m-2">
        <i onClick="deleteReply(${data.id})" class="material-icons">delete</i></div></li>`
    $('#reply__list').prepend(replyItem);
}

function deleteReply(id) {
    console.log(id);
}

function replySave(userId, boardId) {
    console.log(userId)
    let data = {
        userId: userId,
        boardId: boardId,
        content: $("#reply__write__form").val(),
    }

    $.ajax({
        type: "post",
        url: "/jspblog/reply?cmd=save",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
    }).done(function (result){
        if (result.statusCode === 1){
            addReply(result.data);
        }
    })
}

function deleteById(boardId) {
    let data = {
        boardId : boardId
    }

    $.ajax({
        type: "post",
        url: "/jspblog/board?cmd=delete",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    }).done(function(result){
        if (result.statusCode === 1) {
            location.href = "index.jsp";
        } else {
            alert("삭제 실패");
        }
    });
}