<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hello</title>
</head>
<body>
<h1><%= "Проект “Обмен валют”" %>
</h1>
<br/>
<p>REST API для описания валют и обменных курсов.<br />
    Позволяет просматривать и редактировать списки валют и обменных курсов,<br />
    и совершать расчёт конвертации произвольных сумм из одной валюты в другую.<br />
    <br />
    Веб-интерфейс для проекта не подразумевается.<br />

    Методы REST API приложения:<br />
    <br />
    <b>Валюты</b><br />
    <b>GET /currencies</b><br />
    Получение списка валют. Пример ответа:<br />
    [<br />
    {<br />
    "id": 0,<br />
    "name": "United States dollar",<br />
    "code": "USD",<br />
    "sign": "$"<br />
    },<br />
    {<br />
    "id": 0,<br />
    "name": "Euro",<br />
    "code": "EUR",<br />
    "sign": "€"<br />
    }<br />
    ]<br />
    <br />
    <b>GET /currency/EUR</b><br />
    Получение конкретной валюты. Пример ответа:<br />
    {<br />
    "id": 0,<br />
    "name": "Euro",<br />
    "code": "EUR",<br />
    "sign": "€"<br />
    }<br />
    <br />
    <b>POST /currencies</b><br />
    Добавление новой валюты в базу. Данные передаются в теле запроса в виде полей формы (x-www-form-urlencoded). Поля формы - name, code, sign. Пример ответа:<br />
    {<br />
    "id": 0,<br />
    "name": "Euro",<br />
    "code": "EUR",<br />
    "sign": "€"<br />
    }<br />
    <br />
    Обменные курсы<br />
    <br />
    <b>GET /exchangeRates</b><br />
    Получение списка всех обменных курсов. Пример ответа:<br />
    [<br />
    {<br />
    "id": 0,<br />
    "baseCurrency": {<br />
    "id": 0,<br />
    "name": "United States dollar",<br />
    "code": "USD",<br />
    "sign": "$"<br />
    },<br />
    "targetCurrency": {<br />
    "id": 1,<br />
    "name": "Euro",<br />
    "code": "EUR",<br />
    "sign": "€"<br />
    },<br />
    "rate": 0.99<br />
    }<br />
    ]<br />
    <br />
    <b>GET /exchangeRate/USDRUB</b><br />
    Получение конкретного обменного курса. Валютная пара задаётся идущими подряд кодами валют в адресе запроса. Пример ответа:<br />
    {<br />
    "id": 0,<br />
    "baseCurrency": {<br />
    "id": 0,<br />
    "name": "United States dollar",<br />
    "code": "USD",<br />
    "sign": "$"<br />
    },<br />
    "targetCurrency": {<br />
    "id": 1,<br />
    "name": "Euro",<br />
    "code": "EUR",<br />
    "sign": "€"<br />
    },<br />
    "rate": 0.99<br />
    }<br />
    <br />
    <b>POST /exchangeRates</b><br />
    Добавление нового обменного курса в базу. Данные передаются в теле запроса в виде полей формы (x-www-form-urlencoded).<br />
    Поля формы - baseCurrencyCode, targetCurrencyCode, rate. Пример полей формы:<br />
    •	baseCurrencyCode - USD<br />
    •	targetCurrencyCode - EUR<br />
    •	rate - 0.99<br />
    Пример ответа:<br />
    {<br />
    "id": 0,<br />
    "baseCurrency": {<br />
    "id": 0,<br />
    "name": "United States dollar",<br />
    "code": "USD",<br />
    "sign": "$"<br />
    },<br />
    "targetCurrency": {<br />
    "id": 1,<br />
    "name": "Euro",<br />
    "code": "EUR",<br />
    "sign": "€"<br />
    },<br />
    "rate": 0.99<br />
    }<br />
    <br />
    <b>PATCH /exchangeRate/USDRUB</b><br />
    Обновление существующего в базе обменного курса. Валютная пара задаётся идущими подряд кодами валют в адресе запроса.<br />
    Данные передаются в теле запроса в виде полей формы (x-www-form-urlencoded). Единственное поле формы - rate.<br />
    Пример ответа:<br />
    {<br />
    "id": 0,<br />
    "baseCurrency": {<br />
    "id": 0,<br />
    "name": "United States dollar",<br />
    "code": "USD",<br />
    "sign": "$"<br />
    },<br />
    "targetCurrency": {<br />
    "id": 1,<br />
    "name": "Euro",<br />
    "code": "EUR",<br />
    "sign": "€"<br />
    },<br />
    "rate": 0.99<br />
    }<br />
    <br />
    <b>Обмен валюты</b><br />
    <b>GET /exchange?from=BASE_CURRENCY_CODE&to=TARGET_CURRENCY_CODE&amount=$AMOUNT</b><br />
    Расчёт перевода определённого количества средств из одной валюты в другую. Пример запроса - GET /exchange?from=USD&to=AUD&amount=10.<br />
    Пример ответа:<br />
    {<br />
    "baseCurrency": {<br />
    "id": 0,<br />
    "name": "United States dollar",<br />
    "code": "USD",<br />
    "sign": "$"<br />
    },<br />
    "targetCurrency": {<br />
    "id": 1,<br />
    "name": "Australian dollar",<br />
    "code": "AUD",<br />
    "sign": "A€"<br />
    },<br />
    "rate": 1.45,<br />
    "amount": 10.00<br />
    "convertedAmount": 14.50<br />
    }<br />
    Получение курса для обмена может пройти по одному из трёх сценариев. Допустим, совершаем перевод из валюты A в валюту B:<br />
    1.	Если существует валютная пара AB - берём её курс<br />
    2.	Если существует валютная пара BA - берем её курс, и считаем обратный, чтобы получить AB<br />
    3.	Если существуют валютные пары USD-A и USD-B - вычисляем из этих курсов курс AB<br />

</p>
</body>
</html>