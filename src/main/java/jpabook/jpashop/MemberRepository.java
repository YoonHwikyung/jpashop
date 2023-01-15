package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository //컴포넌트 스캔 대상이 되어서 자동으로 스프링 빈에 등록된다
public class MemberRepository {
    //Repository : 엔티티를 찾아줌, dao랑 비슷한 개념

    // 1. EntityManager 생성
    //JPA는 entityManager가 필요하다
    // 우리는 스프링부트를 사용하기 때문에 스프링 컨데이터 위에서 동작한다.
    // 스프링부트가 @PersistenceContext 어노테이션이 있으면 EntityManager를 주입해준다.
    // 이전처럼 EntityManager 생성코드를 작성하지 않아도 스프링부트가 다 해준다.
    // (build.gradle에서 의존성 주입했기 때문 : spring-boot-starter-data-jpa
    @PersistenceContext
    private EntityManager em;

    // 2. 저장
    public Long save(Member member){
        em.persist(member);
        return member.getId();
        // member가 아닌 id를 리턴하는 이유 : 커맨드와 쿼리를 분리, 저장하고나면 가급적이면 사이트 이펙트를 일으키는 커맨드 성이기 떄문에 리턴값을 대체로 만들지 않음.
        // id 정도만 리턴해도 나중에 id로 조회할 수 있으니 id만 리턴
    }

    // 3. 조회
    public Member find(Long id){
        return em.find(Member.class, id);
    }

    // test
    // 단축키 : 클래스명에 마우스 위치 후 alt + enter / 클래스 내부에 커서 + ctrl + shift + t

}
