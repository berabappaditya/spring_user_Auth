package com.example.springUserAuth.repositories;

import com.example.springUserAuth.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByValue(int value);

    @Override
    List<Role> findAll();
}
