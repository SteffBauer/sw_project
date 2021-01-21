# Get Account Value

Get the value of a bank account.

**URL** : `/api/banking/accounts/{iban}`

**Method** : `GET`

**Required Header** : `access-token`


**Example call**

```json
/api/banking/accounts/DE12345678903456
```

## Success Response

**Code** : `200 OK`

**Content example**

The returned value is given in cents.
The value divided by 100 is the amount in euros.

```json
320000
```

## Error Response

---
**Condition** : The submitted iban is null or empty

**Code** : `400 Bad Request`

---
**Condition** : The access token is not valid.

**Code** : `401 Unauthorized`

---
**Condition** : The given iban is not in the system.

**Code** : `404 Not Found`