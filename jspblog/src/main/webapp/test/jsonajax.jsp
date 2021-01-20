<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script
        src="https://code.jquery.com/jquery-3.5.1.js"
        integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
        crossorigin="anonymous"></script>
</head>
<body>
    <button onclick="jsonAjax()">클릭</button>

<script>
    let data = {
        username: "kim",
        password: "1234"
    }

    // JSON.stringify() : js object -> json
    // JSON.parse() : json -> js object
    function jsonAjax() {
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/jspblog/ajax2",
            data: JSON.stringify(data),
            contentType: "application/json",
            dataType: "json"
        })
        .done(function (result){
            console.log(result);
            console.log(result.username);
        })
        .fail(function (error) {

        });
    }
</script>
</body>
</html>
