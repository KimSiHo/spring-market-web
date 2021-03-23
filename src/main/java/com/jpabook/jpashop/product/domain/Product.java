package com.jpabook.jpashop.product.domain;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.common.BaseTimeEntity;
import com.jpabook.jpashop.product.web.ProductKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder @AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String bio;

    private int price;

    //상품 관련 게시판으로 refactor 필요
    private String productTitle;

    @Enumerated(EnumType.STRING)
    private ProductKind productKind;

    private String fileName;

    @Enumerated(value = EnumType.STRING)
    private ProductStatus productStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @OneToMany(mappedBy = "product")
    private List<ProductImageFile> productImageFile = new ArrayList<>();

    public void soldOut(){
        this.productStatus = ProductStatus.soldOut;
    }

}
