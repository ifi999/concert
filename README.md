# 콘서트 예약 서비스

## 소개
- 대기열 시스템과 좌석 예약 서비스를 구현
- 좌석 예약 시에 미리 충전한 잔액을 이용
- 좌석 요청 시, 결제가 이루어지지 않더라도 일정 시간동안 다른 유저가 해당 좌석에 접근할 수 없음

## 요구 사항
- 유저 토큰 발급 API
- 예약 가능 날짜 조회 API
- 예약 가능 좌석 조회 API
- 좌석 예약 요청 API
- 잔액 충전 API
- 잔액 조회 API
- 결제 API
> 각 기능 및 제약 사항에 대해 단위 테스트를 반드시 하나 이상 작성
> 
> 다수의 인스턴스로 어플리케이션이 동작하더라도 기능에 문제가 없도록 작성
> 
> 동시성 이슈 및 대기열 개념 고려

## API 스펙
1. 유저 대기열 토큰 기능
- 서비스를 이용할 토큰을 발급받는 API를 작성합니다.
- 토큰은 유저의 UUID와 해당 유저의 대기열을 관리할 수 있는 정보(대기 순서 or 잔여 시간 등)를 포함
- 이후 모든 API는 위 토큰을 이용해 대기열 검증을 통과해야 이용 가능
> 기본적으로 폴링으로 본인 대기열 확인한다고 가정. 다른 방안 또한 고려해보고 구현해 볼 수 있음

### 유저 토큰 발급 `POST`

#### 요청 바디
```json
{
  "userId": 1
}
```

#### 요청 예시
```text
POST /api/token
Content-Type: application/json

{
  "userId": 1
}
```

#### 응답 바디
```json
{
  "userId": 1,
  "token": "token"
}
```

2. 예약 가능 날짜 / 좌석 API
- 예약가능한 날짜와 해당 날짜의 좌석을 조회하는 API를 각각 작성
- 예약 가능한 날짜 목록을 조회
- 날짜 정보를 입력받아 예약가능한 좌석정보를 조회
> 좌석 정보는 1 ~ 50까지의 좌석 번호로 관리

### 예약 가능 날짜 조회 `GET`

#### 요청
Path Parameter:
- concertId (long): 조회할 콘서트의 ID

#### 요청 예시
```text
GET /api/123/available-dates
```

#### 응답 바디
```json
[
  {
    "concertDate": "2024-07-11T12:00:00"
  },
  {
    "concertDate": "2024-07-12T12:00:00"
  }
]
```

### 예약 가능 좌석 조회 `GET`

#### 요청
Path Parameter:
- concertId (long): 조회할 콘서트의 ID

#### 요청 예시
```text
GET /api/123/available-seats
```

#### 응답 바디
```json
[
  {
    "seatId": 1,
    "seatName": "A1",
    "price": 30000,
    "zone": "Standard"
  }
]
```

3. 좌석 예약 요청 API
- 날짜와 좌석 정보를 입력받아 좌석을 예약 처리하는 API를 작성
- 좌석 예약과 동시에 해당 좌석은 그 유저에게 약 (예시 : 5분)간 임시 배정( 시간은 정책에 따라 자율적으로 정의 )
- 만약 배정 시간 내에 결제가 완료되지 않는다면 좌석에 대한 임시 배정은 해제되어야 하며, 만약 임시배정된 상태라면 다른 사용자는 예약할 수 없어야 한다.

### 좌석 예약 요청 `POST`

#### 요청
Path Parameter:
- concertId (long): 조회할 콘서트의 ID

요청 바디
```json
{
  "seatId": 1,
  "userId": 123456
}
```

#### 요청 예시
```text
POST /api/123/reserve-seat
Content-Type: application/json

{
  "seatId": 1,
  "userId": 123456
}
```

#### 응답 바디
```json
{
  "reservationId": 1,
  "seatName": "A1",
  "reservationTime": "2024-07-01T14:30:00",
  "reservationStatus": "RESERVED"
}
```

4. 잔액 충전 / 조회 API
- 결제에 사용될 금액을 API를 통해 충전하는 API를 작성
- 사용자 식별자 및 충전할 금액을 받아 잔액을 충전
- 사용자 식별자를 통해 해당 사용자의 잔액을 조회

### 잔액 충전 `POST`

#### 요청 바디
```json
{
  "userId": 1,
  "amount": 100000
}
```

#### 요청 예시
```text
POST /api/charge
Content-Type: application/json

{
  "userId": 1,
  "amount": 100000
}
```

#### 응답 바디
```json
{
  "userId": 1,
  "currentBalance": 100000
}
```

### 잔액 조회 `GET`

#### 요청
Path Parameter:
- userId (long): 조회할 유저의 ID

#### 요청 예시
```text
GET /api/balance?userId=1
```

#### 응답 바디
```json
{
  "userId": 1,
  "currentBalance": 90000
}
```

5. 결제 API
- 결제 처리하고 결제 내역을 생성하는 API를 작성
- 결제가 완료되면 해당 좌석의 소유권을 유저에게 배정하고 대기열 토큰을 만료

### 결제 `POST`

#### 요청 바디
```json
{
  "reservationId": 456,
  "paymentAmount": 30000
}
```

#### 요청 예시
```text
POST /api/pay
Content-Type: application/json

{
  "reservationId": 456,
  "paymentAmount": 30000
}
```

#### 응답 바디
```json
{
  "paymentId": 123,
  "reservationId": 456,
  "paymentAmount": 30000,
  "paymentTime": "2024-07-01T15:00:00"
}
```

### 심화 과제
6. 대기열 고도화
- 다양한 전략을 통해 합리적으로 대기열을 제공할 방법을 고안
- e.g. 특정 시간 동안 N 명에게만 권한을 부여
- e.g. 한번에 활성화된 최대 유저를 N 으로 유지

### 키포인트
- 유저간 대기열을 요청 순서대로 정확하게 제공할 방법을 고민해 봅니다.
- 동시에 여러 사용자가 예약 요청을 했을 때, 좌석이 중복으로 배정 가능하지 않도록 합니다.

---
ERD
![img_1.png](img_1.png)

---
Sequence Diagram

![img_2.png](img_2.png)

---
Milestone

![img.png](img.png)