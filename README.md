# 🏕️ 캠핑온탑 campingOnTop

![캠핑온탑 로고](https://github.com/Tesssssssssy/HanwhaBC-be02-campingOnTop-config/assets/105422037/177289fc-5e89-4907-aea1-c5eb453c4059)
![캠핑온탑-001](https://github.com/beyond-sw-camp/be02-2nd-MTM-cityCamp/assets/105422037/90d4ac16-a769-498b-912a-7ca4265230ad)

<br>
<br>

# 💪 팀원 구성

<div align="left">

|                    **임태우**                    |                 **길민석**                 | 
| :----------------------------------------------: | :----------------------------------------: | 
| [@Tesssssssssy](https://github.com/Tesssssssssy) | [@gilms0730](https://github.com/gilms0730) | 

</div>

<br>
<br>

# 📰 프로젝트 소개
‘**캠핑온탑**’은 바쁜 일상 속에서 캠핑을 하고 싶지만 멀리 가기 힘든 사람들이 <br>
**도심 속에서 캠핑**을 즐길 수 있도록 여러 옥탑방, 루프탑 등의 장소들을 이용할 수 있도록 중개하는 서비스이다.

멀지 않은 **private**한 옥탑방, 루프탑에서 밤에 야경을 보며 힐링할 수 있는 기회를 제공한다. 

<br>

### ***“사전 준비가 필요 없는 도심 캠핑”***

공실인 옥탑방, 루프탑, 상가 등을 가지고 있는 사람들은 숙박업을 통해 수익을 창출할 수 있고 <br>
모텔, 파티룸 등의 장소에는 가기 부담스럽고 한적한 곳으로 멀리 가기 힘든 사람들을 위한 중개 플랫폼이다.

<br>
<br>

---

# 🧐 리팩토링 배경
부트캠프 수강 당시, 3번의 3-4일의 단기 스프린트로 프로젝트를 진행했다.

3번의 프로젝트는 아래의 3단계로 진행되었다. 
> - 프로젝트 주제 선정 및 DB 설계
> - 백엔드 
> - 프론트엔드

<br>

이 기간 동안 설계한 계획에 맞게 구현했어야 하지만, 현실적으로 시간이 턱없이 부족했다. <br>
따라서 서비스의 유저 시나리오에 따라 기본 기능을 구현하는데 최대한 집중했다. <br>

그래서 우리는 아래 기능들을 완성했다. 
> - 회원 (가입, 로그인)
> - 숙소 (등록, 조회)
> - 좋아요 (등록, 삭제)
> - 장바구니 (등록, 삭제)

<br>

기본적인 기능은 완성했지만, **캠핑온탑**은 숙소 예약 서비스이기 때문에 아래 기능들이 추가로 필요했다. 
> - 결제 (장바구니 결제, 조회)
> - 리뷰 (등록, 수정, 삭제)
> - 쿠폰 (이벤트성 할인 쿠폰 발급)
> - 로그인 강화 (리프레시 토큰)
> - 지도 (카카오맵)
> - 채팅 (구매자-판매자 실시간 일대일 채팅)
> - HTTPS 도입 (HTTP -> HTTPS)

숙소 예약을 하려면 당연히 내가 원하는 날짜에 숙박 예약을 해야한다. <br>
그리고 보유한 숙소를 등록한 판매자도 수익을 창출하려면 결제 기능은 필수적이었다. <br> <br>

그리고 결제한 사람만 작성할 수 있는 리뷰 기능을 도입하여 <br>
사용자들이 신뢰할 수 있는 숙소에 대한 정보를 볼 수 있도록 해야 했다. <br> <br>

또한 당시 상황에서는 access token만 발급되는 방식이었기에 1시간이 지나면 다시 로그인을 해야 했다. <br> <br>

그리고 구매자가 판매자 간의 연락할 방법이 없었기 때문에 채팅 기능도 필수적이었다. 

마지막으로 현재 내 위치를 기반으로 주변 숙소를 조회할 수 있어야 했다. 

위와 같은 여러 이유들은 모두 선택이 아닌 필수였고 <br>
성장한 실력으로 영혼을 담은 프로젝트의 완성도를 높이고 싶었다. 

<br>

게다가, 위 프로젝트 기간 이후 최종 프로젝트를 진행했고 <br>
수업을 더 들으며 지식과 실습 경험을 쌓았기 때문에 위 기능들을 구현할 수 있다는 자신감이 생겼다. <br>

그래서 함께 **캠핑온탑** 프로젝트를 진행했던 팀원과 함께 <br>
부트캠프 이후 **캠핑온탑 리팩토링 프로젝트**를 진행하기로 했다.

<br>
<br>

# 🏹 리팩토링 목표
> - **결제**
>   - 장바구니 추가 후 장바구니 내역 결제
>   - 결제 내역 조회
>   - 쿠폰 사용 결제 <br> <br>
> - **리뷰**
>   - 리뷰 등록
>   - 리뷰 수정
>   - 리뷰 삭제 <br> <br>
> - **쿠폰** (이벤트성 **할인 쿠폰**)
>   - Redis 대기열을 사용한 실시간 쿠폰 발급
>   - 쿠폰 사용 처리
>   - 쿠폰 만료 처리 (스케줄러 이용) <br> <br>
> - **로그인 강화**
>   - Redis를 사용해 리프레시 토큰 발급 (-> 로그인 자동 연장) <br> <br>
> - **지도**
>   - 카카오맵 도입
>   - 현재 위치 기반으로 주변 숙소 조회
>   - 다음 우편번호를 이용해 숙소 등록 시 위도, 경도 자동 입력 <br> <br>
> - **채팅** (**실시간 일대일 채팅**)
>   - NOSQL 데이터베이스 추가 (Atlas MongoDB)
>   - 채팅방 생성
>   - 채팅 전송
>   - 채팅 조회 <br> <br>
> - **HTTPS 도입**
>   - AWS 인증서, 로드밸런서, ACM 사용해 자동 리다이렉팅 (HTTP -> HTTP)

<br>
<br>

# 🏆 리팩토링 성과
[1. 카카오맵 + 다음 우편번호를 이요한 도로명 주소로 위도, 경도 데이터 받기](https://velog.io/@ewoo97/Vue3-%EC%B9%B4%EC%B9%B4%EC%98%A4%EB%A7%B5-%EB%8B%A4%EC%9D%8C-%EC%9A%B0%ED%8E%B8%EB%B2%88%ED%98%B8%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%B4-%EB%8F%84%EB%A1%9C%EB%AA%85-%EC%A3%BC%EC%86%8C%EB%A1%9C-%EC%9C%84%EB%8F%84-%EA%B2%BD%EB%8F%84-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EB%B0%9B%EA%B8%B0) <br>
[2. Redis 대기열을 이용한 선착순 쿠폰 발급 시스템](https://velog.io/@ewoo97/Springboot-%EB%A0%88%EB%94%94%EC%8A%A4-%EB%8C%80%EA%B8%B0%EC%97%B4%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%84%A0%EC%B0%A9%EC%88%9C-%EC%BF%A0%ED%8F%B0-%EB%B0%9C%EA%B8%89-%EC%8B%9C%EC%8A%A4%ED%85%9C) <br>
[3. Redis를 이용한 리프레시 토큰 발급](https://velog.io/@ewoo97/Springboot-Vue3-Redis%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EB%A6%AC%ED%94%84%EB%A0%88%EC%8B%9C-%ED%86%A0%ED%81%B0-%EB%B0%9C%EA%B8%89) <br>
[4. 웹소켓, STOMP를 이용한 실시간 채팅 기능 구현](https://velog.io/@ewoo97/SpringBootVueMongoDB-%EC%9B%B9%EC%86%8C%EC%BC%93-STOMP%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%8B%A4%EC%8B%9C%EA%B0%84-%EC%B1%84%ED%8C%85-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84) <br>
[5. AWS를 이용한 HTTPS 적용 총정리](https://velog.io/@ewoo97/AWS%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-HTTPS-%EC%A0%81%EC%9A%A9-%EC%B4%9D%EC%A0%95%EB%A6%AC)


<br>
<br>

# 리팩토링 시연 영상
### 실시간 일대일 채팅

[![Video Label](http://img.youtube.com/vi/fRMz5iu0fLc/0.jpg)](https://www.youtube.com/watch?v=fRMz5iu0fLc)

<br>
<br>

# 시스템 아키텍처
![캠핑온탑 시스템 아키텍처](https://github.com/user-attachments/assets/0f516b07-b311-4235-adf6-af00f39fe3fa)

<br>
<br>

---

# 🔍 프로젝트 시나리오 (요구사항 분석)
[리팩토링 전 시나리오 REPORT](https://tessssssssy.notion.site/campingOnTop-DB-81f90c97cc1647a5b654c0903f8cb3c6)

<br>
<br>

# 🔖 프론트엔드 요구사항 명세서
[리팩토링 전 프론트엔드 요구사항 명세서](https://docs.google.com/spreadsheets/d/1M9KUdkTvYwlbRystq-_26WpynnnbtVQdG6v5hFsb93Y/edit?gid=1284096346#gid=1284096346)

<br>
<br>

# 📺 Figma
[리팩토링 전 화면기능 설계서](https://www.figma.com/design/JeGMJmeA0suRljrCCnQ7Ny/campingOnTop-Figma?node-id=0-1&t=0PSuwbG6zqGDQyLk-0)

<br>
<br>

