## ISSUE API

### 1. 이슈 목록 조회

`GET {host}/api/v1/issues?status=TODO`

#### Request Params

| name   | type   | description                                |
|--------|--------|--------------------------------------------|
| status | string | `TODO(default)`, `IN_PROGRESS`, `RESOLVED` |


#### Response
200 OK

```json
[
  {
    "id": 1,
    "comments": [
      {
        "id": 2,
        "issueId": 1,
        "userId": 1,
        "body": "댓글 입니다.",
        "username": "테스트"
      },
      {
        "id": 1,
        "issueId": 1,
        "userId": 1,
        "body": "댓글 입니다.",
        "username": "테스트"
      }
    ],
    "summary": "테스트",
    "description": "테스트 내용",
    "userId": 1,
    "type": "TASK",
    "priority": "LOW",
    "status": "TODO",
    "createdAt": "2024-09-02 14:09:11",
    "updatedAt": "2024-09-02 14:09:11"
  }
]
```
- type: `BUG`, `TASK`
- priority: `LOW`, `MEDIUM`, `HIGH`
- status: `TODO`, `IN_PROGRESS`, `RESOLVED`

### 2. 이슈 등록

`POST {host}/api/v1/issues`

#### Request Body

```json
{
  "summary": "테스트",
  "description": "테스트 내용",
  "userId": 1,
  "type": "TASK",
  "priority": "LOW",
  "status": "TODO"
}
```

#### Response
200 OK

```json
{
  "id": 1,
  "comments": [],
  "summary": "테스트",
  "description": "테스트 내용",
  "userId": 1,
  "type": "TASK",
  "priority": "LOW",
  "status": "TODO",
  "createdAt": "2024-09-02 14:09:11",
  "updatedAt": "2024-09-02 14:09:11"
}
```

### 3. 이슈 상세 조회

`GET {host}/api/v1/issues/{issueId}`

#### Response
200 OK

```json
{
  "id": 1,
  "comments": [
    {
      "id": 2,
      "issueId": 1,
      "userId": 1,
      "body": "댓글 입니다.",
      "username": "테스트"
    },
    {
      "id": 1,
      "issueId": 1,
      "userId": 1,
      "body": "댓글 입니다.",
      "username": "테스트"
    }
  ],
  "summary": "테스트",
  "description": "테스트 내용",
  "userId": 1,
  "type": "TASK",
  "priority": "LOW",
  "status": "TODO",
  "createdAt": "2024-09-02 14:09:11",
  "updatedAt": "2024-09-02 14:09:11"
}
```

### 4. 이슈 수정

`PUT {host}/api/v1/issues/{issueId}`

#### Request Body
```json
{
  "summary": "테스트 수정",
  "description": "테스트 내용 수정",
  "type": "TASK",
  "priority": "HIGH",
  "status": "IN_PROGRESS"
}
```

#### Response
200 OK

```json
{
  "id": 1,
  "comments": [
    {
      "id": 2,
      "issueId": 1,
      "userId": 1,
      "body": "댓글 입니다.",
      "username": "테스트"
    },
    {
      "id": 1,
      "issueId": 1,
      "userId": 1,
      "body": "댓글 입니다.",
      "username": "테스트"
    }
  ],
  "summary": "테스트 수정",
  "description": "테스트 내용 수정",
  "userId": 1,
  "type": "TASK",
  "priority": "HIGH",
  "status": "IN_PROGRESS",
  "createdAt": "2024-09-02 14:09:11",
  "updatedAt": "2024-09-02 14:09:11"
}
```

### 5. 이슈 삭제

`DELETE {host}/api/v1/issues/{issueId}`

#### Response
204 No Content

### 6. 코멘트 등록

`POST {host}/api/v1/issues/{issueId}/comments`

#### Request Body
```json
{
  "body": "댓글 입니다."
}
```

#### Response
200 OK

```json
{
  "id": 1,
  "issueId": 1,
  "userId": 1,
  "body": "댓글 입니다.",
  "username": "테스트"
}
```

### 7. 코멘트 수정

`PUT {host}/api/v1/issues/{issueId}/comments/{commentId}`

#### Request Body

```json
{
  "body": "댓글 변경"
}
```

#### Response
200 OK

```json
{
  "id": 1,
  "issueId": 1,
  "userId": 1,
  "body": "댓글 변경",
  "username": "테스트"
}
```

### 8. 코멘트 삭제

`DELETE {host}/api/v1/issues/{issueId}/comments/{commentId}`

#### Response
204 No Content

---

## USER API

### 1. 회원 가입

`POST {host}/api/v1/users/signup`

#### Request Body
```json
{
  "email": "test@email.com",
  "password": "1234",
  "username": "user"
}
```

#### Response
200 OK

### 2. 로그인

`POST {host}/api/v1/users/signin`

#### Request Body
```json
{
  "email": "test@email.com",
  "password": "1234"
}
```

#### Response
200 OK

```json
{
  "email": "test@email.com",
  "username": "user",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJqYXJhIiwic3ViIjoiYXV0aCIsImlhdCI6MTcyNTQzNjY3MSwiZXhwIjoxNzI1NDQwMjcxLCJ1c2VySWQiOjEsImVtYWlsIjoidGVzdEBlbWFpbC5jb20iLCJwcm9maWxlVXJsIjpudWxsLCJ1c2VybmFtZSI6InVzZXIifQ.qSC3l5-wdgW0bWkSUWi-C4P8NzYqdn0FuLcJPXP_XJo"
}
```

### 3. 로그아웃

`DELETE {host}/api/v1/users/logout`

#### Request Header
```text
Authorization: Bearer {token}
```

#### Response
204 NO_CONTENT

### 4. 내정보 조회

`GET {host}/api/v1/users/me`

#### Request Header
```text
Authorization: Bearer {token}
```

#### Response
200 OK

```json
{
  "id": 1,
  "profileUrl": null,
  "username": "user",
  "email": "test@email.com",
  "createdAt": "2024-09-04T20:14:29.687632",
  "updatedAt": "2024-09-04T20:14:29.687632"
}
```

### 5. 리포터 조회

`GET {host}/api/v1/users/{reporterId}/username`

#### Response
200 OK

```json
{
  "reporter": "user"
}
```

### 6. 내정보 수정

`POST {host}/api/v1/users/{userId}`

#### Request Header
```text
Content-Type: multipart/form-data
```
