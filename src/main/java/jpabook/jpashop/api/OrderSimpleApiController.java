package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * XToOne(ManyToOne, OneToOne) 관계 최적화
 * Order
 * Order -> Member
 * Order -> Delivery
 */

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); // Lazy 강제 초기화
            order.getDelivery().getAddress(); // Lazy 강제 초기화
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        // ORDER 2개
        // N + 1 -> 1 + 회원 N + 배송 N
        List<Order> orders = orderRepository.findAllByString(new OrderSearch()); // 쿼리 N번 -> N + 1 문제 발생

        List<SimpleOrderDto> result = orders.stream() // 컬렉션을 스트림으로 변환
                .map(o -> new SimpleOrderDto(o)) // Order를 SimpleOrderDto로 변환
                .collect(toList()); // 스트림을 다시 컬렉션(List)으로 변환

        return result;
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private String orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order o) {
            orderId = o.getId();
            name = o.getMember().getName(); // Lazy 초기화
            orderDate = o.getOrderDate().toString();
            orderStatus = o.getStatus();
            address = o.getDelivery().getAddress(); // Lazy 초기화
        }
    }
}
