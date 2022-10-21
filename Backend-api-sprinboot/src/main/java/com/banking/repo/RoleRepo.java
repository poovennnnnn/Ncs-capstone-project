package com.banking.repo;

import org.springframework.data.repository.CrudRepository;

import com.banking.entity.Role;


public interface RoleRepo extends CrudRepository<Role, String> {

}
