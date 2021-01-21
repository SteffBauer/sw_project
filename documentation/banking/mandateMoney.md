# Mandate Money

Mandate Money of another bank account.

**URL** : `/api/banking/transfers/transfer`

**Method** : `POST`

**Required Header** : `access-token`

**Data example**

```json
{
  "receiverForename": "Max",
  "receiverSurname": "Muster",
  "receiverIban": "DE12345678902345",
  "iban": "DE126789098767876",
  "date": "2021-01-12",
  "description": "Concert tickets",
  "amount": 1000
}
```

## Success Response

**Code** : `201 Created`

**Content example**

```json
{
  "receiverForename": "Max",
  "receiverSurname": "Muster",
  "receiverIban": "DE12345678902345",
  "iban": "DE126789098767876",
  "date": "2021-01-12",
  "description": "Concert tickets",
  "amount": 1000
}
```

## Error Response

---
**Condition** : The access token is not valid.

**Code** : `401 Unauthorized`

---
**Condition** : The given iban is not in the system.

**Code** : `404 Not Found`

---
**Condition** : The requested transfer has the same payer and receiver iban.

**Code** : `409 Conflict`