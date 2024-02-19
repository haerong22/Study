<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <button onclick="idCheck()">아이디 있니?</button>
    <div id="box"></div>
<script>
    function idCheck() {
        // const xhttp = new XMLHttpRequest();
        // xhttp.onreadystatechange = function() {
        //     if (this.readyState === 4 && this.status === 200) {
        //         const box = document.querySelector("#box");
        //         if (this.responseText === 'OK'){
        //             box.innerHTML = "아이디 중복"
        //         } else {
        //             box.innerHTML = "아이디 사용 가능"
        //         }
        //     }
        // };
        // xhttp.open("GET", "http://localhost:8080/jspblog/ajax", true);
        // xhttp.send();

        // $.ajax("http://localhost:8080/jspblog/ajax").done(function (data) {
        //     alert(data);
        // })

        fetch("http://localhost:8080/jspblog/ajax").then(v => v.text()).then(v => {
            const box = document.querySelector("#box");
            box.innerHTML = v === 'OK' ? "아이디 중복" : "아이디 사용 가능";
        });
    }
</script>
</body>
</html>
