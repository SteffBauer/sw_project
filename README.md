# Softwaredevelopment project
Project for the course 'Softwaredevelopment' at OTH Regensburg

## API documentation

* [Find customer](documentation/customer/findCustomer.md) : `GET /api/customers`
* [Get account value](documentation/banking/getAccountValue.md) : `GET /api/banking/accounts`
* [Transfer money](documentation/banking/transferMoney.md) : `POST /api/banking/transfers/transfer`
* [Mandate money](documentation/banking/mandateMoney.md) : `POST /api/banking/transfers/mandate`


## Deployment
The prerequisite for this is the use of vpn access.
The password is always the "NDS-Account-Id".

### Commands

1. Generate JAR
1. Copy JAR: `scp C:\Workspace\sw_project\target\bank-0.0.1-SNAPSHOT.jar sw_stefan_bauer@im-codd:`
1. Connect to server: `ssh sw_stefan_bauer@im-codd`
1. In case of changed environment variables: `export \$(cat .env | xargs)`
1. Check copied file: `ls -l`
1. Start as daemon process: `java -jar bank-0.0.1-SNAPSHOT.jar > server.log 2> error.log &`
1. Show running processes: `ps -f`
1. Kill process:
    1. In case you know the PID: `kill <PID>`
    1. The mor common case:
        1. Get PID: `netstat -an --tcp --program`
        1. Then kill it.
1. Exit Secure Shell: `exit`


## Links
[HTTP Status Codes](https://de.wikipedia.org/wiki/HTTP-Statuscode) \
[HTTP Methods Explained](https://restfulapi.net/http-methods/#delete)