package com.roles_permisos.security.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@PreAuthorize("denyAll()")
public class HelloWordController {

    @GetMapping("/holaseg")
    @PreAuthorize("hasAuthority('READ')")
    public String secHelloWorld() {
        
        return "Hola Mundo Esto es una prueba con seguridad";
    }

    @GetMapping("/holanoseg")
    @PreAuthorize("permitAll()")
    public String noSecHelloWorld() {
        
        return "Hola Mundo Esto es una prueba sin seguridad";   
    }

    @GetMapping("/holaseg2")
    @PreAuthorize("isAuthenticated()")
    public String noSecHelloWorld2() {
        return "Hola Mundo Esto es una prueba sin seguridad desde el metodo 2";
    }
    
    

}
