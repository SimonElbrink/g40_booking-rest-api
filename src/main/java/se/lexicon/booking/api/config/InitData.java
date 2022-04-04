package se.lexicon.booking.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.booking.api.model.entity.Role;
import se.lexicon.booking.api.model.entity.User;
import se.lexicon.booking.api.repository.UserRepository;

import java.util.Arrays;
import java.util.Collections;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

        Role role1= new Role();
        role1.setName("ROLE_ADMIN");

        Role role2= new Role();
        role2.setName("ROLE_USER");


        User admin= new User();
        admin.setRoles(Arrays.asList(role1,role2));
        admin.setName("admin admin");
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        userRepository.save(admin);


        User user= new User();
        user.setRoles(Collections.singletonList(role2));
        user.setName("user user");
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        userRepository.save(user);

    }
}
