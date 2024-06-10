![header](https://capsule-render.vercel.app/api?type=waving&text=n.Pureun&height=200&color=73C2FB&animation=fadeIn&fontColor=003153)

# 🖥 Todo 🖥
### 할 일을 관리합니다.

---

## 🔷 목차 🔷
### <p align="left"><a href="#-기능과-사용법">📒 기능과 사용법 </a></p>
### <p align="left"><a href="#-프로그램-구조">🌃 프로그램 구조 </a></p>
### <p align="left"><a href="#-시도해본-것">🤖 시도해본 것 </a></p>

---
## 📒 기능과 사용법

프로그램의 사용법과, 기능을 소개합니다.

### 👾 사용법

todo를 조회, 등록, 수정, 삭제할 수 있습니다.
```ruby
@RequestMapping("/todos")
```

comment를 조회, 등록, 수정, 삭제할 수 있습니다.
```ruby
@RequestMapping("/todos/{todoId}/comments")
```

User를 등록, 로그인 할 수 있습니다.
```ruby
@RequestMapping("/users")
```

### 👾 부가기능

할일 목록의 완료여부와, 생성시간을 기반으로 정렬을 선택 할 수 있습니다.

<img width="499" alt="스크린샷 2024-05-24 오전 11 41 38" src="https://github.com/npureaun/image/assets/98468118/ba526453-27ab-4dd6-a628-da25f67d06ce">

유저정보를 저장할때, 패스워드를 암호화 합니다.
```ruby
request
{
  "userId": "n.pureun",
  "userPassword": "mypassword"
}

database
{
  "userId": "n.pureun",
  "userPassword": "$2a$10$duUCWEzZMRe1intGOBfOxuCH8rhXVpEEI1tjN1EffeKDRdkxBGu9G"
}
```

todo에 딸린 comment를 불러옵니다.
```ruby
"id": 93,
"title": "string",
"description": "string",
"created": "2024-05-24T11:52:35.229057",
"writer": "string",
"success": false,
"commentList": [
  {
    "id": 141,
    "writer": "string",
    "comment": "string"
  },
  {
    "id": 142,
    "writer": "string",
    "comment": "string"
  }
]
```

todo를 페이지네이션 합니다.
```ruby
"pageable": {
    "pageNumber": 0,
    "pageSize": 5,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "first": true,
  "last": true,
  "size": 5,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "numberOfElements": 1,
  "empty": false
}
```


### 👾 예외처리

엔티티가 없으면 예외를 발생합니다
```ruby
"message": "UPDATE : Passwords do not match",
"errorCode": null
```

댓글 수정, 삭제시 올바르지 않으면 예외를 발생합니다.
```ruby
"message": "UPDATE : Passwords do not match",
"errorCode": null
```

중복된 userId가 들어오면 db에 메세지를 보내고 저장하지 않습니다.
```ruby
Error: response status is 409
{
  "message": "Data Duplication",
  "errorCode": null
}
```

UserId와 password가 일치하지 않을시 메세지를 보냅니다.
```ruby
Error: response status is 401
{
  "message": "User Password Not Match",
  "errorCode": null
}
```


# <p align="right"><a href="#-목차-">🔝</a></p>

---
## 🌃 프로그램 구조

### <code>Layer</code>

![layer](https://github.com/npureaun/image/assets/98468118/95c510e8-c196-4fdb-a0b0-ff114f043111)

### <code>UseCaseDiagram</code>

<img width="604" alt="diagram" src="https://github.com/npureaun/TodoApp/assets/98468118/0b3d661c-dfeb-42d8-886f-9fb548589bd3">

### <code>apiSpecification</code>

<img width="684" alt="Todo" src="https://github.com/npureaun/TodoApp/assets/98468118/7b178984-0f55-4618-9352-5b5f5bbc7ec5">
<img width="676" alt="comment" src="https://github.com/npureaun/TodoApp/assets/98468118/8571e9b1-5f7c-47d5-b38a-161455f1e29f">
<img width="695" alt="스크린샷 2024-05-24 오후 12 57 28" src="https://github.com/npureaun/image/assets/98468118/4cdda695-dfcb-4a16-ad30-0c7f260775f2">

### <code>ERD</code>

<img width="384" alt="스크린샷 2024-06-10 오전 11 51 38" src="https://github.com/npureaun/TodoApp/assets/98468118/12b24acd-91cd-44f7-8b78-0f420166ef6e">


# <p align="right"><a href="#-목차-">🔝</a></p>

---
## 🤖 시도해본 것

### 👾 "Todo선택 조회시"에 댓글 목록을 불러오는 기능에 고민했습니다.
<details>
<summary><code>fun Todo.toResponse(commentList: List<Comment> = emptyList()): TodoResponse</code></summary>
    
```kotlin
/*
다른 경우에는 매개변수 초기화로 emptyList를 주어 다른 요청에는 비어있는 데이터를 보내고,
댓글조회가 필요한 기능일때만 list를 넘겨주어 데이터를 채워보내는 방법을 구상
*/
 return TodoResponse(
      id = id!!,
      title = title,
      description = description,
      createdDate = createdDate,
      writer = writer,
      success = success,
      commentList = commentList.map { it.toResponse() }
  )
```

</details>

### 👾 정렬기능에 대하여 Enum Class를 사용하였습니다.
<details>
<summary><code>enum class SortTodoSelector(val sort: Sort)</code></summary>
    
```kotlin
/*
Enum Class를 통해 쿼리에 적용 가능한 Sort객체를 지정하고, 그에 맞게 쿼리문을 실행하도록 하여
쿼리함수의 최소화, 동적화를 도모
*/
{
    SUCCESS_ASC_DATE_ASC(
        Sort.by(
            Sort.Order.asc("success"),
            Sort.Order.asc("created")
        )),
    SUCCESS_DESC_DATE_ASC(
        Sort.by(
            Sort.Order.desc("success"),
            Sort.Order.asc("created")
        )),
    SUCCESS_ASC_DATE_DESC(
        Sort.by(
            Sort.Order.asc("success"),
            Sort.Order.desc("created")
        )),
    SUCCESS_DESC_DATE_DESC(
        Sort.by(
            Sort.Order.desc("success"),
            Sort.Order.desc("created")
        ));
}
```



</details>


### 👾 페이지 네이션과 관련하여 jpa Slice를 사용하였습니다.
<details>
<summary><code>override fun getAllTodoList(sortBy: SortTodoSelector, writer:String, page:Int): Slice<TodoResponse></code></summary>
    
```kotlin
/*
이전에 직접 쿼리를 통한 구현과 라이브러리를 쓰는 것중에
여러 지원이 잘 되는 라이브러리를 쓰는게 좋을거 같았고,
정렬처리를 직접 쿼리로 구현하지 않고, 인자로 넘겨주면 자동으로 해준다는 것에 매력을 느낌.
Slice와 Page 둘중에 어떤것을 고를지 생각하다, 우선은 성능적으로 이점이 있고
추후에 무한 스크롤로 확장 가능한 Slice가 좋겠다고 판단.
Todo를 정말 웹사이트로 구현한다면 sns처럼 사용될것이라 추론
*/
val pageable:Pageable = PageRequest.of(page,5, sortBy.sort)
val todoList = if (writer.isEmpty()) todoRepository.findAllWithSort(pageable)
```

</details>

### 👾 n+1쿼리 문제에 대응하였습니다.
<details>
<summary><code>interface TodoRepository:JpaRepository </code></summary>
    
```kotlin
/*
@EntityGraph 그래프를 통해 Todo정보를 불러올때, 딸려오는 Comment에 대한 n+1쿼리 문제를 해결.
*/
@EntityGraph(attributePaths = ["comments"])
@Query("SELECT tl FROM Todo tl")
fun findAllWithSort(pageable: Pageable): Slice<Todo>
```

</details>

### 👾 토큰 발행에는 Jwts를 사용하였습니다.
<details>
<summary><code>object JwtUtil</code></summary>
    
```kotlin
/*
토큰관련 구현방법에 대한 정보가 부족해 우선 간단한 라이브러리를 통해 사용법 부터 익히는 것이 도움이 될거라 생각
*/
fun generateToken(userId: String, expirationInMillisecond: Long = EXPIRATION_TIME):String
fun generateClaims(now:Date, expiration:Date): Map<String,String>
fun getUserIdFromToken(token: String): String
```

</details>

### 👾 <code>SpringSecurity</code>를 도입했습니다.
<details>
<summary><code>class SecurityConfig</code></summary>
    
```kotlin
fun filterChain(http: HttpSecurity): SecurityFilterChain

```

</details>

### 👾 <code>Token</code>에는 최소한의 정보만 담고, 유저정보는 최소정보를 토대로 조회후 UserResponse로 리턴합니다.
<details>
<summary><code>fun getUserInfo():UserResponse</code></summary>
    
```kotlin
 return SecurityContextHolder
    .getContext().authentication.principal.toString()
    .let { """email=([^,]+)""".toRegex().find(it) }
    .let { it?.groups?.get(1)?.value//토큰에서 페이로드의 email정보 추출
        ?: throw EntityNotFoundException("User email not found in Token") }
    .let { userRepository.findByUserEmail(it)?.toResponse()//추출된 정보로 UserTable조회후 DTO로 Return
        ?:throw EntityNotFoundException("User Not Found")}

```

</details>

### 👾 <code>Factory Pattern</code>을 적용해 보았습니다.
<details>
<summary><code>class Todo</code></summary>
    
```kotlin
/* Save과정 중 Service에서 entity를 작성하는 과정이 너무 노출되고, 줄이 길이진다 판단,
 entity save의 과정을 entity에게 위임하고자 하였고, 그에 대한 방법으로 Factory Pattern을 적용해보기로 하였음
 companion object {
        fun saveEntity(request: CreateTodoRequest, userInfo: UserResponse): Todo {
            return Todo(
                title = request.title,
                description = request.description,
                nickname = userInfo.nickname,
                userEmail = userInfo.userEmail
            )
        }
    }
```

</details>

### 👾 <code>MethodArgumentNotValidException</code>을 적용해 보았으며, 

### GlobalExceptionHandler가 spring에서 지원하는 예외를 쓰도록 하였습니다.
<details>
<summary><code>class GlobalExceptionHandler</code></summary>
    
```kotlin
@ExceptionHandler(CreateUpdateException::class)
@ExceptionHandler(EntityNotFoundException::class)
@ExceptionHandler(AuthenticationException::class)
@ExceptionHandler(ServiceException::class)
@ExceptionHandler(IllegalArgumentException::class)
@ExceptionHandler(MethodArgumentNotValidException::class)

```

</details>



# <p align="right"><a href="#-목차-">🔝</a></p>

---
