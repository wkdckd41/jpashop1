package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // Spring이 관리하는 Service로 등록
@Transactional(readOnly = true) // JPA의 모든 데이터 변경은 트랜잭션 안에서 실행되어야 함 // 읽기 전용
@RequiredArgsConstructor // final이 붙은 필드만 가지고 생성자를 만들어줌
public class MemberService {

    private final MemberRepository memberRepository; // 생성자 주입 // final로 선언하여 불변성 유지

    //회원 가입
    @Transactional(readOnly = false) // 읽기 전용이 아님
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member); // 회원 저장
        return member.getId(); // 회원 ID 반환
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName()); // 같은 이름이 있는 중복 회원 검증
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll(); // 회원 전체 조회
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId); // 단건 조회
    }
}
