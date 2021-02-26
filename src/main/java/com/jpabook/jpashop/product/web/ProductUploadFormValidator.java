package com.jpabook.jpashop.product.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;


@Component
@RequiredArgsConstructor
public class ProductUploadFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(ProductUploadForm.class);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ProductUploadForm productUploadForm = (ProductUploadForm)object;
        MultipartFile multipartFile = productUploadForm.getProductImageFile();

        if(multipartFile.getSize() == 0) {
            errors.rejectValue("productImageFile", "invalid.productImageFile", new Object[]{productUploadForm.getProductImageFile()}, "파일을 첨부해주세요.");
        }

    }
}
