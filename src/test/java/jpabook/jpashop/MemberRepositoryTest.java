package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // junit에 스프링과 관련된 테스트 한다고 알려줘야함
@SpringBootTest
public class MemberRepositoryTest {

    // MemberRepository 테스트

    // @Autowired로 MemberRepository 인젝션 받음
    @Autowired MemberRepository memberRepository;

    // 테스트 생성 ( tdd + tab )
    @Test
    @Transactional
    @Rollback(false) //롤백하지않고 커밋
    public void testMember() throws Exception {
        //given : member를 가지고
        // 멤버가 잘 저장되어있는지 확인
        Member member = new Member();
        member.setUsername("memberA");

        //when : save를 하면
        Long savedId = memberRepository.save(member); // extract에서 변수 뽑아오기 : 메소드 커서 & ctrl + alt + v
        // 왜 savedId ? => memberRepository에서 id만 리턴했기때문에 변수명 savedId로 지정
        Member findMember = memberRepository.find(savedId);

        //then : 검증(내가 save한 값이 잘 저장되었는지 확인)
        // assertj라는 라이브러리를 스프링테스트가 가지고있음(library dependency)
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        // 조회해온 findMember 와 member가 같은지 (==비교)
        Assertions.assertThat(findMember).isEqualTo(member);
        System.out.println("findMember == member: " + (findMember == member)); // => true
        // true가 나오는 이유 : 같은 트랜잭션 안에서 저장/조회를 하면 같은 영속성 컨텍스트 안에서는 id값(식별자)이 같으면 같은 Entity로 인식한다.
        // 1차 캐시에서 영속성 컨텍스트에서 똑같이 관리되고 있는 엔티티가 있기 때문에 기존에 관리하던 엔티티가 나온 것
        // 쿼리를 확인해봬면, insert문만 있고 select 쿼리는 나가지도 않았다
        // 어? 영속성 컨텍스트에 있네? 하고 1차 캐시에서 그냥 꺼내온 것(id값이 같이 때문에)




        // 트랜잭션 없는 경우 에러
//        org.springframework.dao.InvalidDataAccessApiUsageException: No EntityManager with actual transaction available for current thread - cannot reliably process 'persist' call; nested exception is javax.persistence.TransactionRequiredException: No EntityManager with actual transaction available for current thread - cannot reliably process 'persist' call

        // EntityManager를 통한 모든 데이터 변경은 항상 트랜잭션 안에서 이루어져야 한다
        // => MemberRepository에 트랜잭션 추가하거나 Test 케이스에 트랜잭션 추가
        // * @Transactional 은 스프링부트에서 제공하는 어노테이션으로 import(쓸 수 있는 옵션값이 더 다양)
    }

}