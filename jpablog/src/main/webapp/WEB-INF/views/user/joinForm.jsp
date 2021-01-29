<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@include file="../layout/header.jsp"%>

<div class="container">
    <form>
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
        <button id="btn-save" type="button" class="btn btn-primary">회원가입</button>
    </form>
</div>

<script src="${pageContext.request.contextPath}/js/user.js"></script>
<%@include file="../layout/footer.jsp"%>