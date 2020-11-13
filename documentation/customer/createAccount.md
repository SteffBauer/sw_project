# Register customer

Register customer to have access to bank accounts

**URL** : `/api/customers/{id}/createaccount`

**Method** : `PUT`

**Authentication required** : No

**Registration required** : Yes

**Data constraints**

`/api/customers/[valid user id]/createaccount`

**Data example**

`/api/customers/234235/createaccount`

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
  "id": 13546,
  "iban": "DE1010010106464"
}
```

## Error Response

**Condition** : The id is not valid.

**Code** : `400 BAD REQUEST`

**Content** : No content provided.