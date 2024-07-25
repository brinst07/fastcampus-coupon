# String

String 타입의 데이터 구조. 텍스트, 직렬화된 객체 등을 저장하는 용도로 사용

## SET

- 시간복잡도 : O(1)
- 지정된 key의 저장된 문자열을 저장

```
SET key value
```

## GET

- 시간복잡도 : O(1)
- 지정된 key의 저장된 문자열을 조회

```
GET key
```

# List

데이터 삽입 순서에 따라 정렬된 문자열 컬렉션의 형태

## LRANGE

- 시간복잡도 : O(S+N) - S
    - S : (HEAD or TAIL)에서의 distance
    - N은 지정된 요소 수
- List에서 지정된 요소를 반환
- 주의해서 사용해야 함
- 싱글스레드를 사용하는 REDIS에서 리스트가 매우 길 경우, 다른 작업을 할 수 없음

```
LRANGE key strat stop
LRANGE key 0 -1 -> 처음부터 끝까지
```

## LPUSH

- 시간복잡도 : O(1)
- List의 HEAD에 지정된 요소를 추가

```
LPUSH key element
```

## RPUSH

- 시간복잡도 : O(1)
- List의 TAIL에 지정된 요소를 추가

```
RPUSH key element
```

## LPOP

- 시간복잡도 : O(N)
    - N은 반환된 요소의 수
- List의 첫 번째 요소를 제거하고 반환

```
LPOP key [count]
```

## RPOP

- 시간복잡도 : O(N)
    - N은 반환된 요소의 수
- List의 마지막 요소를 제거하고 반환

```
RPOP key [count]
```

## LLEN

- 시간복잡도 : O(1)
- List에 저장된 요소 수를 반환

```
LLEN key
```

## LPOS

- 시간복잡도 : O(N)
    - N은 목록의 요소 수
- List에서 일치하는 요소를 찾고 인덱스를 반환
- 전체 리스트를 탐색해야하기에 사용에 주의해야함

```
LPOS key element
```

# SET

순서가 지정되지 않은 문자열 컬렉션의 형태. 중복 요소가 허용되지 않음

## SMEMBERS

- 시간복잡도 : O(N)
    - N은 저장된 요소의 수
- Set에 저장된 모든 요소를 반환
- SET의 크기가 커질 경우, 성능이 떨어질 수 있어, 사용에 주의해야함

```
SMEMBERS key
```

## SADD

- 시간복잡도 : O(1)
- SET에 멤버를 추가

```
SAD key member
```

## SISMEMBER

- 시간복잡도 : O(1)
- Set에 멤버가 포함되어 있는지 확인

```
SISMEMBER key member
```

## SCARD

- 시간복잡도 : O(1)
- Set에 저장된 요소 수를 반환

```
SCARD key
```

## SREM

- 시간복잡도 : O(N)
- Set에 저장된 요소를 제거
```
SREM key member
```

# Sorted Set
지정된 스코에 따라서 순서가 지정되는 문자열 컬렉션의 형태. 중복 요소가 허용되지 않음.

## ZRANGE
- 시간복잡도 : O(log(N) + M)
  - N은 정렬된 집합의 요소 수
  - M은 반환된 요소 수
- Sorted Set에 저장된 범위 내(순위, 스코어) 요소를 반환
```
ZRANGE key start stop
```

## ZADD
- 시간복잡도 : O(log(N))
  - N은 정렬된 집합의 요소 수
- member가 score에 의해 정렬 및 저장
- 동일한 score인 겨우 사전 순 정렬
```
ZADD key score member
```

## ZCARD
- 시간복잡도 : O(1)
- 해당하는 key의 요소 수를 반환
```
ZCARD key
```

## ZPOPMIN
- 시간복잡도 : O(log(N) * M)
  - N은 정렬된 집합의 요소 수
  - M은 POP된 요소 수
- 점수가 낮은 순으로 멤버를 제거하고 반환
```
ZPOPMIN key [count]
```

## ZPOPMAX
- 시간복잡도 : O(log(N) * M)
  - N은 정렬된 집합의 요소 수
  - M은 POP된 요소 수
- 점수가 높은 순으로 멤버를 제거하고 반환

```
ZPOPMIN key [count]
```
