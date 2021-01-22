<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">
    <c:if test="${sessionScope.principal.id == detail.userId}">
        <a href="${pageContext.request.contextPath}/board?cmd=updateForm&id=${detail.id}" class="btn btn-warning">수정</a>
        <button onclick="deleteById(${detail.id})" class="btn btn-danger">삭제</button>
    </c:if>

    <br />
    <br />
    <h6 class="m-2">
        작성자 : <i>${detail.username}  </i> 조회수 : <i>${detail.readCount}</i>
    </h6>
    <br />
    <h3 class="m-2">
        <b>${detail.title}</b>
    </h3>
    <hr />
    <div class="form-group">
        <div id="content" class="m-2"></div>
    </div>

    <hr />

    <!-- 댓글 박스 -->
    <div class="row bootstrap snippets">
        <div class="col-md-12">
            <div class="comment-wrapper">
                <div class="panel panel-info">
                    <div class="panel-heading m-2"><b>Comment</b></div>
                    <div class="panel-body">
                        <textarea id="reply__write__form" class="form-control" placeholder="write a comment..." rows="2"></textarea>
                        <br>
                        <button onclick="replySave(${sessionScope.principal.id}, ${detail.id})" class="btn btn-primary pull-right">댓글쓰기</button>
                        <div class="clearfix"></div>
                        <hr />

                        <!-- 댓글 리스트 시작-->
                        <ul id="reply__list" class="media-list">
                            <!-- 댓글 아이템 -->
                            <c:forEach var="reply" items="${replies}">
                                <li id="reply-${reply.id}" class="media">
                                    <img src="/jspblog/images/profile.jpg" class="img-circle" style="width: 50px; height: 50px;" alt="profile-image"/>
                                    <div class="media-body">
                                        <strong class="text-primary">${reply.userId}</strong>
                                        <p>${reply.content}</p></div>
                                    <div class="m-2">
                                        <c:if test="${sessionScope.principal.id == reply.userId}">
                                            <i onClick="deleteReply(${reply.id})" class="material-icons">delete</i>
                                        </c:if>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                        <!-- 댓글 리스트 끝-->
                    </div>
                </div>
            </div>

        </div>
    </div>
    <!-- 댓글 박스 끝 -->
</div>
<script>
    let content = $.parseHTML("${detail.content}")
    $(document).ready(function () {
        $('#content').append(content[0].data)
    })
</script>
<script src="${pageContext.request.contextPath}/js/boardDetail.js"></script>

</body>
</html>