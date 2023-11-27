package me.seongim.jpabook.controller;

import lombok.RequiredArgsConstructor;
import me.seongim.jpabook.domain.Address;
import me.seongim.jpabook.domain.MemberJ;
import me.seongim.jpabook.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberJController {
    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(),
                form.getZipcode());
        MemberJ memberJ = new MemberJ();
        memberJ.setName(form.getName());
        memberJ.setAddress(address);
        memberService.join(memberJ);
        return "redirect:/";
    }

    //추가
    @GetMapping(value = "/members") public String list(Model model) {
        List<MemberJ> memberJS = memberService.findMembers();
        model.addAttribute("members", memberJS);
        return "members/memberList";
    }
}