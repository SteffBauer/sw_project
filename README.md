# Softwaredevelopment project
Project for the course 'Softwaredevelopment' at OTH Regensburg

## API documentation

### Customer Service

The customer related call "Register Customer" can be made without opening a session.
To create an account the customer or the employee have to be logged in.
The calls "Delete Customer", "Delete Account", "Get address of customer",
"Update address of customer" and "Find Customer" can only be made by an employee of the bank.

* [Register customer](documentation/customer/registerCustomer.md) : `POST /api/customers`
* [Find customer](documentation/customer/findCustomer.md) : `GET /api/customers/:id`
* [Delete customer](documentation/customer/deleteCustomer.md) : `DELETE /api/customers/:id`
* [Get address of customer](documentation/customer/getAddress.md) : `GET /api/customers/:id/address`
* [Update address of customer](documentation/customer/updateAddress.md) : `PUT /api/customers/:id/address`
* [Create account](documentation/customer/createAccount.md) : `PUT /api/customers/:id/createAccount`
* [Delete account](documentation/customer/deleteAccount.md) : `DELETE /api/customers/:id/deleteAccount`


### Session Service

To make banking related api calls, you have to open a session.
With the returned session string, you can make account related transfers or other requests.

* [Open session](documentation/session/openSession.md) : `POST /api/session`


### Support Service

Support can be given by an employee and can be used by an customer, no matter if he is logged
in or not.

* [Ask for support](documentation/support/useSupport.md) : `POST /api/support`
* [Conversation](documentation/support/conversation.md) : `PUT /api/support/{id}`


### Banking Service

For each banking request, you have to provide a valid session, which is not older than ten minutes. 

* [Get account value](documentation/banking/getAccountValue.md) : `GET /api/banking/value`
* [Get account transfers](documentation/banking/getAccountTransfers.md) : `GET /api/banking/transfers`
* [Transfer money](documentation/banking/transferMoney.md) : `PUT /api/banking/transfer`
* [Mandate money](documentation/banking/mandateMoney.md) : `PUT /api/banking/mandate`


## Links
[HTTP Status Codes](https://de.wikipedia.org/wiki/HTTP-Statuscode) \
[HTTP Methods Explained](https://restfulapi.net/http-methods/#delete)

## Deployment
The prerequisite for this is the use of vpn access.
The password is always the "NDS-Account-Id".

### Commands

1. Generate JAR
1. Copy JAR: `scp "C:\Workspace\sw_project\target\bank-0.0.1-SNAPSHOT.jar" sw_stefan_bauer@im-codd:`
1. Connect to server: `ssh sw_stefan_bauer@im-codd`
1. Check copied file: `ls -l`
1. Start as daemon process: `java -jar bank-0.0.1-SNAPSHOT.jar > server.log 2> error.log &`
1. Show running processes: `ps -f`
1. Kill process:  `kill <PID>`
1. Exit Secure Shell: `exit`