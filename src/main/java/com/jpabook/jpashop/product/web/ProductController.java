package com.jpabook.jpashop.product.web;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.account.service.CurrentUser;
import com.jpabook.jpashop.notification.domain.NotificationStatus;
import com.jpabook.jpashop.notification.service.NotificationService;
import com.jpabook.jpashop.product.domain.Product;
import com.jpabook.jpashop.product.domain.ProductRepository;
import com.jpabook.jpashop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;
    private final ProductUploadFormValidator productUploadFormValidator;
    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    @InitBinder("productUploadForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(productUploadFormValidator);
    }

    @GetMapping("/upload/product")
    public String uploadProduct(Model model) {
        model.addAttribute(new ProductUploadForm());
        return "/product/upload-product";
    }

    @PostMapping("/upload/product")
    public String uploadProduct(@Valid ProductUploadForm productUploadForm, Errors errors,
                                @CurrentUser Account account, @RequestParam("productImageFile") MultipartFile file) throws IOException {

        if(errors.hasErrors()){
            return "/product/upload-product";
        }

        /*System.out.println("=============================");
        System.out.println("productUploadForm = " + productUploadForm);
        System.out.println("=============================");
        System.out.println("file = " + file);
        System.out.println("=============================");*/

        productService.upload(file, account, productUploadForm);
        return "redirect:/";
    }

    @GetMapping("/product/detail/{id}")
    public String detailProduct(@PathVariable Long id, Model model) {
        Optional<Product> byId = productRepository.findById(id);
        Product product = byId.get();
        model.addAttribute(product);
        return "/product/detail";
    }

    @GetMapping("/buy/proudct/{id}")
    public String buyProduct(@CurrentUser Account buyer, @PathVariable Long id, Model model) {
        Optional<Product> byId = productRepository.findById(id);
        Product product = byId.get();
        Account owner = product.getAccount();

        notificationService.sendNotification(buyer, owner, product, NotificationStatus.purchaseRequest, "구매 요청입니다");

        return "redirect:/";
    }

    @GetMapping("/sell/product")
    public String buyProduct(@CurrentUser Account account,
                             @RequestParam(value = "productId", required=false) Long productId,
                             @RequestParam(value = "notificationId", required=false) Long notificationId,
                             Model model) {

        notificationService.handleSellNotification(notificationId);
        productService.sell(productId);

        return "redirect:/";
    }
}
