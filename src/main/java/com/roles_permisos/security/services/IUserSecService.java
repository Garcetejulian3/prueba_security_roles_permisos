package com.roles_permisos.security.services;

import java.util.List;
import java.util.Optional;

import com.roles_permisos.security.models.UserSec;

public interface IUserSecService {
    List findAll();
    Optional<UserSec> findById(Long id);
    UserSec save(UserSec userSec);
    void deleteById(Long id);
    void update(UserSec userSec);

}
