


### software-test

<br>

- java / springboot 테스팅 연습


<br>

### TDD

- 테스트로 개발을 이끌어나감
- 요구에 맞게끔 테스트를 먼저 만들고 통과시켜가며 요구에 맞게 코드를 리팩터링해나간다.
- RED-GREEN-BLUE

**tdd basic**

- 핸드폰 번호 유효 여부를 검증하는 `PhoneValidator` 라는 간단한 동작에 대한 요구를 생각해보고 테스팅을 통해 점진적으로 실제 동작을 완성시킨다.

<br>

**tdd-member api**

- `회원가입`, `회원조회` 라는 단순한 api에 대한 mvc구조를 TDD로 간단하게 구현해본다.

- 편의상 service에서 repository를 mocking을 하지 않았으나 단위 테스트에서의 `단위`를 어느정도 수준으로 판단하냐에 따라 repository 레이어를 mocking 하여 테스트 결합도를 낮출 수 있음

<br>

**tdd-member-integ-test**

- `회원가입`, `회원조회` 라는 api가 요청에서 응답까지 실제로 잘 동작하나 통합테스트 수행

- 실제 환경에 보다 가까운 요청-응답 검증 의도를 위해 `MockMvc` 가 아닌 `TestRestTemplate` 을 사용해 통합 테스트 수행

<br>

<br>

### 아키텍쳐 테스팅

- `archunit` 라이브러리를 활용해 애플리케이션 아키텍쳐 구조를 검증한다.
- 모듈 의존성 검증을 위해 Post 도메인과 기능을 추가하고 필요한 부분만 단위 테스트 (사용자와 게시글 관계)
- 적절한 테스팅 연습을 위해 레이어별 패키징을 도메인별 패키징 방식으로 변경한다

**테스트 사항**

패키지구조

- `member` 패키지에서는 `post` 패키지의 클래스를 참조하면 안되고 `member` 에서만 가능하다.
- `post` 패키지에서는 `member` 패키지의 클래스를 참조할 수 있다.
- `post` 패키지의 클래스들을 참조하는 것은 `post` 패키지 내부의 클래스들 뿐이어야 한다.
- 클래스들간 순환참조가 없어야 한다.

<br>

클래스구조

- `Controller` 는 `Service` 를 참조할 수 있다.
- 'Controller' 는 'Repository' 를 참조할 수 없다.
- 'Service' 는 `Repository` 를 참조하고 있어야 한다.
