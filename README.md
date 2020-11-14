# Softwaredevelopment project
Project for the course 'Softwaredevelopment' at OTH Regensburg

## API documentation

### Customer Service

The customer related call "Register Customer" can be made without opening a session.
To create an account, the customer or the employee have to be logged in.
The calls "Delete Customer", "Delete Account" and "Find Customer" can only be made by an employee of the bank.

* [Register customer](documentation/customer/registerCustomer.md) : `POST /api/customers`
* [Find customer](documentation/customer/findCustomer.md) : `GET /api/customers/:id`
* [Delete customer](documentation/customer/deleteCustomer.md) : `Delete /api/customers/:id`
* [Create account](documentation/customer/createAccount.md) : `Put /api/customers/:id/createAccount`
* [Delete account](documentation/customer/deleteAccount.md) : `Delete /api/customers/:id/deleteAccount`


### Session Service

To make banking related api calls, you have to open a session.
With the returned session string, you can make account related transfers or other requests.

* [Open session](documentation/session/openSession.md) : `POST /api/session`


### Banking Service

For each banking request, you have to provide a valid session, which is not older than ten minutes. 

* [Get account value](documentation/banking/getAccountValue.md) : `GET /api/banking/value`
* [Get account transfers](documentation/banking/getAccountTransfers.md) : `GET /api/banking/transfers`
* [Transfer money](documentation/banking/transferMoney.md) : `PUT /api/banking/transfer`
* [Mandate money](documentation/banking/mandateMoney.md) : `PUT /api/banking/mandate`


## Links
[HTTP Status Codes](https://de.wikipedia.org/wiki/HTTP-Statuscode)
