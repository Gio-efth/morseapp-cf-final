package gr.aueb.cf.morseapp.config;

import gr.aueb.cf.morseapp.core.enums.Role;
import gr.aueb.cf.morseapp.model.User;
import gr.aueb.cf.morseapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class UserInitializer {

    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("morseUser").isEmpty()) {
                User user = User.builder()
                        .username("morseUser")
                        .password(passwordEncoder.encode("12345"))
                        .role(Role.USER)
                        .build();
                userRepository.save(user);
            }
        };
    }
}
