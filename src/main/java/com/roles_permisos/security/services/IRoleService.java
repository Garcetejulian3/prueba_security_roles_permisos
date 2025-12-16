package com.roles_permisos.security.services;

import java.util.List;
import java.util.Optional;

import com.roles_permisos.security.models.Role;

public interface IRoleService {
    List findAll();
    Optional<Role> findById(Long id);
    Role save(Role role);
    void deleteById(Long id);
    Role update(Role role);
}



