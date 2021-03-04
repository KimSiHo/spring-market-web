package com.jpabook.jpashop.product.domain;

import com.jpabook.jpashop.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder @AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductImageFile {

    @Id @GeneratedValue
    @Column(name = "product_image_file_id")
    private Long id;

    private String imageURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
