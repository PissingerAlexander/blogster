package de.alex.blogster_rest_api.config.admin;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultAdminUserConfig {

//    @Bean
//    public User insertDefaultAdminUser(UserRepository userRepository) {
//        if (userRepository.findByUsernameIgnoreCase("admin") == null) {
//            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            User user = new User(Role.ADMIN, "admin", passwordEncoder.encode("password"), "admin", "mail@admin.com");
//            userRepository.save(user);
//            System.out.println("added admin user with password: " + user.getPassword());
//        } else {
//            System.out.println("admin user already exists with password: " + userRepository.findByUsernameIgnoreCase("admin").getPassword());
//        }
//
//        return userRepository.findByUsernameIgnoreCase("admin");
//    }

}
