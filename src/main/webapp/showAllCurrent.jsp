<%--
  Created by IntelliJ IDEA.
  User: genes
  Date: 14.06.2023
  Time: 22:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%@page import="com.example.currencyservlet.CurrencyDAO" %>
    <%@page import="com.example.currencyexchange.Currency" %>
    <%@page import="java.io.*, java.util.*" %>

    <% CurrencyDAO currencyDAO = new CurrencyDAO(); %>
    <% ArrayList<Currency> currencyList = currencyDAO.getAllCurrent(); %>

    <%
        PrintWriter pw = response.getWriter();
        pw.println("[" + "<br>");
        for (int i = 0; i < currencyList.size(); i++) {
            if (i == currencyList.size() - 1) {
                pw.println(currencyList.get(i).toString() + "<br>") ;
            } else {
                pw.println(currencyList.get(i).toString() + "," + "<br>");
            }
        }
        pw.println("]");
        %>


</body>
</html>
