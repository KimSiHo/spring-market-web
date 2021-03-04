package com.jpabook.jpashop.notification.web;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.account.service.CurrentUser;
import com.jpabook.jpashop.notification.domain.Notification;
import com.jpabook.jpashop.notification.domain.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@RequiredArgsConstructor
@SessionAttributes
@Controller
public class NotificationController {

    private final NotificationRepository notificationRepository;

    @GetMapping("/settings/notifications")
    public String notifications(@CurrentUser Account account, @ModelAttribute("notificationsCount") String notificationsCount, Model model) {
        List<Notification> notifications = notificationRepository.findRecvMsgByRecipient(account);
        model.addAttribute("notifications", notifications);
        model.addAttribute("notificationsCount", notificationsCount);

        return "settings/notification";
    }
}
