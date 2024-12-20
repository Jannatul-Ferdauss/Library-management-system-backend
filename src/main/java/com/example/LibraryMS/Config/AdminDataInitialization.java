package com.example.LibraryMS.Config;

import com.example.LibraryMS.Entity.Admin;
import com.example.LibraryMS.Repository.AdminRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminDataInitialization {

    @Autowired
    private AdminRepository adminRepository;

    @Bean
    public CommandLineRunner initializeAdminData() {
        return args -> {
            // Hardcoded admin data with hashed passwords
            String hashedPassword1 = BCrypt.hashpw("Admin123", BCrypt.gensalt());
            String hashedPassword2 = BCrypt.hashpw("Admin456", BCrypt.gensalt());
            String hashedPassword3 = BCrypt.hashpw("Admin789", BCrypt.gensalt());

            // Create Admin objects
            if (!adminRepository.existsByEmail("jannatulferdaus@gmail.com")) {
                Admin admin1 = new Admin(
                        "Jannatul Ferdaus",
                        "jannatulferdaus@gmail.com",
                        hashedPassword1,
                        "01234567890" // Contact
                );
                adminRepository.save(admin1);
            }

            if (!adminRepository.existsByEmail("fawzia@gmail.com")) {
                Admin admin2 = new Admin(
                        "Fawzia Tabassum",
                        "fawzia@gmail.com",
                        hashedPassword2,
                        "09876543210" // Contact
                );
                adminRepository.save(admin2);
            }

            if (!adminRepository.existsByEmail("shahinur@gmail.com")) {
                Admin admin3 = new Admin(
                        "Shahinur Marin",
                        "shahinur@gmail.com",
                        hashedPassword3,
                        "01122334455" // Contact
                );
                adminRepository.save(admin3);

            }

            System.out.println("Admin data initialized successfully!");
        };
    }
}
