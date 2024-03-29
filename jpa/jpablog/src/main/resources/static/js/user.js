let index = {
    init : function () {
        $("#btn-save").on("click", () => {
            this.save();
        });
        $("#btn-update").on("click", () => {
            this.update();
        });
    },
    save : function () {
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val(),
        };
        $.ajax({
            type: "post",
            url: "/auth/joinProc",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp){
            if (resp.statusCode === 500) {
                alert("회원가입 실패.");
            } else {
                alert("회원가입 완료.");
            }

            location.href = "/";
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        const id =  $("#id").val();
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val(),
        };
        $.ajax({
            type: "put",
            url: "/user/" + id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp){
            console.log(resp);
            alert("회원수정이 완료되었습니다.");
            location.href = "/";
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
}

index.init();