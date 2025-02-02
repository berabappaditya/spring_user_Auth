package com.example.springUserAuth.repositories;

import com.example.springUserAuth.models.Token;
import com.example.springUserAuth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    List<Token> findAllByUser(User user);
    void deleteByUser(User user);
}
