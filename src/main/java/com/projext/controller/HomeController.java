package com.projext.controller;

import com.projext.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.projext.domain.member.Member;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Member loginMember, Model model){

        if(loginMember == null){
            return "redirect:/login";
        }
        model.addAttribute("member", loginMember);
        return "/index";
    }

}
