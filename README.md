# 1차 성능 개선
- Coupon 발급 API에 locust로 request 발생
- mysql container에 cpu 로드율이 80%를 넘음 -> 좋지 않은 시그널

## 문제점
- RPS가 1000단위로 성능이 좋게 나오지 않음.
- mysql에 많은 부하가 걸림 -> **DB에 병목현상이 생김**
- **500개만 발급되어야 할 쿠폰이 4000개가 넘게 발급됨** -> 동시성 문제 발생

## 성능 개선 방안
- Application Server에 병목이 생기는 경우
  - API 서버 확장으로 부하를 해소(scale out)
- Database Server 병목
  - API 서버 확장으로 부하를 해소할 수 없음
  - 캐시, 데이터베이스 서버 확장(master, slave), 샤딩 등으로 해결이 가능
> 하지만 Database를 확장하는 것보다 Application Server를 확장하는 것이 관리하기도 편하고, 유지보수하기도 편함

## 동시성 이슈 해결
- Lock
  - Lock은 Transaction안에 걸면 안됨
    ```
      Transaction 시작
      lock 획득
      biz 로직 처리
      lock 반납
      Trainsaction 커밋
    ```
    다음과 같은 로직으로 처리되기에 lock을 반납하게 되면 다른 스레드가 접근하게 되고, 동시성 이슈를 해결 할 수 없다.
    따라서 Transaction안에 lock을 거는 행위를 지양해야한다.
