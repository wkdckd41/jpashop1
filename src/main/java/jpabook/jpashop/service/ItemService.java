package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 상품 저장
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 상품 수정
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    } // 변경 감지 기능을 사용하여 데이터를 수정

    // 상품 조회
    public List<Item> findItems() { // 상품 전체 조회
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) { // 단건 조회
        return itemRepository.findOne(itemId);
    }
}
