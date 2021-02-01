<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@include file="../layout/header.jsp"%>

<div class="container">
    <form action="/auth/loginProc" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input name="username" type="text" class="form-control" placeholder="Enter username" id="username">
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input name="password" type="password" class="form-control" placeholder="Enter password" id="password">
        </div>
        <button id="btn-login" type="submit" class="btn btn-primary">로그인</button>
        <a href="https://kauth.kakao.com/oauth/authorize?client_id=e40b81f26358aa250b10a59dc8f3aa62&redirect_uri=http://localhost:8080/auth/kakao/callback&response_type=code"><img height="38px" src="/images/kakao_login_medium.png" alt="kakao_login_button"/> </a>
    </form>
</div>

<%@include file="../layout/footer.jsp"%>