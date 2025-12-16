package com.roles_permisos.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roles_permisos.security.models.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso,Long> {

}
