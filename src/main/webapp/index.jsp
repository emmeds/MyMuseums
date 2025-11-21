<%--
  Created by IntelliJ IDEA.
  User: ettor
  Date: 21/11/2025
  Time: 09:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>INDEX</title>
</head>
<body>
<%
    response.sendRedirect(request.getContextPath() +"/HomePageServlet");
    System.out.println("Redirecting to HomePageServlet");
%>
</body>
</html>
