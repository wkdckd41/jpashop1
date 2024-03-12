package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    // 주문 저장
    public void save(Order order) {
        em.persist(order);
    }

    // 주문 한 건 조회
    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    // 주문 목록 조회
    public List<Order> findAll(OrderSearch orderSearch) {
        return em.createQuery("select o from Order o join o.member m" + // 주문과 회원 조인
                " where o.status = :status" + // 주문 상태 검색
                " and m.name like :name", Order.class) // and m.name like :name: 회원 이름 검색
                .setParameter("status", orderSearch.getOrderStatus()) // 파라미터 바인딩
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000) // 최대 1000건
                .getResultList();
    }

//    /**
//     * JPA Criteria
//     */
//    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
//        Root<Order> o = cq.from(Order.class);
//        Join<Order, Member> m = o.join("member", JoinType.INNER); // 회원과 조인
//
//        List<Predicate> criteria = new ArrayList<>();
//
//        // 주문 상태 검색
//        if (orderSearch.getOrderStatus() != null) {
//            Predicate status = cb.equal(o.get("status"),
//                    orderSearch.getOrderStatus());
//            criteria.add(status);
//        }
//        // 회원 이름 검색
//        if (StringUtils.hasText(orderSearch.getMemberName())) {
//            Predicate name =
//                    cb.like(m.<String>get("name"), "%" +
//                            orderSearch.getMemberName() + "%");
//            criteria.add(name);
//        }
//        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
//        TypedQuery<Order> query =
//                em.createQuery(cq).setMaxResults(1000); //최대 1000건
//        return query.getResultList();
//    }
}
