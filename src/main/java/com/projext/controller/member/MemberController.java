package com.projext.controller.member;

import com.projext.domain.member.Member;
import com.projext.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberRepository memberRepository;
    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member){
        return "/Member/register";
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute Member member, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/Member/register";
        }
        memberRepository.save(member);
        return "redirect:/";
    }
}
