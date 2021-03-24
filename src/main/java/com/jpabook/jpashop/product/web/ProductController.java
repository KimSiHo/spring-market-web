package com.jpabook.jpashop.product.web;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.account.service.CurrentUser;
import com.jpabook.jpashop.notification.domain.NotificationStatus;
import com.jpabook.jpashop.notification.service.NotificationService;
import com.jpabook.jpashop.product.domain.Product;
import com.jpabook.jpashop.product.domain.ProductRepository;
import com.jpabook.jpashop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Slf4j
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
        return "product/product-upload";
    }

    @PostMapping("/upload/product")
    public String uploadProduct(@Valid ProductUploadForm productUploadForm, Errors errors,
                                @CurrentUser Account account, @RequestParam("productImageFile") MultipartFile file) throws IOException {

        if(errors.hasErrors()){
            return "product/product-upload";
        }

        /*System.out.println("=============================");
        System.out.println("productUploadForm = " + productUploadForm);
        System.out.println("=============================");
        System.out.println("file = " + file);
        System.out.println("=============================");*/

        productService.upload(file, account, productUploadForm);
        return "redirect:/";
    }

    @GetMapping("/search/product")
    public String searchProduct(@RequestParam(name = "keyword") String keyword,
                                @RequestParam(name = "productKind") String productKind,
                                @PageableDefault Pageable pageable, Model model) {
        log.debug( "검색 KEYWORD : {}, 검색 상품 종류 : {}", keyword, productKind);

        Page<Product> productSearchList = productService.findProductSearchList(pageable, keyword, productKind);

        model.addAttribute("productList", productSearchList);
        model.addAttribute("productKind", productKind);

        return "product/product-list";
    }

    @GetMapping("/product/list/{productKind}")
    public String productList(@PageableDefault Pageable pageable, @PathVariable String productKind, Model model) {
        ProductKind enumProductKind = ProductKind.valueOf(productKind);
        Page<Product> top10ByByProductKind = productService.findTop10ByProductKind(pageable, enumProductKind);

        model.addAttribute("productList", top10ByByProductKind);
        model.addAttribute("productKind", productKind);
        return "product/product-list";
    }

    @GetMapping("/product/detail/{productKind}/{id}")
    public String detailElectronicProduct(@PathVariable Long id, @PathVariable String productKind, Model model) {
        Optional<Product> byId = productRepository.findById(id);
        Product product = byId.get();
        model.addAttribute(product);
        model.addAttribute("productKind", productKind);
        return "product/product-detail";
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
