package jpabook.jpashop;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter // 이거는 롬복이라는 라이브러리가 필요하다. 롬복은 getter, setter, 생성자, toString 등을 자동으로 만들어준다.
public class Hello {
    private String data;
}
