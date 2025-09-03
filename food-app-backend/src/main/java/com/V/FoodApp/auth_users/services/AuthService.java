package com.V.FoodApp.auth_users.services;

import com.V.FoodApp.auth_users.dtos.LoginRequest;
import com.V.FoodApp.auth_users.dtos.LoginResponse;
import com.V.FoodApp.auth_users.dtos.RegistrationRequest;
import com.V.FoodApp.response.Response;

public interface AuthService {

    Response<?> register(RegistrationRequest registrationRequest);

    Response<LoginResponse> login(LoginRequest loginRequest);
}
