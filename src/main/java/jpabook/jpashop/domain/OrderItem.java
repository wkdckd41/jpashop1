package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) { // 주문 상품 생성 메서드
        OrderItem orderItem = new OrderItem(); // 주문 상품 엔티티 생성
        orderItem.setItem(item); // 주문 상품 설정
        orderItem.setOrderPrice(orderPrice); // 주문 가격 설정
        orderItem.setCount(count); // 주문 수량 설정

        item.removeStock(count); // 주문 수량만큼 재고 감소
        return orderItem;
    }

    //==비스니스 로직==//
    public void cancel() { // 주문 취소
        getItem().addStock(count); // 재고 수량 원복
    }

    //==조회 로직==//
    public int getTotalPrice() { // 주문상품 전체 가격 조회
        return getOrderPrice() * getCount(); // 주문 가격 * 주문 수량
    }
}
