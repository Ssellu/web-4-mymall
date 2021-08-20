package com.megait.mymall.controller;

import com.megait.mymall.domain.Member;
import com.megait.mymall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {

    @RequestMapping("/")
    public String index(Principal principal, Model model){
        String message = "안녕하세요, 손님!";
        if(principal != null){
            message = "안녕하세요, " + principal.getName() + "님!";
        }
        model.addAttribute("msg", message);
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }
    private final MemberRepository memberRepository;

    /*@GetMapping("/mypage")
    public String mypage(Model model, Principal principal) {
        Member member = memberRepository.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("member", member);
        return "member/mypage";
    }*/


    @GetMapping("/mypage2")
    public String mypage(Model model, @AuthenticationPrincipal User user){
        if(user != null) {
            Member member = memberRepository.findByEmail(user.getUsername()).orElseThrow();
            model.addAttribute("member", member);
        }
        return "member/mypage";
    }

    /*
    @RequestMapping("/mypage2")
    public String mypage(Model model,
                         @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member){
        // TODO 여기서부터..
    }*/
}
