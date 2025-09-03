package com.V.FoodApp.auth_users.services;


import com.V.FoodApp.auth_users.dtos.LoginRequest;
import com.V.FoodApp.auth_users.dtos.LoginResponse;
import com.V.FoodApp.auth_users.dtos.RegistrationRequest;
import com.V.FoodApp.auth_users.entity.User;
import com.V.FoodApp.auth_users.repository.UserRepository;
import com.V.FoodApp.exception.BadRequestException;
import com.V.FoodApp.exception.NotFoundException;
import com.V.FoodApp.response.Response;
import com.V.FoodApp.role.entity.Role;
import com.V.FoodApp.role.repository.RoleRepository;
import com.V.FoodApp.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;

    @Override
    public Response<?> register(RegistrationRequest registrationRequest) {
        log.info("INSIDE register()");

        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        //Find all roles from the request
        List<Role> userRole;

        if (registrationRequest.getRoles() != null && !registrationRequest.getRoles().isEmpty()) {
            userRole = registrationRequest.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName.toUpperCase())
                            .orElseThrow(() -> new NotFoundException("Role with name: " + roleName + " Not found")))
                    .toList();
        } else {
            //if no roles prive
            Role defaultRole = roleRepository.findByName("CUSTOMER")
                    .orElseThrow(() -> new NotFoundException("Default CUSTOMER role not found"));
            userRole = List.of(defaultRole);
        }
        //build the user object
        User userToSave = User.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .phonenumber(registrationRequest.getPhonenumber())
                .address(registrationRequest.getAddress())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .roles(userRole)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(userToSave);

        log.info("User registered successfully");

        return Response.builder().statusCode(HttpStatus.OK.value())
                .message("User Registered Successfully")
                .build();
    }

    @Override
    public Response<LoginResponse> login(LoginRequest loginRequest) {
        log.info("INSIDE login()");

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("Invalid Email"));
        if (!user.isActive()) {
            throw new NotFoundException("Account not active, Please contact customer support");
        }

        //verify the password
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid Password");
        }

        // Generate a token
        String token = jwtUtils.generateToken(user.getEmail());

        //Extract roles names as a list
        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName).toList();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setRoles(roleNames);
        return Response.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login successful")
                .data(loginResponse)
                .build();
    }
}
