<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@include file="../layout/header.jsp"%>

<div class="container">
    <form>
        <input type="hidden" id="id" value="${principal.user.id}">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" value="${principal.user.username}" id="username" readonly>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" placeholder="Enter password" id="password">
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control" value="${principal.user.email}" placeholder="Enter email" id="email">
        </div>
        <button id="btn-update" type="button" class="btn btn-primary">수정</button>
    </form>
</div>

<script src="/js/user.js"></script>
<%@include file="../layout/footer.jsp"%>