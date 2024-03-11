package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 단일 테이블 전략
@DiscriminatorColumn(name = "dtype") // 구분 컬럼
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name; // 이름
    private int price; // 가격
    private int stockQuantity; // 재고수량

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
