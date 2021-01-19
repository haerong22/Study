<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layout/header.jsp"%>

<div class="container">
    <form action="/action_page.php">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" placeholder="Enter username" id="username">
        </div>

        <div class="form-group">
            <label for="pwd">Password:</label>
            <input type="password" class="form-control" placeholder="Enter password" id="pwd">
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control" placeholder="Enter email" id="email">
        </div>

        <div class="form-group">
            <label for="address">Address:</label>
            <input type="text" class="form-control" placeholder="Enter address" id="address">
        </div>

        <button type="submit" class="btn btn-primary">회원가입 완료</button>
    </form>
</div>

</body>
</html>
