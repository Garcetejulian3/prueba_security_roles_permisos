package com.roles_permisos.security.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.roles_permisos.security.models.UserSec;
import com.roles_permisos.security.repository.UserSecRepository;

@Service
public class UserDetailsServiceInp implements UserDetailsService {

    @Autowired
    private UserSecRepository userSecRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tenemos nuestro UserSec y necesitamos devolverlos en formato UserDetails
        // Traemos nuestro usuario de la base de datos 
        UserSec userSec = userSecRepository.findUserSecByUsername(username)
        .orElseThrow(()-> new UsernameNotFoundException("El usuario " + username + " No fue encontrado"));

        // Creamos una lista para los permisos
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        // Traer roles y convertilos en SimpleGrantedAuthority
        userSec.getRolesList()
            .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));

        // Traer permisos y convertilos a SimpleGrantedAuthority
        userSec.getRolesList().stream()
            .flatMap(role -> role.getPermissionsList().stream())
            .forEach(permiso -> authorityList.add(new SimpleGrantedAuthority(permiso.getPermisoName())));

        return new User(
            userSec.getUsername(),
            userSec.getPassword(),
            userSec.isEnabled(),
            userSec.isAccountNotExpired(),
            userSec.isAccountNotLocked(),
            userSec.isCredentialNotExpired(),
            authorityList
        );
    }

}
