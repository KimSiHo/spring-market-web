package com.jpabook.jpashop.common.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ProductImageFileRepository extends JpaRepository<ProductImageFile, Long> {
}
