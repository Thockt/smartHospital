package com.example.smartHospital.security;

import com.example.smartHospital.requests.AuthenticationRequest;
import com.example.smartHospital.requests.RegistrationRequest;
import com.example.smartHospital.responses.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (@RequestBody RegistrationRequest request)  {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping("/logout/{id}")
    public void logout (HttpServletRequest httpRequest, @PathVariable Long id) {
        authenticationService.logout(httpRequest,id);
    }

}
