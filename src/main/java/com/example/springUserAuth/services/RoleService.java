package com.example.springUserAuth.services;

import com.example.springUserAuth.models.Role;
import com.example.springUserAuth.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Role createRole(int value) {

        // Check if a role with the given value already exists
        if (roleRepository.findByValue(value) != null) {
            throw new DataIntegrityViolationException("Role with value " + value + " already exists.");
        }

        // If it doesn't exist, create and save the new role
        Role role = new Role();
        role.setValue(value);
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Transactional
    public Role updateRole(Long id, int newValue) {
        Role role = getRoleById(id);
        if (role != null) {
            role.setValue(newValue);
            return roleRepository.save(role);
        }
        return null;
    }
}
