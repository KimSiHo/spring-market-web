package com.jpabook.jpashop.common.controller;

import com.jpabook.jpashop.account.service.CurrentUser;
import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.noti.domain.NotiRepository;
import com.jpabook.jpashop.product.domain.Product;
import com.jpabook.jpashop.product.domain.ProductRepository;
import com.jpabook.jpashop.product.domain.ProductStatus;
import com.jpabook.jpashop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final ProductRepository productRepository;
    private final NotiRepository notiRepository;

    @Value("${upload.path}")
    String uploadPath;

    @GetMapping("/")
    public String home(@CurrentUser Account account, Model model) {
        //List<Product> products = productRepository.findProductByProductStatus(ProductStatus.onSale);
        List<Product> products = productRepository.findAll();
        if (account != null) {
            model.addAttribute(account);

            Long notiCount = notiRepository.countRecvMsgByRecipient(account);
            model.addAttribute("notiCount", notiCount);
        }

        model.addAttribute("products", products);
        model.addAttribute("dataDir", uploadPath);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
