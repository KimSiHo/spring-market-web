package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/sign-up")
    public String signUp(){
        return "account/sign-up";
    }

    @GetMapping("/account/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new AccountForm());
        return "account/createAccountForm";
    }

    @PostMapping("/account/new")
    public String create(@Valid AccountForm memberForm, BindingResult result) {

        if(result.hasErrors()) {
            return "account/createAccountForm";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        accountService.join(member);
        return "redirect:/";
    }

    @GetMapping("/account")
    public String list(Model model) {
        List<Member> accounts = accountService.findMembers();
        model.addAttribute("accounts", accounts);
        return "account/accountList";
    }

}
