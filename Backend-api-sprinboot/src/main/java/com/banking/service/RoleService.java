package com.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.entity.Role;
import com.banking.repo.RoleRepo;


@Service
public class RoleService {

    @Autowired
    private RoleRepo repo;

    public Role createNewRole(Role role) {
        return repo.save(role);
    }
}
