package com.roles_permisos.security.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roles_permisos.security.models.Permiso;
import com.roles_permisos.security.models.Role;
import com.roles_permisos.security.services.PermisoService;
import com.roles_permisos.security.services.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermisoService permisoService;

    @GetMapping("/findall")
    public ResponseEntity<List<Role>> getAllRole() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleService.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/save")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Set<Permiso> permiList = new HashSet<Permiso>();
        Permiso readPermiso;
        // recuperar el permiso/s por su ID
        for(Permiso per:role.getPermissionsList()){
            readPermiso = permisoService.findById(per.getId()).orElse(null);
            if(readPermiso != null){
                permiList.add(readPermiso);
            }
        }
        role.setPermissionsList(permiList);
        Role newRole = roleService.save(role);
        return ResponseEntity.ok(newRole);
    }
    
    
}
