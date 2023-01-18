package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//@AllArgsConstructor
@RequiredArgsConstructor // final에 있는 필드만 가지고 생성자를 만들어줌
public class MemberService {

    //@Autowired //인젝션 : test시 memberRepository에 접근할 수 없으므로 Setter 인젝션을 사용
    private final MemberRepository memberRepository; // 변경할 일이 없기 때문에 final

    // Setter 인젝션 : 스프링에 바로 주입하는 것이 아닌 setter로 들어와서 인젝션
    // 장점 : 테스트코드 작성시 값을 주입
    // 단점 : 애플리케이션 로딩 시점에 세팅이 다 끝난다. 스프링 세팅 후 동작 중간에 값을 바꿀 일이 없다
    //@Autowired
    //public void setMemberRepository(MemberRepository memberRepository) {
    //    this.memberRepository = memberRepository;
    //}

    // 생성자 인젝션
    //@Autowired // 생성자가 하나만 있는 경우에는 스프링이 생성자에 @Autowired 어노테이션을 붙여준다.
    // @AllArgsConstructor 어노테이션으로 아래 생성자를 만들어 준다.
    //public MemberService(MemberRepository memberRepository) {
    //    this.memberRepository = memberRepository;
    //}

    /**
     * 회원 가입
     * @param member
     * @return
     */
    @Transactional // readOnly = false 가 default
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원 중복 체크
     * @param member
     */
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName()); //실무에서는 멀티쓰레드 상황을 고려해서 DB에서 name을 unique로 잡아주자
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     * @return
     */
    private List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 한 회원 조회
     * @param memberId
     * @return
     */
    private Member findMember(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
