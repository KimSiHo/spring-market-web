package com.jpabook.jpashop.common.config;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.account.domain.AccountRepository;
import com.jpabook.jpashop.noti.domain.Noti;
import com.jpabook.jpashop.noti.domain.NotiRepository;
import com.jpabook.jpashop.product.domain.Product;
import com.jpabook.jpashop.product.domain.ProductRepository;
import com.jpabook.jpashop.product.domain.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("!test")
@RequiredArgsConstructor
@Component
@Order(0)
public class DataInsert implements ApplicationRunner {

    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final NotiRepository notiRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = createAccount("kim125y", "kim125y@naver.com", "wkwjsrj");
        Account account1 = createAccount("odreamgodo", "odreamgodo@naver.com", "wkwjsrj");
        Account account2 = createAccount("kim125", "kim125@naver.com", "wkwjsrj");
        Account account3 = createAccount("kim9810", "kim9810@naver.com", "wkwjsrj");

   /*     createProduct("0ad63f3cf92a41191fbc71ae9a52deb0", "shoes", "아디다스 신발입니다", ProductStatus.onSale, account);
        createProduct("unnamed", "shoes", "퓨마 신발입니다", ProductStatus.onSale, account1);

        createProduct("9b0e3ed41433acedd116f8de1b4909ed9c6bcc7b8c82fe9c745a8940514fa28b", "shelf", "큰 선반입니다", ProductStatus.onSale, account1);
        createProduct("unnamed (1)", "shelf", "작은 선반입니다", ProductStatus.onSale, account2);

        createProduct("a6d7857120c70a00a8e67515697ac681a822356d07816ea67d7c5b5ce4f7403f", "monitor", "모니터 입니다", ProductStatus.onSale, account2);

        createProduct("다운로드 (1)", "tshirt", "큰 티셔츠입니다", ProductStatus.onSale, account2);
        createProduct("다운로드", "tshirt", "검정 티셔츠입니다", ProductStatus.onSale, account3);

        Product audio = createProduct("다운로드 (2)", "audio", "낣은 오디오입니다.", ProductStatus.onSale, account);
        createProduct("다운로드 (3)", "audio", "비싼 오디오입니다.", ProductStatus.onSale, account);
        createProduct("다운로드 (4)", "audio", "저렴한 오디오입니다.", ProductStatus.onSale, account2);

        createProduct("다운로드 (5)", "microwave", "전자레인지 입니다.", ProductStatus.soldOut, account1);

        createNoti(account2, account, audio, "구매 요청합니다");
        createNoti(account3, account, audio, "구매 요청합니다");*/

        createPost();

    }

    private void createPost() {
    }

    private void createNoti(Account sender, Account recipient, Product audio, String msg) {
        Noti noti = Noti.builder()
                        .sender(sender)
                        .recipient(recipient)
                        .product(audio)
                        .message(msg)
                        .build();

        notiRepository.save(noti);
    }

    private Product createProduct(String fileName, String keyword, String pio, ProductStatus productStatus, Account account) {
        Product product =Product.builder()
                .fileName(fileName)
                .keyword(keyword)
                .pio(pio)
                .productStatus(productStatus)
                .account(account)
                .build();

        return productRepository.save(product);
    }

    private Account createAccount(String nickname, String email, String passwd) {
        Account account = Account.builder()
                .email(email)
                .nickname(nickname)
                .password(passwordEncoder.encode(passwd))
                .build();

        return accountRepository.save(account);
    }
}
