package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId); // 회원 조회
        Item item = itemRepository.findOne(itemId); // 상품 조회

        // 재고 수량 확인
        if (item.getStockQuantity() < count) {
            throw new IllegalStateException("need more stock");
        }

        // 배송정보 생성
        Delivery delivery = new Delivery(); // 배송정보 생성
        delivery.setAddress(member.getAddress()); // 회원 주소로 배송

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem); // OrderItem...은 가변인자

        // 주문 저장
        orderRepository.save(order); // CascadeType.ALL로 인해 orderItem, delivery도 저장됨

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional // 변경 감지
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    // 검색
//    public List<Order> findOrders(OrderSearch orderSearch) {
}
