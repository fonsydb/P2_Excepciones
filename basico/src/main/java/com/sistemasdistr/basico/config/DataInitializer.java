package com.sistemasdistr.basico.config;

import com.sistemasdistr.basico.model.Role;
import com.sistemasdistr.basico.model.User;
import com.sistemasdistr.basico.repository.RoleRepository;
import com.sistemasdistr.basico.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        System.out.println("=== Inicializando datos ===");

        // Creamos roles si no existen
        if (roleRepository.count() == 0) {
            System.out.println("Creando roles...");
            Role adminRole = new Role();
            adminRole.setRoleName("ROL_ADMIN");
            adminRole.setShowOnCreate(1);

            Role userRole = new Role();
            userRole.setRoleName("ROL_USER");
            userRole.setShowOnCreate(1);

            roleRepository.save(adminRole);
            roleRepository.save(userRole);
            System.out.println("Roles creados");

            // Creamos admin
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setNombreUsuario("Administrador");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setUserRole(adminRole);
            userRepository.save(admin);

            // Creamos usuario
            User user = new User();
            user.setUsername("user");
            user.setEmail("user@example.com");
            user.setNombreUsuario("Usuario Normal");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setUserRole(userRole);
            userRepository.save(user);

            System.out.println("Usuarios creados - admin/admin123, user/user123");
        }
        System.out.println("=== Inicialización completa ===");
    }
}