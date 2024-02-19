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
    <button onclick="postAjax()">클릭</button>

<script>
    function postAjax() {
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/jspblog/ajax1",
            data:"username=kim&password=1234",
            contentType:"application/x-www-form-urlencoded",
            // dataType: "json"
        })
        .done(function (result){
            console.log(result);
        })
        .fail(function (error) {

        });
    }
</script>
</body>
</html>
