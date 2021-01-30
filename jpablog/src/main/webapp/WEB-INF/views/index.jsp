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
        <fmt:parseNumber var="page" integerOnly="true" value="${boards.number / 3}"/>
        <c:forEach begin="1" end="3" varStatus="status">
            <c:set var="p" value="${3 * page + status.current}"/>
            <c:if test="${p le boards.totalPages}">
                <li class="page-item"><a class="page-link" href="?page=${p-1}">${p}</a></li>
            </c:if>
        </c:forEach>
        <c:choose>
            <c:when test="${boards.last}">
                <li class="page-item disabled"><a class="page-link" href="?page=${boards.number + 1}">Next</a></li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="?page=${boards.number + 1}">Next</a></li>
            </c:otherwise>
        </c:choose>

    </ul>
</div>
<script>
    let page = "${boards.pageable.offset}"
    console.log(page);
</script>


<%@include file="layout/footer.jsp"%>