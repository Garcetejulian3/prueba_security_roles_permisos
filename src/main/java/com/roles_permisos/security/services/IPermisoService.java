package com.roles_permisos.security.services;

import java.util.List;
import java.util.Optional;

import com.roles_permisos.security.models.Permiso;

public interface IPermisoService {
    List<Permiso> findAll();
    Optional<Permiso> findById(Long id);
    Permiso save(Permiso permiso);
    void delete(Long id);
    Permiso update(Permiso permiso);

}
