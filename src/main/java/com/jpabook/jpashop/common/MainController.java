package com.jpabook.jpashop.common;

import com.jpabook.jpashop.account.web.CurrentUser;
import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.notification.domain.NotificationRepository;
import com.jpabook.jpashop.product.domain.Product;
import com.jpabook.jpashop.product.domain.ProductRepository;
import com.jpabook.jpashop.product.domain.ProductStatus;
import com.jpabook.jpashop.product.web.ProductKind;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@SessionAttributes("notificationsCount")
@RequiredArgsConstructor
@Controller
public class MainController {

    private final ProductRepository productRepository;
    private final NotificationRepository notificationRepository;

    @GetMapping("/")
    public String home(@CurrentUser Account account, Model model, HttpSession session) {


        //메인 페이지에 보여줄 8개의 상품들 조회
        List<Product> computers = productRepository.findTop8ByProductStatusAndProductKind(ProductStatus.onSale, ProductKind.computer);
        List<Product> electronics = productRepository.findTop8ByProductStatusAndProductKind(ProductStatus.onSale, ProductKind.electronic);
        List<Product> cloths = productRepository.findTop8ByProductStatusAndProductKind(ProductStatus.onSale, ProductKind.cloth);
        List<Product> shoes = productRepository.findTop8ByProductStatusAndProductKind(ProductStatus.onSale, ProductKind.shoes);

        if (account != null) {
            model.addAttribute(account);

            Long notifationsCount = notificationRepository.countRecvMsgByRecipient(account);
            model.addAttribute("notificationsCount", notifationsCount);
            session.setAttribute("notificationsCount", notifationsCount);
        }

        model.addAttribute("computers", computers);
        model.addAttribute("electronics", electronics);
        model.addAttribute("cloths", cloths);
        model.addAttribute("shoes", shoes);

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
