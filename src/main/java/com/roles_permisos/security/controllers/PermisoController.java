package com.roles_permisos.security.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.roles_permisos.security.models.Permiso;
import com.roles_permisos.security.services.PermisoService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/permiso")

public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping
    public ResponseEntity<List<Permiso>> findAllPermisos() {
        List<Permiso> listPermisos = permisoService.findAll();
        return ResponseEntity.ok(listPermisos);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/{id}")
    public ResponseEntity<Permiso> findPermisoById(@PathVariable Long id) {
        Optional<Permiso> permiso = permisoService.findById(id);
        return permiso.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Permiso> createPermiso(@RequestBody Permiso permiso) {
        Permiso newPermiso = permisoService.save(permiso);
        return ResponseEntity.ok(newPermiso);
    }
    
    
    
}
