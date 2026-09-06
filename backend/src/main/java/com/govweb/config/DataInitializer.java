package com.govweb.config;

import com.govweb.service.Service;
import com.govweb.service.ServiceRepository;
import com.govweb.user.Role;
import com.govweb.user.User;
import com.govweb.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seedData(
            UserRepository users,
            ServiceRepository services,
            PasswordEncoder encoder,
            @Value("${app.seed-admin.enabled:true}") boolean seedAdmin,
            @Value("${app.seed-admin.email:admin@govweb.local}") String adminEmail,
            @Value("${app.seed-admin.password:Admin@12345}") String adminPassword) {
        return args -> {
            if (seedAdmin && users.findByEmail(adminEmail).isEmpty()) {
                var admin = new User();
                admin.setFullName("GovWeb Officer");
                admin.setEmail(adminEmail);
                admin.setMobile("9800000000");
                admin.setAddress("Municipality Office");
                admin.setPassword(encoder.encode(adminPassword));
                admin.setRole(Role.OFFICER);
                admin.setVerified(true);
                users.save(admin);
            }

            if (users.findByEmail("citizen@govweb.local").isEmpty()) {
                var citizen = new User();
                citizen.setFullName("Sujan Karki");
                citizen.setEmail("citizen@govweb.local");
                citizen.setMobile("9812345678");
                citizen.setAddress("Kathmandu, Nepal");
                citizen.setPassword(encoder.encode("Citizen@12345"));
                citizen.setRole(Role.CITIZEN);
                citizen.setVerified(true);
                users.save(citizen);
            }

            if (services.count() == 0) {
                addService(services, "Birth Certificate", "जन्म दर्ता प्रमाणपत्र", "Official proof of birth registration for citizens.", 200, 5);
                addService(services, "Residence Certificate", "बसोबास प्रमाणपत्र", "Verify your current address for official use.", 150, 3);
                addService(services, "Marriage Certificate", "विवाह दर्ता प्रमाणपत्र", "Register and receive proof of your marriage.", 500, 7);
                addService(services, "Relationship Certificate", "नाता प्रमाणित प्रमाणपत्र", "Document your relationship with family members.", 250, 5);
            } else {
                services.findAll().forEach(service -> {
                    if (!Integer.valueOf(1500).equals(service.getFee())) {
                        service.setFee(1500);
                        services.save(service);
                    }
                });
            }
        };
    }

    private void addService(ServiceRepository repository, String name, String nepaliName, String description, int fee, int days) {
        var service = new Service();
        service.setName(name);
        service.setNepaliName(nepaliName);
        service.setDescription(description);
        service.setFee(1500);
        service.setProcessingDays(days);
        service.setActive(true);
        repository.save(service);
    }
}
