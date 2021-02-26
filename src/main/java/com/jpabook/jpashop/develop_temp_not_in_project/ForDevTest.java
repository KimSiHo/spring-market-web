package com.jpabook.jpashop.develop_temp_not_in_project;

import com.jpabook.jpashop.account.domain.AccountRepository;
import com.jpabook.jpashop.noti.domain.NotiRepository;
import com.jpabook.jpashop.product.domain.Product;
import com.jpabook.jpashop.product.domain.ProductRepository;
import com.jpabook.jpashop.product.domain.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Order(1)
public class ForDevTest implements ApplicationRunner {

    private final NotiRepository notiRepository;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        /*Account account = accountRepository.findByNickname("kim125y");
        System.out.println("=============================");
        Long byRecipent = notiRepository.countRecvMsgByRecipient(account);
        System.out.println("=============================");

        System.out.println("byRecipent = " + byRecipent);*/

        /*System.out.println("=============================");
        Long count = productRepository.countProductByProductStatus(ProductStatus.onSale);
        System.out.println("count = " + count);
        System.out.println("=============================");*/

        /*System.out.println("=============================");
        List<Product> productByProductStatus = productRepository.findProductByProductStatus(ProductStatus.onSale);

        for (Product byProductStatus : productByProductStatus) {
            System.out.println("byProductStatus = " + byProductStatus.getPio());
        }
        System.out.println("=============================");*/

    }
}
