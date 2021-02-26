package com.jpabook.jpashop.noti.web;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.account.service.CurrentUser;
import com.jpabook.jpashop.noti.domain.Noti;
import com.jpabook.jpashop.noti.domain.NotiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class NotiController {

    private final NotiRepository notiRepository;

    @GetMapping("/noti")
    public String notiList(@CurrentUser Account account, Model model) {
        List<Noti> notiList = notiRepository.findRecvMsgByRecipient(account);
        model.addAttribute("notiList", notiList);

        return "noti/index";
    }
}
