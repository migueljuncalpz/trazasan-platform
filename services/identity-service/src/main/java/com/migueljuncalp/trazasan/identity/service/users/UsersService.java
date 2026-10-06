package com.migueljuncalp.trazasan.identity.service.users;

import com.migueljuncalp.trazasan.identity.controller.dto.user.request.CreateUserRequest;
import com.migueljuncalp.trazasan.identity.controller.dto.user.request.Role;
import com.migueljuncalp.trazasan.identity.controller.dto.user.response.CreateUserResponse;
import com.migueljuncalp.trazasan.identity.domain.AppUser;
import com.migueljuncalp.trazasan.identity.domain.Hospital;
import com.migueljuncalp.trazasan.identity.repository.AppUserRepository;
import com.migueljuncalp.trazasan.identity.repository.HospitalRepository;
import com.migueljuncalp.trazasan.identity.service.users.exceptions.HospitalNotFoundException;
import com.migueljuncalp.trazasan.identity.service.users.exceptions.UserEmailDomainMatchException;
import jakarta.validation.constraints.Email;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsersService {

    private final AppUserRepository appUserRepository;
    private final HospitalRepository hospitalRespository;
    private final PasswordEncoder passwordEncoder;


    public UsersService (AppUserRepository appUserRepository, HospitalRepository hospitalRepository, PasswordEncoder passwordEncoder){
        this.appUserRepository = appUserRepository;
        this.hospitalRespository = hospitalRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public CreateUserResponse create(CreateUserRequest createUserRequest){
        String userEmail = createUserRequest.email();
        Role role = createUserRequest.role();
        String hospitalSlug = createUserRequest.hospitalSlug();
        Hospital hospital = checkIfEmailDomainMatchWithHospitalDomain(userEmail,hospitalSlug);

        appUserRepository.save(new AppUser(
                hospital,
                userEmail,
                passwordEncoder.encode(createUserRequest.password()),
                com.migueljuncalp.trazasan.identity.domain.Role.valueOf(role.name())));

        appUserRepository.findAppUsersByHospital_SlugAndEmailIgnoreCase(createUserRequest.hospitalSlug(),userEmail);

        return new CreateUserResponse(userEmail,role);
    }

    private Hospital checkIfEmailDomainMatchWithHospitalDomain(String userEmail,String hospitalSlug){

        Hospital hospital = hospitalRespository.findBySlug(hospitalSlug).orElseThrow(
                HospitalNotFoundException::new
        );

        String userEmailDomain = Optional.ofNullable(userEmail)
                .filter(e -> e.contains("@"))
                .map(e->e.substring(e.indexOf("@")+1))
                .orElse("");

        if(!hospital.getDomain().equals(userEmailDomain)){
            throw new UserEmailDomainMatchException();
        }
        return hospital;
    }

}
