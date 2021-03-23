package com.jpabook.jpashop.product.domain;

import com.jpabook.jpashop.product.web.ProductKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long> {

    Long countProductByProductStatus(ProductStatus productStatus);

    List<Product> findTop8ByProductStatusAndProductKind(ProductStatus onSale, ProductKind cloth);

    List<Product> findAllByProductKind(ProductKind productKind);
}
