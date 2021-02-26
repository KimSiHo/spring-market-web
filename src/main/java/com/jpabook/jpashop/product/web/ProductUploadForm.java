package com.jpabook.jpashop.product.web;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class ProductUploadForm {

    @NotBlank
    @Length(min = 8, max = 50)
    private String pio;

    private MultipartFile productImageFile;
}
