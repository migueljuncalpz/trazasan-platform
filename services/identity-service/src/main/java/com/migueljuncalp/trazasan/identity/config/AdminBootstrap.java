package com.migueljuncalp.trazasan.identity.config;

import com.migueljuncalp.trazasan.identity.domain.AppUser;
import com.migueljuncalp.trazasan.identity.domain.Hospital;
import com.migueljuncalp.trazasan.identity.domain.Role;
import com.migueljuncalp.trazasan.identity.repository.AppUserRepository;
import com.migueljuncalp.trazasan.identity.repository.HospitalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Component
public class AdminBootstrap implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminBootstrap.class);

    private final HospitalRepository hospitalRepository;
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String hospitalName;
    private final String hospitalSlug;
    private final String adminEmail;
    private final String adminPassword;

    public AdminBootstrap(
            HospitalRepository hospitalRepository,
            AppUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${trazasan.bootstrap-admin.hospital-name}") String hospitalName,
            @Value("${trazasan.bootstrap-admin.hospital-slug}") String hospitalSlug,
            @Value("${trazasan.bootstrap-admin.email}") String adminEmail,
            @Value("${trazasan.bootstrap-admin.password:}") String adminPassword) {
        this.hospitalRepository = hospitalRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.hospitalName = hospitalName;
        this.hospitalSlug = hospitalSlug;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (adminPassword.isBlank()) {
            log.info("Administrador inicial no creado: TRAZASAN_ADMIN_PASSWORD no está definida");
            return;
        }

        String normalizedEmail = adminEmail.trim().toLowerCase(Locale.ROOT);
        Hospital hospital = hospitalRepository.findBySlug(hospitalSlug)
                .orElseGet(() -> hospitalRepository.save(new Hospital(hospitalName, hospitalSlug)));

        if (userRepository.existsByHospitalIdAndEmailIgnoreCase(hospital.getId(), normalizedEmail)) {
            log.info("El administrador inicial ya existe para el hospital {}", hospitalSlug);
            return;
        }

        userRepository.save(new AppUser(
                hospital,
                normalizedEmail,
                passwordEncoder.encode(adminPassword),
                Role.ADMIN
        ));
        log.info("Administrador inicial {} creado para el hospital {}", normalizedEmail, hospitalSlug);
    }
}
