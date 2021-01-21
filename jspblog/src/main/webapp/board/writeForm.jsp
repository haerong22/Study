<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">
    <form action="${pageContext.request.contextPath}/board?cmd=write" method="POST">
        <input type="hidden" name="userId" value="${sessionScope.principal.id}">

        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" class="form-control" placeholder="title" id="title" name="title">
        </div>

        <div class="form-group">
            <label for="summernote">Content:</label>
            <textarea class="form-control" rows="5" id="summernote" name="content"></textarea>
        </div>

        <button type="submit" class="btn btn-primary">글쓰기 등록</button>
    </form>
</div>

<script>
    $(document).ready(function() {
        $('#summernote').summernote({
            placeholder: '글쓰기',
            tabsize: 2,
            height: 400
        });
    });
</script>

</body>
</html>