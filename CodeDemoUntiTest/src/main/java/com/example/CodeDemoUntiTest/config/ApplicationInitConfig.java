package com.example.CodeDemoUntiTest.config;

import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    @NonFinal
    private static final String ADMIN_USER_EMAIL = "admin@gamil.com";

    @NonFinal
    private static final String ADMIN_PASSWORD = "admin";

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Bean
//    ApplicationRunner applicationRunner(AuthRepository authRepository) {
//        return args -> {
//            if (authRepository.findByEmail("admin").isEmpty()) {
//                var roles = new HashSet<String>();
//                roles.add(Roles.ADMIN.name());
//
//                User user = new User().builder()
//                        .email(ADMIN_USER_EMAIL)
//                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
//                        .roles(roles)
//                        .build();
//
//                authRepository.save(user);
//            }
//            log.warn("admin user has been created with default password: admin, please change it");
//        };
//    }
}
