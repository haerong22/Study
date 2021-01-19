<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layout/header.jsp"%>

<div class="container">
    <form action="${pageContext.request.contextPath}/user?cmd=join" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input name="username" type="text" class="form-control" placeholder="Enter username" id="username" required>
        </div>

        <div class="form-group">
            <label for="pwd">Password:</label>
            <input name="password" type="password" class="form-control" placeholder="Enter password" id="pwd" required>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input name="email" type="email" class="form-control" placeholder="Enter email" id="email" required>
        </div>

        <div class="form-group">
            <label for="address">Address:</label>
            <input name="address" type="text" class="form-control" placeholder="Enter address" id="address" required>
        </div>

        <button type="submit" class="btn btn-primary">회원가입 완료</button>
    </form>
</div>

</body>
</html>
