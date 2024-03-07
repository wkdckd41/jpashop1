package jpabook.jpashop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)  // JUnit에 내장된 러너가 아닌, 스프링과 함께 실행하는 러너를 실행하겠다는 뜻
@SpringBootTest // 스프링 부트를 띄운 상태로 테스트를 진행하겠다는 뜻
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository; // 테스트를 진행할 때 스프링이 관리하는 빈을 주입받는다.

    @Test
    @Transactional // 테스트를 실행할 때 트랜잭션을 시작하고, 테스트가 끝나면 롤백을 한다.
    @Rollback(value = false) // 테스트가 끝나고 롤백을 하지 않고, 커밋을 하겠다는 뜻
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then
        assertEquals(findMember.getId(), member.getId());
        assertEquals(findMember.getUsername(), member.getUsername());
        assertEquals(findMember, member);
        System.out.println("findMember == member: " + (findMember == member)); // 같은 영속성 컨텍스트에서 가져온 엔티티는 같다.

    }

}