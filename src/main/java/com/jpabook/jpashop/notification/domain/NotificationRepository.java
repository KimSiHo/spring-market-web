package com.jpabook.jpashop.notification.domain;

import com.jpabook.jpashop.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Long countRecvMsgByRecipient(Account recipient);

    List<Notification> findRecvMsgByRecipient(Account recipient);
}
