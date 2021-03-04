package com.jpabook.jpashop.product.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@Builder @AllArgsConstructor
@NoArgsConstructor
public class ProductUploadForm {

    @NotBlank
    @Length(min = 8, max = 50)
    private String bio;

    private ProductKind productKind;

    private MultipartFile productImageFile;
}
