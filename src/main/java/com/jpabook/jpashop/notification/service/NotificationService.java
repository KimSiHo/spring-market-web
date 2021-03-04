package com.jpabook.jpashop.notification.service;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.notification.domain.Notification;
import com.jpabook.jpashop.notification.domain.NotificationRepository;
import com.jpabook.jpashop.notification.domain.NotificationStatus;
import com.jpabook.jpashop.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void sendNotification(Account sender, Account recipient, Product product, NotificationStatus notificationStatus, String message) {
        Notification notification = Notification.builder()
                        .message(message)
                        .sender(sender)
                        .product(product)
                        .recipient(recipient)
                        .notificationStatus(notificationStatus)
                        .build();

        notificationRepository.save(notification);
    }

    public void handleSellNotification(Long notificationId) {

        Notification notification = notificationRepository.findById(notificationId).get();
        Product product = notification.getProduct();
        Account sender = notification.getSender();
        Account recipient = notification.getRecipient();

        sendNotification(recipient, sender, product, NotificationStatus.saleCompleted, "구매 확정되었습니다.");

        notificationRepository.delete(notification);
    }
}
