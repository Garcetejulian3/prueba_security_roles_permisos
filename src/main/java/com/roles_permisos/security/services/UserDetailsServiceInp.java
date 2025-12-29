package com.roles_permisos.security.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.roles_permisos.security.dto.AuthLoginRequestDTO;
import com.roles_permisos.security.dto.AuthResponseDTO;
import com.roles_permisos.security.models.UserSec;
import com.roles_permisos.security.repository.UserSecRepository;
import com.roles_permisos.security.utils.JwtUtils;

@Service
public class UserDetailsServiceInp implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserSecRepository userSecRepository;
    @Autowired
    private JwtUtils jwtUtils;

    //

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




    public AuthResponseDTO loginUser(AuthLoginRequestDTO authLoginRequest){
        // recuperamos el nombre de usuario y contraseña

        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(username,"Login correcto",accessToken,true);
        return authResponseDTO;
        


    }




    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Password o Contraseña incorrecta");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Password o Contraseña incorrecta");
        }
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(),userDetails.getAuthorities());
    }

}
