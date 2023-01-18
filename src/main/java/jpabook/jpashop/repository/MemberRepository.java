package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext
    //@Autowired // => SpringBoot.SpringDataJPA를 쓰면 @PersistenceContext를 @Autowired로 쓸 수 있다 => @RequiredArgsConstructor
    private final EntityManager em;

    //@PersistenceUnit
    //private EntityManagerFactory emf; => EntityManagerFactory 직접 주입

    // 생성자 인젝션 생성 가능
    //public MemberRepository(EntityManager em) {
    //    this.em = em;
    //}

    public void save(Member member) {
        em.persist(member);
    }

    // 한 멤버 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id); // 단건조회 : (TYPE, PK)
    }

    // 전체 멤버 조회
    public List<Member> findAll() {
        //List<Member> result = em.createQuery("select m from Member m", Member.class) // (JPQL, 반환타입)
        //        .getResultList();

        //return result;

        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 회원명으로 멤버 조회(파라미터 바인딩)
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }




}
