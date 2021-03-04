package com.jpabook.jpashop.common;

import com.jpabook.jpashop.account.service.CurrentUser;
import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.notification.domain.NotificationRepository;
import com.jpabook.jpashop.product.domain.Product;
import com.jpabook.jpashop.product.domain.ProductRepository;
import com.jpabook.jpashop.product.domain.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@SessionAttributes("notificationsCount")
@RequiredArgsConstructor
@Controller
public class MainController {

    private final ProductRepository productRepository;
    private final NotificationRepository notificationRepository;

    @GetMapping("/")
    public String home(@CurrentUser Account account, Model model) {
        List<Product> products = productRepository.findProductByProductStatus(ProductStatus.onSale);
        if (account != null) {
            model.addAttribute(account);

            Long notifationsCount = notificationRepository.countRecvMsgByRecipient(account);
            model.addAttribute("notificationsCount", notifationsCount);
        }

        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
