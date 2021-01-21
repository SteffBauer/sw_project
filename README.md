# Softwaredevelopment project

Project for the course 'Softwaredevelopment' at OTH Regensburg.

If you want to use the api, please refer to the section
[API documentation](#api-documentation). The notes of the owner of the repository are stored in the section
[Notes](#notes). In case you are here for testing the software, please refer to the section
[Testing](#testing). All information you need can be found there.

## API documentation

* [Find customer](documentation/customer/findCustomer.md) : `GET /api/customers`
* [Get account value](documentation/banking/getAccountValue.md) : `GET /api/banking/accounts`
* [Transfer money](documentation/banking/transferMoney.md) : `POST /api/banking/transfers/transfer`
* [Mandate money](documentation/banking/mandateMoney.md) : `POST /api/banking/transfers/mandate`

## Testing

### Predefined employee accounts

| Username | Password |
| -------- | -------- |
| max      | admin    |
| thomas   | admin    |

> **_NOTE:_**  The registration of new employee accounts was not part of the defined requirements.

### Attendant assignment

To assign an employee to a new customer, the employee with the fewest amount
of customers is taken an assigned to the new customer.

### Subsequent modifications

> **_NOTE:_** EmployeeService, additional attributes, ... 

## Notes

The prerequisite for this is the use of vpn access. The password is always the "NDS-Account-Id".

### Commands for the deployment

The prerequisite for this is the use of vpn access. The password is always the "NDS-Account-Id".

1. Run `Generate JAR` in IDE
1. Copy JAR: `scp C:\Workspace\sw_project\target\bank-0.0.1-SNAPSHOT.jar sw_stefan_bauer@im-codd:`
1. Connect to server: `ssh sw_stefan_bauer@im-codd`
1. In case of changed environment variables: `export $(cat .env | xargs)`
1. Check copied file: `ls -l`
1. Start as daemon process: `java -jar bank-0.0.1-SNAPSHOT.jar > server.log 2> error.log &`
1. Show running processes: `ps -f`
1. Kill process:
    1. In case you know the PID: `kill <PID>`
    1. The mor common case:
        1. Get PID: `netstat -an --tcp --program`
        1. Then kill it.
1. Exit Secure Shell: `exit`

### Useful links

[Spring: Guides](https://spring.io/guides) \
[Spring: JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#preface) \
[Baeldung: Spring Guides](https://www.baeldung.com/) \
[Spring and Thymeleaf](https://www.thymeleaf.org/doc/tutorials/2.1/thymeleafspring.html)

[HTTP Status Codes](https://de.wikipedia.org/wiki/HTTP-Statuscode) \
[HTTP Methods Explained](https://restfulapi.net/http-methods/#delete)


