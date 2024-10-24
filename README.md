# 항해플러스 3주차 Step3

## 선택 시나리오 : e-커머스 서비스

### milestone
<details>
  <summary>milestone</summary>
3주차 (10월 05일 ~ 10월 11일)
- 프로젝트 초기 설정

4~5주차 (10월 12일 ~ 10월 25일)

- 기본 기능 구현
    - 잔액 충전/조회
    - 상품 조회
    - 주문/결제
- 추가 기능 구현
    - 상위 상품 조회
    - 장바구니 기능

6~7주차 (10월 26일 ~ 11월 08일)

- 대용량트래픽과 데이터 처리

8~9주차 (11월 09일 ~ 11월 22일)

- 장애 대응 훈련

10주차 (11월 23일 ~ 11월 29일)

- 마무리

![milestone](docs/milestone.png)

</details>


### flow chart
<details>
  <summary>flow chart</summary>

![flowchart](docs/flowchart.png)
</details>

### ERD
<details>
  <summary>ERD</summary>

![ERD](docs/erd.png)
</details>

### Swagger
<details>
  <summary>swagger</summary>

![ORDER](docs/OrderSwagger.png)
![ITEM](docs/ItemSwagger.png)
![BASKET](docs/BasketSwagger.png)
![USER](docs/UserSwagger.png)
</details>


### Api-Spec
<details>
  <summary>Api-Spec</summary>

### 이커머스_API_명세_v0.1

**잔액 충전 / 조회 API**
<details>
  <summary>결제에 사용될 금액을 충전</summary>

> **POST**  */user/charge*
- Request
  ```json
  {
      "id": 1,
      "charge_balance": 5000
  }
  ```
- Response
  ```json
  {
    "status" : "SUCCESS",
    "message" : "충전 성공",
    "data" : {
      "id" : 1,
      "ex_balance" : 2000,
      "charge_balance" : 5000,
      "new_balance" : 7000
    }
  }
  ```
- Error
  ```json
  {
    "error": "Invalid request"
    "message": "충전에 실패했습니다."
  }
  ```
</details>

<details>
  <summary>해당 사용자의 잔액을 조회</summary>

> **POST** */user/balance*
- Request
  ```json
  {
    "id" : 1
  }	
  ```
- Response
  ```json
  {
    "status" : "SUCCESS",
    "message" : "조회 성공",
    "data" : {
      "id" : 1,
      "balance" : 2000,
    }
  }
  ```
- Error
  ```json
  {
    "error": "Invalid request"
    "message": "포인트 조회에 실패했습니다."
  }
  ```
</details>

**상품 조회 API**
<details>
  <summary>상품 정보을 조회</summary>

> **GET** */item/all*
- Response
  ```json
  {
    "status" : "SUCCESS",
    "message" : "조회 성공",
    "data" : [{
        "id" : 1,
        "price" : 1000,
        "name" : "청바지A",
        "stock" : 1
      },{
        "id" : 2,
        "price" : 2000,
        "name" : "슬랙스A",
        "stock" : 2
      },{
        "id" : 3,
        "price" : 3000,
        "name" : "후드티A",
        "stock" : 3
      },{
        "id" : 4,
        "price" : 4000,
        "name" : "맨투맨A",
        "stock" : 4
      }, ...
    }]
  }
  ```
- Error
  ```json
  {
    "error": "Invalid request"
    "message": "상품 목록 조회에 실패했습니다."
  }
  ```
</details>

**주문 / 결제 API**
<details>
  <summary>주문하고 결제를 수행</summary>

> **POST** */order/pay*
- Request
  ```json
  {
      "userId":1,
      "items": [{
        "id":1,
        "name":"청바지A",
        "price":1000
      }, {
        "id":2,
        "name":"후드티B",
        "price":4000
      }]
  }
  ```
- Response
  ```json
  {	
      "status" : "SUCCESS",
      "message" : "구매 성공",
      "data" : {
        "order" : {
        "orderId" : 1,
        "userId" : 1,
        "order_item_count":2
        "total_price": 5000
        "order_dtm" : "2024-10-10 22:32:54"
        },
          "items": [{
              "id":1,
              "name":"청바지A",
              "price":1000
          }, {
              "id":2,
              "name":"후드티B",
              "price":4000
          }]
      }
  }
  ```
- Error
  ```json
  {
      "error": "Invalid request"
      "message": "구매에 실패했습니다."
  }
  ```
</details>

