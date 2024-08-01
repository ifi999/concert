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
<details>
    <summary>1. 유저 대기열 토큰 기능</summary>

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

</details>

<details>
    <summary>2. 예약 가능 날짜 / 좌석 API</summary>

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

</details>

<details>
    <summary>3. 좌석 예약 요청 API</summary>

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

</details>

<details>
    <summary>4. 잔액 충전 / 조회 API</summary>

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

</details>

<details>
    <summary>5. 결제 API</summary>

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

</details>

<details>
    <summary>심화 과제 및 키포인트</summary>

   ### 심화 과제
   6. 대기열 고도화

   - 다양한 전략을 통해 합리적으로 대기열을 제공할 방법을 고안

   - e.g. 특정 시간 동안 N 명에게만 권한을 부여

   - e.g. 한번에 활성화된 최대 유저를 N 으로 유지

   ### 키포인트

   - 유저간 대기열을 요청 순서대로 정확하게 제공할 방법을 고민해 봅니다.
   - 동시에 여러 사용자가 예약 요청을 했을 때, 좌석이 중복으로 배정 가능하지 않도록 합니다.

</details>


---
## ERD

<details>
    <summary>이미지</summary>

   ![img_1.png](img_1.png)

</details>


---
## Sequence Diagram

<details>
    <summary>이미지</summary>

   ![img_2.png](img_2.png)

</details>

---
## Milestone

<details>
    <summary>이미지</summary>

   ![img.png](img.png)

</details>

---
## Swagger

<details>
    <summary>이미지</summary>

   ![image](https://github.com/user-attachments/assets/7412b2a4-2183-4152-b759-83c9820c75e1)

</details>

---

## 동시성 문제 발생 분석 자료

- 동시성 문제가 발생할 만한 로직
  - 포인트 충전

  - 좌석 예약

  - 결제(포인트 사용)

<details>
   <summary>포인트 충전</summary>

   - 적용한 락
     - 비관적 락
   - 이유
     - 낙관적 락을 사용해서 롤백이 일어나는 것보다는 시간이 조금 더 소요되더라도 안정적으로 포인트 충전을 성공하는 것이 중요하다고 생각했기 때문
     - 낙관적 락으로 테스트 실행한 경우 데드락 발생
       - 낙관적 락은 DB 락이 아님에도 데드락이 발생
       - [MySQL 문서](https://dev.mysql.com/doc/refman/8.4/en/innodb-locks-set.html)를 확인 한 결과 레코드를 수정하는 경우에 x-lock이 걸린다고 함
         - 낙관적 락에서 사용되는 `Update ... SET id = ? AND version =?` 부분이 의도치 않게 데드락을 발생시킨 것
     - 좌석 예약은 낙관적 락을 사용하는데 왜 데드락이 발생하지 않았을까?
       - 좌석 예약은 1명을 제외한 나머지는 처리가 필요 없어서 재시도 로직이 없음
       - 포인트 충전은 매출과 관련이 있어서 로직을 추가하였는데, 지연 시간을 넣었음에도 데드락이 발생
   
</details>

<details>
   <summary>좌석 예약</summary>

   - 적용한 락
     - 낙관적 락
   - 이유
     - 가장 먼저 락에 접근한 1명을 제외하고는 모두 실패해도 된다고 생각
     - 또한 비관적 락을 적용할 경우 레코드 락이 걸리는데, 레코드 락이 걸린 좌석이 포함된 좌석 목록을 부르는데 영향을 미치기 때문
   - 비교
     - 낙관적 락
       - 쓰레드풀 개수 10 / 동일한 좌석 예약 1,000회
         - 테스트 평균 소요 시간 : 1,495ms
         - 테스트 중 평균 좌석 목록 조회 시간 : 6.8ms
       - 쓰레드풀 개수 100 / 동일한 좌석 예약 10,000회
         - 테스트 평균 소요 시간 : 3,839ms
         - 테스트 중 평균 좌석 목록 조회 시간 : 
           - 113.8ms (테스트 초반 조회) 
           - 61ms (테스트 중반 조회) 
           - 23.4ms (테스트 후반 조회)
     - 비관적 락
       - 쓰레드풀 개수 10 / 동일한 좌석 예약 1,000회
           - 테스트 평균 소요 시간 : 1,442ms
           - 테스트 중 평균 좌석 목록 조회 시간 : 28.8ms
       - 쓰레드풀 개수 100 / 동일한 좌석 예약 10,000회
           - 테스트 평균 소요 시간 : 2,666ms
           - 테스트 중 평균 좌석 목록 조회 시간 : 
             - 236.6ms (테스트 초반 조회) 
             - 298ms (테스트 중반 조회) 
             - 227.2ms (테스트 후반 조회)
     - 분석
       - 동일한 좌석 예약의 시도 횟수가 늘어날 수록 낙관적 락의 소요 시간 증가
         - 트랜잭션 충돌 횟수가 늘어남에 따라 전체 소요 시간은 비관적 락이 적을 수도 있음
       - 좌석 예약 시도 중 좌석 목록 조회 시간은 낙관적 락이 시도 횟수가 늘어날수록 짧음
         - 좌석 예약과 좌석 목록 조회는 별개의 기능이기에 예약이 목록 조회에 영향을 덜 끼치는 낙관적 락을 선택

</details>

<details>
   <summary>결제(포인트 사용)</summary>

   - 적용한 락
     - 비관적 락
   - 이유
     - 재화의 소모와 관련된 것은 한 번에 하나의 트랜잭션만 접근을 하는 것이 안전할 것이라고 생각했기 때문
   - 고민
     - 관리자 웹 혹은 통계와 같은 것이 있을 때, 레코드 락이 걸릴 것인데 이러한 경우를 모두 고려하면 비관적 락을 사용 못하는게 아닌지

</details>

## 캐싱 전략 설계

   - 캐싱 적용 기준
     - 자주 조회되는 데이터
       - 사용자가 자주 요청하는 데이터를 캐시에 저장하여 빠르게 제공
     - 자주 변경되지 않는 데이터
       - 변경이 거의 없거나 주기적으로 업데이트되는 데이터를 캐싱하여 데이터베이스 부하 감소
     - 계산이 복잡한 로직이 적용되는 데이터
       - 계산이 복잡한 로직을 캐시에 저장하여 재사용함으로써 성능 향상
       - 예를 들어, 통계 계산 등 I/O가 많이 소모되는 작업은 캐싱을 통해 효율적으로 처리

   - 캐싱 적용 기능
     - 콘서트 목록 조회
     - 콘서트 스케쥴 조회
   - 적용 고민했던 기능
     - 예약 가능 좌석 목록 조회
       - 적용하지 않은 이유
         - 해당 기능은 자주 조회되지만, 변경 또한 자주 발생할 것으로 예상
         - 조회 결과를 캐싱하는 것보다는 실시간성을 보장하는 것이 사용자 경험에 좋을 것으로 생각
       - 캐싱을 적용한다면
         - 누군가가 예약하거나, 결제 취소하거나, 예약 만료 등의 이벤트 발생 시 캐시를 무효화하는 로직이 필요
         - 이러한 로직은 유지보수 측면에서 불리할 수 있음

<details>
   <summary>성능 비교</summary>
</details>
