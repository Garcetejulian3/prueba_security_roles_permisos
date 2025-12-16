package com.roles_permisos.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roles_permisos.security.models.UserSec;
@Repository
public interface UserSecRepository extends JpaRepository<UserSec,Long>{

    Optional<UserSec> findUserSecByUsername(String username);
}
