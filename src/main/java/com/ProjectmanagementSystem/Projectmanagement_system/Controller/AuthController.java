package com.ProjectmanagementSystem.Projectmanagement_system.Controller;

import com.ProjectmanagementSystem.Projectmanagement_system.Config.JwtProvider;
import com.ProjectmanagementSystem.Projectmanagement_system.Models.User;
import com.ProjectmanagementSystem.Projectmanagement_system.Repositories.UserRepository;
import com.ProjectmanagementSystem.Projectmanagement_system.Request.LoginRequest;
import com.ProjectmanagementSystem.Projectmanagement_system.Response.AuthResponse;
import com.ProjectmanagementSystem.Projectmanagement_system.Services.CustomUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsImpl customUserDetails;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signup(@RequestBody User user) throws Exception {
        User fetchedUser = userRepository.findByEmail(user.getEmail());

        if (fetchedUser != null) throw new Exception("User already exists");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);;
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("Signup successful");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("Login successful");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(email);
        if (userDetails == null) throw new BadCredentialsException("Invalid Email");

        if (!passwordEncoder.matches(password, userDetails.getPassword())) throw new BadCredentialsException("Invalid Credentials");

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}




