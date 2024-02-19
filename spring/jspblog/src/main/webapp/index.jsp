<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    request.getRequestDispatcher("board?cmd=list&page=0").forward(request, response);
%>