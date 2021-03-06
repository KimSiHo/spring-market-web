package com.jpabook.jpashop.notification.domain;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.common.BaseTimeEntity;
import com.jpabook.jpashop.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor @Builder
@NoArgsConstructor
@Entity
public class Notification extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Account sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private Account recipient;

    private String message;

    @Enumerated(value = EnumType.STRING)
    private NotificationStatus notificationStatus;
}
