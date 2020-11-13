# Register customer

Register customer to have access to bank accounts

**URL** : `/api/customers/{id}`

**Method** : `GET`

**Authentication required** : No

**Registration required** : NO

**Data constraints**

`/api/customers/[valid user id]`

**Data example**

`/api/customers/234235`

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
  "customerId": 123,
  "forename": "Max",
  "surname": "Muster",
  "taxNumber": "123456",
  "address":
  {
    "addressId": 321,
    "street": "Examplestreet",
    "houseNr": 5,
    "zipCode": 1234,
    "city": "Examplecity",
    "country": "Examplecountry"
  }
}
```

## Error Response

**Condition** : The id is not valid.

**Code** : `400 BAD REQUEST`

**Content** : No content provided.