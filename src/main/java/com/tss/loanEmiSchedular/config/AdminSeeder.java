//package com.tss.loanEmiSchedular.config;
//
//import com.tss.loanEmiSchedular.entity.User;
//import com.tss.loanEmiSchedular.enums.Role;
//import com.tss.loanEmiSchedular.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
///**
// * Solves the bootstrap problem: registerStaff (which creates LOAN_OFFICER /
// * ADMIN accounts) is itself locked behind hasRole("ADMIN"), so without this
// * seeder there would be no way to create the very first ADMIN account.
// *
// * On startup, if no ADMIN exists yet, one is created from the
// * app.admin.default-email / app.admin.default-password properties. Change
// * that password immediately after first login in any real deployment.
// */
//@Component
//@RequiredArgsConstructor
//public class AdminSeeder implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Value("${app.admin.default-email:admin@loanapp.com}")
//    private String defaultAdminEmail;
//
//    @Value("${app.admin.default-password:Admin@12345}")
//    private String defaultAdminPassword;
//
//    @Override
//    public void run(String... args) {
//
//        boolean adminExists = userRepository.findAll().stream()
//                .anyMatch(u -> u.getRole() == Role.ADMIN);
//
//        if (adminExists) {
//            return;
//        }
//
//        User admin = new User();
//        admin.setEmail(defaultAdminEmail);
//        admin.setPassword(passwordEncoder.encode(defaultAdminPassword));
//        admin.setRole(Role.ADMIN);
//        userRepository.save(admin);
//
//        System.out.println("Seeded default ADMIN account: " + defaultAdminEmail
//                + " — log in and change this password immediately.");
//    }
//}
