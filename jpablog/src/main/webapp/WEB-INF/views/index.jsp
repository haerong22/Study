<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@include file="layout/header.jsp"%>

<div class="container">
    <div class="card m-2">
        <c:forEach var="board" items="${boards.content}">
            <div class="card-body">
                <h4 class="card-title">${board.title}</h4>
                <a href="/api/board/${board.id}" class="btn btn-primary">상세보기</a>
            </div>
        </c:forEach>
    </div>

    <ul class="pagination justify-content-center">
        <c:choose>
            <c:when test="${boards.first}">
                <li class="page-item disabled"><a class="page-link" href="?page=${boards.number -1}">Previous</a></li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="?page=${boards.number -1}">Previous</a></li>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${boards.last}">
                <li class="page-item disabled"><a class="page-link" href="?page=${boards.number + 1}">Next</a></li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="?page=${boards.number + 1}">Next</a></li>
            </c:otherwise>
        </c:choose>

        <%--<li class="page-item"><a class="page-link" href="#">1</a></li>
        <li class="page-item"><a class="page-link" href="#">2</a></li>
        <li class="page-item"><a class="page-link" href="#">3</a></li>--%>

    </ul>
</div>



<%@include file="layout/footer.jsp"%>