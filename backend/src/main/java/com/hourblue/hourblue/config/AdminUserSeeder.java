package com.hourblue.hourblue.config;

import com.hourblue.hourblue.model.AdminUser;
import com.hourblue.hourblue.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Creates the single admin_user row from environment variables on startup, if it
 * doesn't already exist. This is the "credentials set via environment, not a signup
 * page" approach from PLANNING.md Section 3.7 / 9.1.
 *
 * Set ADMIN_SEED_USERNAME and ADMIN_SEED_PASSWORD in your .env for local dev / Railway
 * environment variables for production. Leave them unset after the first successful run -
 * this only ever creates the account, it never overwrites an existing password.
 */
@Component
public class AdminUserSeeder implements CommandLineRunner {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final String seedUsername;
    private final String seedPassword;

    public AdminUserSeeder(AdminUserRepository adminUserRepository,
                            PasswordEncoder passwordEncoder,
                            @Value("${ADMIN_SEED_USERNAME:}") String seedUsername,
                            @Value("${ADMIN_SEED_PASSWORD:}") String seedPassword) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.seedUsername = seedUsername;
        this.seedPassword = seedPassword;
    }

    @Override
    public void run(String... args) {
        if (seedUsername.isBlank() || seedPassword.isBlank()) {
            return;
        }
        if (adminUserRepository.findByUsername(seedUsername).isPresent()) {
            return;
        }

        AdminUser admin = new AdminUser();
        admin.setUsername(seedUsername);
        admin.setPasswordHash(passwordEncoder.encode(seedPassword));
        adminUserRepository.save(admin);
    }
}
