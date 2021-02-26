package com.jpabook.jpashop.noti.domain;

import com.jpabook.jpashop.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface NotiRepository extends JpaRepository<Noti, Long> {

    Long countRecvMsgByRecipient(Account recipient);

    List<Noti> findRecvMsgByRecipient(Account recipient);
}
