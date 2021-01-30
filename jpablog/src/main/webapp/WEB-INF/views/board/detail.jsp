<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@include file="../layout/header.jsp"%>

<div class="container">
    <button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
    <button id="btn-update" class="btn btn-warning">수정</button>
    <button id="btn-delete" class="btn btn-danger">삭제</button>
    <br/><br/>
    <div class="form-group">
        <h3 id="title">${board.title}</h3>
    </div>
    <hr/>
    <div class="form-group">
        <div id="content">${board.content}</div>
    </div>
    <hr/>
</div>

<script src="/js/board.js"></script>
<%@include file="../layout/footer.jsp"%>