<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@include file="../layout/header.jsp"%>

<div class="container">
    <button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
    <c:if test="${board.user.id == principal.user.id}" >
        <a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
        <button id="btn-delete" class="btn btn-danger">삭제</button>
    </c:if>
    <br/><br/>
    <div>
        글 번호 : <span id="id"><i>${board.id}</i></span>
        작성자 : <span><i>${board.user.username}</i></span>
    </div>
    <br/>
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