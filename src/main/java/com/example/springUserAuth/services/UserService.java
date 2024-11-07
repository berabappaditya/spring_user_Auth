package com.example.springUserAuth.services;

import com.example.springUserAuth.models.Role;
import com.example.springUserAuth.models.Token;
import com.example.springUserAuth.models.User;
import com.example.springUserAuth.repositories.RoleRepository;
import com.example.springUserAuth.repositories.TokenRepository;
import com.example.springUserAuth.repositories.UserRepository;
import com.example.springUserAuth.util.JwtTokenUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private RoleRepository roleRepository;
    private JwtTokenUtil jwtTokenUtil;

    UserService(BCryptPasswordEncoder bCryptPasswordEncoder,
                UserRepository userRepository,
                TokenRepository tokenRepository,
                RoleRepository roleRepository,
                JwtTokenUtil jwtTokenUtil) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Transactional
    public Boolean signup(String email,String name,String password,int role){
        // Check if user already exists
        if (userRepository.existsByEmail(email)) {
            return false;
        }

        // Create a new user
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        user.setEmailVerified(false);  // Set to false if verification is required

        Role userRole = roleRepository.findByValue(role);
        if (userRole == null) {
            throw new RuntimeException("Role not found with value: " + role);
        }


        user.setRoles(Collections.singletonList(userRole));
        User us =  userRepository.save(user);
        return true;

    }

    @Transactional
    public boolean verifyEmail(String email) {
        // Find user by email
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEmailVerified(true);
            userRepository.save(user);
            return true;  // Email verified successfully
        }

        return false;  // User not found
    }

    public Map<String, String> login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!bCryptPasswordEncoder.matches(password, user.getHashedPassword()) || !user.isEmailVerified()) {
            throw new RuntimeException("Invalid login credentials or email not verified");
        }

        // Generate tokens
//        String accessToken = jwtTokenUtil.generateAccessToken(user);
//        String refreshToken = jwtTokenUtil.generateRefreshToken(user);

        String accessToken = "tempImplementation";
        String refreshToken = "tempImplementation";

        // Save the refresh token in the database
        Token token = new Token();
        token.setUser(user);
        token.setToken(refreshToken);
        token.setExpiryAt(new Date(System.currentTimeMillis() + jwtTokenUtil.REFRESH_TOKEN_EXPIRATION));
        tokenRepository.save(token);

        // Return tokens
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    public boolean validateAccessToken(String token) {
        return jwtTokenUtil.validateToken(token);
    }
}
