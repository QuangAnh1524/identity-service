package qanh.indentityservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import qanh.indentityservice.entity.User;
import qanh.indentityservice.enums.Role;
import qanh.indentityservice.repository.UserRepository;

import java.util.HashSet;

@Configuration
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;
    public ApplicationInitConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                User user = User.builder()
                        .username("admin")
                        .roles(roles)
                        .password(passwordEncoder.encode("123"))
                        .build();

                userRepository.save(user);
                log.warn("Create default user: admin with password: 123, please change it");
            }
        };
    }
}
