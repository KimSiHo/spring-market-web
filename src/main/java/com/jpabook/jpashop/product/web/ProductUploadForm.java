package com.jpabook.jpashop.product.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder @AllArgsConstructor
@NoArgsConstructor
public class ProductUploadForm {

    @NotBlank
    @Length(min = 8, max = 50)
    private String bio;

    @NotNull
    private ProductKind productKind;

    @NotNull
    private int price;

    @NotBlank
    @Length(min = 8, max = 30)
    private String productTitle;

    private MultipartFile productImageFile;
}
