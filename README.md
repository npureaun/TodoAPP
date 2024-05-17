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

### 👾 부가기능

할 일 목록은 우선 완료여부로 정렬하고, 등록 시간을 기준으로 내립차순 정렬합니다.
```ruby
[
  {
    "id": 12,
    "title": "string",
    "description": "string",
    "createdDate": "2024-05-16T17:39:24.192098",
    "writer": "string",
    "success": true,
    "commentList": []
  },
  {
    "id": 13,
    "title": "3",
    "description": "3",
    "createdDate": "2024-05-14T17:31:29.574218",
    "writer": "3",
    "success": true,
    "commentList": []
  }
]
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

### <code>ERD</code>

<img width="304" alt="erd" src="https://github.com/npureaun/TodoApp/assets/98468118/73c74fa8-4750-4246-a80d-c06704693f30">


# <p align="right"><a href="#-목차-">🔝</a></p>

---
## 🤖 시도해본 것

### 👾 "Todo선택 조회시"에 댓글 목록을 불러오는 기능에 고민했습니다.
<details>
<summary><code>fun Todo.toResponse(commentList: List<Comment> = emptyList()): TodoResponse</code></summary>
    
```kotlin
/*
1. 코드단에서 comment 엔티티를 리스트로 가지고 있다가 조회시 그대로 보내야 하는가?
2. 부모-자식관계지만, 따로 격리시켜놓다가 필요할때만 호출해 엮어서 보내야 하는가?
= 2안을 선택 : 부모가 자식엔티티를 리스트로 들고 있게된다면, 의존성이 지나치게 높아질거라 판단
이를 구현하기 위해, 매개변수 초기화로 emptyList를 주어 다른 요청에는 비어있는 데이터를 보내고,
댓글조회가 필요한 기능일때만 쿼리를 통해 list를 넘겨주어 데이터를 채워보내는 방법을 구상
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

### 👾 부모-자식관계에서 연결관계처리에 대해 고민했습니다.
<details>
<summary><code>class Comment</code></summary>
    
```kotlin
/*
1. 양방향
2. OneToMany
3. ManyToOne
= 3안을 선택 : 자식테이블에 업데이트가 생겼을 때, 자식테이블에 바로 연결되어 요청이 수행되어야
부모는 자식간에도 캡슐화를 지켜낼 수 있고, 그로인해 자식의 업데이트에도 부모의 추가적인 save없이도
업데이트에 대응가능한 관계가 설정될 것이라 판단
*/
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="todo_id")
val todo: Todo,
```

</details>


# <p align="right"><a href="#-목차-">🔝</a></p>

---
