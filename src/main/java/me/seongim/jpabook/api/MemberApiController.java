package me.seongim.jpabook.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.seongim.jpabook.domain.MemberJ;
import me.seongim.jpabook.service.MemberService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/vi/members")
    public List<MemberJ> memberV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<MemberJ> findMemberJS = memberService.findMembers();
        List<MemberDto> collect = findMemberJS.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @PostMapping("/api/vi/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid MemberJ memberJ) {
        Long id = memberService.join(memberJ);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        MemberJ memberJ = new MemberJ();
        memberJ.setName(request.getName());
        Long id = memberService.join(memberJ);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        MemberJ findMemberJ = memberService.findOne(id);
        return new UpdateMemberResponse(findMemberJ.getId(), findMemberJ.getName());
    }

    @Data
    static class UpdateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    static class UpdateMemberResponse {
        private Long id;
        private String name;

        public UpdateMemberResponse(Long id, String name) {
            this.id = id;
        }
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
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
