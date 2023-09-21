# CurrencyExchange
Проект “Обмен валют”

REST API для описания валют и обменных курсов.
Позволяет просматривать и редактировать списки валют и обменных курсов,
и совершать расчёт конвертации произвольных сумм из одной валюты в другую.

Веб-интерфейс для проекта не подразумевается.

# Методы REST API приложения:

## Валюты

### Получение списка валют

GET /currencies

Пример ответа:

    [
        {
            "id": 0,
            "name": "United States dollar",
            "code": "USD",
            "sign": "$"
        },   
        {
            "id": 0,
            "name": "Euro",
            "code": "EUR",
            "sign": "€"
        }
    ]



### Получение конкретной валюты

GET /currency/EUR

Пример ответа:

        {
            "id": 0,
            "name": "Euro",
            "code": "EUR",
            "sign": "€"
        }

### Добавление новой валюты в базу

POST /currencies

Данные передаются в теле запроса в виде полей формы (x-www-form-urlencoded). Поля формы - name, code, sign.

Пример ответа:

        {
            "id": 0,
            "name": "Euro",
            "code": "EUR",
            "sign": "€"
        }


## Обменные курсы

### Получение списка всех обменных курсов

GET /exchangeRates

Пример ответа:

        [
            {
                "id": 0,
                "baseCurrency": {
                    "id": 0,
                    "name": "United States dollar",
                    "code": "USD",
                    "sign": "$"
                },
                "targetCurrency": {
                    "id": 1,
                    "name": "Euro",
                    "code": "EUR",
                    "sign": "€"
                },
                "rate": 0.99
            }
        ]

### Получение конкретного обменного курса

GET /exchangeRate/USDRUB

Валютная пара задаётся идущими подряд кодами валют в адресе запроса.

Пример ответа:

        {
            "id": 0,
            "baseCurrency": {
                "id": 0,
                "name": "United States dollar",
                "code": "USD",
                "sign": "$"
            },
            "targetCurrency": {
                "id": 1,
                "name": "Euro",
                "code": "EUR",
                "sign": "€"
            },
            "rate": 0.99
        }

### Добавление нового обменного курса в базу

POST /exchangeRates

Данные передаются в теле запроса в виде полей формы (x-www-form-urlencoded). Поля формы - baseCurrencyCode, targetCurrencyCode, rate.

Пример полей формы:

- baseCurrencyCode - USD
- targetCurrencyCode - EUR
- rate - 0.99

Пример ответа:

        {
            "id": 0,
            "baseCurrency": {
                "id": 0,
                "name": "United States dollar",
                "code": "USD",
                "sign": "$"
            },
            "targetCurrency": {
                "id": 1,
                "name": "Euro",
                "code": "EUR",
                "sign": "€"
            },
            "rate": 0.99
        }

### Обновление существующего в базе обменного курса

PATCH /exchangeRate/USDRUB

Валютная пара задаётся идущими подряд кодами валют в адресе запроса. Данные передаются в теле запроса в виде полей формы (x-www-form-urlencoded). Единственное поле формы - rate.

Пример ответа:

        {
            "id": 0,
            "baseCurrency": {
                "id": 0,
                "name": "United States dollar",
                "code": "USD",
                "sign": "$"
            },
            "targetCurrency": {
                "id": 1,
                "name": "Euro",
                "code": "EUR",
                "sign": "€"
            },
            "rate": 0.99
        }

## Обмен валюты

### Расчёт перевода определённого количества средств из одной валюты в другую

GET /exchange?from=BASE_CURRENCY_CODE&to=TARGET_CURRENCY_CODE&amount=$AMOUNT

Пример запроса - GET /exchange?from=USD&to=AUD&amount=10.

Пример ответа:

        {
            "baseCurrency": {
                "id": 0,
                "name": "United States dollar",
                "code": "USD",
                "sign": "$"
            },
            "targetCurrency": {
                "id": 1,
                "name": "Australian dollar",
                "code": "AUD",
                "sign": "A€"
            },
            "rate": 1.45,
            "amount": 10.00,
            "convertedAmount": 14.50
        }


Получение курса для обмена может пройти по одному из трёх сценариев. Допустим, совершаем перевод из валюты A в валюту B:

1. Если существует валютная пара AB - берём её курс
2. Если существует валютная пара BA - берем её курс, и считаем обратный, чтобы получить AB
3. Если существуют валютные пары USD-A и USD-B - вычисляем из этих курсов курс AB.
