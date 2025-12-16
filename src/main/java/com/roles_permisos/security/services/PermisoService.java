package com.roles_permisos.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.roles_permisos.security.models.Permiso;
import com.roles_permisos.security.repository.PermisoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermisoService implements IPermisoService{

    private final PermisoRepository permisoRepository;
    @Override
    public List<Permiso> findAll() {
        return permisoRepository.findAll();
    }

    @Override
    public Optional<Permiso> findById(Long id) {
        return permisoRepository.findById(id);
    }

    @Override
    public Permiso save(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    @Override
    public void delete(Long id) {
        permisoRepository.deleteById(id);
    }

    @Override
    public Permiso update(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

}
