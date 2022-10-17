package com.banking.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.banking.entity.Role;

@Repository
public interface RoleRepo extends CrudRepository<Role, String> {

}
