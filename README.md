# 식당 예약 서비스

SpringBoot를 활용해 식당 예약 서비스를 구현

## 개발 환경

- JDK 11
- Spring Boot 2.7.10
  - spring-boot-starter-data-jpa
  - spring-boot-starter-web
  - spring-boot-starter-test
- MySQL 8

## 라이브러리

- lombok
- jjwt 0.11.4

## 구현 기능


## 공통
- [x] 회원가입
- [x] 로그인(토큰 인증 방식)
- [x] 매장 목록 조회
- [x] 매장 상세 조회

## 고객
- [x] 예약 하기
- [x] 예약 취소
- [x] 내 예약 목록 조회
- [x] 리뷰 작성
- [x] 내 예약 상세 조회
- [ ] 내 리뷰 목록 조회
- [ ] 내 리뷰 상세 조회

## 점장
- [x] 파트너 가입
- [x] 매장 등록
- [x] 내 매장 예약 목록 조회
- [x] 예약 승인
- [x] 예약 거절
- [ ] 매장 수정
- [ ] 매장 삭제
- [ ] 내 매장 예약 상세 조회

## 키오스크
- [x] 예약자 방문 확인 

## 상세 요구 사항

- 점장은 매장 등록 시 파트너에 가입되어 있어야 한다(가입 조건 X)
- 고객은 예약을 하기 위해 회원 가입 및 로그인 상태여야 한다.
- 토큰을 통해 로그인 상태 및 사용자 권한을 확인할 수 있다.
- 매장 목록 조회 시 정렬된 상태로 조회할 수 있다.(가나다순, 별점 순)
- 매장 목록 조회 시 특정 조건을 통해 필터링해서 조회할 수 있다(매장 이름)
- 고객 예약 시 다음 조건을 만족해야 한다.
  - 현재시간 보다 30분 더 늦은 시간대부터 예약이 가능하다.
  - 동일한 매장 예약은 하루에 1번만 가능하다.
  - 추후에 추가할 요구사항
    - 예약이 거절 당했을 경우 거절 시간으로부터 1시간 후부터 다시 예약이 가능하다.
    - 예약을 취소했을 경우 예약 취소 시간으로부터 1시간 후부터 다시 예약이 가능하다.


- 고객은 키오스크를 통해 방문 확인을 진행해야 한다.
  - 예약 시 받은 code를 통해 방문 확인을 진행한다.
  - 예약 시간 10분 전에 방문 확인을 진행해야 한다.
    - 10전에 방문확인을 진행하지 않았을 경우 해당 예약은 시간 초과로 예약이 취소된다.
  - 예약이 성공적으로 이루어 졌을 경우에 방문 확인이 가능하다.
  - 나중에 추가할 내용
    - 예약을 취소했을 경우 방문 처리는 되지 않는다.
    - 예약을 거절당했을 경우 방문 처리는 되지 않는다.

