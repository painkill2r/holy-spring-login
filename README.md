#### 최초 작성일 : 2021.07.20(화)

# Spring Boot 로그인 처리

Spring Boot를 이용한 로그인 처리 프로젝트

## 학습 환경

1. OS : MacOS
2. JDK : OpenJDK 11.0.5
3. Framework : Spring Boot 2.4.4
    - [Spring Initializer 링크 : https://start.spring.io](https://start.spring.io)
    - 패키징 : jar
    - 의존설정(Dependencies)
        - Spring Web
        - Thymeleaf
        - Lombok
        - Validation
4. Build Tools : Gradle
5. Database : H2

## Package 구조

1. 향후 web을 다른 기술로 바꾸어도 domain은 그대로 유지할 수 있도록 설계
2. web은 domain을 의존하지만, domain은 web을 의존하지 않도록 한다.
    - hello.login
        - domain: 화면, UI, 기술 인프라 등등의 영역을 제외한 시스템이 구현해야 하는 핵심 비즈니스 업무 영역
            - item
            - member
            - login
        - web
            - item
            - member
            - login