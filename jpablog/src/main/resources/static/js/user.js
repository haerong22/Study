let index = {
    init : function () {
        $("#btn-save").on("click", () => {
            this.save();
        })
    },
    save : function () {
        let data = {
            username: $("#username").val(),
            password: $("#pwd").val(),
            email: $("#email").val(),
        };
        $.ajax({
            type: "post",
            url: "/jpablog/api/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp){
            console.log(resp);
            alert("회원가입이 완료되었습니다.");
            location.href = "/jpablog";
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    }
}

index.init();