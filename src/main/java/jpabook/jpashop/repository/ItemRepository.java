package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    // 상품 저장
    public void save(Item item) { // 저장
        if (item.getId() == null) { // 아이템 ID가 없으면 새로 생성
            em.persist(item); // 영속성 컨텍스트에 item을 저장
        } else { // 아이템 ID가 있으면 업데이트
            em.merge(item); // 영속성 컨텍스트에 item을 병합
        }
    }

    // 상품 조회
    public Item findOne(Long id) {
        return em.find(Item.class, id); // 단건 조회
    }

    // 상품 전체 조회
    public List<Item> findAll() { // JPQL을 사용하여 Item Entity를 조회
        return em.createQuery("select i from Item i", Item.class) // 모든 Item을 조회
                .getResultList(); // 결과를 List로 반환
    }
}
