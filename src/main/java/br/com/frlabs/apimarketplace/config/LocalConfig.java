package br.com.frlabs.apimarketplace.config;

import br.com.frlabs.apimarketplace.domain.User;
import br.com.frlabs.apimarketplace.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public void startDB() {
        User u1 = new User(null, "Felipe1", "felipeclownz@gmail.com");
        User u2 = new User(null, "Felipe2", "frlabsmd@gmail.com");

        userRepository.saveAll(List.of(u1, u2));
    }
}
