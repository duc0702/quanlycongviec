package com.bkacad.thuchanh.service_quanly_nguoidung.configuration;

import com.bkacad.thuchanh.service_quanly_nguoidung.entity.User;
import com.bkacad.thuchanh.service_quanly_nguoidung.enums.UserRole;
import com.bkacad.thuchanh.service_quanly_nguoidung.enums.UserStatus;
import com.bkacad.thuchanh.service_quanly_nguoidung.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationInitConfig {
@Autowired
    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return  args -> {
           if ( userRepository.findByUsername("admin").isEmpty()){
                User user = User.builder()
                          .username("admin")
                          .password(passwordEncoder.encode("admin"))
                          .role(UserRole.ADMIN)
                        .email("abch@gmail.com")
                        .status(  UserStatus.ACTIVE)
                        .fullName("admin")
                        .phone("123456789")
                          .build();
                userRepository.save(user);
           }
        };
    }
}
