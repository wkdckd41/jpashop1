package jpabook.jpashop.api;


import jakarta.validation.Valid;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    } // 엔티티를 직접 노출한다. 엔티티가 변하면 API 스펙이 변한다.

    @GetMapping("/api/v2/members")
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect);
    } // 엔티티 대신에 DTO를 반환한다. API 스펙을 유지할 수 있다.

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    } // API 요청 값으로 Member 엔티티를 직접 받는다. 엔티티를 RequestBody에 직접 매핑하는 것은 좋지 않다.

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    } // API 요청 값으로 Member 엔티티 대신에 별도의 DTO를 받는다. DTO를 엔티티로 변환하거나 엔티티를 DTO로 변환하는 로직이 추가되었다.

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    } // 엔티티를 직접 사용하지 않고 DTO를 사용한다. 엔티티를 직접 사용하면 엔티티의 모든 값이 노출된다. DTO를 사용하면 API 스펙에 맞추어 응답값을 제어할 수 있다.

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
