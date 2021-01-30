<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@include file="layout/header.jsp"%>

<div class="container">
    <div class="card m-2">
        <c:forEach var="board" items="${boards}">
            <div class="card-body">
                <h4 class="card-title">${board.title}</h4>
                <a href="/api/board/${board.id}" class="btn btn-primary">상세보기</a>
            </div>
        </c:forEach>
    </div>
</div>

<%@include file="layout/footer.jsp"%>