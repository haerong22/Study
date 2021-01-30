<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@include file="../layout/header.jsp"%>

<div class="container">
    <form>
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" class="form-control" placeholder="Enter title" id="title">
        </div>
        <div class="form-group">
            <label for="content">Content:</label>
            <textarea class="form-control summernote" rows="5" id="content"></textarea>
        </div>
        <button id="btn-save" type="button" class="btn btn-primary">글쓰기</button>
    </form>
</div>
<script>
    $('.summernote').summernote({
        placeholder: '글쓰기 ㅎㅎ',
        tabsize: 2,
        height: 300
    });
</script>
<script src="/js/board.js"></script>
<%@include file="../layout/footer.jsp"%>