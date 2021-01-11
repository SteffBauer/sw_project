# Find customer

Find customer by providing a tax number.

**URL** : `/api/customers`

**Method** : `GET`


**Data example**

```json
1234567890
```

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
  "id": 123,
  "forename": "Max",
  "surname": "Muster",
  "taxNumber": "123456",
  "address":
  {
    "id": 321,
    "street": "Examplestreet",
    "houseNr": 5,
    "zipCode": 1234,
    "city": "Examplecity",
    "country": "Examplecountry"
  }
}
```

## Error Response

**Condition** : There is no registered customer with the provided tax number.

**Code** : `404 Not Found`

**Content** : No content provided.