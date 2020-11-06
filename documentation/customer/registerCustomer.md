# Register customer

Register customer to have access to bank accounts

**URL** : `/api/customers`

**Method** : `POST`

**Auth required** : No

**Data example**

```json
{
  "forename": "Max",
  "surname": "Muster",
  "taxNumber": "123456",
  "password": "most_secure_password_ever",
  "address":
  {
    "street": "Examplestreet",
    "houseNr": 5,
    "zipCode": 1234,
    "city": "Examplecity",
    "country": "Examplecountry"
  }
}
```

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
  "customerId": 123,
  "forename": "Max",
  "surname": "Muster",
  "taxNumber": "123456",
  "password": "most_secure_password_ever",
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

**Condition** : If required field is null or empty.

**Code** : `400 BAD REQUEST`

**Content** :

```json
{
  "customerId": 0,
  "forename": "",
  "surname": "",
  "taxNumber": "123456",
  "password": "most_secure_password_ever",
  "address":
  {
    "addressId": 0,
    "street": "Examplestreet",
    "houseNr": 5,
    "zipCode": 1234,
    "city": "Examplecity",
    "country": "Examplecountry"
  }
}
```