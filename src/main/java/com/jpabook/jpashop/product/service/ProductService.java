package com.jpabook.jpashop.product.service;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.common.file.service.FileService;
import com.jpabook.jpashop.product.domain.*;
import com.jpabook.jpashop.product.web.ProductKind;
import com.jpabook.jpashop.product.web.ProductUploadForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageFileRepository productImageFileRepository;

    private final FileService fileService;

    public Product upload(MultipartFile file, Account account, ProductUploadForm productUploadForm) {

        /*현재 S3를 업로드 서비스로 사용 중에 있음*/
        String uploadURL = fileService.uploadImage(file);

        Product product = Product.builder()
                            .bio(productUploadForm.getBio())
                            .account(account)
                            .price(productUploadForm.getPrice())
                            .productTitle(productUploadForm.getProductTitle())
                            .fileName(file.getOriginalFilename())
                            .productKind(productUploadForm.getProductKind())
                            .productStatus(ProductStatus.onSale)
                            .build();

        ProductImageFile productImageFile = ProductImageFile.builder()
                .product(product)
                .imageURL(uploadURL)
                .product(product)
                .build();

        productImageFileRepository.save(productImageFile);
        return productRepository.save(product);
    }

    public void sell(Long productId) {

        Product product = productRepository.findById(productId).get();
        product.soldOut();
    }

    public Page<Product> findTop10ByProductKind(Pageable pageable, ProductKind enumProductKind) {

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "id"));

        return productRepository.findTop10ByProductKind(pageable, enumProductKind);
    }

    public Page<Product> findProductSearchList(Pageable pageable, String keyword, String productKind) {

        ProductKind enumProductKind = ProductKind.valueOf(productKind);
        pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        return productRepository.findTop10SearchProductList(pageable, keyword, enumProductKind);
    }
}
