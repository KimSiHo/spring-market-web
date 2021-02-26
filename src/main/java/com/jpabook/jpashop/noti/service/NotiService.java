package com.jpabook.jpashop.noti.service;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.noti.domain.Noti;
import com.jpabook.jpashop.noti.domain.NotiRepository;
import com.jpabook.jpashop.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotiService {

    private final NotiRepository notiRepository;

    public void sendNoti(Account buyer, Account owner, Product product, String message) {
        Noti noti = Noti.builder()
                        .message(message)
                        .sender(buyer)
                        .product(product)
                        .recipient(owner)
                        .build();

        notiRepository.save(noti);
    }
}