**상위 상품 조회 API**
<details>
  <summary>최근 3일간 가장 많이 팔린 상위 5개 상품 정보를 제공</summary>

> **GET** */item/popular*
- Response
  ```json
  {	
      "status" : "SUCCESS",
      "message" : "조회 성공",
      "data" : [{
              "id" : 1,
              "name" : "청바지A",
              "price" : 1000,
              "stock" : 1,
              "rank" : 1,
              "view" : 5550,
              "order" : 5000
          },{
              "id" : 2,
              "name" : "슬랙스A",
              "price" : 2000,
              "stock" : 2,
              "rank" : 2,
              "view" : 4440,
              "order" : 4000
          },{
              "id" : 3,
              "name" : "후드티A",
              "price" : 3000,
              "stock" : 3,
              "rank" : 3,
              "view" : 3330,
              "order" : 3000
          },{
              "id" : 4,
              "name" : "맨투맨A",
              "price" : 4000,
              "stock" : 4,
              "rank" : 4,
              "view" : 2220,
              "order" : 2000
          },{
              "id" : 4,
              "name" : "후드티B",
              "price" : 4000,
              "stock" : 4,
              "rank" : 5,
              "view" : 1110,
              "order" : 1000
          }
      }]
  }
  ```
- Error
  ```json
  {
    "error": "Invalid request",
    "message": "인기 상품 목록 조회에 실패했습니다."
  }
  ```
</details>
</details>


### 주요 기술 스택
<details>
  <summary>기술 스택 </summary>

- **Java 버전**: 17
- **Spring Boot 버전**: 3.3.1
- **데이터베이스**: H2 + @
- **ORM**: Spring Data JPA, QueryDSL
- **API 문서**: Spring REST Docs
</details>

### 패키지 구조
<details>
  <summary>패키지 구조</summary>

아직 미정...
토요일까지 개인공부 후에 정할 예정 (레이어 + 클린)
</details>

### 중간 회고록
<details>
  <summary>중간회고록</summary>

### 1. 2-1  시나로이 분석 및 작업 계획
해당 주차를 쉬어가는 타이밍이라고 생각을 했다.

그전에 빡세게 한것도 아니면서 보상심리가 있었나보다... 

해당 주차에 가벼운 마음으로 프로젝트를 정하고 시나리오를 짜고 정말 가벼운 마음으로 했다.

이것이 후폭풍으로 돌아올것이라고 생각을 못하고... 

### 2-2  비즈니스 로직 개발 및 유닛&통합 테스트 작성 
전 주차에 설렁설렁하던것이 스노우볼이 되어서 굴러왔다. 

개발을 하면서 계속 바뀌는 erd와 시나리오... 처음부터 완벽할수는 없다지만 이번에는 내가 너무 안일했다. 

일을하면서 항상 주어진일만 진행해왔고 뭔가 주도적으로는 안했었구나를 많이 깨달은 주간이였다.

하지만 이때 뭔가 확실하지 않았던 TDD, 유닛(단위) 테스트의 개념이 살짝쿵 잡히기 시작했고, 많은 수정이 있엇지만 잘 진행했다. 

점점 깔끔해지는 팀원들의 PR과 코드들을 보면서 많이 참고하고 노력했던 한주였던것 같다. 

### 2-3  exception과 filter(interceptor) 적용 & 동시성 통합 테스트 작성
전 주차를 그래도 나름 잘 끝냈다고 생각하고 진행하고 있었는데...

코치님과의 멘토링 그리고 학습메이트와의 이야기를 통해 아직도 많이 변경해야될게 많다는것을 느꼈다. 

그 전부터 점차 알고있던 나의 메타인지... 그 것이 멘토링에서 더 크게 느껴졌던거 같다.

많이 부족하고 많은 것을 놓치면서 회사에서 시키는 거만 하고있다는 것을 느꼈다. 

그와 동시에 약간의 공부 방향성에 대해서 감을 잡은 한주가 되었던거 같다.

이것을 다 느끼기도 전에 학습메이트 태한님께서 DIP가 적용안되어있다라는 말을 해주셨다.

함꼐 코드를 보며 DIP에 대해 또 DIP의 적용에 대해서 말해주셨다.

이런거를 보며 느낀 것은 내가 평소에 객체지향적은 물론 너무 생각없이 일을 해왔구나 라는 생각이 들었다.

시작할때 들었던 방향성을 잡아준다는게 이런걸까 라는 생각이 들면서 메타인지가 제대로 된 한주였다.

</details>
