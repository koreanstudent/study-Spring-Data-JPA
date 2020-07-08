# 개발 환경
- 자바 1.8 ↑
- Gradle -빌드 배포 도구
	- Mavn 빌드의 경우 XML로 라이브러리를 추가하고 사용할 수 있으나 Gradle의 경우에는 빌드스크립트를 통하여 사용할 어플리케이션 버전이나 라이브러리 항목등을 설정할 수 있다.
	- 스크립트 언어로 구성되어 있기 때문에 XML과는 달리 변수선언, if, for 등의 로직을 추가하여 라이브러리를 관리할 수 있다.
- JPA (Java Persistence API) 
	- 현재 자바 진영의 ORM 기술 표준으로, 인터페이스의 모음이다
	- 즉, 실제로 동작하는 것이 아니다.
	- JPA 인터페이스를 구현한 대표적인 오픈소스가 Hibernate라고 할 수 있다.
- Hibernate
	- ORM 프레임워크, Open Source SW
- H2 데이터베이스
- 쿼리 파라미터 로그 남기기 
	- implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.7' 추가
# lombok
	- @Setter, @Getter
	- @NoArgsConstructor AccessLevel.PROTECTED: 기본 생성자 막고 싶은데,JPA 스팩상 PROTECTED로 열어두어야한다.
	- @ToString
	
# JpaRepository 공통 기능 인터페이스
	- 공통 CRUD 제공
	- 제네릭은 <엔티티 타입, 식별자 타입> 설정
	- save(S) : 새로운 엔티티는 저장하고 이미 있는 엔티티는 병합한다.
        - delete(T) : 엔티티 하나를 삭제한다. 내부에서 EntityManager.remove() 호출
        - findById(ID) : 엔티티 하나를 조회한다. 내부에서 EntityManager.find() 호출
        - getOne(ID) : 엔티티를 프록시로 조회한다. 내부에서 EntityManager.getReference() 호출
        - findAll(…) : 모든 엔티티를 조회한다. 정렬( Sort )이나 페이징( Pageable ) 조건을 파라미터로 제공할 수 있다.

# 쿼리 메소드 기능 (https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)
	- 메소드 이름으로 쿼리 생성
	- 메소드 이름으로 JPA NamedQuery 호출
	- @Query 어노테이션을 사용하여 리파지토리 인터페이스에 쿼리 직접 정의
	- 스프링 데이터 JPA는 메소드 이름을 분석해서 JPQL을 생성하고 실행
	- 스프링 데이터 JPA가 제공하는 쿼리 메소드 기능
		- 조회: find...By, read...By, query...By, get...By
		- COUNT: count...By 반환타입 long
		- EXISTS: exists...By 반환타입 boolean
		- 삭제: delete...By, remove...By 반환타입 long
		- DISTINCT: findDistinct, findMemberDistinctBy
		- LIMIT: findFirst3, findFirst, findTop, findTop3

# EntityGraph
	- 연관된 엔티티들을 SQL 한번에 조회하는 방법
	- 페치 조인의 간편 버전
	- LEFT OUTER JOIN 사용	
	
# 참고 : 
	 [lombok] https://projectlombok.org/
	 [postman] https://www.postman.com/
	 [thymeleaf] https://www.thymeleaf.org/
	 [spring initializr] https://start.spring.io/
	 [H2 database] https://www.h2database.com/html/main.html
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
