<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    request.getRequestDispatcher("board?cmd=list").forward(request, response);
%>