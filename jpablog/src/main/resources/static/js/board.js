let index = {
    init : function () {
        $("#btn-save").on("click", () => {
            this.save();
        })
        $("#btn-delete").on("click", () => {
            this.deleteById();
        })
        $("#btn-update").on("click", () => {
            this.update();
        })
        $("#btn-reply-save").on("click", () => {
            this.replySave();
        })
    },
    save : function () {
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        };
        $.ajax({
            type: "post",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp){
            alert("글쓰기 완료!");
            location.href = "/";
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
    deleteById : function () {
        const id = $("#id").text();
        $.ajax({
            type: "delete",
            url: "/api/board/" + id,
            dataType: "json"
        }).done(function (resp){
            if (resp.data === 1) {
                alert("삭제 완료!");
                location.href = "/";
            }
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        let id = $("#id").val();
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        };
        $.ajax({
            type: "patch",
            url: "/api/board/"+id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp){
            if (resp.data === 1) {
                alert("글수정 완료!");
                history.back();
            }
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
    replySave : function () {
        let data = {
            boardId: $("#boardId").val(),
            content: $("#reply-content").val(),
        };
        $.ajax({
            type: "post",
            url: `/api/board/${data.boardId}/reply`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp){
            alert("댓글 작성 완료!");
            location.href = `/board/${data.boardId}`;
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
}

index.init();