# Softwaredevelopment project
Project for the course 'Softwaredevelopment' at OTH Regensburg

## API documentation

### Customer Service

Customer related calls can be made without opening a session.

* [Register customer](documentation/customer/registerCustomer.md) : `POST /api/customers`
* [Find customer](documentation/customer/findCustomer.md) : `GET /api/customers/:id`
* [Create account](documentation/customer/createAccount.md) : `Put /api/customers/:id/createAccount`


### Session Service

To make banking related api calls, you have to open a session. With the returned session string, you can make account related transfers or other requests.

* [Open session](documentation/session/openSession.md) : `POST /api/banking/session`

### Banking Service

Foreach banking request, you have to provide a session string. 

* [Get account value](documentation/banking/getAccountValue.md) : `GET /api/banking/account/value`
* [Get account transfers](documentation/banking/getAccountTransfers.md) : `GET /api/banking/account/transfers`
* [Transfer money](documentation/banking/transferMoney.md) : `PUT /api/banking/account/transfer`
* [Mandate money](documentation/banking/mandateMoney.md) : `PUT /api/banking/account/mandate`
