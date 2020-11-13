# Open session

Used to get a valid session string for a specific bank account.

**URL** : `/api/banking/session`

**Method** : `POST`

**Authentication required** : NO
**Registration required** : YES

**Data constraints**

```json
{
    "username": "[registered user]",
    "password": "[password in plain text]"
}
```

**Data example**

```json
{
    "username": "DE12345566345",
    "password": "most_secure_password_ever"
}
```

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
    "token": "93144b288eb1fdccbe46d6fc0f241a51766ecd3d"
}
```

## Error Response

**Condition** : If 'username' and 'password' combination is wrong.

**Code** : `400 BAD REQUEST`

**Content** : No content provided.