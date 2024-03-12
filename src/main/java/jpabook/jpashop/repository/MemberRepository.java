package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Spring이 관리하는 Repository로 등록
@RequiredArgsConstructor // final이 붙은 필드만 가지고 생성자를 만들어줌
public class MemberRepository {

    private final EntityManager em; // @PersistenceContext와 동일한 역할을 함

    public void save(Member member) {
        em.persist(member); // 영속성 컨텍스트에 member를 저장
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); // 단건 조회
    }

    // JPQL을 사용하여 Member Entity를 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList(); // 결과를 List로 반환
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class) // 이름으로 조회
                .setParameter("name", name)
                .getResultList();
    }
}
