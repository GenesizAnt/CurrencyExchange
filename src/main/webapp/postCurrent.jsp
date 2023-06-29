<%@ page import="com.example.currencyservlet.CurrencyDAO" %><%--
  Created by IntelliJ IDEA.
  User: genes
  Date: 26.06.2023
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<p> Hello </p>

<%--http://localhost:8080/CurrencyExchange_war_exploded/controller?val1=RRR&val2=RRR&amount=R--%>
<%--http://localhost:8080/CurrencyExchange_war_exploded/controller?command=get&code=RUB--%>

<form action="http://localhost:8080/CurrencyExchange_war_exploded/controller?command=post" method="get">
    <input type="text" name="val1" value="RUB"><br><br>
    <input type="text" name="val2" value="USD"><br><br>
    <input type="text" name="amount" value="100"><br><br>
    <button type="submit">Отправить POST запрос</button>
</form>


<%--<% CurrencyDAO currencyDAO = new CurrencyDAO(); %>--%>
<%--<%--%>
<%--    String codeCurrency = request.getParameter("val1");--%>
<%--    String nameCurrency = request.getParameter("val2");--%>
<%--    String signCurrency = request.getParameter("amount");--%>
<%--    currencyDAO.postCurrency(codeCurrency, nameCurrency, signCurrency);--%>
<%--%>--%>

</body>
</html>
