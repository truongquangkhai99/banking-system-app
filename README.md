# Banking System App<hr>

## Before installation

To start the project, you need to install docker and docker-compose. You should also check if you have at least Java 11v
and, if not, install it.

Docker Install: https://docs.docker.com/engine/install/

JAVA install: https://openjdk.org/

## How to install

Dev:

Change value for property 'spring.profiles.active' to 'dev' in [application.yml](src/main/resources/application.yml)

```console
foo@bar:~$ docker-compose --profile dev up

foo@bar:~$ ./mvnw clean spring-boot:run
```

Next in your browser goto [link](http://localhost:8888/)

Prod:

Change value for property 'spring.profiles.active' to 'prod' in [application.yml](src/main/resources/application.yml)

```console
foo@bar:~$ ./mvnw clean package -DskipTests

foo@bar:~$ docker-compose --profile prod up
```

Next in your browser goto [link](http://localhost:8888/)

## Features

- [x] Profiles ('dev' and 'prod')
- [x] Spring Security with JWT
- [x] Spring Cache (Redis)
- [x] Swagger Doc and UI
- [x] Spring AOP
- [x] UI (Thymeleaf with HTML and CSS + Bootstrap 5)
- [x] Docker-compose for db, redis and spring boot app

## Entity Relationship Diagram

![plot](data/erd/erd.jpg)

The diagram was automatically generated in [DbVisualizer](https://www.dbvis.com/).

## Initial user credentials

### Admin: 

email: **_admin@admin.com_**

password: _**admin42**_

## MVC

It's worth mentioning that this app is educational in nature.
When you click on the [link](http://localhost:8888/), the user sees the home page of the site.

![plot](src/main/resources/images/Screenshot%20from%202022-07-12%2012-25-49.jpg)

Next, the new user can register
![plot](src/main/resources/images/Screenshot%20from%202022-07-12%2012-25-49(1).jpg)

Or, if the user already has an account he can log in using email and password.
![plot](src/main/resources/images/Screenshot%20from%202022-07-12%2012-25-49(2).jpg)

The user can then check the exchange rate at the current time. Also note that the
admin and swagger doc tabs are only available to users with the ADMIN role
![plot](src/main/resources/images/Screenshot%20from%202022-07-12%2012-25-49(3).jpg)

The account page provides information about the current status of the user's account. 
It is possible to replenish balance, open a deposit, if there is money to withdraw from the deposit,
close the deposit, take credit and repay credit. Transactions, or history of transactions together with
credits are shown in the tables below.
![plot](src/main/resources/images/Screenshot%20from%202022-07-12%2012-25-49(4).jpg)

On the page for performing transactions, the user can select the desired type of transaction,
fill in the data and, if all conditions are met, perform the transaction.
![plot](src/main/resources/images/Screenshot%20from%202022-07-12%2012-25-49(5).jpg)

a user with the role of administrator can view the necessary records in the database by
clicking on the link in the tab admin.
![plot](src/main/resources/images/Screenshot%20from%202022-07-12%2012-25-49(6).jpg)
![plot](src/main/resources/images/Screenshot%20from%202022-07-12%2012-25-49(7).jpg)

To delete an entry, click on the delete button. Then, to avoid accidentally clicking the button, an alert pops
up where the user has to confirm his actions
![plot](src/main/resources/images/Screenshot%20from%202022-07-12%2012-25-49(8).jpg)

Clicking the update button takes the user to the edit page for that record.
![plot](src/main/resources/images/Screenshot%20from%202022-07-12%2012-25-49(9).jpg)

It is worth noting that if you enter incorrect data, validation errors appear.   
![plot](src/main/resources/images/Screenshot%20from%202022-07-12%2012-25-49(10).jpg)

## REST API

You can check endpoints in swagger http://localhost:8888/swagger-ui/ (Only for ADMIN role)

! Before you check the REST API, get the JWT and setup your auth settings in Postman, Insomnia and e.t.c.

### Auth
| Method | Url              | Description             | Sample Valid Request Body |
|--------|------------------|-------------------------|---------------------------|
| POST   | /auth/jwt/signin | Sign in and get the jwt | [JSON](#sign-in)          |
| POST   | /auth/jwt/signup | Sign up and get the jwt | [JSON](#sign-up)          |

### Person
    
| Method | Url                                                      | Description                           | Sample Valid Request Body |
|--------|----------------------------------------------------------|---------------------------------------|---------------------------|
| GET    | api/v1/person                                            | Get all persons                       |                           |
| GET    | api/v1/person/{id}                                       | Get person by id                      |                           |
| GET    | api/v1/person/?firstname={firstname}                     | Get persons by firstname              |                           |
| GET    | api/v1/person/?lastname={lastname}                       | Get persons by lastname               |                           |
| GET    | api/v1/person/?firstname={firstname}&lastname={lastname} | Get persons by firstname and lastname |                           |
| GET    | api/v1/person/?phone={phone}                             | Get person by phone                   |                           |
| GET    | api/v1/person/?email={email}                             | Get person by email                   |                           |
| POST   | api/v1/person/                                           | Create a new person                   | [JSON](#create-person)    |
| PUT    | api/v1/person/{id}                                       | Update person by id                   | [JSON](#update-person)    |
| DELETE | api/v1/person/{id}                                       | Delete person by id                   |                           |

### Account

| Method | Url                                 | Description               | Sample Valid Request Body |
|--------|-------------------------------------|---------------------------|---------------------------|
| GET    | api/v1/account                      | Get all accounts          |                           |
| GET    | api/v1/account/{id}                 | Get account by id         |                           |
| GET    | api/v1/account/account-history/{id} | Get account history by id |                           |
| PUT    | api/v1/account/{id}                 | Update account by id      | [JSON](#update-account)   |
| DELETE | api/v1/account/{id}                 | Delete account by id      |                           |

### Credit

| Method | Url                | Description         | Sample Valid Request Body |
|--------|--------------------|---------------------|---------------------------|
| GET    | api/v1/credit      | Get all credits     |                           |
| GET    | api/v1/credit/{id} | Get credit by id    |                           |
| POST   | api/v1/credit      | Create a new credit | [JSON](#create-credit)    |
| PUT    | api/v1/credit/{id} | Update credit by id | [JSON](#update-credit)    |
| DELETE | api/v1/credit/{id} | Delete credit by id |                           |

### Deposit

| Method | Url                 | Description          | Sample Valid Request Body |
|--------|---------------------|----------------------|---------------------------|
| GET    | api/v1/deposit      | Get all deposits     |                           |
| GET    | api/v1/deposit/{id} | Get deposit by id    |                           |
| POST   | api/v1/deposit      | Create a new deposit | [JSON](#create-deposit)   |
| PUT    | api/v1/deposit/{id} | Update deposit by id | [JSON](#update-deposit)   |
| DELETE | api/v1/deposit/{id} | Delete deposit by id |                           |

### ExchangeRate

| Method | Url                                                                       | Description                                      | Sample Valid Request Body     |
|--------|---------------------------------------------------------------------------|--------------------------------------------------|-------------------------------|
| GET    | api/v1/exchange-rate                                                      | Get all exchange rates                           |                               |
| GET    | api/v1/exchange-rate/{id}                                                 | Get exchange rate by id                          |                               |
| GET    | api/v1/exchange-rate/?fromCurrency={fromCurrency}                         | Get exchange rates by fromCurrency               |                               |
| GET    | api/v1/exchange-rate/?toCurrency={toCurrency}                             | Get exchange rates by toCurrency                 |                               |
| GET    | api/v1/exchange-rate/?fromCurrency={fromCurrency}&toCurrency={toCurrency} | Get exchange rate by fromCurrency and toCurrency |                               |
| POST   | api/v1/exchange-rate                                                      | Create a new exchange rate                       | [JSON](#create-exchange-rate) |
| PUT    | api/v1/exchange-rate/{id}                                                 | Update exchange rate by id                       | [JSON](#update-exchange-rate) |
| DELETE | api/v1/exchange-rate/{id}                                                 | Delete exchange rate by id                       |                               |

### Transaction

| Method | Url                     | Description              | Sample Valid Request Body   |
|--------|-------------------------|--------------------------|-----------------------------|
| GET    | api/v1/transaction      | Get all transactions     |                             |
| GET    | api/v1/transaction/{id} | Get transaction by id    |                             |
| POST   | api/v1/transaction      | Create a new transaction | [JSON](#create-transaction) |
| DELETE | api/v1/transaction/{id} | Delete transaction by id |                             |

##### <a id="sign-in">Sign in -> /auth/jwt/signin</a>
```json
{
  "email": "admin@admin.com",
  "password": "admin42"
}
```
##### <a id="sign-up">Sign up -> /auth/jwt/signup</a>
```json
{
    "firstname": "Maxim",
    "lastname": "Vinnikov",
    "email": "vmd@gmail.com",
    "phone": "+7 (952) 102-23-12",
    "date_of_birth": "17/11/2002",
    "password": "12345", 
    "confirmed_password": "12345",
    "currency": "RUB",
    "account_password": "12345"
}
```
##### <a id="create-person">Create Person -> api/v1/person</a>

```json
{
  "date_of_birth": "2020-09-17",
  "firstname": "Maxim",
  "lastname": "Vinnikov",
  "email": "v13@g.com",
  "phone": "+7 (961) 192-24-16",
  "password": "12345",
  "status": true,
  "account": {
    "currency": "RUB",
    "account_security_details": {
      "password_hash": "252525"
    }
  },
  "roles": [
    {
      "role": "USER"
    }
  ]
}
```

##### <a id="update-person">Update Person -> api/v1/person/{id}</a>

```json
{
  "date_of_birth": "2002-09-17",
  "firstname": "Maxim",
  "lastname": "Vinnikov",
  "email": "vin.md@gmail.com",
  "phone": "+7 (952) 192-24-16",
  "password": "12345",
  "status": true,
  "account": {
    "balance": 999999,
    "currency": "EUR",
    "account_security_details": {
      "password_hash": "2331"
    }
  },
  "roles": [
    {
      "role": "USER"
    }
  ]
}
```

##### <a id="update-account">Update Account -> api/v1/account/{id}</a>
```json
{
    "balance": 1000,
    "currency": "RUB",
    "account_security_details": {
        "password_hash": "252525"
    },
    "account_history_id": 1,
    "person_id": 1
}
```
##### <a id="create-credit">Create Credit -> api/v1/credit</a>

```json
{
  "total": 80000,
  "date_between_payments_in_days": 30,
  "currency": "USD",
  "accountHistoryId": 1
}
```

##### <a id="update-credit">Update Credit -> api/v1/credit/{id}</a>

```json
{
  "total": 80000,
  "current": 2000,
  "remain": 78000,
  "currency": "USD",
  "date_between_payments_in_days": 30,
  "account_history_id": 1,
  "isClosed": false
}
```

##### <a id="create-deposit">Create Deposit -> api/v1/deposit</a>

```json
{
  "intense_rate": 1.00123,
  "currency": "RUB",
  "accountHistoryId": 1
}
```

##### <a id="update-deposit">Update Deposit -> api/v1/deposit/{id}</a>

```json
{
  "intenseRate": 1.00169,
  "currency": "RUB",
  "balance": 10000,
  "account_history_id": 1
}
```

##### <a id="create-exchange-rate">Create ExchangeRate -> api/v1/exchange-rate</a>

```json
{
  "from_currency": "EUR",
  "to_currency": "RUB",
  "ratio": 0.016
}
```

##### <a id="update-exchange-rate">Update ExchangeRate -> api/v1/exchange-rate/{id}</a>

```json
{
  "from_currency": "EUR",
  "to_Currency": "RUB",
  "ratio": 0.018
}
```

##### <a id="create-transaction">Create Transaction -> api/v1/transaction</a>

If the currency is different between accounts, account and deposit or account and credit, it will be automatically
converted using the existing exchange rate

1 Way: Transfer to another account. Required existing 'from_account_id' and 'to_account_id' fields

```json
{
  "date": "2002-12-09",
  "transaction_details": {
    "amount": 13200,
    "transaction_type": "TRANSFER",
    "from_account_id": 1,
    "to_account_id": 2
  }
}
```

2 Way: Top up yourself deposit. Required 'from_account_id' field

```json
{
  "date": "2002-12-09",
  "transaction_details": {
    "amount": 13200,
    "transaction_type": "DEPOSIT",
    "from_account_id": 1
  }
}
```

3 Way: Top up yourself credit. Required existing 'credit_id' field

```json
{
  "date": "2002-12-09",
  "transaction_details": {
    "amount": 13200,
    "transaction_type": "CREDIT",
    "from_account_id": 1,
    "credit_id": 1
  }
}
```

4 Way: withdraw your deposit. Required existing 'deposit_id' field

```json
{
  "date": "2002-12-09",
  "transaction_details": {
    "amount": 30000,
    "transaction_type": "WITHDRAW_DEPOSIT",
    "from_account_id": 1,
    "deposit_id": 1
  }
}
```

5 Way: top up your account balance. Required existing 'from_account_id' and 'currency' field
```json
{
  "date": "2002-12-09",
  "transaction_details": {
    "amount": 30000,
    "transaction_type": "TOP_UP_ACCOUNT_BALANCE",
    "currency": "RUB",
    "from_account_id": 1
  }
}
```
