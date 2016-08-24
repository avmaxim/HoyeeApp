<%--
  Created by IntelliJ IDEA.
  User: andrei
  Date: 8/5/2016
  Time: 2:17 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hoyee Server</title>
</head>
<body>
    <h1 style="text-align: center; margin-top: 50px">Welcome to Hoyee Server Administration Manager.</h1>
    <h3 style="text-align: center; color: green">Right now is <fmt:formatDate pattern="yyyy-MM-dd" value="${now}" /></h3>
    <h3 style="text-align: center; color: green">Lets get to it =)</h3>
</body>
</html>
