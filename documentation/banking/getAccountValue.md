# Get Account Value

Get the value of a bank account.

**URL** : `/api/banking/accounts`

**Method** : `GET`

**Data example**

```json
{
  "iban": "DE1234567890100000002"
}
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

**Condition** : The submitted iban is null or empty

**Code** : `400 Bad Request`

**Condition** : The given iban is not in the system.

**Code** : `404 Not Found`

