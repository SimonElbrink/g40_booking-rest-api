package se.lexicon.booking.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.lexicon.booking.api.model.entity.Role;
import se.lexicon.booking.api.repository.RoleRepository;

@Component
public class InitData implements CommandLineRunner {


    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        Role role = new Role();
        role.setName("ROLE_ADMIN");
        roleRepository.save(role);

        Role role2 = new Role();
        role2.setName("ROLE_USER");
        roleRepository.save(role2);


    }
}
