package com.pg.helpdesk.init;

import com.pg.helpdesk.entity.Role;
import com.pg.helpdesk.entity.User;
import com.pg.helpdesk.repository.RoleRepository;
import com.pg.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
@Order(10)
public class UserInitializer implements DataLoader {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void load() {
        if (userRepository.count() == 0 && roleRepository.count() == 0) {
            Role roleUser = new Role("ROLE_USER");
            Role roleHr = new Role("ROLE_HR");
            Role roleHelpdesk = new Role("ROLE_HELPDESK");
            roleRepository.save(new HashSet<>(Arrays.asList(roleUser, roleHr, roleHelpdesk)));

            User userAdmin = new User();
            userAdmin.setUsername("admin");
            userAdmin.setFirstName("Lucjan");
            userAdmin.setLastName("Kozipas");
            userAdmin.setEmail("admin@helpdesk.com");
            userAdmin.setPassword(encoder.encode("admin"));
            userAdmin.setRoles(new HashSet<>(Arrays.asList(roleUser, roleHr, roleHelpdesk)));
            userRepository.save(userAdmin);

            User user = new User();
            user.setUsername("user");
            user.setFirstName("John");
            user.setLastName("Troglodyta");
            user.setEmail("user@example.com");
            user.setRoles(new HashSet<>(Arrays.asList(roleUser)));
            user.setPassword(encoder.encode("user"));
            userRepository.save(user);

            User userHelpdesk = new User();
            userHelpdesk.setUsername("helpdesk");
            userHelpdesk.setFirstName("Derek");
            userHelpdesk.setLastName("Rozpierducha");
            userHelpdesk.setEmail("rozpierducha@helpdesk.com");
            userHelpdesk.setRoles(new HashSet<>(Arrays.asList(roleUser, roleHelpdesk)));
            userHelpdesk.setPassword(encoder.encode("helpdesk"));
            userRepository.save(userHelpdesk);
        }
    }
}
