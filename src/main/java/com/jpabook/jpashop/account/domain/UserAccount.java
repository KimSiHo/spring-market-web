package com.jpabook.jpashop.account.domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.List;

public class UserAccount extends User {

    private Account account;

    public UserAccount(Account account) {
        super(account.getNickname(), account.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
