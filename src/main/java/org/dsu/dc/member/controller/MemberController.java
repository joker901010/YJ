package org.dsu.dc.member.controller;

import org.dsu.dc.member.domain.Member;
import org.dsu.dc.member.persistence.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log
@Controller
@RequestMapping("/member")
public class MemberController {

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    @Autowired
    public MemberController(PasswordEncoder passwordEncoder, MemberRepository memberRepository) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/register")
    public void register() {
        log.info("register() called ...");
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("member")Member member, RedirectAttributes redirectAttributes) {

        log.info("MEMBER : " + member);
        String encryptPw = passwordEncoder.encode(member.getMemberPw());

        log.info("encoded password : " + encryptPw);
        member.setMemberPw(encryptPw);

        memberRepository.save(member);
        redirectAttributes.addFlashAttribute("msg", "register success");

        return "redirect:/member/login";
    }


    @GetMapping("/login")
    public void login() {
        log.info("login() called ...");
    }
}
